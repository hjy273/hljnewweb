<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<head>
	<script language="javascript" type="text/javascript">
//ά�����������
var patrolgroupcombotree;
var  device_state;
var  mast_type;
var  TOWERTYPE;
var  tower_material;
var  tower_base;
Ext.onReady(function() {
	jQuery("#addTowerForm").validate({	
		debug: true, 
		submitHandler: function(form){
			if(check()){
        		form.submit();
        	 }
        }
	});
	Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
	patrolgroupcombotree = new TreeComboField({
		width : 220,
		allowBlank:false,
		height:100,
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
	patrolgroupcombotree.on('select', function(combo, record) {
		if("PATROLMAN"!=record.attributes.objtype){
	        patrolgroupcombotree.setComboValue("","");
	      	//$('patrolgroupid').value="";
		    //$('maintenanceid').value="";
	        jQuery('#entity_patrolGroupId').val("");
		    jQuery('#entity_maintenanceId').val("");
	    }else{
			//$('patrolgroupid').value=record.attributes.id;
			//$('maintenanceid').value=record.parentNode.attributes.id;
	    	jQuery('#entity_patrolGroupId').val(record.attributes.id);
			jQuery('#entity_maintenanceId').val(record.parentNode.attributes.id);
		}
	});
	
	
	//�豸״̬
	device_state = new Appcombox({
	   	hiddenName : 'outdoorFacilities.state',
	   	hiddenId : 'outdoorFacilities_state',
	   	emptyText : '��ѡ��',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=device_state',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'outdoorFacilities_state_div'
	})

	//��������
	TOWERTYPE = new Appcombox({
	   	hiddenName : 'outdoorFacilities.towerType',
	   	hiddenId : 'outdoorFacilities_towerType',
	   	emptyText : '��ѡ��',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=TOWERTYPE',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'outdoorFacilities_towerType_div'
	})

    //��������
	tower_material = new Appcombox({
	   	hiddenName : 'outdoorFacilities.towerMaterial',
	   	hiddenId : 'outdoorFacilities_towerMaterial',
	   	emptyText : '��ѡ��',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=tower_material',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'outdoorFacilities_towerMaterial_div'
	})
	
	//��������
	tower_base = new Appcombox({
	   	hiddenName : 'outdoorFacilities.towerBase',
	   	hiddenId : 'outdoorFacilities_towerBase',
	   	emptyText : '��ѡ��',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=tower_base',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'outdoorFacilities_towerBase_div'
	})
	
	//�ݶ�Φ������
	mast_type = new Appcombox({
	   	hiddenName : 'outdoorFacilities.mastType',
	   	hiddenId : 'outdoorFacilities_mastType',
	   	emptyText : '��ѡ��',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=mast_type',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'outdoorFacilities_mastType_div'
	})

	//��Ȩ����
	var  property_right = new Appcombox({
	   	hiddenName : 'outdoorFacilities.propertyRight',
	   	hiddenId : 'outdoorFacilities_propertyRight',
	   	emptyText : '��ѡ��',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=property_right',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'outdoorFacilities_propertyRight_div'
	})
	
});
function getitude() {
	var actionUrl = "/WEBGIS/gisextend/igis.jsp?actiontype=101&userid=${LOGIN_USER.userID}&eid=position";
	window
			.open(actionUrl, 'map',
					'width=400,height=300,scrollbars=yes,z-look=yes,alwaysRaised=yes');
}
	function getStation() {
		//var url = "${ctx}/baseStationAction_getStation.jspx?district=&&id="+ $('stationName').value;
		//var myAjax = new Ajax.Request(url, {
		//	method : 'get',
		//	asynchronous : 'false',
		//	onComplete : setStationCode
		//});
		jQuery.ajax({
			  type: 'POST',
			  async: false,
			  url: "${ctx}/baseStationAction_getStation.jspx?district=&&id="+ jQuery("#stationName").val(),
			  success: function (data, textStatus){
				  setStationCode(data);
						},
			  dataType: 'html'
			  });
	}
	
	function setStationCode(responseText) {
		//$("outdoorFacilities.parentId").options.length = 1;
		//var str = response.responseText;
		//if (str == "")
		//	return true;
		//var optionlist = str.split(";");
		//for ( var i = 0; i < optionlist.length - 1; i++) {
		//	var t = optionlist[i].split("=")[0];
		//	var v = optionlist[i].split("=")[1];
		//	$("outdoorFacilities.parentId").options[i + 1] = new Option(v, t);
		//}
		jQuery("#outdoorFacilities_parentId option").length = 1;
		var str = responseText;
		if (str == "")
			return true;
		var optionlist = str.split(";");
		for ( var i = 0; i < optionlist.length - 1; i++) {
			var v = optionlist[i].split("=")[0];
			var t = optionlist[i].split("=")[1];
			jQuery("<option value='"+v+"'>"+t+"</option>").appendTo("#outdoorFacilities_parentId");
		}
	}
	function displayCondition(value){
			var town=document.getElementById("town");
			var town1=document.getElementById("town1");
			var town2=document.getElementById("town2");
			var town3=document.getElementById("town3");
			var town4=document.getElementById("town4");
			var town5=document.getElementById("town5");
			var town6=document.getElementById("town6");
			var mast=document.getElementById("mast");
			var mast1=document.getElementById("mast1");
			if(value=="town"){
				town.style.display="";
				town1.style.display="";
				town2.style.display="";
				town3.style.display="";
				town4.style.display="";
				town5.style.display="";
				town6.style.display="";
				mast.style.display="none";
				mast1.style.display="none";
				//$('outdoorFacilities.mastType').value="";
				mast_type.setComboValue("","");
				jQuery('#outdoorFacilities_poleHeight').val("");
			}else{
				town.style.display="none";
				town1.style.display="none";
				town2.style.display="none";
				town3.style.display="none";
				town4.style.display="none";
				town5.style.display="none";
				town6.style.display="none";
				mast.style.display="";
				mast1.style.display="";
				jQuery('#outdoorFacilities_towerName').val("");
				jQuery('#outdoorFacilities_towerCode').val("");
				//  $('outdoorFacilities.state').value="";
				device_state.setComboValue("","");
				/// $('outdoorFacilities.towerType').value="";
				TOWERTYPE.setComboValue("","");
				jQuery('#outdoorFacilities_towerHeight').val("");
				jQuery('#outdoorFacilities_towerPlatformNum').val("");
				jQuery('#outdoorFacilities_usePlatformNum').val("");
				jQuery('#outdoorFacilities_oldName').val("");
				jQuery('#outdoorFacilities_producter').val("");
				jQuery('#outdoorFacilities_towerMode').val("");
				//$('outdoorFacilities.towerMaterial').value="";
				tower_material.setComboValue("","");
				//$('outdoorFacilities.towerBase').value="";
				tower_base.setComboValue("","");
			}
	}
	
	
    var rfids = 1;
	function addrfids(){
	   rfids = rfids+1;
	   var _tr='<tr class="trwhite" id="rfids_row_'+rfids+'">'
				+'<td class="tdulleft" style="width:15%">'
				+'	��Ƶ����ţ�'
				+'</td>'
				+'<td class="tdulright" style="width:35%">'
				+'	<input id="rfids_'+rfids+'" type="text" name="rfids" onblur="validRfid(this)" class="inputtext required" style="width:220px" maxlength="40" /><span style="color:red">*</span>'
				+'</td>'
				+'<td class="tdulleft" style="width:15%">'
				+'	λ��������'
				+'</td>'
				+'<td class="tdulright">'
				+'	<input id="addresses_'+rfids+'" type="text" name="addresses" class="inputtext required" style="width:220px" maxlength="40" /><span style="color:red">*</span>'
				+'	<a href="javascript:void(0);" onclick="delrfids(\'rfids_row_'+rfids+'\')">ɾ��</a>'
				+'</td>'
			    +'</tr>';
		       jQuery("#_table").append(_tr);   
	}
	
	function delrfids(id){
        jQuery("tr[id="+id+"]").remove();
	}
	
	
//��֤��Ƶ����Ψһ  ����֤ǰ̨�����ظ�������֤��̨
function validRfid(rfid){
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
  url: 'res/indoorOverRideAction_valid.jspx?rfid='+rfid.value+"&id=",
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

function check(){
	    //if($('outdoorFacilities.parentId').value==null||$('outdoorFacilities.parentId').value.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "")==''){
		//	alert("û��ѡ��������վ��");
		//	return false;
		//}
		if('town'==jQuery(":radio[name='type']:checked").val()){
			if(jQuery("#outdoorFacilities_state").val()==''){
				alert('��ѡ���豸״̬');
				return false;
			}
			if(jQuery("#outdoorFacilities_towerType").val()==''){
				alert('��ѡ����������');
				return false;
			}
			if(jQuery("#outdoorFacilities_towerMaterial").val()==''){
				alert('��ѡ����������');
				return false;
			}
			if(jQuery("#outdoorFacilities_towerBase").val()==''){
				alert('��ѡ����������');
				return false;
			}
		}else{
			if(jQuery("#outdoorFacilities_mastType").val()==''){
				alert('��ѡ������Φ������');
				return false;
			}
		}
		if(jQuery("#outdoorFacilities_propertyRight").val()==''){
				alert('��ѡ���Ȩ����');
				return false;
		}
		//if($("patrolGroupId").value==''){
		//	alert('��ѡ��ά����');
		//	return false;
		//}
		return true;
	}
</script>
</head>
<body>
	<template:titile value="���������Ϣ" />
	<s:form action="outdoorFacilitiesAction_save"
		name="addOutdoorFacilitiesFrom" method="post"
		onsubmit="return check();" id="addTowerForm">
		<table width="850" id="_table" border="0" align="center"
			cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulright" style="width: 18%">
					������վ��
				</td>
				<td class="tdulright" colspan="3" style="width: 82%">
					<input type="text" id="stationName" onchange="getStation()"
						value="�������ѯ���ݣ��ڵ�������б�" onmouseover="this.select()"
						class="inputtext" style="width: 220px" maxlength="40" />
					<br>
					<select name="outdoorFacilities.parentId"
						id="outdoorFacilities_parentId" class="inputtext required"
						style="width: 220px">
						<option value="">
							��ѡ��
						</option>
					</select>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" style="width: 18%">
					ѡ��������ͣ�
				</td>
				<td class="tdulright" colspan="3" style="width: 82%">
					<input type="radio" name="type" value="town"
						onclick="displayCondition(this.value);" checked="checked">
					����
					</input>
					<input type="radio" name="type" value="mast"
						onclick="displayCondition(this.value);">
					Φ��
					</input>
				</td>
			</tr>
			<tr class="trwhite" id="town">
				<td class="tdulright" style="width: 18%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 82%">
				</td>
			</tr>
			<tr class="trwhite" id="town1">
				<td class="tdulleft" style="width: 18%">
					�������ƣ�
				</td>
				<td class="tdulright" style="width: 32%">
					<input type="text" id="outdoorFacilities_towerName"
						name="outdoorFacilities.towerName" class="inputtext required"
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					������ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="outdoorFacilities_towerCode"
						name="outdoorFacilities.towerCode" class="inputtext required"
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite" id="town2">
				<td class="tdulleft" style="width: 18%">
					�豸״̬��
				</td>
				<td class="tdulright" style="width: 32%">
					<div id="outdoorFacilities_state_div"></div>
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					�������ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="outdoorFacilities_towerType_div"></div>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite" id="town3">
				<td class="tdulleft" style="width: 18%">
					�����߶ȣ�
				</td>
				<td class="tdulright" style="width: 32%">
					<input type="text" id="outdoorFacilities_towerHeight"
						name="outdoorFacilities.towerHeight" class="number"
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					����ƽ̨����
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="outdoorFacilities_towerPlatformNum"
						name="outdoorFacilities.towerPlatformNum"
						class="number" style="width: 220"
						maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite" id="town4">
				<td class="tdulleft" style="width: 18%">
					����ƽ̨����
				</td>
				<td class="tdulright" style="width: 32%">
					<input type="text" id="outdoorFacilities_usePlatformNum"
						name="outdoorFacilities.usePlatformNum"
						class="number" style="width: 220"
						maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					ԭ���ƣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="outdoorFacilities_oldName"
						name="outdoorFacilities.oldName" class="inputtext required"
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>

			<tr class="trwhite" id="town5">
				<td class="tdulleft" style="width: 18%">
					�������ң�
				</td>
				<td class="tdulright" style="width: 32%">
					<input type="text" id="outdoorFacilities_producter"
						name="outdoorFacilities.producter" class="inputtext"
						style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					����ͺţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="outdoorFacilities_towerMode"
						name="outdoorFacilities.towerMode" class="inputtext"
						style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite" id="town6">
				<td class="tdulleft" style="width: 18%">
					�������ʣ�
				</td>
				<td class="tdulright" style="width: 32%">
					<div id="outdoorFacilities_towerMaterial_div"></div>
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					����������
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="outdoorFacilities_towerBase_div"></div>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite" id="mast" style="display: none">
				<td class="tdulright" style="width: 18%">
					Φ����Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 82%">
				</td>
			</tr>
			<tr class="trwhite" id="mast1" style="display: none">
				<td class="tdulleft" style="width: 18%">
					�ݶ�Φ�����ͣ�
				</td>
				<td class="tdulright" style="width: 32%">
					<div id="outdoorFacilities_mastType_div"></div>
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					���˸߶ȣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="outdoorFacilities_poleHeight"
						name="outdoorFacilities.poleHeight" class="number "
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" style="width: 18%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 82%"></td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 18%">
					����ʱ�䣺
				</td>
				<td class="tdulright" style="width: 32%">
					<input type="text" name="outdoorFacilities.anTime"
						class="Wdate inputtext " style="width: 220"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					��Ȩ���ʣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="outdoorFacilities_propertyRight_div"></div>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 18%">
					�Ƿ���������Ӫ�̹���
				</td>
				<td class="tdulright" style="width: 32%">
					<s:select list="#{'n':'��','y':'��'}"
						name="outdoorFacilities.isShare"
						value="%{#request.outdoorFacilities.isShare}" listKey="key"
						listValue="value" cssClass="inputtext" cssStyle="width:220px"></s:select>
				</td>
				<td class="tdulleft" style="width: 15%">
					������Ӫ�̣�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="outdoorFacilities.shareOperator"
						class="inputtext" style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 18%">
					�̶��ʲ���ţ�
				</td>
				<td class="tdulright" style="width: 32%">
					<input type="text" name="outdoorFacilities.assetCode"
						class="inputtext" style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					�����룺
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="outdoorFacilities.barCode"
						class="inputtext" style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 18%">
					ά���飺
				</td>
				<td class="tdulright" style="width: 32%">
					<input id="entity_patrolGroupId" name="maintenanceid" value=""
						type="hidden" />
					<input id="entity_maintenanceId" name="patrolgroupid" value=""
						type="hidden" />
					<div id="combotree_patrolgroupdiv" style="width: 220;"></div>
				</td>

				<td class="tdulleft" style="width: 15%">
					��γ�ȣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="position" name="position"
						class="inputtext required isJW"
						style="width: 220" maxlength="40" />
					<input type="button" value="ѡ��" style="display: none"  onclick="getitude()" />
					<span style="color: red">*����γ���Զ��Ÿ�����</span>
				</td>

			</tr>
			<tr class="trwhite">
				<td colspan="4" class="tdulleft" style="width: 18%">
					<a href="javascript:void(0);" onclick="addrfids()">����Ѳ��λ����Ϣ</a>
				</td>
			</tr>

			<tr class="trwhite" id="rfid_row_${item.rfid}">
				<td class="tdulleft" style="width: 18%">
					��Ƶ����ţ�
				</td>
				<td class="tdulright" style="width: 32%">
					<input id="rfids_0" type="text" name="rfids" onblur="validRfid(this)"
						class="inputtext required" style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					λ��������
				</td>
				<td class="tdulright">
					<input id="addresses_0" type="text" name="addresses" class="inputtext required"
						style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>

		</table>
		<table width="40%" border="0" style="margin-top: 6px" align="center"
			cellpadding="0" cellspacing="0">
			<tr align="center">
				<td>
					<input type="submit" id="isubmit" class="button" value="���">
				</td>
				<td>
					<input type="button" class="button" onclick="history.back()"
						value="����">
				</td>
			</tr>
		</table>
		<div align="center" style="margin-top: 20px; color: red;">
			��ɫ*Ϊ������
		</div>
	</s:form>
</body>
