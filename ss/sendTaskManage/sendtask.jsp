<%@include file="/common/header.jsp"%>

<%@ page import="com.cabletech.sendtask.beans.*;" %>

<html>
<head>
<title>
sendtask
</title>
<script type="" language="javascript">
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
	var rowArr = new Array();//一行的信息
	var infoArr = new Array();//所有的信息

	var rowDept = new Array();//一行的信息
	var infoDept = new Array();//所有的信息
	 //初始化数组
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

    //初始化数组
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
	
	//动态上传附件
	function addRowMod() {
			var maxSeq = parseInt(document.getElementById('maxSeq').value);
			//alert(maxSeq);
			maxSeq ++;//自加
			var tableBody = document.getElementsByTagName("tbody")[2];
			//var tableBody = document.getElementById("uploadID");
			var newTr = document.createElement("tr");//创建一个tr
			newTr.id = maxSeq;
			newTr.className = 'trStyle';
			
			var checkTd = document.createElement("td");//创建一个TD，+到TR中
			checkTd.innerHTML = "<input type='checkbox' name='ifCheck' value='" + maxSeq + "'>";
			checkTd.className = 'selTd';
			newTr.appendChild(checkTd);
			
			var fileTd = document.createElement("td");//上传文件的td，+到TR中
			fileTd.className = 'rigthTdStyle';
			var fileUpLoad = document.createElement("input");
			fileUpLoad.type = 'file';
			fileUpLoad.className = 'fileStyle';
			fileUpLoad.name = 'uploadFile[' + maxSeq + '].file';
			fileUpLoad.id = 'uploadFile[' + maxSeq + '].file';
			fileTd.appendChild(fileUpLoad);
			newTr.appendChild(fileTd);
			
			document.getElementById('maxSeq').value = maxSeq;//设置隐藏域的值
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
				if(confirm("确实要删除吗？")) {
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
	 //脚本生成的删除按  钮的删除动作
	function deleteRow(){
      	//获得按钮所在行的id
     	var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
         //由id转换为行索找行的索引,并删除
      	for(i =0; i<uploadID.rows.length;i++){
        	if(uploadID.rows[i].id == rowid){
            	uploadID.deleteRow(uploadID.rows[i].rowIndex);
                fileNum--;
            }
        }
    }
    //添加一个新行
    function addRow(){
    	var  onerow=uploadID.insertRow(-1);
        onerow.id = uploadID.rows.length-2;

        var   cell1=onerow.insertCell();
		var   cell2=onerow.insertCell();


		//创建一个输入框
        var t1 = document.createElement("input");
        t1.name = "uploadFile["+ fileNum + "].file";
		t1.type= "file";
		t1.id = "text" + onerow.id;
		t1.width="300";
        fileNum++;


        //创建删除按钮
        var b1 =document.createElement("button");
        b1.value = "删除附件";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");


        cell1.appendChild(t1);//文字
        cell2.appendChild(b1);
	}


 //显示一个派单详细信息
 function toGetForm(id,subid){
 	var url = "${ctx}/SendTaskAction.do?method=showOneSendTask&id=" + id +"&subid="+subid;
	//window.open(url);
    window.location.href=url;
 }
 function toUpForm(id,subid){
	//if(result == "拒签") {
	//	alert("该派单已经被拒签,不能修改!");
	//	return;
	//}
    //if((result =="待签收") && (workstate =="待重派" || workstate == "待签收")){
	// 	var url = "${ctx}/SendTaskAction.do?method=showOneToUp&id=" + id+"&subid="+subid;;
	//    window.location.href=url;
  	//}else
   // alert("该派单已经签收或者存档,不能修改!");

	var url = "${ctx}/SendTaskAction.do?method=showOneToUp&id=" + id+"&subid="+subid;;
	window.location.href=url;
 }

 function addGoBack(){
 	var url ="${ctx}/SendTaskAction.do?method=showAllSendTask";
    window.location.href=url;
 }



       //检验是否数字
	 function valiD(id){
      	var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        var obj = document.getElementById(id);
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("你填写的数字不合法,请重新输入");
            obj.focus();
            obj.value = "0.00";
            return false;
        }
    }

 //选择日期
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
        alert("请输入工作类别！");
        return;
      }
	  
      if(SendTaskBean.begintime.value == null || SendTaskBean.begintime.value == ""){
      	alert("请输入处理期限的日期！");
        return;
      }
      if(SendTaskBean.time.value == null || SendTaskBean.time.value == ""){
      	alert("请输入处理期限的时间！");
        return;
      }
		
	  var d = new Date();
	  var s = '';
	  s += d.getYear() + "/"; 
      if((d.getMonth() + 1) > 9) {
		s += (d.getMonth() + 1) + "/";            // 获取月份。
	  } else {
		s += "0" + (d.getMonth() + 1) + "/";            // 获取月份。
	  }
	  
   	  // 获取日。
	  if(d.getDate() > 9) {
			s += d.getDate();
	  } else {
			s = s + "0" + d.getDate()
	  }

	  if(s > SendTaskBean.begintime.value) {
		alert("处理期限不能小于当前时间!");
			return;
	  } 
