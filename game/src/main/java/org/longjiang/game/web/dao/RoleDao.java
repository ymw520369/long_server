package org.longjiang.game.web.dao;

import org.longjiang.game.web.been.player.module.Role;

/**
 * Created on 2017/6/6.
 *
 * @author Alan
 * @since 1.0
 */
public interface RoleDao {

    public Role save(Role role);

    public abstract Role findByUserId(long userId);

    public abstract boolean existRoleName(String roleName);

    public abstract boolean existRole(long roleUid);

    public Role findOne(Long roleUid);
}
