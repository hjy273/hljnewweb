
/**
 * sendtask-transfer.js
 * use for sendtask-transfer.jsp
 * @create by yangjun 2011-08-31
 */
var regionId;
var defaultValue;
/**
 * set region id
 */
function setRegionId(region) {
	regionId = region;
}
function setDefaultValue(value) {
	defaultValue = value;
}
/**
 * initial sendtask-transfer.jsp page loading
 */
jQuery(function () {
	// select org
	jQuery("#sel_orgs").combotree({url:encodeURI(contextPath + "/common/httpclient_getOrgnizeDeptJson.jspx?flag=1&&regionid=" + regionId + "&&lv=3"), onBeforeExpand:function (node, param) {
	}});
	jQuery("#sel_orgs").combotree("setValue", defaultValue);
});
String.prototype.trim = function () {
	return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");
};
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

