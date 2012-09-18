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
        	if(confirm("你确定要执行此次删除操作吗?")){
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

		<template:titile value="人员证书一览表" />
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="18" export="true">
			<display:column property="contractorname" title="代维单位" maxLength="18" />
			<display:column property="contractorpersonname" title="代维人员"
				maxLength="18" />
			<display:column property="certificatecode" title="证书编号"
				maxLength="20" />
			<display:column property="certificatename" title="证书名称"
				maxLength="25" />
			<display:column property="licenceissuingauthority" title="发证机关"
				maxLength="25" />
			<display:column property="certificatetypename" title="证书类型"
				maxLength="25" />
			<display:column property="issauthoritydate" title="发证时间"
				maxLength="25" format="{0,date,yyyy/MM/dd}" />
			<display:column property="validperiod" title="有效期" maxLength="12" />

			<display:column media="html" title="操作">
				<%
					BasicDynaBean object = (BasicDynaBean) pageContext
							.findAttribute("currentRowObject");
					String id = (String) object.get("id");
				%>
				<a href="javascript:toGetForm('<%=id%>')">详细</a>
			</display:column>
				<display:column media="html" title="操作">
					<%
						BasicDynaBean object1 = (BasicDynaBean) pageContext
								.findAttribute("currentRowObject");
						String id1 = (String) object1.get("id");
					%>
					<a href="javascript:toUpForm('<%=id1%>')">修改</a>
				</display:column>
				<display:column media="html" title="操作">
					<%
						BasicDynaBean object2 = (BasicDynaBean) pageContext
								.findAttribute("currentRowObject");
						String id2 = (String) object2.get("id");
					%>
					<a href="javascript:toDeletForm('<%=id2%>')">删除</a>
				</display:column>
		</display:table>
		<!-- 
		<html:link action="/PersonCertificateAction.do?method=exportCertificate">导出为Excel文件</html:link>
		 -->
	</body>
</html>


