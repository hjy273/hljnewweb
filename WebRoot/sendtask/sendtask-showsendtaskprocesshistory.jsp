<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="cabletechtag" prefix="apptag"%>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<html>
	<c:if test="${send_task_process_history_map!=null}">
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			class="tabout">
			<c:forEach var="oneProcessMap"
				items="${send_task_process_history_map}">
				<c:set var="processKey" value="${oneProcessMap.key}"></c:set>
				<c:set var="processList" value="${oneProcessMap.value}"></c:set>
				<c:if test="${processKey!=null}">
					<tr class=trcolor>
						<td class="tdl" style="width: 100%; padding: 5px;">
							${processKey }
						</td>
					</tr>
					<tr>
						<td class="tdc" style="width: 100%; padding: 3px;">
							<table width="100%" class="tabout">
								<c:forEach var="oneProcess" items="${oneProcessMap.value}">
									<c:if test="${oneProcess.processResult!=null }">
										<tr>
											<td class="tdl" style="width: 100%; padding: 5px;">
												${oneProcess.processResult }
											</td>
										</tr>
									</c:if>
									<tr>
										<td class="tdl" style="width: 100%; padding: 5px;">
											${oneProcess.processRemark }
										</td>
									</tr>
									<tr>
										<td class="tdl" style="width: 100%; padding: 5px;">
											<apptag:upload cssClass="" entityId="${id}"
												entityType="${oneProcess.processAttachType}" state="look" />
										</td>
									</tr>
									<tr>
										<td class="tdr" align="right"
											style="width: 100%; padding: 5px;">
											${processKey }»À£∫ ${oneProcess.processUser } ${processKey } ±º‰£∫
											&nbsp;&nbsp;&nbsp;&nbsp; ${oneProcess.processTime }
										</td>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
	</c:if>
</html>
