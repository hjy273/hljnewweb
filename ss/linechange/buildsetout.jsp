<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linechange.bean.*;"%>
<html>
<script language="javascript">
	function valCharLength(Value){
      var j=0;
      var s = Value;
      for   (var   i=0;   i<s.length;   i++)
      {
        if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
        else   j++
      }
      return j;
    }
    fileNum=0;
	 //�ű����ɵ�ɾ����  ť��ɾ������
	function deleteRow(){
      	//��ð�ť�����е�id
     	var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
         //��idת��Ϊ�������е�����,��ɾ��
      	for(i =0; i<uploadID.rows.length;i++){
        	if(uploadID.rows[i].id == rowid){
            	uploadID.deleteRow(uploadID.rows[i].rowIndex);
                fileNum--;
            }
        }
    }
    //���һ������
    function addRow(){
    	var  onerow=uploadID.insertRow(-1);
        onerow.id = uploadID.rows.length-2;

        var   cell1=onerow.insertCell();
		var   cell2=onerow.insertCell();


		//����һ�������
        var t1 = document.createElement("input");
        //t1.name = "filename"+onerow.id;//alert(onerow.id);
        t1.name = "uploadFile["+ fileNum + "].file";
		t1.type= "file";
        //t1.style= "width:30px;height:60px"
		t1.id = "text" + onerow.id;
		t1.width="300";
        fileNum++;


        //����ɾ����ť
        var b1 =document.createElement("button");
        b1.value = "ɾ��";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");


        cell1.appendChild(t1);//����
        cell2.appendChild(b1);
	}
	 function valiD(id){
      	var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        var obj = document.getElementById(id);
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("����д�Ĳ�������,����������");
            obj.focus();
            obj.value = "";
            return false;
        }
    }
    //����֤
    function valphone(Value){
      var j=0;
      var s = Value;
      for   (var   i=0;   i<s.length;   i++)
      {
              if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
              else   j++
      }
      //alert("�ַ�����"+j);
      if(j>20){
        alert("�绰���벻�ܴ���20���ַ���");
        return false;
      }else{
        return true;
      }
    }
    function validate1(form){
      var s = form.ephone.value;
      return valphone(s);

    }
    //��֤
    function validate(form){
      var str = /[^\u4E00-\u9FA5]/g;
      var Double = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
      if(!Check(Double,form.sexpense.value)){
        alert("�������ֻ��Ϊ���֣�����Ϊ�����ַ���");
        form.sexpense.value="0";
        return false;
      }
      if(!Check(Double,form.dexpense.value)){
        alert("��Ʒ���ֻ��Ϊ���֣�����Ϊ�����ַ���");
        form.dexpense.value="0";
        return false;
      }
      if(valCharLength(form.setoutremark.value)>512){
            alert("��ע��Ϣ����250�����ֻ���512��Ӣ���ַ�!");
         	return false;
      }
      var s1 = form.sphone.value;
      var s2 = form.dphone.value;
      if(!valphone(s1)){
        form.sphone.focus();
        return false;
      }
       if(!valphone(s2)){
         form.dphone.focus();
         return false;
      }

    }
    //��֤���ַ���
    function CheckEmpty( str )
    {
      return ( str == "" );
    }

    function Check( reg, str )
    {
      if( reg.test( str ) )
      {
        return true;
      }
      return false;
    }
    //��֤���ַ���
    function CheckEmpty( str )
    {
      return ( str == "" );
    }

    function Check( reg, str )
    {
      if( reg.test( str ) )
      {
        return true;
      }
      return false;
    }
	//����ύ
    function addGoBack()
    {
      var url = "${ctx}/buildsetoutaction.do?method=listBuildSetout";
      self.location.replace(url);
    }
