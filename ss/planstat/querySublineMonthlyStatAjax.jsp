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
         alert("��ѡ��Ҫͳ�Ƶ��߶�");
         return false;
     }
     return true;    
  }
</script>
<BR/>
<body onLoad="retrieveURL('${ctx}/PlanMonthlyStatAction.do?method=loadSubline');">
<template:titile value="�߶���ͳ�Ʋ�ѯ"/>
<html:form method="Post" action="/PlanMonthlyStatAction?method=showSublineMonthlyStat" onsubmit="return set_checkvalue();">
  <input  id="lineid"  name="linename" type="hidden"/>
  <input  id="sublineid"  name="sublinename" type="hidden"/>
  <template:formTable contentwidth="200" namewidth="300">
    <logic:notEmpty name="lineinfo">
      <template:formTr name="��·����" tagID="lineTr">
        <select name="lineID" class="inputtext" style="width:180px" id="lId"  onChange="retrieveURL('${ctx}/PlanMonthlyStatAction.do?method=loadSubline&lineID=' + this.value);">
          <option value="">��ѡ��...</option>
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

      <template:formTr name="�߶�����" isOdd="false">
        <span id="sublineSpan"></span>
      </template:formTr>

    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="false">
      <apptag:getYearOptions/>
      <html:select property="endYear" styleClass="inputtext" style="width:180">
        <html:options collection="yearCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="false">
      <html:select property="endMonth" styleClass="inputtext" style="width:180">
        <html:option value="01">һ��</html:option>
        <html:option value="02">����</html:option>
        <html:option value="03">����</html:option>
        <html:option value="04">����</html:option>
        <html:option value="05">����</html:option>
        <html:option value="06">����</html:option>
        <html:option value="07">����</html:option>
        <html:option value="08">����</html:option>
        <html:option value="09">����</html:option>
        <html:option value="10">ʮ��</html:option>
        <html:option value="11">ʮһ��</html:option>
        <html:option value="12">ʮ����</html:option>
      </html:select>
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

