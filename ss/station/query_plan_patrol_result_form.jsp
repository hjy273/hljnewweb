<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript" src="./js/prototype.js"></script>
	<script type="text/javascript" src="./js/json2.js"></script>
	<script type="text/javascript">
	getSelectDateThis=function(strID) {
		var queryForm=document.queryForm;
		document.all.item(strID).value = getPopDate(document.all.item(strID).value);
		if(document.all.item(strID).value !="" && document.all.item(strID).value !=null){
			//if(checkBDate()){
				//preGetWeeklyTaskList();
				//setPlanName();
			//}
		}
		return true;
	}
	loadStation=function(regionId){
			var actionUrl="<%=request.getContextPath()%>/station_info.do?method=loadStationSelectByRegion&show_deleted=0&item_id=station_id&region_id="+regionId+"&"+Math.random();
			new Ajax.Request(actionUrl, {method:"post", onSuccess:function (transport) {
				stationSpan.innerHTML=transport.responseText;
			}, onFailure:function () {
				stationSpan.innerHTML="û���м�վ��";
			}, evalScripts:true});
	}
    </script>
	<body>
		<br>
		<template:titile value="�м�վ�ƻ�Ѳ���ѯ" />
		<html:form action="/plan_patrol_result.do?method=list" method="post"
			styleId="queryForm">
			<template:formTable namewidth="200" contentwidth="200">
				<html:hidden property="reset_query" value="1" />
				<template:formTr name="��������">
					<select name="region_id" class="inputtext" style="width:250;"
						onchange="loadStation(this.value);">
						<option value="">
							����
						</option>
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
					<span id="stationSpan"> <select name="station_id"
							style="width:250;" class="inputtext">
							<option value="">
								����
							</option>
						</select> </span>
				</template:formTr>
				<template:formTr name="�ƻ�����">
					<select name="plan_type" class="inputtext" style="width:250;">
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
					��
					<input name="begin_date_start" class="inputtext" style="width:80;"
						readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='��' ID='btn'
						onclick="getSelectDateThis('begin_date_start')"
						STYLE="font:'normal small-caps 6pt serif';">
					��
					<input name="begin_date_end" class="inputtext" style="width:80;"
						readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='��' ID='btn'
						onclick="getSelectDateThis('begin_date_end')"
						STYLE="font:'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formTr name="��������">
					��
					<input name="end_date_start" class="inputtext" style="width:80;"
						readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='��' ID='btn'
						onclick="getSelectDateThis('end_date_start')"
						STYLE="font:'normal small-caps 6pt serif';">
					��
					<input name="end_date_end" class="inputtext" style="width:80;"
						readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='��' ID='btn'
						onclick="getSelectDateThis('end_date_end')"
						STYLE="font:'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formTr name="Ѳ������">
					��
					<input name="patrol_date_start" class="inputtext" style="width:80;"
						readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='��' ID='btn'
						onclick="getSelectDateThis('patrol_date_start')"
						STYLE="font:'normal small-caps 6pt serif';">
					��
					<input name="patrol_date_end" class="inputtext" style="width:80;"
						readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='��' ID='btn'
						onclick="getSelectDateThis('patrol_date_end')"
						STYLE="font:'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formTr name="Ѳ���ն˺���">
					<input name="sim_id" value="" class="inputtext" type="text"
						style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="�ƻ�״̬">
					<select name="plan_state" class="inputtext" style="width:250;">
						<option value="">
							����
						</option>
						<option value="01">
							δ�ύ
						</option>
						<option value="02">
							�����
						</option>
						<option value="03">
							������д
						</option>
						<option value="04">
							��˲�ͨ��
						</option>
						<option value="05">
							��д���
						</option>
					</select>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">����</html:submit>
					</td>
					<td>
						<html:reset property="action" styleClass="button">ȡ��</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		<script type="text/javascript">
		var queryInfoForm=document.forms[0];
		loadStation(queryInfoForm.region_id.value);
		</script>
	</body>
</html>
