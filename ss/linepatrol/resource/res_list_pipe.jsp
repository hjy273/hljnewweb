<%@include file="/common/header.jsp"%>　
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		 	view=function(idValue){
         		window.location.href = "${ctx}/pipeAction.do?method=load&type=view&id="+idValue;
		 	}
		 	edit=function(idValue){
				window.location.href="${ctx}/pipeAction.do?method=load&type=edit&id="+idValue;
		 	}
		 	exportList=function(){
		 		window.location.href="${ctx}/pipeAction.do?method=exportList";
		 	};
		</script>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
	</head>
	<body>
		<template:titile value="管道查询结果一览表" />
		<html:form action="/pipeAction.do?method=list&isQuery=isQuery">
		<template:formTable>
			<template:formTr name="管道地点" isOdd="true">
				<html:text property="pipeAddress" styleId="pipeAddress" style="width:150px" styleClass="inputtext" />
			</template:formTr>
			<template:formTr name="MIS号">
				<html:text property="MIS" styleId="MIS" style="width:150px" styleClass="inputtext"></html:text>
			</template:formTr>
			<template:formTr name="工程名称">
				<html:text property="workName" styleId="workName" style="width:150px" styleClass="inputtext" />
			</template:formTr>
			<template:formTr name="管道路由">
				<html:text property="pipeLine" styleId="pipeLine" style="width:150px" styleClass="inputtext" />
			</template:formTr>
			<template:formTr name="维护单位">
				<html:select property="maintenanceId" style="width:154px">
					<html:option value="">全部</html:option>
					<html:options collection="cons" property="contractorID"
								labelProperty="contractorName"></html:options>
				</html:select>
			</template:formTr>
			<template:formTr name="交维日期">
				<html:text property="finishStartTime" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" />--<html:text property="finishEndTime" onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'finishStartTime\')}'})" />
			</template:formTr>
			<template:formTr name="管道属性" isOdd="false">
				<apptag:quickLoadList cssClass="input" style="width:150px" id="pipeTypes" name="pipeTypes" listName="pipe_type" type="checkbox" keyValue="${bean.pipeType}"/>
			</template:formTr>
			<template:formTr name="产权属性" isOdd="false">
				<apptag:quickLoadList cssClass="input" style="width:150px" name="routeRess" id="routeRess" listName="property_right" type="checkbox" keyValue="${bean.routeRes}"/>
			</template:formTr>
		</template:formTable>
			<div align="center">
				<html:submit value="查询" styleClass="button"></html:submit>
			</div>
		</html:form>
		<display:table name="sessionScope.result" id="currentRowObject" pagesize="15">
			<display:column media="html" title="工程名称" sortable="true" maxLength="15" >
			<a href="javascript:view('${currentRowObject.id}')">${currentRowObject.workName}</a>
			</display:column>
			<display:column property="pipeAddress" title="管道地点" maxLength="10" sortable="true" />
			<display:column property="pipeLengthChannel" title="管道规模(沟)"  sortable="true"/>
      		<display:column property="pipeLengthHole" title="管道规模(孔)" sortable="true"/>
      		<display:column property="mobileScareChannel" title="移动规模(沟)"  sortable="true"/>
      		<display:column property="mobileScareHole" title="移动规模(孔)" sortable="true"/>
			<display:column media="html" title="管道属性" sortable="true">
				<c:forEach var="s" items="${fn:split(currentRowObject.pipeType,';')}">
				${pipe_type[s]} 
				</c:forEach>
			</display:column>
			<display:column media="html" title="产权属性" sortable="true" >
				<c:out value="${property_right[currentRowObject.routeRes]}" />  
			</display:column>
			<display:column media="html" sortable="true" title="交维日期" >
				<bean:write name="currentRowObject" property="finishTime" format="yyyy-MM-dd"/>
			</display:column>
			<display:column media="html" sortable="true" title="代维" >
				<c:out value="${contractor[currentRowObject.maintenanceId]}" />  
			</display:column>
			<display:column media="html">
				<a href="javascript:edit('${currentRowObject.id}')">编辑</a>
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td style="border:0px">
		    		<c:if test="${result != null}">
					  	<a href="javascript:exportList()">导出为Excel文件</a>
					</c:if>
		    	</td>
		    </tr>
		</table>
	</body>
</html>
