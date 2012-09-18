<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.sparepartmanage.domainobjects.SparepartConstant" %>
<html>
	<head>
		<script type="" language="javascript">
        toDeletForm=function(idValue){
       	 	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
            	var url = "${ctx}/SparepartStorageAction.do?method=deleteSavedStorage&storage_id="+idValue;
	        	self.location.replace(url);
            }
            else
            	return ;
		}
		toUpForm=function(idValue){
		 	var url = "${ctx}/SparepartStorageAction.do?method=updateSavedStorageForm&storage_id="+idValue;
		    self.location.replace(url);
		}
        toGetForm=function(idValue){
        	var url = "${ctx}/SparepartStorageAction.do?method=viewSavedStorage&storage_id="+idValue;
        	self.location.replace(url);
		}
		toStorageOpForm=function(method,param){
		 	var url = "${ctx}/SparepartStorageAction.do?method="+method+"&"+param;
		    self.location.replace(url);
		}
		toApplyForm=function(url,param){
			self.location.replace(url);
		}
		toApplyFormMod=function(url) {
			self.location.replace(url);
		}
		toStorageOpForm=function(method,param){
		 	var url = "${ctx}/SparepartStorageAction.do?method="+method+"&"+param;
		    self.location.replace(url);
		}
		valCharLength=function(Value){
			var j=0;
			var s=Value;
			for(var i=0;i<s.length;i++){
				if(s.substr(i,1).charCodeAt(0)>255){
					j=j+2;
				}else{
					j++;
				}
			}
			return j;
		}
		addGoBack=function(){
			var url = "${ctx}/SparepartStorageAction.do?method=querySparepartStorageForm";
			self.location.replace(url);
		}
		exportList=function(){
			var url="${ctx}/SparepartStorageAction.do?method=exportStorageList";
			self.location.replace(url);
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
		String num="";
		 %>
		<template:titile value="��������һ����" />
		<display:table name="sessionScope.STORAGE_LIST" id="currentRowObject" pagesize="18">
			<display:column property="product_factory" sortable="true" title="��������" headerClass="subject" maxLength="10" />
			<display:column property="spare_part_name" sortable="true" title="��������" headerClass="subject" maxLength="10" />
			<display:column property="spare_part_model" sortable="true" title="�����ͺ�" headerClass="subject" maxLength="10" />
			<display:column property="serial_number" sortable="true" title="�������к�" headerClass="subject" maxLength="15" />
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
			<display:column property="storage_position" title="����λ��" headerClass="subject" maxLength="10" />
			<display:column property="storage_number" title="�������" sortable="true" headerClass="subject" maxLength="10" />
			<display:column media="html" title="����" headerClass="subject">
				<apptag:checkpower thirdmould="90711">
					<%
				    	object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				    	id = (String) object.get("tid");
				    	num = String.valueOf( object.get("storage_number"));
				  	%>
				  	<%--<logic:equal value="02" name="currentRowObject" property="spare_part_state">
						<a href="javascript:toApplyForm('${ctx}/SparepartApplyAction.do?method=listApply&storage_id=<%=id%>')">��������</a>
					--%>
						
						 
							<%if(!num.equals("0")) { %>
								<logic:equal value="02" name="currentRowObject" property="spare_part_state">
									<a href="javascript:toApplyFormMod('${ctx}/SparepartApplyAction.do?method=addApplyForm&storage_id=<%=id%>')" >�������</a> | 
								</logic:equal>
								<a href="javascript:toStorageOpForm('giveBackToStorageForm','from_storage_id=<%=id%>')">�黹����</a>
							<%} %>
						<%--<logic:equal value="03" name="currentRowObject" property="spare_part_state">
							 | <a href="javascript:toApplyFormMod('${ctx}/SparepartApplyAction.do?method=addApplyForm&storage_id=<%=id%>')" >����ʹ��</a>
						</logic:equal>
					--%><%--</logic:equal>--%>
				</apptag:checkpower>	
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">
			<tr>
				<td style="text-align:left;">˵����
					<img src="${ctx}/images/sparepartstate/01.gif">��ʾ���ƶ����á�״̬��
					<img src="${ctx}/images/sparepartstate/02.gif">��ʾ����ά���á�״̬��
					<img src="${ctx}/images/sparepartstate/03.gif">��ʾ�����С�״̬��
					<img src="${ctx}/images/sparepartstate/04.gif">��ʾ�����ޡ�״̬��
					<img src="${ctx}/images/sparepartstate/05.gif">��ʾ�����ϡ�״̬��
				</td>
			</tr>
			<tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="����ΪExcel"
						onclick="exportList()" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
