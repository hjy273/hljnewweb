<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>���ƶ���ˣ������̵��������</title>
	</head>
	<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
		type="text/css">
	<script type="text/javascript">
		goBack=function(){
		
			var url = '<%=(String) request.getSession().getAttribute("S_BACK_URL")%>';
		
			self.location.replace(url);
		}
		
		 toViewForm=function(idValue){
            	window.location.href = "${ctx}/mtUsedAssessAction.do?method=displayFinishMtUsed&id="+idValue;
		}
		
			function openwin(contractorid,currenttime){ 
		window.open("${ctx}/MonthStateAction.do?method=statMonth&contractorid="+contractorid+"&Month="+currenttime,"xx","height=600,width=800,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no"); 
//д��һ�� 
} 
		</script>
	<body>
		<%
			BasicDynaBean basicDynaBean = (BasicDynaBean) request
					.getAttribute("basicDynaBean");
			List mtApproveList = (List) request.getAttribute("finishList");
		%>
		<%
			BasicDynaBean approveBean = null;
			if (mtApproveList != null) {

				for (int i = 0; i < mtApproveList.size() - 1; i++) {
					approveBean = (BasicDynaBean) mtApproveList.get(i);
		%>

		<%
			}
			}
		%>
		<html:form method="post"
			action="/mtUsedAssessAction?method=mobileMtUsedAppoverForm">
			<input type="hidden" name="mtusedid"
				value="<%=basicDynaBean.get("id")%>" />
			<template:titile value="�����̵����������Ϣ" />
			<template:formTable namewidth="150" contentwidth="600">
				<template:formTr name="��������">
					<!-- <a href="javascript:openwin('<%=basicDynaBean.get("contractorid")%>','<%=basicDynaBean.get("createtime")%>');"> -->
					<%=basicDynaBean.get("createtime")%>
					<!-- </a>�������ӵ��Ǹ��鿴��ϸ�У� -->
				</template:formTr>
				<template:formTr name="��ά">
					<%=basicDynaBean.get("contractorname")%>
				</template:formTr>
				<template:formTr name="������">
					<%=basicDynaBean.get("username")%>
				</template:formTr>
				<template:formTr name="��ע">
					<%
						String applyremark = (String) basicDynaBean.get("remark");
						out.println(applyremark == null ? "" : applyremark);
					%>
				</template:formTr>
				<tr>
					<td colspan="2" style="background-color: #FFFFFF;">
						�����ʷ��
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<logic:notEmpty name="contrList">
								<tr class="title3">
									<td align="center">
										�������
									</td>
									<td>
										<table width="100%">
											<logic:iterate id="contrOne" name="contrList">
													<tr>
														<td>
															<logic:equal value="1" name="contrOne" property="state">
						    									ͨ��
						    								</logic:equal>
															<logic:equal value="0" name="contrOne" property="state">
						    									<font color="red">��ͨ��</font>
						    								</logic:equal>
														</td>
													</tr>
													<tr>
														<td>
															<bean:write name="contrOne" property="remark" />
														</td>
													</tr>
													<tr>
														<td align="right">
															<bean:write name="contrOne" property="username" />&nbsp;&nbsp;&nbsp;&nbsp;
															<bean:write name="contrOne" property="assessdate" />
														</td>
													</tr>
											</logic:iterate>

										</table>
									</td>
								</tr>
							</logic:notEmpty>
							<logic:notEmpty name="mobileList">
								<tr class="title3">
									<td align="center">
										�ƶ����
									</td>
									<td>
										<table width="100%">
											<logic:iterate id="mobileOne" name="mobileList">
													<tr>
														<td>
															<logic:equal value="1" name="mobileOne" property="state">
						    									ͨ��
						    								</logic:equal>
															<logic:equal value="0" name="mobileOne" property="state">
						    									<font color="red">��ͨ��</font>
						    								</logic:equal>
														</td>
													</tr>
													<tr>
														<td>
															<bean:write name="mobileOne" property="remark" />
														</td>
													</tr>
													<tr>
														<td align="right">
															<bean:write name="mobileOne" property="username" />&nbsp;&nbsp;&nbsp;&nbsp;
															<bean:write name="mobileOne" property="assessdate" />
														</td>
													</tr>
											</logic:iterate>
										</table>
									</td>
								</tr>
							</logic:notEmpty>

						</table>
					</td>
				</tr>

				<tr>
					<td colspan="2">
						������Ϣ��
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<tr class="title3">
								<td style="text-align: center; width: 100px;">
									��˾
								</td>
								<td style="text-align: center; width: 100px;">
									������
								</td>
								<td style="text-align: center; width: 150px;">
									��������
								</td>
								<td style="text-align: center; width: 300px;">
									���ձ�ע
								</td>
							</tr>
							<%
								if (mtApproveList != null) {

									approveBean = (BasicDynaBean) mtApproveList.get(mtApproveList
											.size() - 1);
							%>
							<tr>
								<td style="text-align: center;">
									<%=approveBean.get("contractorname")%>
								</td>

								<td style="text-align: center;">
									<%=approveBean.get("username")%>
								</td>
								<td style="text-align: center;">
									<%=approveBean.get("assessdate")%>
								</td>
								<td style="text-align: center;">
									<%
										String remark_1 = (String) approveBean.get("remark");
											out.println(remark_1 == null ? "" : remark_1);
									%>
								</td>
							</tr>
							<%
								}
							%>
						</table>
					</td>
				</tr>

			</template:formTable>
			<template:formSubmit>
				<td>
					<!--  <input type="button" value="����" class="button" onclick="goBack()">-->
					<input type="button" class="button" onclick="history.back()" value="����"/>
				</td>
			</template:formSubmit>


		</html:form>
	</body>
</html>
