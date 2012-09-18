<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript" src="./js/prototype.js"></script>
	<script type="text/javascript">
	checkValid=function(addForm){
		var ids = document.getElementsByName('serialId');    //新申请的备件序列号
		var idNum=0;   
		for(var i=0;i<ids.length;i++){  
			  if(ids[i].checked==true){            
			       idNum++;                   
			   }   
		} 
		if(idNum==0){
			alert("请选择备件序列号！");
			return false;
		}		
	
	//	var checkBoxChecked = document.getElementById("serialId").checked; 
		//if(!checkBoxChecked){
			//alert("请选择备件序列号！");
			//return false;
		//}
		
		return true;
	}
	addGoBack=function(){
		window.location.href = "${ctx}/SparepartStorageAction.do?method=listSparepartStorage";
		//self.location.replace(url);
	}
	addGoBackMod=function() {
		window.location.href = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageForRe";
		//self.location.replace(url);
	}
    
    function getSerialNum(){
    	var baseid=document.getElementById("baseid").value;
  			var params = Form.Element.serialize("storagePosition")+"&baseid="+baseid+"&state=02";//获得要查找的备件名称
  			span = document.getElementById('seriNum').innerHTML="";  			
  			var url = "SparepartStorageAction.do?method=getSerialNmuByPositon"
  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForModel, asynchronous:true});
  		}
  	function callbackForModel(originalRequest) {
  		var rst = originalRequest.responseText;
  		var queryRes = eval('('+rst+')');//解析结果
  		var span = document.getElementById('seriNum');
  		for(var i = 0 ; i < queryRes.length; i++) {
  			var newInput = document.createElement("INPUT");
	        newInput.type="checkbox";
	        newInput.id = "serialId";
	        newInput.name = "serialId";
	        newInput.value=queryRes[i]
  			span.appendChild(newInput);
  			var text = document.createTextNode(" "+queryRes[i]);
  			span.appendChild(text);
  			var newline= document.createElement("br");   
        	span.appendChild(newline); 
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
	<body>
		<template:titile value="代维单位归还" />
		<html:form
			action="/SparepartStorageAction.do?method=giveBackToStorage"
			styleId="addForm" onsubmit="return checkValid(this);">
			<input type="hidden" id="baseid" value="<%=request.getAttribute("baseid") %>">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="生产厂商">
					<bean:write property="productFactory" name="SparepartBaseInfoBean"/>
				</template:formTr>
				<template:formTr name="备件名称">
					<bean:write property="sparePartName" name="SparepartBaseInfoBean"/>
				</template:formTr>
				<template:formTr name="备件型号">
					<bean:write property="sparePartModel" name="SparepartBaseInfoBean"/>
				</template:formTr>
				<template:formTr name="备件当前所在位置">
					<html:select property="storagePosition" style="width:250px;" styleClass="inputtext" onchange="getSerialNum();">
						<html:options collection="optionsW" property="storage_position" labelProperty="name" />
					</html:select>		
				</template:formTr>
				<template:formTr name="备件序列号列表">
					<span id="seriNum">
						<logic:iterate id="item" name="initSerialNumbers">
			             <input type="checkbox" name="serialId" id="serialId" value="<bean:write name='item'/>" />&nbsp;<bean:write name="item"/><br>
						 </logic:iterate>
					</span>
				</template:formTr>
				<template:formTr name="归还位置">
					<html:select property="initStorage" style="width:250px;" styleClass="inputtext">
						<html:options collection="returnOptions" property="id" labelProperty="name" />
					</html:select>		
				</template:formTr>
				<template:formTr name="操作人">
					<%=(String) request.getAttribute("user_name")%>
					<input name="storagePerson" type="hidden"
						value="<%=(String) request.getAttribute("user_id")%>"
						 />
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit styleClass="button">归还</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">重置</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBackMod()">返回</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
