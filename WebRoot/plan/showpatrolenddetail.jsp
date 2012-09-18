<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<head>
		<title>巡检明细信息表</title>
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
    //结果ID
  	function loadPatrolItemDetail(id){
 		var url = "${ctx}/TowerPatrolinfo.do?method=loadPatrolItemDetail&rid="+id;
    	window.location.href=url;
 	}    
 	//巡检点的所有巡检项明细 
  	function stationDetails(id){
 		var url = "${ctx}/TowerPatrolinfo.do?method=stationDetails&rid="+id;
    	window.location.href=url;
 	}
</script>
	</head>
	<body>
		<%
			String plan_name = pageContext.findAttribute("plan_name")
					.toString() + "巡检明细";
		%>
		<template:titile value="<%=plan_name%>" />
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="18">
			<display:column property="rs_name" title="资源名称" sortable="true" />
			<display:column property="resourcetypename" title="资源类型"
				sortable="true" />
			<display:column property="patrolmanname" title="巡检人" sortable="true" />
			<display:column property="arrive_time" title="到达时间" sortable="true"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column property="start_time" title="开始巡检时间" sortable="true"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column property="end_time" title="结束巡检时间" sortable="true"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column media="html" title="巡检时长">
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
										"" + day + "天" + hour + "小时" + min + "分" + s
												+ "秒");
							}
				%>
			</display:column>
			<display:column media="html" title="RFID巡检">
				<%
					DynaBean bean = (DynaBean) pageContext
									.findAttribute("currentRowObject");
							String id = bean.get("id").toString();
				%>
				<a style="text-decoration: underline; color: blue;"
					href="javascript:;"
					onclick="loadPatrolItemDetail('<%=id%>');return false;">查看</a>
			</display:column>
			<display:column media="html" title="巡检表">
				<%
					DynaBean bean = (DynaBean) pageContext
									.findAttribute("currentRowObject");
							String id = bean.get("id").toString();
				%>
				<a style="text-decoration: underline; color: blue;"
					href="javascript:;"
					onclick="stationDetails('<%=id%>');return false;">查看</a>
			</display:column>
		</display:table>
		<div align="center">
			<input type="button" class="button" value="返回"
				onclick="location.replace('<%=session.getAttribute("previousURL")%>')">
		</div>
	</body>
</html>