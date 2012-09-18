<%@include file="/common/header.jsp"%>

<script language="JavaScript" type="text/javascript">
  var deptname= "";
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
function setCyc(v){

	//var v = objN.value;
	if(v == "1"){
		
		beginTr.style.display = "none";
		endTr.style.display = "none";
		yearTr.style.display = "";
		ApproveRateBean.cyctype.value = "1";
	}
	if(v == "2"){
		beginTr.style.display = "";
		endTr.style.display = "";
		yearTr.style.display = "none";

		ApproveRateBean.cyctype.value = "2";
	}

}
function setType(objT){
	var t = objT.options[objT.selectedIndex].value;
	if(t == '1'){
		var type = checkR(document.getElementsByName("cyc"));
		
		if(type == '2'){
			document.getElementsByName("cyc")[0].checked = true;
			setCyc(1);
		}
		selYear.style.display = 'none';

	}else{
		selYear.style.display = ''
	}
}

function setContractorName(){
	var cyc = document.all.rcyc.value;
	
	var cname = ApproveRateBean.contractorid.options[ApproveRateBean.contractorid.selectedIndex].text;
	if(ApproveRateBean.contractorid.options[ApproveRateBean.contractorid.selectedIndex].text == "不限"){
		cname = ApproveRateBean.regionid.options[ApproveRateBean.regionid.selectedIndex].text;
	}
	if(ApproveRateBean.contractorid.options[ApproveRateBean.contractorid.selectedIndex].text=="请选择区域"){
		cname = ApproveRateBean.regionid.options[ApproveRateBean.regionid.selectedIndex].text+"全省";
	}
	if(deptname != ""){
		cname = deptname;
	}
	ApproveRateBean.contractorname.value = cname;

	if(cyc == '2'){
		var b = ApproveRateBean.beginmonth.value;
		var e = ApproveRateBean.endmonth.value;
		var by = ApproveRateBean.beginyear.value;
		var ey = ApproveRateBean.endyear.value;
		
		if(by>ey){
			alert("结束日期不能小于开始日期");
			return false;
		}
		if(by == ey &&  b>e){
		alert("结束日期不能小于开始日期");
			return false;
		}
	}
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

function conShow(){

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
                	document.all.wID.options[k-1].text='不限';
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
 	function setDisplay(userType){
 		if(userType == 12){
			regTr.style.display = "none";
		}
		if(userType == 22){
		   deptname = "<%=session.getAttribute("LOGIN_USER_DEPT_NAME")%>";
			regTr.style.display = "none";
			conTr.style.display = "none";
		}
	}
</script>
<logic:equal value="21" name="LOGIN_USER" property="type">
	<body onload="conShow();">
</logic:equal>
<logic:equal value="11" name="LOGIN_USER" property="type">
	<body onload="conShow();">
</logic:equal>
<logic:equal value="12" name="LOGIN_USER" property="type">
	<body onload="conShow();setDisplay(12);">
</logic:equal>

<logic:notEqual value="22" name="LOGIN_USER" property="type">
	<body onload="conShow();">
</logic:notEqual>
<logic:equal value="22" name="LOGIN_USER" property="type">
	<body onload="setDisplay(22);">
</logic:equal>
<span id="mainSpan" style="display:">
<br>
<template:titile value="计划审批通过率统计"/>

<apptag:setSelectOptions valueName="deptCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid" region="true"/>
<html:form method="Post" action="/ApproveRateAction.do?method=getApproveRate" onsubmit="return setContractorName()" target="planformFrame">
<template:formTable namewidth="200" contentwidth="300">

			<template:formTr name="所属区域" isOdd="false" tagID="regTr" style="display:">
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
			<template:formTr name="代维单位" isOdd="false"  tagID="conTr" style="display:">
				<select name="contractorid" class="selecttext" style="width:225" id="wID">
					<option>
					</option>
				</select>
				<logic:present name="lContractor">
					<select name="dId" style="display:none">
						<logic:iterate id="conID" name="lContractor">
							<option
								value="<bean:write name="conID" property="contractorid"/>">
								<bean:write name="conID" property="regionid" />--<bean:write name="conID" property="contractorname" />
							</option>
						</logic:iterate>
					</select>
				</logic:present>
			</template:formTr>


	<template:formTr name="考核对象" >
      <html:select property="approveplantype" styleClass="inputtext" style="width:225" onChange="setType(this);">
        <html:option value="0">全部计划</html:option>
        <html:option value="1">年计划</html:option>
        <html:option value="2">月计划</html:option>
      </html:select>
    </template:formTr>

	<template:formTr name="考核标准" isOdd="false">
      <html:select property="approvetimes" styleClass="inputtext" style="width:225">
        <html:option value="1">一次审批通过</html:option>
        <html:option value="2">两次以上审批通过</html:option>
      </html:select>
    </template:formTr>

	<template:formTr name="统计周期">
		<input type="radio" name="cyc" value="1" id="cyc1" onclick="setCyc(1)" checked/>&nbsp; 年 &nbsp;
		<span id="selYear" style="display:">
		<input type="radio" name="cyc" value="2" id="rcyc" onclick="setCyc(2)"/>&nbsp; 自定义 &nbsp;
		</span>
		<html:hidden property="cyctype" value="1" />
		<html:hidden property="contractorname" value="" />
    </template:formTr>

	<template:formTr name="统计年度" tagID="yearTr"  isOdd="false" style="display:">
	<apptag:getYearOptions />
      <html:select property="statisticyear" styleClass="inputtext" style="width:225">
        <html:options collection="yearCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>

	<template:formTr name="起始时间"  tagID="beginTr" isOdd="false" style="display:none">
      <html:select property="beginyear" styleClass="inputtext" style="width:160">
        <html:options collection="yearCollection" property="value" labelProperty="label"/>
      </html:select>

	  <html:select property="beginmonth" styleClass="inputtext" styleId="bmid" style="width:60;display:">
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

	<template:formTr name="终止时间" tagID="endTr" style="display:none">
      <html:select property="endyear" styleClass="inputtext" style="width:160">
        <html:options collection="yearCollection" property="value" labelProperty="label"/>
      </html:select>

	  <html:select property="endmonth" styleClass="inputtext" styleId="emid" style="width:60;display:">
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
        <html:submit property="action" styleClass="button" >查询</html:submit>
    </td>
    <td width="85">
        <html:reset property="action" styleClass="button" >取消</html:reset>
    </td>
</template:formSubmit>
</html:form>
</span>

<iframe name="hiddenInfoFrame" style="display:none"></iframe>

<div id ="iframeDiv" style="height:280">
  <iframe name=planformFrame border=0 marginWidth=0 marginHeight=0 src="${ctx}/common/blank.html" frameBorder=0 width="100%" scrolling="yes" height="100%">  </iframe>
</div>

</body>
