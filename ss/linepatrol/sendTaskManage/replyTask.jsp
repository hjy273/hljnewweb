<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.linepatrol.sendtask.beans.*,org.apache.commons.beanutils.DynaBean;" %>

<html>
<head>
<title>
sendtask
</title>
<script type="" language="javascript">
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
    //��ʼ������
    function initArray(deptid,userid,username){
    	rowArr[0] = deptid;
        rowArr[1] = userid;
        rowArr[2] = username;
        infoArr[infoArr.length] = rowArr;
        rowArr = new Array();
        return true;
    }

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
  if(valCharLength(form.replyremark.value)>1020){
    alert("������ע����̫�࣡���ܳ���510������");
    return false;
  }
}

 //��ʾһ���ɵ���ϸ��Ϣ
 function toGetForm(id,subid){
//	if(loginUserid != acceptuserid) {
//		alert("�Բ�����������ָ�������˲����������Բ��ܷ�����");
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
    //if((workstate !="������" || workstate == "����֤")){
	 	var url = "${ctx}/ReplyAction.do?method=showOneToUp&id=" + id+"&subid="+subid;
	    window.location.href=url;
  	//}else
   // alert("���ɵ��浵,�����޸�!");
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

		function oneInfoGoBack(){
			var url="ReplyAction.do?method=doQueryAfterMod";
			window.location.href=url;
		}

	function toForward(id, subid) {
		//if(loginUserid != acceptuserid) {
		//	alert("�Բ�����������ָ�������˲����������Բ���ת��");
		//	return;
		//}
		var url = "${ctx}/ReplyAction.do?method=showTaskForward&id=" + id +"&subid="+subid;
    	window.location.href=url;
	}
	
	// �ص������������ҳ��
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


  <!--��ʾ�������ɵ���Ϣ-->
    <logic:equal  name="type" scope="session" value="2">
    	<!--%@include file="/common/listhander.jsp"%-->
    	<% 	
    		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    		String loginUserid= userinfo.getUserID();
    		// ȡ�ô���������ͳ����Ϣ
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
			�������ɵ���Ϣ
			<font face="����" size="2" color="red" >
			(���ţ�<%=deptnum %>��; ���ˣ�
			<html:link action="ReplyAction.do?method=showLoginUserTaskToReply"><%=usernum %>��</html:link>)</font>
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
        	<display:column property="sendtype" title="����" style="width:80px" sortable="true" maxLength="5"/>
        	<display:column property="senddeptname" title="�ɵ���λ" style="width:100px" maxLength="8" sortable="true"/>
            <display:column  media="html" title="��������" sortable="true" style="width:80px"> 
            	<%if(processterm.indexOf('-') != -1) {%>
            		<font color="red" ><%=processterm %></font>
            	<%} else {%>
            		<%=processterm %>
            	<%} %>
            </display:column>
            <display:column media="html" title="������" style="width:60px" sortable="true" >
            	 <%if(loginUserid.equals(acceptuserid)) {%>
            		<font color="#CA7020"><%=usernameacce %></font>
            	<%} else {%>
            		<%=usernameacce %>
            	<%} %>
            </display:column>
            <display:column  media="html" title="����" sortable="true" style="width:40px"> 
                <%if(loginUserid.equals(acceptuserid)) {%>
                	<a href="javascript:toForward('<%=id %>','<%=subid%>')">ת��</a>  
                <%} else { %>
                --
                <%} %>   		
        		
        	</display:column>           
		</display:table>
	</logic:equal>


	<!--�����ɵ�-->
    <logic:equal  name="type" scope="session" value="20">
    <%    	
    	//UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    	//String loginUserName = userinfo.getUserName();  
     %>
      	<br>
      	<logic:equal value="2" scope="session" name="state">
		<template:titile value="�ɵ�����"/>
        <html:form action="/ReplyAction.do?method=replyTask" styleId="addApplyForm" onsubmit="return validate(this)" enctype="multipart/form-data">
        	<html:hidden property="id" name="bean"/>
        	<html:hidden property="subtaskid" name="bean"/>
            <html:hidden property="senduserid" name="bean"/>
			<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
			        	<tr class=trcolor heigth="40">
			        		<td class="tdr"  width="12%">��������ˮ��</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="serialnumber" name="bean" />			                	
			                </td>
			            	<td class="tdr"  width="12%">���������</td>
			                <td class="tdl"  width="18%" colspan="3">
			                	<bean:write property="sendtype" name="bean" />
			                	<input name="sendtype" type="hidden" value="<bean:write property="sendtype" name="bean" />" />
			                </td>
			        	</tr>
			        	
			        	<tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%">�ɵ�ʱ��</td>
			                <td class="tdl" width="18%"> 
			                	<bean:write property="sendtime" name="bean" />
			                	<input name="sendtime" type="hidden" value="<bean:write property="sendtime" name="bean" />" />
			                </td>
			                <td class="tdr"  width="10%">�ɵ�����</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write property="senddeptname" name="bean" />
	                        	<input name="senddeptname" type="hidden" value="<bean:write property="senddeptname" name="bean" />"/>
	                        </td>
	                        <td class="tdr"  width="10%">�� �� ��</td>
	                        <td class="tdl" >
	                        	  <bean:write property="username" name="bean" />
				                  <input name="username" type="hidden" value=" <bean:write property="username" name="bean" />"/>
				            </td>
				            
			        	</tr>

			        	<tr class=trcolor heigth="40">
			                <td class="tdr"   width="10%">������</td>
			                <td class="tdl"  width="18%">
			                	<bean:write name="LOGIN_USER" property="deptName"/>
	                        	<input readonly="readonly" type="hidden"  value="<bean:write name="LOGIN_USER" property="deptName"/>" />
							</td>
			                <td class="tdr" width="10%" >�� �� ��</td>
			                <td class="tdl" >
			                	 <bean:write property="usernameacce" name="bean" />
				                <input name="usernameacce" type="hidden" value="<bean:write property="usernameacce" name="bean" />"/>
				            </td>
				            <td class="tdr" width="10%" >ǩ �� ��</td>
		                	<td class="tdl" >
		                		<logic:notEmpty name="endorsebean">
		                			<bean:write name="endorsebean" property="endorseusername"/>
		                		</logic:notEmpty>
		                	</td>
			        	</tr>

			            <tr class=trcolor heigth="40" >
			            	<td class="tdr"  width="10%">��������</td>
			                <td class="tdl"  colspan="5" >
			                	<bean:write property="sendtopic" name="bean" />
			                	<input name="sendtopic" type="hidden" value="<bean:write property="sendtopic" name="bean" />"/>
			                </td>
			        	</tr>

			             <tr class=trcolor heigth="40">
			            	<td class="tdr"  width="10%">����˵��</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write property="sendtext" name="bean" />
			                	<input name="sendtext"  type="hidden" value="<bean:write property="sendtext" name="bean" />" />
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
							     <apptag:upload cssClass="" id="sendTaskIdAddReply" entityId="${sessionScope.bean.id}" entityType="LP_SENDTASK" state="look"/>
			                </td>
			        	</tr>
                        <tr class=trcolor heigth="40">
                        	<td class="tdr"   colspan="6"><hr/></td>
                        </tr>
	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%">ǩ�ս��</td>
	                        <td class="tdl">
	                        	<bean:write property="result" name="bean" />
	                        	<input name="result" type="hidden" value="<bean:write property="result" name="bean" />"/>
	                        </td>
	                        <td class="tdr"  width="10%">ǩ��ʱ��</td>
	                        <td  class="tdl">
	                        	<logic:notEmpty name="endorsebean">
	                        	<bean:write property="time" name="endorsebean" />
	                        	<input name="time" type="hidden" value="<bean:write property="time" name="bean" />"/>
	                        	</logic:notEmpty>
	                        </td>
	                        <td class="tdr"  width="10%">����״̬</td>
	                        <td class="tdl">
	                        	<bean:write property="workstate" name="bean" />
	                        	<html:hidden property="workstate" name="bean" value=""/>
	                        </td>
	                    </tr>
	                    <logic:notEmpty name="endorsebean">

	                    	 <tr class=trcolor heigth="40">
		                    	<td class="tdr"  width="10%">ǩ�ձ�ע</td>
		                        <td class="tdl" colspan="5">
		                        	<bean:write property="remark" name="endorsebean" />
		                        	<input name="remark" type="hidden"  value="<bean:write property="remark" name="endorsebean" />"/>
		                        </td>
		                     </tr>
	                         <tr class=trcolor heigth="40">
		                    	<td class="tdr"  width="10%">ǩ�ո���</td>
		                        <td class="tdl"  colspan="5">
								    <apptag:upload cssClass="" id="sendTaskendorseIdAddReply" entityId="${sessionScope.endorsebean.id}" entityType="LP_SENDTASKENDORSE" state="look"/>
	                            </td>
		                     </tr>
	                    </logic:notEmpty>
	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"   colspan="6"><hr/></td>
	                    </tr>


	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%" >�������</td>
	                        <td class="tdl" colspan="5">
		                        <select name="replyresult"  style="width:180" class="inputtext">
		                        <option value="δ���">δ���</option>
		                        			<option value="ȫ�����">ȫ�����</option>
				                       		<option value="�������">�������</option>
                                            <option value="�������">�������</option>
		                      	</select>
	                        </td>
	                    </tr>
	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%" >������ע</td>
	                        <td class="tdl"  colspan="5"><html:textarea property="replyremark"  style="width:80%" title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�"    rows="5" styleClass="textarea"></html:textarea></td>
	                    </tr>
	                    <tr class=trcolor heigth="40">
			            	<td class="tdr"  width="10%">��������</td>
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
								            <html:submit styleClass="button">�ύ</html:submit>
								          </td>
								          <td>
								            <html:reset styleClass="button">����</html:reset>
								          </td>
								          <td>
								           <input type="button" value="����" class="button"  onclick="javascript:history.go(-1)">
								          </td>
				                </td>
				            </tr>
			        </table>
                </html:form>
         </logic:equal>
         
       <logic:equal value="1" name="state" scope="session">  
         <template:titile value="�ɵ�����"/>
        	<html:form action="/ReplyAction.do?method=replyTask" styleId="addApplyForm" onsubmit="return validate(this)" enctype="multipart/form-data">
        	<html:hidden property="id" name="bean"/>
        	<html:hidden property="subtaskid" name="bean"/>
            <html:hidden property="senduserid" name="bean"/>
			<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
			        	<tr class=trcolor heigth="40">
			        		<td class="tdr"  width="12%">��������ˮ��</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="serialnumber" name="bean" />
			                </td>
			            	<td class="tdr"  width="10%">�������</td>
			                <td class="tdl"  width="18%" colspan="3">
			                	<bean:write property="sendtype" name="bean" />
			                	<input name="sendtype" type="hidden" value="<bean:write property="sendtype" name="bean" />" />
			                </td>
			        	</tr>
						<tr heigth="40" class=trcolor >
	                    	<td class="tdr"  width="10%">�ɵ�ʱ��</td>
			                <td class="tdl" width="18%"> 
			                	<bean:write property="sendtime" name="bean" />
			                		<input name="sendtime" type="hidden" value="<bean:write property="sendtime" name="bean" />" />
			                </td>
			                <td class="tdr"  width="10%">�ɵ�����</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write property="senddeptname" name="bean" />
	                        		<input name="senddeptname" type="hidden" value="<bean:write property="senddeptname" name="bean" />"/>
	                        </td>
	                        <td class="tdr"  width="10%">�� �� ��</td>
	                        <td class="tdl" width="18%" >
	                        	  <bean:write property="username" name="bean" />
				                 	<input name="username" type="hidden" value=" <bean:write property="username" name="bean" />"/>
				            </td>
			        	</tr>
			        	<tr heigth="40" class=trcolor >
			                <td class="tdr"   width="10%">������</td>
			                <td class="tdl"  width="18%">
			                	<bean:write name="LOGIN_USER" property="deptName"/>
	                        	<input readonly="readonly" type="hidden"  value="<bean:write name="LOGIN_USER" property="deptName"/>" />
							</td>
			                <td class="tdr" width="10%" >�� �� ��</td>
			                <td class="tdl" >
			                	 <bean:write property="usernameacce" name="bean" />
				                 	<input name="usernameacce" type="hidden" value="<bean:write property="usernameacce" name="bean" />"/>
				            </td>
				             <td class="tdr" width="10%" >ǩ �� ��</td>
		                	<td class="tdl" >
		                		<logic:notEmpty name="endorsebean">
		                			<bean:write name="endorsebean" property="endorseusername"/>
		                		</logic:notEmpty>
		                		
		                	</td>
		                	
			        	</tr>

			            <tr heigth="40" class=trcolor >
			            	<td class="tdr"  width="10%">��������</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write property="sendtopic" name="bean" />
			                		<input name="sendtopic"  type="hidden" value="<bean:write property="sendtopic" name="bean" />"/>
			                </td>
			        	</tr>

			             <tr heigth="40" class=trcolor >
			            	<td class="tdr"  width="10%">����˵��</td>
			                <td class="tdl"   colspan="5">
			                	<bean:write property="sendtext" name="bean" />
			                		<input name="sendtext" type="hidden" value="<bean:write property="sendtext" name="bean" />" />
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

			            <tr heigth="40" class=trcolor >
			            	<td class="tdr"  width="10%">���񸽼�</td>
			                <td class="tdl"  colspan="5">
							    <apptag:upload cssClass="" entityId="${sessionScope.bean.id}" entityType="LP_SENDTASK" state="look"/>
			                </td>
			        	</tr>
                        <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                        </tr>
	                    <tr heigth="40" class=trcolor>
	                    	<td class="tdr"  width="10%">ǩ�ս��</td>
	                        <td>
	                        	<bean:write property="result" name="bean" />
	                        		<input name="result" type="hidden" value="<bean:write property="result" name="bean" />"/>
	                        </td>
	                        <td class="tdr"  width="10%">ǩ��ʱ��</td>
	                        <td >
	                        	<logic:notEmpty name="endorsebean">
	                        		<bean:write property="time" name="endorsebean" />
	                        			<input name="time" type="hidden" value="<bean:write property="time" name="bean" />"/>
	                        	</logic:notEmpty>	                        	
	                        </td>
	                        <td class="tdr"  width="10%">����״̬</td>
	                        <td >
	                        	<bean:write property="workstate" name="bean" />
	                        	<html:hidden property="workstate" name="bean" value=""/>
	                        </td>
	                    </tr>
	                    <logic:notEmpty name="endorsebean">

	                    	 <tr heigth="40" class=trcolor>
		                    	<td class="tdr"  width="10%">ǩ�ձ�ע</td>
		                        <td class="tdl"  colspan="5">
		                        	
		                        	<bean:write property="remark" name="endorsebean" />
		                        		<input name="remark" type="hidden"  value="<bean:write property="remark" name="endorsebean" />"/>
		                        </td>
		                     </tr>
	                         <tr  heigth="40" class=trcolor>
		                    	<td class="tdr"  width="10%">ǩ�ո���</td>
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
		                    	<td class="tdr"  width="10%">��֤���</td>
		                        <td class="tdl">
		                        	<%=valiRes %>
		                        </td>
		                        <td class="tdr" width="10%">��֤ʱ��</td>
		                        <td colspan="3" class="tdl">
		                        	<%=valitime %>
		                        </td>
		                    </tr>
						
							<tr class=trcolor heigth="40">
		                    	<td class="tdr"  width="width="10%"">��֤��ע</td>
		                        <td class="tdl" colspan="5">
		                        	<%=valiRe %>
		                        </td>
		                	</tr>
		                
		                	<tr heigth="40" class=trcolor>
		                    	<td class="tdr"  width="width="10%"">��֤����</td>
		                        <td class="tdl"  colspan="5">
		                        	 <apptag:upload cssClass="" entityId="<%=validateid %>" entityType="LP_VALIDATEREPLY" state="look"/>
	                            </td>
		                	</tr>
		                	
		                	<tr heigth="40" class=trcolor >
		                    	<td class="tdr"   colspan="6"><hr/></td>
		                    </tr>
						<%	} %>
						
		                     
	                    
						
	                    <tr heigth="40" class=trcolor>
	                    	<td class="tdr"  width="width="10%"" >�������</td>
	                        <td class="tdl" colspan="5">
		                        <select name="replyresult"  style="width:180" class="inputtext">
		                        <option value="δ���">δ���</option>
		                        			<option value="ȫ�����">ȫ�����</option>
				                       		<option value="�������">�������</option>
                                            <option value="�������">�������</option>
		                      	</select>
	                        </td>
	                    </tr>
	                    <tr heigth="40" class=trcolor>
	                    	<td class="tdr"  width="width="10%"" >������ע</td>
	                        <td class="tdl"  colspan="5"><html:textarea property="replyremark"  style="width:80%" title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�"    rows="3" styleClass="textarea"></html:textarea></td>
	                    </tr>
	                    <tr heigth="40" class=trcolor >
			            	<td class="tdr"  width="10%">��������</td>
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
								            <html:submit styleClass="button">�ύ</html:submit>
								          </td>
								          <td>
								            <html:reset styleClass="button">ȡ��</html:reset>
								          </td>
								          <td>
								            <html:button property="action" styleClass="button" onclick="addGoBackToReply()">����</html:button>
								          </td>
				                </td>
				            </tr>
			        </table>
                </html:form>
             </logic:equal>  
    </logic:equal>

    	<!--��ʾ�ѷ������ɵ���Ϣ�б�-->
    <logic:equal  name="type" scope="session" value="1">
    	<!--%@include file="/common/listhander.jsp"%-->
        <%
    		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    		String loginUserid = userinfo.getUserID();   	
    	 %>
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<br>
		<template:titile value="�����ɵ�һ����"/>
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
        	<display:column  media="html" title="��ˮ��" sortable="true" style="width:90px"> 
        		<% 
        			if(serialnumber != null ) {
        		%>       		
        		<a href="javascript:toGetFormReply('<%=id%>','<%=subid %>')"><%=serialnumber%></a>
        		<% } %>
        	</display:column>
        	<display:column media="html" title="����"  sortable="true" >
        		<a href="javascript:toGetFormReply('<%=id %>','<%=subid %>')" title="<%=titleTopic %>"><%=sendtopic%></a>
        	</display:column>
         	<display:column property="sendtype" title="����" style="width:80px" sortable="true" maxLength="5"/>
            <display:column property="senddeptname" title="�ɵ���λ"  style="width:100px" sortable="true" maxLength="8"/>
            <display:column  media="html" title="��������" sortable="true" style="width:80px"> 
            	<%if(processterm.indexOf('-') != -1) {%>
            		<font color="red" ><%=processterm %></font>
            	<%} else {%>
            		<%=processterm %>
            	<%} %>
            </display:column>
            <display:column property="usernameacce" title="������" style="width:60px" sortable="true" maxLength="4"/>
             <apptag:checkpower thirdmould="110304" ishead="0" >
            	<display:column media="html" title="����" style="width:30px" >
            	<%if(loginUserid.equals(acceptuserid) && !"������".equals(workstate) && "����֤".equals(workstate)) {%>
            		<a href="javascript:toUpForm('<%=id%>','<%=workstate%>','<%=subid %>')">�޸�</a>
            	<%} else {%>
            	--
            	<%} %> 	
                 </display:column>
            </apptag:checkpower>
		</display:table>
		<logic:notEmpty name="lreply">
       		 <html:link action="/ReplyAction.do?method=exportReplyResult">����ΪExcel�ļ�</html:link>
        </logic:notEmpty>
	</logic:equal>

    	<!--��ʾ�ɵ���������ϸ��Ϣ-->
    <logic:equal  name="type" scope="session" value="10">
      	<br>
		<template:titile value="�ɵ�������ϸ��Ϣ"/>

			<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
			        	<tr class=trcolor heigth="40">
			        		<td class="tdr"  width="11%">��ά��ˮ��</td>
			                <td class="tdl"  width="18%">
			                	<bean:write name="bean" property="serialnumber"/>
			                </td>
			            	<td class="tdr"  width="10%">���������</td>
			                <td class="tdl"  width="18%" colspan="3">
			                	<bean:write name="bean" property="sendtype"/>
			                </td>
			        	</tr>
			        	
			        	<tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%">�ɵ�ʱ��</td>
			                <td class="tdl" width="18%">
			                	<bean:write name="bean" property="sendtime"/>
			                </td>
			                <td class="tdr"  width="10%">�ɵ�����</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write name="bean" property="senddeptname"/>
	                        <td class="tdr"  width="10%">�� �� ��</td>
	                        
	                        <td class="tdl"  >
	                        	<bean:write name="bean" property="username"/>
				            </td>
			        	</tr>

			        	<tr class=trcolor heigth="40">
			                <td class="tdr"   width="10%">������</td>
			                <td class="tdl"  width="18%">
			                	<bean:write name="LOGIN_USER" property="deptName"/>
							</td>
			                <td class="tdr" width="10%" >�� �� ��</td>
			                <td class="tdl" colspan="3">
			                	<bean:write name="bean" property="usernameacce"/>
				            </td>
				           
			        	</tr>	                    

			            <tr class=trcolor heigth="40">
			            	<td class="tdr"  width="10%">��������</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write name="bean" property="sendtopic"/>
			                </td>
			        	</tr>

			             <tr class=trcolor heigth="40">
			            	<td class="tdr"  width="10%">����˵��</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write name="bean" property="sendtext"/>
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
							    <apptag:upload cssClass="" entityId="${sessionScope.bean.id}" entityType="LP_SENDTASK" state="look"/>
			                </td>
			        	</tr>
                        <tr class=trcolor heigth="40">
                        	<td class="tdr"   colspan="6"><hr/></td>
                        </tr>
	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%">ǩ�ս��</td>
	                        <td class="tdl" >
	                        	<bean:write name="bean" property="result"/>
	                        </td>
	                        <td class="tdr" width="10%" >ǩ �� ��</td>
		                	<td class="tdl" ><bean:write name="endorsebean" property="endorseusername"/></td>
	                        <td class="tdr"  width="10%" >ǩ��ʱ��</td>
	                        <td  class="tdl" >
	                        	<bean:write name="endorsebean" property="time"/>
	                        </td>

	                    </tr>
	                    <logic:notEmpty name="endorsebean">

	                    	 <tr class=trcolor heigth="40">
		                    	<td class="tdr"  width="10%">ǩ�ձ�ע</td>
		                        <td class="tdl"  colspan="5">
		                        	<bean:write name="endorsebean" property="remark"/>
		                        </td>
		                     </tr>
	                         <tr class=trcolor heigth="40">
		                    	<td class="tdr"  width="10%">ǩ�ո���</td>
		                        <td class="tdl"  colspan="5">
							      <apptag:upload cssClass="" entityId="${sessionScope.endorsebean.id}" entityType="LP_SENDTASKENDORSE" state="look"/>
	                            </td>
		                     </tr>
	                    </logic:notEmpty>
	                    <tr class=trcolor >
	                    	<td class="tdr"   colspan="6"><hr/></td>
	                    </tr>


	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%" >�������</td>
	                        <td class="tdl" >
	                        	<bean:write name="replybean" property="replyresult"/>
	                        </td>
                            <td class="tdr"  width="10%" >������</td>
	                        <td class="tdl" >
	                        	<bean:write name="replybean" property="replyusername"/>
		                    </td>

                            <td class="tdr"  width="10%">����ʱ��</td>
	                        <td class="tdl">
	                        	<bean:write name="replybean" property="replytime"/>
	                        </td>
	                        
                        </tr>
                        <tr class=trcolor heigth="40">
                        	<td class="tdr"  width="10%" >����״̬</td>
	                        <td class="tdl" colspan="5" >
	                        	<bean:write name="bean" property="workstate"/>
		                    </td>
                        </tr>
	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%" >������ע</td>
	                        <td class="tdl" colspan="5">
	                        	<bean:write name="replybean" property="replyremark"/>
	                        </td>
	                    </tr>
	                    <tr class=trcolor >
			            	<td class="tdr"  width="10%">��������</td>
			                <td class="tdl"  colspan="5">
							      <apptag:upload cssClass="" entityId="${sessionScope.replybean.replyid}" entityType="LP_SENDTASKREPLY" state="look"/>
			                </td>
			        	</tr>
			        </table>
                	<p align="center"><input type="button" value="����" class="button"  onclick="javascript:history.go(-1)"></p>
					<!-- oneInfoGoBack() -->
	</logic:equal>

       	<!--��ʾ���޸ĵ��ɵ�������ϸ��Ϣ-->
    <logic:equal  name="type" scope="session" value="4">
      	<br>
		<template:titile value="�޸��ɵ�����"/>
        <html:form action="/ReplyAction.do?method=upReply" styleId="addApplyForm"  onsubmit="return validate(this)" enctype="multipart/form-data">
        	 <html:hidden property="id" name="bean"/>
        	 <html:hidden property="subtaskid" name="bean" />
             <html:hidden property="senduserid"  name="bean"/>
             <html:hidden property="replyacce" name="replybean"/>
             <input  type="hidden" name="replyid" value="<bean:write  property="replyid" name="replybean"/>"/>
             <table width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
			        	<tr class=trcolor heigth="40">
			        		<td class="tdr"  width="12%">��������ˮ��</td>
			                <td class="tdl"  width="18%">
			                	<bean:write property="serialnumber" name="bean"/>
			                </td>
			            	<td class="tdr"  width="10%">���������</td>
			                <td class="tdl"  width="18%" colspan="3">
			                	<bean:write property="sendtype" name="bean"/>
			                	<input name="sendtype" type="hidden" value="<bean:write property="sendtype" name="bean"/>"/>
			                </td>
			        	</tr>
			        	
			        	<tr heigth="40" class=trcolor >
	                    	<td class="tdr"  width="10%">�ɵ�ʱ��</td>
			                <td class="tdl" width="18%">
			                	<bean:write property="sendtime" name="bean"/>
			                		<input name="sendtime" type="hidden" value="<bean:write property="sendtime" name="bean"/>"/>
			                </td>
			                <td class="tdr"  width="10%">�ɵ�����</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write property="senddeptname" name="bean"/>
	                        		<input name="senddeptname" type="hidden" value="<bean:write property="senddeptname" name="bean"/>"/>
	                        </td>
	                        <td class="tdr"  width="10%">�� �� ��</td>
	                        <td class="tdl" >
	                        	<bean:write property="username" name="bean"/>
				            	<input name="username"  type="hidden" value="<bean:write property="username" name="bean"/>"/>
				           	</td>
			        	</tr>

			        	<tr heigth="40" class=trcolor >
			                <td class="tdr"   width="10%">������</td>
			                <td class="tdl"  width="18%">
			                	<bean:write name="LOGIN_USER" property="deptName"/>
	                        	<input type="hidden" value="<bean:write name="LOGIN_USER" property="deptName"/>"/>
							</td>
			                <td class="tdr" width="10%" >�� �� ��</td>
			                <td class="tdl">
			                	<bean:write property="usernameacce" name="bean"/>
				            		<input name="usernameacce"  type="hidden" value="<bean:write property="usernameacce" name="bean"/>"/>
				            </td>
				            <td class="tdr" width="10%" >ǩ �� ��</td>
		                	<td class="tdl" ><bean:write name="endorsebean" property="endorseusername"/></td>
			        	</tr>	                    

			            <tr class=trcolor heigth="40" >
			            	<td class="tdr"  width="10%">��������</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write property="sendtopic" name="bean"/>
			                		<input name="sendtopic"  type="hidden" value="<bean:write property="sendtopic" name="bean"/>"/>
			                </td>
			        	</tr>

			             <tr class=trcolor heigth="40" >
			            	<td class="tdr"  width="10%">����˵��</td>
			                <td class="tdl"  colspan="5">
			                	<bean:write property="sendtext" name="bean"/>
			                		<input name="sendtext"  type="hidden" value="<bean:write property="sendtext" name="bean"/>" />
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

			            <tr class=trcolor heigth="40" >
			            	<td class="tdr"  width="10%">���񸽼�</td>
			                <td class="tdl"  colspan="5">
							    <apptag:upload cssClass="" entityId="${sessionScope.bean.id}" entityType="LP_SENDTASK" state="look"/>
			                </td>
			        	</tr>
                        <tr class=trcolor  heigth="40">
                        	<td class="tdr"   colspan="6"><hr/></td>
                        </tr>
	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%">ǩ�ս��</td>
	                        <td class="tdl" width="18%">
	                        	<bean:write property="result" name="bean"/>
	                        		<input name="result" type="hidden" value="<bean:write property="result" name="bean"/>"/>
	                        </td>
	                        <td class="tdr"  width="10%" >ǩ��ʱ��</td>
	                        <td colspan="3" class="tdl">
	                        	<bean:write property="time" name="endorsebean"/>
	                        		<input name="time" type="hidden" value="<bean:write property="time" name="endorsebean"/>"/>
	                        </td>

	                    </tr>
	                    <logic:notEmpty name="endorsebean">

	                    	 <tr class=trcolor heigth="40">
		                    	<td class="tdr"  width="10%">ǩ�ձ�ע</td>
		                        <td class="tdl"  colspan="5">
		                        	<bean:write property="remark" name="endorsebean"/>
		                        		<input name="remark"  type="hidden" value="<bean:write property="remark" name="endorsebean"/>" />
		                        </td>
		                     </tr>
	                         <tr class=trcolor heigth="40">
		                    	<td class="tdr"  width="10%">ǩ�ո���</td>
		                        <td class="tdl"  colspan="5">
							      <apptag:upload cssClass="" entityId="${sessionScope.endorsebean.id}" entityType="LP_SENDTASKENDORSE" state="look"/>
	                            </td>
		                     </tr>
	                    </logic:notEmpty>
	                    <tr class=trcolor >
	                    	<td class="tdr"   colspan="6"><hr/></td>
	                    </tr>


	                    <tr class=trcolor heigth="40"> 
	                    	<td class="tdr"  width="10%" >�������</td>
	                        <td class="tdl" >
                                 <html:select  property="replyresult"   style="width:100"   styleClass="inputtext">
                                   <logic:equal value="ȫ�����" name="replybean" property="replyresult">
                                   <option value="δ���">δ���</option>
                                    		<option value="ȫ�����" selected="selected">ȫ�����</option>
				                       		<option value="�������">�������</option>
                                            <option value="�������">�������</option>
                               		</logic:equal>
                                    <logic:equal value="�������" name="replybean" property="replyresult">
                                    <option value="δ���">δ���</option>
                                    		<option value="ȫ�����" >ȫ�����</option>
				                       		<option value="�������"selected="selected">�������</option>
                                            <option value="�������">�������</option>
                               		</logic:equal>
                                    <logic:equal value="�������" name="replybean" property="replyresult">
                                    <option value="δ���">δ���</option>
                                    		<option value="ȫ�����" >ȫ�����</option>
				                       		<option value="�������">�������</option>
                                            <option value="�������"selected="selected">�������</option>
                               		</logic:equal>
                               		<logic:equal value="δ���" name="replybean" property="replyresult">
                                    <option value="δ���"selected="selected">δ���</option>
                                    		<option value="ȫ�����" >ȫ�����</option>
				                       		<option value="�������">�������</option>
                                            <option value="�������">�������</option>
                               		</logic:equal>
								</html:select>
                            <td class="tdr"  width="10%" >����ʱ��</td>
	                        <td class="tdl" >
	                        	<bean:write property="replytime" name="replybean"/>
		                       	<input name="replytime" type="hidden" value="<bean:write property="replytime" name="replybean"/>"/>
		                    </td>

                            <td class="tdr"  width="10%">����״̬</td>
	                        <td class="tdl">
	                        	<bean:write property="workstate" name="bean"/>
	                        		<input name="workstate"  type="hidden" value="<bean:write property="workstate" name="bean"/>" />
	                        </td>
                        </tr>
	                    <tr class=trcolor heigth="40">
	                    	<td class="tdr"  width="10%" >������ע</td>
	                        <td class="tdl"  colspan="5"><html:textarea property="replyremark"  name="replybean" style="width:80%" title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�"  rows="5" styleClass="textarea"></html:textarea></td>
	                    </tr>
	                    <tr class=trcolor heigth="40">
			            	<td class="tdr"  width="10%">��������</td>
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
								          	<html:button property="action" styleClass="button" onclick="delRow()">ɾ��ѡ��</html:button>
								          </td>
								          <td>
								            <html:submit styleClass="button">�ύ</html:submit>
								          </td>
								          <td>
								            <html:reset styleClass="button">����</html:reset>
								          </td>
								          <td>
								         <input type="button" class="button" onclick="javascript:history.go(-1);" value="����" >
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
	        <html:form action="/ReplyAction?method=doQuery"   styleId="queryForm2" >
            	<template:formTable namewidth="100" contentwidth="250">
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
                     <template:formTr name="�������">
                      	<select name="result"  style="width:180" class="inputtext">
                        			<option value="">����</option>
                        			<option value="δ���">δ���</option>
		                       		<option value="ȫ�����">ȫ�����</option>
                                    <option value="�������">�������</option>
                                    <option value="�������">�������</option>

                      	</select>
				    </template:formTr>
                    <template:formTr name="������ʼʱ��">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',minDate:'2000/1/1'})" style="width:150" />
	                </template:formTr>
	                <template:formTr name="��������ʱ��">
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
		<template:titile value="ת������"/>
		<html:form action="/ReplyAction?method=forwardTask" styleId="addApplyForm"  enctype="multipart/form-data">
	
        	<table id="tableID" width="90%" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
		        <tr class=trcolor heigth="40">    
		            <td class="tdr" width="10%" >�������</td>
		            <td class="tdl" width="25%" colspan="3">
		            	<html:hidden name="bean" property="sendtype" ></html:hidden> 
		            	<bean:write name="bean" property="sendtype"/>	
		            </td>
		        </tr>
		        <tr class=trcolor heigth="40">		        		
	                <td class="tdr" width="10%">�ɵ�����</td>		                
	                <td class="tdl" width="25%"><%=deptname %></td>
	            	<td class="tdr" width="10%" >�� �� ��</td>
	                <td class="tdl"  >
	                	<%=username%>
                    </td>
		        </tr> 
		        <apptag:processorselect inputName="acceptdeptid,user,mobileId,acceptuserid" spanId="userSpan" />
		         
	         	<tr class=trcolor heigth="40">
	            	<td class="tdr">��������</td>
	                <td class="tdl"  colspan="3" >
	                	<html:hidden name="bean" property="sendtopic" /> 
	                	<bean:write name="bean" property="sendtopic"/>
	                </td>	
	        	</tr>
	
	             <tr class=trcolor heigth="40">
	            	<td class="tdr" >����˵��</td>
	            	<td class="tdl"  colspan="3" >
	            		<html:hidden name="bean" property="sendtext" /> 
	                	<bean:write name="bean" property="sendtext"/>
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
                	<td class="tdr" >���񸽼�</td>
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
					            <html:submit styleClass="button"  property="action">�ύ</html:submit>
					          </td>
					          <td>
					            <input type="button" value="����" class="button"  onclick="javascript:history.go(-1);">
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
