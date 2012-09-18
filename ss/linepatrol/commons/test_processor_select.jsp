<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/common/header.jsp"%>
<form>
	<apptag:processorselect inputName="contractorId,userId,mobileId" spanId="userSpan" />
	<apptag:processorselect inputName="contractorId,userId,mobileId" inputType="radio" spanId="userSpan" />
	<apptag:processorselect inputName="contractorId,userId,mobileId" displayType="mobile" spanId="userSpan" />
	<apptag:processorselect inputName="contractorId,userId,mobileId" displayType="mobile_contractor" spanId="userSpan" />
</form>
