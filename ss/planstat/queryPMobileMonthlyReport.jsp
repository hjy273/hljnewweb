<%@include file="/common/header.jsp"%>

<BR/>

<script language="javascript" type="">

</script>

<body>
<template:titile value="ʡ�ƶ���˾��ͳ�Ʊ���"/>
<html:form method="Post" action="/MonthlyReportAction?method=showPMobileMonthlyReport">
  <template:formTable contentwidth="200" namewidth="300">
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
          <option selected value="06">����</option>
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

