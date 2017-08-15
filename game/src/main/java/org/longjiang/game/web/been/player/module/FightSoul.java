package org.longjiang.game.web.been.player.module;

import org.longjiang.core.data.Protobuf;

/**
 * Created on 2017/6/20.
 *
 * @author Alan
 * @since 1.0
 */
@Protobuf
public class FightSoul {
    public int pos;
    public FightSoulItem item;

    public FightSoul() {
        item = new FightSoulItem();
    }
}
