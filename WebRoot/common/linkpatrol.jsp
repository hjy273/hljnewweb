
<c:if test="${sessionScope.LOGIN_USER.type=='11'}">

	<template:formTr name="所属区域" >
	<apptag:setSelectOptions valueName="regionCollection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
	<select name="regionID" class="selecttext" id="REGIONID" style="width:225">
		<option value="">请选择...</option>
			<c:forEach var="region" items="${regionCollection}">
				<option value="<c:out value="${region.value}"></c:out>"><c:out value="${region.label}"></c:out></option>
			</c:forEach>
	</select><br>
	</template:formTr>
	<template:formTr name="代维单位" >
		<select name="contractorID" class="selecttext" style="width:225" id="WORKID">
			<option value="">请选择...</option>
		</select><br>

	</template:formTr>
	<c:if test="${sessionScope.PMType == 'group'}">
	<template:formTr name="巡检维护组" >
		<select name="patrolID" Class="inputtext" style="width:225" id='EXECUTORID'>
			<option value="">请选择...</option>
		</select><br>
	</template:formTr>
	</c:if>
	<c:if test="${sessionScope.PMType!= 'group'} ">
	<template:formTr name="执 行 人" >
		<select name="patrolID" Class="inputtext" style="width:225" id='EXECUTORID'>
			<option value="">请选择...</option>
		</select><br>
	</template:formTr>
	</c:if>
<apptag:eventInteception evenType="onchange" source="REGIONID" baseUrl="${ctx}/CommonTagAction.do?sqlkey=conListSql" target="WORKID"></apptag:eventInteception>
<apptag:eventInteception evenType="onchange" source="WORKID" baseUrl="${ctx}/CommonTagAction.do?sqlkey=patrolsql" target="EXECUTORID"></apptag:eventInteception>

</c:if>
<c:if test="${sessionScope.LOGIN_USER.type =='12'}">
	<template:formTr name="代维单位" >
	<apptag:setSelectOptions valueName="workCollection" tableName="contractorinfo" columnName1="contractorname" region="true" columnName2="contractorid" order="contractorid" />
	<select name="contractorID" class="selecttext" style="width:225" id="WORKID">
			<option value="">请选择...</option>
			<c:forEach var="workID" items="${workCollection}">
				<option value="<c:out value="${workID.value}"></c:out>"><c:out value="${workID.label}"></c:out></option>
			</c:forEach>
	</select><br>

	</template:formTr>
	<c:if test="${sessionScope.PMType == 'group'}">
	<template:formTr name="巡检维护组" >
		<select name="patrolID" Class="inputtext" style="width:225" id='EXECUTORID'>
			<option value="">请选择...</option>
		</select><br>
	</template:formTr>
	</c:if>
	<c:if test="${sessionScope.PMType!= 'group'} ">
	<template:formTr name="执 行 人" >
		<select name="patrolID" Class="inputtext" style="width:225" id='EXECUTORID'>
			<option value="">请选择...</option>
		</select><br>
	</template:formTr>
	</c:if>
	<ct:eventInteception evenType="onchange" source="WORKID" baseUrl="${ctx}/CommonTagAction.do?sqlkey=patrolsql" target="EXECUTORID"></ct:eventInteception>

</c:if>
<c:if test="${sessionScope.LOGIN_USER.type =='22'}">

	<%
		Map map = new HashMap();
		UserInfo user = (UserInfo)session.getAttribute("LOGIN_USER");
		map.put("parentid",user.getDeptID());
		session.setAttribute("CONDITIONMAP",map);
	 %>
	<c:if test="${sessionScope.PMType == 'group'}">
	<apptag:setSelectOptions valueName="executorCollection" tableName="patrolmaninfo" columnName1="patrolname" exterior="true" columnName2="patrolid" order="patrolid" />
	<template:formTr name="巡检维护组" >
		<select name="patrolID" Class="inputtext" style="width:225" id='executorid'>
			<option value="">请选择...</option>
			<c:forEach var="executorid" items="${executorCollection}">
				<option value="<c:out value="${executorid.value}"></c:out>"><c:out value="${executorid.label}"></c:out></option>
			</c:forEach>
		</select><br>
	</template:formTr>
	</c:if>
	<c:if test="${sessionScope.PMType!= 'group'} ">
	<template:formTr name="执 行 人" >
		<select name="patrolID" Class="inputtext" style="width:225" id='executorid'>
			<option value="">请选择...</option>
			<c:forEach var="workID" items="${executorCollection}">
				<option value="<c:out value="${executorid.value}"></c:out>"><c:out value="${executorid.label}"></c:out></option>
			</c:forEach>
		</select><br>
	</template:formTr>
	</c:if>
</c:if>


