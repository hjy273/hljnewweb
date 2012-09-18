<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
<!--

function checkData(){
	if(mainForm.userid.value == null || mainForm.userid.value.length == 0){
		alert("请输入用户名！");
		mainForm.userid.focus();
		return false;
	}
	if(mainForm.password.value == null || mainForm.password.value.length == 0){
		alert("请输入密码！");
		mainForm.password.focus();
		return false;
	}
	if(mainForm.confirmpasswrod.value == null || mainForm.confirmpasswrod.value.length == 0){
		alert("请确认密码！");
		mainForm.confirmpasswrod.focus();
		return false;
	}
	if(mainForm.password.value != mainForm.confirmpasswrod.value ){
		alert("密码与确认密码不符，请重新输入！");
		mainForm.password.focus();
		return false;
	}
	if(mainForm.initregionid.value == null || mainForm.initregionid.value.length == 0){
		alert("请选择初始区域！");
		mainForm.initregionid.focus();
		return false;
	}
	if(mainForm.initdeptid.value == null || mainForm.initdeptid.value.length == 0){
		alert("请输入初始部门名称！");
		mainForm.initdeptid.focus();
		return false;
	}

	//区域名称
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

<template:titile value="初始化系统页面，请使用一次后删除该页！"/>

<form name="mainForm" method="Post" action="${ctx}/InitDataAction.do?method=initData" target = "newwindow" onsubmit="openwin()">
<template:formTable>
  <template:formTr name="初始用户名">
    <input name="userid" type="text" class="inputtext" style="width:120" maxlength="10">
    (英文、数字构成，少于10位)
</template:formTr>
  <template:formTr name="初始密码" isOdd="false">
    <input name="password" type="password" class="inputtext" style="width:120" maxlength="10">
    (英文、数字构成，少于10位)
</template:formTr>
  <template:formTr name="确认初始密码">
    <input name="confirmpasswrod" type="password" class="inputtext" style="width:120" maxlength="10">
    (英文、数字构成，少于10位)
</template:formTr>
  <template:formTr name="初始区域" isOdd="false">
	<input type="hidden" name="initregionname">
    <select name="initregionid" class="inputtext" style="width:120">
      <option value="110000">北京市</option>
      <option value="120000">天津市</option>
      <option value="130000">河北省</option>
      <option value="140000">山西省</option>
      <option value="150000">内蒙古自治区</option>
      <option value="210000">辽宁省</option>
      <option value="220000">吉林省</option>
      <option value="230000">黑龙江省</option>
      <option value="310000">上海市</option>
      <option value="320000">江苏省</option>
      <option value="330000">浙江省</option>
      <option value="340000">安徽省</option>
      <option value="350000">福建省</option>
      <option value="360000">江西省</option>
      <option value="370000">山东省</option>
      <option value="410000">河南省</option>
      <option value="420000">湖北省</option>
      <option value="430000">湖南省</option>
      <option value="440000">广东省</option>
      <option value="450000">广西壮族自治区</option>
      <option value="460000">海南省</option>
      <option value="500000">重庆市</option>
      <option value="510000">四川省</option>
      <option value="520000">贵州省</option>
      <option value="530000">云南省</option>
      <option value="540000">西藏自治区</option>
      <option value="610000">陕西省</option>
      <option value="620000">甘肃省</option>
      <option value="630000">青海省</option>
      <option value="640000">宁夏回族自治区</option>
      <option value="650000">新疆维吾尔自治区</option>
      <option value="710000">台湾省</option>
      <option value="810000">香港特别行政区</option>
      <option value="820000">澳门特别行政区</option>
    </select>
  </template:formTr>
  <template:formTr name="初始部门名称">
    <input name="initdeptid" type="text" class="inputtext" style="width:120">
  </template:formTr>
  <template:formSubmit>
    <td>
      <input name="Button2" type="button" class="button" value="增加" onclick="saveInitData()">
    </td>
    <td>
      <input name="Button2" type="reset" class="button" value="取消">
    </td>
  </template:formSubmit>
</template:formTable>
</form>
