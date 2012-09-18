<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<body style="width: 95%">
	<div class='title2'
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'>
		${CMMonthlyStatConBean.regionName }����ά��˾${CMMonthlyStatConBean.endYear
		}��ƻ�ִ�����
	</div>
	<hr width='100%' size='1'>
	<display:table
		name="sessionScope.CITY_MOBILE_YEAR_STAT_CONTRACTOR_PATROL_LIST"
		id="currentRowObject" pagesize="12">
		<display:column property="contractorname" title="��ά��˾" sortable="true" />
		<display:column property="planpoint" title="�ƻ�Ѳ����" sortable="true" />
		<display:column property="factpoint" title="ʵ��Ѳ����" sortable="true" />
		<display:column property="patrolp" title="Ѳ����(%)" sortable="true" />
		<display:column property="sublinekm" title="�ƻ�·�ɳ��ȣ�km��"
			sortable="true" />
		<display:column property="patrolkm" title="Ѳ��·�ɳ��ȣ�km��" sortable="true" />
		<display:column property="plannum" title="�ƶ��ƻ�����" sortable="true" />
		<display:column property="noexecuteplannum" title="δִ�мƻ�����"
			sortable="true" />
		<display:column property="nocompleteplannum" title="δ��ɼƻ�����"
			sortable="true" />
		<display:column property="completeplannum" title="��ɼƻ�����"
			sortable="true" />
		<display:column media="html" title="���˽��">
			<bean:define id="examineResult" name="currentRowObject"
				property="examineresult" />
			<img src="${ctx}/images/${examineResult }.jpg" height="10" width="50" />
		</display:column>
	</display:table>
</body>