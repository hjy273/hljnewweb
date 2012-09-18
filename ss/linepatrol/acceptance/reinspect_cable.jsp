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
			alert('û��ѡ���κι���');
		}
		function addCable(str){
			var url = '${ctx}/acceptanceAction.do?method=reinspectCable&objects='+str;
			new Ajax.Request(url,{
		    	method:'post',
		    	evalScripts:true,
		    	onSuccess:function(transport){
					alert('��ӳɹ�');
					parent.close();
		    	},
		    	onFailure: function(){ 
			    	alert('���ʧ�ܣ�������.')
			    }
			});
		}
	</script>
</head>
<body>
	<template:titile value="��������δͨ���б�" />
	<display:table name="sessionScope.cables" id="row"  pagesize="8" export="false">
		<display:column media="html" title="ѡ��">
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
		<display:column property="cableNo" title="���±��"/>
		<display:column property="sid" title="�ʲ����"/>
		<display:column property="trunk" title="�����м̶�"/>
		<display:column property="fibercoreNo" title="��о��"/>
		<display:column media="html" title="���¼���">
			<apptag:quickLoadList style="width:130px" keyValue="${row.cableLevel}" cssClass="select" name="cableLevel" listName="cabletype" type="look" />
		</display:column>
		<display:column media="html" title="���³���">
			${row.cableLength}ǧ��
		</display:column>
	</display:table>
	<table style="border:0px;">
		<tr>
			<td style="border:0px;text-align:center;">
				<input type="button" value="�ύ" class="button" onclick="javascript:addCables()">
				<input type="button" value="����" class="button" onclick="javascript:history.back()">
			</td>
		</tr>
	</table>
</body>