<%@include file="/common/header.jsp"%>

<%@ page import="com.cabletech.sendtask.beans.*;" %>

<html>
<head>
<title>
sendtask
</title>
<script type="" language="javascript">
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
    alert("��֤��ע����̫�࣡���ܳ���510������");
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
				alert("��ѡ��Ҫ�ϴ��ĸ�����·����\n���û��Ҫ�ϴ��ĸ�����ɾ��!");
				pathText.focus();
				return false;
			}
		}
}
 //��ʾһ���ɵ���ϸ��Ϣ
 function toGetForm(id,subid,loginUserid, userid){
	//if(loginUserid != userid) {
	//	alert("�Բ�����������ָ�������˲����������Բ�����֤��");
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

 <!--��ʾ����֤�ɵ���Ϣ-->
    <logic:equal  name="type" scope="session" value="2">
     <%
    		// ȡ�ô���֤����ͳ����Ϣ
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
			����֤�ɵ���Ϣ
			<font face="����" size="2" color="red" >
			(���ţ�<%=deptnum %>��; ���ˣ�
			<html:link action="ValidateAction.do?method=showTaskToValidate&loginuserflg=1"><%=usernum %>��</html:link>)</font>
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
        		<display:column media="html" title="��ˮ��" sortable="true" style="width:90px">        		
				<% 
        			if(serialnumber != null ) {
        		%>       		
        			<a href="javascript:toGetForm('<%=id%>','<%=subid%>')"><%=serialnumber%></a>
        		<% } %>
				</display:column>	
			<display:column media="html" title="����" sortable="true" >			
				<a href="javascript:toGetForm('<%=id%>','<%=subid%>')" title="<%=titleTopic %>"><%=sendtopic%></a>
			</display:column>
        	<display:column property="sendtype" title="����"  sortable="true" maxLength="5"  style="width:80px"/>        	
            <display:column property="senddeptname" title="�ɵ���λ"  sortable="true" maxLength="8"  style="width:100px"/>
            <display:column  media="html" title="��������" sortable="true" style="width:80px"> 
            	<%if("null".equals(processterm)) {%>
            		
            	<%} else if(processterm.indexOf('-') != -1) { %>
            		<font color="red" ><%=processterm %></font>
            	<%} else {%>
            		<%=processterm %>
            	<%} %>
            </display:column>            
            <display:column property="sendusername" title="��֤��"  sortable="true" maxLength="4" style="width:60px"/>
		</display:table>
	</logic:equal>


<!--��ʾ�ɵ���֤����ϸ��Ϣ,������֤-->
    <logic:equal  name="type" scope="session" value="20">
    	<%
    		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    		String loginUserName = userinfo.getUserName(); 
    	
    	 %>
		<template:titile value="�ɵ���֤"/>
        <html:form action="/ValidateAction.do?method=validateTask" styleId="addApplyForm" onsubmit="return validate(this);" enctype="multipart/form-data">
        	<html:hidden property="id" name="bean"/>
        	<html:hidden property="subtaskid" name="bean"/>
        	<logic:notEmpty name="replybean">
            	<input  type="hidden" name="replyid" value="<bean:write name="replybean" property="replyid"/>"/>
            	<input  type="hidden" name="replyuserid" value="<bean:write name="replybean" property="replyuserid"/>"/>
            </logic:notEmpty>
            <table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
			        	<tr class=trcolor height="40">
			        		<td class="tdr"  width="12%">��������ˮ��</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="serialnumber" name="bean"/>
			                </td>
			            	<td class="tdr"  width="10%">���������</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="sendtype" name="bean"/>
			                </td>
			                <td class="tdr"  width="10%">����ʱ��</td>
			                <td class="tdl" >
			                	<bean:write property="processterm" name="bean"/>
			                </td>
			        	</tr>
						<tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%">�ɵ�ʱ��</td>
			                <td class="tdl" width="18%">
			                	<bean:write  property="sendtime"  name="bean" />
			                </td>
			                <td class="tdr"  width="10%">�ɵ�����</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write name="LOGIN_USER_DEPT_NAME"/>
	                        </td>
                            <td class="tdr"  width="10%">�� �� ��</td>
	                        <td class="tdl" width="18%" >
	                        	<bean:write  property="username"  name="bean" />
				          	</td>

			        	</tr>
			        	<tr class=trcolor height="40">
			                <td class="tdr"   width="10%">������</td>
			                <td class="tdl"  width="18%">
			                	<bean:write  property="acceptdeptname"  name="bean" />
							</td>
			                <td class="tdr" width="10%" >�� �� ��</td>
			                <td class="tdl"  colspan="3">
			                	<bean:write  property="usernameacce"  name="bean" />
			                </td>
			        	</tr>

	                    

			            <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">��������</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write  property="sendtopic"  name="bean" />
			                </td>
			        	</tr>

			             <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">����Ҫ��</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write  property="sendtext"  name="bean" />
			                </td>
			        	</tr>

			            <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">���񸽼�</td>
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
	                    	<td class="tdr"  width="10%">ǩ�ս��</td>
	                        <td class="tdl" >
	                        	<bean:write  property="result"  name="bean" />
	                        </td>
	                        <td class="tdr" width="10%" >ǩ �� ��</td>
		                	<td class="tdl" >
		                	<logic:notEmpty name="endorsebean">
		                		<bean:write name="endorsebean" property="endorseusername"/>
		                	</logic:notEmpty>
		                	</td>
	                        <td class="tdr"  width="10%" >ǩ��ʱ��</td>
	                        <td class="tdl" >
	                        <logic:notEmpty name="endorsebean">
	                        	<bean:write  property="time"  name="endorsebean" />
	                        </logic:notEmpty>
	                        </td>

	                    </tr>
	                    <logic:notEmpty name="endorsebean">

	                    	 <tr class=trcolor height="40">
		                    	<td class="tdr"  width="10%">ǩ�ձ�ע</td>
		                        <td class="tdl" colspan="5">
		                        	<bean:write  property="remark"  name="endorsebean" />
		                        </td>
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
	                    <tr class=trcolor height="40">
	                    	<td class="tdr"   colspan="6"><hr/></td>
	                    </tr>


	                    <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%" >�ظ����</td>
	                        <td class="tdl"  width="18%">
	                       
	                        	<bean:write  property="replyresult"  name="replybean" />
		                    </td>
		                    <td class="tdr"  width="10%" >�� �� ��</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write name="replybean" property="replyusername"/>
		                    </td>
                            <td class="tdr"  width="10%" >�ظ�ʱ��</td>
	                        <td class="tdl" >
	                        	<bean:write  property="replytime"  name="replybean" />
		                   	</td>                           
                        </tr>
                        <tr class=trcolor height="40">
                        	<td class="tdr"  width="10%">����״̬</td>
	                        <td class="tdl" width="18%" colspan="5">
	                        	<bean:write  property="workstate"  name="bean" />
	                        </td>
                        </tr>
	                    <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%" >�ظ���ע</td>
	                        <td class="tdl" colspan="5">
	                        	<bean:write  property="replyremark"  name="replybean" />
	                        </td>
	                    </tr>
	                    <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">�ظ�����</td>
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
	                    	<td class="tdr"  width="10%" >��֤���</td>
	                        <td class="tdl" colspan="5">
		                        <select name="validateresult"  style="width:180" class="inputtext">
		                        			<option value="ͨ����֤">ͨ����֤</option>
				                       		<option value="δͨ����֤">δͨ����֤</option>
		                      	</select>
	                        </td>
	                    </tr>
	                    <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%" >��֤��ע</td>
	                        <td class="tdl" colspan="5"><html:textarea property="validateremark"  title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�" style="width:80%"  rows="5" styleClass="textarea"></html:textarea></td>
	                    </tr>
	                    <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">��֤����</td>
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
				                	 	  		<input type="checkbox" id="sel" onclick="checkOrCancel()">ȫѡ&nbsp;
				                	 	  	</td>
								          <td>
								          	<input type="hidden" id="maxSeq" value="0">
								            <html:button property="action" styleClass="button" onclick="addRowMod()">��Ӹ���</html:button>
								          </td>
								          <td>
								          	<html:button property="action" styleClass="button" onclick="delRow()">ɾ��ѡ��</html:button>
								          </td>
								          <td>
								            <html:submit styleClass="button">�ύ</html:submit>
								          </td>
								          <td>
								            <html:reset styleClass="button">����</html:reset>
								          </td>
								          <td>
								            <input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
								          </td>
							        </template:formSubmit>
				                </td>
				            </tr>
			        </table>
            </html:form>

    </logic:equal>

	<!--��ʾ����֤���ɵ���Ϣ�б�-->
    <logic:equal  name="type" scope="session" value="1">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<br>
		<template:titile value="��֤�ɵ�һ����"/>
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
        		<display:column media="html" title="��ˮ��" sortable="true" style="width:90px">        		
				<% 
        			if(serialnumber != null ) {
        		%>       		
        			<a href="javascript:toGetFormVali('<%=id%>','<%=subid %>')"><%=serialnumber%></a>
        		<% } %>
				</display:column>	
			<display:column media="html" title="����" sortable="true" >			
				<a href="javascript:toGetFormVali('<%=id%>','<%=subid %>')" title="<%=titleTopic %>"><%=sendtopic%></a>
			</display:column>
         	<display:column property="sendtype" title="����" sortable="true" maxLength="5" style="width:80px"/>            
            <display:column property="senddeptname" title="�ɵ���λ"  sortable="true" maxLength="8" style="width:100px"/>
            <display:column  media="html" title="��������" sortable="true" style="width:80px"> 
            	<%if(processterm.indexOf('-') != -1) {%>
            		<font color="red" ><%=processterm %></font>
            	<%} else {%>
            		<%=processterm %>
            	<%} %>
            </display:column>
            <display:column property="sendusername" title="��֤��" sortable="true" maxLength="4" style="width:60px"/>     
		</display:table>
        <html:link action="/ValidateAction.do?method=exportValidateResult">����ΪExcel�ļ�</html:link>
	</logic:equal>


<!--��ʾ�ɵ���֤����ϸ��Ϣ-->
    <logic:equal  name="type" scope="session" value="10">
		<template:titile value="�ɵ���֤��ϸ��Ϣ"/>
               <table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
			        	<tr class=trcolor height="40" >
			        		<td class="tdr"  width="12%">��������ˮ��</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="serialnumber" name="bean"/>
			                </td>
			            	<td class="tdr"  width="10%">���������</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="sendtype" name="bean"/>
			                </td>
			                <td class="tdr"  width="10%">����ʱ��</td>
			                <td class="tdl" >
			                	<bean:write property="processterm" name="bean"/>
				            </td>
			        	</tr>
			        	
			        	<tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%">�ɵ�ʱ��</td>
			                <td class="tdl" width="18%">
			                	 <bean:write property="sendtime" name="bean"/>
			               	</td>
			                <td class="tdr"  width="10%">�ɵ�����</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write name="LOGIN_USER_DEPT_NAME"/>
	                        </td>
	                       <td class="tdr"  width="10%">�� �� ��</td>
	                        <td class="tdl" >
	                        	<bean:write property="username" name="bean"/>
				            </td>
			        	</tr>

			        	<tr class=trcolor height="40">
			                <td class="tdr"   width="10%">������</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="acceptdeptname" name="bean"/>
				            </td>
							<td class="tdr" width="10%" >�� �� ��</td>
			                <td class="tdl" colspan="3">
			                	<bean:write property="usernameacce" name="bean"/>
				           </td>
			        	</tr>	                    

			            <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">��������</td>
			                <td class="tdl"  colspan="5" >
			                	<bean:write property="sendtopic" name="bean"/>
			                </td>
			        	</tr>

			             <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">����Ҫ��</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write property="sendtext" name="bean"/>
			               	</td>
			        	</tr>

			            <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">���񸽼�</td>
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
	                    	<td class="tdr"  width="10%">ǩ�ս��</td>
	                        <td class="tdl">
	                        	<bean:write property="result" name="bean"/>
	                        </td>
	                        <td class="tdr" width="10%" >ǩ �� ��</td>
		                	<td class="tdl" ><bean:write name="endorsebean" property="endorseusername"/></td>
	                        <td class="tdr"  width="10%" >ǩ��ʱ��</td>
	                        <td class="tdl">
	                        	<bean:write property="time" name="endorsebean"/>
	                        </td>

	                    </tr>
	                    <logic:notEmpty name="endorsebean">

	                    	 <tr class=trcolor height="40">
		                    	<td class="tdr"  width="10%">ǩ�ձ�ע</td>
		                        <td class="tdl"  colspan="5">
		                        	<bean:write property="remark" name="endorsebean"/>	
		                        </td>
		                     </tr>
	                         <tr class=trcolor height="40">
		                    	<td class="tdr"  width="10%">ǩ�ո���</td>
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
	                    	<td class="tdr"  width="10%" >�ظ����</td>
	                        <td class="tdl" >
	                        	<bean:write property="replyresult" name="replybean"/>
	                        </td>
	                        <td class="tdr"  width="10%" >�� �� ��</td>
	                        <td class="tdl" >
	                        	<bean:write name="replybean" property="replyusername"/>
		                    </td>
                            <td class="tdr"  width="10%" >�ظ�ʱ��</td>
	                        <td class="tdl" >
	                        	<bean:write property="replytime" name="replybean"/>
	                        </td>
                        </tr>
	                    <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%" >�ظ���ע</td>
	                        <td class="tdl"  colspan="5">
	                        	<bean:write property="replyremark" name="replybean"/>
	                        </td>
	                    </tr>
	                    <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">�ظ�����</td>
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
	                    	<td class="tdr"  width="10%" >��֤���</td>
	                        <td class="tdl" >
	                        	<bean:write property="validateresult" name="valibean"/>
		                    </td>
                            <td class="tdr"  width="10%" >��֤ʱ��</td>
	                        <td class="tdl">
	                        	<bean:write property="validatetime" name="valibean"/>
	                        </td>
                            <td class="tdr"  width="10%">����״̬</td>
	                        <td class="tdl">
	                        	<bean:write property="workstate" name="bean"/>
	                        </td>
                        </tr>
	                    <tr class=trcolor height="40">
	                    	<td class="tdr"  width="10%" >��֤��ע</td>
	                        <td class="tdl" colspan="5">
	                        	<bean:write property="validateremark" name="valibean"/>
	                        </td>
	                    </tr>
	                    <tr class=trcolor height="40">
			            	<td class="tdr"  width="10%">��֤����</td>
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
                <p align="center"><input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"></p>
    
    			<!-- oneInfoGoBack() -->
    </logic:equal>


	 <!--������ѯ��ʾ-->
     <logic:equal value="3" name="type"scope="session" >
	      	<br />
	        <template:titile value="���������������ɵ�"/>
	        <html:form action="/ValidateAction?method=doQuery"   styleId="queryForm2" >
            	<template:formTable namewidth="100" contentwidth="250">
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
                     <template:formTr name="��֤���">
                      	<select name="validateresult"  style="width:180" class="inputtext">
                        			<option value="">����</option>
		                       		<option value="ͨ����֤">ͨ����֤</option>
                                    <option value="δͨ����֤">δͨ����֤</option>
                      	</select>
				    </template:formTr>                   
                    <template:formTr name="��֤��ʼʱ��">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='����'  readonly="readonly" ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formTr name="��֤����ʱ��">
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



</body>
</html>
