<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="sublineBean" class="com.cabletech.baseinfo.beans.SublineBean" scope="request"/>
<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
<%
String rId = (String)request.getAttribute("regionID");
String pmtype = (String)session.getAttribute("PMType");
//System.out.println("========================="+pmtype);
%>
<!--
function isValidForm() {

	if(document.sublineBean.subLineName.value==""){
		alert("名称不能为空!! ");
		return false;
	}

	if(document.sublineBean.lineID.value==""){
		alert("所属线路不能为空!! ");
		return false;
	}

	return true;
}


function presetData(){

	var L = sublineBean.sublinecablelist.options.length;

	for(var i = 0; i < L; i++){

		if(eval("listkid") == null){
			return;
		}else if(listkid.length == 1){
			if(sublineBean.sublinecablelist.options[i].value == listkid.value){
				sublineBean.sublinecablelist.options[i].selected = true;
				return;
			}
		}else{

			for(var k = 0; k < listkid.length; k++){
				if(sublineBean.sublinecablelist.options[i].value == listkid[k].value){
					sublineBean.sublinecablelist.options[i].selected = true;
					continue;
				}
			}
		}
	}//end for
}


function toEditCable(fID) {
	//fID 1,增加 2,修改;
	var pageName = "${ctx}/baseinfo/getSublineRelativeCable4GIS.jsp?fID="+fID+"&regionID=<%=rId%>";

	var pointsPop = window.open(pageName,'pointsPop','width=400,height=300,scrollbars=yes');
	//,resizable=yes,,status=yes
	pointsPop.focus();
}
function changeRegion(){
    //alert(sublineBean.rId.value)
    var rId = sublineBean.rId.value;
    var ID = sublineBean.subLineID.value;
    var URL = "${ctx}/sublineAction.do?method=loadSubline4Gis&regionid="+rId +"&sPID="+ID;00003246
    location.replace(URL);
}

//-->
</script>

<template:titile value="修改巡检段信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/sublineAction.do?method=updateSubline4Gis">
  <template:formTable>
    <template:formTr name="巡检段<br>编号" isOdd="false">
      <html:hidden property="regionID"/>
      <html:text readonly="true" property="subLineID" value="<%=sublineBean.getSubLineID()%>" styleClass="inputtext" style="width:100" maxlength="45"/>
    </template:formTr>
    <template:formTr name="巡检段<br>名称">
      <html:text property="subLineName" styleClass="inputtext" style="width:100" maxlength="45"/>
    </template:formTr>
    <template:formTr name="所属区域" isOdd="false">
      <apptag:setSelectOptions valueName="regionCollection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="regionID" styleClass="inputtext" styleId="rId" style="width:100" onchange="changeRegion()">
        <html:options collection="regionCollection" property="value" labelProperty="label"/>
      </html:select>
     </template:formTr>
    <template:formTr name="所属线路" isOdd="false">
      <!--apptag:setSelectOptions valueName="lineCollection" regionID="<=rId>" tableName="lineinfo" columnName1="linename" region="true" columnName2="lineid"/-->
      <html:select property="lineID" styleClass="inputtext" style="width:100">
        <html:options collection="lineCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="所属部门">
      <!--apptag:setSelectOptions valueName="deptCollection" regionID="<rId" tableName="deptinfo" columnName1="deptname" columnName2="deptid" region="true"/-->
      <html:select property="ruleDeptID" styleClass="inputtext" style="width:100">
        <html:option value="">不限</html:option>
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="巡检段<br>类型" isOdd="false">
      <html:select property="lineType" styleClass="inputtext" style="width:100">
        <html:option value="00">直埋</html:option>
        <html:option value="01">架空</html:option>
        <html:option value="02">管道</html:option>
		<html:option value="04">挂墙</html:option>
		<html:option value="03">混合</html:option>
	  </html:select>
    </template:formTr>
    <logic:equal value="group" name="PMType">
    <template:formTr name="巡检维<br>护组">
      <apptag:setSelectOptions valueName="patrolCollection" regionID="<%=rId%>" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true"/>
      <html:select property="patrolid" styleClass="inputtext" style="width:100">
        <html:option value="">无</html:option>
        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
    <template:formTr name="巡检负<br>责人">
      <apptag:setSelectOptions valueName="patrolCollection" regionID="<%=rId%>" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true"/>
        <html:select property="patrolid" styleClass="inputtext" style="width:100">
          <html:option value="">无</html:option>
          <html:options collection="patrolCollection" property="value" labelProperty="label"/>
        </html:select>
    </template:formTr>
    </logic:notEqual>

    <template:formTr name="巡检点<br>数量"  isOdd="false">
      <html:text property="checkPoints" styleClass="inputtext" style="width:100"/>
    </template:formTr>
<logic:equal value="show" name="ShowFIB">
   <template:formTr name="对应光缆">
	  <span id = "listSpan"><br>
	  <table>
	  <%
		String[][] kidArr = sublineBean.getCablelist();
		if( sublineBean.getCablelist() != null){
			for(int i = 0; i < kidArr.length; i++){
				%>
				<tr>
				<td>
				<input type= "hidden" name="sublinecablelist" value="<%=kidArr[i][0]%>">
				<input type="text" name ="cablename" value="<%=kidArr[i][1]%>" style = "border:0;background-color:transparent;width:100;font-size:9pt"  readonly>
				</td>
				</tr>
				<%
			}
		}
	%>
	  </table>
	  </span>
	  <br>
	  <br>
	  <span id="toEditSubListSpan"><a href="javascript:toEditCable(2)">添加编辑对应光缆</a></span>


	</template:formTr>
</logic:equal>
   <logic:notEqual value="noShow" name="ShowFIB">
   </logic:notEqual>
    <template:formTr name="说明"  isOdd="false">
      <html:text property="remark" styleClass="inputtext" style="width:100" maxlength="45"/>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button">修改</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
