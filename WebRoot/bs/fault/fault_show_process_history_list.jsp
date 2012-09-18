<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<display:table name="sessionScope.FAULT_PROCESS_LIST" id="row" export="false">
		<display:column property="process_state_dis" title="故障状态" />
		<display:column property="process_time_dis" title="处理时间" />
		<display:column property="person_name" title="故障处理人" />
	</display:table>
</html>
