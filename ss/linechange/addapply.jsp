
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
	//����ύ
    //��֤
    function validate(form){
      var str = /[^\u4E00-\u9FA5]/g;
      var Double = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
      if(form.changename.value == ""){
        alert("�������Ʋ���Ϊ�գ�����д��");
        form.changename.focus();
        return false;
      }
      if(!Check(Double,form.changelength.value)){
        alert("����ֻ��Ϊ���֣�����Ϊ�����ַ���");
        form.changelength.value="0";
        return false;
      }
      if(!Check(Double,form.plantime.value)){
        alert("�ƻ�����ֻ�������֣�");
        form.plantime.value="0";
        return false;
      }
      if(form.startdate.value == ""){
        alert("��ʼ���ڲ��ܵ��ڿգ�");
        return false;
      }
      if(valCharLength(form.remark.value)>512){
            alert("��ע��Ϣ����250�����ֻ���512��Ӣ���ַ�!");
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
      var url = "${ctx}/changeapplyaction.do?method=getApplyInfoList";
      self.location.replace(url);
    }
</script>
<body>
<template:titile value="��д������Ϣ" />
<html:form action="/changeapplyaction?method=addApply" styleId="addApplyForm" onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<template:formTr name="��������">
<html:text property="changename" styleClass="inputtext" style="width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="��������">
<html:text property="changepro" styleClass="inputtext" style="width:250;" maxlength="15" />
</template:formTr>
<template:formTr name="���̵ص�">
<html:text property="changeaddr" styleClass="inputtext" style="width:250;" maxlength="50" />
</template:formTr>
<template:formTr name="��������">
<apptag:setSelectOptions columnName1="name" columnName2="code" tableName="lineclassdic" valueName="linetypeColl"/>
<html:select property="lineclass" styleClass="inputtext" style="width:160">
  <html:options collection="linetypeColl" property="value" labelProperty="label"/>
</html:select>
</template:formTr>
<template:formTr name="Ӱ��ϵͳ">
<html:text property="involvedSystem" styleClass="inputtext" style="width:250;" maxlength="100" />
</template:formTr>
<template:formTr name="Ǩ�ĳ���">
<html:text property="changelength" styleClass="inputtext" style="width:250;" value="0" maxlength="8" />��
</template:formTr>
<template:formTr name="��ʼʱ��">
<html:text property="startdate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:250;" maxlength="26" />
</template:formTr>
<template:formTr name="�ƻ�����">
<html:text property="plantime" styleClass="inputtext" style="width:250;" maxlength="5" />��
</template:formTr>

<template:formTr name="����Ԥ��">
<html:text property="budget" styleClass="inputtext" style="width:250;" maxlength="8"/>
��Ԫ
</template:formTr>
					<template:formTr name="����˾">
						<select name="superviseUnitId" class="inputtext" style="width: 180px">
							<logic:present name="supervise_units">
								<logic:iterate id="supervise_unit" name="supervise_units">
									<option
										value="<bean:write name="supervise_unit" property="contractorid"/>">
										<bean:write name="supervise_unit" property="contractorname" />
									</option>
								</logic:iterate>
							</logic:present>
						</select>
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

<template:formTr name="ʩ����λ����">
<html:text property="buildunit" styleClass="inputtext" style="width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="ʩ����ʼ����">
<html:text property="starttime" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:250;" readonly="true" maxlength="26" />
</template:formTr>

<template:formTr name="��ע��Ϣ">
<html:textarea property="remark" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="��ע��Ϣ�250������"/>
</template:formTr>
<template:formTr name="����">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
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
		<html:button property="action" styleClass="button" onclick="addGoBack()">����</html:button>
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
</body>
</html>
