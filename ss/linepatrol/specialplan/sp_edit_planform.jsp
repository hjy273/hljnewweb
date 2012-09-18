<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>特巡计划</title>
<script src="${ctx}/js/tab/SpryTabbedPanels.js" type="text/javascript"></script>
<link href="${ctx}/js/tab/SpryTabbedPanels.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jmpopups/jquery.jmpopups-0.5.1.js"></script>
<script type="text/javascript">
	jQuery.setupJMPopups({
		screenLockerBackground: "#EFE9F4",
		screenLockerOpacity: "0.7"
	});
		
	function openAjaxPopup(myUrl,name) {
		jQuery.openPopupLayer({
			name: name,
			width: 500,
			url: myUrl
		});
	}
</script>
<style type="text/css" media="screen">
	#body {margin:0; font-family:"Trebuchet MS"; line-height:120%; color:#333;}
	h1 {margin-bottom:50px; font-size:40px; font-weight:normal;}
	p {margin:0; padding:0 0 30px; font-size:12px;}
	pre {font-size:12px; font-family:"Consolas","Monaco","Bitstream Vera Sans Mono","Courier New","Courier"; line-height:120%; background:#F4F4F4; padding:10px;}
	#general {margin:30px;}
			
	#myHiddenDiv {display:none;}
	.popup {background:#FFF; border:1px solid #333; padding:1px;}
	.popup-header {height:20px; padding:7px; background:url("${ctx}/js/jmpopups/bgr_popup_header.jpg") repeat-x;}
	.popup-header h2 {margin:0; padding:0; font-size:18px; float:left;}
	.popup-header .close-link {float:right; font-size:11px;}
	.popup-body {padding:10px;}
			
	form {margin:0; padding:0;}
	form * {font-size:12px;}
	
</style>
<style type="text/css">
	.text_line {
		border:1px solid #DDD;
		background:#EEE;
		BORDER-LEFT-STYLE: none;
	　　BORDER-RIGHT-STYLE: none;
	　　BORDER-TOP-STYLE: none;
	}
</style>
<!-- 交互 -->
<script type="text/javascript">
	function isSet(){
		if($("set").checked == true){
			$("patrolgroupid").disabled = false;
		}else{
			$("patrolgroupid").disabled = true;
		}
	}
	//弹出编辑巡回任务框，
	function editCircuitTask(url,cl){
		url = url +"&cl="+cl+"&cn="+$("taskname_"+cl).value+"&pn="+$("patrolNum_"+cl).value;
		openAjaxPopup(url,"addCTask");
		
	}
	//弹出盯防任务框
	function editWatchTask(url){
		openAjaxPopup(url,"addWTask");
	}
	//回填巡回数据,关闭弹出窗口
	function setCValue(cl,taskName,patrolNum,startTime,endTime){
		$("taskname_"+cl).value=taskName;
		$("patrolNum_"+cl).value=patrolNum;
		$("startRoute_"+cl).value = startTime;
		$("endRoute_"+cl).value=endTime;
		jQuery.closePopupLayer("addCTask");
	}
	//回填盯防数据，关闭弹出窗口
	function setWValue(startTime,endTime,space){
		$("start_").value=startTime;
		$("end_").value = endTime;
		$("space_").value = space;
		jQuery.closePopupLayer("addWTask");
	}
	//清空巡回任务
	function clearCircuitTask(cl){
		var url = 'specialplan.do?method=clearCircuitTask&lineLevel='+cl;
		new Ajax.Request(url,{
        method:'post',
        evalScripts:true,
        onSuccess:function(transport){
			jQuery('#tasksublinelabel_'+cl).empty();
			jQuery('#tasksublinelabel_'+cl).append(transport.responseText);
			$('startRoute_'+cl).value='';
			$('endRoute_'+cl).value='';
			$('patrolNum_'+cl).value='0';
        },
        onFailure: function(){ alert('请求服务异常......') }
		});
	}
	//清空盯防任务
	function clearWathcTask(){
		var url = 'specialplan.do?method=clearWatchTask';
		new Ajax.Request(url,{
        method:'post',
        evalScripts:true,
        onSuccess:function(transport){
			jQuery('#taskwatcharea').empty();
			jQuery('#taskwatcharea').append(transport.responseText);
			$("start_").value='';
			$("end_").value = '';
			$("space_").value = '';
        },
        onFailure: function(){ alert('请求服务异常......') }
		});
	}
	function viewRegion(pid){
		var url = '/WEBGIS/gisextend/igis.jsp?actiontype=203&pid='+pid+'&userid=${LOGION_USER.userID}';
		window.open(url,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
	}
	 function openwin() {
		 url="/WEBGIS/gisextend/igis.jsp?actiontype=201&userid=${LOGIN_USER.userID}";
		 window.open(url,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
	 }
</script>
<!-- 表单验证 -->
<script type="text/javascript">
	function check(){
		if($("planName").value==""){
			jQuery("#planName1").empty();
			jQuery("#planName1").append("计划名称不能为空");
			return false;
		}
		if($("startDate").value==""){
			jQuery("#startDate1").empty();
			jQuery("#startDate1").append("请填写计划开始日期");
			return false;
		}
		if($("patrolgroupid").value==""){
		}
		if($('approver')){
			if($('approver').value == ""){
				alert("审核人不能为空");
				return false;
			}
		}
		if(confirm("确认提交“"+$("planName").value+"”吗?")){
			return true;
		}else{
			return false;
		}
	}
</script>
<%
	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
	String userId = userInfo.getUserID();
	request.setAttribute("userId", userId);
%>
</head>
<body style="vertical-align:bottom;">
<template:titile value="计划制定"/>
<form id="add_form" name="add_form" method="post" action="${ctx}/specialplan.do?method=savePlan" onsubmit="return check()">
<table width="90%" align="center" border="1" class="tabout" cellpadding="0" cellspacing="1">
  <input type="hidden" value="${businessId}" name="businessId">
  <input type="hidden" value="${isApply}" name="isApply">
  <input type="hidden" value="${type}" name="addOrEdit">
  <input type="hidden" value="${redo }" name="redo">
  <input type="hidden" value="${userId }" name="userId">
  <tr>
    <td class="tdulleft">计划名称：</td>
    <td class="tdulright">
    	<input type="text" name="planName" id="planName" onChange='jQuery("#planName1").empty()' class="inputtext" value="${sp.planName}"/><input type="hidden" name="planType" value="${sp.planType}"></input>
    	<input type="hidden" name="id" value="${sp.id}"> 
    	<div id="planName1" style="color:red"></div>
    </td>
  </tr>
  <tr class=trcolor>
    <td class="tdulleft">计划时间：</td>
    <td class="tdulright"><input name="startDate" id="startDate" onChange='jQuery("#startDate1").empty()' value="<fmt:formatDate value="${sp.startDate}" pattern="yyyy/MM/dd 00:00:00"/>" class="Wdate required" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'%y-%M-%d'})"  type="text" />
      --
      <input name="endDate" id="endDate" value="<fmt:formatDate value="${sp.endDate}" pattern="yyyy/MM/dd HH:mm:ss"/>" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd 23:59:59',minDate:'%y-%M-%d'})"  type="text" />
      <div id="startDate1" style="color:red"></div>
    </td>
  </tr>
  <tr>
  	<td class="tdulleft">巡检组：</td>
  	<td class="tdulright">
		<select name="patrolgroupid" class="inputtext" style="width:200px" id="patrolgroupid">
		<c:forEach var="patrolgroup" items="${patrolgroups}">
			<c:set var="check" value=""></c:set>
			<c:if test="${sp.patrolGroupId == patrolgroup.key}">
				<c:set var="check" value="checked"></c:set>
			</c:if>
			<option value="${patrolgroup.key}" ${check}>${patrolgroup.value}</option>
		</c:forEach>
		</select>
		<!--  input name="set" type="checkbox" id="set" onclick="isSet()" checked="checked" />统一设置巡检组-->
    </td>
  </tr>
  <tr class=trcolor>
    <td class="tdulleft">任务：</td>
    <td class="tdulright" >
      <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
          <li class="TabbedPanelsTab" tabindex="0">巡 &nbsp;&nbsp;&nbsp;&nbsp;回</li>
          <li class="TabbedPanelsTab" tabindex="1">现场盯防</li>
        </ul>
        <div class="TabbedPanelsContentGroup">
          <div class="TabbedPanelsContent">
         	<c:forEach var="cableLevel" items="${cableLevels}">
         		<c:set var="circuitTask" value="${circuitTasks[cableLevel.key]}"/>
         		<c:set var="taskName" value="${cableLevel.value}巡回任务"/> 
         		<c:set var="patrolNum" value="0"/>
         		<c:set var="startTime" value=""/>
         			<c:set var="endTime" value=""/>
         		<c:if test="${circuitTask != null}">
         			<c:set var="taskName" value="${circuitTask.taskName}"/> 
         			<c:set var="patrolNum" value="${circuitTask.patrolNum}"/>
         			<c:set var="startTime" value="${circuitTask.startTime}"/>
         			<c:set var="endTime" value="${circuitTask.endTime}"/>
         		</c:if>
	         	<div id="task_${cableLevel.key}" style="font-size:12px;border: 1px;">
	         	 <input type="text" class="text_line" readonly="readonly" style="width:150px;color:blue;" id="taskname_${cableLevel.key}" value="${taskName}" />&nbsp;&nbsp;&nbsp;<input type="hidden" id="lineLevel" value="${cableLevel.key}"/> <br>
	         	&nbsp;&nbsp;&nbsp;巡回时段:<input name="startRoute_${cableLevel.key}" id="startRoute_${cableLevel.key}" class="text_line" readonly="readonly" style="width:35px;" value="${startTime}" type="text" />--<input name="endRoute_${cableLevel.key}" id="endRoute_${cableLevel.key}" class="text_line" value="${endTime}" readonly="readonly" style="width:35px;" type="text" />
	         	巡回次数：<input type="text" class="text_line" readonly="readonly" id="patrolNum_${cableLevel.key}" style="width:15px;" value="${patrolNum}">
	         	&nbsp;&nbsp;<a href="javascript:;" onclick="editCircuitTask('${ctx}/specialplan.do?method=addTaskFrom','${cableLevel.key}');"/>编辑任务</a>
	         	&nbsp;&nbsp;<a href="javascript:;" onclick="clearCircuitTask('${cableLevel.key}')" title="清空选择任务线段" >清空</a>
	         	<br />
	         	<fieldset>
				<legend>任务线段</legend>
	         	<div id="tasksublinelabel_${cableLevel.key}">
	         		<c:forEach var="entry" items="${circuitTask.taskSubline}">
	         			<c:out value="${entry.value.sublineName}"/>;
	         		</c:forEach>
	         	</div>
	         	</fieldset>
	         	
	         	</div>
         	</c:forEach>
          </div>
          <div class="TabbedPanelsContent">
          	<div>
          		<c:set var="areas" value=""/>
          		<c:forEach var="entry" items="${watchTasks}">
          			<c:set var="startDate" value="${entry.value.startTime}"/>
          			<c:set var="endDate" value="${entry.value.endTime}"/>
          			<c:set var="space" value="${entry.value.space}"/>
          			<c:set var="areas" value="${entry.value.areaName};${areas}"/>
          		</c:forEach>
            	盯防时段：<input id="start_" name="start_" class="text_line" value="${startDate}" readonly="readonly" style="width:30px;" type="text" />--<input id="end_" name="end_" class="text_line" readonly="readonly" value="${endDate}" style="width:30px;" type="text" />
            	自动发送间隔：<input id="space_" name="space_" class="text_line"  readonly="readonly" style="width:15px;" type="text" value="${space}" />分钟
            	&nbsp;&nbsp;<a href="javascript:;" onclick="editWatchTask('${ctx}/specialplan.do?method=addWatchTaskForm');"/>编辑任务</a>
                &nbsp;&nbsp;<a href="javascript:;" onclick="clearWathcTask()" title="清空选择任务区域" >清空</a>
                <fieldset>
				<legend>任务区域</legend>
                <div id="taskwatcharea">
                	${areas}
                </div>
                </fieldset>
            </div>
          </div>
        </div>
      </div>
  </td>
  </tr>
  <c:if test="${isApply}">
	<c:choose>
  		<c:when test="${sp.planType eq '001'}">
  			<apptag:approverselect label="审核人" inputName="approver"
  			 approverType="approver" objectType="LP_HIDDANGER_REPORT" objectId="${businessId}" 
  			 spanId="approverSpan" inputType="radio" notAllowName="reader"/>
			<apptag:approverselect label="抄送人" inputName="reader" 
			 approverType="reader" objectType="LP_HIDDANGER_REPORT" objectId="${businessId}" 
			 spanId="readerSpan" notAllowName="approver"/>
  		</c:when>
  		<c:when test="${ptype eq '002' and redo eq 'redo'}">
  			<apptag:approverselect label="审核人" inputName="approver"
  			 approverType="approver" objectType="LP_SAFEGUARD_PLAN" objectId="${businessId}" 
  			 spanId="approverSpan" inputType="radio" notAllowName="reader"/>
			<apptag:approverselect label="抄送人" inputName="reader" 
			 approverType="reader" objectType="LP_SAFEGUARD_PLAN" objectId="${businessId}" 
			 spanId="readerSpan" notAllowName="approver"/>
  		</c:when>
  		<c:otherwise>
  			<apptag:approverselect label="审核人" inputName="approver" spanId="approverSpan" inputType="radio" notAllowName="reader"/>
			<apptag:approverselect label="抄送人" inputName="reader" spanId="readerSpan" notAllowName="approver"/>
  		</c:otherwise>
  	</c:choose>
  </c:if>
  <c:if test="${ptype eq '001'}">
  	<apptag:approve labelClass="tdulleft" displayType="view" objectId="${sp.id}" objectType="LP_HIDDANGER_PLAN" />
  </c:if>
</table>
<br />
<div style="text-align: center; width:100%">
  <input name="submit" type="submit" class="button" value="保存" />
  <input name="submit" type="reset" class="button" value="重置" />
  <c:if test="${ptype!='002'}">
  	<input type="button" name="action" value="返回" onclick="history.back()" class="button">
  </c:if>
  <c:if test="${ptype eq '002' and redo!='redo'}">
  	<input name="button" type="button" value="返回" class="button" onclick="closeWin();"/>
  </c:if>
  <c:if test="${ptype eq '002' and redo eq 'redo'}">
  	<input type="button" name="action" value="返回" onclick="history.back()" class="button">
  </c:if>
</div>
<script type="text/javascript">
var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
function closeWin(){
	parent.win.close();
}
</script>
</form>
</body>
</html>
