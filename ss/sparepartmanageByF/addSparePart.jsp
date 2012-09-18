<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.sparepartmanage.beans.SparepartBaseInfoBean;" %>

<html>
  <head>
  	<script type="text/javascript">
  		function checkAddInfo() {
  			var sparePartNameEle = document.getElementById('sparePartName');
  			if(sparePartNameEle.value.length == 0) {
  				alert("�����뱸������!");
  				sparePartNameEle.focus();
  				return;
  			}
  			var sparePartModelEle = document.getElementById('sparePartModel');
  			if(sparePartModelEle.value.length == 0) {
  				alert("�����뱸���ͺ�!");
  				sparePartModelEle.focus();
  				return;
  			}
  			var softwareVersionEle = document.getElementById('softwareVersion');
  			if(softwareVersionEle.value.length == 0) {
  				alert("����������汾!");
  				softwareVersionEle.focus();
  				return;
  			}
  			var productFactoryEle = document.getElementById('productFactory');
  			if(productFactoryEle.value.length == 0) {
  				alert("��������������!");
  				productFactoryEle.focus();
  				return;
  			}
  			var sparePartRemarkEle = document.getElementById('sparePartRemark');
  			if(valCharLength(sparePartRemarkEle.value) > 512) {
  				alert("��ע��Ϣ���ܳ���256�����ֻ���512��Ӣ���ַ���")
              	return;
  			}
  			addSeparepartbaseFormId.submit();
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
  
  <body>
  		<br>
  		<template:titile value="��ӱ���������Ϣ"/>
  		<html:form action="SeparepartBaseInfoAction.do?method=addSeparepart" styleId="addSeparepartbaseFormId">
  			<template:formTable namewidth="150" contentwidth="300">
  				<template:formTr name="��������">
			       	<html:text property="sparePartName" styleClass="inputtext" style="width:245;" maxlength="20" styleId="sparePartName"/>
			    </template:formTr>
			    <template:formTr name="�����ͺ�">
			       	<html:text property="sparePartModel" styleClass="inputtext" style="width:245;" maxlength="20"/>
			    </template:formTr>
			    <template:formTr name="����汾">
			        <html:text property="softwareVersion" styleClass="inputtext" style="width:245;" maxlength="20"/>
			    </template:formTr>
			    <template:formTr name="��������">
			       	<html:text property="productFactory"   styleClass="inputtext" style="width:245;" maxlength="20"/>
			    </template:formTr>
			    <template:formTr name="��ע��Ϣ">
			       	<textarea  name="sparePartRemark" cols="36" rows="4"   title="�������256�����֣�512��Ӣ����ĸ��" class="textarea"></textarea>
			    </template:formTr>
  			</template:formTable>
  			  <template:formSubmit>
				      	<td>
                          <html:button property="action" styleClass="button"  onclick="checkAddInfo()">����</html:button>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >����	</html:reset>
				      	</td>
			</template:formSubmit>
  		</html:form>
  </body>
</html>
