<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<jsp:useBean id="statdao" class="com.cabletech.statistics.dao.StatDao" scope="request"/>

<%
String condition="";
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
//List groupCollection = selectForTag.getPatrolManInfo(userinfo);
//request.setAttribute("pglist",groupCollection);
//���ƶ�
if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
   condition = " WHERE regionid IN ('"+userinfo.getRegionID()+"') AND state IS NULL";
}
//�д�ά
if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL  and contractorid='"+userinfo.getDeptID()+"' ";
}
//ʡ�ƶ�
if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL";
}
//ʡ��ά
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
	<title>��������ѯ��Ա��Ϣ</title>
	</head>
	<body>
		<template:titile value="������������Ա��Ϣ" />
		<html:form action="/ConPersonAction?method=doQuery" styleId="queryForm2">
			<template:formTable >
				<template:formTr name="����">
					<html:text property="name" styleClass="inputtext"
						style="width:180px;" maxlength="5" />
				</template:formTr>
				<template:formTr name="�Ա�">
					<html:select property="sex" styleClass="inputtext"
						style="width:180px">
						<html:option value="">����</html:option>
						<html:option value="��">��</html:option>
						<html:option value="Ů">Ů</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="�Ļ��̶�">
					<html:select property="culture" styleClass="inputtext"
						style="width:180">
						<html:option value="">����</html:option>
						<html:option value="����/��ר">����/��ר</html:option>
						<html:option value="��ר">��ר</html:option>
						<html:option value="����/��������">����/��������</html:option>
					</html:select>
				</template:formTr>
				<!--<template:formTr name="����״��">
					<html:select property="ismarriage" styleClass="inputtext" style="width:180px">
						<html:option value="">����</html:option>
						<html:option value="δ��">δ��</html:option>
						<html:option value="�ѻ�">�ѻ�</html:option>
					</html:select>
				</template:formTr>-->
				<template:formTr name="��ְ���">
					<html:select property="conditions" styleClass="inputtext" style="width:180">
						<html:option value="">����</html:option>
						<html:option value="0">��ְ</html:option>
						<html:option value="1">��ְ</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="��ά��λ">
					<html:select property="contractorid" styleClass="inputtext" style="width:180px">
						<html:option value="">����
                         </html:option>
						<html:options collection="deptCollection" property="value"
							labelProperty="label" />
					</html:select>
				</template:formTr>
				<template:formTr name="�̶��绰">
					<html:text property="phone" styleClass="inputtext"
						style="width:180px;" maxlength="12" />
				</template:formTr>
				<template:formTr name="�ֳֵ绰">
					<html:text property="mobile" styleClass="inputtext"
						style="width:180px;" maxlength="12" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">����</html:submit>
					</td>
					<td>
						<html:reset property="action" styleClass="button">ȡ��</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBack()">����</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		<template:cssTable></template:cssTable>
	</body>
</html>


