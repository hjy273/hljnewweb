<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.cabletech.watchinfo.util.Escape"%>
<%@page import="com.cabletech.watchinfo.domainobjects.WatchMms"%>
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

	<title>My JSP 'uploadWatchAttachFromMMS.jsp' starting page</title>

	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <script src="${ctx}/js/prototype.js"></script>
<style>
    .btn {
    BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#cecfde); BORDER-LEFT: #7b9ebd 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #7b9ebd 1px solid
    }
     .submitbtn {
    BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#cecfde); BORDER-LEFT: #7b9ebd 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #7b9ebd 1px solid
    }
</style>

</head>

<body>

	<script type="text/javascript">
	  var watchMmsIdList= new Array(0);
	  var remarkList= new Array(0);
	  var relUrlList= new Array(0);
	  var absUrlList= new Array(0);
	  var senderList=new Array(0);
	  var sendtimeList=new Array(0);
	  
	  function addItem(id,remark,relUrl,absUrl,sender,sendtime){
	    var i=watchMmsIdList.length;
	    watchMmsIdList[i]=id;
	    remarkList[i]=unescape(remark);
	    relUrlList[i]=relUrl;
	    absUrlList[i]=absUrl;
	    senderList[i]=sender;
	    sendtimeList[i]=sendtime;
	    		    
	  }	
	  
	  function fillForm(idx){
        //alert(remarkList[idx]); 
	    $('watchmmsId').value=watchMmsIdList[idx];
	    $('attachRemark').value=remarkList[idx];
	    $('relateUrl').value=relUrlList[idx];
	    $('previewImg').src=absUrlList[idx];
	    $('sender').innerText=senderList[idx];
	    $('sendtime').innerText=sendtimeList[idx];
	    $('detailLayer').visibility='show';
	    //$('IMG_'+idx).border='2';
	    /*
	    var len = document.UploadFromMMSBean.checkbox.length; 
        var i;
        for (i = 0; i < len; i++) 
        { 
            if (i!=idx&&document.UploadFromMMSBean.checkbox[i].checked == true) 
            { 
                document.UploadFromMMSBean.checkbox[i].checked =false;
            } 
        } 
        */
        

	    
	  
	  }
	</script>
	
	
<template:titile value="从彩信图片中选取"/>
<%
  WatchMms mms;
  String watchId=request.getParameter("watchid");
%>

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

<html:form action="/UploadFromMMSAction">


	<%
	  String nullFlag="NO";
	  Object obj=request.getAttribute("mmsPicList"); 
	  if(obj==null||((List)obj).size()<1){
		  nullFlag="YES";
	  }
	  pageContext.setAttribute("nullFlag",nullFlag);
	
	%>
<c:choose>
<c:when test="${nullFlag=='YES'}">
<table>
<tr><td>还没收到该盯防位置的彩信图片</td></tr>
</table>
</c:when> 
<c:otherwise>
<table>	
<tr>
<td>    
		<c:forEach var="item" items="${mmsPicList}" varStatus="status" >
		<% 
		  mms=(WatchMms)pageContext.getAttribute("item");
		  String remark=mms.getRemark();
		  if(remark==null){
			  remark="";
		  }else{
			  remark=Escape.escape(mms.getRemark());
		  }
		%>
	    
	     <script language="javascript">	
	     addItem("<c:out value="${item.key}"/>","<%=remark%>","<%=mms.getRelateurl()%>","<%=mms.getAbsoluteurl()%>","<%=mms.getSender()%>","<%=mms.getSendtime()%>");	

	     </script>		


		<span id="SPAN_<c:out value='${status.count-1}'/>" onclick="fillForm(<c:out value='${status.count-1}'/>)" border="1">
					<img border='0' id="IMG_<c:out value='${status.count-1}'/>" 
					    src="<c:out value='${item.absoluteurl}'/>"
							width='32' height='32' />
		</span>
		
		<c:if test="${status.count%10==0&&status.count>1}"><br/></c:if>
		

		</c:forEach>
		</td>
		</tr>
			</table>
	<layer id="detailLayer" visibility="hide">
		<html:hidden property="watchId" value='<%=watchId%>' />
		<html:hidden property="watchmmsId" />
		<html:hidden property="relateUrl" />
		<fieldset>
			<legend>
				彩信详情：
			</legend>
			发送彩信的手机号：<label id="sender"></label>
			发送时间：<label id="sendtime"></label><br/>
			图片说明：<html:text property="attachRemark"></html:text>
			<br />
            <img id='previewImg' src="${ctx}/images/grayblank.jpg" height='300'/>
		</fieldset>
		<br />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:submit id="submit" styleClass="btn" >&nbsp;作为附件提交&nbsp;</html:submit>
	</layer>	
</c:otherwise>
</c:choose> 

	</html:form>
</body>
</html:html>
