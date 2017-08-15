/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 * <p>
 * 2017年3月2日
 */
package org.longjiang.game.web.dao.redisImpl;

import org.apache.commons.collections4.map.HashedMap;
import org.longjiang.core.util.RedisKeyGen;
import org.longjiang.game.web.been.player.Player;
import org.longjiang.game.web.been.player.PlayerModule;
import org.longjiang.game.web.dao.PlayerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.zip.CRC32;

/**
 * @author alan
 * @since 1.0
 */
//@Component
public class PlayerDaoImpl implements PlayerDao {

    Logger log = LoggerFactory.getLogger(getClass());

    private RedisTemplate redisTemplate;

    public PlayerDaoImpl(@Autowired RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getDocumentKey(long playerUid) {
        return RedisKeyGen.documentKeyName(Player.class.getSimpleName(), playerUid);
    }

    @Override
    public Player findPlayer(long playerUid) {
        String document = getDocumentKey(playerUid);
        Player player = new Player();
        List<PlayerModule> modules = redisTemplate.opsForHash().values(document);
        return player.setModuleData(modules.toArray(new PlayerModule[modules.size()]));
    }

    @Override
    public boolean save(Player player) {
        Collection<PlayerModule> modules = player.moduleMap.values();
        RedisSerializer redisSerializer = redisTemplate.getValueSerializer();
        CRC32 crc32 = new CRC32();
        Map<String, byte[]> values = new HashMap<>();
        modules.forEach(playerModule -> {
            String name = playerModule.moduleName().name();
            byte[] data = redisSerializer.serialize(playerModule);
            crc32.update(data);
            //与数据加载时计算的crc值，不相等则表示有改变
            if (playerModule.crc != crc32.getValue()) {
                values.put(name, data);
            }
        });
        String document = getDocumentKey(player.playerUid);
        redisTemplate.opsForHash().putAll(document, values);
        return true;
    }

    @Transactional
    @Override
    public Player findPlayerByModule(final long playerUid, Player.ModuleName... modules) {
        Player player = new Player();
        player.setModuleData(loadModules(playerUid, modules).toArray(new PlayerModule[0]));
        return player;
    }

    @Override
    public void loadModules(Player player, Player.ModuleName... modules) {
        player.setModuleData(loadModules(player.playerUid, modules).toArray(new PlayerModule[0]));
    }

    @Override
    public List<PlayerModule> loadModules(long playerUid, Player.ModuleName... modules) {
        if (modules.length == 0) {
            return Collections.EMPTY_LIST;
        }
        CRC32 crc32 = new CRC32();
        Map<Player.ModuleName, Long> crcMap = new HashedMap<>();
        RedisSerializer redisSerializer = redisTemplate.getKeySerializer();
        String document = getDocumentKey(playerUid);
        redisTemplate.watch(document);
        List<PlayerModule> values = redisTemplate.executePipelined((RedisConnection connection) -> {
            for (Player.ModuleName module : modules) {
                byte[] data = connection.hGet(redisSerializer.serialize(document), redisSerializer.serialize(module.name()));
                crc32.update(data);
                crcMap.put(module, crc32.getValue());
            }
            return null;
        });
        return values;
    }


}
