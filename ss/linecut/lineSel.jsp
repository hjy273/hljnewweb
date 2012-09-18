<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>��·ѡ��</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<script type="text/javascript" src="<%=basePath%>js/json2.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/prototype.js"></script>
	
	<script type="text/javascript">
	
		var parentWin = window.dialogArguments;
		var lineinfo = "";
		
		function onSelChangeLevel() {
			var ops = document.getElementById('line');
			var length = ops.length;
			ops.options.length = 0;
			var clewOps = document.getElementById('clew');
			var clewLength = clewOps.length;
			clewOps.options.length = 0;
			var newClewOp = document.createElement("option");
			clewOps.add(newClewOp);
			var params = document.getElementById('level').value;
			if(params == "mention") {
				ops.options.add(new Option("",""));
				return;
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
			ops.options.length = 0;
			var params = document.getElementById('line').value;
			if(params == "mention") {
				ops.options.add(new Option("",""));
				return;
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
		
		function saveInfo() {//����ѡ�����·��������
			var levelSel = document.getElementById('level');//��ȡ��·����
			var level = levelSel.value;//��ȡ��·�����ֵ
			if(level == "mention") {
				alert("��ѡ����·����!");
				return;
			}
			
			var lineSel = document.getElementById('line');
			var line = lineSel.value;//
			if(line == "mention") {
				alert("��ѡ�������·");
				return;
			}
			
			var clewSel = document.getElementById('clew');
			var clew = clewSel.value;//
			
			var levelindex = levelSel.options.selectedIndex;//��ȡ3��selectѡ�е��±�
			var lineindex = lineSel.options.selectedIndex;
			var clewindex = clewSel.options.selectedIndex;
			
			var leveltext = levelSel.options[levelindex].text;//��ȡ3��selectѡ�е�text
			var linetext = lineSel.options[lineindex].text;
			var clewtext = clewSel.options[clewindex].text;
			
			var newLineInfo = leveltext + "->" + linetext + "->" + clewtext + "��<br>";//��װ��ʾ��Ϣ
			    
			if(lineinfo.indexOf("<br>") > 0 ) {//�ж���·�Ƿ�����
				var lineArr = lineinfo.split("<br>");
				for(var i = 0; i < lineArr.length; i++) {
					var oldLineInfo = lineArr[i] + "<br>";
					if(newLineInfo == oldLineInfo) {
						alert("����·��Ϣ�Ѿ����ӣ���ѡ��������·!");
						return;
					}
				}
			}
			
			lineinfo = lineinfo + newLineInfo;//��װ��·��Ϣ����ʾ�Ĳ��֡�
			
			var subline = parentWin.document.getElementById('sublineid');//��ȡ�������д���߶�ID��������
			
			var sublineid = subline.value;
			
			if(sublineid.length == 0) {//�����ȡ�ĸ������߶ε�ID������...��װ��Ϣ
				sublineid = clew;
			} else {
				sublineid = sublineid + ',' + clew;
			}
			subline.value = sublineid;//����װ�õ���Ϣ���븸���ڵ���������
			
			parentWin.document.getElementById('lineinfo').innerHTML = lineinfo;//����·��Ϣ���븸������
			
			alert("�����·�ɹ�");
		}
		
		function resetInfo() {//����������·��Ϣ
			lineinfo = "";
			parentWin.document.getElementById('lineinfo').innerHTML = "";
			parentWin.document.getElementById('sublineid').value = "";
		}
	
		function goBack() {
			if(lineinfo == "") {
				if(confirm("��β�����δѡ���κ�·�ߣ�\nȷ��������?")) {
					window.close();
				}
			} else {
				window.close();
			}
		}
	</script>
	
	<style type="text/css">
			*{
				font-size: 12px;
			}
			body{
				background-color: #EAE9E4;
			}
			.totalDiv {
				width: 100%;
				text-align: center;
			}
			
			.topTableStyle {
				margin-top: 20px;
				background: #cee5f7;
				width: 450px;
				text-align: center;
			}
			
			.button{
				width:80px;
				height:24px;
				background-image:url(images/bg_button_main.gif);
				BORDER:#5A6CA2 solid;BORDER-WIDTH:0 0 0 0;color:#247DAB;
			}
			
			.trStyle {
				background: #ffffff;
				height: 22px;
				line-height: 22px;
			}
			
			.leftTdStyle {
				width: 100px;
				text-align: right;
			}
			
			.rigthTdStyle {
				width: 350px;
				text-align: left;
				padding-left: 8px;
			}
			
			.commSel{
				border-top:#323230 solid 1px;
				border-left:#323230 solid 1px;
				border-bottom:#FAFEFA solid 1px;
				border-right:#FAFEFA solid 1px;
				background:#C6D6E2;
				height:120px;
				font-size:12px;
				width: 250px;	
			}
	</style>
  </head>
  
  <body>
  		<div class="totalDiv">
  			<table cellpadding="0" cellspacing="1" class="topTableStyle">
  				<tr class="trStyle">
	  				<td class="leftTdStyle">��·����</td>
	  				<td class="rigthTdStyle">
		  				<select class="commSel" id="level" onchange="onSelChangeLevel()" name="level">
		  					<option value="mention">===��ѡ����·����===</option>
		  					<logic:iterate id="levelList" name="lineLevelList">
		  						<option value="<bean:write name="levelList" property="code"/>"><bean:write name="levelList" property="name"/></option>
		  					</logic:iterate>
		  				</select>
		  				<img id="Loadingimg" src="images/ajaxtags/indicator.gif" style="display:none">
		  			</td>
	  			</tr>
	  			
	  			<tr class="trStyle">
	  				<td class="leftTdStyle">������·��<td class="rigthTdStyle">
		  				<select class="commSel" id="line" onchange="onSelChangeLine()" name="line">
		  					<option></option>
		  				</select>
		  			</td>
		  		</tr>
		  		
		  		<tr class="trStyle">
		  			<td class="leftTdStyle">�����߶Σ�</td>
		  			<td class="rigthTdStyle">
		  				<select class="commSel" id="clew">
		  					<option></option>
		  				</select>
		  			</td>
		  		</tr>
  			</table>
  			
  			<div style="margin-top: 10px;"> 
		  		<input type="hidden" value="0" id="lineSeq">
		  		<input type="button" class="button" value="��  ��" onclick="saveInfo()">
		  		<input type="button" class="button" value="��  ��" onclick="goBack()">
		  	</div>
  		</div>
  </body>
</html>
