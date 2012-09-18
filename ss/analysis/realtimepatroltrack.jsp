<%@ include file="/common/header.jsp"%>
<%@page import="com.cabletech.commons.config.*"%>
<html>
<head>
<%
	UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
	GisConInfo gisip = GisConInfo.newInstance();
 %>
<script language="javascript">
	var xmlHttp;
    function createXMLHttpRequest() {
        if (window.ActiveXObject) {
           xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        } 
        else if (window.XMLHttpRequest) {
           xmlHttp = new XMLHttpRequest();                
        }
    }
	function go(select) {
        //createXMLHttpRequest();
        var regionid = select.value;
        var url="http://<%=gisip.getServerip()%>:<%=gisip.getServerport()%>/WEBGIS/gisextend/igis.jsp?actiontype=103&userid=<%=userinfo.getUserID()%>&rangeid="+regionid;
        //xmlHttp.open("post", url, true);
        //xmlHttp.onreadystatechange = startCallback;
        //xmlHttp.send(null);
         document.getElementById("map").src=url;
    }
	function startCallback() {
        if (xmlHttp.readyState == 4) {
            var pic_span = document.getElementById("pic");
        	pic_span.innerHTML = xmlHttp.responseText;
        }
    }

	function overlib(str){
      //alert(str);
      return false;
      //打开窗口
	}
	function nd(){
	//关闭窗口
  	return false;
	}

</script>
</head>
<body >
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>当前巡检员所在位置</div><hr width='100%' size='1'>

<br>
<div align="right" width="100%">
<%@include file="common.jsp" %>
</div>
<br>
<div align="center" id="pic" style="display:">
<iframe id="map" marginWidth="0" marginHeight="0" src="http://<%=gisip.getServerip()%>:<%=gisip.getServerport()%>/WEBGIS/gisextend/igis.jsp?actiontype=103&userid=<%=user.getUserID()%>&rangeid=" frameBorder=0 width="100%" scrolling=auto height="83%"> </iframe>
</div>
</body>
</html>