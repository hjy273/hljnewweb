<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title></title>
		<script type="" language="javascript">
			function check() {
				var regionid = document.all.item("contractorid").value
				if(regionid == '' || regionid == null) {
					alert("��ѡ���ѯͳ�ƵĴ�ά��˾");
					return false;
				}
				return true;
			}
    	</script>
	</head>
	<body>
		<template:titile value="����ά��˾����ͳ�Ʋ�ѯ" />
		<html:form
			action="/Patrolanalysis.do?method=doContractorQuery&type=${businesstype}"
			styleId="queryForm2" onsubmit="return check();">
			<html:hidden property="timeFlgStr" value="quarter"/>
			<template:formTable namewidth="200" contentwidth="200">
				<template:formTr name="��ά��˾">
					<select name="contractorid" class="inputtext" style="width: 220px"
						id="contractorid">
						<option value=""></option>
						<logic:present name="contractor_list">
							<logic:iterate id="oneContractor" name="contractor_list">
								<option
									value="<bean:write name="oneContractor" property="contractorid"/>">
									<bean:write name="oneContractor" property="contractorname" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
				</template:formTr>				
				<template:formTr name="ͳ�����">
					<html:text property="startdate" readonly="true"
						styleClass="inputtext Wdate" style="width: 220px" 
						onfocus="WdatePicker({dateFmt:'yyyy',vel:'starttime',realDateFmt:'yyyy'})" />
						<input id="starttime" name="starttime" type="hidden" />
				</template:formTr>
				<template:formTr name="ͳ�Ƽ���">
				<select id="quarter" name="quarter" style="width:220">
					<option value="1">��һ����</option>
					<option value="2">�ڶ�����</option>
					<option value="3">��������</option>
					<option value="4">���ļ���</option>
				</select>
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
