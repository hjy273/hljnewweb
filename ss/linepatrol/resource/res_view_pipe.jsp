<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�鿴�ܵ���Ϣ</title>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<template:titile value="�鿴�ܵ���Ϣ"/>
		<template:formTable>
			<template:formTr name="��������" isOdd="false">
				${pipe.workName}
			</template:formTr>
			<template:formTr name="����" isOdd="true">
				<apptag:quickLoadList cssClass="" name="scetion" keyValue="${pipe.scetion}" listName="terminal_address" type="look"/>
			</template:formTr>
			<template:formTr name="�ܵ��ص�" isOdd="false">
				${pipe.pipeAddress}
			</template:formTr>
			<template:formTr name="�ܵ�·��" isOdd="true">
				${pipe.pipeLine}
			</template:formTr>
			<template:formTr name="�ܵ���ģ" isOdd="false">
				�� ${pipe.pipeLengthChannel}
				�� ${pipe.pipeLengthHole}����
			</template:formTr>
			<template:formTr name="�ƶ���ģ" isOdd="true">
				�� ${pipe.mobileScareChannel}
				�� ${pipe.mobileScareHole}����
			</template:formTr>
			<template:formTr name="�ܵ�����" isOdd="false">
				<apptag:quickLoadList cssClass="" name="pipeType" keyValue="${pipe.pipeType}" listName="pipe_type" type="look"/>
				
			</template:formTr>
			<template:formTr name="��Դ����" isOdd="true">
				<apptag:quickLoadList cssClass="" name="routeRes" keyValue="${pipe.routeRes}" listName="property_right" type="look"/>
			</template:formTr>
			<template:formTr name="��ά����" isOdd="false">
				<bean:write name="pipe" property="finishTime" format="yyyy/MM/dd"/>
			</template:formTr>
			<template:formTr name="����ͼֽ����" isOdd="false">
				${pipe.picture}
			</template:formTr>
			<template:formTr name="��ע" isOdd="false">
				${pipe.remark}
			</template:formTr>
		</template:formTable>
		<template:formSubmit >
			<input type="button" name="submit" class="button" onclick='history.go(-1)' value="����"/>
		</template:formSubmit>
		<template:cssTable/>
	</body>
</html>