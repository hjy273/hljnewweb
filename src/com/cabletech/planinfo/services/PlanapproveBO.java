package com.cabletech.planinfo.services;

import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.*;
import com.cabletech.planinfo.dao.*;
import com.cabletech.planinfo.domainobjects.*;

public class PlanapproveBO extends BaseBisinessObject{

    PlanapproveDAOImpl dao = new PlanapproveDAOImpl();

    public void addPlanapprove( Planapprove data ) throws Exception{
        dao.addPlanapprove( data );
    }


    public void addPlanapproveWithSql( Planapprove data ) throws Exception{
        String sql = "insert into PLANAPPROVEMASTER (ID, PLANID, APPROVECONTENT, APPROVER, APPROVEDATE,STATUS) ";
        sql += " values (";
        sql += "'" + data.getId() + "' ,";
        sql += "'" + data.getPlanid() + "' ,";
        sql += "'" + data.getApprovecontent() + "' ,";
        sql += "'" + data.getApprover() + "' ,";
        sql += "SYSDATE ,";
        sql += "'" + data.getStatus() + "' ";
        sql += " )";

        UpdateUtil upU = new UpdateUtil();
        upU.executeUpdateWithCloseStmt( sql );
    }
}
