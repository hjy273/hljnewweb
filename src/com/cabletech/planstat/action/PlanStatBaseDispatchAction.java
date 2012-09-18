package com.cabletech.planstat.action;

import org.apache.log4j.*;
import com.cabletech.commons.base.*;
import com.cabletech.planstat.services.PlanStatService;

public abstract class PlanStatBaseDispatchAction extends BaseDispatchAction{

    private static Logger logger = Logger.getLogger( PlanStatBaseDispatchAction.class.
                                   getName() );

    private PlanStatService service;

    /**
     * <p>Retrives AdminSession home.
     * If instance does not exist, retrieve a new instance.<p>
     *
     * @return AdminSession
     */
    protected PlanStatService getService() throws Exception{
        if( service == null ){
            logger.debug( "Getting new service" );
            this.service = new PlanStatService();
        }
        return this.service;
    }
}
