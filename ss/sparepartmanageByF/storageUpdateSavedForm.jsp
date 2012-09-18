<%@include file="/common/header.jsp"%>
<html>
<script type="text/javascript" src="./js/prototype.js"></script>
	<script type="text/javascript">
	checkValid=function(addForm){
		if(addForm.sparePartId.value==""){
			alert("��ⱸ������Ϊ�գ�");
			addForm.sparePartId.focus();
			return false;
		}
		if(addForm.serialNumber.value==""){
			alert("�������кŲ���Ϊ�գ�");
			addForm.serialNumber.focus();
			return false;
		}
		if(addForm.storagePosition.value==""){
			alert("����λ�ò���Ϊ�գ�");
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
			var params = Form.Element.serialize("serialNumber")//�������к�
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
				span.innerHTML="<font color=red>���к��Ѿ�����,��������д!</font>";
				addForm.serialNumber.value=""
				addForm.serialNumber.focus();
			}  		
			//addForm.submit.disabled=false;	
  			var myGlobalHandlers = {onCreate:function () {
			Element.show("Loadingimg");
			}, onFailure:function () {
				alert("�������ӳ������⣬��رպ�����!");
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
		<template:titile value="�޸��ƶ���˾���" />
		<html:form
			action="/SparepartStorageAction.do?method=updateSavedStorage"
			styleId="addForm">
			<input type="hidden" id="initStorage" name="initStorage" value="<bean:write name="one_storage"  property="initStorage"/>"/>
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="��ⱸ��">
					<input name="tid"
						value="<bean:write name="one_storage"  property="tid"/>"
						type="hidden" class="inputtext" style="width:250;" maxlength="25" />
					<input name="sparePartId"
						value="<bean:write name="one_storage"  property="sparePartId"/>"
						type="hidden" class="inputtext" style="width:250;" maxlength="25" />
					<bean:write name="one_storage" property="sparePartName" />
				</template:formTr>
				<template:formTr name="�������к�">
					<input type="hidden" id="oldid" value="<bean:write name="one_storage"  property="serialNumber"/>"/>
					<input name="serialNumber" id="serialNumber"
						value="<bean:write name="one_storage"  property="serialNumber"/>"
						type="text" class="inputtext" style="width:250;" maxlength="25"/><br><span id="serialNumberSpan"></span>
				</template:formTr>
				<template:formTr name="����λ��">					
					<html:select property="storagePosition" style="width:250px;" styleClass="inputtext">
						<html:options collection="saveOptions" property="id" labelProperty="name"/>
					</html:select>		
				</template:formTr>
				<template:formTr name="�������ڲ���">
					<input name="departId"
						value="<bean:write name="one_storage"  property="departId"/>"
						type="hidden" />
					<bean:write name="one_storage" property="departName" />
				</template:formTr>
					<input name="storageNumber" value="1" type="hidden" />
				<template:formTr name="������">
					<%=(String) request.getAttribute("user_name")%>
				</template:formTr>
				<template:formTr name="��ⱸע">
					<textarea name="storageRemark" class="inputtextarea"
						style="width:250;" rows="5"><bean:write name="one_storage" property="storageRemark" /></textarea>
				</template:formTr>

				<template:formSubmit>
					<td>
						<input type="button" class="button" value="�޸����" onclick="query()">
					</td>
					<td>
						<html:reset styleClass="button">����	</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBack()">����</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
