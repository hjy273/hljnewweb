<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.troublemanage.beans.*"%>
<%@page import="com.cabletech.troublemanage.domainobjects.*"%>
<%@ page import="com.cabletech.baseinfo.domainobjects.*" %>
<%AccidentBean bean = (AccidentBean) request.getAttribute("AccidentBean");
UserInfo userinfo=(UserInfo)session.getAttribute("LOGIN_USER");%>
<style type="text/css">
<!--
.hiInput {
    width: 100%;
    background-color:transparent;
    border-bottom-width: 1px;
    border-top-style: none;
    border-right-style: none;
    border-bottom-style: solid;
    border-left-style: none;
    border-bottom-color: #000000;
}

.commonHiInput {
    background-color:transparent;
    border-bottom-width: 1px;
    border-top-style: none;
    border-right-style: none;
    border-bottom-style: solid;
    border-left-style: none;
    border-bottom-color: #000000;
}
-->
</style>
<script language="javascript">
function mySubmit1(){
    var a=document.getElementsByTagName("td");

    for(var i=0;i<a.length;i++){
        if (a[i].innerText=="null"){
            a[i].innerText="";
        }
    }
     var b=document.getElementsByTagName("input");
    for(var i=0;i<b.length;i++){
        if (b[i].value=="null"){
            b[i].value="";
        }
    }
}
</script>
<script language="javascript" type="">
<!--
var iteName = "";
fileNum=0;
    //�ű����ɵ�ɾ����ť��ɾ������
	function deleteRow(){
      	//��ð�ť�����е�id
     	var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
        //��idת��Ϊ�������е�����,��ɾ��
      	for(i =0; i<uploadID.rows.length;i++){
        	if(uploadID.rows[i].id == rowid){
            	uploadID.deleteRow(uploadID.rows[i].rowIndex);
                fileNum--;
            }
        }
		}
    //���һ������
    function addRow(){

    	var  onerow=uploadID.insertRow(-1);
        onerow.id = uploadID.rows.length-2;

        var   cell1=onerow.insertCell();
		var   cell2=onerow.insertCell();


		//����һ�������
        var t1 = document.createElement("input");
        //t1.name = "filename"+onerow.id;//alert(onerow.id);
        t1.name = "uploadFile["+ fileNum + "].file";
		t1.type= "file";
		t1.id = "text" + onerow.id;
		t1.width="300";
        fileNum++;

        //����ɾ����ť
        var b1 =document.createElement("button");
        b1.value = "ɾ������";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");


        cell1.appendChild(t1);//����
        cell2.appendChild(b1);
	}
    function valiD(){
      var mysplit = /^[-\+]?\d+(\.\d+)?$/;
      if(mysplit.test(this.value)){
        return true;
      }
      else{
        alert("����д�Ĳ�������,����������");
        this.focus();
        return false;
      }
    }
function getTimeWin(objName){

    iteName = objName;

    showx = event.screenX - event.offsetX - 4 - 210 ;
    showy = event.screenY - event.offsetY + 18;

    var timeWin = window.showModalDialog("${ctx}/common/CalendarDlg.htm",self, "dialogWidth:235px; dialogHeight:265px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;help:no");
    if(timeWin!=null){
        objName.value=timeWin;
    }
}
function postIt(){
    //var url = "${ctx}/accidentAction.do?method=acceptAccidentTask&id=" + id.value + "&monitor="+monitor.value+"&commander="+commander.value;
    //self.location.replace(url);
	 var d = new Date();
	 document.all.year.value=d.getFullYear();
	 document.all.month.value=d.getMonth()+1;
    AccidentBean.submit();
}
//-->
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�ޱ����ĵ�</title>
</head>

<body>
<br>

<html:form action="/accidentAction.do?method=completeAccidentForm" enctype="multipart/form-data">
<input type="hidden" name="id" value="<%=bean.getKeyid()%>">

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">��д�ϰ������浥</td>
  </tr>
