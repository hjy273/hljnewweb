package com.cabletech.sysmanage.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.properties.SortOrderEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;
import com.cabletech.commons.tags.displaytag.PagedStatement;
import com.cabletech.commons.tags.displaytag.PaginatedListHelper;
import com.cabletech.commons.web.ClientException;
import com.cabletech.sysmanage.beans.SMSLogBean;

public class SMSLogAction extends SystemBaseDispatchAction{

	/**
	 * 转向发送日志表单页面
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward querySMS_SendLogForm( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{

		String condition=" state is null or state<>'1'";
		QueryUtil query = new QueryUtil();
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		List reginfo = null;
		List coninfo = null;
		List uinfo = null;
		List siminfo=null;

		////////////////////////查询所有区域----省级用户
		String region = "select  r.REGIONNAME, r.REGIONID "
			+ " from  region r   "
			+"where ("+condition+") and substr(r.REGIONID,3,4) != '1111' ";
		////////////////////////代维
		String con = "select  c.CONTRACTORID, c.CONTRACTORNAME, c.REGIONID "
			+ " from  contractorinfo c "
			+ " where ("+condition+")";
		////////////////////////巡检人
		String user = "select p.PATROLID, p.PATROLNAME, p.REGIONID, p.PARENTID "
			+ " from patrolmaninfo p "
			+ " where ("+condition+")";
		String sim="select ownerid,regionid,contractorid,simnumber "
			+"from terminalinfo "
			+"where ("+condition+")";


		//市移动
		if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

			con += "  and c.regionid IN ('" + userinfo.getRegionID() + "') ";
			user += "  and p.regionid IN ('" + userinfo.getRegionID() + "')  order by p.PARENTID ";
			sim+=" and regionid in ('"+userinfo.getRegionID()+"')";
			//System.out.print(con);

			coninfo = query.queryBeans( con );
			uinfo = query.queryBeans( user );
			siminfo=query.queryBeans(sim);

		}
		//市代维
		if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

			user += "  and p.PARENTID in  ('" + userinfo.getDeptID() + "') order by p.PARENTID ";
			sim+=" and contractorid='"+userinfo.getDeptID()+"'";

			uinfo = query.queryBeans( user );
			siminfo=query.queryBeans(sim);

		}
		//省移动
		if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
			user += " order by p.PARENTID ";
			reginfo = query.queryBeans( region );
			coninfo = query.queryBeans( con );
			uinfo = query.queryBeans( user );
			siminfo=query.queryBeans(sim);
		}
		//省代维
		if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

			user += " order by p.PARENTID ";
			con+=" and contractorid in( "
				+"     SELECT contractorid "
				+"     FROM contractorinfo "
				+"     CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"+userinfo.getDeptID()+"')";
			/*
            region+=" and regionid like '%1111' or regionid in("
                +"        select regionid "
                +"        from contractorinfo"
                +"        where (state is null or state<>'1')"
                +"        and contractorid in("
                +"            select contractorid"
                +"            from contractorinfo"
                +"            where (state is null or state<>'1')"
                +"            and parentcontractorid='"+userinfo.getDeptID()+"' or regionid like '%0000'"
                +"        )"
                +"     )";
			 */

			reginfo = query.queryBeans( region );
			coninfo = query.queryBeans( con );
			uinfo = query.queryBeans( user );
			siminfo=query.queryBeans(sim);

		}
		request.setAttribute( "reginfo", reginfo );
		request.setAttribute( "coninfo", coninfo );
		request.setAttribute( "uinfo", uinfo );
		request.setAttribute("siminfo",siminfo);

		return mapping.findForward("smssendlogform");
	}

	/**
	 * 查询短信发送日志
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward querySMS_SendLog( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
		SMSLogBean bean = ( SMSLogBean )form;
		String simid = bean.getSimid();
		//String cons = bean.getContractorid();
		String beginDate = bean.getBegindate();
		String endDate = bean.getEnddate();
		String sql = "select to_char(t.data_time, 'yy/mm/dd hh24:mi:ss') data_time,t.mobiles,t.content,decode(t.rpt_code,'DELIVRD','发送成功','发送失败') handlestate,t.rpt_mobile from SM_MT_RPT t where 2>=1";
		/*if(cons!=null && !"".equals(cons)){
			sql+=" and t.mobiles like '%"+simid+"%'" ;
		}*/
		if(simid!=null && !"".equals(simid)){
			sql+=" and t.mobiles like '%"+simid+"%'" ;
		}
		if(beginDate!=null && !"".equals(beginDate)){
			sql+=" and t.data_time>=to_date('"+ beginDate +"','yyyy/MM/dd HH24:mi:ss') " ;
		}
		if(endDate!=null && !"".equals(endDate)){
			sql+=" and t.data_time <= to_date('"+endDate+"','yyyy/MM/dd HH24:mi:ss')";
		}
