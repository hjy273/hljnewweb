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
            //�û�������
            if( !logindao.validateUserExist( userId ) ){
                //����û������ݿ���
                if( !logindao.addUser( userInfo ) ){
                    return mapping.findForward( "errorformward" );
                }
            }
            else{ //�û��Ѿ�����
                userInfo = super.getService().loadUserInfo( userId );
            }
            //����˺�Ϊ��ͣ��
            if( !userInfo.getAccountState().equals( "1" ) ){
                request.setAttribute( "loginerror", "accerror" );
                return mapping.findForward( "errorformward" );
            }
            //������û��Ĳ˵�
            MenuService menuService = new MenuService();
            Vector firstMenu = menuService.getFirstMenu( userInfo ,businessType);
            request.getSession().setAttribute( "MENU_FIRSTMENU", firstMenu );
            //���û��Ȩ�� 2005-05-31
            if( firstMenu == null || firstMenu.size() < 1 ){
                request.setAttribute( "loginerror", "powererror" );
                return mapping.findForward( "errorformward" );
            }

            String strFirstMenuID = null;
            if( firstMenu.size() > 0 ){
                FirstMenu menu = ( FirstMenu )firstMenu.elementAt( 0 );
                strFirstMenuID = menu.getId();
            }
    //        System.out.println( "��ʼ��������˵�" );
            HashMap mapSecondlyMenu = new HashMap();
            Vector secondlyMenu = menuService.getSecondlyMenu( strFirstMenuID, userInfo );
            mapSecondlyMenu.put( strFirstMenuID, secondlyMenu );
            request.getSession().setAttribute( "MENU_SECONDLYMENU", mapSecondlyMenu );

        //    System.out.println( "��ʼ���������˵�" );
            String strThirdMenuID = null;
            if( firstMenu.size() > 0 ){
                SecondlyMenu menu = ( SecondlyMenu )secondlyMenu.elementAt( 0 );
                strThirdMenuID = menu.getId();
            }
            HashMap mapThirdMenu = new HashMap();
            Vector thirdMenu = menuService.getThirdMenu( strThirdMenuID, userInfo );
            mapThirdMenu.put( strThirdMenuID, thirdMenu );
            request.getSession().setAttribute( "MENU_THIRDMENU", mapThirdMenu );

            //�����½�ɹ�,ת���û���Ϣ
            request.getSession().setMaxInactiveInterval( 7200 ); //Sesion��Чʱ��������Ϊ��λ
            request.getSession().setAttribute( "LOGIN_USER", userInfo );
            request.getSession().setAttribute( "USERCURRENTPOWER", CheckPower.getMoudules( userInfo ) ); //ysj add
            String userType = userInfo.getDeptype();
            //ZSH ȡ�õ�������
            Region region = super.getService().loadRegion( userInfo.getRegionid() );
            request.getSession().setAttribute( "LOGIN_USER_REGION_NAME", region.getRegionName() );

            if( userType.equals( "2" ) ){ //��Ϊ��λ
                Contractor contractor = super.getService().loadContractor( userInfo.getDeptID() );
                request.getSession().setAttribute( "LOGIN_USER_DEPT_NAME", contractor.getContractorName() );
                request.getSession().setAttribute( "LOGIN_USER_DEPT_ID", contractor.getContractorID() );
            }
            else{ //�ڲ�����
                Depart depart = super.getService().loadDepart( userInfo.getDeptID() );
                request.getSession().setAttribute( "LOGIN_USER_DEPT_NAME", depart.getDeptName() );
            }

            log( request, " ��½ϵͳ ", "��½ϵͳ " );
            OnLineUsers online = OnLineUsers.getInstance();
            String loginIp = request.getRemoteAddr();

            if( !online.existUser( userInfo.getUserID() ) ){
                request.getSession().setAttribute( userInfo.getUserID(), online );
                System.out.println( "��ӭ���û�: " + userInfo.getUserID() + " ��½��ϵͳ��" );
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

            //�ж�Ѳ����Ա�ǰ��黹�ǰ��˽��й���
            SysDictionary service = new SysDictionary();
            if( service.isManageByArry().equals( "1" ) ){
                request.getSession().setAttribute( "PMType", "group" );
            }
            else{
                request.getSession().setAttribute( "PMType", "notGroup" );
            }

            //�ж��Ƿ���ʾ������Ϣ
            if( service.isShowFIB().equals( "1" ) ){
                request.getSession().setAttribute( "ShowFIB", "show" );
            }
            else{
                request.getSession().setAttribute( "ShowFIB", "noShow" );
            }

            //�ж��Ƿ��Ͷ���
            if( service.isSendSM().equals( "1" ) ){
                request.getSession().setAttribute( "isSendSm", "send" );
            }
            else{
                request.getSession().setAttribute( "isSendSm", "nosend" );
            }
            return mapping.findForward( "loginformward" );
        }
        catch( Exception e ){
            logger.error( "��½ʧ��,�쳣:" + e.getMessage() );
            // System.out.println("��½ʧ��,�쳣:" + e.getMessage());
            request.setAttribute( "loginerror", "syserror" );
            return mapping.findForward( "errorformward" );
        }

    }

}
