<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linechange.bean.*;"%>
<html>
	<script type="text/javascript" src="js/prototype.js"></script>
<script language="javascript">
	showHistoryDetail=function(value){
		var actionUrl="${ctx}/checkaction.do?method=showCheckHistory&change_id="+value+"&rnd="+Math.random();
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
         if(form.checkdate.value == ""){
        alert("�������ڲ��ܵ��ڿգ�");
        return false;
      }
    }
    //��֤
    function validate(form){
      var r= false;
      if(form.checkresult.options.selectedIndex==0){
	      if(form.square.value <= 0.0){
    	    alert("����д�����");
        	return false;
	      }
      }
      if(valCharLength(form.checkremark.value)>512){
            alert("��ע��Ϣ����250�����ֻ���512��Ӣ���ַ�!");
         	return false;
      }
      return true;
      //if(form.ischangedatum.selected)
    }
	//����ύ
    function addGoBack()
    {
      var url = "${ctx}/changeapplyaction.do?method=getApplyInfoList&op=survey";
      self.location.replace(url);
    }
</script>
<body>
<template:titile value="��д������Ϣ" />
<html:form action="/checkaction?method=updateCheck" styleId="addCheckForm" onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="id"/>
<html:hidden property="changeid"/>
<html:hidden property="checkdatum"/>
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
<template:formTr name="�������">
<bean:write name="changeinfo" property="cost"/>��Ԫ
</template:formTr>
<template:formTr name="�ƻ�����">
<bean:write name="changeinfo" property="plantime"/>��
</template:formTr>


<template:formTr name="ʩ����ʼ����">
<bean:write name="build" property="starttime"/>
</template:formTr>
<template:formTr name="ʩ����������">
<bean:write name="build" property="endtime"/>
</template:formTr>
<template:formTr name="ʩ����λ����">
<bean:write name="build" property="buildunit"/>
</template:formTr>
<template:formTr name="ʩ����λ��ַ">
<bean:write name="build" property="buildaddr"/>
</template:formTr>
<template:formTr name="ʩ����λ��ϵ��">
<bean:write name="build" property="buildperson"/>
</template:formTr>
<template:formTr name="ʩ����λ�绰">
<bean:write name="build" property="buildphone"/>
</template:formTr>
<template:formTr name="ʩ����ע">
<div><bean:write name="build" property="buildremark"/></div>
</template:formTr>
<template:formTr name="ʩ������">
<div><bean:write name="build" property="buildvalue"/></div>
</template:formTr>
<template:formTr name="����">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="build" property="builddatum">
      <apptag:listAttachmentLink fileIdList=""/>
      </logic:empty>
      <logic:notEmpty name="build" property="builddatum">
        <bean:define id="temp" name="build" property="builddatum" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"/>
      </logic:notEmpty>
    </td>
	<td></td>
	</tr>
</table>
</template:formTr>
    <tr>
		<td colspan="3" id="applyAuditingTd"></td>
	</tr>


<template:formTr name="��������">
<html:text property="checkdate" styleClass="inputtext" style="width:250;" readonly="true" maxlength="26" />
<apptag:date property="checkdate" />
</template:formTr>
<template:formTr name="���ո�����">
<html:text property="checkperson" styleClass="inputtext" style="width:250;" maxlength="10" />
</template:formTr>
<template:formTr name="���ս��">
<html:select property="checkresult" styleClass="inputtext" style="width:160">
         <html:option value="ͨ����">ͨ����</html:option>
          <html:option value="δͨ��">δͨ��</html:option>
</html:select></template:formTr>
<template:formTr name="���ձ�ע">
<html:textarea property="checkremark" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="��ע��Ϣ�250������"/>
</template:formTr>


<template:formTr name="���̽���">
<html:text property="square" styleClass="inputtext" style="width:250;" maxlength="8" />��Ԫ
</template:formTr>


<template:formTr name="����">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changecheck" property="checkdatum">
      </logic:empty>
      <logic:notEmpty name="changecheck" property="checkdatum">
        <bean:define id="temp" name="changecheck" property="checkdatum" type="java.lang.String"/>
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
		<html:button property="action" styleClass="button" onclick="addGoBack()">����</html:button>
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
			<script type="text/javascript">
			showHistoryDetail("<bean:write name="changeinfo" property="id" />");
			</script>
</body>
</html>


