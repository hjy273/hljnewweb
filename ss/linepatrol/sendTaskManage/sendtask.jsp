<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<SCRIPT language=JavaScript src="${ctx}/common/PopupDlg.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/common/Comm.js" type=""></SCRIPT>

<%@ page import="com.cabletech.linepatrol.sendtask.beans.*;" %>

<html>
<head>
<title>
sendtask
</title>
<script type="" language="javascript">
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
	var rowArr = new Array();//һ�е���Ϣ
	var infoArr = new Array();//���е���Ϣ

	var rowDept = new Array();//һ�е���Ϣ
	var infoDept = new Array();//���е���Ϣ
	 //��ʼ������
    function initArrayDept(deptid,deptname){
    	rowDept[0] = deptid;
        rowDept[1] = deptname;
        infoDept[infoDept.length] = rowDept;
        rowDept = new Array();
        return true;
    }

	function setSelecteTime(time) {
    	document.all.item(iteName).value = time;
	}

    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}

	function getTimeWin(objName){
		iteName = objName;
		showx = event.screenX - event.offsetX - 4 - 210 ;
		showy = event.screenY - event.offsetY + 18;
		var timeWin = window.showModalDialog("${ctx}/common/time.html",self, "dialogWidth:255px; dialogHeight:285px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;help:no");
	}

    //��ʼ������
    function initArray(deptid,userid,username){
    	rowArr[0] = deptid;
        rowArr[1] = userid;
        rowArr[2] = username;
        infoArr[infoArr.length] = rowArr;
        rowArr = new Array();
        return true;
    }
    function onSelectChange(objId )
	{
		var slt1Obj=document.getElementById(objId);
        var slt2Obj=document.getElementById("userid1");
		i=0;
		for(j= 0; j< this.infoArr.length; j++ )
		{
			if(slt1Obj.value == infoArr[j][0])
			{
				i++;
			}
		}
        slt2Obj.options.length = i;
		k=0;
		for(j =0;j<this.infoArr.length;j++)
		{
			if(slt1Obj.value == infoArr[j][0])
				{
                  //UPuserid
					slt2Obj.options[k].text=this.infoArr[j][2];
					slt2Obj.options[k].value=this.infoArr[j][1];
                    var aObj = document.getElementById("UPuserid");
                    if(aObj != null){
                    	if(aObj.value == this.infoArr[j][1])
                        	slt2Obj.options[k].selected="true";
                    }
					k++;

				}
      }
		return true;
	}

    function bodyOnload(){
      var Obj = document.getElementById("deptid");
      if(Obj != null){
      	 onSelectChange("deptid" ) ;
      }
    }
	
	//��̬�ϴ�����
	function addRowMod() {
			var maxSeq = parseInt(document.getElementById('maxSeq').value);
			//alert(maxSeq);
			maxSeq ++;//�Լ�
			var tableBody = document.getElementsByTagName("tbody")[2];
			//var tableBody = document.getElementById("uploadID");
			var newTr = document.createElement("tr");//����һ��tr
			newTr.id = maxSeq;
			newTr.className = 'trStyle';
			
			var checkTd = document.createElement("td");//����һ��TD��+��TR��
			checkTd.innerHTML = "<input type='checkbox' name='ifCheck' value='" + maxSeq + "'>";
			checkTd.className = 'selTd';
			newTr.appendChild(checkTd);
			
			var fileTd = document.createElement("td");//�ϴ��ļ���td��+��TR��
			fileTd.className = 'rigthTdStyle';
			var fileUpLoad = document.createElement("input");
			fileUpLoad.type = 'file';
			fileUpLoad.className = 'fileStyle';
			fileUpLoad.name = 'uploadFile[' + maxSeq + '].file';
			fileUpLoad.id = 'uploadFile[' + maxSeq + '].file';
			fileTd.appendChild(fileUpLoad);
			newTr.appendChild(fileTd);
			
			document.getElementById('maxSeq').value = maxSeq;//�����������ֵ
			tableBody.appendChild(newTr);
		}
		
		function checkOrCancel() {
			var all = document.getElementById('sel');
			if(all.checked) {
				checkAll();
			} else {
				cancelCheck();
			}
		}

		function checkAll() {
			var subChecks = document.getElementsByName("ifCheck");
			var length = subChecks.length;
			for(var i = 0; i < length; i++) {
				subChecks[i].checked = true;
			}		
		}
		
		function cancelCheck() {
			var allChecks = document.getElementsByName('ifCheck');
			var length = allChecks.length;
			for(var i = 0; i < length; i++) {
				allChecks[i].checked = false;
			}
		}
		
		
		function delRow() {
			var delChecks = document.getElementsByName('ifCheck');
			var tableBody = document.getElementsByTagName('tbody')[2];
			var delRows = new Array(delChecks.length);
			var delid = 0;
			for(i = 0; i < delChecks.length; i++) {
				if(delChecks[i].checked) {
					delRows[delid++] = document.getElementById(delChecks[i].value)
				}
			}
			if(delid != 0) {
				if(confirm("ȷʵҪɾ����")) {
					for(i = 0; i <= delid; i++) {
						if(delRows[i] != null && delRows[i].nodeType == 1) {
							tableBody.removeChild(delRows[i]);
						}
					}
				document.getElementById('sel').checked = false;
				}
			}
		}


	  fileNum=0;
	 //�ű����ɵ�ɾ����  ť��ɾ������
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


 //��ʾһ���ɵ���ϸ��Ϣ
 function toGetForm(id,subid){
 	var url = "${ctx}/SendTaskAction.do?method=showOneSendTask&id=" + id +"&subid="+subid;
	//window.open(url);
    window.location.href=url;
 }
 function toUpForm(id,subid){
	//if(result == "��ǩ") {
	//	alert("���ɵ��Ѿ�����ǩ,�����޸�!");
	//	return;
	//}
    //if((result =="��ǩ��") && (workstate =="������" || workstate == "��ǩ��")){
	// 	var url = "${ctx}/SendTaskAction.do?method=showOneToUp&id=" + id+"&subid="+subid;;
	//    window.location.href=url;
  	//}else
   // alert("���ɵ��Ѿ�ǩ�ջ��ߴ浵,�����޸�!");

	var url = "${ctx}/SendTaskAction.do?method=showOneToUp&id=" + id+"&subid="+subid;;
	window.location.href=url;
 }

 function addGoBack(){
 	var url ="${ctx}/SendTaskAction.do?method=showAllSendTask";
    window.location.href=url;
 }



       //�����Ƿ�����
	 function valiD(id){
      	var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        var obj = document.getElementById(id);
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("����д�����ֲ��Ϸ�,����������");
            obj.focus();
            obj.value = "0.00";
            return false;
        }
    }

 //ѡ������
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}
    function valCharLength(Value){
      var j=0;
      var s = Value;
      for   (var   i=0;   i<s.length;   i++)
      {
              if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
              else   j++
      }
      return j;
    }

	function checkSub() {
//		var userlen  = document.getElementById("DeptTableId").rows.length;
//		for(var v = 1;v<userlen+1;v++){
//			var userid = document.getElementById("userid"+v).value;
//			for(var y = 1;y<userlen+1;y++){
//				var uid = document.getElementById("userid"+y).value;
//				if(v!=y && userid==uid){
//					alert("�����˲����ظ�!");
//					return;
//				}
//
//			}
//		}

	 SendTaskBean.acceptdeptid.value=SendTaskBean.dept_id.value;
	 SendTaskBean.acceptuserid.value=SendTaskBean.user_id.value;
	 SendTaskBean.mobileId.value=SendTaskBean.mobile_id.value;
	 if(SendTaskBean.sendtype.value==null||SendTaskBean.sendtype.value==""||SendTaskBean.sendtype.value.trim()==""){
        alert("�����빤�����");
        return;
      }
	  
      if(SendTaskBean.begintime.value == null || SendTaskBean.begintime.value == ""){
      	alert("�����봦�����޵����ڣ�");
        return;
      }
      if(SendTaskBean.time.value == null || SendTaskBean.time.value == ""){
      	alert("�����봦�����޵�ʱ�䣡");
        return;
      }
		
	  var d = new Date();
	  var s = '';
	  s += d.getYear() + "/"; 
      if((d.getMonth() + 1) > 9) {
		s += (d.getMonth() + 1) + "/";            // ��ȡ�·ݡ�
	  } else {
		s += "0" + (d.getMonth() + 1) + "/";            // ��ȡ�·ݡ�
	  }
	  
   	  // ��ȡ�ա�
	  if(d.getDate() > 9) {
			s += d.getDate();
	  } else {
			s = s + "0" + d.getDate()
	  }

	  if(s > SendTaskBean.begintime.value) {
		alert("�������޲���С�ڵ�ǰʱ��!");
			return;
	  } 
// ʱ����ж�
	  if(s == SendTaskBean.begintime.value) {
	  		 var hour=d.getHours(); 
			 var minute=d.getMinutes(); 
			 var second=d.getSeconds(); 
			 if(hour > 9) {
			 	timestr = hour + ":";
			 } else {
			 	timestr = "0" + hour + ":";
			 }

             if(minute > 9) {
			 	timestr += minute + ":";
			 } else {
			 	timestr += "0" + minute + ":";
			 }

			if(second > 9) {
			 	timestr += second ;
			 } else {
			 	timestr += "0" + second ;
			 }
			 if(timestr > SendTaskBean.time.value) {
				alert("�������޲���С�ڵ�ǰʱ��!");
				return;
			 }
	   }

      if(valCharLength(SendTaskBean.sendtext.value)>1020){
        alert("����˵������̫�࣡���ܳ���510������");
        return;
      }
      if(SendTaskBean.sendtopic.value==null||SendTaskBean.sendtopic.value==""||SendTaskBean.sendtopic.value.trim()==""){
        alert("�������������⣡");
        return;
      }
      if((SendTaskBean.sendtext.value==null||SendTaskBean.sendtext.value==""||SendTaskBean.sendtext.value.trim()=="")&&!haveArr()){
        alert("����������˵��������Ӹ�����");
        return;
      }
	  var allCheck = document.getElementsByName('ifCheck');
	  var length = allCheck.length;
	  //alert(length);
	  for(var i = 0; i < length; i++) {		
		//var index = i + 1;
		var index = allCheck[i].value;
		//alert(index)
		var pathText = document.getElementById('uploadFile[' + index + '].file');
		//alert(pathText);
		var path = pathText.value;
		//alert(path);
		if(path.length == 0) {
			alert("��ѡ��Ҫ�ϴ��ĸ�����·����\n���û��Ҫ�ϴ��ĸ�����ɾ��!");
			pathText.focus();
			return false;
		}
	 }

    	SendTaskBean.processterm.value = SendTaskBean.begintime.value + " " + SendTaskBean.time.value;

        SendTaskBean.submit();
		
	}

    function onSubmitClick(){
	 SendTaskBean.acceptdeptid.value=SendTaskBean.dept_id.value;
	 SendTaskBean.acceptuserid.value=SendTaskBean.user_id.value;
	 SendTaskBean.mobileId.value=SendTaskBean.mobile_id.value;
      if(SendTaskBean.sendtype.value==null||SendTaskBean.sendtype.value==""||SendTaskBean.sendtype.value.trim()==""){
        alert("�����빤�����");
        return;
      }
	  
      if(SendTaskBean.begintime.value == null || SendTaskBean.begintime.value == ""){
      	alert("�����봦�����޵����ڣ�");
        return;
      }
      if(SendTaskBean.time.value == null || SendTaskBean.time.value == ""){
      	alert("�����봦�����޵�ʱ�䣡");
        return;
      }
		
	
      if(valCharLength(SendTaskBean.sendtext.value)>1020){
        alert("����˵������̫�࣡���ܳ���510������");
        return;
      }
      if(SendTaskBean.sendtopic.value==null||SendTaskBean.sendtopic.value==""||SendTaskBean.sendtopic.value.trim()==""){
        alert("�������������⣡");
        return;
      }
      if((SendTaskBean.sendtext.value==null||SendTaskBean.sendtext.value==""||SendTaskBean.sendtext.value.trim()=="")&&!haveArr()){
        alert("����������˵��������Ӹ�����");
        return;
      }
		var allCheck = document.getElementsByName('ifCheck');
		var length = allCheck.length;
		
		for(var i = 0; i < length; i++) {
			var index = i + 1;
			var pathText = document.getElementById('uploadFile[' + index + '].file');
			var path = pathText.value;
			if(path.length == 0) {
				alert("��ѡ��Ҫ�ϴ��ĸ�����·����\n���û��Ҫ�ϴ��ĸ�����ɾ��!");
				pathText.focus();
				return false;
			}
		}

    	SendTaskBean.processterm.value = SendTaskBean.begintime.value + " " + SendTaskBean.time.value;
        SendTaskBean.submit();
    }
