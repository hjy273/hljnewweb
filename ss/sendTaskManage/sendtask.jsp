<%@include file="/common/header.jsp"%>

<%@ page import="com.cabletech.sendtask.beans.*;" %>

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
    function onSelectChange(objId ) //
	{
		var slt1Obj=document.getElementById(objId);
        var slt2Obj=document.getElementById("userid");
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
        alert("����Ҫ������̫�࣡���ܳ���510������");
        return;
      }
      if(SendTaskBean.sendtopic.value==null||SendTaskBean.sendtopic.value==""||SendTaskBean.sendtopic.value.trim()==""){
        alert("�������������⣡");
        return;
      }
      if((SendTaskBean.sendtext.value==null||SendTaskBean.sendtext.value==""||SendTaskBean.sendtext.value.trim()=="")&&!haveArr()){
        alert("����������Ҫ�������Ӹ�����");
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
        alert("����Ҫ������̫�࣡���ܳ���510������");
        return;
      }
      if(SendTaskBean.sendtopic.value==null||SendTaskBean.sendtopic.value==""||SendTaskBean.sendtopic.value.trim()==""){
        alert("�������������⣡");
        return;
      }
      if((SendTaskBean.sendtext.value==null||SendTaskBean.sendtext.value==""||SendTaskBean.sendtext.value.trim()=="")&&!haveArr()){
        alert("����������Ҫ�������Ӹ�����");
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
            	initArray('<bean:write name="uId" property="deptid"/>','<bean:write name="uId" property="userid"/>','<bean:write name="uId" property="username"/>')
            </script>            
        </logic:iterate>
    </logic:present>
    <logic:present name="deptinfo">
    	<logic:iterate id="deptId" name="deptinfo">
             <script type="" language="javascript">

            	initArrayDept('<bean:write name="deptId" property="deptid"/>','<bean:write name="deptId" property="deptname"/>')
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
	                	<html:select property="sendtype" style="width:120" styleClass="inputtext">
	                		<html:option value="��������">��������</html:option>
	                		<html:option value="Ͷ�ߴ���">Ͷ�ߴ���</html:option>
	                		<html:option value="����ά��">����ά��</html:option>
	                		<html:option value="�����ύ">�����ύ</html:option>
	                		<html:option value="��������">��������</html:option>
	                		<html:option value="�ر���">�ر���</html:option>
	                	</html:select>
		            </td>
	                <td class="tdr" width="10%">��ά��ˮ��</td>		                
	                <td class="tdl"></td>                        
		        </tr>
		        <tr class=trcolor >		        		
	                <td class="tdr" width="10%">�ɵ�����</td>		                
	                <td class="tdl" width="25%"><bean:write name="LOGIN_USER_DEPT_NAME"/></td>
	            	<td class="tdr" width="10%" >����ʱ��</td>
	                <td class="tdl"  >
						<input  type="text"  id="protimeid" name="begintime" value="" readonly="readonly" class="inputtext" style="width:100" />
                    	<INPUT TYPE='BUTTON' VALUE='����' maxlength="3" ID='btn'  onclick="GetSelectDateTHIS('protimeid')" STYLE="font:'normal small-caps 6pt serif';" >
						<html:text property="time" styleClass="inputtext" style="width:60" maxlength="45" readonly="true"/>
      					<input type='button' value="ʱ��" id='btn' onclick="getTimeWin('time')" style="font:'normal small-caps 6pt serif';">
                    </td>
		        </tr>  
		        <tr class=trcolor >
		        	<td colspan="4" width="100%"  cellpadding="0" cellspacing="0">
		        		<table id="DeptTableId" width="100%" cellpadding="3" cellspacing="0">
		        			<tr class=trcolor>
		        				<td class="tdr" width="10%">������</td>		                
					            <td class="tdl"  width="25%">
						        	<logic:present name="deptinfo"  scope="session" >
						            	<select name="acceptdeptid"   class="inputtext"  style="width:120" id="deptid" onchange="onSelectChange(id)">
					                    	<logic:iterate id="deptId"  name="deptinfo">
					                        	<option value="<bean:write  name="deptId" property="deptid"/>"><bean:write name="deptId"  property="deptname"/></option>
					                		</logic:iterate>
										</select>
						             </logic:present>
						        </td>
						        <td class="tdr" width="10%" >�� �� ��</td>
					                <td class="tdl"  >
						            <logic:present name="userinfo"  scope="session" >
						            	<select name="acceptuserid" id="userid" class="inputtext" style="width:100">
					                		<logic:iterate id="userId"  name="userinfo">
					                            <option value="<bean:write  name="userId" property="userid"/>"><bean:write name="userId"  property="username"/></option>
					                        </logic:iterate>
										</select> 
						             </logic:present>
						         </td>   
						         <td><input type="button" class="button" value="���" onclick="addDept()"></td> 
		        			</tr>		        				
		        		</table>
		        	</td>
		        </tr>

	         	<tr class=trcolor >
	            	<td class="tdr">��������</td>
	                <td class="tdl"  colspan="3" ><html:text property="sendtopic" style="width:82%" styleClass="inputtext" maxlength="100"/></td>
	        	</tr>
	
	             <tr class=trcolor >
	            	<td class="tdr" >����Ҫ��</td>
	                <td class="tdl" colspan="3"><html:textarea property="sendtext" alt=" ����Ҫ���510�����֣�" style="width:82%"  rows="5" styleClass="textarea"></html:textarea></td>
	        	</tr>
	
	            <tr class=trcolor >
	            	<td class="tdr" >���񸽼�</td>
	                <td class="tdl"  colspan="3">
	                	 <table id="uploadID" width=520 border="0" align="left" cellpadding="3" cellspacing="0" >
					          <tbody></tbody>
	                     </table>
	                </td>
	        	</tr>
	        </table>
	        <table align="center">
	            <tr>
	            	<td colspan="6" class="tdc">
	                	 <template:formSubmit>
	                	 	  <td>
	                	 	  	<input type="hidden" id="maxSeq" value="0">
	                	 	  	<input type="checkbox" onclick="checkOrCancel()" id="sel">ȫѡ&nbsp;
	                	 	  </td>
					          <td>
					            <html:button property="action" styleClass="button" onclick="addRowMod()">��Ӹ���</html:button>
					          </td>
					          <td>
					          	<html:button property="action" styleClass="button" onclick="delRow()">ɾ��ѡ��</html:button>
					          </td>
					          <td>
					            <html:button styleClass="button" onclick="checkSub()" property="action">�ύ</html:button>
					          </td>
					          <td>
					            <html:reset styleClass="button">����</html:reset>
					          </td>
				        </template:formSubmit>
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
				    	if(titleTopic != null && titleTopic.length() > 18) {
				    		sendtopic = titleTopic.substring(0,18) + "...";
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
    <html:link action="/SendTaskAction.do?method=exportSendTaskResult">����ΪExcel�ļ�</html:link>
	</logic:equal>

     <!--��ʾһ���ɵ���ϸ��Ϣҳ��-->
    <logic:equal  name="type" scope="session" value="10">
      	<br>
		<template:titile value="�ɵ���ϸ��Ϣ"/>
		<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
		        	<tr class=trcolor height="40">
		            	<td class="tdr"  width="12%">��������ˮ��</td>
		                <td class="tdl" width="18%"><bean:write name="bean" property="serialnumber"/></td>
		                <td class="tdr"  width="10%">����ʱ��</td>
		                <td class="tdl" width="18%">
                        	<bean:write name="bean" property="processterm"/>
			             </td>
			             <td class="tdr"  width="10%">�������</td>
		                <td class="tdl">
                        	<bean:write name="bean" property="sendtype"/>
			             </td>
		        	</tr>
		        	<tr class=trcolor height="40">
                    	<td class="tdr"  width="10%">�ɵ�ʱ��</td>
		                <td class="tdl" width="18%"> <bean:write name="bean" property="sendtime"/></td>
		                <td class="tdr"  width="10%">�ɵ�����</td>
		                <td class="tdl" width="18%"><bean:write name="LOGIN_USER_DEPT_NAME"/></td>
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
		            	<td class="tdr"  width="10%">����Ҫ��</td>
		                <td class="tdl"   colspan="5"><div><bean:write name="bean" property="sendtext"/></div></td>
		        	</tr>

		            <tr class=trcolor height="40" >
		            	<td class="tdr"  width="10%">���񸽼�</td>
		                <td class="tdl"  colspan="5">
						    <%
						      String sendacce = "";
						      SendTaskBean bean = (SendTaskBean) request.getSession().getAttribute("bean");
						      String result = bean.getResult();
						      String state = bean.getWorkstate();				   
						      sendacce = bean.getSendacce();
						      if (sendacce == null) {
						        sendacce = "";
						      }
						      if(result == null) {
						      	result = "";
						      }
						      if(state == null) {
						      	state = "";
						      }
						    %>
						      <apptag:listAttachmentLink fileIdList="<%=sendacce%>"/>
		                </td>
		        	</tr>
                    <tr class=trcolor height="40">
                    	<td class="tdr"  width="10%">ǩ�ս��</td>
                        <td class="tdl" width="18%"><%=result %></td>
                       	<td class="tdr"  width="10%">����״̬</td>
                        <td colspan="3" class="tdl"><%=state %></td>
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
                            	 <%
							      String acce = "";
							      SendTaskBean endorsebean = (SendTaskBean) request.getSession().getAttribute("endorsebean");
							      acce = endorsebean.getAcce();
							      if (acce == null) {
							        acce = "";
							      }
							    %>
						      <apptag:listAttachmentLink fileIdList="<%=acce%>"/>
                            </td>
	                     </tr>
                    </logic:notEmpty>
		        </table>
                <p align="center"><input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" ></p>
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
				      <template:formTr name="���������">
				      	<html:select property="sendtype" style="width:180" styleClass="inputtext">
				      			<html:option value="">����</html:option>
	                			<html:option value="��������">��������</html:option>
	                			<html:option value="Ͷ�ߴ���">Ͷ�ߴ���</html:option>
	                			<html:option value="����ά��">����ά��</html:option>
	                			<html:option value="�����ύ">�����ύ</html:option>
	                			<html:option value="��������">��������</html:option>
	                			<html:option value="�ر���">�ر���</html:option>
	               		</html:select>
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
		                       		<option value="3���ظ�">���ظ�</option>
		                       		<option value="6����֤">����֤</option>
                                    <option value="9�Ѵ浵">�Ѵ浵</option>
                      	</select>
				    </template:formTr>
                    <template:formTr name="�ɵ���ʼʱ��">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='����'  readonly="readonly" ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formTr name="�ɵ�����ʱ��">
	                	<input  id="end" type="text"  name="endtime" readonly="readonly" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='����' ID='btn'  onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';" >
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
		                	<html:select property="sendtype" style="width:120" styleClass="inputtext">
	                			<html:option value="��������">��������</html:option>
	                			<html:option value="Ͷ�ߴ���">Ͷ�ߴ���</html:option>
	                			<html:option value="����ά��">����ά��</html:option>
	                			<html:option value="�����ύ">�����ύ</html:option>
	                			<html:option value="��������">��������</html:option>
	                			<html:option value="�ر���">�ر���</html:option>
	                		</html:select>
		                </td>
		                <td class="tdr"  width="10%">��ά��ˮ��</td>
		                <td class="tdl" colspan="2">
		                </td>
		               
		        	</tr>
		        	
		        	<tr class=trcolor>
		        		<td class="tdr" width="10%">�ɵ�����</td>
		                <td class="tdl" width="30%"><bean:write name="LOGIN_USER_DEPT_NAME"/></td>
		        		<td class="tdr" width="10%">����ʱ��</td>
		                <td class="tdl"  colspan="2">
                        	<bean:define id="temp" name="bean" property="processterm"  type="java.lang.String"/>
                        	
						      <input  type="text"  id="protimeid" name="begintime" value="<%= temp.substring(0,10)%>" readonly="readonly" class="inputtext" style="width:100" />
                    		  <INPUT TYPE='BUTTON' VALUE='����' maxlength="3" ID='btn'  onclick="GetSelectDateTHIS('protimeid')" STYLE="font:'normal small-caps 6pt serif';" >

                        		<html:text  value="<%= temp.substring(11,16)%>" property="time" styleClass="inputtext" style="width:60" maxlength="45" readonly="true"/>
      							<input type='button' value="ʱ��" id='btn' onclick="getTimeWin('time')" style="font:'normal small-caps 6pt serif';">
                        </td>		        	
		        	</tr>
							
					<tr class=trcolor >
						<td colspan="5" cellpadding="0" cellspacing="0" width="100%">
							<table	id="DeptTableId" width="100%" cellpadding="3" cellspacing="0">
							<logic:equal value="0" name="newOldFlg">
							<logic:iterate id="element" name="deptList" indexId="ind">
								<tr class=trcolor id="<bean:write name='ind'/>">
									<td class="tdr"   width="10%">������</td>
					                <td class="tdl" width="30%">
						                 <logic:present name="deptinfo"  scope="session" >
			                               <bean:define id="tem1" name="element" property="deptid" type="java.lang.String"/>
						                   <select name="acceptdeptid"  class="inputtext"  style="width:120" id="deptid<bean:write name='ind'/>" onchange="onSelectChange2">
					                       		<logic:iterate id="deptId"  name="deptinfo">					                       		
			                                    	<logic:equal value="<%=tem1%>" name="deptId" property="deptid">
			                                        	<option value="<bean:write  name="deptId" property="deptid"/>" selected="selected"><bean:write name="deptId"  property="deptname"/></option>
			                                    	</logic:equal>
			                                        <logic:notEqual value="<%=tem1%>" name="deptId" property="deptid">
			                                        	<option value="<bean:write  name="deptId" property="deptid"/>" ><bean:write name="deptId"  property="deptname"/></option>
			                                        </logic:notEqual>
					                            </logic:iterate>
											</select>
						                 </logic:present></td>
					                <td class="tdr" width="10%" >�� �� ��</td>
					                <td class="tdl" >
						                 <logic:present name="userinfo"  scope="session" >
			                               <bean:define id="tem2" name="element" property="useid" type="java.lang.String"/>
						                   <select name="acceptuserid" class="inputtext" style="width:100" id="userid<bean:write name='ind'/>" >
					                       		<logic:iterate id="userId"  name="userinfo">
			                                    	<logic:equal value="<%=tem2%>" name="userId"  property="userid">
			                                        	<option selected="selected" value="<bean:write  name="userId" property="userid"/>"><bean:write name="userId"  property="username"/></option>
			                                    	</logic:equal>
			                                        <logic:notEqual value="<%=tem2%>" name="userId"  property="userid">
			                                        	<option value="<bean:write  name="userId" property="userid"/>"><bean:write name="userId"  property="username"/></option>
			                                    	</logic:notEqual>			
					                            </logic:iterate>
											</select>
						                 </logic:present>
						             </td>
						             <logic:equal value="0" name="ind">
						             	<td><input type="button" class="button" value="���" onclick="addDept()"></td>
						             </logic:equal>
						             <logic:notEqual value="0" name="ind">
						             	<td><input type="button" class="button" value="ɾ��" id="b<bean:write name='ind'/>" onclick="deleteRowDept2(this)"></td>
						             </logic:notEqual>						             
								</tr>
								</logic:iterate>
								</logic:equal>
								<logic:equal value="1" name="newOldFlg">
										<tr class=trcolor>
										<td class="tdr"   width="10%">������</td>
						                <td class="tdl" width="30%">
							                 <logic:present name="deptinfo"  scope="session" >
				                               <bean:define id="tem1" name="bean" property="acceptdeptname" type="java.lang.String"/>
							                   <select name="acceptdeptid"  class="inputtext"  style="width:120" id="deptid" onchange="onSelectChange2">
						                       		<logic:iterate id="element"  name="deptinfo">
						                       			<logic:notEqual value="<%=tem1%>" name="element"  property="deptname">
				                                        	<option value='<bean:write  name="element" property="deptid"/>'><bean:write name="element"  property="deptname"/></option>
				                                    	</logic:notEqual>
				                                    	<logic:equal value="<%=tem1%>" name="element"  property="deptname">
				                                        	<option selected="selected" value='<bean:write  name="element" property="deptid"/>'><bean:write name="element"  property="deptname"/></option>
				                                    	</logic:equal>			
						                            </logic:iterate>

												</select>
							                 </logic:present></td>
						                <td class="tdr" width="10%" >�� �� ��</td>
						                <td class="tdl" >
							                 <logic:present name="userinfo"  scope="session" >
							               
				                               <bean:define id="tem2" name="bean" property="acceptuserid" type="java.lang.String"/>
							                   <select name="acceptuserid" class="inputtext" style="width:100" id="userid" >
						                       		<logic:iterate id="userId"  name="userinfo">
				                                    	<logic:equal value="<%=tem2%>" name="userId"  property="userid">
				                                        	<option selected="selected" value='<bean:write  name="userId" property="userid"/>'><bean:write name="userId"  property="username"/></option>
				                                    	</logic:equal>
				                                        <logic:notEqual value="<%=tem2%>" name="userId"  property="userid">
				                                        	<option value='<bean:write  name="userId" property="userid"/>'><bean:write name="userId"  property="username"/></option>
				                                    	</logic:notEqual>			
						                            </logic:iterate>
												</select>
							                 </logic:present>
							             </td>
							             <!-- <input type="button" class="button" value="���" onclick="addDept()"></td> --><td>
							             
							            </tr>
							    </logic:equal>
							</table>
						</td>
       				</tr>
		
		            <tr class=trcolor >
		            	<td class="tdr"  width="10%">��������</td>
		                <td class="tdl" colspan="4" ><html:text property="sendtopic"  name="bean" style="width:82%" styleClass="inputtext" maxlength="100"/></td>
		        	</tr>

		             <tr class=trcolor >
		            	<td class="tdr"  width="10%">����Ҫ��</td>
		                <td class="tdl"  colspan="4"><html:textarea property="sendtext" alt=" ����Ҫ���510�����֣�" name="bean" style="width:82%"  rows="5" styleClass="textarea"></html:textarea></td>
		        	</tr>

		            <tr class=trcolor >
		            	<td class="tdr"  width="10%">���񸽼�</td>
		                <td class="tdl"  colspan="4">
	                        	<%
							    	String sendacce1="";
							        SendTaskBean bean1 = (SendTaskBean )request.getSession().getAttribute("bean");
							        sendacce1 = bean1.getSendacce();
							        if(sendacce1==null){
							        	sendacce1="";
							        }
							 	%>
							   <apptag:listAttachmentLink fileIdList="<%=sendacce1%>" showdele="true"/>
		                	 <table id="uploadID" width=400 border="0" align="left" cellpadding="3" cellspacing="0" >
						          <tr class=trcolor>
						            	<td></td>
						          </tr>
		                     </table>
		                </td>
		        	</tr>
                     <tr class=trcolor >
		            	<td class="tdr"  width="10%">�����ɵ�</td>
		                <td class="tdl"  colspan="4"><input type="checkbox"  name="flag"  value="1" />  ������,�������ɵ�.</td>
		        	 </tr>
                     <logic:notEmpty name="endorsebean" scope="session">
                     		<tr class=trcolor >
			                    	<td class="tdr"   colspan="5"><hr/></td>
			                 </tr>
                     		  <tr class=trcolor>
			                    	<td class="tdr"  width="10%">ǩ�ս��</td>
			                        <td class="td1" width="30%">
			                        	<bean:write name="endorsebean" property="result"/>
			                        	<html:hidden property="result" name="bean" style="width:100" styleClass="inputtext"/>
			                        </td>
			                        <td class="tdr"  width="10%">����״̬</td>
			                        <td class="td1" colspan="2">
			                        	<bean:write name="bean" property="workstate"/>
			                        	<html:hidden property="workstate" name="bean" style="width:100;background:#F5F5F5" styleClass="inputtext"/>
			                        </td>
			                    </tr>
	                    	 <tr class=trcolor>
		                    	<td class="tdr"  width="10%">ǩ�ձ�ע</td>
		                        <td class="tdl"  colspan="4">
		                        	<bean:write name="endorsebean" property="remark"/>
		                        	<html:hidden property="remark"  name="endorsebean"  styleClass="textarea" value="<bean:write name="endorsebean" property="remark"/>" />
		                        </td>
		                     </tr>
	                         <tr class=trcolor>
		                    	<td class="tdr"  width="10%">ǩ�ո���</td>
		                        <td class="tdl"  colspan="4">
	                            	 <%
								      String acce = "";
								      SendTaskBean endorsebean = (SendTaskBean) request.getSession().getAttribute("endorsebean");
								      acce = endorsebean.getAcce();
								      if (acce == null) {
								        acce = "";
								      }
								    %>
							      <apptag:listAttachmentLink fileIdList="<%=acce%>"/>
	                            </td>
		                     </tr>
	                    </logic:notEmpty>
		        </table>
		        <table align="center">
			            <tr>
			            	<td colspan="6" class="tdc">
			                	 <template:formSubmit>
							         <td>
			                	 	  	<input type="hidden" id="maxSeq" value="0">
			                	 	  	<input type="checkbox" onclick="checkOrCancel()" id="sel">ȫѡ&nbsp;
			                	 	  </td>
							          <td>
							            <html:button property="action" styleClass="button" onclick="addRowMod()">��Ӹ���</html:button>
							          </td>
							          <td>
							          	<html:button property="action" styleClass="button" onclick="delRow()">ɾ��ѡ��</html:button>
							          </td>
							          <td>
							            <html:button styleClass="button" onclick="onSubmitClick()" property="action">�ύ</html:button>
							          </td>
							          <td>
							            <html:reset styleClass="button">����</html:reset>
							          </td>
							          <td>
							            <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >
							          </td>
						        </template:formSubmit>
			                </td>
			            </tr>
		        </table>
			</html:form>
    </logic:equal>




</body>
</html>
