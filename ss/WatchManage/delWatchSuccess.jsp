<%@page contentType="text/html; charset=GBK"%>
<SCRIPT LANGUAGE="JavaScript" src="\WEBGIS\js\function.js" type=""></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" type="">
opener.parent.Map.frmMapOpt.OnceTool.value="RefreshWatchPoint";
opener.parent.Map.frmMapOpt.submit();
</SCRIPT>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title>移动传输线路巡检管理系统</title>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<br><br><br><br><br>
  <table width="95%" border="0" cellspacing="0" cellpadding="6" align="center">
    <tr>
      <td>
        <table border="0" cellspacing="10" cellpadding="0" align="center" bgcolor="8DAAD8" bordercolor="#000000">
          <tr>
            <td width="60" height="60" bgcolor="#FFFFFF" align="center"><img src="${ctx}/images/succ.gif" width="49" height="49" alt="添加成功！"></td>
            <td width="250" bgcolor="#FFFFFF" align="center">临时盯防点操作成功！</td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <table border="0" cellspacing="10" cellpadding="0" align="center" >
    <tr>
      <td   align="center">
        <img  border="0" style="cursor:hand" src="${ctx}/images/closed.gif" width="80" height="22" onClick="self.close()" alt="关闭">
      </td>
    </tr>
  </table>
</body>
</html>
