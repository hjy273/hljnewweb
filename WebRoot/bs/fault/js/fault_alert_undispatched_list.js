/**
 * fault_alert_undispatched_list.js
 * 
 * @creator yangjun 2011-11-01
 * @used for fault_alert_undispatched_list.jsp
 */
/**
 * dispatch fault
 */
function dispatch(id, businessType) {
	window.location.href = contextPath
			+ '/faultDispatchAction_input.jspx?parameter.id=' + id
			+ '&businessType=' + businessType + "&findType=B16";
}
/**
 * ignore fault
 */
function ignore(id, businessType) {
	window.location.href = contextPath
			+ '/faultAlertAction_ignore.jspx?parameter.id=' + id
			+ '&businessType=' + businessType;
}