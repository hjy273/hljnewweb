package com.cabletech.baseinfo.services;

import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;

public class AlertInfoBO extends BaseBisinessObject{
    /**
     * addDepart
     */
    AlertInfoDAOImpl dao = new AlertInfoDAOImpl();
    //DepartDAO dao=DaoFactory.createDao();


    public AlertInfo loadAlertInfo( String id ) throws Exception{
        return dao.findById( id );
    }
}
