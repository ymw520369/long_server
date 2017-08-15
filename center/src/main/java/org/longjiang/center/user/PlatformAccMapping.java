/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 *
 * 2017年2月22日 	
 */
package org.longjiang.center.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * 平台账号映射表
 * 
 * @scene 1.0
 * 
 * @author Alan
 *
 */
public class PlatformAccMapping {

	/**
	 * 平台用户ID
	 */
	@Indexed
	private String pfUserId;
	/**
	 * 平台用户名
	 */
	private String pfUserName;
	/**
	 * 我们自己生成的账号ID
	 */
	@Id
	private long accountId;

	public PlatformAccMapping(String pfUserId, String pfUserName, long accountId) {
		super();
		this.pfUserId = pfUserId;
		this.pfUserName = pfUserName;
		this.accountId = accountId;
	}

	public String getPfUserId() {
		return pfUserId;
	}

	public void setPfUserId(String pfUserId) {
		this.pfUserId = pfUserId;
	}

	public String getPfUserName() {
		return pfUserName;
	}

	public void setPfUserName(String pfUserName) {
		this.pfUserName = pfUserName;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

}
