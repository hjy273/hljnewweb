<%@include file="/common/header.jsp"%>
<script language="javascript">
<!--
function toGetBack(){
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
			��ѯ������Ϣ
		</center>
		<hr>
	</div>
</div>
<html:form method="Post"
	action="/countyinfoAction.do?method=queryCounty">
	<template:formTable contentwidth="200" namewidth="200">
		<template:formTr name="��������">
			<html:text property="town" styleClass="inputtext" style="width:200"
				maxlength="45" />
		</template:formTr>
		<template:formTr name="��������" isOdd="false">
			<apptag:setSelectOptions valueName="parentRegionCellection"
				tableName="region" columnName1="regionname" region="true"
				columnName2="regionid" order="regionid"
				condition="substr(REGIONID,3,4) != '1111' " />
			<html:select property="regionid" styleClass="inputtext"
				style="width:200">
				<html:option value="">����</html:option>
				<html:options collection="parentRegionCellection" property="value"
					labelProperty="label" />
			</html:select>
		</template:formTr>
		<template:formSubmit>
			<td>
				<html:submit styleClass="button">��ѯ</html:submit>
			</td>
			<td>
				<html:reset styleClass="button">����</html:reset>
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
