<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
//�Ƿ�ѡȡ������
var ifCheckedFlag = 0;

//�������
function addTask(){
	location.href = "${ctx}/TaskAction.do?method=addTask&excutor";
}
//�༭����
function editTask(ID){
	url = "${ctx}/TaskAction.do?method=loadPlanTask&id="+ID;
	self.location.replace(url);
}
//ɾ������
function removeTask(ID){
	location.href = "${ctx}/TaskAction.do?method=removePlanTask&id="+ID;
}
//ȡ�����
function Cancel(){
	location.href = "${ctx}/PlanAction.do?method=queryPlan";
}


//-->
</script>
<body><br><br>
<template:titile value="����Ѳ��ƻ�"/>
<!-- /YearMonthPlanAction?method=addYearPlan -->
<html:form action="/PlanAction?method=updatePlan" >
	
	    <input  id="deptname" type="hidden"  name="regionid" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>
  		<template:formTable >
		    
		    <template:formTr name="�ƻ�����" isOdd="false">
		    	 <bean:write property="planname" name="Plan"/>
		    	 <html:hidden property="planname" name="Plan"/>
			     
				  <html:hidden property="remark" value="1"/><!--�¼ƻ�-->
				  <html:hidden property="status" value="0"/> 
				  
		    </template:formTr>
            <template:formTr name="Ѳ�췽ʽ" isOdd="false">	      
				  <bean:define id="temp" name="Plan" property="patrolmode"></bean:define>
				  <%out.print(temp.equals("01")?"�ֶ�":"�Զ�"); %>
		    </template:formTr>
		    <template:formTr name="�������">
			   <table name="tasklisttable" border="0" cellspacing="0" cellpadding="5">
			   		<logic:iterate id="tasklist" name="taskList" indexId="index">
			   			<tr><td>
			   			<input name=linelevel type="text" class="inputtext" style="border:0;background-color:transparent;width:60px;" value="<bean:write name="tasklist" property="lineleveltext"/>" idValue="<bean:write name="tasklist" property="id"/>"  readonly />
			   			<input name=taskname type="text" class="inputtext" style="border:0;background-color:transparent;width:120px;" value="<bean:write name="tasklist" property="describtion"/>" idValue="<bean:write name="tasklist" property="id"/>"  readonly />
			   			<input name=tasktimes type="text" class="inputtext"  style="border:0;background-color:transparent;width:20px;text-align:right" value="<bean:write name="tasklist" property="excutetimes"/>" readonly>��
			   			<input name="edit" onclick="editTask(<%=index %>)" class="button" type="button" value="�޸�" /><input class="button" name="ɾ��" onclick="removeTask(<%=index %>)" type="button" value="ɾ��" /><br>
			   			Ѳ���߶Σ�<span style="color:#999999"><bean:write name="tasklist" property="subline"/></span>&nbsp;&nbsp;&nbsp;&nbsp;��<bean:write name="tasklist" property="taskpoint"/>��
			   			</td>
			   		</tr>
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
			        <input name="Button2" type="reset" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"value="ȡ��">
			      </td>
		    </template:formSubmit>
	  </template:formTable>
</html:form>
</body>
