<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<head>
	<script language="javascript" type="text/javascript">
function check(){
	if(jQuery("#bsLevel").val()==''){
		alert('��ѡ���վ����');
		//$('baseStation.bsLevel').focus();
		return false;
	}
	if(jQuery("#coverageAreaType").val()==''){
		alert('��ѡ�񸲸���������');
		//$('district').focus();
		return false;
	}
	if(jQuery("#coverageArea").val()==''){
		alert('��ѡ�񸲸�����');
		//$('district').focus();
		return false;
	}
	if(jQuery("#district").val()==''){
		alert('��ѡ����������');
		//$('district').focus();
		return false;
	}
	if(jQuery("#entity_patrolGroupId").val()==''){
		alert('��ѡ��ά����');
		//$('patrolGroupId').focus();
		return false;
	}	
		return true;
	}
	showOrHide=function(divId){
		var display=document.getElementById(divId).style.display;
		document.getElementById("baseInfoTb").style.display="none";
		document.getElementById("geoInfoTb").style.display="none";
		document.getElementById("flagInfoTb").style.display="none";
		document.getElementById("stationInfoTb").style.display="none";
		document.getElementById("otherInfoTb").style.display="none";
		if(display=="none"){
			document.getElementById(divId).style.display="";
		}else{
			document.getElementById(divId).style.display="none";
		}
	}
	function getitude(){
	 
		var actionUrl="/WEBGIS/gisextend/igis.jsp?actiontype=101&userid=${LOGIN_USER.userID}&eid=position";
		window.open(actionUrl,'map','width=400,height=300,scrollbars=yes,z-look=yes,alwaysRaised=yes');
	}
