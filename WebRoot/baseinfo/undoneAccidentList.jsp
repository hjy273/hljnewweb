<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td valign="middle" align="center" class="title2">�ϰ�/������Ϣ</td></tr></table>

<display:table name="sessionScope.queryresult_ONE" pagesize="2" id="currentRowObject">
  <display:column property="type" title="����" sortable="true"/>
  <display:column property="sendtime" title="�ϱ�ʱ��" sortable="true"/>
  <display:column property="reason" title="�ϰ�ԭ��" sortable="true" />
  <display:column property="emlevel" title="���س̶�" sortable="true" />
  <display:column property="subline" title="Ѳ���" sortable="true" />
  <display:column property="point" title="Ѳ���" sortable="true" />
  <display:column property="contractor" title="��ά��λ" sortable="true" />

</display:table>
