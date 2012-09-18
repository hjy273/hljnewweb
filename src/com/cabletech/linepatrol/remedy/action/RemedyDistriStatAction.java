package com.cabletech.linepatrol.remedy.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.remedy.beans.RemedyDistriStatBean;
import com.cabletech.linepatrol.remedy.service.RemedyDistriStatManager;

/**
 * 分布统计总表
 * @author cable
 *
 */
public class RemedyDistriStatAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(RemedyDistriStatAction.class.getName());

	private RemedyDistriStatManager bo = new RemedyDistriStatManager();

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	private SimpleDateFormat sdff = new SimpleDateFormat("yyyy/MM");
	/**
	 * 转到查询分布统计总表的页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryDistriReportForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String deptype = user.getDeptype();
		if(deptype.equals("1")){//移动
			List cons = bo.getConsByDeptId(user);
			request.setAttribute("cons",cons);
		}
		return mapping.findForward("queryDistriReportForm");
	}


	/**
	 *查询分布统计总表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException 
	 */
	public ActionForward getDistriReports(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		String contractorid = request.getParameter("contractorid");
		String startmonth = request.getParameter("startmonth");
		String endmonth = request.getParameter("endmonth");
		System.out.println("contractorid:"+contractorid+" startmonth:"+startmonth+" endmonth:"+endmonth);
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String deptype = user.getDeptype();
		if(!deptype.equals("1")){//移动
			contractorid = user.getDeptID();
		}
		List list = bo.getDistriReports(contractorid,startmonth,endmonth);
		List conlist = bo.getDistriReportCon(contractorid, startmonth, endmonth);
		request.getSession().setAttribute("list",list);
		//移动总维护量
		if(list !=null && list.size()>0){
			float sumfee = 0;
			for(int i = 0;i<list.size();i++){
				BasicDynaBean basic = (BasicDynaBean) list.get(i);
				String totalfee = (String) basic.get("totalfee");
				if(totalfee!=null && !"".equals(totalfee)){
					sumfee+=Float.parseFloat(totalfee);
				}
			}
			request.getSession().setAttribute("sumfee",sumfee+"");
		}

		
		if(conlist !=null && conlist.size()>0){
			Map map = new HashMap();//key 用于存放代维id，value存放list<RemedyDistriStatBean>
			for(int i = 0;i<conlist.size();i++){//循环代维
				BasicDynaBean basiccon = (BasicDynaBean) conlist.get(i);
				String contid = (String) basiccon.get("contractorid");
				String contractorname = (String) basiccon.get("contractorname");
				List beans = new ArrayList();
				for(int m = 0;m<list.size();m++){
					BasicDynaBean basic = (BasicDynaBean) list.get(m);
					String conid = (String) basic.get("contractorid");
					if(contid.equals(conid)){
						RemedyDistriStatBean bean = new RemedyDistriStatBean();
						String conname = (String) basic.get("contractorname");
						String town = (String) basic.get("town");
						String tofee = (String) basic.get("totalfee");
						bean.setContractorid(conid);
						bean.setContractorname(conname);
						bean.setTown(town);
						bean.setTotalfee(tofee);
						beans.add(bean);
					}
				}
				if(beans!=null && beans.size()>0){
					map.put(contractorname, beans);
				}
			}
			request.getSession().setAttribute("map",map);
		}

		
		if(startmonth!=null && endmonth!=null){
			Date startDate = sdf.parse(startmonth);
			Date endDate = sdf.parse(endmonth);
			String time = sdff.format(startDate)+"—"+sdff.format(endDate);
			request.getSession().setAttribute("time",time);
		}
		return mapping.findForward("distriReportList");
	}


	/**
	 * 执行导出Excel
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("exprotReport============= ");
		String sumfee = (String) request.getSession().getAttribute("sumfee");
		Map map = (Map) request.getSession().getAttribute("map");
		String time = (String) request.getSession().getAttribute("time");
		List list = (List) request.getSession().getAttribute("list");
		int size = 0;
		if(list!=null){
			size = list.size();
		}
		bo.exportStorage(sumfee,time,size,map,response);
		return null;
	}


}
