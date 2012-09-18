<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		 	view=function(idValue){
         		window.location.href = "${ctx}/resAction.do?method=load&type=view&id="+idValue;
		 	};
		 	edit=function(idValue){
				window.location.href="${ctx}/resAction.do?method=load&type=edit&id="+idValue;
		 	};
		 	
		 	
		 	exportList=function(){
		 		window.location.href="${ctx}/resAction.do?method=exportList";
		 	};
		 	
		 	scrap = function(id){
		 		var url = "${ctx}/resAction.do?method=scrap&id="+id;
		 		new Ajax.Request(url,{
		 	    	method:'post',
		 	    	evalScripts:true,
		 	    	onSuccess:function(transport){
		 					alert(transport.responseText);
		 					window.document.location.reload();
		 	    	},
		 	    	onFailure: function(){ alert('��������쳣......') }
		 		 	});
		 		 	
		 	};
		</script>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
	</head>
	<body>
		<template:titile value="�м̶β�ѯ" />
		<html:form action="/resAction.do?method=list&isQuery=isQuery">
		<template:formTable>
			<template:formTr name="�м̶�����" isOdd="true">
				<html:text property="segmentname" styleId="segmentname" style="width:150px" styleClass="inputtext" />
			</template:formTr>
			<template:formTr name="MIS��">
				<html:text property="MIS" styleId="MIS" style="width:150px" styleClass="inputtext"></html:text>
			</template:formTr>
			<template:formTr name="��������">
				<html:text property="projectName" styleId="projectName" style="width:150px" styleClass="inputtext" />
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
			<template:formTr name="���¼���" isOdd="false">
				<apptag:quickLoadList cssClass="input" style="width:150px" id="cableLevels" name="cableLevels" listName="cabletype" type="checkbox" keyValue="${bean.cableLevel}"/>
			</template:formTr>
			<template:formTr name="���跽ʽ" isOdd="false">
				<apptag:quickLoadList cssClass="input" style="width:150px" name="laytypes" id="laytypes" listName="layingmethod" type="checkbox" keyValue="${bean.laytype}"/>
			</template:formTr>
			<template:formTr name="�������">
				<html:radio property="currentState" value="">ȫ��</html:radio>
				<html:radio property="currentState" value="y">����</html:radio>
				<html:radio property="currentState" value="n">δ����</html:radio>
			</template:formTr>
		</template:formTable>
			<div align="center">
				<html:submit value="��ѯ" styleClass="button"></html:submit>
			</div>
		</html:form>
		<display:table name="sessionScope.result" id="currentRowObject"
			pagesize="15" requestURI="${ctx }/resAction.do?method=list">
			<display:column media="html" title="�м̶α��" sortable="true">
				<a href="javascript:view('${currentRowObject.kid}')">${currentRowObject.segmentid}</a>
			</display:column>
			<display:column property="assetno" title="�ʲ����" sortable="true" />
			<display:column property="segmentname" title="�м̶�����" maxLength="10"
				sortable="true" />
			<display:column property="fiberType" title="��о�ͺ�" maxLength="10"
				sortable="true" />
			<display:column media="html" title="���¼���" sortable="true">
				<c:out value="${cabletype[currentRowObject.cableLevel]}" />
			</display:column>
			<display:column media="html" sortable="true" title="���跽ʽ">
				<c:forEach var="s" items="${fn:split(currentRowObject.laytype,',')}">
				${layingmethod[s]} 
				</c:forEach>
			</display:column>
			<display:column media="html" sortable="true" title="����">
				<c:out value="${sections[currentRowObject.scetion]}" />
			</display:column>
			<display:column media="html" sortable="true" title="����">
				<c:out value="${places[currentRowObject.place]}" />
			</display:column>
			<display:column property="grossLength" title="���³���"
				headerClass="subject" sortable="true" />
			<display:column property="producer" title="����" sortable="true" />
			<display:column media="html" sortable="true" title="��ά����">
				${currentRowObject.finishtime}
			</display:column>
			<display:column media="html" sortable="true" title="��ά">
				<c:out value="${contractor[currentRowObject.maintenanceId]}" />
			</display:column>
			<display:column media="html">
				<a href="javascript:edit('${currentRowObject.kid}')">�༭</a>
				<c:if test="${LOGIN_USER.deptype=='1'}">
					<a href="javascript:scrap('${currentRowObject.kid}')">����</a>
				</c:if>

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
