<%@page import="com.cabletech.linecut.beans.LineCutBean"%>
<%@include file="/common/header.jsp"%>

<html>
<head>
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
        //t1.name = "filename"+onerow.id;//alert(onerow.id);
        t1.name = "uploadFile["+ fileNum + "].file";
		t1.type= "file";
		t1.id = "text" + onerow.id;
		t1.width="200";
        fileNum++;


        //����ɾ����ť
        var b1 =document.createElement("button");
        b1.value = "ɾ��";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");

        //�޸ĸ�������ʾ��ʽ
        cell1.width="155";
        cell2.width="300";
        cell1.align="right";
        cell1.innerText="������";
        cell2.appendChild(t1);//����
        cell2.appendChild(b1);
	}

 //��ʾ�����յ�ʩ����Ϣ
 function toGetOneWorkForm(id){
 	var url = "${ctx}/LineCutAcceptAction.do?method=showOneWorkForAcc&reid=" + id;
   window.location.href=url;
 }
 function showallAcce(){
 	var url = "${ctx}/LineCutAcceptAction.do?method=showAllWorkForAcc";
    window.location.href=url;
 }
 function toGetOneAccForm(id){
 	var url = "${ctx}/LineCutAcceptAction.do?method=showOneAcc&reid=" + id;
    window.location.href=url;
 }

 function addGoBack(){
 	var url = "${ctx}/LineCutAcceptAction.do?method=showAllAcc";
    window.location.href=url;
 }

