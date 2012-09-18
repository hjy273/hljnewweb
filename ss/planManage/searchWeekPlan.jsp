<%@ include file="/common/header.jsp"%>
<SCRIPT language=javascript src="${ctx}/js/prototype.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/js/parsexml.js" type=""></SCRIPT>


<body>
<br>
<template:titile value="检索巡检计划" />

<html:form action="/PlanAction?method=queryPlan">
	<template:formTable contentwidth="300" namewidth="150">
		<ct:linkage lable2="巡检维护组" lable1="代维单位" lable0="所属区域" evenUrl1="${ctx}/CommonTagAction.do?sqlkey=patrolsql" evenUrl0="${ctx}/CommonTagAction.do?sqlkey=conListSql" name2="executorid" name1="workID" name0="regionid"></ct:linkage>
		<template:formTr name="开始日期">
			<html:text property="begindate" styleClass="Wdate"
				style="width:225;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'});"/>
		</template:formTr>
		<template:formTr name="结束日期">
			<html:text property="enddate" styleClass="Wdate"
				style="width:225;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'});"/>
		</template:formTr>

		<template:formSubmit>
			<td>
				<html:submit property="action" styleClass="button">检索</html:submit>
			</td>
			<td>
				<input name="Button2" type="reset" class="button" value="取消">
			</td>
			<td>
				<html:button property="action" styleClass="button"
					onclick="addGoBack()">返回</html:button>
			</td>
		</template:formSubmit>

	</template:formTable>
</html:form>

<script language="javascript" type="text/javascript">
function GetSelectDateTHIS(strID) {
    document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    return checkBDate();
}

function GetSelectDate(strID) {
    if(planBean.begindate.value =="" || planBean.begindate.value ==null){
    	alert("还没有开始日期!");
        planBean.begindate.focus();
        return false;
    }
    document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    //开始时间
    var yb = planBean.begindate.value.substring(0,4);
	var mb = parseInt(planBean.begindate.value.substring(5,7) ,10);
	var db = parseInt(planBean.begindate.value.substring(8,10),10);
    //结束时间
    var ye = planBean.enddate.value.substring(0,4);
	var me = parseInt(planBean.enddate.value.substring(5,7) ,10);
	var de = parseInt(planBean.enddate.value.substring(8,10),10);
    if(yb!=ye){
      alert("不能跨年!");
      document.all.item(strID).value="";
       planBean.enddate.focus();
      return false;
    }
    if(mb!=me){
      alert("不能跨月!");
      document.all.item(strID).value="";
      planBean.enddate.focus();
      return false;
    }
     if(de < db || me < mb || ye < yb){
      alert("结束日期不能小于开始日期!");
      document.all.item(strID).value="";
      planBean.enddate.focus();
      return false;
    }
	return true;
}
function checkBDate(){
	var y = planBean.begindate.value.substring(0,4);
	var m = parseInt(planBean.begindate.value.substring(5,7) ,10) - 1;
	var d = parseInt(planBean.begindate.value.substring(8,10),10);

	var date = new Date(y,m,d);
	date = new Date(y,m,d + 6);
	y = date.getYear();
	m = date.getMonth() + 1;

	if(m < 10){
		m = "0" + m;
	}

	d = date.getDate();

	if(d < 10){
		d = "0" + d;
	}

	planBean.enddate.value = y + "/" + m + "/" + d;
	planBean.enddate.value = "";
	return true;
}
	function addGoBack(){
    	try{
            	location.href = "${ctx}/PlanAction.do?method=queryPlan";
                return true;
            }
            catch(e){
            	alert(e);
            }
    }


</script>
</body>
