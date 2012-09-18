<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
function addGoBack(){
    location.href = "${ctx}/stataction.do?method=loadQueryForm";
    return true;
}
</script>
<br>
<template:titile value="查询统计信息"/>
<html:form method="Post" action="/stataction.do?method=getStatInfo">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="工程名称">
      <html:text property="changename" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="工程性质">
      <html:text property="changepro" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="工程状态" >
    <html:select property="step" styleClass="inputtext" style="width:160">
      <html:option value="">不限</html:option>
        <html:option value="A">待审定</html:option>
         <html:option value="B1">通过监理审定</html:option>
         <html:option value="B2">通过移动审定</html:option>
          <html:option value="C">施工准备</html:option>
          <html:option value="D">开始施工</html:option>
          <html:option value="E">施工完毕</html:option>
          <html:option value="F">验收完毕</html:option>
          <html:option value="G">已经归档</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="网络性质">
      <apptag:setSelectOptions columnName1="name" columnName2="code" tableName="lineclassdic" valueName="linetypeColl"/>
      <html:select property="lineclass" styleClass="inputtext" style="width:160">
        <html:option value="">不限</html:option>
        <html:options collection="linetypeColl" property="value" labelProperty="label"/>
      </html:select>
      </template:formTr>
      <template:formTr name="填写开始日期">
        <html:text property="begintime" styleClass="inputtext" style="width:160" maxlength="45"/>
        <apptag:date property="begintime"/>
      </template:formTr>
      <template:formTr name="填写结束日期">
        <html:text property="endtime" styleClass="inputtext" style="width:160" maxlength="45"/>
        <apptag:date property="endtime"/>
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
