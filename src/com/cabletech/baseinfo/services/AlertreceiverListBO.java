package com.cabletech.baseinfo.services;

import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;

public class AlertreceiverListBO extends BaseBisinessObject{
    /**
     * addDepart
     */
    AlertreceiverListDAOImpl dao = new AlertreceiverListDAOImpl();
    //AlertreceiverListDAO dao=DaoFactory.createDao();

    public void addAlertreceiverList( AlertreceiverList data ) throws Exception{
        dao.addAlertreceiverList( data );
    }


    public AlertreceiverList loadAlertreceiverList( String id ) throws Exception{
        return dao.findById( id );
    }


    public void removeAlertreceiverList( AlertreceiverList depart ) throws Exception{
        dao.removeAlertreceiverList( depart );
    }


    public AlertreceiverList updateAlertreceiverList( AlertreceiverList AlertreceiverList ) throws Exception{
        return dao.updateAlertreceiverList( AlertreceiverList );
    }
}
