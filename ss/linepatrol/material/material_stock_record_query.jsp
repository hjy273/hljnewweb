<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>�����ϲ�ѯ���Ͽ��</title>
		<script type="text/javascript"
			src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<link type="text/css" rel="stylesheet"
			href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<script type="text/javascript" src="${ctx}/js/prototype.js"></script>
		<script type="text/javascript" src="${ctx}/js/json2.js"></script>
		<script type="text/javascript">
		function onConChange() {
    		var params = Form.Element.serialize("contractorid");
  			var ops = document.getElementById('addrID');
  			ops.options.length = 0;//��������б�
  			ops.options.add(new Option("��ѡ��...", ""));//������ʾ��Ϣ
  			var url = "materialStockAction.do?method=getAddressByCon"
  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callbackForCon, asynchronous:true});
    	}
    	function callbackForCon(originalRequest) {
    		var rst = originalRequest.responseText;
    		var queryRes = eval('('+rst+')');
    		var ops = document.getElementById('addrID');
    		for(var i = 0 ; i<queryRes.length; i++) {
  				ops.options.add(new Option(queryRes[i].address, queryRes[i].id));
  			}
  			var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
				}, onFailure:function () {
					alert("�������ӳ������⣬��رպ�����!");
				}, onComplete:function () {
					if (Ajax.activeRequestCount == 0) {
						Element.hide("Loadingimg");
					}
				}
			}
		}
			
	function onTypeChange() {
		var params = Form.Element.serialize("typeid");
		var ops = document.getElementById('modelid');
		ops.options.length = 0;//��������б�
		ops.options.add(new Option("��ѡ��...", ""));//������ʾ��Ϣ
		var url = "materialStockAction.do?method=getModelByType"
		var myAjax = new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : callbackForType,
			asynchronous : true
		});
	}

	function callbackForType(originalRequest) {
		var rst = originalRequest.responseText;
		var queryRes = eval('(' + rst + ')');
		var ops = document.getElementById('modelid');
		for ( var i = 0; i < queryRes.length; i++) {
			ops.options.add(new Option(queryRes[i].modelname, queryRes[i].id));
		}
		var myGlobalHandlers = {
			onCreate : function() {
				Element.show("Loadingimg");
			},
			onFailure : function() {
				alert("�������ӳ������⣬��رպ�����!");
			},
			onComplete : function() {
				if (Ajax.activeRequestCount == 0) {
					Element.hide("Loadingimg");
				}
			}
		}
	}

	function onModelChange() {
		var params = Form.Element.serialize("modelid");
		var ops = document.getElementById('mtid');
		ops.options.length = 0;//��������б�
		ops.options.add(new Option("��ѡ��...", ""));//������ʾ��Ϣ
		var url = "materialStockAction.do?method=getMTByModel"
		var myAjax = new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : callbackForModel,
			asynchronous : true
		});
	}

	function callbackForModel(originalRequest) {
		var rst = originalRequest.responseText;
		var queryRes = eval('(' + rst + ')');
		var ops = document.getElementById('mtid');
		for ( var i = 0; i < queryRes.length; i++) {
			ops.options.add(new Option(queryRes[i].name, queryRes[i].id));
		}
		var myGlobalHandlers = {
			onCreate : function() {
				Element.show("Loadingimg2");
			},
			onFailure : function() {
				alert("�������ӳ������⣬��رպ�����!");
			},
			onComplete : function() {
				if (Ajax.activeRequestCount == 0) {
					Element.hide("Loadingimg");
				}
			}
		}
	}
