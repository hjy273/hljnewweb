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
			height:400,
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
	//�����༭Ѳ�������
	function editCircuitTask(url,cl){
		url = url +"&cl="+cl+"&cn="+$("taskname_"+cl).value+"&pn="+$("patrolNum_"+cl).value;
		openAjaxPopup(url,"addCTask");
	}
	//�������������
	function editWatchTask(url){
		openAjaxPopup(url,"addWTask");
	}
	//����Ѳ������,�رյ�������
	function setCValue(cl,taskName,patrolNum){
		$("taskname_"+cl).value=taskName;
		$("patrolNum_"+cl).value=patrolNum;
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
	function checkForm(){
		if($("endDate").value.length==0){
			alert("��ֹʱ�䲻��Ϊ�գ�");
			return false;
		}
		if($("reason").value.length==0){
			alert("��ֹԭ����Ϊ�գ�");
			return false;
		}
		if(valCharLength($("reason").value)>1024){
			alert("��ֹԭ���ܳ���1024���ַ���");
		    return;
		}
		//�ж��ַ�����
		function valCharLength(Value){
			var j=0;
			var s = Value;
			for(var i=0; i<s.length; i++) {
				if (s.substr(i,1).charCodeAt(0)>255) {
					j  = j + 2;
				} else { 
					j++;
				}
			}
			return j;
		}
	}
</script>
</head>
<body style="vertical-align:bottom;">
<template:titile value="�ƻ��ƶ�"/>
<form id="add_form" name="add_form" method="post" onsubmit="return checkForm();" action="${ctx}/specialEndPlanAction.do?method=addSpecialEndPlan">
<table width="80%" align="center" border="1" class="tabout" cellpadding="0" cellspacing="1">
  <tr >
    <td class="tdulleft">�ƻ����ƣ�</td>
    <td class="tdulright">${sp.planName}</td>
  </tr>
  <tr class=trcolor>
    <td class="tdulleft">�ƻ�ʱ�䣺</td>
    <td class="tdulright"><bean:write name="sp" property="startDate" format="yyyy-MM-dd HH:mm:ss"/> -- <bean:write name="sp" property="endDate" format="yyyy-MM-dd HH:mm:ss"/></td>
  </tr>
  <tr  >
  	<td class="tdulleft">Ѳ���飺</td>
  	<td class="tdulright">
		<apptag:assorciateAttr table="patrolmaninfo" label="patrolname" key="patrolid" keyValue="${sp.patrolGroupId}" />
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
         		<c:set var="circuitTask" value="${circuitTasks[cableLevel.key]}"/>
         		<c:set var="taskName" value="${cableLevel.value}Ѳ������"/> 
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
	         	&nbsp;&nbsp;&nbsp;Ѳ��ʱ��:<input name="startRoute_${cableLevel.key}" class="text_line" readonly="readonly" style="width:30px;" value="${startTime}" type="text" />--<input name="endRoute_${cableLevel.key}" class="text_line" value="${endTime}" readonly="readonly" style="width:30px;" type="text" />
	         	Ѳ�ش�����<input type="text" class="text_line" readonly="readonly" id="patrolNum_${cableLevel.key}" style="width:15px;" value="${patrolNum}">
	         	<br />
	         	<fieldset>
				<legend>�����߶�</legend>
	         	<div id="tasksublinelabel_${cableLevel.key}">
	         		<a href="javascript:openMap('01','${sp.id}','${cableLevel.key}')">
	         		<c:forEach var="entry" items="${circuitTask.taskSubline}">
	         			<c:out value="${entry.value.sublineName}"/>;
	         		</c:forEach>
	         		</a>
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
            	����ʱ�Σ�<input name="start_" class="text_line" value="${startDate}" readonly="readonly" style="width:40px;" type="text" />--<input name="end_" class="text_line" readonly="readonly" value="${endDate}" style="width:40px;" type="text" />
            	�Զ����ͼ����<input name="space_" class="text_line"  readonly="readonly" style="width:40px;" type="text" value="${space}" />����
                <fieldset>
				<legend>��������</legend>
                <div id="taskwatcharea">
               		<a href="javascript:openMap('02','${sp.id}')">
                	${areas}
                	</a>
                </div>
                </fieldset>
            </div>
          </div>
        </div>
      </div>
  </td>
     
  </tr>
	
</table>
	<table align="center" cellpadding="1" style="border-bottom:0px;border-top: 0px;" cellspacing="0" class="tabout" width="80%">
		<tr class="trcolor">
			<td class="tdulleft" style="width:20%;">��ֹ���ͣ�</td>
			<td class="tdulright">
				<input type="radio" name="endType" value="1" checked="checked">�ƻ���ֹ
				<input type="radio" name="endType" value="2">�����ƶ�
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width:20%;">��ֹʱ�䣺</td>
			<td class="tdulright">
				<input type="hidden" name="planId" value="${sp.id }">
				<input type="hidden" name="prevStartDate" id="prevStartDate" value="<bean:write name='sp' property='startDate' format="yyyy/MM/dd HH:mm:ss"/>">
				<input type="hidden" name="prevEndDate" value="<bean:write name='sp' property='endDate' format="yyyy/MM/dd HH:mm:ss"/>">
				<input name="endDate" class="Wdate" id="endDate" style="width:150px;"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'#F{$dp.$D(\'prevStartDate\')}'})" readonly/>
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width:20%;">�ƻ���ֹԭ��</td>
			<td class="tdulright" colspan="3">
				<textarea class="textarea" rows="3" id="reason" name="reason"></textarea>
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trcolor">
			<apptag:approverselect inputName="approveId,mobiles" colSpan="2" 
					inputType="radio" label="�����" notAllowName="reader"
					approverType="approver" objectId="${businessId }" objectType="LP_SAFEGUARD_PLAN" />
		</tr>
		<tr class="trcolor">
			<apptag:approverselect label="������" inputName="reader,readerPhones"
					spanId="readerSpan" notAllowName="approveId" colSpan="2"
					approverType="reader" objectId="${businessId }" objectType="LP_SAFEGUARD_PLAN" />
		</tr>
	</table>
	<div style="height:20px; margin-left: 10%; width: 80%; margin-top: 5px;">
		<font color="red">�������Ϊ���������������������д���ޡ�</font>
	</div>
	<div align="center" style="height:40px">
		<html:submit property="action" styleClass="button">�ύ</html:submit> &nbsp;&nbsp;
		<html:reset property="action" styleClass="button">��д</html:reset>&nbsp;&nbsp;
		<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
	</div>

<script type="text/javascript">
var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
</script>
</form>
</body>
</html>