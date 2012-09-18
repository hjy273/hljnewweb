package com.cabletech.analysis.dao;

import java.sql.ResultSet;
import java.util.List;
import org.apache.log4j.Logger;
import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;

public class LeakSublineDAOImpl   extends HibernateDaoSupport{
	private Logger logger = Logger.getLogger( this.getClass().getName() );
    public LeakSublineDAOImpl(){
    }
    public List getResultList( String sql ){
        QueryUtil query = null;
        List list = null;
            try{
                query = new QueryUtil();
                list = query.queryBeans( sql );
            }
            catch( Exception ex ){
                logger.error("查询异常："+ex.getMessage());
                ex.printStackTrace();
            }
            return list;


    }
    public ResultSet getResultListJDBC( String sql ){
       QueryUtil query = null;
       ResultSet resultset = null;
           try{
               query = new QueryUtil();
               resultset = query.executeQuery(sql);
           }
           catch( Exception ex ){
               logger.error("查询异常："+ex.getMessage());
               ex.printStackTrace();
           }
           return resultset;
   }
    
    /**
     * 依据SQL得到String型的返回值(单条记录）
     * @param sql String
     * @return String
     */
    public String getStringBack( String sql ){
        ResultSet rs = null;
        try{
            QueryUtil query = new QueryUtil();
            logger.info( "sql :" + sql );
            rs = query.executeQuery( sql );
            rs.next();
            String i = rs.getString( "i" );
            rs.close();
            return i;
        }
        catch( Exception ex ){
            logger.error( "依据SQL得到String型的返回值: " + ex.getMessage() );
            return null;
        }
    } 

}
