<%@include file="/common/header.jsp"%>
<script language="JavaScript" type="">

function GetSelectDateTHIS(strID) {
	document.all.item(strID).value = getPopDate(document.all.item(strID).value);

	if(checkBDate()){
		return true;
	}else{
		return false;
	}
}

function getPlanFormOpen(){
	var formPop = window.open('','formPop','scrollbars=yes,width=800,height=660,status=yes');
    formPop.resizeTo(800,screen.height);
	formPop.focus();
    queryConditionForm.submit();
}

function getPlanForm(){
	queryConditionForm.patrolname.value = queryConditionForm.patrolid.options[queryConditionForm.patrolid.selectedIndex].text;
    queryConditionForm.submit();
}

function makeSize(enlargeFlag){
	if(enlargeFlag == 0){
		
		mainSpan.style.display = "none";
		iframeDiv.style.height = "595";
	}else{
		mainSpan.style.display = "";
		iframeDiv.style.height = "385";
	}
}

</script><body>
<span id="mainSpan" style="display:">
<template:titile value="�¶��������ͳ��"/>
<apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true"/>
<html:form method="Post" action="/StatisticAction.do?method=getMonthPlanTaskForm"  target="planformFrame">
  <template:formTable>
    <template:formTr name="Ѳ��Ա">
      <html:select property="patrolid" styleClass="inputtext" style="width:160">
        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
      </html:select>
      <html:hidden property="patrolname"/>
	  <html:hidden property="cyctype" value="month"/>
    </template:formTr>
    <template:formTr name="ͳ�����" isOdd="false">
      <html:select property="year" styleClass="inputtext" style="width:160">
        <html:option value="2004">2004</html:option>
        <html:option value="2005">2005</html:option>
        <html:option value="2006">2006</html:option>
        <html:option value="2007">2007</html:option>
        <html:option value="2008">2008</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="ͳ���·�">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <html:select property="month" styleClass="inputtext" style="width:160">
              <html:option value="01">1 ��</html:option>
              <html:option value="02">2 ��</html:option>
              <html:option value="03">3 ��</html:option>
              <html:option value="04">4 ��</html:option>
              <html:option value="05">5 ��</html:option>
              <html:option value="06">6 ��</html:option>
              <html:option value="07">7 ��</html:option>
              <html:option value="08">8 ��</html:option>
              <html:option value="09">9 ��</html:option>
              <html:option value="10">10 ��</html:option>
              <html:option value="11">11 ��</html:option>
              <html:option value="12">12 ��</html:option>
            </html:select>
          </td>
          <td align="right">
            <html:button property="action" styleClass="button" onclick="getPlanForm()">��ѯ</html:button>
            &nbsp;&nbsp;&nbsp;
            <html:reset property="action" styleClass="button">ȡ��</html:reset>
          </td>
        </tr>
      </table>
    </template:formTr>
  </template:formTable>
</html:form>
</span>
<div id ="iframeDiv" style="height:385">
  <iframe name=planformFrame border=0 marginWidth=0 marginHeight=0 src="${ctx}/common/blank.html" frameBorder=0 width="100%" scrolling="yes" height="100%">  </iframe>
</div>

</body>
