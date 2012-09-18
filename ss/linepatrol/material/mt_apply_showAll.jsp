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
            	window.location.href = "${ctx}/LinePatrolManagerAction.do?method=viewLinePatrolByID&id="+idValue;
		}
		
        toDeletForm=function(idValue){
       	 	if(confirm("你确定要执行此次删除操作吗?")){
            	window.location.href = "${ctx}/LinePatrolManagerAction.do?method=deleteLinePatrol&id="+idValue;
            }           
		}
		toEditForm=function(idValue){
		 	window.location.href = "${ctx}/LinePatrolManagerAction.do?method=editLinePatrol&id="+idValue;
		}   
		
			
	   toLookForm=function(idValue){
            	window.location.href = "${ctx}/LinePatrolManagerAction.do?method=lookLinePatrolByCon&id="+idValue;
		}	    
  </script>
  <body>
  <%
		BasicDynaBean object=null;
		Object id=null;		
		Object state=null;	
		
		Object title=null;
		 %>
   <template:titile value="材料申请一览表" />
		<display:table name="sessionScope.linePatrolList" requestURI="${ctx}/LinePatrolManagerAction.do?method=queryLinePatrolInfo" id="currentRowObject" pagesize="18">
			<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	      	 		id = object.get("id");
	      	 		state = object.get("state");
	      	 		title = object.get("title");
				} %>
			<display:column media="html" title="申请单标题" >
			    	<a href="javascript:toLookForm('<%=id%>')"><%=title %></a>
			</display:column>
			
			<display:column property="creator" title="申请人" headerClass="subject"  sortable="true"/>	
			<display:column property="type" sortable="true" title="材料来源" headerClass="subject" maxLength="10"/>
				 
			<display:column property="createdate" title="申请时间" headerClass="subject"  sortable="true"/>	 
			 <display:column media="html" title="审批状态" >
				
				<%
                   if(state == null || "1".equals(state.toString())){	
                   %>
                   未审批
				<%			
				 }else if("1".equals(state.toString())){
	             	
	             	%>
	             		<!-- 监理审批通过 -->
	             		
	             	<%
	             	}else if("3".equals(state.toString())){
	             	%>
	             	   移动审批通过
	             	  
	             	<%
	             	}else if("4".equals(state.toString())){
	             	%>
	             	  <!-- <font color="red">移动不予审批</font> -->
	             	  
	             	<%
	             	
	             }else if("2".equals(state.toString())){	             
	              %>
	                <font color="red">移动不予通过</font>
	              
	            		
	              <%} %>
            </display:column>
            <logic:equal value="2" name="type">
            <display:column media="html" title="操作" >
            
				<%
                   if(state == null || "1".equals(state.toString())){	
                   %>
                   <a href="javascript:toEditForm('<%=id%>')">修改</a>
	            	
	            			| <a href="javascript:toDeletForm('<%=id%>')">删除</a>
				<%			
				 }else if("2".equals(state.toString())){	             
	              %>
	                <a href="javascript:toEditForm('<%=id%>')">修改</a>
	            	
	            			| <a href="javascript:toDeletForm('<%=id%>')">删除</a>
	              
	            		
	              <%} %>
	              
				
            </display:column>
            </logic:equal>
		</display:table>
  </body>
</html>
