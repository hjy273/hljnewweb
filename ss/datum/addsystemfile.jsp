<%@include file="/common/header.jsp"%>
<html>
<head>
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
	
</script>
</head>
<body>
<template:titile value="��Ӵ�ά�ƶ�" />
<html:form action="/DatumSystemAction?method=addDatumSystem"  onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<template:formTr name="��ά�ƶ�����">
<html:text property="documentname" styleClass="inputtext" style="width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="��ά�ƶ�����">
<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="documenttype" valueName="documenttypecoll"  condition="category='1' "/>
<html:select property="documenttype" styleClass="inputtext" style="width:160">
  <html:options collection="documenttypecoll" property="value" labelProperty="label"/>
</html:select>
</template:formTr>
<template:formTr name="��ά�ƶ�����">
<html:textarea property="description" cols="36" onkeyup="this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="������Ϣ�250������"/>
</template:formTr>
<template:formTr name="��Ч��">
<html:text property="validatetime" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:250;" maxlength="26" />
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
<script>
function validate(theForm){
		if(theForm.documentname.value==""){
			alert("��ά�ƶ����Ʋ���Ϊ�գ�");
			return false;
		}
		if(fileNum == 0 ){
			alert("��������Ϊ�գ�");
			return false;
		}
	}
</script>
</html>