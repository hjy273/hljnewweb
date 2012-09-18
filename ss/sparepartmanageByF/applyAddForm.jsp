<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript" src="./js/prototype.js"></script>
	<script type="text/javascript">
	checkValid=function(addForm){
		var useMode = addForm.useMode.value;		
		var ids = document.getElementsByName('serialId');    //新申请的备件序列号
		var applyUsePosition = document.getElementById("applyUsePosition");
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
		if(useMode=="01"){
			if(applyUsePosition.value==""){
				alert("申请使用位置不能为空！");
				//applyUsePosition.focus();
				return false;
			}
		}
		if(useMode=="02"){
			var ss = document.getElementsByName('usedserialId');    //被更换的备件序列号
			var ii=0;  
			for(var i=0;i<ss.length;i++){  
			   if(ss[i].checked==true){            
			        ii++;                   
			    }   
			} 
			if(ii==0){
				alert("被更换备件序列号不能为空！");
				return false;
			}
			if(ii !=idNum){
				alert("申请的与更换的备件数量不相等！");
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
    		//	alert("没有需要更换的备件！");
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
  		var params = Form.Element.serialize("patrolgroupCId")+"&baseid="+baseid;//获得要查找的备件名称
  		 document.getElementById('seriNum').innerHTML="";  	
  		var ops = document.getElementById('takenOutStorage');
  			ops.options.length = 0;//清空下拉列表
  			ops.options.add(new Option("===被更换备件使用位置===", ""));//增加提示信息		
  		var url = "SparepartApplyAction.do?method=getPositionByPatrolgroup"
  		var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForPatrolgroup, asynchronous:true});
    }
    
    function callbackForPatrolgroup(originalRequest) {
    		var rst = originalRequest.responseText;
    		var queryRes = eval('('+rst+')');//解析结果
    		var ops = document.getElementById('takenOutStorage');//获取备件名称的sel
    		for(var i = 0 ; i < queryRes.length; i++) {
  				ops.options.add(new Option(queryRes[i], queryRes[i]));
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
    
	
	function onPositonChange() {
			var baseid=document.getElementById("sparePartId").value;
  			var params = Form.Element.serialize("takenOutStorage")+"&baseid="+baseid+"&state=03";//获得要查找的备件名称
  			span = document.getElementById('seriNum').innerHTML="";  			
  			var url = "SparepartStorageAction.do?method=getSerialNmuByPositon"
  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForModel, asynchronous:true});
  		}
  		
  		function callbackForModel(originalRequest) {
  			var rst = originalRequest.responseText;
  			var queryRes = eval('('+rst+')');//解析结果
  			var span = document.getElementById('seriNum');
  			if(queryRes.length==0){
  				//alert("备件数量为0！");
  				span.innerText="无备件";
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
				alert("网络连接出现问题，请关闭后重试!");
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
		<template:titile value="巡检维护组申请备件" />
		<html:form action="/SparepartApplyAction.do?method=addApply"
			styleId="addForm" onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
			<input type="hidden" id="sparePartId" name="sparePartId" value="<%=request.getAttribute("sparePartId") %>"/>
				<template:formTr name="备件名称">
					<%=(String) request.getAttribute("sparepartName")%>
				</template:formTr>				
				<template:formTr name="申请部门">
					<%=(String) request.getAttribute("dept_name")%>
				</template:formTr>
				<template:formTr name="使用方式">
					<html:select property="useMode" styleId="useMode" styleClass="inputtext" style="width:250;" onchange="displayUpdateSpan(this.value);">
						<html:option value="01">直接使用</html:option>
						<html:option value="02">更换使用</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="备件序列号">
					<logic:iterate id="item" name="serialNums">
			             <input type="checkbox" name="serialId" id="serialId" value="<bean:write name='item' property='serial_number'/>" />
			             &nbsp;<bean:write name="item" property="serial_number"/><br>
					 </logic:iterate>
				</template:formTr>
				<template:formTr name="更换类型" tagID="updateSpan1" style="display:none;">
					<html:select property="replaceType" style="width:250;" styleClass="inputtext">
						<html:option value="01">退还旧备件</html:option>
						<html:option value="02">送修旧备件</html:option>
						<html:option value="03">报废旧备件</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="巡检维护组" tagID="patrolRe">
					<select id="patrolgroupRId" name="patrolgroupRId" class="inputtext" style="width:250;">
						<logic:iterate id="s" name="patrolgroups">
							<option value="<bean:write name="s" property="patrolid" />"><bean:write name="s" property="patrolname" /></option>
						</logic:iterate>
					</select>
				</template:formTr>
				<template:formTr name="巡检维护组" tagID="patrolChange" style="display:none">
					<select id="patrolgroupCId" name="patrolgroupCId" class="inputtext" style="width:250;" onchange="OnPatrolgroupChange();">
						<option value="">===请选择===</option>
						<logic:iterate id="s" name="patrolgroupsC">
							<option value="<bean:write name="s" property="patrolid" />"><bean:write name="s" property="patrolname" /></option>
						</logic:iterate>
					</select>
				</template:formTr>
				<template:formTr name="被更换备件使用位置" tagID="updateSpan2" style="display:none;">
					<html:select property="takenOutStorage" styleId="takenOutStorage" styleClass="inputtext" style="width:250;" onchange="onPositonChange();">
						<option value="">===请选择===</option>
					</html:select>
				</template:formTr>
				<template:formTr name="被更换备件序列号" tagID="updateSpan3" style="display:none;">
					<span id="seriNum">
						
					 </span>
				</template:formTr>			
				<template:formTr name="申请使用位置" tagID="applyposition">
					<html:text property="applyUsePosition" styleId="applyUsePosition" styleClass="inputtext"
						style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="申请人">
					<%=(String) request.getAttribute("user_name")%>
					<input name="applyPerson" type="hidden"
						value="<%=(String) request.getAttribute("user_id")%>"/>
				</template:formTr>
				<template:formTr name="申请备注">
					<html:textarea property="applyRemark" styleClass="inputtextarea"
						style="width:250;" rows="5" />
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:submit styleClass="button">提交</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">重置	</html:reset>
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
