<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@include file="/mam/concordatJS.jsp"%>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
<style>
<!--
#errorcno{
	width:220px;
	margin-left:40%;
	background-color: #FDFDDD;
	border: 1px solid #FC6;
	z-index: 1000px;
	position:absolute;
}
-->
</style>
<script language="JavaScript" type="text/javascript">
var addGoBack = function(){
	var url = "${ctx}/concordat.do?method=queryConcordats";
	self.location.replace(url);
}
</script>
<template:titile value="�޸ĺ�ͬ��Ϣ" />
<div id="errorcno" style="display:none"></div>
<input type="hidden" id='notUnique' value='0'/>
<logic:present name="concordatBean">
<bean:define id="concordatBean" name="concordatBean" type="com.cabletech.mam.beans.ConcordatBean"></bean:define>
<html:form onsubmit="return isValidForm();" method="Post" action="/concordat.do?method=editConcordat">
	<template:formTable contentwidth="300" namewidth="200">
		<template:formTr name="��ͬ���">
			<html:hidden property="id" styleId="id"/>
			<input name="cno" value="<%=concordatBean.getCno() %>" onkeyup="validCno(this);" onpaste="return false;" autocomplete="off" title="����Ψһ" class="inputtext" style="width: 200" maxlength="8" /><font color="red">*</font>
		</template:formTr>
		<template:formTr name="ά������">
			<html:text property="patrolregion" name="concordatBean" styleClass="inputtext" style="width:200" maxlength="100" /><font color="red">*</font>
		</template:formTr>
		<template:formTr name="��ͬ����">
			<html:text property="cname" name="concordatBean" styleClass="inputtext" style="width:200" maxlength="50" /><font color="red">*</font>
		</template:formTr>
		<template:formTr name="�׷�">
			<html:text property="deptname" name="concordatBean" readonly="true" disabled="true" styleClass="inputtext" style="width:200" maxlength="50" />
			<html:hidden property="deptid"/>
		</template:formTr>
		<template:formTr name="�ҷ���ά��˾">
			<html:select property="contractorid" name="concordatBean" styleClass="inputtext" style="width: 200px">
				<logic:present name="contractor_list">
					<logic:iterate id="oneContractor" name="contractor_list">
						<logic:equal name="oneContractor" property="contractorid" value="<%=concordatBean.getContractorid() %>">
							<option value="<bean:write name="oneContractor" property="contractorid"/>" selected="selected">
								<bean:write name="oneContractor" property="contractorname" />
							</option>			
						</logic:equal>
						<logic:notEqual name="oneContractor" property="contractorid" value="<%=concordatBean.getContractorid() %>">
							<option value="<bean:write name="oneContractor" property="contractorid"/>">
								<bean:write name="oneContractor" property="contractorname" />
							</option>
						</logic:notEqual>
					</logic:iterate>
				</logic:present>
			</html:select>
		</template:formTr>
		<template:formTr name="��ͬ����">
			<html:select property="ctypeid" name="concordatBean" styleClass="inputtext" style="width: 200px">
				<logic:present name="ctype_list">
					<logic:iterate id="onectype" name="ctype_list">
						<logic:equal name="onectype" property="key" value="<%=concordatBean.getCtypeid() %>">
							<option value="<bean:write name="onectype" property="key"/>" selected="selected">
								<bean:write name="onectype" property="value" />
							</option>							
						</logic:equal>
						<logic:notEqual name="onectype" property="key" value="<%=concordatBean.getCtypeid() %>">
							<option value="<bean:write name="onectype" property="key"/>">
								<bean:write name="onectype" property="value" />
							</option>
						</logic:notEqual>
					</logic:iterate>
				</logic:present>
			</html:select>
		</template:formTr>
		<template:formTr name="ǩ������">
			<html:text property="bookdate" name="concordatBean" styleClass="inputtext" readonly="true" style="width:200" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />		
			<font color="red">*</font>
		</template:formTr>
		<template:formTr name="��ͬ��Ч��">
			<html:text property="perioddate" name="concordatBean" styleClass="inputtext" readonly="true" style="width:200" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />		
			<font color="red">*</font>
		</template:formTr>
		<template:formTr name="��������">
			<html:text property="regionname" name="concordatBean" readonly="true" disabled="true" styleClass="inputtext" style="width:200" maxlength="100" />
			<html:hidden property="regionid"/>
		</template:formTr>
        <template:formTr name="����" >
			<bean:define name="concordatBean" property="id" id="cid" />
			<apptag:upload state="edit" entityId="${cid}" entityType="CONCORDATINFO"></apptag:upload>
        </template:formTr>		
		<template:formSubmit>
			<td colspan="3">
			<html:submit styleClass="button">�޸�</html:submit>
			&nbsp;&nbsp;
			<html:reset styleClass="button">ȡ��</html:reset>
			&nbsp;&nbsp;
			<html:button property="action" styleClass="button" onclick="addGoBack()">����</html:button>
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
</logic:present>