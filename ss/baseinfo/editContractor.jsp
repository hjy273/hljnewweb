<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="contractorBean" class="com.cabletech.baseinfo.beans.ContractorBean" scope="request"/>
<%@include file="/common/header.jsp"%>

<%String beanRegion = contractorBean.getRegionid(); //唯一区域%>
<input type="hidden" name="region" value="<%=beanRegion%>">
<script language="javascript" type="text/javascript">
function isValidForm() {
	if(document.contractorBean.contractorName.value==""){
		alert("外部单位名称不能为空!! ");
		document.contractorBean.contractorName.focus();
		return false;
	}
	if(isNaN(document.contractorBean.pactTerm.value) || document.contractorBean.pactTerm.value == "0"){
		alert("合同期限应该为非0的数字!! ");
		return false;
	}
	if(document.contractorBean.principalInfo.value==""){
		alert("负责人A不能为空!! ");
		document.contractorBean.principalInfo.focus();
		return false;
	}
	if(document.contractorBean.principalB.value==""){
		alert("负责人B不能为空!! ");
		document.contractorBean.principalB.focus();
		return false;
	}
    if(document.contractorBean.linkmanInfo.value==""){
		alert("联系电话不能为空!! ");
		document.contractorBean.linkmanInfo.focus();
		return false;
	}
	if(document.contractorBean.workPhone.value==""){
		alert("办公电话不能为空!! ");
		document.contractorBean.workPhone.focus();
		return false;
	}
    if(document.contractorBean.fax.value==""){
		alert("传真不能为空!! ");
		document.contractorBean.fax.focus();
		return false;
	}
	if(document.contractorBean.email.value==""){
		alert("Email不能为空!! ");
		document.contractorBean.email.focus();
		return false;
	}
	if(document.contractorBean.address.value==""){
		alert("单位地址不能为空!! ");
		document.contractorBean.address.focus();
		return false;
	}
	if(document.contractorBean.maintenanceArea.value==""){
		alert("维护区域描述不能为空!! ");
		document.contractorBean.maintenanceArea.focus();
		return false;
	}
    if(document.contractorBean.pactBeginDate.value==""){
		alert("签约时间不能为空!! ");
		document.contractorBean.pactBeginDate.focus();
		return false;
	}
    if(document.contractorBean.pactTerm.value==""){
		alert("合同期限不能为空格!! ");
		document.contractorBean.pactTerm.focus();
		return false;
	}
    strRemark = document.contractorBean.remark.value;
    if (strRemark.length>100){
      alert("内容过长，说明项只能输入100个字符");
      document.contractorBean.remark.focus();
      return false;
    }
	return true;
}

function getDepOnload(){

	var depV = region.value;
	var URL = "${ctx}/baseinfo/getSelectForCon.jsp?depType=2&selectname=parentcontractorid&regionid=" + depV;

	hiddenInfoFrame.location.replace(URL);
}

function getDep(){
	var depV = contractorBean.regionid.value;
	var URL = "${ctx}/baseinfo/getSelectForCon.jsp?depType=2&selectname=parentcontractorid&regionid=" + depV;
	hiddenInfoFrame.location.replace(URL);
}


