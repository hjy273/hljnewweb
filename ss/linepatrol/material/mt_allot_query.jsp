<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>��ѯ���Ϲ��</title>
    <script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
    <script type="text/javascript" src="./js/prototype.js"></script>
    <script type="text/javascript" src="./js/json2.js"></script>
	<script type="text/javascript">
	
	function onConChange() {
    		var params = Form.Element.serialize("constactid");
  			var ops = document.getElementById('addrID');
  			ops.options.length = 0;//��������б�
  			ops.options.add(new Option("��ѡ��...", ""));//������ʾ��Ϣ
  			var url = "materialAllotAction.do?method=getAddressByCon"
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
			}};
			Ajax.Responders.register(myGlobalHandlers);
    	}
    </script>
  </head>
  
  <body><br>
  	<template:titile value="��ѯ���ϵ�������ϸ"/>
  	<html:form action="/materialAllotAction.do?method=getMaterialAllots" >
  	<template:formTable namewidth="150" contentwidth="300">
  		<template:formTr name="��������">
			<input name="changedate" class="inputtext"  style="width:215px"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly/>
  		</template:formTr> 
  	<logic:equal value="1" name="LOGIN_USER" property="deptype">
  		<template:formTr name="��ά����">
  		<select name="constactid"  id="constactid" class="inputtext" style="width:215px" onchange="onConChange();" >
  			<option value="">��ѡ��...</option>
         <logic:present scope="request" name="cons">
         	<logic:iterate id="r" name="cons">
		         <option value="<bean:write name="r" property="contractorid" />">
		         	<bean:write name="r" property="contractorname"/>
		         </option>
		    </logic:iterate>
         </logic:present>
      </select><img id="Loadingimg" src="./images/ajaxtags/indicator.gif" style="display:none">
  		</template:formTr> 
  		<template:formTr name="��ŵص�">
  		<select name="addrID"  id="addrID" class="inputtext" style="width:215px" >
  			 <option value="">��ѡ��...</option>
	      </select>
  		</template:formTr> 
  		</logic:equal>
  		<logic:equal value="2" name="LOGIN_USER" property="deptype">
  		<template:formTr name="��ŵص�">
	  	 <select name="addrID"  id="addrID" class="inputtext" style="width:215px"  >
	  			<option value="">��ѡ��...</option>
	         <logic:present scope="request" name="address">
	         	<logic:iterate id="r" name="address">
			         <option value="<bean:write name="r" property="id" />">
			         	<bean:write name="r" property="address"/>
			         </option>
			    </logic:iterate>
	         </logic:present>
	      </select>
	      </template:formTr>
  		</logic:equal>
  		<template:formTr name="����״̬">
	  	  <select name="allotState"  class="inputtext" style="width:215px" id="allotState">
			    <option value="0">����</option>
			    <option value="1">����</option>
			    <option value="2">����</option>
	      </select>
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
