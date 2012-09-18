<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>

<script language="javascript" type="">
  var req;
  var which;

  function retrieveURL(url) {
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
        document.getElementById("sublineSpan").innerHTML = req.responseText;
      } else {
        alert("Problem: " + req.statusText);
      }
    }
  }
  function set_checkvalue(){
     document.all.lineid.value = document.all.lId.options[document.all.lId.selectedIndex].text;
     document.all.sublineid.value = document.all.sId.options[document.all.sId.selectedIndex].text;
     if (document.all.sId.options[document.all.sId.selectedIndex].value==""){
         alert("请选择要统计的线段");
         return false;
     }
     return true;    
  }
</script>
<BR/>
<body onLoad="retrieveURL('${ctx}/PlanMonthlyStatAction.do?method=loadSubline');">
<template:titile value="线段月统计查询"/>
<html:form method="Post" action="/PlanMonthlyStatAction?method=showSublineMonthlyStat" onsubmit="return set_checkvalue();">
  <input  id="lineid"  name="linename" type="hidden"/>
  <input  id="sublineid"  name="sublinename" type="hidden"/>
  <template:formTable contentwidth="200" namewidth="300">
    <logic:notEmpty name="lineinfo">
      <template:formTr name="线路名称" tagID="lineTr">
        <select name="lineID" class="inputtext" style="width:180px" id="lId"  onChange="retrieveURL('${ctx}/PlanMonthlyStatAction.do?method=loadSubline&lineID=' + this.value);">
          <option value="">请选择...</option>
          <logic:present name="lineinfo">
            <logic:iterate id="lineinfoId" name="lineinfo">
              <option value="<bean:write name="lineinfoId" property="lineid"/>">
                <bean:write name="lineinfoId" property="linename"/>
              </option>
            </logic:iterate>
          </logic:present>
        </select>
      </template:formTr>
    </logic:notEmpty>

      <template:formTr name="线段名称" isOdd="false">
        <span id="sublineSpan"></span>
      </template:formTr>

    <template:formTr name="年&nbsp;&nbsp;&nbsp;&nbsp;份" isOdd="false">
      <apptag:getYearOptions/>
      <html:select property="endYear" styleClass="inputtext" style="width:180">
        <html:options collection="yearCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="月&nbsp;&nbsp;&nbsp;&nbsp;份" isOdd="false">
      <html:select property="endMonth" styleClass="inputtext" style="width:180">
        <html:option value="01">一月</html:option>
        <html:option value="02">二月</html:option>
        <html:option value="03">三月</html:option>
        <html:option value="04">四月</html:option>
        <html:option value="05">五月</html:option>
        <html:option value="06">六月</html:option>
        <html:option value="07">七月</html:option>
        <html:option value="08">八月</html:option>
        <html:option value="09">九月</html:option>
        <html:option value="10">十月</html:option>
        <html:option value="11">十一月</html:option>
        <html:option value="12">十二月</html:option>
      </html:select>
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

