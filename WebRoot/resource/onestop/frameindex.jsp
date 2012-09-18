<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<link rel="stylesheet" href="../css/extremecomponents.css" type="text/css"></link>
<script language="javascript" type="text/javascript">
function ghbq(td){
  	var tr = td.parentNode.cells;
  	var ob = tab.rows;
  	if(tr[td.cellIndex]==document.getElementById("staion")){
  		document.getElementById("current").src ="/bspweb/oneStopQuick_inputStation.jspx";
	}
  	if(tr[td.cellIndex]==document.getElementById("antenna")){
  		document.getElementById("current").src ="/bspweb/oneStopQuick_inputAntenna.jspx";
	}
	if(tr[td.cellIndex]==document.getElementById("tower")){
  		document.getElementById("current").src ="/bspweb/oneStopQuick_inputTower.jspx";
	}
 	if(tr[td.cellIndex]==document.getElementById("cell")){
  		document.getElementById("current").src ="/bspweb/oneStopQuick_listCell.jspx";
	}
	if(tr[td.cellIndex]==document.getElementById("equ")){
  		document.getElementById("current").src ="/bspweb/oneStopQuick_listEqu.jspx";
	}
	if(tr[td.cellIndex]==document.getElementById("repeater")){
  		document.getElementById("current").src ="/bspweb/oneStopQuick_listRepeater.jspx";
	}
	if(tr[td.cellIndex]==document.getElementById("return")){
		window.location.href='${ctx}/baseStationAction_query.jspx';
	}
  	for(var i=0; i<tr.length-1; i++){	
    	tr[i].className = (td.cellIndex==i)?"ooihj":"ooihs";
    	//ob[i].style.display = (td.cellIndex==i)?"block":"none";
  	}
}
</script>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />

<body >
<table class="ooib" id="obody" border="0" cellspacing="0" cellpadding="0" width="100%" height="95%">
  <tr style="display:">
   <td>
          <iframe id="current" marginWidth="0" marginHeight="0"  frameBorder=0 width="100%"  scrolling="auto" height="100%"> </iframe>
  </td>
 </tr>
 </table>
<table class="ooih" border="0" id="tab" cellspacing="0" cellpadding="0" width="400" height="19">
  <tr>
  	<td class="ooihj" id="staion" nowrap onclick="ghbq(this)">基站信息</td>
    <td class="ooihs" id="equ" nowrap onclick="ghbq(this)">设备</td>
    <td class="ooihs" id="cell" nowrap onclick="ghbq(this)">小区</td>
    <td class="ooihs" id="antenna" nowrap onclick="ghbq(this)">天线</td>
    <td class="ooihs" id="tower" nowrap onclick="ghbq(this)">铁塔</td>
    <td class="ooihs" id="repeater" nowrap onclick="ghbq(this)">直放站</td>
    <c:if test="${flag=='ture'}"><td class="ooihs" id="return" nowrap onclick="ghbq(this)">返回</td></c:if>
    <td width="100%">&nbsp;</td>
  </tr>
</table>
</body>
<script language="javascript" type="text/javascript">
	document.getElementById("current").src ="/bspweb/oneStopQuick_inputStation.jspx";
</script>
</html>