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
       	 	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
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
   <template:titile value="��������һ����" />
		<display:table name="sessionScope.linePatrolList" requestURI="${ctx}/LinePatrolManagerAction.do?method=queryLinePatrolInfo" id="currentRowObject" pagesize="18">
			<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	      	 		id = object.get("id");
	      	 		state = object.get("state");
	      	 		title = object.get("title");
				} %>
			<display:column media="html" title="���뵥����" >
			    	<a href="javascript:toLookForm('<%=id%>')"><%=title %></a>
			</display:column>
			
			<display:column property="creator" title="������" headerClass="subject"  sortable="true"/>	
			<display:column property="type" sortable="true" title="������Դ" headerClass="subject" maxLength="10"/>
				 
			<display:column property="createdate" title="����ʱ��" headerClass="subject"  sortable="true"/>	 
			 <display:column media="html" title="����״̬" >
				
				<%
                   if(state == null || "1".equals(state.toString())){	
                   %>
                   δ����
				<%			
				 }else if("1".equals(state.toString())){
	             	
	             	%>
	             		<!-- ��������ͨ�� -->
	             		
	             	<%
	             	}else if("3".equals(state.toString())){
	             	%>
	             	   �ƶ�����ͨ��
	             	  
	             	<%
	             	}else if("4".equals(state.toString())){
	             	%>
	             	  <!-- <font color="red">�ƶ���������</font> -->
	             	  
	             	<%
	             	
	             }else if("2".equals(state.toString())){	             
	              %>
	                <font color="red">�ƶ�����ͨ��</font>
	              
	            		
	              <%} %>
            </display:column>
            <logic:equal value="2" name="type">
            <display:column media="html" title="����" >
            
				<%
                   if(state == null || "1".equals(state.toString())){	
                   %>
                   <a href="javascript:toEditForm('<%=id%>')">�޸�</a>
	            	
	            			| <a href="javascript:toDeletForm('<%=id%>')">ɾ��</a>
				<%			
				 }else if("2".equals(state.toString())){	             
	              %>
	                <a href="javascript:toEditForm('<%=id%>')">�޸�</a>
	            	
	            			| <a href="javascript:toDeletForm('<%=id%>')">ɾ��</a>
	              
	            		
	              <%} %>
	              
				
            </display:column>
            </logic:equal>
		</display:table>
  </body>
</html>
