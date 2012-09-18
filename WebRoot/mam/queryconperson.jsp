<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>

<jsp:useBean id="selectForTag" class="com.cabletech.commons.tags.SelectForTag" scope="request"/>

<%
String condition="";
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
List groupCollection = selectForTag.getPatrolManInfo(userinfo);
request.setAttribute("pglist",groupCollection);
//市移动
if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
   condition = " WHERE regionid IN ('"+userinfo.getRegionID()+"') AND state IS NULL";
}
//市代维
if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL  and contractorid='"+userinfo.getDeptID()+"' ";
}
//省移动
if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL";
}
//省代维
if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"+userinfo.getDeptID()+"')";
}
List deptCollection = selectForTag.getSelectForTag("contractorname","contractorid","contractorinfo",condition);
request.setAttribute("deptCollection",deptCollection);
%>

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
	<title>按条件查询人员信息</title>
	</head>
	<body>
		<template:titile value="条件查找人员信息" />
		<html:form action="/ConPersonAction?method=doQuery" styleId="queryForm2">
			<template:formTable namewidth="200" contentwidth="200">
				<template:formTr name="代维单位">
					<html:select property="contractorid" styleClass="inputtext" style="width:180">
						<html:option value="">所有
                         </html:option>
						<html:options collection="deptCollection" property="value"
							labelProperty="label" />
					</html:select>
				</template:formTr>
				<template:formTr name="姓名">
					<html:text property="name" styleClass="inputtext"
						style="width:180;" maxlength="5" />
				</template:formTr>
				<template:formTr name="员工编号">
					<html:text property="employeeNum" styleClass="inputtext"
						style="width:180;" maxlength="12" />
				</template:formTr>
				<template:formTr name="常驻区域">
					<html:text property="residantarea" styleClass="inputtext"
						style="width:180;" maxlength="12" />
				</template:formTr>
				<template:formTr name="在岗否">
					<html:select property="conditions" styleClass="inputtext"
						style="width:180">
						<html:option value="">所有</html:option>
						<html:option value="0">在岗</html:option>
						<html:option value="1">离职</html:option>
					</html:select>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">查找</html:submit>
					</td>
					<td>
						<html:reset property="action" styleClass="button">取消</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBack()">返回</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>


