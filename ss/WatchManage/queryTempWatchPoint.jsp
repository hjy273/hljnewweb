<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<script language="javascript" type="">
<!--
function GetSelectDatenew(strID) {
//    if(watchExeQuery.begindate.value =="" || watchExeQuery.begindate.value ==null){
//    	alert("��û�п�ʼ����!");
//        watchExeQuery.begindate.focus();
//        return false;
//    }
    document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    //��ʼʱ��
    var yb = watchExeQuery.begindate.value.substring(0,4);
	var mb = parseInt(watchExeQuery.begindate.value.substring(5,7) ,10);
	var db = parseInt(watchExeQuery.begindate.value.substring(8,10),10);
    //����ʱ��
    var ye = watchExeQuery.enddate.value.substring(0,4);
	var me = parseInt(watchExeQuery.enddate.value.substring(5,7) ,10);
	var de = parseInt(watchExeQuery.enddate.value.substring(8,10),10);
    if(yb == "" && ye != "" ) {
	  alert("�������ѯ�Ŀ�ʼʱ��!");
      //document.all.item(strID).value="";
  	    watchExeQuery.begindate.focus();
  	    return false;
	}
	// �ڿ�ʼ�ͽ���ʱ�䶼���������������ܿ���ȵ��ж�
//	if(yb != "" && ye != "") {
	//	if(yb!=ye){
    //  		alert("��ѯʱ��β��ܿ����!");
    //  		document.all.item(strID).value="";
    //  		watchExeQuery.enddate.focus();
    //  		return false;
    //	}
		if(ye < yb) {
			alert("��ѯ�������ڲ���С�ڿ�ʼ����!");
      		document.all.item(strID).value="";
      		watchExeQuery.enddate.focus();
      		return false;
		}
		if((ye == yb ) && (me < mb  || de < db)) {
			alert("��ѯ�������ڲ���С�ڿ�ʼ����!");
      		document.all.item(strID).value="";
      		watchExeQuery.enddate.focus();
      		return false;
		}
//	}
    
//    if(mb!=me){
//      alert("���ܿ���!");
//      document.all.item(strID).value="";
//      watchExeQuery.enddate.focus();
//      return false;
//    }
     
	return true;
}
function addGoBack()
        {
        	try{
            	location.href = "${ctx}/watchAction.do?method=queryTempWatch";
                return true;
            }
            catch(e){
            	alert(e);
            }
        }

function addSub() {
	//��ʼʱ��
    var yb = watchExeQuery.begindate.value.substring(0,4);
	var mb = parseInt(watchExeQuery.begindate.value.substring(5,7) ,10);
	var db = parseInt(watchExeQuery.begindate.value.substring(8,10),10);
    //����ʱ��
    var ye = watchExeQuery.enddate.value.substring(0,4);
	var me = parseInt(watchExeQuery.enddate.value.substring(5,7) ,10);
	var de = parseInt(watchExeQuery.enddate.value.substring(8,10),10);
	if(yb == "" && ye != "" ) {
	  alert("�������ѯ�Ŀ�ʼʱ��!");
      //document.all.item(strID).value="";
  	    watchExeQuery.begindate.focus();
  	    return false;
	}
	if(yb != "" && ye != "") {
		if(ye < yb) {
			alert("��ѯ�������ڲ���С�ڿ�ʼ����!");
      		document.all.item(strID).value="";
      		watchExeQuery.enddate.focus();
      		return false;
		}
		if((ye == yb ) && (me < mb  || de < db)) {
     		alert("��ѯ�������ڲ���С�ڿ�ʼ����!");
      		document.all.item(strID).value="";
      		watchExeQuery.enddate.focus();
      		return false;
    	}
	}
	
	
    
//    if(mb!=me){
//      alert("���ܿ���!");
//      document.all.item(strID).value="";
//      watchExeQuery.enddate.focus();
//      return false;
//    }
     
	return true;
}
//-->
</script>
<Br/><Br/><Br/>
<template:titile value="����������ʱ���ѯ"/>
<html:form action="/WatchExecuteQueryAction.do?method=queryAllTempWatchPoint" onsubmit="return addSub()">
  <template:formTable contentwidth="200" namewidth="200" >


  <%
    UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
        "LOGIN_USER");
    if (userinfo.getRegionID().substring( 2, 6 ).equals( "0000" )) {
  %>
    <template:formTr name="��������">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid"/>
      <html:select property="regionid" styleClass="inputtext" style="width:180">
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
<%}%>

    <template:formTr name="����״̬">
      <select name="bedited" class="inputtext" style="width:180px">
        <option value="">����</option>
        <option value="1">���ƶ�</option>
        <option value="0">δ�ƶ�</option>
      </select>
    </template:formTr>
	<template:formTr name="��ʼ����" isOdd="false">
      <html:text property="begindate" styleClass="inputtext" size="25" maxlength="45"/>
      <apptag:date property="begindate"/>
    </template:formTr>

    <template:formTr name="��������"   tagID="eDate" >
        <html:text property="enddate"  value="" styleClass="inputtext" style="width:160" readonly="true"/>
        <input type='button' value='��' id='btn'  onclick="GetSelectDatenew('enddate')" style="font:'normal small-caps 6pt serif';" >
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button" >��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">����</html:reset>
      </td>
    </template:formSubmit>

  </template:formTable>
</html:form>
