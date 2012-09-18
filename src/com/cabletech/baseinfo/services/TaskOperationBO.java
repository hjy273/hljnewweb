package com.cabletech.baseinfo.services;

import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Cable tech</p>
 * @author not attributable
 * @version 1.0
 */
public class TaskOperationBO extends BaseBisinessObject{

    TaskOperationDAOImpl dao = new TaskOperationDAOImpl();

    public TaskOperation addTaskOperation( TaskOperation taskoper ) throws Exception{
        return dao.addTaskOperation( taskoper );
    }


    public TaskOperation loadTaskOperation( String ID ) throws Exception{
        return dao.findByID( ID );
    }


    public void removeTaskOperation( TaskOperation taskoper ) throws Exception{
        dao.removeTaskOperation( taskoper );
    }


    public TaskOperation updateTaskOperation( TaskOperation taskoper ) throws Exception{
        return dao.updateTaskOperation( taskoper );
    }


    public TaskOperationBO(){
    }
}
