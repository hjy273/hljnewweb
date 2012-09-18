<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type='text/javascript'>
		  var win;
			function addPlanTask(){
			  win = new Ext.Window({
			  layout : 'fit',
			  width:560,
			  height:410, 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
		    //  autoLoad:{url: '${ctx}/testYearPlanAction.do?method=addYearPlanTask',scripts:true}, 
			 html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="${ctx}/testYearPlanAction.do?method=addYearPlanTask" />',
			  plain: true,
			  title:"添加变更中继段" 
			 });
			  win.show(Ext.getBody());
			}
			
			function editPlanTask(id){
			 var u="${ctx}/testYearPlanAction.do?method=editYearTaskForm&cableLevel="+id;
			  win = new Ext.Window({
			  layout : 'fit',
			  width:560,
			  height:410, 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
		    //  autoLoad:{url: '${ctx}/testYearPlanAction.do?method=addYearPlanTask',scripts:true}, 
			 html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+u+' />',
			  plain: true,
			  title:"修改变更中继段" 
			 });
			  win.show(Ext.getBody());
			}
			
			function deleteTask(idValue){    
	      	   if(confirm("确定要删除吗?")){
	       			var params = "&cableLevel="+idValue;
	  				var url = "${ctx}/testYearPlanAction.do?method=deleteTask"
	  				var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:chipcallback,asynchronous:true}); 
		      	}
		     }
		    
		    function chipcallback(originalRequest) {
		    		var rst = originalRequest.responseText;
		  		    $('yeartask').update(rst);
		    }
			
			
			function close(){
				win.close();
			}
			
			function checkAddInfo() {
				var planName =document.getElementById('planName').value;
				if(planName==""){
					alert("计划名称不能为空!");
					return false;
				}
				var year =document.getElementById('year').value;
				if(year==""){
					alert("计划年份不能为空!");
					return false;
				}
				var times =document.getElementById('testTimes').value;
				if(times==""){
					alert("每年默认测试次数不能为空!");
					return false;
				}
				var yeartask =document.getElementById("yeartask").innerText;
				/*if(yeartask==""){
					alert("计划任务不能为空!");
					return false;
				}*/
				var approvers = document.getElementById("approver").value;
				if(approvers==""){
					alert("请选择年计划审核人!");
			  		return false;
		  		}
			//	return true;
		  }
		
		function saveTask(obj){
	           	obj.request({ 
					   onComplete: function(originalRequest){ 
						   var rst = originalRequest.responseText;
						   if(rst=="1"){
						   	 alert("光缆级别不能重复!");
						   }else{
						   	 $('yeartask').update(rst);
						   	 window.close();
						   }
						}
					});
			}

	    </script>
	</head>

	<body>
		
		<template:titile value="修改年计划" />
		<html:form action="/testYearPlanAction.do?method=saveYearPlan"
			styleId="saveTestPlan" onsubmit="return checkAddInfo();">
			<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				<input name="id" id="id" type="hidden" value="${plan.id}"/>
				<input name="act" id="act" type="hidden" value="edit"/>
				    <tr class=trwhite style="height:30px">
				      <td class="tdulleft">代维单位：</td>
				      <td class="tdulright"><c:out value="${LOGIN_USER.deptName}"></c:out></td>
				      <td class="tdulleft">计划制定人：</td>
				      <td class="tdulright"><c:out value="${LOGIN_USER.userName}"></c:out></td>
				    </tr>
				    <tr class=trcolor>
				      <td class="tdulleft">计划名称：</td>
				      <td class="tdulright" colspan="3">
				           <html:text property="planName" styleClass="required" value="${plan.planName}" style="width:225"></html:text>&nbsp;&nbsp;<font color="red">*</font>
				      </td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">计划年份：</td>
				      <td  class="tdulright" colspan="3">
				   		     <input name="year" id="year" class="Wdate" style="width:70" value="${plan.year}"
					onfocus="WdatePicker({dateFmt:'yyyy',minDate: '%y'})" readonly/>
					&nbsp;&nbsp;<font color="red">*</font>
				    </td>
				    </tr>
				    <tr class=trcolor>
				      	<td class="tdulleft">每年默认测试次数：</td>
				      	<td  class="tdulright" colspan="3"> 
				      		<html:text property="testTimes" styleClass="required" value="${plan.testTimes}" style="width:70px"></html:text>
				      		&nbsp;&nbsp;<font color="red">*</font>
				      	</td>
				      </tr>
				    <tr class=trwhite>
			             <td class="tdulleft">
			                                     变更中继段：
			             </td>
			             <td class="tdulright" colspan="3"><input type="button" value="添加变更中继段" onclick="addPlanTask();"/></td>
				    </tr>
				     <tr class=trcolor>
					     <td class="tdulleft">变更中继段： </td>
			             <td class="tdulright" colspan="3">
				            <div id="yeartask">
				           	   <table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
									    <td width="20%" >光缆级别</td>
										<td width="20%">变更前测试次数</td>
										<td width="20%">申请测试次数</td>
										<td width="20%">变更中继段数量</td>
										<td width="20%">操作</td>	
									</tr>
									<c:forEach var="task" items="${planTasks}">
										  <tr>
									    	<td>${task.value.cableLable} </td>
									    	<td>${task.value.preTestNum}</td>
									    	<td>${task.value.applyNum}</td>
									    	<td>${task.value.trunkNum}</td>
									    	<td><a href="javascript:editPlanTask('${task.value.cableLevel}');">修改</a>
									    	|<a href="javascript:deleteTask('${task.value.cableLevel}');">删除</a>
									    	</td>
									    </tr>
									</c:forEach>
								</table>
			                </div>
			             </td>
				    </tr>
				       <tr class=trwhite>
				    	<apptag:approverselect inputName="approver,mobile" label="审核人"
				    	 colSpan="4" inputType="radio" notAllowName="reads"
				    	 approverType="approver" objectType="LP_TEST_YEAR_PLAN" objectId="${plan.id}" 
				    	 ></apptag:approverselect>
				     </tr>
				      <tr class=trcolor>
				    	<apptag:approverselect inputName="reads,rmobiles" label="抄送人" 
				    	colSpan="4" spanId="reader" notAllowName="approver"
				    	approverType="reader" objectType="LP_TEST_YEAR_PLAN" objectId="${plan.id}" 
				    	></apptag:approverselect>
				     </tr>
				    <tr>
				      <td align="center" colspan="4">				       
				        <html:submit styleClass="button" value="提交"></html:submit>
				        	<input type="button" class="button" onclick="javascript:history.back();" value="返回"/>
				     </td>
				    </tr>
				  </table>
		</html:form>
	</body>
</html>