var patrolgroupcombotree;
//��������
var patrolregioncombotree;
Ext.onReady(function() {
	jQuery("#editBaseStationForm").validate({	
		debug: true, 
		submitHandler: function(form){
			if(check()){
        		form.submit();
        	 }
        }
	});
	patrolregioncombotree = new TreeComboField({
		width : 220,
		height : 100,
		allowBlank : false,
		leafOnly : false,
		renderTo : 'combotree_patrolregiondiv',
		name : "regionname",
		hiddenName : "district",
		hiddenId : "district",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			autoHeight:true, 
			root : new Ext.tree.AsyncTreeNode({
				id : '000000',
				loader : new Ext.tree.TreeLoader({
					dataUrl : '${ctx}/common/externalresources_getRegionJson.jspx'
				})

			})
		})
	});
	patrolregioncombotree.setComboValue("${baseStation.district}",'<apptag:dynLabel objType="region" id="${baseStation.district}" dicType=""></apptag:dynLabel>');//����
	patrolgroupcombotree = new TreeComboField({
		width : 220,
		height : 100,
		allowBlank:false,
		leafOnly : true,
		renderTo : 'combotree_patrolgroupdiv',
		name : "patrolgroupname",
		hiddenName : "pid",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			autoHeight:true, 
			root : new Ext.tree.AsyncTreeNode({
				id : 'root',
				loader : new Ext.tree.TreeLoader({
							dataUrl : '${ctx}/common/externalresources_getPatrolmanJson.jspx'
						})

			})
		})
	});
	patrolgroupcombotree.setComboValue("${baseStation.patrolGroupId}","${baseStation.patrolGroupName}");//����
	patrolgroupcombotree.on('select', function(combo, record) {
		if("PATROLMAN"!=record.attributes.objtype){
	        patrolgroupcombotree.setComboValue("","");
	      	//$('patrolGroupId').value="";
		    //$('baseStation.maintenanceId').value="";
		    jQuery('#entity_patrolGroupId').val("");
		    jQuery('#entity_maintenanceId').val("");
	    }else{
			//$('patrolGroupId').value=record.attributes.id;
			//$('baseStation.maintenanceId').value=record.parentNode.attributes.id;
	    	jQuery('#entity_patrolGroupId').val(record.attributes.id);
			jQuery('#entity_maintenanceId').val(record.parentNode.attributes.id);
		}
	});
		//��������	baseStation.mrType				property_right		basestaion_property_right
		var  property_right = new Appcombox({
			hiddenName : 'baseStation.mrType',
			hiddenId:'mrType',
			emptyText : '��ѡ��',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=property_right',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:true,
			renderTo: 'basestaion_property_right'
		});
		property_right.setComboValue('${baseStation.mrType}','<apptag:dynLabel objType="dic" id="${baseStation.mrType}" dicType="property_right"></apptag:dynLabel>');
		//���ݽṹ	baseStation.roomStructure		room_structure		basestaion_room_structure
		var  room_structure = new Appcombox({
			hiddenName : 'baseStation.roomStructure',
			hiddenId:'roomStructure',
			emptyText : '��ѡ��',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=room_structure',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:true,
			renderTo: 'basestaion_room_structure'
		});
		room_structure.setComboValue('${baseStation.roomStructure}','<apptag:dynLabel objType="dic" id="${baseStation.roomStructure}" dicType="room_structure"></apptag:dynLabel>');
		//��������	baseStation.geoFeature			geography			basestaion_geography
		var  geography = new Appcombox({
			hiddenName : 'baseStation.geoFeature',
			hiddenId:'geoFeature',
			emptyText : '��ѡ��',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=geography',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:true,
			renderTo: 'basestaion_geography'
		});
		geography.setComboValue('${baseStation.geoFeature}','<apptag:dynLabel objType="dic" id="${baseStation.geoFeature}" dicType="geography"></apptag:dynLabel>');
		//��վ����	baseStation.bsType				basestation_type	basestaion_basestation_type
		var  basestation_type = new Appcombox({
			hiddenName : 'baseStation.bsType',
			hiddenId:'bsType',
			emptyText : '��ѡ��',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=basestation_type',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:true,
			renderTo: 'basestaion_basestation_type'
		});	
		basestation_type.setComboValue('${baseStation.bsType}','<apptag:dynLabel objType="dic" id="${baseStation.bsType}" dicType="basestation_type"></apptag:dynLabel>');
		//��վ����	baseStation.bsLevel				basestation_level	basestaion_basestation_level
		var  basestation_level = new Appcombox({
			hiddenName : 'baseStation.bsLevel',
			hiddenId:'bsLevel',
			emptyText : '��ѡ��',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=basestation_level',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:false,
			renderTo: 'basestaion_basestation_level'
		});
		basestation_level.setComboValue('${baseStation.bsLevel}','<apptag:dynLabel objType="dic" id="${baseStation.bsLevel}" dicType="basestation_level"></apptag:dynLabel>');
		//��վ����	baseStation.bsSort				basestation_sort	basestaion_basestation_sort
		var  basestation_sort = new Appcombox({
			hiddenName : 'baseStation.bsSort',
			hiddenId:'bsSort',
			emptyText : '��ѡ��',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=basestation_sort',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:true,
			renderTo: 'basestaion_basestation_sort'
		});
		basestation_sort.setComboValue('${baseStation.bsSort}','<apptag:dynLabel objType="dic" id="${baseStation.bsSort}" dicType="basestation_sort"></apptag:dynLabel>');
		//������������	baseStation.coverageAreaType	overlay_area_type	basestaion_overlay_area_type
		var  overlay_area_type = new Appcombox({
			hiddenName : 'baseStation.coverageAreaType',
			hiddenId:'coverageAreaType',
			emptyText : '��ѡ��',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=overlay_area_type',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:false,
			renderTo: 'basestaion_overlay_area_type'
		});
		overlay_area_type.setComboValue('${baseStation.coverageAreaType}','<apptag:dynLabel objType="dic" id="${baseStation.coverageAreaType}" dicType="overlay_area_type"></apptag:dynLabel>');
		//��������	baseStation.coverageArea		overlay_area		basestaion_overlay_area
		var  overlay_area = new Appcombox({
			hiddenName : 'baseStation.coverageArea',
			hiddenId:'coverageArea',
			emptyText : '��ѡ��',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=overlay_area',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:false,
			renderTo: 'basestaion_overlay_area'
		});
		overlay_area.setComboValue('${baseStation.coverageArea}','<apptag:dynLabel objType="dic" id="${baseStation.coverageArea}" dicType="overlay_area"></apptag:dynLabel>');
})

	var rfids = 1;
	function addrfids(){
	   rfids = rfids+1;
	   var _tr='<tr class="trwhite" id="rfids_row_'+rfids+'">'
				+'<td class="tdulleft" style="width:15%">'
				+'	��Ƶ����ţ�'
				+'</td>'
				+'<td class="tdulright" style="width:35%">'
				+'	<input id="rfids_'+rfids+'" type="text" name="rfids" onblur="validRfid(this,\'\')" class="inputtext required " style="width:220px" maxlength="40" /><span style="color:red">*</span>'
				+'</td>'
				+'<td class="tdulleft" style="width:15%">'
				+'	λ��������'
				+'</td>'
				+'<td class="tdulright">'
				+'	<input id="addresses_'+rfids+'" type="text" name="addresses" class="inputtext required" style="width:220px" maxlength="40" /><span style="color:red">*</span>'
				+'	<a href="javascript:void(0);" onclick="delrfids(\'rfids_row_'+rfids+'\')">ɾ��</a>'
				+'</td>'
			    +'</tr>';
		       jQuery("#otherInfoTb").append(_tr);   
	}
	
	function delrfids(id){
        jQuery("tr[id="+id+"]").remove();
	}


