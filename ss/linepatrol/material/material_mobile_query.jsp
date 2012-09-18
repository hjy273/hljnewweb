<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
  <head>
    
    <title>查询材料盘点申请单</title>
  <script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
  </head>
  
  <body><br>
  	<template:titile value="查询材料盘点申请单"/>
  	<html:form action="/mtUsedAction?method=mobileMtUsedQueryList" >
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="代维名称">
  				<input type="text"  class="inputtext" name="contractorid" style="width:205px;" maxlength="20"/>
  		</template:formTr>
  		<template:formTr name="申请时间">
  				<input id="createtime" name="createtime" class="inputtext"  style="width:205px"
				onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly/>
  		</template:formTr>
  		 <template:formSubmit>
				      	<td>
                          <html:submit property="action" styleClass="button">查询</html:submit>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >重置</html:reset>
				      	</td>
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
  </body>
</html>
