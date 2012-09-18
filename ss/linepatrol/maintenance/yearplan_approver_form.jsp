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
			  title:"添加年计划任务" 
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
			  title:"修改年计划任务" 
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
				var yeartask =document.getElementById("yeartask").innerText;
				if(yeartask==""){
					alert("计划任务不能为空!");
					return false;
				}
				var approvers = document.getElementById("approvers").value;
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


            function checkAdd(){
			 	 var act = document.getElementById("act").value;
			 	 if(act=="transfer"){
				 	 var approvers = document.getElementById("approver").value;
				     if(approvers==null || approvers==""){
						alert("转审人不能为空!");
			  			return false;
	  			     }
			 	 }
  					return true;
			}
			
			
			function viewChangeCables(taskid){
				  var url = "${ctx}/testYearPlanAction.do?method=viewChangeCables&taskid="+taskid;
				  win = new Ext.Window({
				  layout : 'fit',
				  width: document.body.clientWidth * 0.55, 
	              height: 400, 
				  resizable:true,
				  closeAction : 'close', 
				  modal:true,
				  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+url+'></iframe>',
				  plain: true,
				  title:"查看中继段更新信息" 
				 });
				  win.show(Ext.getBody());
			}
	    </script>
	</head>

	<body>
		
		<template:titile value="审核年计划" />
			<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				    <tr class=trcolor style="height:30px">
				      <td class="tdulleft">代维单位：</td>
				      <td class="tdulright"><c:out value="${contraName}"></c:out></td>
				      <td class="tdulleft">计划制定人：</td>
				      <td class="tdulright"><c:out value="${userName}"></c:out></td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">计划名称：</td>
				      <td class="tdulright" colspan="3">
				          ${plan.planName}
				      </td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">计划年份：</td>
				      <td  class="tdulright" colspan="3">
				   		${plan.year}
				    </td>
				    </tr>
				     <tr class=trcolor>
				      <td class="tdulleft">每年默认测试次数：</td>
				      <td  class="tdulright" colspan="3">
				   		${plan.testTimes}
				    </td>
				    </tr>
				     <tr class=trwhite>
					     <td class="tdulleft">年计划任务： </td>
			             <td class="tdulright" colspan="3">
				            <div id="yeartask">
				           	   <table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
									    <td width="20%" >光缆级别</td>
										<td width="20%">变更前测试次数</td>
										<td width="20%">申请测试次数</td>
										<td width="20%">变更中继段数量</td>
										<td width="20%">变更中继段明细</td>			
									</tr>
									<c:forEach var="task" items="${planTasks}">
										  <tr>
									    	<td>${task.value.cableLable} </td>
									    	<td>${task.value.preTestNum}</td>
									    	<td>${task.value.applyNum}</td>
									    	<td>${task.value.trunkNum}</td>
									    	<td><a href="javascript:viewChangeCables('${task.value.id}')">查看</a></td>
									    </tr>
									</c:forEach>
								</table>
			                </div>
			             </td>
				    </tr>
				       <logic:notEmpty name="approveInfos"> 
					 		<tr class=trwhite align="center">
							 	<td colspan="4" align="left">&nbsp;&nbsp;年计划审核详细信息</td>
						    </tr>
							<tr  class=trcolor>
							 	<td width="15%">&nbsp;&nbsp;审核人</td><td width="20%">审核时间 </td>
							 	<td width="10%">审核结果 </td><td width="55%">审核意见 </td>
							 </tr>
							 <c:forEach items="${approveInfos}" var="approve"  varStatus="loop">
							 	<c:if test="${loop.count%2==0 }"> <tr class=trwhite></c:if>
							 	<c:if test="${loop.count%2==1 }"> <tr class=trcolor></c:if>
							 		<td>&nbsp;&nbsp;<bean:write name="approve" property="username"/></td>
							 		<td><bean:write name="approve" property="approve_time"/></td>
							 		<td><bean:write name="approve" property="approve_result_dis"/></td>
							 		<td>
							 			<bean:write name="approve" property="approve_remark"/>
							 		</td>
							 	</tr>
							 </c:forEach>
						 </logic:notEmpty>
				  </table>
		<html:form action="/testYearPlanAction.do?method=approveYearPlan" styleId="saveTestPlan" onsubmit="return checkAdd();">
			<input name="act" id="act" value="${act}" type="hidden"/>
				<table  border="0" align="center" cellpadding="0" cellspacing="0" style="border:#FFF">
					 <input type="hidden" name="planid" id="planid" value="${plan.id}"/>
				 <logic:equal value="approve" name="act">
				    <tr>
						<td height="25" style="text-align: right;">
							审核结果：
						</td>
						<td style="text-align: left;">
							<input type="radio" name="approveResult" value="1" checked />
							通过
							<input type="radio" name="approveResult" value="0" />
							不通过
						</td>
					</tr>
					<tr>
						<td height="25" style="text-align: right;">
							审核意见：
						</td>
						<td  style="text-align: left;">
							<textarea name="approveRemark" rows="6" class="max-length-256" style="width: 400px;"></textarea>
						</td>
					</tr>
				</logic:equal>
					<logic:equal value="transfer" name="act">
				<apptag:approverselect label="转审人" inputName="approver,mobiles" spanId="approverSpan" inputType="radio" />
					<tr>
						<td class="tdulleft" height="25" style="text-align: right;">
							转审说明：
						</td>
						<td class="tdulright"  style="text-align: left;">
							<textarea name="approveRemark" rows="6" class="max-length-256" style="width: 400px;"></textarea>
						</td>
					</tr>
				</logic:equal>
					 <tr>
					      <td height="25" style="text-align: center;" colspan="2">				       
					         <html:submit styleClass="button" value="审核"></html:submit>
					         <input type="button" class="button" value="返回" onclick="javascript:history.back();"/>
					     </td>
					 </tr>
				</table>
		</html:form>
	</body>
</html>
