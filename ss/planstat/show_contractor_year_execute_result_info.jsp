<%@ include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<body style="width: 95%">
	<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
		type="text/css" media="screen, print" />
	<div class='title2'
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'>
		<bean:write name="queryCon" property="conName" />
		��˾
		<bean:write name="queryCon" property="endYear" />
		��ͳ����ϸ��Ϣ
	</div>
	<hr width='100%' size='1'>
	<br>
	<table width="80%" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr class="trcolor">
			<td class="tdulleft" style="width: 15%">
				�ƻ���Σ�
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="planpoint" />
			</td>
			<td class="tdulleft" style="width: 15%">
				Ѳ���Σ�
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="factpoint" />
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width: 15%">
				©���Σ�
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="leakpoint" />
			</td>
			<td class="tdulleft" style="width: 15%">
				Ѳ���ʣ�
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="patrolp" />
				%
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width: 15%">
				�ƻ�·�ɳ��ȣ�
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="sublinekm" />
				����
			</td>
			<td class="tdulleft" style="width: 15%">
				Ѳ��·�ɳ��ȣ�
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="patrolkm" />
				����
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width: 15%">
				�ƶ��ƻ�������
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="plannum" />
			</td>
			<td class="tdulleft" style="width: 15%">
				δѲ��ƻ�������
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean"
					property="noexecuteplannum" />
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width: 15%">
				δ��ɼƻ�������
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean"
					property="nocompleteplannum" />
			</td>
			<td class="tdulleft" style="width: 15%">
				��ɼƻ�������
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="completeplannum" />
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width: 15%">
				���˽����
			</td>
			<td class="tdulright" style="width: 85%" colspan="3">
				<img
					src="${ctx}/images/<bean:write name="monthlyconstatDynaBean" property="examineresult" />.jpg" />
			</td>
		</tr>
	</table>
</body>
