<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>ȡ������</title>
		<script type="text/javascript">
			function submitForm(){
				if(submitForm1.cancelReason.value!=""){
					if(valCharLength(submitForm1.cancelReason.value) > 512) {
						alert("ȡ��ԭ���������ܳ���256�����ֻ���512��Ӣ���ַ�!");
						return;
					}
					submitForm1.submit();
				}else{
					alert("������ȡ��ԭ��");
				}
			}
	function valCharLength(Value){
		     var j=0;
		     var s = Value;
		     for(var i=0; i<s.length; i++) {
				if (s.substr(i,1).charCodeAt(0)>255) {
					j  = j + 2;
				} else { 
					j++;
				}
		     }
		     return j;
	}
		</script>
	</head>
	<body>
		<template:titile value="ȡ������" />
		<html:form action="/safeguardTaskAction.do?method=cancelSafeguardTask" method="post"
			styleId="submitForm1">
			<template:formTable>
				<template:formTr name="ȡ��ԭ��" isOdd="false">
					<input name="id" type="hidden" value="${safeguard_task_id}" />
					<textarea name="cancelReason" value="" rows="5"
						class="inputarea" style="width:150px;"></textarea>
				</template:formTr>
				<template:formSubmit>
					<td colspan="2" align="center" class="tdc">
						<input property="action" type="button" onclick="submitForm()"
							value="ȷ��" class="button"></input>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input property="action" type="reset" value="��д" class="button"></input>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
