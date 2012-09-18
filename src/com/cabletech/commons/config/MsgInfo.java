package com.cabletech.commons.config;

import java.text.*;

public class MsgInfo {
    private String info;

    private String link;

    private String id;

    private String requestUri = "";

    private Object args[];

    private Object linkArgs[] = null;

    public String getInfo() {
        return info;
    }

    public String getLink() {
        if (linkArgs == null) {
            return link;
        } else {
            MessageFormat form = new MessageFormat(link);
            return form.format(this.linkArgs);
        }
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setLinkArg(Object[] args) {
        this.linkArgs = args;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getMessage() {
        MessageFormat form = new MessageFormat(info);
        return form.format(args);
    }

    public void setArg(Object arg0) {
        args = new String[1];
        args[0] = arg0;
    }

    public void setArg(Object arg0, Object arg1) {
        args = new String[2];
        args[0] = arg0;
        args[1] = arg1;
    }

    public void setArg(Object arg0, Object arg1, Object arg2) {
        args = new String[3];
        args[0] = arg0;
        args[1] = arg1;
        args[2] = arg2;
    }

    public String toString() {
        StringBuffer strBuf = new StringBuffer();
        if (this.id != null) {
            strBuf.append("ID=" + this.id);
        } else {
            strBuf.append("ID=NULL");
        }
        if (this.link != null) {
            strBuf.append(",Link=" + this.link);
        } else {
            strBuf.append(",Link=NULL");
        }
        if (this.info != null) {
            strBuf.append(",Info=" + this.info);
        } else {
            strBuf.append(",Info=NULL");
        }
        if (this.args != null) {
            strBuf.append(",Args=" + this.args.toString());
        } else {
            strBuf.append(",Args=NULL");
        }
        return strBuf.toString();

    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

}
