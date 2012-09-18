<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script language="javascript" src="${ctx}/js/prototype.js" type=""></script>
<script language="javascript" type="text/javascript">
var regx=/^\d{11}$/;
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
function isValidForm() {
	if(document.contractorBean.contractorName.value==""||document.contractorBean.contractorName.value.trim()==""){
		alert("单位简称不能为空或者空格!! ");
		return false;
	}
	var strRemark = document.contractorBean.contractorName.value;
    if (strRemark.length>100){
      alert("内容过长，单位简称只能输入100个字符");
      document.contractorBean.contractorName.focus();
      return false;
    }
	if(document.contractorBean.alias.value==""||document.contractorBean.alias.value.trim()==""){
		alert("单位全称不能为空或者空格!! ");
		return false;
	}
	var count=0;
	var inputs = document.getElementsByName('resourcesId');
	for(var i=0;i<inputs.length;i++){
		if(inputs[i].checked==true){
			count ++;
		}
	}
	if(count<=0){
		alert("必须选择合作专业");
		return false;
	}
	if(document.contractorBean.organizationType.value=="1"){
		if(document.contractorBean.legalPerson.value==""||document.contractorBean.legalPerson.value.trim()==""){
			alert("法人代表不能为空或者空格!! ");
			return false;
		}
		if(document.contractorBean.recordDate.value==""||document.contractorBean.recordDate.value.trim()==""){
			alert("登记日期不能为空或者空格!! ");
			return false;
		}
		if(document.contractorBean.capital.value==""||document.contractorBean.capital.value.trim()==""){
			alert("注册资金不能为空或者空格!! ");
			return false;
		}
		if(document.contractorBean.remark.value==""||document.contractorBean.remark.value.trim()==""){
			alert("单位简介不能为空或者空格!! ");
			return false;
		}
	}
    if(document.contractorBean.address.value==""||document.contractorBean.address.value.trim()==""){
		alert("单位地址不能为空或者空格!! ");
		return false;
	}
    if(document.contractorBean.linkmanInfo.value==""||document.contractorBean.linkmanInfo.value.trim()==""){
		alert("联系人不能为空或者空格!! ");
		return false;
	}
     if(document.contractorBean.principalInfo.value==""||document.contractorBean.principalInfo.value.trim()==""){
		alert("负责人A不能为空或者空格!! ");
		return false;
	}
     if(document.contractorBean.tel.value==""||document.contractorBean.tel.value.trim()==""){
		alert("联系电话不能为空或者空格!! ");
		return false;
	}
     if(!regx.test(document.contractorBean.tel.value)){
		alert("联系电话必须为11位手机号码!! ");
		return false;
	}
     if(document.contractorBean.workPhone.value==""||document.contractorBean.workPhone.value.trim()==""){
		alert("办公电话不能为空或者空格!! ");
		return false;
	}
     if(document.contractorBean.fax.value==""||document.contractorBean.fax.value.trim()==""){
		alert("传真不能为空或者空格!! ");
		return false;
	}
     if(document.contractorBean.email.value==""||document.contractorBean.email.value.trim()==""){
		alert("Email不能为空或者空格!! ");
		return false;
	}
    strRemark = document.contractorBean.remark.value;
    if (strRemark.length>100){
      alert("内容过长，单位简介只能输入100个字符");
      document.contractorBean.remark.focus();
      return false;
    }
	if(judgeExist()){
    	alert("存在重名的代维单位！");
    	return false;
    }

	return true;
}
    	judgeExist=function(){
			var flag=false;
			var contractorName=contractorBean.contractorName.value;
			var queryString="contractorName="+contractorName+"&contractorID=-1&rnd="+Math.random();
			var actionUrl="${ctx}/contractorAction.do?method=judgeContractorExist&&"+queryString;
			new Ajax.Request(actionUrl, 
				{
					method:"post", 
					onSuccess:function (transport) {
						if(transport.responseText=='1'){
							flag=true;
						}
					}, 
					asynchronous:false
				}
			);
			return flag;
		}

function getDep(){

	var depV = contractorBean.regionid.value;
	var URL = "getSelectForCon.jsp?depType=2&selectname=parentcontractorid&regionid=" + depV;

	hiddenInfoFrame.location.replace(URL);
}
function addGoBack() {
   location.href = "${ctx}/contractorAction.do?method=queryContractor&contractorName=&linkmanInfo=";
}

function showCompanyInfoTb(value){
	if(value=='1'){
		document.getElementById("companyInfoTb").style.display="";
	}else{
		document.getElementById("companyInfoTb").style.display="none";
	}
}
</script>

