<%@include file="/common/header.jsp"%>

<html>
	<head>
		<script type="" language="javascript">
		addGoBack=function(){
			var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorage";
			self.location.replace(url);
		}
        toDelForm=function(idValue){
       	 	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
            	var url = "${ctx}/EquipmentInfoAction.do?method=deleteForEqu&eid="+idValue;
	        	self.location.replace(url);
            }
            else
            	return ;
		}
		toUpForm=function(idValue){
			var url = "${ctx}/EquipmentInfoAction.do?method=showUpdateForEqu&eid="+idValue;
			self.location.replace(url);
		}
        toGetForm=function(idValue){
        	var url = "${ctx}/SparepartApplyAction.do?method=viewOneApplyInfoForApply&view_method=<%=(String)request.getAttribute("method")%>&apply_id="+idValue;
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
		BasicDynaBean object = null;
		String id = "";
		%>
		<template:titile value="�豸����һ����" />
		<display:table name="sessionScope.equipmentList" id="currentRowObject"
			pagesize="18">
			<display:column property="equipment_name" title="�豸����" sortable="true" 
				 headerClass="subject" />
			<display:column property="contractorname" title="��ά��˾" sortable="true" 
				 headerClass="subject" />
			<display:column media="html" title="���ڲ�" sortable="true" 
				 headerClass="subject" >
				<%
				object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				int layer = Integer.parseInt((String)object.get("layer"));
				if(1 == layer){
					out.print("���Ĳ�");
				}else if(2 == layer){
					out.print("�����SDH");
				}else if(3 == layer){
					out.print("�����΢��");
				}else if(4 == layer){
					out.print("�����FSO");
				}else if(5 == layer){
					out.print("�⽻ά��");
				}
				 %>
			</display:column>
			<display:column media="html" title="����" headerClass="subject">
				
					<%
					object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					id = (String) object.get("eid");
					%>
					 <a href="javascript:toUpForm('<%=id%>')">�޸�</a>
					| <a href="javascript:toDelForm('<%=id%>')">ɾ��</a>
			</display:column>
			
		</display:table>
	</body>
</html>
