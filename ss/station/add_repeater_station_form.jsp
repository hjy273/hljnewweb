<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/prototype.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/validate.js"></script>
	<script type="text/javascript">
	checkValid=function(addForm){
		flag=judgeEmptyValue(addForm.regionId,"所属地州");
		flag=flag&&judgeEmptyValue(addForm.stationName,"中继站名称");
		return flag;
	}
	judgeExistStation=function(addForm){
		if(addForm.stationName.value!=""){
			var value=addForm.stationName.value;
			var regionId=addForm.regionId.value;
			var spanDiv=document.getElementById("existStationDiv");
			var actionUrl="<%=request.getContextPath()%>/station_info.do?method=judgeExist&region_id="+regionId+"&station_name="+value;
			new Ajax.Request(actionUrl+"&rnd="+Math.random(),{
				method:"post",
				onSuccess:function(transport){
					if(transport.responseText=="1"){
						spanDiv.innerHTML="<font color='#FF0000'>中继站已经存在，不能添加！</font>";
					}else{
						spanDiv.innerHTML="<font color='#FF0000'>中继站不存在，可以添加。</font>";
					}
				},
				evalScript:true
			});
		}
	}
	</script>
	<body>
		<template:titile value="增加中继站信息" />
		<html:form action="/station_info.do?method=add" styleId="addForm"
			onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="所属地州">
					<select name="regionId" style="width:250;" class="inputtext"
						onchange="judgeExistStation(document.forms[0]);">
						<logic:present name="region_list" scope="request">
							<logic:iterate id="item" name="region_list" scope="request">
								<option value="<bean:write name="item" property="regionID"/>">
									<bean:write name="item" property="regionName" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
				</template:formTr>
				<template:formTr name="中继站名称">
					<input name="stationName" value="" class="inputtext" type="text"
						style="width:250;" maxlength="25"
						onblur="judgeExistStation(document.forms[0]);" />
					<br />
					<span id="existStationDiv"></span>
				</template:formTr>
				<template:formTr name="是否为地下中继站">
					<select name="isGroundStation" class="inputtext" style="width:250;">
						<option value="0">
							是
						</option>
						<option value="1">
							否
						</option>
					</select>
				</template:formTr>
				<template:formTr name="目前是否有市电">
					<select name="hasElectricity" class="inputtext" style="width:250;">
						<option value="0">
							是
						</option>
						<option value="1">
							否
						</option>
					</select>
				</template:formTr>
				<template:formTr name="能否接市电">
					<select name="connectElectricity" class="inputtext"
						style="width:250;">
						<option value="0">
							是
						</option>
						<option value="1">
							否
						</option>
					</select>
				</template:formTr>
				<template:formTr name="是否有空调">
					<select name="hasAirCondition" class="inputtext" style="width:250;">
						<option value="0">
							是
						</option>
						<option value="1">
							否
						</option>
					</select>
				</template:formTr>
				<template:formTr name="能否接空调">
					<select name="connectAirCondition" class="inputtext"
						style="width:250;">
						<option value="0">
							是
						</option>
						<option value="1">
							否
						</option>
					</select>
				</template:formTr>
				<template:formTr name="是否可接风力发电">
					<select name="connectWindPowerGenerate" class="inputtext"
						style="width:250;">
						<option value="0">
							是
						</option>
						<option value="1">
							否
						</option>
					</select>
				</template:formTr>
				<template:formTr name="备注">
					<html:textarea property="stationRemark" styleClass="inputtextarea"
						style="width:250;" rows="5" />
				</template:formTr>
				<template:formSubmit>
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
		</script>
	</body>
</html>
