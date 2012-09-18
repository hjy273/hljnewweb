<%@include file="/common/header.jsp"%>

<BR/>

<script language="javascript" type="">

</script>

<body>
<logic:equal value="group" name="PMType">
   <template:titile value="巡检维护组月统计报表"/>
</logic:equal>
<logic:notEqual value="group" name="PMType">
   <template:titile value="巡检人员月统计报表"/>
</logic:notEqual>
<html:form method="Post" action="/MonthlyReportAction?method=showPatrolmanMonthlyReport">
  <template:formTable contentwidth="200" namewidth="300">
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
          <option selected value="06">六月</option>
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

