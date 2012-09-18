<%@include file="/common/header.jsp"%>

<script language="JavaScript" type="text/javascript">
var fdate = 1;
var val = 1;
var userType = 11;
var QueryCyc = 1;
////////////////////
function checkR(obj){
	var r = 0;
	//var obj=document.getElementsByName("cyc");
	for(var i=0;i<obj.length;i++)
	{
		if(obj[i].checked)
		{
		r = obj[i].value;
		}
	}
	return r;
}
function subClick(){
    var rtype = checkR(document.getElementsByName("type"));
    if(rtype == '1'){
    	var did = queryConditionForm.deptid.options[queryConditionForm.deptid.selectedIndex].value;
		if(did == ""){
			alert("请选择代维单位！");
			return false;
		}
    }
    if(rtype == '2'){
    	var pid = queryConditionForm.patrolid.options[queryConditionForm.patrolid.selectedIndex].value;
		if(pid == ""){
			alert("请选择巡检人！");
			return false;
		}
	}
	if(rtype == '3'){
    	var lid = queryConditionForm.lineid.options[queryConditionForm.lineid.selectedIndex].value;
		if(lid == ""){
			alert("请选择具体巡检线路！");
			return false;
		}
	}
	if(QueryCyc == 1){
	//开始时间
    var yb = queryConditionForm.begindate.value.substring(0,4);
	var mb = parseInt(queryConditionForm.begindate.value.substring(5,7) ,10);
	var db = parseInt(queryConditionForm.begindate.value.substring(8,10),10);
    //结束时间
    var ye = queryConditionForm.enddate.value.substring(0,4);
	var me = parseInt(queryConditionForm.enddate.value.substring(5,7) ,10);
	var de = parseInt(queryConditionForm.enddate.value.substring(8,10),10);
	if( queryConditionForm.begindate.value == "" ){
      alert("没有开始日期!");
      return false;
    }
    if( queryConditionForm.enddate.value == "" ){
      alert("没有结束日期!");
      return false;
    }
    //alert(de +" "+db+" "+de +" " +db);
	if(de < db || me < mb || ye < yb){
      alert("结束日期不能小于开始日期!");
      //document.all.item(strID).value="";
      //queryConditionForm.enddate.focus();
      return false;
    }
    }
    return true;
}
////////////////////////
function setCyc(objN){

	var v = objN.value;

    if(v == "1" ||v == "10" ||v == "15"){
    	QueryCyc = 1;
       	if(v == "1")
      		fdate = 1;
        else if(v == "15")
        	fdate = 15;
        else if(v == "10")
        	fdate = 10;
		yearTr.style.display = "none";
		monthTr.style.display = "none";
		weekBeginTr.style.display = "";
		weekEndTr.style.display = "";
		queryConditionForm.cyctype.value = "week";
        queryConditionForm.begindate.value = "";
     	queryConditionForm.enddate.value = "";

      }

	if(v == "2"){
		QueryCyc = 2;
        fdate = 30;
		yearTr.style.display = "";
		monthTr.style.display = "";
		weekBeginTr.style.display = "none";
		weekEndTr.style.display = "none";
		//queryConditionForm.cyctype.value = "month";
	}


}

function GetSelectDateTHIS(strID) {
	document.all.item(strID).value = getPopDate(document.all.item(strID).value);

	if(checkBDatenew()){
		return true;
	}else{
		return false;
	}
}

function checkBDate(){
	//alert("OK");
	var y = queryConditionForm.begindate.value.substring(0,4);
	var m = parseInt(queryConditionForm.begindate.value.substring(5,7) ,10) - 1;
	var d = parseInt(queryConditionForm.begindate.value.substring(8,10),10);

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

	queryConditionForm.enddate.value = y + "/" + m + "/" + d;

	return true;
}



function setDisable(objN, status){
	objN.disabled = status;
}


