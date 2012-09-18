<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>修改修缮申请</title>
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
			var flag=judgeEmptyValue(addForm.approverId,"审核人");
			flag=flag&&judgeValueLength(addForm.approverId,20,"审核人");
			return flag;
		}
		checkFormData=function(addForm){
			var flag=judgeEmptyValue(addForm.projectName,"项目名称");
			flag=flag&&judgeEmptyValue(addForm.remedyAddress,"发生地点");
			flag=flag&&judgeEmptyValue(addForm.remedyDate,"申请时间");

			flag=flag&&judgeValueLength(addForm.projectName,50,"项目名称");
			flag=flag&&judgeValueLength(addForm.remedyAddress,100,"发生地点");
			if(addForm.remedyReason.value!=""){
				flag=flag&&judgeValueLength(addForm.remedyReason,1000,"原因说明");
			}
			if(addForm.remedySolve.value!=""){
				flag=flag&&judgeValueLength(addForm.remedySolve,1000,"处理方案");
			}
			if(!document.getElementById("totalFee").value.fee()){
				alert("工程费用合计格式不正确！");
				return false;
			}
			if(!document.getElementById("mtotalFee").value.fee()){
				alert("材料费用合计格式不正确！");
				return false;
			}
			var flg=judgeRemedyApplyExist(addForm,false);
			if(!flg){
				alert("已经存在相同的修缮申请！");
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
					exist_remedy_apply_span.innerHTML="<font color='#FF0000'>已经存在相同的修缮申请！</font>";
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
		<template:titile value="修改工程申请" />
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
						编号：
					</td>
					<td class="tdl" width="35%">
						<bean:write property="remedyCode" name="one_apply" />
					</td>
					<td class="tdr" width="15%">
						维护单位：
					</td>
					<td class="tdl" width="35%">
						<apptag:assorciateAttr table="contractorinfo"
							label="contractorname" key="contractorid"
							keyValue="${one_apply.contractorId}" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						项目名称：
					</td>
					<td class="tdl">
						<html:text property="projectName" name="one_apply"
							styleClass="inputtext" style="width:215;" maxlength="200"
							onblur="judgeRemedyApplyExist(document.forms['modForm'],true);" />
						<span id="exist_remedy_apply_span"></span>
					</td>
					<td class="tdr">
						发生地点：
					</td>
					<td class="tdl">
						<html:text property="remedyAddress" name="one_apply"
							styleClass="inputtext" style="width:215;" maxlength="50" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						申请时间：
					</td>
					<td class="tdl" colspan=3>
						<html:text property="remedyDate" name="one_apply"
							styleClass="inputtext" style="width:215;" readonly="true"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						原因说明：
					</td>
					<td class="tdl">
						<html:textarea property="remedyReason" name="one_apply"
							styleClass="inputtextarea" style="width:215px;" />
					</td>
					<td class="tdr">
						处理方案：
					</td>
					<td class="tdl">
						<html:textarea property="remedySolve" name="one_apply"
							styleClass="inputtextarea" style="width:215px;" />
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan=4 class="tdl">
						工作量汇总
						<input id="btnAdd" name="btnAdd" value="添加" type="button"
							onclick="addTbRow('itemTable','sampleItemTable','trcolor');" />
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan="4" class="tdc" style="padding:10px">
						<table id="itemTable" width="100%" border="1" cellpadding="0" cellspacing="0" style="border-collpase:collapse;">
							<tr>
								<td style="text-align: center; width: 25%">
									项目名称
								</td>
								<!-- td rowspan="2" style="text-align: center; width: 20%">
									单位
								</td>
								<td rowspan="2" style="text-align: center; width: 20%">
									工作量
								</td -->
								<td style="text-align: center; width: 15%">
									操作
								</td>
							</tr>
							<!-- 
							<tr class=trcolor>
								<td style="text-align: center; width: 150;">
									项目
								</td>
								<td style="text-align: center; width: 150;">
									类别
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
													请选择
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
											<input id="btnDel" name="btnDel" value="删除" type="button"
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
											请选择
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
											请选择
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
									<input id="btnDel" name="btnDel" value="删除" type="button"
										onclick="deleteTbRow('itemTable','sampleItemTable',row0);calculateAll();" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						工程费用合计（元）：
					</td>
					<td class="tdl" colspan="3">
						<html:text property="totalFee" styleId="totalFee" name="one_apply"
							styleClass="inputtext" />
						<html:hidden property="mtotalFee" styleClass="inputtext" value="0" />
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan="4" class="tdc">
						<apptag:materialselect label="使用材料" materialUseType="Use" objectId="${one_apply.id}" useType="project_remedy" />
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan="4" class="tdc">
						<apptag:materialselect label="回收材料" materialUseType="Recycle" objectId="${one_apply.id}" useType="project_remedy"/>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						材料费用合计（元）：
					</td>
					<td class="tdl" colspan="3">
						<html:text property="mtotalFee" name="one_apply"
							styleClass="inputtext"  styleId="mtotalFee"/>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						附件：
					</td>
					<td class="tdl" colspan=3>
						<apptag:upload state="edit" cssClass="" entityId="${one_apply.id}" entityType="LP_REMEDY"/>
					</td>
				</tr>
				<tr class=trcolor>
					<apptag:approverselect label="审核人" approverType="approver" inputName="approverId" spanId="approverSpan" objectType="LP_REMEDY" objectId="${one_apply.id}" inputType="radio" notAllowName="reader"/>
				</tr>
				<tr class=trcolor>
					<apptag:approverselect label="抄送人" approverType="reader" inputName="reader" spanId="readerSpan" objectType="LP_REMEDY" objectId="${one_apply.id}" notAllowName="approverId" />
				</tr>
		</table>
		<table align="center" border="0">
			<tr>
				<td>
					<html:button property="action" styleClass="button"
						onclick="saveApply();">暂存</html:button>
				</td>
				<td>
					<html:button property="action" styleClass="button"
						onclick="submitApply();">提交</html:button>
				</td>
				<td>
					<html:button property="action" styleClass="button"
						onclick="goBack();">返回</html:button>
				</td>
			</tr>
		</table>
		</html:form>
		</table>
	</body>
</html>
