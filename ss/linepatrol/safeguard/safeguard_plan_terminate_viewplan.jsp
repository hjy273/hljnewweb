<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>��Ѳ�ƻ�</title>
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
	function openMap(type,planid,calbelevel){
		var url= '/WEBGIS/gisextend/igis.jsp?userid=yht&actiontype=207&type='+type+'&tid='+planid;
		myleft=(screen.availWidth-650)/2;
		mytop=100;
		mywidth=650;
		myheight=500;
		window.open(url,"read_news","height="+myheight+",width="+mywidth+",status=1,resizable=no,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=yes");
	}
</script>
</head>
<body style="vertical-align:bottom;">
<template:titile value="�ƻ��ƶ�"/>
<form id="add_form" name="add_form" method="post" action="${ctx}/specialplan.do?method=savePlan">
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
				<c:if test="${specialEndPlan.endType==1}">
					�ƻ���ֹ
				</c:if>
				<c:if test="${specialEndPlan.endType==2}">
					�����ƶ�
				</c:if>
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width:20%;">��ֹʱ�䣺</td>
			<td class="tdulright">
				<fmt:formatDate  value="${specialEndPlan.endDate}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndDate"/>
				<c:out value="${formatEndDate}"></c:out>
			</td>
		</tr>
		<tr class="trcolor">
			<td class="tdulleft" style="width:20%;">�ƻ���ֹԭ��</td>
			<td class="tdulright" colspan="3">
				<c:out value="${specialEndPlan.reason}"></c:out>
			</td>
		</tr>
		<tr class="trcolor">
			<apptag:approve displayType="view" labelClass="tdulleft" valueClass="tdulright" objectId="${specialEndPlan.id}" objectType="LP_SPECIAL_ENDPLAN" colSpan="4" />
		</tr>
	</table>
	<div align="center" style="height:40px">
		<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
	</div>

<script type="text/javascript">
var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
</script>
</form>
</body>
</html>