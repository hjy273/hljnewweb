<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>修改材料类型</title>
	<script type="text/javascript">
	function checkAddInfo() {
  			var typeName = document.getElementById('typeName');
  			if(typeName.value.length == 0) {
  				alert("请填写类型名称!");
  				typeName.focus();
  				return;
  			}
  			var regionID = document.getElementById('regionID');
  			if(regionID.value.length == 0) {
  				alert("请选择所属区域!");
  				regionID.focus();
  				return;
  			}
  			var remark = document.getElementById('remark');
  			if(valCharLength(remark.value) > 1020) {
  				alert("备注信息不能超过510个汉字或者1020个英文字符！");
              	return;
  			}
  			processBar(editMaterialType);
  			editMaterialType.submit();
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
  	<template:titile value="修改材料类型"/>
  	<html:form action="/materialTypeAction.do?method=editMaterialType" styleId="editMaterialType">
  	<template:formTable namewidth="150" contentwidth="300">
  	<input type="hidden" name="tid" value="<bean:write property="id" name="bean" />" >
  	
  		<input type="hidden" name="state" value="<bean:write property="state" name="bean" />" >
  		<template:formTr name="类型名称">
  				<html:text property="typeName" styleClass="inputtext" name="bean" style="width:215px;" maxlength="20" styleId="typeName"/>
  		</template:formTr>
  		<template:formTr name="所属区域">
  				<html:select property="regionID" styleId="regionID" name="bean" style="width:215px;" styleClass="inputtext">
						<html:options collection="regions"  property="regionid" labelProperty="regionname"/>
				</html:select>	
  		</template:formTr>
  		<template:formTr name="备注">
  				<html:textarea  name="bean" property="remark" styleId="remark" styleClass="textarea" cols="36" rows="4" style="width:215px" title="最多输入510个汉字，1020个英文字母！" ><bean:write name="bean" property="remark"/></html:textarea>
  		</template:formTr>
  		 <template:formSubmit>
				      	<td>
                          <html:button property="action" styleClass="button"  onclick="checkAddInfo()">修改</html:button>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >重置</html:reset>
				      	</td>
				      	<td>
				       	<!--  <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >-->
				       	<input type="button" class="button" onclick="history.back()" value="返回"/>
				      	</td>
				      	
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
  </body>
</html>
