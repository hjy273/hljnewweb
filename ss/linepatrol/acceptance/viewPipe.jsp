<%@include file="/common/header.jsp"%>
<html>
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
<head>
</head>
<body onload="changeStyle()">
	<template:titile value="�ܵ���Ϣ" />
	<template:formTable namewidth="150" contentwidth="250">
		<template:formTr name="��������">
			${pipe.projectName}
		</template:formTr>
		<template:formTr name="��������">
			${pipe.issueNumber}
		</template:formTr>
		<template:formTr name="�ƻ���������">
			<bean:write name="pipe" property="planAcceptanceTime" format="yyyy/MM/dd" />
		</template:formTr>
		<template:formTr name="�ܵ��ص�">
			${pipe.pipeAddress}
		</template:formTr>
		<template:formTr name="��ϸ·��">
			${pipe.pipeRoute}
		</template:formTr>
		<template:formTr name="�ܵ�����">
			<apptag:quickLoadList cssClass="input" style="width:100" keyValue="${pipe.pipeProperty}" name="pipeProperty" listName="property_right" type="look"/>
		</template:formTr>
		<template:formTr name="�ܵ�����">
			�������${pipe.pipeLength0}<br/>
			�ף����${pipe.pipeLength1}
		</template:formTr>
		<template:formTr name="�ƶ�����">
			�������${pipe.moveScale0}<br/>
			�ף����${pipe.moveScale1}
		</template:formTr>
		<template:formTr name="����ͼֽ">
			${pipe.workingDrawing}
		</template:formTr>
		<template:formTr name="ʩ����λ">
			${pipe.builder}
		</template:formTr>
		<template:formTr name="ʩ����λ��ϵ��ʽ">
			${pipe.builderPhone}
		</template:formTr>
		<template:formTr name="�ܵ�������Ŀ����">
			${pipe.pcpm}
		</template:formTr>
		<template:formTr name="ά����λ">
			${pipe.maintenance}
		</template:formTr>
		<template:formTr name="��ע">
			${pipe.remark}
		</template:formTr>
		<template:formSubmit>
			<td>
				<input type="button" value="�ر�" onclick="parent.close();"/>
			</td>
		</template:formSubmit>
	</template:formTable>
</body>
</html>