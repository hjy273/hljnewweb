<%@include file="/wap/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�����ܽ����</title>
		<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<script type="text/javascript">
			function checkForm(){
				if(getTrimValue("operator")=="transfer"){
					if(getTrimValue("approvers").length==0){
						alert("ת���˲���Ϊ�գ�");
						return false;
					}
					return true;
				}
				return true;
			}
			function getTrimValue(value){
				return document.getElementById(value).value.trim();
			}
			String.prototype.trim = function() {
				return this.replace(/^\s+|\s+$/g,"");
			}
	goBack = function() {
		var url = "${sessionScope.S_BACK_URL}";
		location = url;
	}
		</script>
	</head>
	<body>
		<c:if test="${operator=='approve'}">
			<template:titile value="�����ܽ����" />
		</c:if>
		<c:if test="${operator=='transfer'}">
			<template:titile value="�����ܽ�ת��" />
		</c:if>
		<html:form
			action="/wap/drillSummaryAction.do?method=approveDrillSummary"
			onsubmit="return checkForm()" styleId="submitForm1"
			enctype="multipart/form-data">
			<input type="hidden" name="env" value="${env }" />
			<input type="hidden" name="id"
				value="<c:out value='${drillSummary.id }' />" />
			<input type="hidden" name="creator"
				value="<c:out value='${drillSummary.creator }' />" />
			<input type="hidden" name="operator"
				value="<c:out value='${operator }'/>" />
			<p>
				<jsp:include page="drill_summary_wap_base.jsp"></jsp:include>
			</p>
			<c:if test="${operator=='approve'}">
				<p>
					<c:if test="${drillSummary.createTime>drillPlan.deadline}">
						<font color="red">�����ܽ��ύ��ʱ����ʱ<bean:write name="days"
								format="#.##" />Сʱ</font>
					</c:if>
					<c:if test="${drillSummary.createTime<=drillPlan.deadline}">
						<font color="blue">�����ܽ��ύ��ǰ����ǰ<bean:write name="days"
								format="#.##" />Сʱ</font>
					</c:if>
				</p>
				<p>
					��˽����
					<input name="approveResult" value="1" type="radio" checked />
					ͨ�����
					<input name="approveResult" value="0" type="radio" />
					δͨ�����
					<br />
					��˱�ע��
					<html:textarea property="approveRemark" title="" style="width:80%"
						rows="5" styleClass="textarea"></html:textarea>
				</p>
				<p>
					<html:submit property="action">���</html:submit>
					<html:reset property="action">��д</html:reset>
					<html:button property="button" onclick="goBack();">���ش���</html:button>
				</p>
			</c:if>
			<c:if test="${operator=='transfer'}">
				<p>
					ת���ˣ�
					<c:if test="${sessionScope.APPROVER_INPUT_NAME_MAP!=null}">
						<c:forEach var="oneItem"
							items="${sessionScope.APPROVER_INPUT_NAME_MAP}">
							<c:if test="${oneItem.key!='span_value'}">
								<input name="${oneItem.key }" value="${oneItem.value }"
									type="hidden" />
							</c:if>
							<c:if test="${oneItem.key=='span_value'}">
							${oneItem.value }
						</c:if>
						</c:forEach>
					</c:if>
					<br />
					ת��˵����
					<input name="approveResult" value="2" type="hidden" />
					<html:textarea property="approveRemark" title="" style="width:80%"
						rows="5" styleClass="textarea"></html:textarea>
				</p>
				<p>
					<html:submit property="action">ת��</html:submit>
					<html:button property="button" onclick="goBack();">���ش���</html:button>
				</p>
			</c:if>
		</html:form>
	</body>
</html>
