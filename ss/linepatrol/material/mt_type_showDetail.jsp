<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>查看材料类型</title>
	<script type="text/javascript">

		 function  goBack(){
			history.back();
		}
	</script>
  </head>
  
  <body><br>
  	<template:titile value="查看材料类型"/>
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="类型名称">
  				<bean:write name="bean" property="typeName"/>
  		</template:formTr>
  		<template:formTr name="所属区域">
  				<bean:write name="regionName" />
  		</template:formTr>
  		<template:formTr name="备注">
  				<bean:write name="bean" property="remark"/>
  		</template:formTr>
  		 <template:formSubmit>
				      	<td>
				       	<!-- <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >-->
				       	<input type="button" class="button" onclick="history.back()" value="返回"/>
				      	</td>
				      	
			</template:formSubmit>
  	</template:formTable>
  </body>
</html>
