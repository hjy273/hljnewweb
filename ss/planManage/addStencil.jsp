<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
//�Ƿ�ѡȡ������

function checkInput(){
    if(document.stencilBean.patrolid.value == ""){
        alert("��ѡ��ƻ�ִ���� ��");
        document.stencilBean.patrolid.focus();
        return false;
    }else if(document.stencilBean.stencilname.value == ""){
        alert("����дģ������ ��");
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
document.stencilBean.stencilname.value=document.stencilBean.patrolid.options[Index].text+'�ƻ�ģ��'
}
//-->
</script>
<body onload="">
<br>
<template:titile value="���Ӽƻ�ģ��"/>
<html:form action="/StencilAction?method=createStencil" onsubmit="return checkInput()">
  <input  id="deptname" type="hidden"  name="regionid" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>

  <template:formTable>
    <logic:equal value="group" name="PMType">
        <template:formTr name="ִ �� ��" isOdd="false">
                <select  name="patrolid"  Class="inputtext" style="width:250px" onchange="setstencilname();">
                    <option value="">��ѡ��ƻ�Ѳ��ά����</option>
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
        <template:formTr name="ִ �� ��" isOdd="false">
                <select  name="patrolid"  Class="inputtext" style="width:250px" onchange="setstencilname();">
                    <option value="">��ѡ��ƻ�ִ����</option>
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

    <template:formTr name="ģ������" >
      <html:text property="stencilname" styleClass="inputtext" style="width:250px"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:button property="action" styleClass="button" onclick="saveStencil();">��������</html:button>
      </td>
     
    </template:formSubmit>
  </template:formTable>
</html:form>
  <div align="center">
 	  <table width="620" border="0" align="center">
        <tr style="color:red">
          <td width="7%" scope="col" valign="top">˵����</td>
          <td width="94%" scope="col">
         1���ƶ��ƻ�ģ���ܹ����������ӷ��㡢��ݡ���Ч���ƶ��ƻ���<br>
         2��ͬһ���߶β��ܰ���������������������,�����п���Ӱ�쵽����Ѳ���ʡ���Լ���������ڵ�ǰ�ƻ�ģ�塣<br>
      	 3�����������ƶ��ƻ�ģ��ʱ��Ϊ�˽�����ʹ�÷��㣬������ģ��������ʵ����֡�<br>	 
		  </td>
        </tr>
      </table>
   </div>
</body>
