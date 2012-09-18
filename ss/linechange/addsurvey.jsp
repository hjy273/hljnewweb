<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linechange.bean.*;"%>

<html>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script language="javascript">
	showHistoryDetail=function(value){
		var actionUrl="${ctx}/changesurveyaction.do?method=showSurveyHistory&change_id="+value+"&rnd="+Math.random();
		var myAjax=new Ajax.Updater(
			"applyAuditingTd",actionUrl,{
			method:"post",
			evalScript:true
			}
		);
	}
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
		t1.id = "text" + onerow.id;
		t1.width="200";
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
    //��֤
    function validate(form){
      var str = /[^\u4E00-\u9FA5]/g;
      var Double = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
      if(!Check(Double,form.budget.value)){
        alert("����Ԥ��ֻ��Ϊ����");
        form.budget.value="0";
        return false;
      }
      if(form.surveydate.value == ""){
        alert("�������ڲ��ܵ��ڿգ�");
        return false;
      }
      if(valCharLength(form.surveyremark.value)>512){
            alert("���鱸ע��Ϣ����250�����ֻ���512��Ӣ���ַ�!");
         	return false;
      }
      if(valCharLength(form.approveremark.value)>512){
            alert("��˱�ע��Ϣ����250�����ֻ���512��Ӣ���ַ�!");
         	return false;
      }
      return true;
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
      var url = "${ctx}/changeapplyaction.do?method=getApplyInfoList&op=survey";
      self.location.replace(url);
    }
</script>
<body>
<%
  ChangeInfoBean bean = (ChangeInfoBean) request.getAttribute("changeinfo");
  System.out.println(bean.getChangename());
%>
<template:titile value="��д��������Ϣ"/>

<html:form action="/changesurveyaction?method=addSurvey" styleId="addSurveyForm" onsubmit="return validate(this);" enctype="multipart/form-data">
  <template:formTable namewidth="150" contentwidth="350">
    <input type="hidden" name="changeid" value="<%=request.getAttribute("changeid")%>">
    <template:formTr name="��������">
      <bean:write name="changeinfo" property="changename"/>
    </template:formTr>
    <template:formTr name="���̵ص�">
      <bean:write name="changeinfo" property="changeaddr"/>
    </template:formTr>
    <template:formTr name="��������">
      <bean:write name="changeinfo" property="changepro"/>
    </template:formTr>
    <template:formTr name="Ǩ�ĳ���">
      <bean:write name="changeinfo" property="changelength"/> ��
    </template:formTr>
