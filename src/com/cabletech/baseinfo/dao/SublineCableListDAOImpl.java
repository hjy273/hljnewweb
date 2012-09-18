package com.cabletech.baseinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class SublineCableListDAOImpl extends HibernateDaoSupport{
    public SublineCableListDAOImpl(){
    }


    public SublineCableList addSublineCableList( SublineCableList data ) throws
        Exception{
        this.getHibernateTemplate().save( data );
        return data;
    }


    public SublineCableList findById( String id ) throws Exception{
        return( SublineCableList )this.getHibernateTemplate().load(
            SublineCableList.class, id );
    }


    public void deleteSublineCableList( SublineCableList data ) throws Exception{
        this.getHibernateTemplate().delete( data );
    }


    public SublineCableList updateSublineCableList( SublineCableList data ) throws
        Exception{
        this.getHibernateTemplate().saveOrUpdate( data );
        return data;
    }


    public void deleteBySublineID( String sublineid ) throws Exception{
        String sql = "delete from sublinecablesegment where sublineid='" +
                     sublineid + "'";
        UpdateUtil uu = new UpdateUtil();
        uu.executeUpdateWithCloseStmt( sql );
    }


    public String[] getRelatedList( String sublineid ) throws Exception{
        String sql = "select cablesegmentid from sublinecablesegment where sublineid='" +
                     sublineid + "'";
        QueryUtil qu = new QueryUtil();
        String[][] tempArr = qu.executeQueryGetArray( sql, "" );
        if( tempArr != null && tempArr.length > 0 ){
            String[] listArr = new String[tempArr.length];

            for( int i = 0; i < tempArr.length; i++ ){
                listArr[i] = tempArr[i][0];
            }
            return listArr;
        }
        else{
            return null;
        }
    }


    public String[][] getCableList( String sublineid ) throws Exception{
        String sql = "select a.cablesegmentid, nvl(b.SEGMENTNAME,'')" +
        		" from sublinecablesegment a, repeatersection b where a.cablesegmentid = b.kid(+) and a.sublineid='" +
                     sublineid + "'";
        QueryUtil qu = new QueryUtil();
        String[][] tempArr = qu.executeQueryGetArray( sql, "" );
        return tempArr;
    }

}