function haveArr(){
  if((typeof(SendTaskBean.text0)=="undefined"))
    return false;
  else{
    for(i=0;i<uploadID.rows.length;i++){
      if(document.forms["SendTaskBean"].elements["text"+i].value!=""){
        return true;
      }
    }
  }
  return false;
}
 function oneTaskGoBack() {
	var url="SendTaskAction.do?method=doQueryAfterMod";
	window.location.href=url;
}
var currentId;
function addDept() {
        
	var onerow = document.getElementById("DeptTableId").insertRow(-1);
	onerow.id = document.getElementById("DeptTableId").rows.length;
	currentId = onerow.id;
    var cell1 = onerow.insertCell();
	var cell2 = onerow.insertCell();
	var cell3 = onerow.insertCell();
	var cell4 = onerow.insertCell();
	var cell5 = onerow.insertCell();

	onerow.className = "trcolor";
	
	// ��һ��Ԫ��
	cell1.innerText = "������";
	cell1.className = "tdr"

	// ������Ԫ��
	cell3.innerText = "�� �� ��";
	cell3.className = "tdr"

	// ���嵥Ԫ��	
	cell5.className = "td1"
	//����ɾ����ť
    var b1 =document.createElement("button");
    b1.value = "ɾ��";
    b1.id = "b" + onerow.id;
	b1.className = "button";
    b1.onclick=deleteRowDept;
	cell5.appendChild(b1);

	// �ڶ���Ԫ��
	//����һ��select
    var s2 = document.createElement("select");
    s2.name = "acceptuserid";
    s2.id = "userid" + onerow.id;
    s2.className="inputtext";
    s2.style.width="100";
	cell4.className = "td1"
	cell4.appendChild(s2);

	// �ڶ���Ԫ��
	//����һ��select
    var s1 = document.createElement("select");
    s1.name = "acceptdeptid";
	s1.options.length = infoDept.length;
    s1.id = "deptid" + onerow.id;
    s1.className="inputtext";
    s1.style.width="120";
	s1.onchange =onSelectChange2;
	for(i = 0; i<infoDept.length ;i++){
            s1.options[i].value = infoDept[i][0];
            s1.options[i].text = infoDept[i][1];
    }
	cell2.className = "td1"
	cell2.appendChild(s1);
	
	onSelectChange2();
	
}

