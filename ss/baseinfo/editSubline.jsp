<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="sublineBean" class="com.cabletech.baseinfo.beans.SublineBean" scope="request"/>
<%@include file="/common/header.jsp"%>
<script language="javascript">
<!--
function isValidForm() {

	if(document.sublineBean.subLineName.value==""){
		alert("Ѳ���߶����Ʋ���Ϊ��!! ");
		return false;
	}

	if(document.sublineBean.lineID.value==""){
		alert("������·����Ϊ��!! ");
		return false;
	}
	
	var c=document.sublineBean.cablename;
	if(typeof(c)=="undefined"){
		alert("��Ӧ���²���Ϊ��! ");
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
        var url = "${ctx}/sublineAction.do?method=querySubline&subLineName=";
        //var url = '<%=(session.getAttribute("InitialURL") == null)? session.getAttribute("S_BACK_URL") : session.getAttribute("InitialURL")%>';
        self.location.replace(url);

}

function toEditCable(fID) {
	//fID 1,���� 2,�޸�;
	var pageName = "${ctx}/baseinfo/getSublineRelativeCable.jsp?fID="+fID;
	pageName+="&&patrol_id="+form1.patrolid.value;
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

<template:titile value="�޸�Ѳ���߶���Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" styleId="form1" action="/sublineAction.do?method=updateSubline">
  <template:formTable>
    <template:formTr name="Ѳ���߶α��" isOdd="false">
      <html:hidden property="regionID"/>
			<html:hidden property="subLineID" />
			<bean:write name="sublineBean" property="subLineID" />
    </template:formTr>
    <template:formTr name="Ѳ���߶�����">
      <html:text property="subLineName" styleClass="inputtext" style="width:200px" maxlength="50"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
      <apptag:setSelectOptions valueName="regionCollection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" />
      <html:select property="regionID" styleClass="inputtext" styleId="rId" style="width:200px" onchange="changeRegion()">
        <html:options collection="regionCollection" property="value" labelProperty="label"/>
      </html:select><font color="red"> *</font>
     </template:formTr>
    <template:formTr name="������·" isOdd="false">
      <!--apptag:setSelectOptions valueName="lineCollection" tableName="lineinfo" columnName1="linename" region="true" columnName2="lineid"/-->
      <html:select property="lineID" styleClass="inputtext" style="width:200px">
        <html:options collection="lineCollection" property="value" labelProperty="label"/>
      </html:select><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="��������">
      <!--apptag:setSelectOptions valueName="deptCollection" tableName="deptinfo" columnName1="deptname" columnName2="deptid" region="true"/-->
      <html:select property="ruleDeptID" styleClass="inputtext" style="width:200px">
        <html:option value="">����</html:option>
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="��·����" isOdd="false">
      <apptag:quickLoadList cssClass="inputtext" name="lineType" listName="layingmethod" keyValue="${sublineBean.lineType}" style="width:200px" type="select"/>
	  <font color="red"> *</font>
    </template:formTr>

	<logic:equal value="group"   name="PMType">
	    	<template:formTr name="Ѳ��ά����">

			      <html:select property="patrolid" styleClass="inputtext" style="width:200px">
			        <html:option value="">��</html:option>
			        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
			      </html:select><font color="red"> *</font>
	    	</template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
	    	<template:formTr name="Ѳ��ά����">

			      <html:select property="patrolid" styleClass="inputtext" style="width:200px">
			        <html:option value="">��</html:option>
			        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
			      </html:select><font color="red"> *</font>
	   		 </template:formTr>
	</logic:notEqual>


    <template:formTr name="Ѳ�������" isOdd="false">
      <html:hidden property="checkPoints" />
			<bean:write name="sublineBean" property="checkPoints" />
    </template:formTr>
<logic:equal value="show" name="ShowFIB">
  <template:formTr name="��Ӧ����">
	  <div id = "listSpan" style='width:98%'><br>
	  <table style='width:98%'>
	  <%
		String[][] kidArr = sublineBean.getCablelist();
		if( sublineBean.getCablelist() != null){
			for(int i = 0; i < kidArr.length; i++){
				%>
				<tr>
				<td style='width:100%'>
				<input type= "hidden" name="sublinecablelist" value="<%=kidArr[i][0]%>">
				<input type="text" name ="cablename" value="<%=kidArr[i][1]%>" style = "border:0;background-color:transparent;width:280px;font-size:9pt"  readonly>
				</td>
				</tr>
				<%
			}
		}
	%>
	  </table>
	  </div>
	  <br>
	  <br>
	  <span id="toEditSubListSpan"><a href="javascript:toEditCable(2)">��ӱ༭��Ӧ����</a><font color="red"> *</font></span>

    </template:formTr>
</logic:equal>
   <logic:notEqual value="noShow" name="ShowFIB">
   </logic:notEqual>
    <template:formTr name="˵��"  isOdd="false">
      <html:text property="remark" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
      <td>
       <input type="button" class="button" onclick="javascript:history.go(-1);" value="����" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
