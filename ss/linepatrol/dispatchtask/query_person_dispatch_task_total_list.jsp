<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>��ѯͳ��</title>
		<script type="" language="javascript">
			 //��ʾһ���ɵ���ϸ��Ϣ
 	toGetForm=function (dispatchId){
 		var url = "${ctx}/dispatchtask/dispatch_task.do?method=viewDispatchTask&dispatch_id="+dispatchId+"&rnd="+Math.random();
    	location=url;
 	}
		</script>
	</head>
	<body>
		<!--���˹�����Ϣһ����-->
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<br>
		<template:titile value="���˹�����Ϣһ����" />
		<font face="����" size="2" color="red"> <bean:write
				name="username" /> <logic:notEmpty name="querycon">
				<logic:notEmpty name="querycon" property="sendusername">
					<bean:write name="querycon" property="sendusername" />
				</logic:notEmpty>

				<logic:notEmpty name="querycon" property="begintime">
					<bean:write name="querycon" property="begintime" />
				</logic:notEmpty>
				<logic:empty name="querycon" property="begintime">
				��ʼ
			</logic:empty>

				<logic:notEmpty name="querycon" property="endtime">
				-- <bean:write name="querycon" property="endtime" />
				</logic:notEmpty>
				<logic:empty name="querycon" property="endtime">
				-- ����
			</logic:empty>
				<logic:notEmpty name="querycon" property="sendtype">����Ϊ
					<apptag:quickLoadList cssClass="" name="" listName=""
						type="dispatch" keyValue="${querycon.sendtype}"></apptag:quickLoadList>
				</logic:notEmpty>
				<logic:empty name="querycon" property="sendtype">����Ϊȫ��</logic:empty>
			</logic:notEmpty> <logic:equal value="0" name="queryflg">���ɳ�������</logic:equal> <logic:equal
				value="1" name="queryflg">�Ĵ�ǩ�չ�����</logic:equal> <logic:equal
				value="2" name="queryflg">�Ĵ�����������</logic:equal> <logic:equal
				value="3" name="queryflg">�Ĵ���֤������</logic:equal> <logic:equal
				value="11" name="queryflg">��ǩ�չ�����</logic:equal> <logic:equal
				value="12" name="queryflg">�ķ���������</logic:equal> <logic:equal
				value="13" name="queryflg">����֤������</logic:equal> <logic:equal
				value="21" name="queryflg">�ķ�����ʱ������</logic:equal> <bean:write
				name="datacount" />�� </font>
		<logic:notEmpty name="PERSON_TOTAL_LIST">
			<display:table name="sessionScope.PERSON_TOTAL_LIST"
				id="currentRowObject" pagesize="20" style="width:100%">
				<%
					BasicDynaBean object = (BasicDynaBean) pageContext
							.findAttribute("currentRowObject");
					String serialnumber = "";
					String id = "";
					String sendtopic = "";
					String subid = "";
					String overtimenum = "";
					String titleTopic = "";
					if (object != null) {
						serialnumber = (String) object.get("serialnumber");
						id = (String) object.get("id");

						titleTopic = (String) object.get("sendtopic");
						if (titleTopic != null && titleTopic.length() > 30) {
							sendtopic = titleTopic.substring(0, 30) + "...";
						} else {
							sendtopic = titleTopic;
						}

						subid = (String) object.get("subid");
						overtimenum = String.valueOf(object.get("overtimenum"));
					}
				%>
				<display:column media="html" title="��ˮ��" sortable="true"
					style="width:80px">
					<%
						if (serialnumber != null) {
					%>
					<a href="javascript:toGetForm('<%=id%>')"><%=serialnumber%></a>
					<%
						}
					%>
				</display:column>
				<display:column media="html" title="����" sortable="true">
					<a href="javascript:toGetForm('<%=id%>')" title="<%=titleTopic%>"><%=sendtopic%></a>
				</display:column>
				<display:column property="sendtype" title="����" style="width:90px"
					maxLength="6" sortable="true" />
				<display:column property="senddeptname" title="�ɵ���λ"
					style="width:90px" sortable="true" maxLength="6" />
				<display:column property="acceusername" title="������"
					style="width:60px" maxLength="4" sortable="true" />
				<display:column property="processterm" title="��������"
					style="width:90px" maxLength="10" sortable="true" />
				<display:column media="html" title="��ʱ��Сʱ��" style="width:80px"
					sortable="true">
					<%
						if (overtimenum.indexOf('-') != -1) {
					%>
					<font color="red"><%=overtimenum%></font>
					<%
						} else {
					%>
					<%=overtimenum%>
					<%
						}
					%>
				</display:column>
			</display:table>
			<html:link
				action="/dispatchtask/query_dispatch_task.do?method=exportPersonTotalResult">����ΪExcel�ļ�</html:link>
		</logic:notEmpty>
		<p align="center">
			<input type="button" value="����" class="button"
				onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
		</p>
	</body>
</html>
