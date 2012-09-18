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
	jQuery("#editGroupCustomerFrom").validate({	
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
		renderTo : 'combotree_regionid',
		name : "regionname",
		hiddenName : "entity.regionID",
		hiddenId : "regionID",
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
	patrolregioncombotree.setComboValue('${entity.regionID}','<baseinfo:region displayProperty="regionname" id="${entity.regionID}"></baseinfo:region>');
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
	patrolgroupcombotree.setComboValue('${entity.patrolGroupId}','<baseinfo:patrolman displayProperty="patrolname" id="${entity.patrolGroupId}"></baseinfo:patrolman>');
	
	
	
	//客户类型
	var  grouptype = new Appcombox({
	   	hiddenName : 'entity.groupType',
	   	hiddenId : 'groupType',
	   	emptyText : '请选择',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=group_type',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'entity_grouptype'
	})
	grouptype.setComboValue('${entity.groupType}','<apptag:dynLabel objType="dic" id="${entity.groupType}" dicType="group_type"></apptag:dynLabel>');
	
	//客户类型--静态数据
	var grouplevel = new Stacombox({
	   	hiddenName : 'entity.groupLevel',
	   	hiddenId:'groupLevel',
	   	emptyText : '请选择',
	    fields : ['CODEVALUE', 'LABLE'],
   		data : [['A', 'A'], ['B', 'B']],
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'entity_grouplevel'
	})
	grouplevel.setComboValue('${entity.groupLevel}','${entity.groupLevel}');

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
	 	var position=$('position').value;
		var actionUrl="/WEBGIS/gisextend/igis.jsp?actiontype=101&userid=${LOGIN_USER.userID}&eid=position";
		window.open(actionUrl,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
	}
	//表单验证 因为prototype validation  无法对EXTJS的COMBOBOX组件
	//进行验证 ，所以在此验证  以后可考虑换成统一的验证方式
	//add wj
	function check(){
		if($("#groupLevel").val()==''){
			alert('请选择客户级别');
			return false;
		}
		if($("#groupType").val()==''){
			alert('请选择客户类型');
			return false;
		}
		if($("#entity_patrolGroupId").val()==''){
			alert('请选维护组');
			return false;
		}
		if($("#regionID").val()==''){
			alert('请选择区域');
			return false;
		}
		return true;
	}		
	
	
	
</script>
</head>
<body>
	<template:titile value="修改集客家客信息" />
	<s:form action="/res/groupCustomerAction_update.jspx"
		name="editGroupCustomerFrom" id="editGroupCustomerFrom" method="post">
		<input type="hidden" name="entity.id" value="${entity.id }"/>
		<table width="850" border="0" id="_table" align="center"
			cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					客户编码：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="entity.groupCode" value="${entity.groupCode }" 
						class="inputtext required" style="width: 220px" maxlength="12" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					客户名称：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="entity.groupName" value="${entity.groupName }" 
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
					<input type="text" name="entity.LinkMan" value="${entity.linkMan }" 
						class="inputtext required" style="width: 220px" maxlength="5" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					联系电话：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="entity.phone" value="${entity.phone }" 
						class="inputtext required isTel" style="width: 220px" maxlength="11" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					客户经理：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="entity.groupManager" value="${entity.groupManager }" 
						class="inputtext required" style="width: 220px" maxlength="20" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					维护组：
				</td>
				<td class="tdulright" style="width: 35%">
					<input id="entity_maintenanceId" name="entity.maintenanceId" value="${entity.maintenanceId}" type="hidden" />
					<input id="entity_patrolGroupId" name="entity.patrolGroupId" value="${entity.patrolGroupId}" type="hidden" />
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
					<input type="text" id="position" name="position" value="${position }" 
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
				<td class="tdulright" colspan="3" style="width: 85%" > 
					<input type="text" name="entity.address" class="inputtext required" value="${entity.address }" 
						style="width: 645px" maxlength="50" />
					<span style="color: red">*</span>
				</td>
			</tr>

			<tr class="trwhite">
				<td colspan="4" class="tdulleft" style="width: 15%">
					<a href="javascript:void(0);" onclick="addrfids()">增加巡检位置信息</a>
				</td>
			</tr>

		<c:forEach var="item" items="${patrolAddressInfos}" varStatus="stauts">
               <tr class="trwhite" id="rfid_row_${stauts.index}">
				<td class="tdulleft" style="width:15%">
					射频卡编号：
				</td>
				<td class="tdulright" style="width:35%">
					<input id="rfids_0" type="text" name="rfids" value="${item.rfid}" onblur="validRfid(this,'${item.id }')" class="inputtext required " style="width:220px" maxlength="40" /><span style="color:red">*</span>
				</td>
				<td class="tdulleft" style="width:15%">
					位置描述：
				</td>
				<td class="tdulright">
					<input id="addresses_0" type="text" name="addresses" value="${item.address}" class="inputtext required " style="width:220px" maxlength="40" /><span style="color:red">*</span>
					<c:if test="${stauts.index > 0}">
						<a href="javascript:void(0);" onclick="delrfids('rfid_row_${stauts.index}')">删除</a>
					</c:if>
				</td>
			</tr>
            </c:forEach>


		</table>
		<table width="40%" border="0" style="margin-top: 6px" align="center"
			cellpadding="0" cellspacing="0">
			<tr align="center">
				<td>
					<input type="submit" id="isubmit" class="button" value="修改">
				</td>
				<td>
					<input type="button" class="button" onclick="history.back()"
						value="返回">
				</td>
			</tr>
		</table>
	</s:form>
</body>