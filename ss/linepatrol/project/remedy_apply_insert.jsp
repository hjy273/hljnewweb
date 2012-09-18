<%@include file="/common/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加修缮申请</title>
		
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/tableoperate.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/validate.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/prototype.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/remedy/remedy_operate.js"></script>
		<script type="text/javascript" defer="defer" src="${pageContext.request.contextPath}/js/WdatePicker/WdatePicker.js"></script>
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
			var addForm=document.getElementById("addForm");
			if(checkFormData(addForm)){
				addForm.isSubmited.value="0";
				processBar(addForm);
				addForm.submit();
			}
		}
		submitApply=function(){
			var addForm=document.getElementById("addForm");
			if(checkFormData(addForm)&&checkApprover(addForm)&&judgeItem(addForm)&&judgeMaterial(addForm,"Use")&&judgeMaterial(addForm,"Recycle")){
				addForm.isSubmited.value="1";
				processBar(addForm);
				addForm.submit();
			}
		}
		judgeRemedyApplyExist=function(addForm,syncType){
			var name=addForm.projectName.value;
			var actionUrl="${pageContext.request.contextPath}/project/remedy_apply.do?method=judgeExistSameApply";
			var flag;
			new Ajax.Request(actionUrl + "&&project_name="+name+"&&apply_id=&&rnd="+Math.random(), {method:"post", onSuccess:function (transport) {
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
		
		judgeEmptyValue = function (item, message) {
			var value = "";
			if (typeof (item) != "undefined") {
				if (typeof (item.length) != "undefined") {
					for (i = 0; i < item.length; i++) {
						value = item[i].value;
						if (value == "") {
							alert(message + (i + 1) + "\u503c\u4e0d\u80fd\u4e3a\u7a7a\uff01");
							item[i].focus();
							return false;
						}
					}
				} else {
					value = item.value;
					if (value == "") {
						alert(message + "\u503c\u4e0d\u80fd\u4e3a\u7a7a\uff01");
						item.focus();
						return false;
					}
				}
			} else {
				alert("\u6ca1\u6709\u627e\u5230\u5143\u7d20\uff01");
				return false;
			}
			return true;
		};

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
		</script>
	</head>
	<body>
		<br>
		<template:titile value="添加工程申请" />
		<table width="98%" align="center" border="1" cellpadding="0" cellspacing="0" style="border-collpase:collapse;">
			<html:form action="/project/remedy_apply.do?method=insertApply" styleId="addForm" enctype="multipart/form-data">
				<input name="remedyCode" value="${generate_id}" type="hidden" />
				<input name="contractorId" value="${sessionScope.LOGIN_USER.deptID }" type="hidden" />
				<input name="regionId" value="${sessionScope.LOGIN_USER.regionID}" type="hidden" />
				<input name="creator" value="${sessionScope.LOGIN_USER.userID}" type="hidden" />
				<input name="isSubmited" value="1" type="hidden" />
							<tr class=trcolor>
								<td class="tdr">
									编号：
								</td>
								<td class="tdl">
									${generate_id}
								</td>
								<td class="tdr">
									维护单位：
								</td>
								<td class="tdl">
									${sessionScope.LOGIN_USER.deptName}
								</td>
							</tr>
							<tr class=trcolor>
								<td class="tdr">
									项目名称：
								</td>
								<td class="tdl">
									<html:text property="projectName" styleClass="inputtext" style="width:215;" maxlength="200" onblur="judgeRemedyApplyExist(document.forms['addForm'],true);" />
									<span id="exist_remedy_apply_span"></span>
								</td>
								<td class="tdr">
									发生地点：
								</td>
								<td class="tdl">
									<html:text property="remedyAddress" styleClass="inputtext" style="width:215;" maxlength="50" />
								</td>
							</tr>
							<tr class=trcolor>
								<td class="tdr">
									申请时间：
								</td>
								<td class="tdl" colspan=3>
									<html:text property="remedyDate" styleClass="Wdate" style="width:215;" readonly="true"
										onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
								</td>
							</tr>
							<tr class=trcolor>
								<td class="tdr">
									原因说明：
								</td>
								<td class="tdl">
									<html:textarea property="remedyReason" styleClass="inputtextarea" style="width:215px;" />
								</td>
								<td class="tdr">
									处理方案：
								</td>
								<td class="tdl">
									<html:textarea property="remedySolve" styleClass="inputtextarea" style="width:215px;" />
								</td>
							</tr>
				<tr class=trcolor>
					<td colspan=4 class="tdl" style="padding:10px">
						工作量汇总
						<input id="btnAddItem" name="btnAddItem" value="添加" type="button"
							onclick="addTbRow('itemTableSpecial', 'sampleItemTable','trcolor');" />
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan="4" class="tdc" style="padding:10px">
						<!-- 2010-04-27 yangjun update start -->
						<table id="itemTableSpecial" width="100%" border="1" cellpadding="0" cellspacing="0" style="border-collpase:collapse;">
						<!-- 2010-04-27 yangjun update end -->
							<tr>
								<!-- 
								<td rowspan="2" style="text-align: center; width: 5%">
									编号
								</td>
								 -->
								<td style="text-align: center; width: 25%">
									项目名称
								</td>
								<!-- td rowspan="2" style="text-align: center; width: 15%">
									单位
								</td>
								<td rowspan="2" style="text-align: center; width: 20%">
									工作量
								</td-->
								<td style="text-align: center; width: 15%">
									操作
								</td>
							</tr>
						</table>
						<table id="sampleItemTable" width="100%" style="display: none;">
							<tr>
								<!-- 
								<td style="text-align: center;"></td>
								 -->
								<td style="text-align: center;">
									<input id="sample_itemId_row0" name="sample_itemId" type="hidden" />
									<input id="sample_itemTypeId_row0" name="sample_itemTypeId" type="hidden" />
									<input id="sample_itemName_row0" name="sample_itemName" type="text" />
									<!-- 
									<select id="sample_itemName_row0" name="sample_itemName"
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
									<select id="sample_itemType_row0" name="sample_itemType" style="width: 125;" >
										<option value="">请选择</option>
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
										onclick="deleteTbRow('itemTableSpecial','sampleItemTable',row0);calculateAll();" />
								</td>
							</tr>
						</table>
						<!-- 
						<select id="sItemType" name="sItemType" style="display: none;">
							<logic:notEmpty name="item_type_list">
								<logic:iterate id="oneItemType" name="item_type_list">
									<option value="<bean:write property="typename" name="oneItemType" />"
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
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						工程费用合计（元）：
					</td>
					<td class="tdl" colspan="3">
						<html:text property="totalFee" styleId="totalFee" styleClass="inputtext" />
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan="4" class="tdl" style="padding:10px">
						<apptag:materialselect label="使用材料" materialUseType="Use"></apptag:materialselect>
					</td>
				</tr>
				<tr class=trcolor>
			 		<td colspan="4" class="tdl" style="padding:10px">					 			
		 				<apptag:materialselect label="回收材料" materialUseType="Recycle" ></apptag:materialselect>
			 		</td>
			 	</tr>
			 	<tr class=trcolor>
					<td class="tdr">
						材料费用合计（元）：
					</td>
					<td class="tdl" colspan="3">
						<html:text property="mtotalFee" styleId="mtotalFee" styleClass="inputtext" />
					</td>
				</tr>
		 		<tr class=trcolor>
					<td class="tdr">
						附件：
					</td>
					<td class="tdl" colspan=3>
						<apptag:upload state="add" cssClass=""/>
					</td>
				</tr>
				<tr class=trcolor>
					<apptag:approverselect label="审核人" inputName="approverId" spanId="approverSpan" inputType="radio" notAllowName="reader"/>
				</tr>
				<tr class=trcolor>
					<apptag:approverselect label="抄送人" inputName="reader" spanId="readerSpan" notAllowName="approverId" />
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
						<html:reset property="action" styleClass="button">重置</html:reset>
					</td>
				</tr>
				</table>
			</html:form>
		</table>
	</body>
</html>
