<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<%
int count = 7;
BasicDynaBean  MonthlyStatAllDynaBean = (BasicDynaBean)request.getAttribute( "monthlystatallDynaBean" );
String patrolname = (String)request.getSession().getAttribute("patrolname");
String endyearstat = (String)request.getSession().getAttribute("endYearStat");
String endmonthstat = (String)request.getSession().getAttribute("endMonthStat");
String patrolid = (String)request.getSession().getAttribute("patrolID");
%>
<script language="JavaScript" type="">
function addGoFirst()
{
  window.history.back();

}  

function addGoQuery()
{
  var url="${ctx}/planstat/queryPatrolMonthlyStat.jsp";
  self.location.replace(url);

}

function ghbq(td)
{
  var tr = td.parentElement.cells;
  var ob = obody.rows;
  if (tr[td.cellIndex]==document.getElementById("workdaysinfopanel")){
	document.getElementById("workdaylist").src="${ctx}/PlanMonthlyStatAction.do?method=showWorkDaysInfo";
  }
  if (tr[td.cellIndex]==document.getElementById("sublineinfopanel")){
	document.getElementById("sublineinfo").src="${ctx}/PlanMonthlyStatAction.do?method=showSublineinfo";
  }
  if (tr[td.cellIndex]==document.getElementById("companalysispanel")){
	document.getElementById("companalysis").src="${ctx}/PlanMonthlyStatAction.do?method=showCompAnalysisinfo";
  }


  for(var ii=0; ii<tr.length-4; ii++)
  {
    tr[ii].className = (td.cellIndex==ii)?"ooihj":"ooihs";
    ob[ii].style.display = (td.cellIndex==ii)?"block":"none";
  }
}  
</script>

<br>

<body>

<table class="ooib" id="obody" border="0" cellspacing="0" cellpadding="0" width="100%" height="94%">
   <tr>
     <td>
        <br>
		<logic:equal value="group" name="PMType">
		   <template:titile value="巡检维护组月统计查询详细信息"/>
		</logic:equal>
		<logic:notEqual value="group" name="PMType">
		   <template:titile value="巡检人员月统计查询详细信息"/>
		</logic:notEqual>
		<br>
		<table width="500" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout"><tr><th class="thlist" align="center" width="300">项目</th><th class="thlist" align="center" width="200">内容</th></tr>
		  <logic:equal value="group" name="PMType">
	        <tr class=trcolor ><td class="tdulleft">巡检维护组：</td><td class="tdulright">
	        <%=patrolname%>
	        </td></tr>
	      </logic:equal>
	      <logic:notEqual value="group" name="PMType">
	        <tr class=trcolor ><td class="tdulleft">巡检员：</td><td class="tdulright">
	        <%=patrolname%>
	        </td></tr>
	      </logic:notEqual>
	         <tr class=trcolor ><td class="tdulleft">年月份：</td><td class="tdulright">
	            <%=MonthlyStatAllDynaBean.get("statdate") %>
	         </td></tr>
	         <tr class=trcolor ><td class="tdulleft">计划巡检总次数：</td><td class="tdulright">
	            <%=MonthlyStatAllDynaBean.get("planpoint") %>
	         </td></tr>
	         <tr class=trcolor ><td class="tdulleft">实际巡检总次数：</td><td class="tdulright">
	            <%=MonthlyStatAllDynaBean.get("factpoint") %>
	         </td></tr>
	         <tr class=trcolor ><td class="tdulleft">巡检率：</td><td class="tdulright">
	            <%=MonthlyStatAllDynaBean.get("overallpatrolp") %>%
	         </td></tr>
	         
	         <tr class=trcolor ><td class="tdulleft">隐患数量：</td><td class="tdulright">
	            <%=MonthlyStatAllDynaBean.get("trouble") %>
	         </td></tr>
	         <tr class=trcolor ><td class="tdulleft">考核结果：</td><td class="tdulright">
	            <% 
		        String examineresult = (String)MonthlyStatAllDynaBean.get("examineresult");
		        int i = Integer.parseInt(examineresult);
		  		switch(i) {
		  			case 1 : out.print("<img src=\""+request.getContextPath()+"/images/1.jpg\" height=\"10\" width=\"50\" />"); break; 
		  			case 2 : out.print("<img src=\""+request.getContextPath()+"/images/2.jpg\" height=\"10\" width=\"50\" />"); break;
		  			case 3 : out.print("<img src=\""+request.getContextPath()+"/images/3.jpg\" height=\"10\" width=\"50\" />"); break;
		  			case 4 : out.print("<img src=\""+request.getContextPath()+"/images/4.jpg\" height=\"10\" width=\"50\" />"); break;
		  			case 5 : out.print("<img src=\""+request.getContextPath()+"/images/5.jpg\" height=\"10\" width=\"50\" />"); break;
		  			default: out.print("<img src=\""+request.getContextPath()+"/images/0.jpg\" height=\"10\" width=\"50\" />");
				}
		        %>
	         </td></tr>
        </table>

     </td>
   </tr>
   <tr style="display: none">
       <td>
		<table>
		  <tr>
		    <td>
		      <div>
		        <iframe name="mymonthlystatdetaillist" marginWidth="0" marginHeight="0" src="${ctx}/planstat/mymonthlystatdetail.jsp" frameBorder=0 width="100%" scrolling=auto height="400"></iframe>
		      </div>
		    </td>
		  </tr>
		</table>
       </td>
    </tr>
    <tr style="display: none">
	    <td>
	      <div>
	          <iframe id="sublineinfo" marginWidth="0" marginHeight="0" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
	      </div> 
	    </td>    
    </tr>
    <tr style="display: none">
	    <td>
	      <div>
	          <iframe id="workdaylist" marginWidth="0" marginHeight="0" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
	      </div> 
	    </td>
    </tr>
    <tr style="display: none">
    	<td>
	      <div>
	          <iframe id="companalysis" marginWidth="0" marginHeight="0" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
	      </div> 
	    </td>
    </tr>
</table>

<table class="ooih" border="0" cellspacing="0" cellpadding="0" width="400" height="19">
  <tr>
    <td class="ooihj" nowrap onclick="ghbq(this)">总体信息</td>
    <td class="ooihs" nowrap onclick="ghbq(this)">计划信息</td>
    <td id="sublineinfopanel" class="ooihs" nowrap onclick="ghbq(this)">巡检线段</td>
    <td id="workdaysinfopanel" class="ooihs" nowrap onclick="ghbq(this)">工作日信息</td>
    <td id="companalysispanel" class="ooihs" nowrap onclick="ghbq(this)">对比分析</td>
    <td class="ooihs" nowrap onclick="addGoFirst()" >上一页面</td>
    <td class="ooihs" nowrap onclick="addGoQuery()" >查询页面</td>
    <td width="100%">&nbsp;</td>
  </tr>
</table>

</body>






