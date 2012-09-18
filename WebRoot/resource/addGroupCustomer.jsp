<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

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
	jQuery("#addCustomForm").validate({	
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
		hiddenId : "entity_regionID",
		hiddenName : "entity.regionID",
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
	});//区域
	
	
	Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
	patrolgroupcombotree = new TreeComboField({
		width : 220,
		height:100,
		allowBlank:false,
		leafOnly : true,
		renderTo : 'combotree_patrolgroupdiv',
		name : "patrolgroupname",
		hiddenId : "patrolGroup_id",
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
	});//维护组
	patrolgroupcombotree.on('select', function(combo, record) {
	    if("PATROLMAN"!=record.attributes.objtype){
	        patrolgroupcombotree.setComboValue("","");
	      	jQuery('#entity_patrolGroupId').val("");
		    jQuery('#entity_maintenanceId').val("");
	    }else{
			jQuery('#entity_patrolGroupId').val(record.attributes.id);
			jQuery('#entity_maintenanceId').val(record.parentNode.attributes.id);
		}
	});
	
	//客户类型
	var  grouptype = new Appcombox({
		hiddenId : 'entity_groupType',
	   	hiddenName : 'entity.groupType',
	   	emptyText : '请选择',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=group_type',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'entity_grouptype'
	})
	
	//客户级别--静态数据
	var grouplevel = new Stacombox({
		hiddenId : 'entity_groupLevel',
	   	hiddenName : 'entity.groupLevel',
	   	emptyText : '请选择',
	    fields : ['CODEVALUE', 'LABLE'],
   		data : [['A', 'A'], ['B', 'B']],
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'entity_grouplevel'
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
	 	//var position=$('position').value;
		var actionUrl="/WEBGIS/gisextend/igis.jsp?actiontype=101&userid=${LOGIN_USER.userID}&eid=position";
		window.open(actionUrl,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
	}
	function setitude(){
		//var position = $('position').value;
			//$('baseStation.longitude').value = position.split(',')[0];
			//$('baseStation.latitude').value = position.split(',')[1];
		//jQuery('#baseStation_longitude').value = position.split(',')[0];
		//jQuery('#baseStation_latitude').value = position.split(',')[1];
	}
	
	//表单验证 因为prototype validation  无法对EXTJS的COMBOBOX组件
	//进行验证 ，所以在此验证  以后可考虑换成统一的验证方式
	//add wj
	function check(){
		if(jQuery("#entity_groupLevel").val()==''){
			alert('请选择客户级别');
			return false;
		}
		if(jQuery("#entity_groupType").val()==''){
			alert('请选择客户类型');
			return false;
		}
		if(jQuery("#patrolGroup_id").val()==''){
			alert('请选维护组');
			return false;
		}
		if(jQuery("#entity_regionID").val()==''){
			alert('请选择区域');
			return false;
		}
		return true;
	}	
	
	
	
</script>
</head>
<body>
	<template:titile value="添加集客家客信息" />
	<s:form action="/res/groupCustomerAction_save.jspx"
		name="addantennaFrom" id="addCustomForm" method="post" >
		<table width="850" border="0" id="_table" align="center"
			cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					客户编码：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="entity.groupCode"
						class="inputtext required" style="width: 220px" maxlength="12" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					客户名称：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="entity.groupName"
						class="inputtext required" style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					客户级别：
				</td>
				<td class="tdulright" style="width: 35%">

					<div id="entity_grouplevel" style="width: 220;"></div>	
					
					
					
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					客户类型：
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="entity_grouptype" style="width: 220;"></div>	
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					联系人：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="entity.LinkMan"
						class="inputtext required" style="width: 220px" maxlength="5" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					联系电话：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="entity.phone"
						class="inputtext required isTel" style="width: 220px" maxlength="11" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					客户经理：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="entity.groupManager"
						class="inputtext required" style="width: 220px" maxlength="20" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					维护组：
				</td>
				<td class="tdulright" style="width: 35%">
					<input id="entity_maintenanceId" name="entity.maintenanceId" value="" type="hidden" />
					<input id="entity_patrolGroupId" name="entity.patrolGroupId" value="" type="hidden" />
					<div id="combotree_patrolgroupdiv" style="width: 220;"></div>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					区域：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					<div id="combotree_regionid" style="width: 180;"></div>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					经纬度：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					<input type="text" id="position" name="position"
						class="inputtext required isJW"
						style="width: 220" maxlength="40" />
					<input type="button" style="display: none"  value="选择" onclick="getitude()" />
					<span style="color: red">*（经纬度以逗号隔开）</span>
				</td>
			</tr>
			
			
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					地址：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<input type="text" name="entity.address" class="inputtext required"
						style="width: 645px" maxlength="50" />
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
						class="inputtext required" style="width: 220px" maxlength="30" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					位置描述：
				</td>
				<td class="tdulright">
					<input id="addresses_0" type="text" name="addresses" class="inputtext required"
						style="width: 220px" maxlength="100" />
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
	</s:form>
</body>