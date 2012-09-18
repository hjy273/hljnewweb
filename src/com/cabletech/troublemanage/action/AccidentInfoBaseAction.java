package com.cabletech.troublemanage.action;

import org.apache.log4j.*;
import com.cabletech.commons.base.*;
import com.cabletech.troublemanage.services.*;

public abstract class AccidentInfoBaseAction extends BaseAction{

    private static Logger logger = Logger.getLogger( AccidentInfoBaseAction.class.
                                   getName() );

    private AccidentService service;
    /**
     * <p>Retrives AdminSession home.
     * If instance does not exist, retrieve a new instance.<p>
     * @return AccidentService
     * @throws Exception
     */
    protected AccidentService getService() throws Exception{
        if( service == null ){
            logger.debug( "Getting new service" );
            this.service = new AccidentService();
        }
        return this.service;
    }
}
