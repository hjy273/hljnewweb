<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
			<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
			<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
			<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
			<script type="text/javascript">
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
				 function close(){
				  	win.close();
				 }
			</script>
	</head>

	<body>
		
		<template:titile value="查看年计划" />
		<html:form action="/testYearPlanAction.do?method=readPlan">
		<input type="hidden" name="planid" id="planid" value="${plan.id}"/>
			<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				    <tr class=trwhite style="height:30px">
				      <td class="tdulleft">代维单位：</td>
				      <td class="tdulright"><c:out value="${contraName}"></c:out></td>
				      <td class="tdulleft">计划制定人：</td>
				      <td class="tdulright"><c:out value="${userName}"></c:out></td>
				    </tr>
				    <tr class=trcolor>
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
				     <tr class=trwhite>
				    	<td class="tdulleft">审核人：</td>
				        <td class="tdulright" colspan="3">
				        	<c:forEach items="${approvers}" var="approver">
				        		<c:if test="${approver.approverType!='03' && approver.isTransferApproved==0}">
				        			${approver.approverName}
				        		</c:if>
				        	</c:forEach>
				        </td>
				     </tr>
				      <tr class=trcolor>
				    	<td class="tdulleft">抄送人：</td>
				        <td class="tdulright" colspan="3">
				           <c:forEach items="${approvers}" var="approver">
				        		<c:if test="${approver.approverType=='03'}">
				        			${approver.approverName}&nbsp;&nbsp;&nbsp;
				        		</c:if>
				        	</c:forEach>
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
				    <tr>
				      <td align="center" colspan="4">		
				            <c:if test="${LOGIN_USER.deptype=='1' && query=='no' && isread=='true'}">
						    	  <html:submit value="已阅读" styleClass="button"></html:submit>
						    </c:if>		       
				      	<input type="button" class="button" onclick="javascript:history.back();" value="返回"/>
				     </td>
				    </tr>
				  </table>
		</html:form>
	</body>
</html>
