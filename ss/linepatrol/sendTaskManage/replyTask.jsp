<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.linepatrol.sendtask.beans.*,org.apache.commons.beanutils.DynaBean;" %>

<html>
<head>
<title>
sendtask
</title>
<script type="" language="javascript">
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
    //初始化数组
    function initArray(deptid,userid,username){
    	rowArr[0] = deptid;
        rowArr[1] = userid;
        rowArr[2] = username;
        infoArr[infoArr.length] = rowArr;
        rowArr = new Array();
        return true;
    }

	 var fileNum=0;
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
function validate(form){
	var allCheck = document.getElementsByName('ifCheck');
		var length = allCheck.length;
		
		for(var i = 0; i < length; i++) {
			//var index = i + 1;
			var index = allCheck[i].value;
			var pathText = document.getElementById('uploadFile[' + index + '].file');
			var path = pathText.value;
			if(path.length == 0) {
				alert("请选择要上传的附件的路径，\n如果没有要上传的附件请删除!");
				pathText.focus();
				return false;
			}
		}
  if(valCharLength(form.replyremark.value)>1020){
    alert("反馈备注字数太多！不能超过510个汉字");
    return false;
  }
}

 //显示一个派单详细信息
 function toGetForm(id,subid){
//	if(loginUserid != acceptuserid) {
//		alert("对不起，这个任务的指定处理人不能您，所以不能反馈！");
//		return;
//	}
 	var url = "${ctx}/ReplyAction.do?method=showOneToReply&id=" + id + "&subid="+subid;
    window.location.href=url;
 }

 function toGetFormReply(id,subid){
 	var url = "${ctx}/ReplyAction.do?method=showOneReply&id=" + id + "&subid="+subid;
    window.location.href=url;
//window.open(url);
 }

 function addGoBack(){
 	var url ="${ctx}/ReplyAction.do?method=showAllReply";
    window.location.href=url;
 }

	 function addGoBackToReply(){
 		var url ="${ctx}/ReplyAction.do?method=showTaskToReply";
    	window.location.href=url;
 	}


 function toUpForm(id,workstate,subid){
    //if((workstate !="待重做" || workstate == "待验证")){
	 	var url = "${ctx}/ReplyAction.do?method=showOneToUp&id=" + id+"&subid="+subid;
	    window.location.href=url;
  	//}else
   // alert("该派单存档,不能修改!");
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

		//动态上传附件
		function addRowMod() {
			var maxSeq = parseInt(document.getElementById('maxSeq').value);
			maxSeq ++;//自加
			var tableBody = document.getElementsByTagName("tbody")[1];
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
			var tableBody = document.getElementsByTagName('tbody')[1];
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

		function oneInfoGoBack(){
			var url="ReplyAction.do?method=doQueryAfterMod";
			window.location.href=url;
		}

	function toForward(id, subid) {
		//if(loginUserid != acceptuserid) {
		//	alert("对不起，这个任务的指定处理人不能您，所以不能转发");
		//	return;
		//}
		var url = "${ctx}/ReplyAction.do?method=showTaskForward&id=" + id +"&subid="+subid;
    	window.location.href=url;
	}
	
	// 回到待反馈任务的页面
	function backToReply() {
		var url = "${ctx}/ReplyAction.do?method=showTaskToReply";
		window.location.href=url;
	}

	function bodyOnload(){
      var Obj = document.getElementById("deptid");
      if(Obj != null){
      	 onSelectChange("deptid" ) ;
      }
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
</script>
<!--[if IE]>
<style>
div {word-break:break-all;;}
</style>
<![endif]-->
</head>
<body>


  <!--显示待反馈派单信息-->
    <logic:equal  name="type" scope="session" value="2">
    	<!--%@include file="/common/listhander.jsp"%-->
    	<% 	
    		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    		String loginUserid= userinfo.getUserID();
    		// 取得待反馈单的统计信息
    		DynaBean dynaBean = (DynaBean)(((List)request.getSession().getAttribute("countlist")).get(0));;
    		String deptnum = "";
    		String usernum = "";
    		if(dynaBean != null) {
    			deptnum = dynaBean.get("deptnum").toString();
    			usernum = dynaBean.get("usernum").toString();
    		}
    	 %>
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<br>
		<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>
			待反馈派单信息
			<font face="宋体" size="2" color="red" >
			(部门：<%=deptnum %>个; 个人：
			<html:link action="ReplyAction.do?method=showLoginUserTaskToReply"><%=usernum %>个</html:link>)</font>
		</div>
		<hr width='100%' size='1'>
		
		
        <display:table    name="sessionScope.replylist" requestURI="${ctx}/ReplyAction.do?method=showTaskToReply"  id="currentRowObject"  pagesize="20">
        		<%
				    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				    
				    String id = "";
		    		String serialnumber = "";
		    		String sendtopic = "";
		    		String subid = "";
		    		String acceptuserid = "";
		    		String usernameacce = "";
		    		String titleTopic = "";
		    		String processterm = ""; 
		    		if(object != null) {
		    			id = (String) object.get("id");
		    			serialnumber = (String) object.get("serialnumber");
		    			
		    			titleTopic = (String)object.get("sendtopic");
			    		if(titleTopic != null && titleTopic.length() > 30) {
			    			sendtopic = titleTopic.substring(0,30) + "...";
			    		} else {
			    			sendtopic = titleTopic;
			    		}
		    			
		    			subid = (String)object.get("subid");
		    			acceptuserid = (String)object.get("acceptuserid");
		    			usernameacce = (String)object.get("usernameacce");		    			
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
        	<display:column property="sendtype" title="类型" style="width:80px" sortable="true" maxLength="5"/>
        	<display:column property="senddeptname" title="派单单位" style="width:100px" maxLength="8" sortable="true"/>
            <display:column  media="html" title="处理期限" sortable="true" style="width:80px"> 
            	<%if(processterm.indexOf('-') != -1) {%>
            		<font color="red" ><%=processterm %></font>
            	<%} else {%>
            		<%=processterm %>
            	<%} %>
            </display:column>
            <display:column media="html" title="处理人" style="width:60px" sortable="true" >
            	 <%if(loginUserid.equals(acceptuserid)) {%>
            		<font color="#CA7020"><%=usernameacce %></font>
            	<%} else {%>
            		<%=usernameacce %>
            	<%} %>
            </display:column>
            <display:column  media="html" title="操作" sortable="true" style="width:40px"> 
                <%if(loginUserid.equals(acceptuserid)) {%>
                	<a href="javascript:toForward('<%=id %>','<%=subid%>')">转发</a>  
                <%} else { %>
                --
                <%} %>   		
        		
        	</display:column>           
		</display:table>
	</logic:equal>


	<!--反馈派单-->
    <logic:equal  name="type" scope="session" value="20">
    <%    	
    	//UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    	//String loginUserName = userinfo.getUserName();  
     %>
      	<br>
      	<logic:equal value="2" scope="session" name="state">
		<template:titile value="派单反馈"/>
        <html:form action="/ReplyAction.do?method=replyTask" styleId="addApplyForm" onsubmit="return validate(this)" enctype="multipart/form-data">
        	<html:hidden property="id" name="bean"/>
        	<html:hidden property="subtaskid" name="bean"/>
            <html:hidden property="senduserid" name="bean"/>
			<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
			        	<tr class=trcolor heigth="40">
			        		<td class="tdr"  width="12%">工作单流水号</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="serialnumber" name="bean" />			                	
			                </td>
			            	<td class="tdr"  width="12%">工作单类别</td>
			                <td class="tdl"  width="18%" colspan="3">
			                	<bean:write property="sendtype" name="bean" />
			                	<input name="sendtype" type="hidden" value="<bean:write property="sendtype" name="bean" />" />
			                </td>
			        	</tr>
			        	
			        	<tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%">派单时间</td>
			                <td class="tdl" width="18%"> 
			                	<bean:write property="sendtime" name="bean" />
			                	<input name="sendtime" type="hidden" value="<bean:write property="sendtime" name="bean" />" />
			                </td>
			                <td class="tdr"  width="10%">派单部门</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write property="senddeptname" name="bean" />
	                        	<input name="senddeptname" type="hidden" value="<bean:write property="senddeptname" name="bean" />"/>
	                        </td>
	                        <td class="tdr"  width="10%">派 单 人</td>
	                        <td class="tdl" >
	                        	  <bean:write property="username" name="bean" />
				                  <input name="username" type="hidden" value=" <bean:write property="username" name="bean" />"/>
				            </td>
				            
			        	</tr>

			        	<tr class=trcolor heigth="40">
			                <td class="tdr"   width="10%">受理部门</td>
			                <td class="tdl"  width="18%">
			                	<bean:write name="LOGIN_USER" property="deptName"/>
	                        	<input readonly="readonly" type="hidden"  value="<bean:write name="LOGIN_USER" property="deptName"/>" />
							</td>
			                <td class="tdr" width="10%" >受 理 人</td>
			                <td class="tdl" >
			                	 <bean:write property="usernameacce" name="bean" />
				                <input name="usernameacce" type="hidden" value="<bean:write property="usernameacce" name="bean" />"/>
				            </td>
				            <td class="tdr" width="10%" >签 收 人</td>
		                	<td class="tdl" >
		                		<logic:notEmpty name="endorsebean">
		                			<bean:write name="endorsebean" property="endorseusername"/>
		                		</logic:notEmpty>
		                	</td>
			        	</tr>

			            <tr class=trcolor heigth="40" >
			            	<td class="tdr"  width="10%">任务主题</td>
			                <td class="tdl"  colspan="5" >
			                	<bean:write property="sendtopic" name="bean" />
			                	<input name="sendtopic" type="hidden" value="<bean:write property="sendtopic" name="bean" />"/>
			                </td>
			        	</tr>

			             <tr class=trcolor heigth="40">
			            	<td class="tdr"  width="10%">任务说明</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write property="sendtext" name="bean" />
			                	<input name="sendtext"  type="hidden" value="<bean:write property="sendtext" name="bean" />" />
			                </td>
			        	</tr>

				<tr class=trcolor height="40">
					<td class="tdr" width="10%">
						反馈要求
					</td>
					<td class="tdl" colspan="5">
						<bean:write property="replyRequest" name="bean" />
					</td>
				</tr>
				<tr class=trcolor height="40">
					<td class="tdr" width="10%">
						处理时限
					</td>
					<td class="tdl" colspan="5">
						<bean:write property="processterm" name="bean" />
					</td>
				</tr>

			            <tr class=trcolor heigth="40">
			            	<td class="tdr"  width="10%">任务附件</td>
			                <td class="tdl"  colspan="5">
							     <apptag:upload cssClass="" id="sendTaskIdAddReply" entityId="${sessionScope.bean.id}" entityType="LP_SENDTASK" state="look"/>
			                </td>
			        	</tr>
                        <tr class=trcolor heigth="40">
                        	<td class="tdr"   colspan="6"><hr/></td>
                        </tr>
	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%">签收结果</td>
	                        <td class="tdl">
	                        	<bean:write property="result" name="bean" />
	                        	<input name="result" type="hidden" value="<bean:write property="result" name="bean" />"/>
	                        </td>
	                        <td class="tdr"  width="10%">签收时间</td>
	                        <td  class="tdl">
	                        	<logic:notEmpty name="endorsebean">
	                        	<bean:write property="time" name="endorsebean" />
	                        	<input name="time" type="hidden" value="<bean:write property="time" name="bean" />"/>
	                        	</logic:notEmpty>
	                        </td>
	                        <td class="tdr"  width="10%">工作状态</td>
	                        <td class="tdl">
	                        	<bean:write property="workstate" name="bean" />
	                        	<html:hidden property="workstate" name="bean" value=""/>
	                        </td>
	                    </tr>
	                    <logic:notEmpty name="endorsebean">

	                    	 <tr class=trcolor heigth="40">
		                    	<td class="tdr"  width="10%">签收备注</td>
		                        <td class="tdl" colspan="5">
		                        	<bean:write property="remark" name="endorsebean" />
		                        	<input name="remark" type="hidden"  value="<bean:write property="remark" name="endorsebean" />"/>
		                        </td>
		                     </tr>
	                         <tr class=trcolor heigth="40">
		                    	<td class="tdr"  width="10%">签收附件</td>
		                        <td class="tdl"  colspan="5">
								    <apptag:upload cssClass="" id="sendTaskendorseIdAddReply" entityId="${sessionScope.endorsebean.id}" entityType="LP_SENDTASKENDORSE" state="look"/>
	                            </td>
		                     </tr>
	                    </logic:notEmpty>
	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"   colspan="6"><hr/></td>
	                    </tr>


	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%" >反馈结果</td>
	                        <td class="tdl" colspan="5">
		                        <select name="replyresult"  style="width:180" class="inputtext">
		                        <option value="未完成">未完成</option>
		                        			<option value="全部完成">全部完成</option>
				                       		<option value="基本完成">基本完成</option>
                                            <option value="部分完成">部分完成</option>
		                      	</select>
	                        </td>
	                    </tr>
	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%" >反馈备注</td>
	                        <td class="tdl"  colspan="5"><html:textarea property="replyremark"  style="width:80%" title="请不要超过256个汉字或者512个英文字符，否则将截断。"    rows="5" styleClass="textarea"></html:textarea></td>
	                    </tr>
	                    <tr class=trcolor heigth="40">
			            	<td class="tdr"  width="10%">反馈附件</td>
			                <td class="tdl"  colspan="5">
			                	 <!-- <table id="uploadID" width="70%" border="0" align="left" cellpadding="3" cellspacing="0" >
							           <tbody></tbody>
			                     </table> -->
			                     <apptag:upload cssClass="" entityId="" entityType="LP_SENDTASKREPLY" state="add"/>
			                </td>
			        	</tr>
			        </table>
                     <table align="center">
				            <tr>
				            	<td colspan="6" class="tdc">
				                	
				                	 	 <!--  <td>
				                	 	  	<input type="checkbox" id="sel" onclick="checkOrCancel()">全选&nbsp;
				                	 	  </td>
								          <td>
								          	<input type="hidden" id="maxSeq" value="0">
								            <html:button property="action" styleClass="button" onclick="addRowMod()">添加附件</html:button>
								          </td>
								          <td>
								          	<html:button property="action" styleClass="button" onclick="delRow()">删除选中</html:button>
								          </td> -->
								          <td>
								            <html:submit styleClass="button">提交</html:submit>
								          </td>
								          <td>
								            <html:reset styleClass="button">重置</html:reset>
								          </td>
								          <td>
								           <input type="button" value="返回" class="button"  onclick="javascript:history.go(-1)">
								          </td>
				                </td>
				            </tr>
			        </table>
                </html:form>
         </logic:equal>
         
       <logic:equal value="1" name="state" scope="session">  
         <template:titile value="派单反馈"/>
        	<html:form action="/ReplyAction.do?method=replyTask" styleId="addApplyForm" onsubmit="return validate(this)" enctype="multipart/form-data">
        	<html:hidden property="id" name="bean"/>
        	<html:hidden property="subtaskid" name="bean"/>
            <html:hidden property="senduserid" name="bean"/>
			<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
			        	<tr class=trcolor heigth="40">
			        		<td class="tdr"  width="12%">工作单流水号</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="serialnumber" name="bean" />
			                </td>
			            	<td class="tdr"  width="10%">工作类别</td>
			                <td class="tdl"  width="18%" colspan="3">
			                	<bean:write property="sendtype" name="bean" />
			                	<input name="sendtype" type="hidden" value="<bean:write property="sendtype" name="bean" />" />
			                </td>
			        	</tr>
						<tr heigth="40" class=trcolor >
	                    	<td class="tdr"  width="10%">派单时间</td>
			                <td class="tdl" width="18%"> 
			                	<bean:write property="sendtime" name="bean" />
			                		<input name="sendtime" type="hidden" value="<bean:write property="sendtime" name="bean" />" />
			                </td>
			                <td class="tdr"  width="10%">派单部门</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write property="senddeptname" name="bean" />
	                        		<input name="senddeptname" type="hidden" value="<bean:write property="senddeptname" name="bean" />"/>
	                        </td>
	                        <td class="tdr"  width="10%">派 单 人</td>
	                        <td class="tdl" width="18%" >
	                        	  <bean:write property="username" name="bean" />
				                 	<input name="username" type="hidden" value=" <bean:write property="username" name="bean" />"/>
				            </td>
			        	</tr>
			        	<tr heigth="40" class=trcolor >
			                <td class="tdr"   width="10%">受理部门</td>
			                <td class="tdl"  width="18%">
			                	<bean:write name="LOGIN_USER" property="deptName"/>
	                        	<input readonly="readonly" type="hidden"  value="<bean:write name="LOGIN_USER" property="deptName"/>" />
							</td>
			                <td class="tdr" width="10%" >受 理 人</td>
			                <td class="tdl" >
			                	 <bean:write property="usernameacce" name="bean" />
				                 	<input name="usernameacce" type="hidden" value="<bean:write property="usernameacce" name="bean" />"/>
				            </td>
				             <td class="tdr" width="10%" >签 收 人</td>
		                	<td class="tdl" >
		                		<logic:notEmpty name="endorsebean">
		                			<bean:write name="endorsebean" property="endorseusername"/>
		                		</logic:notEmpty>
		                		
		                	</td>
		                	
			        	</tr>

			            <tr heigth="40" class=trcolor >
			            	<td class="tdr"  width="10%">任务主题</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write property="sendtopic" name="bean" />
			                		<input name="sendtopic"  type="hidden" value="<bean:write property="sendtopic" name="bean" />"/>
			                </td>
			        	</tr>

			             <tr heigth="40" class=trcolor >
			            	<td class="tdr"  width="10%">任务说明</td>
			                <td class="tdl"   colspan="5">
			                	<bean:write property="sendtext" name="bean" />
			                		<input name="sendtext" type="hidden" value="<bean:write property="sendtext" name="bean" />" />
			                </td>
			        	</tr>

				<tr class=trcolor height="40">
					<td class="tdr" width="10%">
						反馈要求
					</td>
					<td class="tdl" colspan="5">
						<bean:write property="replyRequest" name="bean" />
					</td>
				</tr>
				<tr class=trcolor height="40">
					<td class="tdr" width="10%">
						处理时限
					</td>
					<td class="tdl" colspan="5">
						<bean:write property="processterm" name="bean" />
					</td>
				</tr>

			            <tr heigth="40" class=trcolor >
			            	<td class="tdr"  width="10%">任务附件</td>
			                <td class="tdl"  colspan="5">
							    <apptag:upload cssClass="" entityId="${sessionScope.bean.id}" entityType="LP_SENDTASK" state="look"/>
			                </td>
			        	</tr>
                        <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                        </tr>
	                    <tr heigth="40" class=trcolor>
	                    	<td class="tdr"  width="10%">签收结果</td>
	                        <td>
	                        	<bean:write property="result" name="bean" />
	                        		<input name="result" type="hidden" value="<bean:write property="result" name="bean" />"/>
	                        </td>
	                        <td class="tdr"  width="10%">签收时间</td>
	                        <td >
	                        	<logic:notEmpty name="endorsebean">
	                        		<bean:write property="time" name="endorsebean" />
	                        			<input name="time" type="hidden" value="<bean:write property="time" name="bean" />"/>
	                        	</logic:notEmpty>	                        	
	                        </td>
	                        <td class="tdr"  width="10%">工作状态</td>
	                        <td >
	                        	<bean:write property="workstate" name="bean" />
	                        	<html:hidden property="workstate" name="bean" value=""/>
	                        </td>
	                    </tr>
	                    <logic:notEmpty name="endorsebean">

	                    	 <tr heigth="40" class=trcolor>
		                    	<td class="tdr"  width="10%">签收备注</td>
		                        <td class="tdl"  colspan="5">
		                        	
		                        	<bean:write property="remark" name="endorsebean" />
		                        		<input name="remark" type="hidden"  value="<bean:write property="remark" name="endorsebean" />"/>
		                        </td>
		                     </tr>
	                         <tr  heigth="40" class=trcolor>
		                    	<td class="tdr"  width="10%">签收附件</td>
		                        <td class="tdl"  colspan="5">
							      <apptag:upload cssClass="" entityId="${sessionScope.endorsebean.id}" entityType="LP_SENDTASKENDORSE" state="look"/>
	                            </td>
		                     </tr>
	                    </logic:notEmpty>
	                    <tr class=trcolor >
	                    	<td class="tdr"   colspan="6"><hr/></td>
	                    </tr>
						
						
						
					
						<%
						
							List beanList = (List)request.getSession().getAttribute("validate_list"); 
							int length = beanList.size();
							for(int i = 0; i < length; i++) {
								//DynaBean taskbean = (DynaBean)beanList.get(i);
								SendTaskBean taskbean=(SendTaskBean)beanList.get(i);
								String valitime = taskbean.getValidatetime();//(String)taskbean.get("validatetime");
								String valiRes = taskbean.getValidateresult();//(String)taskbean.get("validateresult");
								String valiRe = taskbean.getValidateremark();//(String)taskbean.get("validateremark");
								String replyid = taskbean.getReplyid();//(String)taskbean.get("replyid");
								String validateid = taskbean.getValidateid();
								if(valiRe == null) {
									valiRe = "";
								}
								String valiAcc = taskbean.getValidateacce();//(String)taskbean.get("validateacce"); 
								if(valiAcc == null) {
									valiAcc = "";
								}
							
								%>
								
							<tr class=trcolor heigth="40">
							<input type="hidden" name="replyid" value="<%=replyid %>">
		                    	<td class="tdr"  width="10%">验证结果</td>
		                        <td class="tdl">
		                        	<%=valiRes %>
		                        </td>
		                        <td class="tdr" width="10%">验证时间</td>
		                        <td colspan="3" class="tdl">
		                        	<%=valitime %>
		                        </td>
		                    </tr>
						
							<tr class=trcolor heigth="40">
		                    	<td class="tdr"  width="width="10%"">验证备注</td>
		                        <td class="tdl" colspan="5">
		                        	<%=valiRe %>
		                        </td>
		                	</tr>
		                
		                	<tr heigth="40" class=trcolor>
		                    	<td class="tdr"  width="width="10%"">验证附件</td>
		                        <td class="tdl"  colspan="5">
		                        	 <apptag:upload cssClass="" entityId="<%=validateid %>" entityType="LP_VALIDATEREPLY" state="look"/>
	                            </td>
		                	</tr>
		                	
		                	<tr heigth="40" class=trcolor >
		                    	<td class="tdr"   colspan="6"><hr/></td>
		                    </tr>
						<%	} %>
						
		                     
	                    
						
	                    <tr heigth="40" class=trcolor>
	                    	<td class="tdr"  width="width="10%"" >反馈结果</td>
	                        <td class="tdl" colspan="5">
		                        <select name="replyresult"  style="width:180" class="inputtext">
		                        <option value="未完成">未完成</option>
		                        			<option value="全部完成">全部完成</option>
				                       		<option value="基本完成">基本完成</option>
                                            <option value="部分完成">部分完成</option>
		                      	</select>
	                        </td>
	                    </tr>
	                    <tr heigth="40" class=trcolor>
	                    	<td class="tdr"  width="width="10%"" >反馈备注</td>
	                        <td class="tdl"  colspan="5"><html:textarea property="replyremark"  style="width:80%" title="请不要超过256个汉字或者512个英文字符，否则将截断。"    rows="3" styleClass="textarea"></html:textarea></td>
	                    </tr>
	                    <tr heigth="40" class=trcolor >
			            	<td class="tdr"  width="10%">反馈附件</td>
			                <td class="tdl"  colspan="5">
			                	<apptag:upload cssClass="" entityId="" entityType="LP_SENDTASKREPLY" state="add"/>
			                </td>
			        	</tr>
			        </table>
                     <table align="center">
				            <tr>
				            	<td colspan="6" class="tdc">
				                	 	  <td>
				                	 	  </td>
								          <td>
								          	<input type="hidden" id="maxSeq" value="0">
								          </td>
								          <td>
								            <html:submit styleClass="button">提交</html:submit>
								          </td>
								          <td>
								            <html:reset styleClass="button">取消</html:reset>
								          </td>
								          <td>
								            <html:button property="action" styleClass="button" onclick="addGoBackToReply()">返回</html:button>
								          </td>
				                </td>
				            </tr>
			        </table>
                </html:form>
             </logic:equal>  
    </logic:equal>

    	<!--显示已反馈的派单信息列表-->
    <logic:equal  name="type" scope="session" value="1">
    	<!--%@include file="/common/listhander.jsp"%-->
        <%
    		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    		String loginUserid = userinfo.getUserID();   	
    	 %>
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<br>
		<template:titile value="反馈派单一览表"/>
        <display:table    name="sessionScope.lreply"  requestURI="${ctx}/ReplyAction.do?method=doQuery" id="currentRowObject"  pagesize="20">
        	<%
				    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				    String serialnumber = "";
				    String id = "";
				    String sendtopic = "";
				    String subid = "";
				    String acceptuserid = "";
				    String workstate = "";
				    String processterm = "";
				    String titleTopic = "";
				    if(object != null ) {
				    	serialnumber = (String) object.get("serialnumber");
				    	id = (String) object.get("id");
				    	
				    	titleTopic = (String)object.get("sendtopic");
			    		if(titleTopic != null && titleTopic.length() > 30) {
			    			sendtopic = titleTopic.substring(0,30) + "...";
			    		} else {
			    			sendtopic = titleTopic;
			    		}
				    	
				    	subid = (String)object.get("subid");
				    	acceptuserid = (String)object.get("acceptuserid");
				    	workstate = (String)object.get("workstate");
				    	
				    	processterm = String.valueOf(object.get("processterm"));
				    }
				    
				  %>
        	<display:column  media="html" title="流水号" sortable="true" style="width:90px"> 
        		<% 
        			if(serialnumber != null ) {
        		%>       		
        		<a href="javascript:toGetFormReply('<%=id%>','<%=subid %>')"><%=serialnumber%></a>
        		<% } %>
        	</display:column>
        	<display:column media="html" title="主题"  sortable="true" >
        		<a href="javascript:toGetFormReply('<%=id %>','<%=subid %>')" title="<%=titleTopic %>"><%=sendtopic%></a>
        	</display:column>
         	<display:column property="sendtype" title="类型" style="width:80px" sortable="true" maxLength="5"/>
            <display:column property="senddeptname" title="派单单位"  style="width:100px" sortable="true" maxLength="8"/>
            <display:column  media="html" title="处理期限" sortable="true" style="width:80px"> 
            	<%if(processterm.indexOf('-') != -1) {%>
            		<font color="red" ><%=processterm %></font>
            	<%} else {%>
            		<%=processterm %>
            	<%} %>
            </display:column>
            <display:column property="usernameacce" title="处理人" style="width:60px" sortable="true" maxLength="4"/>
             <apptag:checkpower thirdmould="110304" ishead="0" >
            	<display:column media="html" title="操作" style="width:30px" >
            	<%if(loginUserid.equals(acceptuserid) && !"待重做".equals(workstate) && "待验证".equals(workstate)) {%>
            		<a href="javascript:toUpForm('<%=id%>','<%=workstate%>','<%=subid %>')">修改</a>
            	<%} else {%>
            	--
            	<%} %> 	
                 </display:column>
            </apptag:checkpower>
		</display:table>
		<logic:notEmpty name="lreply">
       		 <html:link action="/ReplyAction.do?method=exportReplyResult">导出为Excel文件</html:link>
        </logic:notEmpty>
	</logic:equal>

    	<!--显示派单反馈的详细信息-->
    <logic:equal  name="type" scope="session" value="10">
      	<br>
		<template:titile value="派单反馈详细信息"/>

			<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
			        	<tr class=trcolor heigth="40">
			        		<td class="tdr"  width="11%">网维流水号</td>
			                <td class="tdl"  width="18%">
			                	<bean:write name="bean" property="serialnumber"/>
			                </td>
			            	<td class="tdr"  width="10%">工作单类别</td>
			                <td class="tdl"  width="18%" colspan="3">
			                	<bean:write name="bean" property="sendtype"/>
			                </td>
			        	</tr>
			        	
			        	<tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%">派单时间</td>
			                <td class="tdl" width="18%">
			                	<bean:write name="bean" property="sendtime"/>
			                </td>
			                <td class="tdr"  width="10%">派单部门</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write name="bean" property="senddeptname"/>
	                        <td class="tdr"  width="10%">派 单 人</td>
	                        
	                        <td class="tdl"  >
	                        	<bean:write name="bean" property="username"/>
				            </td>
			        	</tr>

			        	<tr class=trcolor heigth="40">
			                <td class="tdr"   width="10%">受理部门</td>
			                <td class="tdl"  width="18%">
			                	<bean:write name="LOGIN_USER" property="deptName"/>
							</td>
			                <td class="tdr" width="10%" >受 理 人</td>
			                <td class="tdl" colspan="3">
			                	<bean:write name="bean" property="usernameacce"/>
				            </td>
				           
			        	</tr>	                    

			            <tr class=trcolor heigth="40">
			            	<td class="tdr"  width="10%">任务主题</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write name="bean" property="sendtopic"/>
			                </td>
			        	</tr>

			             <tr class=trcolor heigth="40">
			            	<td class="tdr"  width="10%">任务说明</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write name="bean" property="sendtext"/>
			                </td>
			        	</tr>

				<tr class=trcolor height="40">
					<td class="tdr" width="10%">
						反馈要求
					</td>
					<td class="tdl" colspan="5">
						<bean:write property="replyRequest" name="bean" />
					</td>
				</tr>
				<tr class=trcolor height="40">
					<td class="tdr" width="10%">
						处理时限
					</td>
					<td class="tdl" colspan="5">
						<bean:write property="processterm" name="bean" />
					</td>
				</tr>

			            <tr class=trcolor heigth="40">
			            	<td class="tdr"  width="10%">任务附件</td>
			                <td class="tdl"  colspan="5">
							    <apptag:upload cssClass="" entityId="${sessionScope.bean.id}" entityType="LP_SENDTASK" state="look"/>
			                </td>
			        	</tr>
                        <tr class=trcolor heigth="40">
                        	<td class="tdr"   colspan="6"><hr/></td>
                        </tr>
	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%">签收结果</td>
	                        <td class="tdl" >
	                        	<bean:write name="bean" property="result"/>
	                        </td>
	                        <td class="tdr" width="10%" >签 收 人</td>
		                	<td class="tdl" ><bean:write name="endorsebean" property="endorseusername"/></td>
	                        <td class="tdr"  width="10%" >签收时间</td>
	                        <td  class="tdl" >
	                        	<bean:write name="endorsebean" property="time"/>
	                        </td>

	                    </tr>
	                    <logic:notEmpty name="endorsebean">

	                    	 <tr class=trcolor heigth="40">
		                    	<td class="tdr"  width="10%">签收备注</td>
		                        <td class="tdl"  colspan="5">
		                        	<bean:write name="endorsebean" property="remark"/>
		                        </td>
		                     </tr>
	                         <tr class=trcolor heigth="40">
		                    	<td class="tdr"  width="10%">签收附件</td>
		                        <td class="tdl"  colspan="5">
							      <apptag:upload cssClass="" entityId="${sessionScope.endorsebean.id}" entityType="LP_SENDTASKENDORSE" state="look"/>
	                            </td>
		                     </tr>
	                    </logic:notEmpty>
	                    <tr class=trcolor >
	                    	<td class="tdr"   colspan="6"><hr/></td>
	                    </tr>


	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%" >反馈结果</td>
	                        <td class="tdl" >
	                        	<bean:write name="replybean" property="replyresult"/>
	                        </td>
                            <td class="tdr"  width="10%" >反馈人</td>
	                        <td class="tdl" >
	                        	<bean:write name="replybean" property="replyusername"/>
		                    </td>

                            <td class="tdr"  width="10%">反馈时间</td>
	                        <td class="tdl">
	                        	<bean:write name="replybean" property="replytime"/>
	                        </td>
	                        
                        </tr>
                        <tr class=trcolor heigth="40">
                        	<td class="tdr"  width="10%" >工作状态</td>
	                        <td class="tdl" colspan="5" >
	                        	<bean:write name="bean" property="workstate"/>
		                    </td>
                        </tr>
	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%" >反馈备注</td>
	                        <td class="tdl" colspan="5">
	                        	<bean:write name="replybean" property="replyremark"/>
	                        </td>
	                    </tr>
	                    <tr class=trcolor >
			            	<td class="tdr"  width="10%">反馈附件</td>
			                <td class="tdl"  colspan="5">
							      <apptag:upload cssClass="" entityId="${sessionScope.replybean.replyid}" entityType="LP_SENDTASKREPLY" state="look"/>
			                </td>
			        	</tr>
			        </table>
                	<p align="center"><input type="button" value="返回" class="button"  onclick="javascript:history.go(-1)"></p>
					<!-- oneInfoGoBack() -->
	</logic:equal>

       	<!--显示待修改的派单反馈详细信息-->
    <logic:equal  name="type" scope="session" value="4">
      	<br>
		<template:titile value="修改派单反馈"/>
        <html:form action="/ReplyAction.do?method=upReply" styleId="addApplyForm"  onsubmit="return validate(this)" enctype="multipart/form-data">
        	 <html:hidden property="id" name="bean"/>
        	 <html:hidden property="subtaskid" name="bean" />
             <html:hidden property="senduserid"  name="bean"/>
             <html:hidden property="replyacce" name="replybean"/>
             <input  type="hidden" name="replyid" value="<bean:write  property="replyid" name="replybean"/>"/>
             <table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
			        	<tr class=trcolor heigth="40">
			        		<td class="tdr"  width="12%">工作单流水号</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="serialnumber" name="bean"/>
			                </td>
			            	<td class="tdr"  width="10%">工作单类别</td>
			                <td class="tdl"  width="18%" colspan="3">
			                	<bean:write property="sendtype" name="bean"/>
			                	<input name="sendtype" type="hidden" value="<bean:write property="sendtype" name="bean"/>"/>
			                </td>
			        	</tr>
			        	
			        	<tr heigth="40" class=trcolor >
	                    	<td class="tdr"  width="10%">派单时间</td>
			                <td class="tdl" width="18%">
			                	<bean:write property="sendtime" name="bean"/>
			                		<input name="sendtime" type="hidden" value="<bean:write property="sendtime" name="bean"/>"/>
			                </td>
			                <td class="tdr"  width="10%">派单部门</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write property="senddeptname" name="bean"/>
	                        		<input name="senddeptname" type="hidden" value="<bean:write property="senddeptname" name="bean"/>"/>
	                        </td>
	                        <td class="tdr"  width="10%">派 单 人</td>
	                        <td class="tdl" >
	                        	<bean:write property="username" name="bean"/>
				            	<input name="username"  type="hidden" value="<bean:write property="username" name="bean"/>"/>
				           	</td>
			        	</tr>

			        	<tr heigth="40" class=trcolor >
			                <td class="tdr"   width="10%">受理部门</td>
			                <td class="tdl"  width="18%">
			                	<bean:write name="LOGIN_USER" property="deptName"/>
	                        	<input type="hidden" value="<bean:write name="LOGIN_USER" property="deptName"/>"/>
							</td>
			                <td class="tdr" width="10%" >受 理 人</td>
			                <td class="tdl">
			                	<bean:write property="usernameacce" name="bean"/>
				            		<input name="usernameacce"  type="hidden" value="<bean:write property="usernameacce" name="bean"/>"/>
				            </td>
				            <td class="tdr" width="10%" >签 收 人</td>
		                	<td class="tdl" ><bean:write name="endorsebean" property="endorseusername"/></td>
			        	</tr>	                    

			            <tr class=trcolor heigth="40" >
			            	<td class="tdr"  width="10%">任务主题</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write property="sendtopic" name="bean"/>
			                		<input name="sendtopic"  type="hidden" value="<bean:write property="sendtopic" name="bean"/>"/>
			                </td>
			        	</tr>

			             <tr class=trcolor heigth="40" >
			            	<td class="tdr"  width="10%">任务说明</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write property="sendtext" name="bean"/>
			                		<input name="sendtext"  type="hidden" value="<bean:write property="sendtext" name="bean"/>" />
			                </td>
			        	</tr>

				<tr class=trcolor height="40">
					<td class="tdr" width="10%">
						反馈要求
					</td>
					<td class="tdl" colspan="5">
						<bean:write property="replyRequest" name="bean" />
					</td>
				</tr>
				<tr class=trcolor height="40">
					<td class="tdr" width="10%">
						处理时限
					</td>
					<td class="tdl" colspan="5">
						<bean:write property="processterm" name="bean" />
					</td>
				</tr>

			            <tr class=trcolor heigth="40" >
			            	<td class="tdr"  width="10%">任务附件</td>
			                <td class="tdl"  colspan="5">
							    <apptag:upload cssClass="" entityId="${sessionScope.bean.id}" entityType="LP_SENDTASK" state="look"/>
			                </td>
			        	</tr>
                        <tr class=trcolor  heigth="40">
                        	<td class="tdr"   colspan="6"><hr/></td>
                        </tr>
	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%">签收结果</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write property="result" name="bean"/>
	                        		<input name="result" type="hidden" value="<bean:write property="result" name="bean"/>"/>
	                        </td>
	                        <td class="tdr"  width="10%" >签收时间</td>
	                        <td colspan="3" class="tdl">
	                        	<bean:write property="time" name="endorsebean"/>
	                        		<input name="time" type="hidden" value="<bean:write property="time" name="endorsebean"/>"/>
	                        </td>

	                    </tr>
	                    <logic:notEmpty name="endorsebean">

	                    	 <tr class=trcolor heigth="40">
		                    	<td class="tdr"  width="10%">签收备注</td>
		                        <td class="tdl"  colspan="5">
		                        	<bean:write property="remark" name="endorsebean"/>
		                        		<input name="remark"  type="hidden" value="<bean:write property="remark" name="endorsebean"/>" />
		                        </td>
		                     </tr>
	                         <tr class=trcolor heigth="40">
		                    	<td class="tdr"  width="10%">签收附件</td>
		                        <td class="tdl"  colspan="5">
							      <apptag:upload cssClass="" entityId="${sessionScope.endorsebean.id}" entityType="LP_SENDTASKENDORSE" state="look"/>
	                            </td>
		                     </tr>
	                    </logic:notEmpty>
	                    <tr class=trcolor >
	                    	<td class="tdr"   colspan="6"><hr/></td>
	                    </tr>


	                    <tr class=trcolor heigth="40"> 
	                    	<td class="tdr"  width="10%" >反馈结果</td>
	                        <td class="tdl" >
                                 <html:select  property="replyresult"   style="width:100"   styleClass="inputtext">
                                   <logic:equal value="全部完成" name="replybean" property="replyresult">
                                   <option value="未完成">未完成</option>
                                    		<option value="全部完成" selected="selected">全部完成</option>
				                       		<option value="基本完成">基本完成</option>
                                            <option value="部分完成">部分完成</option>
                               		</logic:equal>
                                    <logic:equal value="基本完成" name="replybean" property="replyresult">
                                    <option value="未完成">未完成</option>
                                    		<option value="全部完成" >全部完成</option>
				                       		<option value="基本完成"selected="selected">基本完成</option>
                                            <option value="部分完成">部分完成</option>
                               		</logic:equal>
                                    <logic:equal value="部分完成" name="replybean" property="replyresult">
                                    <option value="未完成">未完成</option>
                                    		<option value="全部完成" >全部完成</option>
				                       		<option value="基本完成">基本完成</option>
                                            <option value="部分完成"selected="selected">部分完成</option>
                               		</logic:equal>
                               		<logic:equal value="未完成" name="replybean" property="replyresult">
                                    <option value="未完成"selected="selected">未完成</option>
                                    		<option value="全部完成" >全部完成</option>
				                       		<option value="基本完成">基本完成</option>
                                            <option value="部分完成">部分完成</option>
                               		</logic:equal>
								</html:select>
                            <td class="tdr"  width="10%" >反馈时间</td>
	                        <td class="tdl" >
	                        	<bean:write property="replytime" name="replybean"/>
		                       	<input name="replytime" type="hidden" value="<bean:write property="replytime" name="replybean"/>"/>
		                    </td>

                            <td class="tdr"  width="10%">工作状态</td>
	                        <td class="tdl">
	                        	<bean:write property="workstate" name="bean"/>
	                        		<input name="workstate"  type="hidden" value="<bean:write property="workstate" name="bean"/>" />
	                        </td>
                        </tr>
	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%" >反馈备注</td>
	                        <td class="tdl"  colspan="5"><html:textarea property="replyremark"  name="replybean" style="width:80%" title="请不要超过256个汉字或者512个英文字符，否则将截断。"  rows="5" styleClass="textarea"></html:textarea></td>
	                    </tr>
	                    <tr class=trcolor heigth="40">
			            	<td class="tdr"  width="10%">反馈附件</td>
			                <td class="tdl"  colspan="5">
			                	 <apptag:upload cssClass="" entityId="${sessionScope.replybean.replyid}" entityType="LP_SENDTASKREPLY" state="edit"/>
			                </td>
			        	</tr>
			        </table>
                     <table align="center">
				            <tr>
				            	<td colspan="6" class="tdc">
								          <td>
				                	 	  	</td>
								          <td>
								          	<input type="hidden" id="maxSeq" value="0">
								          </td>
								          <td>
								          	<html:button property="action" styleClass="button" onclick="delRow()">删除选中</html:button>
								          </td>
								          <td>
								            <html:submit styleClass="button">提交</html:submit>
								          </td>
								          <td>
								            <html:reset styleClass="button">重置</html:reset>
								          </td>
								          <td>
								         <input type="button" class="button" onclick="javascript:history.go(-1);" value="返回" >
								          </td>
				                </td>
				            </tr>
			        </table>
        </html:form>
    </logic:equal>
     <!--条件查询显示-->
     <logic:equal value="3" name="type"scope="session" >
	      	<br />
	        <template:titile value="按条件查找任务派单"/>
	        <html:form action="/ReplyAction?method=doQuery"   styleId="queryForm2" >
            	<template:formTable namewidth="100" contentwidth="250">
            		<template:formTr name="派单主题">
				      <html:text property="sendtopic"  styleClass="inputtext" style="width:180" />
				    </template:formTr>
				    <template:formTr name="工作类别">
				      	<apptag:quickLoadList cssClass="inputtext" name="sendtype"
							listName="dispatch_task" type="select" />
				    </template:formTr>


				    <template:formTr name="处理时限">
                      	<select name="processterm"  style="width:180" class="inputtext">
                        			<option value="">不限</option>
		                       		<option value="超期">超时</option>
		                       		<option value="未超期">未超时</option>
                      	</select>
				    </template:formTr>
                     <template:formTr name="反馈结果">
                      	<select name="result"  style="width:180" class="inputtext">
                        			<option value="">不限</option>
                        			<option value="未完成">未完成</option>
		                       		<option value="全部完成">全部完成</option>
                                    <option value="基本完成">基本完成</option>
                                    <option value="部分完成">部分完成</option>

                      	</select>
				    </template:formTr>
                    <template:formTr name="反馈开始时间">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" style="width:150" />
	                </template:formTr>
	                <template:formTr name="反馈结束时间">
	                	<input  id="end" type="text"  name="endtime" readonly="readonly" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" style="width:150" />
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
	    
<logic:equal value="forward" name="type" scope="session">
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
    <% 
    	UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    	String username = userinfo.getUserName();
    	String deptname = userinfo.getDeptName();
    %>
    	<br>
		<template:titile value="转发任务"/>
		<html:form action="/ReplyAction?method=forwardTask" styleId="addApplyForm"  enctype="multipart/form-data">
	
        	<table id="tableID" width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
		        <tr class=trcolor heigth="40">    
		            <td class="tdr" width="10%" >工作类别</td>
		            <td class="tdl" width="25%" colspan="3">
		            	<html:hidden name="bean" property="sendtype" ></html:hidden> 
		            	<bean:write name="bean" property="sendtype"/>	
		            </td>
		        </tr>
		        <tr class=trcolor heigth="40">		        		
	                <td class="tdr" width="10%">派单部门</td>		                
	                <td class="tdl" width="25%"><%=deptname %></td>
	            	<td class="tdr" width="10%" >派 单 人</td>
	                <td class="tdl"  >
	                	<%=username%>
                    </td>
		        </tr> 
		        <apptag:processorselect inputName="acceptdeptid,user,mobileId,acceptuserid" spanId="userSpan" />
		         
	         	<tr class=trcolor heigth="40">
	            	<td class="tdr">任务主题</td>
	                <td class="tdl"  colspan="3" >
	                	<html:hidden name="bean" property="sendtopic" /> 
	                	<bean:write name="bean" property="sendtopic"/>
	                </td>	
	        	</tr>
	
	             <tr class=trcolor heigth="40">
	            	<td class="tdr" >任务说明</td>
	            	<td class="tdl"  colspan="3" >
	            		<html:hidden name="bean" property="sendtext" /> 
	                	<bean:write name="bean" property="sendtext"/>
	                </td>
	        	</tr>
	
				<tr class=trcolor height="40">
					<td class="tdr" width="10%">
						反馈要求
					</td>
					<td class="tdl" colspan="5">
						<bean:write property="replyRequest" name="bean" />
					</td>
				</tr>
				<tr class=trcolor height="40">
					<td class="tdr" width="10%">
						处理时限
					</td>
					<td class="tdl" colspan="5">
						<bean:write property="processterm" name="bean" />
					</td>
				</tr>

	            <tr class=trcolor heigth="40">
                	<td class="tdr" >任务附件</td>
	                <td class="tdl"  colspan="3">
					    <apptag:upload cssClass="" entityId="${sessionScope.bean.id}" entityType="LP_SENDTASK" state="look"/>
					      <html:hidden name="bean" property="sendacce"/> 
	                </td>
	        	</tr>
	        </table>
	        <table align="center" >
	            <tr>
	            	<td colspan="2" class="tdc">
	                	 <template:formSubmit>
					          <td>
					            <html:submit styleClass="button"  property="action">提交</html:submit>
					          </td>
					          <td>
					            <input type="button" value="返回" class="button"  onclick="javascript:history.go(-1);">
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
</body>
</html>
