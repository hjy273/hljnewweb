<%@ page contentType="text/html; charset=gb2312" %>

<html>
<head>
<title>controlframe</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style type="text/css">
<!--
body {
	background-color: #2A58A6;
	margin-left: 0px;
	margin-top: 0px;
}
-->
</style>
<script language="javascript" src="../js/no_right.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<script language="JavaScript" type="text/JavaScript">
<!--

function hidemenu() {
	
	try{
		hidemenuSub();
	}catch(e){}
	
	var innerUrl = "${ctx}/frames/gisPathPage.jsp?flag=hide";
	gisIFrame.location.replace(innerUrl);

	parent.document.all.mainframeset.cols = "0,6,*";
	closemenu.style.display = "none";
	openmenu.style.display = "block";
}

function hidemenuSub(){

	var w = top.mainFrame.mainPage.document.all.Map.width;
	var h = top.mainframeset.offsetHeight;

	
	var MapWidth = w + 220;
	var regionValue = top.mainFrame.mainPage.gis_controlFrame.regionHidden.value;
	var gisPathV = GisPath.value;

	var MapSrc = gisPathV + "Map.jsp?RegionID="+regionValue+"&chgWidth=" + MapWidth + "&width="+ w +"&height=" + h;


	top.mainFrame.mainPage.document.all.Map.src = MapSrc;

}

function showmenu() { 
	
	try{
		showmenuSub();
	}catch(e){}
	

	var innerUrl = "${ctx}/frames/gisPathPage.jsp?flag=show";
	gisIFrame.location.replace(innerUrl);
	

	parent.document.all.mainframeset.cols = "240,6,*";
	closemenu.style.display = "block";
	openmenu.style.display = "none";
}

function showmenuSub(){

	var w = top.mainFrame.mainPage.document.all.Map.width;
	var h = top.mainframeset.offsetHeight;

	
	var MapWidth = w - 220;
	var regionValue = top.mainFrame.mainPage.gis_controlFrame.regionHidden.value;
	var gisPathV = GisPath.value;

	var MapSrc = gisPathV + "Map.jsp?RegionID="+regionValue+"&chgWidth=" + MapWidth + "&width="+ w +"&height=" + h;


	top.mainFrame.mainPage.document.all.Map.src = MapSrc;

}
//-->
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('../images/arrow_close_on.gif','../images/arrow_resetf_on.gif')">
<table id="closemenu" style="display:block; " width="6" height="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" style="display: "><img src="../images/arrow_close.gif" alt="���ز˵���" name="Image1" width="6" height="58" id="Image1" style="cursor:hand" onClick="hidemenu();" onMouseOver="MM_swapImage('Image1','','../images/arrow_close_on.gif',1)" onMouseOut="MM_swapImgRestore()"></td>
  </tr>
</table>

<table id="openmenu" style="display:none; " width="6" height="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center"><img src="../images/arrow_resetf.gif" alt="��ʾ�˵���" name="Image2" width="6" height="58" id="Image2" onMouseOver="MM_swapImage('Image2','','../images/arrow_resetf_on.gif',1)" onMouseOut="MM_swapImgRestore()" style="cursor:hand" onClick="showmenu();"></td>
  </tr>
</table>

<input type="hidden" name="GisPath" value="">

<iframe name="gisIFrame" style="display:none"></frame>
</body>
</html>
