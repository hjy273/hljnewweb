<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/common/header.jsp"%>
<form>
	<apptag:approverselect label="ÉóºËÈË" inputName="approver"
		spanId="approverSpan" inputType="radio" />
	<apptag:approverselect label="³­ËÍÈË" inputName="reader"
		spanId="readerSpan" notAllowName="approver" />
</form>
