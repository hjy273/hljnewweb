<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.sendtask.beans.*;" %>

<html>
<head>
<title>��ѯͳ��</title>
<script type="" language="javascript">
	var rowArr = new Array();//һ�е���Ϣ
	var infoArr = new Array();//���е���Ϣ

	var rowDept = new Array();//һ�е���Ϣ
	var infoDept = new Array();//���е���Ϣ
	
    //��ʼ������
    function initArray(deptid,userid,username){
    	rowArr[0] = deptid;
        rowArr[1] = userid;
        rowArr[2] = username;
        infoArr[infoArr.length] = rowArr;
        rowArr = new Array();
        return true;
    }
    

	function getTimeWin(objName){
		iteName = objName;
		showx = event.screenX - event.offsetX - 4 - 210 ;
		showy = event.screenY - event.offsetY + 18;
		var timeWin = window.showModalDialog("${ctx}/common/time.html",self, "dialogWidth:255px; dialogHeight:285px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;help:no");
	}

	function setSelecteTime(time) {
    	document.all.item(iteName).value = time;
	}

    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}

	function onSelectChange() {
		var slt1Obj=document.getElementById("deptid");
        var slt2Obj=document.getElementById("userid");
		if(document.all.senddeptname != null) {
			//alert(slt1Obj.options[slt1Obj.selectedIndex].text);
			document.all.senddeptname.value = slt1Obj.options[slt1Obj.selectedIndex].text;
		}
		i=0;
		for(j= 0; j< this.infoArr.length; j++ )
		{
			if(slt1Obj.value == infoArr[j][0])
			{
				i++;
			}
		}
        slt2Obj.options.length = i + 1;
		slt2Obj.options[0].text= "����";
		slt2Obj.options[0].value= "";
		k = 1;
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

	function onUserSelectChange(){
        var slt2Obj=document.getElementById("userid");
		if(document.all.sendusername != null) {			
			document.all.sendusername.value = slt2Obj.options[slt2Obj.selectedIndex].text;
		}
	} 

    function bodyOnload(){
      var Obj = document.getElementById("deptid");
      if(Obj != null){
      	 onSelectChange("deptid" ) ;
      }
    }

	 //��ʾһ���ɵ���ϸ��Ϣ
 	function toGetForm(id,subid,flg ){
		

 		var url = "${ctx}/SendTaskAction.do?method=showOneToTatal&id=" + id + "&subid=" + subid+"&flg=" + flg;
		//window.open(url);
    	window.location.href=url;
 	}

	function toGoBack(flg) {
		var url = "${ctx}/SendTaskAction.do?method=doQueryTotalAfterMod&flg="+ flg;
		
    	window.location.href=url;
	}

		function toGoBackPerson() {

			var url="${ctx}/SendTaskAction.do?method=doQueryPersonToTotal"
			window.location.href=url;
		}

	function deptsel(obj) {
		document.getElementById("deptname").value = obj.options[obj.selectedIndex].text;
	}

	function toPersonInfo(userid, username, flg) {
		var url="${ctx}/SendTaskAction.do?method=ShowPersonTaskInfo&flg="+flg+"&userid="+userid+"&username="+username;
		window.location.href=url;
	}
</script>
</head>
<body>

      <!--������ѯ��ʾ-->
     <logic:equal value="1" name="type"scope="session" >
		    <logic:present name="userinfo">
		    	<logic:iterate id="uId" name="userinfo">
		        	<script type="" language="javascript">
            		initArray('<bean:write name="uId" property="deptid"/>','<bean:write name="uId" property="userid"/>','<bean:write name="uId" property="username"/>')
            </script>            
		        </logic:iterate>
		    </logic:present>		    
	      	<br />
	        <template:titile value="������ͳ�Ʋ�ѯ"/>
	        <html:form action="/SendTaskAction.do?method=doQueryToTotal"   styleId="queryForm2" >
            	<template:formTable namewidth="100" contentwidth="250">
            		<template:formTr name="��������">
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
				    <template:formTr name="�ɵ�����" >
				      <select id="deptid" name="senddeptid"  style="width:180" class="inputtext" >
                        	<option value="">����</option>
                            <logic:present name="deptinfo">
                            	<logic:iterate id="element" name="deptinfo">
                            		<option value="<bean:write  name="element" property="deptid"/>"><bean:write name="element"  property="deptname"/></option>
                            	</logic:iterate>
                            </logic:present>
                      </select>
				    </template:formTr><blockquote><br></blockquote>
	        		<template:formTr name="�� �� ��">
					  	 <html:text property="sendusername"  styleClass="inputtext" style="width:180" />
				    </template:formTr>
				     <template:formTr name="����״̬">
                      	<select name="workstate"  style="width:180" class="inputtext">
                        			<option value="">ȫ��</option>
                                    <option value="�ظ���ʱ">�ظ���ʱ</option>
		                       		<option value="�ظ�����ʱ">�ظ�����ʱ</option>
		                       		<option value="��֤��ʱ">��֤��ʱ</option>
		                       		<option value="��֤����ʱ">��֤����ʱ</option>
                      	</select>
				    </template:formTr>
			    
  

                     <template:formTr name="������">
	                     <select name="acceptdeptid"  style="width:180" class="inputtext" onclick="onSelectChange()">
	                        	<option value="">����</option>
	                            <logic:present name="deptinfo">
	                            	<logic:iterate id="element" name="deptinfo">
	                            		<option value="<bean:write  name="element" property="deptid"/>"><bean:write name="element"  property="deptname"/></option>
	                            	</logic:iterate>
	                            </logic:present>
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
                          <html:submit property="action" styleClass="button" >��ѯ</html:submit>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >����</html:reset>
				      	</td>
				    </template:formSubmit>
	        	</template:formTable>

	    	</html:form>
	    </logic:equal>
	    
	        <!--��ѯͳ����Ϣһ����-->
    <logic:equal  name="type" scope="session" value="2">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<br>
		<template:titile value="��ѯͳ����Ϣһ����"/>

        <display:table name="sessionScope.totallist" requestURI="${ctx}/SendTaskAction.do?method=doQueryToTotal" id="currentRowObject" pagesize="20" style="width:100%">
        		<%
        		
				    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				    String serialnumber = "";
				    String id = "";
				    String sendtopic = "";
				    String titleTopic = "";
				    String subid = "";
				    String replyovertime = "";
				    String validovertime = "";
				    if(object != null ){
				    	serialnumber = (String) object.get("serialnumber");
				    	id = (String) object.get("id");
				    	
				    	titleTopic = (String)object.get("sendtopic");
			    		if(titleTopic != null && titleTopic.length() > 10) {
			    			sendtopic = titleTopic.substring(0,10) + "...";
			    		} else {
			    			sendtopic = titleTopic;
			    		}
				    	
				    	subid = (String)object.get("subid");
				    	replyovertime = String.valueOf(object.get("replyovertime"));
				    	validovertime = String.valueOf(object.get("validovertime"));
				    }
				    
				  %>
        	<display:column  media="html" title="��ˮ��" sortable="true" style="width:80px"> 
        		<% 
        			if(serialnumber != null ) {
        		%>       		
        		<a href="javascript:toGetForm('<%=id%>','<%=subid %>','1')"><%=serialnumber%></a>
        		<% } %>
        	</display:column>
        	<display:column media="html" title="����"  sortable="true">
        		<a href="javascript:toGetForm('<%=id %>','<%=subid %>','1')" title="<%=titleTopic %>"><%=sendtopic%></a>
        	</display:column>       	
            <display:column property="sendtype" title="����"  style="width:80px" maxLength="5" sortable="true"/>
            <display:column property="senddeptname" title="�ɵ�����" style="width:80px" sortable="true" maxLength="5"/>
            <display:column property="acceusername" title="������" style="width:60px" sortable="true" maxLength="4"/>
            <display:column property="processterm" title="��������"  style="width:90px" maxLength="10" sortable="true"/>           
            <display:column media="html" title="�ظ���ʱ" style="width:70px"  sortable="true">
            	<%if(replyovertime.indexOf('-') != -1) {%>
            		<font color="red" ><%=replyovertime %></font>
            	<%} else {%>
            		<%=replyovertime %>
            	<%} %>
            </display:column>
            <display:column media="html" title="��֤��ʱ" style="width:70px" sortable="true">               
            	<%if(validovertime.indexOf('-') != -1) {%>
            		<font color="red" ><%=validovertime %></font>
            	<%} else {%>
            		<%=validovertime %>
            	<%} %>        	
            </display:column>         
                   
         


		</display:table>
    <html:link action="/SendTaskAction.do?method=exportSendTotalResult">����ΪExcel�ļ�</html:link>
	</logic:equal>
	
	<logic:equal  name="type" scope="session" value="3">
		<template:titile value="��ѯͳ����ϸ��Ϣ"/>
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
	                        <td>
	                        	<bean:write property="result" name="bean"/>
	                        </td>
	                        <td class="tdr" width="10%" >ǩ �� ��</td>
		                	<td class="tdl" >
		                	<logic:notEmpty name="endorsebean">
		                	<bean:write name="endorsebean" property="endorseusername"/>
		                	</logic:notEmpty>
		                	</td>
	                        <td class="tdr"  width="10%" >ǩ��ʱ��</td>
	                        <td class="tdl">
	                        	<bean:write property="time" name="bean"/>
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

						<logic:notEmpty name="replybean">
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
	                        <td class="tdl"  >
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
						</logic:notEmpty>
						<logic:notEmpty name="valibean">
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
			        	</logic:notEmpty>
			        </table>
                <p align="center">
                <logic:equal value="1" name="flg">
                	<input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
                </logic:equal>
                <logic:equal value="2" name="flg">
                	<input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
                </logic:equal>
                </p>
    
    			<!-- oneInfoGoBack() -->
    </logic:equal>
    
    <!--���˹���ͳ��������ѯ��ʾ-->
     <logic:equal value="11" name="type"scope="session" >    
     		<logic:present name="userinfo">
		    	<logic:iterate id="uId" name="userinfo">
		        	<script type="" language="javascript">
            		initArray('<bean:write name="uId" property="deptid"/>','<bean:write name="uId" property="userid"/>','<bean:write name="uId" property="username"/>')
            		</script>            
		        </logic:iterate>
		    </logic:present>		
	      	<br />
	        <template:titile value="������ͳ�Ʋ�ѯ���˹���"/>
	        <html:form action="/SendTaskAction.do?method=doQueryPersonToTotal"   styleId="queryForm2" >
            	<template:formTable namewidth="100" contentwidth="250">
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
				    <template:formTr name="����" >
				      <select id="deptid" name="senddeptid"  style="width:180" class="inputtext" onclick="onSelectChange()">
                            <logic:present name="deptinfo">
                            	<logic:iterate id="element" name="deptinfo">
                            		<option value="<bean:write  name="element" property="deptid"/>"><bean:write name="element"  property="deptname"/></option>
                            	</logic:iterate>
                            </logic:present>
                      </select>
                      <html:hidden property="senddeptname"/>
				    </template:formTr>
	        		<template:formTr name="�� �� ��">
	        			<select name="senduserid" id="userid" style="width:180" class="inputtext" onchange="onUserSelectChange()">
				      		<option value="">����</option>
					  	</select>
					  	<html:hidden property="sendusername"/>
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
                          <html:submit property="action" styleClass="button" >��ѯ</html:submit>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >����</html:reset>
				      	</td>
				    </template:formSubmit>
	        	</template:formTable>

	    	</html:form>
	   		<script type="" language="javascript">
				onSelectChange();
			</script>
	    </logic:equal>
	    
<!--���˹���ͳ����Ϣһ����-->   
<logic:equal  name="type" scope="session" value="112">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<br>
		
		<template:titile value="���˹���ͳ��һ����"/>
		<font face="����" size="2" color="red" >
		<logic:notEmpty name="querycon">
			<bean:write  name="querycon" property="senddeptname"/>
			<logic:notEmpty name="querycon" property="sendusername">
				<bean:write name="querycon" property="sendusername"/> 
			</logic:notEmpty >
				
			<logic:notEmpty name="querycon" property="begintime" >
				<bean:write name="querycon" property="begintime"/> 	
			</logic:notEmpty>
			<logic:empty name="querycon" property="begintime">
				��ʼ
			</logic:empty>
			
			<logic:notEmpty name="querycon" property="endtime" >
				-- <bean:write name="querycon" property="endtime"/> 	
			</logic:notEmpty>
			<logic:empty name="querycon" property="endtime">
				-- ����
			</logic:empty>
			<logic:notEmpty name="querycon" property="sendtype">����Ϊ<bean:write name="querycon" property="sendtype"/></logic:notEmpty>
			<logic:empty name="querycon" property="sendtype">����Ϊȫ��</logic:empty>		
			����ͳ��	
		</logic:notEmpty>
		</font>
        <display:table name="sessionScope.numtotallist" requestURI="${ctx}/SendTaskAction.do?method=doQueryPersonToTotal" id="currentRowObject" pagesize="20" style="width:100%">
        		<%        		
				    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				    String userid = null ;
				    String username = null;
				    String sendnum = null ;
				    String endorsenum = null ;
				    String replynum = null ;
				    String valitnum = null ;
				    String ennum = null ;
				    String renum = null ;
				    String vanum = null ;
				    String overtimereply = null ;
				    String overtimevalid = null ;
				    if(object != null) {
				    	userid = String.valueOf(object.get("userid"));
				    	username = String.valueOf(object.get("username"));
				    	sendnum = String.valueOf(object.get("sendnum"));
				    	endorsenum = String.valueOf(object.get("endorsenum"));
				    	replynum = String.valueOf(object.get("replynum"));
				    	valitnum = String.valueOf(object.get("valitnum"));
				    	ennum = String.valueOf(object.get("ennum"));
				    	renum = String.valueOf(object.get("renum"));
				    	vanum = String.valueOf(object.get("vanum"));
				    	overtimereply = String.valueOf(object.get("overtimereply"));
				    	overtimevalid = String.valueOf(object.get("overtimevalid"));				    	
				    }
				    
				  %> 
            <display:column property="username" title="����"  style="width:60px" maxLength="4" sortable="true"/>    
            <display:column media="html" title="�ɵ�" style="width:80px" sortable="true"  >
            	<%
            		if(sendnum != null && !"0".equals(sendnum) ) { 
            	%>
            		<a href="javascript:toPersonInfo('<%=userid%>','<%=username %>','0')"><%=sendnum%></a>
            	<%	} else {%>
            		0
            	<%} %>
            </display:column>
             <display:column media="html" title="��ǩ��" style="width:80px" sortable="true"  >
             	<%
            		if(endorsenum != null && !"0".equals(endorsenum) ) { 
            	%>
            		<a href="javascript:toPersonInfo('<%=userid%>','<%=username %>','1')"><%=endorsenum%></a>
            	<%	} else {%>
            		0
            	<%} %>
             </display:column>
            <display:column media="html" title="���ظ�" style="width:80px"  sortable="true">
            	<%
            		if(replynum != null && !"0".equals(replynum) ) { 
            	%>
            		<a href="javascript:toPersonInfo('<%=userid%>','<%=username %>','2')"><%=replynum%></a>
            	<%	} else {%>
            		0
            	<%} %>
           	</display:column>
            <display:column media="html" title="����֤"  style="width:80px"  sortable="true">
            	<%
            		if(valitnum != null && !"0".equals(valitnum) ) { 
            	%>
            		<a href="javascript:toPersonInfo('<%=userid%>','<%=username %>','3')"><%=valitnum%></a>
            	<%	} else {%>
            		0
            	<%} %>
            </display:column>     
            <display:column media="html"  title="ǩ��" style="width:80px" sortable="true"  >            	
            	<%
            		if(ennum != null && !"0".equals(ennum) ) { 
            	%>
            		<a href="javascript:toPersonInfo('<%=userid%>','<%=username %>','11')"><%=ennum%></a>
            	<%	} else {%>
            		0
            	<%} %>
            </display:column>
            <display:column media="html"  title="�ظ�" style="width:80px" sortable="true">
            	<%
            		if(renum != null && !"0".equals(renum) ) { 
            	%>
            		<a href="javascript:toPersonInfo('<%=userid%>','<%=username %>','12')"><%=renum%></a>
            	<%	} else {%>
            		0
            	<%} %>
            </display:column>
            <display:column media="html" title="��֤"  style="width:80px"  sortable="true">
            	<%
            		if(vanum != null && !"0".equals(vanum) ) { 
            	%>
            		<a href="javascript:toPersonInfo('<%=userid%>','<%=username %>','13')"><%=vanum%></a>
            	<%	} else {%>
            		0
            	<%} %>
            </display:column>   
            <display:column media="html" title="�ظ���ʱ" style="width:80px" sortable="true" >
            	 <%
            		if(overtimereply != null && !"0".equals(overtimereply) ) { 
            	%>
            		<a href="javascript:toPersonInfo('<%=userid%>','<%=username %>','21')"><%=overtimereply%></a>
            	<%	} else {%>
            		0
            	<%} %>
            </display:column>
            <display:column media="html" title="��֤��ʱ" style="width:80px" sortable="true">
            	            	 <%
            		if(overtimevalid != null && !"0".equals(overtimevalid) ) { 
            	%>
            		<a href="javascript:toPersonInfo('<%=userid%>','<%=username %>','22')"><%=overtimevalid%></a>
            	<%	} else {%>
            		0
            	<%} %>
            </display:column>
		</display:table>
    <html:link action="/SendTaskAction.do?method=exportPersonNumTotalResult">����ΪExcel�ļ�</html:link>
	</logic:equal>
	    
	     <!--���˹�����Ϣһ����-->
    <logic:equal  name="type" scope="session" value="12">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<br>
		<template:titile value="���˹�����Ϣһ����"/>
		<% 	
    		// ȡ�ô��ظ�����ͳ����Ϣ
    	//	DynaBean dynaBean = (DynaBean)(((List)request.getSession().getAttribute("personcountList")).get(0));;
    	//	String sendnum = "";
    	//	String sendovernum = "";
    	//	String replyovernum = "";
    	//	String replynum = "";
    	//	if(dynaBean != null) {
    	//		sendnum = dynaBean.get("sendnum").toString();
    	//		sendovernum = dynaBean.get("sendovernum").toString();
    	//		replynum = dynaBean.get("replynum").toString();
    	//		replyovernum = dynaBean.get("replyovernum").toString();
    	//	}
    	 %>
		<font face="����" size="2" color="red" >
			<bean:write name="username"/>
			<logic:notEmpty name="querycon">
				<logic:notEmpty name="querycon" property="sendusername">
				<bean:write name="querycon" property="sendusername"/> 
			</logic:notEmpty >
				
			<logic:notEmpty name="querycon" property="begintime" >
				<bean:write name="querycon" property="begintime"/> 	
			</logic:notEmpty>
			<logic:empty name="querycon" property="begintime">
				��ʼ
			</logic:empty>
			
			<logic:notEmpty name="querycon" property="endtime" >
				-- <bean:write name="querycon" property="endtime"/> 	
			</logic:notEmpty>
			<logic:empty name="querycon" property="endtime">
				-- ����
			</logic:empty>
			<logic:notEmpty name="querycon" property="sendtype">����Ϊ<bean:write name="querycon" property="sendtype"/></logic:notEmpty>
			<logic:empty name="querycon" property="sendtype">����Ϊȫ��</logic:empty>
			</logic:notEmpty>
			<logic:equal value="0" name="queryflg">���ɳ�������</logic:equal>
			<logic:equal value="1" name="queryflg">�Ĵ�ǩ�չ�����</logic:equal>
			<logic:equal value="2" name="queryflg">�Ĵ��ظ�������</logic:equal>
			<logic:equal value="3" name="queryflg">�Ĵ���֤������</logic:equal>
			<logic:equal value="11" name="queryflg">��ǩ�չ�����</logic:equal>
			<logic:equal value="12" name="queryflg">�Ļظ�������</logic:equal>
			<logic:equal value="13" name="queryflg">����֤������</logic:equal>
			<logic:equal value="21" name="queryflg">�Ļظ���ʱ������</logic:equal>
			<logic:equal value="22" name="queryflg">����֤��ʱ������</logic:equal>
			<bean:write name="datacount"/>��
		</font>
        <display:table name="sessionScope.totallist" id="currentRowObject" pagesize="20" style="width:100%">
        		<%        		
				    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				    String serialnumber = "";
				    String id = "";
				    String sendtopic = "";
				    String subid = "";
				    String overtimenum = "";
				    String titleTopic = "";
				    if(object != null ){
				    	serialnumber = (String) object.get("serialnumber");
				    	id = (String) object.get("id");
				    
				  		titleTopic = (String)object.get("sendtopic");
			    		if(titleTopic != null && titleTopic.length() > 14) {
			    			sendtopic = titleTopic.substring(0,14) + "...";
			    		} else {
			    			sendtopic = titleTopic;
			    		}
				    	
				    	subid = (String)object.get("subid");
				    	overtimenum = String.valueOf(object.get("overtimenum"));
				    }
				    
				  %>
        	<display:column  media="html" title="��ˮ��" sortable="true" style="width:80px"> 
        		<% 
        			if(serialnumber != null ) {
        		%>       		
        		<a href="javascript:toGetForm('<%=id%>','<%=subid %>','2')"><%=serialnumber%></a>
        		<% } %>
        	</display:column>
        	<display:column media="html" title="����"  sortable="true">
        		<a href="javascript:toGetForm('<%=id %>','<%=subid %>','2')" title="<%=titleTopic %>"><%=sendtopic%></a>
        	</display:column>
            <display:column property="sendtype" title="����"  style="width:90px" maxLength="6" sortable="true"/>
             <display:column property="senddeptname" title="�ɵ���λ" style="width:90px" sortable="true" maxLength="6" />
            <display:column property="acceusername" title="������" style="width:60px" maxLength="4" sortable="true"/>
            <display:column property="processterm" title="��������"  style="width:90px" maxLength="10" sortable="true"/>           
            <display:column media="html" title="��ʱ��Сʱ��" style="width:80px" sortable="true">               
            	<%if(overtimenum.indexOf('-') != -1) {%>
            		<font color="red" ><%=overtimenum %></font>
            	<%} else {%>
            		<%=overtimenum %>
            	<%} %>            	
            </display:column>         
         	        
		</display:table>
    <html:link action="/SendTaskAction.do?method=exportSendPersonTotalResult">����ΪExcel�ļ�</html:link>
    
    <p align="center">
       <input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
    </p>  
  
	</logic:equal>
	
	
	<!--���Ź���ͳ��������ѯ��ʾ-->
     <logic:equal value="13" name="type"scope="session" >    
	      	<br />
	        <template:titile value="������ͳ�Ʋ�ѯ���Ź���"/>
	        <html:form action="/SendTaskAction.do?method=doQueryDeptTotal"  styleId="queryForm2" >
            	<template:formTable namewidth="100" contentwidth="250">
				     <template:formTr name="��λ">
				     	<select name="deptid"   class="inputtext"  style="width:180" onchange="deptsel(this)">
				     		<option value="">����</option>
				     		<logic:present name="deptinfo"  scope="session" >
		                    	<logic:iterate id="deptId"  name="deptinfo">
		                        	<option value="<bean:write  name="deptId" property="deptid"/>"><bean:write name="deptId"  property="deptname"/></option>
		                		</logic:iterate>
							</logic:present>
						</select>
						<input type="hidden" name="deptname" id="deptname"/>
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
				                
                    <template:formTr name="ͳ�ƿ�ʼʱ��">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='����'  readonly="readonly" ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formTr name="ͳ�ƽ���ʱ��">
	                	<input  id="end" type="text"  name="endtime" readonly="readonly" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='����' ID='btn'  onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formSubmit>
				      	<td>
                          <html:submit property="action" styleClass="button" >��ѯ</html:submit>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >����</html:reset>
				      	</td>
				    </template:formSubmit>
	        	</template:formTable>

	    	</html:form>
	    </logic:equal>
	    
	     <!--���Ź���ͳ����Ϣһ����-->
    <logic:equal  name="type" scope="session" value="14">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<br>
		<template:titile value="���Ź���ͳ����Ϣһ����"/>
		<font face="����" size="2" color="red" >
			<logic:notEmpty name="deptquerybean" property="deptid"> 
				<bean:write name="deptquerybean" property="deptname"/>
			</logic:notEmpty >
			<logic:empty name="deptquerybean" property="deptid">
				���в���
			</logic:empty >
			<logic:notEmpty name="deptquerybean" property="sendtype">
				<bean:write name="deptquerybean" property="sendtype"/>
			</logic:notEmpty>
			<logic:empty name="deptquerybean" property="sendtype">
				��������
			</logic:empty>
			<logic:notEmpty name="deptquerybean" property="begintime">
				<bean:write name="deptquerybean" property="begintime"/> -
			</logic:notEmpty>
			<logic:empty name="deptquerybean" property="begintime">
				��ʼ - 
			</logic:empty>
			<logic:notEmpty name="deptquerybean" property="endtime">
				<bean:write name="deptquerybean" property="endtime"/>
			</logic:notEmpty>
			<logic:empty name="deptquerybean" property="endtime">
				����
			</logic:empty>
		 ���Ź���ͳ�ƽ��</font>
        <display:table name="sessionScope.deptTotalList" id="currentRowObject" pagesize="20" style="width:100%">
        	<%        		
				BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");		
				float replypercent = 0;
        	    float validpercent = 0;
        	    if(object != null) {
        	    	replypercent = Float.parseFloat(object.get("replypercent").toString());
					validpercent = Float.parseFloat(object.get("validpercent").toString());
        	    }		   				    
			%>
        	<display:column  property="deptname" title="��λ����" style="width:200px" sortable="true" maxLength="15"/>         	
        	<display:column property="sendnum" title="�ɵ�����" style="width:80px" sortable="true"/>        	 
            <display:column property="replynum" title="�ظ�����"  style="width:80px" maxLength="8" sortable="true"/>
           
            <display:column property="validnum" title="��֤����"  style="width:80px"  maxLength="13" sortable="true"/> 
          
            <display:column media="html" title="�ظ���ʱ��" style="width:100px"  sortable="true">
            	<%=((int)(replypercent*100)) + "%" %>
            </display:column> 
            <display:column media="html" title="��֤��ʱ��" style="width:100px"  sortable="true">
            	<%=((int)(validpercent*100)) + "%" %>
            </display:column>             
		</display:table>
    <html:link action="/SendTaskAction.do?method=exportDeptTotalResult">����ΪExcel�ļ�</html:link>
	</logic:equal>
</body>
</html>
