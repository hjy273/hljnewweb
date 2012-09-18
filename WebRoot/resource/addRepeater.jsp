<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<script src="${ctx}/js/prototype.js" type="text/javascript"></script>

<head>
	<script language="javascript" type="text/javascript">
	function getStation() {
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
		jQuery("#repeater_msBsId option").length = 1;
		var str = responseText;
		if (str == "")
			return true;
		var optionlist = str.split(";");
		for ( var i = 0; i < optionlist.length - 1; i++) {
			var v = optionlist[i].split("=")[0];
			var t = optionlist[i].split("=")[1];
			jQuery("<option value='"+v+"'>"+t+"</option>").appendTo("#repeater_msBsId");
		}
	}
	function back(){
		window.location.href='${ctx}/repeaterAction_query.jspx';
	}
	showOrHide=function(divId){
		var display=document.getElementById(divId).style.display;
		document.getElementById("baseInfoTb").style.display="none";
		document.getElementById("baseInfoTb2").style.display="none";
		
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
	
	function getLatitudeAndLongitude(){
		jQuery.ajax({
			  type: 'POST',
			  async: false,
			  url: "${ctx}/repeaterAction_getLatitudeAndLongitude.jspx?msBsId="+jQuery("#repeater_msBsId").val(),
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
				+'	��Ƶ����ţ�'
				+'</td>'
				+'<td class="tdulright" style="width:35%">'
				+'	<input id="rfids_'+rfids+'" type="text" name="rfids" onblur="validRfid(this)" class="inputtext required " style="width:220px" maxlength="40" /><span style="color:red">*</span>'
				+'</td>'
				+'<td class="tdulleft" style="width:15%">'
				+'	λ��������'
				+'</td>'
				+'<td class="tdulright">'
				+'	<input id="addresses_'+rfids+'" type="text" name="addresses" class="inputtext required" style="width:220px" maxlength="40" /><span style="color:red">*</span>'
				+'	<a href="javascript:void(0);" onclick="delrfids(\'rfids_row_'+rfids+'\')">ɾ��</a>'
				+'</td>'
			    +'</tr>';
		       jQuery("#baseInfoTb2").append(_tr);   
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
		if(jQuery("#repeater_msBsId").val()==null||jQuery.trim(jQuery("#repeater_msBsId").val())==''){
			alert("û��ѡ��������վ��");
			return false;
		}
		if(jQuery("#district").value==''){
			alert('��ѡ����������');
			return false;
		}
		if(jQuery("#repeater_coverageArea").value==''){
			alert('��ѡ�񸲸Ƿ�Χ');
			return false;
		}
		if(jQuery("#repeater_coverageAreaType").value==''){
			alert('��ѡ�񸲸���������');
			return false;
		}
		if(jQuery("#patrolGroupId").value==''){
			alert('��ѡ��ά����');
			return false;
		}
		return true;
	}
	//ά�����������
	var patrolgroupcombotree;
	//��������
	var patrolregioncombotree;
	Ext.onReady(function() {
		Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
		//����Jquery��֤
		jQuery("#addrepeaterFrom").validate({	
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
			height: 100,
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
		        jQuery("#patrolGroupId").val("");
		        jQuery("#repeater_maintenanceId").val("");
		    }else{
		    	jQuery("#patrolGroupId").val(record.attributes.id);
		    	jQuery("#repeater_maintenanceId").val(record.parentNode.attributes.id);
			}
		});

		patrolregioncombotree = new TreeComboField({
			width : 220,
			allowBlank : false,
			leafOnly : false,
			height:100, 
			renderTo : 'combotree_patrolregiondiv',
			name : "regionname",
			hiddenName : "district",
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
		//��������
		var  coverageArea = new Appcombox({
		   	hiddenName : 'repeater.coverageArea',
		   	emptyText : '��ѡ��',
		   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=overlay_area',
		   	dataCode : 'CODEVALUE',
		   	dataText : 'LABLE',
		   	allowBlank:false,
		   	renderTo: 'repeater_coverageArea'
		})
		//��������
		var  coverageArea = new Appcombox({
		   	hiddenName : 'repeater.coverageAreaType',
		   	emptyText : '��ѡ��',
		   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=overlay_area_type',
		   	dataCode : 'CODEVALUE',
		   	dataText : 'LABLE',
		   	allowBlank:false,
		   	renderTo: 'repeater_coverageAreaType'
		})
		
	});
</script>
</head>
<body>
	<template:titile value="���ֱ��վ" />

	<s:form id="addrepeaterFrom" action="repeaterAction_save"
		name="addrepeaterFrom" method="post" >
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
						��Դ��վ��
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" id="stationName" name="stationName"
							onchange="getStation()" value="�������ѯ���ݣ��ڵ�������б�"
							onmouseover="this.select()" class="inputtext" style="width: 220"
							maxlength="40" />
						<br>
						<select name="repeater.msBsId"
							onchange="getLatitudeAndLongitude()" id="repeater_msBsId"
							class="inputtext required" style="width: 220">
							<option value="">
								��ѡ��
							</option>
						</select>
						<span style="color: red">*</span>
					</td>
					<td class="tdulleft" style="width: 15%">
						��ԴС���ţ�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.cellCode"
							class="inputtext required" style="width: 220" maxlength="40" />
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						ֱ��վ�ţ�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.repeaterType"
							class="inputtext required" style="width: 220" maxlength="40" />
						<span style="color: red">*</span>
					</td>
					<td class="tdulleft" style="width: 15%">
						ֱ��վ���ƣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.repeaterName"
							class="inputtext required" style="width: 220" maxlength="40" />
						<span style="color: red">*</span>
					</td>
				</tr>

				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						��Ԫ���룺
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.netElementCode"
							class="inputtext" style="width: 220" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						��Ԫ���ƣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.netElementName"
							class="inputtext" style="width: 220" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						�������У�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.city" readonly="readonly"
							value="${sessionScope.LOGIN_USER_REGION_NAME}"
							class="inputtext required" style="width: 220" maxlength="40" />
						<span style="color: red">*</span>
					</td>
					<td class="tdulleft" style="width: 15%">
						�������أ�
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="combotree_patrolregiondiv" style="width: 220;"></div>
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						��������
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.town" class="inputtext"
							style="width: 220" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						��װλ�ã�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.installPlace"
							class="inputtext required" style="width: 220" maxlength="40" />
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						����(��)��
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<input type="text" name="repeater.elevation"
							class="inputtext number" style="width: 220" maxlength="40" />
					</td>

				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���Ƿ�Χ��
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="repeater_coverageArea"></div>
						<span style="color: red">*</span>
					</td>
					<td class="tdulleft" style="width: 15%">
						�����������ͣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<div id="repeater_coverageAreaType"></div>
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��γ�ȣ�
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<input type="text" id="position" name="position"
							class="inputtext required isJW"
							style="width: 220" maxlength="40" />
						<input type="button" value="ѡ��" style="display: none"  onclick="getitude()" />
						<span style="color: red">*����γ���Զ��Ÿ�����</span>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<!-- 
					<a href="javascript:showOrHide('baseInfoTb2');">չ��/����</a>
					 -->
				</td>
			</tr>
			<tbody id="baseInfoTb2" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						����BSC��
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<input type="text" name="repeater.attachBsc"
							class="inputtext required" style="width: 220" maxlength="40" />
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						�źŽ��շ�ʽ��
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.receieMode" class="inputtext"
							style="width: 220" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						���緽ʽ��
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.powerSupplyMode"
							class="inputtext" style="width: 220" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						���ӱ�ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<select name="repeater.isMasterSlave" class="inputtext required"
							style="width: 220">
							<option value="y">
								��
							</option>
							<option value="n">
								��
							</option>
						</select>
					</td>
					<td class="tdulleft" style="width: 15%">
						�Ƿ��أ�
					</td>
					<td class="tdulright" style="width: 35%">
						<select name="repeater.isMonitor" class="inputtext required"
							style="width: 220">
							<option value="y">
								��
							</option>
							<option value="n">
								��
							</option>
						</select>
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						�豸�ͺţ�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.equModuel" class="inputtext"
							style="width: 220" maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						�������ң�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.producter" class="inputtext"
							style="width: 220" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						���ʣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.power"
							class="inputtext validate-number" style="width: 220"
							maxlength="40" />
					</td>
					<td class="tdulleft" style="width: 15%">
						ѡƵ����
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.chooseFrequent"
							class="inputtext" style="width: 220" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						ά���飺
					</td>
					<td class="tdulright" style="width: 35%">
						<input name="repeater.maintenanceId" id="repeater_maintenanceId"
							value="" type="hidden" />
						<input name="patrolGroupId" id="patrolGroupId" value=""
							type="hidden" />
						<div id="combotree_patrolgroupdiv" style="width: 220;"></div>
						<font color="#FF0000">*</font>

					</td>
					<td class="tdulleft" style="width: 15%">
						���SIM���ţ�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="repeater.monitorSim" class="inputtext"
							style="width: 220" maxlength="40" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��վʱ�䣺
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<input type="text" name="repeater.createTime"
							class="Wdate inputtext required" style="width: 220"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						<span style="color: red">*</span>
					</td>
				</tr>
				<tr class="trwhite">
					<td colspan="4" class="tdulleft" style="width: 15%">
						<a href="javascript:void(0);" onclick="addrfids()">����Ѳ��λ����Ϣ</a>
					</td>
				</tr>

				<tr class="trwhite" id="rfid_row_${item.rfid}">
					<td class="tdulleft" style="width: 15%">
						��Ƶ����ţ�
					</td>
					<td class="tdulright" style="width: 35%">
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

			</tbody>
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
