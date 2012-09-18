package com.cabletech.baseinfo.services;

import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.struts.util.*;
import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.*;

public class DepartBO extends BaseBisinessObject{
    private static Logger logger = Logger.getLogger( DepartBO.class );
    /**
     * addDepart
     */
    DepartDAOImpl dao = new DepartDAOImpl();
    //DepartDAO dao=DaoFactory.createDao();

    public void addDepart( Depart data ) throws Exception{
        dao.addDepart( data );
    }


    public Depart loadDepart( String id ) throws Exception{
        return dao.findById( id );
    }


    public void removeDepart( Depart depart ) throws Exception{
        dao.removeDepart( depart );
    }


    public Depart updateDepart( Depart depart ) throws Exception{
        return dao.updateDepart( depart );
    }


    /**
     * getDept
     *
     * @param regionid String
     * @return List
     */
    public List getDept( String regionid ){

        String sql = "select deptname,deptid from deptinfo WHERE 1=1   and  (  RegionID IN (SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"
                     + regionid + "')   )  and state is null order by  deptname";
        QueryUtil query = null;
        BasicDynaBean dynaBean = null;
        Vector resultVct = new Vector();
        ArrayList lableList = new ArrayList();

        try{
            query = new QueryUtil();
            Iterator it = query.queryBeans( sql ).iterator();
            while( it.hasNext() ){
                dynaBean = ( BasicDynaBean )it.next();
                lableList.add( new LabelValueBean( ( String ) ( dynaBean.get( "deptname" ) ),
                    ( String ) ( dynaBean.get( "deptid" ) ) ) );
                //lableList.add(dynaBean);
            }
            resultVct.add( lableList );
            logger.info( resultVct );
            return resultVct;
        }
        catch( Exception ex ){
            logger.error( "加载部门用户时出错：" + ex.getMessage() );
            return null;
        }

    }
    /**通过登录用户对象得到可以查询的部门
     * 
     * @param userinfo
     * @return
     */
    public List<String> getDeptByUser(UserInfo userinfo){
    	return dao.findByUser(userinfo);
    }
    
    public List<Depart> getAllDepart(UserInfo userInfo){
    	return dao.getAllDepart(userInfo);
    }
}
