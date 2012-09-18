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
		    BasicDynaBean basicDynaBean = (BasicDynaBean) request.getAttribute("mtUsedInfo");
		    List mtApproveList = (List) request.getAttribute("mtApproveList");
		%>
		<html:form method="post"
			action="/mtUsedAssessAction?method=mobileMtUsedAppoverForm">
			<input type="hidden" name="mtusedid"
				value="<%=basicDynaBean.get("id")%>" />
			<template:titile value="（移动审核）材料盘点申请审核" />
			<template:formTable namewidth="150" contentwidth="600">
				<template:formTr name="申请年月">
					<!-- 
		<a href="2.htm"><%//=basicDynaBean.get("createtime")%></a>（可链接到那个查看详细中）
		 -->
					<%=basicDynaBean.get("createtime")%>
				</template:formTr>
				<template:formTr name="代维">
					<%=basicDynaBean.get("contractorname")%>
				</template:formTr>
				<template:formTr name="申请人">
					<%=basicDynaBean.get("username")%>
				</template:formTr>
				<tr>
					<td colspan="2">
						<table width="100%">
							<tr>
								<td>
									材料名称
								</td>
								<td>
									上月库存
								</td>
								<td>
									本月新增
								</td>
								<td>
									本月使用
								</td>
								<td>
									系统盘点库存
								</td>
								<td>
									实际库存
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
					<td colspan="2" style="background-color: #FFFFFF;">
						请审核：
					</td>
				</tr>

				<template:formTr name="审核状态">
          	<input value="1" type="radio" name="state" checked>通过审批
          	<input value="0" type="radio" name="state">不予通过
				</template:formTr>
				<template:formTr name="备注">
					<textarea name="remark" cols="30" rows="5"></textarea>
				</template:formTr>
			</template:formTable>
			<template:formSubmit>
				<td>
					<html:submit property="action" styleClass="button">提交</html:submit>
				</td>
				<td>
				<!--  	<input type="button" value="返回" class="button" onclick="goBack()">  -->
				<input type="button" class="button" onclick="history.back()" value="返回"/>
				</td>
			</template:formSubmit>


		</html:form>
	</body>
</html>
