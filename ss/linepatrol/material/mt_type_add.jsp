<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>增加材料类型</title>
		<script type="text/javascript">
		function checkAddInfo() {
  			var typeName = document.getElementById('typeName');
  			if(typeName.value.length == 0) {
  				alert("请填写类型名称!");
  				typeName.focus();
  				return false;
  			}
  			var regionID = document.getElementById('regionID');
  			if(regionID.value.length == 0) {
  				alert("请选择所属区域!");
  				regionID.focus();
  				return false;
  			}
  			var remark = document.getElementById('remark');
  			if(valCharLength(remark.value) > 1020) {
  				alert("备注信息不能超过510个汉字或者1020个英文字符！")
              	return false;
  			}
  			processBar(addMaterialType);
  			return true;
  			//document.getElementById('addMaterialType').submit();
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
		<template:titile value="增加材料类型" />
		<html:form action="/materialTypeAction.do?method=addMaterialType"
			styleId="addMaterialType" onsubmit="return checkAddInfo()">
			<template:formTable namewidth="150" contentwidth="300">
				<template:formTr name="类型名称">
					<html:text property="typeName" styleClass="inputtext"
						name="materialTypeBean" style="width:215px;" maxlength="20"
						styleId="typeName" />
				</template:formTr>
				<template:formTr name="所属区域">
					<select name="regionID" class="inputtext" style="width: 215px"
						id="regionID">
						<logic:present scope="request" name="regions">
							<logic:iterate id="r" name="regions">
								<option value="<bean:write name="r" property="regionid" />">
									<bean:write name="r" property="regionname" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
				</template:formTr>
				<template:formTr name="备注">
					<html:textarea property="remark" name="materialTypeBean"
						styleId="remark" cols="20" rows="4" title="最多输入510个汉字，1020个英文字母！"
						styleClass="textarea" style="width:215px"></html:textarea>
				</template:formTr>
				<template:formSubmit>
					<td>
					<!--  	<html:button property="action" styleClass="button"
							onclick="checkAddInfo()">增加</html:button>-->
							<html:submit styleClass="button">增加</html:submit>
					</td>
					<td>
						<html:reset property="action" styleClass="button">重置</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
