<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<script type="text/javascript" language="javascript">
		 function valiD(){
	        var mysplit = /^[1-9]\d{0,6}[\.]{0,4}\d{0,6}$|^-?0(\.\d*)?$/;
	        var num;
	        var obj = document.getElementById("state").value;
	        if(obj==1){
	       		num = document.getElementById("newstock").value;;
	        }else{
	            num = document.getElementById("oldstock").value;;
	        }
			if(!mysplit.test(num)){
				alert("����д�����ֲ��Ϸ�!");
				return;
			}
			var id = document.getElementById("address").value;
			if(obj==1){
			    parent.opener.document.getElementById(id+"11").value= num;
	       		parent.opener.document.getElementById(id+"1").innerText= num;
	        }else{
	          parent.opener.document.getElementById(id+"22").value= num;
	          parent.opener.document.getElementById(id+"2").innerText= num;
	        }
		    parent.close();
		 }
		</script>
				
	</head>
	<body>
		<table align="center">
			<tr height="50px"><td><font size="3" style="font-weight:bold"><bean:write name="bean" property="name"/>�б�</font></td></tr>
		</table>
			<html:form action="/materialUsedInfoAction.do?method=updateMaterialInfo" >
			 <input type="hidden" id="baseid" name="baseid" value="<bean:write name="bean" property="baseid"/>">
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="��������">
  				<bean:write name="bean" property="typename"/>
  		</template:formTr>
  		<template:formTr name="�������">
  				<bean:write name="bean" property="modelname"/>
  		</template:formTr>
  		<template:formTr name="��ŵ�ַ">
  		        <input type="hidden" id="address" value="<bean:write name="bean" property="addrid"/>">
  		       <bean:write name="bean" property="address"/>
  		</template:formTr>
  		<logic:equal value="1" name="state">
	  		<template:formTr name="�������">
	  		      <input type="text" id="newstock" name="newstock" value="<bean:write name="bean" property="newstock"/>"> 
	  		</template:formTr>
  		</logic:equal>
  		<logic:equal value="2" name="state">
	  		<template:formTr name="���ɿ��">
	  		      <input type="text" id="oldstock" name="oldstock" value="<bean:write name="bean" property="oldstock"/>"> 
	  		</template:formTr>
  		</logic:equal>
  	  <input type="hidden" id="state" value="<bean:write name="state"/>">
  		 <template:formSubmit>
				      	<td>
                          <html:button property="action" styleClass="button"  onclick="valiD()">�ύ</html:button>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >����</html:reset>
				      	</td>
				      	<td>
							<html:button property="action" styleClass="button"
							onclick="javascript:window.close();">�ر�</html:button>
						</td>
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
	</body>
</html>
