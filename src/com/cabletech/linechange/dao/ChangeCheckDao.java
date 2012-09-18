package com.cabletech.linechange.dao;

import java.util.List;
import com.cabletech.linechange.domainobjects.ChangeInfo;
import com.cabletech.linechange.domainobjects.ChangeCheck;

public interface ChangeCheckDao extends Dao{
    public List getCheckInfoList( String hql );


    public ChangeCheck getCheckInfo( String id );


    public void insertCheck( ChangeCheck checkinfo,ChangeInfo changeinfo );


    public void updateCheck( ChangeCheck checkinfo,ChangeInfo changeinfo );


    public void removeCheck( ChangeCheck checkinfo );

}
