<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
		function addCables(){
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
					addCable(str);
				}
			}
		}
		function showError(){
			alert('没有选中任何光缆');
		}
		function addCable(str){
			var url = '${ctx}/acceptanceAction.do?method=reinspectCable&objects='+str;
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
	<template:titile value="光缆验收未通过列表" />
	<display:table name="sessionScope.cables" id="row"  pagesize="8" export="false">
		<display:column media="html" title="选择">
			<c:set var="check" value="false" />
			<c:forEach var="obj" items="${apply.cables}">
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
		<display:column property="cableNo" title="光缆编号"/>
		<display:column property="sid" title="资产编号"/>
		<display:column property="trunk" title="光缆中继段"/>
		<display:column property="fibercoreNo" title="纤芯数"/>
		<display:column media="html" title="光缆级别">
			<apptag:quickLoadList style="width:130px" keyValue="${row.cableLevel}" cssClass="select" name="cableLevel" listName="cabletype" type="look" />
		</display:column>
		<display:column media="html" title="光缆长度">
			${row.cableLength}千米
		</display:column>
	</display:table>
	<table style="border:0px;">
		<tr>
			<td style="border:0px;text-align:center;">
				<input type="button" value="提交" class="button" onclick="javascript:addCables()">
				<input type="button" value="返回" class="button" onclick="javascript:history.back()">
			</td>
		</tr>
	</table>
</body>