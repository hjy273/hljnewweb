<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript" src="./js/prototype.js"></script>
	<script type="text/javascript">
	checkValid=function(addForm){
		var useMode = addForm.useMode.value;		
		var ids = document.getElementsByName('serialId');    //������ı������к�
		var applyUsePosition = document.getElementById("applyUsePosition");
			var idNum=0;   
			for(var i=0;i<ids.length;i++){  
			   if(ids[i].checked==true){            
			        idNum++;                   
			    }   
			} 
			if(idNum==0){
				alert("��ѡ�񱸼����кţ�");
				return false;
			}		
		if(useMode=="01"){
			if(applyUsePosition.value==""){
				alert("����ʹ��λ�ò���Ϊ�գ�");
				//applyUsePosition.focus();
				return false;
			}
		}
		if(useMode=="02"){
			var ss = document.getElementsByName('usedserialId');    //�������ı������к�
			var ii=0;  
			for(var i=0;i<ss.length;i++){  
			   if(ss[i].checked==true){            
			        ii++;                   
			    }   
			} 
			if(ii==0){
				alert("�������������кŲ���Ϊ�գ�");
				return false;
			}
			if(ii !=idNum){
				alert("�����������ı�����������ȣ�");
				return false;
			}			
		}
		return true;
	}
	
	 displayUpdateSpan=function(value){
    	if(value=="01"){
    		document.getElementById("updateSpan1").style.display="none";
    		document.getElementById("updateSpan2").style.display="none";
    		document.getElementById("updateSpan3").style.display="none";
    		document.getElementById("applyposition").style.display="";
    		document.getElementById("patrolRe").style.display="";
    		document.getElementById("patrolChange").style.display="none";
    	}
    	if(value=="02"){
    		//var takenOutStorage = document.getElementById("takenOutStorage").value;
    		//var objs = document.getElementsByName('usedserialId');   
    		//if(takenOutStorage=="" || objs.length==0){
    		//	alert("û����Ҫ�����ı�����");
    			//document.getElementById("useMode").value="01";
    		//}else{
    			document.getElementById("updateSpan1").style.display="";
	    		document.getElementById("updateSpan2").style.display="";
	    		document.getElementById("updateSpan3").style.display="";
	    	    document.getElementById("applyposition").style.display="none";
	    	    document.getElementById("patrolRe").style.display="none";
	    	    document.getElementById("patrolChange").style.display="";
    		//}
    	}
    }
    
    function OnPatrolgroupChange(){
    	var useMode = addForm.useMode.value;	
    	if(useMode=="01"){
    		return ;
    	}
    	var baseid=document.getElementById("sparePartId").value;
  		var params = Form.Element.serialize("patrolgroupCId")+"&baseid="+baseid;//���Ҫ���ҵı�������
  		 document.getElementById('seriNum').innerHTML="";  	
  		var ops = document.getElementById('takenOutStorage');
  			ops.options.length = 0;//��������б�
  			ops.options.add(new Option("===����������ʹ��λ��===", ""));//������ʾ��Ϣ		
  		var url = "SparepartApplyAction.do?method=getPositionByPatrolgroup"
  		var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForPatrolgroup, asynchronous:true});
    }
    
    function callbackForPatrolgroup(originalRequest) {
    		var rst = originalRequest.responseText;
    		var queryRes = eval('('+rst+')');//�������
    		var ops = document.getElementById('takenOutStorage');//��ȡ�������Ƶ�sel
    		for(var i = 0 ; i < queryRes.length; i++) {
  				ops.options.add(new Option(queryRes[i], queryRes[i]));
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
    
	
	function onPositonChange() {
			var baseid=document.getElementById("sparePartId").value;
  			var params = Form.Element.serialize("takenOutStorage")+"&baseid="+baseid+"&state=03";//���Ҫ���ҵı�������
  			span = document.getElementById('seriNum').innerHTML="";  			
  			var url = "SparepartStorageAction.do?method=getSerialNmuByPositon"
  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForModel, asynchronous:true});
  		}
  		
  		function callbackForModel(originalRequest) {
  			var rst = originalRequest.responseText;
  			var queryRes = eval('('+rst+')');//�������
  			var span = document.getElementById('seriNum');
  			if(queryRes.length==0){
  				//alert("��������Ϊ0��");
  				span.innerText="�ޱ���";
  			}
  			for(var i = 0 ; i < queryRes.length; i++) {
  				var newInput = document.createElement("INPUT");
	            newInput.type="checkbox";
	            newInput.id = "usedserialId";
	            newInput.name = "usedserialId";
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
	
	function addGoBackMod (){history.go(-1);}
	
	</script>
	<body>
		<template:titile value="Ѳ��ά�������뱸��" />
		<html:form action="/SparepartApplyAction.do?method=addApply"
			styleId="addForm" onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
			<input type="hidden" id="sparePartId" name="sparePartId" value="<%=request.getAttribute("sparePartId") %>"/>
				<template:formTr name="��������">
					<%=(String) request.getAttribute("sparepartName")%>
				</template:formTr>				
				<template:formTr name="���벿��">
					<%=(String) request.getAttribute("dept_name")%>
				</template:formTr>
				<template:formTr name="ʹ�÷�ʽ">
					<html:select property="useMode" styleId="useMode" styleClass="inputtext" style="width:250;" onchange="displayUpdateSpan(this.value);">
						<html:option value="01">ֱ��ʹ��</html:option>
						<html:option value="02">����ʹ��</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="�������к�">
					<logic:iterate id="item" name="serialNums">
			             <input type="checkbox" name="serialId" id="serialId" value="<bean:write name='item' property='serial_number'/>" />
			             &nbsp;<bean:write name="item" property="serial_number"/><br>
					 </logic:iterate>
				</template:formTr>
				<template:formTr name="��������" tagID="updateSpan1" style="display:none;">
					<html:select property="replaceType" style="width:250;" styleClass="inputtext">
						<html:option value="01">�˻��ɱ���</html:option>
						<html:option value="02">���޾ɱ���</html:option>
						<html:option value="03">���Ͼɱ���</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="Ѳ��ά����" tagID="patrolRe">
					<select id="patrolgroupRId" name="patrolgroupRId" class="inputtext" style="width:250;">
						<logic:iterate id="s" name="patrolgroups">
							<option value="<bean:write name="s" property="patrolid" />"><bean:write name="s" property="patrolname" /></option>
						</logic:iterate>
					</select>
				</template:formTr>
				<template:formTr name="Ѳ��ά����" tagID="patrolChange" style="display:none">
					<select id="patrolgroupCId" name="patrolgroupCId" class="inputtext" style="width:250;" onchange="OnPatrolgroupChange();">
						<option value="">===��ѡ��===</option>
						<logic:iterate id="s" name="patrolgroupsC">
							<option value="<bean:write name="s" property="patrolid" />"><bean:write name="s" property="patrolname" /></option>
						</logic:iterate>
					</select>
				</template:formTr>
				<template:formTr name="����������ʹ��λ��" tagID="updateSpan2" style="display:none;">
					<html:select property="takenOutStorage" styleId="takenOutStorage" styleClass="inputtext" style="width:250;" onchange="onPositonChange();">
						<option value="">===��ѡ��===</option>
					</html:select>
				</template:formTr>
				<template:formTr name="�������������к�" tagID="updateSpan3" style="display:none;">
					<span id="seriNum">
						
					 </span>
				</template:formTr>			
				<template:formTr name="����ʹ��λ��" tagID="applyposition">
					<html:text property="applyUsePosition" styleId="applyUsePosition" styleClass="inputtext"
						style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="������">
					<%=(String) request.getAttribute("user_name")%>
					<input name="applyPerson" type="hidden"
						value="<%=(String) request.getAttribute("user_id")%>"/>
				</template:formTr>
				<template:formTr name="���뱸ע">
					<html:textarea property="applyRemark" styleClass="inputtextarea"
						style="width:250;" rows="5" />
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:submit styleClass="button">�ύ</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">����	</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBackMod()">����</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		
	</body>
</html>
