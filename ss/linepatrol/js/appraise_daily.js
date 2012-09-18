/**
 * 代维公司日常考核JS判断
 */
// 验证选中项中的备注是否填写
function checkAppraiseDailyValid() {
	//判断中标合同号是否为空
	var contractNo = document.getElementById("contractNo").value;
	if(contractNo==""){
		var con = confirm("中标合同号为空，是否确认提交！");
		if(!con){
			return false;
		}
	}else{
		var message = "中标合同号为：" + contractNo + "，是否确认提交！"
		var conf = confirm(message);
		if(!conf){
			return false;
		}
	}
	return true;
}

function checkBoxIsNull(flagName){
	// 获得CheckBox
	var ruleCheckbox = document.getElementsByName(flagName+"ruleCheckbox");
	for ( var i = 0; i < ruleCheckbox.length; i++) {
		if (ruleCheckbox[i].checked) {
			// 获得选中的checkbox的值，其值为备注的ID
			var checkboxValue = ruleCheckbox[i].value;
			// 获得备注值
			var remarkValue = document.getElementById(checkboxValue).value;
			if (remarkValue == "") {
				alert("请填写备注信息！");
				document.getElementById(checkboxValue).focus();
				return false;
			}
		}
	}
}

// 选中时，设定备注文本框可填写状态
function setTextWriteState(ruleId,remark,tableId,appraiseType) {
	var remark = document.getElementById(ruleId);
	var ruleCheckbox = document.getElementById(ruleId+"ruleCheckbox");
		if (ruleCheckbox.checked) {
			editRemark(ruleId,'',tableId,appraiseType,'1');
			remark.disabled="";
		} else {
			remark.value="";
			remark.disabled="disabled";
		}
		if(appraiseType=="YearEnd"){
			var markDeduction = document.getElementById(ruleId+"markDeduction");
			if (ruleCheckbox.checked) {
				markDeduction.disabled="";
				markDeduction.value="0";
			} else {
				markDeduction.value="";
				markDeduction.disabled="disabled";
			}
		}
}

/*
 * 获得选择的对象ID和备注的值 
 * itemId用来存储选中的选项ID 
 * remark用来存储选中填写的备注信息
 */
function getItemIdAndRemark(itemId, remark) {
	
}

//将用户填写的值放入页面中
function setInputValueToPage(flagName,appraiseType){
	checkBoxIsNull(flagName);
	// 获得CheckBox
	var ruleCheckbox = document.getElementsByName(flagName+"ruleCheckbox");
	var itemId = "";
	var remark = "";
	var markDeduction = "";
	for ( var i = 0; i < ruleCheckbox.length; i++) {
		if (ruleCheckbox[i].checked) {
			// 获得选中的checkbox的值，其值为备注的ID
			var checkboxValue = ruleCheckbox[i].value;
			// 获得备注值
			var remarkValue = document.getElementById(checkboxValue).value;
			itemId = itemId + checkboxValue + "|";
			remark = remark + remarkValue + "|";
			if(appraiseType=="YearEnd"){
				var markDeductionValue=document.getElementById(checkboxValue+"markDeduction").value;
				markDeduction=markDeduction+markDeductionValue+"|";
			}
		}
	}
	document.getElementById(flagName+"ruleIds").value=("" + itemId);
	document.getElementById(flagName+"remarks").value=("" + remark);
	if(appraiseType=="YearEnd"){
		document.getElementById(flagName+"markDeductions").value=("" + markDeduction);
	}
}
function saveForm(appraiseType,tableId){
		var flagName=appraiseType+tableId;
		var url="/WebApp/appraiseDaily"+appraiseType+"Action.do?method=saveForm";
		setInputValueToPage(flagName,appraiseType);
		var tableId=document.getElementById(flagName+"tableId").value; 
		var contractorId=document.getElementById(flagName+"contractorId").value;
		var businessModule=document.getElementById(flagName+"businessModule").value;
		var businessId=document.getElementById(flagName+"businessId").value; 
		var appraiser=document.getElementById(flagName+"appraiser").value; 
		var ruleIds=document.getElementById(flagName+"ruleIds").value;
		var remarks=document.getElementById(flagName+"remarks").value;
		var par="tableId="+tableId+"&contractorId="+contractorId+"&businessModule="+businessModule+"&businessId="+businessId+"&appraiser="+appraiser+"&ruleIds="+ruleIds+"&remarks="+remarks;
		if(appraiseType == 'Daily'){
			var contractNo=document.getElementById("contractNo").value; 
			par+="&contractNo="+contractNo;
		}
		if(appraiseType == 'YearEnd'){
			var contractNo=document.getElementById("contractNo").value; 
			var year=document.getElementById("year").value;
			var markDeductions=document.getElementById(flagName+"markDeductions").value;
			par+="&contractNo="+contractNo+"&year="+year+"&markDeductions="+markDeductions;
		}
		var myAjax = new Ajax.Request( url , {
			method: 'post',
			parameters:par,
			asynchronous: true,
			onComplete: saveFormBack });
}
function saveFormBack(response){
}
function showOrHidde(flagName){
	var infoDiv = document.getElementById(flagName);
	if(infoDiv.style.display=="block"){
			infoDiv.style.display="none";
	}else{
			infoDiv.style.display="block";
	}
}
function editRemark(ruleId,remark,tableId,appraiseType,flag){
	var actionUrl="/WebApp/linepatrol/appraise/remark.jsp?ruleId="+ruleId+"&remark="+remark+"&tableId="+tableId+"&appraiseType="+appraiseType+"&flag="+flag;
	editRemarkWin = new Ext.Window({
		layout : 'fit',
		width:450,height:200, 
		resizable:true,
		closeAction : 'close', 
		modal:true,
		closable:false,
		autoScroll:true,
		autoLoad:{url: actionUrl,scripts:true}, 
		plain: false,
		title:"编辑备注" 
	});
	editRemarkWin.show(Ext.getBody());
}
function closeRemark(){
	editRemarkWin.close();
}
function saveRemark(ruleId,remark,tableId,appraiseType,markDeduction){
	if(markDeduction!=""){
		document.getElementById(ruleId+"markDeduction").value=markDeduction;
	}
	document.getElementById(ruleId).value=remark;
	saveForm(appraiseType,tableId);
	closeRemark();
}
function viewRemark(remark){
	var actionUrl="/WebApp/linepatrol/appraise/viewRemark.jsp?remark="+remark;
	viewRemarkWin = new Ext.Window({
		layout : 'fit',
		width:450,height:200, 
		resizable:true,
		closeAction : 'close', 
		modal:false,
		autoScroll:true,
		autoLoad:{url: actionUrl,scripts:true}, 
		plain: false,
		title:"查看备注" 
	});
	viewRemarkWin.show(Ext.getBody());
}
function closeViewRemark(){
	viewRemarkWin.close();
}

