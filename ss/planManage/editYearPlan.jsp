<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--

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
//ȡ����������
function Cancel(){
	location.href = "${ctx}/YearMonthPlanAction.do?method=queryYMPlan&fID=1&year=";
}


//-->
</script><body><br><br>
<template:titile value="������Ѳ��ƻ�"/>
<html:form action="/YearMonthPlanAction?method=updateYMPlan" >
  <template:formTable >
    <template:formTr name="�ƻ����">
		<!-- hidden id -->
		<html:hidden property="id" name="YMplan"/>
		<html:hidden property="plantype" name="YMplan"/>
		<html:hidden property="regionid" name="YMplan"/>
		<html:hidden property="creator" name="YMplan"/>
		<html:hidden property="createdate" name="YMplan"/>
	  <html:text property="year" name="YMplan" style="border:0;background-color:transparent;width:250px"></html:text>
    </template:formTr>
    <template:formTr name="�ƻ�����" isOdd="false">
    <html:text property="planname" name="YMplan" styleClass="inputtext"  style="border:0;background-color:transparent;width:250px" />
    </template:formTr>

	<template:formTr name="�������">
			   <table name="tasklisttable" border="0" cellspacing="0" cellpadding="5" align="left">
			   		<logic:iterate id="tasklist" name="taskList" indexId="index">
        <tr valign="middle">
          <td>
			  <input name=linelevel type="text" class="inputtext" style="border:0;background-color:transparent;width:60px;" value="<bean:write name="tasklist" property="lineleveltext"/>" idValue="<bean:write name="tasklist" property="id"/>"  readonly />
			  <input name=taskname type="text" class="inputtext" style="border:0;background-color:transparent;width:120px;" value="<bean:write name="tasklist" property="describtion"/>" idValue="<bean:write name="tasklist" property="id"/>"  readonly />
			  <input name=tasktimes type="text" class="inputtext" style="border:0;background-color:transparent;width:20px" value="<bean:write name="tasklist" property="excutetimes"/>" readonly>��
			  <input name="edit" onclick="editTask(<%=index %>)" type="button" class="button" value="�޸�" /><input name="ɾ��" class="button" onclick="removeTask(<%=index %>)" type="button" value="ɾ��" />
          </td>
        </tr>
			   		</logic:iterate>
			   </table>
    </template:formTr>
			<tr>
				<td colspan="2" style="padding: 10px; text-align: center">
					<table border="1" width="100%" cellpadding="0" cellspacing="0"
						style="border-collapse: collapse;">
						<tr>
							<td style="text-align:center;">
								��˽��
							</td>
							<td style="text-align:center;">
								�� �� ��
							</td>
							<td style="text-align:center;">
								�������
							</td>
							<td style="text-align:center;" align="center">
								������
							</td>
						</tr>
						<tr>
							<td>
								<bean:write name="YMplan" property="status" />
							</td>
							<td>
								<bean:write name="YMplan" property="approver" />
							</td>
							<td>
								<bean:write name="YMplan" property="approvedate" />
							</td>
							<td>
								<bean:write name="YMplan" property="approvecontent" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
            <logic:equal value="send" name="isSendSm">
             	<apptag:approverselect inputName="approverid,phone" label="Ŀ�괦����"
				inputType="radio"></apptag:approverselect>
                        <apptag:setSelectOptions columnName1="username" columnName2="phone" tableName="userinfo" valueName="objectman" region="true" condition="deptype='1' "/>
                        <!-- 
                        <html:select property="phone" styleId="phone" styleClass="inputtext" style="width:250" >
                              <html:options collection="objectman"  property="value" labelProperty="label"/>
                        </html:select>
                         -->
            </logic:equal>
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
