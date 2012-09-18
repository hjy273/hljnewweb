<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript" src="./js/prototype.js"></script>
	<script type="text/javascript" src="./js/json2.js"></script>
	<script type="text/javascript">
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
		<template:titile value="高级查询" />
		<form
			action="<%=request.getContextPath()%>/station_info.do?method=list"
			id="queryForm" method="post">
			<template:formTable namewidth="200" contentwidth="200">
				<html:hidden property="reset_query" value="1" />
				<template:formTr name="所属地州">
					<select name="region_id" class="inputtext" style="width:250px"
						onchange="loadStation(this.value);">
						<option value="">
							不限
						</option>
						<logic:present name="region_list">
							<logic:iterate id="item" name="region_list">
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
				<template:formTr name="是否为地下中继站">
					<select name="is_ground_station" class="inputtext"
						style="width:250px" onchange="">
						<option value="">
							不限
						</option>
						<option value="0">
							是
						</option>
						<option value="1">
							否
						</option>
					</select>
				</template:formTr>
				<template:formTr name="目前是否有市电">
					<select name="has_electricity" class="inputtext"
						style="width:250px" onchange="">
						<option value="">
							不限
						</option>
						<option value="0">
							是
						</option>
						<option value="1">
							否
						</option>
					</select>
				</template:formTr>
				<template:formTr name="能否接市电">
					<select name="connect_electricity" class="inputtext"
						style="width:250px" onchange="">
						<option value="">
							不限
						</option>
						<option value="0">
							是
						</option>
						<option value="1">
							否
						</option>
					</select>
				</template:formTr>
				<template:formTr name="是否有空调">
					<select name="has_air_condition" class="inputtext"
						style="width:250px" onchange="">
						<option value="">
							不限
						</option>
						<option value="0">
							是
						</option>
						<option value="1">
							否
						</option>
					</select>
				</template:formTr>
				<template:formTr name="能否接空调">
					<select name="connect_air_condition" class="inputtext"
						style="width:250px" onchange="">
						<option value="">
							不限
						</option>
						<option value="0">
							是
						</option>
						<option value="1">
							否
						</option>
					</select>
				</template:formTr>
				<template:formTr name="是否可接风力发电">
					<select name="connect_wind_power_generate" class="inputtext"
						style="width:250px" onchange="">
						<option value="">
							不限
						</option>
						<option value="0">
							是
						</option>
						<option value="1">
							否
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
		</form>
		<script type="text/javascript">
		var queryInfoForm=document.forms[0];
		loadStation(queryInfoForm.region_id.value);
		</script>
	</body>
</html>
