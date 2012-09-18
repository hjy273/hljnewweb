<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.sparepartmanage.beans.SparepartBaseInfoBean;" %>

<html>
  <head>
    <title>showOneAllInfo</title>
    <script type="text/javascript">
    	function goBack() {
    		var url="SeparepartBaseInfoAction.do?method=showQueryAfterMod";
    		window.location.href=url;
    	}
    </script>
  </head>
  
  <body>
  	<br/>
  	<logic:present name="oneInfo" scope="request">
  		<template:titile value="备件详细信息"/>
  		<template:formTable namewidth="150" contentwidth="300" th2="内容">
  			<template:formTr name="备件名称">
				<bean:write name="oneInfo" property="sparePartName"/>
			</template:formTr>
			
			<template:formTr name="备件型号">
				<bean:write name="oneInfo" property="sparePartModel"/>
			</template:formTr>
			
			<template:formTr name="软件版本">
				<bean:write name="oneInfo" property="softwareVersion"/>
			</template:formTr>
			
			<template:formTr name="生产厂家">
				<bean:write name="oneInfo" property="productFactory"/>
			</template:formTr>
			
			<template:formTr name="备件备注">
				<bean:write name="oneInfo" property="sparePartRemark"/>
			</template:formTr>
			
			<template:formSubmit>
				<td>
				  	<input type="button" class="button" value="返回" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
				</td>
		   </template:formSubmit>
  		</template:formTable>
  	</logic:present>
  </body>
</html>
