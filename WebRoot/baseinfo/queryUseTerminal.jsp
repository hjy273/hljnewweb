<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>



<template:titile value="手持设备使用情况查询" />
<html:form method="Post"
	action="/UseTerminalAction?method=getUseTerminal">
	<template:formTable contentwidth="200" namewidth="300">
		<template:formTr name="区&nbsp;&nbsp;&nbsp;&nbsp;域" >
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
		<template:formTr name="类&nbsp;&nbsp;&nbsp;&nbsp;型" >
			<html:select property="type">
				<html:option value="note">按短信发送数量</html:option>
				<html:option value="km">按巡检里程</html:option>
				<html:option value="day">按在线天数</html:option>
			</html:select>
		</template:formTr>
		<template:formTr name="年&nbsp;&nbsp;&nbsp;&nbsp;份" >
			<apptag:getYearOptions />
			<html:select property="year" styleClass="inputtext"
				style="width:180">
				<html:options collection="yearCollection" property="value"
					labelProperty="label" />
			</html:select>
		</template:formTr>
		<template:formTr name="月&nbsp;&nbsp;&nbsp;&nbsp;份" >
			<html:select property="month" styleClass="inputtext"
				style="width:180">
				<html:option value="01">一月</html:option>
				<html:option value="02">二月</html:option>
				<html:option value="03">三月</html:option>
				<html:option value="04">四月</html:option>
				<html:option value="05">五月</html:option>
				<html:option value="06">六月</html:option>
				<html:option value="07">七月</html:option>
				<html:option value="08">八月</html:option>
				<html:option value="09">九月</html:option>
				<html:option value="10">十月</html:option>
				<html:option value="11">十一月</html:option>
				<html:option value="12">十二月</html:option>
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