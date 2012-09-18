<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/prototype.js"></script>
	<script type="text/javascript">
	addGoBackMod=function() {
		var url = "<%=request.getContextPath()%>/plan_patrol_result.do?method=list";
		self.location.replace(url);
	}
	showHistoryDetail=function(value){
		var actionUrl="${ctx}/plan_patrol_result.do?method=view&plan_id=<bean:write name='plan_bean' property='tid' />&station_id="+value+"&rnd="+Math.random();
		var myAjax=new Ajax.Updater(
			"writeInfoTd",actionUrl,{
			method:"post",
			evalScript:true
			}
		);
	}
	</script>
	<body>
		<br>
		<template:titile value="�鿴�м�վ�ƻ�" />
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
			<template:formTr name="�м�վ">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<logic:iterate id="stationItem" name="station_list">
						<tr>
							<td>
								<a
									href="javascript:showHistoryDetail('<bean:write name="stationItem" property="repeater_station_id" />');">
									<bean:write name="stationItem" property="station_name" /> </a>
								<!-- ��ͼ��ʾ -->
							</td>
						</tr>
					</logic:iterate>
				</table>
			</template:formTr>
			<tr class="trcolor">
				<td align="right" class="tdulleft">
					������Ϣ
				</td>
				<td align="left" class="tdulleft">
					&nbsp;
				</td>
			</tr>
			<tr class="trcolor">
				<td align="center" colspan="2" id="applyAuditingTd"
					class="tdulright">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<logic:iterate id="auditingItem" name="auditing_history">
							<tr>
								<td>
									<bean:write name="auditingItem" property="username" />
								</td>
								<td>
									<logic:equal name="auditingItem" property="validate_result"
										value="0">
										���ͨ��
									</logic:equal>
									<logic:equal name="auditingItem" property="validate_result"
										value="1">
										��˲�ͨ��
									</logic:equal>
								</td>
								<td>
									<bean:write name="auditingItem" property="validate_time_dis" />
								</td>
								<td>
									<bean:write name="auditingItem" property="validate_remark" />
								</td>
							</tr>
						</logic:iterate>
					</table>
				</td>
			</tr>
			<tr>
				<td id="writeInfoTd" colspan="2"></td>
			</tr>
			<template:formSubmit>
				<td>
					<input type="button" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
				</td>
			</template:formSubmit>
		</template:formTable>
	</body>
</html>
