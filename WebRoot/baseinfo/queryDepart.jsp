<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<script language="javascript" type="">

function onChangeDept(){
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
function getDep(){

	var depV = departBean.regionid.value;
	var URL = "getSelect.jsp?depType=1&selectname=parentID&regionid=" + depV;

	hiddenInfoFrame.location.replace(URL);
}
function toGetBack(){
       window.history.go(-1);
}
//-->
</script>
<apptag:checkpower thirdmould="70203" ishead="5">
	<jsp:forward page="/globinfo/powererror.jsp" />
</apptag:checkpower>
<%
	UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
	if (userinfo.getType().equals("11")) {
%>
<body onload="onChangeDept()">
	<%
		} else {
	%>

<body>
	<%
		}
	%>
	<template:titile value="查询部门信息" />
	<html:form method="Post" action="/departAction.do?method=queryDepart">
		<template:formTable contentwidth="200" namewidth="200">
			<template:formTr name="部门名称">
				<html:text property="deptName" styleClass="inputtext" style="width:160" maxlength="45" />
			</template:formTr>

			<template:formTr name="所属区域">
				<apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" />
				<html:select property="regionid" styleClass="inputtext" style="width:160px">
					<html:option value="">不限</html:option>
					<html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
				</html:select>
			</template:formTr>
			<template:formTr name="上级部门">
				<apptag:setSelectOptions columnName2="deptid" columnName1="deptname" valueName="deptColl" tableName="deptinfo"></apptag:setSelectOptions>
				<html:select property="parentID" styleClass="inputtext" style="width:160px">
					<html:option value="">不限</html:option>
					<html:options collection="deptColl" property="value" labelProperty="label"/>
				</html:select>
			</template:formTr>


			<template:formSubmit>
					<html:submit styleClass="button">查询</html:submit>
					<html:reset styleClass="button">取消</html:reset>
					<input type="button" class="button" onclick="toGetBack()" value="返回">
			</template:formSubmit>
		</template:formTable>
	</html:form>
	<iframe name="hiddenInfoFrame" style="display: none"></iframe>