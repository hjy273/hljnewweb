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
    function addGoBack()
    {
      var url = "${ctx}/buildsetoutaction.do?method=listBuildSetout";
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
<logic:equal value="deliverto" name="type">
<template:titile value="�鿴ʩ��׼����Ϣ" />
<html:form action="/buildsetoutaction?method=updateDeliverTo" styleId="addchangeinfoForm" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
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
<template:formTr name="��������">
<bean:write name="survey" property="surveydate"/>
</template:formTr>
<template:formTr name="������������">
<bean:write property="ename" name="changeinfo"/>
</template:formTr>
<template:formTr name="�������ĵ�ַ">
<bean:write property="eaddr" name="changeinfo"/>
</template:formTr>
<template:formTr name="����������ϵ��">
<bean:write property="eperson" name="changeinfo"/>
</template:formTr>
<template:formTr name="�������ĵ绰">
<bean:write property="ephone" name="changeinfo"/>
</template:formTr>
<template:formTr name="��ע">
<bean:write property="setoutremark" name="changeinfo"/>
</template:formTr>
<template:formTr name="����">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changeinfo" property="setoutdatum">
      <apptag:listAttachmentLink fileIdList=""  />
      </logic:empty>
      <logic:notEmpty name="changeinfo" property="setoutdatum">
        <bean:define id="temp" name="changeinfo" property="setoutdatum" type="java.lang.String"/>
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
</logic:equal>
<logic:equal value="engage" name="type">
<template:titile value="�鿴ʩ��׼����Ϣ" />
<html:form action="/buildsetoutaction?method=updateEngage" styleId="addchangeinfoForm" enctype="multipart/form-data">
<html:hidden property="id"/>
<template:formTable namewidth="150" contentwidth="350">
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
<template:formTr name="����λ����">
<bean:write property="sname" name="changeinfo"/>
</template:formTr>
<template:formTr name="����λ��ַ">

<bean:write property="saddr" name="changeinfo"/>
</template:formTr>
<template:formTr name="����λ�绰">
<bean:write property="sphone" name="changeinfo"/>
</template:formTr>
<template:formTr name="����λ��ϵ��">
<bean:write property="sperson" name="changeinfo"/>
</template:formTr>
<template:formTr name="����λ����">
<bean:write property="sgrade" name="changeinfo"/>
</template:formTr>
<template:formTr name="�������">
<bean:write property="sexpense" name="changeinfo"/>��Ԫ
</template:formTr>
<tr><td>&nbsp;</td>
</tr>
<template:formTr name="��Ƶ�λ����">
<bean:write property="dname" name="changeinfo"/>
</template:formTr>
<template:formTr name="��Ƶ�λ��ַ">
<bean:write property="daddr" name="changeinfo"/>
</template:formTr>
<template:formTr name="��Ƶ�λ�绰">
<bean:write property="dphone" name="changeinfo"/>
</template:formTr>
<template:formTr name="��Ƶ�λ��ϵ��">
<bean:write property="dperson" name="changeinfo"/>
</template:formTr>
<template:formTr name="��Ƶ�λ����">
<bean:write property="dgrade" name="changeinfo"/>
</template:formTr>
<template:formTr name="��Ʒ���">
<bean:write property="dexpense" name="changeinfo"/>��Ԫ
</template:formTr>
<template:formTr name="��ע">
<bean:write property="setoutremark" name="changeinfo"/>
</template:formTr>
<template:formTr name="����">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changeinfo" property="setoutdatum">
       <apptag:listAttachmentLink fileIdList=""  />
      </logic:empty>
      <logic:notEmpty name="changeinfo" property="setoutdatum">
        <bean:define id="temp" name="changeinfo" property="setoutdatum" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>" />
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
</logic:equal>
</body>
</html>

