<%@include file="/common/header.jsp"%>��
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
		<template:titile value="�ܵ���ѯ���һ����" />
		<html:form action="/pipeAction.do?method=list&isQuery=isQuery">
		<template:formTable>
			<template:formTr name="�ܵ��ص�" isOdd="true">
				<html:text property="pipeAddress" styleId="pipeAddress" style="width:150px" styleClass="inputtext" />
			</template:formTr>
			<template:formTr name="MIS��">
				<html:text property="MIS" styleId="MIS" style="width:150px" styleClass="inputtext"></html:text>
			</template:formTr>
			<template:formTr name="��������">
				<html:text property="workName" styleId="workName" style="width:150px" styleClass="inputtext" />
			</template:formTr>
			<template:formTr name="�ܵ�·��">
				<html:text property="pipeLine" styleId="pipeLine" style="width:150px" styleClass="inputtext" />
			</template:formTr>
			<template:formTr name="ά����λ">
				<html:select property="maintenanceId" style="width:154px">
					<html:option value="">ȫ��</html:option>
					<html:options collection="cons" property="contractorID"
								labelProperty="contractorName"></html:options>
				</html:select>
			</template:formTr>
			<template:formTr name="��ά����">
				<html:text property="finishStartTime" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" />--<html:text property="finishEndTime" onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'finishStartTime\')}'})" />
			</template:formTr>
			<template:formTr name="�ܵ�����" isOdd="false">
				<apptag:quickLoadList cssClass="input" style="width:150px" id="pipeTypes" name="pipeTypes" listName="pipe_type" type="checkbox" keyValue="${bean.pipeType}"/>
			</template:formTr>
			<template:formTr name="��Ȩ����" isOdd="false">
				<apptag:quickLoadList cssClass="input" style="width:150px" name="routeRess" id="routeRess" listName="property_right" type="checkbox" keyValue="${bean.routeRes}"/>
			</template:formTr>
		</template:formTable>
			<div align="center">
				<html:submit value="��ѯ" styleClass="button"></html:submit>
			</div>
		</html:form>
		<display:table name="sessionScope.result" id="currentRowObject" pagesize="15">
			<display:column media="html" title="��������" sortable="true" maxLength="15" >
			<a href="javascript:view('${currentRowObject.id}')">${currentRowObject.workName}</a>
			</display:column>
			<display:column property="pipeAddress" title="�ܵ��ص�" maxLength="10" sortable="true" />
			<display:column property="pipeLengthChannel" title="�ܵ���ģ(��)"  sortable="true"/>
      		<display:column property="pipeLengthHole" title="�ܵ���ģ(��)" sortable="true"/>
      		<display:column property="mobileScareChannel" title="�ƶ���ģ(��)"  sortable="true"/>
      		<display:column property="mobileScareHole" title="�ƶ���ģ(��)" sortable="true"/>
			<display:column media="html" title="�ܵ�����" sortable="true">
				<c:forEach var="s" items="${fn:split(currentRowObject.pipeType,';')}">
				${pipe_type[s]} 
				</c:forEach>
			</display:column>
			<display:column media="html" title="��Ȩ����" sortable="true" >
				<c:out value="${property_right[currentRowObject.routeRes]}" />  
			</display:column>
			<display:column media="html" sortable="true" title="��ά����" >
				<bean:write name="currentRowObject" property="finishTime" format="yyyy-MM-dd"/>
			</display:column>
			<display:column media="html" sortable="true" title="��ά" >
				<c:out value="${contractor[currentRowObject.maintenanceId]}" />  
			</display:column>
			<display:column media="html">
				<a href="javascript:edit('${currentRowObject.id}')">�༭</a>
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td style="border:0px">
		    		<c:if test="${result != null}">
					  	<a href="javascript:exportList()">����ΪExcel�ļ�</a>
					</c:if>
		    	</td>
		    </tr>
		</table>
	</body>
</html>
