package org.longjiang.game.web.been.player.module;

import org.longjiang.core.data.Protobuf;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created on 2017/6/6.
 *
 * @author Alan
 * @since 1.0
 */
@Protobuf
@Document
public class Role {
    @Indexed
    public long accountId;
    @Id
    public long roleUid;
    public String roleName;
    public long money;
    public long diamond;
}
