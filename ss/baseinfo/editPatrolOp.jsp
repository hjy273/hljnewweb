<%@include file="/common/header.jsp"%>
<!-- ZSH 2004-10-13 -->
<script language="javascript">
<!--
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
function isValidForm() {

	if(document.PatrolOpBean.operationdes.value.length == 0||document.PatrolOpBean.operationdes.value.trim().length==0){
		alert("事故名称不能为空或者空格!! ");
        PatrolOpBean.operationdes.value="";
		PatrolOpBean.operationdes.focus();
		return false;
	}
    document.PatrolOpBean.operationdes.value=document.PatrolOpBean.operationdes.value.trim();

	return true;
}

 //检验是否数字
    function valiD(id){
    	var mysplit = /\d\d/;
        var obj = document.getElementById(id);
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("你填写的不是数字,请重新输入");
             obj.focus();
             obj.value = "";
        }
    }
function toGetBack(){
        var url = "${ctx}/PatrolOpAction.do?method=queryPatrolOp&operationdes=";
        self.location.replace(url);

}

//-->
</script>
<br><template:titile value="修改巡检事故码信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/PatrolOpAction.do?method=updatePatrolOp">
  <template:formTable >
    <template:formTr name="事故类型" isOdd="false">
      <html:hidden property="regionid"/>
      <html:hidden property="operationcode"/>
      <html:select property="optype" styleClass="inputtext" style="width:200px" disabled="true">

        <html:option value="1">标石</html:option>
        <html:option value="2">人井</html:option>
        <html:option value="3">电杆</html:option>
        <html:option value="4">其他事故</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="事故码">
      <html:text property="subcode" styleId="id"  onblur="valiD(id)" styleClass="inputtext" style="width:200px" maxlength="2" disabled="true"/>
      &nbsp;
      ( 不含类别信息 )
</template:formTr>
    <template:formTr name="事故名称" isOdd="false">
      <html:text property="operationdes" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <template:formTr name="响应周期">
		<html:text property="noticecycle" styleClass="inputtext"  style="width:200px" maxlength="45"/>
        &nbsp;(单位是小时)
    </template:formTr>
    <template:formTr name="处理周期" isOdd="false">
		<html:text property="handlecycle" styleClass="inputtext"  style="width:200px" maxlength="45"/>
        &nbsp;(单位是小时)
    </template:formTr>
    <template:formTr name="事故级别">
      <html:select property="emergencylevel" styleClass="inputtext" style="width:200px">
        <html:option value="1">轻微</html:option>
        <html:option value="2">中度</html:option>
        <html:option value="3">严重</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="备注" isOdd="false">
      <html:text property="remark" styleClass="inputtext" style="width:200px" maxlength="145"/>
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
