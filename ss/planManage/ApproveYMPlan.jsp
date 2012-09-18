<%@include file="/common/header.jsp"%>
<jsp:useBean id="YearMonthPlanBean" class="com.cabletech.planinfo.beans.YearMonthPlanBean" scope="request"/>

<script language="javascript" type="">
//����������
function loadTaskList() {
	var pageName = "${ctx}/YearMonthPlanAction.do?method=getTaskListByPlanid&fID=2&bdate=&planid="+YearMonthPlanBean.id.value;
	hiddenIframe.location.replace(pageName);
}
//����monthv��ֵȷ����ʾ�ꡢ�¼ƻ�
function setMonthTr(monthv){
	if(monthv == null || monthv.length == 0 || monthv == "null"){
		monthTr.style.display = "none";
		yearATr.style.display = "none";
		yearBTr.style.display = "";
	}
}
//����
function addGoBack(){
   	try{
      	location.href = "${ctx}/YearMonthPlanAction.do?method=getUnapprovedYMPlan";
       	return true;
   	}catch(e){
      	alert(e);
	}
}

</script>
<body onload="loadTaskList();setMonthTr('<%=YearMonthPlanBean.getMonth()%>')">
<br>
<template:titile value="����Ѳ��ƻ�"/>
<html:form action="/YearMonthPlanAction?method=ApprovePlan" >
  <template:formTable>
    <template:formTr name="�ƻ����" tagID="yearATr" isOdd="false" style="display:">
    	<input name="year" value="<%=YearMonthPlanBean.getYear() %>" type="hidden" />
    	<%=YearMonthPlanBean.getYear() %>
    </template:formTr>
	<template:formTr name="�ƻ����" tagID="yearBTr" style="display:none">
		<input name="year" value="<%=YearMonthPlanBean.getYear() %>" type="hidden" />
		<%=YearMonthPlanBean.getYear() %>
    </template:formTr>
    <template:formTr name="�ƻ��·�" tagID="monthTr" style="display:">
    	<input name="month" value="<%=YearMonthPlanBean.getMonth() %>" type="hidden" />
    	<%=YearMonthPlanBean.getMonth() %>
    </template:formTr>
    <template:formTr name="�ƻ�����" isOdd="false">
		<!-- hidden id -->
		<html:hidden property="id"/>
				<html:hidden property="planname" styleClass="inputtext"
					style="width:160px" />
				<html:hidden property="plantype" name="YearMonthPlanBean" styleClass="inputtext"
					style="width:160px" />
				<bean:write name="YearMonthPlanBean" property="planname"/>
			</template:formTr>
    <template:formTr name="�������">
		<span id="loadSpan"><img src='images/indicator.gif'/>���ڼ�������,���Ժ�...</span>
		<span id="tasklistspan" >
	      <table id="tasklisttable" name="tasklisttable" border="0" cellspacing="0" cellpadding="5">
	        <tr valign="middle">
	          <td>          </td>
	        </tr>
	      </table>
		 </span>
    </template:formTr>

    <template:formTr name="�������"  isOdd="false">
      <html:select property="status" styleClass="inputtext" style="width:160">
        <html:option value="1">ͨ������</html:option>
        <html:option value="-1">δͨ������</html:option>
      </html:select>
    </template:formTr>

    <template:formTr name="�������">
		<html:textarea property="approvecontent" cols="36" onkeyup="this.value=this.value.substr(0,250)" rows="3" style="width:160" styleClass="textarea" title="��ע��Ϣ�250������"/>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit property="action" styleClass="button">ȷ��</html:submit>
      </td>
      <td>
        <input name="button" type="reset" class="button" value="����" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>

<iframe id="hiddenIframe" style="display:none"></iframe>

</body>
