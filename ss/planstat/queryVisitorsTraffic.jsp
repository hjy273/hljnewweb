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
         alert("请选择起始日期");
         return false;
     }else if (LogPathBean.endDate.value == ""){
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
<template:titile value="模块访问量查询"/>
<html:form method="Post" action="/LogPathAction?method=showVisitorsTraffic" onsubmit="return checkvalue();">
  <template:formTable contentwidth="200" namewidth="300">
    <template:formTr name="所属区域" isOdd="false">
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
    <template:formTr name="类&nbsp;&nbsp;&nbsp;&nbsp;型" isOdd="false">
      <html:select property="queryType" styleClass="inputtext" style="width:180" onChange="retrieveURL('${ctx}/LogPathAction.do?method=loadCon&queryType=' + this.value,this.value);">
        <html:option value="0">不限</html:option>
        <html:option value="1">移动公司</html:option>
        <html:option value="2">代维公司</html:option>
      </html:select>
    </template:formTr>
    <tr id ="con" class=trwhite style="display:none">
       <td class="tdulleft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;代维公司：</td>
       <td class="tdulright">
          <span id="conSpan"></span>
       </td>
    </tr>
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
