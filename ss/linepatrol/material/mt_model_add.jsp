<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>���Ӳ��Ϲ��</title>
	<script type="text/javascript">
	function checkAddInfo() {
  			var modelName = document.getElementById('modelName');
  			if(modelName.value.length == 0) {
  				alert("����д�������!");
  				modelName.focus();
  				return;
  			}
  			var typeID = document.getElementById('typeID');
  			if(typeID.value.length == 0) {
  				alert("��ѡ����������!");
  				typeID.focus();
  				return;
  			}
  			
  			var unit = document.getElementById('unit');
  			if(unit.value.length == 0) {
  				alert("����д��λ!");
  				unit.focus();
  				return;
  			}
  			
  			var remark = document.getElementById('remark');
  			if(valCharLength(remark.value) > 1020) {
  				alert("��ע��Ϣ���ܳ���510�����ֻ���1020��Ӣ���ַ���")
              	return;
  			}
  			processBar(addMaterialModel);
  			addMaterialModel.submit();
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
  
  <body><br>
  	<template:titile value="���Ӳ��Ϲ��"/>
  	<html:form action="/materialModelAction.do?method=addMaterialModel" styleId="addMaterialModel">
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="�������">
  				<html:text property="modelName" styleClass="inputtext" name="materialModelBean" style="width:215px;" maxlength="20" styleId="modelName"/>
  		</template:formTr>
  		<template:formTr name="��������">
  				<select name="typeID" class="inputtext" style="width:215px" id="typeID">
         				<logic:present scope="request" name="lists">
         					<logic:iterate id="r" name="lists">
		                    	<option value="<bean:write name="r" property="id" />"><bean:write name="r" property="typename"/></option>
		                	</logic:iterate>
         				</logic:present>
         		</select>
  		</template:formTr>
  		<template:formTr name="��λ">
  				<html:text property="unit" styleClass="inputtext" name="materialModelBean" style="width:215px;" maxlength="20" styleId="unit"/>
  		</template:formTr>
  		<template:formTr name="��ע">
  				<html:textarea  property="remark" name="materialModelBean" style="width:215px" styleId="remark" cols="36" rows="4"  title="�������510�����֣�1020��Ӣ����ĸ��" styleClass="textarea"></html:textarea>
  		</template:formTr>
  		 <template:formSubmit>
				      	<td>
                          <html:button property="action" styleClass="button"  onclick="checkAddInfo()">����</html:button>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >����</html:reset>
				      	</td>
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
  </body>
</html>