//�ű����ɵ�ɾ����  ť��ɾ������
    function deleteRowDept(){
         //��ð�ť�����е�id
         var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
        //��idת��Ϊ�������е�����,��ɾ��
        for(i =0; i<DeptTableId.rows.length;i++){
            if(DeptTableId.rows[i].id == rowid){
                DeptTableId.deleteRow(DeptTableId.rows[i].rowIndex);
            }
        }
    }


//�ű����ɵ�ɾ����  ť��ɾ������
    function deleteRowDept2(obj){
          //��ð�ť�����е�id
        var rowid = "000";
        rowid = obj.id;
        rowid = rowid.substring(1,rowid.length);
        //��idת��Ϊ�������е�����,��ɾ��
        for(i =0; i<DeptTableId.rows.length;i++){
            if(DeptTableId.rows[i].id == rowid){
                DeptTableId.deleteRow(DeptTableId.rows[i].rowIndex);
            }
        }
    }

function onSelectChange2() {
			if(this.id != null) {
				var rowId = this.id.substring(6,this.id.length);
			} else {
				rowId = currentId;
			}
			

			var slt1Obj=document.getElementById("deptid"+rowId);
       	 	var slt2Obj=document.getElementById("userid"+rowId);
			i=0;
			for(j= 0; j< infoArr.length; j++ )
			{
				if(slt1Obj.value == infoArr[j][0])
				{
					i++;
				}
			}
        	slt2Obj.options.length = i;
			k=0;
			for(j =0;j<infoArr.length;j++)
			{
				if(slt1Obj.value == infoArr[j][0])
				{
                  //UPuserid
					slt2Obj.options[k].text=infoArr[j][2];
					slt2Obj.options[k].value=infoArr[j][1];
                    var aObj = document.getElementById("UPuserid");
                    if(aObj != null){
                    	if(aObj.value == infoArr[j][1])
                        	slt2Obj.options[k].selected="true";
                    }
					k++;

				}
      		}
			return true;
}

