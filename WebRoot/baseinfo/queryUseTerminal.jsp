<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>



<template:titile value="�ֳ��豸ʹ�������ѯ" />
<html:form method="Post"
	action="/UseTerminalAction?method=getUseTerminal">
	<template:formTable contentwidth="200" namewidth="300">
		<template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" >
			<apptag:setSelectOptions valueName="parentRegionCellection"
				tableName="region" columnName1="regionname" region="true"
				columnName2="regionid" order="regionid"
				condition="substr(REGIONID,3,4) != '1111' " />
			<html:select property="regionid" styleClass="inputtext"
				style="width:160">
				<html:options collection="parentRegionCellection" property="value"
					labelProperty="label" />
			</html:select>
		</template:formTr>
		<template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" >
			<html:select property="type">
				<html:option value="note">�����ŷ�������</html:option>
				<html:option value="km">��Ѳ�����</html:option>
				<html:option value="day">����������</html:option>
			</html:select>
		</template:formTr>
		<template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" >
			<apptag:getYearOptions />
			<html:select property="year" styleClass="inputtext"
				style="width:180">
				<html:options collection="yearCollection" property="value"
					labelProperty="label" />
			</html:select>
		</template:formTr>
		<template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" >
			<html:select property="month" styleClass="inputtext"
				style="width:180">
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