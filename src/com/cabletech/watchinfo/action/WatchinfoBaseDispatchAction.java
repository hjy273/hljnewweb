package com.cabletech.watchinfo.action;

import org.apache.log4j.*;
import com.cabletech.commons.base.*;
import com.cabletech.watchinfo.services.*;

public abstract class WatchinfoBaseDispatchAction extends BaseDispatchAction{

    private static Logger logger = Logger.getLogger( WatchinfoBaseAction.class.
                                   getName() );

    private WatchInfoService service;

    /**
     * <p>Retrives AdminSession home.
     * If instance does not exist, retrieve a new instance.<p>
     *
     * @return AdminSession
     */
    protected WatchInfoService getService() throws Exception{
        if( service == null ){
            logger.debug( "Getting new service" );
            this.service = new WatchInfoService();
        }
        return this.service;
    }
}
