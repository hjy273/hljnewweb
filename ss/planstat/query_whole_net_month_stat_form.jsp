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
<template:titile value="ȫ����ͳ�Ʋ�ѯ" />
<html:form method="Post"
	action="/whole_net_stat?method=showMonthStatInfo">
	<input id="regionid" name="regionname" type="hidden" />
	<template:formTable contentwidth="200" namewidth="300">
		<logic:equal value="11" name="LOGIN_USER" property="type">
			<template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="false">
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
		<template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="false">
			<apptag:getYearOptions />
			<html:select property="endYear" styleClass="inputtext"
				style="width:180px">
				<html:options collection="yearCollection" property="value"
					labelProperty="label" />
			</html:select>
		</template:formTr>
		<template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="false">
			<html:select property="endMonth" styleClass="inputtext"
				style="width:180px">
				<html:option value="01">һ��</html:option>
				<html:option value="02">����</html:option>
				<html:option value="03">����</html:option>
				<html:option value="04">����</html:option>
				<html:option value="05">����</html:option>
				<html:option value="06">����</html:option>
				<html:option value="07">����</html:option>
				<html:option value="08">����</html:option>
				<html:option value="09">����</html:option>
				<html:option value="10">ʮ��</html:option>
				<html:option value="11">ʮһ��</html:option>
				<html:option value="12">ʮ����</html:option>
			</html:select>
		</template:formTr>
		<template:formSubmit>
			<td>
				<html:submit styleClass="button">��ѯ</html:submit>
			</td>
			<td>
				<html:reset styleClass="button">ȡ��</html:reset>
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
</body>
