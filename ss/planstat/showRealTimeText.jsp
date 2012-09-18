<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>今日实时短信发送情况</div><hr width='100%' size='1'>
<display:table name="sessionScope.realtimeinfo"   id="currentRowObject"  pagesize="18">
	 <logic:equal value="group" name="PMType">
       <display:column property="name" title="巡检维护组" sortable="true"/>
     </logic:equal>
     <logic:notEqual value="group" name="PMType">
       <display:column property="name" title="巡检员" sortable="true"/>
     </logic:notEqual>
	 <display:column property="simnumber" title="SIM卡号" sortable="true"/>
     <display:column property="minarrivetime" title="第一条时间" sortable="true"/>
	 <display:column property="arrivetime" title="最近一条时间" sortable="true"/>
     <display:column property="total" title="总数" sortable="true" />
	 <display:column property="patrol" title="巡检" sortable="true" href="${ctx}/RealTimeAction.do?method=showRealTimePerCard&handlestate=4,7,8,10,12" paramId="simnumber" paramProperty="simnumber"/>
	 <display:column property="watch" title="盯防" sortable="true" href="${ctx}/RealTimeAction.do?method=showRealTimePerCard&handlestate=6,9,11" paramId="simnumber" paramProperty="simnumber"/>
	 <display:column property="collect" title="采集" sortable="true" href="${ctx}/RealTimeAction.do?method=showRealTimePerCard&handlestate=3" paramId="simnumber" paramProperty="simnumber"/> 
	 <display:column property="trouble" title="隐患" sortable="true" href="${ctx}/RealTimeAction.do?method=showRealTimePerCard&handlestate=20" paramId="simnumber" paramProperty="simnumber"/> 
	 <display:column property="other" title="其它" sortable="true" href="${ctx}/RealTimeAction.do?method=showRealTimePerCard&handlestate=0,1,2,13,14,15,16,19" paramId="simnumber" paramProperty="simnumber"/> 

</display:table>