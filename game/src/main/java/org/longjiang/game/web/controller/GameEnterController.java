package org.longjiang.game.web.controller;

import org.alan.mars.uid.UidCacheManager;
import org.alan.mars.uid.UidTypeEnum;
import org.longjiang.core.data.Protobuf;
import org.longjiang.core.result.GeneralResult;
import org.longjiang.game.web.been.player.module.Role;
import org.longjiang.game.web.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 游戏进入控制器，管理游戏角色创建，首次进入创建角色
 * <p>
 * <p>
 * Created on 2017/6/6.
 *
 * @author Alan
 * @since 1.0
 */
@RequestMapping("enter")
@RestController
public class GameEnterController {

    @Protobuf
    public static class CreateRole {
        public long accountId;
        public String token;
        public String roleName;
    }

    @Autowired
    RoleDao roleDao;

    @Autowired
    UidCacheManager uidCacheManager;

    @RequestMapping(value = "create", method = RequestMethod.POST, produces = "application/protobuf")
    public GeneralResult createRole(@RequestBody CreateRole createRole) {
        //该账号下没有找到角色
        if (roleDao.findByUserId(createRole.accountId) != null) {
            return GeneralResult.ROLE_EXIST();
        }
        if (roleDao.existRoleName(createRole.roleName)) {
            return GeneralResult.ROLE_NAME_REPEAT();
        }

        Role role = createRole(createRole.accountId, createRole.roleName);
        roleDao.save(role);
        return GeneralResult.SUCCESS().setData(role);
    }

    @RequestMapping(value = "getRole", method = RequestMethod.POST, produces = "application/protobuf")
    public GeneralResult getRole(@RequestBody Long roleUid) {
        Role role = roleDao.findOne(roleUid);
        return GeneralResult.SUCCESS().setData(role);
    }

    private Role createRole(long accountId, String roleName) {
        Role role = new Role();
        role.accountId = accountId;
        role.roleUid = uidCacheManager.getUid(UidTypeEnum.ROLE_UID);
        role.roleName = roleName;
        return role;
    }

}
