<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.planinfo.services.*"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@page import="com.cabletech.commons.config.*"%>
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
ifCheckedFlag=0;
//	if(taskBean.operationcheck.length == undefined){
//	 	if(taskBean.operationcheck.checked == true)
//			ifCheckedFlag++;
//	else{
    	for(var i = 0; i < document.getElementsByName('operationcheck').length; i ++){
        	if(document.getElementsByName('operationcheck')[i].checked == true)
            	ifCheckedFlag++;
    	}
//    }

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
function theEnd(){
		var plantype = document.all.plantype.value;
		if(plantype==1){
			location.href = "${ctx}/planManage/showYearPlan.jsp";
		}else{
			location.href = "${ctx}/planManage/showMonthPlan.jsp";
		}	
}
function updatetheEnd(){
		var plantype = document.all.plantype.value;
		if(plantype==1){
			location.href = "${ctx}/planManage/editYearPlan.jsp";
		}else{
			location.href = "${ctx}/planManage/editMonthPlan.jsp";
		}	
}

//�Զ�������������
function setTaskName(objN,name){
	var tempValue = "";
	var operationSize = 0;
//	if(taskBean.operationcheck.length == undefined){
//		if(taskBean.operationcheck.checked == true){
//			taskBean.describtion.value = taskBean.operationname.value;
//			operationSize++;
//		}
//	}else
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

//-->
</script>
<br>
<template:titile value="����Ѳ��ƻ�������"/>
<html:form action="/TaskAction?method=addYearMonthTask">
<input name="ss" type="hidden" id ="SS" value="0"><!-- 0���������һ�� 1������� -->
		<input id="plantype" type="hidden" name="plantype" value="<%=session.getAttribute("plantype") %>" />
	  <template:formTable>
	    <template:formTr name="��������">
	      	<html:text property="describtion" styleClass="inputtext" style="width:160px" maxlength="20" onblur="value=value.replace(/\s/g,'')"/><font color="red">*</font>
	    </template:formTr>
		<template:formTr name="��Ӧ��·����" isOdd="false">
				<html:hidden property="lineleveltext" />
				<apptag:setSelectOptions columnName1="name" columnName2="code" tableName="lineclassdic"   valueName="linetypeColl"  order="code"/>
		      	<html:select property="linelevel" styleClass="inputtext" style="width:160px">
			        <html:options collection="linetypeColl" property="value" labelProperty="label"/>
		      	</html:select>
		</template:formTr>
	    
	    <template:formTr name="Ѳ�����"  isOdd="false">
		      <html:text property="excutetimes" styleClass="inputtext"  style="width:160px" maxlength="3"/>&nbsp;��<font color="red">*</font>
		   <!--  <input type="text" class="inputtext" style="border:0;background-color:transparent;width:30" value="��" readonly>  -->  
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
		          out.println("		<input type=\"checkbox\" name=\"operationcheck\" id=\"operationcheck\" value=\"" + id + "\" onclick=setTaskName(this,'"+ name +"')>  ");
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
	    <template:formSubmit>
		      <td title="�������������">
		        <html:button property="action" styleClass="button" onclick="saveData(0)">����</html:button>
		      </td>
		      <td title="�������������">
		        <html:button property="action" styleClass="button" onclick="saveData(1)">���</html:button>
		      </td>
		       <td title="�������������ˣ����ȡ��ֱ���������ҳ">
		       <c:if test="${EditS=='edit'}">
		             <html:button property="action" styleClass="button" onclick="updatetheEnd()" > ȡ��</html:button>
		       </c:if>
		        <c:if test="${EditS=='add'}">
		       		<html:button property="action" styleClass="button" onclick="theEnd()" > ȡ��</html:button>
		       </c:if>
		      </td>
		    </template:formSubmit>
	  </template:formTable>
</html:form>
