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
    function validate(form){
      var str = /[^\u4E00-\u9FA5]/g;
      var Double = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
      if(!Check(Double,form.cost.value)){
        alert("�������ӦΪ���֣�����Ϊ�����ַ���");
        form.cost.value="0";
        return false;
      }
      var s1 = form.entrustphone.value;
      if(!valphone(s1)){
        form.entrustphone.focus();
        return false;
      }
      if(form.constartdate.value == ""){
        alert("ʩ����ʼ���ڲ��ܵ��ڿգ�");
        return false;
      }
      if(valCharLength(form.entrustremark.value)>512){
            alert("ί�б�ע��Ϣ����250�����ֻ���512��Ӣ���ַ�!");
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
	//go back
    function addGoBack()
    {
      var url = "${ctx}/changeapplyaction.do?method=getApplyInfoList&op=survey";
      self.location.replace(url);
    }
</script>
<body>
<template:titile value="��дʩ��ί����Ϣ" />
<html:form action="/consignaction?method=updateConsign" styleId="addConsignForm" onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="entrustdatum" />
<html:hidden property="id"/>
<html:hidden property="type"/>
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
<bean:write name="changeinfo" property="budget"/>��Ԫ
</template:formTr>
<template:formTr name="��������">
<bean:write name="survey" property="surveydate"/>
</template:formTr>
<template:formTr name="ί��ʩ����λ����">
<html:text property="entrustunit" styleClass="inputtext" style="width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="ί��ʩ����λ��ַ">
<html:text property="entrustaddr" styleClass="inputtext" style="width:250;" maxlength="50" />
</template:formTr>
<template:formTr name="ί��ʩ����λ�绰">
<html:text property="entrustphone" styleClass="inputtext" style="width:250;" maxlength="20" />
</template:formTr>
<template:formTr name="ί��ʩ����λ��ϵ��">
<html:text property="entrustperson" styleClass="inputtext" style="width:250;" maxlength="10" />
</template:formTr>
<template:formTr name="ί��ʩ����λ����">
<html:select property="entrustgrade" styleClass="inputtext">
<html:option value="�׼�">�׼�
</html:option>
<html:option value="�Ҽ�">�Ҽ�
</html:option>
<html:option value="����">����
</html:option>
</html:select>
</template:formTr>
<template:formTr name="�������">
<logic:notEmpty name="cost">
<html:text property="cost" styleClass="inputtext" style="width:250;" maxlength="8" />��Ԫ
</logic:notEmpty>
<logic:empty name="cost">
<input name="cost" value="<bean:write name="changeinfo" property="budget" />" type="text" 
  class="inputtext" style="width:250;" maxlength="8" />
 ��Ԫ
</logic:empty>
</template:formTr>
<template:formTr name="ʩ����ʼ����">
<html:text property="constartdate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:250;" maxlength="26" />
</template:formTr>
<template:formTr name="ί�б�ע">
<html:textarea property="entrustremark" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="��ע��Ϣ�250������"/>
</template:formTr>
<template:formTr name="����">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changeinfo" property="entrustdatum">
      </logic:empty>
      <logic:notEmpty name="changeinfo" property="entrustdatum">
        <bean:define id="temp" name="changeinfo" property="entrustdatum" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"  showdele="true"/>
      </logic:notEmpty>
    </td>
	<td></td>
	</tr>
</table>
</template:formTr>
<template:formSubmit>
	<td>
		<html:button property="action" styleClass="button" onclick="addRow()">��Ӹ���</html:button>
	</td>
	<td>
		<html:submit styleClass="button" >�ύ</html:submit>
	</td>
	<td>
		<html:reset styleClass="button">ȡ��	</html:reset>
	</td>
	<td>
		<input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
</body>
</html>
