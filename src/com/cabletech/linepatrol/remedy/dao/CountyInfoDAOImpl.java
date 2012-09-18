package com.cabletech.linepatrol.remedy.dao;

import com.cabletech.commons.hb.*;
import com.cabletech.linepatrol.remedy.domain.CountyInfo;
 
import org.apache.log4j.Logger;

public class CountyInfoDAOImpl extends HibernateDaoSupport{
	
	private Logger logger = Logger.getLogger("CountyDAOImpl");
    public CountyInfo addCounty( CountyInfo countyInfo ) throws Exception{
        this.getHibernateTemplate().save( countyInfo );
        return countyInfo;
    }


    public CountyInfo findById( String id ) throws Exception{
        return( CountyInfo )this.getHibernateTemplate().load( CountyInfo.class, id );
    }


    public void removeCounty( CountyInfo countyInfo ) throws Exception{
        this.getHibernateTemplate().delete( countyInfo );
    }


    public CountyInfo updateCounty( CountyInfo countyInfo ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( countyInfo );
        return countyInfo;
    }

}
