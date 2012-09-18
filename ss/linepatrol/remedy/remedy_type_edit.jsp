<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>修改修缮类别</title>
	<script type="text/javascript">
	function checkAddInfo() {
  			var typeName = document.getElementById('typeName');
  			if(typeName.value.length == 0) {
  				alert("请填写类别名称!");
  				typeName.focus();
  				return;
  			}
  			var itemID = document.getElementById('itemID');
  			if(itemID.value.length == 0) {
  				alert("请选择所属项目!");
  				itemID.focus();
  				return;
  			}
  			var price = document.getElementById('price');
  			if(price.value.length == 0) {
  				alert("请填写单价!");
  				price.focus();
  				return;
  			}else{
  				var s = calculateScore(price);
  				if(!s){
  					return ;
  				}
  			}
  			
  			var unit = document.getElementById('unit');
  			if(unit.value.length == 0) {
  				alert("请填写单位!");
  				unit.focus();
  				return;
  			}
  			
  			var remark = document.getElementById('remark');
  			if(valCharLength(remark.value) > 1024) {
  				alert("备注信息不能超过512个汉字或者1024个英文字符！")
              	return;
  			}
  			editRemedyType.submit();
  		}
  	var regx = /^\d{1,12}[\.]{0,1}\d{0,5}$/;
	calculateScore=function(element){
		if(!regx.test(element.value)){
			alert("输入值必须为有效数！");
			element.value="0";
			element.focus();	
			return false;		
		}	
		return true;
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
  	<template:titile value="修改修缮类别"/>
  	<html:form action="/remedyTypeAction.do?method=editRemedyType" styleId="editRemedyType">
  	<template:formTable namewidth="150" contentwidth="300">
  		<html:hidden property="tid" name="bean" />
  		<template:formTr name="类别名称">
  				<html:text property="typeName" styleClass="inputtext" name="bean" style="width:215;" maxlength="20" styleId="typeName"/>
  		</template:formTr>
  		<template:formTr name="所属项目">
  				<html:select property="itemID" name="bean" styleClass="inputtext"  style="width:215px" styleId="itemID">
         				<html:options collection="items"  property="id" labelProperty="itemname"/>
         		</html:select>
  		</template:formTr>
  		<template:formTr name="单价">
  				<html:text property="price" styleClass="inputtext" name="bean" style="width:215;" maxlength="20" styleId="price"/>
  		</template:formTr>
  		<template:formTr name="单位">
  				<html:text property="unit" styleClass="inputtext" name="bean" style="width:215;" maxlength="20" styleId="unit"/>
  		</template:formTr>
  		<template:formTr name="备注">
  				<html:textarea  property="remark" name="bean" styleId="remark" cols="36" rows="4"  title="最多输入512个汉字，1024个英文字母！" styleClass="textarea"></html:textarea>
  		</template:formTr>
  		 <template:formSubmit>
				      	<td>
                          <html:button property="action" styleClass="button"  onclick="checkAddInfo()">修改</html:button>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >重置</html:reset>
				      	</td>
				      	<td>
				       	 	 <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >
				      	</td>
			</template:formSubmit>
		</template:formTable>
  	</html:form>
  </body>
</html>
