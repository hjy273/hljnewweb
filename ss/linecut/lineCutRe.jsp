<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.linecut.beans.LineCutBean"%>


<html>
	<head>
		<script type="text/javascript" src="./js/prototype.js"></script>
		<script type="text/javascript" src="./js/json2.js"></script>
		<style type="text/css">
.trStyle {
	background: #ffffff;
}

.fileStyle {
	width: 240px;
	height: 20px;
	line-height: 20px;
}

.selTd {
	text-align: center;
}
</style>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<script type="" language="javascript">

	

	 
  //��ӷ���
    function addGoBack(){
    	try{
            	location.href = "${ctx}/LineCutReAction.do?method=showAllRe";
                return true;
            }
            catch(e){
            	alert(e);
            }
    }
      //�����������
    function addGoBackForAu(){
    	try{
            	location.href = "${ctx}/LineCutReAction.do?method=auditShow";
                return true;
            }
            catch(e){
            	alert(e);
            }
    }
      //��ʾҳ����ϸ��Ϣ��ť����
   function toGetForm(idValue){
     	var url = "${ctx}/LineCutReAction.do?method=showOneInfo&reid=" + idValue;
        window.location.href=url;
    }
	//�޸İ�ť����
   function toUpForm(idValue,isarchive){
        if(isarchive!="������"&&isarchive!="δͨ������"){
          alert("�������ѱ������������޸ģ�");
        }else{
          var url = "${ctx}/LineCutReAction.do?method=upshow&reid=" + idValue;
          window.location.href=url;
        }
    }
    	//ɾ���޸İ�ť����
   function toDelForm(id,reacce,state){
        if(state!="������"&&state!="δͨ������"){
          alert("�������ѱ�����������ɾ����");
        }
        else{
          var url = "${ctx}/LineCutReAction.do?method=doDel&reid=" + id +"&reacce=" + reacce;
          if(confirm("ȷ��ɾ���ü�¼��")){
            window.location.href=url;
          }
        }
    }
    //�鿴������ϸ��ť����
 	function toGetOneAuForm(idValue){
     	var url = "${ctx}/LineCutReAction.do?method=showOneAuInfo&reid=" + idValue;
        window.location.href=url;
    }
     //�鿴������ϸ���ض���
 	function  AuShowGoBack(){
     	var url = "${ctx}/LineCutReAction.do?method=showAllAu";
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
    //=======shenpi=======

	//������ҳ��������ť����
   function toGetFormForAu(idValue){
     	var url = "${ctx}/LineCutReAction.do?method=showOneInfoForAu&reid=" + idValue;
        window.location.href=url;
    }

 //ѡ������
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
	function setSelecteTime(time) {
	    	document.all.item(iteName).value = time;
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

	

	//�ύǰ��֤
	function addresub(){
		var reason = document.getElementById('cutreason').value;
		if(reason.length == 0 ) {
			alert("��������ԭ��!");
			document.getElementById('cutreason').focus();
			return false;
		}
		if(valCharLength(reason)>512) {
			alert("����ĸ��ԭ�����!");
			document.getElementById('cutreason').focus();
			return false;
		}
		var address = document.getElementById('linId').value;
		if(address.length == 0 ) {
			alert("�������ӵص�!");
			document.getElementById('linId').focus();
			return false;
		}
		if(valCharLength(address)>100) {
			alert("����ĸ�ӵص����!");
			document.getElementById('cutreason').focus();
			return false;
		}
		var efsystem = document.getElementById('efsystem').value;
			if(efsystem.length == 0) {
			alert("������Ӱ��ϵͳ!");
			document.getElementById('efsystem').focus();
			return false;
		}
		if(valCharLength(efsystem)>200) {
			alert("�������Ӱ��ϵͳ����!");
			document.getElementById('cutreason').focus();
			return false;
		}
      	if(LineCutBean.date.value == null || LineCutBean.date.value ==""){
	        alert("����дʩ�������ڣ�");
	        return false;
        }
        if(LineCutBean.time.value == null || LineCutBean.time.value ==""){
	        alert("����дʩ����ʱ�䣡");
	        return false;
        }
        if(valCharLength(LineCutBean.reremark.value)>512){
            if (!confirm("��ע��Ϣ����250�����ֻ���512��Ӣ���ַ���������ȥ����ȷ�ϣ�")){
            	return false;
            }
        }
    	LineCutBean.protime.value = LineCutBean.date.value + " " + LineCutBean.time.value;
        LineCutBean.submit();
    }

	//��ģ̬����
	function showModDialog(sURL,height,width){
		var top = (screen.height-height)/2;   // ������screen�϶˵ľ���
		var left = (screen.width-width)/2;    // ������screen��˵ľ���
		var sFeatures = "dialogLeft:" + left + "px; dialogTop:" + top + "px; dialogWidth:" + width + "px; dialogHeight:" + height + "px; help:no; resizable:no; scroll:no; status:no"
		var ret = window.showModalDialog(sURL,self,sFeatures);
		return ret;
	}

	function resetInfo() {
		document.getElementById('lineinfo').innerHTML = '';
		document.getElementById('sublineid').value = '';
	}
	
	//��̬�ϴ�����
	function addRowMod() {
			var maxSeq = parseInt(document.getElementById('maxSeq').value);
			maxSeq ++;//�Լ�
			var tableBody = document.getElementsByTagName("tbody")[0];
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
			var tableBody = document.getElementsByTagName('tbody')[0];
			var delRows = new Array(delChecks.length);
			var delid = 0;
			if(delChecks.length == 0) {
				alert("����û�����Ӹ���!");
				return;
			}
			for(i = 0; i < delChecks.length; i++) {
				if(delChecks[i].checked) {
					delRows[delid++] = document.getElementById(delChecks[i].value)
				}
			}
			if(delid == 0 ) {
				alert("��ѡ��Ҫɾ���ĸ���!");
				return;
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

				function subRe() {
					var cutname = document.getElementById('cutname').value;
					if(cutname.length == 0 ) {
						alert("������������!")
						document.getElementById('cutname').focus();
						return false;
					}
					var sublineidValue = document.getElementById('sublineid').value;
					if(sublineidValue.length == 0) {
						alert("���ƶ���·!");
						document.getElementById('lineMark').focus();
						return false;
					}
					if(sublineidValue.length > 3996) {
						alert("�ƶ����߶��漰̫�࣬�����漰444���߶�!");
						document.getElementById('lineMark').focus();
						return false;
					}
					var reason = document.getElementById('cutreason').value;
					if(reason.length == 0 ) {
						alert("��������ԭ��!");
						document.getElementById('cutreason').focus();
						return false;
					}
					if(valCharLength(reason)>512) {
						alert("����ĸ��ԭ�����!");
						document.getElementById('cutreason').focus();
						return false;
					}
					var address = document.getElementById('linId').value;
					if(address.length == 0 ) {
						alert("�������ӵص�!");
						document.getElementById('linId').focus();
						return false;
					}
					if(valCharLength(address)>100) {
						alert("����ĸ�ӵص����!");
						document.getElementById('cutreason').focus();
						return false;
					}
					var efsystem = document.getElementById('efsystem').value;
					if(efsystem.length == 0) {
						alert("������Ӱ��ϵͳ!");
						document.getElementById('efsystem').focus();
						return false;
					}
					if(valCharLength(efsystem)>200) {
						alert("�������Ӱ��ϵͳ����!");
						document.getElementById('cutreason').focus();
						return false;
					}
					if(LineCutBean.date.value == null || LineCutBean.date.value ==""){
	       				alert("����дʩ�������ڣ�");
	        			return false;
        			}
        			if(LineCutBean.time.value == null || LineCutBean.time.value ==""){
	        			alert("����дʩ����ʱ�䣡");
	        			return false;
        			}
        			if(valCharLength(LineCutBean.reremark.value)>512){
            			if (!confirm("��ע��Ϣ����250�����ֻ���512��Ӣ���ַ���������ȥ����ȷ�ϣ�")){
            				return false;
            			}
        			}
    				LineCutBean.protime.value = LineCutBean.date.value + " " + LineCutBean.time.value;
        			LineCutBean.submit();
				}
		
		function oneReGoBack() {
			var url = "LineCutReAction.do?method=doQueryAfterMod";
			window.location.href=url;
		}

		function auOneGoBack() {
			var url = "LineCutReAction.do?method=doQueryForAuAfterMod";
			window.location.href=url;
		}

	function cancel() {
  		this.close();
	}
</script>


		<title>partRequisition</title>
	</head>
	<body>
		<logic:equal value="r2" name="type">

			<br>
			<template:titile value="��д��·�������" />
			<html:form action="/LineCutReAction?method=addRe"
				styleId="addApplyForm" enctype="multipart/form-data">

				<html:hidden property="protime" value="" />

				<template:formTable namewidth="100" contentwidth="500" th2="����"
					th1="��Ŀ">
					<template:formTr name="�������">
						<html:text property="name" styleId="cutname" value=""
							styleClass="inputtext" style="width:270;" maxlength="50" />
					</template:formTr>

					<template:formTr name="��·�ƶ�">
						<html:button property="lineMark" styleId="lineMark"
							styleClass="button"
							onclick="showModDialog('LineCutReAction.do?method=getLineLevle',300,460)"
							value="�ƶ���·" />
					</template:formTr>

					<template:formTr name="��·��Ϣ">
						<font id="lineinfo"></font>
						<html:hidden property="sublineid" styleId="sublineid" />
					</template:formTr>

					<template:formTr name="���ԭ��">
						<html:textarea property="reason" styleId="cutreason" cols="36"
							rows="4" style="width:270" title="�벻Ҫ����250�����ֻ���512��Ӣ���ַ�!"
							styleClass="textarea" />
						
					</template:formTr>

					<template:formTr name="��ӵص�">
						<%--<html:text property="address" value="" styleId="linId"
							styleClass="inputtext" style="width:200;" maxlength="50" />
						--%>
						<html:textarea property="address" styleId="linId" cols="36"
							rows="4" style="width:270" title="�벻Ҫ����50�����ֻ���100��Ӣ���ַ�!"
							styleClass="textarea" />
					</template:formTr>

					<template:formTr name="Ӱ��ϵͳ" style="width:200">
						<html:textarea property="efsystem" styleId="efsystem" cols="36"
							rows="4" style="width:270" title="�벻Ҫ����100�����ֻ���200��Ӣ���ַ�!"
							styleClass="textarea" />
					</template:formTr>

					<template:formTr name="ԭ��˥��" style="width:200">
						<html:text property="provalue" styleId="provalueid" value="0.00"
							onchange="valiD(id)" styleClass="inputtext" style="width:270;"
							maxlength="26" />�ֱ�(db)
				    </template:formTr>

					<template:formTr name="Ԥ����ʱ">
						<html:text property="prousetime" styleId="prousetimeid"
							value="0.00" onchange="valiD(id)" styleClass="inputtext"
							style="width:270;" />Сʱ
				    </template:formTr>

					<template:formTr name="Ԥ������">
						<input type="text" id="protimeid" name="date" value=""
							readonly="readonly" class="inputtext" style="width: 130" />
						<INPUT TYPE='BUTTON' VALUE='����' ID='btn'
							onclick="GetSelectDateTHIS('protimeid')"
							STYLE="font: 'normal small-caps 6pt serif';">
						<html:text property="time" styleClass="inputtext" style="width:80"
							readonly="true" styleId="time" />
						<input type='button' value="ʱ��" id='btn'
							onclick="getTimeWin('time')"
							style="font: 'normal small-caps 6pt serif';">
					</template:formTr>
					
					<template:formTr name="�������">
							<select name="cutType" class="inputtext" style="width: 270">
								<option value="�½����">
									�½����
								</option>
								<option value="�Ż����">
									�Ż����
								</option>
								<option value="�޸��Ը��">
									�޸��Ը��
								</option>
								<option value="Ǩ���Ը��">
									Ǩ���Ը��
								</option>
							</select>
						</template:formTr>
					
					<logic:equal value="send" name="isSendSm">
						<template:formTr name="���Ž�����">
							<apptag:setSelectOptions columnName1="username"
								columnName2="phone" tableName="userinfo" valueName="objectman"
								region="true" condition="deptype='1' " />
							<html:select property="phone" styleClass="inputtext"
								style="width:270">
								<html:options collection="objectman" property="value"
									labelProperty="label" />
							</html:select>
						</template:formTr>
					</logic:equal>

					<template:formTr name="�����Ƿ��޸�">
						<select name="UPDOC" class="inputtext" style="width: 270" id="mod">
							<option value="�����޸�">
								�����޸�
							</option>
							<option value="��Ҫ�޸�">
								��Ҫ�޸�
							</option>
						</select>
					</template:formTr>

					<template:formTr name="���뱸ע" style="width:270">
						<html:textarea property="reremark" styleId="remark" cols="36"
							rows="4" style="width:270" title="�벻Ҫ����250�����ֻ���512��Ӣ���ַ���������ȥ��"
							styleClass="textarea" />
					</template:formTr>

					<table id="uploadID" width=500 border="0" align="center"
						cellpadding="3" cellspacing="0" class="tabout">
						<tbody></tbody>
					</table>

					<template:formSubmit>
						<td>
							<input type="checkbox" onclick="checkOrCancel()" id="sel">
							ȫѡ&nbsp;
						</td>
						<td>
							<input type="hidden" value="0" id="maxSeq">
							<html:button property="action" styleClass="button"
								onclick="addRowMod()">��Ӹ���</html:button>
						</td>
						<td>
							<input type="button" value="ɾ��ѡ��" onclick="delRow()"
								class="button">
						</td>
						<td>
							<html:button styleClass="button" onclick="subRe()" property="">�� ��</html:button>
						</td>
						<td>
							<input type="reset" value="�� ��" class="button"
								onclick="resetInfo()">
						</td>
					</template:formSubmit>

				</template:formTable>
			</html:form>
		</logic:equal>

		<!--��ʾ���뵥-->
		<logic:equal value="r1" scope="session" name="type">
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<br />
			<%
				DynaBean object1=null;
				String id1= null;
				String numerical = null;
				String name = null;
				String isarchive = null;
				String reacced = null;
				String state = null;
			 %>
			<template:titile value="�������һ����" />
			<display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutReAction.do?method=doQuery" id="currentRowObject"
				pagesize="18">
				<display:column media="html" title="��ˮ��" sortable="true">
					<%
						object1 = (DynaBean)pageContext.getAttribute("currentRowObject");
						if(object1 != null) {
							id1 = (String) object1.get("reid");
							numerical = (String)object1.get("numerical");
							numerical = numerical == null ? "":numerical;
							name = (String)object1.get("name");
							if(name!= null) {
								name = name.length() < 10 ? name: name.substring(0,10)+"...";
							} else {
								name = "";
							}
							isarchive = (String)object1.get("isarchive");
							reacced=(String) object1.get("reacce");
                        	state=(String)object1.get("isarchive");
						}
					 %>
					 <a href="javascript:toGetForm('<%=id1%>')"><%=numerical %></a>
				</display:column>
				<display:column media="html" title="�������" sortable="true">
					  <a href="javascript:toGetForm('<%=id1%>')"><%=name %></a>
				</display:column>
				<display:column property="sublinename" title="�м̶�" maxLength="10"
					style="align:center" sortable="true" />
				<display:column property="address" title="��ӵص�" maxLength="5"
					sortable="true" />
				<display:column property="protime" title="�ƻ����ʱ��" maxLength="20"
					style="align:center" sortable="true" />
				<display:column property="prousetime" title="�ƻ���ʱ" maxLength="10"
					style="align:center" sortable="true" />
				<display:column media="html" title="��ǰ״̬" sortable="true">
					<%if("δͨ������".equals(isarchive)) {%>
						<font color="red"><%=isarchive %></font>
					<%} else if("������".equals(isarchive)) {%>
						<font color="#FFA500"><%=isarchive %></font>
					<%} else if("ͨ������".equals(isarchive)) {%>
						<%=isarchive %>
					<%} else if("����ʩ��".equals(isarchive)) {%>
						<%=isarchive %>
					<%} else if("ʩ�����".equals(isarchive)) {%>
						<%=isarchive %>
					<%} else if("�Ѿ��鵵".equals(isarchive)) {%>
						<%=isarchive %>
					<%} %>
				</display:column>
				<display:column media="html" title="����">
					<apptag:checkpower thirdmould="30104" ishead="0">
					<%
						if("������".equals(isarchive) || "δͨ������".equals(isarchive)) { %>
					    	<a href="javascript:toUpForm('<%=id1%>','<%=isarchive%>')">�޸�</a> 
					  <% } %>
					  </apptag:checkpower>
					  <apptag:checkpower thirdmould="30105" ishead="0">
					  <%
					  	if("������".equals(isarchive) || "δͨ������".equals(isarchive)) { %>
					  		| <a href="javascript:toDelForm('<%=id1%>','<%=reacced%>','<%=state%>')">ɾ��</a>
					  <% } %>
					  </apptag:checkpower>
				</display:column>
			</display:table>
			<html:link action="/LineCutWorkAction.do?method=exportLineCutRe">����ΪExcel�ļ�</html:link>
		</logic:equal>

		<!--��ʾ���뵥����ϸ��Ϣ-->
		<logic:equal value="r10" name="type">
			<BR>
			<template:titile value="��·���������ϸ��Ϣ" />
			<template:formTable namewidth="200" contentwidth="400"  th2="����" th1="��Ŀ">
				<template:formTr name="���뵥λ">
					<bean:write name="reqinfo" property="contractorname" />
				</template:formTr>
				<template:formTr name="�� �� ��">
					<bean:write name="reqinfo" property="username" />
				</template:formTr>
				<template:formTr name="��������">
					<bean:write name="reqinfo" property="retime" />
				</template:formTr>
				<template:formTr name="�������">
					<bean:write name="reqinfo" property="name" />
				</template:formTr>
				<template:formTr name="�м̶���">
					<%
		    		String sublinenamecon = ((LineCutBean) request.getAttribute("reqinfo")).getSublinename();
					String[] atrArr = sublinenamecon.split("<br>");
					for(int i = 0; i < atrArr.length; i++ ) { %>
					<%=atrArr[i] %>;<br>
					<%}%>
				</template:formTr>
				<template:formTr name="���ԭ��">
					<bean:write name="reqinfo" property="reason" />
				</template:formTr>
				<template:formTr name="��ӵص�">
					<bean:write name="reqinfo" property="address" />
				</template:formTr>
				<template:formTr name="�������">
					<bean:write name="reqinfo" property="cutType" />
				</template:formTr>
				<template:formTr name="�ƻ�����">
					<bean:write name="reqinfo" property="protime" />
				</template:formTr>
				<template:formTr name="Ԥ����ʱ">
					<bean:write name="reqinfo" property="prousetime" />Сʱ
		    </template:formTr>
				<template:formTr name="���ǰ˥��">
					<bean:write name="reqinfo" property="provalue" />�ֱ�(db)
		    </template:formTr>
				<template:formTr name="��Ӱ��ϵͳ">
					<bean:write name="reqinfo" property="efsystem" />
				</template:formTr>
				<template:formTr name="�޸�����">
					<bean:write name="reqinfo" property="updoc" />
				</template:formTr>
				<template:formTr name="���뱸ע">
					<bean:write name="reqinfo" property="reremark" />
				</template:formTr>
				<template:formTr name="���븽��">
					<%
		    	String reacce="";
		        LineCutBean bean = (LineCutBean )request.getAttribute("reqinfo");
		        reacce = bean.getReacce();
		        if(reacce==null){
		        	reacce="";
		        }
		 	%>
					<apptag:listAttachmentLink fileIdList="<%=reacce%>" />
				</template:formTr>

				<logic:equal value="" name="reqinfo" property="auditresult">
					<template:formTr name="�������">
	            	������
	            </template:formTr>
				</logic:equal>

				<logic:notEqual value="" name="reqinfo" property="auditresult">
					<logic:notEqual value="δͨ������" name="reqinfo" property="auditresult">
						<template:formTr name="�������">
							<bean:write name="reqinfo" property="auditresult" />
						</template:formTr>
						<template:formTr name="����ʱ��">
							<bean:write name="reqinfo" property="audittime" />
						</template:formTr>
						<template:formTr name="�� �� ��">
							<bean:write name="reqinfo" property="audituserid" />
						</template:formTr>
						<template:formTr name="������ע">
							<bean:write name="reqinfo" property="auditremark" />
						</template:formTr>
						<template:formTr name="��������">
							<%
					    	String auacce="";
	                        LineCutBean beanau = (LineCutBean )request.getAttribute("reqinfo");
					        auacce = beanau.getAuditacce();
					        if(auacce==null){
					        	auacce="";
					        }
					 	%>
							<apptag:listAttachmentLink fileIdList="<%=auacce%>" />
						</template:formTr>
					</logic:notEqual>

					<tr class=trcolor>
						<td>
							&nbsp
						<td>
					</tr>
					<logic:equal value="1" name="apphavemore">
						<logic:iterate id="approve" name="reapprove">
							<template:formTr name="�������">
								<bean:write name="approve" property="auditresult" />
							</template:formTr>
							<template:formTr name="����ʱ��">
								<bean:write name="approve" property="audittime" />
							</template:formTr>
							<template:formTr name="�� �� ��">
								<bean:write name="approve" property="username" />
							</template:formTr>
							<template:formTr name="������ע">
								<bean:write name="approve" property="auditremark" />
							</template:formTr>
							<template:formTr name="��������">
								<logic:equal value="" name="approve" property="auditacce">
                                	�޸���
                            	</logic:equal>
								<logic:notEqual value="" name="approve" property="auditacce">
									<bean:define id="appacce" name="approve" property="auditacce"
										type="java.lang.String" />
									<apptag:listAttachmentLink fileIdList="<%=appacce%>" />
								</logic:notEqual>
								<tr class=trcolor>
									<td>
										&nbsp
									<td>
								</tr>
							</template:formTr>
						</logic:iterate>
					</logic:equal>

				</logic:notEqual>

			</template:formTable>
			<p align="center">
				<input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
			</p>
		</logic:equal>

		<!--�޸�����-->
		<logic:equal value="r4" name="type">
			<br>
			<template:titile value="�޸���·�������" />
			<html:form action="/LineCutReAction?method=doUp"
				styleId="addApplyForm" enctype="multipart/form-data">
				<html:hidden name="reqinfo" property="reid" />
				<html:hidden name="reqinfo" property="reacce" />
				<html:hidden property="protime" value="" />
				<%
                	LineCutBean linecutbean = (LineCutBean)request.getAttribute("reqinfo");
                 %>
				<template:formTable namewidth="100" contentwidth="400" th1="����"
					th2="��д">
					<template:formTr name="�������">
						<%=linecutbean.getName()%>
						<html:hidden name="reqinfo" property="name"
							value="<%=linecutbean.getName() %>" />
					</template:formTr>
					<template:formTr name="�����߶�">
						<%
		    				String sublinenamecon = ((LineCutBean) request.getAttribute("reqinfo")).getSublinename();
							String[] atrArr = sublinenamecon.split("<br>");
							for(int i = 0; i < atrArr.length; i++ ) { %>
						<%=atrArr[i] %>;<br>
						<%}%>
					</template:formTr>
					<template:formTr name="���ԭ��">
						<html:textarea property="reason" name="reqinfo" styleId="cutreason" cols="36"
							rows="4" style="width:270" title="�벻Ҫ����250�����ֻ���512��Ӣ���ַ�!"
							styleClass="textarea" />
						<%--<html:text name="reqinfo" property="reason"
							title="<%=linecutbean.getReason() %>" styleClass="inputtext"
							style="width:200;" maxlength="256" />
					--%>
					</template:formTr>

					<template:formTr name="��ӵص�">
						<html:textarea property="address" name="reqinfo" styleId="linId" cols="36"
							rows="4" style="width:270" title="�벻Ҫ����50�����ֻ���100��Ӣ���ַ�!"
							styleClass="textarea" />
						<%--<html:text name="reqinfo" property="address"
							title="<%=linecutbean.getAddress() %>" styleId="linId"
							styleClass="inputtext" style="width:200;" maxlength="50" />
					--%>
					</template:formTr>
					<template:formTr name="Ӱ��ϵͳ" style="width:200">
						<html:textarea property="efsystem" name="reqinfo" styleId="efsystem" cols="36"
							rows="4" style="width:270" title="�벻Ҫ����100�����ֻ���200��Ӣ���ַ�!"
							styleClass="textarea" />
						<%--<html:text name="reqinfo" property="efsystem"
							title="<%=linecutbean.getEfsystem() %>" styleClass="inputtext"
							style="width:200;" maxlength="100" />
					--%>
					</template:formTr>
					
					<template:formTr name="�������">
							<select name="cutType" class="inputtext" style="width: 270" id="cutType">
								<logic:equal value="�½����" property="cutType" name="reqinfo">
									<option value="�½����" selected="selected">
										�½����
									</option>
									<option value="�Ż����">
										�Ż����
									</option>
									<option value="�޸��Ը��">
										�޸��Ը��
									</option>
									<option value="Ǩ���Ը��">
										Ǩ���Ը��
									</option>
								</logic:equal>
								<logic:equal value="�Ż����" property="cutType" name="reqinfo">
									<option value="�½����">
										�½����
									</option>
									<option value="�Ż����"  selected="selected">
										�Ż����
									</option>
									<option value="�޸��Ը��">
										�޸��Ը��
									</option>
									<option value="Ǩ���Ը��">
										Ǩ���Ը��
									</option>
								</logic:equal>
								<logic:equal value="�޸��Ը��" property="cutType" name="reqinfo">
									<option value="�½����">
										�½����
									</option>
									<option value="�Ż����">
										�Ż����
									</option>
									<option value="�޸��Ը��"   selected="selected">
										�޸��Ը��
									</option>
									<option value="Ǩ���Ը��">
										Ǩ���Ը��
									</option>
								</logic:equal>
								<logic:equal value="Ǩ���Ը��" property="cutType" name="reqinfo">
									<option value="�½����">
										�½����
									</option>
									<option value="�Ż����">
										�Ż����
									</option>
									<option value="�޸��Ը��">
										�޸��Ը��
									</option>
									<option value="Ǩ���Ը��"   selected="selected">
										Ǩ���Ը��
									</option>
								</logic:equal>
							</select>
						</template:formTr>
					
					<template:formTr name="ԭ��˥��" style="width:270">
						<html:text name="reqinfo" property="provalue" styleId="provalueid"
							onchange="valiD(id)" styleClass="inputtext" style="width:270;"
							maxlength="26" title="<%=linecutbean.getProvalue() %>" />�ֱ�(db)
				    </template:formTr>
					<template:formTr name="Ԥ����ʱ">
						<html:text property="prousetime" name="reqinfo"
							title="<%=linecutbean.getProusetime() %>" styleId="prousetimeid"
							onchange="valiD(id)" styleClass="inputtext" style="width:270;" />(Сʱ)
				    </template:formTr>

					<template:formTr name="Ԥ������">
						<% String temp1 = ((LineCutBean)request.getAttribute("reqinfo")).getProtime();  %>
						<input type="text" id="protimeid" name="date"
							value="<%=temp1.substring(0,10)%>" readonly="readonly"
							class="inputtext" style="width: 130"
							title="<%=temp1.substring(0,10)%>" />
						<INPUT TYPE='BUTTON' VALUE='����' ID='btn'
							onclick="GetSelectDateTHIS('protimeid')"
							STYLE="font: 'normal small-caps 6pt serif';">
						<html:text property="time" value="<%= temp1.substring(11,16)%>"
							styleClass="inputtext" style="width:80" readonly="true"
							title="<%= temp1.substring(11,16)%>" />
						<input type='button' value="ʱ��" id='btn'
							onclick="getTimeWin('time')"
							style="font: 'normal small-caps 6pt serif';">

					</template:formTr>
					<template:formTr name="���뱸ע" style="width:270">
						<html:textarea name="reqinfo" property="reremark"
							title="�벻Ҫ����250�����ֻ���512��Ӣ���ַ������򽫽ضϡ�" cols="36" rows="4"
							style="width:270" styleClass="textarea" />
					</template:formTr>
					<template:formTr name="����">
						<%
				    	String reacce1="";
				        LineCutBean bean1 = (LineCutBean )request.getAttribute("reqinfo");
				        reacce1 = bean1.getReacce();
				        if(reacce1==null){
				        	reacce1="";
				        }
				 	%>
						<apptag:listAttachmentLink fileIdList="<%=reacce1%>"
							showdele="true" />
					</template:formTr>
					<table id="uploadID" width=400 border="0" align="center"
						cellpadding="3" cellspacing="0" class="tabout">
						<tbody></tbody>
					</table>
					<template:formSubmit>
						<td>
							<input type="checkbox" onclick="checkOrCancel()" id="sel">
							ȫѡ&nbsp;
						</td>
						<td>
							<input type="hidden" value="0" id="maxSeq">
							<html:button property="action" styleClass="button"
								onclick="addRowMod()">��Ӹ���</html:button>
						</td>
						<td>
							<input type="button" value="ɾ��ѡ��" onclick="delRow()"
								class="button">
						</td>
						<td>
							<html:button styleClass="button" onclick="addresub()" property="">�ύ</html:button>
						</td>
						<td>
							<input type="reset" value="�� ��" class="button">
						</td>
						<td>
							<input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
						</td>
					</template:formSubmit>
				</template:formTable>
			</html:form>
		</logic:equal>

		<!--������ѯҳ��-->
		<logic:equal value="r3" name="type" scope="session">
			<script type="text/javascript" language="javascript">
				//����..............
				function onSelChangeLevel() {
					var ops = document.getElementById('line');
					ops.options.length = 0;
					var clewOps = document.getElementById('clew');
					clewOps.options.length = 0;
					var newClewOp = document.createElement("option");
					newClewOp.value = "";
					newClewOp.text = "===ѡ�����ж���===";
					clewOps.add(newClewOp);
					var params = document.getElementById('level').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getLineByLevelId&levelId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callback, asynchronous:true});
				}
				
				function callback(originalRequest) {
					var ops = document.getElementById('line');
					var rst = originalRequest.responseText;
					var linearr = eval('('+rst+')');
					if(linearr.length == 0) {
						alert("�ü�����δ�ƶ���·!");
						return;
					}
					ops.options.add(new Option('===��ѡ����·===','mention'));
					for(var i = 0; i < linearr.length; i++) {
						ops.options.add(new Option(linearr[i].linename,linearr[i].lineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("�������ӳ������⣬��رպ�����!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
				
				function onSelChangeLine() {
					var ops = document.getElementById('clew');
					var length = ops.length;
					ops.options.length = 0;//��������б�
					ops.options.add(new Option("===ѡ�����ж���===",""));
					var params = document.getElementById('line').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getClewByLineId&lineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServer, asynchronous:true});
				}
				
				function callbackToServer(originalRequest) {
					var ops = document.getElementById('clew');
					var rst = originalRequest.responseText;
					var clewarr = eval('('+rst+')');
					if(clewarr.length == 0) {
						alert("��·����δ�ƶ��߶�!");
						return;
					}
					for(var i = 0; i < clewarr.length; i++) {
						ops.options.add(new Option(clewarr[i].sublinename,clewarr[i].sublineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("�������ӳ������⣬��رպ�����!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
				
				function onSelectChangeClew() {
					var ops = document.getElementById('cutLineName');
					var length = ops.length;
					ops.options.length = 0;//��������б�
					ops.options.add(new Option("===ѡ����������===",""));
					var params = document.getElementById('clew').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getCutNameBySublineid&sublineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServerByName, asynchronous:true});
				}
				
				function callbackToServerByName(originalRequest) {
					var ops = document.getElementById('cutLineName');
					var rst = originalRequest.responseText;
					var nameArr = eval('('+rst+')');
					if(nameArr.length == 0) {
						alert("��·����û�и��������Ϣ!");
						return;
					}
					for(var i = 0; i < nameArr.length; i++) {
						ops.options.add(new Option(nameArr[i].name,nameArr[i].name));
					}
				}
				
	    	</script>
			<br />
			<template:titile value="���������Ҹ������" />
			<html:form action="/LineCutReAction?method=doQuery"
				styleId="queryForm2">
				<template:formTable namewidth="200" contentwidth="200">
					<template:formTr name="���������">
						<select name="reuserid" class="inputtext" style="width: 180px">
							<option value="">
								===ѡ��������Ա===
							</option>
							<logic:present name="lusers">
								<logic:iterate id="lusersId" name="lusers">
									<option
										value="<bean:write name="lusersId" property="reuserid"/>">
										<bean:write name="lusersId" property="username" />
									</option>
								</logic:iterate>
							</logic:present>
						</select>
					</template:formTr>

					<template:formTr name="��·����">
						<select name="level" class="inputtext" style="width: 180px"
							onchange="onSelChangeLevel()" id="level">
							<option value="mention">
								===��ѡ����·����===
							</option>
							<logic:iterate id="linelevel" name="linelevelList">
								<bean:write name="linelevel" property="name" />
								<option value="<bean:write name="linelevel" property="code"/>">
									<bean:write name="linelevel" property="name" />
								</option>
							</logic:iterate>
						</select>
						<img id="Loadingimg" src="./images/ajaxtags/indicator.gif"
							style="display: none">
					</template:formTr>

					<template:formTr name="��·����">
						<select class="inputtext" id="line" onchange="onSelChangeLine()"
							name="line" style="width: 180px;">
							<option></option>
						</select>
					</template:formTr>

					<template:formTr name="�м̶���">
						<select name="sublineid" class="inputtext" style="width: 180px"
							id="clew" onchange="onSelectChangeClew()">
							<option value="">
								===ѡ�����ж���===
							</option>
						</select>
					</template:formTr>


					<template:formTr name="�������">
						<select name="name" class="inputtext" style="width: 180px"
							id="cutLineName">
							<option value="">
								===ѡ�����и��===
							</option>
						</select>
					</template:formTr>

					<template:formTr name="���뿪ʼʱ��">
						<input id="begin" type="text" readonly="readonly" name="begintime"
							class="inputtext" style="width: 150" />
						<INPUT TYPE='BUTTON' VALUE='����' readonly="readonly" ID='btn'
							onclick="GetSelectDateTHIS('begin')"
							STYLE="font: 'normal small-caps 6pt serif';">
					</template:formTr>
					<template:formTr name="�������ʱ��">
						<input id="end" type="text" name="endtime" readonly="readonly"
							class="inputtext" style="width: 150" />
						<INPUT TYPE='BUTTON' VALUE='����' ID='btn'
							onclick="GetSelectDateTHIS('end')"
							STYLE="font: 'normal small-caps 6pt serif';">
					</template:formTr>
					<template:formSubmit>
						<td>
							<html:submit property="action" styleClass="button">����</html:submit>
						</td>
						<td>
							<html:reset property="action" styleClass="button">ȡ��</html:reset>
						</td>
					</template:formSubmit>
				</template:formTable>

			</html:form>
		</logic:equal>
		<!--=========================��������=================================-->
		<!--��ʾ���������뵥-->
		<logic:equal value="reforAu1" scope="session" name="type">
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<br />
			<%
				DynaBean object=null;
				String id= null;
				String numerical = null;
				String name = null;
			%>
			<template:titile value="�������������һ����" />
			<display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutReAction.do?method=auditShow" id="currentRowObject"
				pagesize="18">
				<display:column media="html" title="��ˮ��" sortable="true">
    				<%
						object = (DynaBean)pageContext.getAttribute("currentRowObject");
						if(object != null) {
							id = (String) object.get("reid");
							numerical = (String)object.get("numerical");
							numerical = numerical == null?"":numerical;
							name =  (String)object.get("name");
							if(name != null) {
								name = name.length() < 10 ? name: name.substring(0,10)+"...";
							} else {
								name = "";
							}
						}
					 %>
					 <a href="javascript:toGetFormForAu('<%=id%>')"><%=numerical %></a>
    			</display:column>
				<display:column media="html"  title="�������" sortable="true">
					  <a href="javascript:toGetFormForAu('<%=id%>')"><%=name %></a>
            	</display:column>
				<display:column property="sublinename" title="�����м̶�" maxLength="10"
					style="align:center" sortable="true" />
				<display:column property="prousetime" title="�ƻ���ʱ" maxLength="10"
					style="align:center" sortable="true" />
				<display:column property="protime" title="�ƻ����ʱ��" maxLength="18"
					style="align:center" sortable="true" />
				<display:column property="address" title="��ӵص�" maxLength="10"
					sortable="true" />
			</display:table>
		</logic:equal>


		<!--����ҳ��-->
		<logic:equal value="au2" name="type">
			<script type="text/javascript">
		//��̬�ϴ�����
	function addRowModAu() {
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
		
		function checkOrCancelAu() {
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
		
		
		function delRowAu() {
			var delChecks = document.getElementsByName('ifCheck');
			var tableBody = document.getElementsByTagName('tbody')[1];
			var delRows = new Array(delChecks.length);
			var delid = 0;
			if(delChecks.length == 0) {
				alert("����û�����Ӹ���!");
				return;
			}
			for(i = 0; i < delChecks.length; i++) {
				if(delChecks[i].checked) {
					delRows[delid++] = document.getElementById(delChecks[i].value)
				}
			}
			if(delid == 0 ) {
				alert("��ѡ��Ҫɾ���ĸ���!");
				return;
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
		</script>
			<BR>
			<template:titile value="��·���������Ϣ" />
			<template:formTable namewidth="100" contentwidth="400"  th2="����" th1="��Ŀ">
				<template:formTr name="���뵥λ">
					<bean:write name="reqinfo" property="contractorname" />
				</template:formTr>
				<template:formTr name="���������">
					<bean:write name="reqinfo" property="username" />
				</template:formTr>
				<template:formTr name="��������">
					<bean:write name="reqinfo" property="retime" />
				</template:formTr>
				<template:formTr name="�������">
					<bean:write name="reqinfo" property="name" />
				</template:formTr>
				<template:formTr name="�м̶���">
					<%
		    		String sublinenamecon = ((LineCutBean) request.getAttribute("reqinfo")).getSublinename();
					String[] atrArr = sublinenamecon.split("<br>");
					for(int i = 0; i < atrArr.length; i++ ) { %>
					<%=atrArr[i] %>;<br>
					<%}%>
				</template:formTr>
				<template:formTr name="���ԭ��">
					<bean:write name="reqinfo" property="reason" />
				</template:formTr>
				<template:formTr name="��ӵص�">
					<bean:write name="reqinfo" property="address" />
				</template:formTr>
				<template:formTr name="�������">
					<bean:write name="reqinfo" property="cutType" />
				</template:formTr>
				<template:formTr name="�ƻ��������">
					<bean:write name="reqinfo" property="protime" />
				</template:formTr>
				<template:formTr name="Ԥ�Ƹ����ʱ">
					<bean:write name="reqinfo" property="prousetime" />Сʱ
		    </template:formTr>
				<template:formTr name="���ǰ˥��">
					<bean:write name="reqinfo" property="provalue" />�ֱ�(db)
		    </template:formTr>
				<template:formTr name="��Ӱ��ϵͳ">
					<bean:write name="reqinfo" property="efsystem" />
				</template:formTr>
				<template:formTr name="���뱸ע">
					<bean:write name="reqinfo" property="reremark" />
				</template:formTr>
				<template:formTr name="�޸�����">
					<bean:write name="reqinfo" property="updoc" />
				</template:formTr>
				<template:formTr name="����">
					<%
		    	String reacce="";
		        LineCutBean bean = (LineCutBean )request.getAttribute("reqinfo");
		        reacce = bean.getReacce();
		        if(reacce==null){
		        	reacce="";
		        }
		 	%>
					<apptag:listAttachmentLink fileIdList="<%=reacce%>" />
				</template:formTr>
			</template:formTable>
			<hr />
			<html:form action="/LineCutReAction?method=addAu"
				styleId="addApplyForm" enctype="multipart/form-data">
				<html:hidden property="reid" name="reqinfo" />
				
				<html:hidden property="name" name="reqinfo" />
				<template:formTable namewidth="100" contentwidth="400" th2="��д"
					th1="��Ŀ">
					<template:formTr name="������">
						<bean:write name="LOGIN_USER" property="userName" />
						<input type="hidden" class="inputtext" readonly="readonly"
							value="<bean:write name="LOGIN_USER" property="userName"/>"
							style="width: 200" />
					</template:formTr>
					<template:formTr name="��������">
						<html:text property="audittime" readonly="true"
							style="width:200; border: 0px;background-color: #F5F5F5;">
							<apptag:date property="audittime">
							</apptag:date>
						</html:text>
					</template:formTr>
					<template:formTr name="�������">
						<html:select property="auditresult" styleClass="inputtext"
							style="width:270;">
							<html:option value="ͨ������">ͨ������	</html:option>
							<html:option value="δͨ������">δͨ������	</html:option>
						</html:select>
					</template:formTr>

					<template:formTr name="������ע" style="width:270">
						<html:textarea property="auditremark" cols="36" rows="4"
							title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�" style="width:270"
							styleClass="textarea" />
					</template:formTr>

					<table id="uploadID" width=500 border="0" align="center"
						cellpadding="3" cellspacing="0" class="tabout">
						<tbody></tbody>
					</table>


					<template:formSubmit>
						<td>
							<input type="checkbox" onclick="checkOrCancelAu()" id="sel">
							ȫѡ&nbsp;
						</td>
						<td>
							<html:button property="action" styleClass="button"
								onclick="addRowModAu()">��Ӹ���</html:button>
						</td>
						<td>
							<input type="button" value="ɾ��ѡ��" onclick="delRowAu()"
								class="button">
						</td>
						<td>
							<input type="hidden" value="0" id="maxSeq">
							<html:submit styleClass="button">�ύ</html:submit>
						</td>
						<td>
							<html:reset styleClass="button">ȡ��	</html:reset>
						</td>
						<td>
							<input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
						</td>
					</template:formSubmit>
				</template:formTable>
			</html:form>
		</logic:equal>

		<!--��ʾ������-->
		<logic:equal value="au1" scope="session" name="type">
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<BR>
			 <%
				DynaBean object2=null;
				String id2= null;
				String numerical = null;
				String name = null;
				String isarchive = null;
			%>
			<template:titile value="�����������һ����" />
			<display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutReAction.do?method=doQueryForAu" id="currentRowObject"
				pagesize="18">
				<display:column media="html" title="��ˮ��" sortable="true" >
    				<%
						object2 = (DynaBean)pageContext.getAttribute("currentRowObject");
						if(object2 != null) {
							id2 = (String) object2.get("reid");
							numerical = (String)object2.get("numerical");
							numerical = numerical == null? "": numerical;
							name = (String) object2.get("name");
							if(name != null) {
								name = name.length() < 10 ? name: name.substring(0,10)+"...";
							} else {
								name = "";
							}
							isarchive = (String) object2.get("isarchive");
						}
					 %>
					 <a href="javascript:toGetOneAuForm('<%=id2%>')"><%=numerical %></a>
    			</display:column>
				
				<display:column media="html"  title="�������" sortable="true" >
					  <a href="javascript:toGetOneAuForm('<%=id2%>')"><%=name %></a>
            	</display:column>
            	
				<display:column property="sublinename" title="�м̶�" maxLength="15"
					style="align:center" sortable="true" />
				<display:column property="protime" title="�ƻ����ʱ��" maxLength="18"
					style="align:center"  sortable="true" />
				<display:column property="address" title="��ӵص�" maxLength="10"
					sortable="true" />
				<display:column property="audittime" title="��������" maxLength="13"
					sortable="true" />
				<display:column media="html" title="��ǰ״̬" sortable="true" >
					  <%if("�Ѿ��鵵".equals(isarchive)) {%>
						<font color=""><%=isarchive %></font>
					  <%} 
					  else if("ʩ�����".equals(isarchive)) {%>
					  	<font color="#DA70D6"><%=isarchive %></font>
					  <%} 
					  else if("�Ѿ�����".equals(isarchive)) {%>
						<font color="#BDB76B"><%=isarchive %></font>					  	
					  <%} 
					  else if("����ʩ��".equals(isarchive)) {%>
					  	<font color="#FFA500"><%=isarchive %></font>
					  <%} else {%>
					  	<%=isarchive %>
					  <%} %>
				</display:column>
			</display:table>
			<html:link action="/LineCutWorkAction.do?method=exportReLineCut">����ΪExcel�ļ�</html:link>
		</logic:equal>

		<!--��ʾ����������ϸ��Ϣ-->
		<logic:equal value="au10" name="type">
			<br />
			<template:titile value="��·���������ϸ��Ϣ" />
			<template:formTable namewidth="200"  contentwidth="400"  th2="����" th1="��Ŀ">
				<template:formTr name="���뵥λ">
					<bean:write name="reqinfo" property="contractorname" />
				</template:formTr>
				
				<template:formTr name="��������">
					<bean:write name="reqinfo" property="retime" />
				</template:formTr>
				<template:formTr name="�������">
					<bean:write name="reqinfo" property="name" />
				</template:formTr>
				<template:formTr name="�м̶���">
					<%
		    		String sublinenamecon = ((LineCutBean) request.getAttribute("reqinfo")).getSublinename();
					String[] atrArr = sublinenamecon.split("<br>");
					for(int i = 0; i < atrArr.length; i++ ) { %>
					<%=atrArr[i] %>;<br>
					<%}%>
				</template:formTr>
				<template:formTr name="���ԭ��">
					<bean:write name="reqinfo" property="reason" />
				</template:formTr>
				<template:formTr name="��ӵص�">
					<bean:write name="reqinfo" property="address" />
				</template:formTr>
				<template:formTr name="�������">
					<bean:write name="reqinfo" property="cutType" />
				</template:formTr>
				<template:formTr name="�ƻ��������">
					<bean:write name="reqinfo" property="protime" />
				</template:formTr>
				<template:formTr name="���ǰ˥��">
					<bean:write name="reqinfo" property="provalue" />�ֱ�(db)
		    </template:formTr>
				<template:formTr name="��Ӱ��ϵͳ">
					<bean:write name="reqinfo" property="efsystem" />
				</template:formTr>
				<template:formTr name="���뱸ע">
					<bean:write name="reqinfo" property="reremark" />
				</template:formTr>

				<template:formTr name="�޸�����">
					<bean:write name="reqinfo" property="updoc" />
				</template:formTr>

				<template:formTr name="���븽��">
					<logic:equal value="" name="reqinfo" property="reacce">
						<apptag:listAttachmentLink fileIdList="" />
					</logic:equal>
					<logic:notEqual value="" name="reqinfo" property="reacce">
						<bean:define id="tem" name="reqinfo" property="reacce"
							type="java.lang.String" />
						<apptag:listAttachmentLink fileIdList="<%=tem%>" />
					</logic:notEqual>
				</template:formTr>

				<template:formTr name="������λ">
					<bean:write name="reqinfo" property="deptname" />
				</template:formTr>
				<template:formTr name="������">
					<bean:write name="reqinfo" property="username" />
				</template:formTr>
				<template:formTr name="����ʱ��">
					<bean:write name="reqinfo" property="audittime" />
				</template:formTr>
				<template:formTr name="�������">
					<bean:write name="reqinfo" property="auditresult" />
				</template:formTr>
				<template:formTr name="������ע">
					<bean:write name="reqinfo" property="auditremark" />
				</template:formTr>
				<template:formTr name="�޸�����">
					<bean:write name="reqinfo" property="updoc" />
				</template:formTr>

				<template:formTr name="��������">

					<logic:equal value="" name="reqinfo" property="auditacce">
						<apptag:listAttachmentLink fileIdList="" />
					</logic:equal>
					<logic:notEqual value="" name="reqinfo" property="auditacce">
						<bean:define id="tem1" name="reqinfo" property="auditacce"
							type="java.lang.String" />
						<apptag:listAttachmentLink fileIdList="<%=tem1%>" />
					</logic:notEqual>

				</template:formTr>
			</template:formTable>

			<p align="center">
			<logic:equal value="1" name="flg">
				<input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
			</logic:equal>
			<logic:equal value="2" name="flg">
				<html:button property="action" styleClass="button"
					onclick="cancel()">�ر�</html:button>
			</logic:equal>	
			</p>
		</logic:equal>

		<!--������ѯҳ��-->
		<logic:equal value="au3" name="type" scope="session">
			<script type="text/javascript" language="javascript">
	    		//����..............
				function onSelChangeLevel() {
					var ops = document.getElementById('line');
					ops.options.length = 0;
					var clewOps = document.getElementById('clew');
					clewOps.options.length = 0;
					var newClewOp = document.createElement("option");
					newClewOp.value = "";
					newClewOp.text = "===ѡ�����ж���===";
					clewOps.add(newClewOp);
					var params = document.getElementById('level').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getLineByLevelId&levelId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callback, asynchronous:true});
				}
				
				function callback(originalRequest) {
					var ops = document.getElementById('line');
					var rst = originalRequest.responseText;
					var linearr = eval('('+rst+')');
					if(linearr.length == 0) {
						alert("�ü�����δ�ƶ���·!");
						return;
					}
					ops.options.add(new Option('===��ѡ����·===','mention'));
					for(var i = 0; i < linearr.length; i++) {
						ops.options.add(new Option(linearr[i].linename,linearr[i].lineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("�������ӳ������⣬��رպ�����!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
				
				function onSelChangeLine() {
					var ops = document.getElementById('clew');
					var length = ops.length;
					ops.options.length = 0;//��������б�
					ops.options.add(new Option("===ѡ�����ж���===",""));
					var params = document.getElementById('line').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getClewByLineId&lineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServer, asynchronous:true});
				}
				
				function callbackToServer(originalRequest) {
					var ops = document.getElementById('clew');
					var rst = originalRequest.responseText;
					var clewarr = eval('('+rst+')');
					if(clewarr.length == 0) {
						alert("��·����δ�ƶ��߶�!");
						return;
					}
					for(var i = 0; i < clewarr.length; i++) {
						ops.options.add(new Option(clewarr[i].sublinename,clewarr[i].sublineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("�������ӳ������⣬��رպ�����!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
				
				function onSelectChangeClew() {
					var ops = document.getElementById('cutLineName');
					var length = ops.length;
					ops.options.length = 0;//��������б�
					ops.options.add(new Option("===ѡ����������===",""));
					var params = document.getElementById('clew').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getNameByClewId&sublineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServerByName, asynchronous:true});
				}
				
				function callbackToServerByName(originalRequest) {
					var ops = document.getElementById('cutLineName');
					var rst = originalRequest.responseText;
					var nameArr = eval('('+rst+')');
					if(nameArr.length == 0) {
						alert("��·����û�и��!");
						return;
					}
					for(var i = 0; i < nameArr.length; i++) {
						ops.options.add(new Option(nameArr[i].name,nameArr[i].name));
					}
				}
	    	</script>

			<br />
			<template:titile value="���������Ҹ��������Ϣ" />
			<html:form action="/LineCutReAction?method=doQueryForAu"
				styleId="queryForm2">
				<template:formTable namewidth="200" contentwidth="200">
					<template:formTr name="����������">
						<select name="audituserid" class="inputtext" style="width: 180px">
							<option value="">
								ѡ��������Ա
							</option>
							<logic:present name="lusers">
								<logic:iterate id="lusersId" name="lusers">
									<option
										value="<bean:write name="lusersId" property="audituserid"/>">
										<bean:write name="lusersId" property="username" />
									</option>
								</logic:iterate>
							</logic:present>
						</select>
					</template:formTr>

					<template:formTr name="��·����">
						<select name="level" class="inputtext" style="width: 180px"
							onchange="onSelChangeLevel()" id="level">
							<option value="mention">
								===��ѡ����·����===
							</option>
							<logic:iterate id="linelevel" name="linelevelList">
								<bean:write name="linelevel" property="name" />
								<option value="<bean:write name="linelevel" property="code"/>">
									<bean:write name="linelevel" property="name" />
								</option>
							</logic:iterate>
						</select>
						<img id="Loadingimg" src="./images/ajaxtags/indicator.gif"
							style="display: none">
					</template:formTr>

					<template:formTr name="��·����">
						<select class="inputtext" id="line" onchange="onSelChangeLine()"
							name="line" style="width: 180px;">
							<option></option>
						</select>
					</template:formTr>

					<template:formTr name="�м̶���">
						<select name="sublineid" class="inputtext" style="width: 180px"
							id="clew" onchange="onSelectChangeClew()">
							<option value="">
								===ѡ�����ж���===
							</option>
						</select>
					</template:formTr>

					<template:formTr name="�������">
						<select name="name" class="inputtext" style="width: 180px"
							id="cutLineName">
							<option value="">
								===ѡ�����и��===
							</option>
						</select>
					</template:formTr>


					<template:formTr name="������ʼʱ��">
						<input id="begin" type="text" readonly="readonly" name="begintime"
							class="inputtext" style="width: 150" />
						<INPUT TYPE='BUTTON' VALUE='����' readonly="readonly" ID='btn'
							onclick="GetSelectDateTHIS('begin')"
							STYLE="font: 'normal small-caps 6pt serif';">
					</template:formTr>
					<template:formTr name="��������ʱ��">
						<input id="end" type="text" name="endtime" class="inputtext"
							style="width: 150" />
						<INPUT TYPE='BUTTON' VALUE='����' ID='btn'
							onclick="GetSelectDateTHIS('end')"
							STYLE="font: 'normal small-caps 6pt serif';">
					</template:formTr>
					<template:formSubmit>
						<td>
							<html:submit property="action" styleClass="button">����</html:submit>
						</td>
						<td>
							<html:reset property="action" styleClass="button">ȡ��</html:reset>
						</td>
					</template:formSubmit>
				</template:formTable>
			</html:form>
		</logic:equal>


		<iframe id="hiddenIframe" style="display: none"></iframe>
	</body>

</html>
