<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<%@page import="com.cabletech.commons.config.GisConInfo"%>

<%
GisConInfo gisip = GisConInfo.newInstance();
%>
<script language="JavaScript" type="">
function addGoPreview()
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
  var ob = obody.rows;
  if (tr[td.cellIndex]==document.getElementById("curvePanel")){
	document.getElementById("FrmHistoryCurve").src="${ctx}/analysis/showHistoryCurveGivenDay.jsp";
  }
  if (tr[td.cellIndex]==document.getElementById("smTrackPanel")){
	document.getElementById("FrmPatrolTrack").src ="${ctx}/analysis/showHistoryTrack.jsp";
  }
  if (tr[td.cellIndex]==document.getElementById("smTextPanel")){
	document.getElementById("FrmSMText").src="${ctx}/WorkInfoHistoryAction.do?method=getSMInfoGivenDay&flagGraphic=0";
  }
  if (tr[td.cellIndex]==document.getElementById("smGraphicPanel")){
	document.getElementById("FrmSMGraphic").src="${ctx}/WorkInfoHistoryAction.do?method=getSMInfoGivenDay&flagGraphic=1";
  }


  for(var ii=0; ii<tr.length-3; ii++)
  {
    tr[ii].className = (td.cellIndex==ii)?"ooihj":"ooihs";
    ob[ii].style.display = (td.cellIndex==ii)?"block":"none";
  }
}  
</script>

<br>

<body>

<table class="ooib" id="obody" border="0" cellspacing="0" cellpadding="0" width="100%" height="94%">
   <tr style="display:">
        <td>
		   <div>
		      <iframe name="FrmHistoryCurve" marginWidth="0" marginHeight="0" src="${ctx}/analysis/showHistoryCurveGivenDay.jsp" frameBorder=0 width="100%" scrolling=auto height="100%"></iframe>
		   </div>
		</td>
    </tr>
    <tr style="display: none">
	    <td>
	      <div>
	          <iframe id="FrmPatrolTrack" marginWidth="0" marginHeight="0" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
	      </div> 
	    </td>    
    </tr>
    <tr style="display: none">
	    <td>
	      <div>
	          <iframe name="FrmSMText" marginWidth="0" marginHeight="0" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
	      </div> 
	    </td>
    </tr>
	<tr style="display: none">
	    <td>
	      <div>
	          <img src="${ctx}/images/1px.gif" alt="">
              <br>
              <iframe name=FrmSMGraphic border=0 marginWidth=0 marginHeight=0 frameBorder=0 width="100%" scrolling=NO height="100%">        </iframe>
          </div>
        </td>
	</tr>
</table>

<table class="ooih" border="0" cellspacing="0" cellpadding="0" width="400" height="19">
  <tr>
    <td id="curvePanel" class="ooihj" nowrap onclick="ghbq(this)">历史曲线</td>
    <td id="smTrackPanel" class="ooihs" nowrap onclick="ghbq(this)">巡检轨迹</td>
    <td id="smTextPanel" class="ooihs" nowrap onclick="ghbq(this)">任务执行</td>
    <td id="smGraphicPanel" class="ooihs" nowrap onclick="ghbq(this)">任务执行图例</td>
    <td class="ooihs" nowrap onclick="addGoPreview()" >上一页面</td>
    <td class="ooihs" nowrap onclick="addGoQuery()" style="display:none">查询页面</td>
    <td width="100%">&nbsp;</td>
  </tr>
</table>

</body>






