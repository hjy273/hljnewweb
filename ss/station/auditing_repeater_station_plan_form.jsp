<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/validate.js"></script>
	<script type="text/javascript">
	checkValid=function(addForm){
		var flag=judgeEmptyValue(addForm.validateResult,"�������");
		return flag;
	}
	addGoBackMod=function(){
		var url = "<%=request.getContextPath()%>/station_plan.do?method=<%=request.getAttribute("query_method")%>";
		self.location.replace(url);
	}
	</script>
	<body>
		<template:titile value="����м�վ�ƻ�" />
		<html:form action="/station_plan.do?method=auditing"
			styleId="auditingForm" method="post"
			onsubmit="return checkValid(this);">
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
									<bean:write name="stationItem" property="station_name" />
									<!-- ��ͼ��ʾ -->
								</td>
							</tr>
						</logic:iterate>
					</table>
				</template:formTr>
				<template:formTr name="������">
					<input name="query_method" type="hidden"
						value="<%=request.getAttribute("query_method")%>" />
					<input name="objectId" type="hidden"
						value="<bean:write name="plan_bean" property="tid" />" />
					<input name="validateUserid" type="hidden"
						value="<%=((UserInfo) session.getAttribute("LOGIN_USER")).getUserID()%>" />
					<%=((UserInfo) session.getAttribute("LOGIN_USER")).getUserName()%>
				</template:formTr>
				<template:formTr name="�������">
					<html:select property="validateResult" styleClass="inputtext"
						style="width:250;">
						<html:option value="0">���ͨ��</html:option>
						<html:option value="1">��˲�ͨ��</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="��ע">
					<html:textarea property="validateRemark" styleClass="inputtextarea"
						style="width:250;" rows="5" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit styleClass="button">�ύ</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">ȡ��	</html:reset>
					</td>
					<td>
					  <input type="button" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
						
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		<script type="text/javascript">
		</script>
	</body>
</html>
