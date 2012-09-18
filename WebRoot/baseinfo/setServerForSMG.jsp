<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<title>设置服务器号码</title>
<template:formTable>
  <template:formTr name="服务器号码">
    <input type="text" name="serverid" class="inputtext" style="width:220">
  </template:formTr>
  <template:formSubmit>
    <td>
      <input type="button" value="确定" class="button" onclick="opener.initTerminal()">
    </td>
    <td>
      <input type="button" value="取消" class="button" onclick="self.close()">
    </td>
  </template:formSubmit>
</template:formTable>
