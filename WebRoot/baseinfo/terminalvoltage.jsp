<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script type="text/javascript">
<!--

//-->
</script>
<div class='title2' style='font-size:12px; font-weight:600;bottom:1'>�豸��ѹ��ѯ���</div><hr width='100%' size='1'>
<display:table name="sessionScope.lowVoltage" pagesize="18"
	id="currentRowObject">
	<display:column property="deviceid" title="�豸���" />
	<display:column property="simnumber" title="SIM����" />
	<display:column property="queryman" title="Ѳ����" />
	<display:column property="querytime" title="��ѯʱ��" />
	<display:column property="respvoltage" title="�豸��ѹ" />
	<display:column property="resptime" title="����ʱ��" />
</display:table>