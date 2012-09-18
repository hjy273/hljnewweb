<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>查看管道信息</title>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<template:titile value="查看管道信息"/>
		<template:formTable>
			<template:formTr name="工程名称" isOdd="false">
				${pipe.workName}
			</template:formTr>
			<template:formTr name="区域" isOdd="true">
				<apptag:quickLoadList cssClass="" name="scetion" keyValue="${pipe.scetion}" listName="terminal_address" type="look"/>
			</template:formTr>
			<template:formTr name="管道地点" isOdd="false">
				${pipe.pipeAddress}
			</template:formTr>
			<template:formTr name="管道路由" isOdd="true">
				${pipe.pipeLine}
			</template:formTr>
			<template:formTr name="管道规模" isOdd="false">
				沟 ${pipe.pipeLengthChannel}
				孔 ${pipe.pipeLengthHole}公里
			</template:formTr>
			<template:formTr name="移动规模" isOdd="true">
				沟 ${pipe.mobileScareChannel}
				孔 ${pipe.mobileScareHole}公里
			</template:formTr>
			<template:formTr name="管道属性" isOdd="false">
				<apptag:quickLoadList cssClass="" name="pipeType" keyValue="${pipe.pipeType}" listName="pipe_type" type="look"/>
				
			</template:formTr>
			<template:formTr name="资源属性" isOdd="true">
				<apptag:quickLoadList cssClass="" name="routeRes" keyValue="${pipe.routeRes}" listName="property_right" type="look"/>
			</template:formTr>
			<template:formTr name="交维日期" isOdd="false">
				<bean:write name="pipe" property="finishTime" format="yyyy/MM/dd"/>
			</template:formTr>
			<template:formTr name="竣工图纸数量" isOdd="false">
				${pipe.picture}
			</template:formTr>
			<template:formTr name="备注" isOdd="false">
				${pipe.remark}
			</template:formTr>
		</template:formTable>
		<template:formSubmit >
			<input type="button" name="submit" class="button" onclick='history.go(-1)' value="返回"/>
		</template:formSubmit>
		<template:cssTable/>
	</body>
</html>