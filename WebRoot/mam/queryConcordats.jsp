<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@include file="/mam/concordatJS.jsp"%>
<style>
<!--
#error{
	width:220px;
	margin-left:40%;
	background-color: #FDFDDD;
	border: 1px solid #FC6;
	z-index: 1000px;
	position:absolute;
}
-->
</style>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
<script language="javascript" type="">
<!--
var validform=function(){
	if($('begin').value!='' && $('end').value!='' && $('begin').value>$('end').value){
		pushMessage($('error'),null,'����ѡ���д���');
		return false;
	}
	return true;
}
//-->
</script>
<template:titile value="��ѯ��ͬ" />
<div id="error" style="display:none"></div>
<html:form method="Post" onsubmit="return validform();" action="/concordat.do?method=queryConcordats">
	<template:formTable contentwidth="300" namewidth="200">
		<template:formTr name="��ͬ���">
			<input name="cno" class="inputtext" style="width: 200" maxlength="8" />
		</template:formTr>
		<template:formTr name="��ͬ����">
			<html:text property="cname" styleClass="inputtext" style="width:200" maxlength="50" />
		</template:formTr>
		<template:formTr name="��ͬ����">
			<select name="ctypeid" class="inputtext" style="width: 200px">
				<option value="">ȫ��</option>
				<logic:present name="ctype_list">
					<logic:iterate id="onectype" name="ctype_list">
						<option value="<bean:write name="onectype" property="key"/>">
							<bean:write name="onectype" property="value" />
						</option>
					</logic:iterate>
				</logic:present>
			</select>
		</template:formTr>		
		<template:formTr name="��ͬ��Ч��">
			�ӣ�<html:text property="begindate" styleId="begin" styleClass="inputtext" readonly="true" style="width:73" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />		
			����<html:text property="enddate" styleId="end" styleClass="inputtext" readonly="true" style="width:73" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
		</template:formTr>
		<logic:present name="LOGIN_USER">
		<logic:equal value="11" name="LOGIN_USER" property="type">
		<template:formTr name="��������">
			<select name="regionid" class="inputtext" style="width: 200px">
				<logic:present name="region_list">
					<logic:iterate id="oneRegion" name="region_list">
						<option value="<bean:write name="oneRegion" property="regionid"/>">
							<bean:write name="oneRegion" property="regionname" />
						</option>
					</logic:iterate>
				</logic:present>
			</select>
		</template:formTr>
		</logic:equal>
		</logic:present>
		<template:formTr name="ά������">
			<html:text property="patrolregion" styleClass="inputtext" style="width:200" maxlength="50" />
		</template:formTr>	
						
		<template:formSubmit>
			<td colspan="2">
				<html:submit styleClass="button">��ѯ</html:submit>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:reset styleClass="button">ȡ��</html:reset>
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>