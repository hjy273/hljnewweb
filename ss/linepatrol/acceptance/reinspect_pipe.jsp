<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	
	<script type="text/javascript">
		function addPipes(){
			var objects = document.getElementsByName('objects');
			if(objects.length == 0){
				showError();
			}else{
				var str = '';
				for(var j = 0 ; j < objects.length ; j++){
					if(objects[j].checked){
						if(str == ''){
							str = objects[j].value;
						}else{
							str += ',' + objects[j].value;
						}
					}
				}
				if(str == ''){
					showError();
				}else{
					addPipe(str);
				}
			}
		}
		function showError(){
			alert('没有选中任何管道');
		}
		function addPipe(str){
			var url = '${ctx}/acceptanceAction.do?method=reinspectPipe&objects='+str;
			new Ajax.Request(url,{
		    	method:'post',
		    	evalScripts:true,
		    	onSuccess:function(transport){
					alert('添加成功');
					parent.close();
		    	},
		    	onFailure: function(){ 
			    	alert('添加失败，请重试.')
			    }
			});
		}
	</script>
</head>
<body>
	<template:titile value="管道验收未通过列表" />
	<display:table name="sessionScope.pipes" id="row"  pagesize="8" export="false">
		<display:column media="html" title="选择">
			<c:set var="check" value="false" />
			<c:forEach var="obj" items="${apply.pipes}">
				<c:if test="${obj.id eq row.id}">
					<c:set var="check" value="true" />
				</c:if>
			</c:forEach>
			<c:choose>
				<c:when test="${check}">
					<input type="checkbox" name="objects" value="${row.id}" checked/>
				</c:when>
				<c:otherwise>
					<input type="checkbox" name="objects" value="${row.id}" />
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column property="projectName" title="工程名称"/>
		<display:column property="pipeAddress" title="管道地点"/>
		<display:column property="pipeRoute" title="详细路由"/>
		<display:column property="builder" title="施工单位"/>
		<display:column media="html" title="管道属性">
			<apptag:quickLoadList style="width:130px" keyValue="${row.pipeProperty}" cssClass="select" name="pipeProperty" listName="property_right" type="look" />
		</display:column>
	</display:table>
	<table style="border:0px;">
		<tr>
			<td style="border:0px;text-align:center;">	
				<input type="button" value="提交" class="button" onclick="javascript:addPipes()">
				<input type="button" value="返回" class="button" onclick="javascript:history.back()">
			</td>
		</tr>
	</table>
</body>