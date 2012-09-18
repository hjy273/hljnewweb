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
		公司
		<bean:write name="queryCon" property="endYear" />
		年统计详细信息
	</div>
	<hr width='100%' size='1'>
	<br>
	<table width="80%" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr class="trcolor">
			<td class="tdulleft" style="width: 15%">
				计划点次：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="planpoint" />
			</td>
			<td class="tdulleft" style="width: 15%">
				巡检点次：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="factpoint" />
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width: 15%">
				漏检点次：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="leakpoint" />
			</td>
			<td class="tdulleft" style="width: 15%">
				巡检率：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="patrolp" />
				%
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width: 15%">
				计划路由长度：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="sublinekm" />
				公里
			</td>
			<td class="tdulleft" style="width: 15%">
				巡回路由长度：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="patrolkm" />
				公里
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width: 15%">
				制定计划数量：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="plannum" />
			</td>
			<td class="tdulleft" style="width: 15%">
				未巡检计划数量：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean"
					property="noexecuteplannum" />
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width: 15%">
				未完成计划数量：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean"
					property="nocompleteplannum" />
			</td>
			<td class="tdulleft" style="width: 15%">
				完成计划数量：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="monthlyconstatDynaBean" property="completeplannum" />
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width: 15%">
				考核结果：
			</td>
			<td class="tdulright" style="width: 85%" colspan="3">
				<img
					src="${ctx}/images/<bean:write name="monthlyconstatDynaBean" property="examineresult" />.jpg" />
			</td>
		</tr>
	</table>
</body>
