<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>查询材料年统计</title>
    <script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		
	<script type="text/javascript">
	function checkAddInfo() {
  			var contraid = document.getElementById('contraid');
  			if(contraid.value.length == 0) {
  				alert("请选择代维!");
  				contraid.focus();
  				return;
  			}
  			var year = document.getElementById('year');
  			if(year.value.length == 0) {
  				alert("请选择年份!");
  				year.focus();
  				return;
  			}
  	}
  			
	</script>
  </head>
  
  <body><br>
  	<template:titile value="查询材料年统计"/>
  	<html:form action="/materialYearStatAction.do?method=materialYearStat" styleId="materialYearStat">
  	<template:formTable namewidth="150" contentwidth="300">
  	<logic:equal value="1" name="LOGIN_USER" property="deptype">
  		<template:formTr name="代维">
  			<select class="inputtext" style="width:205px;" id="contraid" name="contraid">
  				<logic:present scope="request" name="cons">
  					<logic:iterate id="c" name="cons">
  						<option value="<bean:write name="c" property="contractorid"/>">
  							<bean:write name="c" property="contractorname"/>
  						</option>
  					</logic:iterate>
  				</logic:present>
  			</select>
  		</template:formTr>
  	</logic:equal>
  		<template:formTr name="年份">
  				<input name="year" class="inputtext"  style="width:205px"
				onfocus="WdatePicker({dateFmt:'yyyy'})" readonly/>
  		</template:formTr>  		
  		 <template:formSubmit>
				      	<td>
                          <html:submit property="action" styleClass="button"> 查询</html:submit>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >重置</html:reset>
				      	</td>
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
  </body>
</html>
