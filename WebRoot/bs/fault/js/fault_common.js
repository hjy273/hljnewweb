/**
 * fault_common.js
 * 
 * @creator yangjun 2011-10-31
 * @used for fault's all jsp files
 */
var contextPath;
/**
 * set context path
 */
setContextPath = function(path) {
	contextPath = path;
}
/**
 * show fault process history
 */
showProcessHistory = function(dispatchId) {
	var actionUrl = contextPath
			+ "/faultProcessAction_showProcessHistoryList.jspx?parameter.id="
			+ dispatchId + "&&rnd=" + Math.random();
	// var myAjax = new Ajax.Updater("processHistoryTd", actionUrl, {
	// method : "post",
	// evalScripts : true
	// });
	jQuery("#processHistoryTd").load(actionUrl);
};
/**
 * show fault process all photos
 */
showProcessPhotos = function(dispatchId) {
	var actionUrl = contextPath
			+ "/faultProcessAction_showProcessPhotos.jspx?parameter.id="
			+ dispatchId + "&&rnd=" + Math.random();
	// var myAjax = new Ajax.Updater("processPhotosTd", actionUrl, {
	// method : "post",
	// evalScripts : true
	// });
	jQuery("#processPhotosTd").load(actionUrl);
};
/**
 * show fault all photos
 */
showFaultPhotos = function(dispatchId) {
	var actionUrl = contextPath
			+ "/faultProcessAction_showFaultPhotos.jspx?parameter.id="
			+ dispatchId + "&&rnd=" + Math.random();
	// var myAjax = new Ajax.Updater("processPhotosTd", actionUrl, {
	// method : "post",
	// evalScripts : true
	// });
	jQuery("#photosTd").load(actionUrl);
};
/**
 * show fault reply audit history
 */
showAuditHistory = function(dispatchId) {
	var actionUrl = contextPath
			+ "/faultAuditAction_showAuditHistoryList.jspx?parameter.id="
			+ dispatchId + "&&rnd=" + Math.random();
	// var myAjax = new Ajax.Updater("auditHistoryTd", actionUrl, {
	// method : "post",
	// evalScripts : true
	// });
	jQuery("#auditHistoryTd").load(actionUrl);
};
/**
 * view fault dispatch
 */
function viewFaultAlert(id, businessType) {
	window.location.href = contextPath
			+ '/faultAlertAction_view.jspx?parameter.id=' + id
			+ '&businessType=' + businessType;
}
/**
 * view fault dispatch
 */
function viewFaultDispatch(id, businessType) {
	window.location.href = contextPath
			+ '/faultDispatchAction_view.jspx?parameter.id=' + id
			+ '&businessType=' + businessType;
}
