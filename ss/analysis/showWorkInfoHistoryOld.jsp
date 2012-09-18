<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />

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
  var ob = obody.rows;
  if (tr[td.cellIndex]==document.getElementById("curvePanel")){
	document.getElementById("FrmHistoryCurve").src="${ctx}/analysis/showHistoryCurve.jsp";
  }
  if (tr[td.cellIndex]==document.getElementById("smTrackPanel")){
	document.getElementById("FrmPatrolTrack").src ="${ctx}/analysis/showHistoryTrack.jsp";
  }
  if (tr[td.cellIndex]==document.getElementById("smTextPanel")){
	document.getElementById("FrmSMText").src="${ctx}/WorkInfoHistoryAction.do?method=getSMInfo&flagGraphic=0";
  }
  if (tr[td.cellIndex]==document.getElementById("smGraphicPanel")){
	document.getElementById("FrmSMGraphic").src="${ctx}/WorkInfoHistoryAction.do?method=getSMInfo&flagGraphic=1";
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
<%@include file="commonFrame.jsp" %>

<table class="ooih" border="0" cellspacing="0" cellpadding="0" width="400" height="19">
  <tr>
    <td id="curvePanel" class="ooihj" nowrap onclick="ghbq(this)">��ʷ����</td>
    <td id="smTrackPanel" class="ooihs" nowrap onclick="ghbq(this)">Ѳ��켣</td>
    <td id="smTextPanel" class="ooihs" nowrap onclick="ghbq(this)">����ִ��</td>
    <td id="smGraphicPanel" class="ooihs" nowrap onclick="ghbq(this)">����ִ��ͼ��</td>
    <td class="ooihs" nowrap onclick="addGoQuery()" >��ѯҳ��</td>
    <td width="100%">&nbsp;</td>
  </tr>
</table>

</body>






