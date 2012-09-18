<script type="text/javascript"  src="/WebApp/linepatrol/js/appraise_daily.js"></script>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/wap/header.jsp"%>
<html>
	<head></head>
	<script type="text/javascript">
		function checkAddInfo() {
  			saveEvaluate.submit();
  		}
	</script>
	<body>
		<div>
			<a class="dept1">${LOGIN_USER.userName} 您好！</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">功能导航</a><a class="dept"
				href="javascript:exitSys();">退出</a><br /><br/>
		</div>
		<html:form action="/wap/appraiseDailyAction.do?method=saveDaily" styleId="saveEvaluate">
		<input type="hidden" id="ruleIds" name="ruleIds" />
		<input type="hidden" id="remarks" name="remarks" />
		<input type="hidden" name="tableId" value="${tableId}" />
		<input type="hidden" name="businessModule" value="other" />
		<input type="hidden" name="businessId" value="" />
		<input type="hidden" name="appraiser" value="${LOGIN_USER.userName}" />
		<input type="hidden" name="contractorId" value="${contractorId }"/>
		代维公司 :${contractorName}&nbsp;&nbsp;&nbsp;&nbsp;中标合同号 :
		<html:select property="contractNo">
			<c:forEach var="contractNo" items="${contractNoArray}">
				<html:option value="${contractNo}">${contractNo}</html:option>
			</c:forEach>
		</html:select>
		<br />
		<c:forEach var="item" items="${appraiseTable.appraiseItems}">
			${item.itemName}（${item.weight}分）<br/>
			<c:forEach var="content" items="${item.appraiseContents}">
				&nbsp;&nbsp;${content.contentDescription}（${content.weight }分）<br/>
				<c:forEach var="rule" items="${content.appraiseRules}">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="checkbox" name="ruleCheckbox" onclick="setTextWriteState()" value="${rule.id}"/>${rule.ruleDescription}（${rule.weight }分）<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="text" readonly="readonly" id="${rule.id}"/><br/>
				</c:forEach>
			</c:forEach>
		</c:forEach>
		<div align="center">
		<html:button property="action"  styleId="subbtn"
							onclick="checkAddInfo()">提交</html:button>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="back" value="返回" onchange="history.back()"/>
		</div>
		</html:form>
	</body>
</html>
