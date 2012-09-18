<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>查询材料规格</title>
    <script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
    <script type="text/javascript" src="./js/prototype.js"></script>
    <script type="text/javascript" src="./js/json2.js"></script>
	<script type="text/javascript">
	
	function onConChange() {
    		var params = Form.Element.serialize("constactid");
  			var ops = document.getElementById('addrID');
  			ops.options.length = 0;//清空下拉列表
  			ops.options.add(new Option("请选择...", ""));//增加提示信息
  			var url = "materialAllotAction.do?method=getAddressByCon"
  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForCon, asynchronous:true});
    	}
    	
    	function callbackForCon(originalRequest) {
    		var rst = originalRequest.responseText;
    		var queryRes = eval('('+rst+')');
    		var ops = document.getElementById('addrID');
    		for(var i = 0 ; i<queryRes.length; i++) {
  				ops.options.add(new Option(queryRes[i].address, queryRes[i].id));
  			}
  			var myGlobalHandlers = {onCreate:function () {
			Element.show("Loadingimg");
			}, onFailure:function () {
				alert("网络连接出现问题，请关闭后重试!");
			}, onComplete:function () {
				if (Ajax.activeRequestCount == 0) {
					Element.hide("Loadingimg");
				}
			}};
			Ajax.Responders.register(myGlobalHandlers);
    	}
    </script>
  </head>
  
  <body><br>
  	<template:titile value="查询材料调拨单明细"/>
  	<html:form action="/materialAllotAction.do?method=getMaterialAllots" >
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="调拨日期">
			<input name="changedate" class="inputtext"  style="width:215px"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly/>
  		</template:formTr> 
  	<logic:equal value="1" name="LOGIN_USER" property="deptype">
  		<template:formTr name="代维名称">
  		<select name="constactid"  id="constactid" class="inputtext" style="width:215px" onchange="onConChange();" >
  			<option value="">请选择...</option>
         <logic:present scope="request" name="cons">
         	<logic:iterate id="r" name="cons">
		         <option value="<bean:write name="r" property="contractorid" />">
		         	<bean:write name="r" property="contractorname"/>
		         </option>
		    </logic:iterate>
         </logic:present>
      </select><img id="Loadingimg" src="./images/ajaxtags/indicator.gif" style="display:none">
  		</template:formTr> 
  		<template:formTr name="存放地点">
  		<select name="addrID"  id="addrID" class="inputtext" style="width:215px" >
  			 <option value="">请选择...</option>
	      </select>
  		</template:formTr> 
  		</logic:equal>
  		<logic:equal value="2" name="LOGIN_USER" property="deptype">
  		<template:formTr name="存放地点">
	  	 <select name="addrID"  id="addrID" class="inputtext" style="width:215px"  >
	  			<option value="">请选择...</option>
	         <logic:present scope="request" name="address">
	         	<logic:iterate id="r" name="address">
			         <option value="<bean:write name="r" property="id" />">
			         	<bean:write name="r" property="address"/>
			         </option>
			    </logic:iterate>
	         </logic:present>
	      </select>
	      </template:formTr>
  		</logic:equal>
  		<template:formTr name="调拨状态">
	  	  <select name="allotState"  class="inputtext" style="width:215px" id="allotState">
			    <option value="0">不限</option>
			    <option value="1">调入</option>
			    <option value="2">调出</option>
	      </select>
  		</template:formTr> 	
  		 <template:formSubmit>
				      	<td>
                          <html:submit property="action" styleClass="button">查询</html:submit>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >重置</html:reset>
				      	</td>
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
  </body>
</html>
