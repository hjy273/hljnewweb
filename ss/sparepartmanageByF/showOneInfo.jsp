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
  		<template:titile value="������ϸ��Ϣ"/>
  		<template:formTable namewidth="150" contentwidth="300" th2="����">
  			<template:formTr name="��������">
				<bean:write name="oneInfo" property="sparePartName"/>
			</template:formTr>
			
			<template:formTr name="�����ͺ�">
				<bean:write name="oneInfo" property="sparePartModel"/>
			</template:formTr>
			
			<template:formTr name="����汾">
				<bean:write name="oneInfo" property="softwareVersion"/>
			</template:formTr>
			
			<template:formTr name="��������">
				<bean:write name="oneInfo" property="productFactory"/>
			</template:formTr>
			
			<template:formTr name="������ע">
				<bean:write name="oneInfo" property="sparePartRemark"/>
			</template:formTr>
			
			<template:formSubmit>
				<td>
				  	<input type="button" class="button" value="����" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
				</td>
		   </template:formSubmit>
  		</template:formTable>
  	</logic:present>
  </body>
</html>
