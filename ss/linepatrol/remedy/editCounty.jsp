<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="countyinfoBean"
	class="com.cabletech.linepatrol.remedy.beans.CountyInfoBean"
	scope="request" />
<%@include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/js/validate.js"></script>
<script language="javascript">
<!--
function isValidForm() {

	if(document.countyinfoBean.town.value==""){
		alert("�������Ʋ���Ϊ��!! ");
       document.countyinfoBean.town.focus();
		return false;
	}
    if(trim(document.countyinfoBean.town.value)==""){
		alert("�������Ʋ���Ϊ�ո�!! ");
       document.countyinfoBean.town.focus();
		return false;
	}
	var str = document.countyinfoBean.town.value;
	var length = str.getGBLength();
	if(length>40){
	alert("���������������20�����ֻ���40���ַ���");
	document.countyinfoBean.town.focus();
	return false;
}
	var strtext = document.countyinfoBean.remark.value;
	var lengthtext = strtext.getGBLength();
	if(lengthtext>200){
	alert("��ע�������100�����ֻ���200���ַ���");
	document.countyinfoBean.remark.focus();
    return false;
}
	return true;
}

function toGetBack() {
        	try{
            	location.href = "${ctx}/countyinfoAction.do?method=queryCounty&countyName=";
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
	<apptag:checkpower thirdmould="70404" ishead="5">
		<jsp:forward page="/globinfo/powererror.jsp" />
	</apptag:checkpower>
	<html:form onsubmit="return isValidForm()" method="Post"
		action="/countyinfoAction.do?method=updateCounty">
		<template:formTable contentwidth="200" namewidth="200">
			<html:hidden property="id" />
			<template:formTr name="��������">
				<html:text property="town" styleClass="inputtext" style="width:200"
					maxlength="40" />
			</template:formTr>
			<template:formTr name="��������" isOdd="false">
				<apptag:setSelectOptions valueName="parentRegionCellection"
					tableName="region" columnName1="regionname" region="true"
					columnName2="regionid" order="regionid"
					condition="substr(REGIONID,3,4) != '1111' " />
				<html:select property="regionid" styleClass="inputtext"
					style="width:200">
					<html:options collection="parentRegionCellection" property="value"
						labelProperty="label" />
				</html:select>
			</template:formTr>
			<template:formTr name="��ע">
				<html:textarea property="remark" cols="10" rows="5"
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