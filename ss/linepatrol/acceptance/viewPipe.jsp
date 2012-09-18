<%@include file="/common/header.jsp"%>
<html>
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
<head>
</head>
<body onload="changeStyle()">
	<template:titile value="管道信息" />
	<template:formTable namewidth="150" contentwidth="250">
		<template:formTr name="工程名称">
			${pipe.projectName}
		</template:formTr>
		<template:formTr name="工程名称">
			${pipe.issueNumber}
		</template:formTr>
		<template:formTr name="计划验收日期">
			<bean:write name="pipe" property="planAcceptanceTime" format="yyyy/MM/dd" />
		</template:formTr>
		<template:formTr name="管道地点">
			${pipe.pipeAddress}
		</template:formTr>
		<template:formTr name="详细路由">
			${pipe.pipeRoute}
		</template:formTr>
		<template:formTr name="管道属性">
			<apptag:quickLoadList cssClass="input" style="width:100" keyValue="${pipe.pipeProperty}" name="pipeProperty" listName="property_right" type="look"/>
		</template:formTr>
		<template:formTr name="管道长度">
			沟（公里）${pipe.pipeLength0}<br/>
			孔（公里）${pipe.pipeLength1}
		</template:formTr>
		<template:formTr name="移动长度">
			沟（公里）${pipe.moveScale0}<br/>
			孔（公里）${pipe.moveScale1}
		</template:formTr>
		<template:formTr name="竣工图纸">
			${pipe.workingDrawing}
		</template:formTr>
		<template:formTr name="施工单位">
			${pipe.builder}
		</template:formTr>
		<template:formTr name="施工单位联系方式">
			${pipe.builderPhone}
		</template:formTr>
		<template:formTr name="管道中心项目经理">
			${pipe.pcpm}
		</template:formTr>
		<template:formTr name="维护单位">
			${pipe.maintenance}
		</template:formTr>
		<template:formTr name="备注">
			${pipe.remark}
		</template:formTr>
		<template:formSubmit>
			<td>
				<input type="button" value="关闭" onclick="parent.close();"/>
			</td>
		</template:formSubmit>
	</template:formTable>
</body>
</html>