package com.cabletech.sysmanage.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.action.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.web.*;
import com.cabletech.menu.domainobjects.*;
import com.cabletech.menu.services.*;
import com.cabletech.power.*;
import com.cabletech.sysmanage.dao.*;
import com.cabletech.sysmanage.services.*;
import com.cabletech.sysmanage.util.*;
import com.cabletech.utils.*;

public class SSOLoginAction extends BaseInfoBaseDispatchAction{

    private static Logger logger = Logger.getLogger( SSOLoginAction.class );
    public ActionForward SSOLogin( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        try{
            String userId = request.getParameter( "userId" );
            String userName = request.getParameter( "userName" );
            String businessType = "1";
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName( userName );
            userInfo.setUserID( userId );
            userInfo.setDeptID( "00000001" );
            userInfo.setRegionID( "440600" );
            userInfo.setDeptype( "1" );
            userInfo.setAccountState( "1" );
            userInfo.setNewpsdate( new Date() );
            LoginDao logindao = new LoginDao();
            //用户不存在
            if( !logindao.validateUserExist( userId ) ){
                //添加用户到数据库中
                if( !logindao.addUser( userInfo ) ){
                    return mapping.findForward( "errorformward" );
                }
            }
            else{ //用户已经存在
                userInfo = super.getService().loadUserInfo( userId );
            }
            //如果账号为暂停，
            if( !userInfo.getAccountState().equals( "1" ) ){
                request.setAttribute( "loginerror", "accerror" );
                return mapping.findForward( "errorformward" );
            }
            //载入该用户的菜单
            MenuService menuService = new MenuService();
            Vector firstMenu = menuService.getFirstMenu( userInfo ,businessType);
            request.getSession().setAttribute( "MENU_FIRSTMENU", firstMenu );
            //如果没有权限 2005-05-31
            if( firstMenu == null || firstMenu.size() < 1 ){
                request.setAttribute( "loginerror", "powererror" );
                return mapping.findForward( "errorformward" );
            }

            String strFirstMenuID = null;
            if( firstMenu.size() > 0 ){
                FirstMenu menu = ( FirstMenu )firstMenu.elementAt( 0 );
                strFirstMenuID = menu.getId();
            }
    //        System.out.println( "开始载入二级菜单" );
            HashMap mapSecondlyMenu = new HashMap();
            Vector secondlyMenu = menuService.getSecondlyMenu( strFirstMenuID, userInfo );
            mapSecondlyMenu.put( strFirstMenuID, secondlyMenu );
            request.getSession().setAttribute( "MENU_SECONDLYMENU", mapSecondlyMenu );

        //    System.out.println( "开始载入三级菜单" );
            String strThirdMenuID = null;
            if( firstMenu.size() > 0 ){
                SecondlyMenu menu = ( SecondlyMenu )secondlyMenu.elementAt( 0 );
                strThirdMenuID = menu.getId();
            }
            HashMap mapThirdMenu = new HashMap();
            Vector thirdMenu = menuService.getThirdMenu( strThirdMenuID, userInfo );
            mapThirdMenu.put( strThirdMenuID, thirdMenu );
            request.getSession().setAttribute( "MENU_THIRDMENU", mapThirdMenu );

            //如果登陆成功,转载用户信息
            request.getSession().setMaxInactiveInterval( 7200 ); //Sesion有效时长，以秒为单位
            request.getSession().setAttribute( "LOGIN_USER", userInfo );
            request.getSession().setAttribute( "USERCURRENTPOWER", CheckPower.getMoudules( userInfo ) ); //ysj add
            String userType = userInfo.getDeptype();
            //ZSH 取得地区名称
            Region region = super.getService().loadRegion( userInfo.getRegionid() );
            request.getSession().setAttribute( "LOGIN_USER_REGION_NAME", region.getRegionName() );

            if( userType.equals( "2" ) ){ //代为单位
                Contractor contractor = super.getService().loadContractor( userInfo.getDeptID() );
                request.getSession().setAttribute( "LOGIN_USER_DEPT_NAME", contractor.getContractorName() );
                request.getSession().setAttribute( "LOGIN_USER_DEPT_ID", contractor.getContractorID() );
            }
            else{ //内部部门
                Depart depart = super.getService().loadDepart( userInfo.getDeptID() );
                request.getSession().setAttribute( "LOGIN_USER_DEPT_NAME", depart.getDeptName() );
            }

            log( request, " 登陆系统 ", "登陆系统 " );
            OnLineUsers online = OnLineUsers.getInstance();
            String loginIp = request.getRemoteAddr();

            if( !online.existUser( userInfo.getUserID() ) ){
                request.getSession().setAttribute( userInfo.getUserID(), online );
                System.out.println( "欢迎新用户: " + userInfo.getUserID() + " 登陆到系统！" );
            }

            Date nowDate = new Date();
            String nowDateStr = DateUtil.DateToTimeString( nowDate );
            String sql =
                "update USERINFO set LASTLOGINTIME = to_date('" +
                nowDateStr + "','yy/mm/dd hh24:mi'), LASTLOGINIP = '" + loginIp +
                "'  where USERID = '" +
                userInfo.getUserID() + "'";
            UpdateUtil updateU = new UpdateUtil();
            updateU.executeUpdate( sql );

            //判断巡检人员是按组还是按人进行管理
            SysDictionary service = new SysDictionary();
            if( service.isManageByArry().equals( "1" ) ){
                request.getSession().setAttribute( "PMType", "group" );
            }
            else{
                request.getSession().setAttribute( "PMType", "notGroup" );
            }

            //判断是否显示光缆信息
            if( service.isShowFIB().equals( "1" ) ){
                request.getSession().setAttribute( "ShowFIB", "show" );
            }
            else{
                request.getSession().setAttribute( "ShowFIB", "noShow" );
            }

            //判断是否发送短信
            if( service.isSendSM().equals( "1" ) ){
                request.getSession().setAttribute( "isSendSm", "send" );
            }
            else{
                request.getSession().setAttribute( "isSendSm", "nosend" );
            }
            return mapping.findForward( "loginformward" );
        }
        catch( Exception e ){
            logger.error( "登陆失败,异常:" + e.getMessage() );
            // System.out.println("登陆失败,异常:" + e.getMessage());
            request.setAttribute( "loginerror", "syserror" );
            return mapping.findForward( "errorformward" );
        }

    }

}
