<%@include file="/common/header.jsp"%>
<html>
<script type="text/javascript" src="./js/prototype.js"></script>
	<script type="text/javascript">
	
	function onPositonChange() {
			var baseid=document.getElementById("baseid").value;
  			var params = Form.Element.serialize("initStorage")+"&baseid="+baseid+"&state=01";//���Ҫ���ҵı�������
  			span = document.getElementById('seriNum').innerHTML="";  			
  			var url = "SparepartStorageAction.do?method=getSerialNmuByPositon"
  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForModel, asynchronous:true});
  		}
  		
  		function callbackForModel(originalRequest) {
  			var rst = originalRequest.responseText;
  			var queryRes = eval('('+rst+')');//�������
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
				alert("�������ӳ������⣬��رպ�����!");
			}, onComplete:function () {
				if (Ajax.activeRequestCount == 0) {
					Element.hide("Loadingimg");
				}
			}};
			Ajax.Responders.register(myGlobalHandlers);
  		}
	checkValid=function(addForm){
		var ss = document.getElementsByName('serialId');   
		var ii=0;   
		for(var i=0;i<ss.length;i++){  
		   if(ss[i].checked==true){            
		        ii++;                   
		    }   
		} 
		if(ii==0){
			alert("��ѡ�񱸼����кţ�");
			return false;
		}
		return true;
	}
	addGoBack=function(){
		window.location.href = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageForDraw";
		//self.location.replace(url);
	}
	</script>
	<body>
		<br>
		<template:titile value="��ά��λ����" />
		<html:form action="/SparepartStorageAction.do?method=takeOutFromStorage"
			styleId="addForm" onsubmit="return checkValid(this);">
			<input type="hidden" id="baseid" value="<%=request.getAttribute("baseid") %>">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="��������">
					<bean:write name="SparepartBaseInfoBean" property="productFactory"/>
				</template:formTr>
				<template:formTr name="��������">
					<bean:write name="SparepartBaseInfoBean" property="sparePartName"/>
				</template:formTr>
				<template:formTr name="�����ͺ�">
					<bean:write name="SparepartBaseInfoBean" property="sparePartModel"/>
				</template:formTr>
				<template:formTr name="��ǰ����λ��">
					<html:select property="initStorage" style="width:250px;" styleClass="inputtext" onchange="onPositonChange();">
						<html:options collection="sparePartoptions" property="init_storage"  labelProperty="name" />
					</html:select>	
				</template:formTr>
				<template:formTr name="���к��б�">
					<span id="seriNum">
						<logic:iterate id="item" name="initSerialNumbers">
			               <input type="checkbox" name="serialId" id="serialId" value="<bean:write name='item'/>" />&nbsp;<bean:write name="item"/><br>
						</logic:iterate>
					</span>
					
				</template:formTr>
				<template:formTr name="���ڲ���">
					<%=(String) request.getAttribute("dept_name")%>
				</template:formTr>
				<template:formTr name="����λ��">
					<html:select property="storagePosition" style="width:250px;" styleClass="inputtext" >
						<html:options collection="saveOptions" property="id"  labelProperty="name" />
					</html:select>	
				</template:formTr>
				<template:formTr name="������">
					<%=(String) request.getAttribute("user_name")%>
				</template:formTr>
				<!--<template:formTr name="���ñ�ע">
					<html:textarea property="storageRemark" styleClass="inputtextarea"
						style="width:250;" rows="5" />
				</template:formTr>-->

				<template:formSubmit>
					<td>
						<html:submit styleClass="button">����</html:submit>
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
