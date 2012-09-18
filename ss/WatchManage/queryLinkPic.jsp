<%@include file="/common/header.jsp"%>

<script language="javascript" type="">
  function checkvalue(){
     var beginDate = document.getElementById("beginDate").value;
     var endDate = document.getElementById("endDate").value;
     if (beginDate == ""){
         alert("请选择开始日期");
         return false;
     }
     if (endDate == ""){
         alert("请选择结束日期");
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
		alert("结束日期不能小于开始日期！");
		return false;
	 }else if(thestartdate > todaydate){
		alert("开始日期不能大于当前日期！");
		return false;    
     }else if(theenddate > todaydate){
		alert("结束日期不能大于当前日期！");
		return false;
     }
     return true;    
  }

</script>
<Br/><Br/><Br/>
<template:titile value="从彩信提取图片"/>
<html:form action="/WatchPicAction.do?method=showLinkUploadPic" onsubmit="return checkvalue();">
  <template:formTable contentwidth="200" namewidth="200" >
    <template:formTr name="开始日期" isOdd="false">
      <html:text readonly="true" property="beginDate" styleClass="inputtext" style="width:200" maxlength="12"/>
      <apptag:date property="beginDate"/>
    </template:formTr>
    <template:formTr name="结束日期" isOdd="false">
      <html:text readonly="true" property="endDate" styleClass="inputtext" style="width:200" maxlength="12"/>
      <apptag:date property="endDate"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">重置</html:reset>
      </td>
    </template:formSubmit>

  </template:formTable>
</html:form>
<br/>

<br/>
<template:titile value="从本地上传图片" />
<html:form action="/WatchPicAction?method=addWatchPic"  onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
  <template:formTr name="附件">
     <apptag:Attachment state="add" fileIdList=""></apptag:Attachment>
  </template:formTr>

<template:formSubmit>
	<td>
		<html:button property="action" styleClass="button" onclick="addRow()">添加附件</html:button>
	</td>
	<td>
		<html:submit styleClass="button" >上传</html:submit>
	</td>
	<td>
		<html:reset styleClass="button">取消	</html:reset>
	</td>
	<td>
		<html:button property="action" styleClass="button" onclick="addGoBack()">返回</html:button>
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
<script language="javascript" type="">
function validate(theForm){
		if(fileNum == 0 ){
			alert("附件不能为空！");
			return false;
		}
}
function addGoBack(){
	var url = "${ctx}/watchAction.do?method=queryWatch";
	self.location.replace(url);
}
</script>

