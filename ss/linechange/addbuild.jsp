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
    }

   //����֤
    function validate(form){
      var j=0;
      var s = form.buildphone.value;
      for   (var   i=0;   i<s.length;   i++)
      {
              if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
              else   j++
      }
      //alert("�ַ�����"+j);
      if(j>20){
        alert("�绰���벻�ܴ���20���ַ���");
        return false;
      }
      if(form.starttime.value == ""){
        alert("ʩ����ʼ���ڲ��ܵ��ڿգ�");
        return false;
      }
      if(form.endtime.value == ""){
        alert("ʩ���������ڲ��ܵ��ڿգ�");
        return false;
      }
      if(valCharLength(form.buildremark.value)>512){
            alert("ʩ����ע��Ϣ����250�����ֻ���512��Ӣ���ַ�!");
         	return false;
      }
      if(valCharLength(form.buildvalue.value)>512){
            alert("ʩ��������Ϣ����250�����ֻ���512��Ӣ���ַ�!");
         	return false;
      }
    }
	//����ύ
    function addGoBack()
    {
      var url = "${ctx}/buildaction.do?method=getChangeInfo";
      self.location.replace(url);
    }
</script>
<body>
<template:titile value="��дʩ����Ϣ" />
<html:form action="/buildaction.do?method=addBuild" styleId="addBuildForm" onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="changeid"/>
<html:hidden property="id"/>
<html:hidden property="builddatum"/>
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
<template:formTr name="ί��ʩ����λ�绰">
<bean:write name="changeinfo" property="entrustphone"/>
</template:formTr>
<template:formTr name="ί��ʩ����λ��ϵ��">
<bean:write name="changeinfo" property="entrustperson"/>
</template:formTr>


<template:formTr name="ʩ����ʼ����">
<input name="starttime" value="<bean:write name="changeinfo" property="starttime" />"  
 class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:250;" readonly maxlength="26" />
</template:formTr>


<template:formTr name="ʩ����������">
<html:text property="endtime" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:250;" readonly="true" maxlength="26" />
</template:formTr>


<template:formTr name="ʩ����λ����">
<input name="buildunit" value="<bean:write name="changeinfo" property="buildunit" />"  
 class="inputtext" style="width:250;" maxlength="25"/>
</template:formTr>


<template:formTr name="ʩ����λ��ַ">
<html:text property="buildaddr" styleClass="inputtext" style="width:250;" maxlength="50" />
</template:formTr>
<template:formTr name="ʩ����λ��ϵ��">
<html:text property="buildperson" styleClass="inputtext" style="width:250;" maxlength="10" />
</template:formTr>
<template:formTr name="ʩ����λ�绰">
<html:text property="buildphone" styleClass="inputtext" style="width:250;" maxlength="20" />
</template:formTr>
<template:formTr name="ʩ����ע">
<html:textarea property="buildremark" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="��ע��Ϣ�250������"/>
</template:formTr>
<template:formTr name="ʩ������">
<html:textarea property="buildvalue" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="��ע��Ϣ�250������"/>
</template:formTr>
<template:formTr name="����">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changebuild" property="builddatum">
      </logic:empty>
      <logic:notEmpty name="changebuild" property="builddatum">
        <bean:define id="temp" name="changebuild" property="builddatum" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"  showdele="true"/>
      </logic:notEmpty>
    </td>
	<td></td>
	</tr>
</table>
</template:formTr>
    <tr>
		<td colspan="3" id="applyAuditingTd"></td>
	</tr>
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
			<script type="text/javascript">
			showHistoryDetail("<bean:write name="changeinfo" property="id" />");
			</script>
</body>
</html>


