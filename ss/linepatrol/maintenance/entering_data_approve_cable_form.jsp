<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>��˲��Լƻ�</title>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type='text/javascript'>
			function view(planid,lineid){
				window.location.href="${ctx}/testApproveAction.do?method=viewCableData&planid="+planid+"&lineid="+lineid;
			}
			
			 function checkAdd(){
			 	 var act = document.getElementById("act").value;
			 	 if(act=="transfer"){
				 	 var approvers = document.getElementById("approver").value;
				     if(approvers==null || approvers==""){
						alert("ת���˲���Ϊ��!");
			  			return false;
	  			     }
			 	 }
			 	 processBar(saveTestPlan);
  					return true;
			}
		 function  goBack(){
			var url="${ctx}/testPlanAction.do?method=getActWorkForm";
			self.location.replace(url);
		}
		</script>
	</head>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	<body>
		
		<template:titile value="���¼������" />
	<%
		BasicDynaBean object=null;
		Object lineid=null;	
		Object planid = null;	
		Object state="";
		int i = 1;
	 %>
	       <table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				    <tr class=trcolor>
				      <td class="tdulleft">��ά��λ��</td>
				      <td class="tdulright"><c:out value="${contraName}"></c:out></td>
				      <td class="tdulleft">�ƻ��ƶ��ˣ�</td>
				      <td class="tdulright"><c:out value="${userName}"></c:out></td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">�ƻ����ƣ�</td>
				      <td class="tdulright">
				         <c:out value="${plan.testPlanName}"></c:out>
				      </td>
				      <td colspan="2">&nbsp;</td>
				    </tr>
				    <tr class=trcolor>
				      <td class="tdulleft">�ƻ����ͣ�</td>
				      <td class="tdulright" colspan="3">
				     	 <c:if test="${plan.testPlanType=='1'}">���˲���</c:if>
				         <c:if test="${plan.testPlanType=='2'}">�ӵص������</c:if>
				         <c:if test="${plan.testPlanType=='3'}"> �������׾�Ե�������</c:if>
				     </td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">�ƻ��������ڣ�</td>
				      <td class="tdulright" colspan="3">
				      <bean:write name="plan" property="testBeginDate" format="yyyy-MM-dd"/>
				          ��
				        <bean:write name="plan" property="testEndDate" format="yyyy-MM-dd"/>
				     </td>
				    </tr>
				 
				    <tr class=trcolor>
				      <td class="tdulleft">��ע��</td>
				      <td class="tdulright" colspan="3">				       
				           <c:out value="${plan.testPlanRemark}"></c:out>  
				     </td>
				    </tr>
				    </table>
		   <display:table name="sessionScope.planLines"  id="currentRowObject" pagesize="12" >
		    	<display:column value="<%=i%>" title="���" style="5%"></display:column>
				<display:column property="segmentname" sortable="true" title="�м̶�"  maxLength="30" style="width:20%"/>
				<display:column property="finishtime" sortable="true" title="��άʱ��"  style="width:10%"/>
				<display:column property="cableline_test_port" sortable="true" title="�ƻ����Զ�" style="width:7%"/>
				<display:column property="plan_date"  sortable="true" style="width:15%" title="�ƻ�����ʱ��" maxLength="25" />
				<display:column property="username" sortable="true" title="�ƻ�������" style="width:15%" maxLength="12"/>
				<logic:equal value="1" name="currentRowObject" property="state">
					<display:column property="isok" sortable="true" title="�Ƿ�ϸ�" />
				</logic:equal>
				<logic:notEqual value="1" name="currentRowObject" property="state">
					<display:column value="" sortable="true" title="�Ƿ�ϸ�" />
				</logic:notEqual>
				 <display:column property="iswritestate" sortable="true" title="�Ƿ�¼��" />
				<display:column media="html" title="����" >
				<% i++;
					object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	           		 if(object != null) {
		      	 		lineid = object.get("lineid");
		      	 		planid = object.get("test_plan_id");
		      	 		state = object.get("state");
				} %>
				 <% if(state.equals("1")){ %>
						<a href="javascript:view('<%=planid%>','<%=lineid%>')">�鿴</a>
				 <%} %>
            </display:column>
			</display:table><br/>
				<logic:notEmpty name="dataApproves"> 
			    <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
					 		<tr class=trcolor>
							   	<td class="tdulleft" colspan="2">����¼����˴���:</td>
							   	<td class="tdulright" colspan="2"><c:out value="${approvetimes}"></c:out></td>
						   	</tr>
					 		<tr class=trwhite align="center">
							 	<td colspan="4" align="left">����¼�������ϸ��Ϣ</td>
						    </tr>
							<tr  class=trcolor>
							 	<td width="10%">�����</td><td width="20%">���ʱ�� </td><td width="10%">��˽�� </td><td width="60%">������ </td>
							 </tr>
							 <c:forEach items="${dataApproves}" var="approve"  varStatus="loop">
							 	<c:if test="${loop.count%2==0 }"> <tr class=trwhite></c:if>
							 	<c:if test="${loop.count%2==1 }"> <tr class=trcolor></c:if>
							 		<td><bean:write name="approve" property="username"/></td>
							 		<td><bean:write name="approve" property="approve_time"/></td>
							 		<td><bean:write name="approve" property="approve_result"/></td>
							 		<td>
							 			<bean:write name="approve" property="approve_remark"/>
							 		</td>
							 	</tr>
							 </c:forEach>
				</table>
			</logic:notEmpty>
			<html:form action="/testApproveAction.do?method=approveTestData" styleId="saveTestPlan" onsubmit="return checkAdd();">
			<input name="act" id="act" value="${act}" type="hidden"/>
				<table  border="0" align="center" cellpadding="0" cellspacing="0" style="border:#FFF">
					 <input type="hidden" name="dataid" id="dataid" value="${dataid}"/>
				 <logic:equal value="approve" name="act">
				    <tr>
						<td height="25" style="text-align: right;">
							��˽����
						</td>
						<td style="text-align: left;">
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
						<td  style="text-align: left;">
							<textarea name="approveRemark" rows="6" class="max-length-256" style="width: 400px;"></textarea>
						</td>
					</tr>
				</logic:equal>
					<logic:equal value="transfer" name="act">
				<apptag:approverselect label="ת����" inputName="approver,mobiles" spanId="approverSpan" inputType="radio" />
					<tr>
						<td class="tdulleft" height="25" style="text-align: right;">
							ת��˵����
						</td>
						<td class="tdulright"  style="text-align: left;">
							<textarea name="approveRemark" rows="6" class="max-length-256" style="width: 400px;"></textarea>
						</td>
					</tr>
				</logic:equal>
					 <tr>
					      <td height="25" style="text-align: center;" colspan="2">				       
					         <html:submit styleClass="button" value="���"></html:submit>
					         <input type="button" class="button" value="����" onclick="goBack();"/>
					     </td>
					 </tr>
				</table>
		</html:form>
	</body>
</html>