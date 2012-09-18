<%@include file="/common/header.jsp"%>
<script language="javascript">
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}

function isValidForm(form) {
	if(form.operationName.value.length == 0||form.operationName.value.trim().length==0){
		alert("任务操作名称不能为空或者空格!! ");
        form.operationName.value="";
		form.operationName.focus();
		return false;
	}
    form.operationName.value=form.operationName.value.trim();
    if(valCharLength(form.operationName.value)>50){
      alert("任务操作名称不能大于25个汉字，请重新填写！");
      form.operationName.value=cutString(form.operationName.value,150);
      return false;
    }
    if(valCharLength(form.operationDes.value)>150){
      alert("备注不能大于75个汉字，请重新填写！");
      form.operationDes.value=cutString(form.operationDes.value,150);
      return false;
    }
}
function valCharLength(Value){
  var j=0;
  var s = Value;
  for   (var   i=0;   i<s.length;   i++)
  {
    if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
    else   j++;
  }
  return j;
}
function cutString(value,len){
  var s="";var j=0;
  for(i=0;i<value.length;i++){
    if(value.substr(i,1).charCodeAt(0)>255) j=j+2;
    else j++;
    if(j>len) break;
    s=s+value.substr(i,1).charAt(0);
  }
  return s;
}
</script>
<br><template:titile value="增加任务操作信息"/>
<html:form onsubmit="return isValidForm(this);" method="Post" action="/TaskOperationAction.do?method=addTaskOperation">
  <template:formTable>
    <template:formTr name="任务操作名称">
      <html:text property="operationName" styleClass="inputtext" style="width:200px"/><font color="red">*</font>
    </template:formTr>
     <template:formTr name="所属区域" isOdd="false" style="display:none">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="regionID" styleClass="inputtext" style="width:200px">
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="备注" isOdd="false">
<!--      <html:text property="operationDes" styleClass="inputtext" style="width:200px"  maxlength="145"/>-->
      <html:textarea property="operationDes" styleClass="inputtext" style="width:200px;height:50px"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">增加</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
