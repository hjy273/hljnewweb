<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script language="JavaScript" src="${ctx}/js/overlib.js"></script>
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
        createXMLHttpRequest();
        var regionid = select.value;
        var url="${ctx}/RealTimeOnlineAction.do?method=getOnlineNum&range="+regionid+"&s=true";
        
        xmlHttp.open("post", url, true);
        xmlHttp.onreadystatechange = startCallback;
        xmlHttp.send(null);
    }
	function startCallback() {
        if (xmlHttp.readyState == 4) {
            var pic_span = document.getElementById("pic");
        	pic_span.innerHTML = xmlHttp.responseText;
        }
    }

	
</script>
</head>
<body >
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>今日在线人数实时曲线</div><hr width='100%' size='1'>

<br>
<div align="right" width="100%">
<%@include file="common.jsp" %>
</div>
<div align="center" id="pic" style="display:">
<%@include file="onlinenumpic.jsp" %>
</div>
</body>
</html>