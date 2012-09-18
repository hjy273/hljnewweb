package com.cabletech.lineinfo.action;

import org.apache.log4j.*;
import com.cabletech.commons.base.*;
import com.cabletech.lineinfo.services.*;

public abstract class LineInfoBaseAction extends BaseAction{

    private static Logger logger = Logger.getLogger( LineInfoBaseAction.class.
                                   getName() );

    private LineinfoService service;
    /**
     * <p>Retrives AdminSession home.
     * If instance does not exist, retrieve a new instance.<p>
     *
     * @return AdminSession
     */
    protected LineinfoService getService() throws Exception{
        if( service == null ){
            logger.debug( "Getting new service" );
            this.service = new LineinfoService();
        }
        return this.service;
    }
}
