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
//    if(yb!=ye){
//      alert("���ܿ���!");
//      document.all.item(strID).value="";
//       watchExeQuery.enddate.focus();
//      return false;
//    }
//    if(mb!=me){
//      alert("���ܿ���!");
//      document.all.item(strID).value="";
//      watchExeQuery.enddate.focus();
//      return false;
//    }
     if(de < db || me < mb || ye < yb){
      alert("�������ڲ���С�ڿ�ʼ����!");
      document.all.item(strID).value="";
      watchExeQuery.enddate.focus();
      return false;
    }
	return true;
}
var flag = "0";
function settype(v){

  if(v == 1){
	flag = 1;
//    contractorTr.style.display = "";
    patrolTr.style.display = "none";
    watchTr.style.display = "none";
    watchStaBean.executorid.value="";
    watchStaBean.watchid.value="";
    watchStaBean.statype.value = "1"; //��ά��λ
  }

  if(v == 2){
	flag = 2;
//    contractorTr.style.display = "none";
    patrolTr.style.display = "";
    watchTr.style.display = "none";
//    watchStaBean.contractorid.value="";
//    watchStaBean.watchid.value="";
//    watchStaBean.statype.value = "2"; //Ѳ��Ա
  }

  if(v == 3){
	flag = 3;
//    contractorTr.style.display = "none";
    patrolTr.style.display = "none";
    watchTr.style.display = "";
//    watchStaBean.contractorid.value="";
//    watchStaBean.executorid.value="";
//    watchStaBean.statype.value = "3"; //��������
  }

}
function addGoBack()
        {
        	try{
            	location.href = "${ctx}/WatchManage/queryWatchexeSta.jsp";
                return true;
            }
            catch(e){
            	alert(e);
            }
        }
function makeSize(enlargeFlag){
	if(enlargeFlag == 0){

		mainSpan.style.display = "none";
		iframeDiv.style.height = "400";
	}else{
		mainSpan.style.display = "";
		iframeDiv.style.height = "320";
	}
}
//����ʹ�ά
    function onChangeCon(){
          k=0;
          for( i=0;i<document.all.workID.options.length;i++){
             if(document.all.workID.options[i].text.substring(0,6)== document.all.rId.value){
                k++;
                document.all.cId.options.length=k;
                document.all.cId.options[k-1].value=document.all.workID.options[i].value;
                document.all.cId.options[k-1].text=document.all.workID.options[i].text.substring(8);
             }
           }
           if(k==0){
             document.all.cId.options.length=1;
             document.all.cId.options[0].value="";
             document.all.cId.options[0].text="��";
           }
           onChangeDeptUser();
           onChangeWatch();
    }
    //����Ͷ���
    function onChangeWatch(){
          k=1;
          for( i=0;i<document.all.wID.options.length;i++){
             if(document.all.wID.options[i].text.substring(0,6)== document.all.rId.value){
                 k++;
                document.all.wId.options.length=k;
                document.all.wId.options[0].value="";
                document.all.wId.options[0].text="����";
                document.all.wId.options[k-1].value=document.all.wID.options[i].value;
                document.all.wId.options[k-1].text=document.all.wID.options[i].text.substring(20);
             }
           }
           if(k==1){
             document.all.wId.options.length=1;
             document.all.wId.options[0].value="";
             document.all.wId.options[0].text="����������";
           }
    }
    function onChangeDeptWatch(){
          k=1;
          for( i=0;i<document.all.wID.options.length;i++){
             if(document.all.wID.options[i].text.substring(8,18)== document.all.cId.value){
                 k++;
                document.all.wId.options.length=k;
                document.all.wId.options[0].value="";
                document.all.wId.options[0].text="����";
                document.all.wId.options[k-1].value=document.all.wID.options[i].value;
                document.all.wId.options[k-1].text=document.all.wID.options[i].text.substring(20);
             }
           }
           if(k==1){
             document.all.wId.options.length=1;
             document.all.wId.options[0].value="";
             document.all.wId.options[0].text="����������";
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
          document.all.uId.options[k-1].text=document.all.userID.options[i].text.substring(20,document.all.userID.options.length);
      }
      if(k==1){
        document.all.uId.options.length=1;
        document.all.uId.options[0].value="";
        document.all.uId.options[0].text="��";
      }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////
</script>
<Br/>
<%
UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
"LOGIN_USER");
if(userinfo.getRegionID().substring( 2, 6 ).equals( "0000" )){%>
<body onload="onChangeCon()">
<% }else{%>
<body>
<% }%>
<template:titile value="��������Ѳ����ϸ"/>
<html:form action="/WatchExecuteQueryAction.do?method=queryWactchExecute">
  <template:formTable contentwidth="200" namewidth="200">

    <logic:notEmpty name="reginfo">
          <template:formTr name="��������" isOdd="false">
            <select name="regionid" class="inputtext" style="width:180px" id="rId" onchange="onChangeCon()">
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

      <template:formTr name="ͳ������" isOdd="false">

        <logic:equal value="group" name="PMType">
          	<input type="radio" name="type" value="2" onclick="settype(2)"/> Ѳ��ά����
        </logic:equal>
        <logic:notEqual value="group" name="PMType">
          	<input type="radio" name="type" value="2" onclick="settype(2)"/> Ѳ����
        </logic:notEqual>
        <input type="radio" name="type" value="3" onclick="settype(3)"/> ��������
        <html:hidden property="statype"/>
      </template:formTr>
        <logic:notEmpty name="coninfo">
          <template:formTr name="��ά��λ" tagID="contractorTr">
            <select name="contractorid" class="inputtext" style="width:180px" id="cId" onchange="onChangeDeptUser(),onChangeDeptWatch()">
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
    <template:formTr name="Ѳ��ά����" isOdd="false"  tagID="patrolTr" style="display:none">
      <select name="executorid" class="inputtext" style="width:180px" id="uId">
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
    <template:formTr name="Ѳ �� ��" isOdd="false" tagID="patrolTr" style="display:none">
      <select name="executorid" class="inputtext" style="width:180px" id="uId">
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


    <template:formTr name="��������" tagID="watchTr" style="display:none">
      <select name="watchid" class="inputtext" style="width:180px" id="wId">
        <option value="">����</option>
        <logic:present name="watchinfo">
          <logic:iterate id="watchinfoId" name="watchinfo">
            <option value="<bean:write name="watchinfoId" property="placeid"/>">
              <bean:write name="watchinfoId" property="placename"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>

    <logic:present name="watchinfo">
      <select name="wID"   style="display:none">
        <logic:iterate id="watchinfoId" name="watchinfo">
          <option value="<bean:write name="watchinfoId" property="placeid"/>"><bean:write name="watchinfoId" property="regionid"/>--<bean:write name="watchinfoId" property="contractorid"/>--<bean:write name="watchinfoId" property="placename"/></option>
        </logic:iterate>
      </select>
    </logic:present>

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
        <html:submit styleClass="button">��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">����</html:reset>
      </td>
      <td>
        <html:button property="action" styleClass="button" onclick="addGoBack()" >����</html:button>
      </td>
    </template:formSubmit>

  </template:formTable>
</html:form>
