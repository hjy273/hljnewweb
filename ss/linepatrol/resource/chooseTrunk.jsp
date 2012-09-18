<%@include file="/common/header.jsp"%>
<html>
<head>
	<title>中继段</title>
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
	<template:titile value="选择中继段" />
	<template:formTable>
		<html:form action="/trunkAction.do?method=submit" styleId="form" method="post">
			<input type="hidden" id="id" name="id" value="${id}">
			<template:formTr name="模糊搜索">
				<input type="text" style="width:130px" class="inputtext" id="filter" value="${filter}" />
			</template:formTr>
			<template:formTr name="光缆级别">
				<apptag:quickLoadList cssClass="input" isQuery="true" style="width:130px" keyValue="${level}" name="cableLevel" listName="cabletype" type="select"/>
				<input type="button" value="查找" onclick="find()" >
				<input type="button" value="全选" onclick="checkall()" >
				<input type="button" value="全不选" onclick="cancelCheckAll()" >
			</template:formTr>
		    <c:if test="${!empty trunkList}">
				<tr class=trcolor>
					<td class="tdulleft" colspan="2" style="text-align:center;">
						<html:submit property="action" styleId="submit" styleClass="button" value="确定" />
					</td>
			    </tr>
			</c:if>
			<template:formTr name="中继段列表">
				<table>
					${trunkList}
				</table>
			</template:formTr>
			<c:if test="${!empty trunkList}">
			<template:formSubmit>
				<td>
					<html:submit property="action" styleId="submit" styleClass="button" value="确定" />
				</td>
		    </template:formSubmit>
		    </c:if>
		</html:form>
	</template:formTable>
</body>
</html>