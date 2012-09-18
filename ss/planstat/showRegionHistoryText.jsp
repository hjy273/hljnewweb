<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />

<br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>各地市级移动公司<%=(String)session.getAttribute("mystatdate") %>短信发送统计</div><hr width='100%' size='1'>
<display:table name="sessionScope.regionhistoryinfo"   id="currentRowObject"  pagesize="18">
     <display:column property="regionname" title="区域" sortable="true"/>
     <display:column property="dailykm" title="巡检里程(KM)" sortable="true"/>
     <display:column property="totalnum" title="总数" sortable="true" />
	 <display:column property="patrolnum" title="巡检" sortable="true" href="${ctx}/RealTimeAction.do?method=showRegionHistoryPerReg&handlestate=4,7,8,10,12" paramId="regionid" paramProperty="regionid"/>
	 <display:column property="watchnum" title="盯防" sortable="true" href="${ctx}/RealTimeAction.do?method=showRegionHistoryPerReg&handlestate=6,9,11" paramId="regionid" paramProperty="regionid"/>
	 <display:column property="collectnum" title="采集" sortable="true" href="${ctx}/RealTimeAction.do?method=showRegionHistoryPerReg&handlestate=3" paramId="regionid" paramProperty="regionid"/> 
	 <display:column property="troublenum" title="隐患" sortable="true" href="${ctx}/RealTimeAction.do?method=showRegionHistoryPerReg&handlestate=20" paramId="regionid" paramProperty="regionid"/> 
	 <display:column property="othernum" title="其它" sortable="true" href="${ctx}/RealTimeAction.do?method=showRegionHistoryPerReg&handlestate=0,1,2,13,14,15,16,19" paramId="regionid" paramProperty="regionid"/> 
</display:table>