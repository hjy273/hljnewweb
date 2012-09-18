<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>��˲��Լƻ�</title>
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
			 	 var r = "������";
			 	 var act = document.getElementById("act").value;
			 	 if(act=="transfer"){
			 	 	r="ת��˵��";
				 	 var approvers = document.getElementById("approver").value;
				     if(approvers==null || approvers==""){
						alert("ת���˲���Ϊ��!");
			  			return false;
	  			     }
			 	 }
			 	 if(valCharLength(document.getElementById('approveRemark').value) > 1024) {
			  		   alert(r+"���ܳ���512�����ֻ���1024��Ӣ���ַ���")
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
		<template:titile value="��˲��Լƻ�" />
		<html:form action="/testApproveAction.do?method=approveTestPlan"
			styleId="testApproveBean" onsubmit="return checkAdd();">
			<input name="act" id="act" value="${act}" type="hidden"/>
			<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				 <input type="hidden" name="planid" id="planid" value="${testPlan.id}"/>
				    <tr class=trcolor>
				      <td class="tdulleft">��ά��λ��</td>
				      <td class="tdulright"><c:out value="${c.contractorName}"></c:out></td>
				      <td class="tdulleft">�ƻ��ƶ��ˣ�</td>
				      <td class="tdulright"><c:out value="${user.userName}"></c:out></td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">�ƻ����ƣ�</td>
				      <td class="tdulright">
				         <c:out value="${testPlan.testPlanName}"></c:out>
				      </td>
				      <td colspan="2"><a href="javascript:viewPalnDetail();">�鿴�ƻ���ϸ��Ϣ</a></td>
				    </tr>
				    <tr class=trcolor>
				      <td class="tdulleft">�ƻ����ͣ�</td>
				      <td class="tdulright" colspan="3">
				     	 <c:if test="${testPlan.testPlanType=='1'}">���˲���</c:if>
				         <c:if test="${testPlan.testPlanType=='2'}">�ӵص������</c:if>
				         <c:if test="${testPlan.testPlanType=='3'}"> �������׾�Ե�������</c:if>
				     </td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">�ƻ��������ڣ�</td>
				      <td class="tdulright" colspan="3">
				      <bean:write name="testPlan" property="testBeginDate" format="yyyy-MM-dd"/>
				          ��
				        <bean:write name="testPlan" property="testEndDate" format="yyyy-MM-dd"/>
				     </td>
				    </tr>
				 
				    <tr class=trcolor>
				      <td class="tdulleft">��ע��</td>
				      <td class="tdulright" colspan="3">				       
				           <c:out value="${testPlan.testPlanRemark}"></c:out>  
				     </td>
				    </tr>
				    <logic:notEmpty name="planApproves"> 
					 		<tr class=trcolor>
							   	<td class="tdulleft">���Լƻ���˴�����</td>
							   	<td class="tdulright" colspan="3"><c:out value="${testPlan.approveTimes}"></c:out></td>
						   	</tr>
					 		<tr class=trwhite align="center">
							 	<td colspan="4" align="left">&nbsp;&nbsp;�ƻ������ϸ��Ϣ</td>
						    </tr>
							<tr  class=trcolor>
							 	<td width="15%">&nbsp;&nbsp;�����</td><td width="20%">���ʱ�� </td>
							 	<td width="10%">��˽�� </td><td width="55%">������ </td>
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
								��˽����
							</td>
							<td colspan="3" style="text-align: left;">
								<input type="radio" name="approveResult" value="1" checked />
								ͨ��
								<input type="radio" name="approveResult" value="0" />
								��ͨ��
							</td>
						</tr>
						<tr>
							<td height="25" style="text-align: right;">
								��������
							</td>
							<td colspan="3" style="text-align: left;">
								<textarea name="approveRemark" id="approveRemark" rows="6"  style="width: 400px;"></textarea>
							</td>
						</tr>
				</logic:equal>
				<logic:equal value="transfer" name="act">
					<apptag:approverselect label="ת����" inputName="approver,mobiles" spanId="approverSpan" inputType="radio" />
					<tr class=trwhite>
						<td class="tdulleft" height="25" style="text-align: right;">
							ת��˵����
						</td>
						<td class="tdulright"  style="text-align: left;">
							<textarea name="approveRemark" id="approveRemark" rows="6"  style="width: 400px;"></textarea>
						</td>
					</tr>
				</logic:equal>
				    <tr>
				      <td align="center" colspan="4">				       
				        <html:submit styleClass="button" value="���"></html:submit>
				         <input type="button" class="button" value="����" onclick="javascript:history.back();"/>
				     </td>
				    </tr>
				  </table>
		</html:form>
	</body>
</html>
