<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@page import="com.cabletech.commons.config.*"%>
<%@page import="com.cabletech.utils.GISkit.*"%>
<%
  String regionid = "";
  String userid = "";
  String PMType = (String)session.getAttribute("PMType");
  if (request.getParameter("cRegion") != null) {
    regionid = request.getParameter("cRegion");
  }
  if (request.getParameter("userID")!=null){
    userid = request.getParameter("userID");
  }

  //String GisPath = GisConInfo.newInstance().getWholePath();
  //String GisPath = GisConInfo.newInstance().getWholePathFromDB(regionid);

  String GisPath = GISPath.getGisPath(regionid);
  //设置重载地图参数
  String init = request.getParameter("init");
  //System.out.println("init===="+init);
  if( init.equals("") ) init = "yes";
  String mapPath = GisPath + "Map.jsp?init="+init+"&RegionID=" + regionid+"&userID="+userid+"&pmType="+PMType;
%>
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
<script language="javascript" src="../../js/no_right.js"></script><script language="JavaScript" type="text/JavaScript">
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
</script><script language="JavaScript" type="text/JavaScript">
<!--
function getWH(mapPath){

    var w = parent.document.all.Map.width;
    var h = top.mainframeset.offsetHeight;

    mapPath = mapPath + "&width="+ w +"&height=" + h;
    //alert(mapPath);

    parent.document.all.Map.src = mapPath;
}

function hidemenu(){

    var w = parent.document.all.Map.width;
    var h = top.mainframeset.offsetHeight;

    //alert(w + ":" + h );


    var MapWidth = parent.document.all.Map.width + 200;
    var regionValue = regionHidden.value;
    var gisPathV = GisPath.value;

    var MapSrc = gisPathV + "Map.jsp?RegionID="+regionValue+"&chgWidth=" + MapWidth + "&width="+ w +"&height=" + h;

    alert(MapSrc);

    parent.document.all.Map.src = MapSrc;

    parent.document.all.gis_mainframeset.cols = "*,6,0";
    closemenu.style.display = "none";
    openmenu.style.display = "block";

    //alert("OK");

}
function showmenu() {

    var w = parent.document.all.Map.width;
    var h = top.mainframeset.offsetHeight;


    var MapWidth = parent.document.all.Map.width - 200;
    var regionValue = regionHidden.value;
    var gisPathV = GisPath.value;

    var MapSrc = gisPathV + "Map.jsp?RegionID="+regionValue+"&chgWidth=" + MapWidth + "&width="+ w +"&height=" + h;

    //alert(MapSrc);

    parent.document.all.Map.src = MapSrc;



    parent.document.all.gis_mainframeset.cols = "*,6,200";
    closemenu.style.display = "block";
    openmenu.style.display = "none";

    //alert("OK");

}
//-->
</script></head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="getWH('<%=mapPath%>');MM_preloadImages('../../images/arrow_close_on.gif','../../images/arrow_resetf_on.gif')">
<table id="closemenu" style="display:block; " width="6" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center">
      <img
        src="../../images/arrow_resetf.gif"
        alt="隐藏菜单栏"
        name="Image1"
        width="6"
        height="58"
        id="Image1"
        style="cursor:hand"
        onClick="hidemenu();"
        onMouseOver="MM_swapImage('Image1','','../../images/arrow_resetf_on.gif',1)"
        onMouseOut="MM_swapImgRestore()">
    </td>
  </tr>
</table>
<table id="openmenu" style="display:none; " width="6" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center">
      <img
        src="../../images/arrow_close.gif"
        alt="显示菜单栏"
        name="Image2"
        width="6"
        height="58"
        id="Image2"
        onMouseOver="MM_swapImage('Image2','','../../images/arrow_close_on.gif',1)"
        onMouseOut="MM_swapImgRestore()"
        style="cursor:hand"
        onClick="showmenu();">
    </td>
  </tr>
  <input type="hidden" name="regionHidden" value="<%=regionid%>">
</table>
<input type="hidden" name="GisPath" value="<%=GisPath%>">
</body>
</html>