function toGetBack(){
        var url = "${ctx}/contractorAction.do?method=queryContractor&contractorName=&linkmanInfo=";
        self.location.replace(url);

}
</script>
<template:titile value="修改代维单位信息"/>
<html:form onsubmit="return isValidForm()" styleId="contractor" method="Post" action="/contractorAction.do?method=updateContractor">
  <template:formTable>
    <html:hidden property="contractorID" styleClass="inputtext" style="width:160px"/>
    <template:formTr name="单位名称">
      <html:text property="contractorName" styleClass="inputtext" style="width:160px" maxlength="40"/><font color="red"> *</font>
    </template:formTr>
    
     <template:formTr name="别名">
      <html:text property="alias" styleClass="inputtext" style="width:160px"/>
    </template:formTr>
    <!--<template:formTr name="单位类型" isOdd="false">
      <html:select property="depttype" styleClass="inputtext" style="width:160px">
      <html:option value="2" >代维单位</html:option>
      <html:option value="3">监理单位</html:option>
      </html:select>
    </template:formTr>-->
    <html:hidden property="depttype" styleClass="inputtext" style="width:160px"/>
    <template:formTr name="所属区域" isOdd="false">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="regionid" styleClass="inputtext" style="width:160px">
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select><font color="red"> *</font>
    </template:formTr>
     
        <!--
    <template:formTr name="上级单位">
      <apptag:setSelectOptions valueName="parentcontractorCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid" region="true"/>
      <span id="selectSpan">
        <html:select property="parentcontractorid" styleClass="inputtext" style="width:160px">
          <html:option value="0">无 </html:option>
          <html:options collection="parentcontractorCollection" property="value" labelProperty="label"/>
        </html:select>
      </span>
    </template:formTr>
    -->
    <logic:equal value="1" name="LOGIN_USER" property="deptype">
    	 <template:formTr name="上级单位">
		      <apptag:setSelectOptions valueName="parentcontractorCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid"  condition="SUBSTR(REGIONID,3,4)='0000' " />
		          <html:select property="parentcontractorid" styleClass="inputtext" style="width:160px">
		          	<html:option value="0">无  </html:option>
		          <html:options collection="parentcontractorCollection" property="value" labelProperty="label"/>
		        </html:select><font color="red"> *</font>
		    </template:formTr>
    </logic:equal>
    <logic:equal value="2" name="LOGIN_USER" property="deptype">
        <logic:equal value="0" name="contractorBean" property="parentcontractorid">
            <template:formTr name="上级单位">
            <html:hidden property="parentcontractorid"/>
            <select name="parentcontractorid" disabled Class="inputtext" style="width:160px">
                <option value="0">无  </option>
                <option value="<bean:write name="LOGIN_USER_DEPT_ID"/>"><bean:write name="LOGIN_USER_DEPT_NAME"/></option>
            </select><font color="red"> *</font>
		    </template:formTr>
        </logic:equal>
        <logic:notEqual value="0" name="contractorBean" property="parentcontractorid">
          <template:formTr name="上级单位">
            <select name="parentcontractorid" Class="inputtext" style="width:160px">
                <option value="0">无  </option>
                <option value="<bean:write name="LOGIN_USER_DEPT_ID"/>"><bean:write name="LOGIN_USER_DEPT_NAME"/></option>
            </select><font color="red"> *</font>
		    </template:formTr>
        </logic:notEqual>

    </logic:equal>

    <template:formTr name="负责人A" isOdd="false">
      <html:text property="principalInfo" styleClass="inputtext" style="width:160px" maxlength="25"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="负责人B" isOdd="false">
      <html:text property="principalB" styleClass="inputtext" style="width:160px" maxlength="25"/><font color="red"> *</font>
    </template:formTr>
     <template:formTr name="联系电话" >
      <html:text property="linkmanInfo" styleClass="inputtext" style="width:160px" maxlength="25"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="办公电话" >
      <html:text property="workPhone" styleClass="inputtext" style="width:160px" maxlength="25"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="传真" >
      <html:text property="fax" styleClass="inputtext" style="width:160px" maxlength="25"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="Email" >
      <html:text property="email" styleClass="inputtext" style="width:160px" maxlength="25"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="单位地址" >
      <html:text property="address" styleClass="inputtext" style="width:160px" maxlength="100"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="维护区域描述" >
      <html:text property="maintenanceArea" styleClass="inputtext" style="width:160px" maxlength="512"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="签约时间" isOdd="false">
      <html:text readonly="true" property="pactBeginDate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:160px" maxlength="45"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="合同期限">
      <html:text property="pactTerm" styleClass="inputtext" style="width:160px" maxlength="10"/> 月<font color="red"> *</font>
    </template:formTr>
    <!--
      <template:formTr name="状态" isOdd="false">
      <html:select property="state" styleClass="inputtext" style="width:160px">
      <html:option value="1">正常</html:option>
      <html:option value="2">暂停</html:option>
      <html:option value="3">停止</html:option>
      </html:select>
      </template:formTr>
    -->
    <template:formTr name="备注" isOdd="false">
      <html:textarea property="remark" styleClass="textarea" style="width:280;" rows="2" cols="" ></html:textarea>
    </template:formTr>
    <!--
    <template:formTr name="备&nbsp;&nbsp;&nbsp;&nbsp;注">
      <html:text property="remark" styleClass="inputtext" style="width:160px" maxlength="10"/>
    </template:formTr>
    -->
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">更新</html:submit>
      </td>
      <td>
       <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
