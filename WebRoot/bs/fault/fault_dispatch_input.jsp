<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="${ctx}/js/jQuery/pikachoose/styles/bottom.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="${ctx}/js/jQuery/pikachoose/lib/jquery.pikachoose.js"></script>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<script src="${ctx}/bs/fault/js/fault_common.js" type="text/javascript"></script>
<script src="${ctx}/bs/fault/js/fault_dispatch_input.js"
	type="text/javascript"></script>
<script language="javascript" type="text/javascript">
	setContextPath("${ctx}");
	function check() {
		if (jQuery("#findType").val() == "") {
			alert("û��ѡ���ַ�ʽ��");
			return false;
		}
		if (jQuery("#troubleLevel").val() == "") {
			alert("û��ѡ����ϼ���");
			return false;
		}
		if (jQuery("#patrolGroupId").val() == "") {
			alert("û��ѡ��ά���飡");
			return false;
		}
		return true;
	}
	Ext
			.onReady(function() {
				jQuery("#faultDispatchForm").validate({
					debug : true,
					submitHandler : function(form) {
						if (check()) {
							form.submit();
						}
					}
				});
				//���ַ�ʽ		faultAlert.findType			FIND_TYPE		fault_alert_findtype
				if (document.getElementById("fault_alert_findtype")) {
					var find_type = new Appcombox(
							{
								width : 180,
								hiddenName : 'faultAlert.findType',
								hiddenId : 'findType',
								emptyText : '��ѡ��',
								dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=FIND_TYPE',
								dataCode : 'CODEVALUE',
								dataText : 'LABLE',
								allowBlank : false,
								renderTo : 'fault_alert_findtype'
							});
					find_type
							.on(
									"select",
									function(combo, record) {
										if (combo.getValue() == "B17") {
											document
													.getElementById("emos_title_div").style.display = "";
											document
													.getElementById("emos_value_div").style.display = "";
										} else {
											document
													.getElementById("emos_title_div").style.display = "none";
											document
													.getElementById("emos_value_div").style.display = "none";
										}
									});
					find_type.filter(function(record, id) {
						if (record.get("CODEVALUE") != 'B16')
							return true;
						else
							return false;
					});
				}
				// ���ϼ���		faultAlert.troubleLevel		TROUBLE_LEVEL	fault_alert_troublelevel
				var trouble_level = new Appcombox(
						{
							width : 180,
							hiddenName : 'faultAlert.troubleLevel',
							hiddenId : 'troubleLevel',
							emptyText : '��ѡ��',
							dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=TROUBLE_LEVEL',
							dataCode : 'CODEVALUE',
							dataText : 'LABLE',
							allowBlank : false,
							renderTo : 'fault_alert_troublelevel'
						});
				trouble_level
						.setComboValue(
								'${faultAlert.troubleLevel}',
								'<apptag:dynLabel objType="dic" id="${faultAlert.troubleLevel}" dicType="TROUBLE_LEVEL"></apptag:dynLabel>');
				setResourceItem('#faultAlert_stationId');
				getSelectResourceList('${businessType}',
						'${faultAlert.stationId}', '${faultAlert.stationType}');
			});
