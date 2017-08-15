/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 *
 * 2017年2月27日 	
 */
package org.longjiang.center.dao;

import org.longjiang.center.user.PlatformAccMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 *
 * @scene 1.0
 *
 * @author Alan
 *
 */
public interface PlatformAccountDao extends MongoRepository<PlatformAccMapping, Long> {

	PlatformAccMapping findByPfUserId(String pfUserId);
}
