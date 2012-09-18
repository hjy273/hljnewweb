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
<template:titile value="查看合同信息" />
<div id="errorcno" style="display:none"></div>
<logic:present name="concordatBean">
<html:form onsubmit="return isValidForm();" method="Post" action="/concordat.do?method=editConcordat">
	<template:formTable contentwidth="300" namewidth="200">
		<template:formTr name="合同编号">
			<html:hidden property="id" styleId="id"/>
			<bean:write name="concordatBean" property="cno"/>
		</template:formTr>
		<template:formTr name="维护区域">
			<bean:write name="concordatBean" property="patrolregion"/>
		</template:formTr>
		<template:formTr name="合同名称">
			<bean:write name="concordatBean" property="cname"/>
		</template:formTr>
		<template:formTr name="甲方">
			<bean:write name="concordatBean" property="deptname"/>
			<html:hidden property="deptid"/>
		</template:formTr>
		<template:formTr name="乙方代维公司">
			<bean:write name="concordatBean" property="contractorname"/>
		</template:formTr>
		<template:formTr name="合同类型">
			<bean:write name="concordatBean" property="ctypename"/>
		</template:formTr>
		<template:formTr name="签订日期">
			<bean:write name="concordatBean" property="bookdate"/>
		</template:formTr>
		<template:formTr name="合同有效期">
			<bean:write name="concordatBean" property="perioddate"/>
		</template:formTr>
		<template:formTr name="行政区域">
			<bean:write name="concordatBean" property="regionname"/>
			<html:hidden property="regionid"/>
		</template:formTr>
        <template:formTr name="附件" >
			<bean:define name="concordatBean" property="id" id="cid" />
			<apptag:upload state="look" entityId="${cid}" entityType="CONCORDATINFO"></apptag:upload>
        </template:formTr>		
		<template:formSubmit>
			<td colspan="3">
			<html:button property="action" styleClass="button" onclick="addGoBack()">返回</html:button>
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
</logic:present>