<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.sparepartmanage.beans.SparepartBaseInfoBean;" %>

<html>
  <head>
    <title>My JSP 'test.jsp' starting page</title>
    <script type="text/javascript" src="${ctx}/js/dojo.js"></script>
	<script type="text/javascript">
		dojo.require("dojo.widget.ComboBox");
	</script>
  </head>
  
  <body>
  	<select dojoType="ComboBox" dataUrl="${ctx}/SeparepartBaseInfoAction.do?method=getSpbName"
		 style="width: 180px;" name="name"  pageSize="10"  maxListLength="40" > 
	</select>
  </body>
</html>
