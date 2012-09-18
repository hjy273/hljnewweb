<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/validate.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/prototype.js"></script>
	<script type="text/javascript">
	var nowDate=new Date(<%=new java.util.Date().getYear() + 1900%>,<%=new java.util.Date().getMonth()%>,<%=new java.util.Date().getDate()%>);
	checkValid=function(addForm){
		flag=judgeEmptyValue(addForm.regionId,"��������");
		flag=flag&&judgeEmptyValue(addForm.planName,"�ƻ�����");
		flag=flag&&judgeEmptyValue(addForm.planType,"�ƻ�����");
		flag=flag&&judgeEmptyValue(addForm.beginDate,"��ʼ����");
		flag=flag&&judgeEmptyValue(addForm.endDate,"��������");
		flag=flag&&judgeNoneSelectedValue(addForm.station_in_plan,"�м�վ");
		var beginDateValue=convertStrToDate(addForm.beginDate.value," ","/");
		var endDateValue=convertStrToDate(addForm.endDate.value," ","/");
		flag=flag&&beforeDate(beginDateValue,endDateValue,"��ʼ���ڲ��ܴ��ڽ������ڣ�");
		flag=flag&&beforeDate(nowDate,beginDateValue,"���ܴ������ڼƻ���");
		flag=flag&&beforeDate(nowDate,endDateValue,"���ܴ������ڼƻ���");
		return flag;
	}
	getSelectDateThis=function(strID) {
		var modForm=document.modForm;
		if(modForm.regionId.value == ""){
			alert("��ѡ���������� ��");
			modForm.regionId.focus();
			return false;
		}
		document.all.item(strID).value = getPopDate(document.all.item(strID).value);
		if(document.all.item(strID).value !="" && document.all.item(strID).value !=null){
			setPlanName(modForm);
		}
		return true;
	}
	judgeExistPlan=function(addForm){
		var planName='<bean:write name="plan_bean" property="planName" />';
		var spanDiv=document.getElementById("existPlanDiv");
		if(addForm.planName.value!=""&&addForm.planName.value!=planName){
			var value=addForm.planName.value;
			var actionUrl="<%=request.getContextPath()%>/station_plan.do?method=judgeExist&plan_name="+value;
			new Ajax.Request(actionUrl+"&rnd="+Math.random(),{
				method:"post",
				onSuccess:function(transport){
					if(transport.responseText=="1"){
						spanDiv.innerHTML="<font color='#FF0000'>�м�վ�ƻ��Ѿ����ڣ������޸ģ�</font>";
					}else{
						spanDiv.innerHTML="<font color='#FF0000'>�м�վ�ƻ������ڣ������޸ġ�</font>";
					}
				},
				evalScript:true
			});
		}else{
			spanDiv.innerHTML="";
		}
	}
	setPlanName=function(addForm){
		if(addForm.beginDate.value!=""&&addForm.endDate.value!=""){
			var index=addForm.regionId.options.selectedIndex;
			var planName=addForm.regionId.options[index].text;
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
	savePlan=function(modForm){
		modForm.submit_auditing.value="A002";
		if(checkValid(modForm)){
			modForm.submit();
		}
	}
	submitPlan=function(modForm){
		modForm.submit_auditing.value="A001";
		if(checkValid(modForm)){
			return true;
		}else{
			return false;
		}
	}
	loadStation=function(regionId){
		var actionUrl="<%=request.getContextPath()%>/station_info.do?method=loadStationByRegion&region_id="+regionId+"&"+Math.random();
		var myAjax=new Ajax.Updater(
			"stationDiv",actionUrl,{
				method:"post",
				evalScripts:true
			}
		);
		stationDiv.style.display="";
	}
	addGoBackMod=function(){
		var url = "<%=request.getContextPath()%>/station_info.do?method=list";
		self.location.replace(url);
	}
	</script>
	<body>
		<template:titile value="�޸��м�վ�ƻ�" />
		<html:form action="/station_plan.do?method=mod" styleId="modForm"
			onsubmit="return submitPlan(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="�ƻ�����">
					<input name="submit_auditing" value="A001" type="hidden" />
					<input name="tid" value="" class="inputtext" type="hidden"
						style="width:250;" maxlength="25" />
					<input name="planName" value="" class="inputtext" type="text"
						style="width:250;" maxlength="25" readonly />
					<br />
					<span id="existPlanDiv"></span>
				</template:formTr>
				<template:formTr name="��������">
					<select name="regionId" style="width:250;" class="inputtext"
						onchange="loadStation(this.value);setPlanName(document.forms[0]);">
						<logic:present name="region_list" scope="request">
							<logic:iterate id="item" name="region_list" scope="request">
								<option value="<bean:write name="item" property="regionID"/>">
									<bean:write name="item" property="regionName" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
				</template:formTr>
				<template:formTr name="�ƻ�����">
					<select name="planType" style="width:250;" class="inputtext"
						onchange="setPlanName(document.forms[0]);">
						<option value="01">
							�����ռƻ�
						</option>
						<option value="02">
							�����ܼƻ�
						</option>
						<option value="03">
							�����¼ƻ�
						</option>
						<option value="04">
							���ְ���ƻ�
						</option>
					</select>
				</template:formTr>
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
				<tr class="trcolor">
					<td align="right" class="tdulleft">
						ѡ���м�վ
					</td>
					<td class="tdulright">
						&nbsp;
					</td>
				</tr>
				<tr style="display:none">
					<td colspan="2">
						<logic:iterate id="item" name="station_list">
							<input name="station_id" type="hidden"
								value="<bean:write name="item" property="repeater_station_id" />" />
						</logic:iterate>
					</td>
				</tr>
				<tbody id="stationDiv"></tbody>
				<template:formTr name="��ע">
					<html:textarea property="planRemark" styleClass="inputtextarea"
						style="width:250;" rows="5" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<input name="btnSave" class="button" value="����" type="button"
							onclick="savePlan(document.modForm);" />
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
		modForm.tid.value='<bean:write name="plan_bean" property="tid" />';
		modForm.regionId.value='<bean:write name="plan_bean" property="regionId" />';
		modForm.planName.value='<bean:write name="plan_bean" property="planName" />';
		modForm.planType.value='<bean:write name="plan_bean" property="planType" />';
		modForm.beginDate.value='<bean:write name="plan_bean" property="beginDate" />';
		modForm.endDate.value='<bean:write name="plan_bean" property="endDate" />';
		modForm.planRemark.value='<bean:write name="plan_bean" property="planRemark" />';
		loadStation(modForm.regionId.value);
		</script>
	</body>
</html>
