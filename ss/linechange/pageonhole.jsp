<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linechange.bean.*;"%>
<html>
<script language="javascript">

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
      var r= false;
      for(i=0;i<form.ischangedatum.length;i++){
        if(form.ischangedatum[i].checked){
          r = true;
        }
      }
      if(r==false){
        alert("��ѡ���Ƿ���Ҫ�޸����ϣ�");
        return false;
      }
      if(form.square.value <= 0.0){
        alert("����д�����");
        return false;
      }
      //if(form.ischangedatum.selected)
    }
    //����ύ
    function addGoBack()
    {
      var url = "${ctx}/pageonholeaction.do?method=getChangeInfo";
      self.location.replace(url);
    }
</script>
<body>
<template:titile value="��д�鵵��Ϣ" />
<html:form action="/pageonholeaction.do?method=updatePageonhole" onsubmit="return validate(this);" styleId="addChangeForm" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="id"/>
<html:hidden property="pageonholedatum"/>
<html:hidden property="pageonholedatum"/>
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
<template:formTr name="����Ԥ��">
<bean:write name="changeinfo" property="budget"/>��Ԫ
</template:formTr>
<template:formTr name="Ǩ�ĳ���">
<bean:write name="changeinfo" property="changelength"/>��
</template:formTr>
<template:formTr name="�������">
<bean:write name="changeinfo" property="cost"/>��Ԫ
</template:formTr>
<template:formTr name="�ƻ�����">
<bean:write name="changeinfo" property="plantime"/>��
</template:formTr>
<template:formTr name="���̽���">
<html:text property="square" styleClass="inputtext" style="width:250;" maxlength="8" />
</template:formTr>
<template:formTr name="�Ƿ���Ҫ�޸�����">
<html:radio property="ischangedatum"  value="��">��
</html:radio>
<html:radio property="ischangedatum"  value="��">��
</html:radio>
</template:formTr>
<template:formTr name="�鵵����">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changeinfo" property="pageonholedatum">
      </logic:empty>
      <logic:notEmpty name="changeinfo" property="pageonholedatum">
        <bean:define id="temp" name="changeinfo" property="pageonholedatum" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"  showdele="true"/>
      </logic:notEmpty>
    </td>
	<td></td>
	</tr>
</table>
</template:formTr>
<template:formTr name="�鵵��ע">
<html:textarea property="pageonholeremark" cols="36" onkeyup="this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="��ע��Ϣ�250������"/>
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

    <!--
		<html:button property="action" styleClass="button" onclick="addGoBack()">����</html:button>
	-->
    </td>
</template:formSubmit>
</template:formTable>
</html:form>
</body>
</html>


