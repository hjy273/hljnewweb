<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td valign="middle" align="center" class="title2">Ѳ����Ա��̬</td></tr></table>

<display:table name="sessionScope.queryresult_TWO" id="currentRowObject"  pagesize="3">
  <display:column property="patrolname" title="Ѳ��ά����" sortable="true"/>
  <display:column property="sim" title="�豸�ֻ�����" sortable="true"/>
  <display:column property="parentid" title="��ά��λ" sortable="true"/>
  <display:column property="jobinfo" title="������Ϣ" sortable="true"/>
</display:table>