function goQueryPage() {
	var url = "${ctx}/linecut/lineCutRe.jsp"
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
	
	function oneInfoGoBack() {
		var url="LineCutAcceptAction.do?method=queryAccAfterMod";
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
<title>lineCutAcc</title>
</head>
<body>
  <!--��ʾ���յ�-->
<logic:equal value="ac1" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <BR>
  <%
	DynaBean object1 = null;
	String id1 = null;
	String numerical = null;
	String name = null;
	String auditresult = null;
  %>
  <template:titile value="���ʩ������һ����"/>
  <display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutAcceptAction.do?method=doQuery" id="currentRowObject" pagesize="18">
  	<display:column media="html" title="��ˮ��" sortable="true">
		<%
			object1 = (DynaBean)pageContext.getAttribute("currentRowObject");
			if(object1 != null) {
				id1 = (String) object1.get("reid");
				numerical = (String)object1.get("numerical");
				numerical = numerical==null ? "": numerical;
				name =  (String)object1.get("name");
				if(name != null) {
					name = name.length() < 10 ? name: name.substring(0,10)+"...";
				} else {
					name = "";
				}
				auditresult = (String)object1.get("auditresult");
			}
			
		%>
		<a href="javascript:toGetOneAccForm('<%=id1%>')"><%=numerical %></a>
	</display:column>
    <display:column media="html" title="�������" sortable="true">
		<a href="javascript:toGetOneAccForm('<%=id1%>')"><%=name %></a>
	</display:column>
    <display:column property="address" title="��ӵص�" maxLength="10"  style="align:center" sortable="true"/>
    <display:column property="essetime" title="���ʱ��" maxLength="20"  style="align:center" sortable="true"/>
    <display:column property="audittime" title="����ʱ��" maxLength="20"  style="align:center" sortable="true"/>
    <display:column media="html" title="���ս��" sortable="true">
    	<%if("ͨ������".equals(auditresult)) {%>
    		<font color="#FFA500" ><%=auditresult %></font>
    	<%} else if("δͨ������".equals(auditresult)) { %>
    		<font color="#FFA500"><%=auditresult %></font>
    	<%} else { %>
    		<%=auditresult %>
    	<%} %>
    </display:column>
    <display:column property="username" title="�ύ��" maxLength="10"  style="align:center" sortable="true"/>
  </display:table>
  <html:link action="/LineCutAcceptAction.do?method=exportLineCutAcc">����ΪExcel�ļ�</html:link>
</logic:equal>


  <!--��ʾ���д����յĸ��ʩ��-->
<logic:equal value="ac2" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <BR>
  <%
	DynaBean object1 = null;
	String id1 = null;
	String numerical = null;
	String name = null;
  %>
  <template:titile value="���ʩ������һ����"/>
  <display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutAcceptAction.do?method=showAllWorkForAcc" id="currentRowObject" pagesize="18">
    	<display:column media="html" title="��ˮ��">
		<%
			object1 = (DynaBean)pageContext.getAttribute("currentRowObject");
			if(object1 != null) {
				id1 = (String) object1.get("reid");
				numerical = (String)object1.get("numerical");
				numerical = numerical==null ? "": numerical;
				name =  (String)object1.get("name");
				if(name != null) {
					name = name.length() < 10 ? name: name.substring(0,10)+"...";
				} else {
					name = "";
				}
			}
			
		%>
		<a href="javascript:toGetOneWorkForm('<%=id1%>')"><%=numerical %></a>
	</display:column>
	<display:column media="html" title="�������">
		<a href="javascript:toGetOneWorkForm('<%=id1%>')"><%=name %></a>
	</display:column>
    <display:column property="address" title="��ӵص�" maxLength="10"  style="align:center" sortable="true"/>
    <display:column property="sublinename" title="�м̶�" maxLength="10"  style="align:center" sortable="true"/>
    <display:column property="endvalue" title="��Ӻ�˥��" maxLength="10"  style="align:center"" sortable="true"/>
    <display:column property="essetime" title="�������" maxLength="18"  style="align:center" sortable="true"/>
    <display:column property="username" title="�ύ��" maxLength="10"  style="align:center" sortable="true"/>
    <%--<display:column media="html" title="����">
    <%
      BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String id = (String) object.get("reid");
    %>
      <a href="javascript:toGetOneWorkForm('<%=id%>')">����</a>
    </display:column>
    --%>
  </display:table>
</logic:equal>
  <!--����һ�����ʩ��-->
<logic:equal value="ac20" name="type">
	<%
		LineCutBean bean = (LineCutBean)request.getAttribute("reqinfo");
	 %>
	 <br>
  <template:titile value="���ո��ʩ��" />
  <template:formTable namewidth="100" contentwidth="400" th2="����" th1="��Ŀ">
    <template:formTr name="�������">
    	<%=bean.getName() %>
      <html:hidden property="name" name="reqinfo" styleClass="inputtext" style="width:220" />
    </template:formTr>
    <template:formTr name="�����߶�">
    	<%=bean.getSublinename() %>
      <html:hidden property="sublinename" name="reqinfo" styleClass="inputtext" style="width:220"/>
    </template:formTr>
    <template:formTr name="���ԭ��">
    	<%=bean.getReason() %>
      <html:hidden property="reason" name="reqinfo" styleClass="inputtext" style="width:220"/>
    </template:formTr>
    <template:formTr name="��ӵص�">
    	<%=bean.getAddress() %>
      <html:hidden property="address" name="reqinfo" styleClass="inputtext" style="width:220"/>
    </template:formTr>
    <template:formTr name="�������">
    	<%=bean.getCutType() %>
    </template:formTr>
    <template:formTr name="���ǰ˥��">
    	<%=bean.getProvalue() %>
      <html:hidden property="provalue" name="reqinfo" styleClass="inputtext" style="width:180"/>
      �ֱ�(db)
    </template:formTr>
    <template:formTr name="��Ӱ��ϵͳ">
    	<%=bean.getEfsystem() %>
      <html:hidden property="efsystem" name="reqinfo" styleClass="inputtext" style="width:220"/>
    </template:formTr>
    <template:formTr name="��Ӻ�˥��">
    	<%=bean.getEndvalue() %>
      <html:hidden property="endvalue" name="reqinfo" styleClass="inputtext" style="width:180" />
      �ֱ�(db)
    </template:formTr>
    <template:formTr name="��������">
    	<%=bean.getManpower() %>
      <html:hidden property="manpower" name="reqinfo" styleClass="inputtext" style="width:200" />
      ����
    </template:formTr>
    <template:formTr name="�����ʱ">
    	<%=bean.getUsetime() %>
      <html:hidden property="usetime" name="reqinfo" styleClass="inputtext" style="width:200"/>
      Сʱ
    </template:formTr>
    <template:formTr name="���ʱ��">
    	<bean:write name="reqinfo" property="essetime"/>
      <input id="timeid" type="hidden"  value="<bean:write name="reqinfo" property="essetime"/>" name="essetime" class="inputtext" style="width:220" title=""/>
    </template:formTr>
    <template:formTr name="ʩ����ע">
    	<%=bean.getWorkremark() %>
      <html:hidden property="workremark" name="reqinfo" styleClass="forTextArea3" value="<=%bean.getWorkremark() %>" />      
    </template:formTr>
    <template:formTr name="�޸�����">
    	<bean:write name="reqinfo" property="updoc"/>
    	<html:hidden property="updoc" name="reqinfo"  value="<%bean.getUpdoc() %>" />  
    </template:formTr>
    <template:formTr name="ʩ������">
    <%
      String reacce1 = "";
      LineCutBean bean1 = (LineCutBean) request.getAttribute("reqinfo");
      reacce1 = bean1.getWorkacce();
      if (reacce1 == null) {
        reacce1 = "";
      }
    %>
      <apptag:listAttachmentLink fileIdList="<%=reacce1%>"/>
    </template:formTr>
  </template:formTable>
  <hr />
  <html:form action="/LineCutAcceptAction?method=acceptOneWork" styleId="addApplyForm" enctype="multipart/form-data">
      <html:hidden property="reid" name="reqinfo"/>
     <html:hidden property="name" name="reqinfo" />
      <template:formTable namewidth="100" contentwidth="400" th2="��д" th1="��Ŀ">
        <template:formTr name="������">
        	<bean:write name="LOGIN_USER" property="userName"/>
          <input type="hidden" class="inputtext"  value="<bean:write name="LOGIN_USER" property="userName"/>" style="width:200"/>
        </template:formTr>
        <template:formTr name="��������">
          <html:text property="audittime"  readonly="true" style="width:200; border: 0px;background-color: #F5F5F5;">
            <apptag:date property="audittime">            </apptag:date>
          </html:text>
        </template:formTr>
        <template:formTr name="���ս��">
          <html:select property="auditresult" styleClass="inputtext" style="width:270;">
            <html:option value="ͨ������">ͨ������</html:option>
            <html:option value="δͨ������">δͨ������</html:option>
          </html:select>
        </template:formTr>
        <template:formTr name="���ձ�ע" style="width:270">
          <html:textarea property="auditremark" cols="36" rows="4" style="width:270"title="�벻Ҫ����250�����ֻ���512��Ӣ���ַ������򽫽ضϡ�" styleClass="textarea"/>
        </template:formTr>
        <table id="uploadID" width=500 border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
        	<tbody></tbody>
        </table>
        <template:formSubmit>
           <td>
			<input type="checkbox" onclick="checkOrCancel()" id="sel">ȫѡ&nbsp;
		  </td>
          <td>
          	<input type="hidden" value="0" id="maxSeq">
            <html:button property="action" styleClass="button" onclick="addRowMod()">��Ӹ���</html:button>
          </td>
          <td>
			<input type="button" value="ɾ��ѡ��" onclick="delRow()" class="button">
		  </td>
          <td>
            <html:submit styleClass="button">�ύ</html:submit>
          </td>
          <td>
            <html:reset styleClass="button">ȡ��</html:reset>
          </td>
          <td>
            <input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
          </td>
        </template:formSubmit>
     </template:formTable>
    </html:form>
</logic:equal>




  <!--��ʾһ�����յ���ϸ��Ϣ-->
<logic:equal value="ac10" name="type">
	<br>
  <template:titile value="������ϸ��Ϣ"/>
  <template:formTable namewidth="100" contentwidth="300"  th2="����" th1="��Ŀ">
    <template:formTr name="�������">
        <bean:write name="reqinfo" property="name"/>
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
        <bean:write name="reqinfo" property="reason"/>
    </template:formTr>
    <template:formTr name="��ӵص�">
        <bean:write name="reqinfo" property="address"/>
    </template:formTr>
    <template:formTr name="�������">
        <bean:write name="reqinfo" property="cutType"/>
    </template:formTr>
    <template:formTr name="���ǰ˥��">
        <bean:write name="reqinfo" property="provalue"/>�ֱ�(db)
    </template:formTr>
    <template:formTr name="��Ӱ��ϵͳ">
        <bean:write name="reqinfo" property="efsystem"/>
    </template:formTr>
    <template:formTr name="�޸�����">
    	<bean:write name="reqinfo" property="updoc"/>
    </template:formTr>
    
    <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
    
    <template:formTr name="��Ӻ�˥��">
        <bean:write name="reqinfo" property="endvalue"/>�ֱ�(db)
    </template:formTr>
    <template:formTr name="��������">
        <bean:write name="reqinfo" property="manpower"/>����
    </template:formTr>
    <template:formTr name="�����ʱ">
        <bean:write name="reqinfo" property="usetime"/>Сʱ
    </template:formTr>
    <template:formTr name="���ʱ��">
      <bean:write name="reqinfo" property="essetime"/>
    </template:formTr>
    
     <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
    
    <template:formTr name="����ʱ��">
       <bean:write name="reqinfo" property="audittime"/>
    </template:formTr>
    <template:formTr name="���ս��">
        <bean:write name="reqinfo" property="auditresult"/>
    </template:formTr>
    <template:formTr name="���ձ�ע">
        <bean:write name="reqinfo" property="auditremark"/>
    </template:formTr>
    
    <template:formTr name="���ո���">
    <%
      String reacce1 = "";
      LineCutBean bean1 = (LineCutBean) request.getAttribute("reqinfo");
      reacce1 = bean1.getAuditacce();
      if (reacce1 == null) {
        reacce1 = "";
      }
    %>
      <apptag:listAttachmentLink fileIdList="<%=reacce1%>"/>
    </template:formTr>
  </template:formTable>
  <p align="center"><input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"></p>
 </logic:equal>

	<logic:equal value="ac3" name="type"scope="session" >
	      	<br />
	        <template:titile value="���������Ҹ������"/>
	        <html:form action="/LineCutAcceptAction?method=doQuery"   styleId="queryForm2" >
            	<template:formTable namewidth="100" contentwidth="250">
	        		<template:formTr name="�������">
				      <html:text property="name"  styleClass="inputtext" style="width:180" />
				    </template:formTr>
				    <template:formTr name="��ӵص�">
				      <html:text property="address"  styleClass="inputtext" style="width:180" />
				    </template:formTr>
				    
				    
				    <template:formTr name="����ύ��λ">
				    	<select class="inputtext" name="contractorid" style="width:180"> 
				    		<option value="">===ѡ�������ύ��λ===</option>
				    		<logic:iterate id="cl" name="reConList">
				    			<option value="<bean:write name="cl" property="contractorID"/>"><bean:write name="cl" property="contractorName"/></option>
				    		</logic:iterate>
				    	</select>
				    </template:formTr>
				    
				    <template:formTr name="���ս��">
                      <select name="auditresult" Class="inputtext" style="width:180">
                          <option value="">����</option>
                          <option value="ͨ������">ͨ������</option>
                          <option value="δͨ������">δͨ������</option>
                      </select>
				    </template:formTr>
	                <template:formTr name="���տ�ʼʱ��">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='����'  readonly="readonly" ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formTr name="���ս���ʱ��">
	                	<input  id="end" type="text"  name="endtime" readonly="readonly" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='����' ID='btn'  onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formSubmit>
						      	<td>
		                          <html:submit property="action" styleClass="button" >����</html:submit>
						      	</td>
						      	<td>
						       	 	<html:reset property="action" styleClass="button" >ȡ��</html:reset>
						      	</td>
						    </template:formSubmit>
	        	</template:formTable>

	    	</html:form>
	    </logic:equal>
	</body>
</html>