</script>
<body>
	<template:titile value="�����ɵ�" />
	<s:form action="faultDispatchAction_dispatch" id="faultDispatchForm"
		name="form" method="post">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<c:if test="${faultAlert.findType=='B16' }">
				<tr class="trwhite">
					<td class="tdulright" colspan="4" style="width: 100%">
						<b>����ͼƬ</b>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulright" colspan="4"
						style="width: 100%; padding: 5px; text-align: center;"
						id="photosTd">
					</td>
				</tr>
			</c:if>
			<tr class="trwhite">
				<td class="tdulright" colspan="4" style="width: 100%">
					<b>���ϵ���Ϣ</b>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���ϱ��⣺
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<input id="businessType" name="businessType"
						value="${businessType }" type="hidden" />
					<input id="faultAlert_id" name="faultAlert.id"
						value="${faultAlert.id }" type="hidden" />
					<input id="faultAlert.businessType" name="faultAlert.businessType"
						value="${faultAlert_businessType }" type="hidden" />
					<input id="faultDispatch_alarmId" name="faultDispatch.alarmId"
						value="${faultAlert.id }" type="hidden" />
					<input id="faultAlert_troubleTitle" name="faultAlert.troubleTitle"
						value="${faultAlert.troubleTitle }" type="input"
						class="inputtext required" style="width: 180px" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���ַ�ʽ��
				</td>
				<td class="tdulright" style="width: 35%">
					<c:if test="${faultAlert.findType=='B16' }">
						<input id="faultAlert.findType" name="faultAlert.findType"
							value="${faultAlert.findType }" type="hidden" />
						<apptag:dynLabel objType="dic" id="${faultAlert.findType}"
							dicType="FIND_TYPE"></apptag:dynLabel>
					</c:if>
					<c:if test="${faultAlert.findType!='B16' }">
						<div id="fault_alert_findtype" style="float: left"></div>
						<font style="color: red; float: left; clear: right;">&nbsp;*</font>
					</c:if>
				</td>
				<td class="tdulleft" style="width: 15%">
					<c:if test="${faultAlert.findType=='B17' }">
					</c:if>
					<div id="emos_title_div" style="display: none;">
						EMOS���ţ�
					</div>
				</td>
				<td class="tdulright" style="width: 35%">
					<c:if test="${faultAlert.findType=='B17' }">
						<!-- 
						<input id="faultAlert.eomsId" name="faultAlert.eomsId"
							value="${faultAlert.eomsId }" type="input" class="inputtext"
							style="width: 180px" />
						 -->
					</c:if>
					<c:if test="${faultAlert.findType!='B17' }">
						<!-- 
						<input id="faultAlert.eomsId" name="faultAlert.eomsId" value=""
							type="hidden" />
						 -->
					</c:if>
					<div id="emos_value_div" style="display: none;">
						<input id="faultAlert.eomsId" name="faultAlert.eomsId" value=""
							type="input" class="inputtext" style="width: 180px" />
					</div>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���Ϸ���ʱ�䣺
				</td>
				<td class="tdulright" style="width: 35%">
					<fmt:formatDate value="${faultAlert.troubleTime }"
						pattern="yyyy-MM-dd HH:mm:ss" var="troubleTime" />
					<input id="faultAlert.troubleTime" name="faultAlert.troubleTime"
						value="${troubleTime }" type="input"
						class="Wdate inputtext required" style="width: 180px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d'})" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					�Ƿ�������ϣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<!-- ${faultAlert.isInstancy } -->
					<c:set var="checkedNoRadio" value="checked"></c:set>
					<c:set var="checkedYesRadio" value=""></c:set>
					<c:if test="${faultAlert.isInstancy==1 }">
						<c:set var="checkedYesRadio" value="checked"></c:set>
						<c:set var="checkedNoRadio" value=""></c:set>
					</c:if>
					<c:if test="${faultAlert.isInstancy==2 }">
						<c:set var="checkedYesRadio" value=""></c:set>
						<c:set var="checkedNoRadio" value="checked"></c:set>
					</c:if>
					<input name="faultAlert.isInstancy" value="2" type="radio" ${checkedNoRadio } />
					��
					<input name="faultAlert.isInstancy" value="1" type="radio" ${checkedYesRadio } />
					��
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���ϼ���
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="fault_alert_troublelevel" style="float: left"></div>
					<font style="color: red; float: left; clear: right;">&nbsp;*</font>
				</td>
				<td class="tdulleft" style="width: 15%">
					�������ޣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="deadline" style="width: 180px"
						name="faultDispatch.deadline" class="Wdate inputtext required"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'faultAlert.troubleTime\')}'})" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����������
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<textarea id="troubleDesc" name="faultAlert.troubleDesc"
						class="inputtext" style="width: 660px; height: 80px;">${faultAlert.troubleDesc }</textarea>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����վ�㣺
				</td>
				<td class="tdulright" style="width: 85%" colspan="3">
					<input name="resourceName" id="resourceName" type="text"
						onblur="clearResourcesOptions();getSelectResourceList('${businessType}','','');"
						class="inputtext" style="width: 180px" />
					��������Դ���ƽ���ģ����ѯ��
					<br />
					<select id="faultAlert_stationId" name="faultAlert.stationId"
						class="inputtext required" onchange="setStationInfo();"
						style="width: 180px">
						<option value="">
							��ѡ��
						</option>
					</select>
					<input type="hidden" name="faultAlert.stationType" id="stationType" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���ϵص㣺
				</td>
				<td class="tdulright" style="width: 35%">
					<input id="address" name="faultAlert.address"
						value="${faultAlert.address }" type="input"
						class="inputtext required" style="width: 180px" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					ά���飺
				</td>
				<td class="tdulright" style="width: 35%">

							<input id="patrolGroupName" name="patrolGroupName" class="inputtext required" readonly="readonly" />
							<a href="javascript:search();"> <img border="0"
								src="${ctx}/images/selectcode.gif" /> </a>
							<font style="color: red;">*</font>
							<input type="hidden" name="faultDispatch.maintenanceId"
								id="maintenanceId" />
							<input type="hidden" name="faultDispatch.patrolGroup"
								id="patrolGroupId" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��ע��
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<textarea id="remark" name="faultDispatch.remark" class="inputtext"
						style="width: 660px; height: 80px;"></textarea>
				</td>
			</tr>
		</table>
		<table width="100%" style="border: 0px">
			<tr>
				<td style="text-align: center; border: 0px">
					<html:submit styleClass="button">�ɵ�</html:submit>
					<input type="button" class="button" onclick="history.back()"
						value="����">
				</td>
			</tr>
		</table>
	</s:form>
	<script type="text/javascript">
	showFaultPhotos('${faultAlert.id }');
</script>
</body>