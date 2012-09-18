package com.cabletech.linechange.services;

import java.util.List;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linechange.bean.ChangeInfoBean;
import com.cabletech.linechange.domainobjects.ChangeInfo;

public interface BuildSetoutBO{
    public void addOrUpdateDeliverTo(ChangeInfo changeinfo);
     public List getChangeInfo();
    public void addOrUpdateEngage(ChangeInfo changeinfo);
     public ChangeInfo getChangeInfo( String changeid );
    public List getChangeInfo(UserInfo user,ChangeInfoBean changeinfo);
    public List getSetoutInfo(UserInfo user,ChangeInfoBean changeinfo);

}
