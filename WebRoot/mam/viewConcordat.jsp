<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@include file="/mam/concordatJS.jsp"%>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
<script language="JavaScript" type="text/javascript">
var addGoBack = function(){
	var url = "${ctx}/concordat.do?method=queryConcordats";
	self.location.replace(url);
}
</script>
<template:titile value="�鿴��ͬ��Ϣ" />
<div id="errorcno" style="display:none"></div>
<logic:present name="concordatBean">
<html:form onsubmit="return isValidForm();" method="Post" action="/concordat.do?method=editConcordat">
	<template:formTable contentwidth="300" namewidth="200">
		<template:formTr name="��ͬ���">
			<html:hidden property="id" styleId="id"/>
			<bean:write name="concordatBean" property="cno"/>
		</template:formTr>
		<template:formTr name="ά������">
			<bean:write name="concordatBean" property="patrolregion"/>
		</template:formTr>
		<template:formTr name="��ͬ����">
			<bean:write name="concordatBean" property="cname"/>
		</template:formTr>
		<template:formTr name="�׷�">
			<bean:write name="concordatBean" property="deptname"/>
			<html:hidden property="deptid"/>
		</template:formTr>
		<template:formTr name="�ҷ���ά��˾">
			<bean:write name="concordatBean" property="contractorname"/>
		</template:formTr>
		<template:formTr name="��ͬ����">
			<bean:write name="concordatBean" property="ctypename"/>
		</template:formTr>
		<template:formTr name="ǩ������">
			<bean:write name="concordatBean" property="bookdate"/>
		</template:formTr>
		<template:formTr name="��ͬ��Ч��">
			<bean:write name="concordatBean" property="perioddate"/>
		</template:formTr>
		<template:formTr name="��������">
			<bean:write name="concordatBean" property="regionname"/>
			<html:hidden property="regionid"/>
		</template:formTr>
        <template:formTr name="����" >
			<bean:define name="concordatBean" property="id" id="cid" />
			<apptag:upload state="look" entityId="${cid}" entityType="CONCORDATINFO"></apptag:upload>
        </template:formTr>		
		<template:formSubmit>
			<td colspan="3">
			<html:button property="action" styleClass="button" onclick="addGoBack()">����</html:button>
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
</logic:present>