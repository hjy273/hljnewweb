<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>�ֲ�ͳ���ܱ�</title>
 <script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<script type="text/javascript">
			function validate(){
				var startmonth = document.getElementById("startmonth").value;
				var endmonth = document.getElementById("endmonth").value;
				if(startmonth==""){
					alert("��ʼ�·ݲ���Ϊ��!");
					document.getElementById("startmonth").focus();
					return;
				}
				if(endmonth==""){
				   alert("�����·ݲ���Ϊ��!");
				   document.getElementById("endmonth").focus();
				   return ;
				}
				query.submit();
			}
		</script>
  </head>
  
  <body><br>
  	<template:titile value="�ֲ�ͳ���ܱ�"/>
  	<html:form action="/remedyDistriStatAction.do?method=getDistriReports" styleId="query">
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="��ʼ�·�">
  				<input name="startmonth" class="inputtext" id="startmonth" style="width:215"
				onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly/>
  		</template:formTr>
  		<template:formTr name="�����·�">
  				<input name="endmonth" class="inputtext" id="endmonth" style="width:215"
				onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly/>
  		</template:formTr>
  		<logic:equal value="1" name="LOGIN_USER" property="deptype">
	  		<template:formTr name="������ά">
	  				<select name="contractorid" class="inputtext" style="width:215px" id="contractorid" onchange="onConChange();">
	  						<option value="">����</option>
	         				<logic:present scope="request" name="cons">
	         					<logic:iterate id="r" name="cons">
			                    	<option value="<bean:write name="r" property="contractorid" />"><bean:write name="r" property="contractorname"/></option>
			                	</logic:iterate>
	         				</logic:present>
	         		</select>
	  		</template:formTr>
  		</logic:equal>
  		 <template:formSubmit>
				      	<td>
                          <html:button property="action" styleClass="button" onclick="validate();">��ѯ</html:button>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >����</html:reset>
				      	</td>
			</template:formSubmit>
  	</template:formTable>
  	
  	</html:form>
  </body>
</html>
