<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�޸���������</title>
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/tableoperate.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/validate.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/prototype.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/remedy/remedy_operate.js"></script>
		<script type="text/javascript" defer="defer"
			src="${pageContext.request.contextPath}/js/WdatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		var regx=/^\d+(\.\d+)?$/
		String.prototype.fee=function(){
			return (regx.test(this));
		}
		getSelectDateThis=function(strID) {
			document.all.item(strID).value = getPopDate(document.all.item(strID).value);
			return true;
		}
		saveApply=function(){
			var addForm=document.getElementById("modForm");
			if(checkFormData(addForm)){
				addForm.isSubmited.value="0";
				processBar(modForm);
				addForm.submit();
			}
		}
		submitApply=function(){
			var addForm=document.getElementById("modForm");
			if(checkFormData(addForm)&&checkApprover(addForm)&&judgeItem(addForm)&&judgeMaterial(addForm,"Use")&&judgeMaterial(addForm,"Recycle")){
				addForm.isSubmited.value="1";
				processBar(modForm);
				addForm.submit();
			}
		}
		checkApprover=function(addForm){
			var flag=judgeEmptyValue(addForm.approverId,"�����");
			flag=flag&&judgeValueLength(addForm.approverId,20,"�����");
			return flag;
		}
		checkFormData=function(addForm){
			var flag=judgeEmptyValue(addForm.projectName,"��Ŀ����");
			flag=flag&&judgeEmptyValue(addForm.remedyAddress,"�����ص�");
			flag=flag&&judgeEmptyValue(addForm.remedyDate,"����ʱ��");

			flag=flag&&judgeValueLength(addForm.projectName,50,"��Ŀ����");
			flag=flag&&judgeValueLength(addForm.remedyAddress,100,"�����ص�");
			if(addForm.remedyReason.value!=""){
				flag=flag&&judgeValueLength(addForm.remedyReason,1000,"ԭ��˵��");
			}
			if(addForm.remedySolve.value!=""){
				flag=flag&&judgeValueLength(addForm.remedySolve,1000,"������");
			}
			if(!document.getElementById("totalFee").value.fee()){
				alert("���̷��úϼƸ�ʽ����ȷ��");
				return false;
			}
			if(!document.getElementById("mtotalFee").value.fee()){
				alert("���Ϸ��úϼƸ�ʽ����ȷ��");
				return false;
			}
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
			var actionUrl="${pageContext.request.contextPath}/project/remedy_apply.do?method=judgeExistSameApply";
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
			var url = '${sessionScope.S_BACK_URL}';
			self.location.replace(url);
		}
		</script>
	</head>
	<body>
		<br>
		<%
			int i = 0;
			Map applyMap = (Map) request.getAttribute("apply_map");
			pageContext.setAttribute("one_apply", applyMap.get("one_apply"));
			pageContext.setAttribute("apply_item_list", applyMap.get("apply_item_list"));
		%>
		<template:titile value="�޸Ĺ�������" />
		<table width="98%" align="center" border="1" cellpadding="0" cellspacing="0" style="border-collpase:collapse;">
			<html:form action="/project/remedy_apply.do?method=updateApply" styleId="modForm" enctype="multipart/form-data">
				<input name="contractorId" value="<bean:write property="contractorId" name="one_apply" />" type="hidden" />
				<input name="remedyCode" value="<bean:write property="remedyCode" name="one_apply" />" type="hidden" />
				<input name="regionId" value="<bean:write property="regionId" name="one_apply" />" type="hidden" />
				<input name="creator" value="<bean:write property="creator" name="one_apply" />" type="hidden" />
				<input name="id" value="<bean:write property="id" name="one_apply" />" type="hidden" />
				<input name="isSubmited" value="1" type="hidden" />
				<tr class=trcolor>
					<td class="tdr" width="15%">
						��ţ�
					</td>
					<td class="tdl" width="35%">
						<bean:write property="remedyCode" name="one_apply" />
					</td>
					<td class="tdr" width="15%">
						ά����λ��
					</td>
					<td class="tdl" width="35%">
						<apptag:assorciateAttr table="contractorinfo"
							label="contractorname" key="contractorid"
							keyValue="${one_apply.contractorId}" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						��Ŀ���ƣ�
					</td>
					<td class="tdl">
						<html:text property="projectName" name="one_apply"
							styleClass="inputtext" style="width:215;" maxlength="200"
							onblur="judgeRemedyApplyExist(document.forms['modForm'],true);" />
						<span id="exist_remedy_apply_span"></span>
					</td>
					<td class="tdr">
						�����ص㣺
					</td>
					<td class="tdl">
						<html:text property="remedyAddress" name="one_apply"
							styleClass="inputtext" style="width:215;" maxlength="50" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						����ʱ�䣺
					</td>
					<td class="tdl" colspan=3>
						<html:text property="remedyDate" name="one_apply"
							styleClass="inputtext" style="width:215;" readonly="true"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ԭ��˵����
					</td>
					<td class="tdl">
						<html:textarea property="remedyReason" name="one_apply"
							styleClass="inputtextarea" style="width:215px;" />
					</td>
					<td class="tdr">
						��������
					</td>
					<td class="tdl">
						<html:textarea property="remedySolve" name="one_apply"
							styleClass="inputtextarea" style="width:215px;" />
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan=4 class="tdl">
						����������
						<input id="btnAdd" name="btnAdd" value="���" type="button"
							onclick="addTbRow('itemTable','sampleItemTable','trcolor');" />
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan="4" class="tdc" style="padding:10px">
						<table id="itemTable" width="100%" border="1" cellpadding="0" cellspacing="0" style="border-collpase:collapse;">
							<tr>
								<td style="text-align: center; width: 25%">
									��Ŀ����
								</td>
								<!-- td rowspan="2" style="text-align: center; width: 20%">
									��λ
								</td>
								<td rowspan="2" style="text-align: center; width: 20%">
									������
								</td -->
								<td style="text-align: center; width: 15%">
									����
								</td>
							</tr>
							<!-- 
							<tr class=trcolor>
								<td style="text-align: center; width: 150;">
									��Ŀ
								</td>
								<td style="text-align: center; width: 150;">
									���
								</td>
							</tr>
							<select id="sItemType" name="sItemType" style="width: 125; display: none;">>
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
							 -->
							<logic:notEmpty name="apply_item_list">
								<%
									i = 1;
								%>
								<logic:iterate id="oneApplyItem" name="apply_item_list">
									<tr id="<%=i%>" class=trcolor>
										<td style="text-align: center;">
											<input id="itemName_<%=i%>" name="itemName" type="text"
												value="<bean:write name="oneApplyItem" property="itemname" />" />
											<!-- 
											<select id="itemName_<%//=i%>" name="itemName" style="width: 125;"
												onchange="changeItemType('itemName','sItemType','itemType','<%//=i%>');calculateOne('<%//=i%>');calculateAll();">
												<logic:notEmpty name="item_list">
													<logic:iterate id="oneItem" name="item_list">
														<option value="<bean:write property="itemname" name="oneItem" />"
															varId="<bean:write property="id" name="oneItem" />" >
															<bean:write property="itemname" name="oneItem" />
														</option>
													</logic:iterate>
												</logic:notEmpty>
											</select>
											 -->
										</td>
										<!-- 
										<td style="text-align: center;">
											<select id="itemType_<%//=i%>" name="itemType"
												style="width: 125;"
												onchange="displayUnit('itemType','<%//=i%>');calculateOne('<%//=i%>');calculateAll();">
												<option value="">
													��ѡ��
												</option>
											</select>
										</td>
										 -->
										<!-- td style="text-align: center;">
											<input id="itemUnit_<%//=i%>" name="itemUnit" type="hidden"
												value="<bean:write name="oneApplyItem" property="item_unit" />" />
											<span id="itemUnitDis_<%//=i%>"><bean:write
													name="oneApplyItem" property="item_unit" /> </span>
										</td>
										<td style="text-align: center;">
											<input id="itemWorkNumber_<%//=i%>" name="itemWorkNumber"
												type="text" style="width: 80;"
												value="<bean:write name="oneApplyItem" property="remedyload" />" />
										</td -->
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
									</script>
									<%
										i++;
									%>
								</logic:iterate>
							</logic:notEmpty>
						</table>
						<table id="sampleItemTable" border="0" cellpadding="0" cellspacing="0" width="100%" style="display: none;">
							<tr>
								<td style="text-align: center;">
									<input id="sample_itemId_row0" name="sample_itemId" value=""
										type="hidden" />
									<input id="sample_itemTypeId_row0" name="sample_itemTypeId"
										value="" type="hidden" />
									<input id="sample_itemName_row0" name="sample_itemName"
										value="" type="text" />
									<!-- 
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
									 -->
								</td>
								<!-- 
								<td style="text-align: center;">
									<select id="sample_itemType_row0" name="sample_itemType" style="width: 125;"
										onchange="displayUnit('itemType','row0');calculateOne('row0');calculateAll();">
										<option value="">
											��ѡ��
										</option>
									</select>
								</td>
								 -->
								<!-- td style="text-align: center;">
									<input id="sample_itemUnit_row0" name="sample_itemUnit"
										type="hidden" value="" />
									<span id="sample_itemUnitDis_row0"></span>
								</td>
								<td style="text-align: center;">
									<input id="sample_itemWorkNumber_row0"
										name="sample_itemWorkNumber" type="text" style="width: 80;" />
								</td -->
								<td style="text-align: center;">
									<input id="btnDel" name="btnDel" value="ɾ��" type="button"
										onclick="deleteTbRow('itemTable','sampleItemTable',row0);calculateAll();" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						���̷��úϼƣ�Ԫ����
					</td>
					<td class="tdl" colspan="3">
						<html:text property="totalFee" styleId="totalFee" name="one_apply"
							styleClass="inputtext" />
						<html:hidden property="mtotalFee" styleClass="inputtext" value="0" />
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan="4" class="tdc">
						<apptag:materialselect label="ʹ�ò���" materialUseType="Use" objectId="${one_apply.id}" useType="project_remedy" />
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan="4" class="tdc">
						<apptag:materialselect label="���ղ���" materialUseType="Recycle" objectId="${one_apply.id}" useType="project_remedy"/>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						���Ϸ��úϼƣ�Ԫ����
					</td>
					<td class="tdl" colspan="3">
						<html:text property="mtotalFee" name="one_apply"
							styleClass="inputtext"  styleId="mtotalFee"/>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						������
					</td>
					<td class="tdl" colspan=3>
						<apptag:upload state="edit" cssClass="" entityId="${one_apply.id}" entityType="LP_REMEDY"/>
					</td>
				</tr>
				<tr class=trcolor>
					<apptag:approverselect label="�����" approverType="approver" inputName="approverId" spanId="approverSpan" objectType="LP_REMEDY" objectId="${one_apply.id}" inputType="radio" notAllowName="reader"/>
				</tr>
				<tr class=trcolor>
					<apptag:approverselect label="������" approverType="reader" inputName="reader" spanId="readerSpan" objectType="LP_REMEDY" objectId="${one_apply.id}" notAllowName="approverId" />
				</tr>
		</table>
		<table align="center" border="0">
			<tr>
				<td>
					<html:button property="action" styleClass="button"
						onclick="saveApply();">�ݴ�</html:button>
				</td>
				<td>
					<html:button property="action" styleClass="button"
						onclick="submitApply();">�ύ</html:button>
				</td>
				<td>
					<html:button property="action" styleClass="button"
						onclick="goBack();">����</html:button>
				</td>
			</tr>
		</table>
		</html:form>
		</table>
	</body>
</html>