function onTypeChange() {
	var objValue = this.value();
	if(objValue == "") {

	}
}
</script>
</head>
<body >
	<logic:present name="userinfo">
    	<logic:iterate id="uId" name="userinfo">
        	<script type="" language="javascript">
            	//initArray('<bean:write name="uId" property="deptid"/>','<bean:write name="uId" property="userid"/>','<bean:write name="uId" property="username"/>')
            </script>            
        </logic:iterate>
    </logic:present>
    <logic:present name="deptinfo">
    	<logic:iterate id="deptId" name="deptinfo">
             <script type="" language="javascript">

            	//initArrayDept('<bean:write name="deptId" property="deptid"/>','<bean:write name="deptId" property="deptname"/>')
             </script>
        </logic:iterate>
    </logic:present>
    
	<logic:equal value="2" name="type" scope="session">
    	<br>
		<template:titile value="ָ������"/>
		<html:form action="/SendTaskAction.do?method=sendTask" styleId="addApplyForm" onsubmit="return validate(this);" enctype="multipart/form-data" >
		<html:hidden property="processterm"  value=""/>
        	<table id="tableID" width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
		        <tr class=trcolor>    
		            <td class="tdr" width="10%" >�������</td>
		            <td class="tdl" width="25%">		                	
	                	<apptag:quickLoadList cssClass="inputtext" name="sendtype"
							listName="dispatch_task" type="select" />
						<input type="hidden" name="acceptdeptid" value=""/>
		                <input type="hidden" name="acceptuserid" value=""/>
		                <input type="hidden" name="mobileId" value=""/>
		            </td>
	                <td class="tdr" width="10%"><!-- ��ά��ˮ�� --></td>		                
	                <td class="tdl"></td>                        
		        </tr>
		        <tr class=trcolor >		        		
	                <td class="tdr" width="10%">�ɵ�����</td>		                
	                <td class="tdl" width="25%"><bean:write name="LOGIN_USER" property="deptName"/></td>
	                <td class="tdr" width="10%">
						�ɵ���
					</td>
					<td class="tdl" width="25%">
						<bean:write name="LOGIN_USER" property="userName" />
					</td>
		        </tr>  
		        <apptag:processorselect inputName="dept_id,user,mobile_id,user_id" spanId="userSpan" />

	         	<tr class=trcolor >
	            	<td class="tdr">��������</td>
	                <td class="tdl"  colspan="3" ><html:text property="sendtopic" style="width:82%" styleClass="inputtext" maxlength="100"/></td>
	        	</tr>
	
	             <tr class=trcolor >
	            	<td class="tdr" >����˵��</td>
	                <td class="tdl" colspan="3"><html:textarea property="sendtext" alt=" ����˵���510�����֣�" style="width:82%"  rows="5" styleClass="textarea"></html:textarea></td>
	        	</tr>
	
			<tr class=trcolor height="40">
				<td class="tdr" width="10%">
					����Ҫ��
				</td>
				<td class="tdl" colspan="3">
					<html:textarea property="replyRequest" alt="����Ҫ���510�����֣�"
							style="width:82%" rows="5" styleClass="textarea"></html:textarea>
				</td>
			</tr>
			<tr class=trcolor height="40">
				<td class="tdr" width="10%">
					����ʱ��
				</td>
				<td class="tdl" colspan="3">
						<input type="text" id="protimeid" name="begintime" value=""
							readonly="readonly" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})"
							style="width: 100" />
						<html:text property="time" styleClass="Wdate"
							onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" style="width:60"
							maxlength="45" readonly="true" />
				</td>
			</tr>

	            <tr class=trcolor >
	            	<td class="tdr" >���񸽼�</td>
	                <td class="tdl"  colspan="3">
	                	 <!-- <table id="uploadID" width=520 border="0" align="left" cellpadding="3" cellspacing="0" >
					          <tbody></tbody>
	                     </table> -->
	                     <apptag:upload cssClass="" entityId="" entityType="LP_SENDTASK" state="add"/>
	                </td>
	        	</tr>
	        </table>
	        <table align="center">
	            <tr height="50">
	            	<td colspan="6" class="tdc">
	                	 	  <!-- <td>
	                	 	  	<input type="hidden" id="maxSeq" value="0">
	                	 	  	<input type="checkbox" onclick="checkOrCancel()" id="sel">ȫ ѡ&nbsp;&nbsp;
	                	 	  </td>
					          <td>
					            <html:button property="action" styleClass="button" onclick="addRowMod()">��Ӹ���</html:button>
					          </td>
					          <td>
					          	<html:button property="action" styleClass="button" onclick="delRow()">ɾ��ѡ��</html:button>
					          </td> -->
					          <td>
					            <html:button styleClass="button" onclick="checkSub()" property="action">�ύ</html:button>
					          </td>
					          <td>
					            <html:reset styleClass="button">����</html:reset>
					          </td>
	                </td>
	            </tr>
	       </table>
	         <script type="" language="javascript">
					bodyOnload();
             </script>
		</html:form>
