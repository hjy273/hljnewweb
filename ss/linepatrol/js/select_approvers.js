showDlg = function(inputName, spanId) {
	var actionURL = "/WebApp/load_approvers.do?method=loadApprovers&input_name="
			+ inputName + "&span_id=" + spanId + "&rnd=" + Math.random();
	var dlg = document.createElement("div");
	dlg.id = "dlg";
	dlg.bgColor = "#CCCCCC";
	dlg.style.position = "absolute";
	dlg.style.zIndex = "9999";
	dlg.style.width = "400px";
	dlg.style.height = "300px";
	// dlg.style.top = "100px";
	dlg.style.left = (parseInt(document.body.clientWidth) - 400) / 2 + "px";
	dlg.style.top = (parseInt(document.body.clientHeight) - 300) / 2 + "px";
	// dlg.style.background = "EEEEEE";
	dlg.style.border = "1px solid #0066cc";
	dlg.style.padding = "5px";
	var table = document.createElement("table");
	var tr = document.createElement("tr");
	var td = document.createElement("td");
	table.border = "0";
	table.width = "100%";
	td.style.textAlign = "right";
	td.height = "25";
	var newA = document.createElement("a");
	newA.id = "hideDlg";
	newA.href = "#";
	newA.innerHTML = "[X]";
	newA.onclick = function() {
		document.body.removeChild(document.getElementById("dlg"));
		document.body.removeChild(document.getElementById("mask"));
		return false;
	}
	td.appendChild(newA);
	tr.appendChild(td);
	table.appendChild(tr);
	dlg.appendChild(table);
	var iFrame = document.createElement("iframe");
	iFrame.id = "addFrame";
	iFrame.name = "addFrame";
	iFrame.scrolling = "yes";
	iFrame.src = "";
	iFrame.frameBorder = "0";
	iFrame.width = "375";
	iFrame.height = "270";
	dlg.appendChild(iFrame);
	// dlg.innerHTML = "<iframe id='addFrame' scrolling='yes' src=''
	// frameborder='0' width='100%' name='addFrame' />";
	document.body.appendChild(dlg);
	document.getElementById("addFrame").src = actionURL;
	var newMask = document.createElement("div");
	newMask.id = "mask";
	newMask.style.position = "absolute";
	newMask.style.zIndex = "1";
	newMask.style.width = document.body.scrollWidth + "px";
	newMask.style.height = document.body.scrollHeight + "px";
	newMask.style.top = "0px";
	newMask.style.left = "0px";
	newMask.style.background = "#000";
	newMask.style.filter = "alpha(opacity=40)";
	newMask.style.opacity = "0.40";
	document.body.appendChild(newMask);
};
showApproverDlg = function(inputName, spanId, existName, inputType,
		notAllowName, displayType, departId, exceptSelf) {
	var actionURL = "/WebApp/load_approvers.do?method=loadApprovers&input_name="
			+ inputName
			+ "&span_id="
			+ spanId
			+ "&input_type="
			+ inputType
			+ "&display_type="
			+ displayType
			+ "&except_self="
			+ exceptSelf
			+ "&depart_id=" + departId + "&rnd=" + Math.random();
	if (typeof (document.forms[0].elements[existName]) != "undefined"
			&& document.forms[0].elements[existName] != null) {
		var existValue = document.forms[0].elements[existName].value;
		actionURL = actionURL + "&exist_value=" + existValue;
	}
	if (typeof (document.forms[0].elements[notAllowName]) != "undefined"
			&& document.forms[0].elements[notAllowName] != null) {
		var notAllowValue = document.forms[0].elements[notAllowName].value;
		actionURL = actionURL + "&not_allow_value=" + notAllowValue;
	}
	jQuery("#panelWindow" + spanId).jWindowOpen( {
		modal : false,
		center : true,
		close : "#" + spanId + "HideDlg",
		closeHoverClass : "hover"
	});
	var iFrame = document.getElementById(spanId + "Frame");
	iFrame.src = actionURL;
};
