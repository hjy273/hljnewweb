<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<c:if test="${sessionScope.FAULT_AUDIT_HISTORY_MAP!=null}">
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			class="tabout">
			<c:forEach var="oneProcessMap"
				items="${sessionScope.FAULT_AUDIT_HISTORY_MAP}">
				<c:set var="processKey" value="${oneProcessMap.key}"></c:set>
				<c:set var="processList" value="${oneProcessMap.value}"></c:set>
				<c:if test="${processKey!=null}">
					<thead>
						<tr>
							<th class="tdl" style="width: 20%; padding: 5px;">
								${processKey }
							</th>
							<th class="tdc" style="width: 80%; padding: 5px;">
							</th>
						</tr>
					</thead>
					<!-- 
					<tr class=trcolor>
						<td class="tdl" style="width: 100%; padding: 5px;">
							${processKey }
						</td>
					</tr>
					<tr>
						<td class="tdc" style="width: 100%; padding: 3px;">
							<table width="100%" class="tabout">
					 -->
					<c:forEach var="oneProcess" items="${oneProcessMap.value}">
						<c:if test="${oneProcess.isAuditing!=null }">
							<tr>
								<td class="tdr" style="width: 20%; padding: 5px;">
									${processKey }结果：
								</td>
								<td class="tdl" style="width: 80%; padding: 5px;">
									${oneProcess.isAuditing }
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="tdr" style="width: 20%; padding: 5px;">
								${processKey }备注：
							</td>
							<td class="tdl" style="width: 80%; padding: 5px;">
								${oneProcess.remark }
							</td>
						</tr>
						<tr>
							<td class="tdr" colspan="2" align="right"
								style="width: 100%; padding: 5px;">
								${processKey }人： ${oneProcess.auditor } ${processKey }时间：
								&nbsp;&nbsp;&nbsp;&nbsp; ${oneProcess.auditingTimeString }
							</td>
						</tr>
					</c:forEach>
					<!-- 
							</table>
						</td>
					</tr>
					 -->
				</c:if>
			</c:forEach>
		</table>
	</c:if>
	<logic:empty name="FAULT_AUDIT_HISTORY_MAP" scope="session">
		没有可显示的内容。
	</logic:empty>
</html>
