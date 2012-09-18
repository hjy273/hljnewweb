<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>��ӹ����м̶���Ϣ</title>
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
			ABC��DEFGH
			A��1-һ�ɹ��£�2���Ǹɹ��£�3����۹��£�������������4��������£�������ͻ���Ӫҵ����
			B��0-12о��1-24о��2-36о��3-48о��4-72о��5-96о��6-144о��7-288о��8-52о��9-56о��
			C��1-�������£�2-�������£�3-�������������£���������������
			D��H�����й����м̶�˳���ţ�����5λ������0���롣��00005��
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
					msg.innerHTML ="�м̶α�Ų���Ϊ��";
					return;
				}
				var url = 'resAction.do?method=validateCode&code='+$("segmentid").value;
	 			new Ajax.Request(url,{
		        	method:'post',
		        	evalScripts:true,
		        	onSuccess:function(transport){
	 					msg.innerHTML = transport.responseText;
		        	},
		        	onFailure: function(){ alert('��������쳣......') }
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
					alert("�ʲ���Ų���Ϊ��!");
					return false;
				}
				
				var MIS=document.getElementById("MIS").value;
				if(MIS==""){
					alert("MIS�Ų���Ϊ��!");
					return false;
				}
				
				var scetion = document.getElementById('place_id').value;
				if(scetion==""){
					alert("λ��������Ϊ��!");
					return false;
				}
				var segmentid = document.getElementById('segmentid').value;
				if(segmentid==""){
					alert("�м̶α�Ų���Ϊ��!");
					return false;
				}
				var pointa = document.getElementById('pointa').value;
				if(pointa==""){
					alert("A�˲���Ϊ��!");
					return false;
				}
				var pointz = document.getElementById('pointz').value;
				if(pointz==""){
					alert("B�˲���Ϊ��!");
					return false;
				}
				var segmentname = document.getElementById('segmentname').value;
				if(segmentname==""){
					alert("�м̶����Ʋ���Ϊ��!");
					return false;
				}
				var fiberType = document.getElementById('fiberType').value;
				if(fiberType==""){
					alert("��о�ͺŲ���Ϊ��!");
					return false;
				}
				var producer = document.getElementById('producer');
				if(producer==""){
					alert("���³��Ҳ���Ϊ��!");
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
					alert("���跽ʽ����Ϊ��!");
					return false;
				}
				var grossLength = document.getElementById('grossLength').value;
				var regDigit=/^\d+(\.\d+)?$/;
				if(grossLength==""){
					alert("���³��Ȳ���Ϊ��!");
					return false;
				}
				if(!regDigit.test(grossLength)){
					alert("���³���ֻ��Ϊ�Ǹ����֣�");
					return false;
				}
				var reservedLength = document.getElementById('reservedLength').value;
				if(reservedLength!=""&&!regDigit.test(reservedLength)){
					alert("��������ֻ��Ϊ�Ǹ����֣�");
					return false;
				}
				
				var builder = document.getElementById('builder').value;
				if(builder==""){
					alert("ʩ����λ����Ϊ��!");
					return false;
				}
				
				var projectName = document.getElementById('projectName').value;
				if(projectName==""){
					alert("�������Ʋ���Ϊ��!");
					return false;
				}
				
				var finishtime = document.getElementById('finishtime').value;
				if(finishtime==""){
					alert("��ά���ڲ���Ϊ��!");
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
		<template:titile value="��ӹ����м̶���Ϣ" />
		<html:form action="/resAction.do?method=save" styleId="saveres_cable"
			onsubmit="return judge()">
			<template:formTable>
				<template:formTr name="�ʲ����" isOdd="false">
					<input type="text" id="assetno" class="inputtext required"
						style="width: 200px" name="assetno" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="MIS��" isOdd="false">
					<input type="text" name="MIS" id="MIS" class="inputtext requried" style="width:200px" />&nbsp;&nbsp;<font color="red">*</font>
				</template:formTr>
				<template:formTr name="���¼���" isOdd="true">
					<apptag:quickLoadList cssClass="inputtext required"
						style="width:200px" name="cableLevel" listName="cabletype"
						type="select" />
				</template:formTr>
				<template:formTr name="��о����" isOdd="false">
					<select name="coreNumber" id="coreNumber" class="inputtext"
						style="width: 200px">
						<option value="12">
							12о
						</option>
						<option value="24">
							24о
						</option>
						<option value="36">
							36о
						</option>
						<option value="48">
							48о
						</option>
						<option value="72">
							72о
						</option>
						<option value="96">
							96о
						</option>
						<option value="144">
							144о
						</option>
						<option value="288">
							288о
						</option>
						<option value="52">
							52о
						</option>
						<option value="56">
							56о
						</option>
					</select>
				</template:formTr>
				<template:formTr name="����" isOdd="true">
					<apptag:quickLoadList cssClass="" name="scetion"
						listName="terminal_address" type="radio" />
				</template:formTr>
				<template:formTr name="λ������" isOdd="false">
					<apptag:setSelectOptions columnName1="regionname"
						columnName2="regionid" tableName="region" valueName="region"
						state="false" condition="parentregionid='${LOGIN_USER.regionid}'" />
					<html:select property="place" styleId="place_id"
						styleClass="inputtext" style="width:160">
						<html:options collection="region" property="value"
							labelProperty="label" />
					</html:select>
					<font color="red">*</font>ָ�������ڳ��������磺������ͨ�ݣ���ɽ�����ģ�����
			</template:formTr>
				<template:formTr name="�м̶α��" isOdd="false">
					<input type="text" class="inputtext" style="width: 200px"
						onclick="setSegmentid(this);" onblur="validnumber();"
						id="segmentid" name="segmentid" maxlength="12" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="A��" isOdd="true">
					<input type="text" class="inputtext required" style="width: 200px"
						onblur="setSegmentName()" id="pointa" name="pointa" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="B��" isOdd="false">
					<input type="text" class="inputtext required" style="width: 200px"
						onblur="setSegmentName()" id="pointz" name="pointz" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="�м̶�����" isOdd="true">
					<input type="text" class="inputtext required" style="width: 200px"
						name="segmentname" id="segmentname" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="��о�ͺ�" isOdd="true">
					<input type="text" class="inputtext required" style="width: 200px"
						name="fiberType" id="fiberType" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="���³���" isOdd="false">
					<input type="text" class="inputtext required" style="width: 200px"
						name="producer" id="producer" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="���跽ʽ" isOdd="true">
					<apptag:quickLoadList cssClass="" style="width:200px"
						name="laytype" listName="layingmethod" type="checkbox" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="���³���" isOdd="false">
					<input type="text" class="inputtext required" style="width: 200px"
						name="grossLength" id="grossLength" />����
				<font color="red">*</font>
				</template:formTr>
				<template:formTr name="��������" isOdd="true">
					<input type="text" class="inputtext required" style="width: 200px"
						name="reservedLength" id="reservedLength" value="0" />����
			</template:formTr>
				<template:formTr name="��Ȩ����" isOdd="false">
					<apptag:quickLoadList cssClass="" style="width:200px" name="owner"
						listName="property_right" type="radio" />
				</template:formTr>
				<template:formTr name="ʩ����λ" isOdd="true">
					<input type="text" class="inputtext required" style="width: 200px"
						name="builder" id="builder" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="��������" isOdd="true">
					<input type="text" class="inputtext required" style="width: 200px"
						name="projectName" id="projectName" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="��ά����" isOdd="false">
					<input type="text" class="Wdate" style="width: 200px"
						name="finishtime" id="finishtime"
						onfocus="WdatePicker({dateFmt:'yyyy/MM'})" />
					<font color="red">*</font>
				</template:formTr>
				<template:formTr name="�������" isOdd="true">
					<input type="radio" name="currentState" value="y" style="" />����
				<input type="radio" name="currentState" value="n" style="" />δ����
			</template:formTr>
				<template:formTr name="�Ƿ�����ʽ����ͼֽ" isOdd="false">
					<input type="radio" name="havePicture" value="y" style="" />��
				<input type="radio" name="havePicture" value="n" style="" />��
			</template:formTr>
				<template:formTr name="��ά��ʽ" isOdd="true">
					<input type="radio" name="isMaintenance" style="" value="n" />���Ͻ�ά
				<input type="radio" name="isMaintenance" style="" value="y" />���ս�ά
			</template:formTr>
				<c:if test="${LOGIN_USER.deptype=='1'}">
					<template:formTr name="ά����λ">
						<apptag:setSelectOptions columnName2="contractorid"
							columnName1="contractorname" valueName="contractor"
							tableName="contractorinfo" />
						<html:select property="maintenanceId" styleClass="inputtext"
							style="width:200px">
							<html:option value="">��ѡ��</html:option>
							<html:options collection="contractor" property="value"
								labelProperty="label" />
						</html:select>
					</template:formTr>
				</c:if>
				<template:formTr name="��ע" isOdd="false">
					<input type="text" class="inputtext" style="width: 200px"
						name="remark" />
				</template:formTr>
				<template:formTr name="��ע2" isOdd="false">
					<input type="text" class="inputtext" style="width: 200px"
						name="remark2" />
				</template:formTr>
				<template:formTr name="��ע3" isOdd="false">
					<input type="text" class="inputtext" style="width: 200px"
						name="remark3" />
				</template:formTr>
				<template:formTr name="����">
					<apptag:upload state="add" cssClass="" />
				</template:formTr>
			</template:formTable>
			<template:formSubmit>
				<input type="submit" class="button" name="submit" value="�ύ" />
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