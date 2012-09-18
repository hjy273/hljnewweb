<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.fsmanager.domainobjects.*"%>
<script language="javascript" type="">
function downloadFile() {
  location.href = "${ctx}/RouteInfoAction.do?method=downloadTemplet";
}

function showupload() {
    var objpart = document.getElementById("groupDivId");
    var objup = document.getElementById("upDivId");
    objpart.style.display="none";
    objup.style.display="block";
}

function noupload()
{
    var objpart = document.getElementById("groupDivId");
    var objup = document.getElementById("upDivId");

    objpart.style.display="block";
    objup.style.display="none";
}

function isValidForm() {
	var myform = document.forms[0]
	if(myform.routeName.value==""){
		alert("·�����Ʋ���Ϊ��!! ");
	    myform.routeName.focus();
		return false;
	}
    if(trim(myform.routeName.value)==""){
		alert("·�����Ʋ���Ϊ�ո�!! ");
        myform.routeName.focus();
		return false;
	}
	return true;
}

function addGoBack()
        {
           location.href = "${ctx}/RouteInfoAction.do?method=queryRouteInfo";
           return true;
        }

</script>
<div id="groupDivId">
	<template:titile value="���·����Ϣ" />
	<html:form onsubmit="return isValidForm()"  method="Post" action="/RouteInfoAction.do?method=addRouteInfo">
		<template:formTable contentwidth="220" namewidth="220">
			<template:formTr name="·������">
				<html:text property="routeName" styleClass="inputtext"
					style="width:160" maxlength="40" />
			</template:formTr>
			<template:formTr name="��������" isOdd="false">
				<apptag:setSelectOptions valueName="parentRegionCellection"
					tableName="region" columnName1="regionname" columnName2="regionid"
					region="true" order="regionid"
					condition="substr(REGIONID,3,4) != '1111' " />
				<html:select property="regionID" styleClass="inputtext"
					style="width:200">
					<html:options collection="parentRegionCellection" property="value"
						labelProperty="label" />
				</html:select>
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:submit styleClass="button">���</html:submit>
				</td>
				<td>
					<html:reset styleClass="button">ȡ��</html:reset>
				</td>
				<td>
					<html:button property="action" styleClass="button" styleId="upId"
						onclick="showupload()">����·����Ϣ</html:button>
				</td>
				<td>
					<html:button property="action" styleClass="button"
						onclick="downloadFile()">ģ������</html:button>
				</td>
				<td>
					<input type="button" value="����" class="button" onclick="addGoBack()"/>
				</td>

			</template:formSubmit>
		</template:formTable>
	</html:form>
	<iframe name="hiddenInfoFrame" style="display: none"></iframe>
</div>
<div id="upDivId" style="display: none">
	<html:form styleId="upform" action="/RouteInfoAction?method=upLoadShow"
		method="post" enctype="multipart/form-data">
		<table align="center" border="0" width="600" height="100%">
			<tr>
				<td valign="top" height="100%">
					<table align="center" border="0">
						<tr>
							<td align="left" height="50">
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<b>��ѡ��Ҫ�����Excel�ļ�:</b>
								<br />
							</td>
						</tr>
						<tr>
							<td>
								<html:file property="file" style="width:300px"
									styleClass="inputtext" />
							</td>
						</tr>
						<tr>
							<td align="center" valign="middle" height="60">
								<html:button styleClass="button" value="��������" property="sub"
									onclick="javascript:upform.submit()">�ύ</html:button>
								<html:button value="ȡ������" styleClass="button" property="action"
									onclick="noupload()" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</html:form>
</div>

