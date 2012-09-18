package com.cabletech.baseinfo.dao;

import java.util.*;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class TempPointDAOImpl extends HibernateDaoSupport{

    public TempPointDAOImpl(){
    }


    public TempPoint getTP( String pointID ) throws Exception{
        TempPoint tp = null;
        List tpList = this.getHibernateTemplate().find(
                      "select gpscoordinate,simid,bedited,receivetime,regionid from temppointinfo where pointid=" +
                      "\'" + String.valueOf( pointID ) + "\'" );
        //find("select pointid from temppointinfo where pointid=",pointID);
        //List tplist = this.getHibernateTemplate().find
        if( ( tpList != null ) && ( tpList.size() > 0 ) ){
            tp = ( TempPoint )tpList.get( 0 );
        }
        if( tp == null ){
            //tp.setPointID("null");
            //System.out.println( "tp is null" );
        }

        return tp;
    }


    public TempPoint findById( String id ) throws Exception{
        //System.out.println( id );
        return( TempPoint )this.getHibernateTemplate().load( TempPoint.class, id );
    }


    public void removeTempPoint( TempPoint point ) throws Exception{
        this.getHibernateTemplate().delete( point );
    }

}
