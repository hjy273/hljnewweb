<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.sparepartmanage.beans.SparepartBaseInfoBean;" %>

<html>
  <head>
    <title>querySparepart</title>
    <script type="text/javascript" src="./js/prototype.js"></script>
    <script type="text/javascript" src="./js/json2.js"></script>
    <script type="text/javascript">
    	function onFacChange() {
    		var params = Form.Element.serialize("productFactory");//获得要查找的备件厂商
  			var ops = document.getElementById('sparePartName');//获取备件名称的sel
  			ops.options.length = 0;//清空下拉列表
  			ops.options.add(new Option("===选择所有名称===", ""));//增加提示信息
  			var url = "SeparepartBaseInfoAction.do?method=getNameByFac"
  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForName, asynchronous:true});
    	}
    	
    	function callbackForName(originalRequest) {
    		var rst = originalRequest.responseText;
    		var queryRes = eval('('+rst+')');//解析结果
    		var ops = document.getElementById('sparePartName');//获取备件名称的sel
    		for(var i = 0 ; i < queryRes.length; i++) {
  				ops.options.add(new Option(queryRes[i].spare_part_name, queryRes[i].spare_part_name));
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
    
    
  		function onNameChange() {
  			var params = Form.Element.serialize("sparePartName") + "&" + Form.Element.serialize("productFactory");//获得要查找的备件名称
  			var ops = document.getElementById('sparePartModel');//获取备件型号的sel
  			ops.options.length = 0;//清空下拉列表
  			ops.options.add(new Option("===选择所有型号===", ""));//增加提示信息
  			var url = "SeparepartBaseInfoAction.do?method=getModelByName"
  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForModel, asynchronous:true});
  		}
  		
  		function callbackForModel(originalRequest) {
  			var rst = originalRequest.responseText;
  			var queryRes = eval('('+rst+')');//解析结果
  			var ops = document.getElementById('sparePartModel');//获取备件型号的sel
  			for(var i = 0 ; i < queryRes.length; i++) {
  				ops.options.add(new Option(queryRes[i].spare_part_model, queryRes[i].spare_part_model));
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
  		
  		function onModelChange() {
  			var params = Form.Element.serialize("sparePartModel") + "&" + Form.Element.serialize("productFactory") + "&" + Form.Element.serialize("sparePartName");//获得要查找的备件型号
  			var ops = document.getElementById('softwareVersion');//获取软件版本的sel
  			ops.options.length = 0;//清空下拉列表
  			ops.options.add(new Option("===选择所有版本===", ""));//增加提示信息
  			var url = "SeparepartBaseInfoAction.do?method=getVersionByModel"
  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForVersion, asynchronous:true});
  		}
  		
  		function callbackForVersion(originalRequest) {
  			var rst = originalRequest.responseText;
  			var queryRes = eval('('+rst+')');//解析结果
  			var ops = document.getElementById('softwareVersion');//获取软件版本的sel
  			for(var i = 0 ; i < queryRes.length; i++) {
  				ops.options.add(new Option(queryRes[i].software_version, queryRes[i].software_version));
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
  
  <body>
  		<br />
        <template:titile value="按条件查询备件信息"/>
         <html:form action="/SeparepartBaseInfoAction?method=showQueryResult"   styleId="queryForm2" >
         	<template:formTable namewidth="200"  contentwidth="200">
         		<template:formTr name="生产厂家">
         			<select name="productFactory" class="inputtext" style="width:180px" onchange="onFacChange()">
         				<option value="">===选择所有厂家===</option>
         				<logic:present scope="request" name="facList">
         					<logic:iterate id="facListId" name="facList">
		                    	<option value="<bean:write name="facListId" />"><bean:write name="facListId"/></option>
		                	</logic:iterate>
         				</logic:present>
         			</select>
         			<img id="Loadingimg" src="./images/ajaxtags/indicator.gif" style="display:none">
         		</template:formTr>
         		
         		<template:formTr name="备件名称"  >
         			<select name="sparePartName" class="inputtext" style="width:180px" onchange="onNameChange()" id="sparePartName">
         				<option value="">===选择所有名称===</option>
         			</select>
         		</template:formTr>
         		
         		<template:formTr name="备件型号" >
         			<select name="sparePartModel" class="inputtext" style="width:180px" id="sparePartModel" onchange="onModelChange()">
         				<option value="">===选择所有型号===</option>
         			</select>
         		</template:formTr>
         		
         		<template:formTr name="软件版本">
         			<select name="softwareVersion" class="inputtext" style="width:180px" id="softwareVersion">
         				<option value="">===选择所有版本===</option>
         			</select>
         		</template:formTr>
         		
         		<template:formSubmit>
         				<td>
                          <html:submit property="action" styleClass="button" >查询 </html:submit>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >重置	</html:reset>
				      	</td>
         		</template:formSubmit>
         		
         	</template:formTable>
         </html:form>
  </body>
</html>
