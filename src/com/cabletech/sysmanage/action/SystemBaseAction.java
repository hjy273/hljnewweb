package com.cabletech.sysmanage.action;

import org.apache.log4j.*;
import com.cabletech.commons.base.*;
import com.cabletech.sysmanage.services.*;

public abstract class SystemBaseAction extends BaseAction{

    private static Logger logger = Logger.getLogger( SystemBaseAction.class.
                                   getName() );

    private SystemService service;

    protected SystemService getService() throws Exception{
        if( service == null ){
            logger.debug( "Getting new service" );
            this.service = new SystemService();
        }
        return this.service;
    }
}
