<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>（移动审核）材料盘点申请审核</title>
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
//写成一行 
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
			<template:titile value="材料盘点申请审核信息" />
			<template:formTable namewidth="150" contentwidth="600">
				<template:formTr name="申请年月">
					<!-- <a href="javascript:openwin('<%=basicDynaBean.get("contractorid")%>','<%=basicDynaBean.get("createtime")%>');"> -->
					<%=basicDynaBean.get("createtime")%>
					<!-- </a>（可链接到那个查看详细中） -->
				</template:formTr>
				<template:formTr name="代维">
					<%=basicDynaBean.get("contractorname")%>
				</template:formTr>
				<template:formTr name="申请人">
					<%=basicDynaBean.get("username")%>
				</template:formTr>
				<template:formTr name="备注">
					<%
						String applyremark = (String) basicDynaBean.get("remark");
						out.println(applyremark == null ? "" : applyremark);
					%>
				</template:formTr>
				<tr>
					<td colspan="2" style="background-color: #FFFFFF;">
						审核历史：
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<logic:notEmpty name="contrList">
								<tr class="title3">
									<td align="center">
										监理审核
									</td>
									<td>
										<table width="100%">
											<logic:iterate id="contrOne" name="contrList">
													<tr>
														<td>
															<logic:equal value="1" name="contrOne" property="state">
						    									通过
						    								</logic:equal>
															<logic:equal value="0" name="contrOne" property="state">
						    									<font color="red">不通过</font>
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
										移动审核
									</td>
									<td>
										<table width="100%">
											<logic:iterate id="mobileOne" name="mobileList">
													<tr>
														<td>
															<logic:equal value="1" name="mobileOne" property="state">
						    									通过
						    								</logic:equal>
															<logic:equal value="0" name="mobileOne" property="state">
						    									<font color="red">不通过</font>
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
						验收信息：
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<tr class="title3">
								<td style="text-align: center; width: 100px;">
									公司
								</td>
								<td style="text-align: center; width: 100px;">
									验收人
								</td>
								<td style="text-align: center; width: 150px;">
									验收日期
								</td>
								<td style="text-align: center; width: 300px;">
									验收备注
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
					<!--  <input type="button" value="返回" class="button" onclick="goBack()">-->
					<input type="button" class="button" onclick="history.back()" value="返回"/>
				</td>
			</template:formSubmit>


		</html:form>
	</body>
</html>
