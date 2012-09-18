<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appdynlabel.js" type="text/javascript"></script>

<head>
<script language="javascript" type="text/javascript">
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
		       jQuery("#_table").append(_tr);   
	}
	
	function delrfids(id){
        jQuery("tr[id="+id+"]").remove();
	}
	
	
Ext.onReady(function() {
	jQuery("#addIndoorForm").validate({	
		debug: true, 
		submitHandler: function(form){
			if(check()){
        		form.submit();
        	 }
        }
	});
	Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
    patrolregioncombotree = new TreeComboField({
		width : 220,
		height : 100,
		allowBlank : false,
		leafOnly : false,
		renderTo : 'combotree_regionid',
		name : "regionname",
		hiddenId : "ior_regionId",
		hiddenName : "ior.regionId",
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
	
	
	Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
	patrolgroupcombotree = new TreeComboField({
		width : 220,
		height:100,
		allowBlank:false,
		leafOnly : true,
		renderTo : 'combotree_patrolgroupdiv',
		name : "patrolgroupname",
		hiddenId : "ior_patrolGroupId",
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
	      	//$('ior.patrolGroupId').value="";
		    //$('ior.maintenanceId').value="";
	        jQuery('#ior_patrolGroupId').val("");
		    jQuery('#ior_maintenanceId').val("");
	    }else{
			//$('ior.patrolGroupId').value=record.attributes.id;
			//$('ior.maintenanceId').value=record.parentNode.attributes.id;
	    	jQuery('#ior_patrolGroupId').val(record.attributes.id);
			jQuery('#ior_maintenanceId').val(record.parentNode.attributes.id);
		}
	});
	
	//合成方式
	var  blending = new Appcombox({
		hiddenId : 'ior_blending',
	   	hiddenName : 'ior.blending',
	   	emptyText : '请选择',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=blending_type',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'ior_blending_div'
	})

    //覆盖区域
	var  coverageArea = new Appcombox({
		hiddenId : 'ior_coverageArea',
	   	hiddenName : 'ior.coverageArea',
	   	emptyText : '请选择',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=overlay_area',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'ior_coverageArea_div'
	})
	
	//覆盖类型
	var  coverageArea = new Appcombox({
		hiddenId : 'ior_coverageAreaType',
	   	hiddenName : 'ior.coverageAreaType',
	   	emptyText : '请选择',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=overlay_area_type',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'ior_coverageAreaType_div'
	})

})

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

	function getitude(){
	 	//var position=jQuery('#position').val();
		var actionUrl="/WEBGIS/gisextend/igis.jsp?actiontype=101&userid=${LOGIN_USER.userID}&eid=position";
		window.open(actionUrl,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
	}
	function setitude(){
		//var position = $('position').value;
		//	$('baseStation.longitude').value = position.split(',')[0];
		//	$('baseStation.latitude').value = position.split(',')[1];
	}
	
	//表单验证 因为prototype validation  无法对EXTJS的COMBOBOX组件
	//进行验证 ，所以在此验证  以后可考虑换成统一的验证方式
	//add wj
	function check(){
		if(jQuery("#ior_patrolGroupId").val()==''){
			alert('请选择维护组');
			return false;
		}
		if(jQuery("#ior_regionId").val()==''){
			alert('请选择区域');
			return false;
		}
		if(jQuery("#ior_blending").val()==''){
			alert('请选合成方式');
			return false;
		}
		if(jQuery("#ior_coverageArea").val()==''){
			alert('请选择覆盖区域');
			return false;
		}
		if(jQuery("#ior_coverageAreaType").val()==''){
			alert('请选择覆盖类型');
			return false;
		}
		return true;
	}	
	
	
	
</script>
</head>
<body>
	<template:titile value="添加室内分布信息"/>

	<s:form action="/res/indoorOverRideAction_save.jspx" id="addIndoorForm" name="addantennaFrom" method="post" >
		<table width="850" border="0" id="_table" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					分布系统名称：
				</td>
				<td class="tdulright" style="width:35%">
					<input type="text" name="ior.distributeSystem" class="inputtext required" style="width:220px" maxlength="40"/><span style="color:red">*</span>
				</td>
				<td class="tdulleft" style="width:15%">
					维护组：
				</td>
				<td class="tdulright" style="width:35%" >
					<input name="ior.maintenanceId" id="ior_maintenanceId" value="" type="hidden" />
					<input name="ior.patrolGroupId" id="ior_patrolGroupId" value="" type="hidden" />
					<div id="combotree_patrolgroupdiv" style="width: 220;"></div>
					<span style="color: red">*</span>
				</td>
			</tr>
			
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					区域：
				</td>
				<td class="tdulright" style="width:35%">
					<div id="combotree_regionid" style="width: 180;"></div>
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width:15%">
					合成方式：
				</td>
				<td class="tdulright" style="width:35%">
				<div id="ior_blending_div"></div>
				<span style="color: red">*</span>
				</td>
		 </tr>

		<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					入网时间：
				</td>
				<td class="tdulright" style="width:35%">
					<input type="text" name="ior.createtime" class="Wdate inputtext" readonly style="width:220px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				</td>
				<td class="tdulleft" style="width:15%">
					所属站号：
				</td>
				<td class="tdulright" style="width:35%">
					<input type="text" name="ior.stationCode" class="inputtext " style="width:220px" maxlength="15"/>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					经纬度：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					<input type="text" id="position" name="position" class="inputtext required isJW" style="width:220" maxlength="40"/><input type="button" value="选择" style="display: none" onclick="getitude()"/><span style="color:red">*（经纬度以逗号隔开）</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					覆盖区域：
				</td>
				<td class="tdulright" style="width:35%">
				 <div id="ior_coverageArea_div"></div>
				 <span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width:15%">
					覆盖类型:
				</td>
				<td class="tdulright" style="width:35%">
				    <div id="ior_coverageAreaType_div"></div>
				    <span style="color: red">*</span>
				</td>
			</tr>
			
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					信源方式：
				</td>
				<td class="tdulright" style="width:35%">
				    <select name="ior.source">
				     <option value="">请选择</option>
				     <option value="基站">&nbsp;基站&nbsp;</option>
				     <option value="直放站">直放站</option>
				    </select>
				</td>
				<td class="tdulleft" style="width:15%">
					楼层数：
				</td>
				<td class="tdulright" style="width:35%">
					<input type="text" name="ior.floorCount" class="inputtext validate-integer " style="width:220px" maxlength="10"/>
				</td>
			</tr>
			
			
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					地址：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					<input type="text" name="ior.address" class="inputtext required" style="width:645px" maxlength="50"/><span style="color:red">*</span>
				</td>
			</tr>

			<tr class="trwhite">
				<td colspan="4" class="tdulleft" style="width:15%">
					<a href="javascript:void(0);" onclick="addrfids()">增加巡检位置信息</a>
				</td>
			</tr>
			
            <tr class="trwhite" id="rfid_row_${item.rfid}">
				<td class="tdulleft" style="width:15%">
					射频卡编号：
				</td>
				<td class="tdulright" style="width:35%">
					<input id="rfids_0" type="text" name="rfids" onblur="validRfid(this)"  class="inputtext required" style="width:220px" maxlength="30" /><span style="color:red">*</span>
				</td>
				<td class="tdulleft" style="width:15%">
					位置描述：
				</td>
				<td class="tdulright">
					<input id="addresses_0" type="text" name="addresses"  class="inputtext required" style="width:220px" maxlength="100" /><span style="color:red">*</span>
				</td>
			</tr>


		</table>
			<table width="40%" border="0"  style="margin-top: 6px" align="center" cellpadding="0" cellspacing="0"><tr align="center">
		      	<td>
		      		<input type="submit" id="isubmit" class="button" value="添加">
		      	</td>
		      	<td>
		      	  	<input type="button" class="button" onclick="history.back()" value="返回" >
		      	</td>
		    </tr></table>
	</s:form>
</body>