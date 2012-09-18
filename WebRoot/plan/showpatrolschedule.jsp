<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<head>
		<title>巡检计划执行情况信息表</title>
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
 	function loadPatrolPlanDetail(id){
 		var url = "${ctx}/TowerPatrolinfo.do?method=showOnePlanInfo&planid="+id;
    	window.location.href=url;
 	}
	function loadPatrolEndDetail(id){
		var url = "${ctx}/TowerPatrolinfo.do?method=loadPatrolEndDetail&planid="+id;
		window.location.href=url;
	}
	function loadLosePatrolDetail(id){
		var url = "${ctx}/TowerPatrolinfo.do?method=loadLosePatrolDetail&planid="+id;
		window.location.href=url;
	}
</script>
	</head>
	<body>
		<template:titile value="${pagetitle}" />
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="10">
			<display:column property="regionname" title="巡检区县" maxLength="18" />
			<display:column title="代维单位" property="contractorname" />
			<display:column property="patrolname" title="巡检组" maxLength="20" />
			<display:column title="计划类型" sortable="true">
				<logic:equal value="1" property="plan_type" name="currentRowObject">
						半年
					</logic:equal>
				<logic:equal value="2" property="plan_type" name="currentRowObject">
						季度
					</logic:equal>
				<logic:equal value="3" property="plan_type" name="currentRowObject">
						每月
					</logic:equal>
				<logic:equal value="4" property="plan_type" name="currentRowObject">
						自定义
					</logic:equal>
			</display:column>
			<display:column media="html" title="计划名称" sortable="true">
				<%
					DynaBean bean = (DynaBean) pageContext
							.findAttribute("currentRowObject");
					String id = bean.get("id").toString();
					String planname = bean.get("plan_name").toString();
				%>
				<a style="text-decoration: underline; color: blue;"
					href="javascript:;"
					onclick="loadPatrolPlanDetail('<%=id%>');return false;"><%=planname%></a>
			</display:column>
			<display:column property="start_time" title="计划开始时间"  format="{0,date,yyyy-MM-dd}" />
			<display:column property="end_time" title="计划结束时间"  format="{0,date,yyyy-MM-dd}"  />
			<display:column property="patrolcount" title="计划巡检数量" maxLength="25" />
			<display:column media="html" title="已巡检数量" sortable="true">
				<logic:notEmpty name="currentRowObject" property="endpatrolcount">
					<logic:equal value="0" name="currentRowObject"
						property="endpatrolcount">
			         0
			       </logic:equal>
					<logic:notEqual value="0" name="currentRowObject"
						property="endpatrolcount">
						<%
							DynaBean bean1 = (DynaBean) pageContext
									.findAttribute("currentRowObject");
							String id1 = bean1.get("id").toString();
							String endpatrolcount = bean1.get("endpatrolcount").toString();
						%>
						<a title="巡检明细" style="text-decoration: underline; color: blue;"
							href="javascript:;" onclick="loadPatrolEndDetail('<%=id1%>')"><%=endpatrolcount%></a>
					</logic:notEqual>
				</logic:notEmpty>
			</display:column>
			<display:column media="html" title="未巡检数量" sortable="true">
				<logic:notEmpty name="currentRowObject" property="nopatrolcount">
					<logic:equal value="0" name="currentRowObject"
						property="nopatrolcount">
			            0
			         </logic:equal>
					<logic:notEqual value="0" name="currentRowObject"
						property="nopatrolcount">
						<%
							DynaBean bean2 = (DynaBean) pageContext
									.findAttribute("currentRowObject");
							String id2 = bean2.get("id").toString();
							String nopatrolcount = bean2.get("nopatrolcount").toString();
						%>
						<a title="未巡检明细" style="text-decoration: underline; color: blue;"
							href="javascript:;" onclick="loadLosePatrolDetail('<%=id2%>')"><%=nopatrolcount%></a>
					</logic:notEqual>
				</logic:notEmpty>
			</display:column>
			<display:column property="planstate" title="巡检完成率" />
		</display:table>
		<table border="0" width="100%">
			<tr>
				<td style="text-align:center;border:0px">
					<input name="btnBack" value="返回" class="button" type="button" onclick="history.go('-1');" />
				</td>
			</tr>
		</table>
	</body>
</html>