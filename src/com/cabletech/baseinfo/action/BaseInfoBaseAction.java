package com.cabletech.baseinfo.action;

import org.apache.log4j.*;
import com.cabletech.baseinfo.services.*;
import com.cabletech.commons.base.*;

public abstract class BaseInfoBaseAction extends BaseAction{

    private static Logger logger = Logger.getLogger( BaseInfoBaseAction.class.
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
