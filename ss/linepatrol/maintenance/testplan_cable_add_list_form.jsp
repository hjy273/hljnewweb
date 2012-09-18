<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="com.cabletech.linepatrol.maintenance.module.TestPlanLine" %>

<html>
	<head>
		<title>添加备纤测试</title>
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
			  title:"添加中继段" 
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
			  title:"修改中继段" 
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
			 		alert("没有一条测试中继段不能保存!");
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
	<template:titile value="添加备纤测试        计划测试日期${testPlan.testBeginDate}至${testPlan.testEndDate}" />
		<html:form action="/testPlanAction.do?method=saveTestPlan"
			styleId="saveTestPlan" onsubmit="return checkAddInfo();"><bean:size id="lineSize" name="planLines" />
			<input name="linesize" type="hidden" id="linesize" value="${lineSize}"/>
			<input name="isTempSaved" type="hidden" id="isTempSaved" value="0"/>
			<input name="testBeginDate" type="hidden" id="testBeginDate" value="${testPlan.testBeginDate}"/>
		    <table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		    	<tr class=trwhite>
		    		<td colspan="2" align="center">
		    			<display:table name="sessionScope.planLines"  id="currentRowObject" pagesize="18" >
		    				<display:column value="<%=i%>" title="序号"></display:column>
							<display:column property="cablelineName" sortable="true" title="中继段"  maxLength="25" style="width:10%"/>
							<display:column property="contractorTime" sortable="true" title="交维时间"  style="width:10%"/>
							<display:column property="cablelineTestPort" sortable="true" title="计划测试端" style="width:10%"/>
							<display:column property="cablePlanTestNum" sortable="true" title="年计划测试次数" style="width:10%"/>
							<display:column property="cablePlanedTestNum" sortable="true" title="已经做入计划次数" style="width:10%"/>
							<display:column property="testPlanDate" format="{0,date,yyyy-MM-dd}" sortable="true" style="width:20%" title="计划测试时间" maxLength="25" />
							<display:column property="testMan" sortable="true" title="计划测试人" style="width:10%"/>
							 <display:column media="html" title="操作" style="width:10%">
								<%i++;
								 object = (TestPlanLine ) pageContext.findAttribute("currentRowObject");
						            if(object != null) {
						            	lienid = object.getCablelineId();
									} 
								%>
								<a href="javascript:updateCable('<%=lienid%>')">修改</a>
					            	| <a href="javascript:toDelete('<%=lienid%>')">删除</a>
					            
				            </display:column>
						</display:table>
		    		</td>
		    	</tr>
			    <tr class=trcolor>
			      <td align="center" colspan="2">
			        <input type="button" class="lbutton" value="添加测试中继段" onclick="addCable();"/>
			        <input type="button" class="button"  value="暂存" onclick="tempSave();"/>
			        <input type="submit" class="button"  value="提交"/>
			        </label>
			      </td>
			    </tr>
			  </table>
		
		
		</html:form>
	<script language="javascript" type="text/javascript">
     function toDelete(idValue){    
	     if(confirm("确定要删除吗?")){
	     	var params = "&cableid="+idValue;
	  		var url = "${ctx}/testPlanAction.do?method=deleteCable"
	  		var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:true});
	     }
    }
    
    function callback(originalRequest) {
    		var rst = originalRequest.responseText;
  			var myGlobalHandlers = {onCreate:function () {
			}, onFailure:function () {
				alert("网络连接出现问题，请关闭后重试!");
			}, onComplete:function () {
			}};
			Ajax.Responders.register(myGlobalHandlers);
			freshPage();
    }
	</script>
	</body>
</html>
