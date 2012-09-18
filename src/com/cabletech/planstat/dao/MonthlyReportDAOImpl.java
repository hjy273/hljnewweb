package com.cabletech.planstat.dao;

import com.cabletech.commons.hb.QueryUtil;
import java.util.List;
import org.apache.log4j.Logger;
import com.cabletech.commons.hb.HibernateDaoSupport;
import java.sql.ResultSet;

public class MonthlyReportDAOImpl extends HibernateDaoSupport{
    private Logger logger = Logger.getLogger(MonthlyReportDAOImpl.class.getName());
    public MonthlyReportDAOImpl(){
    }
    public List getBackInfoList( String sql ){
        QueryUtil query = null;
        List list = null;
            try{
                query = new QueryUtil();
                list = query.queryBeans( sql );
            }
            catch( Exception ex ){
                logger.error("查询异常："+ex.getMessage());
                list =null;
                ex.printStackTrace();
                
            }
            return list;
    }
    
    public ResultSet getBackInfoListJDBC( String sql ){
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

}
