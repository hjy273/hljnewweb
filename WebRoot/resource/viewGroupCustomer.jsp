<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script src="${ctx}/js/validation/prototype.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/effects.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/validation_cn.js"
	type="text/javascript"></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript"
	src="${ctx}/js/wdatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet"
	href="${ctx}/js/wdatePicker/skin/WdatePicker.css">
<link href="${ctx}/js/extjs/resources/css/ext-all.css" rel="stylesheet"
	type="text/css">
<script src="${ctx}/js/extjs/adapter/ext/ext-base.js"
	type="text/javascript"></script>
<script src="${ctx}/js/extjs/ext-all.js" type="text/javascript"></script>
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
				+'	��Ƶ����ţ�'
				+'</td>'
				+'<td class="tdulright" style="width:35%">'
				+'	<input type="text" name="rfids" onblur="validRfid(this)" class="inputtext required " style="width:220px" maxlength="40" /><span style="color:red">*</span>'
				+'</td>'
				+'<td class="tdulleft" style="width:15%">'
				+'	λ��������'
				+'</td>'
				+'<td class="tdulright">'
				+'	<input type="text" name="addresses" class="inputtext required" style="width:220px" maxlength="40" /><span style="color:red">*</span>'
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

	function getitude(){
	 	var position=$('position').value;
		var actionUrl="/WEBGIS/gisextend/igis.jsp?actiontype=101&userid=${LOGIN_USER.userID}&eid=position";
		window.open(actionUrl,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
	}
	function setitude(){
		var position = $('position').value;
			$('baseStation.longitude').value = position.split(',')[0];
			$('baseStation.latitude').value = position.split(',')[1];
	}
	
	//����֤ ��Ϊprototype validation  �޷���EXTJS��COMBOBOX���
	//������֤ �������ڴ���֤  �Ժ�ɿ��ǻ���ͳһ����֤��ʽ
	//add wj
	function check(){
		if($("entity.groupLevel").value==''){
			alert('��ѡ��ͻ�����');
			return false;
		}
		if($("entity.groupType").value==''){
			alert('��ѡ��ͻ�����');
			return false;
		}
		if($("entity.patrolGroupId").value==''){
			alert('��ѡά����');
			return false;
		}
		if($("entity.regionID").value==''){
			alert('��ѡ������');
			return false;
		}
		return true;
	}		
	
	
	
</script>
</head>
<body>
	<template:titile value="�鿴���ͼҿ���Ϣ" />
	<s:form action="/res/groupCustomerAction_update.jspx"
		name="addantennaFrom" method="post" onsubmit="return check()">
		<input type="hidden" name="entity.id" value="${entity.id }"/>
		<table width="850" border="0" id="_table" align="center"
			cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�ͻ����룺
				</td>
				<td class="tdulright" style="width: 35%">
					${entity.groupCode }
				</td>
				<td class="tdulleft" style="width: 15%">
					�ͻ����ƣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${entity.groupName }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�ͻ�����
				</td>
				<td class="tdulright" style="width: 35%">
					${entity.groupLevel}
				</td>
				<td class="tdulleft" style="width: 15%">
					�ͻ����ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel objType="dic" id="${entity.groupType}" dicType="group_type"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��ϵ�ˣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${entity.linkMan }
				</td>
				<td class="tdulleft" style="width: 15%">
					��ϵ�绰��
				</td>
				<td class="tdulright" style="width: 35%">
					${entity.phone }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�ͻ�����
				</td>
				<td class="tdulright" style="width: 35%">
					${entity.groupManager }
				</td>
				<td class="tdulleft" style="width: 15%">
					ά���飺
				</td>
				<td class="tdulright" style="width: 35%">
					<baseinfo:patrolman displayProperty="patrolname" id="${entity.patrolGroupId}"></baseinfo:patrolman>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					<baseinfo:region displayProperty="regionname" id="${entity.regionID}"></baseinfo:region>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��γ�ȣ�
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${position }
				</td>
			</tr>
			
			
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��ַ��
				</td>
				<td class="tdulright" colspan="3" style="width: 85%" > 
					${entity.address }
				</td>
			</tr>
		<c:forEach var="item" items="${patrolAddressInfos}" varStatus="stauts">
               <tr class="trwhite" id="rfid_row_${stauts.index}">
				<td class="tdulleft" style="width:15%">
					��Ƶ����ţ�
				</td>
				<td class="tdulright" style="width:35%">
					${item.rfid}
				</td>
				<td class="tdulleft" style="width:15%">
					λ��������
				</td>
				<td class="tdulright">
					${item.address}
				</td>
			</tr>
            </c:forEach>


		</table>
		<table width="40%" border="0" style="margin-top: 6px" align="center"
			cellpadding="0" cellspacing="0">
			<tr align="center">
				<td>
					<input type="button" class="button" onclick="history.back()"
						value="����">
				</td>
			</tr>
		</table>
	</s:form>
</body>