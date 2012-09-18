<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>审核测试计划</title>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type='text/javascript'>
		  var win;
			function viewPalnDetail(){
			  var planid = document.getElementById("planid").value;
			  url="${ctx}/testPlanAction.do?method=viewPalnDetail&planId="+planid;
			  win = new Ext.Window({
			  layout : 'fit',
			  width: document.body.clientWidth * 0.9, 
              height: 450 , 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+url+' />',
			  plain: true
			 });
			  win.show(Ext.getBody());
			}
			 function close(){
			  	win.close();
			 }
			 
			 function checkAdd(){
			 	 var r = "审核意见";
			 	 var act = document.getElementById("act").value;
			 	 if(act=="transfer"){
			 	 	r="转审说明";
				 	 var approvers = document.getElementById("approver").value;
				     if(approvers==null || approvers==""){
						alert("转审人不能为空!");
			  			return false;
	  			     }
			 	 }
			 	 if(valCharLength(document.getElementById('approveRemark').value) > 1024) {
			  		   alert(r+"不能超过512个汉字或者1024个英文字符！")
			           return false;
			  	  }
			  	  processBar(testApproveBean);
  					return true;
			 }
			 
			 function valCharLength(Value){
			     var j=0;
			     var s = Value;
			     for(var i=0; i<s.length; i++) {
					if (s.substr(i,1).charCodeAt(0)>255) {
						j  = j + 2;
					} else { 
						j++;
					}
			     }
			     return j;
		   }
	</script>
	</head>

	<body>
		<template:titile value="审核测试计划" />
		<html:form action="/testApproveAction.do?method=approveTestPlan"
			styleId="testApproveBean" onsubmit="return checkAdd();">
			<input name="act" id="act" value="${act}" type="hidden"/>
			<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				 <input type="hidden" name="planid" id="planid" value="${testPlan.id}"/>
				    <tr class=trcolor>
				      <td class="tdulleft">代维单位：</td>
				      <td class="tdulright"><c:out value="${c.contractorName}"></c:out></td>
				      <td class="tdulleft">计划制定人：</td>
				      <td class="tdulright"><c:out value="${user.userName}"></c:out></td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">计划名称：</td>
				      <td class="tdulright">
				         <c:out value="${testPlan.testPlanName}"></c:out>
				      </td>
				      <td colspan="2"><a href="javascript:viewPalnDetail();">查看计划详细信息</a></td>
				    </tr>
				    <tr class=trcolor>
				      <td class="tdulleft">计划类型：</td>
				      <td class="tdulright" colspan="3">
				     	 <c:if test="${testPlan.testPlanType=='1'}">备纤测试</c:if>
				         <c:if test="${testPlan.testPlanType=='2'}">接地电阻测试</c:if>
				         <c:if test="${testPlan.testPlanType=='3'}"> 金属护套绝缘电阻测试</c:if>
				     </td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">计划测试日期：</td>
				      <td class="tdulright" colspan="3">
				      <bean:write name="testPlan" property="testBeginDate" format="yyyy-MM-dd"/>
				          至
				        <bean:write name="testPlan" property="testEndDate" format="yyyy-MM-dd"/>
				     </td>
				    </tr>
				 
				    <tr class=trcolor>
				      <td class="tdulleft">备注：</td>
				      <td class="tdulright" colspan="3">				       
				           <c:out value="${testPlan.testPlanRemark}"></c:out>  
				     </td>
				    </tr>
				    <logic:notEmpty name="planApproves"> 
					 		<tr class=trcolor>
							   	<td class="tdulleft">测试计划审核次数：</td>
							   	<td class="tdulright" colspan="3"><c:out value="${testPlan.approveTimes}"></c:out></td>
						   	</tr>
					 		<tr class=trwhite align="center">
							 	<td colspan="4" align="left">&nbsp;&nbsp;计划审核详细信息</td>
						    </tr>
							<tr  class=trcolor>
							 	<td width="15%">&nbsp;&nbsp;审核人</td><td width="20%">审核时间 </td>
							 	<td width="10%">审核结果 </td><td width="55%">审核意见 </td>
							 </tr>
							 <c:forEach items="${planApproves}" var="approve"  varStatus="loop">
							 	<c:if test="${loop.count%2==0 }"> <tr class=trwhite></c:if>
							 	<c:if test="${loop.count%2==1 }"> <tr class=trcolor></c:if>
							 		<td>&nbsp;&nbsp;<bean:write name="approve" property="username"/></td>
							 		<td><bean:write name="approve" property="approve_time"/></td>
							 		<td><bean:write name="approve" property="approve_result"/></td>
							 		<td>
							 			<bean:write name="approve" property="approve_remark"/>
							 		</td>
							 	</tr>
							 </c:forEach>
						 </logic:notEmpty>
				    <logic:equal value="approve" name="act">
					    <tr>
							<td height="25" style="text-align: right;">
								审核结果：
							</td>
							<td colspan="3" style="text-align: left;">
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
							<td colspan="3" style="text-align: left;">
								<textarea name="approveRemark" id="approveRemark" rows="6"  style="width: 400px;"></textarea>
							</td>
						</tr>
				</logic:equal>
				<logic:equal value="transfer" name="act">
					<apptag:approverselect label="转审人" inputName="approver,mobiles" spanId="approverSpan" inputType="radio" />
					<tr class=trwhite>
						<td class="tdulleft" height="25" style="text-align: right;">
							转审说明：
						</td>
						<td class="tdulright"  style="text-align: left;">
							<textarea name="approveRemark" id="approveRemark" rows="6"  style="width: 400px;"></textarea>
						</td>
					</tr>
				</logic:equal>
				    <tr>
				      <td align="center" colspan="4">				       
				        <html:submit styleClass="button" value="审核"></html:submit>
				         <input type="button" class="button" value="返回" onclick="javascript:history.back();"/>
				     </td>
				    </tr>
				  </table>
		</html:form>
	</body>
</html>
