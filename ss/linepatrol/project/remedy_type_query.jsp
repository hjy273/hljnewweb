<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>��ѯ�������</title>
	</head>
	<body>
		<br>
		<template:titile value="��ѯ�������" />
		<html:form action="/project/remedy_type.do?method=getRemedyTypes"
			styleId="addRemedyItem">
			<template:formTable namewidth="150" contentwidth="300">
				<template:formTr name="�������">
					<html:text property="typeName" styleClass="inputtext"
						style="width:215;" maxlength="20"
						styleId="itemName" />
				</template:formTr>
				<template:formTr name="������Ŀ">
					<select name="itemID" class="inputtext" style="width: 215px"
						id="regionID">
						<option value="-1">
							����
						</option>
						<logic:present scope="request" name="items">
							<logic:iterate id="item" name="items">
								<option value="<bean:write name="item" property="id" />">
									<bean:write name="item" property="itemname" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">��ѯ</html:submit>
					</td>
					<td>
						<html:reset property="action" styleClass="button">����</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
