<%@include file="/common/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�����������</title>
		
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
		</script>
	</head>
	<body>
		<br>
		<template:titile value="��ӹ�������" />
		<table width="98%" align="center" border="1" cellpadding="0" cellspacing="0" style="border-collpase:collapse;">
			<html:form action="/project/remedy_apply.do?method=insertApply" styleId="addForm" enctype="multipart/form-data">
				<input name="remedyCode" value="${generate_id}" type="hidden" />
				<input name="contractorId" value="${sessionScope.LOGIN_USER.deptID }" type="hidden" />
				<input name="regionId" value="${sessionScope.LOGIN_USER.regionID}" type="hidden" />
				<input name="creator" value="${sessionScope.LOGIN_USER.userID}" type="hidden" />
				<input name="isSubmited" value="1" type="hidden" />
							<tr class=trcolor>
								<td class="tdr">
									��ţ�
								</td>
								<td class="tdl">
									${generate_id}
								</td>
								<td class="tdr">
									ά����λ��
								</td>
								<td class="tdl">
									${sessionScope.LOGIN_USER.deptName}
								</td>
							</tr>
							<tr class=trcolor>
								<td class="tdr">
									��Ŀ���ƣ�
								</td>
								<td class="tdl">
									<html:text property="projectName" styleClass="inputtext" style="width:215;" maxlength="200" onblur="judgeRemedyApplyExist(document.forms['addForm'],true);" />
									<span id="exist_remedy_apply_span"></span>
								</td>
								<td class="tdr">
									�����ص㣺
								</td>
								<td class="tdl">
									<html:text property="remedyAddress" styleClass="inputtext" style="width:215;" maxlength="50" />
								</td>
							</tr>
							<tr class=trcolor>
								<td class="tdr">
									����ʱ�䣺
								</td>
								<td class="tdl" colspan=3>
									<html:text property="remedyDate" styleClass="Wdate" style="width:215;" readonly="true"
										onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
								</td>
							</tr>
							<tr class=trcolor>
								<td class="tdr">
									ԭ��˵����
								</td>
								<td class="tdl">
									<html:textarea property="remedyReason" styleClass="inputtextarea" style="width:215px;" />
								</td>
								<td class="tdr">
									��������
								</td>
								<td class="tdl">
									<html:textarea property="remedySolve" styleClass="inputtextarea" style="width:215px;" />
								</td>
							</tr>
				<tr class=trcolor>
					<td colspan=4 class="tdl" style="padding:10px">
						����������
						<input id="btnAddItem" name="btnAddItem" value="���" type="button"
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
									���
								</td>
								 -->
								<td style="text-align: center; width: 25%">
									��Ŀ����
								</td>
								<!-- td rowspan="2" style="text-align: center; width: 15%">
									��λ
								</td>
								<td rowspan="2" style="text-align: center; width: 20%">
									������
								</td-->
								<td style="text-align: center; width: 15%">
									����
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
									<select id="sample_itemType_row0" name="sample_itemType" style="width: 125;" >
										<option value="">��ѡ��</option>
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
						���̷��úϼƣ�Ԫ����
					</td>
					<td class="tdl" colspan="3">
						<html:text property="totalFee" styleId="totalFee" styleClass="inputtext" />
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan="4" class="tdl" style="padding:10px">
						<apptag:materialselect label="ʹ�ò���" materialUseType="Use"></apptag:materialselect>
					</td>
				</tr>
				<tr class=trcolor>
			 		<td colspan="4" class="tdl" style="padding:10px">					 			
		 				<apptag:materialselect label="���ղ���" materialUseType="Recycle" ></apptag:materialselect>
			 		</td>
			 	</tr>
			 	<tr class=trcolor>
					<td class="tdr">
						���Ϸ��úϼƣ�Ԫ����
					</td>
					<td class="tdl" colspan="3">
						<html:text property="mtotalFee" styleId="mtotalFee" styleClass="inputtext" />
					</td>
				</tr>
		 		<tr class=trcolor>
					<td class="tdr">
						������
					</td>
					<td class="tdl" colspan=3>
						<apptag:upload state="add" cssClass=""/>
					</td>
				</tr>
				<tr class=trcolor>
					<apptag:approverselect label="�����" inputName="approverId" spanId="approverSpan" inputType="radio" notAllowName="reader"/>
				</tr>
				<tr class=trcolor>
					<apptag:approverselect label="������" inputName="reader" spanId="readerSpan" notAllowName="approverId" />
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
						<html:reset property="action" styleClass="button">����</html:reset>
					</td>
				</tr>
				</table>
			</html:form>
		</table>
	</body>
</html>
