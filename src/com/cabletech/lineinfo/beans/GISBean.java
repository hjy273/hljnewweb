package com.cabletech.lineinfo.beans;

import org.apache.struts.action.*;
import com.cabletech.commons.base.*;

public class GISBean extends BaseBean{
    private String serverIP;
    private String actionType;
    private String methodType;
    private String url;
    private String pid;
    private String type;
    private String funid;
    public GISBean(){
    }


    // Public Methods
    public void reset( ActionMapping mapping,
        javax.servlet.http.HttpServletRequest request ){

    }


    public String getServerIP(){
        return serverIP;
    }


    public void setServerIP( String serverIP ){
        this.serverIP = serverIP;
    }


    public String getActionType(){
        return actionType;
    }


    public void setActionType( String actionType ){
        this.actionType = actionType;
    }


    public String getMethodType(){
        return methodType;
    }


    public void setMethodType( String methodType ){
        this.methodType = methodType;
    }


    public String getUrl(){
        return url;
    }


    public void setUrl( String url ){
        this.url = url;
    }


    public String getPid(){
        return pid;
    }


    public void setPid( String pid ){
        this.pid = pid;
    }


    public String getType(){
        return type;
    }


    public void setType( String type ){
        this.type = type;
    }


    public String getFunid(){
        return funid;
    }


    public void setFunid( String funid ){
        this.funid = funid;
    }

}
