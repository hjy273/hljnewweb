<%@ page language="java" contentType="text/html; charset=GBK"%><%@ include file="/common/header.jsp"%>
<SCRIPT language=javascript src="${ctx}/js/prototype.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/js/parsexml.js" type=""></SCRIPT>

<body>
<template:titile value="��ѯѲ��Ա��Ϣ" />
	<html:form method="Post"
		action="/patrolSonAction.do?method=queryPatrolSon">
		<template:formTable contentwidth="300" namewidth="150">
            <%@include file="/common/linkpatrol.jsp" %>
			<template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��">
				<html:select property="sex" styleClass="inputtext" style="width:225">
					<html:option value="">����</html:option>
					<html:option value="1">��</html:option>
					<html:option value="2">Ů</html:option>
				</html:select>
			</template:formTr>
			<template:formTr name="����״̬" >
				<html:select property="jobState" styleClass="inputtext"
					style="width:225">
					<html:option value="">����</html:option>
					<html:option value="1">�ڸ�</html:option>
					<html:option value="2">�ݼ�</html:option>
					<html:option value="3">��ְ</html:option>
				</html:select>
			</template:formTr>


			<template:formSubmit>
				<td>
					<html:submit styleClass="button">��ѯ</html:submit>
				</td>
				<td>
					<html:reset styleClass="button">ȡ��</html:reset>
				</td>
			</template:formSubmit>
		</template:formTable>
	</html:form>
</body>
