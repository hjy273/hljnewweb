<%@include file="/common/header.jsp"%>
<html>
<head>
	<title>�м̶�</title>
	<script type="text/javascript">
		function find(){
			var filter = $('filter').value;
			var level = $('cableLevel').value;
			var url = "${ctx}/trunkAction.do?method=queryTrunks&filter="+filter+"&id="+$('id').value+"&level="+level;
			window.location.href = url;
		}
		function setValue(obj){
			var url = '${ctx}/trunkAction.do?method=addTrunks&id='+obj.value;
			if(obj.checked){
				url += '&type=add';
			}else{
				url += '&type=delete';
			}
			sendAjax(url);
		}
		function sendAjax(url){
			$('submit').disabled = "disabled";
			var myAjax = new Ajax.Request(
				url, 
				{method:"post", onComplete:callback, asynchronous:true}
			);
		}
		function callback(){
			$('submit').disabled = "";
		}
		function checkall(){
			var filter = $('filter').value;
			var level = $('cableLevel').value;
			window.location.href = '${ctx}/trunkAction.do?method=checkall&filter='+filter+"&level="+level+"&id="+$('id').value;
		}
		function cancelCheckAll(){
			var filter = $('filter').value;
			var level = $('cableLevel').value;
			window.location.href = '${ctx}/trunkAction.do?method=cancelCheckAll&filter='+filter+"&level="+level+"&id="+$('id').value;
		}
	</script>
</head>
<body>
	<template:titile value="ѡ���м̶�" />
	<template:formTable>
		<html:form action="/trunkAction.do?method=submit" styleId="form" method="post">
			<input type="hidden" id="id" name="id" value="${id}">
			<template:formTr name="ģ������">
				<input type="text" style="width:130px" class="inputtext" id="filter" value="${filter}" />
			</template:formTr>
			<template:formTr name="���¼���">
				<apptag:quickLoadList cssClass="input" isQuery="true" style="width:130px" keyValue="${level}" name="cableLevel" listName="cabletype" type="select"/>
				<input type="button" value="����" onclick="find()" >
				<input type="button" value="ȫѡ" onclick="checkall()" >
				<input type="button" value="ȫ��ѡ" onclick="cancelCheckAll()" >
			</template:formTr>
		    <c:if test="${!empty trunkList}">
				<tr class=trcolor>
					<td class="tdulleft" colspan="2" style="text-align:center;">
						<html:submit property="action" styleId="submit" styleClass="button" value="ȷ��" />
					</td>
			    </tr>
			</c:if>
			<template:formTr name="�м̶��б�">
				<table>
					${trunkList}
				</table>
			</template:formTr>
			<c:if test="${!empty trunkList}">
			<template:formSubmit>
				<td>
					<html:submit property="action" styleId="submit" styleClass="button" value="ȷ��" />
				</td>
		    </template:formSubmit>
		    </c:if>
		</html:form>
	</template:formTable>
</body>
</html>