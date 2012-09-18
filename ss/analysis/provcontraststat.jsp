<%@ include file="/common/header.jsp"%>
<script type="text/javascript">
<!--
function ck(id){
  if (id=="1"){
	document.getElementById("monthcontrast").src ="${ctx}/ShowContrastChart?viewtype=monthly";

  }
  if (id=="2"){
  	//alert("test");
	document.getElementById("monthcontrast").src ="${ctx}/ShowContrastChart?viewtype=area";

  }
}
//-->
</script>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=session.getAttribute("year") %>年<%=session.getAttribute("month") %>月巡检情况统计分析</div><hr width='100%' size='1'>
<table width='100%' bgcolor="#EAE9E4">
 <tr width="80%" align="center">
 <td valign="top">
 <input type="radio" name="stat" checked onclick="ck('1')" />最近几月份比较    
 <input type="radio" name="stat" onclick="ck('2')"/>本省各地市比较<br>
 </td>
 </tr>
 <tr>
 <td height="402">
<div id="div1" align="center" style="display:">
     <iframe id="monthcontrast" style="color:#EAE9E4" marginWidth="0" marginHeight="0" src="${ctx}/ShowContrastChart?viewtype=monthly" frameBorder=0 width="100%" scrolling=auto height="99%"> </iframe>
</div>
 </td>

 </tr>
</table>