/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 *
 * 2017年3月1日 	
 */
package org.longjiang.center.rpcimpl;

import org.alan.mars.rpc.server.RpcService;
import org.alan.mars.service.UidService;
import org.alan.mars.uid.UidDao;
import org.alan.mars.uid.UidTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * 
 * @scene 1.0
 * 
 * @author Alan
 *
 */
@RpcService(UidService.class)
@Component
public class UidServiceImpl implements UidService {

    UidDao uidDao;

    @Autowired
    public UidServiceImpl(UidDao uidDao) {
        this.uidDao = uidDao;
    }

    @Override
    public long getAndUpdateUid(UidTypeEnum key, int num) {
        return uidDao.getAndUpdateUid(key, num);
    }
}
