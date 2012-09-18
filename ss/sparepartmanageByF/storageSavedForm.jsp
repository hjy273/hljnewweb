<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript" src="./js/prototype.js"></script>
    <script type="text/javascript" src="./js/json2.js"></script>
	<script type="text/javascript">
	checkValid=function(addForm){
		if(addForm.sparePartId.value==""){
			alert("��ѡ����ⱸ���ͺţ�");
			addForm.sparePartId.focus();
			return false;
		}
		if(addForm.storagePosition.value==""){
			alert("����λ�ò���Ϊ�գ�");
			addForm.storagePosition.focus();
			return false;
		}
		if(addForm.serialNumber.value==""){
			alert("�������кŲ���Ϊ�գ�");
			addForm.serialNumber.focus();
			return false;
		}		
		
		return true;
	}
    valiDigit=function(obj,msg){
        var mysplit = /^\d{1,6}$/;
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
            alert("����д��"+msg+"��������,����������");
            obj.value="0";
            obj.focus();
            return false;
        }
    }
	addGoBack=function(){
		var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorage";
		self.location.replace(url);
	}
	
		function onFacChange() {
    		var params = Form.Element.serialize("productFactory");//���Ҫ���ҵı�������
  			var ops = document.getElementById('sparePartName');//��ȡ�������Ƶ�sel
  			ops.options.length = 0;//��������б�
  			ops.options.add(new Option("===ѡ����������===", ""));//������ʾ��Ϣ
  			var url = "SeparepartBaseInfoAction.do?method=getNameByFac"
  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForName, asynchronous:true});
    	}
    	
    	function callbackForName(originalRequest) {
    		var rst = originalRequest.responseText;
    		var queryRes = eval('('+rst+')');//�������
    		var ops = document.getElementById('sparePartName');//��ȡ�������Ƶ�sel
    		for(var i = 0 ; i < queryRes.length; i++) {
  				ops.options.add(new Option(queryRes[i].spare_part_name, queryRes[i].spare_part_name));
  			}
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
    
    
  		function onNameChange() {
  			var params = Form.Element.serialize("sparePartName") + "&" + Form.Element.serialize("productFactory");//���Ҫ���ҵı�������
  			var ops = document.getElementById('sparePartModel');//��ȡ�����ͺŵ�sel
  			ops.options.length = 0;//��������б�
  			ops.options.add(new Option("===ѡ�������ͺ�===", ""));//������ʾ��Ϣ
  			var url = "SeparepartBaseInfoAction.do?method=getModelByNameForStockSave"
  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForModel, asynchronous:true});
  		}
  		
  		function callbackForModel(originalRequest) {
  			var rst = originalRequest.responseText;
  			var queryRes = eval('('+rst+')');//�������
  			var ops = document.getElementById('sparePartModel');//��ȡ�����ͺŵ�sel
  			for(var i = 0 ; i < queryRes.length; i++) {
  				ops.options.add(new Option(queryRes[i].spare_part_model, queryRes[i].tid));
  			}
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
		
		function onModelChange() {
			var modelSel = document.getElementById('sparePartModel').value;
			document.getElementById('tid').value=modelSel;
		}
		
		function savePositionForExcel(){
			var selvalue = document.getElementById('storagePosition').value;
			document.getElementById('storagePositionID').value=selvalue;
		}
		
		downloadFile=function() {
			window.location.href = "${ctx}/SparepartStorageAction.do?method=downloadTemplet";
		}
		
		function showupload() {
			if(addForm.sparePartId.value==""){
				alert("��ѡ����ⱸ���ͺţ�");
				addForm.sparePartId.focus();
				return false;
			}
			if(addForm.storagePosition.value==""){
				alert("��ѡ�񱣴�λ�ã�");
				addForm.storagePosition.focus();
				return false;
			}
		    var objpart = document.getElementById("paretpart");
		    var objup = document.getElementById("up");
		    objpart.style.display="none";
		    objup.style.display="block";
		}
		
		function noupload() {
			var objpart = document.getElementById("paretpart");
		    var objup = document.getElementById("up");
		    objpart.style.display="block";
		    objup.style.display="none";
		}
		
		function check() {
			var fileEle = document.getElementById('file');
			if( fileEle.value.length==0 ) {
				alert("��ѡ����Ҫ����ı�������");
				return false;
			}
			var filePath = fileEle.value;
			var startIndex = filePath.lastIndexOf(".");
			var endIndex = filePath.length;
			var fileType = filePath.substr(startIndex, endIndex);
			if(fileType != '.xls') {
				alert("��ѡ����ļ���ʽ����ȷ");
				return false;
			}
		}
		
		function query(){
			//addForm.submit.disabled=true;   
			var params = Form.Element.serialize("serialNumber");//�������к�
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
		//	addForm.submit.disabled=false;	
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
	</script>
	<body>
		<br>
		<div id="paretpart">
			<template:titile value="�ƶ���˾���" />
			<html:form action="/SparepartStorageAction.do?method=saveToStorage"
				styleId="addForm">
				<template:formTable namewidth="150" contentwidth="350">
					<template:formTr name="��������">
	         			<select name="productFactory" class="inputtext" style="width:250px" onchange="onFacChange()">
	         				<option value="">===��ѡ�񳧼�===</option>
	         				<logic:present scope="request" name="facList">
	         					<logic:iterate id="facListId" name="facList">
			                    	<option value="<bean:write name="facListId" />"><bean:write name="facListId"/></option>
			                	</logic:iterate>
	         				</logic:present>
	         			</select>
	         			<img id="Loadingimg" src="./images/ajaxtags/indicator.gif" style="display:none">
	         		</template:formTr>	         		
	         		<template:formTr name="��ⱸ��">
	         			<select name="sparePartName" class="inputtext" style="width:250px" onchange="onNameChange()" id="sparePartName">
	         				<option value="">===��ѡ�񱸼�����===</option>
	         			</select>
	         		</template:formTr>
					
					<template:formTr name="�����ͺ�" >
	         			<select name="sparePartId" class="inputtext" style="width:250px" id="sparePartModel" onchange="onModelChange()">
	         				<option value="">===��ѡ�񱸼��ͺ�===</option>
	         			</select>
	         		</template:formTr>
					<template:formTr name="����λ��">
						<select name="storagePosition" class="inputtext" style="width:250px" id="storagePosition" onchange="savePositionForExcel();">
	         				<option value="">===��ѡ�񱣴�λ��===</option>
	         				<logic:present scope="request" name="savePosition">
	         					<logic:iterate id="item" name="savePosition">
			                    	<option value="<bean:write name="item" property="id"/>"><bean:write name="item" property="name"/></option>
			                	</logic:iterate>
	         				</logic:present>
	         			</select>
					</template:formTr>
					<template:formTr name="�������к�">
						<html:text property="serialNumber" styleClass="inputtext"
							style="width:250;" maxlength="25"/>&nbsp;&nbsp;<br><span id="serialNumberSpan"></span>
					</template:formTr>
					
					<template:formTr name="�������ڲ���">
						<input name="departId" value="<%=(String)request.getAttribute("user_dept_id") %>" type="hidden" />
						<%=(String)request.getAttribute("user_dept_name") %>
					</template:formTr>
						<html:hidden property="storageNumber" styleClass="inputtext" value="1" name="stNum"/>
					<template:formTr name="������">
						<%=(String) request.getAttribute("user_name")%>
						<input name="storagePerson" type="hidden"
							value="<%=(String) request.getAttribute("user_id")%>"
							class="inputtext" style="width:250;"/>
					</template:formTr>
					<template:formTr name="��ⱸע">
						<html:textarea property="storageRemark" styleClass="inputtextarea"
							style="width:250;" rows="5" />
					</template:formTr>
	
					<template:formSubmit>
						<td>
							<input type="button" class="button" value="���" onclick="query()">
						</td>
						<td>
							<html:reset styleClass="button">����</html:reset>
						</td>
						<td>
							<input type="button" class="button" value="���뱸����" onclick="showupload()">
						</td>
						<td>
							<input type="button" class="button2" value="����ģ������" onclick="downloadFile()">
						</td>
					</template:formSubmit>
				</template:formTable>
			</html:form>
		</div>
		
		<div id="up" style="display: none">
			<html:form method="post" action="/SparepartStorageAction.do?method=upLoadShow" enctype="multipart/form-data" id="upform" onsubmit="return check()">
		      <table align="center" border="0" width="600" height="100%">
		        <tr>
		          <td valign="top" height="100%">
		            <table align="center" border="0">
		              <tr>
		                <td align="left" height="50">
		                  <br/>
		                  <br/>
		                  <br/>
		                  <br/>
		                  <br/>
		                  <br/>
		                  <br/>
		                  <b>��ѡ��Ҫ�����Excel�ļ�:</b>
		                  <br/>
		                </td>
		              </tr>
		              <tr>
		                <td>
		                  <input type="file" name="file" value="" style="width:300px" class="inputtext" id="file">
		                  <input type="hidden" name="sparePartId" id="tid">
		                  <input type="hidden" name="storagePosition" id="storagePositionID">
		                  <input name="storagePerson" type="hidden"
							value="<%=(String) request.getAttribute("user_id")%>"
							class="inputtext" style="width:250;"/>
							<input name="departId" value="<%=(String)request.getAttribute("user_dept_id") %>" type="hidden" />
								<%=(String)request.getAttribute("user_dept_name") %>
		                </td>
		              </tr>
		              <tr>
		                <td align="center" valign="middle" height="60">
		                  <input type="submit" value="���뱸����ⵥ" class="button2">
		                  <input type="button" value="ȡ������" onclick="noupload()" class="button">
		                </td>
		              </tr>
		            </table>
		          </td>
		        </tr>
		      </table>
		    </html:form>
		</div>
	</body>
</html>