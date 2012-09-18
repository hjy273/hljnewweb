<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.linepatrol.sendtask.beans.*;" %>

<html>
<head>
<title>
sendtask
</title>
<script type="" language="javascript">
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
        b1.value = "删除";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");


        cell1.appendChild(t1);//文字
        cell2.appendChild(b1);
	}

 //显示一个派单详细信息
 function toGetForm(id,subid){
//	if(loginUserid != acceptuserid) {
//		alert("对不起，这个任务的指定处理人不能您，所以不能签收！");
//		return;
//	}
 	var url = "${ctx}/EndorseAction.do?method=showEndorseTask&id=" + id+"&subid="+subid;
    window.location.href=url;
//window.open(url);
 }

 function toGetFormEndorse(id,subid){
	
 	var url = "${ctx}/EndorseAction.do?method=showOneEndorse&id=" + id+"&subid="+subid;
    window.location.href=url;
	//window.open(url);
 }

 function addGoBack(){
 	var url ="${ctx}/EndorseAction.do?method=showAllEndorse";
    window.location.href=url;
 }

	function addGoBackToEnd(){
 		var url ="${ctx}/EndorseAction.do?method=showTaskToEndorse";
    	window.location.href=url;
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
function validate(form){
  if(valCharLength(form.remark.value)>1020){
    alert("签收备注字数太多！不能超过510个汉字");
    return false;
  }
}
 //选择日期
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}
	
	function oneInfoGoBack() {
		var url="EndorseAction.do?method=doQueryAfterMod";
		window.location.href=url;
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
	

</script>
<!--[if IE]>
<style>
div {word-break:break-all;;}
</style>
<![endif]-->


</head>
<body>
	<!--显示已签收的派单信息-->
    <logic:equal  name="type" scope="session" value="1">
<!--%@include file="/common/listhander.jsp"%-->
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<br>
		<template:titile value="签收派单一览表"/>

        <display:table    name="sessionScope.endorselist" requestURI="${ctx}/EndorseAction.do?method=doQuery"   id="currentRowObject"  pagesize="20">
        		<%
		    	BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
		    	
		    	String serialnumber="";
			    String id="";
			    String sendtopic="";
			    String subid = "";
			    String titleTopic = "";
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
			    	processterm = String.valueOf(object.get("processterm"));
			    	
			    } 
				%>
        	<display:column media="html" title="流水号" sortable="true" style="width:90px">        		
				<% 
        			if(serialnumber != null ) {
        		%>       		
        			<a href="javascript:toGetFormEndorse('<%=id%>', '<%=subid%>')"><%=serialnumber%></a>
        		<% } %>
			</display:column>	
			<display:column media="html" title="主题" sortable="true" >			
				<a href="javascript:toGetFormEndorse('<%=id%>', '<%=subid%>')" title="<%=titleTopic %>"><%=sendtopic%></a>
			</display:column>
            <display:column property="sendtype" title="类型" style="width:80px" sortable="true" maxLength="4"/>
            <display:column property="senddeptname" title="派单单位" style="width:100px" sortable="true" maxLength="8"/>
            <display:column  media="html" title="处理期限" sortable="true" style="width:80px"> 
            	<%if(processterm.indexOf('-') != -1) {%>
            		<font color="red" ><%=processterm %></font>
            	<%} else {%>
            		<%=processterm %>
            	<%} %>
            </display:column>
            <display:column property="usernameacce" title="处理人" style="width:60px" sortable="true" maxLength="4"/>           
		</display:table>
		<logic:notEmpty name="endorselist">
        	<html:link action="/EndorseAction.do?method=exportendorseResult">导出为Excel文件</html:link>
        </logic:notEmpty>
	</logic:equal>


    <!--已经签收的派单详细信息-->
    <logic:equal  name="type" scope="session" value="10">
      	<br>
		<template:titile value="派单签收详细信息"/>
		<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
		        	<tr class=trcolor height="40">
		            	<td class="tdr"  width="12%">工作单流水号</td>
		                <td class="tdl"  width="18%">
		                	<bean:write name="bean" property="serialnumber"/>
		                </td>
		                <td class="tdr"  width="10%">工作单类别</td>
		                <td class="tdl"  width="18%" colspan="3">
                        	<bean:write name="bean" property="sendtype"/>
			            </td>
		        	</tr>
		        	<tr class=trcolor height="40" >
                    	<td class="tdr"  width="10%">派单时间</td>
		                <td class="tdl" width="18%"><bean:write name="bean" property="sendtime"/>  </td>
		                <td class="tdr"  width="10%">派单部门</td>
                        <td class="tdl" width="18%"><bean:write name="bean" property="senddeptname"/> </td>
                        <td class="tdr"  width="10%">派 单 人</td>
                        <td class="tdl"  ><bean:write name="bean" property="username"/>
                    	</td>

		        	</tr>

		        	<tr class=trcolor height="40" >
		                <td class="tdr"   width="10%">受理部门</td>
		                <td class="tdl"  width="18%"><bean:write name="LOGIN_USER" property="deptName"/></td>

		                <td class="tdr" width="10%" >受 理 人</td>
		                <td class="tdl" ><bean:write name="bean" property="usernameacce"/>
		                <td class="tdr" width="10%" >签 收 人</td>
		                <td class="tdl" >
		                	 <logic:notEmpty name="endorsebean">
		                		<bean:write name="endorsebean" property="endorseusername"/>
		                	</logic:notEmpty>
			         	</td>
		        	</tr>



		            <tr class=trcolor height="40" >
		            	<td class="tdr"  width="10%">任务主题</td>
		                <td class="tdl"  colspan="5" ><bean:write name="bean" property="sendtopic"/> </td>
		        	</tr>

		             <tr class=trcolor height="40" >
		            	<td class="tdr"  width="10%">任务说明</td>
		                <td class="tdl" colspan="5"><div><bean:write name="bean" property="sendtext"/></div></td>
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

		            <tr class=trcolor height="40" >
		            	<td class="tdr"  width="10%">任务附件</td>
		                <td class="tdl"  colspan="5">
						   <apptag:upload cssClass="" entityId="${sessionScope.bean.id}" entityType="LP_SENDTASK" state="look"/>
		                </td>
		        	</tr>
                    <tr class=trcolor height="40">
                    	<td class="tdr"  width="10%">签收结果</td>
                        <td class="tdl" > <bean:write name="bean" property="result"/></td>
                        <td class="tdr"  width="10%">签收时间</td>
                        <td class="tdl" >
                        <logic:notEmpty name="endorsebean">
                        <bean:write name="endorsebean" property="time"/>
                         </logic:notEmpty>
                        </td>                       
                        <td class="tdr"  width="10%">工作状态</td>
                        <td class="tdl" ><bean:write name="bean" property="workstate"/></td>
                    </tr>
                    <logic:notEmpty name="endorsebean">
                    	<tr class=trcolor height="40" >
			            <td class="tdr"   colspan="6"><hr/></td>
                        </tr>
                    	 <tr class=trcolor>
	                    	<td class="tdr"  width="10%">签收备注</td>
	                        <td class="tdl" colspan="5"><div><bean:write name="endorsebean" property="remark"/></div></td>
	                     </tr>
                         <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%">签收附件</td>
	                        <td class="tdl" colspan="5">
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
                <p align="center"><input type="button" value="返回" class="button"  onclick="javascript:history.back();"></p>
    </logic:equal>




  <!--显示待签收派单信息-->
    <logic:equal  name="type" scope="session" value="2">
    	<!--%@include file="/common/listhander.jsp"%-->
    	 <%	
    		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    		String loginUserId = userinfo.getUserID();
    		// 取得待签收单的统计信息
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
			待签收派单信息
			<font face="宋体" size="2" color="red" >
			(部门：<%=deptnum %>个; 个人：
			<html:link action="EndorseAction.do?method=showLoginUserTaskToEndorse"><%=usernum %>个</html:link></a>)</font>
		</div>
		<hr width='100%' size='1'>
		
        <display:table    name="sessionScope.endorselist" requestURI="${ctx}/EndorseAction.do?method=showTaskToEndorse"  id="currentRowObject"  pagesize="20">
        	     <%
				    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				    String serialnumber="";
				    String id="";
				    String sendtopic="";
				    String subid = "";
				    String acceptuserid = "";
				    String usernameacce = "";
				    String processterm = "";
				    String titleTopic = "";
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
				    	acceptuserid = (String)object.get("acceptuserid");
				    	usernameacce = (String)object.get("usernameacce");
				    	processterm = String.valueOf(object.get("processterm"));
				    }
				    
				 %>
        	<display:column media="html" title="流水号"  style="width:90px" sortable="true">
        		<% 
        			if(serialnumber != null ) {
        		%>       		
        		<a href="javascript:toGetForm('<%=id%>', '<%=subid %>')"><%=serialnumber%></a>
        		<% } %>
        	</display:column>
        	<display:column media="html" title="主题" sortable="true">
        		<a href="javascript:toGetForm('<%=id %>', '<%=subid %>')" title="<%=titleTopic %>"><%=sendtopic%></a>
        	</display:column>
        	<display:column property="sendtype" title="类型"  style="width:80px" maxLength="5" sortable="true"/>
        	<display:column property="senddeptname" title="派单单位" style="width:100px" maxLength="8" sortable="true"/>
        	 <display:column  media="html" title="处理期限" sortable="true" style="width:80px"> 
            	<%if(processterm.indexOf('-') != -1) {%>
            		<font color="red" ><%=processterm %></font>
            	<%} else {%>
            		<%=processterm %>
            	<%} %>
            </display:column>
            <display:column media="html" title="处理人" sortable="true" style="width:60px" >
            <%if(loginUserId.equals(acceptuserid)) {%>
            	<font color="#CA7020"><%=usernameacce %></font>
            <%} else {%>
            	<%=usernameacce %>
            <%} %>
            </display:column>
            
		</display:table>
	</logic:equal>

 <!--显示一个待签收派单详细信息页面-->
    <logic:equal  name="type" scope="session" value="20">
    	<%
    		//UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    		//String loginUserName = userinfo.getUserName(); 
    	
    	 %>
      	<br>
		<template:titile value="签收派单"/>
        <html:form action="/EndorseAction.do?method=endorseTask" styleId="addApplyForm" onsubmit="return validate(this)" enctype="multipart/form-data">
			<html:hidden property="id" name="bean"/>
			<html:hidden property="subtaskid" name="bean"/>
            <html:hidden property="senduserid" name="bean"/>
            <input name="sendtopic" type="hidden" value="<bean:write property="sendtopic" name="bean" />"/>
          	<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
		        	<tr class=trcolor heigth="40">
		                <td class="tdr"  width="10%">工作流水号</td>
		                <td class="tdl"  width="18%">
		                	<bean:write property="serialnumber" name="bean"/>
		                </td>
		                <td class="tdr"  width="10%">工作类别</td>
		                <td class="tdl"  width="18%" colspan="3">
		                	<bean:write property="sendtype" name="bean"/>
		                </td>
		        	</tr>

		        	<tr class=trcolor heigth="40">
		                <td class="tdr"   width="10%">受理部门</td>
		                <td class="tdl"  width="18%">
		                	<bean:write name="LOGIN_USER" property="deptName"/>
		                </td>
		                <td class="tdr" width="10%" >受 理 人</td>
		                <td class="tdl" width="18%" colspan="3" >
		                	<bean:write property="usernameacce" name="bean"/>
		                </td>
                         
		        	</tr>

                    <tr class=trcolor heigth="40">
                    	<td class="tdr"  width="10%">派单时间</td>
		                <td class="tdl" width="18%">
		                	<bean:write property="sendtime" name="bean"/>
		                </td>
		                <td class="tdr"  width="10%">派单部门</td>
		                <td class="tdl" width="18%">
		                	<bean:write property="senddeptname" name="bean"/>
		                </td>
		                <td class="tdr"  width="10%">派 单 人</td>
                     	<td class="tdl" >
                       		<bean:write property="username" name="bean"/>
						</td>
		        	</tr>

		            <tr class=trcolor heigth="40">
		            	<td class="tdr"  width="10%">任务主题</td>
		                <td class="tdl"  colspan="5" >
		                	<bean:write property="sendtopic" name="bean"/>
		                </td>
		        	</tr>

		             <tr class=trcolor heigth="40">
		            	<td class="tdr"  width="10%">任务说明</td>
		                <td class="tdl"   colspan="5">
		                	<bean:write property="sendtext" name="bean"/>
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
							<apptag:upload cssClass="" id="sendTaskId" entityId="${sessionScope.bean.id}" entityType="LP_SENDTASK" state="look"/>
		                </td>
		        	</tr>
                    <tr class=trcolor heigth="40">
                    	<td class="tdr"  width="10%" >签收结果</td>
                        <td class="tdl" colspan="5">
	                        <select name="result"  style="width:150" class="inputtext">
	                        			<option value="签收">签收</option>
			                       		<option value="拒签">拒签</option>
	                      	</select>
                        </td>
                    </tr>
                    <tr class=trcolor heigth="40">
                    	<td class="tdr"  width="10%" >签收备注</td>
                        <td class="tdl"  colspan="5"><html:textarea property="remark" title="请不要超过256个汉字或者512个英文字符，否则将截断。"   style="width:80%"  rows="6" styleClass="textarea"></html:textarea></td>
                    </tr>
                    <tr class=trcolor heigth="40" >
		            	<td class="tdr"  width="10%">签收附件</td>
		                <td class="tdl"  colspan="5">
		                	 <!-- <table id="uploadID" width="70%" border="0" align="left" cellpadding="3" cellspacing="0" >
						          <tr class=trcolor>
						            	<td></td>
						          </tr> -->
						          <apptag:upload cssClass="" entityId="" entityType="LP_SENDTASKENDORSE" state="add"/>
		                     </table>
		                </td>
		        	</tr>
		        </table>
                 <table align="center">
			            <tr>
			            	<td colspan="6" class="tdc">
			                	 	  <!-- <td>
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
							            <html:submit styleClass="button">签收</html:submit>
							          </td>
							          <td>
							            <html:reset styleClass="button">重置</html:reset>
							          </td>
							          <td>
							            <input type="button" value="返回" class="button"  onclick="javascript:history.go(-1);">
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
	        <html:form action="/EndorseAction?method=doQuery"   styleId="queryForm2" >
            	<template:formTable namewidth="100" contentwidth="250">
            		<template:formTr name="派单主题">
				      <html:text property="sendtopic"  styleClass="inputtext" style="width:180" />
				    </template:formTr>
				    <template:formTr name="工作单类别">
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
                     <template:formTr name="签收结果">
                      	<select name="result"  style="width:180" class="inputtext">
                        			<option value="">不限</option>
		                       		<option value="签收">签收</option>
		                       		<option value="拒签">拒签</option>
                      	</select>
				    </template:formTr>
                    <template:formTr name="签收开始时间">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" style="width:150" />
	                </template:formTr>
	                <template:formTr name="签收结束时间">
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
</body>
</html>
