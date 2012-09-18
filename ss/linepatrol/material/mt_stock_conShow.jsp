<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">		
		exportList=function(){
			var newstock = document.getElementById("newstock").outerText
			var oldstock = document.getElementById("oldstock").outerText;
			var sum = document.getElementById("sum").outerText;
			//alert(newstock+" old:"+oldstock+" sum"+sum);
			var url="${ctx}/materialStockAction.do?method=exportStoksList&p=con&new="+newstock+"&old="+oldstock+"&sum="+sum;
			self.location.replace(url);
		}
		
		function  goBack(){
			var url="${ctx}/materialStockAction.do?method=queryMaterialStrockByConForm";
			self.location.replace(url);
		}
		</script>
		<title>���Ͽ��</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />		
		<template:titile value="���Ͽ��һ����" />
			<display:table varTotals="totalMap" name="sessionScope.marterialStocksCon" id="currentRowObject" pagesize="18">
			<logic:equal value="1" property="deptype" name="LOGIN_USER">
				<display:column property="contractorname" title="��ά����" headerClass="subject"  sortable="true"/> 
			</logic:equal>
			<display:column property="typename" title="��������" headerClass="subject"  sortable="true"/> 
			<display:column property="modelname" title="�������" headerClass="subject"  sortable="true"/> 
			<display:column property="name" title="��������" headerClass="subject"  sortable="true"/> 
			<display:column property="address" title="��ŵ�ַ" headerClass="subject"  sortable="true"/> 
			<display:column property="newstock" title="�������" headerClass="subject" total="true" sortable="true"/> 
			<display:column property="oldstock" title="���ɿ��" headerClass="subject" total="true" sortable="true"/> 
			<display:column property="sum" sortable="true" title="�ϼ�" total="true" headerClass="subject" />		
				
			<display:footer>
<tr>
   <td>�ܼ�: </td>
   <td>�������
     <logic:equal value="1" property="deptype" name="LOGIN_USER">
     <span id="newstock"> <fmt:formatNumber pattern="#,#00.0000#"><bean:write name="totalMap"  property="column6"/></fmt:formatNumber></span>
      </logic:equal>
       <logic:equal value="2" property="deptype" name="LOGIN_USER">
    	  <span id="newstock"><fmt:formatNumber pattern="#,#00.0000#"><bean:write name="totalMap"  property="column5"/></fmt:formatNumber></span>
      </logic:equal>
   </td>
    <td>���ɿ��:
    	<logic:equal value="1" property="deptype" name="LOGIN_USER">
         	<span id="oldstock"><fmt:formatNumber pattern="#,#00.0000#"><bean:write name="totalMap"  property="column7"/></fmt:formatNumber></span>
        </logic:equal>
        <logic:equal value="2" property="deptype" name="LOGIN_USER">
         	<span id="oldstock"><fmt:formatNumber pattern="#,#00.0000#"><bean:write name="totalMap"  property="column6"/></fmt:formatNumber></span>
        </logic:equal>
    </td>
<td>
  �ϼ�:
    	<logic:equal value="1" property="deptype" name="LOGIN_USER">
         	<span id="sum"><fmt:formatNumber pattern="#,#00.0000#"><bean:write name="totalMap"  property="column8"/></fmt:formatNumber></span>
        </logic:equal>
        <logic:equal value="2" property="deptype" name="LOGIN_USER">
         	<span id="sum"><fmt:formatNumber pattern="#,#00.0000#"><bean:write name="totalMap"  property="column7"/></fmt:formatNumber></span>
        </logic:equal>
</td>
</tr>
</display:footer>	
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="marterialStocksCon">
					  	<a href="javascript:exportList()">����ΪExcel�ļ�</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
			<tr>
				<td style="text-align:center;">
				<!-- <input name="action" class="button" value="����"
						onclick="goBack();" type="button" />
						-->
						<input type="button" class="button" onclick="history.back()" value="����"/>
				</td>
			</tr>
			
		
			
		</table>
	</body>
</html>