//��֤��Ƶ����Ψһ  ����֤ǰ̨�����ظ�������֤��̨
function validRfid(rfid,id){
  if('' == rfid.value){
return;
}
var flag = true;
jQuery("#isubmit").removeAttr('disabled');
jQuery("input:text[name='rfids']").each(function(i,e){
  if(this != rfid){
     if(jQuery(this).val()==rfid.value){
        jQuery("#isubmit").attr('disabled','disabled');
        flag = false;
        alert("��Ƶ���Ų����ظ�");
     }
  }
});

if(flag){
jQuery.ajax({
  type: 'POST',
  async: false,
  url: 'res/indoorOverRideAction_valid.jspx?rfid='+rfid.value+"&id="+id,
  success: function (data, textStatus){
				if('true'==data){
				   jQuery("#isubmit").attr('disabled','disabled');
				   alert("��Ƶ�����Ѵ���");
				}
			},
  dataType: 'html'
  });
}
}
	function validCode(){
		jQuery.ajax({
			  type: 'POST',
			  async: false,
			  url: 'baseStationAction_valid.jspx?id=${baseStation.id}'+'&&stationCode='+$("#baseStation_stationCode").val(),
			  success: function (data, textStatus){
				  			msg.innerHTML = data;
							if(data!=""){
								$("#baseStation_stationCode").val("");
							}
						},
			  dataType: 'html'
			  });
	}
