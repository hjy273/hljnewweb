<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>����ά��ѯ���Ͽ��</title>
    <script type="text/javascript" src="./js/prototype.js"></script>
    <script type="text/javascript" src="./js/json2.js"></script>
	<script type="text/javascript">
		function onConChange() {
	    		var params = Form.Element.serialize("contractorid");
	  			var ops = document.getElementById('addrID');
	  			ops.options.length = 0;//��������б�
	  			ops.options.add(new Option("��ѡ��...", "-1"));//������ʾ��Ϣ
	  			var url = "materialStockAction.do?method=getAddressByCon"
	  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForCon, asynchronous:true});
	    	}
	    	
	    	function callbackForCon(originalRequest) {
	    		var rst = originalRequest.responseText;
	    		var queryRes = eval('('+rst+')');
	    		var ops = document.getElementById('addrID');
	    		for(var i = 0 ; i<queryRes.length; i++) {
	  				ops.options.add(new Option(queryRes[i].address, queryRes[i].id));
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
			
		</script>
  </head>
  
  <body><br>
  	<template:titile value="����ά��ѯ���Ͽ��"/>
  	<html:form action="/materialStockAction.do?method=getMarterialStocksByCon" >
  	<template:formTable namewidth="150" contentwidth="300">
    <logic:equal value="1" property="deptype" name="LOGIN_USER">
  		<template:formTr name="������ά">
  				<select name="contractorid" class="inputtext" style="width:215px" id="contractorid" onchange="onConChange();">
  						<option value="-1">����</option>
         				<logic:present scope="request" name="cons">
         					<logic:iterate id="r" name="cons">
		                    	<option value="<bean:write name="r" property="contractorid" />"><bean:write name="r" property="contractorname"/></option>
		                	</logic:iterate>
         				</logic:present>
         		</select><img id="Loadingimg" src="./images/ajaxtags/indicator.gif" style="display:none">
  		</template:formTr>
  		<template:formTr name="��ŵص�">
  				<select name="addrID" class="inputtext" style="width:215px" id="addrID">
  						<option value="-1">����</option>         				
         		</select>
  		</template:formTr>
  		</logic:equal>
  		<logic:equal value="2" property="deptype" name="LOGIN_USER">
  			<template:formTr name="��ŵص�">
  				<select name="addrID" class="inputtext" style="width:215px" id="addrID">
  						<option value="-1">����</option>
         				<logic:present scope="request" name="address">
         					<logic:iterate id="r" name="address">
		                    	<option value="<bean:write name="r" property="id" />"><bean:write name="r" property="address"/></option>
		                	</logic:iterate>
         				</logic:present>
         		</select>
         	</template:formTr>
  		</logic:equal>
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
