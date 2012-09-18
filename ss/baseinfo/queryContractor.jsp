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
</script><template:titile value="查询外部单位信息"/>
<html:form method="Post" action="/contractorAction.do?method=queryContractor">
  <template:formTable >
    <template:formTr name="单位名称">
      <html:text property="contractorName" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <template:formTr name="所属区域" isOdd="false">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="regionid" styleClass="inputtext" style="width:200px">
        <html:option value="">不限</html:option>
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <logic:equal value="1" name="LOGIN_USER" property="deptype">
    	 <template:formTr name="上级单位">
		      <apptag:setSelectOptions valueName="parentcontractorCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid"  condition=" SUBSTR(REGIONID,3,4)='0000' " />
		          <html:select property="parentcontractorid" styleClass="inputtext" style="width:200px">
		          	<html:option value="">无  </html:option>
		          <html:options collection="parentcontractorCollection" property="value" labelProperty="label"/>
		        </html:select>
		    </template:formTr>
    </logic:equal>
    <logic:equal value="2" name="LOGIN_USER" property="deptype">
    	 <template:formTr name="上级单位">
         	<select name="parentcontractorid" Class="inputtext" style="width:200px">
                <option value="">不限  </option>
            	<option value="<bean:write name="LOGIN_USER_DEPT_ID"/>"><bean:write name="LOGIN_USER_DEPT_NAME"/></option>
         	</select>
		    </template:formTr>
    </logic:equal>
    <template:formTr name="负 责 人" isOdd="false">
      <html:text property="principalInfo" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <template:formTr name="联 系 电话" >
      <html:text property="linkmanInfo" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    
   <!--
    <template:formTr name="状态" isOdd="false">
      <html:select property="state" styleClass="inputtext" style="width:200px">
        <html:option value="">不限</html:option>
        <html:option value="1">正常</html:option>
        <html:option value="2">暂停</html:option>
        <html:option value="3">停止</html:option>
      </html:select>
    </template:formTr>-->
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
      <td>
        <html:button property="action" styleClass="button" onclick="addGoBack()" >返回</html:button>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
