package com.cabletech.commons.tags;

import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.JspException;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.SpringContext;
import com.cabletech.commons.tags.module.Dictionary;
import com.cabletech.commons.tags.services.DictionaryService;

public class QuickloadListsTag extends AbstractUiTag {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 选择使用的组件类型下拉列表，复选框，单选钮
	 */
	private String type="look";//select，radio，checkbox,look,subset
	/**
	 * 下拉类别内容名称，英文描述。
	 */
	private String listName="";
	private String keyValue="";
	
	private String id;
	private String cssClass;
	private String name;
	private String style;
	private String isQuery = "false";
	private String onchange=null;
	private String onclick=null;
	
	public String getIsQuery() {
		return isQuery;
	}
	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}
	//@Resource(name="dictionaryService")
	private DictionaryService dictService;
	
	
	@Override
	public int doStartTag() throws JspException {
		UserInfo userinfo = ( UserInfo )this.pageContext.getSession().getAttribute( "LOGIN_USER" );
		dictService = (DictionaryService)SpringContext.getApplicationContext().getBean("dictionaryService");
		//获得数据
		List<Dictionary> dicts = null;
		dicts = (List<Dictionary>)this.pageContext.getSession().getAttribute( listName+"_"+type );
		if("subset".equals(type)){
			
			dicts = dictService.getDictBySubset(keyValue,listName,userinfo.getRegionid());
		}else{
			if(dicts == null){
				dicts = dictService.getDictByAssortment(listName,null);
				this.pageContext.getSession().setAttribute(listName+"_"+type, dicts);
			}
		}
		//组织成相应的组件
		String htmlStr = assembly(dicts);
		println(htmlStr.toString());
		return SKIP_BODY;
	}
	private String assembly(List<Dictionary> data){
		StringBuilder assembly = new StringBuilder();
		if(id==null){
			id=name;
		}
		if("checkbox".equals(type)){
			int i = 1;
			
			for(Dictionary dict:data){
				assembly.append("<input type=\"checkbox\" name=\""+name+"\" value=\""+dict.getCode()+"\" id=\""+id+i+"\" class=\""+cssClass+"\"");
				List<String> list = Arrays.asList(keyValue.split(","));
				if(list.contains(dict.getCode())){
					assembly.append(" checked ");
				}
				assembly.append(" />"+dict.getLable());
				i++;
			}
		}else if("radio".equals(type)){
			int i = 1;
			for(Dictionary dict:data){
				assembly.append("<input type=\"radio\" name=\""+name+"\"");
				if(onclick != null){
					assembly.append(" onclick=\""+getOnclick()+"\" ");
				}
				if(onchange != null){
					assembly.append(" onchange=\""+getOnchange()+"\" ");
				}
				assembly.append(" value=\""+dict.getCode()+"\" id=\""+id+i+"\" class=\""+cssClass+"\"");
				if(dict.getCode().equals(keyValue)){
					assembly.append(" checked ");
				}
				assembly.append(" />"+dict.getLable());
				i++;
			}
		}else if("select".equals(type)){//默认是list
			
			assembly.append("<select name=\""+name+"\" id=\""+id+"\" style=\""+style+"\" ");
			if(onclick != null){
				assembly.append(" onclick=\""+getOnclick()+"\" ");
			}
			if(onchange != null){
				assembly.append(" onchange=\""+getOnchange()+"\" ");
			}
			assembly.append(" class=\""+cssClass+"\" />");
			if(isQuery.equals("true")){
				assembly.append("<option value=\"\"> 不限 </option>");
			}else if(isQuery.equals("select")){
				assembly.append("<option value=\"\"> 请选择 </option>");
			}
			for(Dictionary dict:data){
				assembly.append("<option value=\""+dict.getCode()+"\" ");
				if(dict.getCode().equals(keyValue)){
					assembly.append("selected");
				}
				assembly.append(">"+dict.getLable()+"</option>");
			}
			assembly.append("</select>");
		}else if ("subset".equals(type)){
			assembly.append("<select name=\""+name+"\" id=\""+id+"\" style=\""+style+"\" class=\""+cssClass+"\" />");
			if(isQuery.equals("true")){
				assembly.append("<option value=\"\"> 不限 </option>");
			}else if(isQuery.equals("select")){
				assembly.append("<option value=\"\"> 请选择 </option>");
			}
			for(Dictionary dict:data){
				assembly.append("<option value=\""+dict.getCode()+"\" ");
				if(dict.getCode().equals(keyValue)){
					assembly.append("selected");
				}
				assembly.append(">"+dict.getLable()+"</option>");
			}
			assembly.append("</select>");
		}else{
			for(Dictionary dict:data){
				if(keyValue.indexOf(dict.getCode())!=-1){
					assembly.append(dict.getLable()+" ");
				}
//				if(dict.getCode().equals(keyValue)){
//					assembly.append(dict.getLable());
//				}
			}
		}
		return assembly.toString();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public String getOnchange() {
		return onchange;
	}
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	
}