</script>
</head>
<body>
	<template:titile value="�༭��վ��Ϣ" />
	<s:form action="baseStationAction_update" name="editBaseStationForm" method="post" id="editBaseStationForm">
		<input type="hidden" name="baseStation.pointId" value="${baseStation.pointId}" />
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<!-- 
					<a href="javascript:showOrHide('baseInfoTb');">չ��/����</a>
					 -->
				</td>
			</tr>
			<tbody id="baseInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						վַ��ţ�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.stationCode" id="baseStation_stationCode"
							value="${baseStation.stationCode}" class="inputtext required"
							style="width: 220px" maxlength="40" onblur="validCode();" />
						<span style="color: red">*</span>
						<span id="msg" style="color: red"></span>
					</td>
					<td class="tdulleft" style="width: 15%">
						��վ���ƣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.stationName"
							value="${baseStation.stationName }" class="inputtext required"
							style="width: 220px" maxlength="50" />
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�������У�
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.city!=null}">
							<input type="text" name="baseStation.city"
								value="${baseStation.city }" readonly="readonly"
								class="inputtext required" style="width: 220px" maxlength="40" />
							<span style="color: red">*</span>
						</c:if>
						<c:if test="${baseStation.city==null}">
							<input type="text" name="baseStation.city"
								value="${LOGIN_USER_REGION_NAME }" readonly="readonly"
								class="inputtext required" style="width: 220px" maxlength="40" />
							<span style="color: red">*</span>
						</c:if>
					</td>
					<td class="tdulleft" style="width: 15%">
						�������أ�
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="combotree_patrolregiondiv" style="width: 180;"></div>
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��������
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.town"
							value="${baseStation.town}" class="inputtext "
							style="width: 220px" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						���������壺
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.village"
							value="${baseStation.village }" class="inputtext "
							style="width: 220px" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��ϸ��ַ��
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.address"
							value="${baseStation.address }" class="inputtext required"
							style="width: 220px" maxlength="40" />
						<span style="color: red">*</span>
					</td>
					<td class="tdulleft" style="width: 15%">
						����ʱ�䣺
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.anTime"
							value="<fmt:formatDate value="${baseStation.anTime }" pattern="yyyy-MM-dd"/>"
							class="Wdate inputtext" style="width: 220px"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�������ͣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="basestaion_property_right"></div>
					</td>
					<td class="tdulleft" style="width: 15%">
						���������
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.roomArea"
							value="${baseStation.roomArea}"
							class="number " style="width: 220px"
							maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���ݽṹ��
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<div id="basestaion_room_structure"></div>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<!-- 
					<a href="javascript:showOrHide('geoInfoTb');">չ��/����</a>
					 -->
				</td>
			</tr>
			<tbody id="geoInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						����(��)��
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.elevation"
							value="${baseStation.elevation}"
							class="number " style="width: 220px"
							maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						����������
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="basestaion_geography"></div>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��γ�ȣ�
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<input type="text" name="position" value="${position}"
							class="inputtext required isJW"
							style="width: 220px" maxlength="40" />
						<input type="button" value="ѡ��" style="display: none"  onclick="getitude()" />
						<span style="color: red">*������γ���Զ��Ÿ�����</span>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					��ʶ��Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<!-- 
					<a href="javascript:showOrHide('flagInfoTb');">չ��/����</a>
					 -->
				</td>
			</tr>
			<tbody id="flagInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�߽��վ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'��','y':'��'}" name="baseStation.isBoundary"
							value="%{#request.baseStation.isBoundary}" listKey="key"
							listValue="value" cssClass="inputtext" cssStyle="width:220px"></s:select>
					</td>
					<td class="tdulleft" style="width: 15%">
						VIP��վ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'��','y':'��'}" name="baseStation.isVip"
							value="%{#request.baseStation.isVip}" listKey="key"
							listValue="value" cssClass="inputtext" cssStyle="width:220px"></s:select>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						����ڵ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'��','y':'��'}"
							name="baseStation.isTransitNode"
							value="%{#request.baseStation.isTransitNode}" listKey="key"
							listValue="value" cssClass="inputtext" cssStyle="width:220px"></s:select>
					</td>
					<td class="tdulleft" style="width: 15%">
						�߼�վ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'��','y':'��'}" name="baseStation.isLimit"
							value="%{#request.baseStation.isLimit}" listKey="key"
							listValue="value" cssClass="inputtext" cssStyle="width:220px"></s:select>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���ڸ��Ǳ�ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'��','y':'��'}"
							name="baseStation.isIndoorCoverage"
							value="%{#request.baseStation.isIndoorCoverage}" listKey="key"
							listValue="value" cssClass="inputtext" cssStyle="width:220px"></s:select>
					</td>
					<td class="tdulleft" style="width: 15%">
						��ͨ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'��','y':'��'}"
							name="baseStation.isEveryVillage"
							value="%{#request.baseStation.isEveryVillage}" listKey="key"
							listValue="value" cssClass="inputtext" cssStyle="width:220px"></s:select>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						ʩ����վ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'��','y':'��'}"
							name="baseStation.isBenefactorBs"
							value="%{#request.baseStation.isBenefactorBs}" listKey="key"
							listValue="value" cssClass="inputtext" cssStyle="width:220px"></s:select>
					</td>
					<td class="tdulleft" style="width: 15%">
						�¹һ�վ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'��','y':'��'}" name="baseStation.isDrive"
							value="%{#request.baseStation.isDrive}" listKey="key"
							listValue="value" cssClass="inputtext" cssStyle="width:220px"></s:select>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					վ�����
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<!-- 
					<a href="javascript:showOrHide('stationInfoTb');">չ��/����</a>
					 -->
				</td>
			</tr>
			<tbody id="stationInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						������ߣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'��','y':'��'}"
							name="baseStation.isSanitaryWare"
							value="%{#request.baseStation.isSanitaryWare}" listKey="key"
							listValue="value" cssClass="inputtext" cssStyle="width:220px"></s:select>
					</td>
					<td class="tdulleft" style="width: 15%">
						�е����������
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.mainsLeadin"
							value="${baseStation.mainsLeadin}" class="inputtext"
							style="width: 220px" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���������
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.gateLock"
							value="${baseStation.gateLock}" class="inputtext "
							style="width: 220px" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						�������⣺
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.leaveOverQuestion"
							value="${baseStation.leaveOverQuestion}" class="inputtext "
							style="width: 220px" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						ҵ����ϵ�ˣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.owner"
							value="${baseStation.owner}" class="inputtext required"
							style="width: 220px" maxlength="40" />
						<span style="color: red">*</span>
					</td>
					<td class="tdulleft" style="width: 15%">
						ҵ����ϵ�绰��
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.ownerTel"
							value="${baseStation.ownerTel}" class="inputtext required isTel"
							style="width: 220px" maxlength="40" />
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						ά���飺
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<input id="entity_maintenanceId" name="baseStation.maintenanceId"
							value="${baseStation.maintenanceId}" type="hidden" />
						<input id="entity_patrolGroupId" name="patrolGroupId" value="${baseStation.patrolGroupId}"
							type="hidden" />
						<div id="combotree_patrolgroupdiv" style="width: 220;"></div>
						<font color="#FF0000">*</font>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<!-- 
					<a href="javascript:showOrHide('otherInfoTb');">չ��/����</a>
					 -->
				</td>
			</tr>
			<tbody id="otherInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						����BSC��
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.attachBSC"
							value="${baseStation.attachBSC}" class="inputtext "
							style="width: 220px" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						BCF������
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.bcfNum"
							value="${baseStation.bcfNum}" class="inputtext validate-integer "
							style="width: 220px" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						Ƶ�Σ�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.frequencyChannel"
							value="${baseStation.frequencyChannel}" class="inputtext "
							style="width: 220px" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						��վ���ͣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="basestaion_basestation_type"></div>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��վ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.bsIdentifies"
							value="${baseStation.bsIdentifies}" class="inputtext "
							style="width: 220px" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						��վ����
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="basestaion_basestation_level"></div>
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��վ���ࣺ
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="basestaion_basestation_sort"></div>
					</td>
					<td class="tdulleft" style="width: 15%">
						��վ���ã�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.bsDeploy"
							value="${baseStation.bsDeploy}" class="inputtext "
							style="width: 220px" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�������
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.areaType"
							value="${baseStation.areaType}" class="inputtext "
							style="width: 220px" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						�����������ͣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="basestaion_overlay_area_type"></div>
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��������
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<div id="basestaion_overlay_area"></div>
						<span style="color: red">*</span>
					</td>
				</tr>


				<tr class="trwhite">
					<td colspan="4" class="tdulleft" style="width: 15%">
						<a href="javascript:void(0);" onclick="addrfids()">����Ѳ��λ����Ϣ</a>
						<input type="hidden" name="rfids" value="" />
						<input type="hidden" name="addresses" value="" />
					</td>
				</tr>


				<c:forEach var="item" items="${patrolAddressInfos}"
					varStatus="stauts">
					<tr class="trwhite" id="rfid_row_${stauts.index}">
						<td class="tdulleft" style="width: 15%">
							��Ƶ����ţ�
						</td>
						<td class="tdulright" style="width: 35%">
							<input id="rfids_0" type="text" name="rfids"
								onblur="validRfid(this,'${item.id }')" value="${item.rfid}"
								class="inputtext required " style="width: 220px" maxlength="40" />
							<span style="color: red">*</span>
						</td>
						<td class="tdulleft" style="width: 15%">
							λ��������
						</td>
						<td class="tdulright">
							<input id="addresses_0" type="text" name="addresses" value="${item.address}"
								class="inputtext required " style="width: 220px" maxlength="40" />
							<span style="color: red">*</span>
							<c:if test="${stauts.index > 0}">
								<a href="javascript:void(0);"
									onclick="delrfids('rfid_row_${stauts.index}')">ɾ��</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>




			</tbody>
			<table width="40%" border="0" style="margin-top: 6px" align="center"
				cellpadding="0" cellspacing="0">
				<tr align="center">
					<td>
						<input type="submit" id="isubmit" class="button" value="����">
					</td>
					<td>
						<input type="button" class="button" onclick="history.back()"
							value="����">
					</td>
				</tr>
			</table>
		</table>
	</s:form>
</body>
