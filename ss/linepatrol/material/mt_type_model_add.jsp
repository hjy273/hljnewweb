<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>���Ӳ��Ϲ��</title>
	<script type="text/javascript">
	function checkAddInfo(act) {
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
  			act = document.getElementById('act').value=act;
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
  	<html:form action="/materialTypeAction.do?method=addMaterialModel" styleId="addMaterialModel">
  	<input name="act" id="act" type="hidden"/>
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="�������">
  				<input name="modelName" class="inputtext" style="width:215px;" maxlength="20" id="modelName"/>
  		</template:formTr>
  		<template:formTr name="��������">
  				<bean:write name="bean" property="typeName"/>
  				<input type="hidden" value="<bean:write name="bean" property="id"/>" name="typeid"/>
  		</template:formTr>
  		<template:formTr name="��λ">
  					<input name="unit" class="inputtext" style="width:215px;" maxlength="20" id="unit"/>
  		</template:formTr>
  		<template:formTr name="��ע">
  					<textarea name="remark"  id="remark" cols="36" rows="4" style="width:215px"  title="�������510�����֣�1020��Ӣ����ĸ��" class="textarea"></textarea>
  		</template:formTr>
  		 <template:formSubmit>
				      	<td>
                          <html:button property="action" styleClass="button"  onclick="checkAddInfo('add')">�������</html:button>
				      	</td>
				      	<td>
				       	 	<html:submit property="action" styleClass="button" >���</html:submit>
				      	</td>
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
  </body>
</html>
