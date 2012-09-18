
<%@page import="org.springframework.web.context.request.SessionScope"%>
<%@page import="org.apache.commons.lang.StringUtils"%><%@include file="/common/header.jsp"%>
<%@page import="java.util.Date" %>
<%@page import="com.cabletech.commons.util.DateUtil" %>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<script type='text/javascript'
			src='${ctx}/linepatrol/js/change_style.js'></script>
		<script type="text/javascript">
		function check(){
			if( ($('begintime').value == '' && $('endtime').value != '') ||
				($('begintime').value != '' && $('endtime').value == '') ){
				alert("日期段必须同时有开始日期和结束日期");
				return false;
			}
			if( ($('begintime').value != '' && $('endtime').value != '')
					&& ($('begintime').value == $('endtime').value) ){
				alert('开始日期和结束日期不能相等');
				return false;
			}
			return true;
		}
		function isAcceptanceState(state){
			if(state=='1'){
				document.getElementById("pass").style.display="";
			}else{
				document.getElementById("pass").style.display="none";
			}
		}
	</script>
	<style type="text/css">
		.Content{ border:1px #000000 solid;}

		.table_1{  border:1px #000000 solid;}
		.table_1_tr1{ border-bottom:1px #000000 solid;}
		
		.table_2{  border:0px #000000 solid;}
		.table_2 td{ border-bottom:1px #000000 solid; border-right:1px #000000 solid;}
		.table_2_td{ border-top:1px #000000 solid; border-left:1px #000000 solid; }
		.table_2_left{border-left:1px #000000 solid; }
	</style>
	</head>
	<body onload="changeStyle()">
		<template:titile value="管道统计" />
		<!-- 查询页面 -->
		<html:form action="/acceptancePipeStatAction.do?method=pipeStatList"
			styleId="form" onsubmit="return check()">
			<template:formTable namewidth="150" contentwidth="400">
				<template:formTr name="项目名称">
					<html:text property="projectName" styleClass="inputtext"
						style="width:120px" />
				</template:formTr>
				<template:formTr name="项目经理">
					<html:text property="pcpm" styleClass="inputtext"
						style="width:120px" />
				</template:formTr>
				<template:formTr name="验收情况">
				<html:radio property="isRecord" value="" onclick="isAcceptanceState(this.value);" >全部</html:radio>
				<html:radio property="isRecord" value="0" onclick="isAcceptanceState(this.value);">未验收</html:radio>
				<html:radio property="isRecord" value="1" onclick="isAcceptanceState(this.value);">验收</html:radio>
			</template:formTr>
			<tbody id="pass" style="display:none">
			<template:formTr name="是否通过">
				<html:radio property="ispassed" value="">全部</html:radio>
				<html:radio property="ispassed" value="1">通过</html:radio>
				<html:radio property="ispassed" value="0">未通过</html:radio>
			</template:formTr>
			</tbody>
				<template:formTr name="管道属性">
					<apptag:quickLoadList cssClass="input" style="width:200px"
						name="pipeType" listName="pipe_type" type="select" isQuery="true" />
				</template:formTr>
				<template:formTr name="产权属性">
					<apptag:quickLoadList cssClass="input" style="width:200px"
						name="pipeProperty" listName="property_right" type="select"
						isQuery="true" />
				</template:formTr>
				<template:formTr name="代维公司" isOdd="false">
					<apptag:setSelectOptions valueName="connCollection"
						tableName="contractorinfo" columnName1="contractorname"
						region="true" columnName2="contractorid" order="contractorname" />
					<html:select property="contractorid" styleClass="inputtext"
						styleId="rId" style="width:200px">
						<html:option value="">不限</html:option>
						<html:options collection="connCollection" property="value"
							labelProperty="label" />
					</html:select>
				</template:formTr>
				<template:formTr name="验收时间">
					<html:text property="begintime" styleClass="Wdate"
						style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})" /> 至
				<html:text property="endtime" styleClass="Wdate" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})" />
				</template:formTr>
			</template:formTable>
			<div align="center">
				<input name="isQuery" value="true" type="hidden">
				<html:submit property="action" styleClass="button">查 询</html:submit>
			</div>
		</html:form>
		<template:displayHide styleId="form"></template:displayHide>
		
		<logic:notEmpty name="list">
			<!-- 查询结果 -->
			<%
				List<Object> list = (List<Object>)request.getSession().getAttribute("list");
				//统计总体信息
				List<Object> totalList = (List<Object>)list.get(0);
				//详细信息列表
				List<Object> detailList = (List<Object>)list.get(1);
				request.setAttribute("totalList", totalList);
				request.setAttribute("detailList", detailList);
			%>
			<table cellspacing="0" cellpadding="0" class="Content">
				<tr>
					<td style="padding: 0px; margin: 0px;">
						<table cellspacing="0" cellpadding="0" style="border: 0px;" class="table_1">
							<tr>
								<td colspan="5" class="table_1_tr1">
									<b>总体信息</b>
								</td>
							</tr>
							<tr>
								<td class="table_1_tr1">
									项目数量：<%=totalList.get(0) %>
								</td>
								<td class="table_1_tr1">
									验收通过数量：<%=totalList.get(1) %>
								</td>
								<td class="table_1_tr1">
									验收通过率：<%=totalList.get(2) %> %
								</td>
								<td class="table_1_tr1">
									沟公里数：<%=totalList.get(3) %>
								</td>
								<td class="table_1_tr1">
									孔公里数：<%=totalList.get(4) %>
								</td>
							</tr>
							<tr>
								<td class="table_1_tr1">
									沟公里通过率：<%=totalList.get(5) %> %
								</td>
								<td class="table_1_tr1" colspan="4">
									孔公里通过率：<%=totalList.get(6) %> %
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding: 0px; margin: 0px;">
						<%
							for(int i = 0; i < detailList.size(); i++) {
								List<Object> oneDetailList = (List<Object>)detailList.get(i);
								//详细信息的统计
								DynaBean detailStat = (DynaBean)oneDetailList.get(0);
								//管道详细信息列表
								List<DynaBean> detailInfoList = (List<DynaBean>)oneDetailList.get(1);
								request.setAttribute("detailStat", detailStat);
								request.setAttribute("detailInfoList", detailInfoList);
						%>
							<table cellspacing="0" cellpadding="0" style="border: 0px;" class="table_1_tr1">
								<tr>
									<td colspan="5" class="table_1_tr1">
										<b><bean:write name="detailStat" property="name"/></b>
									</td>
								</tr>
								<tr>
									<td class="table_1_tr1">
										项目经理：<bean:write name="detailStat" property="pcpm"/>
									</td>
									<td class="table_1_tr1">
										是否通过验收：<bean:write name="detailStat" property="ispassed"/>
									</td>
									<td class="table_1_tr1">
										申请验收时间：<bean:write name="detailStat" property="applydate"/>
									</td>
									<td class="table_1_tr1">
										合格数量：<bean:write name="detailStat" property="ispassednum"/>
									</td>
									<td class="table_1_tr1">
										未合格数量：<bean:write name="detailStat" property="unpassednum"/>
									</td>
								</tr>
								<tr>
									<td class="table_1_tr1">
										沟公里数：<bean:write name="detailStat" property="pitchlength"/>
									</td>
									<td class="table_1_tr1" colspan="4">
										孔公里数：<bean:write name="detailStat" property="holelength"/>
									</td>
								</tr>
								<tr>
									<td colspan="5" style="padding: 0px; margin: 0px;">
										<table cellspacing="0" cellpadding="0" style="border: 0px;" class="table_2">
											<tr>
												<td>管道地点</td>
												<td>产权属性</td>
												<td>管道属性</td>
												<td>详细路由</td>
												<td>管道规模</td>
												<td>计划时间</td>
												<td>验收时间</td>
												<td>验收代维</td>
												<td>是否验收</td>
												<td>验收情况</td>
											</tr>
											<c:forEach items="${detailInfoList}" var="detail">
											<%		
													DynaBean detail= (DynaBean)pageContext.findAttribute("detail");
													String isrecord = (String)detail.get("isrecord");
													request.setAttribute("isrecord", isrecord);
													String flag="a";
													String planDate=(String)detail.get("plan_date");
													String applyDate=(String)detail.get("apply_date");
													if(StringUtils.isNotBlank(planDate)&&StringUtils.isNotBlank(applyDate)){
													Date plan=DateUtil.strToDate(planDate);
													Date apply=DateUtil.strToDate(applyDate);
													if(apply.before(plan)){
														flag="p";
													}
													}
													request.setAttribute("flag",flag);
													%>
												<tr>
													<td>
														<c:if test="${flag=='a'}">
															<font color="red">
														</c:if> 
														<bean:write name="detail" property="pipe_address"/>
															<c:if test="${flag=='a'}">
															</font>
														</c:if> 
														</td>
													<td><bean:write name="detail" property="pipe_property"/></td>
													<td><bean:write name="detail" property="pipe_type"/></td>
													<td><bean:write name="detail" property="pipe_route"/></td>
													<td><bean:write name="detail" property="gdgm"/></td>
													<td><bean:write name="detail" property="plan_date"/></td>
													<td>
														<c:if test="${flag=='a'}">
															<font color="red">
														</c:if> 
														<bean:write name="detail" property="apply_date"/>
															<c:if test="${flag=='a'}">
															</font>
														</c:if> 
													</td>
													<td><bean:write name="detail" property="contractorid"/></td>
													<td><bean:write name="detail" property="isrecord"/></td>
													<td><c:if test="${isrecord=='验收'}"><bean:write name="detail" property="ispassed"/></c:if>
														<c:if test="${isrecord!='验收'}">无</c:if>
													</td>
												</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
								<tr><td height="20"></td></tr>
							</table>
						<%
							}
						%>
					</td>
				</tr>
			</table>
		</logic:notEmpty>
		
		<!-- 查询列表 -->
		<logic:notEmpty name="list">
			<!-- 查询结果 -->
			<table class="tdlist">
				<tr>
					<td class="tdlist">
						<input type="button" class="button" value="返回"
							onclick="history.back()" />
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>
</html>