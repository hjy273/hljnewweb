<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<template:titile value="��ǰ������Ϣ"/>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/userinfoAction.do?method=queryUserinfo" id="currentRowObject" pagesize="18">
  <display:column property="pid" title="��ʶ����"/>
  <display:column property="executorid" title="�ϱ���"/>
  <display:column property="executetime" title="�ϱ�ʱ��"/>
  <display:column property="operationcode" title="��������"/>
</display:table>