</script>
	</head>

	<body>
		<br>
		<template:titile value="���ϳ�����¼��ѯ" />
		<html:form action="/material_stock_record.do?method=listMaterialStockRecord" styleId="queryForm">
			<template:formTable namewidth="200" contentwidth="300">
				<logic:equal value="1" property="deptype" name="LOGIN_USER">
					<template:formTr name="������ά">
						<select name="contractorid" class="inputtext" style="width: 215px"
							id="contractorid" onchange="onConChange();">
							<option value="">
								����
							</option>
							<logic:present scope="session" name="contractorList">
								<logic:iterate id="oneContractor" name="contractorList">
									<option value="<bean:write name="oneContractor" property="contractorid" />">
										<bean:write name="oneContractor" property="contractorname" />
									</option>
								</logic:iterate>
							</logic:present>
						</select>
						<img id="Loadingimg" src="./images/ajaxtags/indicator.gif"
							style="display: none">
					</template:formTr>
					<template:formTr name="��ŵص�">
						<select name="addrID" class="inputtext" style="width: 215px"
							id="addrID">
							<option value="">
								����
							</option>
						</select>
					</template:formTr>
				</logic:equal>
				<logic:equal value="2" property="deptype" name="LOGIN_USER">
					<template:formTr name="��ŵص�">
						<select name="addrID" class="inputtext" style="width: 215px"
							id="addrID">
							<option value="">
								����
							</option>
							<logic:present scope="session" name="addressList">
								<logic:iterate id="oneAddress" name="addressList">
									<option value="<bean:write name="oneAddress" property="id" />">
										<bean:write name="oneAddress" property="address" />
									</option>
								</logic:iterate>
							</logic:present>
						</select>
					</template:formTr>
				</logic:equal>
				<template:formTr name="��������">
					<select name="typeid" class="inputtext" style="width: 215px"
						id="typeid" onchange="onTypeChange();">
						<option value="">
							����
						</option>
						<logic:present scope="session" name="typeList">
							<logic:iterate id="oneType" name="typeList">
								<option value="<bean:write name="oneType" property="id" />">
									<bean:write name="oneType" property="typename" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
					<img id="Loadingimg" src="./images/ajaxtags/indicator.gif"
						style="display: none">
				</template:formTr>
				<template:formTr name="���Ϲ��">
					<select name="modelid" class="inputtext" style="width: 215px"
						id="modelid" onchange="onModelChange();">
						<option value="">
							����
						</option>
					</select>
				</template:formTr>
				<template:formTr name="��������">
					<select name="mtid" class="inputtext" style="width: 215px"
						id="mtid">
						<option value="">
							����
						</option>
					</select>
					<img id="Loadingimg2" src="./images/ajaxtags/indicator.gif"
						style="display: none">
				</template:formTr>
				<template:formTr name="���Ͽ������">
					<input name="storageType" value="" type="radio" checked />����
					<input name="storageType" value="0" type="radio" />����
					<input name="storageType" value="1" type="radio" />����
				</template:formTr>
				<template:formTr name="������;">
					<input name="useType" value="" type="radio" checked />����
					<input name="useType" value="0" type="radio" />���
					<input name="useType" value="1" type="radio" />ʹ��
				</template:formTr>
				<template:formTr name="��������">
					<input name="beginDate" class="inputtext" style="width: 100px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly />
					-
					<input name="endDate" class="inputtext" style="width: 100px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly />
				</template:formTr>
			</template:formTable>
			<div align="center">
				<html:submit property="action" styleClass="button">��ѯ</html:submit>
				<html:reset property="action" styleClass="button">����</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="queryForm"></template:displayHide>
		<logic:notEmpty name="RECORD_DATA_MAP">
		<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css"
	type="text/css" media="screen, print" />
	<script type="" language="javascript">
		exportMaterialStockRecord=function(useType){
			var url="${ctx}/material_stock_record.do?method=exportMaterialStockRecord&&use_type="+useType;
			location=url;
		}
		displayMaterialInfo=function(type){
			if(type=='0'){
				inMaterialTr.style.display="";
				outMaterialTr.style.display="none";
				btnInMaterial.className = "button_clicked";
				btnOutMaterial.className="button_not_click";
			}else if(type=='1'){
				inMaterialTr.style.display="none";
				outMaterialTr.style.display="";
				btnOutMaterial.className = "button_clicked";
				btnInMaterial.className="button_not_click";
			}else{
				inMaterialTr.style.display="";
				outMaterialTr.style.display="none";
				btnInMaterial.className = "button_clicked";
				btnOutMaterial.className="button_not_click";
			}
		}
		</script>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td style="width:20%;padding-left:10px;">
					<logic:notEqual value="1" name="use_type">
						<input name="btnInMaterial" type="button" class="button_clicked"
							style="cursor:hand" id="btnInMaterial" value="��������Ϣ" 
							onclick="displayMaterialInfo('0');" />
					</logic:notEqual>
					<logic:notEqual value="0" name="use_type">
						<input name="btnOutMaterial" type="button" class="button_not_click"
							style="cursor:hand" id="btnOutMaterial" value="���������Ϣ" 
							onclick="displayMaterialInfo('1');" />
					</logic:notEqual>
				</td>
				<td style="width:80%">
				</td>
			</tr>
			<tr id="inMaterialTr">
				<td colspan="2" style="padding:10px;">
					<logic:notEmpty name="RECORD_DATA_MAP">
						<logic:notEmpty name="RECORD_DATA_MAP" property="record_in_list">
							<table width="100%" border="1" cellspacing="0" cellpadding="0"
								style="border-collapse: collapse;">
								<tr class="trcolor">
									<td>
										��������
									</td>
									<td>
										��ά��˾
									</td>
									<td>
										��ŵص�
									</td>
									<td>
										�������
									</td>
									<td>
										���ʱ��
									</td>
									<td>
										�������
									</td>
									<td>
										�����Դ
									</td>
								</tr>
								<logic:iterate id="oneMaterialIn" name="RECORD_DATA_MAP"
									property="record_in_list">
									<tr class="trcolor">
										<td>
											<bean:write name="oneMaterialIn" property="material_name" />
										</td>
										<td>
											<bean:write name="oneMaterialIn" property="contractorname" />
										</td>
										<td>
											<bean:write name="oneMaterialIn" property="address" />
										</td>
										<td>
											<bean:write name="oneMaterialIn" property="storage_type_name" />
										</td>
										<td>
											<logic:equal value="�������" name="oneMaterialIn" property="source">
												<bean:write name="oneMaterialIn" property="record_date_simple_string" />
											</logic:equal>
											<logic:notEqual value="�������" name="oneMaterialIn" property="source">
												<bean:write name="oneMaterialIn" property="record_date_string" />
											</logic:notEqual>
										</td>
										<td>
											<bean:write name="oneMaterialIn" property="count" />
										</td>
										<td>
											<bean:write name="oneMaterialIn" property="source" />
										</td>
									</tr>
								</logic:iterate>
							</table>
						</logic:notEmpty>
					</logic:notEmpty>
				</td>
			</tr>
			<tr id="outMaterialTr" style="display:none;padding:10px;">
				<td colspan="2">
					<logic:notEmpty name="RECORD_DATA_MAP">
						<logic:notEmpty name="RECORD_DATA_MAP" property="record_out_list">
							<table width="100%" border="1" cellspacing="0" cellpadding="0"
								style="border-collapse: collapse;">
								<tr class="trcolor">
									<td>
										��������
									</td>
									<td>
										��ά��˾
									</td>
									<td>
										��ŵص�
									</td>
									<td>
										�������
									</td>
									<td>
										ʹ��ʱ��
									</td>
									<td>
										ʹ������
									</td>
									<td>
										������;
									</td>
								</tr>
								<logic:iterate id="oneMaterialIn" name="RECORD_DATA_MAP"
									property="record_out_list">
									<tr class="trcolor">
										<td>
											<bean:write name="oneMaterialIn" property="material_name" />
										</td>
										<td>
											<bean:write name="oneMaterialIn" property="contractorname" />
										</td>
										<td>
											<bean:write name="oneMaterialIn" property="address" />
										</td>
										<td>
											<bean:write name="oneMaterialIn" property="storage_type_name" />
										</td>
										<td>
											<bean:write name="oneMaterialIn" property="record_date_string" />
										</td>
										<td>
											<bean:write name="oneMaterialIn" property="count" />
										</td>
										<td>
											<bean:write name="oneMaterialIn" property="source" />
										</td>
									</tr>
								</logic:iterate>
							</table>
						</logic:notEmpty>
					</logic:notEmpty>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
					<input name="btnExportMaterial" type="button" class="button_not_click"
						style="cursor: hand" id="btnExportMaterial" value="�������ϳ������Ϣ"
						onclick="exportMaterialStockRecord('${use_type}');" />
				</td>
			</tr>
			<tr>
				<td colspan="2" height="10">
				</td>
			</tr>
		</table>
		<logic:equal value="1" name="use_type">
			<script type="text/javascript">
			btnOutMaterial.className = "button_clicked";
			//btnInMaterial.className="button_not_click";
			inMaterialTr.style.display="none";
			outMaterialTr.style.display="";
			</script>
		</logic:equal>
		<logic:equal value="0" name="use_type">
			<script type="text/javascript">
			//btnOutMaterial.className = "button_not_click";
			btnInMaterial.className="button_clicked";
			inMaterialTr.style.display="";
			outMaterialTr.style.display="none";
			</script>
		</logic:equal>
		</logic:notEmpty>
	</body>
</html>
