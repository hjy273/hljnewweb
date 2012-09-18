<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<head>
		<title>Ѳ����ϸ��Ϣ��</title>
		<script type="text/javascript" language="javascript">
	function addGoBack()
        {
            try
			{
                location.href = "${ctx}/TowerPatrolinfo.do?method=showPlanInfo";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
    //���ID
  	function loadPatrolItemDetail(id){
 		var url = "${ctx}/TowerPatrolinfo.do?method=loadPatrolItemDetail&rid="+id;
    	window.location.href=url;
 	}    
 	//Ѳ��������Ѳ������ϸ 
  	function stationDetails(id){
 		var url = "${ctx}/TowerPatrolinfo.do?method=stationDetails&rid="+id;
    	window.location.href=url;
 	}
</script>
	</head>
	<body>
		<%
			String plan_name = pageContext.findAttribute("plan_name")
					.toString() + "Ѳ����ϸ";
		%>
		<template:titile value="<%=plan_name%>" />
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="18">
			<display:column property="rs_name" title="��Դ����" sortable="true" />
			<display:column property="resourcetypename" title="��Դ����"
				sortable="true" />
			<display:column property="patrolmanname" title="Ѳ����" sortable="true" />
			<display:column property="arrive_time" title="����ʱ��" sortable="true"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column property="start_time" title="��ʼѲ��ʱ��" sortable="true"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column property="end_time" title="����Ѳ��ʱ��" sortable="true"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column media="html" title="Ѳ��ʱ��">
				<%
					DynaBean bean1 = (DynaBean) pageContext
									.findAttribute("currentRowObject");
							Object arrive_time_obj = bean1.get("arrive_time")
									.toString();
							Object end_time_obj = bean1.get("end_time");
							String arrive_time = "";
							String end_time = "";
							if (null != end_time_obj && null != end_time_obj) {
								arrive_time = arrive_time_obj.toString();
								end_time = end_time_obj.toString();
								java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								java.util.Date now = df.parse(end_time);
								java.util.Date date = df.parse(arrive_time);
								long l = now.getTime() - date.getTime();
								long day = l / (24 * 60 * 60 * 1000);
								long hour = (l / (60 * 60 * 1000) - day * 24);
								long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
								long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60
										* 60 - min * 60);
								pageContext.getOut().print(
										"" + day + "��" + hour + "Сʱ" + min + "��" + s
												+ "��");
							}
				%>
			</display:column>
			<display:column media="html" title="RFIDѲ��">
				<%
					DynaBean bean = (DynaBean) pageContext
									.findAttribute("currentRowObject");
							String id = bean.get("id").toString();
				%>
				<a style="text-decoration: underline; color: blue;"
					href="javascript:;"
					onclick="loadPatrolItemDetail('<%=id%>');return false;">�鿴</a>
			</display:column>
			<display:column media="html" title="Ѳ���">
				<%
					DynaBean bean = (DynaBean) pageContext
									.findAttribute("currentRowObject");
							String id = bean.get("id").toString();
				%>
				<a style="text-decoration: underline; color: blue;"
					href="javascript:;"
					onclick="stationDetails('<%=id%>');return false;">�鿴</a>
			</display:column>
		</display:table>
		<div align="center">
			<input type="button" class="button" value="����"
				onclick="location.replace('<%=session.getAttribute("previousURL")%>')">
		</div>
	</body>
</html>