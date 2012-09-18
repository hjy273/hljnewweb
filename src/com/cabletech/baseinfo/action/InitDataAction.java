package com.cabletech.baseinfo.action;

import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.generatorID.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.web.*;
import com.cabletech.sysmanage.action.LoginAction;

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
public class InitDataAction extends BaseInfoBaseDispatchAction{

    private static Logger logger =
        Logger.getLogger( InitDataAction.class.getName() );

    public InitDataAction(){
    }


    public ActionForward initData( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String userid = request.getParameter( "userid" );
        String pwd = request.getParameter( "password" );
        String regionid = request.getParameter( "initregionid" );
        String regionname = request.getParameter( "initregionname" );
        String depatname = request.getParameter( "initdeptid" );

        int sucFlag = doSaveInitData( userid, pwd, regionid, regionname,
                      depatname );
        if( sucFlag == 1 ){
            //如果登陆成功,转载用户信息
            UserInfo userInfo = super.getService().loadUserInfo( userid );
            //取得地区名称/部门名称
            request.getSession().setAttribute( "LOGIN_USER_REGION_NAME",
                regionname );
            request.getSession().setAttribute( "LOGIN_USER_DEPT_NAME", depatname );

            request.getSession().setAttribute( "LOGIN_USER", userInfo );

            //跳转叶面
            LoginAction loginAct = new LoginAction();
            return loginAct.login( mapping, form, request, response );

            //return mapping.findForward("loginformward");
        }
        else{
            //出错
            String errmsg = "执行过程中出现错误";
            request.setAttribute( "errmsg", errmsg );

            return mapping.findForward( "commonerror" );
        }

    }


    //区域
    public String makeRegionSQL( String regionname, String regionid ){
        StringBuffer strBf = new StringBuffer();

        strBf.append( "\n" );
        strBf.append( "INSERT INTO REGION ( REGIONNAME, REGIONID, REGIONDES, STATE,PARENTREGIONID ) VALUES ( \n" );
        strBf.append( "'" + regionname + "' ," );
        strBf.append( "'" + regionid + "' ," );
        strBf.append( "'区域描述', '1', '000000') " );

        return strBf.toString();
    }


    //部门
    public String makeDeptSQL( String depatname, String regionid ){
        StringBuffer strBf = new StringBuffer();

        strBf.append( "\n" );
        strBf.append(
            "INSERT INTO DEPTINFO ( DEPTID, DEPTNAME, LINKMANINFO, PARENTID, PARENTNAME, REGIONID, REMARK,STATE ) VALUES (  \n" );
        strBf.append( "'0000', '" );
        strBf.append( depatname );
        strBf.append( "', '联系人', '0000', '中国移动', '" );
        strBf.append( regionid );
        strBf.append( "', '备注', '1')" );

        return strBf.toString();
    }


    //初始用户
    public String makeUserSQL( String userid, String pwd, String regionid ){
        StringBuffer strBf = new StringBuffer();

        strBf.append( "\n" );
        strBf.append( "INSERT INTO USERINFO ( USERNAME, USERID, DEPTID, REGIONID, WORKID, PASSWORD, ACCOUNTTERM,PASSWORDTERM, ACCOUNTSTATE, PHONE, EMAIL, POSITION, STATE, DEPTYPE ) VALUES (   \n" );
        strBf.append( "'初始用户', '" );
        strBf.append( userid );
        strBf.append( "', '0000', '" );
        strBf.append( regionid );
        strBf.append( "', '0000', '" );
        strBf.append( pwd );
        strBf.append(
            "',  SYSDATE,  SYSDATE+300,'1', '0000', '0000', '0000',  '1',  '1' )  " );

        return strBf.toString();
    }


    //用户组
    public String makeUserGroupSQL( String regionid ){
        StringBuffer strBf = new StringBuffer();

        strBf.append( "\n" );
        strBf.append(
            "INSERT INTO USERGROUPMASTER ( ID, GROUPNAME, REGIONID, REMARK ) VALUES (   \n" );
        strBf.append( "'00000', " );
        strBf.append( "'超级用户组', '" );
        strBf.append( regionid );
        strBf.append( "',  '超级用户组' )  " );

        return strBf.toString();
    }


    //用户组用户对应表
    public String makeUserGroupUserSQL( String userid ){
        StringBuffer strBf = new StringBuffer();

        strBf.append( "\n" );
        strBf.append(
            "INSERT INTO USERGOURPUSERLIST ( ID, USERID, USERGROUPID, REMARK ) VALUES (   \n" );
        strBf.append( "'00', '" );
        strBf.append( userid );
        strBf.append( "','00000', " );
        strBf.append( " '' )  " );

        return strBf.toString();
    }


    //取得所有sonMenu
    public String[][] getSomMenuVct() throws Exception{

        String sql = " select id from SONMENU order by id ";
        QueryUtil qUtil = new QueryUtil();

        String[][] menuArr = qUtil.executeQueryGetArray( sql, "" );

        return menuArr;
    }


    /**
     * 处理数据库
     */

    public int doSaveInitData( String userid, String pwd, String regionid,
        String regionname, String depatname ) throws
        Exception{
        int sucFlag = 1;
        String sql = "";

        sql = makeRegionSQL( regionname, regionid );

        try{
            super.getDbService().dbUpdate( sql );
        }
        catch( Exception ex ){
            ex.printStackTrace();
            sucFlag = -1;
            return sucFlag;
        }

        sql = makeDeptSQL( depatname, regionid );

        try{
            super.getDbService().dbUpdate( sql );
        }
        catch( Exception ex ){
            ex.printStackTrace();
            sucFlag = -1;
            return sucFlag;
        }
        //用户
        sql = makeUserSQL( userid, pwd, regionid );

        try{
            super.getDbService().dbUpdate( sql );
        }
        catch( Exception ex ){
            ex.printStackTrace();
            sucFlag = -1;
            return sucFlag;
        }
        //用户组
        sql = makeUserGroupSQL( regionid );

        try{
            super.getDbService().dbUpdate( sql );
        }
        catch( Exception ex ){
            ex.printStackTrace();
            sucFlag = -1;
            return sucFlag;
        }

        //用户组用户对应表
        sql = makeUserGroupUserSQL( userid );

        try{
            super.getDbService().dbUpdate( sql );
        }
        catch( Exception ex ){
            ex.printStackTrace();
            sucFlag = -1;
            return sucFlag;
        }

        //用户组权限
        String[][] menuArr = getSomMenuVct();

        CustomID idFactory = new CustomID();
        String[] idArr = idFactory.getStrSeqs( menuArr.length,
                         "usergroupmoudulelist", 20 );

        for( int i = 0; i < menuArr.length; i++ ){

            UsergroupModuleList UgMList = new UsergroupModuleList();

            UgMList.setId( idArr[i] );
            UgMList.setUsergroupid( "00000" );
            UgMList.setSonmenuid( menuArr[i][0] );

            super.getService().addUgMList( UgMList );

        }

        return sucFlag;
    }

}
