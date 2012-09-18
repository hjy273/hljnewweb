<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />

<html>
	<head>
		<title>演练查询列表</title>
		<script type="text/javascript">
			toViewDrillPlanModify=function(planModifyId){
            	window.location.href = "${ctx}/drillPlanModifyAction.do?method=viewDrillPlanModify&planModifyId="+planModifyId;
			}
			toReadDrillPlanModify=function(planModifyId,isread){
            	window.location.href = "${ctx}/drillPlanModifyAction.do?method=viewDrillPlanModify&planModifyId="+planModifyId+"&isread="+isread;
			}
			toApproveDrillPlanModifyForm=function(planModifyId,operator){
            	window.location.href = "${ctx}/drillPlanModifyAction.do?method=approveDrillPlanModifyForm&planModifyId="+planModifyId+"&operator="+operator;
			}
		</script>
	</head>
	<body>
		<display:table name="sessionScope.list" id="modify" pagesize="18">
			<display:column property="task_name" title="演练名称" headerClass="subject" maxLength="18" sortable="true"/>
			<display:column property="prev_starttime" title="变更前开始时间" headerClass="subject"  sortable="true"/>
			<display:column property="prev_endtime" title="变更前结束时间" headerClass="subject"  sortable="true"/>
			<display:column property="next_starttime" title="变更后开始时间" headerClass="subject" maxLength="18" sortable="true"/>
			<display:column property="next_endtime" title="变更后结束时间" headerClass="subject"  sortable="true"/>
			<display:column property="username" title="创建人" headerClass="subject"  sortable="true"/>
			<display:column property="modify_date" title="创建时间" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<logic:equal value="approve_change_drill_proj_task" name="modify" property="jbpm_task_name">
					<logic:equal value="true" name="modify" property="isread">
						<a href="javascript:toReadDrillPlanModify('<bean:write name="modify" property="modify_id"/>','isread')" title="查看演练方案变更">查看</a>
					</logic:equal>
					<logic:equal value="false" name="modify" property="isread">
						<a href="javascript:toViewDrillPlanModify('<bean:write name="modify" property="modify_id"/>')" title="查看演练方案变更">查看</a> |
						<a href="javascript:toApproveDrillPlanModifyForm('<bean:write name="modify" property="modify_id"/>','approve')" title="审核演练方案变更">审核</a> | 
						<a href="javascript:toApproveDrillPlanModifyForm('<bean:write name="modify" property="modify_id"/>','transfer')" title="转审演练方案变更">转审</a>
					</logic:equal>
				</logic:equal>
			</display:column>
		</display:table>
	</body>
</html>

