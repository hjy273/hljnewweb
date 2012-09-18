<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<body>
	<template:titile value="查看基站"/>
	<s:form action="stationAction_save" name="view" method="post">
		<template:formTable contentwidth="300" namewidth="220">
			<template:formTr name="基站坐标点">
				<apptag:assorciateAttr label="pointname" key="pointid" table="pointinfo" keyValue="${station.pointId}"/>
			</template:formTr>
			<template:formTr name="基站编号">
				${station.code }
			</template:formTr>
			<template:formTr name="基站名称">
				${station.stationname }
			</template:formTr>
			<template:formTr name="基站类型">
				${stationTypeList[station.stationtype]}
			</template:formTr>
			<template:formTr name="基站级别">
				${stationLevelList[station.stationlevel]}
			</template:formTr>
			<template:formTr name="地址">
				${station.pos}
			</template:formTr>
			<template:formTr name="开通日期">
				${station.opendate }
			</template:formTr>

			<template:formTr name="维护组">
				<apptag:assorciateAttr  key="patrolid" keyValue="${station.stationSc.pgId }" label="patrolname" table="patrolmaninfo"/>
			</template:formTr>


			<template:formSubmit>
		      	<td>
		      	  	<input type="button" class="button" onclick="history.back()" value="返回" >
		      	</td>
		    </template:formSubmit>
		</template:formTable>
	</s:form>
</body>