<%@include file="/common/header.jsp"%>
<%@include file="/common/listhander.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
        toAuditingForm=function(idValue){
		 	var url = "${ctx}/TowerPatrolinfo.do?method=auditingForm&query_method=listWaitAuditingPlan&plan_id="+idValue;
		    self.location.replace(url);
		}
        toGetForm=function(idValue){
			var flag = 3;
        	var url = ""+idValue;
        	self.location.replace(url);
		}
		addGoBack=function(){
			var url = "";
			self.location.replace(url);
		}
		exportList=function(){
			var url="${ctx}/TowerPatrol.do?method=exportList";
			self.location.replace(url);
		}
		</script>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
.subject {
	text-align: center;
}
</style>
	</head>
	<body>
		<%
			BasicDynaBean object = null;
		%>
		<div id="main">
			<div class="title">
				待审核巡检计划列表
			</div>
			<div id="main_7"></div>
			<display:table name="sessionScope.PLAN_LIST" id="currentRowObject"
				pagesize="18">
				<display:column title="代维单位" property="contractorname"
					sortable="true">
				</display:column>
				<display:column title="巡检维护组" property="patrolgroupname" />
				<display:column media="html" title="计划名称" sortable="true">
					<a
						href="<%=request.getContextPath()%>/TowerPatrolinfo.do?method=showOnePlanInfo&planid=${currentRowObject.id}">${currentRowObject.planname}
					</a>
				</display:column>
				<display:column title="计划类型" sortable="true">
					<logic:equal value="1" property="plantype" name="currentRowObject">
						半年
					</logic:equal>
					<logic:equal value="2" property="plantype" name="currentRowObject">
						季度
					</logic:equal>
					<logic:equal value="3" property="plantype" name="currentRowObject">
						月度
					</logic:equal>
					<logic:equal value="4" property="plantype" name="currentRowObject">
						自定义
					</logic:equal>
				</display:column>
				<display:column title="开始时间" property="starttime"  format="{0,date,yyyy-MM-dd}"  />
				<display:column title="结束时间" property="endtime" format="{0,date,yyyy-MM-dd}"/>
				<display:column media="html" title="操作">
					<logic:equal value="02" name="currentRowObject"
						property="planstate">
						<a
							href="javascript:toAuditingForm('${currentRowObject.id}');">审核
						</a>
					</logic:equal>
				</display:column>
			</display:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				style="border: 0px">
				<tr>
					<td style="text-align: center;">
						<logic:notEmpty name="PLAN_LIST" scope="session">
						</logic:notEmpty>
					</td>
				</tr>
			</table>
			<div id="main_8"></div>
		</div>
	</body>
</html>