</table>
<br>
<table width="85%"  border="0" align="center" cellpadding="5" cellspacing="0">
  <tr>
    <input type="hidden"  value="<%=bean.getContractorid()%>" name="companyname"/>
  	 <input type="hidden" name="bconfirman" class="hiInput" value="<%=userinfo.getUserName()%>">

    <td>&nbsp;&nbsp;�������浥λ��<%=bean.getContractorid()%>&nbsp;&nbsp;&nbsp;&nbsp;
      ��ˣ�<%=userinfo.getUserName()%>&nbsp;&nbsp;&nbsp;&nbsp;
      �ʱ�䣺<input type="text" name="year" class="commonHiInput" style="width:50;" >�� <input type="text" name="month" class="commonHiInput" style="width:50;" >�� </td>
   </tr>
</table>
<table width="85%" align="center" cellpadding="5" cellspacing="1" bgcolor="#999999">
  <tr>
    <td width="15%" rowspan="4" align="center" class=trcolor >�ϰ�����
    </td>
    <td class=trcolor width="25%" >�������� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getSendtime().substring(0,10) %></td>
  </tr>
  <tr>
    <td class=trcolor >����ʱ�� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getSendtime().substring(11) %></td>
  </tr>
  <tr>
    <td class=trcolor >�ϰ����� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getLid() %></td>
  </tr>
  <tr>
    <td class=trcolor >�ص��ʯ�� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getPid() %></td>
  </tr>
  <!--<tr>
    <td width="15%" rowspan="10" align="center" class=trcolor >�ϰ�����
    </td>
    <td class=trcolor >�׷���Ա��������վʱ�� </td>
    <td colspan="3" bgcolor="#FFFFFF"><input type="text" name="befortime" class="hiInput" onclick="getTimeWin(this)"></td>
  </tr>
  <tr>
    <td class=trcolor >�׷�֪ͨʱ�� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getNoticetime() %></td>
  </tr>
  <tr>
    <td class=trcolor >�׷������ </td>
    <td colspan="3" bgcolor="#FFFFFF"><input type="text" name="cooperateman" class="hiInput" value="<%=bean.getCooperateman() %>"></td>
  </tr>
  <tr>
    <td class=trcolor >�ҷ����� ����ʱ�� </td>
    <td colspan="3" bgcolor="#FFFFFF"><input type="text" name="testtime" class="hiInput" onclick="getTimeWin(this)"></td>
  </tr>
  <tr>
    <td class=trcolor >������ </td>
    <td colspan="3" bgcolor="#FFFFFF"><input type="text" name="testman" class="hiInput"></td>
  </tr>
  <tr>
    <td class=trcolor >���о������ж� </td>
    <td colspan="3" bgcolor="#FFFFFF"><input type="text" name="distance" class="hiInput"></td>
  </tr>
  <tr>
    <td class=trcolor >ʵ�� ���о������ж� </td>
    <td colspan="3" bgcolor="#FFFFFF"><input type="text" name="realdistance" class="hiInput"></td>
  </tr>
  <tr>
    <td class=trcolor >�۽��� </td>
    <td colspan="3" bgcolor="#FFFFFF"><input type="text" name="fixedman" class="hiInput"></td>
  </tr>
  <tr>
    <td class=trcolor >����� </td>
    <td colspan="3" bgcolor="#FFFFFF"><input type="text" name="monitor" class="hiInput"></td>
  </tr>
  <tr>
    <td class=trcolor >ָ�� </td>
    <td colspan="3" bgcolor="#FFFFFF"><input type="text" name="commander" class="hiInput"></td>
  </tr>
  <tr>
    <td width="15%" rowspan="6" align="center" class=trcolor >�ϰ���ʱ
    </td>
    <td class=trcolor >ϵͳ / ��о�� </td>
    <td width="20%" class=trcolor >��ʱ�� ͨʱ�� </td>
    <td width="21%" class=trcolor >�޸�ʱ�� </td>
    <td width="24%" class=trcolor >��ʱ </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF"><input type="text" name="corecode" class="hiInput"></td>
    <td width="20%" bgcolor="#FFFFFF"><input type="text" name="tempfixedtime" class="hiInput" onclick="getTimeWin(this)"></td>
    <td width="21%" bgcolor="#FFFFFF"><input type="text" name="fixedtime" class="hiInput" onclick="getTimeWin(this)"></td>
    <td width="24%" bgcolor="#FFFFFF"><input type="text" name="diachronic" class="hiInput"></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF"><input type="text" name="corecode" class="hiInput"></td>
    <td width="20%" bgcolor="#FFFFFF"><input type="text" name="tempfixedtime" class="hiInput" onclick="getTimeWin(this)"></td>
    <td width="21%" bgcolor="#FFFFFF"><input type="text" name="fixedtime" class="hiInput" onclick="getTimeWin(this)"></td>
    <td width="24%" bgcolor="#FFFFFF"><input type="text" name="diachronic" class="hiInput"></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF"><input type="text" name="corecode" class="hiInput"></td>
    <td width="20%" bgcolor="#FFFFFF"><input type="text" name="tempfixedtime" class="hiInput" onclick="getTimeWin(this)"></td>
    <td width="21%" bgcolor="#FFFFFF"><input type="text" name="fixedtime" class="hiInput" onclick="getTimeWin(this)"></td>
    <td width="24%" bgcolor="#FFFFFF"><input type="text" name="diachronic" class="hiInput"></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF"><input type="text" name="corecode" class="hiInput"></td>
    <td width="20%" bgcolor="#FFFFFF"><input type="text" name="tempfixedtime" class="hiInput" onclick="getTimeWin(this)"></td>
    <td width="21%" bgcolor="#FFFFFF"><input type="text" name="fixedtime" class="hiInput" onclick="getTimeWin(this)"></td>
    <td width="24%" bgcolor="#FFFFFF"><input type="text" name="diachronic" class="hiInput"></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF"><input type="text" name="corecode" class="hiInput"></td>
    <td width="20%" bgcolor="#FFFFFF"><input type="text" name="tempfixedtime" class="hiInput" onclick="getTimeWin(this)"></td>
    <td width="21%" bgcolor="#FFFFFF"><input type="text" name="fixedtime" class="hiInput" onclick="getTimeWin(this)"></td>
    <td width="24%" bgcolor="#FFFFFF"><input type="text" name="diachronic" class="hiInput"></td>
  </tr>-->
  <tr>
    <td align="center" class=trcolor >����ԭ��
    <td colspan="4" valign="top" bgcolor="#FFFFFF" class=trcolor >
    <%=bean.getResonandfix() %><!--
    <input type="text" name="resonandfix" class="hiInput">--></td>
  </tr>
  <tr>
    <td align="center" class=trcolor >����</td>
    <td  colspan="4" valign="top" bgcolor="#FFFFFF" class=trcolor >
    <table id="uploadID" width=100% border="0" align="center" cellpadding="0" cellspacing="0">
    	<tr class=trcolor align="center"></tr>

	</table>
    <html:button styleClass="button" onclick="addRow()" property="action">��Ӹ���</html:button>
    </td>
  </tr>
</table><!--
<table width="85%"  border="0" align="center" cellpadding="15" cellspacing="0">
  <tr>
    <td width="80">�ҷ���ˣ�</td>
    <td align="left"><input type="text" name="breportman" class="hiInput" value="<%=bean.getBreportman()%>"></td>
  </tr>
  <tr>
    <td width="80">�׷�ȷ���ˣ�</td>
    <td align="left"><input type="text" name="bconfirmman" class="hiInput" value="<%=bean.getBconfirmman()%>"></td>
  </tr>
</table>-->
<p>
<p>
<template:formSubmit>
<td>
    <html:button property="action" styleClass="lbutton" onclick="postIt()">����¹ʴ���</html:button>
</td>
</template:formSubmit>
<br>
<br>
</html:form>
</body>
</html>
<script language="javascript">
 var d=new Date();
 document.all.year.value=d.getFullYear();
 document.all.month.value=d.getMonth()+1;
 mySubmit1();
</script>
