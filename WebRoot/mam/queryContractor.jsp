<%@ page language="java" contentType="text/html; charset=GBK"%><%@include
	file="/common/header.jsp"%>

<script language="javascript" type="">
<!--

function getDep(){

	var depV = contractorBean.regionid.value;
	var URL = "getSelectForCon.jsp?depType=2&selectname=parentcontractorid&regionid=" + depV;

	hiddenInfoFrame.location.replace(URL);
}
function addGoBack()
        {
        	try{
            	location.href = "${ctx}/contractorAction.do?method=queryContractor&contractorName=&linkmanInfo=";
                return true;
            }
            catch(e){
            	alert(e);
            }
        }
//-->
</script>
<template:titile value="��ѯ��ά��λ" />
<html:form method="Post"
	action="/contractorAction.do?method=queryContractor">
	<template:formTable contentwidth="220" namewidth="220">
		<template:formTr name="��λ����">
			<html:text property="contractorName" styleClass="inputtext"
				style="width:160" maxlength="45" />
		</template:formTr>
		<template:formTr name="��֯����">
			<select name="organizationType" class="inputtext" style="width: 160">
				<option value="">
					����
				</option>
				<option value="1">
					��������
				</option>
				<option value="2">
					��֧����
				</option>
			</select>
		</template:formTr>
		<template:formTr name="��������">
			<apptag:setSelectOptions valueName="parentRegionCellection"
				tableName="region" columnName1="regionname" region="true"
				columnName2="regionid" order="regionid" />
			<html:select property="regionid" styleClass="inputtext"
				style="width:160">
				<html:option value="">����</html:option>
				<html:options collection="parentRegionCellection" property="value"
					labelProperty="label" />
			</html:select>
		</template:formTr>
		<template:formTr name="�ϼ���˾">
			<apptag:setSelectOptions valueName="parentcontractorCollection"
				tableName="contractorinfo" region="true"
				columnName1="contractorname" columnName2="contractorid" />
			<html:select property="parentcontractorid" styleClass="inputtext"
				style="width:160">
				<html:option value="0">����</html:option>
				<html:option value="0">��  </html:option>
				<html:options collection="parentcontractorCollection"
					property="value" labelProperty="label" />
			</html:select>
		</template:formTr>
		<template:formTr name="����רҵ">
			<c:forEach var="res" items="${resources}">
				<html:checkbox property="resourcesId" value="${res.code}">${res.resourcename}</html:checkbox>
			</c:forEach>
		</template:formTr>
		<template:formSubmit>
			<td>
				<html:submit styleClass="button">��ѯ</html:submit>
			</td>
			<td>
				<html:reset styleClass="button">ȡ��</html:reset>
			</td>
			<td>
				<html:button property="action" styleClass="button"
					onclick="addGoBack()">����</html:button>
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display: none"></iframe>
