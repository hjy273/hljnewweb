<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>按材料查询材料库存</title>
    <script type="text/javascript" src="./js/prototype.js"></script>
    <script type="text/javascript" src="./js/json2.js"></script>
	<script type="text/javascript">
		function onTypeChange() {
	    		var params = Form.Element.serialize("typeid");
	  			var ops = document.getElementById('modelid');
	  			ops.options.length = 0;//清空下拉列表
	  			ops.options.add(new Option("请选择...", ""));//增加提示信息
	  			var url = "materialStockAction.do?method=getModelByType"
	  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForType, asynchronous:true});
	    	}
	    	
	    	function callbackForType(originalRequest) {
	    		var rst = originalRequest.responseText;
	    		var queryRes = eval('('+rst+')');
	    		var ops = document.getElementById('modelid');
	    		for(var i = 0 ; i<queryRes.length; i++) {
	  				ops.options.add(new Option(queryRes[i].modelname, queryRes[i].id));
	  			}
	  			var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
				}, onFailure:function () {
					alert("网络连接出现问题，请关闭后重试!");
				}, onComplete:function () {
					if (Ajax.activeRequestCount == 0) {
						Element.hide("Loadingimg");
					}
		    	 }
		       }
		    }
		    
		    function onModelChange() {
	    		var params = Form.Element.serialize("modelid");
	  			var ops = document.getElementById('mtid');
	  			ops.options.length = 0;//清空下拉列表
	  			ops.options.add(new Option("请选择...", ""));//增加提示信息
	  			var url = "materialStockAction.do?method=getMTByModel"
	  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForModel, asynchronous:true});
	    	}
	    	
	    	function callbackForModel(originalRequest) {
	    		var rst = originalRequest.responseText;
	    		var queryRes = eval('('+rst+')');
	    		var ops = document.getElementById('mtid');
	    		for(var i = 0 ; i<queryRes.length; i++) {
	  				ops.options.add(new Option(queryRes[i].name, queryRes[i].id));
	  			}
	  			var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg2");
				}, onFailure:function () {
					alert("网络连接出现问题，请关闭后重试!");
				}, onComplete:function () {
					if (Ajax.activeRequestCount == 0) {
						Element.hide("Loadingimg");
					}
		    	 }
		       }
		    }
			
		</script>
  </head>
  
  <body><br>
  	<template:titile value="按材料查询材料库存"/>
  	<html:form action="/materialStockAction.do?method=getMarterialStocksByMT" >
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="材料类型">
  				<select name="typeid" class="inputtext" style="width:215px" id="typeid" onchange="onTypeChange();">
  						<option value="">不限</option>
         				<logic:present scope="request" name="types">
         					<logic:iterate id="r" name="types">
		                    	<option value="<bean:write name="r" property="id" />"><bean:write name="r" property="typename"/></option>
		                	</logic:iterate>
         				</logic:present>
         		</select><img id="Loadingimg" src="./images/ajaxtags/indicator.gif" style="display:none">
  		</template:formTr>
  		<template:formTr name="材料规格">
  				<select name="modelid" class="inputtext" style="width:215px" id="modelid" onchange="onModelChange();">
  						<option value="">不限</option>         				
         		</select>
  		</template:formTr>
  		<template:formTr name="材料名称">
  				<select name="mtid" class="inputtext" style="width:215px" id="mtid">
  						<option value="">不限</option>         				
         		</select><img id="Loadingimg2" src="./images/ajaxtags/indicator.gif" style="display:none">
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
