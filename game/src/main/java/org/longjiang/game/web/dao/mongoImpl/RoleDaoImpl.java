/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 * <p>
 * 2017年3月2日
 */
package org.longjiang.game.web.dao.mongoImpl;

import com.google.common.base.Stopwatch;
import org.alan.mars.mongo.MarsMongoDao;
import org.longjiang.game.web.been.player.module.Role;
import org.longjiang.game.web.dao.RoleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author alan
 * @since 1.0
 */
//@Component
public class RoleDaoImpl extends MarsMongoDao<Role, Long> implements RoleDao {

    Logger log = LoggerFactory.getLogger(getClass());

    public RoleDaoImpl(@Autowired MongoTemplate mongoTemplate) {
        super(Role.class, mongoTemplate);
    }

    @CachePut(value = "saveRole", key = "#p0.roleUid")
    @Override
    public Role save(Role role) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("开始保存玩家数据,roleUid={}", role.roleUid);
        Role r = super.save(role);
        log.info("玩家数据保存完成,roleUid={},useTime={}", role.roleUid, stopwatch);
        return r;
    }

    @Cacheable(value = "findRoleByUserId", key = "#p0")
    @Override
    public Role findByUserId(long userId) {
        log.debug("find role by user id, accountId is {}", userId);
        return mongoTemplate.findOne(
                Query.query(Criteria.where("accountId").is(userId)), Role.class);
    }

    @Cacheable(value = "roleExist", key = "#p0")
    @Override
    public boolean existRole(long roleUid) {
        log.debug("exist role by role uid, roleUid is {}", roleUid);
        return mongoTemplate.exists(
                Query.query(Criteria.where("roleUid").is(roleUid)), Role.class);
    }

    @Cacheable(value = "roleNameExist", key = "#p0")
    @Override
    public boolean existRoleName(String roleName) {
        log.debug("check role name exist, role name is  {}", roleName);
        Criteria criteria = Criteria.where("roleName").is(roleName);
        Query query = new Query(criteria);
        return mongoTemplate.exists(query, Role.class);
    }

    @Cacheable(value = "findRoleByroleUid", key = "#p0")
    @Override
    public Role findOne(Long roleUid) {
        log.debug("向数据库查找数据，{}", roleUid);
        return super.findOne(roleUid);
    }
}
