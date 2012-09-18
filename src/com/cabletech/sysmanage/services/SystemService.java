package com.cabletech.sysmanage.services;

import java.util.*;

import com.cabletech.commons.base.*;
import com.cabletech.sysmanage.domainobjects.*;

public class SystemService extends BaseService{

    ModuleBO mbo;

    public SystemService(){
        mbo = new ModuleBO();
    }


    //Ä£¿é
    public void addModule( Module data ) throws Exception{
        mbo.addModule( data );
    }


    public Module loadPatrolOp( String id ) throws Exception{
        return mbo.loadModule( id );
    }


    public Module updateModule( Module data ) throws Exception{
        return mbo.updateModule( data );
    }


    public void removeModule( Module data ) throws Exception{
        mbo.removeModule( data );
    }


    public Vector getMoList() throws Exception{
        return mbo.getModuleList();
    }

}
