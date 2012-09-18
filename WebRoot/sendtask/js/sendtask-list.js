
/**
 * sendtask-list.js
 * @create yangjun 2011-09-16
 * @use for sendtask-list.jsp
 */
var regionId;
var defaultValues;
function setDefaultValues(values) {
	defaultValues = values;
}
function setRegionId(region) {
	regionId = region;
}
toRecycleForm = function (dispatchId, taskId) {
	var url = contextPath + "/sendTaskSigninAction_recycle.jspx?id=" + dispatchId;
	location.href = url;
};
toUpdateForm = function (dispatchId) {
	var url = contextPath + "/sendTaskAction_edit.jspx?id=" + dispatchId;
	location.href = url;
};
/**
 * initial sendtask-list.jsp page loading
 */
jQuery(function () {
	// select org
	var orgUrl = contextPath + "/common/httpclient_getOrgnizeDeptJson.jspx?flag=1&objtype=ORG&regionid=" + regionId + "&lv=1&rnd=" + Math.random();
	var userUrl = contextPath + "/common/httpclient_getOrgnizeDeptJson.jspx?flag=1&regionid=" + regionId + "&lv=3&rnd=" + Math.random();
	jQuery("#sel_send_orgs").combotree({url:encodeURI(orgUrl)});
	jQuery("#sel_send_users").combotree({url:encodeURI(userUrl)});
	jQuery("#sel_accept_orgs").combotree({url:encodeURI(orgUrl)});
	jQuery("#sel_accept_users").combotree({url:encodeURI(userUrl)});
	jQuery("#sel_send_orgs").combotree("setValue", defaultValues[0]);
	jQuery("#sel_send_users").combotree("setValue", defaultValues[1]);
	jQuery("#sel_accept_orgs").combotree("setValue", defaultValues[2]);
	jQuery("#sel_accept_users").combotree("setValue", defaultValues[3]);
});

