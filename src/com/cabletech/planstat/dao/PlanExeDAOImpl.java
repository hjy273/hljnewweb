package com.cabletech.planstat.dao;

import com.cabletech.commons.hb.HibernateDaoSupport;
import java.util.List;
import com.cabletech.planstat.dao.PlanExeResultDAO;
import org.apache.log4j.*;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.planstat.domainobjects.PlanStat;

public class PlanExeDAOImpl extends HibernateDaoSupport  implements PlanExeResultDAO{
    private Logger logger = Logger.getLogger(PlanExeDAOImpl.class.getName());
    public PlanExeDAOImpl(){
    }
    public List getBackInfoList( String hql ){
        QueryUtil query = null;
        try{
            query = new QueryUtil();
            List list = query.queryBeans( hql );
            return list;
        }
        catch( Exception ex1 ){
            return null;
        }

    }
    public PlanStat getPlanStat(String planid) throws Exception{
        return (PlanStat)this.getHibernateTemplate().load(PlanStat.class,planid);

    }




}


