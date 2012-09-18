<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<head>
	<script language="javascript" type="text/javascript">
//维护组下拉组件
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
	
	
	//设备状态
	device_state = new Appcombox({
	   	hiddenName : 'outdoorFacilities.state',
	   	hiddenId : 'outdoorFacilities_state',
	   	emptyText : '请选择',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=device_state',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'outdoorFacilities_state_div'
	})

	//铁塔类型
	TOWERTYPE = new Appcombox({
	   	hiddenName : 'outdoorFacilities.towerType',
	   	hiddenId : 'outdoorFacilities_towerType',
	   	emptyText : '请选择',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=TOWERTYPE',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'outdoorFacilities_towerType_div'
	})

    //铁塔材质
	tower_material = new Appcombox({
	   	hiddenName : 'outdoorFacilities.towerMaterial',
	   	hiddenId : 'outdoorFacilities_towerMaterial',
	   	emptyText : '请选择',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=tower_material',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'outdoorFacilities_towerMaterial_div'
	})
	
	//铁塔基础
	tower_base = new Appcombox({
	   	hiddenName : 'outdoorFacilities.towerBase',
	   	hiddenId : 'outdoorFacilities_towerBase',
	   	emptyText : '请选择',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=tower_base',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'outdoorFacilities_towerBase_div'
	})
	
	//屋顶桅杆类型
	mast_type = new Appcombox({
	   	hiddenName : 'outdoorFacilities.mastType',
	   	hiddenId : 'outdoorFacilities_mastType',
	   	emptyText : '请选择',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=mast_type',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'outdoorFacilities_mastType_div'
	})

	//产权性质
	var  property_right = new Appcombox({
	   	hiddenName : 'outdoorFacilities.propertyRight',
	   	hiddenId : 'outdoorFacilities_propertyRight',
	   	emptyText : '请选择',
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
				+'	射频卡编号：'
				+'</td>'
				+'<td class="tdulright" style="width:35%">'
				+'	<input id="rfids_'+rfids+'" type="text" name="rfids" onblur="validRfid(this)" class="inputtext required" style="width:220px" maxlength="40" /><span style="color:red">*</span>'
				+'</td>'
				+'<td class="tdulleft" style="width:15%">'
				+'	位置描述：'
				+'</td>'
				+'<td class="tdulright">'
				+'	<input id="addresses_'+rfids+'" type="text" name="addresses" class="inputtext required" style="width:220px" maxlength="40" /><span style="color:red">*</span>'
				+'	<a href="javascript:void(0);" onclick="delrfids(\'rfids_row_'+rfids+'\')">删除</a>'
				+'</td>'
			    +'</tr>';
		       jQuery("#_table").append(_tr);   
	}
	
	function delrfids(id){
        jQuery("tr[id="+id+"]").remove();
	}
	
	
//验证射频卡号唯一  先验证前台，无重复后再验证后台
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
        alert("射频卡号不能重复");
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
				   alert("射频卡号已存在");
				}
			},
  dataType: 'html'
  });
}
}

function check(){
	    //if($('outdoorFacilities.parentId').value==null||$('outdoorFacilities.parentId').value.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "")==''){
		//	alert("没有选择所属基站！");
		//	return false;
		//}
		if('town'==jQuery(":radio[name='type']:checked").val()){
			if(jQuery("#outdoorFacilities_state").val()==''){
				alert('请选择设备状态');
				return false;
			}
			if(jQuery("#outdoorFacilities_towerType").val()==''){
				alert('请选择铁塔类型');
				return false;
			}
			if(jQuery("#outdoorFacilities_towerMaterial").val()==''){
				alert('请选择铁塔材质');
				return false;
			}
			if(jQuery("#outdoorFacilities_towerBase").val()==''){
				alert('请选择铁塔基础');
				return false;
			}
		}else{
			if(jQuery("#outdoorFacilities_mastType").val()==''){
				alert('请选择铁塔桅杆类型');
				return false;
			}
		}
		if(jQuery("#outdoorFacilities_propertyRight").val()==''){
				alert('请选择产权性质');
				return false;
		}
		//if($("patrolGroupId").value==''){
		//	alert('请选择维护组');
		//	return false;
		//}
		return true;
	}
