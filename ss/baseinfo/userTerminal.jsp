<%@ include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />

<script language="javascript" type="text/javascript">
	var flag = true;
function addGoBack()
{
  window.history.go(-1);
}
function ghbq(td)
{
    var tr = td.parentElement.cells;
  
  var ob = obody.rows;
  if (tr[td.cellIndex]==document.getElementById("UseTerminalPanl")){
  	if(!flag){
  		//go("${ctx}/baseinfo/useTerminalChart.jsp");
			document.getElementById("UseTerminal").src ="${ctx}/baseinfo/useTerminalChart.jsp";
		}
  }
	//前50名
  if (tr[td.cellIndex]==document.getElementById("asc50pPanl")){
  	flag =false;
		document.getElementById("asc50").src ="${ctx}/UseTerminalAction.do?method=getUseTerminalCondition&conn=asc";
  }
  //后50名
  if (tr[td.cellIndex]==document.getElementById("dsec50Panl")){
  	flag =false;
		document.getElementById("dsec50").src ="${ctx}/UseTerminalAction.do?method=getUseTerminalCondition&conn=desc";
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
          <iframe id="UseTerminal" marginWidth="0" marginHeight="0" src="${ctx}/baseinfo/useTerminalChart.jsp" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
      </div>
  </td>
  </tr>
 
  <tr width="100%" style="display:none">
  <td>
  	  <div>
          <iframe id="asc50" marginWidth="0" marginHeight="0" src="" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
      </div>
  </td>
  </tr>
  <tr style="display:none">
   <td>
  	  <div>
          <iframe id="dsec50" marginWidth="0" marginHeight="0" src="" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
      </div>
  </td>
  </tr>
  
 </table>
<table class="ooih" border="0" cellspacing="0" cellpadding="0" width="400" height="19">
  <tr>
  	<td class="ooihj" id="UseTerminalPanl" nowrap onclick="ghbq(this)">总体信息</td>
    <td class="ooihs" id="asc50pPanl" nowrap onclick="ghbq(this)">前50位</td>
    <td class="ooihs" id="dsec50Panl" nowrap onclick="ghbq(this)">后50位</td>
    <td width="100%">&nbsp;</td>
  </tr>
</table>