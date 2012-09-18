<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�޸���������</title>
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/tableoperate.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/validate.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/prototype.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/remedy/remedy_operate.js"></script>
		<script type="text/javascript" defer="defer"
			src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		var regx=/^\d+(\.\d+)?$/
		getSelectDateThis=function(strID) {
			document.all.item(strID).value = getPopDate(document.all.item(strID).value);
			return true;
		}
		saveApply=function(){
			var addForm=document.getElementById("modForm");
			if(checkFormData(addForm)){
				addForm.isSubmited.value="0";
				addForm.submit();
			}
		}
		submitApply=function(){
			var addForm=document.getElementById("modForm");
			if(checkFormData(addForm)&&judgeItem(addForm)&&judgeMaterial(addForm)){
				addForm.isSubmited.value="1";
				addForm.submit();
			}
		}
		checkFormData=function(addForm){
			var flag=judgeEmptyValue(addForm.remedyCode,"����������");
			flag=flag&&judgeEmptyValue(addForm.remedyAddress,"�����ص�");
			flag=flag&&judgeEmptyValue(addForm.remedyDate,"����ʱ��");
			flag=flag&&judgeEmptyValue(addForm.projectName,"��Ŀ����");
			flag=flag&&judgeEmptyValue(addForm.townId,"����");
			flag=flag&&judgeEmptyValue(addForm.totalFee,"�ϼ�");
			flag=flag&&judgeEmptyValue(addForm.nextProcessMan,"�ύ������");
			
			flag=flag&&judgeValueLength(addForm.remedyCode,30,"����������");
			flag=flag&&judgeValueLength(addForm.remedyAddress,100,"�����ص�");
			flag=flag&&judgeValueLength(addForm.projectName,50,"��Ŀ����");
			flag=flag&&judgeValueLength(addForm.nextProcessMan,20,"�ύ������");
			if(addForm.remedyReason.value!=""){
				flag=flag&&judgeValueLength(addForm.remedyReason,1000,"ԭ��˵��");
			}
			if(addForm.remedySolve.value!=""){
				flag=flag&&judgeValueLength(addForm.remedySolve,1000,"������");
			}
			
			flag=flag&&validateTime(addForm.remedyDate.value,"","����ʱ�����Ϊ���ڸ�ʽ��yyyy/mm/dd����"," ","/");
			
			flag=flag&&judgeValueIsFloat(addForm.totalFee,"�ϼƱ���Ϊ���֣�");
			
			var flg=judgeRemedyApplyExist(addForm,false);
			if(!flg){
				alert("�Ѿ�������ͬ���������룡");
			}
			flag=flag&&flg;
			return flag;
		}
		judgeRemedyApplyExist=function(addForm,syncType){
			var name=addForm.projectName.value;
			var id=addForm.id.value;
			var actionUrl="<%=request.getContextPath()%>/remedy_apply.do?method=judgeExistSameApply";
			var flag;
			new Ajax.Request(actionUrl + "&&project_name="+name+"&&apply_id="+id+"&&rnd="+Math.random(), {method:"post", onSuccess:function (transport) {
				if(transport.responseText=="1"){
					exist_remedy_apply_span.innerHTML="<font color='#FF0000'>�Ѿ�������ͬ���������룡</font>";
					flag=false;
				}else{
					exist_remedy_apply_span.innerHTML="";
					flag=true;
				}
			}, onFailure:function () {
				flag=false;
			}, evalScripts:true, asynchronous:syncType});
			return flag;
		}
		goBack=function(){
			var url = '<%=(String) request.getSession().getAttribute("S_BACK_URL")%>';
			self.location.replace(url);
		}
		</script>
		<script type="text/javascript">
		var fileNum=0;
		 //�ű����ɵ�ɾ����  ť��ɾ������
		function deleteRow(uploadIDV,rowId){
	      	var uploadID=document.getElementById(uploadIDV);
    		//��ð�ť�����е�id
    	 	var rowid = "000";
        	//rowid = this.id;
        	rowid=rowId;
	        rowid = rowid.substring(1,rowid.length);
    	     //��idת��Ϊ�������е�����,��ɾ��
      		for(i =0; i<uploadID.rows.length;i++){
        		if(uploadID.rows[i].id == rowid){
            		uploadID.deleteRow(uploadID.rows[i].rowIndex);
	                fileNum--;
    	        }
        	}
    	}
	    //���һ������
    	function addRow(uploadIDV,param){
    		var uploadID=document.getElementById(uploadIDV);
    		var  onerow=uploadID.insertRow(-1);
	        onerow.id = uploadID.rows.length-2;
    	    var   cell1=onerow.insertCell();
			var   cell2=onerow.insertCell();
			//����һ�������
    	    var t1 = document.createElement("input");
        	//t1.name = "filename"+onerow.id;//alert(onerow.id);
	        t1.name = "uploadFile"+param+"["+ fileNum + "].file";
			t1.type= "file";
			t1.id = "text" + onerow.id;
			t1.width="180";
    	    fileNum++;
        	//����ɾ����ť
	        var b1 =document.createElement("button");
    	    b1.value = "ɾ��";
        	b1.id = "b" + onerow.id;
	        b1.onclick=function(){deleteRow(uploadIDV,b1.id)};
    	    //alert(b1.id + "rrr");
        	cell1.appendChild(t1);//����
	        cell2.appendChild(b1);
		}
		</script>
	</head>
	<body>
		<br>
		<%
			int i = 0;
			Map applyMap = (Map) request.getAttribute("apply_map");
			pageContext.setAttribute("one_apply", applyMap.get("one_apply"));
			pageContext.setAttribute("reason_file_list", applyMap
					.get("reason_file_list"));
			pageContext.setAttribute("reason_file_name_list", applyMap
					.get("reason_file_name_list"));
			pageContext.setAttribute("solve_file_list", applyMap
					.get("solve_file_list"));
			pageContext.setAttribute("solve_file_name_list", applyMap
					.get("solve_file_name_list"));
			pageContext.setAttribute("apply_item_list", applyMap
					.get("apply_item_list"));
			pageContext.setAttribute("apply_material_list", applyMap
					.get("apply_material_list"));
		%>
		<template:titile value="�޸���������" />
		<html:form action="/remedy_apply.do?method=updateApply"
			styleId="modForm" enctype="multipart/form-data">
			<table width="750" bgcolor="#FFFFFF" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr>
					<td colspan="2">
						<table width="100%">
							<tr>
								<td>
									<bean:write property="remedyCode" name="one_apply" />
									<input name="contractorId"
										value="<bean:write property="contractorId" name="one_apply" />"
										type="hidden" />
									<input name="remedyCode"
										value="<bean:write property="remedyCode" name="one_apply" />"
										type="hidden" />
									<input name="regionId"
										value="<bean:write property="regionId" name="one_apply" />"
										type="hidden" />
									<input name="creator"
										value="<bean:write property="creator" name="one_apply" />"
										type="hidden" />
									<input name="prevState"
										value="<bean:write property="state" name="one_apply" />"
										type="hidden" />
									<input name="id"
										value="<bean:write property="id" name="one_apply" />"
										type="hidden" />
									<input name="currentStepId"
										value="<bean:write property="currentStepId" name="one_apply" />"
										type="hidden" />
									<input name="applyState"
										value="<bean:write property="state" name="one_apply" />"
										type="hidden" />
									<input name="_reason"
										value="<bean:write name="reason_file_list" />" type="hidden" />
									<input name="_reason_name"
										value="<bean:write name="reason_file_name_list" />"
										type="hidden" />
									<input name="_solve"
										value="<bean:write name="solve_file_list" />" type="hidden" />
									<input name="_solve_name"
										value="<bean:write name="solve_file_name_list" />"
										type="hidden" />
									<input name="isSubmited" value="1" type="hidden" />
								</td>
							</tr>
							<tr>
								<td align="center" width="15%">
									��Ŀ����
								</td>
								<td width="35%">
									<html:text property="projectName" name="one_apply"
										styleClass="inputtext" style="width:215;" maxlength="200"
										onblur="judgeRemedyApplyExist(document.forms['modForm'],true);" />
									<span id="exist_remedy_apply_span"></span>
								</td>
								<td align="center" width="15%">
									ά����λ
								</td>
								<td width="35%">
									<bean:write property="contractorName" name="one_apply" />
								</td>
							</tr>
							<tr>
								<td align="center">
									�����ص�
								</td>
								<td>
									<html:text property="remedyAddress" name="one_apply"
										styleClass="inputtext" style="width:215;" maxlength="50" />
								</td>
								<td align="center">
									����ʱ��
								</td>
								<td>
									<html:text property="remedyDate" name="one_apply"
										styleClass="inputtext" style="width:215;" readonly="true"
										onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
								</td>
							</tr>
							<tr>
								<td align="center">
									����
								</td>
								<td colspan="3">
									<logic:notEmpty name="town_list">
										<select name="townId" style="width: 215;">
											<logic:iterate id="oneTown" name="town_list">
												<option value="<bean:write property="id" name="oneTown" />">
													<bean:write property="town" name="oneTown" />
												</option>
											</logic:iterate>
										</select>
									</logic:notEmpty>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<select id="sItemType" name="sItemType"
							style="width: 125; display: none;">
							<logic:notEmpty name="item_type_list">
								<logic:iterate id="oneItemType" name="item_type_list">
									<option
										value="<bean:write property="typename" name="oneItemType" />"
										varId="<bean:write property="id" name="oneItemType" />"
										varRefId="<bean:write property="remedyitemid" name="oneItemType" />"
										varUnit="<bean:write property="unit" name="oneItemType" />"
										varUnitPrice="<bean:write property="price" name="oneItemType" />">
										<bean:write property="typename" name="oneItemType" />
									</option>
								</logic:iterate>
							</logic:notEmpty>
						</select>
						<table id="itemTable" border="0" cellpadding="0" cellspacing="0"
							width="100%">
							<tr>
								<td colspan="2" style="text-align: center; width: 300;">
									��Ŀ����
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									���ۣ�Ԫ��
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									��λ
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									������
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									Ԥ����ã�Ԫ��
								</td>
								<td rowspan="2" style="text-align: center; width: 50;">
									����
								</td>
							</tr>
							<tr>
								<td style="text-align: center; width: 150;">
									��Ŀ
								</td>
								<td style="text-align: center; width: 150;">
									���
								</td>
							</tr>
							<logic:notEmpty name="apply_item_list">
								<%
									i = 1;
								%>
								<logic:iterate id="oneApplyItem" name="apply_item_list">
									<tr id="<%=i%>">
										<td style="text-align: center; width: 150;">
											<input id="itemId_<%=i%>" name="itemId" type="hidden"
												value="<bean:write name="oneApplyItem" property="item_id" />" />
											<input id="itemTypeId_<%=i%>" name="itemTypeId" type="hidden"
												value="<bean:write name="oneApplyItem" property="remedyitemtypeid" />" />
											<select id="itemName_<%=i%>" name="itemName"
												style="width: 125;"
												onchange="changeItemType('itemName','sItemType','itemType','<%=i%>');calculateOne('<%=i%>');calculateAll();">
												<logic:notEmpty name="item_list">
													<logic:iterate id="oneItem" name="item_list">
														<option
															value="<bean:write property="itemname" name="oneItem" />"
															varId="<bean:write property="id" name="oneItem" />">
															<bean:write property="itemname" name="oneItem" />
														</option>
													</logic:iterate>
												</logic:notEmpty>
											</select>
										</td>
										<td style="text-align: center; width: 150;">
											<select id="itemType_<%=i%>" name="itemType"
												style="width: 125;"
												onchange="displayUnit('itemType','<%=i%>');calculateOne('<%=i%>');calculateAll();">
												<option value="">
													��ѡ��
												</option>
											</select>
										</td>
										<td style="text-align: center;">
											<input id="itemUnitPrice_<%=i%>" name="itemUnitPrice"
												type="hidden"
												value="<bean:write name="oneApplyItem" property="price" />" />
											<span id="itemUnitPriceDis_<%=i%>"><bean:write
													name="oneApplyItem" property="price" /> </span>
										</td>
										<td style="text-align: center;">
											<input id="itemUnit_<%=i%>" name="itemUnit" type="hidden"
												value="<bean:write name="oneApplyItem" property="item_unit" />" />
											<span id="itemUnitDis_<%=i%>"><bean:write
													name="oneApplyItem" property="item_unit" /> </span>
										</td>
										<td style="text-align: center;">
											<input id="itemWorkNumber_<%=i%>" name="itemWorkNumber"
												type="text" style="width: 80;"
												value="<bean:write name="oneApplyItem" property="remedyload" />"
												onblur="calculateOne('<%=i%>');calculateAll();" />
										</td>
										<td style="text-align: center;">
											<input id="itemFee_<%=i%>" name="itemFee" type="hidden"
												value="<bean:write name="oneApplyItem" property="remedyfee" />" />
											<span id="itemFeeDis_<%=i%>"><bean:write
													name="oneApplyItem" property="remedyfee" /> </span>
										</td>
										<td style="text-align: center;">
											<input id="btnDel" name="btnDel" value="ɾ��" type="button"
												onclick="deleteTbRow('itemTable','sampleItemTable',<%=i%>);calculateAll();" />
										</td>
									</tr>
									<%
										i++;
									%>
								</logic:iterate>
								<%
									i = 1;
								%>
								<logic:iterate id="oneItem" name="apply_item_list">
									<script type="text/javascript">
										if(typeof(document.forms[0].elements["itemFee"].length)=="undefined"){
											document.forms[0].elements["itemName"].value='<bean:write name="oneItem" property="itemname" />';
											changeItemType('itemName','sItemType','itemType','1');
											document.forms[0].elements["itemType"].value='<bean:write name="oneItem" property="typename" />';
											displayUnit('itemType','1');
										}else{
											document.forms[0].elements["itemName"][<%=(i - 1)%>].value='<bean:write name="oneItem" property="itemname" />';
											changeItemType('itemName','sItemType','itemType','<%=i%>');
											document.forms[0].elements["itemType"][<%=(i - 1)%>].value='<bean:write name="oneItem" property="typename" />';
											displayUnit('itemType','<%=i%>');
										}
									</script>
									<%
										i++;
									%>
								</logic:iterate>
							</logic:notEmpty>
						</table>
						<table id="sampleItemTable" border="0" cellpadding="0"
							cellspacing="0" width="100%" style="display: none;">
							<tr>
								<td style="text-align: center;">
									<input id="sample_itemId_row0" name="sample_itemId" value=""
										type="hidden" />
									<input id="sample_itemTypeId_row0" name="sample_itemTypeId"
										value="" type="hidden" />
									<select id="sample_itemName_row0" name="sample_itemName"
										style="width: 125;"
										onchange="changeItemType('itemName','sItemType','itemType','row0');calculateOne('row0');calculateAll();">
										<option value="">
											��ѡ��
										</option>
										<logic:notEmpty name="item_list">
											<logic:iterate id="oneItem" name="item_list">
												<option
													value="<bean:write property="itemname" name="oneItem" />"
													varId="<bean:write property="id" name="oneItem" />">
													<bean:write property="itemname" name="oneItem" />
												</option>
											</logic:iterate>
										</logic:notEmpty>
									</select>
								</td>
								<td style="text-align: center;">
									<select id="sample_itemType_row0" name="sample_itemType"
										style="width: 125;"
										onchange="displayUnit('itemType','row0');calculateOne('row0');calculateAll();">
										<option value="">
											��ѡ��
										</option>
									</select>
								</td>
								<td style="text-align: center;">
									<input id="sample_itemUnitPrice_row0"
										name="sample_itemUnitPrice" type="hidden" value="" />
									<span id="sample_itemUnitPriceDis_row0"></span>
								</td>
								<td style="text-align: center;">
									<input id="sample_itemUnit_row0" name="sample_itemUnit"
										type="hidden" value="" />
									<span id="sample_itemUnitDis_row0"></span>
								</td>
								<td style="text-align: center;">
									<input id="sample_itemWorkNumber_row0"
										name="sample_itemWorkNumber" type="text" style="width: 80;"
										onblur="calculateOne('row0');calculateAll();" />
								</td>
								<td style="text-align: center;">
									<input id="sample_itemFee_row0" name="sample_itemFee"
										type="hidden" />
									<span id="sample_itemFeeDis_row0"></span>
								</td>
								<td style="text-align: center;">
									<input id="btnDel" name="btnDel" value="ɾ��" type="button"
										onclick="deleteTbRow('itemTable','sampleItemTable',row0);calculateAll();" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<template:formTr name="�ϼƣ�Ԫ��" style="background-color:#FFFFFF;">
					<html:hidden property="totalFee" name="one_apply"
						styleClass="inputtext" />
					<span id="total_fee"><bean:write property="totalFee"
							name="one_apply" />Ԫ </span>
				</template:formTr>
				<tr>
					<td colspan="2">
						<input id="btnAdd" name="btnAdd" value="��Ӷ�����" type="button"
							onclick="addTbRow('itemTable','sampleItemTable');" class="button" />
					</td>
				</tr>
				
				<template:formTr name="ԭ��˵��" style="background-color:#FFFFFF;">
					<html:textarea property="remedyReason" name="one_apply"
						styleClass="inputtextarea" style="width:215;" rows="5" />
					<table id="uploadID1"
						style="width: 215; margin-left: 0px; margin-top: 0px;" border="0"
						align="left" cellpadding="0" cellspacing="0">
						<logic:empty name="reason_file_list">
						</logic:empty>
						<logic:notEmpty name="reason_file_list">
							<tr>
								<td width="180">
									<bean:define id="temp" name="reason_file_list"
										type="java.lang.String" />
									<apptag:listAttachmentLink fileIdList="<%=temp%>"
										showdele="true" deleteSuffix="_reason" />
								</td>
								<td width="35">
									&nbsp;
								</td>
							</tr>
						</logic:notEmpty>
					</table>
					<br>
					<input name="btnUpload" class="button" value="���ԭ�򸽼�" type="button"
						onclick="addRow('uploadID1','_reason');" />
				</template:formTr>
				<template:formTr name="������" style="background-color:#FFFFFF;">
					<html:textarea property="remedySolve" name="one_apply"
						styleClass="inputtextarea" style="width:215;" rows="5" />
					<table id="uploadID2"
						style="width: 215; margin-left: 0px; margin-top: 0px;" border="0"
						align="left" cellpadding="0" cellspacing="0">
						<logic:empty name="solve_file_list">
						</logic:empty>
						<logic:notEmpty name="solve_file_list">
							<tr>
								<td width="180">
									<bean:define id="temp" name="solve_file_list"
										type="java.lang.String" />
									<apptag:listAttachmentLink fileIdList="<%=temp%>"
										showdele="true" deleteSuffix="_solve" />
								</td>
								<td width="35">
									&nbsp;
								</td>
							</tr>
						</logic:notEmpty>
					</table>
					<br>
					<input name="btnUpload" class="button" value="��ӷ�������" type="button"
						onclick="addRow('uploadID2','_solve');" />
				</template:formTr>
				<tr>
					<td colspan="2">
						<select id="sMaterialMode" name="sMaterialMode"
							style="width: 95; display: none;">
							<logic:notEmpty name="material_model_list">
								<logic:iterate id="oneMaterialMode" name="material_model_list">
									<option
										value="<bean:write property="id" name="oneMaterialMode" />"
										varRefId="<bean:write property="typeid" name="oneMaterialMode" />">
										<bean:write property="modelname" name="oneMaterialMode" />
									</option>
								</logic:iterate>
							</logic:notEmpty>
						</select>
						<select id="sMaterial" name="sMaterial"
							style="width: 95; display: none;">
							<logic:notEmpty name="material_list">
								<logic:iterate id="oneMaterial" name="material_list">
									<option value="<bean:write property="id" name="oneMaterial" />"
										varRefId="<bean:write property="modelid" name="oneMaterial" />"
										varUnitPrice="<bean:write property="price" name="oneMaterial" />">
										<bean:write property="name" name="oneMaterial" />
									</option>
								</logic:iterate>
							</logic:notEmpty>
						</select>
						<select id="sMaterialStorageAddr" name="sMaterialStorageAddr"
							style="width: 95; display: none;">
							<logic:notEmpty name="material_storage_list">
								<logic:iterate id="oneMaterialStorage"
									name="material_storage_list">
									<option
										value="<bean:write property="addressid" name="oneMaterialStorage" />"
										varRefId="<bean:write property="materialid" name="oneMaterialStorage" />"
										varOldStorage="<bean:write property="oldstock" name="oneMaterialStorage" />"
										varNewStorage="<bean:write property="newstock" name="oneMaterialStorage" />">
										<bean:write property="address" name="oneMaterialStorage" />
									</option>
								</logic:iterate>
							</logic:notEmpty>
						</select>
						<table id="materialTable" border="0" cellpadding="0"
							cellspacing="0" width="100%">
							<tr>
								<td colspan="3" style="text-align: center; width: 300;">
									����
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									��ŵص�
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									���Ͽ������
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									�����
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									����
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									ʹ������
								</td>
								<td rowspan="2" style="text-align: center; width: 100;">
									���Ϸ���
								</td>
								<td rowspan="2" style="text-align: center; width: 50;">
									����
								</td>
							</tr>
							<tr>
								<td style="text-align: center; width: 100;">
									����
								</td>
								<td style="text-align: center; width: 100;">
									���
								</td>
								<td style="text-align: center; width: 100;">
									����
								</td>
							</tr>
							<logic:notEmpty name="apply_material_list">
								<%
									i = 1;
								%>
								<logic:iterate id="oneApplyMaterial" name="apply_material_list">
									<tr id="<%=i%>">
										<td style="text-align: center; width: 100;">
											<select id="materialType_<%=i%>" name="materialType"
												style="width: 95;"
												onchange="changeMaterialMode('materialType','sMaterialMode','materialMode','<%=i%>');">
												<option value="">
													��ѡ��
												</option>
												<logic:notEmpty name="material_type_list">
													<logic:iterate id="oneMaterialType"
														name="material_type_list">
														<option
															value="<bean:write property="id" name="oneMaterialType" />">
															<bean:write property="typename" name="oneMaterialType" />
														</option>
													</logic:iterate>
												</logic:notEmpty>
											</select>
										</td>
										<td style="text-align: center; width: 100;">
											<select id="materialMode_<%=i%>" name="materialMode"
												style="width: 95;"
												onchange="changeMaterial('materialMode','sMaterial','material','<%=i%>');">
												<option value="">
													��ѡ��
												</option>
											</select>
										</td>
										<td style="text-align: center; width: 100;">
											<select id="material_<%=i%>" name="material"
												style="width: 95;"
												onchange="changeMaterialStorage('material','sMaterialStorageAddr','materialStorageAddr','<%=i%>');">
												<option value="">
													��ѡ��
												</option>
											</select>
										</td>
										<td style="text-align: center; width: 100;">
											<select id="materialStorageAddr_<%=i%>"
												name="materialStorageAddr" style="width: 95;"
												onchange="changeMaterialStorageType('<%=i%>');">
												<option value="">
													��ѡ��
												</option>
											</select>
										</td>
										<td style="text-align: center; width: 100;">
											<select id="materialStorageType_<%=i%>"
												name="materialStorageType" style="width: 95;"
												onchange="changeMaterialStorageNumber('materialStorageType','<%=i%>');">
												<option value="">
													��ѡ��
												</option>
												<option value="0">
													���ɲ���
												</option>
												<option value="1">
													��������
												</option>
											</select>
										</td>
										<td style="text-align: center; width: 100;">
											<input id="materialStorageNumber_<%=i%>"
												name="materialStorageNumber" type="hidden" />
											<span id="materialStorageNumberDis_<%=i%>"></span>
										</td>
										<td style="text-align: center; width: 100;">
											<input id="materialUnitPrice_<%=i%>"
												name="materialUnitPrice" type="hidden" />
											<span id="materialUnitPriceDis_<%=i%>"></span>
										</td>
										<td style="text-align: center; width: 100;">
											<input id="materialUseNumber_<%=i%>" name="materialUseNumber"
												type="text" style="width: 80;"
												onblur="compareStorageNumber('<%=i%>');calculateOneMaterial('<%=i %>');calculateAllMaterial();" />
										</td>
										<td style="text-align: center; width: 100;">
											<input id="materialPrice_<%=i%>"
												name="materialPrice" type="hidden" />
											<span id="materialPriceDis_<%=i%>"></span>
										</td>
										<td style="text-align: center;">
											<input id="btnDel" name="btnDel" value="ɾ��" type="button"
												onclick="deleteTbRow('materialTable','sampleMaterialTable',<%=i%>);calculateAllMaterial();" />
										</td>
									</tr>
									<%
										i++;
									%>
								</logic:iterate>
								<%
									i = 1;
								%>
								<logic:iterate id="oneApplyMaterial" name="apply_material_list">
									<script type="text/javascript">
										if(typeof(document.forms[0].elements["materialStorageNumber"].length)=="undefined"){
											document.forms[0].elements["materialType"].value='<bean:write name="oneApplyMaterial" property="typeid" />';
											changeMaterialMode('materialType','sMaterialMode','materialMode','1');
											document.forms[0].elements["materialMode"].value='<bean:write name="oneApplyMaterial" property="modelid" />';
											changeMaterial('materialMode','sMaterial','material','1');
											document.forms[0].elements["material"].value='<bean:write name="oneApplyMaterial" property="materialid" />';
											changeMaterialStorage('material','sMaterialStorageAddr','materialStorageAddr','1');
											document.forms[0].elements["materialStorageAddr"].value='<bean:write name="oneApplyMaterial" property="addressid" />';
											onchange="changeMaterialStorageType('1');"
											document.forms[0].elements["materialStorageType"].value='<bean:write name="oneApplyMaterial" property="storagetype" />';
											changeMaterialStorageNumber('materialStorageType','1');
											document.forms[0].elements["materialStorageNumber"].value='<bean:write name="oneApplyMaterial" property="storage_number" />';
											//document.forms[0].elements[""].value='<bean:write name="oneApplyMaterial" property="" />';
											document.forms[0].elements["materialUseNumber"].value='<bean:write name="oneApplyMaterial" property="use_number" />';
											//document.forms[0].elements[""].value='<bean:write name="oneApplyMaterial" property="" />';
										}else{
											document.forms[0].elements["materialType"][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="typeid" />';
											changeMaterialMode('materialType','sMaterialMode','materialMode','<%=i%>');
											document.forms[0].elements["materialMode"][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="modelid" />';
											changeMaterial('materialMode','sMaterial','material','<%=i%>');
											document.forms[0].elements["material"][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="materialid" />';
											changeMaterialStorage('material','sMaterialStorageAddr','materialStorageAddr','<%=i%>');
											document.forms[0].elements["materialStorageAddr"][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="addressid" />';
											onchange="changeMaterialStorageType('<%=(i - 1)%>');"
											document.forms[0].elements["materialStorageType"][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="storagetype" />';
											changeMaterialStorageNumber('materialStorageType','<%=i%>');
											document.forms[0].elements["materialStorageNumber"][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="storage_number" />';
											//document.forms[0].elements[""][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="" />';
											document.forms[0].elements["materialUseNumber"][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="use_number" />';
											//document.forms[0].elements[""][<%=(i - 1)%>].value='<bean:write name="oneApplyMaterial" property="" />';
										}
									</script>
									<%
										i++;
									%>
								</logic:iterate>
							</logic:notEmpty>
						</table>
						<table id="sampleMaterialTable" border="0" cellpadding="0"
							cellspacing="0" width="100%" style="display: none;">
							<tr>
								<td style="text-align: center;">
									<select id="sample_materialType_row0"
										name="sample_materialType" style="width: 95;"
										onchange="changeMaterialMode('materialType','sMaterialMode','materialMode','row0');">
										<option value="">
											��ѡ��
										</option>
										<logic:notEmpty name="material_type_list">
											<logic:iterate id="oneMaterialType" name="material_type_list">
												<option
													value="<bean:write property="id" name="oneMaterialType" />">
													<bean:write property="typename" name="oneMaterialType" />
												</option>
											</logic:iterate>
										</logic:notEmpty>
									</select>
								</td>
								<td style="text-align: center;">
									<select id="sample_materialMode_row0"
										name="sample_materialMode" style="width: 95;"
										onchange="changeMaterial('materialMode','sMaterial','material','row0');">
										<option value="">
											��ѡ��
										</option>
									</select>
								</td>
								<td style="text-align: center;">
									<select id="sample_material_row0" name="sample_material"
										style="width: 95;"
										onchange="changeMaterialStorage('material','sMaterialStorageAddr','materialStorageAddr','row0');">
										<option value="">
											��ѡ��
										</option>
									</select>
								</td>
								<td style="text-align: center;">
									<select id="sample_materialStorageAddr_row0"
										name="sample_materialStorageAddr" style="width: 95;"
										onchange="changeMaterialStorageType('row0');">
										<option value="">
											��ѡ��
										</option>
									</select>
								</td>
								<td style="text-align: center;">
									<select id="sample_materialStorageType_row0"
										name="sample_materialStorageType" style="width: 95;"
										onchange="changeMaterialStorageNumber('materialStorageType','row0');">
										<option value="">
											��ѡ��
										</option>
										<option value="0">
											���ɲ���
										</option>
										<option value="1">
											��������
										</option>
									</select>
								</td>
								<td style="text-align: center;">
									<input id="sample_materialStorageNumber_row0"
										name="sample_materialStorageNumber" type="hidden" />
									<span id="sample_materialStorageNumberDis_row0"></span>
								</td>
								<td style="text-align: center;">
									<input id="sample_materialUnitPrice_row0"
										name="sample_materialUnitPrice" type="hidden" />
									<span id="sample_materialUnitPriceDis_row0"></span>
								</td>
								<td style="text-align: center;">
									<input id="sample_materialUseNumber_row0"
										name="sample_materialUseNumber" type="text" style="width: 80;"
										onblur="compareStorageNumber('row0');calculateOneMaterial('row0');calculateAllMaterial();" />
								</td>
								<td style="text-align: center;">
									<input id="sample_materialPrice_row0"
										name="sample_materialPrice" type="hidden" />
									<span id="sample_materialPriceDis_row0"></span>
								</td>
								<td style="text-align: center;">
									<input id="btnDel" name="btnDel" value="ɾ��" type="button"
										onclick="deleteTbRow('materialTable','sampleMaterialTable',row0);calculateAllMaterial();" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<template:formTr name="�ϼƣ�Ԫ��" style="background-color:#FFFFFF;">
					<span id="total_material_fee">0</span>
					<html:hidden property="totalMaterialFee" styleClass="inputtext" value="0" />
				</template:formTr>
				<tr>
					<td colspan="2">
						<input id="btnAddMaterial" name="btnAddMaterial" value="��Ӳ�����"
							type="button"
							onclick="addTbRow('materialTable','sampleMaterialTable');"
							class="button" />
					</td>
				</tr>
				<template:formTr name="�ύ������" style="background-color:#FFFFFF;">
					<logic:notEmpty name="next_process_man_list">
						<select name="nextProcessMan" style="width: 215;">
							<logic:iterate id="oneProcessMan" name="next_process_man_list">
								<option
									value="<bean:write property="userid" name="oneProcessMan" />">
									<bean:write property="username" name="oneProcessMan" />
								</option>
							</logic:iterate>
						</select>
					</logic:notEmpty>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="saveApply();">�ݴ�</html:button>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="submitApply();">�ύ</html:button>
					</td>
					<td>
						<html:reset property="action" styleClass="button">����</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="goBack();">����</html:button>
					</td>
				</template:formSubmit>
			</table>
		</html:form>
		<script type="text/javascript">
		var addForm=document.getElementById("modForm");
		addForm.townId.value='<bean:write property="townId" name="one_apply" />';
		addForm.nextProcessMan.value='<bean:write property="nextProcessMan" name="one_apply" />';
		</script>
	</body>
</html>
