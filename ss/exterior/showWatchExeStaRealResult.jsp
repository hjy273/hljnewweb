<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<%@ taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<link rel="stylesheet" href="${ctx}/css/forOtherSystem.css"
	type="text/css" media="screen, print" />
<display:table name="sessionScope.watchExeStaResultList"
	id="currentRowObject" pagesize="16">
	 <display:column property="regionName" title="区域" sortable="true"/>
	 <display:column property="contractorName" title="代维公司" sortable="true"/>
	 <display:column property="watchSize" title="盯防个数" sortable="true"/>
	 <display:column property="infoNeeded" title="盯防所需信息数" sortable="true"/>
	 <display:column property="infoDone" title="执行信息数" sortable="true"/>
	 <display:column property="watchExecuterate" title="完成率" sortable="true"/>
	 <display:column property="alertCount" title="报警数量" sortable="true"/>
</display:table>
