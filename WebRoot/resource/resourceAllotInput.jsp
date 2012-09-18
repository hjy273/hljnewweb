<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/resource/selected_options.js" type="text/javascript"></script>
<script src="${ctx}/js/prototype.js" type="text/javascript"></script>
<head>
	<script language="javascript" type="text/javascript">
function getPatrolMans(contractorId,elementId){
	//var contractorId = jQuery('#resourceAllot_newMaintenceId').val();
	var actionUrl = "${ctx}/patrolAction.do?method=getPatrolMansByParentId&&parentID="+contractorId+"&&rnd="+Math.random();
	//new Ajax.Request(encodeURI(url),{
	//	method: 'post',
    // 	evalScripts: true,
    // 	onSuccess: function(transport){
    //		var str = transport.responseText;
    //		document.all.newPatrolManId.options.length = 0;
	//		if(str != ''){
	//			var strings = str.split(";");
	//			for(var i=0; i<strings.length; i++){
	//				if(strings[i] != ""){
	//					var xstrings = strings[i].split(",");
	//					var option = new Option(xstrings[0],xstrings[1]);
	//					document.all.newPatrolManId.options.add(option);
	//				}
	//			}
	//		}else{
	//			var option = new Option("��","");
	//			document.all.newPatrolManId.options.add(option);
	//		}    			
    // 	},
    // 	onFailure: function(){
	//		alert("Ѳ����δ֪�쳣��");
	//	}       		
	//});
	jQuery.ajax({
		url:actionUrl,
		success:function(data,textStatus,jqXHR){
			var str = jqXHR.responseText;
			//document.getElementById(elementId).options.length = 0;
			if(str != ''){
				var strings = str.split(";");
				for(var i=0; i<strings.length; i++){
					if(strings[i] != ""){
						var xstrings = strings[i].split(",");
						//var option = new Option(xstrings[0],xstrings[1]);
						//document.getElementById(elementId).options.add(option);
						jQuery(elementId).append("<option value='"+xstrings[1]+"'>"+xstrings[0]+"</option>");
					}
				}
			}else{
				//var option = new Option("��","");
				//document.getElementById(elementId).options.add(option);
				jQuery(elementId).append("<option value=''>��</option>");
			}
		},
		error:function(){
			alert("Ѳ����δ֪�쳣��");
		}
	});
}
function changeResources(){
	var contractorId = jQuery('#resourceAllot_oldMaintenceId').val();
	var patrolmanId = jQuery('#resourceAllot_oldPatrolManId').val();
	var resourceName = jQuery('#resourceName').val();
	var resourceType = "";
	var resourceTypeElement=document.forms[0].elements["resourceType"];
	for(i=0;i<resourceTypeElement.length;i++){
		if(resourceTypeElement[i].checked){
			resourceType=resourceTypeElement[i].value;
			break;
		}
	}
	if(resourceType==""){
		return;
	}
	var actionUrl = "${ctx}/resourceAllotAction_getResources.jspx?";
	actionUrl += "parameter.oldMaintenceId="+contractorId;
	actionUrl += "&&parameter.oldPatrolmanId="+patrolmanId;
	actionUrl += "&&parameter.resourceName="+resourceName;
	actionUrl += "&&parameter.resourceType="+resourceType;
	actionUrl += "&&rnd="+Math.random();
	//new Ajax.Request(encodeURI(url),{
	//	method: 'post',
    //	evalScripts: true,
    // 	onSuccess: function(transport){
    // 		var str = transport.responseText;
    // 		document.all.oldResources.options.length = 0;
	//		if(str != ''){
	//			var strings = str.split(";");
	//			for(var i=0; i<strings.length; i++){
	//				if(strings[i] != ""){
	//					var xstrings = strings[i].split(",");
	//					var option = new Option(xstrings[1],xstrings[0]);
	//					document.all.oldResources.options.add(option);
	//				}
	//			}
	//		}       			
    // 	},
    // 	onFailure: function(){
	//		alert("��ȡ��Դδ֪�쳣��");
	//	}       		
	//});
	jQuery.ajax({
		url:actionUrl,
		success:function(data,textStatus,jqXHR){
			var str = jqXHR.responseText;
			document.all.oldResources.options.length = 0;
			if(str != ''){
				var strings = str.split(";");
				for(var i=0; i<strings.length; i++){
					if(strings[i] != ""){
						var xstrings = strings[i].split(",");
						var option = new Option(xstrings[1],xstrings[0]);
						document.all.oldResources.options.add(option);
					}
				}
			}    
		},
		error:function(){
			alert("��ȡ��Դδ֪�쳣��");
		}
	});
}
	function check(){
		var target = document.getElementById("newResources");
		if(jQuery("#resourceAllot_oldMaintenceId").val()==""){
			alert("��ѡ��ԭ��ά��λ��");
			return false;
		}
		if(jQuery("#resourceAllot_newMaintenceId").val()==""){
			alert("��ѡ�������ά����λ��");
			return false;
		}
		if(jQuery("#resourceAllot_newPatrolManId").val()==""){
			alert("��ѡ�������ά��Ѳ���飡");
			return false;
		}
		if(target.options.length==0){
			alert("��ѡ��Ҫ���·������Դ��");
			return false;
		}
		if(target.options.length>200){
			alert("һ��Ѳ��ά���鲻�ܷ��䳬��200����Դ��");
			return false;
		}
		for(var i=0; i<target.options.length; i++){
             target.options[i].selected=true;
     	}
		return true;
	}