// 时间的判断
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
				alert("处理期限不能小于当前时间!");
				return;
			 }
	   }

      if(valCharLength(SendTaskBean.sendtext.value)>1020){
        alert("任务要求字数太多！不能超过510个汉字");
        return;
      }
      if(SendTaskBean.sendtopic.value==null||SendTaskBean.sendtopic.value==""||SendTaskBean.sendtopic.value.trim()==""){
        alert("请输入任务主题！");
        return;
      }
      if((SendTaskBean.sendtext.value==null||SendTaskBean.sendtext.value==""||SendTaskBean.sendtext.value.trim()=="")&&!haveArr()){
        alert("请输入任务要求或者添加附件！");
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
			alert("请选择要上传的附件的路径，\n如果没有要上传的附件请删除!");
			pathText.focus();
			return false;
		}
	 }

    	SendTaskBean.processterm.value = SendTaskBean.begintime.value + " " + SendTaskBean.time.value;
        SendTaskBean.submit();
		
	}

    function onSubmitClick(){
      if(SendTaskBean.sendtype.value==null||SendTaskBean.sendtype.value==""||SendTaskBean.sendtype.value.trim()==""){
        alert("请输入工作类别！");
        return;
      }
	  
      if(SendTaskBean.begintime.value == null || SendTaskBean.begintime.value == ""){
      	alert("请输入处理期限的日期！");
        return;
      }
      if(SendTaskBean.time.value == null || SendTaskBean.time.value == ""){
      	alert("请输入处理期限的时间！");
        return;
      }
		
	
      if(valCharLength(SendTaskBean.sendtext.value)>1020){
        alert("任务要求字数太多！不能超过510个汉字");
        return;
      }
      if(SendTaskBean.sendtopic.value==null||SendTaskBean.sendtopic.value==""||SendTaskBean.sendtopic.value.trim()==""){
        alert("请输入任务主题！");
        return;
      }
      if((SendTaskBean.sendtext.value==null||SendTaskBean.sendtext.value==""||SendTaskBean.sendtext.value.trim()=="")&&!haveArr()){
        alert("请输入任务要求或者添加附件！");
        return;
      }
		var allCheck = document.getElementsByName('ifCheck');
		var length = allCheck.length;
		
		for(var i = 0; i < length; i++) {
			var index = i + 1;
			var pathText = document.getElementById('uploadFile[' + index + '].file');
			var path = pathText.value;
			if(path.length == 0) {
				alert("请选择要上传的附件的路径，\n如果没有要上传的附件请删除!");
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
	
	// 第一单元格
	cell1.innerText = "受理部门";
	cell1.className = "tdr"

	// 第三单元格
	cell3.innerText = "受 理 人";
	cell3.className = "tdr"

	// 第五单元格	
	cell5.className = "td1"
	//创建删除按钮
    var b1 =document.createElement("button");
    b1.value = "删除";
    b1.id = "b" + onerow.id;
	b1.className = "button";
    b1.onclick=deleteRowDept;
	cell5.appendChild(b1);

	// 第二单元格
	//创建一个select
    var s2 = document.createElement("select");
    s2.name = "acceptuserid";
    s2.id = "userid" + onerow.id;
    s2.className="inputtext";
    s2.style.width="100";
	cell4.className = "td1"
	cell4.appendChild(s2);

	// 第二单元格
	//创建一个select
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

//脚本生成的删除按  钮的删除动作
    function deleteRowDept(){
         //获得按钮所在行的id
         var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
        //由id转换为行索找行的索引,并删除
        for(i =0; i<DeptTableId.rows.length;i++){
            if(DeptTableId.rows[i].id == rowid){
                DeptTableId.deleteRow(DeptTableId.rows[i].rowIndex);
            }
        }
    }


//脚本生成的删除按  钮的删除动作
    function deleteRowDept2(obj){
          //获得按钮所在行的id
        var rowid = "000";
        rowid = obj.id;
        rowid = rowid.substring(1,rowid.length);
        //由id转换为行索找行的索引,并删除
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
		<template:titile value="指派任务"/>
		<html:form action="/SendTaskAction.do?method=sendTask" styleId="addApplyForm" onsubmit="return validate(this);" enctype="multipart/form-data" >
		<html:hidden property="processterm"  value=""/>
        	<table id="tableID" width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
		        <tr class=trcolor>    
		            <td class="tdr" width="10%" >工作类别</td>
		            <td class="tdl" width="25%">		                	
	                	<html:select property="sendtype" style="width:120" styleClass="inputtext">
	                		<html:option value="交付工作">交付工作</html:option>
	                		<html:option value="投诉处理">投诉处理</html:option>
	                		<html:option value="共建维护">共建维护</html:option>
	                		<html:option value="报表提交">报表提交</html:option>
	                		<html:option value="紧急任务">紧急任务</html:option>
	                		<html:option value="特别工作">特别工作</html:option>
	                	</html:select>
		            </td>
	                <td class="tdr" width="10%">网维流水号</td>		                
	                <td class="tdl"></td>                        
		        </tr>
		        <tr class=trcolor >		        		
	                <td class="tdr" width="10%">派单部门</td>		                
	                <td class="tdl" width="25%"><bean:write name="LOGIN_USER_DEPT_NAME"/></td>
	            	<td class="tdr" width="10%" >处理时限</td>
	                <td class="tdl"  >
						<input  type="text"  id="protimeid" name="begintime" value="" readonly="readonly" class="inputtext" style="width:100" />
                    	<INPUT TYPE='BUTTON' VALUE='日期' maxlength="3" ID='btn'  onclick="GetSelectDateTHIS('protimeid')" STYLE="font:'normal small-caps 6pt serif';" >
						<html:text property="time" styleClass="inputtext" style="width:60" maxlength="45" readonly="true"/>
      					<input type='button' value="时间" id='btn' onclick="getTimeWin('time')" style="font:'normal small-caps 6pt serif';">
                    </td>
		        </tr>  
		        <tr class=trcolor >
		        	<td colspan="4" width="100%"  cellpadding="0" cellspacing="0">
		        		<table id="DeptTableId" width="100%" cellpadding="3" cellspacing="0">
		        			<tr class=trcolor>
		        				<td class="tdr" width="10%">受理部门</td>		                
					            <td class="tdl"  width="25%">
						        	<logic:present name="deptinfo"  scope="session" >
						            	<select name="acceptdeptid"   class="inputtext"  style="width:120" id="deptid" onchange="onSelectChange(id)">
					                    	<logic:iterate id="deptId"  name="deptinfo">
					                        	<option value="<bean:write  name="deptId" property="deptid"/>"><bean:write name="deptId"  property="deptname"/></option>
					                		</logic:iterate>
										</select>
						             </logic:present>
						        </td>
						        <td class="tdr" width="10%" >受 理 人</td>
					                <td class="tdl"  >
						            <logic:present name="userinfo"  scope="session" >
						            	<select name="acceptuserid" id="userid" class="inputtext" style="width:100">
					                		<logic:iterate id="userId"  name="userinfo">
					                            <option value="<bean:write  name="userId" property="userid"/>"><bean:write name="userId"  property="username"/></option>
					                        </logic:iterate>
										</select> 
						             </logic:present>
						         </td>   
						         <td><input type="button" class="button" value="添加" onclick="addDept()"></td> 
		        			</tr>		        				
		        		</table>
		        	</td>
		        </tr>

	         	<tr class=trcolor >
	            	<td class="tdr">任务主题</td>
	                <td class="tdl"  colspan="3" ><html:text property="sendtopic" style="width:82%" styleClass="inputtext" maxlength="100"/></td>
	        	</tr>
	
	             <tr class=trcolor >
	            	<td class="tdr" >任务要求</td>
	                <td class="tdl" colspan="3"><html:textarea property="sendtext" alt=" 任务要求最长510个汉字！" style="width:82%"  rows="5" styleClass="textarea"></html:textarea></td>
	        	</tr>
	
	            <tr class=trcolor >
	            	<td class="tdr" >任务附件</td>
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
	                	 	  	<input type="checkbox" onclick="checkOrCancel()" id="sel">全选&nbsp;
	                	 	  </td>
					          <td>
					            <html:button property="action" styleClass="button" onclick="addRowMod()">添加附件</html:button>
					          </td>
					          <td>
					          	<html:button property="action" styleClass="button" onclick="delRow()">删除选中</html:button>
					          </td>
					          <td>
					            <html:button styleClass="button" onclick="checkSub()" property="action">提交</html:button>
					          </td>
					          <td>
					            <html:reset styleClass="button">重置</html:reset>
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

    <!--显示所有派单页面-->
    <logic:equal  name="type" scope="session" value="1">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<br>
		<template:titile value="派单信息一览表"/>		
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
        	<display:column  media="html" title="流水号" sortable="true" style="width:90px"> 
        		<% 
        			if(serialnumber != null ) {
        		%>       		
        		<a href="javascript:toGetForm('<%=id%>','<%=subid%>')"><%=serialnumber%></a>
        		<% } %>
        	</display:column>
        	<display:column media="html" title="主题"  sortable="true">
        		<a href="javascript:toGetForm('<%=id %>','<%=subid%>')" title="<%=titleTopic %>"><%=sendtopic%></a>
        	</display:column>        	 
            <display:column property="sendtype" title="类型"  style="width:80px" maxLength="5" sortable="true"/>
            <display:column property="senddeptname" title="派单单位" style="width:80px" maxLength="5" sortable="true"/>           
            <display:column  media="html" title="处理期限" sortable="true" style="width:80px"> 
            	<%if(processterm.indexOf('-') != -1) {%>
            		<font color="red" ><%=processterm %></font>
            	<%} else {%>
            		<%=processterm %>
            	<%} %>
            </display:column>
            <display:column property="usernameacce" title="处理人" style="width:60px" maxLength="4" sortable="true"/>
            <apptag:checkpower thirdmould="110104" ishead="0">
            	<display:column media="html" title="操作" style="width:40px">
            	<%if("待签收".equals(workstate)) {%>
            		<a href="javascript:toUpForm('<%=id%>','<%=subid%>')">修改</a>
            	<%} else {%>
            	--
            	<%} %>
            	
                 </display:column>
            </apptag:checkpower>


		</display:table>
    <html:link action="/SendTaskAction.do?method=exportSendTaskResult">导出为Excel文件</html:link>
	</logic:equal>

     <!--显示一个派单详细信息页面-->
    <logic:equal  name="type" scope="session" value="10">
      	<br>
		<template:titile value="派单详细信息"/>
		<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
		        	<tr class=trcolor height="40">
		            	<td class="tdr"  width="12%">工作单流水号</td>
		                <td class="tdl" width="18%"><bean:write name="bean" property="serialnumber"/></td>
		                <td class="tdr"  width="10%">处理时限</td>
		                <td class="tdl" width="18%">
                        	<bean:write name="bean" property="processterm"/>
			             </td>
			             <td class="tdr"  width="10%">工作类别</td>
		                <td class="tdl">
                        	<bean:write name="bean" property="sendtype"/>
			             </td>
		        	</tr>
		        	<tr class=trcolor height="40">
                    	<td class="tdr"  width="10%">派单时间</td>
		                <td class="tdl" width="18%"> <bean:write name="bean" property="sendtime"/></td>
		                <td class="tdr"  width="10%">派单部门</td>
		                <td class="tdl" width="18%"><bean:write name="LOGIN_USER_DEPT_NAME"/></td>
		                <td class="tdr"  width="10%">派 单 人</td>
                        <td class="tdl" ><bean:write name="bean" property="username"/>
			                 </td>

		        	</tr>
					<logic:equal value="0" name="newOldFlg">
						<logic:iterate id="element" name="deptlist">
							<tr class=trcolor height="40">
			                	<td class="tdr"   width="10%">受理部门</td>
			                	<td class="tdl" width="18%"><bean:write name="element" property="deptname"/>
				                </td>
			                	<td class="tdr" width="10%" >受 理 人</td>
			                	<td class="tdl" colspan="3"><bean:write name="element" property="username"/>			                	
				               </td>
		        			</tr>
						</logic:iterate>
					</logic:equal>
					<logic:equal value="1" name="newOldFlg">
						<tr class=trcolor height="40">
			                	<td class="tdr"   width="10%">受理部门</td>
			                	<td class="tdl" width="18%"><bean:write name="bean" property="acceptdeptname"/>
				                </td>
			                	<td class="tdr" width="10%" >受 理 人</td>
			                	<td class="tdl" colspan="3"><bean:write name="bean" property="usernameacce"/>
				               </td>
		        			</tr>
					</logic:equal>
						

		            <tr class=trcolor height="40" >
		            	<td class="tdr"  width="10%">任务主题</td>
		                <td class="tdl"  colspan="5" ><bean:write name="bean" property="sendtopic"/></td>
		        	</tr>

		             <tr class=trcolor height="40">
		            	<td class="tdr"  width="10%">任务要求</td>
		                <td class="tdl"   colspan="5"><div><bean:write name="bean" property="sendtext"/></div></td>
		        	</tr>

		            <tr class=trcolor height="40" >
		            	<td class="tdr"  width="10%">任务附件</td>
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
                    	<td class="tdr"  width="10%">签收结果</td>
                        <td class="tdl" width="18%"><%=result %></td>
                       	<td class="tdr"  width="10%">工作状态</td>
                        <td colspan="3" class="tdl"><%=state %></td>
                    </tr>
                    <logic:notEmpty name="endorsebean">
                    	<tr class=trcolor height="40" >
			                    	<td class="tdr"   colspan="6"><hr/></td>
                        </tr>
                    	 <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%">签收备注</td>
	                        <td class="tdl"  colspan="5">
	                        	<bean:write name="endorsebean" property="remark"/>
	                        </td>
	                     </tr>
                         <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%">签收附件</td>
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
                <p align="center"><input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" ></p>
    </logic:equal>


    <!--条件查询显示-->
     <logic:equal value="3" name="type"scope="session" >
	      	<br />
	        <template:titile value="按条件查找任务派单"/>
	        <html:form action="/SendTaskAction?method=doQuery"   styleId="queryForm2" >
            	<template:formTable namewidth="100" contentwidth="250">                   
				    <template:formTr name="派 单 人">
					  	 <html:text property="sendusername"  styleClass="inputtext" style="width:180" />
				    </template:formTr>
				      <template:formTr name="派单主题">
				      	<html:text property="sendtopic"  styleClass="inputtext" style="width:180" />
				      </template:formTr>
				      <template:formTr name="工作单类别">
				      	<html:select property="sendtype" style="width:180" styleClass="inputtext">
				      			<html:option value="">不限</html:option>
	                			<html:option value="交付工作">交付工作</html:option>
	                			<html:option value="投诉处理">投诉处理</html:option>
	                			<html:option value="共建维护">共建维护</html:option>
	                			<html:option value="报表提交">报表提交</html:option>
	                			<html:option value="紧急任务">紧急任务</html:option>
	                			<html:option value="特别工作">特别工作</html:option>
	               		</html:select>
				    </template:formTr>					
				    <template:formTr name="处理时限">
                      	<select name="processterm"  style="width:180" class="inputtext">
                        			<option value="">不限</option>
		                       		<option value="超期">超时</option>
		                       		<option value="未超期">未超时</option>
                      	</select>
				    </template:formTr>
                    <template:formTr name="工作状态">
                      	<select name="workstate"  style="width:180" class="inputtext">
                        			<option value="">不限</option>
                                    <option value="0待重派">待重派</option>
		                       		<option value="1待签收">待签收</option>
                                    <option value="1待重做">待重做</option>
		                       		<option value="3待回复">待回复</option>
		                       		<option value="6待验证">待验证</option>
                                    <option value="9已存档">已存档</option>
                      	</select>
				    </template:formTr>
                    <template:formTr name="派单开始时间">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='日期'  readonly="readonly" ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formTr name="派单结束时间">
	                	<input  id="end" type="text"  name="endtime" readonly="readonly" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='日期' ID='btn'  onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formSubmit>
						      	<td>
		                          <html:submit property="action" styleClass="button" >查找</html:submit>
						      	</td>
						      	<td>
						       	 	<html:reset property="action" styleClass="button" >重置</html:reset>
						      	</td>
						    </template:formSubmit>
	        	</template:formTable>

	    	</html:form>
	    </logic:equal>


        <!--修改显示-->
        <logic:equal value="4" name="type" scope="session">
    		<br>
		 	<template:titile value="修改派单"/>
			<html:form action="/SendTaskAction.do?method=upSendtask" styleId="addApplyForm" enctype="multipart/form-data">
		    	<html:hidden property="id"  name="bean"/>
		    	<html:hidden property="subtaskid"  name="bean"/>
                <html:hidden property="result" name="bean"/>
                <html:hidden property="sendacce" name="bean"/>
		    	<html:hidden property="processterm"  value=""/>

                 <table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
		        	<tr class=trcolor >
		            	<td class="tdr"  width="10%">工作类别</td>
		                <td class="tdl" width="30%">		                	
		                	<html:select property="sendtype" style="width:120" styleClass="inputtext">
	                			<html:option value="交付工作">交付工作</html:option>
	                			<html:option value="投诉处理">投诉处理</html:option>
	                			<html:option value="共建维护">共建维护</html:option>
	                			<html:option value="报表提交">报表提交</html:option>
	                			<html:option value="紧急任务">紧急任务</html:option>
	                			<html:option value="特别工作">特别工作</html:option>
	                		</html:select>
		                </td>
		                <td class="tdr"  width="10%">网维流水号</td>
		                <td class="tdl" colspan="2">
		                </td>
		               
		        	</tr>
		        	
		        	<tr class=trcolor>
		        		<td class="tdr" width="10%">派单部门</td>
		                <td class="tdl" width="30%"><bean:write name="LOGIN_USER_DEPT_NAME"/></td>
		        		<td class="tdr" width="10%">处理时限</td>
		                <td class="tdl"  colspan="2">
                        	<bean:define id="temp" name="bean" property="processterm"  type="java.lang.String"/>
                        	
						      <input  type="text"  id="protimeid" name="begintime" value="<%= temp.substring(0,10)%>" readonly="readonly" class="inputtext" style="width:100" />
                    		  <INPUT TYPE='BUTTON' VALUE='日期' maxlength="3" ID='btn'  onclick="GetSelectDateTHIS('protimeid')" STYLE="font:'normal small-caps 6pt serif';" >

                        		<html:text  value="<%= temp.substring(11,16)%>" property="time" styleClass="inputtext" style="width:60" maxlength="45" readonly="true"/>
      							<input type='button' value="时间" id='btn' onclick="getTimeWin('time')" style="font:'normal small-caps 6pt serif';">
                        </td>		        	
		        	</tr>
							
					<tr class=trcolor >
						<td colspan="5" cellpadding="0" cellspacing="0" width="100%">
							<table	id="DeptTableId" width="100%" cellpadding="3" cellspacing="0">
							<logic:equal value="0" name="newOldFlg">
							<logic:iterate id="element" name="deptList" indexId="ind">
								<tr class=trcolor id="<bean:write name='ind'/>">
									<td class="tdr"   width="10%">受理部门</td>
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
					                <td class="tdr" width="10%" >受 理 人</td>
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
						             	<td><input type="button" class="button" value="添加" onclick="addDept()"></td>
						             </logic:equal>
						             <logic:notEqual value="0" name="ind">
						             	<td><input type="button" class="button" value="删除" id="b<bean:write name='ind'/>" onclick="deleteRowDept2(this)"></td>
						             </logic:notEqual>						             
								</tr>
								</logic:iterate>
								</logic:equal>
								<logic:equal value="1" name="newOldFlg">
										<tr class=trcolor>
										<td class="tdr"   width="10%">受理部门</td>
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
						                <td class="tdr" width="10%" >受 理 人</td>
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
							             <!-- <input type="button" class="button" value="添加" onclick="addDept()"></td> --><td>
							             
							            </tr>
							    </logic:equal>
							</table>
						</td>
       				</tr>
		
		            <tr class=trcolor >
		            	<td class="tdr"  width="10%">任务主题</td>
		                <td class="tdl" colspan="4" ><html:text property="sendtopic"  name="bean" style="width:82%" styleClass="inputtext" maxlength="100"/></td>
		        	</tr>

		             <tr class=trcolor >
		            	<td class="tdr"  width="10%">任务要求</td>
		                <td class="tdl"  colspan="4"><html:textarea property="sendtext" alt=" 任务要求最长510个汉字！" name="bean" style="width:82%"  rows="5" styleClass="textarea"></html:textarea></td>
		        	</tr>

		            <tr class=trcolor >
		            	<td class="tdr"  width="10%">任务附件</td>
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
		            	<td class="tdr"  width="10%">结束派单</td>
		                <td class="tdl"  colspan="4"><input type="checkbox"  name="flag"  value="1" />  不改了,结束该派单.</td>
		        	 </tr>
                     <logic:notEmpty name="endorsebean" scope="session">
                     		<tr class=trcolor >
			                    	<td class="tdr"   colspan="5"><hr/></td>
			                 </tr>
                     		  <tr class=trcolor>
			                    	<td class="tdr"  width="10%">签收结果</td>
			                        <td class="td1" width="30%">
			                        	<bean:write name="endorsebean" property="result"/>
			                        	<html:hidden property="result" name="bean" style="width:100" styleClass="inputtext"/>
			                        </td>
			                        <td class="tdr"  width="10%">工作状态</td>
			                        <td class="td1" colspan="2">
			                        	<bean:write name="bean" property="workstate"/>
			                        	<html:hidden property="workstate" name="bean" style="width:100;background:#F5F5F5" styleClass="inputtext"/>
			                        </td>
			                    </tr>
	                    	 <tr class=trcolor>
		                    	<td class="tdr"  width="10%">签收备注</td>
		                        <td class="tdl"  colspan="4">
		                        	<bean:write name="endorsebean" property="remark"/>
		                        	<html:hidden property="remark"  name="endorsebean"  styleClass="textarea" value="<bean:write name="endorsebean" property="remark"/>" />
		                        </td>
		                     </tr>
	                         <tr class=trcolor>
		                    	<td class="tdr"  width="10%">签收附件</td>
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
			                	 	  	<input type="checkbox" onclick="checkOrCancel()" id="sel">全选&nbsp;
			                	 	  </td>
							          <td>
							            <html:button property="action" styleClass="button" onclick="addRowMod()">添加附件</html:button>
							          </td>
							          <td>
							          	<html:button property="action" styleClass="button" onclick="delRow()">删除选中</html:button>
							          </td>
							          <td>
							            <html:button styleClass="button" onclick="onSubmitClick()" property="action">提交</html:button>
							          </td>
							          <td>
							            <html:reset styleClass="button">重置</html:reset>
							          </td>
							          <td>
							            <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >
							          </td>
						        </template:formSubmit>
			                </td>
			            </tr>
		        </table>
			</html:form>
    </logic:equal>




</body>
</html>
