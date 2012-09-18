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
<template:titile value="�������������ѯ"/>
<html:form method="Post" action="/troubleQueryAction.do?method=queryTrouble">
  <template:formTable contentwidth="200" namewidth="200">

  <input type="hidden" name="regionid" value="<%=regionid%>">

   <template:formTr name="�¹�״̬">
      <html:select property="status" styleClass="inputtext" style="width:160">
        <html:option value="">����</html:option>
        <html:option value="0">������</html:option>
        <html:option value="2">������</html:option>
        <html:option value="3">�Ѵ���</html:option>
      </html:select>
    </template:formTr>

    <template:formTr name="��ѯ����"  isOdd="false">
        <input type="radio" name="type" value="1" onclick="settype(this)" checked/>&nbsp; ��ά��λ &nbsp;
        <input type="radio" name="type" value="2" onclick="settype(this)"/>&nbsp; Ѳ��� &nbsp;
        <html:hidden property="queryby" value="contractor" />
        <html:hidden property="staObj" value="" />
    </template:formTr>

    <template:formTr name="��ά��λ" tagID="contractorTr" style="display:">
        <apptag:setSelectOptions valueName="deptCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid" region="true"/>

        <html:select property="contractorid" styleClass="inputtext" style="width:160">
            <html:option value="">����</html:option>
            <html:options collection="deptCollection" property="value" labelProperty="label"/>
        </html:select>
    </template:formTr>

    <template:formTr name="Ѳ���" tagID="sublineTr" style="display:none">
        <apptag:setSelectOptions valueName="sublineCollection" tableName="sublineinfo" columnName1="sublinename" columnName2="sublineid" region="true"/>

        <html:select property="sublineid" styleClass="inputtext" style="width:160" >
            <html:option value="">����</html:option>
            <html:options collection="sublineCollection" property="value" labelProperty="label"/>
        </html:select>

    </template:formTr>

    <template:formTr name="��ѯ����" isOdd="false">

      <input type="radio" name="cyc" value="1" checked onclick="setCyc(this)" />
      &nbsp;
      �Զ���
      &nbsp;
      <input type="radio" name="cyc" value="2" onclick="setCyc(this)" />
      &nbsp;
      ��
      &nbsp;

      <html:hidden property="cyctype" value="month"/>
    </template:formTr>
    <template:formTr name="��ʼʱ��" tagID="weekBeginTr" style="display:">
      <html:text property="begintime" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:160" readonly="true"/>
    </template:formTr>
    <template:formTr name="��ֹʱ��" tagID="weekEndTr" isOdd="false" style="display:">
              <html:text property="endtime" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:160" readonly="true"/>
    </template:formTr>
    <template:formTr name="ͳ�����" tagID="yearTr" style="display:none">
      <html:select property="year" styleClass="inputtext" style="width:160">
        <html:option value="">����</html:option>
        <html:option value="2004">2004</html:option>
        <html:option value="2005">2005</html:option>
        <html:option value="2006">2006</html:option>
        <html:option value="2007">2007</html:option>
        <html:option value="2008">2008</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="ͳ���·�" tagID="monthTr" isOdd="false" style="display:none">
            <html:select property="month" styleClass="inputtext" style="width:160">
              <html:option value="">����</html:option>
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

     <template:formSubmit>
      <td>
        <html:button property="action" styleClass="button" onclick="setStaObj();queryAccident()">��ѯ</html:button>
      </td>
      <td>
        <html:reset property="action" styleClass="button">ȡ��</html:reset>
      </td>
    </template:formSubmit>

  </template:formTable>
</html:form>
</span>


</body>
