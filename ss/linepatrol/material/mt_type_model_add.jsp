<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>增加材料规格</title>
	<script type="text/javascript">
	function checkAddInfo(act) {
  			var modelName = document.getElementById('modelName');
  			if(modelName.value.length == 0) {
  				alert("请填写规格名称!");
  				modelName.focus();
  				return;
  			}
  			var typeID = document.getElementById('typeID');
  			if(typeID.value.length == 0) {
  				alert("请选择所属类型!");
  				typeID.focus();
  				return;
  			}
  			
  			var unit = document.getElementById('unit');
  			if(unit.value.length == 0) {
  				alert("请填写单位!");
  				unit.focus();
  				return;
  			}
  			
  			var remark = document.getElementById('remark');
  			if(valCharLength(remark.value) > 1020) {
  				alert("备注信息不能超过510个汉字或者1020个英文字符！")
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
  	<template:titile value="增加材料规格"/>
  	<html:form action="/materialTypeAction.do?method=addMaterialModel" styleId="addMaterialModel">
  	<input name="act" id="act" type="hidden"/>
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="规格名称">
  				<input name="modelName" class="inputtext" style="width:215px;" maxlength="20" id="modelName"/>
  		</template:formTr>
  		<template:formTr name="所属类型">
  				<bean:write name="bean" property="typeName"/>
  				<input type="hidden" value="<bean:write name="bean" property="id"/>" name="typeid"/>
  		</template:formTr>
  		<template:formTr name="单位">
  					<input name="unit" class="inputtext" style="width:215px;" maxlength="20" id="unit"/>
  		</template:formTr>
  		<template:formTr name="备注">
  					<textarea name="remark"  id="remark" cols="36" rows="4" style="width:215px"  title="最多输入510个汉字，1020个英文字母！" class="textarea"></textarea>
  		</template:formTr>
  		 <template:formSubmit>
				      	<td>
                          <html:button property="action" styleClass="button"  onclick="checkAddInfo('add')">继续添加</html:button>
				      	</td>
				      	<td>
				       	 	<html:submit property="action" styleClass="button" >完成</html:submit>
				      	</td>
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
  </body>
</html>
