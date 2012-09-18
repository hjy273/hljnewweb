package com.cabletech.planstat.dao;

import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.planstat.domainobjects.SublineRequest;
import com.cabletech.planstat.domainobjects.SublineResponse;

public class SublineRealTimeDAOImpl extends HibernateDaoSupport{
	private Logger logger = Logger.getLogger(SublineRealTimeDAOImpl.class.getName());
    public SublineRealTimeDAOImpl(){
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
    
    public ResultSet getReceiverListJDBC( String sql ){
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
    
    public SublineRequest addRealTimeSublineRequest(SublineRequest data) throws Exception{
        this.getHibernateTemplate().save( data );
        return data;
    }
    public SublineResponse addRealTimeSublineResponse(SublineResponse data) throws Exception{
        this.getHibernateTemplate().save( data );
        return data;
    }
    
}
