<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td valign="middle" align="center" class="title2">当前正在执行盯防信息</td></tr></table>

<display:table name="sessionScope.queryResult_THREE"  id="resultList" pagesize="18">
	<logic:equal value="group" name="PMType">
    	<display:column property="patrol" title="巡检维护组"  sortable="true"/>
	</logic:equal>
    <logic:notEqual value="group" name="PMType">
    	<display:column property="patrol" title="执行巡检维护人"  sortable="true"/>
    </logic:notEqual>

	<display:column property="sim" title="设备手机号码"  sortable="true"/>
	<display:column property="exetime" title="执行时间"  sortable="true"/>
	<display:column property="watch" title="外力盯防"  sortable="true"/>
	<display:column property="place" title="盯防地点"  sortable="true"/>

</display:table>

</body>
</html>

