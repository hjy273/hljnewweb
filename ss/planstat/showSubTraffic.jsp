<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />

<script language="javascript" type="">

function addGoFirst()
{
  window.history.back();
var url="${ctx}/planstat/showSubTraffic.jsp";

}
function GoMainModule()
{
  var url="${ctx}/planstat/showVisitorsTraffic.jsp";
  parent.location.replace(url);

}
function ghbq(td)
{
  var tr = td.parentElement.cells;
  var ob = obody.rows;
  for(var ii=0; ii<tr.length-4; ii++)
  {
    tr[ii].className = (td.cellIndex==ii)?"ooihj":"ooihs";
    ob[ii].style.display = (td.cellIndex==ii)?"block":"none";
  }
}
</script><br>
</head>
<body>
<table class="ooib" id="obody" border="0" cellspacing="0" cellpadding="0" width="100%" height="94.43%">
  <tr>
    <td>
      <div>
          <iframe name="myvisitorstraffictext" marginWidth="0" marginHeight="0" src="${ctx}/planstat/showSubTrafficText.jsp" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
      </div>
    </td>
  </tr>
  <tr style="display: none">
    <td>
     <div>
         <img src="${ctx}/images/1px.gif" alt="">
         <br>
         <iframe name=treemenu border=0 marginWidth=0 marginHeight=0 src="${ctx}/ShowSubTrafficChart" frameBorder=0 width="100%" scrolling=NO height="100%">        </iframe>
      </div>
    </td>
  </tr>
</table>

<table class="ooih" border="0" cellspacing="0" cellpadding="0" width="400" height="19">
  <tr>
    <td class="ooihj" nowrap onclick="ghbq(this)">�ı���ʽ</td>
    <td class="ooihs" nowrap onclick="ghbq(this)">ͼ�η�ʽ</td>
    <td class="ooihs" nowrap onclick="addGoBack()" style="display:none">�����ı���ʽ</td>
    <td class="ooihs" nowrap onclick="addGoBack()" style="display:none">����������Ϣҳ(ͼ��)</td>
    <td class="ooihs" nowrap onclick="GoMainModule()" >������ģ����ʾҳ��</td>
    <td width="100%">&nbsp;</td>
  </tr>
</table>

</body>




