<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
//�Ƿ�ѡȡ������
var ifCheckedFlag = 0;

//�������
function addTask(){
	location.href = "${ctx}/planManage/addYearMonthTask.jsp";
}
//�༭����
function editTask(ID){
	url = "${ctx}/TaskAction.do?method=loadTaskInfo&id="+ID;
	self.location.replace(url);
}
//ɾ������
function removeTask(ID){
	if(confirm("ȷ��Ҫɾ����?")){
		location.href = "${ctx}/TaskAction.do?method=removeTaskInfo&id="+ID;
	}
}
//ȡ�����
function Cancel(){
	location.href = "${ctx}/planManage/addMonthPlan.jsp";
}
function save(){
	if($('approverid').value == ''){
		alert("Ŀ�괦���˲���Ϊ�գ�");
		return false;
	}
	document.getElementById("sbtn").disabled="disabled";
	return true;
}

//-->
</script>
<body><br><br>
<template:titile value="������Ѳ��ƻ�"/>
<!-- /YearMonthPlanAction?method=addYearPlan -->
<html:form action="/YearMonthPlanAction?method=saveYMPlan" onsubmit="return save();">
		<input id="plantype" type="hidden" name="plantype" value="2"/>
	    <input  id="deptname" type="hidden"  name="regionid" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>
  		<template:formTable namewidth="200" contentwidth="400">
		    <template:formTr name="�ƻ�����">
		    	<html:text property="year" name="YMplan" style="border:0;background-color:transparent;width:50px"></html:text>��<html:text property="month" name="YMplan" style="border:0;background-color:transparent;width:50px"></html:text>
		    </template:formTr>
		    <template:formTr name="�ƻ�����" isOdd="false">
			      <html:text property="planname" name="YMplan" styleClass="inputtext"  style="border:0;background-color:transparent;width:250px"  />
				  <html:hidden property="remark" value="1"/><!--�¼ƻ�-->
				  <html:hidden property="status" value="0"/> 
				  
		    </template:formTr>
            <logic:equal value="send" name="isSendSm">
            <tr>
            <apptag:approverselect inputName="approverid,phone" label="Ŀ�괦����"
				inputType="radio"></apptag:approverselect>
			</tr>
			<!-- 
             <template:formTr name="Ŀ�괦����">
                        <apptag:setSelectOptions columnName1="username" columnName2="phone" tableName="userinfo" valueName="objectman" region="true" condition="deptype='1' "/>
                        <html:select property="phone" styleClass="inputtext" style="width:250" >
                              <html:options collection="objectman"  property="value" labelProperty="label"/>
                        </html:select>
            </template:formTr>
             -->
            </logic:equal>
          
		    <template:formTr name="�������">
			   <table name="tasklisttable" border="0" cellspacing="0" cellpadding="5">
			   	<tr>
			   		<td>
			   		<logic:iterate id="tasklist" name="taskList" indexId="index">
			   			<input name=linelevel type="text" class="inputtext" style="border:0;background-color:transparent;width:60px;cursor:hand" value="<bean:write name="tasklist" property="lineleveltext"/>" idValue="<bean:write name="tasklist" property="id"/>"  readonly >
			   			<input name=taskname type="text" class="inputtext" style="border:0;background-color:transparent;width:120px;cursor:hand" value="<bean:write name="tasklist" property="describtion"/>" idValue="<bean:write name="tasklist" property="id"/>"  readonly >
			   			<input name=tasktimes type="text" class="inputtext" style="border:0;background-color:transparent;width:20px" value="<bean:write name="tasklist" property="excutetimes"/>" readonly />��
			   			<input name="edit" onclick="editTask(<%=index %>)" class="button" type="button" value="�޸�" /><input name="ɾ��" class="button" onclick="removeTask(<%=index %>)" type="button" value="ɾ��" />
			   		</logic:iterate>
			   		</td>
			   	</tr>
			   </table>
		    </template:formTr>

		    <template:formSubmit>
		    	 <td>
		    	 	<html:button property="button" styleClass="button" onclick="addTask()">�������</html:button>
		    	 </td>
		    	  <td>
		    	  	<html:submit property="action" styleId="sbtn" styleClass="button"> ����</html:submit>
		    	  </td>
			      <td>
			        <input name="Button2" type="reset" class="button" onclick="Cancel()"value="ȡ��">
			      </td>
		    </template:formSubmit>
	  </template:formTable>
</html:form>
</body>
