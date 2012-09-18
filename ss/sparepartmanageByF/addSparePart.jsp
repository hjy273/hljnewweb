<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.sparepartmanage.beans.SparepartBaseInfoBean;" %>

<html>
  <head>
  	<script type="text/javascript">
  		function checkAddInfo() {
  			var sparePartNameEle = document.getElementById('sparePartName');
  			if(sparePartNameEle.value.length == 0) {
  				alert("请输入备件名称!");
  				sparePartNameEle.focus();
  				return;
  			}
  			var sparePartModelEle = document.getElementById('sparePartModel');
  			if(sparePartModelEle.value.length == 0) {
  				alert("请输入备件型号!");
  				sparePartModelEle.focus();
  				return;
  			}
  			var softwareVersionEle = document.getElementById('softwareVersion');
  			if(softwareVersionEle.value.length == 0) {
  				alert("请输入软件版本!");
  				softwareVersionEle.focus();
  				return;
  			}
  			var productFactoryEle = document.getElementById('productFactory');
  			if(productFactoryEle.value.length == 0) {
  				alert("请输入生产厂家!");
  				productFactoryEle.focus();
  				return;
  			}
  			var sparePartRemarkEle = document.getElementById('sparePartRemark');
  			if(valCharLength(sparePartRemarkEle.value) > 512) {
  				alert("备注信息不能超过256个汉字或者512个英文字符！")
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
  		<template:titile value="添加备件基本信息"/>
  		<html:form action="SeparepartBaseInfoAction.do?method=addSeparepart" styleId="addSeparepartbaseFormId">
  			<template:formTable namewidth="150" contentwidth="300">
  				<template:formTr name="备件名称">
			       	<html:text property="sparePartName" styleClass="inputtext" style="width:245;" maxlength="20" styleId="sparePartName"/>
			    </template:formTr>
			    <template:formTr name="备件型号">
			       	<html:text property="sparePartModel" styleClass="inputtext" style="width:245;" maxlength="20"/>
			    </template:formTr>
			    <template:formTr name="软件版本">
			        <html:text property="softwareVersion" styleClass="inputtext" style="width:245;" maxlength="20"/>
			    </template:formTr>
			    <template:formTr name="生产厂家">
			       	<html:text property="productFactory"   styleClass="inputtext" style="width:245;" maxlength="20"/>
			    </template:formTr>
			    <template:formTr name="备注信息">
			       	<textarea  name="sparePartRemark" cols="36" rows="4"   title="最多输入256个汉字，512个英文字母！" class="textarea"></textarea>
			    </template:formTr>
  			</template:formTable>
  			  <template:formSubmit>
				      	<td>
                          <html:button property="action" styleClass="button"  onclick="checkAddInfo()">增加</html:button>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >重置	</html:reset>
				      	</td>
			</template:formSubmit>
  		</html:form>
  </body>
</html>
