package com.cabletech.troublemanage.services;

import com.cabletech.commons.base.*;
import com.cabletech.troublemanage.dao.*;
import com.cabletech.troublemanage.domainobjects.*;

public class AccidentBO extends BaseBisinessObject{

    AccidentDAOImpl dao = new AccidentDAOImpl();
    //DepartDAO dao=DaoFactory.createDao();

    public void addAccident( Accident data ) throws Exception{
        dao.addAccident( data );
    }


    public Accident loadAccident( String id ) throws Exception{
        return dao.findById( id );
    }


    public void removeAccident( Accident data ) throws Exception{
        dao.removeAccident( data );
    }


    public Accident updateAccident( Accident data ) throws Exception{
        return dao.updateAccident( data );
    }
}
