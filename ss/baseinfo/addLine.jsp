<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>

<script language="javascript" type="">

function isValidForm(form) {
	if(form.lineName.value==""){
		alert("线路名称不能为空!! ");
		return false;
	}
    if(trim(form.lineName.value)==""){
		alert("线路名称不能为空格!! ");
		return false;
	}
    if(valCharLength(form.lineName.value)>100){
      alert("线路名称过长，不能大于50个汉字，请重新填写！");
      return false;
    }
    if(form.ruleDeptID.value==""){
		alert("所属部门不能为空!! ");
		return false;
	}
    if(trim(form.ruleDeptID.value)==""){
		alert("所属部门不能为空格!! ");
		return false;
	}
	if(form.lineType.value==""){
		alert("线路级别不能为空!! ");
		return false;
	}
    if(trim(form.lineType.value)==""){
		alert("线路级别不能为空格!! ");
		return false;
	}
    strRemark = form.remark.value;
    if (strRemark.length>45){
      alert("内容过长，说明项只能输入45个字符");
      form.remark.focus();
      return false;
    }

}
function valCharLength(Value){
  var j=0;
  var s = Value;
  for   (var   i=0;   i<s.length;   i++)
  {
    if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
    else   j++
  }
  return j;
}
function toGetBack(){
 	var url="${ctx}/lineAction.do?method=queryLine&lineName=";
	self.location.replace(url);
}

//-->
</script>
<apptag:checkpower thirdmould="70902" ishead="5">
  <jsp:forward page="/globinfo/powererror.jsp"/>
</apptag:checkpower>
<BR>
<template:titile value="增加线路信息"/>
<html:form onsubmit="return isValidForm(this)" method="Post" action="/lineAction.do?method=addLine">
  <template:formTable>
    <template:formTr name="线路名称">
      <html:text property="lineName" styleClass="inputtext" style="width:200px" maxlength="50" title="线路名称最多50个汉字"/><font color="red"> *</font>
    </template:formTr>

  <!--
     <template:formTr name="主管部门" isOdd="false">
      <select name="ruleDeptID" class="inputtext" style="width:180px" id="uId">
        <logic:present name="deptinfo_line">
          <logic:iterate id="deptinfo_lineId" name="deptinfo_line">
            <option value="<bean:write name="deptinfo_lineId" property="deptid"/>">
              <bean:write name="deptinfo_lineId" property="deptname"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    -->


    <template:formTr name="所属部门" isOdd="false">
      <apptag:setSelectOptions valueName="deptCollection" tableName="deptinfo" columnName1="deptname" columnName2="deptid" region="true"/>
      <html:select property="ruleDeptID" styleClass="inputtext" style="width:200px">
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select><font color="red"> *</font>
    </template:formTr>



	<template:formTr name="线路级别">
	<!--apptag:quickLoadList cssClass="inputtext" name="lineType" listName="cabletype"  style="width:200px" type="select"/ -->
	  <apptag:setSelectOptions valueName="lineTypeCollection" tableName="lineclassdic" columnName1="name" columnName2="code"/>
	 <html:select property="lineType" styleClass="inputtext" style="width:200px">
        <html:options collection="lineTypeCollection" property="value" labelProperty="label"/>
      </html:select>
    <font color="red"> *</font>
    </template:formTr>

    <template:formTr name="说&nbsp;&nbsp;&nbsp;&nbsp;明" isOdd="false">
      <textarea cols="" rows="2" name="remark" style="width:280px;" class="textarea" ></textarea>
    </template:formTr>
    <!--
    <template:formTr name="说&nbsp;&nbsp;&nbsp;&nbsp;明" isOdd="false">
      <html:text property="remark" styleClass="inputtext" style="width:160px" maxlength="45"/>
	</template:formTr>
    -->
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">增加</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
      <td>
        <html:button  property="button" styleClass="button" onclick="toGetBack()">返回</html:button>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
