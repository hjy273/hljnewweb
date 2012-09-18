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
    
    <title>线路选择</title>
    
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
		
		function saveInfo() {//保存选择的线路到父窗口
			var levelSel = document.getElementById('level');//获取线路级别
			var level = levelSel.value;//获取线路级别的值
			if(level == "mention") {
				alert("请选择线路级别!");
				return;
			}
			
			var lineSel = document.getElementById('line');
			var line = lineSel.value;//
			if(line == "mention") {
				alert("请选择光缆线路");
				return;
			}
			
			var clewSel = document.getElementById('clew');
			var clew = clewSel.value;//
			
			var levelindex = levelSel.options.selectedIndex;//获取3个select选中的下标
			var lineindex = lineSel.options.selectedIndex;
			var clewindex = clewSel.options.selectedIndex;
			
			var leveltext = levelSel.options[levelindex].text;//获取3个select选中的text
			var linetext = lineSel.options[lineindex].text;
			var clewtext = clewSel.options[clewindex].text;
			
			var newLineInfo = leveltext + "->" + linetext + "->" + clewtext + "；<br>";//组装显示信息
			    
			if(lineinfo.indexOf("<br>") > 0 ) {//判断线路是否增加
				var lineArr = lineinfo.split("<br>");
				for(var i = 0; i < lineArr.length; i++) {
					var oldLineInfo = lineArr[i] + "<br>";
					if(newLineInfo == oldLineInfo) {
						alert("该线路信息已经增加，请选择其他线路!");
						return;
					}
				}
			}
			
			lineinfo = lineinfo + newLineInfo;//组装线路信息，显示的部分。
			
			var subline = parentWin.document.getElementById('sublineid');//获取父窗口中存放线段ID的隐藏域
			
			var sublineid = subline.value;
			
			if(sublineid.length == 0) {//如果获取的父窗口线段的ID不存在...组装信息
				sublineid = clew;
			} else {
				sublineid = sublineid + ',' + clew;
			}
			subline.value = sublineid;//将组装好的信息放入父窗口的隐藏域中
			
			parentWin.document.getElementById('lineinfo').innerHTML = lineinfo;//将线路信息放入父窗口中
			
			alert("添加线路成功");
		}
		
		function resetInfo() {//重置所有线路信息
			lineinfo = "";
			parentWin.document.getElementById('lineinfo').innerHTML = "";
			parentWin.document.getElementById('sublineid').value = "";
		}
	
		function goBack() {
			if(lineinfo == "") {
				if(confirm("这次操作尚未选择任何路线，\n确定返回吗?")) {
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
	  				<td class="leftTdStyle">线路级别：</td>
	  				<td class="rigthTdStyle">
		  				<select class="commSel" id="level" onchange="onSelChangeLevel()" name="level">
		  					<option value="mention">===请选择线路级别===</option>
		  					<logic:iterate id="levelList" name="lineLevelList">
		  						<option value="<bean:write name="levelList" property="code"/>"><bean:write name="levelList" property="name"/></option>
		  					</logic:iterate>
		  				</select>
		  				<img id="Loadingimg" src="images/ajaxtags/indicator.gif" style="display:none">
		  			</td>
	  			</tr>
	  			
	  			<tr class="trStyle">
	  				<td class="leftTdStyle">光缆线路：<td class="rigthTdStyle">
		  				<select class="commSel" id="line" onchange="onSelChangeLine()" name="line">
		  					<option></option>
		  				</select>
		  			</td>
		  		</tr>
		  		
		  		<tr class="trStyle">
		  			<td class="leftTdStyle">光缆线段：</td>
		  			<td class="rigthTdStyle">
		  				<select class="commSel" id="clew">
		  					<option></option>
		  				</select>
		  			</td>
		  		</tr>
  			</table>
  			
  			<div style="margin-top: 10px;"> 
		  		<input type="hidden" value="0" id="lineSeq">
		  		<input type="button" class="button" value="添  加" onclick="saveInfo()">
		  		<input type="button" class="button" value="返  回" onclick="goBack()">
		  	</div>
  		</div>
  </body>
</html>
