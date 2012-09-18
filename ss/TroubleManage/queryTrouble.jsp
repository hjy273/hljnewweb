<%@include file="/common/header.jsp"%>
<%
//String regionid = request.getParameter("regionid");
String regionid = (String)session.getAttribute("hiddentroubleregion");
System.out.println("regionid in jsp:" + regionid);
//if(regionid == null || regionid == ""){
	//regionid = (String)session.getAttribute("selectedRegion");
//}

%>
<script language="JavaScript" type="">

function setCyc(objN){

    var v = objN.value;

    if(v == "1"){
        yearTr.style.display = "none";
        monthTr.style.display = "none";
        weekBeginTr.style.display = "";
        weekEndTr.style.display = "";

        queryTroubleBean.cyctype.value = "week";
    }

    if(v == "2"){
        yearTr.style.display = "";
        monthTr.style.display = "";
        weekBeginTr.style.display = "none";
        weekEndTr.style.display = "none";

        queryTroubleBean.cyctype.value = "month";
    }

}


function setDisable(objN, status){
    objN.disabled = status;
}

function queryAccidentOpen(){
    var formPop = window.open('','formPop','scrollbars=yes,width=800,height=660,status=yes');
    formPop.resizeTo(800,screen.height);
    formPop.focus();
    queryTroubleBean.submit();
}

function queryAccident(){
    //queryTroubleBean.contractorname.value = queryTroubleBean.contractorid.options[queryTroubleBean.contractorid.selectedIndex].text;
    queryTroubleBean.submit();
}

function makeSize(enlargeFlag){
    if(enlargeFlag == 0){

        mainSpan.style.display = "none";
        iframeDiv.style.height = "595";
    }else{
        mainSpan.style.display = "";
        iframeDiv.style.height = "385";
    }
}

function settype(objN){

    var v = objN.value;

    if(v == "1"){

        sublineTr.style.display = "none";
        contractorTr.style.display = "";

        queryTroubleBean.queryby.value = "contractor";
    }

    if(v == "2"){

        sublineTr.style.display = "";
        contractorTr.style.display = "none";

        queryTroubleBean.queryby.value = "subline";
    }

}

function setStaObj(){

    if(queryTroubleBean.queryby.value == "subline"){

        queryTroubleBean.staObj.value = queryTroubleBean.contractorid.options[queryTroubleBean.contractorid.selectedIndex].text;

    }else{
        queryTroubleBean.staObj.value = queryTroubleBean.contractorid.options[queryTroubleBean.contractorid.selectedIndex].text;

    }

}

</script>
<body>
<span id="mainSpan" style="display:">
<template:titile value="隐患处理情况查询"/>
<html:form method="Post" action="/troubleQueryAction.do?method=queryTrouble">
  <template:formTable contentwidth="200" namewidth="200">

  <input type="hidden" name="regionid" value="<%=regionid%>">

   <template:formTr name="事故状态">
      <html:select property="status" styleClass="inputtext" style="width:160">
        <html:option value="">不限</html:option>
        <html:option value="0">待处理</html:option>
        <html:option value="2">处理中</html:option>
        <html:option value="3">已处理</html:option>
      </html:select>
    </template:formTr>

    <template:formTr name="查询类型"  isOdd="false">
        <input type="radio" name="type" value="1" onclick="settype(this)" checked/>&nbsp; 代维单位 &nbsp;
        <input type="radio" name="type" value="2" onclick="settype(this)"/>&nbsp; 巡检段 &nbsp;
        <html:hidden property="queryby" value="contractor" />
        <html:hidden property="staObj" value="" />
    </template:formTr>

    <template:formTr name="代维单位" tagID="contractorTr" style="display:">
        <apptag:setSelectOptions valueName="deptCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid" region="true"/>

        <html:select property="contractorid" styleClass="inputtext" style="width:160">
            <html:option value="">不限</html:option>
            <html:options collection="deptCollection" property="value" labelProperty="label"/>
        </html:select>
    </template:formTr>

    <template:formTr name="巡检段" tagID="sublineTr" style="display:none">
        <apptag:setSelectOptions valueName="sublineCollection" tableName="sublineinfo" columnName1="sublinename" columnName2="sublineid" region="true"/>

        <html:select property="sublineid" styleClass="inputtext" style="width:160" >
            <html:option value="">不限</html:option>
            <html:options collection="sublineCollection" property="value" labelProperty="label"/>
        </html:select>

    </template:formTr>

    <template:formTr name="查询周期" isOdd="false">

      <input type="radio" name="cyc" value="1" checked onclick="setCyc(this)" />
      &nbsp;
      自定义
      &nbsp;
      <input type="radio" name="cyc" value="2" onclick="setCyc(this)" />
      &nbsp;
      月
      &nbsp;

      <html:hidden property="cyctype" value="month"/>
    </template:formTr>
    <template:formTr name="起始时间" tagID="weekBeginTr" style="display:">
      <html:text property="begintime" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:160" readonly="true"/>
    </template:formTr>
    <template:formTr name="终止时间" tagID="weekEndTr" isOdd="false" style="display:">
              <html:text property="endtime" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:160" readonly="true"/>
    </template:formTr>
    <template:formTr name="统计年份" tagID="yearTr" style="display:none">
      <html:select property="year" styleClass="inputtext" style="width:160">
        <html:option value="">不限</html:option>
        <html:option value="2004">2004</html:option>
        <html:option value="2005">2005</html:option>
        <html:option value="2006">2006</html:option>
        <html:option value="2007">2007</html:option>
        <html:option value="2008">2008</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="统计月份" tagID="monthTr" isOdd="false" style="display:none">
            <html:select property="month" styleClass="inputtext" style="width:160">
              <html:option value="">不限</html:option>
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

     <template:formSubmit>
      <td>
        <html:button property="action" styleClass="button" onclick="setStaObj();queryAccident()">查询</html:button>
      </td>
      <td>
        <html:reset property="action" styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>

  </template:formTable>
</html:form>
</span>


</body>
