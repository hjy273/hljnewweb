<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>��ƻ�</title>
	</head>

	<body>
		<template:titile value="��ѯ��ƻ�" />
		<html:form action="/testYearPlanAction.do?method=getYearPlans" styleId="queryForm">
	       <table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		       	<c:if test="${LOGIN_USER.deptype=='1'}">
		       	<tr class=trwhite>
				      <td class="tdulleft">��ά��</td>
				      <td class="tdulright" >
		       		<html:select property="contractorId" styleClass="inputtext" styleId="contractorId" style="width:135px">
		       			<html:option value="">����</html:option>
		       			<html:options property="contractorID" labelProperty="contractorName" collection="cons"/>
		       		</html:select>
		       		 </td>
				  </tr>
		       	</c:if>
			     <tr class=trwhite>
				      <td class="tdulleft">�ƻ����ƣ�</td>
				      <td class="tdulright" >
				          <html:text property="planName" styleClass="inputtext required" style="width:225px"></html:text>
				      </td>
				  </tr>
				  <tr class=trcolor>
				      <td class="tdulleft">�ƻ���ݣ�</td>
				      <td  class="tdulright">
				      <html:text property="year" styleId="year" styleClass="Wdate" style="width:225px" onfocus="WdatePicker({dateFmt:'yyyy'})" readonly="true"></html:text>
				    </td>
				   </tr>
			  </table>
			  <div align="center">
			  	<input type="hidden" name="isQuery" value="true">
			  	<input name="action" class="button" value="��ѯ"	 type="submit" />
			  </div>
		</html:form>
		<template:displayHide styleId="queryForm"></template:displayHide>
		
		<logic:empty name="list"></logic:empty>
		<script type="text/javascript" language="javascript">
			 toViewForm=function(idValue){
	            	window.location.href = "${ctx}/testYearPlanAction.do?method=viewYearPlanForm&planid="+idValue;
			 }
			goBack=function(){
	            	window.location.href = "${ctx}/testYearPlanAction.do?method=queryYearPlanForm";
			 }
		</script>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
		<display:table name="sessionScope.list" id="currentRowObject" pagesize="15">
      		<display:column property="contractorname" title="��ά��λ" headerClass="subject"  sortable="true"/>	
			<display:column property="plan_name" title="�ƻ�����" headerClass="subject"  sortable="true"/>	
			<display:column property="year" sortable="true" title="�ƻ����" headerClass="subject" />
			<display:column property="totalnum" sortable="true" title="�ƻ�������" headerClass="subject" />
			<display:column property="worknum" sortable="true" title="��ɲ�����" headerClass="subject" />
			<display:column property="notestnum" sortable="true" title="δ���Ե���" headerClass="subject" />
			<display:column property="create_time" sortable="true" title="����ʱ��" headerClass="subject" />
			 <display:column media="html" title="����" >
			 <bean:define id="tid" name="currentRowObject" property="id"></bean:define>
			  <a href="javascript:toViewForm('<%=tid%>')">�鿴</a>
            </display:column>
		</display:table>
		<div align="center">
			<input name="action" class="button" value="����"	 type="button" onclick="goBack();"/>
		</div>
	</body>
</html>
