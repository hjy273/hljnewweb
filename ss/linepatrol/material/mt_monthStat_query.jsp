<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>��ѯ������ͳ��</title>
    <script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		
	<script type="text/javascript">
	function checkAddInfo() {
  			var contraid = document.getElementById('contractorid');
  			if(contraid.value.length == 0) {
  				alert("��ѡ���ά!");
  				contraid.focus();
  				return false;
  			}
  			var Month = document.getElementById('Month');
  			if(Month.value.length == 0) {
  				alert("��ѡ���·�!");
  				Month.focus();
  				return false;
  			}
  			return true;
  	}
  			
	</script>
  </head>
  
  <body><br>
  	<template:titile value="��ѯ������ͳ��"/>
  	<html:form action="/MonthStateAction.do?method=statMonth" styleId="materialYearStat" onsubmit="return checkAddInfo()">
  	<template:formTable namewidth="150" contentwidth="300">
  	<logic:equal value="1" name="LOGIN_USER" property="deptype">
  		<template:formTr name="��ά">
  			<select class="inputtext" style="width:205px;" id="contractorid" name="contractorid">
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
  		<template:formTr name="�·�">
  				<input name="Month" class="inputtext"  style="width:205px" id="Month"
				onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly/>
  		</template:formTr>  		
  		 <template:formSubmit>
				      	<td>
                          <html:submit property="action" styleClass="button"> ��ѯ</html:submit>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >����</html:reset>
				      	</td>
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
  </body>
</html>
