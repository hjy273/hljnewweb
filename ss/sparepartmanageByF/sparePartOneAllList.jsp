<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.sparepartmanage.domainobjects.SparepartConstant" %>
<html>
	<head>
		<script type="" language="javascript">
        toDeletForm=function(idValue){
       	 	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
            	window.location.href = "${ctx}/SparepartStorageAction.do?method=deleteSavedStorage&storage_id="+idValue;
	        	//self.location.replace(url);
            }
            else
            	return ;
		}
		toUpForm=function(idValue){
		 window.location.href= "${ctx}/SparepartStorageAction.do?method=updateSavedStorageForm&storage_id="+idValue;
		    //self.location.replace(url);
		}
     
		addGoBack=function(){
			window.location.href = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageByBack";
			//self.location.replace(url);
			//history.back();
		}
		</script>
		<title>partBaseInfo</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />
		<%
		BasicDynaBean object=null;
		String id="";
		 %>
		<template:titile value="����������Ϣһ����" />
		<display:table name="sessionScope.STORAGE_LIST" id="currentRowObject" pagesize="18">
			<display:column property="product_factory" sortable="true" title="��������" headerClass="subject" maxLength="10"/>
			<display:column property="spare_part_name" sortable="true" title="��������" headerClass="subject" maxLength="10"/>
			<display:column property="spare_part_model" sortable="true" title="�����ͺ�" headerClass="subject" maxLength="10"/>
			<display:column property="serial_number" sortable="true" title="�������к�" headerClass="subject" />
			<%
				 object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
				String name =  (String) object.get("name");
				if(name != null && !name.trim().equals("")){%>
				<display:column property="name" title="����λ��" headerClass="subject" maxLength="20"/>
			<% 	}
				
			 %>
			<display:column media="html" title="����״̬" sortable="true" class="subject" headerClass="subject">
				<%
				    object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
				    String state = (String) object.get("spare_part_state");
				    String path =(String)application.getAttribute("ctx");
				    if(SparepartConstant.MOBILE_WAIT_USE.equals(state)){
				    	out.print("<img src=\""+path+"/images/sparepartstate/"+state+".gif\" alt=\"�ƶ�����\">");
				    }
				    if(SparepartConstant.CONTRACTOR_WAIT_USE.equals(state)){
				    	out.print("<img src=\""+path+"/images/sparepartstate/"+state+".gif\" alt=\"��ά����\">");
				    }
				    if(SparepartConstant.IS_APPLY.equals(state)){
				    	out.print("<img src=\""+path+"/images/sparepartstate/"+state+".gif\" alt=\"�ѱ�����\">");
				    }
				    if(SparepartConstant.IS_REPLACE.equals(state)){
				    	out.print("<img src=\""+path+"/images/sparepartstate/"+state+".gif\" alt=\"���滻\">");
				    }				    
				    if(SparepartConstant.IS_RUNNING.equals(state)){
				    	out.print("<img src=\""+path+"/images/sparepartstate/"+state+".gif\" alt=\"����\">");
				    }
				    if(SparepartConstant.IS_MENDING.equals(state)){
				    	out.print("<img src=\""+path+"/images/sparepartstate/"+state+".gif\" alt=\"����\">");
				    }
				    if(SparepartConstant.IS_DISCARDED.equals(state)){
				    	out.print("<img src=\""+path+"/images/sparepartstate/"+state+".gif\" alt=\"����\">");
				    }
				 %>
			</display:column>
			<logic:equal value="01" name="currentRowObject" property="spare_part_state">
				<display:column media="html" title="����" headerClass="subject" >
				<%
					 object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
					 id = (String) object.get("tid");
				 %>
					<apptag:checkpower thirdmould="90704">				
						<a href="javascript:toUpForm('<%=id%>')">�޸�</a> 
						| <a href="javascript:toDeletForm('<%=id%>')">ɾ��</a>
					</apptag:checkpower>
				</display:column>
			</logic:equal>			
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px" align="center">
			<tr>
				<td style="text-align:left;">˵����
					<img src="${ctx}/images/sparepartstate/01.gif">��ʾ���ƶ����á�״̬��
					<img src="${ctx}/images/sparepartstate/02.gif">��ʾ����ά���á�״̬��
					<img src="${ctx}/images/sparepartstate/06.gif">��ʾ���ѱ����롱״̬��
					<img src="${ctx}/images/sparepartstate/07.gif">��ʾ�����滻��״̬��
					<img src="${ctx}/images/sparepartstate/03.gif">��ʾ�����С�״̬��
					<img src="${ctx}/images/sparepartstate/04.gif">��ʾ�����ޡ�״̬��
					<img src="${ctx}/images/sparepartstate/05.gif">��ʾ�����ϡ�״̬��
					
				</td>
			</tr>
			
			<tr>
				<td style="text-align: center;">
					<input name="action" class="button" value="����"
						onclick="addGoBack()" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
