<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
//�Ƿ�ѡȡ������
var ifCheckedFlag = 0;

//�������
function addTask(){
	location.href = "${ctx}/StencilTaskAction.do?method=addTask";
}
//�༭����
function editTask(ID){
	url = "${ctx}/StencilTaskAction.do?method=loadStencilTask&id="+ID;
	self.location.replace(url);
}
//ɾ������
function removeTask(ID){
	location.href = "${ctx}/StencilTaskAction.do?method=removeStencilTask&id="+ID;
}
//ȡ�����
function Cancel(){
	location.href = "${ctx}/StencilAction.do?method=queryStencil";
}


//-->
</script>
<%session.setAttribute("edittask","add"); %>
<body><br><br>
<template:titile value="�޸ļƻ�ģ��"/>
<!-- /YearMonthPlanAction?method=addYearPlan -->
<html:form action="/StencilAction?method=updateStencil" >
	
	    <input  id="deptname" type="hidden"  name="regionid" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>
  		<template:formTable >
		    
		    <template:formTr name="ģ������" isOdd="false">
			      <html:text property="stencilname" name="Stencil" styleClass="inputtext"  style="border:0;background-color:transparent;width:300px" />
				  
		    </template:formTr>
            
		    <template:formTr name="�������">
			   <table name="tasklisttable" border="0" cellspacing="0" cellpadding="5">
			   		<logic:iterate id="tasklist" name="StencilTaskList" indexId="index">
			   			<tr><td>
			   			<input name=linelevel type="text" class="inputtext" style="border:0;background-color:transparent;width:60px;" value="<bean:write name="tasklist" property="lineleveltext"/>" idValue="<bean:write name="tasklist" property="id"/>"  readonly />
			   			<input name=taskname type="text" class="inputtext" style="border:0;background-color:transparent;width:120px;" value="<bean:write name="tasklist" property="description"/>" idValue="<bean:write name="tasklist" property="id"/>"  readonly />
			   			<input name=tasktimes type="text" class="inputtext" style="border:0;background-color:transparent;width:20px;text-align:right" value="<bean:write name="tasklist" property="excutetimes"/>" readonly />��
			   			<input name="edit" onclick="editTask(<%=index %>)" type="button" value="�޸�" /><input name="ɾ��" onclick="removeTask(<%=index %>)" type="button" value="ɾ��" /><br>
			   			�漰�߶Σ�<span style="color:#999999"><bean:write name="tasklist" property="subline"/></span>&nbsp;&nbsp;&nbsp;&nbsp;��<bean:write name="tasklist" property="taskpoint"/>��
			   		</td></tr>
			   		</logic:iterate>
			   </table>
		    </template:formTr>

		    <template:formSubmit>
		    	 <td>
		    	 	<html:button property="button" styleClass="button" onclick="addTask()">�������</html:button>
		    	 </td>
		    	  <td>
		    	  	<html:submit property="action" styleClass="button">����</html:submit>
		    	  </td>
			      <td>
			        <input name="Button2" type="reset" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="ȡ��">
			      </td>
		    </template:formSubmit>
	  </template:formTable>
</html:form>
</body>
