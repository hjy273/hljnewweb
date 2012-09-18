<%@include file="/common/header.jsp"%>
<html>
<script type="text/javascript" src="./js/prototype.js"></script>
	<script type="text/javascript">
	checkValid=function(addForm){
		if(addForm.sparePartId.value==""){
			alert("入库备件不能为空！");
			addForm.sparePartId.focus();
			return false;
		}
		if(addForm.serialNumber.value==""){
			alert("备件序列号不能为空！");
			addForm.serialNumber.focus();
			return false;
		}
		if(addForm.storagePosition.value==""){
			alert("保存位置不能为空！");
			addForm.storagePosition.focus();
			return false;
		}
		
		return true;
	}
	
    
    function query(){
			var serialNumber = document.getElementById("serialNumber").value;
			var oldid = document.getElementById("oldid").value;
			if(serialNumber == oldid){
				var form1 = document.getElementById("addForm");
				var s = checkValid(form1);
				if(s){
					addForm.submit();
				}
				return;
			}
			//addForm.submit.disabled=true;   
			var params = Form.Element.serialize("serialNumber")//备件序列号
			var url = "SparepartStorageAction.do?method=querySerialNumber";
  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackQuery, asynchronous:true});
		}
		
		function callbackQuery(originalRequest) {
  			var message = originalRequest.responseText;
  			var span = document.getElementById('serialNumberSpan');
  			if(message=="0"){
				span.innerHTML="";
				var form1 = document.getElementById("addForm");
				var s = checkValid(form1);
				if(s){
					addForm.submit();
				}
			}
			if(message=="1"){		
				span.innerHTML="<font color=red>序列号已经存在,请重新填写!</font>";
				addForm.serialNumber.value=""
				addForm.serialNumber.focus();
			}  		
			//addForm.submit.disabled=false;	
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
		
    
	addGoBack=function(){
		//var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageForOneByMobile";
		//self.location.replace(url);
		try{
	   		 history.go(-1);
   		 }catch(e){
    	     alert(e);
         }
	   }
	</script>
	<body>
		<br>
		<template:titile value="修改移动公司入库" />
		<html:form
			action="/SparepartStorageAction.do?method=updateSavedStorage"
			styleId="addForm">
			<input type="hidden" id="initStorage" name="initStorage" value="<bean:write name="one_storage"  property="initStorage"/>"/>
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="入库备件">
					<input name="tid"
						value="<bean:write name="one_storage"  property="tid"/>"
						type="hidden" class="inputtext" style="width:250;" maxlength="25" />
					<input name="sparePartId"
						value="<bean:write name="one_storage"  property="sparePartId"/>"
						type="hidden" class="inputtext" style="width:250;" maxlength="25" />
					<bean:write name="one_storage" property="sparePartName" />
				</template:formTr>
				<template:formTr name="备件序列号">
					<input type="hidden" id="oldid" value="<bean:write name="one_storage"  property="serialNumber"/>"/>
					<input name="serialNumber" id="serialNumber"
						value="<bean:write name="one_storage"  property="serialNumber"/>"
						type="text" class="inputtext" style="width:250;" maxlength="25"/><br><span id="serialNumberSpan"></span>
				</template:formTr>
				<template:formTr name="保存位置">					
					<html:select property="storagePosition" style="width:250px;" styleClass="inputtext">
						<html:options collection="saveOptions" property="id" labelProperty="name"/>
					</html:select>		
				</template:formTr>
				<template:formTr name="备件所在部门">
					<input name="departId"
						value="<bean:write name="one_storage"  property="departId"/>"
						type="hidden" />
					<bean:write name="one_storage" property="departName" />
				</template:formTr>
					<input name="storageNumber" value="1" type="hidden" />
				<template:formTr name="操作人">
					<%=(String) request.getAttribute("user_name")%>
				</template:formTr>
				<template:formTr name="入库备注">
					<textarea name="storageRemark" class="inputtextarea"
						style="width:250;" rows="5"><bean:write name="one_storage" property="storageRemark" /></textarea>
				</template:formTr>

				<template:formSubmit>
					<td>
						<input type="button" class="button" value="修改入库" onclick="query()">
					</td>
					<td>
						<html:reset styleClass="button">重置	</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBack()">返回</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
