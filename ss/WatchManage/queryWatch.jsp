<%@ include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo" %>
<script language="javascript" type="">
<!--
function GetSelectDatenew(strID) {
//    if(watchBean.beginDate.value =="" || watchBean.beginDate.value ==null){
//    	alert("��û�п�ʼ����!");
//        watchBean.beginDate.focus();
//        return false;
//    }
    document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    //��ʼʱ��
    var yb = watchBean.beginDate.value.substring(0,4);
	var mb = parseInt(watchBean.beginDate.value.substring(5,7) ,10);
	var db = parseInt(watchBean.beginDate.value.substring(8,10),10);
    //����ʱ��
    var ye = watchBean.endDate.value.substring(0,4);
	var me = parseInt(watchBean.endDate.value.substring(5,7) ,10);
	var de = parseInt(watchBean.endDate.value.substring(8,10),10);
    if(yb == "" && ye != "" ) {
	  alert("�������ѯ�Ŀ�ʼʱ��!");
      //document.all.item(strID).value="";
      watchBean.beginDate.focus();
      return false;
	}
	// �ڿ�ʼ�ͽ���ʱ�䶼���������������ܿ���ȵ��ж�
	if(yb != "" && ye != "") {
		//if(yb!=ye){
      	//	alert("��ѯʱ��β��ܿ����!");
      	//	document.all.item(strID).value="";
      	//	watchBean.endDate.focus();
      	//	return false;
    	//}
		if(ye < yb) {
			alert("��ѯ�������ڲ���С�ڿ�ʼ����!");
      		document.all.item(strID).value="";
      		watchExeQuery.endDate.focus();
      		return false;
		}
		if((ye == yb ) && (me < mb  || de < db)) {
      		alert("��ѯ�������ڲ���С�ڿ�ʼ����!");
      		document.all.item(strID).value="";
      		watchBean.endDate.focus();
      		return false;
    	}
	}
//    if(mb!=me){
//      alert("���ܿ���!");
//      document.all.item(strID).value="";
//      watchBean.endDate.focus();
//      return false;
//    }
	return true;
}
var iteName = "";

function getTimeWin(objName){

    iteName = objName;

    showx = event.screenX - event.offsetX - 4 - 210 ;
    showy = event.screenY - event.offsetY + 18;

    var timeWin = window.showModalDialog("${ctx}/common/time.html",self, "dialogWidth:235px; dialogHeight:265px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;help:no");

}

function setSelecteTime(time) {
    document.all.item(iteName).value = time;
}

function toLoadPoint(){

    opSelect(true);

    var desPage = "${ctx}/pointAction.do?method=loadPoint4Watch&id=" + watchBean.lid.value;
    //alert(desPage);

    hiddenFrame.location.replace(desPage);

}

function opSelect(flag){

    watchBean.startpointid.disabled = flag;
    watchBean.endpointid.disabled = flag;

}

function toSetVis(visStr){

    for(var i = 0; i < toHideTr.length; i ++){

        toHideTr[i].style.display = visStr;
    }

}

