<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script language="javascript" src="${ctx}/js/prototype.js" type=""></script>
<script language="javascript" type="text/javascript">
var regx=/^\d{11}$/;
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
function isValidForm() {
	if(document.contractorBean.contractorName.value==""||document.contractorBean.contractorName.value.trim()==""){
		alert("��λ��Ʋ���Ϊ�ջ��߿ո�!! ");
		return false;
	}
	var strRemark = document.contractorBean.contractorName.value;
    if (strRemark.length>100){
      alert("���ݹ�������λ���ֻ������100���ַ�");
      document.contractorBean.contractorName.focus();
      return false;
    }
	if(document.contractorBean.alias.value==""||document.contractorBean.alias.value.trim()==""){
		alert("��λȫ�Ʋ���Ϊ�ջ��߿ո�!! ");
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
		alert("����ѡ�����רҵ");
		return false;
	}
	if(document.contractorBean.organizationType.value=="1"){
		if(document.contractorBean.legalPerson.value==""||document.contractorBean.legalPerson.value.trim()==""){
			alert("���˴�����Ϊ�ջ��߿ո�!! ");
			return false;
		}
		if(document.contractorBean.recordDate.value==""||document.contractorBean.recordDate.value.trim()==""){
			alert("�Ǽ����ڲ���Ϊ�ջ��߿ո�!! ");
			return false;
		}
		if(document.contractorBean.capital.value==""||document.contractorBean.capital.value.trim()==""){
			alert("ע���ʽ���Ϊ�ջ��߿ո�!! ");
			return false;
		}
		if(document.contractorBean.remark.value==""||document.contractorBean.remark.value.trim()==""){
			alert("��λ��鲻��Ϊ�ջ��߿ո�!! ");
			return false;
		}
	}
    if(document.contractorBean.address.value==""||document.contractorBean.address.value.trim()==""){
		alert("��λ��ַ����Ϊ�ջ��߿ո�!! ");
		return false;
	}
    if(document.contractorBean.linkmanInfo.value==""||document.contractorBean.linkmanInfo.value.trim()==""){
		alert("��ϵ�˲���Ϊ�ջ��߿ո�!! ");
		return false;
	}
     if(document.contractorBean.principalInfo.value==""||document.contractorBean.principalInfo.value.trim()==""){
		alert("������A����Ϊ�ջ��߿ո�!! ");
		return false;
	}
     if(document.contractorBean.tel.value==""||document.contractorBean.tel.value.trim()==""){
		alert("��ϵ�绰����Ϊ�ջ��߿ո�!! ");
		return false;
	}
     if(!regx.test(document.contractorBean.tel.value)){
		alert("��ϵ�绰����Ϊ11λ�ֻ�����!! ");
		return false;
	}
     if(document.contractorBean.workPhone.value==""||document.contractorBean.workPhone.value.trim()==""){
		alert("�칫�绰����Ϊ�ջ��߿ո�!! ");
		return false;
	}
     if(document.contractorBean.fax.value==""||document.contractorBean.fax.value.trim()==""){
		alert("���治��Ϊ�ջ��߿ո�!! ");
		return false;
	}
     if(document.contractorBean.email.value==""||document.contractorBean.email.value.trim()==""){
		alert("Email����Ϊ�ջ��߿ո�!! ");
		return false;
	}
    strRemark = document.contractorBean.remark.value;
    if (strRemark.length>100){
      alert("���ݹ�������λ���ֻ������100���ַ�");
      document.contractorBean.remark.focus();
      return false;
    }
	if(judgeExist()){
    	alert("���������Ĵ�ά��λ��");
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
	<template:titile value="���Ӵ�ά��λ" />
	<html:form onsubmit="return isValidForm()" method="Post"
		action="/contractorAction.do?method=addContractor">
		<table width="720" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<!-- ������Ϣ -->
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��λ��ƣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="contractorName" styleClass="inputtext"
						style="width:160" />
					<font color="#FF0000">*</font>
				</td>
				<td class="tdulleft" style="width: 15%">
					��λȫ�ƣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="alias" styleClass="inputtext"
						style="width:160" />
					<font color="#FF0000">*</font>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��λ���ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					
					<html:select property="depttype" styleClass="inputtext"
						style="width:160">
						<html:option value="2">��ά��˾</html:option>
					</html:select>
				</td>
				<td class="tdulleft" style="width: 15%">
					�ϼ���λ��
				</td>
				<td class="tdulright" style="width: 35%">
						<apptag:setSelectOptions valueName="parentcontractorCollection"
							tableName="contractorinfo" region="true"
							columnName1="contractorname" columnName2="contractorid" />
						<html:select property="parentcontractorid" styleClass="inputtext"
							style="width:160">
							<html:option value="0">��  </html:option>
							<html:options collection="parentcontractorCollection"
								property="value" labelProperty="label" />
						</html:select>
					<font color="#FF0000">*</font>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��֯���ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="organizationType" class="inputtext"
						style="width: 160" onchange="showCompanyInfoTb(this.value);">
						<option value="1">
							��������
						</option>
						<option value="2">
							��֧����
						</option>
					</select>
					<font color="#FF0000">*</font>
				</td>
				<td class="tdulleft" style="width: 15%">
					��������
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
					����רҵ��
				</td>
				<td class="tdulright" style="width: 85%" colspan="3">
					<c:forEach var="res" items="${resources}">
						<html:checkbox property="resourcesId" value="${res.code}">${res.resourcename}</html:checkbox>
					</c:forEach>
					<div style="color: red">
						* ���۴������Ǽ����Ǵ�ά��λ����Ҫѡ����ĺ���רҵ
					</div>
				</td>
			</tr>
			<!-- ��˾��Ϣ -->
			<tbody id="companyInfoTb">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���˴���
					</td>
					<td class="tdulright" style="width: 35%">
						<html:text property="legalPerson" styleClass="inputtext"
							style="width:160" />
						<font color="#FF0000">*</font>
					</td>
					<td class="tdulleft" style="width: 15%">
						�Ǽ����ڣ�
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
						ע���ʽ�
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<html:text property="capital" styleClass="inputtext"
							style="width:160" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��λ��飺
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<textarea cols="5" rows="2" name="remark" style="width: 280;"
							class="textarea"></textarea>
						<font color="#FF0000">*</font>
					</td>
				</tr>
			</tbody>
			<!-- ͨѶ��Ϣ -->
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��λ��ַ��
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="address" styleClass="inputtext"
						style="width:160" />
					<font color="#FF0000">*</font>
				</td>
				<td class="tdulleft" style="width: 15%">
					�ʱࣺ
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="postcode" styleClass="inputtext"
						style="width:160" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�� ϵ �ˣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="linkmanInfo" styleClass="inputtext"
						style="width:160" />
					<font color="#FF0000">*</font>
				</td>
				<td class="tdulleft" style="width: 15%">
					��ϵ�绰��
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="tel" styleClass="inputtext" style="width:160" />
					<font color="#FF0000">*</font>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					������A��
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="principalInfo" styleClass="inputtext"
						style="width:160" />
					<font color="#FF0000">*</font>
				</td>
				<td class="tdulleft" style="width: 15%">
					������B��
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="principalB" styleClass="inputtext"
						style="width:160" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�칫�绰��
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="workPhone" styleClass="inputtext"
						style="width:160" />
					<font color="#FF0000">*</font>
				</td>
				<td class="tdulleft" style="width: 15%">
					���棺
				</td>
				<td class="tdulright" style="width: 35%">
					<html:text property="fax" styleClass="inputtext" style="width:160" />
					<font color="#FF0000">*</font>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					Email��
				</td>
				<td class="tdulright" style="width: 85%" colspan="3">
					<html:text property="email" styleClass="inputtext"
						style="width:160" />
					<font color="#FF0000">*</font>
				</td>
			</tr>
			<template:formSubmit>
				<td>
					<html:submit styleClass="button">����</html:submit>
				</td>
				<td>
					<html:reset styleClass="button">ȡ��</html:reset>
				</td>

			</template:formSubmit>
		</table>
	</html:form>
	<iframe name="hiddenInfoFrame" style="display: none"></iframe>
</body>
