package com.cabletech.linechange.dao;

import com.cabletech.linechange.domainobjects.ChangeBuild;
import java.util.List;
import com.cabletech.linechange.domainobjects.ChangeInfo;

public interface ChangeBuildDao{
    public List getBuildInfoList( String hql );


    public ChangeBuild getBuildInfo( String id );


    public void insertBuild( ChangeBuild buildinfo,ChangeInfo changeinfo );


    public void updateBuild( ChangeBuild buildinfo,ChangeInfo changeinfo );


    public void removeBuild( ChangeBuild buildinfo );

}
