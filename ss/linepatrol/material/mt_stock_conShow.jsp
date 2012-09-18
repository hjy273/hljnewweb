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
		<title>材料库存</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />		
		<template:titile value="材料库存一览表" />
			<display:table varTotals="totalMap" name="sessionScope.marterialStocksCon" id="currentRowObject" pagesize="18">
			<logic:equal value="1" property="deptype" name="LOGIN_USER">
				<display:column property="contractorname" title="代维名称" headerClass="subject"  sortable="true"/> 
			</logic:equal>
			<display:column property="typename" title="类型名称" headerClass="subject"  sortable="true"/> 
			<display:column property="modelname" title="规格名称" headerClass="subject"  sortable="true"/> 
			<display:column property="name" title="材料名称" headerClass="subject"  sortable="true"/> 
			<display:column property="address" title="存放地址" headerClass="subject"  sortable="true"/> 
			<display:column property="newstock" title="新增库存" headerClass="subject" total="true" sortable="true"/> 
			<display:column property="oldstock" title="利旧库存" headerClass="subject" total="true" sortable="true"/> 
			<display:column property="sum" sortable="true" title="合计" total="true" headerClass="subject" />		
				
			<display:footer>
<tr>
   <td>总计: </td>
   <td>新增库存
     <logic:equal value="1" property="deptype" name="LOGIN_USER">
     <span id="newstock"> <fmt:formatNumber pattern="#,#00.0000#"><bean:write name="totalMap"  property="column6"/></fmt:formatNumber></span>
      </logic:equal>
       <logic:equal value="2" property="deptype" name="LOGIN_USER">
    	  <span id="newstock"><fmt:formatNumber pattern="#,#00.0000#"><bean:write name="totalMap"  property="column5"/></fmt:formatNumber></span>
      </logic:equal>
   </td>
    <td>利旧库存:
    	<logic:equal value="1" property="deptype" name="LOGIN_USER">
         	<span id="oldstock"><fmt:formatNumber pattern="#,#00.0000#"><bean:write name="totalMap"  property="column7"/></fmt:formatNumber></span>
        </logic:equal>
        <logic:equal value="2" property="deptype" name="LOGIN_USER">
         	<span id="oldstock"><fmt:formatNumber pattern="#,#00.0000#"><bean:write name="totalMap"  property="column6"/></fmt:formatNumber></span>
        </logic:equal>
    </td>
<td>
  合计:
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
					  	<a href="javascript:exportList()">导出为Excel文件</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
			<tr>
				<td style="text-align:center;">
				<!-- <input name="action" class="button" value="返回"
						onclick="goBack();" type="button" />
						-->
						<input type="button" class="button" onclick="history.back()" value="返回"/>
				</td>
			</tr>
			
		
			
		</table>
	</body>
</html>