<body>
	<template:titile value="增加代维单位" />
	<html:form onsubmit="return isValidForm()" method="Post"
		action="/contractorAction.do?method=addContractor">
		<table width="720" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<!-- 基础信息 -->
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					单位简称：
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="contractorName" styleClass="inputtext"
						style="width:160" />
					<font color="#FF0000">*</font>
				</td>
				<td class="tdulleft" style="width: 15%">
					单位全称：
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="alias" styleClass="inputtext"
						style="width:160" />
					<font color="#FF0000">*</font>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					单位类型：
				</td>
				<td class="tdulright" style="width: 35%">
					
					<html:select property="depttype" styleClass="inputtext"
						style="width:160">
						<html:option value="2">代维公司</html:option>
					</html:select>
				</td>
				<td class="tdulleft" style="width: 15%">
					上级单位：
				</td>
				<td class="tdulright" style="width: 35%">
						<apptag:setSelectOptions valueName="parentcontractorCollection"
							tableName="contractorinfo" region="true"
							columnName1="contractorname" columnName2="contractorid" />
						<html:select property="parentcontractorid" styleClass="inputtext"
							style="width:160">
							<html:option value="0">无  </html:option>
							<html:options collection="parentcontractorCollection"
								property="value" labelProperty="label" />
						</html:select>
					<font color="#FF0000">*</font>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					组织类型：
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="organizationType" class="inputtext"
						style="width: 160" onchange="showCompanyInfoTb(this.value);">
						<option value="1">
							独立法人
						</option>
						<option value="2">
							分支机构
						</option>
					</select>
					<font color="#FF0000">*</font>
				</td>
				<td class="tdulleft" style="width: 15%">
					所属区域：
				</td>
				<td class="tdulright" style="width: 35%">
					<c:if test="${LOGIN_USER.type=='11'}">
						<apptag:setSelectOptions valueName="parentRegionCellection"
							tableName="region" columnName1="regionname" region="true"
							columnName2="regionid" order="regionid" />
						<html:select property="regionid" styleClass="inputtext"
							style="width:160">
							<html:options collection="parentRegionCellection"
								property="value" labelProperty="label" />
						</html:select>
					</c:if>
					<c:if test="${LOGIN_USER.type=='12'}">
						<html:select property="regionid" styleClass="inputtext"
							style="width:160">
							<html:option value="${LOGIN_USER.regionID}"></html:option>
						</html:select>
					</c:if>
					<font color="#FF0000">*</font>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					合作专业：
				</td>
				<td class="tdulright" style="width: 85%" colspan="3">
					<c:forEach var="res" items="${resources}">
						<html:checkbox property="resourcesId" value="${res.code}">${res.resourcename}</html:checkbox>
					</c:forEach>
					<div style="color: red">
						* 不论创建的是监理还是代维单位，都要选择负责的合作专业
					</div>
				</td>
			</tr>
			<!-- 公司信息 -->
			<tbody id="companyInfoTb">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						法人代表：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="legalPerson" styleClass="inputtext"
							style="width:160" />
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%">
						登记日期：
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="recordDate" styleClass="inputtext"
							style="width:160" readonly="true"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate:'%y-%M-%d'})" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						注册资金：
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<html:text property="capital" styleClass="inputtext"
							style="width:160" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						单位简介：
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<textarea cols="5" rows="2" name="remark" style="width: 280;"
							class="textarea"></textarea>
						<font color="#FF0000">*</font>
					</td>
				</tr>
			</tbody>
			<!-- 通讯信息 -->
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					单位地址：
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="address" styleClass="inputtext"
						style="width:160" />
					<font color="#FF0000">*</font>
				</td>
				<td class="tdulleft" style="width: 15%">
					邮编：
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="postcode" styleClass="inputtext"
						style="width:160" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					联 系 人：
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="linkmanInfo" styleClass="inputtext"
						style="width:160" />
					<font color="#FF0000">*</font>
				</td>
				<td class="tdulleft" style="width: 15%">
					联系电话：
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="tel" styleClass="inputtext" style="width:160" />
					<font color="#FF0000">*</font>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					负责人A：
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="principalInfo" styleClass="inputtext"
						style="width:160" />
					<font color="#FF0000">*</font>
				</td>
				<td class="tdulleft" style="width: 15%">
					负责人B：
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="principalB" styleClass="inputtext"
						style="width:160" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					办公电话：
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="workPhone" styleClass="inputtext"
						style="width:160" />
					<font color="#FF0000">*</font>
				</td>
				<td class="tdulleft" style="width: 15%">
					传真：
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="fax" styleClass="inputtext" style="width:160" />
					<font color="#FF0000">*</font>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					Email：
				</td>
				<td class="tdulright" style="width: 85%" colspan="3">
					<html:text property="email" styleClass="inputtext"
						style="width:160" />
					<font color="#FF0000">*</font>
				</td>
			</tr>
			<template:formSubmit>
				<td>
					<html:submit styleClass="button">增加</html:submit>
				</td>
				<td>
					<html:reset styleClass="button">取消</html:reset>
				</td>

			</template:formSubmit>
		</table>
	</html:form>
	<iframe name="hiddenInfoFrame" style="display: none"></iframe>
</body>
