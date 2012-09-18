<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript" src="./js/prototype.js"></script>
		<script type="text/javascript" src="./js/json2.js"></script>
	
	<style type="text/css">			
			.commSel{
				border-top:#323230 solid 1px;
				border-left:#323230 solid 1px;
				border-bottom:#FAFEFA solid 1px;
				border-right:#FAFEFA solid 1px;
				background:#C6D6E2;
				height:120px;
				font-size:12px;
				width: 200px;	
			}
	</style>
<script type="" language="javascript">
		var parentWin = window.dialogArguments;

		function onSelChangeCon() {
			var ops = document.getElementById('patrol');
			var length = ops.length;
			ops.options.length = 0;
			var clewOps = document.getElementById('watchid');
			var clewLength = clewOps.length;
			clewOps.options.length = 0;
			var newClewOp = document.createElement("option");
			clewOps.add(newClewOp);
			var params = document.getElementById('con').value;
			if(params == "") {
				ops.options.add(new Option("",""));
				return;
			}
			var url = 'WatchMsgAction.do?method=getPatrolInfoByConId&conid=' + params;
			var myAjax = new Ajax.Request(url, {method:"post", onComplete:callback, asynchronous:true});
		}
		
		function callback(originalRequest) {
			var ops = document.getElementById('patrol');
			var rst = originalRequest.responseText;
			var linearr = eval('('+rst+')');
			if(linearr.length == 0) {
				alert("�ô�ά��λ��û��ά����!");
				return;
			}
			ops.options.add(new Option('===��ѡ��ά����===','')); 
			for(var i = 0; i < linearr.length; i++) {
				ops.options.add(new Option(linearr[i].patrolname,linearr[i].patrolid));
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

	function onSelChangePatrol() {
			var clewOps = document.getElementById('watchid');
			var clewLength = clewOps.length;
			clewOps.options.length = 0;
			var newClewOp = document.createElement("option");
			clewOps.add(newClewOp);
			var params = document.getElementById('patrol').value;
			if(params == "") {
				ops.options.add(new Option("",""));
				return;
			}
			var flg = document.getElementById("pagetype").value
			var url = 'WatchMsgAction.do?method=getWatchInfoByPatrolId&patrolid=' + params + '&flg='+flg;
			var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackPatrol, asynchronous:true});
		}
		
		function callbackPatrol(originalRequest) {
			var ops = document.getElementById('watchid');
			var rst = originalRequest.responseText;
			var linearr = eval('('+rst+')');
			if(linearr.length == 0) {
				var pagetype = document.getElementById("pagetype").value;
				if(pagetype == "0") {
					alert("��ά������û������ִ�еĶ�����Ϣ!");
				} else {
					alert("��ά������û���ѽ����Ķ�����Ϣ!");
				}
				return;
			}
			ops.options.length=0;
			ops.options.add(new Option('===��ѡ�񶢷�����===','')); 
			for(var i = 0; i < linearr.length; i++) {
				ops.options.add(new Option(linearr[i].placename,linearr[i].placeid));
			}
			var myGlobalHandlers = {onCreate:function () {
			//Element.show("Loadingimgpatrol");
			}, onFailure:function () {
				alert("�������ӳ������⣬��رպ�����!");
			}, onComplete:function () {
				if (Ajax.activeRequestCount == 0) {
					//Element.hide("Loadingimgpatrol");
				}
			}};
			Ajax.Responders.register(myGlobalHandlers);
		}
		
		function subQuery() {
			
			var watchname = document.getElementById('watchid').value;
			if(watchname == "") {
				alert("��ѡ���ѯ�Ķ������ƣ�");
				return;
			}
			var url = "${ctx}/WatchMsgAction.do?method=doMsgQuery";
        	//window.location.href=url;
			document.forms[0].action = url;
			document.forms[0].submit();
		}

	function onSelectChangeWatch(obj) {
		var textstr = obj.options[obj.selectedIndex].text;
		document.getElementById("watchname").value = textstr;
	}

 	//ѡ������
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}
</script>
<title>queryMsgWa</title>
</head>
<body>
<logic:equal name="flg" value="0">
	<template:titile value="��ѯ����ִ�еĶ���������־" />
</logic:equal>
<logic:equal name="flg" value="1">
	<template:titile value="��ѯ�ѽ����Ķ���������־" />
</logic:equal>
<html:form action="/WatchMsgAction.do?method=doMsgQuery" styleId="WatchMsgBean" enctype="multipart/form-data">
	<logic:equal name="flg" value="0">
		<input type="hidden" id="pagetype" name="pagetype" value="0" />
	</logic:equal>
	<logic:equal name="flg" value="1">
		<input type="hidden" id="pagetype" name="pagetype" value="1" />
	</logic:equal>

	<template:formTable namewidth="100" contentwidth="500" th2="����" th1="��Ŀ">
		<template:formTr name="��ά��λ">
			<select class="commSel" id="con" onchange="onSelChangeCon()" name="con">
				<option value="">===��ѡ���ά��λ===</option>
				<logic:iterate id="element" name="coninfolist">
				<option value="<bean:write name="element" property="conid"/>"><bean:write name="element" property="conname"/></option>
			</logic:iterate>
		</select>
		<img id="Loadingimg" src="images/ajaxtags/indicator.gif" style="display:none">
		</template:formTr>

		<template:formTr name="ά����">
			<select class="commSel" id="patrol" onchange="onSelChangePatrol()" name="patrol">
		  		<option></option>
		  	</select>
		  	<img id="Loadingimgpatrol" src="images/ajaxtags/indicator.gif" style="display:none">
		</template:formTr>
		
		<template:formTr name="��������">
			<select class="commSel" id="watchid"  name="watchid" onchange="onSelectChangeWatch(this)">
		  		<option></option>
		  	</select>
		  	<input type="hidden" id="watchname" name="watchname" value="" />
		</template:formTr>				

		<template:formTr name="��ʼ����">
			<input type="text" id="begindate" name="begindate" value=""
				readonly="readonly" class="inputtext" style="width: 170" />
			<INPUT TYPE='BUTTON' VALUE='����' ID='btn'
				onclick="GetSelectDateTHIS('begindate')"
				STYLE="font: 'normal small-caps 6pt serif';">						
		</template:formTr>		
		<template:formTr name="��������">
			<input type="text" id="enddate" name="enddate" value=""
				readonly="readonly" class="inputtext" style="width: 170" />
			<INPUT TYPE='BUTTON' VALUE='����' ID='btn'
				onclick="GetSelectDateTHIS('enddate')"
				STYLE="font: 'normal small-caps 6pt serif';">						
		</template:formTr>
		<template:formSubmit>
			<td>
				<html:button styleClass="button" onclick="subQuery()" property="">�� ѯ</html:button>
			</td>
			<td>
				<input type="reset" value="�� ��" class="button"
					onclick="resetInfo()">
			</td>
		</template:formSubmit>

	</template:formTable>
</html:form>
</body>
</html>
