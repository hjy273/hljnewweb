<%@page import="com.cabletech.baseinfo.beans.*"%>
<%@include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/js/validate.js"></script>
<script language="javascript">
<!--
function isValidForm() {

	if(document.projectCountyInfoBean.town.value==""){
		alert("�������Ʋ���Ϊ��!! ");
       document.projectCountyInfoBean.town.focus();
		return false;
	}
    if(jQuery.trim(document.projectCountyInfoBean.town.value)==""){
		alert("�������Ʋ���Ϊ�ո�!! ");
       document.projectCountyInfoBean.town.focus();
		return false;
	}
	var str = document.projectCountyInfoBean.town.value;
	var length = str.getGBLength();
	if(length>40){
	alert("���������������20�����ֻ���40���ַ���");
	document.projectCountyInfoBean.town.focus();
	return false;
}
	var strtext = document.projectCountyInfoBean.remark.value;
	var lengthtext = strtext.getGBLength();
	if(lengthtext>200){
	alert("��ע�������100�����ֻ���200���ַ���");
	document.projectCountyInfoBean.remark.focus();
    return false;
}
	return true;
}

function toGetBack() {
        	try{
            	location.href = "${ctx}/project/county_info.do?method=queryCounty&countyName=";
                return true;
            }
            catch(e){
            	alert(e);
            }
        }

//-->
</script>
<div id="main">
	<div class="title2">
		<center>
			�޸�������Ϣ
		</center>
		<hr>
	</div>
	<html:form onsubmit="return isValidForm()" method="Post"
		action="/project/county_info.do?method=updateCounty">
		<template:formTable contentwidth="200" namewidth="200">
			<html:hidden property="id" />
			<template:formTr name="��������">
				<html:text property="town" name="countyinfoBean" styleClass="inputtext" style="width:200"
					maxlength="40" />
			</template:formTr>
			<template:formTr name="��������" isOdd="false">
				<apptag:setSelectOptions valueName="parentRegionCellection"
					tableName="region" columnName1="regionname" region="true"
					columnName2="regionid" order="regionid"
					condition="substr(REGIONID,3,4) != '1111' " />
				<html:select property="regionid" name="countyinfoBean" styleClass="inputtext"
					style="width:200">
					<html:options collection="parentRegionCellection" property="value"
						labelProperty="label" />
				</html:select>
			</template:formTr>
			<template:formTr name="��ע">
				<html:textarea property="remark" name="countyinfoBean" cols="10" rows="5"
					style="width:200;" styleClass="textarea" id="remark"></html:textarea>
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:submit styleClass="button">����</html:submit>
				</td>
				<td>
					<input type="button" class="button"
						onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"
						value="����">
				</td>
			</template:formSubmit>
		</template:formTable>
	</html:form>