<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.beans.PatrolManBean"%>
<%@page import="com.cabletech.planinfo.beans.StencilTaskBean"%>
<%@page import="com.cabletech.planinfo.beans.*"%>
<script language="javascript" type="text/javascript">
<!--
	function next(){
	  var count = validate();
		if(count == 0){
			alert("��ѡ��һ��Ѳ��ά����");
			return false;
		}
		//if(validate()>5){
			//alert("������ƶ�5��Ѳ����������ƻ���");
			//return false;
		//}
		if(confirm("��ȷ��Ҫ����ѡ��Ѳ������������ƻ��ƶ���\n���ĳѲ�������ƶ������ѡ���Զ�ʧЧ������\n���ظ���������")){
			batchBean.submit();
			document.all.bbt.disabled=true;
			document.all.cbt.disabled=true;
		}
		
	}
	function validate(){
		var isChecked = false;
		if(document.all.patrol.checked){
			isChecked = true;
		}
		var k=0;
		if(document.all.patrol.length == null){
			if(document.all.patrol.checked){
				k++;
				isChecked = true;
			}
		}else{
		for(i=0;i<document.all.patrol.length;i++){
			if(document.all.patrol[i].checked){
				k++;
				isChecked = true;
			}
		}
	}
	 // document.getElementById('patrolCount').value = k;
		/*
		for(i=0;i<document.all.patrol.length;i++){
			if(document.all.patrol[i].checked){
				k++
				isChecked = true;
			}
		}
		*/
		
		return k;
	}
	
//-->
</script>
<%
	List patrolList = (List)request.getAttribute("patrol");
 %>
<body onload="">
<br>
<template:titile value="�����ƶ��ƻ�"/>
<html:form action="/batchPlan.do?method=createBatchPlan" >
  <input id="deptname" type="hidden"  name="regionid" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>
  <input id="patrolCount" type="hidden"  name="patrolCount" value="<%=patrolList.size() %>">
  <%

	BatchBean bean = (BatchBean)session.getAttribute("batch"); 
	//out.println(bean.getBatchname());
	%>
  <template:formTable contentwidth="500" namewidth="150">  
    <template:formTr name="Ѳ��ά����" >
    	<table name="tasklisttable" border="0" cellspacing="0" cellpadding="5">
    <%
    	for(int i=0;i<patrolList.size();i++){
    		PatrolManBean pm = (PatrolManBean)patrolList.get(i);
    		%>
    		<tr>
    		<td>
    		<input type="checkbox" name="patrol"  value="<%=pm.getPatrolID() %>"/>
			<input name="patrolname" type="text" value="<%= pm.getPatrolName()%>"  class="inputtext" style="border:0;font-weight:bold;background-color:transparent;width:400;"  readonly ><br>
    		<%
    		for(int j=0;j<pm.getTemp().size();j++){
    			StencilTaskBean stencil = (StencilTaskBean)pm.getTemp().get(j);
    			//checked
    		 %>
    		&nbsp&nbsp&nbsp&nbsp<input type="radio" name="<%=pm.getPatrolID()%>" checked value="<%=stencil.getID() %>"/>
			<input name="patrolname" type="text" value="<%= stencil.getStencilname()%>"  class="inputtext" style="border:0;background-color:transparent;width:350;"  readonly ><br/>
    		
     		<%} %>
    		</td>
    		</tr>
    		<%		
    	}
     %>
    
		</table>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:button property="action" styleClass="button" styleId="cbt" onclick="return next()">��������</html:button>
      </td>
      <td>
        <html:button property="action" styleClass="button" styleId="bbt" onclick="history.back(-1)">����</html:button>
      </td>
     
    </template:formSubmit>
  </template:formTable>
</html:form>
</body>
