<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.sparepartmanage.beans.SparepartBaseInfoBean;" %>
<html>
  <head>
    <title>showEdit</title>
    <script type="text/javascript">
    	function checkInfo() {
    		var nameEle = document.getElementById('sparePartName');
    		if(nameEle.value.length == 0) {
    			alert("�����뱸�����ƣ�");
    			nameEle.focus();
    			return;
    		}
    		var modelEle = document.getElementById('sparePartModel');
    		if(modelEle.value.length == 0) {
    			alert("�����뱸���ͺţ�");
    			modelEle.focus();
    			return;
    		}
    		var versionEle = document.getElementById('softwareVersion');
    		if(versionEle.value.length == 0) {
    			alert("����������汾��");
    			versionEle.focus();
    			return;
    		}
    		var factoryEle = document.getElementById('productFactory');
    		if(factoryEle.value.length == 0) {
    			alert("�������������̣�");
    			factoryEle.focus();
    			return;
    		}
    		var remarkEle = document.getElementById('sparePartRemark');
    		if(valCharLength(remarkEle.value) > 512) {
    			alert("��ע��Ϣ���ܳ���250�����ֻ���512��Ӣ���ַ���")
              	return;
    		}
    		editFormId.submit();
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
		    
		function goBack() {
    		var url="SeparepartBaseInfoAction.do?method=showQueryAfterMod";
    		window.location.href=url;
    	}
    </script>
  </head>
  
  <body>
  		<BR/>
  		<logic:present name="oneInfo" scope="request">
  			<template:titile value="������ϸ��Ϣ�޸�"/>
  			<html:form action="SeparepartBaseInfoAction.do?method=doEdit" styleId="editFormId">
  			<template:formTable namewidth="150" contentwidth="300" th2="��д">
  				<template:formTr name="��������">
  					<input type="hidden" name="sparePartId" value="<bean:write name="oneInfo" property="sparePartId"/>"/>
  					<input  type="text" name="sparePartName" id="sparePartName" value="<bean:write name="oneInfo" property="sparePartName"/>" Class="inputtext" style="width:180;" maxlength="20"/>
				</template:formTr>
				
				<template:formTr name="�����ͺ�">
					<input  type="text" name="sparePartModel" id="sparePartModel" value="<bean:write name="oneInfo" property="sparePartModel"/>" Class="inputtext" style="width:180;" maxlength="20"/>
				</template:formTr>
				
				<template:formTr name="����汾">
					<input  type="text" name="softwareVersion" id="softwareVersion" value="<bean:write name="oneInfo" property="softwareVersion"/>" Class="inputtext" style="width:180;" maxlength="20"/>
				</template:formTr>
				
				<template:formTr name="��������">
					<input  type="text" name="productFactory" id="productFactory" value="<bean:write name="oneInfo" property="productFactory"/>" Class="inputtext" style="width:180;" maxlength="20"/>
				</template:formTr>
				
				<template:formTr name="������ע">
					<textarea  name="sparePartRemark" id="sparePartRemark" cols="12" rows="5"   title="���Ϊ256������,512��Ӣ���ַ���" class="textarea"><bean:write name="oneInfo" property="sparePartRemark"/></textarea>
				</template:formTr>
				
				<template:formSubmit>
					<td>
						<html:button property="action" onclick="checkInfo()" styleClass="button">�޸�</html:button>	
					</td>
					<td>
						<input type="reset" value="����" class="button">
					</td>
					<td>
						<html:button property="action" styleClass="button" onclick="goBack()">����</html:button>	
					</td>
				</template:formSubmit>
  			</template:formTable>
  			</html:form>
  		</logic:present>
  </body>
</html>
