<%@include file="/common/header.jsp"%>
<jsp:useBean id="YearMonthPlanBean" class="com.cabletech.planinfo.beans.YearMonthPlanBean" scope="request"/>

<script language="javascript" type="">
//加载子任务
function loadTaskList() {
	var pageName = "${ctx}/YearMonthPlanAction.do?method=getTaskListByPlanid&fID=2&bdate=&planid="+YearMonthPlanBean.id.value;
	hiddenIframe.location.replace(pageName);
}
//根据monthv的值确定显示年、月计划
function setMonthTr(monthv){
	if(monthv == null || monthv.length == 0 || monthv == "null"){
		monthTr.style.display = "none";
		yearATr.style.display = "none";
		yearBTr.style.display = "";
	}
}
//返回
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
<template:titile value="审批巡检计划"/>
<html:form action="/YearMonthPlanAction?method=ApprovePlan" >
  <template:formTable>
    <template:formTr name="计划年度" tagID="yearATr" isOdd="false" style="display:">
    	<input name="year" value="<%=YearMonthPlanBean.getYear() %>" type="hidden" />
    	<%=YearMonthPlanBean.getYear() %>
    </template:formTr>
	<template:formTr name="计划年度" tagID="yearBTr" style="display:none">
		<input name="year" value="<%=YearMonthPlanBean.getYear() %>" type="hidden" />
		<%=YearMonthPlanBean.getYear() %>
    </template:formTr>
    <template:formTr name="计划月份" tagID="monthTr" style="display:">
    	<input name="month" value="<%=YearMonthPlanBean.getMonth() %>" type="hidden" />
    	<%=YearMonthPlanBean.getMonth() %>
    </template:formTr>
    <template:formTr name="计划名称" isOdd="false">
		<!-- hidden id -->
		<html:hidden property="id"/>
				<html:hidden property="planname" styleClass="inputtext"
					style="width:160px" />
				<html:hidden property="plantype" name="YearMonthPlanBean" styleClass="inputtext"
					style="width:160px" />
				<bean:write name="YearMonthPlanBean" property="planname"/>
			</template:formTr>
    <template:formTr name="任务分配">
		<span id="loadSpan"><img src='images/indicator.gif'/>正在加载任务,请稍候...</span>
		<span id="tasklistspan" >
	      <table id="tasklisttable" name="tasklisttable" border="0" cellspacing="0" cellpadding="5">
	        <tr valign="middle">
	          <td>          </td>
	        </tr>
	      </table>
		 </span>
    </template:formTr>

    <template:formTr name="审批结果"  isOdd="false">
      <html:select property="status" styleClass="inputtext" style="width:160">
        <html:option value="1">通过审批</html:option>
        <html:option value="-1">未通过审批</html:option>
      </html:select>
    </template:formTr>

    <template:formTr name="审批意见">
		<html:textarea property="approvecontent" cols="36" onkeyup="this.value=this.value.substr(0,250)" rows="3" style="width:160" styleClass="textarea" title="备注信息最长250个汉字"/>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit property="action" styleClass="button">确定</html:submit>
      </td>
      <td>
        <input name="button" type="reset" class="button" value="返回" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>

<iframe id="hiddenIframe" style="display:none"></iframe>

</body>
