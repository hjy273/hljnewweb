<%@include file="/common/header.jsp"%>
<%
String gpsV = request.getParameter("tPID");
String regionID = request.getParameter("regionID");
//System.out.println("==================="+regionID+" "+gpsV);
%>



<script language="javascript" type="">
<!--
function isValidForm() {
    //部门校验
	if(document.GISCrossPointBean.crosspointname.value==""){
		alert("名称不能为空!! ");
		return false;
	}
	return true;
}

//-->
</script><template:titile value="增加标识点信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/GISCrossPointAction.do?method=addGISCrossPoint">
  <template:formTable>

    <template:formTr name="名称">
	  <html:hidden property="gpscoordinate" value="<%=gpsV%>" />
      <html:text property="crosspointname" styleClass="inputtext" style="width:110"/>
    </template:formTr>

    <template:formTr name="类型" isOdd="false">
	  <html:select property="type" styleClass="inputtext" style="width:110">
	  	<!--
        <html:option value="1">中继点</html:option>
        <html:option value="2">基站</html:option>
		<html:option value="6">机房</html:option>
		-->
		<html:option value="3">特殊标示点</html:option>
      </html:select>
	</template:formTr>

    <template:formTr name="区域">
      <apptag:setSelectOptions valueName="regionCellection" tableName="region" columnName1="regionname" currentRegion="true" columnName2="regionid" regionID="<%=regionID%>"/>
      <html:select property="regionid" styleClass="inputtext" style="width:110">
        <html:options collection="regionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>

    <template:formTr name="备注"  isOdd="false">
      <html:text property="remark" styleClass="inputtext" style="width:110"/>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button" >增加</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>

