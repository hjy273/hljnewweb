<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
//是否选取了任务
var ifCheckedFlag = 0;
var plantype = 7;

function checkInput(){
	var yb = document.batchBean.startdate.value.substring(0,4);
    var mb = parseInt(document.batchBean.startdate.value.substring(5,7) ,10) - 1;
    var db = parseInt(document.batchBean.startdate.value.substring(8,10),10);
    var tdate = new Date();
    var indate = new Date(yb,mb,db);
    var de = parseInt(document.batchBean.enddate.value.substring(8,10),10);
    var tdate = new Date();
    var indate = new Date(yb,mb,db);
    if(de-db > 15){
    	alert("计划周期不能大于15天！");
    	return false;
    }else if(document.batchBean.batchname.value == ""){
          alert("请填写批量计划名称！");
          document.batchBean.batchname.focus();
        return false;
    }else if(document.batchBean.startdate.value == ""){
        alert("请选择计划开始日期 ！");
        document.batchBean.startdate.focus();
        return false;
    }else if(document.batchBean.enddate.value == ""){
        alert("请选择计划结束日期 ！");
        document.batchBean.enddate.focus();
        return false;
    }else if(indate < tdate){
		alert("开始日期不能小于当前日期！");
		return false;
	}else
    	return true;
}

function savePlan(){
    if(checkInput()){
    	document.batchBean.submit();
    }
}

// 选择日期
function GetSelectDateTHIS(strID) {
   
   // document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    if(document.getElementById(strID).value !="" && document.getElementById(strID).value !=null){
      if(checkBDate()){
        //preGetWeeklyTaskList();
        setPlanName();
      }
    }
    return true;
}


function checkBDate(){
    var yb = document.batchBean.startdate.value.substring(0,4);
    var mb = parseInt(document.batchBean.startdate.value.substring(5,7) ,10) - 1;
    var db = parseInt(document.batchBean.startdate.value.substring(8,10),10);
    var date = new Date(yb,mb,db);
    if(plantype == 7){
    	date = new Date(yb,mb,db + 6);	
    }if(plantype == 10)
        date = new Date(yb,mb,db + 9);
    else if(plantype > 10)
        date = new Date(yb,mb,db + 14);
    else if(plantype > 10)
        date = new Date(yb,mb,db + 9);
    y = date.getYear();
    m = date.getMonth() + 1;
    d = date.getDate();
//确保日期不能超过月末
    if(parseInt(m) != mb+1){
       m=mb+1;
       if(m == 2){//是2月
             if(y %400 ==0 ||(y %4 == 0  && y % 100 !=0))
                d=29;
            else
                d=28;
       }else if( m== 1 || m== 3||m== 5||m== 7||m== 8 ||m== 10||m== 12){
         d=31;
       }else{
            d=30;
       }
    }
    if(m < 10){
        m = "0" + m;
    }
    if(d < 10){
        d = "0" + d;
    }
    document.batchBean.enddate.value = yb + "/" + m + "/" + d;
    return true;
}
function GetSelectDate(strID) {
    if(document.batchBean.startdate.value =="" || document.batchBean.startdate.value ==null){
        alert("还没有开始日期!");
        document.batchBean.startdate.focus();
        return false;
    }
    document.getElementById(strID).value = getPopDate(document.getElementById(strID).value);
    //开始时间
    var yb = document.batchBean.startdate.value.substring(0,4);
    var mb = parseInt(document.batchBean.startdate.value.substring(5,7) ,10);
    var db = parseInt(document.batchBean.startdate.value.substring(8,10),10);
    //结束时间
    var ye = document.batchBean.enddate.value.substring(0,4);
    var me = parseInt(document.batchBean.enddate.value.substring(5,7) ,10);
    var de = parseInt(document.batchBean.enddate.value.substring(8,10),10);
    if(de-db > 15){
    	alert("计划周期不能大于15天！");
    	return false;
    }
    if(yb!=ye){
      alert("计划不能跨年!");
      document.getElementById(strID).value="";
      document.batchBean.enddate.focus();
      return false;
    }
    if(mb!=me){
      alert("计划不能跨月!");
      document.getElementById(strID).value="";
      document.batchBean.enddate.focus();
      return false;
    }
     if(de < db || me < mb || ye < yb){
      alert("结束日期不能小于开始日期!");
      document.getElementById(strID).value="";
      document.batchBean.enddate.focus();
      return false;
    }
    return true;
}
function setPlanName(weekNumber){
    var newWeekNumber = 1;
    var obj = document.getElementById("deptname");

    var y = document.batchBean.startdate.value.substring(0,4);
    var m = document.batchBean.startdate.value.substring(5,7);
    var d = parseInt(document.batchBean.startdate.value.substring(8,10),10);
    var planname = "";
    if(plantype == 7){
    	if(d >=1 && d<=7)
        	newWeekNumber = 1;
    	if(d >=8 && d<=14)
        	newWeekNumber = 2;
     	if(d >=15 && d<=21)
        	newWeekNumber = 3;
     	if(d >=22 && d<=28)
        	newWeekNumber = 4;
     	if(d >=29 && d<=31)
        	newWeekNumber = 5;
     	planname =obj.value + y + "年"+ m + "月第" + newWeekNumber + "周批量巡检计划";
     	//batchBean.batchname.value = planname;
     }else if(plantype == 10){
        if(d >=1 && d<=10)
            newWeekNumber ="上";
        if(d >=11 && d<=20)
            newWeekNumber = "中";
         if(d >=21 )
            newWeekNumber ="下";
        planname =obj.value + y + "年"+ m + "月" + newWeekNumber + "旬批量巡检计划";
    }else if(plantype == 15){
         if(d >=1 && d<=15)
            newWeekNumber = "上";
        if(d >=16 )
            newWeekNumber = "下";
        planname =obj.value + y + "年"+ m + "月" + newWeekNumber + "半月批量巡检计划";
    }else if(plantype == 0){
        planname =obj.value + y + "年"+ m + "月自定义批量巡检计划";
    }

    document.batchBean.batchname.value = planname;
    document.batchBean.taskopname.value = planname;
    //set hidden id
    

   
    //alert(batchBean.id.value);
}

 function settype(obj){
        plantype = obj.value;
        document.batchBean.startdate.value="";
        document.batchBean.enddate.value="";
 }
