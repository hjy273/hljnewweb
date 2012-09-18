<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js"
			type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<head>
	<script language="javascript" type="text/javascript">
	function back() {
		window.location.href = '${ctx}/repeaterAction_query.jspx';
	}
	showOrHide = function(divId) {
		var display = document.getElementById(divId).style.display;
		document.getElementById("baseInfoTb").style.display = "none";
		document.getElementById("baseInfoTb2").style.display = "none";

		if (display == "none") {
			document.getElementById(divId).style.display = "";
		} else {
			document.getElementById(divId).style.display = "none";
		}
	}
	function getitude(){
		var actionUrl="/WEBGIS/gisextend/igis.jsp?actiontype=101&userid=${LOGIN_USER.userID}&eid=position";
		window.open(actionUrl,'map','width=400,height=300,scrollbars=yes,z-look=yes,alwaysRaised=yes');
	}
	
	function getLatitudeAndLongitude(){
		jQuery.ajax({
			  type: 'POST',
			  async: false,
			  url: "${ctx}/repeaterAction_getLatitudeAndLongitude.jspx?msBsId="+jQuery("#repeater_parentId").val(),
			  success: function (data, textStatus){
				  settude(data);
						},
			  dataType: 'html'
			  });
	}
	function settude(responseText){
		jQuery("#position").val(responseText);
	}
	
		
	var rfids = 1;
	function addrfids(){
	   rfids = rfids+1;
	   var _tr='<tr class="trwhite" id="rfids_row_'+rfids+'">'
				+'<td class="tdulleft" style="width:15%">'
				+'	射频卡编号：'
				+'</td>'
				+'<td class="tdulright" style="width:35%">'
				+'	<input id="rfids_'+rfids+'" type="text" name="rfids" onblur="validRfid(this,\'\')" class="inputtext required " style="width:220px" maxlength="40" /><span style="color:red">*</span>'
				+'</td>'
				+'<td class="tdulleft" style="width:15%">'
				+'	位置描述：'
				+'</td>'
				+'<td class="tdulright">'
				+'	<input id="addresses_'+rfids+'" type="text" name="addresses" class="inputtext required" style="width:220px" maxlength="40" /><span style="color:red">*</span>'
				+'	<a href="javascript:void(0);" onclick="delrfids(\'rfids_row_'+rfids+'\')">删除</a>'
				+'</td>'
			    +'</tr>';
		       jQuery("#baseInfoTb2").append(_tr);   
	}
	
	function delrfids(id){
        jQuery("tr[id="+id+"]").remove();
	}
	
	
	
