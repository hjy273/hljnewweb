<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<script type="text/javascript" src="${ctx}/js/validate.js"></script>


<%
    UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
    String strRegion = userinfo.getRegionID();
%>
<script language="javascript" type="">
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
	var str = document.forms[0].town.value;
	var length = str.getGBLength();
	if(length>40){
	alert("���������������20�����ֻ���40���ַ���");
	document.forms[0].town.focus();
	return false;
}
	var strtext = document.forms[0].remark.value;
	var lengthtext = strtext.getGBLength();
	if(lengthtext>200){
	alert("��ע�������100�����ֻ���200���ַ���");
	document.forms[0].remark.focus();
    return false;
}
	return true;
}

//-->
</script>

<body>
	<div class="title2">
		<center>
			���������Ϣ
		</center>
	</div>
	<html:form onsubmit="return isValidForm()" method="Post"
		action="/countyinfoAction.do?method=addCounty">
		<template:formTable contentwidth="220" namewidth="220">
			<template:formTr name="��������">
				<html:text property="town" styleClass="inputtext" style="width:200"
					maxlength="40" />
			</template:formTr>
			<template:formTr name="��������" isOdd="false">
				<apptag:setSelectOptions valueName="parentRegionCellection"
					tableName="region" columnName1="regionname" columnName2="regionid"
					region="true" order="regionid"
					condition="substr(REGIONID,3,4) != '1111' " />
				<html:select property="regionid" styleClass="inputtext"
					style="width:200">
					<html:options collection="parentRegionCellection" property="value"
						labelProperty="label" />
				</html:select>
			</template:formTr>
			<template:formTr name="��ע" isOdd="false">
				<textarea cols="10" rows="5" name="remark" style="width: 200;"
					class="textarea" id="remark"></textarea>
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:submit styleClass="button">����</html:submit>
				</td>
				<td>
					<html:reset styleClass="button">����</html:reset>
				</td>

			</template:formSubmit>
		</template:formTable>
	</html:form>
	<iframe name="hiddenInfoFrame" style="display: none"></iframe>
</body>
