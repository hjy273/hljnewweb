<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/validate.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/prototype.js"></script>
	<script type="text/javascript">
	var nowDate=new Date(<%=new java.util.Date().getYear()+1900%>,<%=new java.util.Date().getMonth()%>,<%=new java.util.Date().getDate()%>);
	checkValid=function(addForm){
		flag=judgeEmptyValue(addForm.planName,"�ƻ�����");
		var isSubmited=addForm.submit_auditing.value;
		flag=flag&&judgeEmptyValue(addForm.beginDate,"��ʼ����");
		flag=flag&&judgeEmptyValue(addForm.endDate,"��������");
		var beginDateValue=convertStrToDate(addForm.beginDate.value," ","/");
		var endDateValue=convertStrToDate(addForm.endDate.value," ","/");
		flag=flag&&beforeDate(beginDateValue,endDateValue,"��ʼ���ڲ��ܴ��ڽ������ڣ�");
		flag=flag&&beforeDate(nowDate,beginDateValue,"���ܴ������ڼƻ���");
		flag=flag&&beforeDate(nowDate,endDateValue,"���ܴ������ڼƻ���");
		return flag;
	}
	getSelectDateThis=function(strID) {
		var addForm=document.copyForm;
		document.all.item(strID).value = getPopDate(document.all.item(strID).value);
		if(document.all.item(strID).value !="" && document.all.item(strID).value !=null){
			setPlanName(addForm);
		}
		return true;
	}
	judgeExistPlan=function(addForm){
		if(addForm.planName.value!=""){
			var value=addForm.planName.value;
			var spanDiv=document.getElementById("existPlanDiv");
			var actionUrl="<%=request.getContextPath()%>/station_plan.do?method=judgeExist&plan_name="+value;
			new Ajax.Request(actionUrl+"&rnd="+Math.random(),{
				method:"post",
				onSuccess:function(transport){
					if(transport.responseText=="1"){
						spanDiv.innerHTML="<font color='#FF0000'>�м�վ�ƻ��Ѿ����ڣ�������ӣ�</font>";
					}else{
						spanDiv.innerHTML="<font color='#FF0000'>�м�վ�ƻ������ڣ�������ӡ�</font>";
					}
				},
				evalScript:true
			});
		}
	}
	setPlanName=function(addForm){
		if(addForm.beginDate.value!=""&&addForm.endDate.value!=""){
			var planName="<%=(String) request.getAttribute("region_name")%>";
			var beginDateValue=convertStrToDate(addForm.beginDate.value," ","/");
			var endDateValue=convertStrToDate(addForm.endDate.value," ","/");
			planName=planName+"��"+beginDateValue.getYear()+"��"+(beginDateValue.getMonth()+1)+"��"+beginDateValue.getDate()+"��";
			planName=planName+"��"+endDateValue.getYear()+"��"+(endDateValue.getMonth()+1)+"��"+endDateValue.getDate()+"�յ�";
			if(addForm.planType.value=="01"){
				planName=planName+"�����ռƻ�";
			}
			if(addForm.planType.value=="02"){
				planName=planName+"�����ܼƻ�";
			}
			if(addForm.planType.value=="03"){
				planName=planName+"�����¼ƻ�";
			}
			if(addForm.planType.value=="04"){
				planName=planName+"���ְ���ƻ�";
			}
			addForm.planName.value=planName;
			judgeExistPlan(addForm);
		}
	}
	savePlan=function(addForm){
		addForm.submit_auditing.value="A002";
		if(checkValid(addForm)){
			addForm.submit();
		}
	}
	addGoBackMod=function() {
		var url = "<%=request.getContextPath()%>/station_plan.do?method=list";
		self.location.replace(url);
	}
	</script>
	<body>
		<br>
		<template:titile value="�����м�վ�ƻ�" />
		<html:form action="/station_plan.do?method=copy" styleId="copyForm"
			onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="�ƻ�����">
					<input name="planName" value="" class="inputtext" type="text"
						style="width:250;" maxlength="25" readonly />
					<input name="planType" value="" type="hidden" />
					<br />
					<span id="existPlanDiv"></span>
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
								</td>
							</tr>
						</logic:iterate>
					</table>
				</template:formTr>
				<input name="submit_auditing" value="A001" type="hidden" />
				<input name="planId" value="" type="hidden" />
				<input name="regionId" value="" type="hidden" />
				<template:formTr name="��ʼ����">
					<html:text property="beginDate" styleClass="inputtext"
						style="width:225;" readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='��' ID='btn'
						onclick="getSelectDateThis('beginDate')"
						STYLE="font:'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formTr name="��������">
					<html:text property="endDate" styleClass="inputtext"
						style="width:225;" readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='��' ID='btn'
						onclick="getSelectDateThis('endDate')"
						STYLE="font:'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formSubmit>
					<td>
						<input name="btnSave" class="button" value="����" type="button"
							onclick="savePlan(document.copyForm);" />
					</td>
					<td>
						<html:submit styleClass="button">�ύ</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">ȡ��	</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		<script type="text/javascript">
		var modForm=document.forms[0];
		modForm.planId.value='<bean:write name="plan_bean" property="tid" />';
		modForm.regionId.value='<bean:write name="plan_bean" property="regionId" />';
		modForm.planName.value='<bean:write name="plan_bean" property="planName" />';
		modForm.planType.value='<bean:write name="plan_bean" property="planType" />';
		</script>
	</body>
</html>
