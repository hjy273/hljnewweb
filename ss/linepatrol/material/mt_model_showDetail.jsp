<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>�鿴���Ϲ��</title>
	
  </head>
  
  <body><br>
  	<template:titile value="�鿴���Ϲ��"/>
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="�������">
  				<bean:write name="bean" property="modelName"/>
  		</template:formTr>
  		<template:formTr name="��������">
  				<bean:write name="typename" />
  		</template:formTr>
  		<template:formTr name="��λ">
  				<bean:write name="bean" property="unit"/>
  		</template:formTr>
  		<template:formTr name="��ע">
  				<bean:write name="bean" property="remark"/>
  		</template:formTr>
  		 <template:formSubmit>
				      	<td>
				       	<!--  <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >-->
				       	<input type="button" class="button" onclick="history.back()" value="����"/>
				      	</td>
				      	
			</template:formSubmit>
  	</template:formTable>
  </body>
</html>
