package com.cabletech.baseinfo.action;

import org.apache.log4j.*;
import com.cabletech.baseinfo.services.*;
import com.cabletech.commons.base.*;

/**
 * 实现了BaseDispatchAction，创建 service 对象
 * @author Administrator
 *
 */
public abstract class BaseInfoBaseDispatchAction extends BaseDispatchAction{

    private static Logger logger = Logger.getLogger( BaseInfoBaseDispatchAction.class.
                                   getName() );

    private BaseInfoService service;

    /**
     * <p>Retrives AdminSession home.
     * If instance does not exist, retrieve a new instance.<p>
     *
     * @return AdminSession
     */
    protected BaseInfoService getService() throws Exception{
        if( service == null ){
            logger.debug( "Getting new service" );
            this.service = new BaseInfoService();
        }
        return this.service;
    }
}
