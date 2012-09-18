<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<%@ taglib uri="newcabletechtags" prefix="ct1" %>
<script language="JavaScript" type="">
function addGoFirst()
{
  window.history.back();

}  

function addGoQuery()
{
  var url="${ctx}/analysis/queryWorkInfoHistory.jsp";
  self.location.replace(url);

}

function ghbq(td)
{
  var tr = td.parentElement.cells;
  var pic_span = document.getElementById("tabtext");
   if (tr[td.cellIndex]==document.getElementById("curvePanel")){
	pic_span.src="${ctx}/analysis/showHistoryCurve.jsp";
   }
   if (tr[td.cellIndex]==document.getElementById("smTextPanel")){
    pic_span.src="${ctx}/WorkInfoHistoryAction.do?method=getSMInfo&flagGraphic=0";
   }
   if (tr[td.cellIndex]==document.getElementById("smGraphicPanel")){
	pic_span.src="${ctx}/WorkInfoHistoryAction.do?method=getSMInfo&flagGraphic=1";
   }
   if (tr[td.cellIndex]==document.getElementById("queryPanel")){
	 addGoQuery();
   }
  for(var i=0; i<tr.length-1; i++)
  {	
    tr[i].className = (td.cellIndex==i)?"ooihj":"ooihs";
  }
}
</script>

<br>

<body>
<ct1:tabpanel styleClass="ooih" orientation="down" width="100%" contentName="tabtext" contentHigth="94%"  defaultContent="${ctx}/analysis/showHistoryCurve.jsp">
	<ct1:tab tabId="curvePanel" defaultTab="true" tabName="历史曲线"></ct1:tab>
	<ct1:tab tabId="smTextPanel" tabName="任务执行"></ct1:tab>
	<ct1:tab tabId="smGraphicPanel"  tabName="任务执行图例"></ct1:tab>
	<ct1:tab tabId="queryPanel" tabName="查询页面"></ct1:tab>
</ct1:tabpanel>
</body>






