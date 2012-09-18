<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<%@page import="com.cabletech.planstat.beans.*"%>
<%@page import="com.cabletech.planstat.domainobjects.PlanStat"%>
<%@page import="com.cabletech.commons.config.*"%>
<head>
	<%
	    String strplanid = (String) request.getSession().getAttribute("theplanid");
	    GisConInfo gisip = GisConInfo.newInstance();
	    System.out.println("IP :" + gisip.getServerip());
	%>

	<script language="JavaScript" type="">
function addGoFirst()
{
  //window.history.back();
  var url = "${ctx}/PlanExeResultAction.do?method=queryPlanExeResult";
  self.location.replace(url);

}
function addGoBack()
{
  var url="${ctx}/planstat/showPlanExeResult.jsp";
  self.location.replace(url);

}
function ghbq(td)
{
  var tr = td.parentNode.cells;
  var ob = document.getElementById("obody").rows;
  if (tr[td.cellIndex]==document.getElementById("leakpanel")){
	document.getElementById("leaklist").src ="${ctx}/planstat/planLeakList.jsp";
  }
  if (tr[td.cellIndex]==document.getElementById("leakGIS")){
	document.getElementById("FrameLeakGIS").src ="Http://<%=gisip.getServerip()%>:<%=gisip.getServerport()%>/WEBGIS/gisextend/igis.jsp?actiontype=006&planid=" + '<%=strplanid%>';
  }
  if (tr[td.cellIndex]==document.getElementById("sublineCover")){
	document.getElementById("FrameSublineCover").src ="Http://<%=gisip.getServerip()%>:<%=gisip.getServerport()%>/WEBGIS/gisextend/igis.jsp?actiontype=007&planid=" + '<%=strplanid%>';
  }
  for(var ii=0; ii<tr.length-3; ii++)
  {
    tr[ii].className = (td.cellIndex==ii)?"ooihj":"ooihs";
    ob[ii].style.display = (td.cellIndex==ii)?"":"none";
  }
}

function toInputNoFinishReson(planid, executorname) {
	  var url="${ctx}/planstat/inputNofinishReason.jsp?planid="+planid+"&executorname="+executorname;
  	self.location.replace(url);
}
</script>
</head>

<body>
	<table class="ooib" id="obody" border="0" cellspacing="0"
		cellpadding="0" width="100%" height="96%" style="border: 0px;">
		<tr>
			<td style="border: 0px; height: 370px;">
				<iframe id="sublinedetaillist" marginWidth="0" marginHeight="0"
					src="${ctx}/planstat/planResultDetail.jsp" frameBorder=0
					width="100%" scrolling=auto height="480px">
				</iframe>
			</td>
		</tr>
		<tr style="display: none">
			<td style="border: 0px; height: 370px;">
				<div>
					<iframe id="sublinedetaillist" marginWidth="0" marginHeight="0"
						src="${ctx}/planstat/showSublineDetailInfo.jsp" frameBorder=0
						width="100%" scrolling=auto height="480px">
					</iframe>
				</div>
			</td>
		</tr>
		<tr style="display: none">
			<td style="border: 0px; height: 370px;">
				<div>
					<iframe id="FrameSublineCover" marginWidth="0" marginHeight="0"
						frameBorder=0 width="100%" scrolling=auto height="480px">
					</iframe>
				</div>
			</td>
		</tr>
		<tr style="display: none">
			<td style="border: 0px; height: 370px;">
				<div>
					<iframe name="mypatrollist" marginWidth="0" marginHeight="0"
						src="${ctx}/planstat/planPatrolList.jsp" frameBorder=0
						width="100%" scrolling=auto height="480px">
					</iframe>
				</div>
			</td>
		</tr>
		<tr style="display: none">
			<td style="border: 0px; height: 370px;">
				<div>
					<iframe id="workdaylist" marginWidth="0" marginHeight="0"
						src="${ctx}/planstat/showWorkdayInfo.jsp" frameBorder=0
						width="100%" scrolling=auto height="480px">
					</iframe>
				</div>
			</td>
		</tr>
		<tr style="display: none">
			<td style="border: 0px; height: 370px;">
				<div>
					<iframe id="leaklist" marginWidth="0" marginHeight="0"
						frameBorder=0 width="100%" scrolling=auto height="480px">
					</iframe>
				</div>
			</td>
		</tr>
		<tr style="display: none">
			<td style="border: 0px; height: 370px;">
				<div>
					<iframe id="FrameLeakGIS" marginWidth="0" marginHeight="0"
						frameBorder=0 width="100%" scrolling=auto height="480px">
					</iframe>
				</div>
			</td>
		</tr>
	</table>
	<div
		style="width: 98%; margin: 0px auto; position: absolute; bottom: 5px; right: 1%;">
		<table class="ooih" border="0" cellspacing="0" cellpadding="0"
			width="400px" height="19px">
			<tr>
				<td class="ooihj" nowrap onclick="ghbq(this)">
					总体信息
				</td>
				<td class="ooihs" nowrap onclick="ghbq(this)">
					巡检线段详细信息
				</td>
				<td id="sublineCover" class="ooihs" nowrap onclick="ghbq(this)">
					巡检线段覆盖位置
				</td>
				<td class="ooihs" nowrap onclick="ghbq(this)">
					巡检明细
				</td>
				<td class="ooihs" nowrap onclick="ghbq(this)">
					巡检回放
				</td>
				<td id="leakpanel" class="ooihs" nowrap onclick="ghbq(this)">
					漏检明细
				</td>
				<td id="leakGIS" class="ooihs" nowrap onclick="ghbq(this)">
					漏检点位置
				</td>
				<td class="ooihs" nowrap onclick="addGoFirst()">
					查询页面
				</td>
				<td class="ooihs" nowrap
					onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
					上一页面
				</td>
				<td width="100%">
					&nbsp;
				</td>
			</tr>
		</table>
	</div>
</body>

