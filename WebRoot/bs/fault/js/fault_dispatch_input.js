/**
 * fault_dispatch_input.js
 * 
 * @creator yangjun 2011-10-31
 * @used for fault_dispatch_input.jsp
 */
var resourceItem;
var resourceDefaultValue;
var resourceDefaultValueType;
var patrolgroupcombotree;

/**
 * set resource options form element
 */
setResourceItem = function(item) {
	resourceItem = jQuery(item);
}


/**
 * select resources list for businesstype
 */
getSelectResourceList = function(businessType, defaultValue, defaultValueType) {
	var resourceName = $('#resourceName').val();
	var url = contextPath + "/faultDispatchAction_getResources.jspx";
	url += "?parameter.businessType=" + businessType;
	// url += "&&parameter.maintenceId="+contractorId;
	// url += "&&parameter.patrolmanId="+patrolmanId;
	url += "&&parameter.resourceName=" + resourceName;
	resourceDefaultValue = defaultValue;
	resourceDefaultValueType = defaultValueType;
	// var myAjax = new Ajax.Request(url, {
	// method : 'get',
	// asynchronous : 'true',
	// onComplete : setResourceCode
	// });
	jQuery.post(url, function(data, textStatus, jqXHR) {
		setResourceCode(jqXHR);
	});
}
/**
 * clear resources options
 */
function clearResourcesOptions() {
	resourceItem.empty();
}
/**
 * set resource options for ajax response
 */
function setResourceCode(response) {
	// resourceItem.options.length = 1;
	var str = response.responseText;
	if (str == "")
		return true;
	var optionlist = str.split(";");
	if (typeof (optionlist.length) == "undefined") {
		var t = optionlist.split("=")[0];
		var v = optionlist.split("=")[1];
		var addr = optionlist[i].split("=")[2];
		var pid = optionlist[i].split("=")[3];
		var pname = optionlist[i].split("=")[4];
		var cid = optionlist[i].split("=")[5];
		var rType = optionlist[i].split("=")[6];
		// resourceItem.options[0] = new Option(v, t);
		if (t == resourceDefaultValue && rType == resourceDefaultValueType) {
			// resourceItem.options[0].selected = true;
			resourceItem.append("<option value='" + t + "' addr='" + addr
					+ "' pid='" + pid + "' cid='" + cid + "' pname='" + pname
					+ "' rtype='"+rType+"' selected>" + v + "</option>");
			if (pid != "" && pid != null && pid != "null") {
			    jQuery("#patrolGroupName").val(pname);
				jQuery('#maintenanceId').val(cid);
				jQuery('#patrolGroupId').val(pid);
				jQuery('#stationType').val(rType);
			}
		} else {
			resourceItem.append("<option value='" + t + "' addr='" + addr
					+ "' pid='" + pid + "' cid='" + cid + "' pname='" + pname
					+ "' rtype='"+rType+"'>" + v + "</option>");
		}
	} else {
		for ( var i = 0; i < optionlist.length; i++) {
			var t = optionlist[i].split("=")[0];
			var v = optionlist[i].split("=")[1];
			var addr = optionlist[i].split("=")[2];
			var pid = optionlist[i].split("=")[3];
			var pname = optionlist[i].split("=")[4];
			var cid = optionlist[i].split("=")[5];
			var rType = optionlist[i].split("=")[6];
			// resourceItem.options[i + 1] = new Option(v, t);
			if (t == resourceDefaultValue && rType == resourceDefaultValueType) {
				// resourceItem.options[i + 1].selected = true;
				resourceItem.append("<option value='" + t + "' addr='" + addr
						+ "' pid='" + pid + "' cid='" + cid + "' pname='"
						+ pname + "' rtype='"+rType+"' selected>" + v + "</option>");
				if (pid != "" && pid != null && pid != "null") {
					jQuery("#patrolGroupName").val(pname);
					jQuery('#maintenanceId').val(cid);
					jQuery('#patrolGroupId').val(pid);
					jQuery('#stationType').val(rType);
				}
			} else {
				resourceItem.append("<option value='" + t + "' addr='" + addr
						+ "' pid='" + pid + "' cid='" + cid + "' pname='"
						+ pname + "' rtype='"+rType+"'>" + v + "</option>");
			}
		}
	}
}
function setStationInfo() {
	var station = jQuery("#faultAlert_stationId");
	var address = station.find("option:selected").attr("addr");
	var patrolmanId = station.find("option:selected").attr("pid");
	var patrolmanName = station.find("option:selected").attr("pname");
	var contractorId = station.find("option:selected").attr("cid");
	var rType = station.find("option:selected").attr("rtype");
	if (address == null || address == "null") {
		address = "";
	}
	if (patrolmanId == null || patrolmanId == "null") {
		patrolmanId = "";
		patrolmanName = "";
		contractorId = "";
	}
	jQuery("#address").val(address);
	jQuery("#patrolGroupName").val(patrolmanName);

	jQuery('#maintenanceId').val(contractorId);
	jQuery('#patrolGroupId').val(patrolmanId);
	jQuery('#stationType').val(rType);
}
/**
 * selected patrolgroup event
 */
function search() {
	var val = window.showModalDialog(contextPath
			+ '/common/externalresources_getpatrolgroup.jspx', '',
			'status:no;center:yes;dialogWidth:400px;dialogHeight:300px;');
	var patrolGroupName = "";
	var patrolGroupId = "";
	var orgId = "";
	if (val) {
		for (i = 0; i < val.length; i++) {
			patrolGroupName += val[i].NAME + ",";
			patrolGroupId += val[i].ID + ",";
			orgId += val[i].ORGID + ",";
		}
		patrolGroupId = patrolGroupId.substring(0, patrolGroupId.length - 1);
		patrolGroupName = patrolGroupName.substring(0, patrolGroupName.length - 1);
		orgId = orgId.substring(0, orgId.length - 1);
		jQuery("#patrolGroupName").val(patrolGroupName);
		jQuery("#patrolGroupId").val(patrolGroupId);
		jQuery("#maintenanceId").val(orgId);
	}
}