<%@include file="/common/header.jsp"%>
<html>
<head>
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
</head>
<body onload="changeStyle()">
	<template:titile value="������Ϣ" />
	<template:formTable namewidth="150" contentwidth="250">
		<template:formTr name="���±��">
			${cable.cableNo}
		</template:formTr>
		<template:formTr name="�ʲ����">
			${cable.sid}
		</template:formTr>
		<template:formTr name="��������">
			${cable.issueNumber}
		</template:formTr>
		<template:formTr name="�ƻ���������">
			<bean:write name="cable" property="planAcceptanceTime" format="yyyy/MM/dd" />
		</template:formTr>
		<template:formTr name="A��">
			${cable.a}
		</template:formTr>
		<template:formTr name="Z��">
			${cable.z}
		</template:formTr>
		<template:formTr name="�����м̶�">
			${cable.trunk}
		</template:formTr>
		<template:formTr name="���跽ʽ">
			<apptag:quickLoadList cssClass="input" keyValue="${cable.layingMethod}" name="layingmethod" listName="layingmethod" type="look"/>
		</template:formTr>
		<template:formTr name="��о��">
			${cable.fibercoreNo}
		</template:formTr>
		<template:formTr name="���¼���">
			<apptag:quickLoadList cssClass="input" keyValue="${cable.cableLevel}" style="width:100" name="cableLevel" listName="cabletype" type="look"/>
		</template:formTr>
		<template:formTr name="���³���">
			${cable.cableLength}
		</template:formTr>
		<template:formTr name="ʩ����λ">
			${cable.builder}
		</template:formTr>
		<template:formTr name="ʩ����λ��ϵ��ʽ">
			${cable.builderPhone}
		</template:formTr>
		<template:formTr name="����������Ŀ����">
			${cable.prcpm}
		</template:formTr>
		<template:formTr name="��ע">
			${cable.remark}
		</template:formTr>
		<template:formSubmit>
			<td>
				<input type="button" value="�ر�" onclick="parent.close();"/>
			</td>
		</template:formSubmit>
	</template:formTable>
</body>
</html>