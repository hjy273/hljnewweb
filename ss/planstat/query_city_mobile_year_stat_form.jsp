<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
function onChangeRegion(){
	var rId=document.forms[0].elements["rId"];
	var regionid=document.getElementById("regionid");
   	regionid.value = rId.options[rId.selectedIndex].text;
}
</script>
<logic:equal value="11" name="LOGIN_USER" property="type">
	<body onload="onChangeRegion()">
</logic:equal>
<logic:notEqual value="11" name="LOGIN_USER" property="type">
	<body>
</logic:notEqual>
<template:titile value="市移动公司年统计查询" />
<html:form method="Post"
	action="/city_mobile_year_stat.do?method=showYearStatInfo">
	<input id="regionid" name="regionname" type="hidden" />
	<template:formTable contentwidth="200" namewidth="300">
		<logic:equal value="11" name="LOGIN_USER" property="type">
			<template:formTr name="区&nbsp;&nbsp;&nbsp;&nbsp;域" isOdd="false">
				<select name="regionID" class="inputtext" style="width: 180px"
					id="rId" onchange="onChangeRegion()">
					<logic:present name="reginfo">
						<logic:iterate id="reginfoId" name="reginfo">
							<option
								value="<bean:write name="reginfoId" property="regionid"/>">
								<bean:write name="reginfoId" property="regionname" />
							</option>
						</logic:iterate>
					</logic:present>
				</select>
			</template:formTr>
		</logic:equal>
		<template:formTr name="年&nbsp;&nbsp;&nbsp;&nbsp;份">
			<apptag:getYearOptions />
			<html:select property="endYear" styleClass="inputtext">
				<html:options collection="yearCollection" property="value"
					labelProperty="label" />
			</html:select>
		</template:formTr>
		<template:formSubmit>
			<td>
				<html:submit styleClass="button">查询</html:submit>
			</td>
			<td>
				<html:reset styleClass="button">取消</html:reset>
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
</body>