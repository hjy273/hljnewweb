<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>�޸Ĳ��Ϲ��</title>
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
  			processBar(editMaterialModel);
  			editMaterialModel.submit();
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
		 
		 function  goBack(){
			history.back();
		}
	</script>
  </head>
  
  <body><br>
  	<template:titile value="�޸Ĳ��Ϲ��"/>
  	<html:form action="/materialModelAction.do?method=editMaterialModel" styleId="editMaterialModel">
  	<template:formTable namewidth="150" contentwidth="300">
  	<html:hidden property="tid" name="bean" />
  		<template:formTr name="�������">
  				<html:text property="modelName" styleClass="inputtext" name="bean" style="width:215px;" maxlength="20" styleId="modelName"/>
  		</template:formTr>
  		<template:formTr name="��������">
  				<html:select property="typeID" name="bean" style="width:215px;" styleId="typeID" styleClass="inputtext">
						<html:options collection="lists"  property="id" labelProperty="typename"/>
				</html:select>	
  		</template:formTr>
  		<template:formTr name="��λ">
  				<html:text property="unit" styleClass="inputtext" name="bean" style="width:215px;" maxlength="20" styleId="unit"/>
  		</template:formTr>
  		<template:formTr name="��ע">
  				<html:textarea  name="bean" property="remark" styleId="remark" styleClass="textarea" style="width:215px" cols="36" rows="4"  title="�������510�����֣�1020��Ӣ����ĸ��" ><bean:write name="bean" property="remark"/></html:textarea>
  		</template:formTr>
  		 <template:formSubmit>
				      	<td>
                          <html:button property="action" styleClass="button"  onclick="checkAddInfo()">�޸�</html:button>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >����</html:reset>
				      	</td>
				      	<td>
				       	 <!--  <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >
				      	-->
				      	<input type="button" class="button" onclick="history.back()" value="����"/>
				      	</td>
				      	
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
  </body>
</html>