</script>
<body>
<logic:equal value="deliverto" name="flow">
  <template:titile value="��дʩ��׼����Ϣ"/>
  <html:form action="/buildsetoutaction?method=updateDeliverTo" onsubmit="return validate1(this);" styleId="addchangeinfoForm" enctype="multipart/form-data">
    <template:formTable namewidth="150" contentwidth="350">
      <html:hidden property="id"/>
      <html:hidden property="type"/>
      <html:hidden property="setoutdatum"/>
      <template:formTr name="��������">
        <bean:write name="changeinfo" property="changename"/>
      </template:formTr>
      <template:formTr name="��������">
        <bean:write name="changeinfo" property="changepro"/>
      </template:formTr>
      <template:formTr name="���̵ص�">
        <bean:write name="changeinfo" property="changeaddr"/>
      </template:formTr>
      <template:formTr name="��������">
        <%=(String)request.getAttribute("line_class_name") %>
      </template:formTr>
      <template:formTr name="Ӱ��ϵͳ">
        <bean:write name="changeinfo" property="involvedSystem"/>
      </template:formTr>
      <template:formTr name="����Ԥ��">
        <bean:write name="changeinfo" property="budget"/>
        ��Ԫ
      </template:formTr>
      <template:formTr name="��������">
        <bean:write name="survey" property="surveydate"/>
      </template:formTr>
      <template:formTr name="����λ����">
        <input name="sname" value="<bean:write name="changeinfo" property="sname" />" 
         class="inputtext" style="width:250;" maxlength="25" type="text" />
      </template:formTr>
      <template:formTr name="����λ��ַ">
        <input name="saddr" value="<bean:write name="changeinfo" property="saddr" />" 
         class="inputtext" style="width:250;" maxlength="50" type="text" />
      </template:formTr>
      <template:formTr name="����λ�绰">
        <input name="sphone" value="<bean:write name="changeinfo" property="sphone" />" 
         class="inputtext" style="width:250;" maxlength="20" type="text" />
      </template:formTr>
      <template:formTr name="����λ��ϵ��">
        <input name="sperson" value="<bean:write name="changeinfo" property="sperson" />" 
         class="inputtext" style="width:250;" maxlength="10" type="text" />
      </template:formTr>
      <template:formTr name="����λ����">
        <select name="sgrade" class="inputtext">
          <option value="�׼�">�׼�</option>
          <option value="�Ҽ�">�Ҽ�</option>
          <option value="����">����</option>
        </select>
      </template:formTr>
      <script type="text/javascript">
        addchangeinfoForm.sgrade.value="<bean:write name="changeinfo" property="sgrade" />";
      </script>
      <template:formTr name="�������">
        <input name="sexpense" value="<bean:write name="changeinfo" property="sexpense" />" 
         class="inputtext" style="width:250;" maxlength="8" type="text" />��Ԫ
      </template:formTr>
      <template:formTr name="������������">
        <html:text property="ename" styleClass="inputtext" style="width:250;" maxlength="25"/>
      </template:formTr>
      <template:formTr name="�������ĵ�ַ">
        <html:text property="eaddr" styleClass="inputtext" style="width:250;" maxlength="50"/>
      </template:formTr>
      <template:formTr name="����������ϵ��">
        <html:text property="eperson" styleClass="inputtext" style="width:250;" maxlength="10"/>
      </template:formTr>
      <template:formTr name="�������ĵ绰">
        <html:text property="ephone" styleClass="inputtext" style="width:250;" maxlength="20"/>
      </template:formTr>
      <template:formTr name="��ע">
        <html:textarea property="setoutremark" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="��ע��Ϣ�250������"/>
      </template:formTr>
      <template:formTr name="����">
        <table id="uploadID" border="0" align="left" cellpadding="0" cellspacing="0">
          <tr class=trcolor>
            <td>
              <logic:empty name="changeinfo" property="setoutdatum">              </logic:empty>
              <logic:notEmpty name="changeinfo" property="setoutdatum">
                <bean:define id="temp" name="changeinfo" property="setoutdatum" type="java.lang.String"/>
                <apptag:listAttachmentLink fileIdList="<%=temp%>" showdele="true"/>
              </logic:notEmpty>
            </td>
            <td>            </td>
          </tr>
        </table>
      </template:formTr>
      <template:formSubmit>
        <td>
          <html:button property="action" styleClass="button" onclick="addRow()">��Ӹ���</html:button>
        </td>
        <td>
          <html:submit styleClass="button">�ύ</html:submit>
        </td>
        <td>
          <html:reset styleClass="button">ȡ��</html:reset>
        </td>
        <td>
         <input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
        </td>
      </template:formSubmit>
    </template:formTable>
  </html:form>
