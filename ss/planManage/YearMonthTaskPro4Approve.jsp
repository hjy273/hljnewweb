<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.planinfo.services.*"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@page import="com.cabletech.commons.config.*"%>
<%
  UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
  String regionid = userinfo.getRegionID();
  TaskBO taskbo = new TaskBO();
  Vector vct = taskbo.getTaskOperations(regionid);
  Vector pointlist = new Vector();

  String lineNum = "";
  if (request.getAttribute("lineNum") != null) {
	lineNum = (String)request.getAttribute("lineNum");
  }

  if (request.getAttribute("pointsList") != null) {
    pageContext.setAttribute("pointsCollection",request.getAttribute("pointsList"));
  }

  Vector oplist = new Vector();
  if (request.getAttribute("oplist") != null) {
    oplist = (Vector) request.getAttribute("oplist");
  }
%>
<script language="javascript" type="">
<!--
var functionid = "1";
//是否选取了子任务
var ifCheckedFlag = 0;


function toOpenWindow() {
	//var pageName = "subTaskList.jsp";
	var pageName = "${ctx}/TaskAction.do?method=getObjectEntrance&functionid=" + functionid;
	var calendarPop = window.open(pageName,'calendarPop','width=330,height=560');
	//,resizable=yes,status=yes
	calendarPop.focus();
}

function checkInput(){

	if(taskBean.describtion.value.length == 0){
		alert("请输入该任务名称！");
		taskBean.describtion.focus();
		return false;

	}

	if(ifCheckedFlag == 0){
		alert("请选择操作类型！");
		return false;
	}

	if(taskBean.excutetimes.value.length == 0){
		alert("请输入该巡检次数！");
		taskBean.excutetimes.focus();
		return false;

	}

	return true;

}

function saveData(){

	for(var i = 0; i < taskBean.operationcheck.length; i ++){
		if(taskBean.operationcheck[i].checked == true)
			ifCheckedFlag++;
	}


	if(checkInput()){
		taskBean.submit();
	}
}

function presetData(){

	for(var i = 0; i < taskBean.operationcheck.length; i ++){

		for(var k = 0; k < tasklist.options.length; k ++){
			if(taskBean.operationcheck[i].value == tasklist.options[k].value){
				taskBean.operationcheck[i].checked = true;
			}

		}
	}
}

//-->
</script><body onload="presetData()">
<html:form action="/TaskAction?method=updateYearMonthTask">
  <template:formTable>
    <template:formTr name="任务名称" isOdd="false">
			<!-- hidden values -->
		<html:hidden property="id" />
		<html:hidden property="regionid" />

		<input type="hidden" value="<%=lineNum%>" name="lineNum">

      <html:text property="describtion" styleClass="inputtext" style="width:200px" disabled="true"/>
    </template:formTr>

	<template:formTr name="对应线路级别">

	<html:hidden property="lineleveltext" />
	 <apptag:setSelectOptions columnName1="name" columnName2="code" tableName="lineclassdic" valueName="linetypeColl"/>
      <html:select property="linelevel" styleClass="inputtext" style="width:200px" disabled="true" >
      <html:options collection="linetypeColl" property="value" labelProperty="label"/>
      </html:select>
	</template:formTr>


    <template:formTr name="操作类型" isOdd="false">
    <%
      if (vct.size() > 0) {
        out.println("<table border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");
        for (int i = 0; i < vct.size(); i++) {
          Vector oneRecord = (Vector) vct.get(i);
          String id = (String) oneRecord.get(0);
          String name = (String) oneRecord.get(1);
          out.println("<tr valign=\"middle\"> ");
          out.println("	<td>           ");
          out.println("		<input type=\"checkbox\" name=\"operationcheck\" value=\"" + id + "\" disabled>  ");
          out.println("	</td>     ");
          out.println("	<td>  ");
          out.println("		<input type=\"text\" name=\"operationname\" class=\"inputtext\" style=\"border:0;background-color:transparent;width:80\" value=\"" + name + "\" hiddenValue=\"" + id + "\" readonly>");
          out.println("	</td>  ");
          out.println("</tr>   ");
        }
        out.println("</table>");
      }
    %>
    </template:formTr>
    <template:formTr name="巡检次数">
      <html:text property="excutetimes" styleClass="inputtext" style="width:200px"  disabled="true"/>
      <input type="text" class="inputtext" style="border:0;background-color:transparent;width:30" value="次" readonly>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:button property="action" styleClass="button" onclick="self.close()">确定</html:button>
      </td>
      <td>
        <html:button property="action" styleClass="button" onclick="self.close()">取消</html:button>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<select name="tasklist" style="display:none">
<%
  for (int i = 0; i < oplist.size(); i++) {
    Vector oneRecord = (Vector) oplist.get(i);
    out.println("<option value=" + (String) oneRecord.get(0) + ">");
    out.println((String) oneRecord.get(0));
    out.println("</option>");
  }
%>
</select>
</body>
