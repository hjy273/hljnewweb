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
        b1.value = "ɾ��";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");


        cell1.appendChild(t1);//����
        cell2.appendChild(b1);
	}

 //��ʾһ���ɵ���ϸ��Ϣ
 function toGetForm(id,subid){
//	if(loginUserid != acceptuserid) {
//		alert("�Բ�����������ָ�������˲����������Բ���ǩ�գ�");
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
function validate(form){
  if(valCharLength(form.remark.value)>1020){
    alert("ǩ�ձ�ע����̫�࣡���ܳ���510������");
    return false;
  }
}
 //ѡ������
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}
	
	function oneInfoGoBack() {
		var url="EndorseAction.do?method=doQueryAfterMod";
		window.location.href=url;
	}

//��̬�ϴ�����
		function addRowMod() {
			var maxSeq = parseInt(document.getElementById('maxSeq').value);
			maxSeq ++;//�Լ�
			var tableBody = document.getElementsByTagName("tbody")[1];
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
	<!--��ʾ��ǩ�յ��ɵ���Ϣ-->
    <logic:equal  name="type" scope="session" value="1">
<!--%@include file="/common/listhander.jsp"%-->
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<br>
		<template:titile value="ǩ���ɵ�һ����"/>

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
        	<display:column media="html" title="��ˮ��" sortable="true" style="width:90px">        		
				<% 
        			if(serialnumber != null ) {
        		%>       		
        			<a href="javascript:toGetFormEndorse('<%=id%>', '<%=subid%>')"><%=serialnumber%></a>
        		<% } %>
			</display:column>	
			<display:column media="html" title="����" sortable="true" >			
				<a href="javascript:toGetFormEndorse('<%=id%>', '<%=subid%>')" title="<%=titleTopic %>"><%=sendtopic%></a>
			</display:column>
            <display:column property="sendtype" title="����" style="width:80px" sortable="true" maxLength="4"/>
            <display:column property="senddeptname" title="�ɵ���λ" style="width:100px" sortable="true" maxLength="8"/>
            <display:column  media="html" title="��������" sortable="true" style="width:80px"> 
            	<%if(processterm.indexOf('-') != -1) {%>
            		<font color="red" ><%=processterm %></font>
            	<%} else {%>
            		<%=processterm %>
            	<%} %>
            </display:column>
            <display:column property="usernameacce" title="������" style="width:60px" sortable="true" maxLength="4"/>           
		</display:table>
		<logic:notEmpty name="endorselist">
        	<html:link action="/EndorseAction.do?method=exportendorseResult">����ΪExcel�ļ�</html:link>
        </logic:notEmpty>
	</logic:equal>


    <!--�Ѿ�ǩ�յ��ɵ���ϸ��Ϣ-->
    <logic:equal  name="type" scope="session" value="10">
      	<br>
		<template:titile value="�ɵ�ǩ����ϸ��Ϣ"/>
		<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
		        	<tr class=trcolor height="40">
		            	<td class="tdr"  width="12%">��������ˮ��</td>
		                <td class="tdl"  width="18%">
		                	<bean:write name="bean" property="serialnumber"/>
		                </td>
		                <td class="tdr"  width="10%">���������</td>
		                <td class="tdl"  width="18%" colspan="3">
                        	<bean:write name="bean" property="sendtype"/>
			            </td>
		        	</tr>
		        	<tr class=trcolor height="40" >
                    	<td class="tdr"  width="10%">�ɵ�ʱ��</td>
		                <td class="tdl" width="18%"><bean:write name="bean" property="sendtime"/>  </td>
		                <td class="tdr"  width="10%">�ɵ�����</td>
                        <td class="tdl" width="18%"><bean:write name="bean" property="senddeptname"/> </td>
                        <td class="tdr"  width="10%">�� �� ��</td>
                        <td class="tdl"  ><bean:write name="bean" property="username"/>
                    	</td>

		        	</tr>

		        	<tr class=trcolor height="40" >
		                <td class="tdr"   width="10%">������</td>
		                <td class="tdl"  width="18%"><bean:write name="LOGIN_USER" property="deptName"/></td>

		                <td class="tdr" width="10%" >�� �� ��</td>
		                <td class="tdl" ><bean:write name="bean" property="usernameacce"/>
		                <td class="tdr" width="10%" >ǩ �� ��</td>
		                <td class="tdl" >
		                	 <logic:notEmpty name="endorsebean">
		                		<bean:write name="endorsebean" property="endorseusername"/>
		                	</logic:notEmpty>
			         	</td>
		        	</tr>



		            <tr class=trcolor height="40" >
		            	<td class="tdr"  width="10%">��������</td>
		                <td class="tdl"  colspan="5" ><bean:write name="bean" property="sendtopic"/> </td>
		        	</tr>

		             <tr class=trcolor height="40" >
		            	<td class="tdr"  width="10%">����˵��</td>
		                <td class="tdl" colspan="5"><div><bean:write name="bean" property="sendtext"/></div></td>
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
                        <td class="tdl" > <bean:write name="bean" property="result"/></td>
                        <td class="tdr"  width="10%">ǩ��ʱ��</td>
                        <td class="tdl" >
                        <logic:notEmpty name="endorsebean">
                        <bean:write name="endorsebean" property="time"/>
                         </logic:notEmpty>
                        </td>                       
                        <td class="tdr"  width="10%">����״̬</td>
                        <td class="tdl" ><bean:write name="bean" property="workstate"/></td>
                    </tr>
                    <logic:notEmpty name="endorsebean">
                    	<tr class=trcolor height="40" >
			            <td class="tdr"   colspan="6"><hr/></td>
                        </tr>
                    	 <tr class=trcolor>
	                    	<td class="tdr"  width="10%">ǩ�ձ�ע</td>
	                        <td class="tdl" colspan="5"><div><bean:write name="endorsebean" property="remark"/></div></td>
	                     </tr>
                         <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%">ǩ�ո���</td>
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
                <p align="center"><input type="button" value="����" class="button"  onclick="javascript:history.back();"></p>
    </logic:equal>




  <!--��ʾ��ǩ���ɵ���Ϣ-->
    <logic:equal  name="type" scope="session" value="2">
    	<!--%@include file="/common/listhander.jsp"%-->
    	 <%	
    		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    		String loginUserId = userinfo.getUserID();
    		// ȡ�ô�ǩ�յ���ͳ����Ϣ
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
			��ǩ���ɵ���Ϣ
			<font face="����" size="2" color="red" >
			(���ţ�<%=deptnum %>��; ���ˣ�
			<html:link action="EndorseAction.do?method=showLoginUserTaskToEndorse"><%=usernum %>��</html:link></a>)</font>
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
        	<display:column media="html" title="��ˮ��"  style="width:90px" sortable="true">
        		<% 
        			if(serialnumber != null ) {
        		%>       		
        		<a href="javascript:toGetForm('<%=id%>', '<%=subid %>')"><%=serialnumber%></a>
        		<% } %>
        	</display:column>
        	<display:column media="html" title="����" sortable="true">
        		<a href="javascript:toGetForm('<%=id %>', '<%=subid %>')" title="<%=titleTopic %>"><%=sendtopic%></a>
        	</display:column>
        	<display:column property="sendtype" title="����"  style="width:80px" maxLength="5" sortable="true"/>
        	<display:column property="senddeptname" title="�ɵ���λ" style="width:100px" maxLength="8" sortable="true"/>
        	 <display:column  media="html" title="��������" sortable="true" style="width:80px"> 
            	<%if(processterm.indexOf('-') != -1) {%>
            		<font color="red" ><%=processterm %></font>
            	<%} else {%>
            		<%=processterm %>
            	<%} %>
            </display:column>
            <display:column media="html" title="������" sortable="true" style="width:60px" >
            <%if(loginUserId.equals(acceptuserid)) {%>
            	<font color="#CA7020"><%=usernameacce %></font>
            <%} else {%>
            	<%=usernameacce %>
            <%} %>
            </display:column>
            
		</display:table>
	</logic:equal>

 <!--��ʾһ����ǩ���ɵ���ϸ��Ϣҳ��-->
    <logic:equal  name="type" scope="session" value="20">
    	<%
    		//UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    		//String loginUserName = userinfo.getUserName(); 
    	
    	 %>
      	<br>
		<template:titile value="ǩ���ɵ�"/>
        <html:form action="/EndorseAction.do?method=endorseTask" styleId="addApplyForm" onsubmit="return validate(this)" enctype="multipart/form-data">
			<html:hidden property="id" name="bean"/>
			<html:hidden property="subtaskid" name="bean"/>
            <html:hidden property="senduserid" name="bean"/>
            <input name="sendtopic" type="hidden" value="<bean:write property="sendtopic" name="bean" />"/>
          	<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
		        	<tr class=trcolor heigth="40">
		                <td class="tdr"  width="10%">������ˮ��</td>
		                <td class="tdl"  width="18%">
		                	<bean:write property="serialnumber" name="bean"/>
		                </td>
		                <td class="tdr"  width="10%">�������</td>
		                <td class="tdl"  width="18%" colspan="3">
		                	<bean:write property="sendtype" name="bean"/>
		                </td>
		        	</tr>

		        	<tr class=trcolor heigth="40">
		                <td class="tdr"   width="10%">������</td>
		                <td class="tdl"  width="18%">
		                	<bean:write name="LOGIN_USER" property="deptName"/>
		                </td>
		                <td class="tdr" width="10%" >�� �� ��</td>
		                <td class="tdl" width="18%" colspan="3" >
		                	<bean:write property="usernameacce" name="bean"/>
		                </td>
                         
		        	</tr>

                    <tr class=trcolor heigth="40">
                    	<td class="tdr"  width="10%">�ɵ�ʱ��</td>
		                <td class="tdl" width="18%">
		                	<bean:write property="sendtime" name="bean"/>
		                </td>
		                <td class="tdr"  width="10%">�ɵ�����</td>
		                <td class="tdl" width="18%">
		                	<bean:write property="senddeptname" name="bean"/>
		                </td>
		                <td class="tdr"  width="10%">�� �� ��</td>
                     	<td class="tdl" >
                       		<bean:write property="username" name="bean"/>
						</td>
		        	</tr>

		            <tr class=trcolor heigth="40">
		            	<td class="tdr"  width="10%">��������</td>
		                <td class="tdl"  colspan="5" >
		                	<bean:write property="sendtopic" name="bean"/>
		                </td>
		        	</tr>

		             <tr class=trcolor heigth="40">
		            	<td class="tdr"  width="10%">����˵��</td>
		                <td class="tdl"   colspan="5">
		                	<bean:write property="sendtext" name="bean"/>
		                </td>
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

		            <tr class=trcolor heigth="40">
		            	<td class="tdr"  width="10%">���񸽼�</td>
		                <td class="tdl"  colspan="5">
							<apptag:upload cssClass="" id="sendTaskId" entityId="${sessionScope.bean.id}" entityType="LP_SENDTASK" state="look"/>
		                </td>
		        	</tr>
                    <tr class=trcolor heigth="40">
                    	<td class="tdr"  width="10%" >ǩ�ս��</td>
                        <td class="tdl" colspan="5">
	                        <select name="result"  style="width:150" class="inputtext">
	                        			<option value="ǩ��">ǩ��</option>
			                       		<option value="��ǩ">��ǩ</option>
	                      	</select>
                        </td>
                    </tr>
                    <tr class=trcolor heigth="40">
                    	<td class="tdr"  width="10%" >ǩ�ձ�ע</td>
                        <td class="tdl"  colspan="5"><html:textarea property="remark" title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�"   style="width:80%"  rows="6" styleClass="textarea"></html:textarea></td>
                    </tr>
                    <tr class=trcolor heigth="40" >
		            	<td class="tdr"  width="10%">ǩ�ո���</td>
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
				                	 	  <input type="checkbox" id="sel" onclick="checkOrCancel()">ȫѡ&nbsp;
				                	 </td>		                	 	 
							          <td>
							          	<input type="hidden" id="maxSeq" value="0">
							            <html:button property="action" styleClass="button" onclick="addRowMod()">��Ӹ���</html:button>
							          </td>
							          <td>
								          	<html:button property="action" styleClass="button" onclick="delRow()">ɾ��ѡ��</html:button>
								          </td> -->
							          <td>
							            <html:submit styleClass="button">ǩ��</html:submit>
							          </td>
							          <td>
							            <html:reset styleClass="button">����</html:reset>
							          </td>
							          <td>
							            <input type="button" value="����" class="button"  onclick="javascript:history.go(-1);">
							          </td>
			                </td>
			            </tr>
		        </table>
         </html:form>
    </logic:equal>

      <!--������ѯ��ʾ-->
     <logic:equal value="3" name="type"scope="session" >
	      	<br />
	        <template:titile value="���������������ɵ�"/>
	        <html:form action="/EndorseAction?method=doQuery"   styleId="queryForm2" >
            	<template:formTable namewidth="100" contentwidth="250">
            		<template:formTr name="�ɵ�����">
				      <html:text property="sendtopic"  styleClass="inputtext" style="width:180" />
				    </template:formTr>
				    <template:formTr name="���������">
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
                     <template:formTr name="ǩ�ս��">
                      	<select name="result"  style="width:180" class="inputtext">
                        			<option value="">����</option>
		                       		<option value="ǩ��">ǩ��</option>
		                       		<option value="��ǩ">��ǩ</option>
                      	</select>
				    </template:formTr>
                    <template:formTr name="ǩ�տ�ʼʱ��">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" style="width:150" />
	                </template:formTr>
	                <template:formTr name="ǩ�ս���ʱ��">
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
</body>
</html>
