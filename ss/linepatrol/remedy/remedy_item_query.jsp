<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>��ѯ������</title>

  </head>
  
  <body><br>
  	<template:titile value="��ѯ������"/>
  	<html:form action="/remedyItemAction.do?method=getRemedyItems" styleId="addRemedyItem">
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="��Ŀ����">
  				<html:text property="itemName" styleClass="inputtext" name="remedyItemBean" style="width:215;" maxlength="20" styleId="itemName"/>
  		</template:formTr>
  		<template:formTr name="��������">
  				<select name="regionID" class="inputtext" style="width:215px" id="regionID">
  						<option value="">����</option>
         				<logic:present scope="request" name="regions">
         					<logic:iterate id="r" name="regions">
		                    	<option value="<bean:write name="r" property="regionid" />"><bean:write name="r" property="regionname"/></option>
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
