<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/common/header.jsp"%>
<form>
	<apptag:approverselect label="�����" inputName="approver"
		spanId="approverSpan" inputType="radio" />
	<apptag:approverselect label="������" inputName="reader"
		spanId="readerSpan" notAllowName="approver" />
</form>
