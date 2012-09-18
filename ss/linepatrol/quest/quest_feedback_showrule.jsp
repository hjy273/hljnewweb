<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>�������</title>
		<script type="text/javascript">
			function checkForm(){
				//var test=parent.window.document.getElementById("getTestValue").value;
				var options=document.getElementById("options").value;
				var itemId=document.getElementById("itemId").value;
				var comId=document.getElementById("comId").value;
				var comItemId="d"+comId+""+itemId;//ҳ����ʾDIV��ID
				var comItemIdIn=comId+""+itemId;//ҳ����Input��ID
				var parentLable=parent.window.document.getElementById(comItemId);
				var parentLableIn=parent.window.document.getElementById(comItemIdIn);
				var showValue="";//��ҳ������ʾ��ֵ
				var trueValue="";//������Action�е�ֵ
				var explainValue="";
				if(options=='2'||options=='3'){
					var explain=document.getElementsByName("explainValue");
					for(var i=0;i<explain.length;i++){
						if(explain[i].checked){
							explainValue=document.getElementById(explain[i].value).value;
							showValue+=explainValue+",";
							trueValue+=explain[i].value+",";
						}
					}
					showValue=showValue.substring(0,showValue.length-1);
					trueValue=trueValue.substring(0,trueValue.length-1);
					if(explainValue==""){
						alert("������ѡ��һ�");
						return;
					}
					
				}else if(options=='1'){
					var explainValue=document.getElementById("explainValue").value;
					if(explainValue==""){
						alert("��������ֵ��");
						return;
					}
					if(isNaN(explainValue)){
						alert("����Ϊ��ֵ��");
						return;
					}
					showValue=explainValue;
					trueValue=explainValue;
				}else{
					var explainValue=document.getElementById("explainValue").value;
					if(explainValue==""){
						alert("���������ݣ�");
						return;
					}
					showValue=explainValue;
					trueValue=explainValue;
				}
				//alert(trueValue);
				parentLableIn.value=trueValue;
				//parentLable.innerHTML="";
				parentLable.innerHTML=showValue;
				//alert(parentLableIn.value);
			    window.parent.win.close();
			}
			function setDefaultValue(){
				parent.window.document.getElementById(comItemId)
				var options=document.getElementById("options").value;
				var itemId=document.getElementById("itemId").value;
				var comId=document.getElementById("comId").value;
				var comItemId=comId+""+itemId;
				var parentValue=parent.window.document.getElementById(comItemId).value;
				if(parentValue!=null&&parentValue!=""){
					if(options=='2'||options=='3'){
						var explain=document.getElementsByName("explainValue");
						var explainValue=parentValue.split(",");
						for(var i=0;i<explainValue.length;i++){
							for(var j=0;j<explain.length;j++){
								if(explainValue[i]==explain[j].value){
									explain[j].checked=true;
								}
							}
						}
					}else{
						document.getElementById('explainValue').value=parentValue;
					}
				}
			}
		</script>
	</head>
	<body onload="setDefaultValue()">
		<template:titile value="�������"/>
		<table>
			<tr>
				<td>${questitem.item }</td>
			</tr>
			<input type="hidden" value="${questitem.options}" id="options"/>
			<input type="hidden" value="${questitem.id}" id="itemId"/>
			<input type="hidden" value="${comId}" id="comId"/>
			<c:forEach items="${ruleInfoList}" var="ruleInfo">
				<tr>
					<td>
						<input type="hidden" value="${ruleInfo.gradeExplain }" id="${ruleInfo.id }">
						<c:if test="${questitem.options=='2'}">
							<input type="radio" name="explainValue" value="${ruleInfo.id }"/ >${ruleInfo.gradeExplain }
						</c:if>
						<c:if test="${questitem.options=='3'}">
							<input type="checkbox" name="explainValue" value="${ruleInfo.id }"/ >${ruleInfo.gradeExplain }
						</c:if>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${questitem.options=='1'}">
				<tr>
					<td>
						���������֣�<input type="text" name="explainValue" id="explainValue" />
					</td>
				</tr>
			</c:if>
			<c:if test="${questitem.options=='4'}">
				<tr>
					<td>
						<textarea name="explainValue" id="explainValue" rows="3" cols="50"></textarea>
					</td>
				</tr>
			</c:if>
		</table>
		<div align="center" style="height:40px">
			<button class="button" onclick="checkForm()">ȷ��</button>
		</div>
	</body>
</html>
