<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="sublineBean" class="com.cabletech.baseinfo.beans.SublineBean" scope="request"/>
<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<script language="javascript">
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


function toGetBack(){
        //var url = "${ctx}/sublineAction.do?method=querySubline&subLineName=";
        var url = '<%=(session.getAttribute("InitialURL") == null)? session.getAttribute("previousURL") : session.getAttribute("InitialURL")%>';
        self.location.replace(url);

}

function toEditCable(fID) {
	//fID 1,增加 2,修改;
	var pageName = "${ctx}/baseinfo/getSublineRelativeCable.jsp?fID="+fID;

	var pointsPop = window.open(pageName,'pointsPop','width=400,height=300,scrollbars=yes');
	//,resizable=yes,,status=yes
	pointsPop.focus();
}
function changeRegion(){
    //alert(sublineBean.rId.value)
    var rId = sublineBean.rId.value;
    var ID = sublineBean.subLineID.value;
    var URL = "${ctx}/sublineAction.do?method=loadSubline&regionid="+rId +"&id="+ID;
    location.replace(URL);
}
//-->
</script>

<template:titile value="修改巡检线段信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/sublineAction.do?method=updateSubline">
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="巡检线段编号" >
      <html:hidden property="regionID"/>
      <html:text readonly="true" property="subLineID" styleClass="inputtext" style="width:200" maxlength="45"/>
    </template:formTr>
    <template:formTr name="巡检线段名称">
      <html:text property="subLineName" styleClass="inputtext" style="width:200" maxlength="50"/>
    </template:formTr>
    <template:formTr name="所属区域" >
      <apptag:setSelectOptions valueName="regionCollection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="regionID" styleClass="inputtext" styleId="rId" style="width:200" onchange="changeRegion()">
        <html:options collection="regionCollection" property="value" labelProperty="label"/>
      </html:select>
     </template:formTr>
    <template:formTr name="所属线路" >
      <!--apptag:setSelectOptions valueName="lineCollection" tableName="lineinfo" columnName1="linename" region="true" columnName2="lineid"/-->
      <html:select property="lineID" styleClass="inputtext" style="width:200">
        <html:options collection="lineCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="所属部门">
      <!--apptag:setSelectOptions valueName="deptCollection" tableName="deptinfo" columnName1="deptname" columnName2="deptid" region="true"/-->
      <html:select property="ruleDeptID" styleClass="inputtext" style="width:200">
        <html:option value="">不限</html:option>
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="线路类型" >
      <html:select property="lineType" styleClass="inputtext" style="width:200">
        <html:option value="00">直埋</html:option>
        <html:option value="01">架空</html:option>
        <html:option value="02">管道</html:option>
		<html:option value="04">挂墙</html:option>
		<html:option value="03">混合</html:option>
	  </html:select>
    </template:formTr>

	<logic:equal value="group"   name="PMType">
	    	<template:formTr name="巡检维护组">

			      <html:select property="patrolid" styleClass="inputtext" style="width:200">
			        <html:option value="">无</html:option>
			        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
			      </html:select>
	    	</template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
	    	<template:formTr name="巡检维护人">

			      <html:select property="patrolid" styleClass="inputtext" style="width:200">
			        <html:option value="">无</html:option>
			        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
			      </html:select>
	   		 </template:formTr>
	</logic:notEqual>


    <template:formTr name="巡检点数量" >
      <html:text property="checkPoints" styleClass="inputtext" style="width:200" readonly="true"/>
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
				<input type="text" name ="cablename" value="<%=kidArr[i][1]%>" style = "border:0;background-color:transparent;width:280;font-size:9pt"  readonly>
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
    <template:formTr name="说明"  >
      <html:text property="remark" styleClass="inputtext" style="width:200" maxlength="45"/>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button">更新</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="返回" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
