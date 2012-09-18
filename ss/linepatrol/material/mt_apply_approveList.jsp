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
   <template:titile value="审批材料申请一览表" />
		<display:table name="sessionScope.assesslist" requestURI="${ctx}/LinePatrolManagerAction.do?method=queryLinePatorAssess" id="currentRowObject" pagesize="18">
			<%object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");	
						if(object != null) {
	      	 		      id = object.get("id");
	      	 		       title = object.get("title");
	      	 		    } %>
			<display:column media="html" title="申请单标题" >
			    	<a href="javascript:toLookForm('<%=id%>')"><%=title %></a>
			</display:column>
			<display:column property="creator" title="申请人" headerClass="subject"  sortable="true"/>	
			<display:column property="type" sortable="true" title="材料来源" headerClass="subject" maxLength="10"/>
			
			<display:column property="createdate" title="申请时间" headerClass="subject"  sortable="true"/>	 
			<logic:equal value="query" name="type" >
			  <display:column media="html" title="审批状态" >
			  
			       <% if(object != null) {
	      	 		      
	      	 		       stat = object.get("state");
	      	 		    }
	      	 		    %>
	      	 		    <logic:equal value="3" name="stype" >
	      	 		    <%
	      	 		    if("2".equals(stat)){
	      	 		    %>
	      	 		     <font color="red">不予审批</font> 
	      	 		    <%
	      	 		    }else {
	      	 		    %>
	      	 		     通过审批
	      	 		    <%
	      	 		    }
					 %>
					 </logic:equal>
					 <logic:equal value="1" name="stype" >
					    <%
	      	 		    if("4".equals(stat)){
	      	 		    %>
	      	 		     <font color="red">不予审批</font> 
	      	 		    <%
	      	 		    }else if("3".equals(stat)){
	      	 		    %>
	      	 		     通过审批
	      	 		    <%
	      	 		    }%>
					 </logic:equal>
			  </display:column>
			</logic:equal>
			 <display:column media="html" title="操作" >
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	      	 		id = object.get("id");
				} %>
				<logic:equal value="3" name="type" >
				<a href="javascript:toViewForm('<%=id%>')">审批</a>
				</logic:equal>
				<logic:equal value="1" name="type" >
	      	 		<a href="javascript:toViewForm('<%=id%>')">审批</a>
				</logic:equal>
				<logic:equal value="query" name="type" >
				  <a href="javascript:toLookForm('<%=id%>')">查看</a>
				</logic:equal>
	            		
	             
            </display:column>
		</display:table>
  </body>
</html>
