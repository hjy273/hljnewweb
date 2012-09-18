package com.cabletech.planinfo.action;

import org.apache.log4j.*;
import com.cabletech.commons.base.*;
import com.cabletech.planinfo.services.*;

public abstract class PlanInfoBaseAction extends BaseAction{

    private static Logger logger = Logger.getLogger( PlanInfoBaseAction.class.
                                   getName() );

    private PlanInfoService service;

    /**
     * <p>Retrives AdminSession home.
     * If instance does not exist, retrieve a new instance.<p>
     *
     * @return AdminSession
     */
    protected PlanInfoService getService() throws Exception{
        if( service == null ){
            logger.debug( "Getting new service" );
            this.service = new PlanInfoService();
        }
        return this.service;
    }
}
