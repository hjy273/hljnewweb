<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.sparepartmanage.beans.SparepartBaseInfoBean;" %>

<html>
  <head>
    <title>querySparepart</title>
    <script type="text/javascript" src="./js/prototype.js"></script>
    <script type="text/javascript" src="./js/json2.js"></script>
    <script type="text/javascript">
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
  			var url = "SeparepartBaseInfoAction.do?method=getModelByName"
  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForModel, asynchronous:true});
  		}
  		
  		function callbackForModel(originalRequest) {
  			var rst = originalRequest.responseText;
  			var queryRes = eval('('+rst+')');//�������
  			var ops = document.getElementById('sparePartModel');//��ȡ�����ͺŵ�sel
  			for(var i = 0 ; i < queryRes.length; i++) {
  				ops.options.add(new Option(queryRes[i].spare_part_model, queryRes[i].spare_part_model));
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
  			var params = Form.Element.serialize("sparePartModel") + "&" + Form.Element.serialize("productFactory") + "&" + Form.Element.serialize("sparePartName");//���Ҫ���ҵı����ͺ�
  			var ops = document.getElementById('softwareVersion');//��ȡ����汾��sel
  			ops.options.length = 0;//��������б�
  			ops.options.add(new Option("===ѡ�����а汾===", ""));//������ʾ��Ϣ
  			var url = "SeparepartBaseInfoAction.do?method=getVersionByModel"
  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForVersion, asynchronous:true});
  		}
  		
  		function callbackForVersion(originalRequest) {
  			var rst = originalRequest.responseText;
  			var queryRes = eval('('+rst+')');//�������
  			var ops = document.getElementById('softwareVersion');//��ȡ����汾��sel
  			for(var i = 0 ; i < queryRes.length; i++) {
  				ops.options.add(new Option(queryRes[i].software_version, queryRes[i].software_version));
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
    </script>
  </head>
  
  <body>
  		<br />
        <template:titile value="��������ѯ������Ϣ"/>
         <html:form action="/SeparepartBaseInfoAction?method=showQueryResult"   styleId="queryForm2" >
         	<template:formTable namewidth="200"  contentwidth="200">
         		<template:formTr name="��������">
         			<select name="productFactory" class="inputtext" style="width:180px" onchange="onFacChange()">
         				<option value="">===ѡ�����г���===</option>
         				<logic:present scope="request" name="facList">
         					<logic:iterate id="facListId" name="facList">
		                    	<option value="<bean:write name="facListId" />"><bean:write name="facListId"/></option>
		                	</logic:iterate>
         				</logic:present>
         			</select>
         			<img id="Loadingimg" src="./images/ajaxtags/indicator.gif" style="display:none">
         		</template:formTr>
         		
         		<template:formTr name="��������"  >
         			<select name="sparePartName" class="inputtext" style="width:180px" onchange="onNameChange()" id="sparePartName">
         				<option value="">===ѡ����������===</option>
         			</select>
         		</template:formTr>
         		
         		<template:formTr name="�����ͺ�" >
         			<select name="sparePartModel" class="inputtext" style="width:180px" id="sparePartModel" onchange="onModelChange()">
         				<option value="">===ѡ�������ͺ�===</option>
         			</select>
         		</template:formTr>
         		
         		<template:formTr name="����汾">
         			<select name="softwareVersion" class="inputtext" style="width:180px" id="softwareVersion">
         				<option value="">===ѡ�����а汾===</option>
         			</select>
         		</template:formTr>
         		
         		<template:formSubmit>
         				<td>
                          <html:submit property="action" styleClass="button" >��ѯ </html:submit>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >����	</html:reset>
				      	</td>
         		</template:formSubmit>
         		
         	</template:formTable>
         </html:form>
  </body>
</html>
