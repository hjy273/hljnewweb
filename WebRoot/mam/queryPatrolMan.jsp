<%@ page language="java" contentType="text/html; charset=GBK"%><%@include
	file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<jsp:useBean id="selectForTag"
	class="com.cabletech.commons.tags.SelectForTag" scope="request" />
<script language="javascript">
function onChangeCon(){
      k=1;
      for( i=0;i<document.all.workID.options.length;i++){
             if(document.all.workID.options[i].text.substring(0,6)== document.all.rId.value){
                k++;
                document.all.dId.options.length=k;
                document.all.dId.options[0].value="";
                document.all.dId.options[0].text="不限";

                document.all.dId.options[k-1].value=document.all.workID.options[i].value;
                document.all.dId.options[k-1].text=document.all.workID.options[i].text.substring(8,document.all.workID.options.length);
             }
      }
      if(k==0)
        document.all.dId.options.length=0;

    }
</script>
<%
    String condition = "";
    UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
    //市移动
    if (userinfo.getDeptype().equals("1")
            && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE regionid IN ('" + userinfo.getRegionID()
                + "') AND state IS NULL";
    }
    //市代维
    if (userinfo.getDeptype().equals("2")
            && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE state IS NULL  and contractorid='" + userinfo.getDeptID()
                + "' ";
    }
    //省移动
    if (userinfo.getDeptype().equals("1")
            && userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE state IS NULL";
    }
    //省代维
    if (userinfo.getDeptype().equals("2")
            && userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
                + userinfo.getDeptID() + "')";
    }
    List deptCollection = selectForTag.getSelectForTag("contractorname", "contractorid",
            "contractorinfo", condition);
    request.setAttribute("deptCollection", deptCollection);
%>

<template:titile value="查询巡检维护组信息" />
<html:form method="Post" action="/patrolAction.do?method=queryPatrol">
	<template:formTable contentwidth="300" namewidth="200">
		<template:formTr name="巡检维护组" isOdd="">
			<html:text property="patrolName" styleClass="inputtext"
				style="width:200" />
		</template:formTr>
		<template:formTr name="代维单位">
			<html:select property="parentID" styleClass="inputtext"
				style="width:200">
				<html:option value="">不限</html:option>
				<html:options collection="deptCollection" property="value"
					labelProperty="label" />
			</html:select>
		</template:formTr>
		<template:formTr name="驻地地址" isOdd="">
			<html:text property="address" styleClass="inputtext"
				style="width:200" />
		</template:formTr>
		<template:formSubmit>
			<td>
				<html:submit styleClass="button">查询</html:submit>
			</td>
			<td>
				<html:reset styleClass="button">取消</html:reset>
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
