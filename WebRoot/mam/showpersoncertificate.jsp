<%@ page language="java" contentType="text/html; charset=GBK"%><%@include
	file="/common/header.jsp"%>

<jsp:useBean id="selectForTag"
	class="com.cabletech.commons.tags.SelectForTag" scope="request" />
<html>
	<head>
		<script type="" language="javascript">
        function toGetForm(idValue){
            var url = "${ctx}/PersonCertificateAction.do?method=showOneCertificate&id=" + idValue;
            self.location.replace(url);
        }
        function addGoBack()
        {
            try
			{
                location.href = "${ctx}/PersonCertificateAction.do?method=showCertificate";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
        function toUpForm(idValue){
            var url =  "${ctx}/PersonCertificateAction.do?method=showUp&id=" + idValue;
            self.location.replace(url);
        }

        function toDeletForm(idValue){
        	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
            	var url =  "${ctx}/PersonCertificateAction.do?method=deleteInfo&id=" + idValue;
            	self.location.replace(url);
        	}
        	else
        		return ;
        }
    </script>

		<title>partBaseInfo</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
	</head>
	<body>

		<template:titile value="��Ա֤��һ����" />
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="18" export="true">
			<display:column property="contractorname" title="��ά��λ" maxLength="18" />
			<display:column property="contractorpersonname" title="��ά��Ա"
				maxLength="18" />
			<display:column property="certificatecode" title="֤����"
				maxLength="20" />
			<display:column property="certificatename" title="֤������"
				maxLength="25" />
			<display:column property="licenceissuingauthority" title="��֤����"
				maxLength="25" />
			<display:column property="certificatetypename" title="֤������"
				maxLength="25" />
			<display:column property="issauthoritydate" title="��֤ʱ��"
				maxLength="25" format="{0,date,yyyy/MM/dd}" />
			<display:column property="validperiod" title="��Ч��" maxLength="12" />

			<display:column media="html" title="����">
				<%
					BasicDynaBean object = (BasicDynaBean) pageContext
							.findAttribute("currentRowObject");
					String id = (String) object.get("id");
				%>
				<a href="javascript:toGetForm('<%=id%>')">��ϸ</a>
			</display:column>
				<display:column media="html" title="����">
					<%
						BasicDynaBean object1 = (BasicDynaBean) pageContext
								.findAttribute("currentRowObject");
						String id1 = (String) object1.get("id");
					%>
					<a href="javascript:toUpForm('<%=id1%>')">�޸�</a>
				</display:column>
				<display:column media="html" title="����">
					<%
						BasicDynaBean object2 = (BasicDynaBean) pageContext
								.findAttribute("currentRowObject");
						String id2 = (String) object2.get("id");
					%>
					<a href="javascript:toDeletForm('<%=id2%>')">ɾ��</a>
				</display:column>
		</display:table>
		<!-- 
		<html:link action="/PersonCertificateAction.do?method=exportCertificate">����ΪExcel�ļ�</html:link>
		 -->
	</body>
</html>


