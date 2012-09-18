<%@include file="/common/header.jsp"%>
<html>
<head>
<title>
jsp1
</title>

<script language="javascript" type="">

function onload(){
     var con = window.confirm("操作已过时,请重新登陆!");
     var url = "${ctx}/mainweb.htm";
     win=window.open (url, "newwindow", "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
     window.opener=null;
     window.close();
}
</script>
</head>
<body onload="onload()">
</body>
</html>
