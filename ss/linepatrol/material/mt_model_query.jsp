<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>��ѯ���Ϲ��</title>

  </head>
  
  <body><br>
  	<template:titile value="��ѯ���Ϲ��"/>
  	<html:form action="/materialModelAction.do?method=getMaterialModels" >
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="�������">
  				<html:text property="modelName" styleClass="inputtext" name="materialModelBean" style="width:215px;" maxlength="20" styleId="typeName"/>
  		</template:formTr>
  		<template:formTr name="��������">
  				<select name="typeID" class="inputtext" style="width:215px" id="typeID">
  						<option value="-1">����</option>
         				<logic:present scope="request" name="types">
         					<logic:iterate id="r" name="types">
		                    	<option value="<bean:write name="r" property="id" />"><bean:write name="r" property="typename"/></option>
		                	</logic:iterate>
         				</logic:present>
         		</select>
  		</template:formTr>
  		 <template:formSubmit>
				      	<td>
                          <html:submit property="action" styleClass="button">��ѯ</html:submit>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >����</html:reset>
				      	</td>
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
  </body>
</html>
