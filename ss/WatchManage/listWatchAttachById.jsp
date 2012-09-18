<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@include file="/common/header.jsp"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>盯防相关附件列表</title>
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>

	<body>
<template:titile value="盯防相关附件列表"/>

<TABLE class=borderon id=control cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
    <TR>
      <td>
        <table border="0" cellspacing="0" cellpadding="0">
          <tr align="center">
<td width=100 class="lbutton"  style="cursor:hand"><input type="button" value="返回" class="lbutton" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/></td>
<td width=100 class="lbutton"  style="cursor:hand"><a href="${ctx}/WatchManage/uploadWatchAttachFromLocal.jsp?watchid=<c:out value="${watchid}"/>">从本地上传附件</a></td>
<!-- 
<td width=100 class="lbutton"  style="cursor:hand"><a href="${ctx}/ListMmsPicAction.do?watchid=<c:out value="${watchid}"/>">从彩信提取图片</a></td>
 -->
          </tr>
      </table></td>
    </TR>
   <tr>
    <td height="2" background="../images/bg_line.gif"><img src="../images/1px.gif" width="1" height="1"></td>
  </tr>
  </TBODY>
</TABLE>

	<%
	  String nullFlag="NO";
	  Object obj=request.getAttribute("attachList"); 
	  if(obj==null||((List)obj).size()<1){
		  nullFlag="YES";
	  }
	  pageContext.setAttribute("nullFlag",nullFlag);
	
	%>
	

<table>

	<tr>
		<td>
			<fieldset>
				<legend>
					盯防相关附件列表
				</legend>
				<table>
                  <c:choose>
                     <c:when test="${nullFlag=='YES'}">

                           <tr><td>未添加附件</td></tr>

                     </c:when> 
                     <c:otherwise>
						<c:forEach var="attach" items="${attachList}">
							<tr> <td>
                  <c:choose>
                     <c:when test="${attach.flag==1}">
	                      <a href="<c:out value="${attach.fullMmsUrl}"/>" target="_blank">
		                      <img border='0' src='${ctx}/images/attachment.gif' width='15' height='15' /> 
		                      <c:out value="${attach.attachPath}" /> 
	                      </a>
                     </c:when> 
		                 <c:otherwise>
			                 <a href="${ctx}/downloadAction.do?isWatch=1&fileid=<c:out value="${attach.id}"/>">
				                 <img border='0' src='${ctx}/images/attachment.gif' width='15' height='15' /> 
				                 <c:out value="${attach.attachPath}" /> 
			                 </a>
		                 </c:otherwise>
                 </c:choose> 				
							</td>
							<td>
								&nbsp;&nbsp;<c:out value="${attach.remark}" escapeXml="true" default="无说明" />
							</td>
							<td>
								&nbsp;&nbsp;<c:out value="${attach.uploadtime}" escapeXml="true" default="" />
							</td>		
							<td>
								&nbsp;&nbsp;<a href="${ctx}/DeleteWatchAttachAction.do?watchid=<c:out value="${attach.placeId}"/>&fileid=<c:out value="${attach.id}"/>&flag=<c:out value="${attach.flag}"/>&attachpath=<c:out value="${attach.attachPath }"/>">删除</a>
							</td>
						</tr>
				 </c:forEach>
               </c:otherwise>
             </c:choose> 						
          </table>
		</fieldset>
	  </td>
	</tr>
</table>

</body>
</html>
