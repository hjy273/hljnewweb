<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<%
		    UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		    String deptid = user.getDeptID();
		%>

		<title>�����̵�����</title>
		<script type="text/javascript"
			src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
			type="text/css">
		<link type="text/css" rel="stylesheet"
			href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<style>
table {
	font-size: 12px;
}
</style>

		<script type="text/javascript">
		goBack=function(){
			var url = '<%=(String) request.getSession().getAttribute("S_BACK_URL")%>';
			self.location.replace(url);
		}
		
			function openwin(contractorid,cuurenttime){ 
		window.open("${ctx}/MonthStateAction.do?method=statMonth&contractorid="+contractorid+"&Month="+cuurenttime,"xx","height=600,width=800,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no"); 
//д��һ�� 
}
		function getMarterialInfo(mtid){
			    var URL = "${ctx}/materialUsedInfoAction.do?method=getMarterialInfos&act=view&material_id="+mtid;
		       	myleft=(screen.availWidth-500)/2;
				mytop=260;
				mywidth=500;
				myheight=320;
		        window.open(URL,"open","height="+myheight+",width="+mywidth+",status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=no");
		}
 
		</script>

	</head>

	<body>
		<template:titile value="�����̵�����" />
		<html:form method="post"
			action="/mtUsedAction?method=doMtUsedEditForm"
			styleId="doMtUsedEditForm">
			<template:formTable namewidth="150" contentwidth="600">
				<template:formTr name="��������">
					<input type="hidden" name="id"
						value='<bean:write name="disBean" property="id"/>' />
					<bean:write name="disBean" property="createtime" />
				</template:formTr>
				<tr>
					<td colspan="2">
						<table width="100%">
							<tr>
								<td>
									��������
								</td>
								<td>
									���¿��
								</td>
								<td>
									��������
								</td>
								<td>
									����ʹ��
								</td>
								<td>
									ϵͳ�̵���
								</td>
								<td>
									ʵ�ʿ��
								</td>
							</tr>
							<%
							    int i = 0;
							%>
							<logic:iterate id="oneMap" name="detail_storage_map">
								<tr>
									<td>
										<input id="materialId" name="materialId" type="hidden"
											value="<bean:write name="oneMap" property="key" />" />
										<bean:define id="mtid" name="oneMap" property="key" ></bean:define>
										<bean:write name="oneMap" property="value.material_name" />
									</td>
									<td>
										<input id="lastMonthStock" name="lastMonthStock" type="hidden"
											value="<bean:write name="oneMap" property="value.last_month_storage" />" />
										<bean:write name="oneMap" property="value.last_month_storage" />
									</td>
									<td>
										<input id="newAddedStock" name="newAddedStock" type="hidden"
											value="<bean:write name="oneMap" property="value.storage_number" />" />
										<bean:write name="oneMap" property="value.storage_number" />
									</td>
									<td>
										<input id="newUsedStock" name=newUsedStock "" type="hidden"
											value="<bean:write name="oneMap" property="value.used_number" />" />
										<bean:write name="oneMap" property="value.used_number" />
									</td>
									<td>
										<bean:write name="oneMap" property="value.have_number" />
									</td>
									<td>
										<span id="real_stock_<%=i%>">
										    <a href="javascript:getMarterialInfo('<%=mtid%>')">
										    	<bean:write name="oneMap" property="value.real_number" /></a></span>
									</td>
								</tr>
								<%
								    i++;
								%>
							</logic:iterate>
						</table>
					</td>
				</tr>
				<template:formTr name="��ע">
					<bean:write name="disBean" property="remark" />
				</template:formTr>
				<template:formSubmit>
					<!-- 
					<td>
						<input type="button" class="button" value="�鿴������Ϣ"
							onclick="openwin('<%//=deptid%>','<bean:write name="disBean" property="createtime"/>');" />
					</td>
					 -->
					<td>
						<!-- <html:button property="action" styleClass="button"
							onclick="goBack();">����</html:button> -->
							<input type="button" class="button" onclick="history.back()" value="����"/>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
