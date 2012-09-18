<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="" language="javascript">
        function addGoBack()
        {
            try{
                location.href = "${ctx}/ConPersonAction.do?method=showConPerson";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
    </script>
		<title>��Ա��ϸ��Ϣ</title>
	</head>
	<body>
		<logic:present name="personinfo">
			<template:titile value="��Ա��ϸ��Ϣ" />
			<br />
			<table width="720" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<!-- ������Ϣ -->
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						Ա����ţ�
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="employeeNum" />
					</td>
					<td class="tdulleft" style="width: 15%" rowspan="4">
						��Ƭ��
					</td>
					<td class="tdulright" style="width: 35%" rowspan="4">
						<bean:define name="personinfo" property="id" id="conperson_id" />
						<apptag:upload state="look" entityId="${conperson_id}"
							entityType="CONTRACTORPERSON"></apptag:upload>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						������
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="name" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�Ա�
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write property="sex" name="personinfo" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���ڴ�ά��λ��
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="contractorid" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���壺
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="nation" />
					</td>
					<td class="tdulleft" style="width: 15%">
						���֤���룺
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="identitycard" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						ְ�ƣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="technicalTitle" />
					</td>
					<td class="tdulleft" style="width: 15%">
						��ְʱ�䣺
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="enterTime"
							format="yyyy/MM/dd HH:mm:ss" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��פ����
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="residantarea" />
					</td>
					<td class="tdulleft" style="width: 15%">
						ְ��
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="jobinfo" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��Ҫ����ְ��
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<bean:write name="personinfo" property="postOffice" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�ֻ����룺
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="mobile" />
					</td>
					<td class="tdulleft" style="width: 15%">
						�������룺
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="postalcode" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�̶��绰���룺
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="phone" />
					</td>
					<td class="tdulleft" style="width: 15%">
						E-mail���䣺
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="email" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��ͥסַ��
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<bean:write name="personinfo" property="familyaddress" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�Ļ��̶ȣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write property="culture" name="personinfo" />
					</td>
					<td class="tdulleft" style="width: 15%">
						���ѧ�����ʱ�䣺
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="graduateDate"
							format="yyyy/MM/dd HH:mm:ss" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��ҵԺУ��
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="academy" />
					</td>
					<td class="tdulleft" style="width: 15%">
						��ѧרҵ��
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="speciality" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�ڸڷ�
					</td>
					<td class="tdulright" style="width: 35%">
						<logic:equal value="0" property="conditions" name="personinfo">�ڸ�</logic:equal>
						<logic:equal value="1" property="conditions" name="personinfo">��ְ</logic:equal>
					</td>
					<td class="tdulleft" style="width: 15%">
						��ְʱ�䣺
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write property="leaveTime" name="personinfo"
							format="yyyy/MM/dd HH:mm:ss" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						����������
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<bean:write name="personinfo" property="workrecord" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��ע��
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<bean:write name="personinfo" property="remark" />
					</td>
				</tr>
				<template:formSubmit>
					<td>
						<input type="button" class="button" onclick="addGoBack()"
							value="����">
					</td>
				</template:formSubmit>
			</table>
		</logic:present>
	</body>
</html>


