<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<body>
	<template:titile value="�鿴��վ"/>
	<s:form action="stationAction_save" name="view" method="post">
		<template:formTable contentwidth="300" namewidth="220">
			<template:formTr name="��վ�����">
				<apptag:assorciateAttr label="pointname" key="pointid" table="pointinfo" keyValue="${station.pointId}"/>
			</template:formTr>
			<template:formTr name="��վ���">
				${station.code }
			</template:formTr>
			<template:formTr name="��վ����">
				${station.stationname }
			</template:formTr>
			<template:formTr name="��վ����">
				${stationTypeList[station.stationtype]}
			</template:formTr>
			<template:formTr name="��վ����">
				${stationLevelList[station.stationlevel]}
			</template:formTr>
			<template:formTr name="��ַ">
				${station.pos}
			</template:formTr>
			<template:formTr name="��ͨ����">
				${station.opendate }
			</template:formTr>

			<template:formTr name="ά����">
				<apptag:assorciateAttr  key="patrolid" keyValue="${station.stationSc.pgId }" label="patrolname" table="patrolmaninfo"/>
			</template:formTr>


			<template:formSubmit>
		      	<td>
		      	  	<input type="button" class="button" onclick="history.back()" value="����" >
		      	</td>
		    </template:formSubmit>
		</template:formTable>
	</s:form>
</body>