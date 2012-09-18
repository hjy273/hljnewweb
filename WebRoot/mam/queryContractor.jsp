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
<template:titile value="查询代维单位" />
<html:form method="Post"
	action="/contractorAction.do?method=queryContractor">
	<template:formTable contentwidth="220" namewidth="220">
		<template:formTr name="单位名称">
			<html:text property="contractorName" styleClass="inputtext"
				style="width:160" maxlength="45" />
		</template:formTr>
		<template:formTr name="组织类型">
			<select name="organizationType" class="inputtext" style="width: 160">
				<option value="">
					不限
				</option>
				<option value="1">
					独立法人
				</option>
				<option value="2">
					分支机构
				</option>
			</select>
		</template:formTr>
		<template:formTr name="所属区域">
			<apptag:setSelectOptions valueName="parentRegionCellection"
				tableName="region" columnName1="regionname" region="true"
				columnName2="regionid" order="regionid" />
			<html:select property="regionid" styleClass="inputtext"
				style="width:160">
				<html:option value="">不限</html:option>
				<html:options collection="parentRegionCellection" property="value"
					labelProperty="label" />
			</html:select>
		</template:formTr>
		<template:formTr name="上级公司">
			<apptag:setSelectOptions valueName="parentcontractorCollection"
				tableName="contractorinfo" region="true"
				columnName1="contractorname" columnName2="contractorid" />
			<html:select property="parentcontractorid" styleClass="inputtext"
				style="width:160">
				<html:option value="0">不限</html:option>
				<html:option value="0">无  </html:option>
				<html:options collection="parentcontractorCollection"
					property="value" labelProperty="label" />
			</html:select>
		</template:formTr>
		<template:formTr name="合作专业">
			<c:forEach var="res" items="${resources}">
				<html:checkbox property="resourcesId" value="${res.code}">${res.resourcename}</html:checkbox>
			</c:forEach>
		</template:formTr>
		<template:formSubmit>
			<td>
				<html:submit styleClass="button">查询</html:submit>
			</td>
			<td>
				<html:reset styleClass="button">取消</html:reset>
			</td>
			<td>
				<html:button property="action" styleClass="button"
					onclick="addGoBack()">返回</html:button>
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display: none"></iframe>
