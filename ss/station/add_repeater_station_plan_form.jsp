<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/validate.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/prototype.js"></script>
	<script type="text/javascript">
	var nowDate=new Date(<%=new java.util.Date().getYear() + 1900%>,<%=new java.util.Date().getMonth()%>,<%=new java.util.Date().getDate()%>);
	checkValid=function(addForm){
		flag=judgeEmptyValue(addForm.regionId,"所属地州");
		flag=flag&&judgeEmptyValue(addForm.planName,"计划名称");
		flag=flag&&judgeEmptyValue(addForm.planType,"计划类型");
		flag=flag&&judgeEmptyValue(addForm.beginDate,"开始日期");
		flag=flag&&judgeEmptyValue(addForm.endDate,"结束日期");
		flag=flag&&judgeNoneSelectedValue(addForm.station_in_plan,"中继站");
		var beginDateValue=convertStrToDate(addForm.beginDate.value," ","/");
		var endDateValue=convertStrToDate(addForm.endDate.value," ","/");
		flag=flag&&beforeDate(beginDateValue,endDateValue,"开始日期不能大于结束日期！");
		flag=flag&&beforeDate(nowDate,beginDateValue,"不能创建过期计划！");
		flag=flag&&beforeDate(nowDate,endDateValue,"不能创建过期计划！");
		return flag;
	}
	getSelectDateThis=function(strID) {
		var addForm=document.addForm;
		if(addForm.regionId.value == ""){
			alert("请选择所属地州 ！");
			addForm.regionId.focus();
			return false;
		}
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
			var index=addForm.regionId.options.selectedIndex;
			var planName=addForm.regionId.options[index].text;
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
	submitPlan=function(addForm){
		addForm.submit_auditing.value="A001";
		if(checkValid(addForm)){
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
	</script>
	<body>
		<template:titile value="填写中继站计划" />
		<html:form action="/station_plan.do?method=add" styleId="addForm"
			onsubmit="return submitPlan(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="计划名称">
					<input name="submit_auditing" value="A001" type="hidden" />
					<input name="planName" value="" class="inputtext" type="text"
						style="width:250;" maxlength="25" readonly />
					<br />
					<span id="existPlanDiv"></span>
				</template:formTr>
				<template:formTr name="所属地州">
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
				<template:formTr name="计划类型">
					<select name="planType" style="width:250;" class="inputtext"
						onchange="setPlanName(document.forms[0]);">
						<option value="01">
							波分日计划
						</option>
						<option value="02">
							波分周计划
						</option>
						<option value="03">
							波分月计划
						</option>
						<option value="04">
							波分半年计划
						</option>
					</select>
				</template:formTr>
				<template:formTr name="开始日期">
					<html:text property="beginDate" styleClass="inputtext"
						style="width:225;" readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='▼' ID='btnBeginDate'
						onclick="getSelectDateThis('beginDate')"
						STYLE="font:'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formTr name="结束日期">
					<html:text property="endDate" styleClass="inputtext"
						style="width:225;" readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='▼' ID='btnEndDate'
						onclick="getSelectDateThis('endDate')"
						STYLE="font:'normal small-caps 6pt serif';">
				</template:formTr>
				<tr class="trcolor">
					<td align="right" class="tdulleft">
						选择中继站
					</td>
					<td class="tdulright">
						&nbsp;
					</td>
				</tr>
				<tbody id="stationDiv"></tbody>
				<template:formTr name="备注">
					<html:textarea property="planRemark" styleClass="inputtextarea"
						style="width:250;" rows="5" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<input name="btnSave" class="button" value="保存" type="button"
							onclick="savePlan(document.addForm);" />
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
		var addForm=document.forms[0];
		loadStation(addForm.regionId.value);
		</script>
	</body>
</html>
