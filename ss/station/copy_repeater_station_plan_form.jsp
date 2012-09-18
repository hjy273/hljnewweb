<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/validate.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/prototype.js"></script>
	<script type="text/javascript">
	var nowDate=new Date(<%=new java.util.Date().getYear()+1900%>,<%=new java.util.Date().getMonth()%>,<%=new java.util.Date().getDate()%>);
	checkValid=function(addForm){
		flag=judgeEmptyValue(addForm.planName,"计划名称");
		var isSubmited=addForm.submit_auditing.value;
		flag=flag&&judgeEmptyValue(addForm.beginDate,"开始日期");
		flag=flag&&judgeEmptyValue(addForm.endDate,"结束日期");
		var beginDateValue=convertStrToDate(addForm.beginDate.value," ","/");
		var endDateValue=convertStrToDate(addForm.endDate.value," ","/");
		flag=flag&&beforeDate(beginDateValue,endDateValue,"开始日期不能大于结束日期！");
		flag=flag&&beforeDate(nowDate,beginDateValue,"不能创建过期计划！");
		flag=flag&&beforeDate(nowDate,endDateValue,"不能创建过期计划！");
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
						spanDiv.innerHTML="<font color='#FF0000'>中继站计划已经存在，不能添加！</font>";
					}else{
						spanDiv.innerHTML="<font color='#FF0000'>中继站计划不存在，可以添加。</font>";
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
			planName=planName+"从"+beginDateValue.getYear()+"年"+(beginDateValue.getMonth()+1)+"月"+beginDateValue.getDate()+"日";
			planName=planName+"到"+endDateValue.getYear()+"年"+(endDateValue.getMonth()+1)+"月"+endDateValue.getDate()+"日的";
			if(addForm.planType.value=="01"){
				planName=planName+"波分日计划";
			}
			if(addForm.planType.value=="02"){
				planName=planName+"波分周计划";
			}
			if(addForm.planType.value=="03"){
				planName=planName+"波分月计划";
			}
			if(addForm.planType.value=="04"){
				planName=planName+"波分半年计划";
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
		<template:titile value="复制中继站计划" />
		<html:form action="/station_plan.do?method=copy" styleId="copyForm"
			onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="计划名称">
					<input name="planName" value="" class="inputtext" type="text"
						style="width:250;" maxlength="25" readonly />
					<input name="planType" value="" type="hidden" />
					<br />
					<span id="existPlanDiv"></span>
				</template:formTr>
				<template:formTr name="所属地州">
					<%=(String) request.getAttribute("region_name")%>
				</template:formTr>
				<template:formTr name="开始日期">
					<bean:write name="plan_bean" property="beginDate" />
				</template:formTr>
				<template:formTr name="结束日期">
					<bean:write name="plan_bean" property="endDate" />
				</template:formTr>
				<template:formTr name="计划类型">
					<logic:equal name="plan_bean" property="planType" value="01">
					波分日计划
				</logic:equal>
					<logic:equal name="plan_bean" property="planType" value="02">
					波分周计划
				</logic:equal>
					<logic:equal name="plan_bean" property="planType" value="03">
					波分月计划
				</logic:equal>
					<logic:equal name="plan_bean" property="planType" value="04">
					波分半年计划
				</logic:equal>
				</template:formTr>
				<template:formTr name="中继站">
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
				<template:formTr name="开始日期">
					<html:text property="beginDate" styleClass="inputtext"
						style="width:225;" readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='▼' ID='btn'
						onclick="getSelectDateThis('beginDate')"
						STYLE="font:'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formTr name="结束日期">
					<html:text property="endDate" styleClass="inputtext"
						style="width:225;" readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='▼' ID='btn'
						onclick="getSelectDateThis('endDate')"
						STYLE="font:'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formSubmit>
					<td>
						<input name="btnSave" class="button" value="保存" type="button"
							onclick="savePlan(document.copyForm);" />
					</td>
					<td>
						<html:submit styleClass="button">提交</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">取消	</html:reset>
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