</script>
</head>
<body>
	<template:titile value="添加铁塔信息" />
	<s:form action="outdoorFacilitiesAction_save"
		name="addOutdoorFacilitiesFrom" method="post"
		onsubmit="return check();" id="addTowerForm">
		<table width="850" id="_table" border="0" align="center"
			cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulright" style="width: 18%">
					所属基站：
				</td>
				<td class="tdulright" colspan="3" style="width: 82%">
					<input type="text" id="stationName" onchange="getStation()"
						value="请输入查询内容，在点击下面列表" onmouseover="this.select()"
						class="inputtext" style="width: 220px" maxlength="40" />
					<br>
					<select name="outdoorFacilities.parentId"
						id="outdoorFacilities_parentId" class="inputtext required"
						style="width: 220px">
						<option value="">
							请选择
						</option>
					</select>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" style="width: 18%">
					选择添加类型：
				</td>
				<td class="tdulright" colspan="3" style="width: 82%">
					<input type="radio" name="type" value="town"
						onclick="displayCondition(this.value);" checked="checked">
					铁塔
					</input>
					<input type="radio" name="type" value="mast"
						onclick="displayCondition(this.value);">
					桅杆
					</input>
				</td>
			</tr>
			<tr class="trwhite" id="town">
				<td class="tdulright" style="width: 18%">
					铁塔信息
				</td>
				<td class="tdulright" colspan="3" style="width: 82%">
				</td>
			</tr>
			<tr class="trwhite" id="town1">
				<td class="tdulleft" style="width: 18%">
					铁塔名称：
				</td>
				<td class="tdulright" style="width: 32%">
					<input type="text" id="outdoorFacilities_towerName"
						name="outdoorFacilities.towerName" class="inputtext required"
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					铁塔编号：
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
					设备状态：
				</td>
				<td class="tdulright" style="width: 32%">
					<div id="outdoorFacilities_state_div"></div>
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					铁塔类型：
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="outdoorFacilities_towerType_div"></div>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite" id="town3">
				<td class="tdulleft" style="width: 18%">
					铁塔高度：
				</td>
				<td class="tdulright" style="width: 32%">
					<input type="text" id="outdoorFacilities_towerHeight"
						name="outdoorFacilities.towerHeight" class="number"
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					铁塔平台数：
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
					已用平台数：
				</td>
				<td class="tdulright" style="width: 32%">
					<input type="text" id="outdoorFacilities_usePlatformNum"
						name="outdoorFacilities.usePlatformNum"
						class="number" style="width: 220"
						maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					原名称：
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
					铁塔厂家：
				</td>
				<td class="tdulright" style="width: 32%">
					<input type="text" id="outdoorFacilities_producter"
						name="outdoorFacilities.producter" class="inputtext"
						style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					规格型号：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="outdoorFacilities_towerMode"
						name="outdoorFacilities.towerMode" class="inputtext"
						style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite" id="town6">
				<td class="tdulleft" style="width: 18%">
					铁塔材质：
				</td>
				<td class="tdulright" style="width: 32%">
					<div id="outdoorFacilities_towerMaterial_div"></div>
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					铁塔基础：
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="outdoorFacilities_towerBase_div"></div>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite" id="mast" style="display: none">
				<td class="tdulright" style="width: 18%">
					桅杆信息
				</td>
				<td class="tdulright" colspan="3" style="width: 82%">
				</td>
			</tr>
			<tr class="trwhite" id="mast1" style="display: none">
				<td class="tdulleft" style="width: 18%">
					屋顶桅杆类型：
				</td>
				<td class="tdulright" style="width: 32%">
					<div id="outdoorFacilities_mastType_div"></div>
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					抱杆高度：
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
					附加信息
				</td>
				<td class="tdulright" colspan="3" style="width: 82%"></td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 18%">
					入网时间：
				</td>
				<td class="tdulright" style="width: 32%">
					<input type="text" name="outdoorFacilities.anTime"
						class="Wdate inputtext " style="width: 220"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					产权性质：
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="outdoorFacilities_propertyRight_div"></div>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 18%">
					是否与其他运营商共享：
				</td>
				<td class="tdulright" style="width: 32%">
					<s:select list="#{'n':'否','y':'是'}"
						name="outdoorFacilities.isShare"
						value="%{#request.outdoorFacilities.isShare}" listKey="key"
						listValue="value" cssClass="inputtext" cssStyle="width:220px"></s:select>
				</td>
				<td class="tdulleft" style="width: 15%">
					共享运营商：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="outdoorFacilities.shareOperator"
						class="inputtext" style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 18%">
					固定资产编号：
				</td>
				<td class="tdulright" style="width: 32%">
					<input type="text" name="outdoorFacilities.assetCode"
						class="inputtext" style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					条形码：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="outdoorFacilities.barCode"
						class="inputtext" style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 18%">
					维护组：
				</td>
				<td class="tdulright" style="width: 32%">
					<input id="entity_patrolGroupId" name="maintenanceid" value=""
						type="hidden" />
					<input id="entity_maintenanceId" name="patrolgroupid" value=""
						type="hidden" />
					<div id="combotree_patrolgroupdiv" style="width: 220;"></div>
				</td>

				<td class="tdulleft" style="width: 15%">
					经纬度：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="position" name="position"
						class="inputtext required isJW"
						style="width: 220" maxlength="40" />
					<input type="button" value="选择" style="display: none"  onclick="getitude()" />
					<span style="color: red">*（经纬度以逗号隔开）</span>
				</td>

			</tr>
			<tr class="trwhite">
				<td colspan="4" class="tdulleft" style="width: 18%">
					<a href="javascript:void(0);" onclick="addrfids()">增加巡检位置信息</a>
				</td>
			</tr>

			<tr class="trwhite" id="rfid_row_${item.rfid}">
				<td class="tdulleft" style="width: 18%">
					射频卡编号：
				</td>
				<td class="tdulright" style="width: 32%">
					<input id="rfids_0" type="text" name="rfids" onblur="validRfid(this)"
						class="inputtext required" style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					位置描述：
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
					<input type="submit" id="isubmit" class="button" value="添加">
				</td>
				<td>
					<input type="button" class="button" onclick="history.back()"
						value="返回">
				</td>
			</tr>
		</table>
		<div align="center" style="margin-top: 20px; color: red;">
			红色*为必填项
		</div>
	</s:form>
</body>
