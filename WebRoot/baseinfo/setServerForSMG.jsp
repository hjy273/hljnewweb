<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<title>���÷���������</title>
<template:formTable>
  <template:formTr name="����������">
    <input type="text" name="serverid" class="inputtext" style="width:220">
  </template:formTr>
  <template:formSubmit>
    <td>
      <input type="button" value="ȷ��" class="button" onclick="opener.initTerminal()">
    </td>
    <td>
      <input type="button" value="ȡ��" class="button" onclick="self.close()">
    </td>
  </template:formSubmit>
</template:formTable>
