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
	//��ʼʱ��
    var yb = LineCutBean.begintime.value.substring(0,4);
	var mb = parseInt(LineCutBean.begintime.value.substring(5,7) ,10);
	var db = parseInt(LineCutBean.begintime.value.substring(8,10),10);
    //����ʱ��
    var ye = LineCutBean.endtime.value.substring(0,4);
	var me = parseInt(LineCutBean.endtime.value.substring(5,7) ,10);
	var de = parseInt(LineCutBean.endtime.value.substring(8,10),10);
	if(yb != "" && ye != "") {
		if(ye < yb) {
			alert("��ѯ�������ڲ���С�ڿ�ʼ����!");
      		document.all.item(strID).value="";
      		LineCutBean.endtime.focus();
      		return false;
		}
		if((ye == yb ) && (me < mb  || de < db)) {
     		alert("��ѯ�������ڲ���С�ڿ�ʼ����!");
      		document.all.item(strID).value="";
      		LineCutBean.endtime.focus();
      		return false;
    	}
	}
	return true;
}
		
		//ѡ������
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
				//����..............
				function onSelChangeLevel() {
					var ops = document.getElementById('line');
					ops.options.length = 0;
					var clewOps = document.getElementById('clew');
					clewOps.options.length = 0;
					var newClewOp = document.createElement("option");
					newClewOp.value = "";
					newClewOp.text = "===ѡ�����ж���===";
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
						alert("�ü�����δ�ƶ���·!");
						return;
					}
					ops.options.add(new Option('===��ѡ����·===','mention'));
					for(var i = 0; i < linearr.length; i++) {
						ops.options.add(new Option(linearr[i].linename,linearr[i].lineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("�������ӳ������⣬��رպ�����!");
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
					ops.options.length = 0;//��������б�
					ops.options.add(new Option("===ѡ�����ж���===",""));
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
						alert("��·����δ�ƶ��߶�!");
						return;
					}
					for(var i = 0; i < clewarr.length; i++) {
						ops.options.add(new Option(clewarr[i].sublinename,clewarr[i].sublineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("�������ӳ������⣬��رպ�����!");
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
					ops.options.length = 0;//��������б�
					ops.options.add(new Option("===ѡ����������===",""));
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
						alert("��·����û�и��!");
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
       <template:titile value="��Ӳ�ѯ" />
			<html:form action="/LineCutReAction?method=queryCutBeanStat"
				styleId="searchForm" id="searchForm" onsubmit="return checkDate()">
				<template:formTable namewidth="200" contentwidth="200">

					<template:formTr name="��·����">
						<select name="level" class="inputtext" style="width: 180px"
							onchange="onSelChangeLevel()" id="level">
							<option value="mention">
								===��ѡ����·����===
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

					<template:formTr name="��·����">
						<select class="inputtext" id="line" onchange="onSelChangeLine()"
							name="line" style="width: 180px;">
							<option></option>
						</select>
					</template:formTr>

					<template:formTr name="�м̶���">
						<select name="sublineid" class="inputtext" style="width: 180px"
							id="clew" onchange="onSelectChangeClew()">
							<option value="">
								===ѡ�����ж���===
							</option>
						</select>
					</template:formTr>


					<template:formTr name="�������">
						<select name="name" class="inputtext" style="width: 180px"
							id="cutLineName">
							<option value="">
								===ѡ�����и��===
							</option>
						</select>
					</template:formTr>

					<template:formTr name="���뿪ʼʱ��">
						<input id="begin" type="text" readonly="readonly" name="begintime"
							class="inputtext" style="width: 150" />
						<INPUT TYPE='BUTTON' VALUE='����' readonly="readonly" ID='btn'
							onclick="GetSelectDateTHIS('begin')"
							STYLE="font: 'normal small-caps 6pt serif';">
					</template:formTr>
					<template:formTr name="�������ʱ��">
						<input id="end" type="text" name="endtime" readonly="readonly"
							class="inputtext" style="width: 150" />
						<INPUT TYPE='BUTTON' VALUE='����' ID='btn'
							onclick="GetSelectDateTHIS('end')"
							STYLE="font: 'normal small-caps 6pt serif';">
					</template:formTr>
					
					<template:formTr name="���״̬">
						<select name="isarchive" class="inputtext" style="width: 180px">
							 	<option value="0">===ѡ����״̬===</option>
    						 	<option value="������">������</option>
     						 	<option value="ͨ������">ͨ������</option>
      							<option value="δͨ������">δͨ������</option>
      							<option value="����ʩ��">����ʩ��</option>
      							<option value="ʩ�����">ʩ�����</option>
     							<!--  <option value="������">������</option>-->
      							<option value="�Ѿ�����">�Ѿ�����</option>
     							<option value="δͨ������">δͨ������</option>
     							<option value="�Ѿ��鵵">�Ѿ��鵵</option>
						</select>
					</template:formTr>
					
					<template:formSubmit>
						<td>
							<html:submit property="action" styleClass="button">����</html:submit>
						</td>
						<td>
							<html:reset property="action" styleClass="button">ȡ��</html:reset>
						</td>
					</template:formSubmit>
				</template:formTable>

			</html:form>
	</body>
</html>