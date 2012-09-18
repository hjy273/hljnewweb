<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />

<html>
	<head>
		<title>������ѯ�б�</title>
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
			<display:column property="task_name" title="��������" headerClass="subject" maxLength="18" sortable="true"/>
			<display:column property="prev_starttime" title="���ǰ��ʼʱ��" headerClass="subject"  sortable="true"/>
			<display:column property="prev_endtime" title="���ǰ����ʱ��" headerClass="subject"  sortable="true"/>
			<display:column property="next_starttime" title="�����ʼʱ��" headerClass="subject" maxLength="18" sortable="true"/>
			<display:column property="next_endtime" title="��������ʱ��" headerClass="subject"  sortable="true"/>
			<display:column property="username" title="������" headerClass="subject"  sortable="true"/>
			<display:column property="modify_date" title="����ʱ��" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
				<logic:equal value="approve_change_drill_proj_task" name="modify" property="jbpm_task_name">
					<logic:equal value="true" name="modify" property="isread">
						<a href="javascript:toReadDrillPlanModify('<bean:write name="modify" property="modify_id"/>','isread')" title="�鿴�����������">�鿴</a>
					</logic:equal>
					<logic:equal value="false" name="modify" property="isread">
						<a href="javascript:toViewDrillPlanModify('<bean:write name="modify" property="modify_id"/>')" title="�鿴�����������">�鿴</a> |
						<a href="javascript:toApproveDrillPlanModifyForm('<bean:write name="modify" property="modify_id"/>','approve')" title="��������������">���</a> | 
						<a href="javascript:toApproveDrillPlanModifyForm('<bean:write name="modify" property="modify_id"/>','transfer')" title="ת�������������">ת��</a>
					</logic:equal>
				</logic:equal>
			</display:column>
		</display:table>
	</body>
</html>

