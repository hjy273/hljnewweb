<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.linecut.beans.LineCutBean"%>

<html>
	<head>
		<title>LineCutSearch</title>
				<script type="text/javascript" src="./js/prototype.js"></script>
		<script type="text/javascript" src="./js/json2.js"></script>
		<style type="text/css">
.trStyle {
	background: #ffffff;
}

.fileStyle {
	width: 240px;
	height: 20px;
	line-height: 20px;
}

.selTd {
	text-align: center;
}
</style>
		<script type="text/javascript" language="javascript">
		
		function checkDate(strID) {
	//开始时间
    var yb = LineCutBean.begintime.value.substring(0,4);
	var mb = parseInt(LineCutBean.begintime.value.substring(5,7) ,10);
	var db = parseInt(LineCutBean.begintime.value.substring(8,10),10);
    //结束时间
    var ye = LineCutBean.endtime.value.substring(0,4);
	var me = parseInt(LineCutBean.endtime.value.substring(5,7) ,10);
	var de = parseInt(LineCutBean.endtime.value.substring(8,10),10);
	if(yb != "" && ye != "") {
		if(ye < yb) {
			alert("查询结束日期不能小于开始日期!");
      		document.all.item(strID).value="";
      		LineCutBean.endtime.focus();
      		return false;
		}
		if((ye == yb ) && (me < mb  || de < db)) {
     		alert("查询结束日期不能小于开始日期!");
      		document.all.item(strID).value="";
      		LineCutBean.endtime.focus();
      		return false;
    	}
	}
	return true;
}
		
		//选择日期
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	checkDate(strID);
    	return ;
	}

    function getTimeWin(objName){
		iteName = objName;
		showx = event.screenX - event.offsetX - 4 - 210 ;
		showy = event.screenY - event.offsetY + 18;
		var timeWin = window.showModalDialog("${ctx}/common/time.html",self, "dialogWidth:255px; dialogHeight:285px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;help:no");
	}
	function setSelecteTime(time) {
	    	document.all.item(iteName).value = time;
    }
				//级联..............
				function onSelChangeLevel() {
					var ops = document.getElementById('line');
					ops.options.length = 0;
					var clewOps = document.getElementById('clew');
					clewOps.options.length = 0;
					var newClewOp = document.createElement("option");
					newClewOp.value = "";
					newClewOp.text = "===选择所有段名===";
					clewOps.add(newClewOp);
					var params = document.getElementById('level').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getLineByLevelId&levelId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callback, asynchronous:true});
				}
				
				function callback(originalRequest) {
					var ops = document.getElementById('line');
					var rst = originalRequest.responseText;
					var linearr = eval('('+rst+')');
					if(linearr.length == 0) {
						alert("该级别尚未制定线路!");
						return;
					}
					ops.options.add(new Option('===请选择线路===','mention'));
					for(var i = 0; i < linearr.length; i++) {
						ops.options.add(new Option(linearr[i].linename,linearr[i].lineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("网络连接出现问题，请关闭后重试!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
				
				function onSelChangeLine() {
					var ops = document.getElementById('clew');
					var length = ops.length;
					ops.options.length = 0;//清空下拉列表
					ops.options.add(new Option("===选择所有段名===",""));
					var params = document.getElementById('line').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getClewByLineId&lineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServer, asynchronous:true});
				}
				
				function callbackToServer(originalRequest) {
					var ops = document.getElementById('clew');
					var rst = originalRequest.responseText;
					var clewarr = eval('('+rst+')');
					if(clewarr.length == 0) {
						alert("该路线尚未制定线段!");
						return;
					}
					for(var i = 0; i < clewarr.length; i++) {
						ops.options.add(new Option(clewarr[i].sublinename,clewarr[i].sublineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("网络连接出现问题，请关闭后重试!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
				
				
				
					function onSelectChangeClew() {
					var ops = document.getElementById('cutLineName');
					var length = ops.length;
					ops.options.length = 0;//清空下拉列表
					ops.options.add(new Option("===选择所有名称===",""));
					var params = document.getElementById('clew').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getNameByClewId&sublineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServerByName, asynchronous:true});
				}
				
				function callbackToServerByName(originalRequest) {
					var ops = document.getElementById('cutLineName');
					var rst = originalRequest.responseText;
					var nameArr = eval('('+rst+')');
					if(nameArr.length == 0) {
						alert("该路线下没有割接!");
						return;
					}
					for(var i = 0; i < nameArr.length; i++) {
						ops.options.add(new Option(nameArr[i].name,nameArr[i].name));
					}
				}
				
				
	    	</script>
	</head>
	
	<body>
		<br />
       <template:titile value="割接查询" />
			<html:form action="/LineCutReAction?method=queryCutBeanStat"
				styleId="searchForm" id="searchForm" onsubmit="return checkDate()">
				<template:formTable namewidth="200" contentwidth="200">

					<template:formTr name="线路级别">
						<select name="level" class="inputtext" style="width: 180px"
							onchange="onSelChangeLevel()" id="level">
							<option value="mention">
								===请选择线路级别===
							</option>
							<logic:iterate id="linelevel" name="linelevelList">
								<bean:write name="linelevel" property="name" />
								<option value="<bean:write name="linelevel" property="code"/>">
									<bean:write name="linelevel" property="name" />
								</option>
							</logic:iterate>
						</select>
						<img id="Loadingimg" src="./images/ajaxtags/indicator.gif"
							style="display: none">
					</template:formTr>

					<template:formTr name="线路名称">
						<select class="inputtext" id="line" onchange="onSelChangeLine()"
							name="line" style="width: 180px;">
							<option></option>
						</select>
					</template:formTr>

					<template:formTr name="中继段名">
						<select name="sublineid" class="inputtext" style="width: 180px"
							id="clew" onchange="onSelectChangeClew()">
							<option value="">
								===选择所有段名===
							</option>
						</select>
					</template:formTr>


					<template:formTr name="割接名称">
						<select name="name" class="inputtext" style="width: 180px"
							id="cutLineName">
							<option value="">
								===选择所有割接===
							</option>
						</select>
					</template:formTr>

					<template:formTr name="申请开始时间">
						<input id="begin" type="text" readonly="readonly" name="begintime"
							class="inputtext" style="width: 150" />
						<INPUT TYPE='BUTTON' VALUE='日期' readonly="readonly" ID='btn'
							onclick="GetSelectDateTHIS('begin')"
							STYLE="font: 'normal small-caps 6pt serif';">
					</template:formTr>
					<template:formTr name="申请结束时间">
						<input id="end" type="text" name="endtime" readonly="readonly"
							class="inputtext" style="width: 150" />
						<INPUT TYPE='BUTTON' VALUE='日期' ID='btn'
							onclick="GetSelectDateTHIS('end')"
							STYLE="font: 'normal small-caps 6pt serif';">
					</template:formTr>
					
					<template:formTr name="割接状态">
						<select name="isarchive" class="inputtext" style="width: 180px">
							 	<option value="0">===选择割接状态===</option>
    						 	<option value="待审批">待审批</option>
     						 	<option value="通过审批">通过审批</option>
      							<option value="未通过审批">未通过审批</option>
      							<option value="正在施工">正在施工</option>
      							<option value="施工完毕">施工完毕</option>
     							<!--  <option value="待验收">待验收</option>-->
      							<option value="已经验收">已经验收</option>
     							<option value="未通过验收">未通过验收</option>
     							<option value="已经归档">已经归档</option>
						</select>
					</template:formTr>
					
					<template:formSubmit>
						<td>
							<html:submit property="action" styleClass="button">查找</html:submit>
						</td>
						<td>
							<html:reset property="action" styleClass="button">取消</html:reset>
						</td>
					</template:formSubmit>
				</template:formTable>

			</html:form>
	</body>
</html>