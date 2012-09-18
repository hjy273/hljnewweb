<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
//是否选取了任务

function checkInput(){
    if(document.stencilBean.patrolid.value == ""){
        alert("请选择计划执行人 ！");
        document.stencilBean.patrolid.focus();
        return false;
    }else if(document.stencilBean.stencilname.value == ""){
        alert("请填写模板名称 ！");
        document.stencilBean.stencilname.focus();
        return false;
    }else
    return true;
}

function saveStencil(){
    if(checkInput()){
        document.stencilBean.submit();
    }
}
function setstencilname(){
var Index = document.stencilBean.patrolid.selectedIndex;
document.stencilBean.stencilname.value=document.stencilBean.patrolid.options[Index].text+'计划模板'
}
//-->
</script>
<body onload="">
<br>
<template:titile value="增加计划模板"/>
<html:form action="/StencilAction?method=createStencil" onsubmit="return checkInput()">
  <input  id="deptname" type="hidden"  name="regionid" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>

  <template:formTable>
    <logic:equal value="group" name="PMType">
        <template:formTr name="执 行 组" isOdd="false">
                <select  name="patrolid"  Class="inputtext" style="width:250px" onchange="setstencilname();">
                    <option value="">请选择计划巡检维护组</option>
                      <logic:present name="patrolgroup">
                        <logic:iterate id="pgId" name="patrolgroup">
                            <option value="<bean:write name="pgId" property="patrolid"/>"><bean:write name="pgId" property="patrolname"/></option>
                        </logic:iterate>
                      </logic:present>
                </select>
                <!-- hidden id -->
                <html:hidden property="id"/>
          </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
        <template:formTr name="执 行 人" isOdd="false">
                <select  name="patrolid"  Class="inputtext" style="width:250px" onchange="setstencilname();">
                    <option value="">请选择计划执行人</option>
                      <logic:present name="patrolgroup">
                        <logic:iterate id="pgId" name="patrolgroup">
                            <option value="<bean:write name="pgId" property="patrolid"/>"><bean:write name="pgId" property="patrolname"/></option>
                        </logic:iterate>
                      </logic:present>
                </select>
                <!-- hidden id -->
                <html:hidden property="id"/>
          </template:formTr>
    </logic:notEqual>

    <template:formTr name="模板名称" >
      <html:text property="stencilname" styleClass="inputtext" style="width:250px"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:button property="action" styleClass="button" onclick="saveStencil();">增加任务</html:button>
      </td>
     
    </template:formSubmit>
  </template:formTable>
</html:form>
  <div align="center">
 	  <table width="620" border="0" align="center">
        <tr style="color:red">
          <td width="7%" scope="col" valign="top">说明：</td>
          <td width="94%" scope="col">
         1、制定计划模板能够帮助您更加方便、快捷、高效的制定计划。<br>
         2、同一条线段不能包含在两个以上子任务中,否则将有可能影响到您的巡检率。此约束仅适用于当前计划模板。<br>
      	 3、建议您在制定计划模板时，为了将来的使用方便，请您的模板起个合适的名字。<br>	 
		  </td>
        </tr>
      </table>
   </div>
</body>
