<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script src="${ctx}/js/validation/prototype.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/effects.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/validation_cn.js" type="text/javascript"></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx}/js/wdatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/js/wdatePicker/skin/WdatePicker.css">
<head>
<script language="javascript" type="">

</script>
</head>
<body>
	<template:titile value="添加基站"/>

	<s:form action="stationAction_save" name="addStationFrom" method="post">
		<template:formTable contentwidth="300" namewidth="220">
			<template:formTr name="基站坐标点">
			<s:select list="%{#request.pointList}" name="station.pointId" cssClass="inputtext" cssStyle="width:220"></s:select>
			</template:formTr>
			<template:formTr name="基站编号">
				<input type="text" name="station.code" class="inputtext required" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formTr name="基站名称">
				<input type="text" name="station.stationname" class="inputtext required" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formTr name="基站类型">
				<s:select list="%{#request.stationTypeList}" name="station.stationtype"   cssClass="inputtext" cssStyle="width:220" />
			</template:formTr>
			<template:formTr name="基站级别">
				<s:select list="%{#request.stationLevelList}" name="station.stationlevel" cssClass="inputtext" cssStyle="width:220" />
			</template:formTr>
			<template:formTr name="地址">
				<input type="text" name="station.pos"  class="inputtext required" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formTr name="开通日期">
				<input name="station.opendate" class="Wdate inputtext" readOnly style="width:220" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
			</template:formTr>

			<template:formTr name="巡检组">
			<s:select list="%{#request.patrolgroups}" name="sc.pgId" cssClass="inputtext" cssStyle="width:220"></s:select>
			</template:formTr>

			<template:formSubmit>
		      	<td>
		      		<input type="submit" class="button" value="添加">
		      	</td>
		      	<td>
		      	  	<input type="button" class="button" onclick="history.back()" value="返回" >
		      	</td>
		    </template:formSubmit>
		</template:formTable>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('addStationFrom', {immediate : true, onFormValidate : formCallback});
		</script>
	</s:form>
</body>
