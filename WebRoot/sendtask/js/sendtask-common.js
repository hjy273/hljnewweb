
/**
 * sendtask-common.js
 * @create yangjun 2011-09-16
 * @use for sendtask/*.jsp
 */
String.prototype.trim = function () {
	return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");
};
function judgeEmptyString(value) {
	return (value == null || value == "" || value.trim() == "");
}
function valCharLength(Value) {
	var j = 0;
	var s = Value;
	for (var i = 0; i < s.length; i++) {
		if (s.substr(i, 1).charCodeAt(0) > 255) {
			j = j + 2;
		} else {
			j++;
		}
	}
	return j;
}
goBack = function (inputUrl) {
	var url = contextPath;
	if (typeof (inputUrl) == "undefined" || inputUrl == "") {
		url += "/sendTaskAction_waitHandleList.jspx";
	} else {
		url += inputUrl;
	}
	location = url;
};
showProcessHistory = function (applyId) {
	var actionUrl = contextPath + "/sendTaskAction_showSendTaskProcessHistory.jspx?id=" + applyId + "&&rnd=" + Math.random();
	var myAjax = new Ajax.Updater("processHistoryTd", actionUrl, {method:"post", evalScript:true});
};
toViewForm = function (dispatchId, inputUrl) {
	var url = contextPath + "/sendTaskAction_view.jspx?id=" + dispatchId + "&&backUrl=" + inputUrl;
	location.href = url;
};
toDeleteForm = function (dispatchId, inputUrl) {
	var url = contextPath + "/sendTaskAction_delete.jspx?id=" + dispatchId + "&&backUrl=" + inputUrl;
	if (confirm("\u786e\u5b9a\u8981\u5220\u9664\u8be5\u6d3e\u5355\u5417\uff1f")) {
		location.href = url;
	}
};