//验证射频卡号唯一  先验证前台，无重复后再验证后台
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
        alert("射频卡号不能重复");
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
				   alert("射频卡号已存在");
				}
			},
  dataType: 'html'
  });
}
}
	
	function check(){
		if(jQuery("#repeater_msBsId").val()==null||jQuery.trim(jQuery("#repeater_msBsId").val())==''){
			alert("没有选择所属基站！");
			return false;
		}
		if(jQuery("#district").value==''){
			alert('请选择所属区县');
			return false;
		}
		if(jQuery("#repeater_coverageArea").value==''){
			alert('请选择覆盖范围');
			return false;
		}
		if(jQuery("#repeater_coverageAreaType").value==''){
			alert('请选择覆盖区域类型');
			return false;
		}
		if(jQuery("#patrolGroupId").value==''){
			alert('请选择维护组');
			return false;
		}
		return true;
	}
	//维护组下拉组件
	var patrolgroupcombotree;
	//所属区县
	var patrolregioncombotree;
	Ext.onReady(function() {
		Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
		jQuery("#updaterepeaterFrom").validate({	
			debug: true, 
			submitHandler: function(form){
				if(check()){
	        		form.submit();
	        	 }
	        }
		});
		patrolgroupcombotree = new TreeComboField({
			width : 220,
			height: 100,
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
		patrolgroupcombotree.setComboValue("${repeater.patrolGroupId}","${repeater.patrolGroupName}");//中文
		patrolgroupcombotree.on('select', function(combo, record) {
		    if("PATROLMAN"!=record.attributes.objtype){
		        patrolgroupcombotree.setComboValue("","");
		      	jQuery('#patrolGroupId').val("");
		      	jQuery('#maintenanceId').val("");
		    }else{
		    	jQuery('#patrolGroupId').val(record.attributes.id);
		    	jQuery('#maintenanceId').val(record.parentNode.attributes.id);
			}
		});
		
		
		patrolregioncombotree = new TreeComboField({
			width : 220,
			allowBlank : false,
			leafOnly : false,
			renderTo : 'combotree_patrolregiondiv',
			name : "regionname",
			hiddenName : "district",
			displayField : 'text',
			height: 100,
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
		patrolregioncombotree.on('select', function(combo, record) {
			var regionId=record.attributes.id;
			document.forms[0].elements["district_input"].value = regionId;
		});
		patrolregioncombotree.setComboValue("${repeater.district}","${repeater.districtName}");
		
		
	    //覆盖区域
		var  coverageArea = new Appcombox({
		   	hiddenName : 'repeater.coverageArea',
		   	emptyText : '请选择',
		   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=overlay_area',
		   	dataCode : 'CODEVALUE',
		   	dataText : 'LABLE',
		   	allowBlank:false,
		   	renderTo: 'repeater_coverageArea'
		})
		coverageArea.setComboValue('${repeater.coverageArea}','<apptag:dynLabel objType="dic" id="${repeater.coverageArea}" dicType="overlay_area"></apptag:dynLabel>');
		//覆盖类型
		var  coverageAreaType = new Appcombox({
		   	hiddenName : 'repeater.coverageAreaType',
		   	emptyText : '请选择',
		   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=overlay_area_type',
		   	dataCode : 'CODEVALUE',
		   	dataText : 'LABLE',
		   	allowBlank:false,
		   	renderTo: 'repeater_coverageAreaType'
		})
		coverageAreaType.setComboValue('${repeater.coverageAreaType}','<apptag:dynLabel objType="dic" id="${repeater.coverageAreaType}" dicType="overlay_area_type"></apptag:dynLabel>');

	});
</script>
</head>
<body>
	<template:titile value="更新直放站" />

	<s:form action="repeaterAction_update" id="updaterepeaterFrom" name="updaterepeaterFrom"
		method="post">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					基础信息
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<input type="hidden" name="district_input" value="" />
					<!-- 
					<a href="javascript:showOrHide('baseInfoTb');">展开/隐藏</a>
					 -->
				</td>
			</tr>
			<tbody id="baseInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						信源基站：
					</td>
					<td class="tdulright" style="width: 35%">
						<s:select list="%{#request.BASESTATIONS}"
							onchange="getLatitudeAndLongitude()"
							value="%{#request.repeater.msBsId}" name="repeater.msBsId" id="repeater_msBsId"
							cssClass="inputtext validate-select-one" cssStyle="width:220"></s:select>
						<span style="color: red">*</span>
					</td>
					<td class="tdulleft" style="width: 15%">
						信源小区号：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.cellCode"
							class="inputtext required" value="${repeater.cellCode}"
							style="width: 220" maxlength="40" />
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						直放站号：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.repeaterType"
							value="${repeater.repeaterType}" class="inputtext required"
							style="width: 220" maxlength="40" />
						<span style="color: red">*</span>
					</td>
					<td class="tdulleft" style="width: 15%">
						直放站名称：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.repeaterName"
							value="${repeater.repeaterName}" class="inputtext required"
							style="width: 220" maxlength="40" />
						<span style="color: red">*</span>
					</td>
				</tr>

				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						网元编码：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.netElementCode"
							value="${repeater.netElementCode}" class="inputtext"
							style="width: 220" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						网元名称：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.netElementName"
							value="${repeater.netElementName}" class="inputtext"
							style="width: 220" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						所属城市：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.city" readonly="readonly"
							value="${sessionScope.LOGIN_USER_REGION_NAME}"
							class="inputtext required" style="width: 220" maxlength="40" />
						<span style="color: red">*</span>
					</td>
					<td class="tdulleft" style="width: 15%">
						所属区县：
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="combotree_patrolregiondiv" style="width: 220;"></div>
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						所属乡镇：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.town" value="${repeater.town}"
							class="inputtext " style="width: 220" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						安装位置：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.installPlace"
							value="${repeater.installPlace}" class="inputtext required"
							style="width: 220" maxlength="40" />
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						海拔(米)：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<input type="text" name="repeater.elevation"
							value="${repeater.elevation}" class="inputtext validate-number "
							style="width: 220" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						覆盖范围：
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="repeater_coverageArea"></div>
						<span style="color: red">*</span>
					</td>
					<td class="tdulleft" style="width: 15%">
						覆盖区域类型：
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="repeater_coverageAreaType"></div>
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						经纬度：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<input type="text" id="position" name="position"
							value="${position}" class="inputtext required isJW"
							style="width: 220" maxlength="40" /><input type="button" style="display: none"  value="选择" onclick="getitude()"/>
						<span style="color: red">*（经度纬度以逗号隔开）</span>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					附加信息
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<!-- 
					<a href="javascript:showOrHide('baseInfoTb2');">展开/隐藏</a>
					 -->
				</td>
			</tr>
			<tbody id="baseInfoTb2" style="display: ;">
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						归属BSC：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<input type="text" name="repeater.attachBsc"
							value="${repeater.attachBsc}" class="inputtext required"
							style="width: 220" maxlength="40" />
						<span style="color: red">*</span>
					</td>

				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						信号接收方式：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.receieMode"
							value="${repeater.receieMode}" class="inputtext "
							style="width: 220" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						供电方式：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.powerSupplyMode"
							value="${repeater.powerSupplyMode}" class="inputtext"
							style="width: 220" maxlength="40" />
					</td>

				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						主从标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<select name="repeater.isMasterSlave" class="inputtext required"
							style="width: 220">
							<option value="y">
								主
							</option>
							<option value="n">
								从
							</option>
						</select>
					</td>
					<td class="tdulleft" style="width: 15%">
						是否监控：
					</td>
					<td class="tdulright" style="width: 35%">
						<select name="repeater.isMonitor" class="inputtext required"
							style="width: 220">
							<option value="y">
								是
							</option>
							<option value="n">
								否
							</option>
						</select>
					</td>

				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						设备型号：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.equModuel"
							value="${repeater.equModuel}" class="inputtext"
							style="width: 220" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						生产厂家：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.producter"
							value="${repeater.producter}" class="inputtext"
							style="width: 220" maxlength="40" />
					</td>

				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						功率：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.power" value="${repeater.power}"
							class="inputtext validate-number" required style="width: 220"
							maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						选频数：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.chooseFrequent"
							value="${repeater.chooseFrequent}" class="inputtext "
							style="width: 220" maxlength="40" />
					</td>

				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						监控SIM卡号：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.monitorSim"
							value="${repeater.monitorSim}" class="inputtext "
							style="width: 220" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						建站时间：
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.createTime"
							value="${repeater.createTime}" class="Wdate inputtext required"
							style="width: 220" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						<span style="color: red">*</span>
					</td>

				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						维护组：
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<input name="repeater.maintenanceId" id="maintenanceId" value="${repeater.maintenanceId }" type="hidden" />
						<input name="patrolGroupId" id="patrolGroupId" value="${repeater.patrolGroupId }" type="hidden" />
						<div id="combotree_patrolgroupdiv" style="width: 220;"></div>
						<font color="#FF0000">*</font>
					</td>
				</tr>


				<tr class="trwhite">
				<td colspan="4" class="tdulleft" style="width:15%">
					<a href="javascript:void(0);" onclick="addrfids()">增加巡检位置信息</a>
					<input type="hidden" name="rfids" value="" />
					<input type="hidden" name="addresses" value="" />
				</td>
			</tr>
			
			
		<c:forEach var="item" items="${patrolAddressInfos}" varStatus="stauts">
               <tr class="trwhite" id="rfid_row_${stauts.index}">
				<td class="tdulleft" style="width:15%">
					射频卡编号：
				</td>
				<td class="tdulright" style="width:35%">
					<input id="rfids_0" type="text" name="rfids" onblur="validRfid(this,'${item.id }')" value="${item.rfid}" class="inputtext required " style="width:220px" maxlength="40" /><span style="color:red">*</span>
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
				
				
				
			</tbody>
		</table>
		<table width="40%" border="0" style="margin-top: 6px" align="center"
			cellpadding="0" cellspacing="0">
			<tr align="center">
				<td>
					<input type="submit" id="isubmit" class="button" value="更新">
				</td>
				<td>
					<input type="button" class="button" onclick=
	history.back();
value="返回">
				</td>
			</tr>
		</table>
		<div align="center" style="margin-top: 20px; color: red;">
			红色*为必填项
		</div>
	</s:form>
</body>
