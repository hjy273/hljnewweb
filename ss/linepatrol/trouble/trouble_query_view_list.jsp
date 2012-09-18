<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
		    <script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type="text/javascript">
			toViewReplyForm=function(idValue,isread,queryact){
	            window.location.href = "${ctx}/troubleQueryStatAction.do?method=viewTroubleReply&replyid="+idValue+"&isread="+isread+"&queryact="+queryact;
			}
		</script>
	</head>

	<body>
		<br>
		<template:titile value="故障信息" />
			<input type="hidden" id="troubleid" name="troubleid" value="<bean:write name="trouble" property="id"/>" />
			<table  width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			   <tr class=trwhite>
				    <td class="tdulleft">故障登记人：</td>
				    <td class="tdulright" >
						<c:out value="${sendman}"></c:out>
					</td>
					<td class="tdulleft">故障登记单位：</td>
				    <td class="tdulright">
				        <c:out value="${deptName}"></c:out>
				    </td>
				  </tr>
				 <tr class=trcolor>
				    <td class="tdulleft">指派代维：</td>
				    <td class="tdulright" >
						<c:forEach items="${unitlist}" var="unit">
							<bean:write name="unit" property="contractorname"/><br/>
						</c:forEach>
					</td>
					<td class="tdulleft">故障单号：</td>
				    <td class="tdulright">
				    	<c:out value="${trouble.troubleId}"></c:out>
				    </td>
				  </tr>
			
			 	  <tr class=trwhite>
				    <td class="tdulleft">故障发生时间：</td>
				    <td class="tdulright">
				    	<bean:write  name="trouble" property="troubleStartTime" format="yyyy/MM/dd HH:mm:ss"/>
					</td>
				    <td class="tdulleft">故障派发时间：</td>
				    <td class="tdulright">
				    	<bean:write  name="trouble" property="troubleSendTime" format="yyyy/MM/dd HH:mm:ss" />
					</td>
				  </tr>
				  
				  <tr class=trcolor>
				    <td class="tdulleft">是否为重大故障：</td>
				    <td class="tdulright">
				    	<c:if test="${trouble.isGreatTrouble=='0'}">
				    		否
				    	</c:if>
				    	<c:if test="${trouble.isGreatTrouble=='1'}">
				    		是
				    	</c:if>
				    </td>
				    <td class="tdulleft">故障反馈时限：</td>
				    <td class="tdulright">
				    	<bean:write name="trouble" property="replyDeadline" format="yyyy/MM/dd HH:mm:ss"/>
				    </td>
				  </tr>
				  <tr>
				    <td class="tdulleft">故障派发人：</td>
				    <td class="tdulright" colspan="3"><bean:write name="trouble" property="troubleSendMan"/></td>
				  </tr>
				  
				  <tr class=trwhite>
				    <td class="tdulleft">平台：</td>
				    <td class="tdulright">
				    ${trouble.connector}
				    </td>
				    <td class="tdulleft">平台电话：</td>
				    <td class="tdulright"><bean:write name="trouble" property="connectorTel"/></td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">故障原因：</td>
				    <td class="tdulright" colspan="3">&nbsp;<bean:write name="reasonName"/></td>
				  </tr>
				    <tr class=trwhite>
				         <td class="tdulleft">eoms单号数量：</td>
						  <td class="tdulright" colspan="3">
						  	${eomsnum}个
					     </td>
				     </tr>
				   <tr class=trcolor>
				         <td class="tdulleft">eoms单号：</td>
						  <td class="tdulright" colspan="3" style="word-break:break-all;width:60%;">
						  	${eoms}
					     </td>
				  </tr>
				  <tr class=trwhite>
				    <td class="tdulleft">故障描述：</td>
				    <td class="tdulright" colspan="3" style="word-break:break-all;width:500px;" ><bean:write name="trouble" property="troubleRemark"/></td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft">附件:</td>
				    <td class="tdulright" colspan="3" >
				    	 <apptag:upload state="look" cssClass="" entityId="${trouble.id}" entityType="LP_TROUBLE_INFO"/>
				    </td>
				  </tr>
			<logic:notEmpty property="cancelUserId" name="trouble">
				<tr class=trcolor>
					<td class="tdr">
						取消人：
					</td>
					<td class="tdl">
						<bean:write property="cancelUserName" name="trouble" />
					</td>
					<td class="tdr">
						取消时间：
					</td>
					<td class="tdl">
						<bean:write property="cancelTimeDis" name="trouble" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						取消原因：
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="cancelReason" name="trouble" />
					</td>
				</tr>
			</logic:notEmpty>
				</table>
				<c:if test="${not empty replyid }">
					<apptag:appraiseDailyDaily contractorId="${contractorId }" businessId="${replyid }" businessModule="trouble" 
							displayType="view" tableClass="tabout" tableStyle="width:80%; border-top: 0px; border-bottom: 0px;"></apptag:appraiseDailyDaily>
					<apptag:appraiseDailySpecial contractorId="${contractorId }" businessId="${replyid }" businessModule="trouble" 
							displayType="view" tableClass="tabout" tableStyle="width:80%; border-top: 0px; border-bottom: 0px;"></apptag:appraiseDailySpecial>
				</c:if>
				<table  width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				<c:if test="${not empty replys}">
				 <tr><td colspan="4">
				 <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				 	<tr>
				 		<td>代维公司</td><td>反馈时间</td><td>反馈人角色</td><td>状态</td><td>操作</td>
				 	</tr>
				 	<c:forEach items="${replys}" var="reply" varStatus="varloop">
				 		<c:if test="${varloop.index%2==0}">
				 		  <tr class=trcolor>
				 		</c:if>
				 		<c:if test="${varloop.index%2!=0}">
				 		  <tr class=trwhite>
				 		  </c:if>
							<td><bean:write name="reply" property="contractorname"/></td>
							<td>&nbsp;
				    			<bean:write name="reply" property="reply_submit_time" format="yyyy/MM/dd HH:mm:ss"/>
							</td>
							<td>
						    	<bean:write name="reply" property="confirm_result"/>
							</td>
							<td>&nbsp;<bean:write name="reply" property="approve_state"/></td>
							<td>
							<bean:define id="rid" property="id" name="reply"></bean:define>
								 <a href="javascript:toViewReplyForm('<%=rid%>','${isread}','${queryact}')">查看详细</a>
							</td>
				 		</tr>
				 	</c:forEach>
				 </table>
				 </td>
				 </tr>
				 </c:if>
				 <tr class=trcolor >
				    <td align="center" colspan="4">
				    	<input type="button" value="返回" class="button" onclick="javascript:history.back();" />
				    </td>
				 </tr>
	    	</table>
	</body>
</html>