function setopname(){
	var opname = document.all.IdOp.options[document.all.IdOp.selectedIndex].text
	document.all.IdName.value=opname;
}
//-->
</script>
<body onload="">
<br>
<template:titile value="批量制定巡检计划"/>
<html:form action="/batchPlan.do?method=addBatchPlan" onsubmit="return checkInput()">
	<input  id="deptname" type="hidden"  name="deptname" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>
	
  <template:formTable>
    <input type="hidden" name="patrolmode" value="01"/>
    <!-- 
     <template:formTr name="巡检方式" >
    	<select name="patrolmode" class="inputtext" style="width:250px">
    		<option value="01">手动方式</option>
    		<option value="02">自动方式</option>
    	</select>
    </template:formTr> -->
    <template:formTr name="计划类型" >
    	<input  type="radio"  name="batchtype" value="7"  checked="checked" onclick="settype(this)" /> 周
        <input  type="radio"  name="batchtype" value="10" onclick="settype(this)" /> 旬
       
        <input  type="radio"  name="batchtype" value="15" onclick="settype(this)"/> 半月
         <!-- <input  type="radio"  name="batchtype" value="0" onclick="settype(this)"/> 自定义
         -->
    </template:formTr>
    
    <template:formTr name="开始日期" >
        <html:text property="startdate" styleId="startdate" styleClass="Wdate" style="width:225px;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'});" onchange="GetSelectDateTHIS('startdate')" readonly="true"/>
    </template:formTr>
    
    <template:formTr name="结束日期" >
        <html:text property="enddate" styleId="enddate" styleClass="Wdate" style="width:225px;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'});" readonly="true"/>
    </template:formTr>
    <template:formTr name="批量计划名称" >
      <html:text property="batchname" styleId="batchname" styleClass="inputtext" style="width:250px"/>
      <html:hidden property="taskopname"/>
    </template:formTr>
    <template:formSubmit>
     
      <td>
        <html:button property="action" styleClass="button" onclick="savePlan()">下一步</html:button>
      </td>
      
     
    </template:formSubmit>
  </template:formTable>
</html:form>
<div align="center">
 	  <table width="620" border="0" align="center">
        <tr style="color:red">
          <td width="7%" scope="col" valign="top">说明：</td>
          <td width="94%" scope="col">
         1、建议您在制定计划时首先考虑周计划或旬计划。<br>
         2、批量计划制定只针对计划模板制定，若没有制定计划模板请先制定模板。<br>
         3、为了提高您的工作效率、减轻您的工作负担，这里为您更加方便、快捷的批量制定计划功能。<br>
         4、同一条线段不能包含在两个以上子任务中。此约束仅适用于当前计划模板。<br>
         5、您不能制定当前日期以前的计划，即开始日期不能小于等于当前日期。<br>
      	 6、同一巡检组（人）的计划执行时间段不能交叉，即当前计划的开始时间不能跨入上一计划内。<br>
      	 7、同一巡检组同一时间段内不能重复制定计划。<br>	 
      	 8、巡检计划不能跨月<br>
		  </td>
        </tr>
      </table>
   </div>

</body>
