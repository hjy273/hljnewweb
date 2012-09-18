<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script type="text/javascript">
	addGoBackMod=function() {
		var url = "<%=request.getContextPath()%>/station_plan.do?method=listNotFinishedPlan";
		self.location.replace(url);
	}
	toWriteForm=function(idValue){
		var url = "<%=request.getContextPath()%>/plan_patrol_result.do?method=writeForm&station_id=" + idValue + "&plan_id=<bean:write name='plan_bean' property='tid' />";
		self.location.replace(url);
	}
	checkValid=function(addForm){
		if(typeof(addForm.not_finished_plan)=="undefined"){
			return true;
		}else{
			addForm.all_finished.checked=false;
			alert("�ƻ��е��м�վû��ȫ����дѲ����Ϣ!");
			return true;
		}
	}
	</script>
	<body>
		<br>
		<template:titile value="��д�м�վѲ��ƻ�" />
		<html:form
			action="/plan_patrol_result.do?method=updatePlanPatrolState"
			styleId="writeForm" method="post" onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="�ƻ�����">
					<bean:write name="plan_bean" property="planName" />
				</template:formTr>
				<template:formTr name="��������">
					<%=(String) request.getAttribute("region_name")%>
				</template:formTr>
				<template:formTr name="��ʼ����">
					<bean:write name="plan_bean" property="beginDate" />
				</template:formTr>
				<template:formTr name="��������">
					<bean:write name="plan_bean" property="endDate" />
				</template:formTr>
				<template:formTr name="�ƻ�����">
					<logic:equal name="plan_bean" property="planType" value="01">
					�����ռƻ�
				</logic:equal>
					<logic:equal name="plan_bean" property="planType" value="02">
					�����ܼƻ�
				</logic:equal>
					<logic:equal name="plan_bean" property="planType" value="03">
					�����¼ƻ�
				</logic:equal>
					<logic:equal name="plan_bean" property="planType" value="04">
					���ְ���ƻ�
				</logic:equal>
				</template:formTr>
				<template:formTr name="������">
					<%=request.getAttribute("validate_user_name")%>
				</template:formTr>
				<template:formTr name="������ע">
					<bean:write name="plan_bean" property="validateRemark" />
				</template:formTr>
				<template:formTr name="����д�м�վ">
					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<logic:iterate id="stationItem" name="writed_station_list">
							<tr>
								<td>
									<a
										href="javascript:toWriteForm('<bean:write name="stationItem" property="repeater_station_id" />');"><bean:write
											name="stationItem" property="station_name" /> </a>
								</td>
							</tr>
						</logic:iterate>
					</table>
				</template:formTr>
				<template:formTr name="δ��д�м�վ">
					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<logic:iterate id="stationItem" name="not_writed_station_list">
							<tr>
								<td>
									<a
										href="javascript:toWriteForm('<bean:write name="stationItem" property="repeater_station_id" />');"><bean:write
											name="stationItem" property="station_name" /> </a>
									<input name="not_finished_plan"
										value="<bean:write name="stationItem" property="repeater_station_id" />"
										type="hidden" />
								</td>
							</tr>
						</logic:iterate>
					</table>
				</template:formTr>
				<template:formTr name="�Ƿ�ȫ�����">
					<input name="plan_id"
						value="<bean:write name="plan_bean" property="tid" />"
						type="hidden" />
					<input name="all_finished" value="00" type="checkbox" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit styleClass="button">�ύ</html:submit>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBackMod()">����</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
