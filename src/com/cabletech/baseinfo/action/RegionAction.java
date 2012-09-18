package com.cabletech.baseinfo.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.web.*;

public class RegionAction extends BaseInfoBaseDispatchAction {

	private static Logger logger = Logger.getLogger(RegionAction.class
			.getName());

	/**
	 * ����һ������
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward addRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		// System.out.println("addRegion");
		RegionBean bean = (RegionBean) form;

		Region data = new Region();
		BeanUtil.objectCopy(bean, data);
		// data.setRegionID(super.getDbService().getSeq("region",6));
		data.setState(null);
		super.getService().createRegion(data);

		// Log
		log(request, " ����������Ϣ ", " �������Ϲ��� ");

		return forwardInfoPage(mapping, request, "0081");
	}

	public ActionForward loadRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {

		Region data = super.getService().loadRegion(request.getParameter("id"));
		RegionBean bean = new RegionBean();
		BeanUtil.objectCopy(data, bean);
		request.setAttribute("regionBean", bean);
		request.getSession().setAttribute("regionBean", bean);
		return mapping.findForward("updateRegion");
	}

	/**
	 * GIS�����뵱ǰ��������
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward loadCurrentRegion4Gis(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {

		// String regionid = super.getLoginUserInfo(request).getRegionid();
		String regionid = request.getParameter("cRegion");

		// System.out.println("cRegion : " + regionid);

		Region data = super.getService().loadRegion(regionid);
		RegionBean bean = new RegionBean();
		BeanUtil.objectCopy(data, bean);
		request.setAttribute("regionBean", bean);
		request.getSession().setAttribute("regionBean", bean);

		return mapping.findForward("getCurrentRegion4Gis");
	}

	public ActionForward queryRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {

		RegionBean bean = (RegionBean) form;

		String sql = "select a.regionid regionid,a.regionname regionname,b.parentregionid parentregionid,a.regiondes regiondes from region a , (select regionid regionid, regionname parentregionid from region)b ";

		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);

		// where a.parentregionid = b.regionid(+)
		sqlBuild.addConstant("a.parentregionid = b.regionid(+)");
		sqlBuild.addConditionAnd("a.regionid = {0}", bean.getRegionID());
		sqlBuild.addConditionAnd("a.regionname like {0}", "%"
				+ bean.getRegionName() + "%");
		sqlBuild.addConditionAnd("a.parentregionid = {0}", bean
				.getParentregionid());
		// sqlBuild.addConditionAnd("a.state = {0}", bean.getState());
		sqlBuild.addConstant(" and a.state is null");
		sqlBuild.addTableRegion("a.regionid", super.getLoginUserInfo(request)
				.getRegionid());
		sql = sqlBuild.toSql();
		// System.out.println( "************************" + sql );
		sql += " order by sortno,regionid ";
		List list = super.getDbService().queryBeans(sql);

		request.getSession().setAttribute("queryresult", list);
		return mapping.findForward("queryregionresult");
	}

	public ActionForward updateRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {

		// bean.v
		Region data = new Region();
		BeanUtil.objectCopy((RegionBean) form, data);

		super.getService().updateRegion(data);

		// Log
		log(request, " ����������Ϣ ", " �������Ϲ��� ");

		return forwardInfoPage(mapping, request, "0082", data.getRegionName());
	}

	/**
	 * ɾ������
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward deleteRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {

		// bean.v
		// Region data =
		// super.getService().loadRegion(request.getParameter("id"));
		// super.getService().removeRegion(data);
		String sql = "update region set state='1' where regionid='"
				+ request.getParameter("id") + "'";
		try {
			UpdateUtil exec = new UpdateUtil();
			exec.executeUpdate(sql);
			log(request, " ɾ��������Ϣ ", " �������Ϲ��� ");
			return forwardInfoPage(mapping, request, "0083");
		} catch (Exception e) {
			logger.warn("ɾ��������Ϣ�쳣��" + e.getMessage());
			return this.forwardErrorPage(mapping, request, "error");
		}
	}

	public ActionForward exportRegionResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			logger.info(" ����dao");
			List list = (List) request.getSession().getAttribute("queryresult");
			logger.info("�õ�list");
			super.getService().exportRegionResult(list, response);
			logger.info("���excel�ɹ�");
			return null;
		} catch (Exception e) {
			logger.error("������Ϣ��������쳣:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

}
