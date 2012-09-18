<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
        toDeleteForm=function(idValue){
       	 	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
            	var url = "<%=request.getContextPath()%>/station_info.do?method=del&station_id="+idValue;
	        	self.location.replace(url);
            }
            else
            	return ;
		}
		toUpdateForm=function(idValue){
		 	var url = "<%=request.getContextPath()%>/station_info.do?method=modForm&station_id="+idValue;
		    self.location.replace(url);
		}
        toGetForm=function(idValue){
			var flag = 3;
        	var url = ""+idValue;
        	self.location.replace(url);
		}
		addGoBack=function(){
			var url = "";
			self.location.replace(url);
		}
		exportList=function(){
			var url="<%=request.getContextPath()%>/station_info.do?method=exportList";
			self.location.replace(url);
		}
		</script>
		<title></title>
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
		%>
		<template:titile value="�м�վ��Ϣ�б�" />
		<display:table name="sessionScope.STATION_LIST" requestURI="${ctx}/station_info.do?method=list" id="currentRowObject"
			pagesize="18">
			<display:column media="html" sortable="true" title="�м�վ����"
				headerClass="subject">
				<%
				object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				%>
				<a
					href="<%=request.getContextPath()%>/station_info.do?method=view&station_id=<%=(String) object.get("tid")%>"><%=(String) object.get("station_name")%>
				</a>
			</display:column>
			<display:column property="regionname" sortable="true" title="��������"
				headerClass="subject" />
			<display:column title="�������м�վ" headerClass="subject">
				<%
				                            if ("1".equals(object.get("is_ground_station"))) {
				                            out.print("��");
				                        }
				                        if ("0".equals(object.get("is_ground_station"))) {
				                            out.print("��");
				                        }
				%>
			</display:column>
			<display:column title="�е�" headerClass="subject">
				<%
				                            if ("1".equals(object.get("has_electricity"))) {
				                            out.print("��");
				                        }
				                        if ("0".equals(object.get("has_electricity"))) {
				                            out.print("��");
				                        }
				%>
			</display:column>
			<display:column title="�ܷ���е�" headerClass="subject">
				<%
				                            if ("1".equals(object.get("connect_electricity"))) {
				                            out.print("��");
				                        }
				                        if ("0".equals(object.get("connect_electricity"))) {
				                            out.print("��");
				                        }
				%>
			</display:column>
			<display:column title="�յ�" headerClass="subject">
				<%
				                            if ("1".equals(object.get("has_air_condition"))) {
				                            out.print("��");
				                        }
				                        if ("0".equals(object.get("has_air_condition"))) {
				                            out.print("��");
				                        }
				%>
			</display:column>
			<display:column title="�ܷ�ӿյ�" headerClass="subject">
				<%
				                            if ("1".equals(object.get("connect_air_condition"))) {
				                            out.print("��");
				                        }
				                        if ("0".equals(object.get("connect_air_condition"))) {
				                            out.print("��");
				                        }
				%>
			</display:column>
			<display:column title="�ɷ�ӷ�������" headerClass="subject">
				<%
				                            if ("1".equals(object.get("connect_wind_power_generate"))) {
				                            out.print("��");
				                        }
				                        if ("0".equals(object.get("connect_wind_power_generate"))) {
				                            out.print("��");
				                        }
				%>
			</display:column>
			<display:column media="html" title="����" headerClass="subject"
				>
				<apptag:checkpower thirdmould="73002">
					<a
						href="javascript:toUpdateForm('<%=(String) object.get("tid")%>');">�޸�</a>
					<logic:equal value="1" name="currentRowObject" property="is_allow">
					</logic:equal>
				</apptag:checkpower>
				<apptag:checkpower thirdmould="73003">
					<logic:equal value="1" name="currentRowObject" property="is_allow">
						<a
							href="javascript:toDeleteForm('<%=(String) object.get("tid")%>');">ɾ��</a>
					</logic:equal>
				</apptag:checkpower>
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			style="border:0px">
			<tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="����ΪExcel"
						onclick="exportList()" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
