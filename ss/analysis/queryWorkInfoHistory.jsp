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
         alert("��ѡ����ʼ����");
         return false;
     }else if (WorkInfoHistoryConBean.endDate.value == ""){
         alert("��ѡ����ֹ����");
         return false;
     }else if(theenddate < thestartdate){
		alert("��ֹ���ڲ���С����ʼ���ڣ�");
		return false;
	 }else if(thestartdate > todaydate){
		alert("��ʼ���ڲ��ܴ��ڵ�ǰ���ڣ�");
		return false;    
     }else if(theenddate > todaydate){
		alert("��ֹ���ڲ��ܴ��ڵ�ǰ���ڣ�");
		return false;
     }else if(startmonth != endmonth){
		alert("��ֹ���ڲ��ܿ��£�");
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
<template:titile value="Ѳ�칤��������ʷ��ѯ"/>
<html:form method="Post" action="/WorkInfoHistoryAction?method=getOnlineNumberForDays" onsubmit="return checkvalue();">
  <template:formTable contentwidth="200" namewidth="300">
    <template:formTr name="��ѯ��Χ" isOdd="false">
	   <select name="rangeID" class="inputtext" style="width:200px">
	      <logic:equal value="11" name="LOGIN_USER" property="type">
	        <option value="11">ȫʡ����</option>
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
    <template:formTr name="��ʼ����" isOdd="false">
      <html:text readonly="true" property="startDate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:200" maxlength="12"/>
    </template:formTr>
    <template:formTr name="��ֹ����" isOdd="false">
      <html:text readonly="true" property="endDate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:200" maxlength="12"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
</body>