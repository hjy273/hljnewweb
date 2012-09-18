<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@include file="/mam/concordatJS.jsp"%>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
<%
	String deptname = (String)request.getAttribute("deptname");
	String deptid = (String)request.getAttribute("deptid");
	java.util.Map map = (java.util.Map)request.getAttribute("regionmap");
	String regionname = map.get("regionname").toString();
	String regionid = map.get("regionid").toString();
%>
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
<template:titile value="���Ӻ�ͬ��Ϣ" />
<div id="errorcno" style="display:none"></div>
<input type="hidden" id='notUnique' value='0'/>
<html:form onsubmit="return isValidForm();" method="Post" action="/concordat.do?method=addConcordat">
	<template:formTable contentwidth="300" namewidth="200">
		<template:formTr name="��ͬ���">
			<input name="cno" onkeyup="validCno(this);" onpaste="return false;" autocomplete="off" title="����Ψһ" class="inputtext" style="width: 200" maxlength="8" /><font color="red">*</font>
		</template:formTr>
		<template:formTr name="ά������">
			<html:text property="patrolregion" styleClass="inputtext" style="width:200" maxlength="100" /><font color="red">*</font>
		</template:formTr>
		<template:formTr name="��ͬ����">
			<html:text property="cname" styleClass="inputtext" style="width:200" maxlength="50" /><font color="red">*</font>
		</template:formTr>
		<template:formTr name="�׷�">
			<html:text property="deptname" value="<%=deptname %>" readonly="true" disabled="true" styleClass="inputtext" style="width:200" maxlength="50" />
			<input type="hidden" name="deptid" value="<%=deptid %>"/>
		</template:formTr>
		<template:formTr name="�ҷ���ά��˾">
			<select name="contractorid" class="inputtext" style="width: 200px">
				<logic:present name="contractor_list">
					<logic:iterate id="oneContractor" name="contractor_list">
						<option value="<bean:write name="oneContractor" property="contractorid"/>">
							<bean:write name="oneContractor" property="contractorname" />
						</option>
					</logic:iterate>
				</logic:present>
			</select>
		</template:formTr>
		<template:formTr name="��ͬ����">
			<select name="ctypeid" class="inputtext" style="width: 200px">
				<logic:present name="ctype_list">
					<logic:iterate id="onectype" name="ctype_list">
						<option value="<bean:write name="onectype" property="key"/>">
							<bean:write name="onectype" property="value" />
						</option>
					</logic:iterate>
				</logic:present>
			</select>
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
			<html:text property="regionname" value="<%=regionname %>" readonly="true" disabled="true" styleClass="inputtext" style="width:200" maxlength="100" />
			<input type="hidden" name="regionid" value="<%=regionid %>"/>
		</template:formTr>
        <template:formTr name="����" >
			<apptag:upload state="add"></apptag:upload>
        </template:formTr>		
		<template:formSubmit>
			<td colspan="2">
			<html:submit styleClass="button">����</html:submit>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<html:reset styleClass="button">ȡ��</html:reset>
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
<script type="text/javascript">
document.onkeydown = _back4bidden;
function _back4bidden(e) {   
    var code;   
    if (!e) var e = window.event;   
    if (e.keyCode) code = e.keyCode;   
    else if (e.which) code = e.which;   
    if (((event.keyCode == 8) && 
    	((event.srcElement.type != "text" &&    
         event.srcElement.type != "textarea" &&    
         event.srcElement.type != "password") ||    
         event.srcElement.readOnly == true)) ||    
        ((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82)) ) ||    
        (event.keyCode == 116) ) {    
        event.keyCode = 0;    
        event.returnValue = false;    
    }   
    return true;   
}
</script>