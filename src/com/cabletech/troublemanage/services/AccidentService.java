package com.cabletech.troublemanage.services;

import java.util.*;
import javax.servlet.http.*;

import com.cabletech.commons.base.*;
import com.cabletech.troublemanage.beans.*;
import com.cabletech.troublemanage.domainobjects.*;

public class AccidentService extends BaseService{
    AccidentBO bo;
    StatisticsBO stabo;

    public AccidentService(){
        bo = new AccidentBO();
        stabo = new StatisticsBO();
    }


    public void addAccident( Accident data ) throws Exception{
        bo.addAccident( data );
    }


    public void removeAccident( Accident data ) throws Exception{
        bo.removeAccident( data );
    }


    public Accident loadAccident( String id ) throws Exception{
        return bo.loadAccident( id );
    }


    public Accident updateAccident( Accident region ) throws Exception{
        return bo.updateAccident( region );
    }


    /*±¨±í*/
    public void ExportTroubleNoticeform( AccidentBean bean, Vector taskVct,
        HttpServletResponse response ) throws
        Exception{
        stabo.ExportTroubleNoticeform( bean, taskVct, response );
    }


    public void ExportAccidentNoticeform( AccidentBean bean, Vector taskVct,
        HttpServletResponse response ) throws
        Exception{
        stabo.ExportAccidentNoticeform( bean, taskVct, response );
    }


    public void exportUndoneTrouble( List list, HttpServletResponse response ) throws
        Exception{
        stabo.exportUndoneTrouble( list, response );
    }


    public boolean exportTroubleResult( List list, HttpServletResponse response ) throws
        Exception{
        return stabo.exportTroubleResult( list, response );
    }


    public void exportUndoneAccident( List list, HttpServletResponse response ) throws
        Exception{
        stabo.exportUndoneAccident( list, response );
    }


    public boolean exportAccidentResult( List list, HttpServletResponse response ) throws
        Exception{
        return stabo.exportAccidentResult( list, response );
    }
}
