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
 * ResourceBleandAction ��Ҫ������������Դ�������������
 * �� A������ʱ���֣�������A��ά�Ĺ�Ͻ��Χ�Ĺܵ� �������A����������ά���Ľ��ùܵ�������ùܵ���������Ĵ�ά��˾��
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
	 * �����Ѿ���ʼ���յģ�����Դ���䲻��ȷ����Դ��Ϣ��
	 * �����ؼ��ְ����������������ƣ�������Դ��Ϣ���ܵ��ص㣬�ܵ�·�ɣ����±�ţ�����·�ɣ�
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
		String resourceType = request.getParameter("resourceType");//��Դ���͹ܵ������� 
		String applyName = request.getParameter("applyName");//��������
		String resourceName = request.getParameter("resourceName");//��Դ��ַ������
		UserInfo user = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		List result = atm.searchResource(resourceType, applyName, resourceName, user);//������������������Դ
		if(result.size()==0){
			result = null;
		}
		request.setAttribute("resourceType", resourceType);
		request.setAttribute("applyName", applyName);
		request.setAttribute("resourceName", resourceName);
		request.getSession().setAttribute("dept", am.getdeptOptions4Apply(applyName));//���ǲ��뱾�����յĴ�ά��
		request.getSession().setAttribute("allDept",am.getDeptOptions());
		request.getSession().setAttribute("searchResult", result);
		request.getSession().setAttribute("blendResources", new HashMap<String,String>());
		return mapping.findForward("searchResult");
	}
	
	/**
	 * ʵʱ����ı䣬��ָ����������blendResources���С�
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
		if(change != null){//�������д���
			if(newConId!=null||!"".equals(newConId)){
				change = applyId+","+oldConId+","+newConId;
				blendResources.put(rsId, change);
			}else{//�µĴ�ά�����ڣ�
				blendResources.remove(rsId);
			}
		}else{
			if(newConId!=null||!"".equals(newConId)){//����ı�
				change = applyId+","+oldConId+","+newConId;
				blendResources.put(rsId, change);
			}
		}
		return null;
	}
	/**
	 * ���·��䲻��ȷ��������Դ���ݡ�
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
		log(request, "����������Դ�������", "���ս�ά");
		return forwardInfoPage(mapping, request, "bleandSuccess");

	}
	/**
	 * ��Mapת��Ϊ�Զ��ŷָ�����顣
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
