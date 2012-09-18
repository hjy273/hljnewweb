<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>问卷汇总</title>
		<script type="text/javascript">
			toBackToStatList=function(issueId){
            	window.location.href = "${ctx}/questAction.do?method=questFeedbackStatList";
			}
		</script>
	</head>
	<body>
		<template:titile value="${issueName}"/>
		<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%" style="border: 0px;">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;"><b>问卷说明：</b></td>
				<td class="tdulright"><bean:write name="issueBean" property="remark" /></td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;vertical-align:top;"><b>问题：</b></td>
				<td class="tdulright">
					<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="100%" style="border: 0px;">
						<tr class="trcolor">
							<td colspan="2">
								<%int i = 0; %>
								<c:forEach items="${list}" var="questStatMap">
									<c:forEach items="${questStatMap}" var="questStat"} >
										<%i++; %>
										<div style="width:100%;height: 30px;line-height: 30px;"><b><%=i %>、${questStat.key }</b></div>
										<bean:define id="record" name="questStat" property="value"></bean:define>
										<table align="center" cellpadding="0" cellspacing="0" class="tabout" width="100%" border="1">
											<c:forEach items="${record}" var="recordValue">
												<tr>
													<c:forEach	items="${recordValue}" var="eachValue">
														<!-- ${fn:split(eachValue,';')[1] }:选择该选项的代维公司名称 -->
														<c:if test="${fn:split(eachValue,'^')[2]=='titleShow' }">
															<td width="100/${comNum }%" title="${fn:split(eachValue,'^')[0] }">
														</c:if>
														<c:if test="${fn:split(eachValue,'^')[2]!='titleShow' }">
															<td width="100/${comNum }%">
														</c:if>
															<c:if test="${fn:split(eachValue,'^')[0]=='bold' }">
																<b>${fn:split(eachValue,'^')[1]}</b>
															</c:if>
															<c:if test="${fn:split(eachValue,'^')[0]!='bold' }">
																${fn:split(eachValue,'^')[1]}
															</c:if>
														</td>
													</c:forEach>
												</tr>
											</c:forEach>
										</table>
									</c:forEach>
								</c:forEach>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br/>
		<div align="center" style="height:40px">
			<html:button property="button" styleClass="button" onclick="toBackToStatList()">返回</html:button>
		</div>
	</body>
</html>
