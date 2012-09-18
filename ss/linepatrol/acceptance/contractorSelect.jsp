<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type="text/javascript">
		function choose(){
			var objs = document.getElementsByName('dept');
			var ids = '';
			var name = '';
			var num = 0;
			for(var i=0;i<objs.length;i++){
				if(objs[i].checked){
					num++;
					if(ids == ''){
						ids = objs[i].value;
					}else{
						ids += ',' + objs[i].value;
					}
					if(name == ''){
						name = $(objs[i].value).value;
					}else{
						name += ',' + $(objs[i].value).value;
					}
				}
			}
			if(num == 0){
				alert('代维公司至少选一个');
				return false;
			}
			setValue(name,ids);
			parent.setValue(ids);
			parent.setName(name);
			parent.close();
		}
		function setValue(name,id){
			var url = "${ctx}/acceptanceAction.do?method=saveChoose&names="+name+"&ids="+id;
			var myAjax = new Ajax.Request(
				url, 
				{method:"post", onComplete:callback, asynchronous:true}
			);
		}
		function callback(){
			
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="选择代维公司" />
	<form id="form" action="#">
		<template:formTable namewidth="150" contentwidth="300">
			<template:formTr name="代维公司">
				<c:forEach var="dept" items="${dept}">
					<input type="checkbox" name="dept" class="required" value="${dept.contractorid}" 
						<c:forTokens items="${ids}" var="item" delims=","> 
							<c:if test="${item eq dept.contractorid}">
								checked
							</c:if>
						</c:forTokens> 
					>
					<input type="text" class="view" id="${dept.contractorid}" value="${dept.contractorname}"><br/>
				</c:forEach>
			</template:formTr>
			<template:formSubmit>
				<td>
					<input type="button" onclick="choose();" class="button" value="提交">
				</td>
			</template:formSubmit>
		</template:formTable>
	</form>
</body>