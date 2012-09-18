
/**
 * sendtask-dispatch.js
 * use for sendtask-dispatch.jsp
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
 * initial sendtask-dispatch.jsp page loading
 */
jQuery(function () {
	// select org
	jQuery("#sel_orgs").combotree({url:encodeURI(contextPath + "/common/httpclient_getOrgnizeDeptJson.jspx?flag=1&&regionid=" + regionId + "&&lv=3"), onBeforeExpand:function (node, param) {
	}});
	jQuery("#sel_orgs").combotree("setValue", defaultValue);
});


