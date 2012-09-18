<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<%@ page import="java.util.List,org.apache.commons.beanutils.DynaBean,com.cabletech.baseinfo.domainobjects.UserInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=GBK">
		<title>北京移动线路传输维护管理系统</title>
		<script type="text/javascript">
		goBackHome=function(){
			var url="${ctx}/wap/login.do?method=index&&env=wap";
			location=url;
		}
		goBackWaitWork=function(){
			var url="${sessionScope.S_BACK_URL}";
			location=url;
		}
		validate=function(){
			var form1=document.forms[0];
			var user=form1.user_info;
			var flag=false;
			if(typeof(user.length)!="undefined"){
				for(i=0;i<user.length;i++){
					if(user[i].checked){
						flag=true;
						break;
					}
				}
			}else{
				if(user.checked){
					flag=true;
				}
			}
			if(!flag){
				alert("没有选择受理人信息！");
				return false;
			}
			return true;
		}
		</script>
	</head>
	<body>
		<template:titile value="请选择受理人" />
		<div>
			<a class="dept1">${LOGIN_USER.userName} 您好！</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">功能导航</a><a class="dept"
				href="javascript:exitSys();">退出</a><br /><br />
		</div>
		<form method="post" action="${ctx}/wap/load_processors.do?method=addWapUsers">
			<input name="action_url" value="${action_url}"  type="hidden" />
			<input name="input_name" value="${input_name }" type="hidden" />
			<input name="input_type" value="${input_type }" type="hidden" />
			<input name="exist_value" value="${exist_value }" type="hidden" />
			<input name="except_self" value="${except_self }" type="hidden" />
			<input name="display_type" value="${display_type }" type="hidden" />
			<p>
			<%
			UserInfo userInfo=(UserInfo)session.getAttribute("LOGIN_USER");
			String exceptSelf=(String)request.getAttribute("except_self");
			if(request.getAttribute("user_list")!=null){
				List userList=(List)request.getAttribute("user_list");
				DynaBean bean;
				for(int i=0;i<userList.size();i++){
					bean=(DynaBean)userList.get(i);
					if(bean!=null){
						if(exceptSelf!=null&&exceptSelf.equals("true")&&userInfo.getUserID().equals(bean.get("userid"))){
							continue;
						}
			 %>
				<input name="user_info" value="D<%=(String)bean.get("deptid") %>U<%=(String)bean.get("userid") %>M<%=(String)bean.get("mobile") %>N<%=(String)bean.get("name") %>" type="${input_type }" /><%=(String)bean.get("name") %>--负责：
				<%if(bean.get("position")!=null){
				%>
				<%=(String)bean.get("position") %>
				<%
				} %>
				<br/>
			<%
					}
				}
			}
		 	%>
			</p>
			<p>
				<input name="btnSubmit" value="下一步" type="submit" onclick="return validate();" />
				<input name="btnReset" value="重选" type="reset" />
				<input name="btnToHome" value="回首页" type="button" onclick="goBackHome();" />
				<logic:notEmpty name="S_BACK_URL">
					<input name="btnToWaitWork" type="button" id="btnToHome" value="返回待办"
						onclick="goBackWaitWork();" />
				</logic:notEmpty>
			</p>
		</form>
	</body>
</html>