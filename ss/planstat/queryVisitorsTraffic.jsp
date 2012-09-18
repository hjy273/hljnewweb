<%@include file="/common/header.jsp"%>

<script language="javascript" type="">
  var req;
  var which;
  var abc;

  function retrieveURL(url,type) {
    if (type==2){
       document.getElementById("con").style.display="block";
    }else{
       document.getElementById("con").style.display="none";
    }
    url= url + '&regionid=' + document.all.regionID.options[document.all.regionID.selectedIndex].value;
    if (window.XMLHttpRequest) { // Non-IE browsers
      req = new XMLHttpRequest();
      req.onreadystatechange = processStateChange;
      try {
        req.open("GET", url, true);
      } catch (e) {
        alert(e);
      }
      req.send(null);
    } else if (window.ActiveXObject) { // IE
      req = new ActiveXObject("Microsoft.XMLHTTP");
      if (req) {
        req.onreadystatechange = processStateChange;
        req.open("GET", url, true);
        req.send();
      }
    }
    
  }

  function processStateChange() {
    if (req.readyState == 4) { // Complete
      if (req.status == 200) { // OK response
        document.getElementById("conSpan").innerHTML = req.responseText;
      } else {
        alert("Problem: " + req.statusText);
      }
    }
  }

  function checkvalue(){
     var startyear = LogPathBean.startDate.value.substring(0,4);
     var startmonth = parseInt(LogPathBean.startDate.value.substring(5,7) ,10) - 1;
     var startday = parseInt(LogPathBean.startDate.value.substring(8,10),10);
     var thestartdate = new Date(startyear,startmonth,startday);

     var endyear = LogPathBean.endDate.value.substring(0,4);
     var endmonth = parseInt(LogPathBean.endDate.value.substring(5,7) ,10) - 1;
     var endday = parseInt(LogPathBean.endDate.value.substring(8,10),10);
     var theenddate = new Date(endyear,endmonth,endday);

     var todaydate = new Date();
     if (LogPathBean.startDate.value == ""){
         alert("��ѡ����ʼ����");
         return false;
     }else if (LogPathBean.endDate.value == ""){
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
     }
     return true;    
  }

 function clearCon(){
   LogPathBean.queryType.value='0';
   document.getElementById("con").style.display="none";
 }
</script>
<BR/>

<body >
<template:titile value="ģ���������ѯ"/>
<html:form method="Post" action="/LogPathAction?method=showVisitorsTraffic" onsubmit="return checkvalue();">
  <template:formTable contentwidth="200" namewidth="300">
    <template:formTr name="��������" isOdd="false">
      <select name="regionID" class="inputtext" style="width:180px" id="rId" onchange="clearCon()">
        <logic:present name="reginfo">
          <logic:iterate id="reginfoId" name="reginfo">
            <option value="<bean:write name="reginfoId" property="regionid"/>">
                 <bean:write name="reginfoId" property="regionname"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="false">
      <html:select property="queryType" styleClass="inputtext" style="width:180" onChange="retrieveURL('${ctx}/LogPathAction.do?method=loadCon&queryType=' + this.value,this.value);">
        <html:option value="0">����</html:option>
        <html:option value="1">�ƶ���˾</html:option>
        <html:option value="2">��ά��˾</html:option>
      </html:select>
    </template:formTr>
    <tr id ="con" class=trwhite style="display:none">
       <td class="tdulleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ά��˾��</td>
       <td class="tdulright">
          <span id="conSpan"></span>
       </td>
    </tr>
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
