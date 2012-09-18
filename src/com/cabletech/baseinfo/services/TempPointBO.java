package com.cabletech.baseinfo.services;

import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.*;

public class TempPointBO extends BaseBisinessObject{
    TempPointDAOImpl dao = new TempPointDAOImpl();

    public TempPointBO(){
    }


    public TempPoint getTP( String id ) throws Exception{
        return dao.getTP( id );
    }


    public TempPoint loadTP( String id ) throws Exception{
        return dao.findById( id );
    }


    public void deleteTempPoint( TempPoint data ) throws Exception{
        dao.removeTempPoint( data );
    }


    /**
     * 临时点存为巡检点之后，将临时点置位
     * @param tempID String
     * @throws Exception
     */
    public void setTempEdit( String tempID ) throws Exception{
        String sql = "update temppointinfo set bedited = '1' where pointid = '" +
                     tempID + "'";

   //     //System.out.println( "更新临时点 :" + sql );

        UpdateUtil jutil = new UpdateUtil();
        jutil.executeUpdate( sql );

    }

}
