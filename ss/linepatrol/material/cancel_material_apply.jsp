<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>取消材料申请</title>
		<script type="text/javascript">
			function submitForm(){
				if(submitForm1.cancelReason.value!=""){
					if(valCharLength(submitForm1.cancelReason.value) > 512) {
						alert("取消原因描述不能超过256个汉字或者512个英文字符!");
						return;
					}
					submitForm1.submit();
				}else{
					alert("请输入取消原因！");
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
		<template:titile value="取消材料申请" />
		<html:form action="/material_apply.do?method=cancelMaterialApply" method="post"
			styleId="submitForm1">
			<template:formTable>
				<template:formTr name="取消原因" isOdd="false">
					<input name="id" type="hidden" value="${apply_id}" />
					<textarea name="cancelReason" value="" rows="5"
						class="inputarea" style="width:150px;"></textarea>
				</template:formTr>
				<template:formSubmit>
					<td colspan="2" align="center" class="tdc">
						<input property="action" type="button" onclick="submitForm()"
							value="确定" class="button"></input>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input property="action" type="reset" value="重写" class="button"></input>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
