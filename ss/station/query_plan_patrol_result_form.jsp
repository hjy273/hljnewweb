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
				stationSpan.innerHTML="没有中继站！";
			}, evalScripts:true});
	}
    </script>
	<body>
		<br>
		<template:titile value="中继站计划巡检查询" />
		<html:form action="/plan_patrol_result.do?method=list" method="post"
			styleId="queryForm">
			<template:formTable namewidth="200" contentwidth="200">
				<html:hidden property="reset_query" value="1" />
				<template:formTr name="所属地州">
					<select name="region_id" class="inputtext" style="width:250;"
						onchange="loadStation(this.value);">
						<option value="">
							不限
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
				<template:formTr name="中继站名称">
					<span id="stationSpan"> <select name="station_id"
							style="width:250;" class="inputtext">
							<option value="">
								不限
							</option>
						</select> </span>
				</template:formTr>
				<template:formTr name="计划类型">
					<select name="plan_type" class="inputtext" style="width:250;">
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
					从
					<input name="begin_date_start" class="inputtext" style="width:80;"
						readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='▼' ID='btn'
						onclick="getSelectDateThis('begin_date_start')"
						STYLE="font:'normal small-caps 6pt serif';">
					到
					<input name="begin_date_end" class="inputtext" style="width:80;"
						readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='▼' ID='btn'
						onclick="getSelectDateThis('begin_date_end')"
						STYLE="font:'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formTr name="结束日期">
					从
					<input name="end_date_start" class="inputtext" style="width:80;"
						readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='▼' ID='btn'
						onclick="getSelectDateThis('end_date_start')"
						STYLE="font:'normal small-caps 6pt serif';">
					到
					<input name="end_date_end" class="inputtext" style="width:80;"
						readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='▼' ID='btn'
						onclick="getSelectDateThis('end_date_end')"
						STYLE="font:'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formTr name="巡检日期">
					从
					<input name="patrol_date_start" class="inputtext" style="width:80;"
						readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='▼' ID='btn'
						onclick="getSelectDateThis('patrol_date_start')"
						STYLE="font:'normal small-caps 6pt serif';">
					到
					<input name="patrol_date_end" class="inputtext" style="width:80;"
						readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='▼' ID='btn'
						onclick="getSelectDateThis('patrol_date_end')"
						STYLE="font:'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formTr name="巡检终端号码">
					<input name="sim_id" value="" class="inputtext" type="text"
						style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="计划状态">
					<select name="plan_state" class="inputtext" style="width:250;">
						<option value="">
							不限
						</option>
						<option value="01">
							未提交
						</option>
						<option value="02">
							待审核
						</option>
						<option value="03">
							正在填写
						</option>
						<option value="04">
							审核不通过
						</option>
						<option value="05">
							填写完毕
						</option>
					</select>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">查找</html:submit>
					</td>
					<td>
						<html:reset property="action" styleClass="button">取消</html:reset>
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
