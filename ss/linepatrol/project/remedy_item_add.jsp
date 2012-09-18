<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>����������Ŀ</title>
		<script type="text/javascript">
	function checkAddInfo(value) {
  			var itemName = document.getElementById('itemName');
  			var addForm=document.forms[0];
  			addForm.continue_add_type.value=value;
  			if(itemName.value.length == 0) {
  				alert("����д��Ŀ����!");
  				itemName.focus();
  				return;
  			}
  			var regionID = document.getElementById('regionID');
  			if(regionID.value.length == 0) {
  				alert("��ѡ����������!");
  				regionID.focus();
  				return;
  			}
  			var remark = document.getElementById('remark');
  			if(valCharLength(remark.value) > 1024) {
  				alert("��ע��Ϣ���ܳ���512�����ֻ���1024��Ӣ���ַ���")
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
		<template:titile value="����������" />
		<html:form action="/project/remedy_item.do?method=addRemedyItem"
			styleId="addRemedyItem">
			<template:formTable namewidth="150" contentwidth="300">
				<template:formTr name="��Ŀ����">
					<html:text property="itemName" styleClass="inputtext"
						style="width:215;" maxlength="20"
						styleId="itemName" />
				</template:formTr>
				<template:formTr name="��������">
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
				<template:formTr name="��ע">
					<html:textarea property="remark"
						styleId="remark" cols="36" rows="4" title="�������512�����֣�1024��Ӣ����ĸ��"
						styleClass="textarea"></html:textarea>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="checkAddInfo('1')">����</html:button>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="checkAddInfo('0')">�������</html:button>
					</td>
					<td>
						<html:reset property="action" styleClass="button">����</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
