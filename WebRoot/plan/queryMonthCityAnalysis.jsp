<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title></title>
		<script type="text/javascript" language="javascript">
			function check() {
				var regionid = jQuery("#regionid").val();
				if(regionid == '' || regionid == null) {
					alert("��ѡ���ѯͳ�Ƶĵ���");
					return false;
				}
				return true;
			}

    	</script>
	</head>
	<body>
		<template:titile value="��������ͳ�Ʋ�ѯ" />
		<html:form
			action="/Patrolanalysis.do?method=doCityQuery&type=${businesstype}"
			styleId="queryForm2" onsubmit="return check();">
			<html:hidden property="timeFlgStr" value="month"/>
			<template:formTable namewidth="200" contentwidth="200">
				<template:formTr name="����">
					<select name="regionid" class="inputtext" style="width: 220px"
						id="regionid">
						<option value=""></option>
						<logic:present name="city_list">
							<logic:iterate id="oneCity" name="city_list">
								<option
									value="<bean:write name="oneCity" property="regionid"/>">
									<bean:write name="oneCity" property="regionname" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
				</template:formTr>
				<template:formTr name="ͳ������">
					<html:text property="startdate" readonly="true"
						styleClass="inputtext Wdate" style="width: 220px"
						onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy��MM��',vel:'starttime',realDateFmt:'yyyy-MM-01'})" />
				    <input id="starttime" name="starttime" type="hidden" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">����</html:submit>
						<html:reset property="action" styleClass="button">ȡ��</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
