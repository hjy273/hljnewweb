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
  
//执行情况
  if (tr[td.cellIndex]==document.getElementById("EXEPlanPanel")){
	document.getElementById("EXEPlan").src ="${ctx}/ProvinceStatAction.do?method=getExecuteResult";
  }
  //对比分析
  if (tr[td.cellIndex]==document.getElementById("ContrastStatPanel")){
	document.getElementById("ContrastStat").src ="${ctx}/ProvinceStatAction.do?method=getContrastDataForWhole";
  }
  //巡检线段
  if (tr[td.cellIndex]==document.getElementById("SublinePanel")){
	document.getElementById("Subline").src ="${ctx}/ProvinceStatAction.do?method=getSublinePatrolForChart";
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
          <iframe id="General" marginWidth="0" marginHeight="0" src="${ctx}/analysis/provgeneral.jsp" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
      </div>
  </td>
  </tr>
 
  <tr width="100%" style="display:none">
  <td>
  	  <div>
          <iframe id="EXEPlan" marginWidth="0" marginHeight="0" src="" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
      </div>
  </td>
  </tr>
  
  <tr style="display:none">
   <td>
  	  <div>
          <iframe id="ContrastStat" marginWidth="0" marginHeight="0" src="" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
      </div>
  </td>
  </tr>
  
  <tr style="display:none">
  <td>
  	  <div>
          <iframe id="Subline" marginWidth="0" marginHeight="0" src="" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
      </div>
  </td>
  </tr>

 </table>
<table class="ooih" border="0" cellspacing="0" cellpadding="0" width="400" height="19">
  <tr>
  	<td class="ooihj" id="GeneralPanel" nowrap onclick="ghbq(this)">总体信息</td>
    <td class="ooihs" id="EXEPlanPanel" nowrap onclick="ghbq(this)">执行结果</td>
    <td class="ooihs" id="ContrastStatPanel" nowrap onclick="ghbq(this)">对比分析</td>
    <td class="ooihs" id="SublinePanel" nowrap onclick="ghbq(this)">巡检线段</td>
    <td width="100%">&nbsp;</td>
  </tr>
</table>