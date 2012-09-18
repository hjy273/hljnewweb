<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
//是否选取了任务
var ifCheckedFlag = 0;
var plantype = 7;

function checkInput(){
    //开始时间
    var yb = parseInt($('planBean').begindate.value.substring(0,4) ,10);
    var mb = parseInt($('planBean').begindate.value.substring(5,7) ,10);
    var db = parseInt($('planBean').begindate.value.substring(8,10),10);
    //结束时间
    var ye = parseInt($('planBean').enddate.value.substring(0,4) ,10);
    var me = parseInt($('planBean').enddate.value.substring(5,7) ,10);
    var de = parseInt($('planBean').enddate.value.substring(8,10),10);
    var tdate = new Date();
    var indate = new Date(yb,mb-1,db);
    if(yb!=ye){
      	alert("计划不能跨年!");
		document.getElementById(strID).value="";
       	$('planBean').enddate.focus();
      	return false;
    }
    if(mb!=me){
      	alert("计划不能跨月!");
		document.getElementById(strID).value="";
      	$('planBean').enddate.focus();
      	return false;
    }
    if(de-db > 15){
    	alert("计划周期不能大于15天！");
    	return false;
    }else if($('planBean').executorid.value == ""){
        alert("请选择计划执行人 ！");
        $('planBean').executorid.focus();
        return false;
    }else if($('planBean').begindate.value == ""){
        alert("请选择计划开始日期 ！");
        $('planBean').begindate.focus();
        return false;
    }else if($('planBean').enddate.value == ""){
        alert("请选择计划结束日期 ！");
        $('planBean').enddate.focus();
        return false;
    }else if(indate < tdate){
    	alert("开始日期不能早于当前日期！");
		return false;
	}else
    	return true;
}

function savePlan(isload){
    if(checkInput()){
    	$('planBean').isload.value= isload;
        $('planBean').submit();
    }
}

// 选择日期
function GetSelectDateTHIS(strID) {
	if($('planBean').executorid.value == ""){
		alert("请选择巡检维护组 ！");
		$('planBean').executorid.focus();
		document.getElementById(strID).value="";
   		return false;
    }
    if(document.getElementById(strID).value !="" && document.getElementById(strID).value != null){
      	if(checkBDate()){
        	setPlanName();
      	}
    }
    return true;
}


