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
	//����ύ
    function addGoBack()
    {
      var url = "${ctx}/consignaction.do?method=listConsign";
      self.location.replace(url);
    }
</script>
<!--[if IE]>
<style>
div {word-break:break-all;;}
</style>
<![endif]-->
<style type="">
div {width:300px;border:solid 1px #000;}
</style>
<body>
<template:titile value="�鿴ʩ��ί����Ϣ" />
<html:form action="/consignaction?method=updateConsign" styleId="addConsignForm" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="entrustdatum" />
<html:hidden property="id"/>
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
<template:formTr name="ί��ʩ����λ����">
<bean:write name="changeinfo" property="entrustunit"/>
</template:formTr>
<template:formTr name="ί��ʩ����λ��ַ">
<bean:write name="changeinfo" property="entrustaddr"/>
</template:formTr>
<template:formTr name="ί��ʩ����λ�绰">
<bean:write name="changeinfo" property="entrustphone"/>
</template:formTr>
<template:formTr name="ί��ʩ����λ��ϵ��">
<bean:write name="changeinfo" property="entrustperson"/>
</template:formTr>
<template:formTr name="ί��ʩ����λ����">
<bean:write name="changeinfo" property="entrustgrade"/>
</template:formTr>
<template:formTr name="�������">
<bean:write name="changeinfo" property="cost"/>��Ԫ
</template:formTr>
<template:formTr name="ʩ����ʼ����">
<bean:write name="changeinfo" property="constartdate"/>
</template:formTr>
<template:formTr name="ί�б�ע">
<div><bean:write name="changeinfo" property="entrustremark"/></div>
</template:formTr>
<template:formTr name="����">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changeinfo" property="entrustdatum">
      <apptag:listAttachmentLink fileIdList=""/>
      </logic:empty>
      <logic:notEmpty name="changeinfo" property="entrustdatum">
        <bean:define id="temp" name="changeinfo" property="entrustdatum" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"/>
      </logic:notEmpty>
    </td>
	<td></td>
	</tr>
</table>
</template:formTr>
<template:formSubmit>
	<td>
		<input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
</body>
</html>
