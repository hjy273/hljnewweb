<%@include file="/common/header.jsp"%>

<script language="javascript" type="">
  function checkvalue(){
     var startyear = WorkInfoHistoryConBean.startDate.value.substring(0,4);
     var startmonth = parseInt(WorkInfoHistoryConBean.startDate.value.substring(5,7) ,10) - 1;
     var startday = parseInt(WorkInfoHistoryConBean.startDate.value.substring(8,10),10);
     var thestartdate = new Date(startyear,startmonth,startday);

     var endyear = WorkInfoHistoryConBean.endDate.value.substring(0,4);
     var endmonth = parseInt(WorkInfoHistoryConBean.endDate.value.substring(5,7) ,10) - 1;
     var endday = parseInt(WorkInfoHistoryConBean.endDate.value.substring(8,10),10);
     var theenddate = new Date(endyear,endmonth,endday);

     var todaydate = new Date();
     if (WorkInfoHistoryConBean.startDate.value == ""){
         alert("请选择起始日期");
         return false;
     }else if (WorkInfoHistoryConBean.endDate.value == ""){
         alert("请选择终止日期");
         return false;
     }else if(theenddate < thestartdate){
		alert("终止日期不能小于起始日期！");
		return false;
	 }else if(thestartdate > todaydate){
		alert("起始日期不能大于当前日期！");
		return false;    
     }else if(theenddate > todaydate){
		alert("终止日期不能大于当前日期！");
		return false;
     }else if(startmonth != endmonth){
		alert("起止日期不能跨月！");
		return false;    
     }
     return true;    
  }
</script>
<BR/>

<%
 String regionName = (String)request.getSession().getAttribute( "LOGIN_USER_REGION_NAME" );
 String contractorName = (String)request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" );
%>

<body >
<template:titile value="巡检工作过程历史查询"/>
<html:form method="Post" action="/WorkInfoHistoryAction?method=getOnlineNumberForDays" onsubmit="return checkvalue();">
  <template:formTable contentwidth="200" namewidth="300">
    <template:formTr name="查询范围" isOdd="false">
	   <select name="rangeID" class="inputtext" style="width:200px">
	      <logic:equal value="11" name="LOGIN_USER" property="type">
	        <option value="11">全省区域</option>
	        <logic:present name="rangeinfo">
	          <logic:iterate id="rangeinfoId" name="rangeinfo">
	            <option value="<bean:write name="rangeinfoId" property="rangeid"/>">
	                 <bean:write name="rangeinfoId" property="rangename"/>
	            </option>
	          </logic:iterate>
	        </logic:present>
	      </logic:equal>
	      <logic:equal value="12" name="LOGIN_USER" property="type">
	        <option value="12"><%=regionName %></option>
	        <logic:present name="rangeinfo">
	          <logic:iterate id="rangeinfoId" name="rangeinfo">
	            <option value="<bean:write name="rangeinfoId" property="rangeid"/>">
	                 <bean:write name="rangeinfoId" property="rangename"/>
	            </option>
	          </logic:iterate>
	        </logic:present>
	      </logic:equal>
	      <logic:equal value="22" name="LOGIN_USER" property="type">
	        <option value="22"><%=contractorName %></option>
	        <logic:present name="rangeinfo">
	          <logic:iterate id="rangeinfoId" name="rangeinfo">
	            <option value="<bean:write name="rangeinfoId" property="rangeid"/>">
	                 <bean:write name="rangeinfoId" property="rangename"/>
	            </option>
	          </logic:iterate>
	        </logic:present>
	      </logic:equal>
	   </select>
    </template:formTr>
    <template:formTr name="起始日期" isOdd="false">
      <html:text readonly="true" property="startDate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:200" maxlength="12"/>
    </template:formTr>
    <template:formTr name="终止日期" isOdd="false">
      <html:text readonly="true" property="endDate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:200" maxlength="12"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
</body>