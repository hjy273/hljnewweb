<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
<!--
//是否选取了任务
var ifCheckedFlag = 0;

function checkInput(){

	try{
		for(var i = 0; i < YearMonthPlanBean.taskcheck.length; i ++){
			if(YearMonthPlanBean.taskcheck[i].checked == true)
				ifCheckedFlag++;
		}
	}catch(e){}

	try{
		if(YearMonthPlanBean.taskcheck.checked == true)
				ifCheckedFlag++;

	}catch(e){}


	return true;
}


function preSetPlanName(){
    var obj = document.getElementById("deptname");
	var preSetName = "巡检计划";
	var yi = YearMonthPlanBean.year.selectedIndex;
	if(yi == null){
		yi = 0;
	}

	var mi = YearMonthPlanBean.month.selectedIndex;
	if(mi == null){
		mi = 0;
	}

	preSetName = obj.value + YearMonthPlanBean.year.options[yi].value + "年度" + parseInt(YearMonthPlanBean.month.options[mi].value, 10) + "月份" + preSetName;
	YearMonthPlanBean.planname.value = preSetName;

}



//-->
</script>
<body onload="preSetPlanName()">
<br>
<template:titile value="增加月巡检计划"/>
<html:form action="/YearMonthPlanAction?method=createYMPlan" onsubmit="return checkInput()">
	 <input  id="deptname" type="hidden"  name="regionid" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>
		<input id="plantype" type="hidden" name="plantype" value="2"/>
  <template:formTable >
		    <template:formTr name="计划年度" isOdd="false">
					<apptag:getYearOptions />
				      <html:select property="year" styleClass="inputtext" style="width:200px" onchange="preSetPlanName();">
				       <html:options collection="yearCollection" property="value" labelProperty="label"/>
				      </html:select>
		    </template:formTr>
		    <template:formTr name="计划月份">
				      <html:select property="month" styleClass="inputtext" style="width:200px" onchange="preSetPlanName();">
				        <html:option value="01">1 月</html:option>
				        <html:option value="02">2 月</html:option>
				        <html:option value="03">3 月</html:option>
				        <html:option value="04">4 月</html:option>
				        <html:option value="05">5 月</html:option>
				        <html:option value="06">6 月</html:option>
				        <html:option value="07">7 月</html:option>
				        <html:option value="08">8 月</html:option>
				        <html:option value="09">9 月</html:option>
				        <html:option value="10">10 月</html:option>
				        <html:option value="11">11 月</html:option>
				        <html:option value="12">12 月</html:option>
				      </html:select>
		    </template:formTr>
		    <template:formTr name="计划名称" isOdd="false">
				      <html:text property="planname" styleClass="inputtext" style="width:200px"/>
					  <html:hidden property="remark" value="1"/> <!-- 新计划 -->
					  <html:hidden property="status" value="0"/>
		    </template:formTr>
           
			 <template:formSubmit>
		    	  <td>
		    	  	<html:submit property="action" styleClass="button">增加任务</html:submit>
		    	  </td>
			      <td>
			        <input name="Button2" type="reset" class="button" value="取消">
			      </td>
		    </template:formSubmit>

	  </template:formTable>
</html:form>


</body>