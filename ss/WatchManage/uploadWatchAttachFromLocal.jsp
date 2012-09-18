<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
	<base href="<%=basePath%>">

	<title>本地文件上传</title>


	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style>
    .btn {
    BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#cecfde); BORDER-LEFT: #7b9ebd 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #7b9ebd 1px solid
    }
</style>

</head>

<body>
<template:titile value="从本地上传附件"/>

<TABLE class=borderon id=control cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
    <TR>
      <td>
        <table border="0" cellspacing="0" cellpadding="0">
          <tr align="center">
            <td width=100 class="lbutton"  style="cursor:hand"><a href="${ctx}/WatchPicAction.do?method=viewLinkPicEx">&nbsp;返&nbsp;&nbsp;回&nbsp;</a></td>

          </tr>
      </table></td>
    </TR>
   <tr>
    <td height="2" background="../images/bg_line.gif"><img src="../images/1px.gif" width="1" height="1"></td>
  </tr>
  </TBODY>
</TABLE>

	<html:form action="/UploadFromLocalAction" method="post"
		enctype="multipart/form-data">
		
		<html:hidden property='watchId'
			value='<%= request.getParameter("watchid") %>' />
		<table width="568" border="0">
			<tr>
				<td width="562">
					<fieldset>
						<legend>
							从本地上传附件
						</legend>
						<table width="560" border="0">
							<tr>
								<td width="66">
									文件
								</td>
								<td width="478">
									<html:file property="attachFile"></html:file>
								</td>
							</tr>
							<tr>
								<td>
									附件说明
								</td>
								<td>
									<html:text property="attachRemark" size="50" />
								</td>
							</tr>
							<tr>
								<td colspan="2">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:submit styleClass="btn">&nbsp;上&nbsp;&nbsp;&nbsp;&nbsp;传&nbsp;</html:submit>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>

		</table>




	</html:form>
</body>
</html:html>
