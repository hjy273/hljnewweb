<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>�޸�������Ŀ</title>
	<script type="text/javascript">
	function checkAddInfo() {
  			var itemName = document.getElementById('itemName');
  			if(itemName.value.length == 0) {
  				alert("����д��Ŀ����!");
  				itemName.focus();
  				return;
  			}
  			var regionID = document.getElementById('regionID');
  			if(regionID.value.length == 0) {
  				alert("��ѡ����������!");
  				regionID.focus();
  				return;
  			}
  			var remark = document.getElementById('remark');
  			if(valCharLength(remark.value) > 1024) {
  				alert("��ע��Ϣ���ܳ���512�����ֻ���1024��Ӣ���ַ���")
              	return;
  			}
  			addRemedyItem.submit();
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
  	<template:titile value="�޸�������"/>
  	<html:form action="/remedyItemAction.do?method=editRemedyItem" styleId="addRemedyItem">
  	<template:formTable namewidth="150" contentwidth="300">
  	<html:hidden property="tid" name="bean" />
  		<template:formTr name="��Ŀ����">
  				<html:text property="itemName" styleClass="inputtext" name="bean" style="width:215;" maxlength="20" styleId="itemName"/>
  		</template:formTr>
  		<template:formTr name="��������">
  				<html:select property="regionID" name="bean" style="width:215px;" styleClass="inputtext">
						<html:options collection="regions"  property="regionid" labelProperty="regionname"/>
				</html:select>	
  		</template:formTr>
  		<template:formTr name="��ע">
  				<html:textarea  name="bean" property="remark" styleClass="textarea" cols="36" rows="4"  title="�������512�����֣�1024��Ӣ����ĸ��" ><bean:write name="bean" property="remark"/></html:textarea>
  		</template:formTr>
  		 <template:formSubmit>
				      	<td>
                          <html:button property="action" styleClass="button"  onclick="checkAddInfo()">�޸�</html:button>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >����</html:reset>
				      	</td>
				      	<td>
				       	 	 <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >
				      	</td>
				      	
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
  </body>
</html>
