<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>分布统计总表</title>
 <script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<script type="text/javascript">
			function validate(){
				var startmonth = document.getElementById("startmonth").value;
				var endmonth = document.getElementById("endmonth").value;
				if(startmonth==""){
					alert("起始月份不能为空!");
					document.getElementById("startmonth").focus();
					return;
				}
				if(endmonth==""){
				   alert("结束月份不能为空!");
				   document.getElementById("endmonth").focus();
				   return ;
				}
				query.submit();
			}
		</script>
  </head>
  
  <body><br>
  	<template:titile value="分布统计总表"/>
  	<html:form action="/remedyDistriStatAction.do?method=getDistriReports" styleId="query">
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="起始月份">
  				<input name="startmonth" class="inputtext" id="startmonth" style="width:215"
				onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly/>
  		</template:formTr>
  		<template:formTr name="结束月份">
  				<input name="endmonth" class="inputtext" id="endmonth" style="width:215"
				onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly/>
  		</template:formTr>
  		<logic:equal value="1" name="LOGIN_USER" property="deptype">
	  		<template:formTr name="所属代维">
	  				<select name="contractorid" class="inputtext" style="width:215px" id="contractorid" onchange="onConChange();">
	  						<option value="">不限</option>
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
                          <html:button property="action" styleClass="button" onclick="validate();">查询</html:button>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >重置</html:reset>
				      	</td>
			</template:formSubmit>
  	</template:formTable>
  	
  	</html:form>
  </body>
</html>
