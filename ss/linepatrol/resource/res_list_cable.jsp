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
		 	    	onFailure: function(){ alert('请求服务异常......') }
		 		 	});
		 		 	
		 	};
		</script>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
	</head>
	<body>
		<template:titile value="中继段查询" />
		<html:form action="/resAction.do?method=list&isQuery=isQuery">
		<template:formTable>
			<template:formTr name="中继段名称" isOdd="true">
				<html:text property="segmentname" styleId="segmentname" style="width:150px" styleClass="inputtext" />
			</template:formTr>
			<template:formTr name="MIS号">
				<html:text property="MIS" styleId="MIS" style="width:150px" styleClass="inputtext"></html:text>
			</template:formTr>
			<template:formTr name="工程名称">
				<html:text property="projectName" styleId="projectName" style="width:150px" styleClass="inputtext" />
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
			<template:formTr name="光缆级别" isOdd="false">
				<apptag:quickLoadList cssClass="input" style="width:150px" id="cableLevels" name="cableLevels" listName="cabletype" type="checkbox" keyValue="${bean.cableLevel}"/>
			</template:formTr>
			<template:formTr name="敷设方式" isOdd="false">
				<apptag:quickLoadList cssClass="input" style="width:150px" name="laytypes" id="laytypes" listName="layingmethod" type="checkbox" keyValue="${bean.laytype}"/>
			</template:formTr>
			<template:formTr name="交资情况">
				<html:radio property="currentState" value="">全部</html:radio>
				<html:radio property="currentState" value="y">交资</html:radio>
				<html:radio property="currentState" value="n">未交资</html:radio>
			</template:formTr>
		</template:formTable>
			<div align="center">
				<html:submit value="查询" styleClass="button"></html:submit>
			</div>
		</html:form>
		<display:table name="sessionScope.result" id="currentRowObject"
			pagesize="15" requestURI="${ctx }/resAction.do?method=list">
			<display:column media="html" title="中继段编号" sortable="true">
				<a href="javascript:view('${currentRowObject.kid}')">${currentRowObject.segmentid}</a>
			</display:column>
			<display:column property="assetno" title="资产编号" sortable="true" />
			<display:column property="segmentname" title="中继段名称" maxLength="10"
				sortable="true" />
			<display:column property="fiberType" title="纤芯型号" maxLength="10"
				sortable="true" />
			<display:column media="html" title="光缆级别" sortable="true">
				<c:out value="${cabletype[currentRowObject.cableLevel]}" />
			</display:column>
			<display:column media="html" sortable="true" title="敷设方式">
				<c:forEach var="s" items="${fn:split(currentRowObject.laytype,',')}">
				${layingmethod[s]} 
				</c:forEach>
			</display:column>
			<display:column media="html" sortable="true" title="区划">
				<c:out value="${sections[currentRowObject.scetion]}" />
			</display:column>
			<display:column media="html" sortable="true" title="区县">
				<c:out value="${places[currentRowObject.place]}" />
			</display:column>
			<display:column property="grossLength" title="光缆长度"
				headerClass="subject" sortable="true" />
			<display:column property="producer" title="厂家" sortable="true" />
			<display:column media="html" sortable="true" title="交维日期">
				${currentRowObject.finishtime}
			</display:column>
			<display:column media="html" sortable="true" title="代维">
				<c:out value="${contractor[currentRowObject.maintenanceId]}" />
			</display:column>
			<display:column media="html">
				<a href="javascript:edit('${currentRowObject.kid}')">编辑</a>
				<c:if test="${LOGIN_USER.deptype=='1'}">
					<a href="javascript:scrap('${currentRowObject.kid}')">作废</a>
				</c:if>

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
