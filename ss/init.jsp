<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
<!--

function checkData(){
	if(mainForm.userid.value == null || mainForm.userid.value.length == 0){
		alert("�������û�����");
		mainForm.userid.focus();
		return false;
	}
	if(mainForm.password.value == null || mainForm.password.value.length == 0){
		alert("���������룡");
		mainForm.password.focus();
		return false;
	}
	if(mainForm.confirmpasswrod.value == null || mainForm.confirmpasswrod.value.length == 0){
		alert("��ȷ�����룡");
		mainForm.confirmpasswrod.focus();
		return false;
	}
	if(mainForm.password.value != mainForm.confirmpasswrod.value ){
		alert("������ȷ�����벻�������������룡");
		mainForm.password.focus();
		return false;
	}
	if(mainForm.initregionid.value == null || mainForm.initregionid.value.length == 0){
		alert("��ѡ���ʼ����");
		mainForm.initregionid.focus();
		return false;
	}
	if(mainForm.initdeptid.value == null || mainForm.initdeptid.value.length == 0){
		alert("�������ʼ�������ƣ�");
		mainForm.initdeptid.focus();
		return false;
	}

	//��������
	mainForm.initregionname.value = mainForm.initregionid.options[mainForm.initregionid.options.selectedIndex].text;

	return true;
}

function saveInitData(){
	if(checkData()){

		mainForm.submit();
	}
}

function openwin() {
	newwin=window.open ("", "newwindow", "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");

	newwin.moveTo(0,0);
	newwin.resizeTo(screen.width,screen.height);

	newwin.focus();
}


//-->
</script>

<template:titile value="��ʼ��ϵͳҳ�棬��ʹ��һ�κ�ɾ����ҳ��"/>

<form name="mainForm" method="Post" action="${ctx}/InitDataAction.do?method=initData" target = "newwindow" onsubmit="openwin()">
<template:formTable>
  <template:formTr name="��ʼ�û���">
    <input name="userid" type="text" class="inputtext" style="width:120" maxlength="10">
    (Ӣ�ġ����ֹ��ɣ�����10λ)
</template:formTr>
  <template:formTr name="��ʼ����" isOdd="false">
    <input name="password" type="password" class="inputtext" style="width:120" maxlength="10">
    (Ӣ�ġ����ֹ��ɣ�����10λ)
</template:formTr>
  <template:formTr name="ȷ�ϳ�ʼ����">
    <input name="confirmpasswrod" type="password" class="inputtext" style="width:120" maxlength="10">
    (Ӣ�ġ����ֹ��ɣ�����10λ)
</template:formTr>
  <template:formTr name="��ʼ����" isOdd="false">
	<input type="hidden" name="initregionname">
    <select name="initregionid" class="inputtext" style="width:120">
      <option value="110000">������</option>
      <option value="120000">�����</option>
      <option value="130000">�ӱ�ʡ</option>
      <option value="140000">ɽ��ʡ</option>
      <option value="150000">���ɹ�������</option>
      <option value="210000">����ʡ</option>
      <option value="220000">����ʡ</option>
      <option value="230000">������ʡ</option>
      <option value="310000">�Ϻ���</option>
      <option value="320000">����ʡ</option>
      <option value="330000">�㽭ʡ</option>
      <option value="340000">����ʡ</option>
      <option value="350000">����ʡ</option>
      <option value="360000">����ʡ</option>
      <option value="370000">ɽ��ʡ</option>
      <option value="410000">����ʡ</option>
      <option value="420000">����ʡ</option>
      <option value="430000">����ʡ</option>
      <option value="440000">�㶫ʡ</option>
      <option value="450000">����׳��������</option>
      <option value="460000">����ʡ</option>
      <option value="500000">������</option>
      <option value="510000">�Ĵ�ʡ</option>
      <option value="520000">����ʡ</option>
      <option value="530000">����ʡ</option>
      <option value="540000">����������</option>
      <option value="610000">����ʡ</option>
      <option value="620000">����ʡ</option>
      <option value="630000">�ຣʡ</option>
      <option value="640000">���Ļ���������</option>
      <option value="650000">�½�ά���������</option>
      <option value="710000">̨��ʡ</option>
      <option value="810000">����ر�������</option>
      <option value="820000">�����ر�������</option>
    </select>
  </template:formTr>
  <template:formTr name="��ʼ��������">
    <input name="initdeptid" type="text" class="inputtext" style="width:120">
  </template:formTr>
  <template:formSubmit>
    <td>
      <input name="Button2" type="button" class="button" value="����" onclick="saveInitData()">
    </td>
    <td>
      <input name="Button2" type="reset" class="button" value="ȡ��">
    </td>
  </template:formSubmit>
</template:formTable>
</form>