</logic:equal>

    <!--��ʾ�����ɵ�ҳ��-->
    <logic:equal  name="type" scope="session" value="1">
    	<!--%@include file="/common/listhander.jsp"%-->
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<br>
		<template:titile value="�ɵ���Ϣһ����"/>		
        <display:table name="sessionScope.sendlist" requestURI="${ctx}/SendTaskAction.do?method=doQuery" id="currentRowObject" pagesize="20" style="width:100%">
        		<%
				    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				    String serialnumber = "";
				    String id = "";
				    String sendtopic = "";
				    String titleTopic = "";
				    String subid = "";
				    //String result = "";
                    String workstate = "";
                    String processterm = "";
				    if(object != null) {	
				    	serialnumber = (String) object.get("serialnumber");
				    	id = (String) object.get("id");
				    	
				    	titleTopic = (String)object.get("sendtopic");
				    	if(titleTopic != null && titleTopic.length() > 30) {
				    		sendtopic = titleTopic.substring(0,30) + "...";
				    	} else {
				    		sendtopic = titleTopic;
				    	}
				    	
				    	subid = (String)object.get("subid");
				    	//result = (String) object.get("result");
				    	workstate = (String) object.get("workstate");
				    	processterm = String.valueOf(object.get("processterm"));
				    }
				    
				    
				  %>
        	<display:column  media="html" title="��ˮ��" sortable="true" style="width:90px"> 
        		<% 
        			if(serialnumber != null ) {
        		%>       		
        		<a href="javascript:toGetForm('<%=id%>','<%=subid%>')"><%=serialnumber%></a>
        		<% } %>
        	</display:column>
        	<display:column media="html" title="����"  sortable="true">
        		<a href="javascript:toGetForm('<%=id %>','<%=subid%>')" title="<%=titleTopic %>"><%=sendtopic%></a>
        	</display:column>        	 
            <display:column property="sendtype" title="����"  style="width:80px" maxLength="5" sortable="true"/>
            <display:column property="senddeptname" title="�ɵ���λ" style="width:80px" maxLength="5" sortable="true"/>           
            <display:column  media="html" title="��������" sortable="true" style="width:80px"> 
            	<%if(processterm.indexOf('-') != -1) {%>
            		<font color="red" ><%=processterm %></font>
            	<%} else {%>
            		<%=processterm %>
            	<%} %>
            </display:column>
            <display:column property="usernameacce" title="������" style="width:60px" maxLength="4" sortable="true"/>
            <apptag:checkpower thirdmould="110104" ishead="0">
            	<display:column media="html" title="����" style="width:40px">
            	<%if("��ǩ��".equals(workstate)) {%>
            		<a href="javascript:toUpForm('<%=id%>','<%=subid%>')">�޸�</a>
            	<%} else {%>
            	--
            	<%} %>
            	
                 </display:column>
            </apptag:checkpower>


		</display:table>
		<logic:notEmpty name="sendlist">
	   		 <html:link action="/SendTaskAction.do?method=exportSendTaskResult">����ΪExcel�ļ�</html:link>
	    </logic:notEmpty>
	</logic:equal>

     <!--��ʾһ���ɵ���ϸ��Ϣҳ��-->
    <logic:equal  name="type" scope="session" value="10">
      	<br>
		<template:titile value="�ɵ���ϸ��Ϣ"/>
		<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
		        	<tr class=trcolor height="40">
		            	<td class="tdr"  width="12%">��������ˮ��</td>
		                <td class="tdl" width="18%"><bean:write name="bean" property="serialnumber"/></td>
			             <td class="tdr"  width="10%">�������</td>
		                <td class="tdl" colspan="3">
                        	<bean:write name="bean" property="sendtype"/>
			             </td>
		        	</tr>
		        	<tr class=trcolor height="40">
                    	<td class="tdr"  width="10%">�ɵ�ʱ��</td>
		                <td class="tdl" width="18%"> <bean:write name="bean" property="sendtime"/></td>
		                <td class="tdr"  width="10%">�ɵ�����</td>
		                <td class="tdl" width="18%"><bean:write name="LOGIN_USER" property="deptName"/></td>
		                <td class="tdr"  width="10%">�� �� ��</td>
                        <td class="tdl" ><bean:write name="bean" property="username"/>
			                 </td>

		        	</tr>
					<logic:equal value="0" name="newOldFlg">
						<logic:iterate id="element" name="deptlist">
							<tr class=trcolor height="40">
			                	<td class="tdr"   width="10%">������</td>
			                	<td class="tdl" width="18%"><bean:write name="element" property="deptname"/>
				                </td>
			                	<td class="tdr" width="10%" >�� �� ��</td>
			                	<td class="tdl" colspan="3"><bean:write name="element" property="username"/>			                	
				               </td>
		        			</tr>
						</logic:iterate>
					</logic:equal>
					<logic:equal value="1" name="newOldFlg">
						<tr class=trcolor height="40">
			                	<td class="tdr"   width="10%">������</td>
			                	<td class="tdl" width="18%"><bean:write name="bean" property="acceptdeptname"/>
				                </td>
			                	<td class="tdr" width="10%" >�� �� ��</td>
			                	<td class="tdl" colspan="3"><bean:write name="bean" property="usernameacce"/>
				               </td>
		        			</tr>
					</logic:equal>
						

		            <tr class=trcolor height="40" >
		            	<td class="tdr"  width="10%">��������</td>
		                <td class="tdl"  colspan="5" ><bean:write name="bean" property="sendtopic"/></td>
		        	</tr>

		             <tr class=trcolor height="40">
		            	<td class="tdr"  width="10%">����˵��</td>
		                <td class="tdl"   colspan="5"><div><bean:write name="bean" property="sendtext"/></div></td>
		        	</tr>
				<tr class=trcolor height="40">
					<td class="tdr" width="10%">
						����Ҫ��
					</td>
					<td class="tdl" colspan="5">
						<bean:write property="replyRequest" name="bean" />
					</td>
				</tr>
				<tr class=trcolor height="40">
					<td class="tdr" width="10%">
						����ʱ��
					</td>
					<td class="tdl" colspan="5">
						<bean:write property="processterm" name="bean" />
					</td>
				</tr>


		            <tr class=trcolor height="40" >
		            	<td class="tdr"  width="10%">���񸽼�</td>
		                <td class="tdl"  colspan="5">
						    <apptag:upload cssClass="" entityId="${sessionScope.bean.id}" entityType="LP_SENDTASK" state="look"/>
		                </td>
		        	</tr>
                    <tr class=trcolor height="40">
                    	<td class="tdr"  width="10%">ǩ�ս��</td>
                        <td class="tdl" width="18%"><bean:write name="bean" property="result"/></td>
                       	<td class="tdr"  width="10%">����״̬</td>
                        <td colspan="3" class="tdl"><bean:write name="bean" property="workstate"/></td>
                    </tr>
                    <logic:notEmpty name="endorsebean">
                    	<tr class=trcolor height="40" >
			                    	<td class="tdr"   colspan="6"><hr/></td>
                        </tr>
                    	 <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%">ǩ�ձ�ע</td>
	                        <td class="tdl"  colspan="5">
	                        	<bean:write name="endorsebean" property="remark"/>
	                        </td>
	                     </tr>
                         <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%">ǩ�ո���</td>
	                        <td class="tdl"  width="520" colspan="5">
						      <apptag:upload cssClass="" entityId="${sessionScope.endorsebean.id}" entityType="LP_SENDTASKENDORSE" state="look"/>
                            </td>
	                     </tr>
                    </logic:notEmpty>
		        </table>
                <p align="center"><input type="button" class="button" onclick="javascript:history.go(-1)" value="����" ></p>
    </logic:equal>


    <!--������ѯ��ʾ-->
     <logic:equal value="3" name="type"scope="session" >
	      	<br />
	        <template:titile value="���������������ɵ�"/>
	        <html:form action="/SendTaskAction?method=doQuery"   styleId="queryForm2" >
            	<template:formTable namewidth="100" contentwidth="250">                   
				    <template:formTr name="�� �� ��">
					  	 <html:text property="sendusername"  styleClass="inputtext" style="width:180" />
				    </template:formTr>
				      <template:formTr name="�ɵ�����">
				      	<html:text property="sendtopic"  styleClass="inputtext" style="width:180" />
				      </template:formTr>
				      <template:formTr name="�������">
				      	<apptag:quickLoadList cssClass="inputtext" name="sendtype"
							listName="dispatch_task" type="select" />
				    </template:formTr>					
				    <template:formTr name="����ʱ��">
                      	<select name="processterm"  style="width:180" class="inputtext">
                        			<option value="">����</option>
		                       		<option value="����">��ʱ</option>
		                       		<option value="δ����">δ��ʱ</option>
                      	</select>
				    </template:formTr>
                    <template:formTr name="����״̬">
                      	<select name="workstate"  style="width:180" class="inputtext">
                        			<option value="">����</option>
                                    <option value="0������">������</option>
		                       		<option value="1��ǩ��">��ǩ��</option>
                                    <option value="1������">������</option>
		                       		<option value="3������">������</option>
		                       		<option value="6����֤">����֤</option>
                                    <option value="9�Ѵ浵">�Ѵ浵</option>
                      	</select>
				    </template:formTr>
                    <template:formTr name="�ɵ���ʼʱ��">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" style="width:150" />
	                </template:formTr>
	                <template:formTr name="�ɵ�����ʱ��">
	                	<input  id="end" type="text"  name="endtime" readonly="readonly" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" style="width:150" />
	                </template:formTr>
	                <template:formSubmit>
						      	<td>
		                          <html:submit property="action" styleClass="button" >����</html:submit>
						      	</td>
						      	<td>
						       	 	<html:reset property="action" styleClass="button" >����</html:reset>
						      	</td>
						    </template:formSubmit>
	        	</template:formTable>

	    	</html:form>
	    </logic:equal>


        <!--�޸���ʾ-->
        <logic:equal value="4" name="type" scope="session">
    		<br>
		 	<template:titile value="�޸��ɵ�"/>
			<html:form action="/SendTaskAction.do?method=upSendtask" styleId="addApplyForm" enctype="multipart/form-data">
		    	<html:hidden property="id"  name="bean"/>
		    	<html:hidden property="subtaskid"  name="bean"/>
                <html:hidden property="result" name="bean"/>
                <html:hidden property="sendacce" name="bean"/>
		    	<html:hidden property="processterm"  value=""/>

                 <table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
		        	<tr class=trcolor >
		            	<td class="tdr"  width="10%">�������</td>
		                <td class="tdl" width="30%">		                	
		                	<apptag:quickLoadList cssClass="inputtext" name="sendtype"
							listName="dispatch_task" type="select" />
		                </td>
		                <td class="tdr"  width="10%">��ά��ˮ��</td>
		                <td class="tdl" colspan="3"><bean:write name="bean" property="serialnumber"/>
		                <input type="hidden" name="acceptdeptid" value="<bean:write name="bean" property="acceptdeptid"/>"/>
		                <input type="hidden" name="acceptuserid" value="<bean:write name="bean" property="acceptuserid"/>"/>
		                <input type="hidden" name="mobileId" value=""/>
		                </td>
		               
		        	</tr>
		        	
		        	<tr class=trcolor>
		        		<td class="tdr" width="10%">�ɵ�����</td>
		                <td class="tdl" width="30%"><bean:write name="LOGIN_USER" property="deptName"/></td>
					<td class="tdr" width="10%">
						�ɵ���
					</td>
					<td class="tdl" width="30%">
						<bean:write name="LOGIN_USER" property="userName" />
					</td>
					<td class="tdr" width="10%">
						�ɵ�ʱ��
					</td>
					<td class="tdl" width="30%">
						<bean:write property="sendtime" name="bean" />
					</td>
		        	</tr>
							
					<tr class=trcolor>
		        		<td class="tdr" width="10%">������</td>
		                <td class="tdl" width="30%"><bean:write name="bean" property="acceptdeptname"/></td>
					<td class="tdr" width="10%">
						������
					</td>
					<td class="tdl" width="30%" colspan="3">
						<bean:write name="bean" property="acceptusername" />
					</td>
		        	</tr>
		            <tr class=trcolor >
		            	<td class="tdr"  width="10%">��������</td>
		                <td class="tdl" colspan="5" ><html:text property="sendtopic"  name="bean" style="width:82%" styleClass="inputtext" maxlength="100"/></td>
		        	</tr>

		             <tr class=trcolor >
		            	<td class="tdr"  width="10%">����˵��</td>
		                <td class="tdl"  colspan="5"><html:textarea property="sendtext" alt=" ����˵���510�����֣�" name="bean" style="width:82%"  rows="5" styleClass="textarea"></html:textarea></td>
		        	</tr>

			<tr class=trcolor height="40">
				<td class="tdr" width="10%">
					����Ҫ��
				</td>
				<td class="tdl" colspan="5">
					<html:textarea property="replyRequest" alt="����Ҫ���510�����֣�"
							name="bean" style="width:82%" rows="5" styleClass="textarea"></html:textarea>
				</td>
			</tr>
			<tr class=trcolor height="40">
				<td class="tdr" width="10%">
					����ʱ��
				</td>
				<td class="tdl" colspan="5">
						<bean:define id="temp" name="bean" property="processterm"
							type="java.lang.String" />

						<input type="text" id="protimeid" name="begintime"
							value="<%= temp.substring(0,10)%>" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})"
							style="width: 100" />


						<html:text value="<%= temp.substring(11,16)%>" property="time"
							styleClass="Wdate" style="width:60" maxlength="45"
							onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" readonly="true" />
				</td>
			</tr>

		            <tr class=trcolor >
		            	<td class="tdr"  width="10%">���񸽼�</td>
		                <td class="tdl"  colspan="5">
							 <apptag:upload cssClass="" entityId="${sessionScope.bean.id }" entityType="LP_SENDTASK" state="edit"/>
		                </td>
		        	</tr>
		        	<!-- 
                     <tr class=trcolor >
		            	<td class="tdr"  width="10%">�����ɵ�</td>
		                <td class="tdl"  colspan="5"><input type="checkbox"  name="flag"  value="1" />  ������,�������ɵ�.</td>
		        	 </tr>
		        	 -->
		        </table>
		        <table align="center">
			            <tr>
			            	<td colspan="6" class="tdc">
							         <td>
			                	 	  	<input type="hidden" id="maxSeq" value="0">
			                	 	  </td>
							          <td>
							          </td>
							          <td>
							            <html:button styleClass="button" onclick="onSubmitClick()" property="action">�ύ</html:button>
							          </td>
							          <td>
							            <html:reset styleClass="button">����</html:reset>
							          </td>
							          <td>
							            <input type="button" class="button" onclick="javascript:history.go(-1)" value="����" >
							          </td>
			                </td>
			            </tr>
		        </table>
			</html:form>
    </logic:equal>




</body>
</html>
