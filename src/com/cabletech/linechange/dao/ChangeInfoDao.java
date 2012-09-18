package com.cabletech.linechange.dao;

import java.util.List;
import com.cabletech.linechange.domainobjects.ChangeInfo;

public interface ChangeInfoDao extends Dao{
    public List getChangeInfo(String hql);
    public ChangeInfo getChange(String changeid);
    public void insertChange(ChangeInfo change);
    public void updateChange(ChangeInfo change);
    public void removeChange(ChangeInfo changeinfo);
}
