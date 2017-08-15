package org.longjiang.game.web.been.player;

import org.longjiang.core.data.Protobuf;

/**
 * 模块数据接口
 * <p>
 * Created on 2017/6/20.
 *
 * @author Alan
 * @since 1.0
 */
@Protobuf
public abstract class PlayerModule {

    public transient long crc;

    public abstract Player.ModuleName moduleName();
}