function checksub(){
  	setStaObj();
    if(fdate == "1" ||fdate == "10" ||fdate == "15"){
      if(queryConditionForm.begindate.value == "" || queryConditionForm.begindate.value ==null){
        alert("没有开始日期!");
        return false;
      }
    }
    if(fdate == 30){
    	queryConditionForm.begindate.value = queryConditionForm.year.value + "/" + queryConditionForm.month.value + "/1";
    	y = parseInt(queryConditionForm.year.value);
        m = parseInt(queryConditionForm.month.value,10);
        d= 30;
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
        queryConditionForm.enddate.value = y + "/" + m + "/" + d;
      }

     if(subClick()){
	    queryConditionForm.submit();
	  }
}
function setStaObj(){


	//alert(queryConditionForm.queryby.value);

	if(queryConditionForm.queryby.value == "patrol"){

		queryConditionForm.staObj.value = queryConditionForm.patrolid.options[queryConditionForm.patrolid.selectedIndex].text;

	}else if(queryConditionForm.queryby.value == "line"){

		queryConditionForm.staObj.value = queryConditionForm.lineid.options[queryConditionForm.lineid.selectedIndex].text;
	}else{

		queryConditionForm.staObj.value = queryConditionForm.deptid.options[queryConditionForm.deptid.selectedIndex].text;
	}

	//alert(queryConditionForm.staObj.value);

}

function makeSize(enlargeFlag){
	if(enlargeFlag == 0){

		mainSpan.style.display = "none";
		iframeDiv.style.height = "400";
	}else{
		mainSpan.style.display = "";
		iframeDiv.style.height = "280";
	}
}
///
function checkBDatenew(){
	var yb = queryConditionForm.begindate.value.substring(0,4);
	var mb = parseInt(queryConditionForm.begindate.value.substring(5,7) ,10) - 1;
	var db = parseInt(queryConditionForm.begindate.value.substring(8,10),10);
	var date = new Date(yb,mb,db);
    if(fdate == 10)
		date = new Date(yb,mb,db + 9);
    else if(fdate == 15)
    	date = new Date(yb,mb,db + 14);
    else if(fdate == 1)
    	date = new Date(yb,mb,db + 6);
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
    if(queryConditionForm.begindate.value == "")
    	queryConditionForm.enddate.value = "";
    else
		queryConditionForm.enddate.value = y + "/" + m + "/" + d;
	return true;
}
function GetSelectDatenew(strID) {
    if(queryConditionForm.begindate.value =="" || queryConditionForm.begindate.value ==null){
    	alert("还没有开始日期!");
        queryConditionForm.begindate.focus();
        return false;
    }
    document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    //开始时间
    var yb = queryConditionForm.begindate.value.substring(0,4);
	var mb = parseInt(queryConditionForm.begindate.value.substring(5,7) ,10);
	var db = parseInt(queryConditionForm.begindate.value.substring(8,10),10);
    //结束时间
    var ye = queryConditionForm.enddate.value.substring(0,4);
	var me = parseInt(queryConditionForm.enddate.value.substring(5,7) ,10);
	var de = parseInt(queryConditionForm.enddate.value.substring(8,10),10);
	if(ye == "")
		return false;
    if(yb!=ye){
      alert("不能跨年!");
      document.all.item(strID).value="";
       queryConditionForm.enddate.focus();
      return false;
    }
    if(mb!=me){
      alert("不能跨月!");
      document.all.item(strID).value="";
      queryConditionForm.enddate.focus();
      return false;
    }
     if(de < db || me < mb || ye < yb){
      alert("结束日期不能小于开始日期!");
      document.all.item(strID).value="";
      queryConditionForm.enddate.focus();
      return false;
    }
	return true;
}

