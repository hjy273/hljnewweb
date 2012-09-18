<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
//是否选取了任务
var ifCheckedFlag = 0;
//返回,点击返回执行addGoBack()函数
function addGoBack(){
   try{
      location.href = "${ctx}/YearMonthPlanAction.do?method=queryYMPlan&fID=1&year=";
      return true;
   }
   catch(e){
      alert(e);
   }
}
//验证表单数据
function checkInput(){
	if($('YearMonthPlanBean').planname.value =="" ){
    	alert("请输入计划名称！");
		return false;
    }
    return true;
}

function loadTask(objN) {
	//var pageName = "${ctx}/TaskAction.do?method=loadTask&id="+ objN.idValue + "&lineNum=" + objN.index;
	//var taskPop = window.open(pageName,'taskPop','width=436,height=390,scrollbars=yes');
	//taskPop.focus();
}

function toAddTask() {
	//var pageName = "${ctx}/planManage/addYearMonthTask.jsp";
	//var taskPop = window.open(pageName,'taskPop','width=436,height=530,scrollbars=yes');
	//taskPop.focus();
}
//del
function setTaskList(taskname,taskid,excutetimes,linelevel){

	var rowNum =tasklisttable.rows.length - 1;
	var tr = document.all.tasklisttable.insertRow();
	var td = tr.insertCell();
	td.innerHTML = "<input type=\"checkbox\" name=\"taskcheck\" value=\""+ taskid +"\" checked>";

	td = tr.insertCell();
	td.innerHTML = "<input name=linelevel type=\"text\" class=\"inputtext\" style=\"border:0;background-color:transparent;width:80;cursor:hand\" value=\""+ linelevel +"\" idValue=\""+ taskid +"\" index=\"" + rowNum + "\"readonly  readonly onclick=\"loadTask(this)\">";

	td = tr.insertCell();
	td.innerHTML = "<input name=taskname type=\"text\" class=\"inputtext\" style=\"border:0;background-color:transparent;width:180;cursor:hand\" value=\""+ taskname +"\" idValue=\""+ taskid +"\" index=\"" + rowNum + "\"readonly onclick=\"loadTask(this)\">";
	td = tr.insertCell();
	td.innerHTML = "<input name=tasktimes type=\"text\" name=\"tasktimes\" class=\"inputtext\" style=\"border:0;background-color:transparent;width:30\" value=\"" + excutetimes + "\" readonly><input type=\"text\" class=\"inputtext\" style=\"border:0;background-color:transparent;width:30\" value=\"次\" readonly>";
	return true;
}
//del
function reSetTaskList(tasknameV,taskid,excutetimes,i , linelevelV ){
	try{
		$('YearMonthPlanBean').taskname[i].value = tasknameV;
		$('YearMonthPlanBean').tasktimes[i].value = excutetimes;
		$('YearMonthPlanBean').linelevel[i].value = linelevelV;
	}catch(e){
		$('YearMonthPlanBean').taskname.value = tasknameV;
		$('YearMonthPlanBean').tasktimes.value = excutetimes;
		$('YearMonthPlanBean').linelevel.value = linelevelV;
	}
	return true;
}
//设置年度计划名称
function preSetPlanName(){
	var obj = document.getElementById("deptname");
	var preSetName = "年度巡检计划";
	var i = $('YearMonthPlanBean').year.selectedIndex;
	if(i == null){
		i = 0;
	}
	preSetName = obj.value + $('YearMonthPlanBean').year.options[i].value + preSetName;
	$('YearMonthPlanBean').planname.value = preSetName;

}

//-->
</script><body onload="preSetPlanName()"><br><br>
<template:titile value="增加年巡检计划"/>
<!-- /YearMonthPlanAction?method=addYearPlan -->
<html:form action="/YearMonthPlanAction?method=createYMPlan" styleId="YearMonthPlanBean" onsubmit="return checkInput();">
		<input id="plantype" type="hidden" name="plantype" value="1"/>
	    <input  id="deptname" type="hidden"  name="regionid" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>
  		<template:formTable >
		    <template:formTr name="计划年度">
				<apptag:getYearOptions />
			      <html:select property="year" styleClass="inputtext" style="width:250px" onchange="preSetPlanName()">
					<html:options collection="yearCollection" property="value" labelProperty="label"/>
			      </html:select>
		    </template:formTr>
		    <template:formTr name="计划名称" isOdd="false">
			      <html:text property="planname" styleClass="inputtext" style="width:250px" maxlength="30" onblur="value=value.replace(/\s/g,'')"/>

				  <html:hidden property="remark" value="1"/><!-- 新计划 -->
				  <html:hidden property="status" value="0"/>
		    </template:formTr>
            <!--
            <logic:equal value="send" name="isSendSm">
             <template:formTr name="目标处理人">
                        <apptag:setSelectOptions columnName1="username" columnName2="phone" tableName="userinfo" valueName="objectman" region="true" condition="deptype='1' "/>
                        <html:select property="phone" styleClass="inputtext" style="width:250" >
                              <html:options collection="objectman"  property="value" labelProperty="label"/>
                        </html:select>
            </template:formTr>
            </logic:equal>
            
		    <template:formTr name="任务分配">
			      <table id="tasklisttable" name="tasklisttable" border="0" cellspacing="0" cellpadding="5">
			        <tr valign="middle">
			          <td>          </td>
			        </tr>
			      </table>
			      <br>
			      <table width="85%" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td align="right">
			            <a href="javascript:toAddTask()">新增任务</a>
			          </td>
			        </tr>
			      </table>
		    </template:formTr>
   
            -->
		    <template:formSubmit>
		    	  <td>
		    	  	<html:submit property="action" styleClass="button">增加任务</html:submit>
		    	  </td>
			      <td>
			        <input name="Button2" type="reset" class="button" value="取消">
			      </td>
		    </template:formSubmit>
	  </template:formTable>
</html:form>
</body>
