<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>增加修缮项目</title>
		<script type="text/javascript">
	function checkAddInfo(value) {
  			var itemName = document.getElementById('itemName');
  			var addForm=document.forms[0];
  			addForm.continue_add_type.value=value;
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
	</script>
	</head>

	<body>
		<br>
		<template:titile value="增加修缮项" />
		<html:form action="/project/remedy_item.do?method=addRemedyItem"
			styleId="addRemedyItem">
			<template:formTable namewidth="150" contentwidth="300">
				<template:formTr name="项目名称">
					<html:text property="itemName" styleClass="inputtext"
						style="width:215;" maxlength="20"
						styleId="itemName" />
				</template:formTr>
				<template:formTr name="所属区域">
					<input name="continue_add_type" type="hidden" value="1" />
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
					<html:textarea property="remark"
						styleId="remark" cols="36" rows="4" title="最多输入512个汉字，1024个英文字母！"
						styleClass="textarea"></html:textarea>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="checkAddInfo('1')">增加</html:button>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="checkAddInfo('0')">完成增加</html:button>
					</td>
					<td>
						<html:reset property="action" styleClass="button">重置</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
