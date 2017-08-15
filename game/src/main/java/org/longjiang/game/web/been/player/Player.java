package org.longjiang.game.web.been.player;

import org.longjiang.core.data.Protobuf;
import org.longjiang.game.web.been.player.module.FightSoul;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/6/6.
 *
 * @author Alan
 * @since 1.0
 */
@Protobuf
public class Player {

    static Logger log = LoggerFactory.getLogger(Player.class);

    /**
     * 玩家模块枚举
     */
    public enum ModuleName {
        ROLE
    }

    public long playerUid;
    public Map<ModuleName, PlayerModule> moduleMap = new HashMap<>();
    public ArrayList<PlayerModule> moduleList = new ArrayList<>();
    public ArrayList<Integer> asa = new ArrayList<>();
    public ArrayList<String> str = new ArrayList<>();
    public FightSoul fightSoul;

    public Player setModuleData(PlayerModule... modules) {
        for (PlayerModule module : modules) {
            moduleMap.put(module.moduleName(), module);
        }

        return this;
    }

    public <T> T getModule(ModuleName moduleName, Class<T> clazz) {
        PlayerModule playerModule = moduleMap.get(moduleName);
        if (playerModule == null) {
            log.warn("模块未被初始化，playerUid={},moduleName={}", playerUid, moduleName);
            return null;
        }
        if (clazz.isInstance(playerModule)) {
            return clazz.cast(playerModule);
        }
        log.warn("模块转型类型错误，playerUid={},moduleName={},class={}", playerUid, moduleName, clazz.getName());
        return null;
    }
}
