<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="com.cabletech.linepatrol.maintenance.module.TestPlanStation" %>

<html>
	<head>
		<title>��ӽӵص������</title>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type='text/javascript'>
		  var win;
			function addStation(){
			  win = new Ext.Window({
			  layout : 'fit',
			  width: document.body.clientWidth * 0.60 , 
              height: 450 , 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="${ctx}/testPlanAction.do?method=addStationForm" />',
			  plain: true,
			  title:"��ӻ�վ" 
			 });
			  win.show(Ext.getBody());
			}
			  
			 function close(){
			  	win.close();
			 }
			 
			 function updateStation(id){
			  url="${ctx}/testPlanAction.do?method=updateStationForm&stationid="+id;
			  win = new Ext.Window({
			  layout : 'fit',
			  width: document.body.clientWidth * 0.60 , 
              height: 450 , 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+url+'></iframe>',
			  plain: true,
			  title:"�޸Ļ�վ" 
			 });
			  win.show(Ext.getBody());
			}
			 
			  function checkAddInfo(){
			 	var linesize = document.getElementById("stationsize").value;
			 	if(linesize=="" || linesize==0){
			 		alert("û��һ�����Ի�վ���ܱ���!");
			 		return false;
			 	}
			 	processBar(saveTestPlan);
			 	return true;
			 }
	    </script>
	</head>

	<body>
	<%
		TestPlanStation object=null;
		Object stationid="";	
		int i = 1;	
	 %>
	<template:titile value="��ӽӵص������        �ƻ���������${testPlan.testBeginDate}��${testPlan.testEndDate}" />
		<html:form action="/testPlanAction.do?method=saveTestPlan"
			styleId="saveTestPlan" onsubmit="return checkAddInfo();">
			<bean:size id="stationSize" name="planStations" />
			<input name="stationsize" type="hidden" id="stationsize" value="${stationSize}"/>
		    <table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		    	<tr class=trwhite>
		    		<td colspan="2" align="center">
		    			<display:table name="sessionScope.planStations"  id="currentRowObject" pagesize="18" >
		    			    <display:column value="<%=i%>" title="���"></display:column>
							<display:column property="stationName" sortable="true" title="��վ����"  maxLength="25" style="width:20%"/>
							<display:column property="testPlanDate" format="{0,date,yyyy-MM-dd}" sortable="true" style="width:20%" title="�ƻ�����ʱ��" maxLength="25" />
							<display:column property="testMan" sortable="true" title="�ƻ�������" style="width:25%"/>
							<display:column media="html" title="����" style="width:10%">
								<% i++;
									object = (TestPlanStation ) pageContext.findAttribute("currentRowObject");
						            if(object != null) {
						            	stationid = object.getTestStationId();
									} 
								%>
								<a href="javascript:updateStation('<%=stationid%>')">�޸�</a>
					            	| <a href="javascript:toDelete('<%=stationid%>')">ɾ��</a>
					            
				            </display:column>
						</display:table>
		    		</td>
		    	</tr>
			    <tr class=trcolor>
			      <td align="center" colspan="2">
			        <input type="button" class="lbutton" value="��Ӳ��Ի�վ" onclick="addStation();"/>
			       <input type="submit" class="button"  value="�ύ"/>
			        </label>
			      </td>
			    </tr>
			  </table>
		
		
		</html:form>
		
		<script language="javascript" type="text/javascript">
		function freshPage(){
			  	window.location.href=window.location.href;
			  	
	    }
	     function toDelete(idValue){    
	      	if(confirm("ȷ��Ҫɾ����?")){
	       		var params = "&stationid="+idValue;
	  			var url = "${ctx}/testPlanAction.do?method=deleteStation"
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
