<%@include file="/common/header.jsp"%>

<script language="javascript" type="">
  function checkvalue(){
     var beginDate = document.getElementById("beginDate").value;
     var endDate = document.getElementById("endDate").value;
     if (beginDate == ""){
         alert("��ѡ��ʼ����");
         return false;
     }
     if (endDate == ""){
         alert("��ѡ���������");
         return false;
     }
     var startyear = beginDate.substring(0,4);
     var startmonth = parseInt(beginDate.substring(5,7) ,10) - 1;
     var startday = parseInt(beginDate.substring(8,10),10);
     var thestartdate = new Date(startyear,startmonth,startday);

     var endyear = endDate.substring(0,4);
     var endmonth = parseInt(endDate.substring(5,7) ,10) - 1;
     var endday = parseInt(endDate.substring(8,10),10);
     var theenddate = new Date(endyear,endmonth,endday);
     var todaydate = new Date();
     if(theenddate < thestartdate){
		alert("�������ڲ���С�ڿ�ʼ���ڣ�");
		return false;
	 }else if(thestartdate > todaydate){
		alert("��ʼ���ڲ��ܴ��ڵ�ǰ���ڣ�");
		return false;    
     }else if(theenddate > todaydate){
		alert("�������ڲ��ܴ��ڵ�ǰ���ڣ�");
		return false;
     }
     return true;    
  }

</script>
<Br/><Br/><Br/>
<template:titile value="�Ӳ�����ȡͼƬ"/>
<html:form action="/WatchPicAction.do?method=showLinkUploadPic" onsubmit="return checkvalue();">
  <template:formTable contentwidth="200" namewidth="200" >
    <template:formTr name="��ʼ����" isOdd="false">
      <html:text readonly="true" property="beginDate" styleClass="inputtext" style="width:200" maxlength="12"/>
      <apptag:date property="beginDate"/>
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
      <html:text readonly="true" property="endDate" styleClass="inputtext" style="width:200" maxlength="12"/>
      <apptag:date property="endDate"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">����</html:reset>
      </td>
    </template:formSubmit>

  </template:formTable>
</html:form>
<br/>

<br/>
<template:titile value="�ӱ����ϴ�ͼƬ" />
<html:form action="/WatchPicAction?method=addWatchPic"  onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
  <template:formTr name="����">
     <apptag:Attachment state="add" fileIdList=""></apptag:Attachment>
  </template:formTr>

<template:formSubmit>
	<td>
		<html:button property="action" styleClass="button" onclick="addRow()">��Ӹ���</html:button>
	</td>
	<td>
		<html:submit styleClass="button" >�ϴ�</html:submit>
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
<script language="javascript" type="">
function validate(theForm){
		if(fileNum == 0 ){
			alert("��������Ϊ�գ�");
			return false;
		}
}
function addGoBack(){
	var url = "${ctx}/watchAction.do?method=queryWatch";
	self.location.replace(url);
}
</script>

