<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.planinfo.services.*"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@page import="com.cabletech.commons.config.*"%>
<%@page import="com.cabletech.planinfo.beans.*"%>
<%@page import="com.cabletech.planinfo.domainobjects.*"%>	
<%
  UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
  String regionid = userinfo.getRegionID();
  TaskBO taskbo = new TaskBO();
  Vector vct = taskbo.getTaskOperations(regionid);
%>
<script language="javascript">
<!--
var functionid = "1";
//�Ƿ�ѡȡ��������
var ifCheckedFlag = 0;
function toOpenWindow() {
	//var pageName = "subTaskList.jsp";
	var pageName = "${ctx}/TaskAction.do?method=getObjectEntrance&functionid=" + functionid;
	var calendarPop = window.open(pageName,'calendarPop','width=330,height=560');
	//,resizable=yes,status=yes
	calendarPop.focus();
}

function checkInput(){

	if(taskBean.describtion.value == ""){
		alert("��������������ƣ�");
		taskBean.describtion.focus();
		return false;

	}
	if(ifCheckedFlag == 0){
		alert("��ѡ��������ͣ�");
		return false;
	}
	if(taskBean.excutetimes.value.length == 0){
		alert("�������Ѳ�������");
		taskBean.excutetimes.focus();
		return false;
	}else{
		 var mysplit = /^\d{1,3}$/;
		 if(mysplit.test(taskBean.excutetimes.value)){
          return true;
        }
        else{
            taskBean.excutetimes.value="";
        	alert("Ѳ�����ֻ��������,�ҽ�Ϊ����,����������");
        	return false;
        }
		 if(taskBean.excutetimes.value == 0){
			alert("Ѳ���������Ϊ0��ֻ�ܴ��ڵ���1��");
			return false;
		 }
	}
	return true;
}
function saveData(ss){
//	if(taskBean.operationcheck.length == undefined){
//	 	if(taskBean.operationcheck.checked == true)
//			ifCheckedFlag++;
//	else{
		for(var i = 0; i < taskBean.operationcheck.length; i ++){
			if(taskBean.operationcheck[i].checked == true)
				ifCheckedFlag++;
		}
//	}

	var k = taskBean.linelevel.selectedIndex;
	var levelText = taskBean.linelevel[k].text;

	taskBean.lineleveltext.value = levelText;
	if(ss=='1'){
		document.all.SS.value ="1";
	}else{
		document.all.SS.value="0";
	}
	if(checkInput()){
		taskBean.submit();
	}
}

//�Զ�������������
function setTaskName(objN,name){
	var tempValue = "";
	var operationSize = 0;
//	if(taskBean.operationcheck.length == undefined){
//	 	if(taskBean.operationcheck.checked == true){
//			taskBean.describtion.value = taskBean.operationname.value;
//			operationSize++;
//		}
//	else{
		for(var i = 0; i < taskBean.operationcheck.length; i ++){
			if(taskBean.operationcheck[i].checked == true){
				taskBean.describtion.value = taskBean.operationname[i].value;
				operationSize++;
			}
		}
		if(operationSize > 1){
			taskBean.describtion.value = "�ۺ�ά������";
		}
//	}
}
//��ʼ��������������
function presetData(){
	for(var i = 0; i < taskBean.operationcheck.length; i ++){
		for(var k = 0; k < tasklist.options.length; k ++){
			if(taskBean.operationcheck[i].value == tasklist.options[k].value){
				taskBean.operationcheck[i].checked = true;
			}
		}
	}
	var j = taskBean.linelevel.selectedIndex;
	var levelText = taskBean.linelevel[j].text;
	taskBean.lineleveltext.value = levelText;

}
//-->
</script>
<br>
<template:titile value="�޸�Ѳ��ƻ�������"/>
<body onload="presetData()">
<html:form action="/TaskAction?method=addYearMonthTask">
<input name="ss" type="hidden" id ="SS" value="0"><!-- 0���������һ�� 1������� -->
	  <template:formTable>
	    <template:formTr name="��������">
	      	<html:text property="describtion" name="taskinfo" styleClass="inputtext" style="width:160px" maxlength="20" onblur="value=value.replace(/\s/g,'')"/>
	    </template:formTr>
		<template:formTr name="��Ӧ��·����" isOdd="false">
				<html:hidden property="lineleveltext" name="taskinfo"/>
				<apptag:setSelectOptions columnName1="name" columnName2="code" tableName="lineclassdic"   valueName="linetypeColl"  order="code"/>
		      	<html:select property="linelevel" name="taskinfo" styleClass="inputtext" style="width:160px">
			        <html:options collection="linetypeColl" property="value" labelProperty="label"/>
		      	</html:select>
		</template:formTr>
	    
	    <template:formTr name="Ѳ�����"  isOdd="false">
		      <html:text property="excutetimes" name="taskinfo" styleClass="inputtext"  style="width:160px" maxlength="3"/>
		      <input type="text" class="inputtext" style="border:0;background-color:transparent;width:30px" value="��" readonly>
	    </template:formTr>
	    
	    <template:formTr name="��������">
	     <div style="color:red">����ѡ��ΪѲ����Ա����Ѳ���ʱ����Ҫ���Ĳ����������ο���</div>
		    <%
		      if (vct.size() > 0) {
		        out.println("<table border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");
		        for (int i = 0; i < vct.size(); i++) {
		          Vector oneRecord = (Vector) vct.get(i);
		          String id = (String) oneRecord.get(0);
		          String name = (String) oneRecord.get(1);
		          out.println("<tr valign=\"middle\"> ");
		          out.println("	<td>           ");
		          out.println("		<input type=\"checkbox\" name=\"operationcheck\" value=\"" + id + "\" onclick=setTaskName(this,'"+ name +"')>  ");
		          out.println("	</td>     ");
		          out.println("	<td>  ");
		          out.println("		<input type=\"text\" name=\"operationname\" class=\"inputtext\" style=\"border:0;background-color:transparent;width:80px\" value=\"" + name + "\" hiddenValue=\"" + id + "\" readonly>");
		          out.println("	</td>  ");
		          out.println("</tr>   ");
		        }
		        out.println("</table>");
		      }
		    %>
	    </template:formTr>
	    <template:formSubmit>
		      <!--  td>
		        <html:button property="action" styleClass="button" onclick="saveData(0)">����</html:button>
		      </td>-->
		      <td>
		        <html:button property="action" styleClass="button" onclick="saveData(1)">���</html:button>
		      </td>
		    </template:formSubmit>
	  </template:formTable>
</html:form>
<select name="tasklist" style="display:none">
<% 
	TaskBean taskinfo = (TaskBean)request.getAttribute("taskinfo");
	List toList = (List)taskinfo.getTaskOpList();
	for (int i = 0; i < toList.size(); i++) {
    	TaskOperationList tol = (TaskOperationList)toList.get(i);
    	out.println("<option value=" + (String) tol.getOperationid() + ">");
    	out.println((String) tol.getOperationid());
    	out.println("</option>");
  	}
%>
</select>

</body>
