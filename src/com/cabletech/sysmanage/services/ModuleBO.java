package com.cabletech.sysmanage.services;

import java.util.*;

import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.*;
import com.cabletech.sysmanage.dao.*;
import com.cabletech.sysmanage.domainobjects.*;

public class ModuleBO extends BaseBisinessObject{

    ModuleDAOImpl dao = new ModuleDAOImpl();

    public void addModule( Module data ) throws Exception{
        dao.addModule( data );
    }


    public void removeModule( Module data ) throws Exception{
        dao.removeModule( data );
    }


    public Module loadModule( String id ) throws Exception{
        return dao.findById( id );
    }


    public Module updateModule( Module data ) throws Exception{
        return dao.updateModule( data );
    }


    /**
     * 按照级别取得模块列表
     * @return Vector
     * @throws Exception
     */
    public Vector getModuleList() throws Exception{

        String sql = "select id, modulename, ifdefaultauthorized from systemmodulemaster where levelkey = '1'";
        QueryUtil jutil = new QueryUtil();

        String[][] topMoArr = jutil.executeQueryGetArray( sql, "" );

        Vector moduleListVct = getSubLevelModule( topMoArr );
        return moduleListVct;

    }


    /**
     *
     * @param ParentMoArr String[][]
     * @return Vector
     * @throws Exception
     */
    public Vector getSubLevelModule( String[][] ParentMoArr ) throws
        Exception{

        Vector moduleVct = new Vector();

        for( int i = 0; i < ParentMoArr.length; i++ ){

            String parentMoudleId = ParentMoArr[i][0];
            String sql =
                " select id, modulename, ifdefaultauthorized from systemmodulemaster where \n";
            sql += " id IN (SELECT id FROM systemmodulemaster CONNECT BY PRIOR id = PARENTID START WITH id = '" +
                parentMoudleId + "') \n";
            sql += " order by id \n";

            QueryUtil jutil = new QueryUtil();
            String[][] moduleArr = jutil.executeQueryGetArray( sql, "" );

            moduleVct.add( moduleArr );

        }

        return moduleVct;
    }

}