//		if(rpt_code!=null && !"".equals(rpt_code)){
//			
//		}
		sql +="order by data_time desc";
		PagedStatement p = new PagedStatement();		
		//15是指每页数据记录数量为12
		PaginatedListHelper paginaredList = p.initPaginatedListHelper(sql, 15, request);
		request.setAttribute("queryresultpage", paginaredList);
		request.getSession().setAttribute("queryresult", p.getPageList());
		return mapping.findForward( "querysmssendlog" );
	}

	/**
	 * 发送日志
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward querySMS_SendLog1( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
		SMSLogBean bean = ( SMSLogBean )form;

		String preTypeStr =
			"'1','初始设备','2','定位','3','调度','6','报警','9','普通','其他'";
		String preStateStr = "'1','已发送','未处理'";

		String sql = "select to_char(a.SENDTIME, 'yy/mm/dd hh24:mi:ss') sendtime, b.USERNAME username, decode(a.type," +
		preTypeStr + ") type, a.content content, decode(a.handlestate," +
		preStateStr +
		") handlestate, a.destid simid, c.regionname region from SENDMSGLOG a, USERINFO b, REGION c ,terminalinfo d,patrolmaninfo e";

		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
		sqlBuild.addConstant(
		"a.USERID = b.USERID(+) and b.REGIONID = c.REGIONID and a.destid=d.simnumber(+) and d.ownerid=e.patrolid" );

		//sqlBuild.addConditionAnd("a.sendtime >= {0}", DateUtil.StringToDate(bean.getBegindate()));
		//sqlBuild.addConditionAnd("a.sendtime <= {0}", DateUtil.StringToDate(bean.getEnddate()));

		sqlBuild.addDateFormatStrEnd( "a.sendtime", bean.getBegindate(), ">=" );
		sqlBuild.addDateFormatStrEnd( "a.sendtime", bean.getEnddate(), "<=" );

		sqlBuild.addConditionAnd( "b.regionid = {0}", bean.getRegionid() );
		sqlBuild.addConditionAnd("e.patrolid= {0}",bean.getPatrolmanid());
		sqlBuild.addConditionAnd("a.destid={0}",bean.getSimid());
		sqlBuild.addTableRegion( "b.regionid",
				super.getLoginUserInfo( request ).getRegionid() );

		if(bean.getContractorid()==null||bean.getContractorid().equals("")){
			if(super.getLoginUserInfo(request).getType().equals("11")){
			}
			if(super.getLoginUserInfo(request).getType().equals("12")){
				sqlBuild.addConstant(" and e.parentid in("
						+" select contractorid "
						+" from contractorinfo "
						+" where (state is null or state<>'1') and regionid='"
						+super.getLoginUserInfo(request).getRegionID()+"')");
			}
			if(super.getLoginUserInfo(request).getType().equals("21")){
				sqlBuild.addConstant(" and e.parentid in ("
						+"                      select contractorid "
						+"                      from contractorinfo"
						+"                      where (state is null or state<>'1') and contractorid in("
						+"                            select contractorid "
						+"                            from contractorinfo "
						+"                            where parentcontractorid='"+super.getLoginUserInfo(request).getDeptID()+"' and (state is null or state<>'1')"
						+"                            )"
						+"                      )"
				);
			}
			if(super.getLoginUserInfo(request).getType().equals("22")){
				sqlBuild.addConstant(" and e.parentid='"+super.getLoginUserInfo(request).getDeptID()+"'");
			}
		}
		else{
			if(bean.getContractorid().equals("-1")) bean.setContractorid("");
			//if(bean.getPatrolmanid().equals("-1")) bean.setPatrolmanid("-1");
			sqlBuild.addConditionAnd( "e.parentid={0}", bean.getContractorid() );
		}
		sqlBuild.addConstant( " order by a.sendtime desc" );

		sql = sqlBuild.toSql();

		//System.out.println( "********************************\n" + sql );
		//        List list = super.getDbService().queryBeans( sql );
		//request.setAttribute("querypoint", list);
		//        request.getSession().setAttribute( "queryresult", list );

		// 2009-5-19 by guixy 后台分页修改开始		
		PagedStatement p = new PagedStatement();		
		PaginatedListHelper paginaredList = p.initPaginatedListHelper(sql, 18, request);

		request.setAttribute("queryresultpage", paginaredList);
		request.getSession().setAttribute("queryresult", p.getPageList());
		// 2009-5-19 by guixy 后台分页修改结束

		return mapping.findForward( "querysmssendlog" );

	}

	/**
	 * 转向接受日志表单页面
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward querySMS_ReciveLogForm( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
		String condition=" state is null or state<>'1'";
		QueryUtil query = new QueryUtil();
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		List reginfo = null;
		List coninfo = null;
		List uinfo = null;
		List siminfo=null;

		////////////////////////查询所有区域----省级用户
		String region = "select  r.REGIONNAME, r.REGIONID "
			+ " from  region r   where ("+condition+") and substr(r.REGIONID,3,4) != '1111' ";
		////////////////////////代维
		String con = "select  c.CONTRACTORID, c.CONTRACTORNAME, c.REGIONID "
			+ " from  contractorinfo c "
			+ " where ("+condition+")";
		////////////////////////巡检人
		String user = "select p.PATROLID, p.PATROLNAME, p.REGIONID, p.PARENTID "
			+ " from patrolmaninfo p "
			+ " where ("+condition+")";
		String sim="select ownerid,regionid,contractorid,simnumber from terminalinfo t where ("+condition+")";

		if (userinfo.getDeptype().equals("1")
                && userinfo.getRegionID().substring(2, 6).equals("0000")) {
            region += "  and exists(select regionid from region re ";
            region += " where r.regionid=re.regionid ";
            region += " start with re.regionid='" + userinfo.getRegionid() + "' ";
            region += " connect by prior re.regionid=re.parentregionid) ";

            con += "  and exists(select regionid from region re ";
            con += " where c.regionid=re.regionid ";
            con += " start with re.regionid='" + userinfo.getRegionid() + "' ";
            con += " connect by prior re.regionid=re.parentregionid) ";

            user += "  and exists(select regionid from region re ";
            user += " where p.regionid=re.regionid ";
            user += " start with re.regionid='" + userinfo.getRegionid() + "' ";
            user += " connect by prior re.regionid=re.parentregionid) order by p.PARENTID ";

            sim += " and exists(select regionid from region re ";
            sim += " where t.regionid=re.regionid ";
            sim += " start with re.regionid='" + userinfo.getRegionid() + "' ";
            sim += " connect by prior re.regionid=re.parentregionid) ";
        }
		
		//市移动
		if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

			con += "  and c.regionid IN ('" + userinfo.getRegionID() + "') ";
			user += "  and p.regionid IN ('" + userinfo.getRegionID() + "')  order by p.PARENTID ";
			sim+=" and regionid in ('"+userinfo.getRegionID()+"')";

			coninfo = query.queryBeans( con );
			uinfo = query.queryBeans( user );
			siminfo=query.queryBeans(sim);

		}
		//市代维
		if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

			user += "  and p.PARENTID in  ('" + userinfo.getDeptID() + "') order by p.PARENTID ";
			sim+=" and contractorid='"+userinfo.getDeptID()+"'";

			uinfo = query.queryBeans( user );
			siminfo=query.queryBeans(sim);

		}
		//省移动
		if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
			reginfo = query.queryBeans( region );
			coninfo = query.queryBeans( con );
			uinfo = query.queryBeans( user );
			siminfo=query.queryBeans(sim);
		}
		//省代维
		if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

			user += " order by p.PARENTID ";
			con+=" and contractorid in( SELECT contractorid"
				+"                      FROM contractorinfo"
				+"                      CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"+userinfo.getDeptID()+"')";

			/*
            region+=" and regionid like '%1111' or regionid in("
                +"        select regionid "
                +"        from contractorinfo"
                +"        where (state is null or state<>'1')"
                +"        and contractorid in("
                +"            select contractorid"
                +"            from contractorinfo"
                +"            where (state is null or state<>'1')"
                +"            and parentcontractorid='"+userinfo.getDeptID()+"' or regionid like '%0000'"
                +"        )"
                +"     )";
			 */

			reginfo = query.queryBeans( region );
			coninfo = query.queryBeans( con );
			uinfo = query.queryBeans( user );
			siminfo=query.queryBeans(sim);

		}
		request.setAttribute( "reginfo", reginfo );
		request.setAttribute( "coninfo", coninfo );
		request.setAttribute( "uinfo", uinfo );
		request.setAttribute("siminfo",siminfo);
		//System.out.println(con);

		return mapping.findForward("smsreceivelogform");
	}

	/**
	 * 接受日志
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward querySMS_ReciveLog( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
		SMSLogBean bean = ( SMSLogBean )form;

		String preTypeStr = "'1','制定线路','2','巡检','4','盯防','其他'";
		String preStateStr = "'1','消息不合法','2','手机号码未注册','3','创建临时点','4','未发现匹配巡检点','5','写入隐患表','6','写入外力盯防表','7','写入巡检执行表','8','该点今天已被巡检，写入定位表','9','创建盯防','10','非活动时间短信','11','未发现匹配盯防点','12','位置超出地图范围','未处理'";

		//        String sql = "select to_char(a.arrivetime, 'yy/mm/dd hh24:mi:ss') arrivetime, decode(a.type, " +
		//            preTypeStr +
		//            ") type, a.simid sim, substr(a.content,7,43) content, decode(a.handlestate," +
		//            preStateStr + ") handlestate, c.regionname region from RECIEVEMSGLOG a, TERMINALINFO b, REGION c  ";
		String sql = "select to_char(a.arrivetime, 'yy/mm/dd hh24:mi:ss') arrivetime, decode(a.type, " +
		preTypeStr +
		") type, a.simid sim, trim(a.content) content, decode(a.handlestate," +
		preStateStr
		+ ") handlestate, c.regionname region from RECIEVEMSGLOG a, TERMINALINFO b, REGION c ,patrolmaninfo d ";

		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
		sqlBuild.addConstant(
		" a.simid = b.SIMNUMBER(+) and b.REGIONID = c.REGIONID and d.patrolid=b.ownerid" );

		//sqlBuild.addConditionAnd("a.arrivetime >= {0}",DateUtil.StringToDate(bean.getBegindate()));
		//sqlBuild.addConditionAnd("a.arrivetime <= {0}",DateUtil.StringToDate(bean.getEnddate()));

		sqlBuild.addDateFormatStrEnd( "a.arrivetime", bean.getBegindate(), ">=" );
		sqlBuild.addDateFormatStrEnd( "a.arrivetime", bean.getEnddate(), "<=" );

		sqlBuild.addConditionAnd( "b.regionid = {0}", bean.getRegionid() );
		sqlBuild.addConditionAnd( "a.simid = {0}", bean.getSimid() );
		sqlBuild.addConditionAnd("d.patrolid= {0}",bean.getPatrolmanid());
		sqlBuild.addTableRegion( "b.regionid",
				super.getLoginUserInfo( request ).getRegionid() );

		if(bean.getContractorid()==null||bean.getContractorid().equals("")){
			if(super.getLoginUserInfo(request).getType().equals("11")){
			}
			if(super.getLoginUserInfo(request).getType().equals("12")){
				sqlBuild.addConstant(" and d.parentid in("
						+" select contractorid "
						+" from contractorinfo "
						+" where (state is null or state<>'1') and regionid='"
						+super.getLoginUserInfo(request).getRegionID()+"')");
			}
			if(super.getLoginUserInfo(request).getType().equals("21")){
				sqlBuild.addConstant(" and d.parentid in ("
						+"                      select contractorid "
						+"                      from contractorinfo"
						+"                      where (state is null or state<>'1') and contractorid in("
						+"                            select contractorid "
						+"                            from contractorinfo "
						+"                            where parentcontractorid='"+super.getLoginUserInfo(request).getDeptID()+"' and (state is null or state<>'1')"
						+"                            )"
						+"                      )"
				);
			}
			if(super.getLoginUserInfo(request).getType().equals("22")){
				sqlBuild.addConstant(" and d.parentid='"+super.getLoginUserInfo(request).getDeptID()+"'");
			}
		}
		else{
			if(bean.getContractorid().equals("-1")) bean.setContractorid("");
			//if(bean.getPatrolmanid().equals("-1")) bean.setPatrolmanid("-1");
			sqlBuild.addConditionAnd( "d.parentid={0}", bean.getContractorid() );
		}
		sqlBuild.addConstant( " order by a.arrivetime desc" );

		sql = sqlBuild.toSql();

		// 2009-5-19 by guixy 后台分页修改开始		
		PagedStatement p = new PagedStatement();		
		PaginatedListHelper paginaredList = p.initPaginatedListHelper(sql, 18, request);

		request.setAttribute("queryresultpage", paginaredList);
		request.getSession().setAttribute("queryresult", p.getPageList());
		// 2009-5-19 by guixy 后台分页修改结束

		return mapping.findForward( "querysmsrecivelog" );
	}

}
