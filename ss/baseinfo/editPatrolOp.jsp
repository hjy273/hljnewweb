<%@include file="/common/header.jsp"%>
<!-- ZSH 2004-10-13 -->
<script language="javascript">
<!--
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
function isValidForm() {

	if(document.PatrolOpBean.operationdes.value.length == 0||document.PatrolOpBean.operationdes.value.trim().length==0){
		alert("�¹����Ʋ���Ϊ�ջ��߿ո�!! ");
        PatrolOpBean.operationdes.value="";
		PatrolOpBean.operationdes.focus();
		return false;
	}
    document.PatrolOpBean.operationdes.value=document.PatrolOpBean.operationdes.value.trim();

	return true;
}

 //�����Ƿ�����
    function valiD(id){
    	var mysplit = /\d\d/;
        var obj = document.getElementById(id);
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("����д�Ĳ�������,����������");
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
<br><template:titile value="�޸�Ѳ���¹�����Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/PatrolOpAction.do?method=updatePatrolOp">
  <template:formTable >
    <template:formTr name="�¹�����" isOdd="false">
      <html:hidden property="regionid"/>
      <html:hidden property="operationcode"/>
      <html:select property="optype" styleClass="inputtext" style="width:200px" disabled="true">

        <html:option value="1">��ʯ</html:option>
        <html:option value="2">�˾�</html:option>
        <html:option value="3">���</html:option>
        <html:option value="4">�����¹�</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="�¹���">
      <html:text property="subcode" styleId="id"  onblur="valiD(id)" styleClass="inputtext" style="width:200px" maxlength="2" disabled="true"/>
      &nbsp;
      ( ���������Ϣ )
</template:formTr>
    <template:formTr name="�¹�����" isOdd="false">
      <html:text property="operationdes" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <template:formTr name="��Ӧ����">
		<html:text property="noticecycle" styleClass="inputtext"  style="width:200px" maxlength="45"/>
        &nbsp;(��λ��Сʱ)
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
		<html:text property="handlecycle" styleClass="inputtext"  style="width:200px" maxlength="45"/>
        &nbsp;(��λ��Сʱ)
    </template:formTr>
    <template:formTr name="�¹ʼ���">
      <html:select property="emergencylevel" styleClass="inputtext" style="width:200px">
        <html:option value="1">��΢</html:option>
        <html:option value="2">�ж�</html:option>
        <html:option value="3">����</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="��ע" isOdd="false">
      <html:text property="remark" styleClass="inputtext" style="width:200px" maxlength="145"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="����" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
