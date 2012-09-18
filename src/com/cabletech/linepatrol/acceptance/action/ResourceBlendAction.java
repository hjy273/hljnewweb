package com.cabletech.linepatrol.acceptance.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.acceptance.service.AcceptanceConstant;
import com.cabletech.linepatrol.acceptance.service.ApplyManager;
import com.cabletech.linepatrol.acceptance.service.ApplyTaskManager;

/**
 * ResourceBleandAction 主要负责处理，验收资源分配错误的情况；
 * 如 A在验收时发现，不属于A代维的管辖范围的管道 分配给了A，可以有线维中心将该管道调配给该管道所在区域的代维公司。
 * @author simon_zhang
 *
 */
public class ResourceBlendAction extends BaseInfoBaseDispatchAction {
	
	private static final long serialVersionUID = 1L;

	public ActionForward searchForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		request.setAttribute("resourceType",AcceptanceConstant.CABLE );
		request.getSession().setAttribute("searchResult", null);
		return mapping.findForward("searchForm");
	}
	/**
	 * 搜索已经开始验收的，且资源分配不争确的资源信息。
	 * 搜索关键字包括：验收申请名称，验收资源信息（管道地点，管道路由，光缆编号，光缆路由）
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward searchResource(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");
		ApplyManager am = (ApplyManager) ctx.getBean("applyManager");
		String resourceType = request.getParameter("resourceType");//资源类型管道、光缆 
		String applyName = request.getParameter("applyName");//申请名称
		String resourceName = request.getParameter("resourceName");//资源地址，名称
		UserInfo user = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		List result = atm.searchResource(resourceType, applyName, resourceName, user);//根据以上条件搜索资源
		if(result.size()==0){
			result = null;
		}
		request.setAttribute("resourceType", resourceType);
		request.setAttribute("applyName", applyName);
		request.setAttribute("resourceName", resourceName);
		request.getSession().setAttribute("dept", am.getdeptOptions4Apply(applyName));//仅是参与本次验收的代维。
		request.getSession().setAttribute("allDept",am.getDeptOptions());
		request.getSession().setAttribute("searchResult", result);
		request.getSession().setAttribute("blendResources", new HashMap<String,String>());
		return mapping.findForward("searchResult");
	}
	
	/**
	 * 实时保存改变，到指定的容器（blendResources）中。
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 */
	public ActionForward saveChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ClientException{
		String applyId = request.getParameter("applyid");
		String rsId = request.getParameter("objectId");
		String oldConId = request.getParameter("oldconid");
		String newConId = request.getParameter("newconid");
		Map<String,String> blendResources= (Map<String,String>)request.getSession().getAttribute("blendResources");
		String change = blendResources.get(rsId);
		if(change != null){//在容器中存在
			if(newConId!=null||!"".equals(newConId)){
				change = applyId+","+oldConId+","+newConId;
				blendResources.put(rsId, change);
			}else{//新的代维不存在，
				blendResources.remove(rsId);
			}
		}else{
			if(newConId!=null||!"".equals(newConId)){//保存改变
				change = applyId+","+oldConId+","+newConId;
				blendResources.put(rsId, change);
			}
		}
		return null;
	}
	/**
	 * 重新分配不正确的验收资源数据。
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 */
	public ActionForward blendResource(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ClientException{
		WebApplicationContext ctx = getWebApplicationContext();
		ApplyTaskManager atm = (ApplyTaskManager) ctx.getBean("applyTaskManager");
		HttpSession session = request.getSession();
		Map<String,String> bleandResources = (Map<String, String>)session.getAttribute("blendResources");//
		UserInfo user = (UserInfo)session.getAttribute("LOGIN_USER");
		String []bleandResourceArray = Map2Array(bleandResources);
		if (bleandResourceArray.length<=0) {
			return forwardErrorPage(mapping, request, "bleandError");
		} else {
			atm.reapportion(bleandResourceArray,user);
		}
		session.setAttribute("blendResources", null);
		request.getSession().setAttribute("searchResult", null);
		log(request, "调整验收资源分配情况", "验收交维");
		return forwardInfoPage(mapping, request, "bleandSuccess");

	}
	/**
	 * 将Map转换为以逗号分割的数组。
	 * @param map
	 * @return
	 */
	private String [] Map2Array(Map<String,String> map){
		String [] array = new String[map.size()];
		int index=0;
		for(Map.Entry<String, String> e : map.entrySet()){
			array[index] =e.getKey()+","+e.getValue();
			index++;
		}
		return array;
	}

}
