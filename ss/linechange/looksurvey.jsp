<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linechange.bean.*;"%>
<html>
	<script type="text/javascript" src="js/prototype.js"></script>
<script language="javascript">
	showHistoryDetail=function(value){
		var actionUrl="${ctx}/changesurveyaction.do?method=showSurveyHistory&show_type=all&change_id="+value+"&rnd="+Math.random();
		var myAjax=new Ajax.Updater(
			"applyAuditingTd",actionUrl,{
			method:"post",
			evalScript:true
			}
		);
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
	//����ύ
    function addGoBack()
    {
      var url = "${ctx}/changesurveyaction.do?method=getSurveyInfoList";
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
<template:titile value="�鿴��������Ϣ" />
<html:form action="/changesurveyaction?method=updateSurvey" styleId="addSurveyForm" enctype="multipart/form-data">
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
<template:formTr name="Ǩ�ĳ���">
<bean:write name="changeinfo" property="changelength"/>��
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
<html:hidden property="surveydatum" />
<html:hidden property="changeid"/>
<template:formTr name="��������">
<bean:write name="changesurvey" property="surveydate"/>
</template:formTr>
<template:formTr name="���鸺����">
<bean:write name="changesurvey" property="principal"/>
</template:formTr>
<template:formTr name="����Ԥ��">
<bean:write name="changesurvey" property="budget"/>��Ԫ
</template:formTr>


    <template:formTr name="����λ">
      <bean:write name="changeinfo" property="sname"/>
    </template:formTr>
    <template:formTr name="����λ��ַ">
      <bean:write name="changeinfo" property="saddr"/>
    </template:formTr>
    <template:formTr name="����λ�绰">
      <bean:write name="changeinfo" property="sphone"/>
    </template:formTr>
    <template:formTr name="����λ��ϵ��">
      <bean:write name="changeinfo" property="sperson"/>
    </template:formTr>
    <template:formTr name="����λ����">
      <bean:write name="changeinfo" property="sgrade"/>
    </template:formTr>
    <template:formTr name="�������">
      <bean:write name="changeinfo" property="sexpense"/>��Ԫ
    </template:formTr>


<logic:notEmpty name="survey_list">
	<logic:iterate id="change_survey" name="survey_list">
<template:formTr name="�����鱸ע">
<div><bean:write name="change_survey" property="surveyremark"/></div>
</template:formTr>
<template:formTr name="�����鸽��">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="change_survey" property="surveydatum">
      </logic:empty>
      <logic:notEmpty name="change_survey" property="surveydatum">
        <bean:define id="temp" name="change_survey" property="surveydatum" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"/>
      </logic:notEmpty>
    </td>
	<td></td>
	</tr>
</table>
</template:formTr>
<template:formTr name="�������󶨽��">
<bean:write name="change_survey" property="approveresult"/>
</template:formTr>
<template:formTr name="����������">
<bean:write name="change_survey" property="approver"/>
</template:formTr>
<template:formTr name="������������">
<bean:write name="change_survey" property="approvedate"/>
</template:formTr>
<template:formTr name="�������󶨱�ע">
<div><bean:write name="change_survey" property="approveremark"/></div>
</template:formTr>
	</logic:iterate>
</logic:notEmpty>


<template:formTr name="���鱸ע">
<div><bean:write name="changesurvey" property="surveyremark"/></div>
</template:formTr>
<template:formTr name="����">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changesurvey" property="surveydatum">
      </logic:empty>
      <logic:notEmpty name="changesurvey" property="surveydatum">
        <bean:define id="temp" name="changesurvey" property="surveydatum" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"/>
      </logic:notEmpty>
    </td>
	<td></td>
	</tr>
</table>
</template:formTr>
<tr>
      <td colspan="3" align="center">&nbsp;</td>
</tr>
    <tr>
		<td colspan="3" id="applyAuditingTd"></td>
	</tr>
<template:formTr name="�󶨽��">
<bean:write name="changesurvey" property="approveresult"/>
</template:formTr>
<template:formTr name="�� �� ��">
<bean:write name="changesurvey" property="approver"/>
</template:formTr>
<template:formTr name="������">
<bean:write name="changesurvey" property="approvedate"/>
</template:formTr>
<template:formTr name="�󶨱�ע">
<div><bean:write name="changesurvey" property="approveremark"/></div>
</template:formTr>
<template:formSubmit>
	<td>
		<input type="button" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
			<script type="text/javascript">
			showHistoryDetail("<bean:write name="changeinfo" property="id" />");
			</script>
</body>
</html>


