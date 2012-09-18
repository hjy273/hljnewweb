package com.cabletech.baseinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import java.sql.*;

public class RegionDAOImpl extends HibernateDaoSupport{
    private Logger logger = Logger.getLogger("RegionDAOImpl");
    public Region addRegion( Region region ) throws Exception{
        //测试
    //    System.out.print( "准备保存Region信息" );
        this.getHibernateTemplate().save( region );
  //      System.out.print( "已保存Region信息，返回Region对象" );
        return region;
    }


    public Region findById( String id ) throws Exception{
        return( Region )this.getHibernateTemplate().load( Region.class, id );
    }


    public void removeRegion( Region region ) throws Exception{
        this.getHibernateTemplate().delete( region );
    }


    public Region updateRegion( Region region ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( region );
        return region;
    }


    /**
     * getRoot
     */
    public String getRoot(){
        String sql = "select regionid from region r where r.REGIONID like '%0000' and (parentregionid is null or parentregionid = '000000')";
        String regionid = "";
        ResultSet rs = null;
        try{
            QueryUtil query = new QueryUtil();
            rs = query.executeQuery(sql);
            rs.next();
            regionid = rs.getString("regionid");
            logger.info("root :"+regionid);
            return regionid;
        }
        catch( Exception ex ){
            try{
                rs.close();
            }
            catch( SQLException ex1 ){
                rs = null;
            }
            logger.info("查询顶级区域时出错:"+ex.getMessage());
            return null;
        }

    }

}


