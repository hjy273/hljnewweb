<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<%@ page language="java" contentType="text/html; charset=GBK"%>

<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
<html>
  <head>
    
  </head>
  <script type="text/javascript">
     toViewForm=function(idValue){
            	window.location.href = "${ctx}/LinePatrolManagerAction.do?method=checkLinePatrolByCon&id="+idValue;
		}
		
	   toLookForm=function(idValue){
            	window.location.href = "${ctx}/LinePatrolManagerAction.do?method=lookLinePatrolByCon&id="+idValue;
		}	
  </script>
  <body>
  <%
		BasicDynaBean object=null;
		Object id=null;		
		Object stat=null;	
		Object title =null;
		 %>
   <template:titile value="������������һ����" />
		<display:table name="sessionScope.assesslist" requestURI="${ctx}/LinePatrolManagerAction.do?method=queryLinePatorAssess" id="currentRowObject" pagesize="18">
			<%object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");	
						if(object != null) {
	      	 		      id = object.get("id");
	      	 		       title = object.get("title");
	      	 		    } %>
			<display:column media="html" title="���뵥����" >
			    	<a href="javascript:toLookForm('<%=id%>')"><%=title %></a>
			</display:column>
			<display:column property="creator" title="������" headerClass="subject"  sortable="true"/>	
			<display:column property="type" sortable="true" title="������Դ" headerClass="subject" maxLength="10"/>
			
			<display:column property="createdate" title="����ʱ��" headerClass="subject"  sortable="true"/>	 
			<logic:equal value="query" name="type" >
			  <display:column media="html" title="����״̬" >
			  
			       <% if(object != null) {
	      	 		      
	      	 		       stat = object.get("state");
	      	 		    }
	      	 		    %>
	      	 		    <logic:equal value="3" name="stype" >
	      	 		    <%
	      	 		    if("2".equals(stat)){
	      	 		    %>
	      	 		     <font color="red">��������</font> 
	      	 		    <%
	      	 		    }else {
	      	 		    %>
	      	 		     ͨ������
	      	 		    <%
	      	 		    }
					 %>
					 </logic:equal>
					 <logic:equal value="1" name="stype" >
					    <%
	      	 		    if("4".equals(stat)){
	      	 		    %>
	      	 		     <font color="red">��������</font> 
	      	 		    <%
	      	 		    }else if("3".equals(stat)){
	      	 		    %>
	      	 		     ͨ������
	      	 		    <%
	      	 		    }%>
					 </logic:equal>
			  </display:column>
			</logic:equal>
			 <display:column media="html" title="����" >
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	      	 		id = object.get("id");
				} %>
				<logic:equal value="3" name="type" >
				<a href="javascript:toViewForm('<%=id%>')">����</a>
				</logic:equal>
				<logic:equal value="1" name="type" >
	      	 		<a href="javascript:toViewForm('<%=id%>')">����</a>
				</logic:equal>
				<logic:equal value="query" name="type" >
				  <a href="javascript:toLookForm('<%=id%>')">�鿴</a>
				</logic:equal>
	            		
	             
            </display:column>
		</display:table>
  </body>
</html>