</logic:equal>
<logic:equal value="engage" name="flow">
  <template:titile value="��дʩ��׼����Ϣ"/>
  <html:form action="/buildsetoutaction?method=updateEngage" styleId="addchangeinfoForm" onsubmit="return validate(this);" enctype="multipart/form-data">
    <template:formTable namewidth="150" contentwidth="350">
      <html:hidden property="id"/>
      <html:hidden property="type"/>
      <html:hidden property="setoutdatum"/>
      <template:formTr name="��������">
        <bean:write name="changeinfo" property="changename"/>
      </template:formTr>
      <template:formTr name="��������">
        <bean:write name="changeinfo" property="changepro"/>
      </template:formTr>
      <template:formTr name="���̵ص�">
        <bean:write name="changeinfo" property="changeaddr"/>
      </template:formTr>
      <template:formTr name="��������">
        <%=(String)request.getAttribute("line_class_name") %>
      </template:formTr>
      <template:formTr name="Ӱ��ϵͳ">
        <bean:write name="changeinfo" property="involvedSystem"/>
      </template:formTr>
      <template:formTr name="����Ԥ��">
        <bean:write name="changeinfo" property="budget"/>
        ��Ԫ
      </template:formTr>
      <template:formTr name="��������">
        <bean:write name="survey" property="surveydate"/>
      </template:formTr>
      <template:formTr name="����λ����">
        <input name="sname" value="<bean:write name="changeinfo" property="sname" />" 
         class="inputtext" style="width:250;" maxlength="25" type="text" />
      </template:formTr>
      <template:formTr name="����λ��ַ">
        <input name="saddr" value="<bean:write name="changeinfo" property="saddr" />" 
         class="inputtext" style="width:250;" maxlength="50" type="text" />
      </template:formTr>
      <template:formTr name="����λ�绰">
        <input name="sphone" value="<bean:write name="changeinfo" property="sphone" />" 
         class="inputtext" style="width:250;" maxlength="20" type="text" />
      </template:formTr>
      <template:formTr name="����λ��ϵ��">
        <input name="sperson" value="<bean:write name="changeinfo" property="sperson" />" 
         class="inputtext" style="width:250;" maxlength="10" type="text" />
      </template:formTr>
      <template:formTr name="����λ����">
        <select name="sgrade" class="inputtext">
          <option value="�׼�">�׼�</option>
          <option value="�Ҽ�">�Ҽ�</option>
          <option value="����">����</option>
        </select>
      </template:formTr>
      <script type="text/javascript">
        addchangeinfoForm.sgrade.value="<bean:write name="changeinfo" property="sgrade" />";
      </script>
      <template:formTr name="�������">
        <input name="sexpense" value="<bean:write name="changeinfo" property="sexpense" />" 
         class="inputtext" style="width:250;" maxlength="8" type="text" />��Ԫ
      </template:formTr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <template:formTr name="��Ƶ�λ����">
        <html:text property="dname" styleClass="inputtext" style="width:250;" maxlength="25"/>
      </template:formTr>
      <template:formTr name="��Ƶ�λ��ַ">
        <html:text property="daddr" styleClass="inputtext" style="width:250;" maxlength="50"/>
      </template:formTr>
      <template:formTr name="��Ƶ�λ�绰">
        <html:text property="dphone" styleClass="inputtext" style="width:250;" maxlength="20"/>
      </template:formTr>
      <template:formTr name="��Ƶ�λ��ϵ��">
        <html:text property="dperson" styleClass="inputtext" style="width:250;" maxlength="10"/>
      </template:formTr>
      <template:formTr name="��Ƶ�λ����">
        <html:select property="dgrade" styleClass="inputtext">
          <html:option value="�׼�">�׼�</html:option>
          <html:option value="�Ҽ�">�Ҽ�</html:option>
        </html:select>
      </template:formTr>
      <template:formTr name="��Ʒ���">
        <html:text property="dexpense" styleClass="inputtext" style="width:250;" maxlength="8"/>��Ԫ
      </template:formTr>
      <template:formTr name="��ע">
        <html:textarea property="setoutremark" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="��ע��Ϣ�250������"/>
      </template:formTr>
      <template:formTr name="����">
        <table id="uploadID" border="0" align="left" cellpadding="0" cellspacing="0">
          <tr class=trcolor>
            <td>
              <logic:empty name="changeinfo" property="setoutdatum">              </logic:empty>
              <logic:notEmpty name="changeinfo" property="setoutdatum">
                <bean:define id="temp" name="changeinfo" property="setoutdatum" type="java.lang.String"/>
                <apptag:listAttachmentLink fileIdList="<%=temp%>" showdele="true"/>
              </logic:notEmpty>
            </td>
            <td>            </td>
          </tr>
        </table>
      </template:formTr>
      <template:formSubmit>
        <td>
          <html:button property="action" styleClass="button" onclick="addRow()">��Ӹ���</html:button>
        </td>
        <td>
          <html:submit styleClass="button">�ύ</html:submit>
        </td>
        <td>
          <html:reset styleClass="button">ȡ��</html:reset>
        </td>
        <td>
<input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
        </td>
      </template:formSubmit>
    </template:formTable>
  </html:form>
</logic:equal>
</body>
</html>