function toClearEndDate(){
    watchBean.endDate.value = "����";
}
function addGoBack()
        {
            try{
                location.href = "${ctx}/watchAction.do?method=queryWatch";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
///////////////////////////////ʾ������////////////////////////////
//����ʹ�ά
    function onChangeCon(){
          k=0;
          for( i=0;i<document.all.workID.options.length;i++){
             if(document.all.workID.options[i].text.substring(0,6)== document.all.rId.value){
                 k++;
                document.all.cId.options.length=k;
                document.all.cId.options[k-1].value=document.all.workID.options[i].value;
                document.all.cId.options[k-1].text=document.all.workID.options[i].text.substring(8,document.all.workID.options.length);
             }
           }
           if(k==0){
             document.all.cId.options.length=1;
             document.all.cId.options[0].value="";
             document.all.cId.options[0].text="��";
           }
           onChangeDeptUser();
    }

//    ��ά��λ��Ѳ����
    function onChangeDeptUser()
    {
      var iArray = document.all.userID.options;   //�����ص�����
      var iIndex = 0;
      var iStart = 0; //
      var iEnd = iArray.length - 1;
      var iCount = 0;
      var iSeek = document.all.cId.value;       // ��Ҫ���ҵ�ֵ,�ϼ��˵���ֵ
      while(iSeek != ""){
           iCount++;
         iIndex =parseInt( ( iStart + iEnd )/2);

         if( iArray[iIndex].text.substring(8,18) < iSeek ){ //iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
                iStart = iIndex;
         }else if( iArray[iIndex].text.substring(8,18) > iSeek ){//iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
                    iEnd = iIndex;
         }else{
             if(iIndex == 0){
                break;
            }else{
                if(iArray[iIndex-1].text.substring(8,18) == iSeek){ //iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
                       iEnd = iIndex;
                }else{
                    break;
                }
            }
        }
        if(iIndex==iEnd-1 && iArray[iEnd].text.substring(8,18) != iSeek){ //iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
            index = iArray.length;
            document.all.uId.options.length=0;
            break;
        }
        if(iIndex==iStart && iArray[iEnd].text.substring(8,18) != iSeek){ //iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
            index = iArray.length;
            document.all.uId.options.length=0;
            break;
        }
      }

      k=1;
      ////iArray[iIndex].text.substring(8,18) ,Ҫ�滻���¼��˵��ıȽ�����
      //=document.all.cId.value ��Ҫ���ҵ�ֵ,�ϼ��˵���ֵ
      for( i=iIndex;i<iArray.length && iArray[i].text.substring(8,18)==document.all.cId.value;i++){
          k++;
          document.all.uId.options.length=k;   //document.all.uId.options Ҫ���õ�select

          document.all.uId.options[0].value="";
          document.all.uId.options[0].text="����";

          document.all.uId.options[k-1].value=document.all.userID.options[i].value;
          document.all.uId.options[k-1].text=document.all.userID.options[i].text.substring(20);
      }
      if(k==1){
        document.all.uId.options.length=1;
        document.all.uId.options[0].value="";
        document.all.uId.options[0].text="��";
      }
    }

function addSub() {
	//��ʼʱ��
    var yb = watchBean.beginDate.value.substring(0,4);
	var mb = parseInt(watchBean.beginDate.value.substring(5,7) ,10);
	var db = parseInt(watchBean.beginDate.value.substring(8,10),10);
    //����ʱ��
    var ye = watchBean.endDate.value.substring(0,4);
	var me = parseInt(watchBean.endDate.value.substring(5,7) ,10);
	var de = parseInt(watchBean.endDate.value.substring(8,10),10);
    if(yb == "" && ye != "" ) {
	  alert("�������ѯ�Ŀ�ʼʱ��!");
      //document.all.item(strID).value="";
      watchBean.beginDate.focus();
      return false;
	}
	// �ڿ�ʼ�ͽ���ʱ�䶼���������������ܿ���ȵ��ж�
	if(yb != "" && ye != "") {
		//if(yb!=ye){
      	//	alert("��ѯʱ��β��ܿ����!");
      	//	document.all.item(strID).value="";
      	//	watchBean.endDate.focus();
      	//	return false;
    	//}
		if(ye < yb) {
			alert("��ѯ�������ڲ���С�ڿ�ʼ����!");
      		document.all.item(strID).value="";
      		watchExeQuery.endDate.focus();
      		return false;
		}
		if((ye == yb ) && (me < mb  || de < db)) {
     		alert("��ѯ�������ڲ���С�ڿ�ʼ����!");
      		document.all.item(strID).value="";
      		watchBean.endDate.focus();
      		return false;
    	}
	}
	return true;
}
//////////////////////////////////////////////////////////////////////////////////////////////////
</script>
<Br/>
<%
UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
if(userinfo.getRegionID().substring( 2, 6 ).equals( "0000" )){%>
<body onload="onChangeCon()">
<% }else{%>
<body>
<% }%>
<template:titile value="��ѯ������������Ϣ"/>

<html:form  method="Post"
    action="/watchAction.do?method=queryWatch" onsubmit="return addSub()" >
    <template:formTable contentwidth="200" namewidth="200">


        <template:formTr name="��������" isOdd="false" >
            <html:text property="placeName" styleClass="inputtext" style="width:160" maxlength="45"/>
        </template:formTr>

        <logic:notEmpty name="reginfo">
          <template:formTr name="��������" isOdd="false">
            <select name="regionID" class="inputtext" style="width:180px" id="rId"  onchange="onChangeCon()" >
              <logic:present name="reginfo">
                <logic:iterate id="reginfoId" name="reginfo">
                  <option value="<bean:write name="reginfoId" property="regionid"/>">
                   <bean:write name="reginfoId" property="regionname"/>
                  </option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>
        </logic:notEmpty>


        <logic:notEmpty name="coninfo">
          <template:formTr name="��ά��λ" tagID="conTr">
            <select name="contractorid" class="inputtext" style="width:180px" id="cId" onchange="onChangeDeptUser()">
              <option value="">����</option>
              <logic:present name="coninfo">
                <logic:iterate id="coninfoId" name="coninfo">
                  <option value="<bean:write name="coninfoId" property="contractorid"/>">
                  <bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>

          <logic:present name="coninfo">
              <select name="workID"   style="display:none">
                <logic:iterate id="coninfoId" name="coninfo">
                    <option value="<bean:write name="coninfoId" property="contractorid"/>"><bean:write name="coninfoId" property="regionid"/>--<bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </select>
          </logic:present>

        </logic:notEmpty>

    <logic:equal value="group" name="PMType">
    <template:formTr name="Ѳ��ά����" isOdd="false">
      <select name="principal" class="inputtext" style="width:180px" id="uId">
        <option value="">����</option>
        <logic:present name="uinfo">
          <logic:iterate id="uinfoId" name="uinfo">
            <option value="<bean:write name="uinfoId" property="patrolid"/>">
              <bean:write name="uinfoId" property="patrolname"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    </logic:equal>

    <logic:notEqual value="group" name="PMType">
    <template:formTr name="Ѳ �� ��" isOdd="false">
      <select name="principal" class="inputtext" style="width:180px" id="uId">
        <option value="">����</option>
        <logic:present name="uinfo">
          <logic:iterate id="uinfoId" name="uinfo">
            <option value="<bean:write name="uinfoId" property="patrolid"/>">
              <bean:write name="uinfoId" property="patrolname"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    </logic:notEqual>

          <logic:present name="uinfo">
              <select name="userID"   style="display:none">
                <logic:iterate id="uinfoId" name="uinfo">
                    <option value="<bean:write name="uinfoId" property="patrolid"/>"><bean:write name="uinfoId" property="regionid"/>--<bean:write name="uinfoId" property="parentid"/>--<bean:write name="uinfoId" property="patrolname"/></option>
                </logic:iterate>
              </select>
          </logic:present>

	<template:formTr name="��ʼ����" >
      <html:text property="beginDate" styleClass="inputtext" size="25" maxlength="45"/>
      <apptag:date property="begindate"/>
    </template:formTr>

    <template:formTr name="��������"   tagID="eDate" >
        <html:text property="endDate"  value="" styleClass="inputtext" style="width:160" readonly="true"/>
        <input type='button' value='��' id='btn'  onclick="GetSelectDatenew('enddate')" style="font:'normal small-caps 6pt serif';" >
    </template:formTr>


        <template:formSubmit>
            <td >
                <html:submit styleClass="button" >��ѯ</html:submit>
            </td>
            <td >
                <html:reset styleClass="button"  >����</html:reset>
            </td>

        </template:formSubmit>

   </template:formTable>
</html:form>