function checkBDate(){
    var yb = $('planBean').begindate.value.substring(0,4);
    var mb = parseInt($('planBean').begindate.value.substring(5,7) ,10) - 1;
    var db = parseInt($('planBean').begindate.value.substring(8,10),10);
    var date = new Date(yb,mb,db);
    
    if(plantype == 7){
    	date = new Date(yb,mb,db + 6);	
    }if(plantype == 10)
        date = new Date(yb,mb,db + 9);
    else if(plantype == 15)
        date = new Date(yb,mb,db + 14);
    else if(plantype == 30)
        date = new Date(yb,mb,db + 31);
    //y = date.getYear();
    m = date.getMonth() + 1;
    d = date.getDate();
	
	//确保日期不能超过月末
    if(parseInt(m) != mb+1){
       m=mb+1;
       if(m == 2){//是2月
             if(yb %400 ==0 ||(yb %4 == 0  && yb % 100 !=0))
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
    $('planBean').enddate.value = yb + "/" + m + "/" + d;
    return true;
}
function GetSelectDate(strID) {
    if($('planBean').begindate.value =="" || $('planBean').begindate.value ==null){
        alert("还没有开始日期!");
        $('planBean').begindate.focus();
        return false;
    }
    document.getElementById(strID).value = getPopDate(document.getElementById(strID).value);
    //开始时间
    var yb = $('planBean').begindate.value.substring(0,4);
    var mb = parseInt($('planBean').begindate.value.substring(5,7) ,10);
    var db = parseInt($('planBean').begindate.value.substring(8,10),10);
    //结束时间
    var ye = $('planBean').enddate.value.substring(0,4);
    var me = parseInt(planBean.enddate.value.substring(5,7) ,10);
    var de = parseInt($('planBean').enddate.value.substring(8,10),10);
    if(de-db > 15){
    	alert("计划周期不能大于15天！");
    	return false;
    }
    if(yb!=ye){
      alert("计划不能跨年!");
      document.getElementById(strID).value="";
       $('planBean').enddate.focus();
      return false;
    }
    if(mb!=me){
      alert("计划不能跨月!");
      document.getElementById(strID).value="";
      $('planBean').enddate.focus();
      return false;
    }
     if(de < db || me < mb || ye < yb){
      alert("结束日期不能小于开始日期!");
      document.getElementById(strID).value="";
      $('planBean').enddate.focus();
      return false;
    }
    return true;
}
function setPlanName(weekNumber){
    var newWeekNumber = 1;
    var obj = document.getElementById("deptname");
    var i = $('planBean').executorid.selectedIndex;
    if(i == null){
        i = 0;
    }

    var y = $('planBean').begindate.value.substring(0,4);
    var m = $('planBean').begindate.value.substring(5,7);
    var d = parseInt($('planBean').begindate.value.substring(8,10),10);
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
     	planname =obj.value + $('planBean').executorid.options[i].text + y + "年"+ m + "月第" + newWeekNumber + "周巡检计划";
     	//$('planBean').planname.value = planname;
     }else if(plantype == 10){
        if(d >=1 && d<=10)
            newWeekNumber ="上";
        if(d >=11 && d<=20)
            newWeekNumber = "中";
         if(d >=21 )
            newWeekNumber ="下";
        planname =obj.value + $('planBean').executorid.options[i].text + y + "年"+ m + "月" + newWeekNumber + "旬巡检计划";
    }else if(plantype == 15){
         if(d >=1 && d<=15)
            newWeekNumber = "上";
        if(d >=16 )
            newWeekNumber = "下";
        planname =obj.value + $('planBean').executorid.options[i].text + y + "年"+ m + "月" + newWeekNumber + "半月巡检计划";
    }else if(plantype == 0){
        planname =obj.value + $('planBean').executorid.options[i].text + y + "年"+ m + "月自定义巡检计划";
    }

    $('planBean').planname.value = planname;
    //set hidden id
    if(weekNumber < 10){
        weekNumber = "0"+ weekNumber;
    }

    $('planBean').id.value = $('planBean').executorid.options[i].value + y + m +"0"+ newWeekNumber;
    //alert($('planBean').id.value);
}

 function settype(obj){
        plantype = obj.value;
        $('planBean').begindate.value="";
        $('planBean').enddate.value="";
 }

//-->
</script>
<body onload="">
<br>
<template:titile value="增加巡检计划"/>
<html:form action="/PlanAction?method=createPlan" styleId="planBean" onsubmit="return checkInput()">
  <input  id="deptname" type="hidden"  name="regionid" value="<bean:write name="LOGIN_USER_DEPT_NAME"/>"/>
 <input type="hidden" name="isload" />
  <template:formTable >
    <logic:equal value="group" name="PMType">
        <template:formTr name="巡检维护组" isOdd="false">
                <select  name="executorid"  Class="inputtext" style="width:250px">
                    <option value="">请选择计划巡检维护组</option>
                      <logic:present name="patrolgroup">
                        <logic:iterate id="pgId" name="patrolgroup">
                            <option value="<bean:write name="pgId" property="patrolid"/>"><bean:write name="pgId" property="patrolname"/></option>
                        </logic:iterate>
                      </logic:present>
                </select>
                <!-- hidden id -->
                <html:hidden property="id"/>
                
          </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
        <template:formTr name="执 行 人" isOdd="false">
                <select  name="executorid"  Class="inputtext" style="width:250px">
                    <option value="">请选择计划执行人</option>
                      <logic:present name="patrolgroup">
                        <logic:iterate id="pgId" name="patrolgroup">
                            <option value="<bean:write name="pgId" property="patrolid"/>"><bean:write name="pgId" property="patrolname"/></option>
                        </logic:iterate>
                      </logic:present>
                </select>
                <!-- hidden id -->
                <html:hidden property="id"/>
          </template:formTr>
    </logic:notEqual>
    <input type="hidden" name="patrolmode" value="01"/>
    <!--  template:formTr name="巡检方式"  -->
    	<!-- select name="patrolmode" class="inputtext" style="width:250px">
    		<option value="01">手动方式</option>
    		<option value="02">自动方式</option>
    	</select -->
    <!-- /template:formTr -->
    <template:formTr name="计划类型" >
    	<input  type="radio"  name="plantype" value="7"  checked="checked" onclick="settype(this)" /> 周
         <input  type="radio"  name="plantype" value="10" onclick="settype(this)" /> 旬
        
        <input  type="radio"  name="plantype" value="15" onclick="settype(this)"/> 半月
         <!--  <input  type="radio"  name="plantype" value="0" onclick="settype(this)"/> 自定义-->
        
    </template:formTr>
	

    <template:formTr name="开始日期" >
        <html:text property="begindate" styleClass="Wdate" styleId="begindate" style="width:225px;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'});" onchange="GetSelectDateTHIS('begindate')" readonly="true"/>
    </template:formTr>
    <template:formTr name="结束日期" >
        <html:text property="enddate" styleId="enddate" styleClass="Wdate" style="width:225px;"  onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'begindate\')}'});" readonly="true"/>
    </template:formTr>
    <template:formTr name="计划名称" >
      <html:text property="planname" styleClass="inputtext" style="width:250px"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:button property="action" styleClass="button" onclick="savePlan('n')">增加任务</html:button>
      </td>
      <td>
        <html:button property="action" styleClass="button" onclick="savePlan('y')">导入任务</html:button>
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
         2、为了更加方便、快捷制定计划，可以在制定计划前先制定计划模板。模板制定见帮助“计划模板制定”.<br>
         3、巡检计划不能跨月<br>
         4、您不能制定当前日期以前的计划，即开始日期不能小于等于当前日期。<br>
      	 5、同一巡检组（人）的计划执行时间段不能交叉，即当前计划的开始时间不能跨入上一计划内。<br>
      	 6、同一巡检组同一时间段内不能重复制定计划。<br>
      	 6、同一条线段不能包含在两个以上子任务中,否则将有可能影响到您的巡检率。此约束仅适用于当前计划。<br>		 
		  </td>
        </tr>
      </table>
   </div>
</body>
