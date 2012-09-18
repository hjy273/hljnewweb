<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
//是否选取了任务
var ifCheckedFlag = 0;

//添加任务
function addTask(){
	location.href = "${ctx}/planManage/addYearMonthTask.jsp";
}
//编辑任务
function editTask(ID){
	url = "${ctx}/TaskAction.do?method=loadTaskInfo&id="+ID;
	self.location.replace(url);
}
//删除任务
function removeTask(ID){
	if(confirm("确定要删除吗?")){
		location.href = "${ctx}/TaskAction.do?method=removeTaskInfo&id="+ID;
	}
}
//取消添加
function Cancel(){
	location.href = "${ctx}/planManage/addMonthPlan.jsp";
}
function save(){
	if($('approverid').value == ''){
		alert("目标处理人不能为空！");
		return false;
	}
	document.getElementById("sbtn").disabled="disabled";
	return true;
}

//-->
</script>
<body><br><br>
<template:titile value="增加月巡检计划"/>
<!-- /YearMonthPlanAction?method=addYearPlan -->
<html:form action="/YearMonthPlanAction?method=saveYMPlan" onsubmit="return save();">
		<input id="plantype" type="hidden" name="plantype" value="2"/>
	    <input  id="deptname" type="hidden"  name="regionid" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>
  		<template:formTable namewidth="200" contentwidth="400">
		    <template:formTr name="计划年月">
		    	<html:text property="year" name="YMplan" style="border:0;background-color:transparent;width:50px"></html:text>年<html:text property="month" name="YMplan" style="border:0;background-color:transparent;width:50px"></html:text>
		    </template:formTr>
		    <template:formTr name="计划名称" isOdd="false">
			      <html:text property="planname" name="YMplan" styleClass="inputtext"  style="border:0;background-color:transparent;width:250px"  />
				  <html:hidden property="remark" value="1"/><!--新计划-->
				  <html:hidden property="status" value="0"/> 
				  
		    </template:formTr>
            <logic:equal value="send" name="isSendSm">
            <tr>
            <apptag:approverselect inputName="approverid,phone" label="目标处理人"
				inputType="radio"></apptag:approverselect>
			</tr>
			<!-- 
             <template:formTr name="目标处理人">
                        <apptag:setSelectOptions columnName1="username" columnName2="phone" tableName="userinfo" valueName="objectman" region="true" condition="deptype='1' "/>
                        <html:select property="phone" styleClass="inputtext" style="width:250" >
                              <html:options collection="objectman"  property="value" labelProperty="label"/>
                        </html:select>
            </template:formTr>
             -->
            </logic:equal>
          
		    <template:formTr name="任务分配">
			   <table name="tasklisttable" border="0" cellspacing="0" cellpadding="5">
			   	<tr>
			   		<td>
			   		<logic:iterate id="tasklist" name="taskList" indexId="index">
			   			<input name=linelevel type="text" class="inputtext" style="border:0;background-color:transparent;width:60px;cursor:hand" value="<bean:write name="tasklist" property="lineleveltext"/>" idValue="<bean:write name="tasklist" property="id"/>"  readonly >
			   			<input name=taskname type="text" class="inputtext" style="border:0;background-color:transparent;width:120px;cursor:hand" value="<bean:write name="tasklist" property="describtion"/>" idValue="<bean:write name="tasklist" property="id"/>"  readonly >
			   			<input name=tasktimes type="text" class="inputtext" style="border:0;background-color:transparent;width:20px" value="<bean:write name="tasklist" property="excutetimes"/>" readonly />次
			   			<input name="edit" onclick="editTask(<%=index %>)" class="button" type="button" value="修改" /><input name="删除" class="button" onclick="removeTask(<%=index %>)" type="button" value="删除" />
			   		</logic:iterate>
			   		</td>
			   	</tr>
			   </table>
		    </template:formTr>

		    <template:formSubmit>
		    	 <td>
		    	 	<html:button property="button" styleClass="button" onclick="addTask()">添加任务</html:button>
		    	 </td>
		    	  <td>
		    	  	<html:submit property="action" styleId="sbtn" styleClass="button"> 保存</html:submit>
		    	  </td>
			      <td>
			        <input name="Button2" type="reset" class="button" onclick="Cancel()"value="取消">
			      </td>
		    </template:formSubmit>
	  </template:formTable>
</html:form>
</body>