function settype(objN){

	var v = objN.value;
	if(v == "1"){
		val=1;
		patrolTr.style.display = "none";
		contractorTr.style.display = "";
		lineTr.style.display = "none";

		queryConditionForm.queryby.value = "contractor";
		conShow();

	}

	if(v == "2"){
		val=2;
		patrolTr.style.display = "";
		if(document.getElementById("contractorTr")){
		contractorTr.style.display = "";
		}
		lineTr.style.display = "none";

		queryConditionForm.queryby.value = "patrol";
		if(userType != 22){
			conShow();
			onChangeExe();
		}
	}

	if(v == "3"){
		val = 3;
		patrolTr.style.display = "none";
		if(document.getElementById("contractorTr")){
		contractorTr.style.display = "none";
		}
		lineTr.style.display = "";

		queryConditionForm.queryby.value = "line";
		if(userType == 22){
			showLine();
		}else
			conShow();
	}

}
function showLine(){
		var iArray = document.all.lLine.options;   //所隐藏的数组
    	//var iSeek = document.all.reId.value;       // 所要查找的值,上级菜单的值
        //var to_Copy = document.all.lID.options;
        var iIndex = 0;
        k=1;
        for( i=iIndex;i<iArray.length;i++){
          	k++;
          	document.all.lID.options.length=k;   //document.all.pID.options 要设置的select

          	document.all.lID.options[0].value="";
          	document.all.lID.options[0].text="请选择巡检线路";

        	document.all.lID.options[k-1].value=document.all.lLine.options[i].value;
          	document.all.lID.options[k-1].text=document.all.lLine.options[i].text.substring(8,document.all.lLine.options[i].text.length);
     	}

}
function conShow(){
          if(val == 3){
          		//按区域选择区域内的线路

          		if(document.all.reId.options[document.all.reId.selectedIndex].value.substring(2,6)=='0000'){
           			document.all.lID.options.length=1;
           			document.getElementById("lID").options[0].value='';
                	document.getElementById("lID").options[0].text='请选择区域';
					return true;
          		}

          		var iArray = document.all.lLine.options;   //所隐藏的数组
    			var iSeek = document.all.reId.value;       // 所要查找的值,上级菜单的值
          		var to_Copy = document.all.lID.options;
          		var iIndex = find(iArray,iSeek,to_Copy);
          		k=1;
          		for( i=iIndex;i<iArray.length && iArray[i].text.substring(0,6)==document.all.reId.value;i++){
          			k++;
          			document.all.lID.options.length=k;   //document.all.pID.options 要设置的select

          			document.all.lID.options[0].value="";
          			document.all.lID.options[0].text="请选择巡检线路";

          			document.all.lID.options[k-1].value=document.all.lLine.options[i].value;
          			document.all.lID.options[k-1].text=document.all.lLine.options[i].text.substring(8,document.all.lLine.options[i].text.length);
     			}
          	return;
          }
          //按区域选择代维单位
          if(document.all.reId.options[document.all.reId.selectedIndex].value.substring(2,6)=='0000'){
           		document.all.wID.options.length=1;
           		document.getElementById("wID").options[0].value='';
                document.getElementById("wID").options[0].text='请选择区域';
				return true;
          }
          k=0;
          for( i=0;i<document.all.dId.options.length;i++){
                if(document.all.dId.options[i].text.substring(0,6)== document.all.reId.value){
                k++;
                document.getElementById("wID").disabled="";
                if(k==1){
                	document.all.wID.options.length=k;
                	document.all.wID.options[k-1].value='';
                	document.all.wID.options[k-1].text='请选择代维单位';
                	k++;
                }
                document.all.wID.options.length=k;
                document.all.wID.options[k-1].value=document.all.dId.options[i].value;
                document.all.wID.options[k-1].text=document.all.dId.options[i].text.substring(8,document.all.dId.options[i].text.length);
             }
        }
        if(k==0){
        	document.all.wID.options.length=1;
            document.all.wID.options[0].value='';
            document.all.wID.options[0].text='无代维单位';
        }

    }

    function regionChange(){
        conShow();
    }
    function onChangeExe(){
    if(document.all.wID.value==""){
    	document.all.pID.options.length=1;
    	document.all.pID.options[0].value='';
        document.all.pID.options[0].text="请选择巡检人";
        return ;
    }
    var rtype = checkR(document.getElementsByName("type"));
    if(rtype =="1")
    {
    	return;
    }
    var iArray = document.all.exeId.options;   //所隐藏的数组
      var iIndex = 0;
      var iStart = 0; //
      var iEnd = iArray.length - 1;
      var iCount = 0;
      var iSeek = document.all.wID.value;      // 所要查找的值,上级菜单的值
      while(iSeek != ""){
           iCount++;
         iIndex =parseInt( ( iStart + iEnd )/2);

         if( iArray[iIndex].text.substring(8,18) < iSeek ){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                iStart = iIndex;
         }else if( iArray[iIndex].text.substring(8,18) > iSeek ){//iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                    iEnd = iIndex;
         }else{
             if(iIndex == 0){
             	 //alert("==0");
                break;
            }else{
                if(iArray[iIndex-1].text.substring(8,18) == iSeek){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                       iEnd = iIndex;
                }else{
                    break;
                }
            }
        }
        //alert(iIndex +"  "+ iEnd);
        if(iIndex==iEnd-1){
        	if(iArray[iEnd].text.substring(8,18) == iSeek){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
            	iIndex = iEnd;
            	//alert("==")
            	break;
        	}else{
        	//	alert("!="+iArray.length)
        		index = iArray.length;
         	   document.all.pID.options.length=0;
          	  break;
       	 	}
       	}

      }
      //alert(iIndex);
      k=1;
      ////iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
      //=document.all.wID.value 所要查找的值,上级菜单的值
      for( i=iIndex;i<iArray.length && iArray[i].text.substring(8,18)==document.all.wID.value;i++){
          k++;
          document.all.pID.options.length=k;   //document.all.pID.options 要设置的select

          document.all.pID.options[0].value="";
          document.all.pID.options[0].text="请选择巡检人";

          document.all.pID.options[k-1].value=document.all.exeId.options[i].value;
          document.all.pID.options[k-1].text=document.all.exeId.options[i].text.substring(20,document.all.exeId.options[i].text.length);
      }
    }
    function find(iArray,iSeek,to_Copy){
    	//var iArray = document.all.exeId.options;   //所隐藏的数组
    	//var iSeek = document.all.wID.value;       // 所要查找的值,上级菜单的值
      var iIndex = 0;
      var iStart = 0; //
      var iEnd = iArray.length - 1;
      var iCount = 0;

      while(iSeek != ""){
           iCount++;
         iIndex =parseInt( ( iStart + iEnd )/2);

         if( iArray[iIndex].text.substring(0,6) < iSeek ){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                iStart = iIndex;
         }else if( iArray[iIndex].text.substring(0,6) > iSeek ){//iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                iEnd = iIndex;
         }else{
             if(iIndex == 0){
                break;
            }else{
                if(iArray[iIndex-1].text.substring(0,6) == iSeek){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
                       iEnd = iIndex;
                }else{
                    break;
                }
            }
        }
        if(iIndex==iEnd-1 && iArray[iEnd].text.substring(0,6) != iSeek){ //iArray[iIndex].text.substring(8,18) ,要替换成下级菜单的比较条件
            index = iArray.length;
            to_Copy.length=0;
            //document.all.pID.options.length=0;
            break;
        }
      }
      return iIndex;
    }
	function setDisplay(){
		regTr.style.display = "none";
	}
	function setChecked(){
		userType = 22;
		document.all.r1.checked = "true";
		settype(document.all.r1);
	}
