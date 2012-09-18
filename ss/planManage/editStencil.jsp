<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
//是否选取了任务
var ifCheckedFlag = 0;

//添加任务
function addTask(){
	location.href = "${ctx}/StencilTaskAction.do?method=addTask";
}
//编辑任务
function editTask(ID){
	url = "${ctx}/StencilTaskAction.do?method=loadStencilTask&id="+ID;
	self.location.replace(url);
}
//删除任务
function removeTask(ID){
	location.href = "${ctx}/StencilTaskAction.do?method=removeStencilTask&id="+ID;
}
//取消添加
function Cancel(){
	location.href = "${ctx}/StencilAction.do?method=queryStencil";
}


//-->
</script>
<%session.setAttribute("edittask","add"); %>
<body><br><br>
<template:titile value="修改计划模板"/>
<!-- /YearMonthPlanAction?method=addYearPlan -->
<html:form action="/StencilAction?method=updateStencil" >
	
	    <input  id="deptname" type="hidden"  name="regionid" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>
  		<template:formTable >
		    
		    <template:formTr name="模板名称" isOdd="false">
			      <html:text property="stencilname" name="Stencil" styleClass="inputtext"  style="border:0;background-color:transparent;width:300px" />
				  
		    </template:formTr>
            
		    <template:formTr name="任务分配">
			   <table name="tasklisttable" border="0" cellspacing="0" cellpadding="5">
			   		<logic:iterate id="tasklist" name="StencilTaskList" indexId="index">
			   			<tr><td>
			   			<input name=linelevel type="text" class="inputtext" style="border:0;background-color:transparent;width:60px;" value="<bean:write name="tasklist" property="lineleveltext"/>" idValue="<bean:write name="tasklist" property="id"/>"  readonly />
			   			<input name=taskname type="text" class="inputtext" style="border:0;background-color:transparent;width:120px;" value="<bean:write name="tasklist" property="description"/>" idValue="<bean:write name="tasklist" property="id"/>"  readonly />
			   			<input name=tasktimes type="text" class="inputtext" style="border:0;background-color:transparent;width:20px;text-align:right" value="<bean:write name="tasklist" property="excutetimes"/>" readonly />次
			   			<input name="edit" onclick="editTask(<%=index %>)" type="button" value="修改" /><input name="删除" onclick="removeTask(<%=index %>)" type="button" value="删除" /><br>
			   			涉及线段：<span style="color:#999999"><bean:write name="tasklist" property="subline"/></span>&nbsp;&nbsp;&nbsp;&nbsp;共<bean:write name="tasklist" property="taskpoint"/>点
			   		</td></tr>
			   		</logic:iterate>
			   </table>
		    </template:formTr>

		    <template:formSubmit>
		    	 <td>
		    	 	<html:button property="button" styleClass="button" onclick="addTask()">添加任务</html:button>
		    	 </td>
		    	  <td>
		    	  	<html:submit property="action" styleClass="button">保存</html:submit>
		    	  </td>
			      <td>
			        <input name="Button2" type="reset" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="取消">
			      </td>
		    </template:formSubmit>
	  </template:formTable>
</html:form>
</body>
