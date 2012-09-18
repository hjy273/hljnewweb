<%@include file="/common/header.jsp"%>
<script language="JavaScript" src="${ctx}/linepatrol/js/selectListTools.js"></script>
<script language="javascript">
	var xmlHttp;
    function createXMLHttpRequest() {
        if (window.ActiveXObject) {
           xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        } 
        else if (window.XMLHttpRequest) {
           xmlHttp = new XMLHttpRequest();                
        }
    }
	function search() {
        createXMLHttpRequest();
        //查询右侧已经存在的光缆
        var subline = "";
        var target = document.getElementById("target");
        if(target.options.length>0){
        	for(var i=0; i<target.options.length; i++){
	            subline += target.options[i].value+",";
	     	}
        }
        var maintenanceId = document.getElementById("maintenanceId").value;
        var segmentname = document.getElementById("segmentname").value;
        var url="${ctx}/pipeAction.do?method=searchPipe&segmentname="+segmentname+"&maintenanceId="+maintenanceId+"&subline="+subline;
        
        xmlHttp.open("post", url, false);
        xmlHttp.onreadystatechange = startCallback;
        xmlHttp.send(null);
    }
	function startCallback() {
        if (xmlHttp.readyState == 4) {
            var left = document.getElementById("left");
        	left.innerHTML = xmlHttp.responseText;
        }
    }
    
    function check(){
		var target = document.getElementById("target");
		var targetInput = document.getElementById("targetSubline");
		var subline = "";
		if(target.options.length==0){
			alert("请选择要重新分配的管道！");
			return false;
		}
		for(var i=0; i<target.options.length; i++){
             subline += target.options[i].value+",";
     	}
     	targetInput.value=subline.substring(0,subline.length-1)
		return true;
	}
	//前往上一步
	function goAssign1(){
		var maintenanceId = document.getElementById("maintenanceId").value;
		var url="${ctx}/pipeAction.do?method=pipeAssignOne&maintenanceId="+maintenanceId;
		window.location.href=url;
	}
</script>
<template:titile value="管道重新分配" />
<body onload="search()">
	<html:form method="Post"
		action="/pipeAction.do?method=pipeAssignThree">
		<table border="0" align="center" cellspacing="0" cellpadding="0">
			<tr>
				<td valign="top">
					<input type="hidden" name="maintenanceId" id="maintenanceId" value="${maintenanceId }">
					关键字：<input type="text" name="segmentname" id="segmentname">
					<input type="button" value="查询" onclick="search()">
					<br>
					<span id="left"></span>
				</td>
				<td valign="middle" align="center">
					<br />
					<br />
					<input type="button" value="&lt;-"
						onclick="moveSelected(document.all.target,document.all.original)" />
					<br />
					<br />
					<input type="button" value="-&gt;"
						onclick="moveSelected(document.all.original,document.all.target)" />
					<br />
					<br />
					<input type="button" value="&lt;&lt;--"
						onclick="moveAll(document.all.target,document.all.original);" />
					<br />
					<br />
					<input type="button" value="--&gt;&gt;"
						onclick="moveAll(document.all.original,document.all.target)" />
					<br />
					<br />
				</td>
				<td>
					<label for="rightTitle">
						分配线段
					</label>
					<br />
					<select name="target" style="width: 200px" size="15"
						multiple="multiple" id="target">
						<c:if test="${not empty list}">
							<c:forEach items="${list}" var="bean">
								<option value="<bean:write name='bean' property='id'/>"><bean:write name='bean' property="work_name"/></option>
							</c:forEach>
						</c:if>
					</select>
				</td>
			</tr>
		</table>
		<input type="hidden" name="sublineid" id="targetSubline" />
		<template:formSubmit>
			<td>
				<input type="button" value="上一步" class="button"
					onclick="goAssign1();" />
			</td>
			<td>
				<html:submit styleClass="button" onclick="return check()">下一步</html:submit>
			</td>
		</template:formSubmit>
	</html:form>
	<table width="500" border="0" align="center" cellpadding="3"
		cellspacing="0">
		<tr>
			<td>
				<div align="left" style="color: red">
					注意：
					<br>
					* 关键字包括：工程名称、管道地点
					<br>
				</div>
			</td>
		</tr>
	</table>
</body>
