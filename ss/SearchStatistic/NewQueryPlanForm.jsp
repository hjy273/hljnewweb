<%@include file="/common/header.jsp"%>

<script language="JavaScript" type="">
var fdate = 1;
function enableSelect(flag){
	if (flag==1){
		deptTr.style.display = "";
		sublineTr.style.display = "none";
		patrolmanTr.style.display = "none";
	}
	if (flag==2){
		deptTr.style.display = "none";
		sublineTr.style.display = "";
		patrolmanTr.style.display = "none";
	}
	if (flag==3){
		deptTr.style.display = "none";
		sublineTr.style.display = "none";
		patrolmanTr.style.display = "";
	}

}

function setCyc(objN){

	var v = objN.value;

    if(v == "1" ||v == "10" ||v == "15"){
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

function settype(objN){

	var v = objN.value;

	if(v == "1"){

		patrolTr.style.display = "none";
		contractorTr.style.display = "";
		lineTr.style.display = "none";

		queryConditionForm.queryby.value = "contractor";
	}

	if(v == "2"){

		patrolTr.style.display = "";
		contractorTr.style.display = "none";
		lineTr.style.display = "none";

		queryConditionForm.queryby.value = "patrol";
	}

	if(v == "3"){

		patrolTr.style.display = "none";
		contractorTr.style.display = "none";
		lineTr.style.display = "";

		queryConditionForm.queryby.value = "line";
	}

}
function checksub(){
  	setStaObj();
    if(fdate == "1" ||fdate == "10" ||fdate == "15"){
      if(queryConditionForm.begindate.value == "" || queryConditionForm.begindate.value ==null){
        alert("û�п�ʼ����!");
        return false;
      }
    }
    if(fdate == 30){
    	queryConditionForm.begindate.value = queryConditionForm.year.value + "/" + queryConditionForm.month.value + "/1";
    	y = parseInt(queryConditionForm.year.value);
        m = parseInt(queryConditionForm.month.value,10);
        d= 30;
        if(m == 2){//��2��
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
   queryConditionForm.submit();
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
//ȷ�����ڲ��ܳ�����ĩ
	if(parseInt(m) != mb+1){
       m=mb+1;
       if(m == 2){//��2��
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
    	alert("��û�п�ʼ����!");
        queryConditionForm.begindate.focus();
        return false;
    }
    document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    //��ʼʱ��
    var yb = queryConditionForm.begindate.value.substring(0,4);
	var mb = parseInt(queryConditionForm.begindate.value.substring(5,7) ,10);
	var db = parseInt(queryConditionForm.begindate.value.substring(8,10),10);
    //����ʱ��
    var ye = queryConditionForm.enddate.value.substring(0,4);
	var me = parseInt(queryConditionForm.enddate.value.substring(5,7) ,10);
	var de = parseInt(queryConditionForm.enddate.value.substring(8,10),10);
    if(yb!=ye){
      alert("���ܿ���!");
      document.all.item(strID).value="";
       queryConditionForm.enddate.focus();
      return false;
    }
    if(mb!=me){
      alert("���ܿ���!");
      document.all.item(strID).value="";
      queryConditionForm.enddate.focus();
      return false;
    }
     if(de < db || me < mb || ye < yb){
      alert("�������ڲ���С�ڿ�ʼ����!");
      document.all.item(strID).value="";
      queryConditionForm.enddate.focus();
      return false;
    }
	return true;
}
function getDep()
{
}
</script>

<body onload="getDep()">
<span id="mainSpan" style="display:">

<template:titile value="Ѳ��ƻ����ܱ����ѯ"/>

<html:form method="Post" action="/StatisticAction.do?method=getStatisticForm" target="planformFrame">
<template:formTable namewidth="200" contentwidth="300">

	<input type="hidden" name="staObj" />

	<template:formTr name="��ѯ����"  isOdd="false">
		<input type="radio" name="type" value="1" onclick="settype(this)" checked/> ��ά��λ
		<logic:equal value="group" name="PMType">
        	 <input type="radio" name="type" value="2" onclick="settype(this)"/>Ѳ��ά����
		</logic:equal>
        <logic:notEqual value="group" name="PMType">
        	 <input type="radio" name="type" value="2" onclick="settype(this)"/>Ѳ����
        </logic:notEqual>


		<html:hidden property="queryby" value="contractor" />
    </template:formTr>

    <template:formTr name="��ά��λ" tagID="contractorTr" style="display:">
        <select name="deptid" Class="inputtext" style="width:225" onchange="getDep()">
        	<logic:present name="lCon">
            	<logic:iterate id="lConID" name="lCon">
                	<option value="<bean:write name="lConID" property="contractorid"/>"><bean:write name="lConID" property="contractorname"/></option>
            	</logic:iterate>
        	</logic:present>
        <select>
    </template:formTr>
    <logic:equal value="group" name="PMType">
    	<template:formTr name="Ѳ��ά����" tagID="patrolTr" style="display:none">
				<span id="patrolSpan">
               		 <select  name="patrolid" Class="inputtext" style="width:225" >
							<logic:present name="lpatrol">
		                    	<logic:iterate id="lID" name="lpatrol">
		                        	<option value="<bean:write name="lID" property="patrolid"/>"> <bean:write name="lID" property="patrolname"/></option>
		                    	</logic:iterate>
							</logic:present>
			        </select>
				</span>
		    </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
    	<template:formTr name="Ѳ��ά����" tagID="patrolTr" style="display:none">
				<span id="patrolSpan">
					 <select  name="patrolid" Class="inputtext" style="width:225" >
							<logic:present name="lpatrol">
		                    	<logic:iterate id="lID" name="lpatrol">
		                        	<option value="<bean:write name="lID" property="patrolid"/>"> <bean:write name="lID" property="patrolname"/></option>
		                    	</logic:iterate>
							</logic:present>
			        </select>
				</span>
		    </template:formTr>
    </logic:notEqual>


	<template:formTr name="Ѳ����·" tagID="lineTr" style="display:none">
		<span id="lineSpan">
	        <select name="lineid" Class="inputtext" style="width:225" >
            	<logic:present name="lLine">
                	<logic:iterate id="lLineID" name="lLine">
                    	<option value="<bean:write name="lLineID" property="lineid"/>"><bean:write name="lLineID" property="linename"/></option>
                	</logic:iterate>
            	</logic:present>
	        </select>
		</span>
    </template:formTr>

	<template:formTr name="��ѯ����"  isOdd="false">
		<input type="radio" name="cyc" value="1" onclick="setCyc(this)" checked/> ��
        <input type="radio" name="cyc" value="10" onclick="setCyc(this)"/> Ѯ
        <input type="radio" name="cyc" value="15" onclick="setCyc(this)"/>����
		<input type="radio" name="cyc" value="2" onclick="setCyc(this)"/> ��
		<html:hidden property="cyctype" />
    </template:formTr>

    <template:formTr name="��ʼʱ��" tagID="weekBeginTr" style="display:">
        <html:text property="begindate" value="" styleClass="inputtext" style="width:200" readonly="true" />
        <input type='button' value='��' id='btn'  onclick="GetSelectDateTHIS('begindate')" style="font:'normal small-caps 6pt serif';" >
    </template:formTr>
    <template:formTr name="��ֹʱ��"   tagID="weekEndTr" isOdd="false" style="display:">
        <html:text property="enddate"  value="" styleClass="inputtext" style="width:200" readonly="true"/>
        <input type='button' value='��' id='btn'  onclick="GetSelectDatenew('enddate')" style="font:'normal small-caps 6pt serif';" >
    </template:formTr>

	<template:formTr name="ͳ�����" tagID="yearTr" style="display:none">
	<apptag:getYearOptions />
      <html:select property="year" styleClass="inputtext" style="width:225">
        <html:options collection="yearCollection" property="value" labelProperty="label"/>
	  </html:select>
    </template:formTr>
	<template:formTr name="ͳ���·�" tagID="monthTr" isOdd="false" style="display:none">
      <html:select property="month" styleClass="inputtext" style="width:225">
        <html:option value="01">1 ��</html:option>
        <html:option value="02">2 ��</html:option>
        <html:option value="03">3 ��</html:option>
        <html:option value="04">4 ��</html:option>
        <html:option value="05">5 ��</html:option>
        <html:option value="06">6 ��</html:option>
        <html:option value="07">7 ��</html:option>
        <html:option value="08">8 ��</html:option>
        <html:option value="09">9 ��</html:option>
        <html:option value="10">10 ��</html:option>
        <html:option value="11">11 ��</html:option>
        <html:option value="12">12 ��</html:option>
      </html:select>
    </template:formTr>

</template:formTable>
<template:formSubmit>
    <td width="85">
        <html:button property="action"  onclick="checksub()" styleClass="button">��ѯ</html:button>
    </td>
    <td width="85">
        <html:reset property="action" styleClass="button">ȡ��</html:reset>
    </td>
</template:formSubmit>
</html:form>
<br>
<br>
</span>


<iframe name="hiddenInfoFrame" style="display:none"></iframe>

<div id ="iframeDiv" style="height:280">
  <iframe name=planformFrame border=0 marginWidth=0 marginHeight=0 src="${ctx}/common/blank.html" frameBorder=0 width="100%" scrolling="no" height="100%">  </iframe>
</div>

</body>
