<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<%
   session.setAttribute("S_BACK_URL",null);
%>
<script type="">
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
function toGetBack(){
 	var url="${ctx}/lineAction.do?method=queryLine&lineName=";
	self.location.replace(url);
}
</script>
<br>
<%
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
if(userinfo.getType().equals("11") || userinfo.getType().equals("21")){%>
<body>
<% }else{%>
<body>
<% }%>
<template:titile value="查询线路信息"/>
<html:form method="Post" action="/lineAction.do?method=queryLine">
  <template:formTable >
    <template:formTr name="线路编号" isOdd="false">
      <html:text property="lineID" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <template:formTr name="线路名称">
      <html:text property="lineName" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>

    <logic:notEmpty name="reginfo">
          <template:formTr name="所属区域" isOdd="false">
            <select name="regionid" class="inputtext" style="width:160px" id="rId"  onclick="onChangeDept()" >
              <option value="">不限</option>
              <logic:present name="reginfo">
                <logic:iterate id="reginfoId" name="reginfo">
                  <option value="<bean:write name="reginfoId" property="regionid"/>">
                  <bean:write name="reginfoId" property="regionname"/>
                  </option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>
        </logic:notEmpty>
    <!--
    <template:formTr name="主管部门" isOdd="false">
      <apptag:setSelectOptions valueName="deptCollection" tableName="deptinfo" columnName1="deptname" columnName2="deptid" region="true"/>
      <html:select property="ruleDeptID" styleClass="inputtext" style="width:200px">
        <html:option value="">不限</html:option>
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    -->
    <logic:notEmpty name="deptinfo">
          <template:formTr name="所属部门" tagID="deptTr">
            <select name="ruleDeptID" class="inputtext" style="width:160px" id="dId">
              <option value="">不限</option>
              <logic:present name="deptinfo">
                <logic:iterate id="deptinfoId" name="deptinfo">
                  <option value="<bean:write name="deptinfoId" property="deptid"/>">
                  <bean:write name="deptinfoId" property="deptname"/></option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>

          <logic:present name="deptinfo">
              <select name="workID">
                <logic:iterate id="deptinfoId" name="deptinfo">
                    <option value="<bean:write name="deptinfoId" property="deptid"/>"><bean:write name="deptinfoId" property="regionid"/>--<bean:write name="deptinfoId" property="deptname"/></option>
                </logic:iterate>
              </select>
          </logic:present>
    </logic:notEmpty>

	<template:formTr name="线路级别">
    <apptag:setSelectOptions columnName1="name" columnName2="code" tableName="lineclassdic" valueName="linetypeColl"/>
      <html:select property="lineType" styleClass="inputtext" style="width:200px">
		<html:option value="">不限</html:option>
          <html:options collection="linetypeColl" property="value" labelProperty="label"/>

      </html:select>
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
