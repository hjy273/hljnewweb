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
		<title>人员详细信息</title>
	</head>
	<body>
		<logic:present name="personinfo">
			<template:titile value="人员详细信息" />
			<br />
			<table width="720" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<!-- 基础信息 -->
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						员工编号：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="employeeNum" />
					</td>
					<td class="tdulleft" style="width: 15%" rowspan="4">
						照片：
					</td>
					<td class="tdulright" style="width: 35%" rowspan="4">
						<bean:define name="personinfo" property="id" id="conperson_id" />
						<apptag:upload state="look" entityId="${conperson_id}"
							entityType="CONTRACTORPERSON"></apptag:upload>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						姓名：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="name" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						性别：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write property="sex" name="personinfo" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						所在代维单位：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="contractorid" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						民族：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="nation" />
					</td>
					<td class="tdulleft" style="width: 15%">
						身份证号码：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="identitycard" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						职称：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="technicalTitle" />
					</td>
					<td class="tdulleft" style="width: 15%">
						入职时间：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="enterTime"
							format="yyyy/MM/dd HH:mm:ss" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						常驻区域：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="residantarea" />
					</td>
					<td class="tdulleft" style="width: 15%">
						职务：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="jobinfo" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						主要工作职责：
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<bean:write name="personinfo" property="postOffice" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						手机号码：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="mobile" />
					</td>
					<td class="tdulleft" style="width: 15%">
						邮政编码：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="postalcode" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						固定电话号码：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="phone" />
					</td>
					<td class="tdulleft" style="width: 15%">
						E-mail邮箱：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="email" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						家庭住址：
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<bean:write name="personinfo" property="familyaddress" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						文化程度：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write property="culture" name="personinfo" />
					</td>
					<td class="tdulleft" style="width: 15%">
						最高学历获得时间：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="graduateDate"
							format="yyyy/MM/dd HH:mm:ss" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						毕业院校：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="academy" />
					</td>
					<td class="tdulleft" style="width: 15%">
						所学专业：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write name="personinfo" property="speciality" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						在岗否：
					</td>
					<td class="tdulright" style="width: 35%">
						<logic:equal value="0" property="conditions" name="personinfo">在岗</logic:equal>
						<logic:equal value="1" property="conditions" name="personinfo">离职</logic:equal>
					</td>
					<td class="tdulleft" style="width: 15%">
						离职时间：
					</td>
					<td class="tdulright" style="width: 35%">
						<bean:write property="leaveTime" name="personinfo"
							format="yyyy/MM/dd HH:mm:ss" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						工作经历：
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<bean:write name="personinfo" property="workrecord" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						备注：
					</td>
					<td class="tdulright" style="width: 85%" colspan="3">
						<bean:write name="personinfo" property="remark" />
					</td>
				</tr>
				<template:formSubmit>
					<td>
						<input type="button" class="button" onclick="addGoBack()"
							value="返回">
					</td>
				</template:formSubmit>
			</table>
		</logic:present>
	</body>
</html>


