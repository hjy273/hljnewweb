<%@ include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />

<script language="javascript" type="text/javascript">
function addGoBack()
{
  window.history.go(-1);
}
function ghbq(td)
{
  var tr = td.parentElement.cells;
  var ob = obody.rows;
  
// if (tr[td.cellIndex]==document.getElementById("RealTimePanel")){
//	document.getElementById("RealTime").src ="${ctx}/RealTimeOnlineAction.do?method=getOnlineNum";
//  }
  //���Ż���
  if (tr[td.cellIndex]==document.getElementById("NotePanel")){
	document.getElementById("Note").src ="${ctx}/RealTimeNoteAction.do?method=getNoteNumInfo";
  }
  //ͼ�����ChartAnalysePanel
   if (tr[td.cellIndex]==document.getElementById("NoteChartPanel")){
	document.getElementById("NoteChart").src ="${ctx}/RealTimeNoteAction.do?method=showChartNoteInfo";
  }
  for(var i=0; i<tr.length-1; i++)
  {	
    tr[i].className = (td.cellIndex==i)?"ooihj":"ooihs";
    ob[i].style.display = (td.cellIndex==i)?"block":"none";
  }
}
</script>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<br>
<table class="ooib" id="obody" border="0" cellspacing="0" cellpadding="0" width="100%" height="94%">
  <tr style="display:">
   <td>
  	  <div>
          <iframe id="RealTime" marginWidth="0" marginHeight="0" src="${ctx}/analysis/realtime.jsp" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
      </div>
  </td>
  </tr>
  <tr style="display:none">
  <td>
  	  <div>
          <iframe id="Note" marginWidth="0" marginHeight="0" src="" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
      </div>
  </td>
  </tr>

  <tr width="100%" style="display:none">
   <td>
  	  <div>
          <iframe id="NoteChart" marginWidth="0" marginHeight="0" src="" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
      </div>
   </td>
  </tr>
 </table>
<table class="ooih" border="0" cellspacing="0" cellpadding="0" width="400" height="19">
  <tr>
  	<td class="ooihj" id="RealTimePanel" nowrap onclick="ghbq(this)">ʵʱ����</td>
    <td class="ooihs" id="NotePanel" nowrap onclick="ghbq(this)">����ִ�л���</td>
    <td class="ooihs" id="NoteChartPanel" nowrap onclick="ghbq(this)">����ִ��ͼ��</td>
    <td width="100%">&nbsp;</td>
  </tr>
</table>