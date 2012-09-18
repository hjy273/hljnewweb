package com.cabletech.lineinfo.services;

import com.cabletech.commons.base.*;
import com.cabletech.lineinfo.dao.*;
import com.cabletech.lineinfo.domainobjects.*;

public class SmsSendLogBO extends BaseBisinessObject{
    public SmsSendLogBO(){
    }


    SmsSendLogDAOImpl dao = new SmsSendLogDAOImpl();

    public void addSmsSendLog( SmsSendLog data ) throws Exception{
        dao.addSmsSendLog( data );
    }

}
