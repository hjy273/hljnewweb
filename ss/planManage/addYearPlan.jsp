<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
//�Ƿ�ѡȡ������
var ifCheckedFlag = 0;
//����,�������ִ��addGoBack()����
function addGoBack(){
   try{
      location.href = "${ctx}/YearMonthPlanAction.do?method=queryYMPlan&fID=1&year=";
      return true;
   }
   catch(e){
      alert(e);
   }
}
//��֤������
function checkInput(){
	if($('YearMonthPlanBean').planname.value =="" ){
    	alert("������ƻ����ƣ�");
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
	td.innerHTML = "<input name=tasktimes type=\"text\" name=\"tasktimes\" class=\"inputtext\" style=\"border:0;background-color:transparent;width:30\" value=\"" + excutetimes + "\" readonly><input type=\"text\" class=\"inputtext\" style=\"border:0;background-color:transparent;width:30\" value=\"��\" readonly>";
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
//������ȼƻ�����
function preSetPlanName(){
	var obj = document.getElementById("deptname");
	var preSetName = "���Ѳ��ƻ�";
	var i = $('YearMonthPlanBean').year.selectedIndex;
	if(i == null){
		i = 0;
	}
	preSetName = obj.value + $('YearMonthPlanBean').year.options[i].value + preSetName;
	$('YearMonthPlanBean').planname.value = preSetName;

}

//-->
</script><body onload="preSetPlanName()"><br><br>
<template:titile value="������Ѳ��ƻ�"/>
<!-- /YearMonthPlanAction?method=addYearPlan -->
<html:form action="/YearMonthPlanAction?method=createYMPlan" styleId="YearMonthPlanBean" onsubmit="return checkInput();">
		<input id="plantype" type="hidden" name="plantype" value="1"/>
	    <input  id="deptname" type="hidden"  name="regionid" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>
  		<template:formTable >
		    <template:formTr name="�ƻ����">
				<apptag:getYearOptions />
			      <html:select property="year" styleClass="inputtext" style="width:250px" onchange="preSetPlanName()">
					<html:options collection="yearCollection" property="value" labelProperty="label"/>
			      </html:select>
		    </template:formTr>
		    <template:formTr name="�ƻ�����" isOdd="false">
			      <html:text property="planname" styleClass="inputtext" style="width:250px" maxlength="30" onblur="value=value.replace(/\s/g,'')"/>

				  <html:hidden property="remark" value="1"/><!-- �¼ƻ� -->
				  <html:hidden property="status" value="0"/>
		    </template:formTr>
            <!--
            <logic:equal value="send" name="isSendSm">
             <template:formTr name="Ŀ�괦����">
                        <apptag:setSelectOptions columnName1="username" columnName2="phone" tableName="userinfo" valueName="objectman" region="true" condition="deptype='1' "/>
                        <html:select property="phone" styleClass="inputtext" style="width:250" >
                              <html:options collection="objectman"  property="value" labelProperty="label"/>
                        </html:select>
            </template:formTr>
            </logic:equal>
            
		    <template:formTr name="�������">
			      <table id="tasklisttable" name="tasklisttable" border="0" cellspacing="0" cellpadding="5">
			        <tr valign="middle">
			          <td>          </td>
			        </tr>
			      </table>
			      <br>
			      <table width="85%" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td align="right">
			            <a href="javascript:toAddTask()">��������</a>
			          </td>
			        </tr>
			      </table>
		    </template:formTr>
   
            -->
		    <template:formSubmit>
		    	  <td>
		    	  	<html:submit property="action" styleClass="button">��������</html:submit>
		    	  </td>
			      <td>
			        <input name="Button2" type="reset" class="button" value="ȡ��">
			      </td>
		    </template:formSubmit>
	  </template:formTable>
</html:form>
</body>
