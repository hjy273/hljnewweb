<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>�ֲ�ͳ���ܱ�</title>
 <script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
	
		<script type="text/javascript">
			goBack=function(){
			var url = "${ctx}/remedyDistriStatAction.do?method=queryDistriReportForm";
		    self.location.replace(url);
		}     
		exportList=function(){
	     	var url="${ctx}/remedyDistriStatAction.do?method=exportReport";
			self.location.replace(url);
		}
		</script>
  </head>
  
  <body><br/>
  	<template:titile value="�ֲ�ͳ���ܱ�"/>
  		<table  border="1" width="90%" bgcolor="#FFFFFF" align="center"  cellpadding="0"
			cellspacing="0"  bordercolor="#000000" style="border-collapse:collapse " >
  			<tr height="50px;" align="center">
  				<th><font size="3">ά����λ</font></th>
  				<th><font size="3">ά������</font></th>
  				<th><font size="3"><bean:write name="time"/>�·ݹ������ֲ�ͳ���ܱ�</font></th>
  				<th><font size="3">�ƶ���ά������Ԫ��</font></th>
  			</tr>
  			<%int i = 0;  %>
  			<logic:iterate id="report" name="map"> 
  				<tr align="center">
  					<td><bean:write name="report" property="key"/></td>
  					<td><table  width="100%" border="0"   bordercolor="#000000" align="center" cellpadding="0" cellspacing="0"  style="border-collapse:collapse ">	
  					<%int m = 1; int n =1; %>
   						<logic:iterate id="bean" name="report"  property="value"> 
	   							<tr height="28px;" align="center" style="line-height: 28px">
	   							  <bean:size id="size"  name="report"  property="value"/>
	   							  <bean:define id="sizer"  name="size"/>
	   							  <%
	   							    Object obj =sizer;
	   							    int len = Integer.parseInt(obj.toString());
	   							    if(m==len){%>
	   							    	<td style="border:0px;" align="center"><bean:write name="bean" property="town"/></td>
	   							    <% }else{%>
	   							   	    <td style="border-bottom:1px solid #000000;border-collapse:collapse"><bean:write name="bean" property="town"/></td>
	   							   <% }m++; %>
	   							</tr>
   						</logic:iterate></table>
   					</td>
   					<td><table width="100%" border="0"   bordercolor="#000000" align="center" cellpadding="0" cellspacing="0"  style="border-collapse:collapse ">
   						<logic:iterate id="bean" name="report"  property="value"> 
	   							<tr height="28px;" align="center" style="line-height: 28px">
	   							 <bean:size id="size"  name="report"  property="value"/>
	   							  <bean:define id="sizer"  name="size"/>
	   								<%
	   								Object obj =sizer;
	   							    int len = Integer.parseInt(obj.toString());
	   								if(n==len){%>
	   							    	<td style="border:0px;" align="center"><bean:write name="bean" property="totalfee"/></td>
	   							    <% }else{%>
	   							   	    <td align="center" style="border-bottom:1px solid #000000;border-collapse:collapse"><bean:write name="bean" property="totalfee"/></td>
	   							   <% }n++; %>
	   							</tr>
   						</logic:iterate></table>
   					</td>
   					<% 	
					   if(i==0){   			
   					%>
   					<bean:size id="size" name="list"/>
   					<td rowspan="<bean:write name='size'/>"><font size="4" style="font-weight: bold;"><bean:write name="sumfee"/></font></td>
   					<%}i++; %>
			   	 </tr>
			</logic:iterate>
  		</table>
  		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr height="50px;">
		    	<td>
		    		 <logic:notEmpty name="map">
					  	<a href="javascript:exportList()">����ΪExcel�ļ�</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
			<tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="����"
						onclick="goBack();" type="button" />
				</td>
			</tr>
			
		</table>
  </body>
</html>
