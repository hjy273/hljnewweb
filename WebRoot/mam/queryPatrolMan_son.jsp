<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<jsp:useBean id="selectForTag" class="com.cabletech.commons.tags.SelectForTag" scope="request"/>


<script language="javascript" type="">

///////////////////////////////ʾ������////////////////////////////
    function onChangeInit(){
       document.all.uId.options.length=0;
    }
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
      if(k==0)
       document.all.cId.options.length=0;
     if(document.all.rId.value.substring(2,6)!='0000'){
         onChangeDeptUser();
     }else{
       document.all.uId.options.length=0;
     }

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
      //alert("" + iSeek);
      while(iSeek != ""){
           iCount++;
         iIndex =parseInt( ( iStart + iEnd )/2);
        // alert("" + iArray[iIndex].text.substring(8,18));
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
    }






//////////////////////////////////////////////////////////////////////////////////////////////////
</script>
<%
String condition="";
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
//���ƶ�
if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
   condition = " WHERE regionid IN ('"+userinfo.getRegionID()+"') AND state IS NULL";
}
//�д�ά
if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL  and parentid='"+userinfo.getDeptID()+"' ";
}
//ʡ�ƶ�
if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL";
}
//ʡ��ά
if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE parentid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"+userinfo.getDeptID()+"') order by PARENTID ";
}
List groupCollection = selectForTag.getSelectForTag("patrolname","patrolid","patrolmaninfo",condition);
request.setAttribute("groupCollection",groupCollection);
%>

<%
if(userinfo.getRegionID().substring( 2, 6 ).equals( "0000" )){%>
<body onload="onChangeInit()">
<% }else{%>
<body>
<% }%>
<template:titile value="��ѯѲ��Ա��Ϣ"/>
<html:form method="Post" action="/patrolSonAction.do?method=queryPatrolSon">
  <template:formTable contentwidth="200" namewidth="300">

        <logic:notEmpty name="reginfo">
          <template:formTr name="��������" >
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
            <select name="parentID" class="inputtext" style="width:180px" id="cId" onchange="onChangeDeptUser()">
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
              <select name="workID" style="display:none">
                <logic:iterate id="coninfoId" name="coninfo">
                    <option value="<bean:write name="coninfoId" property="contractorid"/>"><bean:write name="coninfoId" property="regionid"/>--<bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </select>
          </logic:present>
      </logic:notEmpty>

    <logic:equal value="group" name="PMType">
    <template:formTr name="Ѳ��ά����" >
      <select name="patrolID" class="inputtext" style="width:180px" id="uId">
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
    <template:formTr name="Ѳ �� ��" >
      <select name="patrolID" class="inputtext" style="width:180px" id="uId">
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
      <select name="userID" style="display:none">
        <logic:iterate id="uinfoId" name="uinfo">
            <option value="<bean:write name="uinfoId" property="patrolid"/>"><bean:write name="uinfoId" property="regionid"/>--<bean:write name="uinfoId" property="parentid"/>--<bean:write name="uinfoId" property="patrolname"/></option>
        </logic:iterate>
      </select>
   </logic:present>

    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��">
      <html:select property="sex" styleClass="inputtext" style="width:180">
        <html:option value="">����</html:option>
        <html:option value="1">��</html:option>
        <html:option value="2">Ů</html:option>
      </html:select>
    </template:formTr>
   <template:formTr name="����״̬"  >
      <html:select property="jobState" styleClass="inputtext" style="width:180">
        <html:option value="">����</html:option>
        <html:option value="1">�ڸ�</html:option>
        <html:option value="2">�ݼ�</html:option>
        <html:option value="3">��ְ</html:option>
      </html:select>
    </template:formTr>


    <template:formSubmit>
      <td>
        <html:submit styleClass="button" >��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
