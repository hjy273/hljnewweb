<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>查询材料规格</title>

  </head>
  
  <body><br>
  	<template:titile value="查询材料规格"/>
  	<html:form action="/materialModelAction.do?method=getMaterialModels" >
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="规格名称">
  				<html:text property="modelName" styleClass="inputtext" name="materialModelBean" style="width:215px;" maxlength="20" styleId="typeName"/>
  		</template:formTr>
  		<template:formTr name="所属类型">
  				<select name="typeID" class="inputtext" style="width:215px" id="typeID">
  						<option value="-1">不限</option>
         				<logic:present scope="request" name="types">
         					<logic:iterate id="r" name="types">
		                    	<option value="<bean:write name="r" property="id" />"><bean:write name="r" property="typename"/></option>
		                	</logic:iterate>
         				</logic:present>
         		</select>
  		</template:formTr>
  		 <template:formSubmit>
				      	<td>
                          <html:submit property="action" styleClass="button">查询</html:submit>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >重置</html:reset>
				      	</td>
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
  </body>
</html>
