<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
  <head>
    
    <title>��ѯ�����̵����뵥</title>
  <script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
  </head>
  
  <body><br>
  	<template:titile value="��ѯ�����̵����뵥"/>
  	<html:form action="/mtUsedAction?method=mobileMtUsedQueryList" >
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="��ά����">
  				<input type="text"  class="inputtext" name="contractorid" style="width:205px;" maxlength="20"/>
  		</template:formTr>
  		<template:formTr name="����ʱ��">
  				<input id="createtime" name="createtime" class="inputtext"  style="width:205px"
				onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly/>
  		</template:formTr>
  		 <template:formSubmit>
				      	<td>
                          <html:submit property="action" styleClass="button">��ѯ</html:submit>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >����</html:reset>
				      	</td>
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
  </body>
</html>
