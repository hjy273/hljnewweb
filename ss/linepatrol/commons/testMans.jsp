<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type='text/javascript'>
			function saveUser(){
				var objs = document.getElementsByName('user');
				var num=0;   
				for(var i=0;i<objs.length;i++){  
				    if(objs[i].checked==true){            
				      num++;                   
				     }   
				} 
				if(num==0){ 
					alert("��ѡ��һ���û�!"); 
					return ; 
				} 
				parent.submitForm($('saveTestMan'));
			}
			queryUser=function(){
			    var userName =  document.getElementById("userName").value;
				var url="${ctx}/loadTestManAction.do?method=getTestMans&userName="+userName;
				self.location.replace(url);
		    }
		    
		</script>
	</head>
	<body>
		<form action="${ctx}/loadTestManAction.do?method=saveUsers" id="saveTestMan"  method="post" >
			<template:formTable >
			<template:formTr name="ģ����ѯ">
				<input type="text" style="width:150" class="inputtext" id="userName" name="userName">
				<input type="button" value="��������ѯ" onclick="queryUser()" >
			</template:formTr>
			<template:formTr name="��ά�û�">
				<table width="100%">
					<c:forEach items="${contractorUser}" var="user"> 
						<%int i = 0; %>
							<bean:define id="username" name="user" property="name"></bean:define>
							<c:forEach items="${testMans}" var="man">
								<c:if test="${username==man}">
							     <%i++; %>
								</c:if>
							</c:forEach>
							   <%
							   	if(i>=1){%>
							   	<input type="checkbox" name="user" checked="checked" value="<bean:write name="user" property="name"/>"/>
							 <%	}else{%>
							 <input type="checkbox" name="user" value="<bean:write name="user" property="name"/>"/>
							 <% }
							    %>
				     		<bean:write name="user" property="name"/><br/>
				     	</c:forEach>
				</table>
			</template:formTr>
		</template:formTable>
		<template:formSubmit>
			<td>
				 <input type="button"  onclick="saveUser();" class="button" value="����"/>
			</td>
			<td>
				<html:reset property="reset" styleClass="button" value="����" />
			</td>
			<td>
				<input type="button"  onclick="parent.closeWin2();" class="button" value="�ر�"/>
			</td>
	    </template:formSubmit>
		</form>
	</body>
</html>
