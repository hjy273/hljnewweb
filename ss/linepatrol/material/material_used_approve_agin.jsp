<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�����̵���������</title>
	</head>
	<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
		type="text/css">
	<script type="text/javascript">
  		
  		function openwin(contractorid,htime){ 
		window.open("${ctx}/MonthStateAction.do?method=statMonth&contractorid="+contractorid+"&Month="+htime,"xx","height=600,width=800,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no"); 
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

	<body>
		<%
					BasicDynaBean basicDynaBean = (BasicDynaBean) request
					.getAttribute("basicDynaBean");
			List mtApproveList = (List) request.getAttribute("mtApproveList");
			int mtApproveCount = mtApproveList.size();
			List ydApproveList = (List) request.getAttribute("ydApproveList");
			String currenttime = (String) basicDynaBean.get("createtime");
		%>
		<html:form action="/mtUsedAssessAction?method=addMtUsedAppoverForm"
			styleId="addMtUsedApplyForm" method="post">
			<input type="hidden" name="mtusedid"
				value="<%=basicDynaBean.get("id")%>" />
			<template:titile value="��������ˣ������̵���������" />
			<template:formTable namewidth="150" contentwidth="600">
				<template:formTr name="��������">
					<!-- 
					<a
						href="javascript:openwin('<%=(String) basicDynaBean.get("contractorid")%>','<%=currenttime%>');"><%=currenttime%>
					</a>�������ӵ��Ǹ��鿴��ϸ�У�
					 -->
					<%=currenttime%>
				</template:formTr>
				<template:formTr name="��ά">
					<%=basicDynaBean.get("contractorname")%>
				</template:formTr>
				<template:formTr name="������">
					<%=basicDynaBean.get("username")%>
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
							    int j = 0;
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
										<span id="real_stock_<%=j%>"><a href="javascript:getMarterialInfo('<%=mtid%>')"><bean:write name="oneMap"
												property="value.real_number" /></a> </span>
									</td>
								</tr>
								<%
								    j++;
								%>
							</logic:iterate>
						</table>
					</td>
				</tr>
				<template:formTr name="��ע">
					<%
						String remark = (String) basicDynaBean.get("remark");
						out.println(remark == null ? "" : remark);
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
					<td colspan="2" style="background-color:#FFFFFF;">
						������
					</td>
				</tr>

				<template:formTr name="���״̬">
					<select name="state">
						<option value="1" selected>
							ͨ��
						</option>
						<option value="0">
							��ͨ��
						</option>
					</select>
				</template:formTr>
				<template:formTr name="��ע">
					<textarea name="remark" cols="30" rows="5"></textarea>
				</template:formTr>
			</template:formTable>
			<template:formSubmit>
				<td>
					<html:submit property="action" styleClass="button">�ύ</html:submit>
				</td>
				<td>
					<!--  <input type="button" class="button"
						onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"
						value="����">
						-->
						<input type="button" class="button" onclick="history.back()" value="����"/>
				</td>

			</template:formSubmit>
		</html:form>
	</body>
	</htmlt>