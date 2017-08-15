package org.longjiang.game.web.been.player.module;

import org.longjiang.core.data.Protobuf;

/**
 * Created on 2017/6/20.
 *
 * @author Alan
 * @since 1.0
 */
@Protobuf
public class FightSoulItem {
    public int id;
    public int type;
    public int star;
    public int level;
    public int totalExp;
    public int count;

    public void update(int type, int star, int level, int exp) {
        this.type = type;
        this.star = star;
        this.level = level;
        this.totalExp = exp;
    }
}