</script>

<logic:equal value="21" name="LOGIN_USER" property="type">
	<body onload="conShow();onChangeExe();">
</logic:equal>
<logic:equal value="22" name="LOGIN_USER" property="type">
	<body onload="conShow();onChangeExe();setDisplay();">
</logic:equal>
<logic:equal value="11" name="LOGIN_USER" property="type">
	<body onload="conShow();onChangeExe();">
</logic:equal>
<logic:equal value="12" name="LOGIN_USER" property="type">
	<body onload="conShow();onChangeExe();setDisplay();">
</logic:equal>

<span id="mainSpan" style="display:">
<template:titile value="计划巡检率查询" />
<html:form method="Post" action="/StatisticAction.do?method=getPatrolRate" target="planformFrame">
		<template:formTable namewidth="200" contentwidth="300">
 			<input type="hidden" name="staObj" />
			<template:formTr name="查询类型" isOdd="false">
					<input type="radio" name="type" value="1" onclick="settype(this)" checked /> 代维单位
				<logic:equal value="group" name="PMType">
					<input type="radio" name="type" value="2" id="r1" onclick="settype(this)" />巡检维护组
				</logic:equal>
				<logic:notEqual value="group" name="PMType">
					<input type="radio" name="type" value="2" id="r1" onclick="settype(this)" />巡检人
        		</logic:notEqual>
				<input type="radio" name="type" value="3" onclick="settype(this)" />巡检线路
				<html:hidden property="queryby" value="contractor" />
			</template:formTr>

				<template:formTr name="所属区域" isOdd="false" tagID="regTr"
					style="display:">
					<logic:present name="lRegion">
						<select name="regionid" class="selecttext" id="reId"
							style="width:225" onchange="regionChange()">
							<logic:iterate id="lRegionID" name="lRegion">
								<option
									value="<bean:write name="lRegionID" property="regionid"/>">
									<bean:write name="lRegionID" property="regionname" />
								</option>
							</logic:iterate>
						</select>
					</logic:present>
				</template:formTr>

				<template:formTr name="代维单位" isOdd="false" tagID="contractorTr"
					style="display:">
					<select name="deptid" class="selecttext" style="width:225" id="wID"
						onchange="onChangeExe()">
						<option>
						</option>
					</select>
					<logic:present name="lCon">
						<select name="dId" style="display:none">
							<logic:iterate id="lConID" name="lCon">
								<option
									value="<bean:write name="lConID" property="contractorid"/>">
									<bean:write name="lConID" property="regionid" />--<bean:write name="lConID" property="contractorname" />
								</option>
							</logic:iterate>
						</select>
					</logic:present>
				</template:formTr>

			<logic:equal value="group" name="PMType">
				<template:formTr name="巡检维护组" tagID="patrolTr" style="display:none">
					<span id="patrolSpan"> <select name="patrolid"
							Class="inputtext" style="width:225" id='pID'>
							<logic:present name="lpatrol">
								<logic:iterate id="lID" name="lpatrol">
									<option value="<bean:write name="lID" property="patrolid"/>">
										<bean:write name="lID" property="patrolname" />
									</option>
								</logic:iterate>
							</logic:present>
						</select> <!-- hidden id --> <html:hidden property="id" /> </span>
				</template:formTr>
			</logic:equal>

			<logic:notEqual value="group" name="PMType">
				<template:formTr name="巡检维护人" tagID="patrolTr" style="display:none">

					<select name="patrolid" Class="inputtext" style="width:225"
						id='pID'>
						<option value="">请选择巡检人</option>
						<logic:present name="lpatrol">
							<logic:iterate id="pgId" name="lpatrol">
								<option value="<bean:write name="pgId" property="patrolid"/>">
									<bean:write name="pgId" property="patrolname" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
				</template:formTr>
			</logic:notEqual>


			<logic:present name="lpatrol">
				<select name="exeId" style="display:none" id="exeId">
					<logic:iterate id="pgId" name="lpatrol">
						<option value="<bean:write name="pgId" property="patrolid"/>">
							<bean:write name="pgId" property="regionid" />--<bean:write name="pgId" property="parentid" />--<bean:write name="pgId" property="patrolname" />
						</option>
					</logic:iterate>
				</select>
			</logic:present>


			<template:formTr name="巡检线路" tagID="lineTr" style="display:none">

				<select name="lineid" class="selecttext" style="width:225" id="lID">
					<option>
					</option>
				</select>
				<logic:present name="lLine">
					<select name="lLine" style="display:none">
						<logic:iterate id="lLineID" name="lLine">
							<option value="<bean:write name="lLineID" property="lineid"/>">
								<bean:write name="lLineID" property="regionid" />--<bean:write name="lLineID" property="linename" />
							</option>
						</logic:iterate>
					</select>
				</logic:present>
			</template:formTr>

			<template:formTr name="查询周期" isOdd="false">
				<input type="radio" name="cyc" value="1" onclick="setCyc(this)"
					checked /> 周
        <input type="radio" name="cyc" value="10" onclick="setCyc(this)" /> 旬
        <input type="radio" name="cyc" value="15" onclick="setCyc(this)" />半月
		<input type="radio" name="cyc" value="2" onclick="setCyc(this)" /> 月
		<html:hidden property="cyctype" />
			</template:formTr>

			<template:formTr name="起始时间" tagID="weekBeginTr" style="display:">
				<html:text property="begindate" value="" styleClass="inputtext"
					style="width:200" readonly="true" />
				<input type='button' value='▼' id='btn'	onclick="GetSelectDateTHIS('begindate')" style="font:'normal small-caps 6pt serif';">
			</template:formTr>
			<template:formTr name="终止时间" tagID="weekEndTr" isOdd="false" style="display:">
				<html:text property="enddate" value="" styleClass="inputtext" style="width:200" readonly="true" />
				<input type='button' value='▼' id='btn' onclick="GetSelectDatenew('enddate')" style="font:'normal small-caps 6pt serif';">
			</template:formTr>

			<template:formTr name="统计年份" tagID="yearTr" style="display:none">
				<apptag:getYearOptions />
				<html:select property="year" styleClass="inputtext"
					style="width:225">
					<html:options collection="yearCollection" property="value"
						labelProperty="label" />
				</html:select>
			</template:formTr>
			<template:formTr name="统计月份" tagID="monthTr" isOdd="false"
				style="display:none">
				<html:select property="month" styleClass="inputtext"
					style="width:225">
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

		</template:formTable>
		<template:formSubmit>
			<td width="85">
				<html:button property="action" onclick="checksub()"
					styleClass="button">查询</html:button>
			</td>
			<td width="85">
				<html:reset property="action" styleClass="button">取消</html:reset>
			</td>
		</template:formSubmit>
	</html:form>
<br>
<br>
</span>


<iframe name="hiddenInfoFrame" style="display:none"></iframe>

<div id="iframeDiv" style="height:280">
	<iframe name=planformFrame border=0 marginWidth=0 marginHeight=0
		src="${ctx}/common/blank.html" frameBorder=0 width="100%"
		scrolling="no" height="100%">
	</iframe>
</div>

</body>
