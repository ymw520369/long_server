package org.longjiang.game.web.dao;

import org.longjiang.game.web.been.player.PlayerModule;
import org.longjiang.game.web.been.player.Player;

import java.util.List;

/**
 * Created on 2017/6/19.
 *
 * @author Alan
 * @since 1.0
 */
public interface PlayerDao {
    /**
     * 通过玩家唯一ID查找玩家信息
     * <p>
     * 该方法返回player的完整信息
     *
     * @param playerUid
     * @return
     */
    Player findPlayer(long playerUid);

    /**
     * 保存玩家信息
     *
     * @param player
     * @return
     */
    boolean save(Player player);

    /**
     * 通过传入的模块类型值查找玩家数据，返回的player信息中可能不包含所有信息
     *
     * @param playerUid
     * @param modules
     * @return
     */
    Player findPlayerByModule(long playerUid, Player.ModuleName... modules);

    /**
     * 加载player指定模块的数据
     *
     * @param player
     * @param modules
     * @return
     */
    void loadModules(Player player, Player.ModuleName... modules);

    /**
     * 加载指定玩家在的模块信息
     *
     * @param playerUid
     * @param modules
     * @return
     */
    List<PlayerModule> loadModules(long playerUid, Player.ModuleName... modules);
}
