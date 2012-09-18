<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>修改修缮项目</title>
	<script type="text/javascript">
	function checkAddInfo() {
  			var itemName = document.getElementById('itemName');
  			if(itemName.value.length == 0) {
  				alert("请填写项目名称!");
  				itemName.focus();
  				return;
  			}
  			var regionID = document.getElementById('regionID');
  			if(regionID.value.length == 0) {
  				alert("请选择所属区域!");
  				regionID.focus();
  				return;
  			}
  			var remark = document.getElementById('remark');
  			if(valCharLength(remark.value) > 1024) {
  				alert("备注信息不能超过512个汉字或者1024个英文字符！")
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
  	<template:titile value="修改修缮项"/>
  	<html:form action="/remedyItemAction.do?method=editRemedyItem" styleId="addRemedyItem">
  	<template:formTable namewidth="150" contentwidth="300">
  	<html:hidden property="tid" name="bean" />
  		<template:formTr name="项目名称">
  				<html:text property="itemName" styleClass="inputtext" name="bean" style="width:215;" maxlength="20" styleId="itemName"/>
  		</template:formTr>
  		<template:formTr name="所属区域">
  				<html:select property="regionID" name="bean" style="width:215px;" styleClass="inputtext">
						<html:options collection="regions"  property="regionid" labelProperty="regionname"/>
				</html:select>	
  		</template:formTr>
  		<template:formTr name="备注">
  				<html:textarea  name="bean" property="remark" styleClass="textarea" cols="36" rows="4"  title="最多输入512个汉字，1024个英文字母！" ><bean:write name="bean" property="remark"/></html:textarea>
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
