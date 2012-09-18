<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/prototype.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/validate.js"></script>
	<script type="text/javascript">
	checkValid=function(addForm){
		flag=judgeEmptyValue(addForm.regionId,"��������");
		flag=flag&&judgeEmptyValue(addForm.stationName,"�м�վ����");
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
						spanDiv.innerHTML="<font color='#FF0000'>�м�վ�Ѿ����ڣ�������ӣ�</font>";
					}else{
						spanDiv.innerHTML="<font color='#FF0000'>�м�վ�����ڣ�������ӡ�</font>";
					}
				},
				evalScript:true
			});
		}
	}
	</script>
	<body>
		<template:titile value="�����м�վ��Ϣ" />
		<html:form action="/station_info.do?method=add" styleId="addForm"
			onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="��������">
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
				<template:formTr name="�м�վ����">
					<input name="stationName" value="" class="inputtext" type="text"
						style="width:250;" maxlength="25"
						onblur="judgeExistStation(document.forms[0]);" />
					<br />
					<span id="existStationDiv"></span>
				</template:formTr>
				<template:formTr name="�Ƿ�Ϊ�����м�վ">
					<select name="isGroundStation" class="inputtext" style="width:250;">
						<option value="0">
							��
						</option>
						<option value="1">
							��
						</option>
					</select>
				</template:formTr>
				<template:formTr name="Ŀǰ�Ƿ����е�">
					<select name="hasElectricity" class="inputtext" style="width:250;">
						<option value="0">
							��
						</option>
						<option value="1">
							��
						</option>
					</select>
				</template:formTr>
				<template:formTr name="�ܷ���е�">
					<select name="connectElectricity" class="inputtext"
						style="width:250;">
						<option value="0">
							��
						</option>
						<option value="1">
							��
						</option>
					</select>
				</template:formTr>
				<template:formTr name="�Ƿ��пյ�">
					<select name="hasAirCondition" class="inputtext" style="width:250;">
						<option value="0">
							��
						</option>
						<option value="1">
							��
						</option>
					</select>
				</template:formTr>
				<template:formTr name="�ܷ�ӿյ�">
					<select name="connectAirCondition" class="inputtext"
						style="width:250;">
						<option value="0">
							��
						</option>
						<option value="1">
							��
						</option>
					</select>
				</template:formTr>
				<template:formTr name="�Ƿ�ɽӷ�������">
					<select name="connectWindPowerGenerate" class="inputtext"
						style="width:250;">
						<option value="0">
							��
						</option>
						<option value="1">
							��
						</option>
					</select>
				</template:formTr>
				<template:formTr name="��ע">
					<html:textarea property="stationRemark" styleClass="inputtextarea"
						style="width:250;" rows="5" />
				</template:formTr>
				<template:formSubmit>
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
		</script>
	</body>
</html>
