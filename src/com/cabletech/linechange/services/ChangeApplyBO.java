package com.cabletech.linechange.services;

import java.util.List;
import com.cabletech.linechange.domainobjects.ChangeInfo;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linechange.bean.ChangeInfoBean;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: Cable tech</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface ChangeApplyBO extends ChangeServices{
    public List getChangeInfo(UserInfo user,ChangeInfoBean bean);
    public ChangeInfo getChangeInfo(String changeid);
    public ChangeInfo saveChangeInfo(ChangeInfo changeinfo);
    public ChangeInfo updateChangeInfo(ChangeInfo changeinfo,String [] delfileid);
    public boolean removeChangeInfo(String changeid);

}
