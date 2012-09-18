<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/validate.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/prototype.js"></script>
	<script type="text/javascript">
	checkValid=function(addForm){
		flag=judgeEmptyValue(addForm.regionId,"所属地州");
		flag=flag&&judgeEmptyValue(addForm.stationName,"中继站名称");
		return flag;
	}
	judgeExistStation=function(addForm){
		var stationName='<bean:write name="station_bean" property="stationName" />';
		var regionId='<bean:write name="station_bean" property="regionId" />';
		var spanDiv=document.getElementById("existStationDiv");
		if(addForm.stationName.value!=""&&(addForm.stationName.value!=stationName||addForm.regionId.value!=regionId)){
			var value=addForm.stationName.value;
			var regionId=addForm.regionId.value;
			var actionUrl="<%=request.getContextPath()%>/station_info.do?method=judgeExist&region_id="+regionId+"&station_name="+value;
			new Ajax.Request(actionUrl+"&rnd="+Math.random(),{
				method:"post",
				onSuccess:function(transport){
					if(transport.responseText=="1"){
						spanDiv.innerHTML="<font color='#FF0000'>中继站已经存在，不能修改！</font>";
					}else{
						spanDiv.innerHTML="<font color='#FF0000'>中继站不存在，可以修改。</font>";
					}
				},
				evalScript:true
			});
		}else{
			spanDiv.innerHTML="";
		}
	}
	addGoBackMod=function(){
		var url = '<%=request.getContextPath()%>/station_info.do?method=list';
		self.location.replace(url);
	}
	</script>
	<body>
		<template:titile value="修改中继站信息" />
		<html:form action="/station_info.do?method=mod" styleId="modForm"
			onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="所属地州">
					<html:select property="regionId" styleClass="inputtext"
						style="width:250;"
						onchange="judgeExistStation(document.forms[0]);">
						<logic:present name="region_list">
							<logic:iterate id="item" name="region_list">
								<option value="<bean:write name="item" property="regionID"/>">
									<bean:write name="item" property="regionName" />
								</option>
							</logic:iterate>
						</logic:present>
					</html:select>
				</template:formTr>
				<template:formTr name="中继站名称">
					<input name="tid" value="" class="inputtext" type="hidden"
						style="width:250;" maxlength="25" />
					<input name="stationName" value="" class="inputtext" type="text"
						style="width:250;" maxlength="25"
						onblur="judgeExistStation(document.forms[0]);" />
					<br />
					<span id="existStationDiv"></span>
				</template:formTr>
				<template:formTr name="是否为地下中继站">
					<html:select property="isGroundStation" styleClass="inputtext"
						style="width:250;">
						<html:option value="0">是</html:option>
						<html:option value="1">否</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="目前是否有市电">
					<html:select property="hasElectricity" styleClass="inputtext"
						style="width:250;">
						<html:option value="0">是</html:option>
						<html:option value="1">否</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="能否接市电">
					<html:select property="connectElectricity" styleClass="inputtext"
						style="width:250;">
						<html:option value="0">是</html:option>
						<html:option value="1">否</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="是否有空调">
					<html:select property="hasAirCondition" styleClass="inputtext"
						style="width:250;">
						<html:option value="0">是</html:option>
						<html:option value="1">否</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="能否接空调">
					<html:select property="connectAirCondition" styleClass="inputtext"
						style="width:250;">
						<html:option value="0">是</html:option>
						<html:option value="1">否</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="是否可接风力发电">
					<html:select property="connectWindPowerGenerate"
						styleClass="inputtext" style="width:250;">
						<html:option value="0">是</html:option>
						<html:option value="1">否</html:option>
					</html:select>
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
					<td>
						<input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		<script type="text/javascript">
		var modForm=document.forms[0];
		modForm.tid.value='<bean:write name="station_bean" property="tid" />';
		modForm.regionId.value='<bean:write name="station_bean" property="regionId" />';
		modForm.stationName.value='<bean:write name="station_bean" property="stationName" />';
		modForm.isGroundStation.value='<bean:write name="station_bean" property="isGroundStation" />';
		modForm.hasElectricity.value='<bean:write name="station_bean" property="hasElectricity" />';
		modForm.connectElectricity.value='<bean:write name="station_bean" property="connectElectricity" />';
		modForm.hasAirCondition.value='<bean:write name="station_bean" property="hasAirCondition" />';
		modForm.connectAirCondition.value='<bean:write name="station_bean" property="connectAirCondition" />';
		modForm.connectWindPowerGenerate.value='<bean:write name="station_bean" property="connectWindPowerGenerate" />';
		modForm.stationRemark.value='<bean:write name="station_bean" property="stationRemark" />';
		</script>
	</body>
</html>
