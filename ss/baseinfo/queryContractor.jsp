<%@include file="/common/header.jsp"%>

<script language="javascript" type="">
<!--

function getDep(){

	var depV = contractorBean.regionid.value;
	var URL = "getSelectForCon.jsp?depType=2&selectname=parentcontractorid&regionid=" + depV;

	hiddenInfoFrame.location.replace(URL);
}
function addGoBack()
        {
        	try{
            	location.href = "${ctx}/contractorAction.do?method=queryContractor&contractorName=&linkmanInfo=";
                return true;
            }
            catch(e){
            	alert(e);
            }
        }
//-->
</script><template:titile value="��ѯ�ⲿ��λ��Ϣ"/>
<html:form method="Post" action="/contractorAction.do?method=queryContractor">
  <template:formTable >
    <template:formTr name="��λ����">
      <html:text property="contractorName" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="regionid" styleClass="inputtext" style="width:200px">
        <html:option value="">����</html:option>
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <logic:equal value="1" name="LOGIN_USER" property="deptype">
    	 <template:formTr name="�ϼ���λ">
		      <apptag:setSelectOptions valueName="parentcontractorCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid"  condition=" SUBSTR(REGIONID,3,4)='0000' " />
		          <html:select property="parentcontractorid" styleClass="inputtext" style="width:200px">
		          	<html:option value="">��  </html:option>
		          <html:options collection="parentcontractorCollection" property="value" labelProperty="label"/>
		        </html:select>
		    </template:formTr>
    </logic:equal>
    <logic:equal value="2" name="LOGIN_USER" property="deptype">
    	 <template:formTr name="�ϼ���λ">
         	<select name="parentcontractorid" Class="inputtext" style="width:200px">
                <option value="">����  </option>
            	<option value="<bean:write name="LOGIN_USER_DEPT_ID"/>"><bean:write name="LOGIN_USER_DEPT_NAME"/></option>
         	</select>
		    </template:formTr>
    </logic:equal>
    <template:formTr name="�� �� ��" isOdd="false">
      <html:text property="principalInfo" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <template:formTr name="�� ϵ �绰" >
      <html:text property="linkmanInfo" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    
   <!--
    <template:formTr name="״̬" isOdd="false">
      <html:select property="state" styleClass="inputtext" style="width:200px">
        <html:option value="">����</html:option>
        <html:option value="1">����</html:option>
        <html:option value="2">��ͣ</html:option>
        <html:option value="3">ֹͣ</html:option>
      </html:select>
    </template:formTr>-->
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
      <td>
        <html:button property="action" styleClass="button" onclick="addGoBack()" >����</html:button>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
