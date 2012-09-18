<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>��Ѳ�ƻ�</title>
<script src="${ctx}/js/tab/SpryTabbedPanels.js" type="text/javascript"></script>
<link href="${ctx}/js/tab/SpryTabbedPanels.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jmpopups/jquery.jmpopups-0.5.1.js"></script>

<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />

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
	.popup-header .close-link {float:right;font-size:11px;}
	.popup-body {padding:10px;}
			

	
</style>
<style type="text/css">
	.text_line {
		border:1px solid #DDD;
		background:#EEE;
		BORDER-LEFT-STYLE: none;
	����BORDER-RIGHT-STYLE: none;
	����BORDER-TOP-STYLE: none;
	}
</style>
<!-- ���� -->
<script type="text/javascript">
	function isSet(){
		if($("set").checked == true){
			$("patrolgroupid").disabled = false;
		}else{
			$("patrolgroupid").disabled = true;
		}
	}
	function openW(url){
		win = new Ext.Window({
			layout : 'fit',
			width:650,
			height:400,
			resizable:true,
			closeAction : 'close',
			modal:true,
			html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
			plain: true
		});
		win.show(Ext.getBody());
	}
	//�����༭Ѳ�������
	function editCircuitTask(url,cl){
		url = url +"&cl="+cl+"&cn="+$("taskname_"+cl).value+"&pn="+$("patrolNum_"+cl).value;
		//openW(url);
		openAjaxPopup(url,"addCTask");
	}
	//�������������
	function editWatchTask(url){
		openAjaxPopup(url,"addWTask");
	}
	//����Ѳ������,�رյ�������
	function setCValue(cl,taskName,patrolNum,startTime,endTime){
		$("taskname_"+cl).value=taskName;
		$("patrolNum_"+cl).value=patrolNum;
		$("startRoute_"+cl).value = startTime;
		$("endRoute_"+cl).value=endTime;
		jQuery.closePopupLayer("addCTask");
	}
	//��������ݣ��رյ�������
	function setWValue(startTime,endTime,space){
		$("start_").value=startTime;
		$("end_").value = endTime;
		$("space_").value = space;
		jQuery.closePopupLayer("addWTask");
	}
	//���Ѳ������
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
        onFailure: function(){ alert('��������쳣......') }
		});
	}
	//��ն�������
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
        onFailure: function(){ alert('��������쳣......') }
		});
	}
	 function openwin() {
		 url="/WEBGIS/gisextend/igis.jsp?actiontype=201&userid=${LOGIN_USER.userID}";
		 window.open(url,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
	 }
</script>
<!-- ����֤ -->
<script type="text/javascript">
	function check(){
		if($("planName").value==""){
			jQuery("#planName1").empty();
			jQuery("#planName1").append("�ƻ����Ʋ���Ϊ��");
			return false;
		}
		if($("startDate").value==""){
			jQuery("#startDate1").empty();
			jQuery("#startDate1").append("����д�ƻ���ʼ����");
			return false;
		}
		if($("endDate").value==""){
			jQuery("#startDate1").empty();
			jQuery("#startDate1").append("����д�ƻ���������");
			return false;
		}
		if($("patrolgroupid").value==""){
			jQuery("#pgid").empty();
			jQuery("#pgid").append("��ѡ��Ѳ����");
			return false;
		}
		if($('approver')){
			if($('approver').value == ""){
				alert("����˲���Ϊ��");
				return false;
			}
		}
		if(confirm("ȷ���ύ��"+$("planName").value+"����?")){
			processBar(add_form);
			return true;
		}else{
			return false;
		}
	}
	var win;
	function viewHiddanger(id){
		var url = '${ctx}/hiddangerAction.do?method=viewHiddanger&id='+id;
		win = new Ext.Window({
			layout : 'fit',
			width:650,
			height:400,
			resizable:true,
			closeAction : 'close',
			modal:true,
			html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
			plain: true
		});
		win.show(Ext.getBody());
	}
	function viewRegion(pid){
		var url = '/WEBGIS/gisextend/igis.jsp?actiontype=203&pid='+pid+'&userid=${LOGION_USER.userID}';
		window.open(url,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
	}
	function close(){
		win.close();
	}
</script>
<%
	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
	String userId = userInfo.getUserID();
	String deptId = userInfo.getDeptID();
	request.setAttribute("userId", userId);
	request.setAttribute("deptId",deptId);
	request.getSession().setAttribute("LOGIN_USER", userInfo);
%>
</head>
<body style="vertical-align:bottom;">
<template:titile value="�ƻ��ƶ�"/>
<form id="add_form" name="add_form" method="post" action="${ctx}/specialplan.do?method=savePlan" onsubmit="return check()">
<table width="90%" align="center" border="1" class="tabout" cellpadding="0" cellspacing="1">
  <input type="hidden" name="businessId" value="${businessId}">
   <input type="hidden" value="${isApply}" name="isApply">
   <input type="hidden" value="${redo}" name="redo">
   <input type="hidden" value="${endId}" name="endId">
   <input type="hidden" value="${userId }" name="userId"/>
  <tr >
    <td class="tdulleft">�ƻ����ƣ�</td>
    <td class="tdulright"><input type="text" name="planName" id="planName" onChange='jQuery("#planName1").empty()' class="inputtext" value="${sPlan.planName}"/><input type="hidden" name="planType" value="${sPlan.planType}"></input>
    	<div id="planName1" style="color:red"></div>
    </td>
  </tr>
  <tr class=trcolor>
    <td class="tdulleft">�ƻ�ʱ�䣺</td>
    <td class="tdulright"><input name="startDate" id="startDate" onChange='jQuery("#startDate1").empty()' value="${sPlan.startDate }" class="Wdate required" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd 00:00:00',minDate:'%y-%M-%d'})"  type="text" />
      --
      <input name="endDate" id="endDate" value="${sPlan.endDate}" onChange='jQuery("#startDate1").empty()' class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd 23:59:59',minDate:'%y-%M-%d'})"  type="text" />
      <div id="startDate1" style="color:red"></div>
      </td>
   	  
  </tr>
  <tr  >
  	<td class="tdulleft">Ѳ����
  	</td>
  	<td class="tdulright">
		<select name="patrolgroupid" class="inputtext" style="width:200px" id="patrolgroupid">
		<c:forEach var="patrolgroup" items="${patrolgroups}">
			<option value="${patrolgroup.key}">${patrolgroup.value}</option>
		</c:forEach>
		</select>
		<div id="pgid" style="color:red"></div>
		<!--  input name="set" type="checkbox" id="set" onclick="isSet()" checked="checked" />ͳһ����Ѳ����-->
    </td>
  </tr>
  <tr class=trcolor>
    <td class="tdulleft">����</td>
    <td class="tdulright" >
      <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
          <li class="TabbedPanelsTab" tabindex="0">Ѳ &nbsp;&nbsp;&nbsp;&nbsp;��</li>
          <li class="TabbedPanelsTab" tabindex="1">�ֳ�����</li>
        </ul>
        <div class="TabbedPanelsContentGroup">
          <div class="TabbedPanelsContent">
         	<c:forEach var="cableLevel" items="${cableLevels}">
	         	<div id="task_${cableLevel.key}" style="font-size:12px;border: 1px;">
	         	 <input type="text" class="text_line" readonly="readonly" style="width:150px;color:blue;" id="taskname_${cableLevel.key}" value="${cableLevel.value }Ѳ������" />&nbsp;&nbsp;&nbsp;<input type="hidden" id="lineLevel" value="${cableLevel.key}"/><br>
	         	&nbsp;&nbsp;&nbsp;Ѳ��ʱ��:<input name="startRoute_${cableLevel.key}" id="startRoute_${cableLevel.key}" class="text_line" readonly="readonly" style="width:35px;" type="text" />--<input name="endRoute_${cableLevel.key}" id="endRoute_${cableLevel.key}" class="text_line" readonly="readonly" style="width:35px;" type="text" />
	         	Ѳ�ش�����<input type="text" class="text_line" readonly="readonly" id="patrolNum_${cableLevel.key}" style="width:15px;" value="0">
	         	&nbsp;&nbsp;<a href="javascript:;" onclick="editCircuitTask('${ctx}/specialplan.do?method=addTaskFrom','${cableLevel.key}');"/>�༭����</a>
	         	&nbsp;&nbsp;<a href="javascript:;" onclick="clearCircuitTask(${cableLevel.key})" title="���ѡ�������߶�" >���</a>
	         	<br />
	         	<fieldset>
				<legend>�����߶�</legend>
	         	<div id="tasksublinelabel_${cableLevel.key}">
	         		
	         	</div>
	         	</fieldset>
	         	
	         	</div>
         	</c:forEach>
          </div>
          <div class="TabbedPanelsContent">
          	<div>
            	����ʱ�Σ�<input id="start_" name="start_" class="text_line" readonly="readonly" style="width:30px;" type="text" />--<input name="end_" id="end_" class="text_line" readonly="readonly" style="width:30px;" type="text" />
            	�Զ����ͼ����<input name="space_" id="space_" class="text_line" readonly="readonly" style="width:15px;" type="text" value="1" />����
            	&nbsp;&nbsp;<a href="javascript:;" onclick="editWatchTask('${ctx}/specialplan.do?method=addWatchTaskForm');"/>�༭����</a>
                &nbsp;&nbsp;<a href="javascript:;" onclick="clearWathcTask()" title="���ѡ����������" >���</a>
                <fieldset>
				<legend>��������</legend>
                <div id="taskwatcharea">
                	
                </div>
                </fieldset>
            </div>
          </div>
        </div>
      </div>
    </td>
  </tr>
  <c:if test="${sPlan.planType eq '001'}">
  	<tr>
  		<td class="tdulleft">�鿴������Ϣ��</td>
  		<td class="tdulright"><a href="#" onclick="javascript:viewHiddanger('${businessId}')">�鿴</a></td>
  	</tr>
  </c:if>
  <c:if test="${sPlan.isApply == true}">
  	<c:choose>
  		<c:when test="${sPlan.planType eq '001'}">
  			<apptag:approverselect label="�����" inputName="approver"
  			 approverType="approver" objectType="LP_HIDDANGER_REPORT" objectId="${businessId}" 
  			 spanId="approverSpan" inputType="radio" notAllowName="reader"/>
			<apptag:approverselect label="������" inputName="reader" 
			 approverType="reader" objectType="LP_HIDDANGER_REPORT" objectId="${businessId}" 
			 spanId="readerSpan" notAllowName="approver"/>
  		</c:when>
  		<c:when test="${sPlan.planType eq '002' and redo=='redo'}">
  			<apptag:approverselect label="�����" inputName="approver"
  			 approverType="approver" objectType="LP_SAFEGUARD_PLAN" objectId="${businessId}" 
  			 spanId="approverSpan" inputType="radio" notAllowName="reader"/>
			<apptag:approverselect label="������" inputName="reader" 
			 approverType="reader" objectType="LP_SAFEGUARD_PLAN" objectId="${businessId}" 
			 spanId="readerSpan" notAllowName="approver"/>
  		</c:when>
  		<c:otherwise>
  			<apptag:approverselect label="�����" inputName="approver" spanId="approverSpan" inputType="radio" notAllowName="reader"/>
			<apptag:approverselect label="������" inputName="reader" spanId="readerSpan" notAllowName="approver"/>
  		</c:otherwise>
  	</c:choose>
  	
  </c:if>
</table>
<br />
<div style="text-align: center; width:100%">
  <input name="submit" type="submit" class="button" value="�� ��" />
  <input name="submit" type="reset" class="button" value="����" />
  <c:if test="${sPlan.planType!='002'}">
  	<input type="button" name="action" value="����" onclick="history.back()" class="button">
  </c:if>
  <c:if test="${sPlan.planType eq '002' and redo!='redo'}">
  	<input name="button" type="button" value="����" class="button" onclick="closeWin();"/>
  </c:if>
   <c:if test="${sPlan.planType eq '002' and redo=='redo'}">
  	<input type="button" name="action" value="����" onclick="history.back()" class="button">
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
