<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>�����ϲ�ѯ���Ͽ��</title>
    <script type="text/javascript" src="./js/prototype.js"></script>
    <script type="text/javascript" src="./js/json2.js"></script>
	<script type="text/javascript">
		function onTypeChange() {
	    		var params = Form.Element.serialize("typeid");
	  			var ops = document.getElementById('modelid');
	  			ops.options.length = 0;//��������б�
	  			ops.options.add(new Option("��ѡ��...", ""));//������ʾ��Ϣ
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
					alert("�������ӳ������⣬��رպ�����!");
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
	  			ops.options.length = 0;//��������б�
	  			ops.options.add(new Option("��ѡ��...", ""));//������ʾ��Ϣ
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
					alert("�������ӳ������⣬��رպ�����!");
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
  	<template:titile value="�����ϲ�ѯ���Ͽ��"/>
  	<html:form action="/materialStockAction.do?method=getMarterialStocksByMT" >
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="��������">
  				<select name="typeid" class="inputtext" style="width:215px" id="typeid" onchange="onTypeChange();">
  						<option value="">����</option>
         				<logic:present scope="request" name="types">
         					<logic:iterate id="r" name="types">
		                    	<option value="<bean:write name="r" property="id" />"><bean:write name="r" property="typename"/></option>
		                	</logic:iterate>
         				</logic:present>
         		</select><img id="Loadingimg" src="./images/ajaxtags/indicator.gif" style="display:none">
  		</template:formTr>
  		<template:formTr name="���Ϲ��">
  				<select name="modelid" class="inputtext" style="width:215px" id="modelid" onchange="onModelChange();">
  						<option value="">����</option>         				
         		</select>
  		</template:formTr>
  		<template:formTr name="��������">
  				<select name="mtid" class="inputtext" style="width:215px" id="mtid">
  						<option value="">����</option>         				
         		</select><img id="Loadingimg2" src="./images/ajaxtags/indicator.gif" style="display:none">
  		</template:formTr>
  		 <template:formSubmit>
				      	<td>
                          <html:submit property="action" styleClass="button">��ѯ</html:submit>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >����</html:reset>
				      	</td>
			</template:formSubmit>
  	</template:formTable>
  	</html:form>
  </body>
</html>
