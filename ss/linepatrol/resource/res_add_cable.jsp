<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>添加光缆中继段信息</title>
		<script language="javascript" src="${ctx}/js/validation/prototype.js"
			type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js"
			type=""></script>
		<script language="javascript"
			src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css"
			rel="stylesheet" type="text/css">
		<script type="text/javascript">
			/**
			ABC－DEFGH
			A：1-一干光缆，2－骨干光缆，3－汇聚光缆（包括互连），4－接入光缆（包括大客户，营业厅）
			B：0-12芯，1-24芯，2-36芯，3-48芯，4-72芯，5-96芯，6-144芯，7-288芯，8-52芯，9-56芯；
			C：1-城区光缆，2-郊区光缆，3-城区至郊区光缆，郊区至郊区光缆
			D－H：所有光缆中继段顺序编号，不足5位数字以0补齐。如00005。
			**/
			function setSegmentid(field){
				field.value="";
				var A = document.getElementById('cableLevel').value;
				var B = document.getElementById('coreNumber').value;
				
				var C = document.getElementsByName('scetion').value;
				$$('input[type="radio"][name="scetion"]').select(function(i){return i.checked}).each(function(i){C = i.value}); 
				var B = getCode(B);
				var segmentcode = $("segmentid").value.substring(4,9);
				//alert("A"+A +"B"+B +"C"+C +"  "+ segmentcode)
				$("segmentid").value=A+B+C+"-"+segmentcode;
				if (field.createTextRange) {  
			          var r = field.createTextRange();  
			          r.moveStart('character', field.value.length);  
			          r.collapse();  
			          r.select();  
			      }  
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
			function validnumber(){
				if($("segmentid").value == ""){
					msg.innerHTML ="中继段编号不能为空";
					return;
				}
				var url = 'resAction.do?method=validateCode&code='+$("segmentid").value;
	 			new Ajax.Request(url,{
		        	method:'post',
		        	evalScripts:true,
		        	onSuccess:function(transport){
	 					msg.innerHTML = transport.responseText;
		        	},
		        	onFailure: function(){ alert('请求服务异常......') }
       		 	});
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
			function judge(){
				var assetno = document.getElementById('assetno').value;
				if(assetno==""){
					alert("资产编号不能为空!");
					return false;
				}
				
				var MIS=document.getElementById("MIS").value;
				if(MIS==""){
					alert("MIS号不能为空!");
					return false;
				}
				
				var scetion = document.getElementById('place_id').value;
				if(scetion==""){
					alert("位置区域不能为空!");
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
					alert("B端不能为空!");
					return false;
				}
				var segmentname = document.getElementById('segmentname').value;
				if(segmentname==""){
					alert("中继段名称不能为空!");
					return false;
				}
				var fiberType = document.getElementById('fiberType').value;
				if(fiberType==""){
					alert("纤芯型号不能为空!");
					return false;
				}
				var producer = document.getElementById('producer');
				if(producer==""){
					alert("光缆厂家不能为空!");
					return false;
				}
				var laytype = document.getElementsByName('laytype');
				var num = 0;
				 for(var i=0;laytype!=null && i<laytype.length;i++){ 
			         if(laytype[i].checked==true){
			         	num++;
			         } 
			     } 
				if(num==0){
					alert("敷设方式不能为空!");
					return false;
				}
				var grossLength = document.getElementById('grossLength').value;
				var regDigit=/^\d+(\.\d+)?$/;
				if(grossLength==""){
					alert("光缆长度不能为空!");
					return false;
				}
				if(!regDigit.test(grossLength)){
					alert("光缆长度只能为非负数字！");
					return false;
				}
				var reservedLength = document.getElementById('reservedLength').value;
				if(reservedLength!=""&&!regDigit.test(reservedLength)){
					alert("盘留长度只能为非负数字！");
					return false;
				}
				
				var builder = document.getElementById('builder').value;
				if(builder==""){
					alert("施工单位不能为空!");
					return false;
				}
				
				var projectName = document.getElementById('projectName').value;
				if(projectName==""){
					alert("工程名称不能为空!");
					return false;
				}
				
				var finishtime = document.getElementById('finishtime').value;
				if(finishtime==""){
					alert("交维日期不能为空!");
					return false;
				}
				var msg=sendAjax();
				if(msg != ''){
					alert(msg);
					return false;
				}
				return true;
			}
			function sendAjax(){
			var response = '';
			jQuery.ajax({
			   type: "POST",
			   url: "${ctx}/datumAction.do?method=validateFile",
			   async: false,
			   success: function(msg){
				   response = msg;
			   }
			});
			return response;
		}
		</script>
	</head>
	<body>
		<template:titile value="添加光缆中继段信息" />
		<html:form action="/resAction.do?method=save" styleId="saveres_cable"
			onsubmit="return judge()">
			<template:formTable>
				<template:formTr name="资产编号" isOdd="false">
					<input type="text" id="assetno" class="inputtext required"
						style="width: 200px" name="assetno" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="MIS号" isOdd="false">
					<input type="text" name="MIS" id="MIS" class="inputtext requried" style="width:200px" />&nbsp;&nbsp;<font color="red">*</font>
				</template:formTr>
				<template:formTr name="光缆级别" isOdd="true">
					<apptag:quickLoadList cssClass="inputtext required"
						style="width:200px" name="cableLevel" listName="cabletype"
						type="select" />
				</template:formTr>
				<template:formTr name="纤芯数量" isOdd="false">
					<select name="coreNumber" id="coreNumber" class="inputtext"
						style="width: 200px">
						<option value="12">
							12芯
						</option>
						<option value="24">
							24芯
						</option>
						<option value="36">
							36芯
						</option>
						<option value="48">
							48芯
						</option>
						<option value="72">
							72芯
						</option>
						<option value="96">
							96芯
						</option>
						<option value="144">
							144芯
						</option>
						<option value="288">
							288芯
						</option>
						<option value="52">
							52芯
						</option>
						<option value="56">
							56芯
						</option>
					</select>
				</template:formTr>
				<template:formTr name="区域" isOdd="true">
					<apptag:quickLoadList cssClass="" name="scetion"
						listName="terminal_address" type="radio" />
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
					<font color="red">*</font>指的是所在城区；例如：朝阳，通州，房山，崇文，宣武
			</template:formTr>
				<template:formTr name="中继段编号" isOdd="false">
					<input type="text" class="inputtext" style="width: 200px"
						onclick="setSegmentid(this);" onblur="validnumber();"
						id="segmentid" name="segmentid" maxlength="12" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="A端" isOdd="true">
					<input type="text" class="inputtext required" style="width: 200px"
						onblur="setSegmentName()" id="pointa" name="pointa" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="B端" isOdd="false">
					<input type="text" class="inputtext required" style="width: 200px"
						onblur="setSegmentName()" id="pointz" name="pointz" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="中继段名称" isOdd="true">
					<input type="text" class="inputtext required" style="width: 200px"
						name="segmentname" id="segmentname" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="纤芯型号" isOdd="true">
					<input type="text" class="inputtext required" style="width: 200px"
						name="fiberType" id="fiberType" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="光缆厂家" isOdd="false">
					<input type="text" class="inputtext required" style="width: 200px"
						name="producer" id="producer" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="敷设方式" isOdd="true">
					<apptag:quickLoadList cssClass="" style="width:200px"
						name="laytype" listName="layingmethod" type="checkbox" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="光缆长度" isOdd="false">
					<input type="text" class="inputtext required" style="width: 200px"
						name="grossLength" id="grossLength" />公里
				<font color="red">*</font>
				</template:formTr>
				<template:formTr name="盘留长度" isOdd="true">
					<input type="text" class="inputtext required" style="width: 200px"
						name="reservedLength" id="reservedLength" value="0" />公里
			</template:formTr>
				<template:formTr name="产权属性" isOdd="false">
					<apptag:quickLoadList cssClass="" style="width:200px" name="owner"
						listName="property_right" type="radio" />
				</template:formTr>
				<template:formTr name="施工单位" isOdd="true">
					<input type="text" class="inputtext required" style="width: 200px"
						name="builder" id="builder" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="工程名称" isOdd="true">
					<input type="text" class="inputtext required" style="width: 200px"
						name="projectName" id="projectName" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="交维日期" isOdd="false">
					<input type="text" class="Wdate" style="width: 200px"
						name="finishtime" id="finishtime"
						onfocus="WdatePicker({dateFmt:'yyyy/MM'})" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="交资情况" isOdd="true">
					<input type="radio" name="currentState" value="y" style="" />交资
				<input type="radio" name="currentState" value="n" style="" />未交资
			</template:formTr>
				<template:formTr name="是否有正式竣工图纸" isOdd="false">
					<input type="radio" name="havePicture" value="y" style="" />有
				<input type="radio" name="havePicture" value="n" style="" />无
			</template:formTr>
				<template:formTr name="交维方式" isOdd="true">
					<input type="radio" name="isMaintenance" style="" value="n" />资料交维
				<input type="radio" name="isMaintenance" style="" value="y" />验收交维
			</template:formTr>
				<c:if test="${LOGIN_USER.deptype=='1'}">
					<template:formTr name="维护单位">
						<apptag:setSelectOptions columnName2="contractorid"
							columnName1="contractorname" valueName="contractor"
							tableName="contractorinfo" />
						<html:select property="maintenanceId" styleClass="inputtext"
							style="width:200px">
							<html:option value="">请选择</html:option>
							<html:options collection="contractor" property="value"
								labelProperty="label" />
						</html:select>
					</template:formTr>
				</c:if>
				<template:formTr name="备注" isOdd="false">
					<input type="text" class="inputtext" style="width: 200px"
						name="remark" />
				</template:formTr>
				<template:formTr name="备注2" isOdd="false">
					<input type="text" class="inputtext" style="width: 200px"
						name="remark2" />
				</template:formTr>
				<template:formTr name="备注3" isOdd="false">
					<input type="text" class="inputtext" style="width: 200px"
						name="remark3" />
				</template:formTr>
				<template:formTr name="资料">
					<apptag:upload state="add" cssClass="" />
				</template:formTr>
			</template:formTable>
			<template:formSubmit>
				<input type="submit" class="button" name="submit" value="提交" />
			</template:formSubmit>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}
	
			var valid = new Validation('saveres_cable', {immediate : true, onFormValidate : formCallback});
		</script>
		<template:cssTable />
	</body>
</html>