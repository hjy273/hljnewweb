<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<jsp:useBean id="statdao" class="com.cabletech.statistics.dao.StatDao" scope="request"/>

<%
String condition="";
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
//List groupCollection = selectForTag.getPatrolManInfo(userinfo);
//request.setAttribute("pglist",groupCollection);
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
List deptCollection = statdao.getSelectForTag("contractorname","contractorid","contractorinfo",condition);
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
		<template:titile value="按条件查找人员信息" />
		<html:form action="/ConPersonAction?method=doQuery" styleId="queryForm2">
			<template:formTable >
				<template:formTr name="姓名">
					<html:text property="name" styleClass="inputtext"
						style="width:180px;" maxlength="5" />
				</template:formTr>
				<template:formTr name="性别">
					<html:select property="sex" styleClass="inputtext"
						style="width:180px">
						<html:option value="">所有</html:option>
						<html:option value="男">男</html:option>
						<html:option value="女">女</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="文化程度">
					<html:select property="culture" styleClass="inputtext"
						style="width:180">
						<html:option value="">所有</html:option>
						<html:option value="高中/中专">高中/中专</html:option>
						<html:option value="大专">大专</html:option>
						<html:option value="本科/本科以上">本科/本科以上</html:option>
					</html:select>
				</template:formTr>
				<!--<template:formTr name="婚姻状况">
					<html:select property="ismarriage" styleClass="inputtext" style="width:180px">
						<html:option value="">所有</html:option>
						<html:option value="未婚">未婚</html:option>
						<html:option value="已婚">已婚</html:option>
					</html:select>
				</template:formTr>-->
				<template:formTr name="在职情况">
					<html:select property="conditions" styleClass="inputtext" style="width:180">
						<html:option value="">所有</html:option>
						<html:option value="0">在职</html:option>
						<html:option value="1">离职</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="代维单位">
					<html:select property="contractorid" styleClass="inputtext" style="width:180px">
						<html:option value="">所有
                         </html:option>
						<html:options collection="deptCollection" property="value"
							labelProperty="label" />
					</html:select>
				</template:formTr>
				<template:formTr name="固定电话">
					<html:text property="phone" styleClass="inputtext"
						style="width:180px;" maxlength="12" />
				</template:formTr>
				<template:formTr name="手持电话">
					<html:text property="mobile" styleClass="inputtext"
						style="width:180px;" maxlength="12" />
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
		<template:cssTable></template:cssTable>
	</body>
</html>


