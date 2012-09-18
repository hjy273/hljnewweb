package com.cabletech.lnsso.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Depart;
import com.cabletech.baseinfo.domainobjects.Region;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.baseinfo.services.DepartBO;
import com.cabletech.baseinfo.services.InitDisplayBO;
import com.cabletech.baseinfo.services.RegionBO;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.web.ClientException;
import com.cabletech.lnsso.ContractorConversion;
import com.cabletech.lnsso.DeptConversion;
import com.cabletech.lnsso.GetUserGroup;
import com.cabletech.lnsso.LoginService;
import com.cabletech.lnsso.RegionConversion;
import com.cabletech.lnsso.ValidateUGroup;
import com.cabletech.lnsso.ws.GetUserGroupImpl;
import com.cabletech.lnsso.ws.LoginServiceImpl;
import com.cabletech.menu.domainobjects.FirstMenu;
import com.cabletech.menu.domainobjects.SecondlyMenu;
import com.cabletech.menu.services.MenuService;
import com.cabletech.power.CheckPower;
import com.cabletech.sysmanage.domainobjects.Notice;
import com.cabletech.sysmanage.services.NoticeBo;
import com.cabletech.sysmanage.services.SysDictionary;
import com.cabletech.sysmanage.util.OnLineUsers;

public class LoginSSOAction  extends BaseInfoBaseDispatchAction{
	private static Logger logger = Logger.getLogger( "LoginSSOSDAction" );
	String Flag = "nodisplay";
	public ActionForward login( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ) throws
	        ClientException, Exception{
		long start = System.currentTimeMillis();
		String username = request.getParameter("un");//用户名
		String usertype = request.getParameter("ut");//域用户类型 AD ：内部 SD：外部
		//String usergroup = request.getParameter("ug");//用户组
		String loginflag = request.getParameter("loginflag");
		//通过webservice 验证用户是否有该用户组的访问权限。并返回用户信息。
		
		//要求必须提提供以下信息：用户名，姓名，部门id，区域id或区域名称
		LoginService  loginservice = new LoginServiceImpl();
		UserInfo userInfo = loginservice.getUserInfo(username,usertype);
		if(userInfo == null){
			request.setAttribute( "loginerror", "nouser" );
			return mapping.findForward( "errorformward" );
		}
		String city = userInfo.getRegionID();
		if("1".equals(userInfo.getDeptype())){
			userInfo.setDeptID(new DeptConversion().getDept(city));
		}else if("2".equals(userInfo.getDeptype())){
			userInfo.setDeptID(new ContractorConversion().getConversionCon(city));
		}else{
			request.setAttribute( "loginerror", "nodept" );
			return mapping.findForward( "errorformward" );
		}
		
		userInfo.setRegionid(new RegionConversion().getRegionID(city));
		//通过webservice 获取AD、SD用户组列表；并有巡检系统中的用户组对比，如果能够在巡检系统中找到那么能够登录系统，否则不能登录。
		GetUserGroup getUergroup = new GetUserGroupImpl();
		List UGNameList = getUergroup.getUserGroupList(username,usertype);//通过webservice 获得用户组信息
		//验证该用户组在巡检系统中是否存在。
		ValidateUGroup valUGroup = new ValidateUGroup();
		List groups = valUGroup.valUGroup(UGNameList);
		//ADLoginService adloginservice = new ADLoginService();
		//Usergroup group = adloginservice.getUserGroup(usergroup);
		if(groups.size()<1){
			request.setAttribute( "loginerror", "powererror" );
			return mapping.findForward( "errorformward" );
		}else{
			request.getSession().setAttribute("USERGROUP", groups);
		}
		
		 //载入该用户的菜单
        MenuService menuService = new MenuService();
        Vector firstMenu = menuService.getFirstMenu( groups);
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
        logger.info( "开始载入二级菜单" );
        HashMap mapSecondlyMenu = new HashMap();
        Vector secondlyMenu = menuService.getSecondlyMenu( strFirstMenuID, groups );
        mapSecondlyMenu.put( strFirstMenuID, secondlyMenu );
        request.getSession().setAttribute( "MENU_SECONDLYMENU", mapSecondlyMenu );

        logger.info( "开始载入三级菜单" );
        String strThirdMenuID = null;
        if( firstMenu.size() > 0 ){
            SecondlyMenu menu = ( SecondlyMenu )secondlyMenu.elementAt( 0 );
            strThirdMenuID = menu.getId();
        }
        HashMap mapThirdMenu = new HashMap();
        Vector thirdMenu = menuService.getThirdMenu( strThirdMenuID,
        		groups );
        mapThirdMenu.put( strThirdMenuID, thirdMenu );
        request.getSession().setAttribute( "MENU_THIRDMENU", mapThirdMenu );

        //如果登陆成功,转载用户信息
        request.getSession().setMaxInactiveInterval( 1800 ); //Sesion有效时长，以秒为单位 30分钟
        if(userInfo.getRegionID().substring(2).equals("0000")&& !userInfo.getRegionid().startsWith("11"))
               userInfo.setType(userInfo.getDeptype() + "1");
        else
            userInfo.setType(userInfo.getDeptype() + "2");
        logger.info("UserType:" + userInfo.getType());
       
        String deptId=userInfo.getDeptID();
        if("1".equals(userInfo.getDeptype())){
            String deptName=new DepartBO().loadDepart(deptId).getDeptName();
            userInfo.setDeptName(deptName);
        }
        if("2".equals(userInfo.getDeptype())){
            String deptName=new ContractorBO().loadContractor(deptId).getContractorName();
            userInfo.setDeptName(deptName);
        }
        
        String regionId=userInfo.getRegionid();
        String regionName=new RegionBO().loadRegion(regionId).getRegionName();
        HashMap map=new HashMap();
        map.put(regionId,regionName);
        request.getSession().setAttribute("regionInfo",map);

        request.getSession().setAttribute( "LOGIN_USER", userInfo );
        request.getSession().setAttribute( "USERCURRENTPOWER", CheckPower.getMoudules( userInfo.getType(),groups) );
        String userType = userInfo.getDeptype();
        //zhj
//        if( Flag.equals( "display" ) ){
//            String enddate = DateUtil.DateToTimeString( new Date( userInfo.getNewpsdate().getTime()
//                             + timer ) );
//            request.getSession().setAttribute( "display", Flag ); //设置提示密码过期显示
//            request.getSession().setAttribute( "enddate", enddate.substring( 0, 10 ) );
//        }
        //ZSH 取得地区名称
        Region region = super.getService().loadRegion( userInfo.getRegionid() );
        request.getSession().setAttribute( "LOGIN_USER_REGION_NAME", region.getRegionName() );

        if( userType.equals( "2" ) ){ //代为单位
            Contractor contractor = super.getService().loadContractor( userInfo.getDeptID() );
            request.getSession().setAttribute( "LOGIN_USER_DEPT_NAME",
                contractor.getContractorName() );
            request.getSession().setAttribute( "LOGIN_USER_DEPT_ID", contractor.getContractorID() );
        }
        else{ //内部部门
            Depart depart = super.getService().loadDepart( userInfo.getDeptID() );
            request.getSession().setAttribute( "LOGIN_USER_DEPT_NAME", depart.getDeptName() );
        }
        RegionBO rbo = new RegionBO();

        request.getSession().setAttribute( "REGION_ROOT", rbo.getRegionID() );
        log( request, " 登陆系统 ", "登陆系统 " );
        OnLineUsers online = OnLineUsers.getInstance();
        String loginIp = request.getRemoteAddr();

        if( !online.existUser( userInfo.getUserID() ) ){
            request.getSession().setAttribute( userInfo.getUserID(), online );
            logger.info( "欢迎新用户: " + userInfo.getUserID() + " 登陆到系统！" );
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
        //判断是否是第一次登录，登录时间写入数据库
        if( loginflag == null  ){
            SimpleDateFormat dtFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            String strDt = dtFormat.format( nowDate );


            OracleIDImpl ora = new OracleIDImpl();
            String uid = ora.getSeq( "useronlinetime", 16 );
            logger.info( "获得主键id" + uid );
            request.getSession().setAttribute( "userkeyid", uid );
            String sql2 =
                "insert into useronlinetime ul (ul.KEYID, ul.USERID, ul.LOGINIP, ul.LOGINTIME) values ( " +
                "'" + uid + "', " + "'" + userInfo.getUserID() + "','" + loginIp + "'," + "to_date('"
                + strDt + "','YYYY-MM-DD HH24:MI:SS'))";
            updateU.executeUpdate( sql2 );
        }
        
        //加载tabmenu菜单
//        SetupDesktopBO sdbo= new SetupDesktopBO();
//        request.getSession().setAttribute("tabmenu",sdbo.toLoadTabMenu(userInfo));
//        String tabmm = sdbo.getTabMenuModule(userInfo);
        //公告提示信息
        NoticeBo nbo = new NoticeBo();
        Notice notice = nbo.Message(rbo.getRegionID(), userInfo);
        request.getSession().setAttribute("notice", notice);
        //加载初始信息
        InitDisplayBO initbo = new InitDisplayBO();
        //取得公告列表
		String noticestr = initbo.getNoticeInfo(rbo.getRegionID(),userInfo.getRegionID(),"");
		//巡检人员列表
		String onlineman = initbo.getPatrolManStr(userInfo);
		if(noticestr == null)
			noticestr = "";
		request.getSession().setAttribute("noticeli", noticestr);
		request.getSession().setAttribute("onlineman", onlineman);
		//设置桌面
//		request.getSession().setAttribute("favstr",sdbo.getFavStr(userInfo));
//		request.getSession().setAttribute("nonfavstr",sdbo.getNonFavStr(userInfo));
//		if(tabmm.indexOf("002") != -1){
//		//加载计划信息
//		InitDisplayPlanBO idpbo = new InitDisplayPlanBO();
//		request.getSession().setAttribute("FulfillPlan",idpbo.getFulfillPlan(userInfo));
//		request.getSession().setAttribute("ProgressPlan",idpbo.getProgressPlan(userInfo));
//		request.getSession().setAttribute("ShallStartPlan",idpbo.getShallStartPlan(userInfo));
//		}
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
        long end = System.currentTimeMillis();
        long total=(end - start)/1000;
        log.info("运行时间："+total/60+"分"+total%60+"秒");
        return mapping.findForward( "loginformward" );
	}
}
