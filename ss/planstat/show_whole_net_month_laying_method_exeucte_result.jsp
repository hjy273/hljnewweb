<%@ include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />

<script language="javascript" type="text/javascript">
	
</script>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<div class='title2' style='font-size: 14px; font-weight: 600; bottom: 1'
	align='center'>
	ȫ��<bean:write name="CMMonthlyStatConBean" property="endYear" />
	��<bean:write name="CMMonthlyStatConBean" property="endMonth" />�²�ͬ����ͳ����ϸ��Ϣ
</div>
<hr width='100%' size='1'>
<br>
<display:table name="month_laying_method_exeucte_result_list"
	id="currentRowObject">
	<display:column property="name" title="��·����"  />
	<display:column property="planpoint" title="�ƻ�Ѳ����" sortable="true" />
	<display:column property="factpoint" title="ʵ��Ѳ����" sortable="true" />
	<display:column property="patrolp" title="Ѳ����(%)" sortable="true" />
	<display:column property="sublinekm" title="�ƻ�·�ɳ��ȣ�km��" sortable="true" />
	<display:column property="patrolkm" title="Ѳ��·�ɳ��ȣ�km��" sortable="true" />
</display:table>
