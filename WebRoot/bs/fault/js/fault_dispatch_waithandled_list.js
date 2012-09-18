/**
 * fault_dispatch_waithandled_list.js
 * 
 * @creator yangjun 2011-11-01
 * @used for fault_dispatch_waithandled_list.jsp
 */
/**
 * reply fault
 */
function reply(id, taskId, businessType) {
	window.location.href = contextPath
			+ '/faultReplyAction_input.jspx?parameter.id=' + id
			+ '&faultReply.workflowTaskId=' + taskId + '&businessType='
			+ businessType;
}
/**
 * audit fault reply
 */
function audit(id, taskId, businessType) {
	window.location.href = contextPath
			+ '/faultAuditAction_input.jspx?parameter.id=' + id
			+ '&faultAudit.workflowTaskId=' + taskId + '&businessType='
			+ businessType;
}
/**
 * goto doing task page
 */
function toDoTaskForm(url, businessType) {
	window.location.href = contextPath + "/" + url + '&businessType='
			+ businessType;
}