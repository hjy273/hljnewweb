package com.cabletech.lineinfo.dao;

import com.cabletech.commons.hb.*;
import com.cabletech.lineinfo.domainobjects.*;

public class SmsSendLogDAOImpl extends HibernateDaoSupport{
    public SmsSendLogDAOImpl(){
    }


    public SmsSendLog addSmsSendLog( SmsSendLog smsSendLog ) throws Exception{
        this.getHibernateTemplate().save( smsSendLog );
        return smsSendLog;
    }

}
