<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>

<script language="javascript" type="">

function ghbq(td)
{
  var tr = td.parentElement.cells;
  var ob = obody.rows;
  if (tr[td.cellIndex]==document.getElementById("graphicspanel")){
	document.getElementById("myworkdaysgraphics").src="${ctx}/ShowPmMonthWorkdaysChart";
  }
  for(var ii=0; ii<tr.length-1; ii++)
  {
    tr[ii].className = (td.cellIndex==ii)?"ooihj":"ooihs";
    ob[ii].style.display = (td.cellIndex==ii)?"block":"none";
  }
}
</script><br>
</head>
<body>
<table class="ooib" id="obody" border="0" cellspacing="0" cellpadding="0" width="100%" height="93%">
  <tr>
    <td>
      <div>
          <iframe name="myworkdaystext" marginWidth="0" marginHeight="0" src="${ctx}/planstat/showWorkDaysInfoText.jsp" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
      </div>
    </td>
  </tr>
  <tr style="display: none">
    <td>
     <div>
         <img src="${ctx}/images/1px.gif" alt="">
         <br>
         <iframe id="myworkdaysgraphics" name=treemenu border=0 marginWidth=0 marginHeight=0  frameBorder=0 width="100%" scrolling=NO height="100%">        </iframe>
      </div>
    </td>
  </tr>
</table>

<table class="ooih" border="0" cellspacing="0" cellpadding="0" width="400" height="19">
  <tr>
    <td class="ooihj" nowrap onclick="ghbq(this)">文本方式</td>
    <td id ="graphicspanel" class="ooihs" nowrap onclick="ghbq(this)">图形方式</td>
    <td width="100%">&nbsp;</td>
  </tr>
</table>

</body>