</script>
</head>
<body>
	<template:titile value="������Դ����" />
	<s:form action="resourceAllotAction_confirm" name="resourceAllotForm"
		id="resourceAllotAction_confirm" onsubmit="return check()"
		method="post">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					ԭ��ά��λ��
				</td>
				<td class="tdulright" style="width: 85%">
					<select name="parameter.oldMaintenceId"
						id="resourceAllot_oldMaintenceId" class="inputtext"
						style="width: 200"
						onchange="getPatrolMans(this.value,'#resourceAllot_oldPatrolManId');changeResources();">
						<option value="">
							��ѡ��
						</option>
						<c:forEach var="oneDept" items="${deptCollection}">
							<option value="${oneDept.value }">
								${oneDept.label }
							</option>
						</c:forEach>
					</select>
					<select name="parameter.oldPatrolmanId"
						id="resourceAllot_oldPatrolManId" class="inputtext"
						style="width: 200" onchange="changeResources();">
						<option value="">
							ȫ��ά����
						</option>
						<option value="-1">
							��ά����
						</option>
					</select>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					������ά����λ��
				</td>
				<td class="tdulright" style="width: 85%">
					<select name="parameter.newMaintenceId"
						id="resourceAllot_newMaintenceId" class="inputtext"
						style="width: 200"
						onchange="getPatrolMans(this.value,'#resourceAllot_newPatrolManId');">
						<option value="">
							��ѡ��
						</option>
						<c:forEach var="oneDept" items="${deptCollection}">
							<option value="${oneDept.value }">
								${oneDept.label }
							</option>
						</c:forEach>
					</select>
					<select name="parameter.newPatrolmanId"
						id="resourceAllot_newPatrolManId" class="inputtext"
						style="width: 200">
						<option value="">
							��ѡ��ά����
						</option>
					</select>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��Դ���ͣ�
				</td>
				<td class="tdulright" style="width: 85%">
					<c:forEach var="oneResourceType"
						items="${sessionScope.RESOURCE_TYPE_MAP}">
						<input name="parameter.resourceType" id="resourceType"
							value="${oneResourceType.key}" type="radio"
							onclick="changeResources();">
						${oneResourceType.value }
					</c:forEach>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%;">
					ѡ����Դ��
				</td>
				<td class="tdulright" style="width: 85%;">
					<input name="parameter.resourceName" id="resourceName" type="text"
						onblur="changeResources();" class="inputtext" />
					��������Դ���ƽ���ģ����ѯ��
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="2" style="text-align: center;">
					<table width="95%">
						<tr>
							<td style="text-align: center; width: 40%;">
								<select name="oldResources" id="oldResources"
									style="width: 200; height: 200" multiple="multiple" size="10">
								</select>
							</td>
							<td style="text-align: center; width: 20%;">
								<p>
									<input name="" value="ѡ  ��" type="button" class="button"
										onclick="moveSelected(document.all.oldResources,document.all.newResources)" />
								</p>
								<p>
									<input name="" value="ɾ  ��" type="button" class="button"
										onclick="moveSelected(document.all.newResources,document.all.oldResources)" />
								</p>
								<p>
									<input name="" value="ȫ��ѡ��" type="button" class="button"
										onclick="moveAll(document.all.oldResources,document.all.newResources);" />
								</p>
								<p>
									<input name="" value="ȫ��ɾ��" type="button" class="button"
										onclick="moveAll(document.all.newResources,document.all.oldResources)" />
								</p>
							</td>
							<td style="text-align: center; width: 40%;">
								<select name="parameter.newResources" id="newResources"
									style="width: 200; height: 200" multiple="multiple" size="10">
								</select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table width="40%" border="0" style="margin-top: 6px" align="center"
			cellpadding="0" cellspacing="0">
			<tr align="center">
				<td>
					<input type="submit" class="button" value="��һ��">
				</td>
			</tr>
		</table>
	</s:form>
</body>
<script type="text/javascript">
getPatrolMans();
</script>
