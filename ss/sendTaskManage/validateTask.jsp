<%@include file="/common/header.jsp"%>

<%@ page import="com.cabletech.sendtask.beans.*;" %>

<html>
<head>
<title>
sendtask
</title>
<script type="" language="javascript">
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
  if(valCharLength(form.validateremark.value)>1020){
    alert("验证备注字数太多！不能超过510个汉字");
    return false;
  }
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
}
 //显示一个派单详细信息
 function toGetForm(id,subid,loginUserid, userid){
	//if(loginUserid != userid) {
	//	alert("对不起，这个任务的指定处理人不能您，所以不能验证！");
	//	return;
	//}
 	var url = "${ctx}/ValidateAction.do?method=showOneValidate&id=" + id + "&subid="+subid;
    window.location.href=url;
 }

 function toGetFormVali(id,subid){
 	var url = "${ctx}/ValidateAction.do?method=showValidate&id=" + id + "&subid="+subid;
    window.location.href=url;
	//window.open(url);
 }

 function addGoBack(){
 	var url ="${ctx}/ValidateAction.do?method=showAllValidate";
    window.location.href=url;
 }

	function addGoBackToValidate(){
 		var url ="${ctx}/ValidateAction.do?method=showTaskToValidate";
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

		function oneInfoGoBack() {
			var url="ValidateAction.do?method=doQueryAfterMod"
			window.location.href=url;
		}

		function test() {
			var url="${ctx}/SendTaskAction.do?method=showQueryTotal"
			window.location.href=url;
		}

</script>
<!--[if IE]>
<style>
div {word-break:break-all;;}
</style>
<![endif]-->
</head>
<body>

 <!--显示待验证派单信息-->
    <logic:equal  name="type" scope="session" value="2">
     <%
    		// 取得待验证单的统计信息
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
			待验证派单信息
			<font face="宋体" size="2" color="red" >
			(部门：<%=deptnum %>个; 个人：
			<html:link action="ValidateAction.do?method=showTaskToValidate&loginuserflg=1"><%=usernum %>个</html:link>)</font>
		</div>
		<hr width='100%' size='1'>
		

        <display:table    name="sessionScope.valiList" requestURI="${ctx}/ValidateAction.do?method=showTaskToValidate"  id="currentRowObject"  pagesize="20">
        		<%
		    	BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
		    	String id = "";
		    	String serialnumber = "";
		    	String sendtopic = "";
		    	String subid = "";
		    	//String userid = "";
		    	String processterm = "";
				String titleTopic = "";
		    	if(object != null) {
		    		id = (String) object.get("id");
		    		serialnumber = (String) object.get("serialnumber");
		    		
		    		subid = (String)object.get("subid");
		    		//userid = (String)object.get("senduserid");
		    		
		    		titleTopic = (String)object.get("sendtopic");
		    		if(titleTopic != null && titleTopic.length() > 18) {
		    			sendtopic = titleTopic.substring(0,18) + "...";
		    		} else {
		    			sendtopic = titleTopic;
		    		}
		    		
		    		processterm = String.valueOf(object.get("processterm"));
		    	}
				%>
        		<display:column media="html" title="流水号" sortable="true" style="width:90px">        		
				<% 
        			if(serialnumber != null ) {
        		%>       		
        			<a href="javascript:toGetForm('<%=id%>','<%=subid%>')"><%=serialnumber%></a>
        		<% } %>
				</display:column>	
			<display:column media="html" title="主题" sortable="true" >			
				<a href="javascript:toGetForm('<%=id%>','<%=subid%>')" title="<%=titleTopic %>"><%=sendtopic%></a>
			</display:column>
        	<display:column property="sendtype" title="类型"  sortable="true" maxLength="5"  style="width:80px"/>        	
            <display:column property="senddeptname" title="派单单位"  sortable="true" maxLength="8"  style="width:100px"/>
            <display:column  media="html" title="处理期限" sortable="true" style="width:80px"> 
            	<%if("null".equals(processterm)) {%>
            		
            	<%} else if(processterm.indexOf('-') != -1) { %>
            		<font color="red" ><%=processterm %></font>
            	<%} else {%>
            		<%=processterm %>
            	<%} %>
            </display:column>            
            <display:column property="sendusername" title="验证人"  sortable="true" maxLength="4" style="width:60px"/>
		</display:table>
	</logic:equal>


<!--显示派单验证的详细信息,进行验证-->
    <logic:equal  name="type" scope="session" value="20">
    	<%
    		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    		String loginUserName = userinfo.getUserName(); 
    	
    	 %>
		<template:titile value="派单验证"/>
        <html:form action="/ValidateAction.do?method=validateTask" styleId="addApplyForm" onsubmit="return validate(this);" enctype="multipart/form-data">
        	<html:hidden property="id" name="bean"/>
        	<html:hidden property="subtaskid" name="bean"/>
        	<logic:notEmpty name="replybean">
            	<input  type="hidden" name="replyid" value="<bean:write name="replybean" property="replyid"/>"/>
            	<input  type="hidden" name="replyuserid" value="<bean:write name="replybean" property="replyuserid"/>"/>
            </logic:notEmpty>
            <table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
			        	<tr class=trcolor height="40">
			        		<td class="tdr"  width="12%">工作单流水号</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="serialnumber" name="bean"/>
			                </td>
			            	<td class="tdr"  width="10%">工作单类别</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="sendtype" name="bean"/>
			                </td>
			                <td class="tdr"  width="10%">处理时限</td>
			                <td class="tdl" >
			                	<bean:write property="processterm" name="bean"/>
			                </td>
			        	</tr>
						<tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%">派单时间</td>
			                <td class="tdl" width="18%">
			                	<bean:write  property="sendtime"  name="bean" />
			                </td>
			                <td class="tdr"  width="10%">派单部门</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write name="LOGIN_USER_DEPT_NAME"/>
	                        </td>
                            <td class="tdr"  width="10%">派 单 人</td>
	                        <td class="tdl" width="18%" >
	                        	<bean:write  property="username"  name="bean" />
				          	</td>

			        	</tr>
			        	<tr class=trcolor height="40">
			                <td class="tdr"   width="10%">受理部门</td>
			                <td class="tdl"  width="18%">
			                	<bean:write  property="acceptdeptname"  name="bean" />
							</td>
			                <td class="tdr" width="10%" >受 理 人</td>
			                <td class="tdl"  colspan="3">
			                	<bean:write  property="usernameacce"  name="bean" />
			                </td>
			        	</tr>

	                    

			            <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">任务主题</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write  property="sendtopic"  name="bean" />
			                </td>
			        	</tr>

			             <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">任务要求</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write  property="sendtext"  name="bean" />
			                </td>
			        	</tr>

			            <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">任务附件</td>
			                <td class="tdl"  colspan="5">
							    <%
							      String sendacce = "";
							      SendTaskBean bean = (SendTaskBean) request.getSession().getAttribute("bean");
							      sendacce = bean.getSendacce();
							      if (sendacce == null) {
							        sendacce = "";
							      }
							    %>
							      <apptag:listAttachmentLink fileIdList="<%=sendacce%>"/>
			                </td>
			        	</tr>
                        <tr class=trcolor height="40">
                        	<td class="tdr"   colspan="6"><hr/></td>
                        </tr>
	                    <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%">签收结果</td>
	                        <td class="tdl" >
	                        	<bean:write  property="result"  name="bean" />
	                        </td>
	                        <td class="tdr" width="10%" >签 收 人</td>
		                	<td class="tdl" >
		                	<logic:notEmpty name="endorsebean">
		                		<bean:write name="endorsebean" property="endorseusername"/>
		                	</logic:notEmpty>
		                	</td>
	                        <td class="tdr"  width="10%" >签收时间</td>
	                        <td class="tdl" >
	                        <logic:notEmpty name="endorsebean">
	                        	<bean:write  property="time"  name="endorsebean" />
	                        </logic:notEmpty>
	                        </td>

	                    </tr>
	                    <logic:notEmpty name="endorsebean">

	                    	 <tr class=trcolor height="40">
		                    	<td class="tdr"  width="10%">签收备注</td>
		                        <td class="tdl" colspan="5">
		                        	<bean:write  property="remark"  name="endorsebean" />
		                        </td>
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
	                    <tr class=trcolor height="40">
	                    	<td class="tdr"   colspan="6"><hr/></td>
	                    </tr>


	                    <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%" >回复结果</td>
	                        <td class="tdl"  width="18%">
	                       
	                        	<bean:write  property="replyresult"  name="replybean" />
		                    </td>
		                    <td class="tdr"  width="10%" >回 复 人</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write name="replybean" property="replyusername"/>
		                    </td>
                            <td class="tdr"  width="10%" >回复时间</td>
	                        <td class="tdl" >
	                        	<bean:write  property="replytime"  name="replybean" />
		                   	</td>                           
                        </tr>
                        <tr class=trcolor height="40">
                        	<td class="tdr"  width="10%">工作状态</td>
	                        <td class="tdl" width="18%" colspan="5">
	                        	<bean:write  property="workstate"  name="bean" />
	                        </td>
                        </tr>
	                    <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%" >回复备注</td>
	                        <td class="tdl" colspan="5">
	                        	<bean:write  property="replyremark"  name="replybean" />
	                        </td>
	                    </tr>
	                    <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">回复附件</td>
			                <td class="tdl"  colspan="5">
			                	 <%
								      String replyacce = "";
								      SendTaskBean replybean = (SendTaskBean) request.getSession().getAttribute("replybean");
								      replyacce = replybean.getReplyacce();
								      if (replyacce == null) {
								        replyacce = "";
								      }
								    %>
							      <apptag:listAttachmentLink fileIdList="<%=replyacce%>"/>
			                </td>
			        	</tr>

                        <tr class=trcolor height="40">
	                    	<td class="tdr"   colspan="6"><hr/></td>
	                    </tr>
                        <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%" >验证结果</td>
	                        <td class="tdl" colspan="5">
		                        <select name="validateresult"  style="width:180" class="inputtext">
		                        			<option value="通过验证">通过验证</option>
				                       		<option value="未通过验证">未通过验证</option>
		                      	</select>
	                        </td>
	                    </tr>
	                    <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%" >验证备注</td>
	                        <td class="tdl" colspan="5"><html:textarea property="validateremark"  title="请不要超过256个汉字或者512个英文字符，否则将截断。" style="width:80%"  rows="5" styleClass="textarea"></html:textarea></td>
	                    </tr>
	                    <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">验证附件</td>
			                <td class="tdl"  colspan="5">
			                	 <table id="uploadID" width="70%" border="0" align="left" cellpadding="3" cellspacing="0" >
							          <tr class=trcolor>
							            	<td></td>
							          </tr>
			                     </table>
			                </td>
			        	</tr>
			        </table>
                    <table align="center">
				            <tr>
				            	<td colspan="6" class="tdc">
				                	 <template:formSubmit>
				                	
				                	 		<td>
				                	 	  		<input type="checkbox" id="sel" onclick="checkOrCancel()">全选&nbsp;
				                	 	  	</td>
								          <td>
								          	<input type="hidden" id="maxSeq" value="0">
								            <html:button property="action" styleClass="button" onclick="addRowMod()">添加附件</html:button>
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
								            <input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
								          </td>
							        </template:formSubmit>
				                </td>
				            </tr>
			        </table>
            </html:form>

    </logic:equal>

	<!--显示已验证的派单信息列表-->
    <logic:equal  name="type" scope="session" value="1">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<br>
		<template:titile value="验证派单一览表"/>
        <display:table    name="sessionScope.lvali" requestURI="${ctx}/ValidateAction.do?method=doQuery"  id="currentRowObject"  pagesize="20">
        <%
		    	BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
		    	String id = "";
		    	String serialnumber = "";
		    	String sendtopic = "";
		    	String subid = "";
		    	 String titleTopic = "";
			    String processterm = "";
		    	if(object != null) {
		    		id = (String) object.get("id");
		    		serialnumber = (String) object.get("serialnumber");
		    		
		    		titleTopic = (String)object.get("sendtopic");
			    	if(titleTopic != null && titleTopic.length() > 18) {
			    		sendtopic = titleTopic.substring(0,18) + "...";
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
        			<a href="javascript:toGetFormVali('<%=id%>','<%=subid %>')"><%=serialnumber%></a>
        		<% } %>
				</display:column>	
			<display:column media="html" title="主题" sortable="true" >			
				<a href="javascript:toGetFormVali('<%=id%>','<%=subid %>')" title="<%=titleTopic %>"><%=sendtopic%></a>
			</display:column>
         	<display:column property="sendtype" title="类型" sortable="true" maxLength="5" style="width:80px"/>            
            <display:column property="senddeptname" title="派单单位"  sortable="true" maxLength="8" style="width:100px"/>
            <display:column  media="html" title="处理期限" sortable="true" style="width:80px"> 
            	<%if(processterm.indexOf('-') != -1) {%>
            		<font color="red" ><%=processterm %></font>
            	<%} else {%>
            		<%=processterm %>
            	<%} %>
            </display:column>
            <display:column property="sendusername" title="验证人" sortable="true" maxLength="4" style="width:60px"/>     
		</display:table>
        <html:link action="/ValidateAction.do?method=exportValidateResult">导出为Excel文件</html:link>
	</logic:equal>


<!--显示派单验证的详细信息-->
    <logic:equal  name="type" scope="session" value="10">
		<template:titile value="派单验证详细信息"/>
               <table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
			        	<tr class=trcolor height="40" >
			        		<td class="tdr"  width="12%">工作单流水号</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="serialnumber" name="bean"/>
			                </td>
			            	<td class="tdr"  width="10%">工作单类别</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="sendtype" name="bean"/>
			                </td>
			                <td class="tdr"  width="10%">处理时限</td>
			                <td class="tdl" >
			                	<bean:write property="processterm" name="bean"/>
				            </td>
			        	</tr>
			        	
			        	<tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%">派单时间</td>
			                <td class="tdl" width="18%">
			                	 <bean:write property="sendtime" name="bean"/>
			               	</td>
			                <td class="tdr"  width="10%">派单部门</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write name="LOGIN_USER_DEPT_NAME"/>
	                        </td>
	                       <td class="tdr"  width="10%">派 单 人</td>
	                        <td class="tdl" >
	                        	<bean:write property="username" name="bean"/>
				            </td>
			        	</tr>

			        	<tr class=trcolor height="40">
			                <td class="tdr"   width="10%">受理部门</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="acceptdeptname" name="bean"/>
				            </td>
							<td class="tdr" width="10%" >受 理 人</td>
			                <td class="tdl" colspan="3">
			                	<bean:write property="usernameacce" name="bean"/>
				           </td>
			        	</tr>	                    

			            <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">任务主题</td>
			                <td class="tdl"  colspan="5" >
			                	<bean:write property="sendtopic" name="bean"/>
			                </td>
			        	</tr>

			             <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">任务要求</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write property="sendtext" name="bean"/>
			               	</td>
			        	</tr>

			            <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">任务附件</td>
			                <td class="tdl"  colspan="5">
							    <%
							      String sendacce = "";
							      SendTaskBean bean = (SendTaskBean) request.getSession().getAttribute("bean");
							      sendacce = bean.getSendacce();
							      if (sendacce == null) {
							        sendacce = "";
							      }
							    %>
							      <apptag:listAttachmentLink fileIdList="<%=sendacce%>"/>
			                </td>
			        	</tr>
                        <tr class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                        </tr>
	                    <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%">签收结果</td>
	                        <td class="tdl">
	                        	<bean:write property="result" name="bean"/>
	                        </td>
	                        <td class="tdr" width="10%" >签 收 人</td>
		                	<td class="tdl" ><bean:write name="endorsebean" property="endorseusername"/></td>
	                        <td class="tdr"  width="10%" >签收时间</td>
	                        <td class="tdl">
	                        	<bean:write property="time" name="endorsebean"/>
	                        </td>

	                    </tr>
	                    <logic:notEmpty name="endorsebean">

	                    	 <tr class=trcolor height="40">
		                    	<td class="tdr"  width="10%">签收备注</td>
		                        <td class="tdl"  colspan="5">
		                        	<bean:write property="remark" name="endorsebean"/>	
		                        </td>
		                     </tr>
	                         <tr class=trcolor height="40">
		                    	<td class="tdr"  width="10%">签收附件</td>
		                        <td class="tdl"  colspan="5">
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
	                    <tr class=trcolor >
	                    	<td class="tdr"   colspan="6"><hr/></td>
	                    </tr>


	                    <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%" >回复结果</td>
	                        <td class="tdl" >
	                        	<bean:write property="replyresult" name="replybean"/>
	                        </td>
	                        <td class="tdr"  width="10%" >回 复 人</td>
	                        <td class="tdl" >
	                        	<bean:write name="replybean" property="replyusername"/>
		                    </td>
                            <td class="tdr"  width="10%" >回复时间</td>
	                        <td class="tdl" >
	                        	<bean:write property="replytime" name="replybean"/>
	                        </td>
                        </tr>
	                    <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%" >回复备注</td>
	                        <td class="tdl"  colspan="5">
	                        	<bean:write property="replyremark" name="replybean"/>
	                        </td>
	                    </tr>
	                    <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">回复附件</td>
			                <td class="tdl"  colspan="5">
			                	 <%
								      String replyacce = "";
								      SendTaskBean replybean = (SendTaskBean) request.getSession().getAttribute("replybean");
								      replyacce = replybean.getReplyacce();
								      if (replyacce == null) {
								        replyacce = "";
								      }
								    %>
							      <apptag:listAttachmentLink fileIdList="<%=replyacce%>"/>
			                </td>
			        	</tr>

                        <tr class=trcolor height="40">
	                    	<td class="tdr"   colspan="6"><hr/></td>
	                    </tr>
                        <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%" >验证结果</td>
	                        <td class="tdl" >
	                        	<bean:write property="validateresult" name="valibean"/>
		                    </td>
                            <td class="tdr"  width="10%" >验证时间</td>
	                        <td class="tdl">
	                        	<bean:write property="validatetime" name="valibean"/>
	                        </td>
                            <td class="tdr"  width="10%">工作状态</td>
	                        <td class="tdl">
	                        	<bean:write property="workstate" name="bean"/>
	                        </td>
                        </tr>
	                    <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%" >验证备注</td>
	                        <td class="tdl" colspan="5">
	                        	<bean:write property="validateremark" name="valibean"/>
	                        </td>
	                    </tr>
	                    <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">验证附件</td>
			                <td class="tdl"  colspan="5">
			                	  <%
								      String valiacce = "";
								      SendTaskBean valibean = (SendTaskBean) request.getSession().getAttribute("valibean");
								      valiacce = valibean.getValidateacce();
								      if (valiacce == null) {
								        valiacce = "";
								      }
								    %>
							      <apptag:listAttachmentLink fileIdList="<%=valiacce%>"/>
			                </td>
			        	</tr>
			        </table>
                <p align="center"><input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"></p>
    
    			<!-- oneInfoGoBack() -->
    </logic:equal>


	 <!--条件查询显示-->
     <logic:equal value="3" name="type"scope="session" >
	      	<br />
	        <template:titile value="按条件查找任务派单"/>
	        <html:form action="/ValidateAction?method=doQuery"   styleId="queryForm2" >
            	<template:formTable namewidth="100" contentwidth="250">
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
                     <template:formTr name="验证结果">
                      	<select name="validateresult"  style="width:180" class="inputtext">
                        			<option value="">不限</option>
		                       		<option value="通过验证">通过验证</option>
                                    <option value="未通过验证">未通过验证</option>
                      	</select>
				    </template:formTr>                   
                    <template:formTr name="验证开始时间">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='日期'  readonly="readonly" ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formTr name="验证结束时间">
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



</body>
</html>
