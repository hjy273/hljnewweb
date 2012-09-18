package com.cabletech.baseinfo.services;

import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.struts.util.*;
import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.*;

public class LineBO extends BaseBisinessObject{
    LineDAOImpl dao = new LineDAOImpl();
    private static Logger logger = Logger.getLogger( LineBO.class );
    //LineDAO dao=DaoFactory.createDao();

    public void addLine( Line data ) throws Exception{
        dao.addLine( data );
    }


    public void removeLine( Line data ) throws Exception{
        dao.removeLine( data );
    }


    public Line loadLine( String id ) throws Exception{
        return dao.findById( id );
    }


    public Line updateLine( Line line ) throws Exception{
        return dao.updateLine( line );
    }


    public boolean valiLineCanDele( String lineid ){
        return dao.valiLineCanDele( lineid );
    }


    public boolean validateLineName( String linename, String type,String region ){
        return dao.validateLineName( linename, type,region );
    }


    /**
     * getLine
     *
     * @param regionid String
     * @return List
     */
    public List getLineInfo( String regionid ){
        String sql = "select linename,lineid from lineinfo WHERE 1=1   and  (  RegionID IN (SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"
                     + regionid + "')   )  and state is null order by  linename";
        QueryUtil query = null;
        BasicDynaBean dynaBean = null;
        Vector resultVct = new Vector();
        ArrayList lableList = new ArrayList();

        try{
            query = new QueryUtil();
            Iterator it = query.queryBeans( sql ).iterator();
            while( it.hasNext() ){
                dynaBean = ( BasicDynaBean )it.next();
                lableList.add( new LabelValueBean( ( String ) ( dynaBean.get( "linename" ) ),
                    ( String ) ( dynaBean.get( "lineid" ) ) ) );
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

    public String getLineClassName(String lineClass){
        try{
            QueryUtil query=new QueryUtil();
            String sql="select name from lineclassdic where code='"+lineClass+"'";
            List list=query.queryBeans(sql);
            if(list!=null&&list.size()>0){
                BasicDynaBean bean=(BasicDynaBean)list.get(0);
                String name=(String)bean.get("name");
                return name;
            }
        }catch(Exception ex){
            logger.error(""+ex.getMessage());
        }
        return "";
    }
}
