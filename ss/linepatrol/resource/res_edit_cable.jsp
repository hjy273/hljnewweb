<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>编辑光缆中继段信息</title>
		<script language="javascript" src="${ctx}/js/validation/prototype.js"
			type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js"
			type=""></script>
		<script language="javascript"
			src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css"
			rel="stylesheet" type="text/css">

		<script type='text/javascript'
			src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<link rel='stylesheet' type='text/css'
			href='${ctx}/js/extjs/resources/css/ext-all.css' />

		<script type="text/javascript">
			/**
			ABC－DEFGH
			A：1-一干光缆，2－骨干光缆，3－汇聚光缆（包括互连），4－接入光缆（包括大客户，营业厅）
			B：0-12芯，1-24芯，2-36芯，3-48芯，4-72芯，5-96芯，6-144芯，7-288芯，8-52芯，9-56芯；
			C：1-城区光缆，2-郊区光缆，3-城区至郊区光缆，郊区至郊区光缆
			D－H：所有光缆中继段顺序编号，不足5位数字以0补齐。如00005。
			**/
			function setSegmentid(){
				var A = document.getElementById('cableLevel').value;
				var B = document.getElementById('coreNumber').value;
				
				var C = document.getElementsByName('scetion').value;
				$$('input[type="radio"][name="scetion"]').select(function(i){return i.checked}).each(function(i){C = i.value}); 
				var B = getCode(B);
				var segmentcode = $("segmentid").value.substring(4,9);
				//alert("A"+A +"B"+B +"C"+C +"  "+ segmentcode)
				$("segmentid").value=A+B+C+"-"+segmentcode;
			}
			function getCode(B){
				var code;
				switch (B){
				   	case '12':code=0;break;case '24':code=1;break;case '36':code=2;break;case '48':code=3;break;
				   	case '72':code=4;break;case '96':code=5;break;case '144':code=6;break;case '288':code=7;break;
				   	case '52':code=8;break;case '56':code=9;break;default:code=0;
				}
				return code;				
			}
			function setSegmentName(){
				var A = document.getElementById('pointa').value;
				var Z = document.getElementById('pointz').value;
				$("segmentname").value=A+"-"+Z;
			}
			function checkAdd(){
				var assetno = document.getElementById('assetno').value;
				if(assetno==""){
					alert("资产编号不能为空!");
					return false;
				}
				
				var MIS=document.getElementById("MIS").value;
				if(MIS==""){
					alert("MIS号不能为空！");
					return false;
				}
				
				var segmentid = document.getElementById('segmentid').value;
				if(segmentid==""){
					alert("中继段编号不能为空!");
					return false;
				}
				
				var pointa = document.getElementById('pointa').value;
				if(pointa==""){
					alert("A端不能为空!");
					return false;
				}
				var pointz = document.getElementById('pointz').value;
				if(pointz==""){
					alert("Z端不能为空!");
					return false;
				}
				var segmentname = document.getElementById('segmentname').value;
				if(segmentname==""){
					alert("中继段名称不能为空!");
					return false;
				}
				var finishtime = document.getElementById('finishtime').value;
				if(finishtime==""){
					alert("交维日期不能为空!");
					return false;
				}
				var regDigit=/^\d+(\.\d+)?$/;
				var reservedLength = document.getElementById('reservedLength').value;
				if(reservedLength!=""&&!regDigit.test(reservedLength)){
					alert("盘留长度只能为非负数字！");
					return false;
				}
				return true;
			}
			function more(){
				window.location.href = '${ctx}/linepatrol/resource/fileDetail.jsp';
			}
			function onSelect(obj){
				loadData(obj.value);
			}
			function loadData(section,sel){
				jQuery.ajax({
					   type: "POST",
					   url: "resAction.do",
					   data: "method=loadTown&scetion="+section+"&sel="+sel,
					   success: function(msg){
						 jQuery("#place_id").html(msg);
					   }
				});
			}
		</script>

	</head>
	<body>
		<template:titile value="编辑光缆中继段信息" />
		<html:form action="/resAction.do?method=save" styleId="saveres_cable"
			onsubmit="return checkAdd();">
			<template:formTable>
				<template:formTr name="资产编号" isOdd="false">
					<input type="hidden" id="kid" value="${rs.kid }" name="kid" />
					<input type="hidden" id="maintenanceId"
						value="${rs.maintenanceId }" name="maintenanceId" />
					<input type="text" class="inputtext" value="${rs.assetno}"
						style="width: 200px" name="assetno" id="assetno" />&nbsp;&nbsp;<font
						color="red">*</font>
				</template:formTr>
				<template:formTr name="MIS号" isOdd="false">
					<input type="text" class="inputtext" value="${rs.MIS}" style="width:200px" name="MIS" id="MIS" />&nbsp;&nbsp;<font color="red">*</font>
				</template:formTr>
				<template:formTr name="光缆级别" isOdd="true">
					<apptag:quickLoadList cssClass="inputtext"
						keyValue="${rs.cableLevel}" style="width:200px" id="cableLevel"
						name="cableLevel" listName="cabletype" type="look" />
					<input type="hidden" value="${rs.cableLevel}" name="cableLevel" />
				</template:formTr>
				<template:formTr name="纤芯数量" isOdd="false">
					<html:select property="coreNumber" styleId="coreNumber" name="rs"
						styleClass="inputtext" style="width:200px">
						<!-- select name="coreNumber" class="inputtext" style="width:200px" -->
						<html:option value="12">12芯</html:option>
						<html:option value="24">24芯</html:option>
						<html:option value="36">36芯</html:option>
						<html:option value="48">48芯</html:option>
						<html:option value="72">72芯</html:option>
						<html:option value="96">96芯</html:option>
						<html:option value="144">144芯</html:option>
						<html:option value="288">288芯</html:option>
						<html:option value="52">52芯</html:option>
						<html:option value="56">56芯</html:option>
					</html:select>
					<!-- /select -->
					<input type="hidden" class="inputtext" style="width: 200px"
						value="${rs.coreNumber}" />
				</template:formTr>
				<template:formTr name="区域" isOdd="true">
					<apptag:quickLoadList cssClass="1" name="scetion" id="scetion"
						keyValue="${rs.scetion}" listName="terminal_address" type="radio" />
				</template:formTr>
				<template:formTr name="位置区域" isOdd="false">
					<apptag:setSelectOptions columnName1="regionname"
						columnName2="regionid" tableName="region" valueName="region"
						state="false" condition="parentregionid='${LOGIN_USER.regionid}'" />
					<html:select property="place" styleId="place_id"
						styleClass="inputtext" style="width:160">
						<html:options collection="region" property="value"
							labelProperty="label" />
					</html:select>	
				*指的是所在城区；例如：朝阳，通州，房山，崇文，宣武
			</template:formTr>
				<template:formTr name="中继段编号" isOdd="false">
					<input type="text" class="inputtext" style="width: 200px"
						onfocus="//setSegmentid();" value="${rs.segmentid}"
						name="segmentid" id="segmentid" maxlength="12" />&nbsp;&nbsp;<font color="red">*</font>
				</template:formTr>
				<template:formTr name="A端" isOdd="true">
					<input type="text" class="inputtext" style="width: 200px"
						onblur="setSegmentName()" value="${rs.pointa}" name="pointa"
						id="pointa" />&nbsp;&nbsp;<font color="red">*</font>
				</template:formTr>
				<template:formTr name="B端" isOdd="false">
					<input type="text" class="inputtext" style="width: 200px"
						onblur="setSegmentName()" value="${rs.pointz}" name="pointz"
						id="pointz" />&nbsp;&nbsp;<font color="red">*</font>
				</template:formTr>
				<template:formTr name="中继段名称" isOdd="true">
					<input type="text" class="inputtext" style="width: 200px"
						value="${rs.segmentname}" name="segmentname" id="segmentname" />&nbsp;&nbsp;<font
						color="red">*</font>
				</template:formTr>

				<template:formTr name="纤芯型号" isOdd="true">
					<input type="text" class="inputtext" style="width: 200px"
						value="${rs.fiberType}" name="fiberType" />
				</template:formTr>
				<template:formTr name="光缆厂家" isOdd="false">
					<input type="text" class="inputtext" style="width: 200px"
						value="${rs.producer}" name="producer" />
				</template:formTr>
				<template:formTr name="敷设方式" isOdd="true">
					<apptag:quickLoadList cssClass="" keyValue="${rs.laytype}"
						style="width:200px" name="laytype" listName="layingmethod"
						type="checkbox" />
				</template:formTr>
				<template:formTr name="光缆长度" isOdd="false">
					<input type="hidden" class="inputtext" readonly
						style="width: 200px" value="${rs.grossLength}" name="grossLength" />${rs.grossLength}公里
			</template:formTr>
				<template:formTr name="盘留长度" isOdd="true">
					<input type="text" class="inputtext" style="width: 200px"
						value="${rs.reservedLength}" name="reservedLength" id="reservedLength" />公里
			</template:formTr>
				<template:formTr name="施工单位" isOdd="false">
					<input type="text" class="inputtext" style="width: 200px"
						value="${rs.builder}" name="builder" />&nbsp;&nbsp;
			</template:formTr>
				<template:formTr name="工程名称" isOdd="true">
					<input type="text" class="inputtext" style="width: 200px"
						value="${rs.projectName}" name="projectName" />
				</template:formTr>
				<template:formTr name="交维日期" isOdd="false">
					<input type="text" class="Wdate" style="width: 200px"
						value="<fmt:formatDate value="${rs.finishtime}" pattern="yyyy/MM"/>"
						name="finishtime" id="finishtime"
						onfocus="WdatePicker({dateFmt:'yyyy/MM'})" />&nbsp;&nbsp;<font
						color="red">*</font>
				</template:formTr>

				<template:formTr name="交资情况" isOdd="true">
					<c:set var="y" value="" />
					<c:set var="n" value="" />
					<c:if test="${rs.currentState=='y'}">
						<c:set var="y" value="checked"></c:set>
					</c:if>
					<c:if test="${rs.currentState!='y'}">
						<c:set var="n" value="checked"></c:set>
					</c:if>
					<input type="radio" name="currentState" ${y} value="y" />交资
				<input type="radio" name="currentState" ${n} value="n" />未交资
			</template:formTr>
				<template:formTr name="是否有正式竣工图纸" isOdd="false">
					<c:set var="y" value="" />
					<c:set var="n" value="" />
					<c:if test="${rs.havePicture=='y'}">
						<c:set var="y" value="checked"></c:set>
					</c:if>
					<c:if test="${rs.havePicture!='y'}">
						<c:set var="n" value="checked"></c:set>
					</c:if>
					<input type="radio" name="havePicture" ${y} value="y" />有
				<input type="radio" name="havePicture" ${n} value="n" />无
			</template:formTr>
				<template:formTr name="交维方式" isOdd="true">
					<c:set var="y" value="" />
					<c:set var="n" value="" />
					<c:if test="${rs.isMaintenance=='y'}">
						<c:set var="y" value="checked"></c:set>
					</c:if>
					<c:if test="${rs.isMaintenance!='y'}">
						<c:set var="n" value="checked"></c:set>
					</c:if>
					<input type="radio" name="isMaintenance" ${y} value="y" />验收交维
				<input type="radio" name="isMaintenance" ${n} value="n" />资料交维
			</template:formTr>
				<c:if test="${LOGIN_USER.deptype=='1'}">
					<template:formTr name="维护单位">
						<apptag:setSelectOptions columnName2="contractorid"
							columnName1="contractorname" valueName="contractor"
							tableName="contractorinfo" />
						<html:select property="maintenanceId" name="rs"
							styleClass="inputtext" style="width:200px">
							<html:option value="">请选择</html:option>
							<html:options collection="contractor" property="value"
								labelProperty="label" />
						</html:select>
					</template:formTr>
				</c:if>
				<template:formTr name="备注" isOdd="false">
					<input type="text" class="inputtext" style="width: 200px"
						value="${rs.remark}" name="remark" />
				</template:formTr>
				<template:formTr name="备注2" isOdd="false">
					<input type="text" class="inputtext" style="width: 200px"
						value="${rs.remark2}" name="remark2" />
				</template:formTr>
				<template:formTr name="备注3" isOdd="false">
					<input type="text" class="inputtext" style="width: 200px"
						value="${rs.remark3}" name="remark3" />
				</template:formTr>
				<template:formTr name="历史资料" isOdd="true">
					<a href="javascript:more();">查看</a>
				</template:formTr>
				<template:formTr name="本次更新" isOdd="false">
					<apptag:upload state="edit" cssClass="" entityId="${rs.kid}"
						entityType="LP_ACCEPTANCE_CABLE" useable="1" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<input type="hidden" name="operator" value="update" />
						<input type="submit" class="button" name="submit" value="提 交" />
						<input type="button" class="button" name="goback"
							onclick="history.go(-1)" value="返 回" />
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		<template:cssTable />
	</body>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			loadData('${rs.scetion}','${rs.place}');
		});
		</script>
</html>