<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<%@ page import="java.util.List,org.apache.commons.beanutils.DynaBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=GBK">
		<title>�����ƶ���·����ά������ϵͳ</title>
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
			var user=form1.contractors;
			var flag=false;
			if(typeof(user.length)!="undefined"){
				for(i=0;i<user.length;i++){
					if(user[i].selected){
						flag=true;
						break;
					}
				}
			}else{
				if(user.selected){
					flag=true;
				}
			}
			if(!flag){
				alert("û��ѡ����������Ϣ��");
				return false;
			}
			return true;
		}
		</script>
	</head>
	<body>
		<div>
			<a class="dept1">${LOGIN_USER.userName} ���ã�</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">���ܵ���</a><a class="dept"
				href="javascript:exitSys();">�˳�</a><br /><br />
		</div>
		<template:titile value="��ѡ������λ" />
		<form method="post" action="${ctx}/wap/load_processors.do?method=loadWapUsers">
			<input name="action_url" value="${action_url}"  type="hidden" />
			<input name="input_name" value="${input_name }" type="hidden" />
			<input name="input_type" value="${input_type }" type="hidden" />
			<input name="exist_value" value="${exist_value }" type="hidden" />
			<input name="except_self" value="${except_self }" type="hidden" />
			<input name="display_type" value="${display_type }" type="hidden" />
			<p>
			<select name="contractors" multiple="multiple" style="width:150px;height:150px;">
					<%
			if(request.getAttribute("depart_list")!=null){
				List departList=(List)request.getAttribute("depart_list");
				DynaBean bean;
				for(int i=0;i<departList.size();i++){
					bean=(DynaBean)departList.get(i);
					if(bean!=null){
			 %>
					<option value="<%=(String)bean.get("departid") %>"><%=(String)bean.get("departname") %></option>
					<%
					}
				}
			}
		 	%>
				</select>
			</p>
			<p>
				<input name="btnSubmit" value="��һ��" type="submit" onclick="return validate();" />
				<input name="btnReset" value="��ѡ" type="reset" />
				<input name="btnToHome" value="����ҳ" type="button" onclick="goBackHome();" />
				<logic:notEmpty name="S_BACK_URL">
					<input name="btnToWaitWork" type="button" id="btnToHome" value="���ش���"
						onclick="goBackWaitWork();" />
				</logic:notEmpty>
			</p>
		</form>
	</body>
</html>