<%@include file="/common/header.jsp"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<%@page import="com.cabletech.partmanage.beans.*;"%>
<html>
<head>
<title>info</title>
</head>
<body>
<br/>
<template:titile value="故障信息"/><br/><br/>
<table width="450" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
  <tr>
    <th class="thlist"  width="150">故障详细</th>
  </tr>
  <tr class=trcolor>
    <td class="tdulright"><br/>
      &nbsp;&nbsp;&nbsp;&nbsp;在<font color="blue">古城-十河</font>线路<br/>
      &nbsp;&nbsp;&nbsp;&nbsp;<font color="blue">元疃－吴店段</font>线段<font color="blue"><br/>
      &nbsp;&nbsp;&nbsp;&nbsp;杆084点</font>附近<font color="blue"></font>处出现故障。
        <br/><br/>
    </td>

  </tr>
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="6">      </td>
    </tr>
    <tr>
      <td align="right">
        <table border="0" align="center" cellpadding="0" cellspacing="0">
          <tr align="center">
            <td>
              <input type="button" name="action" value="派 单"  class="button">
            </td>
            <td>
              <input type="button" name="action" value="报 警" class="button">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>

</body>
</html>
