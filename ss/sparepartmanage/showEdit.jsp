<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.sparepartmanage.beans.SparepartBaseInfoBean;" %>
<html>
  <head>
    <title>showEdit</title>
    <script type="text/javascript">
    	function checkInfo() {
    		var nameEle = document.getElementById('sparePartName');
    		if(nameEle.value.length == 0) {
    			alert("请输入备件名称！");
    			nameEle.focus();
    			return;
    		}
    		var modelEle = document.getElementById('sparePartModel');
    		if(modelEle.value.length == 0) {
    			alert("请输入备件型号！");
    			modelEle.focus();
    			return;
    		}
    		var versionEle = document.getElementById('softwareVersion');
    		if(versionEle.value.length == 0) {
    			alert("请输入软件版本！");
    			versionEle.focus();
    			return;
    		}
    		var factoryEle = document.getElementById('productFactory');
    		if(factoryEle.value.length == 0) {
    			alert("请输入生产厂商！");
    			factoryEle.focus();
    			return;
    		}
    		var remarkEle = document.getElementById('sparePartRemark');
    		if(valCharLength(remarkEle.value) > 512) {
    			alert("备注信息不能超过250个汉字或者512个英文字符！")
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
  			<template:titile value="备件详细信息修改"/>
  			<html:form action="SeparepartBaseInfoAction.do?method=doEdit" styleId="editFormId">
  			<template:formTable namewidth="150" contentwidth="300" th2="填写">
  				<template:formTr name="备件名称">
  					<input type="hidden" name="sparePartId" value="<bean:write name="oneInfo" property="sparePartId"/>"/>
  					<input  type="text" name="sparePartName" id="sparePartName" value="<bean:write name="oneInfo" property="sparePartName"/>" Class="inputtext" style="width:180;" maxlength="20"/>
				</template:formTr>
				
				<template:formTr name="备件型号">
					<input  type="text" name="sparePartModel" id="sparePartModel" value="<bean:write name="oneInfo" property="sparePartModel"/>" Class="inputtext" style="width:180;" maxlength="20"/>
				</template:formTr>
				
				<template:formTr name="软件版本">
					<input  type="text" name="softwareVersion" id="softwareVersion" value="<bean:write name="oneInfo" property="softwareVersion"/>" Class="inputtext" style="width:180;" maxlength="20"/>
				</template:formTr>
				
				<template:formTr name="生产厂家">
					<input  type="text" name="productFactory" id="productFactory" value="<bean:write name="oneInfo" property="productFactory"/>" Class="inputtext" style="width:180;" maxlength="20"/>
				</template:formTr>
				
				<template:formTr name="备件备注">
					<textarea  name="sparePartRemark" id="sparePartRemark" cols="12" rows="5"   title="最多为256个汉字,512个英文字符！" class="textarea"><bean:write name="oneInfo" property="sparePartRemark"/></textarea>
				</template:formTr>
				
				<template:formSubmit>
					<td>
						<html:button property="action" onclick="checkInfo()" styleClass="button">修改</html:button>	
					</td>
					<td>
						<input type="reset" value="重置" class="button">
					</td>
					<td>
						<html:button property="action" styleClass="button" onclick="goBack()">返回</html:button>	
					</td>
				</template:formSubmit>
  			</template:formTable>
  			</html:form>
  		</logic:present>
  </body>
</html>