<template:formTr name="�ƻ�����">
<bean:write name="changeinfo" property="plantime"/>��
</template:formTr>
    <template:formTr name="���븽��">
      <logic:empty name="changeinfo" property="applydatumid">
        <apptag:listAttachmentLink fileIdList=""/>
      </logic:empty>
      <logic:notEmpty name="changeinfo" property="applydatumid">
        <bean:define id="temp" name="changeinfo" property="applydatumid" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"/>
      </logic:notEmpty>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="surveydate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:250;" maxlength="26"/>
    </template:formTr>
    
    
    <template:formTr name="���鸺����">
      <input name="principal" value="<%=request.getAttribute("user_name")%>" 
       class="inputtext" style="width:250;" maxlength="10" type="text" />
    </template:formTr>
    <template:formTr name="����Ԥ��">
      <input name="budget" value="<bean:write name="changeinfo" property="budget" />" 
         class="inputtext" style="width:250;" maxlength="8" type="text" />
      ��Ԫ
    </template:formTr>
    <logic:equal value="1" name="dept_type">
    <template:formTr name="����λ">
      <bean:write name="changeinfo" property="sname"/>
      <html:hidden name="changeinfo" property="sname" />
      <input name="surveytype" value="B2" type="hidden" />
    </template:formTr>
    <template:formTr name="����λ��ַ">
      <bean:write name="changeinfo" property="saddr"/>
      <html:hidden name="changeinfo" property="saddr" />
    </template:formTr>
    <template:formTr name="����λ�绰">
      <bean:write name="changeinfo" property="sphone"/>
      <html:hidden name="changeinfo" property="sphone" />
    </template:formTr>
    <template:formTr name="����λ��ϵ��">
      <bean:write name="changeinfo" property="sperson"/>
      <html:hidden name="changeinfo" property="sperson" />
    </template:formTr>
    <template:formTr name="����λ����">
      <bean:write name="changeinfo" property="sgrade"/>
      <html:hidden name="changeinfo" property="sgrade" />
    </template:formTr>
    <template:formTr name="�������">
      <bean:write name="changeinfo" property="sexpense"/>��Ԫ
      <html:hidden name="changeinfo" property="sexpense" />
    </template:formTr>
    </logic:equal>
    <logic:equal value="2" name="dept_type">
    <template:formTr name="����λ">
      <input name="surveytype" value="B1" type="hidden" />
      <input name="sname" value="<%=request.getAttribute("dept_name")%>" 
       class="inputtext" style="width:250;" maxlength="10" type="text" />
    </template:formTr>
    <template:formTr name="����λ��ַ">
        <html:text property="saddr" styleClass="inputtext" style="width:250;" maxlength="50"/>
    </template:formTr>
    <template:formTr name="����λ�绰">
        <html:text property="sphone" styleClass="inputtext" style="width:250;" maxlength="20"/>
    </template:formTr>
    <template:formTr name="����λ��ϵ��">
      <input name="sperson" value="<%=request.getAttribute("user_name")%>" 
       class="inputtext" style="width:250;" maxlength="10" type="text" />
    </template:formTr>
    <template:formTr name="����λ����">
        <html:select property="sgrade" styleClass="inputtext">
          <html:option value="�׼�">�׼�</html:option>
          <html:option value="�Ҽ�">�Ҽ�</html:option>
          <html:option value="����">����</html:option>
        </html:select>
    </template:formTr>
    <template:formTr name="�������">
        <html:text property="sexpense" styleClass="inputtext" style="width:250;" maxlength="8"/>��Ԫ
    </template:formTr>
    </logic:equal>
    
    <logic:equal value="B1" name="changeinfo" property="step">
    <template:formTr name="�����鱸ע">
		<bean:write name="change_survey" property="surveyremark"/>
    </template:formTr>
    <template:formTr name="�����鸽��">
      <logic:empty name="change_survey" property="surveydatum">
      </logic:empty>
      <logic:notEmpty name="change_survey" property="surveydatum">
        <bean:define id="temp" name="change_survey" property="surveydatum" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"/>
      </logic:notEmpty>
    </template:formTr>
    <template:formTr name="�����󶨽��">
		<bean:write name="change_survey" property="approveresult"/>
    </template:formTr>
    <template:formTr name="��������">
		<%=(String)request.getAttribute("approver_supervise") %>
    </template:formTr>
    <template:formTr name="�����󶨱�ע">
		<bean:write name="change_survey" property="approveremark"/>
    </template:formTr>
    </logic:equal>
    
    
    <template:formTr name="���鱸ע">
      <textarea name="surveyremark" cols="36" onkeyup="//this.value=this.value.substr(0,250)" 
      	rows="5" style="width:250" class="textarea" title="��ע��Ϣ�250������"></textarea>
    </template:formTr>
    <template:formTr name="���鸽��">
      <table id="uploadID" border="0" align="left" cellpadding="0" cellspacing="0">
        <tr class=trcolor>
          <td>          </td>
        </tr>
      </table>
    </template:formTr>
    <tr>
		<td colspan="3" id="applyAuditingTd"></td>
	</tr>
    <tr>
      <td colspan="3" align="center">&nbsp;</td>
    </tr>
    <template:formTr name="�󶨽��">
      <select name="approveresult" class="inputtext" style="width:160">
        <option value="ͨ����">ͨ����</option>
        <option value="δͨ��">δͨ��</option>
      </select>
    </template:formTr>
    <template:formTr name="�� �� ��">
      <input name="approver" value="<%=(String)request.getAttribute("user_name") %>" type="text" class="inputtext" style="width:250;" maxlength="10"/>
    </template:formTr>
    <template:formTr name="��ע">
      <textarea name="approveremark" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" class="textarea" title="��ע��Ϣ�250������"/></textarea>
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
</body>
			<script type="text/javascript">
			showHistoryDetail("<bean:write name="changeinfo" property="id" />");
			</script>
</html>
