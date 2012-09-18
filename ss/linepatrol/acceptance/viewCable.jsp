<%@include file="/common/header.jsp"%>
<html>
<head>
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
</head>
<body onload="changeStyle()">
	<template:titile value="光缆信息" />
	<template:formTable namewidth="150" contentwidth="250">
		<template:formTr name="光缆编号">
			${cable.cableNo}
		</template:formTr>
		<template:formTr name="资产编号">
			${cable.sid}
		</template:formTr>
		<template:formTr name="工程名称">
			${cable.issueNumber}
		</template:formTr>
		<template:formTr name="计划验收日期">
			<bean:write name="cable" property="planAcceptanceTime" format="yyyy/MM/dd" />
		</template:formTr>
		<template:formTr name="A端">
			${cable.a}
		</template:formTr>
		<template:formTr name="Z端">
			${cable.z}
		</template:formTr>
		<template:formTr name="光缆中继段">
			${cable.trunk}
		</template:formTr>
		<template:formTr name="铺设方式">
			<apptag:quickLoadList cssClass="input" keyValue="${cable.layingMethod}" name="layingmethod" listName="layingmethod" type="look"/>
		</template:formTr>
		<template:formTr name="纤芯数">
			${cable.fibercoreNo}
		</template:formTr>
		<template:formTr name="光缆级别">
			<apptag:quickLoadList cssClass="input" keyValue="${cable.cableLevel}" style="width:100" name="cableLevel" listName="cabletype" type="look"/>
		</template:formTr>
		<template:formTr name="光缆长度">
			${cable.cableLength}
		</template:formTr>
		<template:formTr name="施工单位">
			${cable.builder}
		</template:formTr>
		<template:formTr name="施工单位联系方式">
			${cable.builderPhone}
		</template:formTr>
		<template:formTr name="工程中心项目经理">
			${cable.prcpm}
		</template:formTr>
		<template:formTr name="备注">
			${cable.remark}
		</template:formTr>
		<template:formSubmit>
			<td>
				<input type="button" value="关闭" onclick="parent.close();"/>
			</td>
		</template:formSubmit>
	</template:formTable>
</body>
</html>