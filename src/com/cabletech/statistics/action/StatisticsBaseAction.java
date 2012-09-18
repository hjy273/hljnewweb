package com.cabletech.statistics.action;

import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.statistics.services.*;

public abstract class StatisticsBaseAction extends BaseAction{
    private static Logger logger = Logger.getLogger( StatisticsBaseAction.class.
                                   getName() );
    public static ActionErrors errors = new ActionErrors();

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


    /**
     * 取得RegionID传进查询条件
     * @param request HttpServletRequest
     * @return String
     */
    public String getRegionID( HttpServletRequest request ){
        UserInfo user;
        user = super.getLoginUserInfo( request );
        if( user != null && user.getRegionID() != null ){
            return user.getRegionID();
        }
        else{
            return "@ErrorNoRegionID@";
        }

    }

}
