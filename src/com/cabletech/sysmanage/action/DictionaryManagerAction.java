package com.cabletech.sysmanage.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.tags.module.Dictionary;
import com.cabletech.commons.tags.services.DictionaryService;
import com.cabletech.commons.web.ClientException;

public class DictionaryManagerAction  extends BaseInfoBaseDispatchAction{
	private static final long serialVersionUID = 1L;
	/**
	 * 查询所有的字典数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward queryDictionary( ActionMapping mapping,
		        ActionForm form,
		        HttpServletRequest request,
		        HttpServletResponse response ) throws
		        ClientException, Exception{
			HttpSession session = request.getSession();
			UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
			WebApplicationContext ctx = getWebApplicationContext();
			DictionaryService ds =(DictionaryService)ctx.getBean("dictionaryService");
			List<Dictionary> dicts = ds.queryDictionary(userinfo.getRegionid());
			session.setAttribute("result", dicts);
			return mapping.findForward("dict_list");
	 }
	/**
	 * 加载单个字典
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward loadDict( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ) throws
	        ClientException, Exception{
		String id = request.getParameter("id");
		WebApplicationContext ctx = getWebApplicationContext();
		DictionaryService ds =(DictionaryService)ctx.getBean("dictionaryService");
		Dictionary dict = ds.findById(id);
		request.setAttribute("dict", dict);
		return mapping.findForward("edit_dict");
	}
	/**
	 * 保存数据字典
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward saveDict( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ) throws
	        ClientException, Exception{
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		String lable = request.getParameter("lable");
		String assortmentId = request.getParameter("assortmentId");
		String sort = request.getParameter("sort");
		String parentId = request.getParameter("parentId");
		Dictionary dict = new Dictionary();
		dict.setIdStr(id);
		dict.setCode(code);
		dict.setAssortmentId(assortmentId);
		dict.setLable(lable);
		dict.setParentId(parentId);
		dict.setSortStr(sort);
		dict.setRegionid(userinfo.getRegionid());
		WebApplicationContext ctx = getWebApplicationContext();
		DictionaryService ds =(DictionaryService)ctx.getBean("dictionaryService");
		ds.save(dict);
		if("edit".equals(request.getParameter("operator"))){
			log(request,"修改数据字典（数据字典名称为："+lable+"）","系统管理");
		}else{
			log(request,"添加数据字典（数据字典名称为："+lable+"）","系统管理");
		}
		return super.forwardInfoPage(mapping, request, "saveDict");
	}
	/**
	 * 添加数据字典
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward addDict( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ) throws
	        ClientException, Exception{
		return mapping.findForward("add_Dict");
	}
	
	public ActionForward isUsable( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ) throws
	        ClientException, Exception{
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		String value = request.getParameter("value");
		String assortmentId = request.getParameter("assId"); 
		WebApplicationContext ctx = getWebApplicationContext();
		DictionaryService ds =(DictionaryService)ctx.getBean("dictionaryService");
		boolean result = ds.isUsable(assortmentId,value,userinfo.getRegionid());
		if(result){
			super.outPrint(response, "\""+value+"\"已经存在。");
		}else{
			super.outPrint(response, "");
		}
		
		return null;
	}
	
}
