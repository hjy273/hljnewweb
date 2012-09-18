
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<logic:equal value="SECTION" scope="session" name="type">
<display:table name="sessionScope.noteNum"   id="currentRowObject"  pagesize="14">
     <display:column property="titlename" title="名称" sortable="true"/>
     <display:column property="total" title="总数" />
	 <display:column property="patrol" title="巡检" />
	 <display:column property="watch" title="盯防"  />
	 <display:column property="collect" title="采集" /> 
	 <display:column property="trouble" title="隐患"  /> 
	 <display:column property="other" title="其它"/> 
</display:table>
</logic:equal>
<logic:equal value="sim" scope="session" name="type">
<display:table name="sessionScope.noteNum"   id="currentRowObject"  pagesize="14"> 
     <display:column property="titlename" title="巡检维护组" sortable="true"/>
	 <display:column property="simid" title="SIM卡号" />
     <display:column property="firsttime" title="第一条时间" />
	 <display:column property="lasttime" title="最近一条时间" />
     <display:column property="total" title="总数" />
	 <display:column property="patrol" title="巡检" />
	 <display:column property="watch" title="盯防"  />
	 <display:column property="collect" title="采集" /> 
	 <display:column property="trouble" title="隐患"  /> 
	 <display:column property="other" title="其它"  /> 
</display:table>
</logic:equal>
