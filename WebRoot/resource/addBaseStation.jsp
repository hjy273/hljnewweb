
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>

<head>
	<script language="javascript" type="text/javascript">
	//维护组下拉组件
	var patrolgroupcombotree;
	//所属区县
	var patrolregioncombotree;
	Ext.onReady(function() {
		//Jquery 校验
		jQuery("#addBasestationForm").validate({	
			debug: true, 
			submitHandler: function(form){
				if(check()){
            		form.submit();
	        	 }
	        }
		});
		patrolgroupcombotree = new TreeComboField({
			width : 220,
			allowBlank:false,
			leafOnly : true,
			height: 100,
			renderTo : 'combotree_patrolgroupdiv',
			name : "patrolgroupname",
			hiddenName : "pid",
			hiddenId : "patrolGroup_id",
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
		
		
		patrolregioncombotree = new TreeComboField({
			width : 220,
			height : 100,
			allowBlank : false,
			leafOnly : false,
			renderTo : 'combotree_patrolregiondiv',
			name : "regionname",
			hiddenName : "district",
			displayField : 'text',
			hiddenId : "district",
			valueField : 'id',
			tree : new Ext.tree.TreePanel({
				autoScroll : true,
				rootVisible : false,
				autoHeight:true, 
				root : new Ext.tree.AsyncTreeNode({
					id : '000000',
					height:100,
					loader : new Ext.tree.TreeLoader({
						dataUrl : '${ctx}/common/externalresources_getRegionJson.jspx'
					})
	
				})
			})
		});
		//机房类型	baseStation.mrType				property_right		basestaion_property_right
		var  property_right = new Appcombox({
			hiddenName : 'baseStation.mrType',
			emptyText : '请选择',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=property_right',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:true,
			renderTo: 'basestaion_property_right'
		});
		//房屋结构	baseStation.roomStructure		room_structure		basestaion_room_structure
		var  room_structure = new Appcombox({
			hiddenName : 'baseStation.roomStructure',
			emptyText : '请选择',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=room_structure',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:true,
			renderTo: 'basestaion_room_structure'
		});
		//地理特征	baseStation.geoFeature			geography			basestaion_geography
		var  geography = new Appcombox({
			hiddenName : 'baseStation.geoFeature',
			emptyText : '请选择',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=geography',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:true,
			renderTo: 'basestaion_geography'
		});
		//基站类型	baseStation.bsType				basestation_type	basestaion_basestation_type
		var  basestation_type = new Appcombox({
			hiddenName : 'baseStation.bsType',
			emptyText : '请选择',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=basestation_type',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:true,
			renderTo: 'basestaion_basestation_type'
		});	
		//基站级别	baseStation.bsLevel				basestation_level	basestaion_basestation_level
		var  basestation_level = new Appcombox({
			hiddenName : 'baseStation.bsLevel',
			hiddenId : 'baseStation_bsLevel',
			emptyText : '请选择',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=basestation_level',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:false,
			renderTo: 'basestaion_basestation_level'
		});
		//基站分类	baseStation.bsSort				basestation_sort	basestaion_basestation_sort
		var  basestation_sort = new Appcombox({
			hiddenName : 'baseStation.bsSort',
			emptyText : '请选择',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=basestation_sort',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:true,
			renderTo: 'basestaion_basestation_sort'
		});
		//覆盖区域类型	baseStation.coverageAreaType	overlay_area_type	basestaion_overlay_area_type
		var  overlay_area_type = new Appcombox({
			hiddenId : 'baseStation_coverageAreaType',
			hiddenName : 'baseStation.coverageAreaType',
			emptyText : '请选择',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=overlay_area_type',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:false,
			renderTo: 'basestaion_overlay_area_type'
		});
		//覆盖区域	baseStation.coverageArea		overlay_area		basestaion_overlay_area
		var  overlay_area = new Appcombox({
			hiddenId : 'baseStation_coverageArea',
			hiddenName : 'baseStation.coverageArea',
			emptyText : '请选择',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=overlay_area',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:false,
			renderTo: 'basestaion_overlay_area'
		});
	});
	//function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
	function validCode(){
		//if($("baseStation.stationCode").value.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "") == ""||$("baseStation.stationCode").value==null){
		//	msg.innerHTML ="站址编号不能为空或者null";
		//	return;
		//}
		var urls = 'baseStationAction_valid.jspx?stationCode='+jQuery("#baseStation_stationCode").val();
		//new Ajax.Request(urls,{
		//	method:'post',
		//	evalScripts:true,
		//	onSuccess:function(transport){
		//		msg.innerHTML = transport.responseText;
		//		if(transport.responseText != ""){
		//			$("baseStation.stationCode").value="";
		//		}
		//	},
		//	onFailure: function(){ alert('请求服务异常......') }
		//});
		
		jQuery.ajax({
			  type: 'POST',
			  async: false,
			  url: urls,//"${ctx}/baseStationAction_getStation.jspx?district=&&id="+ jQuery("#stationName").val(),
			  success: function (data, textStatus){
				  //setStationCode(data);
				  msg.innerHTML = data;
				  if(data != ""){
					  jQuery("#baseStation_stationCode").val("");
				  }
			  },
			  dataType: 'html'
			  });
	}
	function check(){
		//alert($("baseStation.bsLevel").value);
		if(jQuery("#baseStation_bsLevel").val()==''){
			alert('请选择基站级别');
			//$('baseStation.bsLevel').focus();
			return false;
		}
		if(jQuery("#baseStation_coverageAreaType").val()==''){
			alert('请选择覆盖区域类型');
			//$('district').focus();
			return false;
		}
		if(jQuery("#baseStation_coverageArea").val()==''){
			alert('请选择覆盖区域');
			//$('district').focus();
			return false;
		}
		if(jQuery("#district").val()==''){
			alert('请选择所属区县');
			//$('district').focus();
			return false;
		}
		if(jQuery("#patrolGroup_id").val()==''){
			alert('请选择维护组');
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
	};
	function getitude(){
	 	//var position=$('position').value;
		var actionUrl="/WEBGIS/gisextend/igis.jsp?actiontype=101&userid=${LOGIN_USER.userID}&eid=position";
		window.open(actionUrl,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
	}
	function setitude(){
		//var position = $('position').value;
		//	$('baseStation.longitude').value = position.split(',')[0];
		//	$('baseStation.latitude').value = position.split(',')[1];
	}
	
		
	var rfids = 1;
	function addrfids(){
	   rfids = rfids+1;
	   var _tr='<tr class="trwhite" id="rfids_row_'+rfids+'">'
				+'<td class="tdulleft" style="width:15%">'
				+'	射频卡编号：'
				+'</td>'
				+'<td class="tdulright" style="width:35%">'
				+'	<input id="rfids_'+rfids+'" type="text" name="rfids" onblur="validRfid(this)" class="inputtext required " style="width:220px" maxlength="40" /><span style="color:red">*</span>'
				+'</td>'
				+'<td class="tdulleft" style="width:15%">'
				+'	位置描述：'
				+'</td>'
				+'<td class="tdulright">'
				+'	<input id="addresses_'+rfids+'" type="text" name="addresses" class="inputtext required" style="width:220px" maxlength="40" /><span style="color:red">*</span>'
				+'	<a href="javascript:void(0);" onclick="delrfids(\'rfids_row_'+rfids+'\')">删除</a>'
				+'</td>'
			    +'</tr>';
		       jQuery("#otherInfoTb").append(_tr);   
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
	
</script>
</head>
<body>
	<template:titile value="添加基站" />
	<s:form action="baseStationAction_save" name="addStationFrom"
		method="post" id="addBasestationForm">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					基础信息
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<!-- 
					<a href="javascript:showOrHide('baseInfoTb');">展开/隐藏</a>
					 -->
				</td>
			</tr>
			<tbody id="baseInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						站址编号：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.stationCode" id="baseStation_stationCode"
							onblur="validCode()" class="inputtext validate-alphanum required"
							style="width: 220px" maxlength="40" />
						<span style="color: red">*</span>
						<span id="msg" style="color: red"></span>
					</td>
					<td class="tdulleft" style="width: 15%">
						基站名称：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.stationName"
							class="inputtext required" style="width: 220px" maxlength="50" />
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						所属地市：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.city" readonly="readonly"
							value="${LOGIN_USER_REGION_NAME}" class="inputtext required"
							style="width: 220px" maxlength="40" />
						<span style="color: red">*</span>
					</td>
					<td class="tdulleft" style="width: 15%">
						所属区县：
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="combotree_patrolregiondiv" style="width: 180;"></div>
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						所属乡镇：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.town" class="inputtext "
							style="width: 220px" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						所属行政村：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.village" class="inputtext "
							style="width: 220px" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						详细地址：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.address"
							class="inputtext required" style="width: 220px" maxlength="40" />
						<span style="color: red">*</span>
					</td>
					<td class="tdulleft" style="width: 15%">
						入网时间：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.anTime"
							class="Wdate inputtext required" style="width: 220px"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						机房类型：
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="basestaion_property_right"></div>
					</td>
					<td class="tdulleft" style="width: 15%">
						房屋面积：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.roomArea"
							class="number " style="width: 220px"
							maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						房屋结构：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<div id="basestaion_room_structure"></div>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					地理信息
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<!-- 
					<a href="javascript:showOrHide('geoInfoTb');">展开/隐藏</a>
					 -->
				</td>
			</tr>
			<tbody id="geoInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						海拔(米)：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.elevation"
							class="number " style="width: 220px"
							maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						地理特征：
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="basestaion_geography"></div>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						经纬度：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<input type="text" name="position"
							class="inputtext required isJW
							style="width: 220px" maxlength="40"  />
						<input type="button" style="display: none" value="选择" onclick="getitude()" />
						<span style="color: red">*（经度纬度以逗号隔开）</span>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					标识信息
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<!-- 
					<a href="javascript:showOrHide('flagInfoTb');">展开/隐藏</a>
					 -->
				</td>
			</tr>
			<tbody id="flagInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						边界基站标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'否','y':'是'}" name="baseStation.isBoundary"
							listKey="key" listValue="value" cssClass="inputtext"
							cssStyle="width:220px"></s:select>
					</td>
					<td class="tdulleft" style="width: 15%">
						VIP基站标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'否','y':'是'}" name="baseStation.isVip"
							listKey="key" listValue="value" cssClass="inputtext"
							cssStyle="width:220px"></s:select>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						传输节点标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'否','y':'是'}"
							name="baseStation.isTransitNode" listKey="key" listValue="value"
							cssClass="inputtext" cssStyle="width:220px"></s:select>
					</td>
					<td class="tdulleft" style="width: 15%">
						边际站标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'否','y':'是'}" name="baseStation.isLimit"
							listKey="key" listValue="value" cssClass="inputtext"
							cssStyle="width:220px"></s:select>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						室内覆盖标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'否','y':'是'}"
							name="baseStation.isIndoorCoverage" listKey="key"
							listValue="value" cssClass="inputtext" cssStyle="width:220px"></s:select>
					</td>
					<td class="tdulleft" style="width: 15%">
						村通标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'否','y':'是'}"
							name="baseStation.isEveryVillage" listKey="key" listValue="value"
							cssClass="inputtext" cssStyle="width:220px"></s:select>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						施主基站标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'否','y':'是'}"
							name="baseStation.isBenefactorBs" listKey="key" listValue="value"
							cssClass="inputtext" cssStyle="width:220px"></s:select>
					</td>
					<td class="tdulleft" style="width: 15%">
						下挂基站标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'否','y':'是'}" name="baseStation.isDrive"
							listKey="key" listValue="value" cssClass="inputtext"
							cssStyle="width:220px"></s:select>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					站点情况
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<!-- 
					<a href="javascript:showOrHide('stationInfoTb');">展开/隐藏</a>
					 -->
				</td>
			</tr>
			<tbody id="stationInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						卫生洁具：
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="#{'n':'否','y':'是'}"
							name="baseStation.isSanitaryWare" listKey="key" listValue="value"
							cssClass="inputtext" cssStyle="width:220px"></s:select>
					</td>
					<td class="tdulleft" style="width: 15%">
						市电引入情况：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.mainsLeadin"
							class="inputtext " style="width: 220px" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						门锁情况：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.gateLock" class="inputtext"
							style="width: 220px" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						遗留问题：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.leaveOverQuestion"
							class="inputtext" style="width: 220px" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						业主联系人：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.owner"
							class="inputtext required" style="width: 220px" maxlength="40" />
						<span style="color: red">*</span>
					</td>
					<td class="tdulleft" style="width: 15%">
						业主联系电话：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.ownerTel"
							class="inputtext required isTel" style="width: 220px" maxlength="40" />
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						维护组：
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<input id="entity_maintenanceId" name="baseStation.maintenanceId" type="hidden" />
						<input id="entity_patrolGroupId" name="patrolGroupId" value="" type="hidden" />
						<div id="combotree_patrolgroupdiv" style="width: 220;"></div>
						<span style="color: red">*</span>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					其他信息
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<!-- 
					<a href="javascript:showOrHide('otherInfoTb');">展开/隐藏</a>
					 -->
				</td>
			</tr>
			<tbody id="otherInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						归属BSC：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.attachBSC" class="inputtext "
							style="width: 220px" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						BCF数量：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.bcfNum"
							class="inputtext validate-integer " style="width: 220px"
							maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						频段：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.frequencyChannel"
							class="inputtext " style="width: 220px" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						基站类型：
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="basestaion_basestation_type"></div>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						基站标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.bsIdentifies"
							class="inputtext " style="width: 220px" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						基站级别：
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="basestaion_basestation_level"></div>
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						基站分类：
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="basestaion_basestation_sort"></div>
					</td>
					<td class="tdulleft" style="width: 15%">
						基站配置：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.bsDeploy" class="inputtext"
							style="width: 220px" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						区域类别：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="baseStation.areaType" class="inputtext"
							style="width: 220px" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						覆盖区域类型：
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="basestaion_overlay_area_type"></div>
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						覆盖区域：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<div id="basestaion_overlay_area"></div>
						<span style="color: red">*</span>
					</td>
				</tr>



				<tr class="trwhite">
					<td colspan="4" class="tdulleft" style="width: 15%">
						<a href="javascript:void(0);" onclick="addrfids()">增加巡检位置信息</a>
					</td>
				</tr>

				<tr class="trwhite" id="rfid_row_${item.rfid}">
					<td class="tdulleft" style="width: 15%">
						射频卡编号：
					</td>
					<td class="tdulright" style="width: 35%">
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



			</tbody>

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
