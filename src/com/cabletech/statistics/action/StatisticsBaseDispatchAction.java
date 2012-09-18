package com.cabletech.statistics.action;

import org.apache.log4j.*;
import com.cabletech.commons.base.*;
import com.cabletech.statistics.services.*;

public abstract class StatisticsBaseDispatchAction extends BaseDispatchAction{

    private static Logger logger = Logger.getLogger( StatisticsBaseDispatchAction.class.
                                   getName() );

    private StatisticsService service;
    /**
     * <p>Retrives AdminSession home.
     * If instance does not exist, retrieve a new instance.<p>
     *
     * @return AdminSession
     */
    protected StatisticsService getService() throws Exception{
        if( service == null ){
            logger.debug( "Getting new service" );
            this.service = new StatisticsService();
        }
        return this.service;
    }

}
