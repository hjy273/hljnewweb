package com.cabletech.linechange.services;

import com.cabletech.linechange.domainobjects.ChangeInfo;
import com.cabletech.linechange.bean.ChangeInfoBean;
import java.util.List;
import com.cabletech.baseinfo.domainobjects.UserInfo;

public interface ConsignBO{
    public void addOrUpdateConsign( ChangeInfo changeinfo );


    public List getChangeInfo();


    public ChangeInfo getChangeInfo( String changeid );


    public List getChangeInfo( UserInfo user, ChangeInfoBean changeinfo );


    public List getConsignInfo( UserInfo user, ChangeInfoBean changeinfo );

}
