<%@ include file="/common/header.jsp"%>
<%@ page
	import="com.cabletech.planstat.beans.MonthlyStatCityMobileConBean"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<body style="width: 95%">
	<div class='title2'
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'>
		${CMMonthlyStatConBean.regionName }�ƶ���˾${CMMonthlyStatConBean.endYear
		}��ƻ���Ϣ
	</div>
	<hr width='100%' size='1'>
	<display:table name="sessionScope.CITY_MOBILE_YEAR_STAT_GENERAL_INFO"
		id="currentRowObject" pagesize="12">
		<display:column property="conname" title="��ά��˾" />
		<logic:iterate id="oneLineType" name="lineTypeDictMap">
			<bean:define id="dictKey" name="oneLineType" property="key"></bean:define>
			<bean:define id="dictValue" name="oneLineType" property="value"></bean:define>
			<display:column property="${dictKey}" title="${dictValue}" />
		</logic:iterate>
		<display:column property="total" title="�ܴ���" />
	</display:table>
</body>
