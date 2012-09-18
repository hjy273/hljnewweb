<%@include file="/common/header.jsp"%>
<%

String fID = "1";
if(request.getParameter("fID") != null){
	fID = request.getParameter("fID");
}

%>

<script language="javascript">
<!--

function presetData(fID){
	//alert(fID);

	if(fID == "1"){
		return;
	}else{

		try{

			//alert(opener.sublineBean.sublinecablelist.length);

			for(var i = 0; i < opener.sublineBean.sublinecablelist.length; i++ ){
				txt = opener.sublineBean.cablename[i].value;
				val = opener.sublineBean.sublinecablelist[i].value;
				toList.options[toList.length] = new Option(txt,val);

			}

			for(var i = 0; i < opener.sublineBean.sublinecablelist.length; i++ ){

				for(var k = 0; k < sublineBean.sublinecablelist.options.length; k++){

					if(opener.sublineBean.sublinecablelist[i].value == sublineBean.sublinecablelist.options[k].value){

						sublineBean.sublinecablelist.options[k] = null;
						continue;

					}
				}

			}

			if(toList.length == 0){

				txt = opener.sublineBean.cablename.value;
				val = opener.sublineBean.sublinecablelist.value;
				toList.options[toList.length] = new Option(txt,val);


				for(var k = 0; k < sublineBean.sublinecablelist.options.length; k++){

					if(opener.sublineBean.sublinecablelist.value == sublineBean.sublinecablelist.options[k].value){

						sublineBean.sublinecablelist.options[k] = null;
						continue;

					}
				}

			}

		}catch(e){}

	}

}

function getSelectedItems(){

	for(var i = 0; i < sublineBean.sublinecablelist.options.length; i++){
		if(sublineBean.sublinecablelist.options[i].selected == true ){

			txt = sublineBean.sublinecablelist.options[i].text;
			val = sublineBean.sublinecablelist.options[i].value;
			toList.options[toList.length] = new Option(txt,val);

			sublineBean.sublinecablelist.options[i] = null;
			i--;
		}

	}

}



function getAllItems(){

}

function removeSelectedItems(){

	for(var i = 0; i < toList.options.length; i++){
		if(toList.options[i].selected == true ){

			txt = toList.options[i].text;
			val = toList.options[i].value;
			sublineBean.sublinecablelist.options[toList.length] = new Option(txt,val);

			toList.options[i] = null;
			i--;
		}

	}
}

function removeAllItems(){

}

function queryCableList(){
			var queryCondition="";
			if(typeof(document.getElementById("queryCondition"))!="undefined"){
				queryCondition=document.getElementById("queryCondition").value;
			}
		    $("cableInfoListTd").innerHTML = "数据加载中........<img src='${ctx}/images/onloading.gif' border='0'/>";
		     var pars = Math.random();
		     new Ajax.Request('${ctx}/sublineAction.do?method=queryCableList&&patrol_id=${param.patrol_id}&&query_value='+queryCondition,
		      {
		        method:"post",
		        parameters:pars,
		        evalScripts:true,encoding:"GBK",
		        onSuccess: function(transport){
		          var response = transport.responseText || "加载数据为空！";
		          $("cableInfoListTd").innerHTML = response;
		          var sublinecablelist=document.forms[0].elements["sublinecablelist"];
		          var existsublinecablelist=opener.sublineBean.sublinecablelist;
		          if(typeof(sublinecablelist)!="undefined"&&sublinecablelist!=null){
		          	var sublineCableOptions=sublinecablelist.options;
		          	for(i=sublineCableOptions.length-1;i>=0;i--){
		          		for(j=0;j<existsublinecablelist.length;j++){
		          			if(sublineCableOptions[i].value==existsublinecablelist[j].value){
		          				sublineCableOptions.remove(i);
		          				break;
		          			}
		          		}
		          	}
		          }
		        },
		        onFailure: function(){ $("cableInfoListTd").innerHTML = "加载失败。。。。。"; }// 
		      });
}
function setParent(){

	if (toList.options.length == 0){
		alert("请选择对应光缆！");
		return;

	}

	var conStr = "<br><table style='width:98%'>";

	for(var i = 0; i < toList.options.length; i++){

		conStr += "<tr><td style='width:100%'><input type=hidden name=sublinecablelist value='"+toList.options[i].value+"'>";
		conStr += "<input type=text name=cablename value = '"+toList.options[i].text+"' style ='border:0;background-color:transparent;width:280px;font-size:9pt'   readonly>";
		conStr += "</td></tr>";

	}
	conStr += "</table><br>";

	opener.listSpan.innerHTML = conStr;
	self.close();


}

//-->
</script>

<html>
	<head>
		<title>编辑对应光缆</title>
	</head>
	<body onload="queryCableList();presetData('<%=fID%>');">
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td height="24" align="center" class="title2">
					添加修改对应光缆信息
				</td>
			</tr>
			<tr>
				<td height="1">
					<img src="${ctx}/images/1px.gif" width="1px" height="1px">
				</td>
			</tr>
			<tr>
				<td height="24">
					查询条件：
					<input type="text" name="queryCondition" id="queryCondition" value="">
					<input type="button" name="btnQuery" value="查 询"
						onclick="queryCableList();">
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center">
			<tr>
				<td width="45%" align="center">
					<!--  -->
					<table width="90%" border="0" cellpadding="0" cellspacing="0"
						class="tabout">
						<tr>
							<th width="160px" class="thlist">
								该线段对应光缆
							</th>
						</tr>
						<tr>
							<td align="center">
								<select name="toList" multiple="multiple" size="15"
									style="width: 160px" class="multySelect">
								</select>
							</td>
						</tr>
					</table>
					<!--  -->
				</td>
				<td width="10%" align="right" align="center">
					<input type="button" name="button" value="  &lt; "
						onclick="getSelectedItems()">
					<br>
					<input type="button" name="button" value=" &gt;  "
						onclick="removeSelectedItems()">
				</td>
				<td width="45%" align="center">
					<html:form action="/sublineAction.do?method=addSubline">
						<!--  -->
						<br>
						<table width="90%" border="0" cellpadding="0" cellspacing="0"
							class="tabout">
							<tr>
								<th width="160px" class="thlist">
									全部光缆信息
								</th>
							</tr>
							<tr>
								<td align="center" id="cableInfoListTd">
								</td>
							</tr>
						</table>
						<!--  -->
					</html:form>

				</td>
			</tr>
		</table>
		<template:formSubmit>
			<td>
				<input type="button" class="button" onclick="setParent()" value="确定">
			</td>
			<td>
				<input type="button" class="button" onclick="self.close()"
					value="关闭">
			</td>
		</template:formSubmit>
	</body>
</html>
