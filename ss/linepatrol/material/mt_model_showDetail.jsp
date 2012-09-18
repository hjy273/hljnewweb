<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>查看材料规格</title>
	
  </head>
  
  <body><br>
  	<template:titile value="查看材料规格"/>
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="规格名称">
  				<bean:write name="bean" property="modelName"/>
  		</template:formTr>
  		<template:formTr name="所属类型">
  				<bean:write name="typename" />
  		</template:formTr>
  		<template:formTr name="单位">
  				<bean:write name="bean" property="unit"/>
  		</template:formTr>
  		<template:formTr name="备注">
  				<bean:write name="bean" property="remark"/>
  		</template:formTr>
  		 <template:formSubmit>
				      	<td>
				       	<!--  <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >-->
				       	<input type="button" class="button" onclick="history.back()" value="返回"/>
				      	</td>
				      	
			</template:formSubmit>
  	</template:formTable>
  </body>
</html>
