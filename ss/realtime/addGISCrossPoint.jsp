<%@include file="/common/header.jsp"%>
<%
String gpsV = request.getParameter("tPID");
String regionID = request.getParameter("regionID");
//System.out.println("==================="+regionID+" "+gpsV);
%>



<script language="javascript" type="">
<!--
function isValidForm() {
    //����У��
	if(document.GISCrossPointBean.crosspointname.value==""){
		alert("���Ʋ���Ϊ��!! ");
		return false;
	}
	return true;
}

//-->
</script><template:titile value="���ӱ�ʶ����Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/GISCrossPointAction.do?method=addGISCrossPoint">
  <template:formTable>

    <template:formTr name="����">
	  <html:hidden property="gpscoordinate" value="<%=gpsV%>" />
      <html:text property="crosspointname" styleClass="inputtext" style="width:110"/>
    </template:formTr>

    <template:formTr name="����" isOdd="false">
	  <html:select property="type" styleClass="inputtext" style="width:110">
	  	<!--
        <html:option value="1">�м̵�</html:option>
        <html:option value="2">��վ</html:option>
		<html:option value="6">����</html:option>
		-->
		<html:option value="3">�����ʾ��</html:option>
      </html:select>
	</template:formTr>

    <template:formTr name="����">
      <apptag:setSelectOptions valueName="regionCellection" tableName="region" columnName1="regionname" currentRegion="true" columnName2="regionid" regionID="<%=regionID%>"/>
      <html:select property="regionid" styleClass="inputtext" style="width:110">
        <html:options collection="regionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>

    <template:formTr name="��ע"  isOdd="false">
      <html:text property="remark" styleClass="inputtext" style="width:110"/>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button" >����</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>

