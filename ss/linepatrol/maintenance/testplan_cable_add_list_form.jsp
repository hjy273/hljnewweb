<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="com.cabletech.linepatrol.maintenance.module.TestPlanLine" %>

<html>
	<head>
		<title>��ӱ��˲���</title>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type='text/javascript'>
		  var winCable;
			function addCable(){
			  winCable = new Ext.Window({
			  layout : 'fit',
			  width: document.body.clientWidth * 0.60 , 
              height: 450 , 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="${ctx}/testPlanAction.do?method=addCableForm" />',
			  plain: true,
			  title:"����м̶�" 
			 });
			  winCable.show(Ext.getBody());
			}
			
			function updateCable(id){
			  var testBeginDate = document.getElementById("testBeginDate").value;
			  url="${ctx}/testPlanAction.do?method=updateCableForm&cableid="+id+"&testBeginDate="+testBeginDate;
			  winCable = new Ext.Window({
			  layout : 'fit',
			  width: document.body.clientWidth * 0.60 , 
              height: 450 , 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+url+'></iframe>',
			  plain: true,
			  title:"�޸��м̶�" 
			 });
			  winCable.show(Ext.getBody());
			}
			  
			 function close(){
			  	winCable.close();
			 }
			 
			  function freshPage(){
			  	window.location.href=window.location.href;
			  	
			  }
		
			 function checkAddInfo(){
			 	var linesize = document.getElementById("linesize").value;
			 	if(linesize=="" || linesize==0){
			 		alert("û��һ�������м̶β��ܱ���!");
			 		return false;
			 	}
			 	processBar(saveTestPlan);
			 	return true;
			 }
			 function tempSave(){
			 	document.forms[0].isTempSaved.value="1";
			 	if(checkAddInfo()){
			 		document.forms[0].submit();
			 		//alert(document.forms[0].isTempSaved.value);
			 	}
			 }
	    </script>
	</head>

	<body>
	<%
		TestPlanLine object=null;
		Object lienid="";		
		int i = 1;
	 %>
	<template:titile value="��ӱ��˲���        �ƻ���������${testPlan.testBeginDate}��${testPlan.testEndDate}" />
		<html:form action="/testPlanAction.do?method=saveTestPlan"
			styleId="saveTestPlan" onsubmit="return checkAddInfo();"><bean:size id="lineSize" name="planLines" />
			<input name="linesize" type="hidden" id="linesize" value="${lineSize}"/>
			<input name="isTempSaved" type="hidden" id="isTempSaved" value="0"/>
			<input name="testBeginDate" type="hidden" id="testBeginDate" value="${testPlan.testBeginDate}"/>
		    <table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		    	<tr class=trwhite>
		    		<td colspan="2" align="center">
		    			<display:table name="sessionScope.planLines"  id="currentRowObject" pagesize="18" >
		    				<display:column value="<%=i%>" title="���"></display:column>
							<display:column property="cablelineName" sortable="true" title="�м̶�"  maxLength="25" style="width:10%"/>
							<display:column property="contractorTime" sortable="true" title="��άʱ��"  style="width:10%"/>
							<display:column property="cablelineTestPort" sortable="true" title="�ƻ����Զ�" style="width:10%"/>
							<display:column property="cablePlanTestNum" sortable="true" title="��ƻ����Դ���" style="width:10%"/>
							<display:column property="cablePlanedTestNum" sortable="true" title="�Ѿ�����ƻ�����" style="width:10%"/>
							<display:column property="testPlanDate" format="{0,date,yyyy-MM-dd}" sortable="true" style="width:20%" title="�ƻ�����ʱ��" maxLength="25" />
							<display:column property="testMan" sortable="true" title="�ƻ�������" style="width:10%"/>
							 <display:column media="html" title="����" style="width:10%">
								<%i++;
								 object = (TestPlanLine ) pageContext.findAttribute("currentRowObject");
						            if(object != null) {
						            	lienid = object.getCablelineId();
									} 
								%>
								<a href="javascript:updateCable('<%=lienid%>')">�޸�</a>
					            	| <a href="javascript:toDelete('<%=lienid%>')">ɾ��</a>
					            
				            </display:column>
						</display:table>
		    		</td>
		    	</tr>
			    <tr class=trcolor>
			      <td align="center" colspan="2">
			        <input type="button" class="lbutton" value="��Ӳ����м̶�" onclick="addCable();"/>
			        <input type="button" class="button"  value="�ݴ�" onclick="tempSave();"/>
			        <input type="submit" class="button"  value="�ύ"/>
			        </label>
			      </td>
			    </tr>
			  </table>
		
		
		</html:form>
	<script language="javascript" type="text/javascript">
     function toDelete(idValue){    
	     if(confirm("ȷ��Ҫɾ����?")){
	     	var params = "&cableid="+idValue;
	  		var url = "${ctx}/testPlanAction.do?method=deleteCable"
	  		var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:true});
	     }
    }
    
    function callback(originalRequest) {
    		var rst = originalRequest.responseText;
  			var myGlobalHandlers = {onCreate:function () {
			}, onFailure:function () {
				alert("�������ӳ������⣬��رպ�����!");
			}, onComplete:function () {
			}};
			Ajax.Responders.register(myGlobalHandlers);
			freshPage();
    }
	</script>
	</body>
</html>
