package org.longjiang.game.web.controller;

import org.longjiang.game.web.been.player.module.Role;
import org.longjiang.game.web.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2017/6/13.
 *
 * @author Alan
 * @since 1.0
 */
@RequestMapping("role")
@RestController
public class RoleInfoController {

    @Autowired
    RoleDao roleDao;

    public Role getRoleInfo(long accountId) {
        return roleDao.findByUserId(accountId);
    }
}
