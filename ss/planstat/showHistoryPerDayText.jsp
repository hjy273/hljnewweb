<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<%
   String historydate = (String)session.getAttribute("historydate");
   String conname = (String)session.getAttribute("mycontractorname");
%>
<br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=conname%><%=historydate %>短信发送情况</div><hr width='100%' size='1'>
<display:table name="sessionScope.historyperdayinfo"   id="currentRowObject"  pagesize="15">
	 <logic:equal value="group" name="PMType">
       <display:column property="patrolname" title="巡检维护组" sortable="true"/>
     </logic:equal>
     <logic:notEqual value="group" name="PMType">
       <display:column property="patrolname" title="巡检员" sortable="true"/>
     </logic:notEqual>
	 <display:column property="simid" title="SIM卡号" sortable="true"/>
     <display:column property="firstdate" title="首条时间" sortable="true" />
     <display:column property="lastdate" title="末条时间" sortable="true" />
     <display:column property="totalnum" title="总数" sortable="true" />
	 <display:column property="patrolnum" title="巡检" sortable="true" href="${ctx}/RealTimeAction.do?method=showHistoryPerCard&handlestate=4,7,8,10,12" paramId="simid" paramProperty="simid"/>
	 <display:column property="watchnum" title="盯防" sortable="true" href="${ctx}/RealTimeAction.do?method=showHistoryPerCard&handlestate=6,9,11" paramId="simid" paramProperty="simid"/>
	 <display:column property="collectnum" title="采集" sortable="true" href="${ctx}/RealTimeAction.do?method=showHistoryPerCard&handlestate=3" paramId="simid" paramProperty="simid"/> 
	 <display:column property="troublenum" title="隐患" sortable="true" href="${ctx}/RealTimeAction.do?method=showHistoryPerCard&handlestate=20" paramId="simid" paramProperty="simid"/> 
	 <display:column property="othernum" title="其它" sortable="true" href="${ctx}/RealTimeAction.do?method=showHistoryPerCard&handlestate=0,1,2,13,14,15,16,19" paramId="simid" paramProperty="simid"/> 
	 <display:column property="dailykm" title="里程" sortable="true" />
	 <display:column property="avgsendtime" title="间隔时间" sortable="true" />
	 <display:column property="avgsenddistance" title="间隔距离" sortable="true" />
</display:table>

 <div align="center">
 	  <table width="620" border="0" align="center">
        <tr style="color:red">
          <td width="7%" scope="col" valign="top">说明：</td>
             <logic:equal value="group" name="PMType"> 
	            <td width="94%" scope="col">
	              1、里程:指巡检维护组在具体某一天的巡检里程数(单位:公里),用来表示相邻两个短信发送点的距离的总和.<br>
	              2、间隔时间:指巡检维护组在具体某一天发送短信的平均间隔时间(单位:秒),用来表示每隔多长时间发送一条短信.<br> 
	              3、间隔距离:指巡检维护组在具体某一天发送短信的平均间隔距离(单位:米),用来表示每隔多远距离发送一条短信.<br> 
			    </td>
			 </logic:equal>
			 <logic:notEqual value="group" name="PMType"> 
	            <td width="94%" scope="col">
	              1、里程:指巡检员在具体某一天的巡检里程数(单位:公里),用来表示相邻两个短信发送点的距离的总和.<br>
	              2、间隔时间:指巡检员在具体某一天发送短信的平均间隔时间(单位:秒),用来表示每隔多长时间发送一条短信.<br> 
	              3、间隔距离:指巡检员在具体某一天发送短信的平均间隔距离(单位:米),用来表示每隔多远距离发送一条短信.<br> 
			    </td>
			 </logic:notEqual>
        </tr>
      </table>
   </div>