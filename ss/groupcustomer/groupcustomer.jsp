<%@include file="/common/header.jsp"%>

<html>
<head>
<script type="" language="javascript">
function downloadFile() {
  location.href = "${ctx}/GroupCustomerAction.do?method=downloadTemplet";
}

 //�������뼯�ſͻ�����ʱ����ҳ��
function showupload() {
    var objpart = document.getElementById("groupDivId");
    var objup = document.getElementById("upDivId");
    objpart.style.display="none";
    objup.style.display="block";
}

function noupload()
{
    var objpart = document.getElementById("groupDivId");
    var objup = document.getElementById("upDivId");

    objpart.style.display="block";
    objup.style.display="none";
}

function isValidForm() {   
    if(document.forms[0].groupid.value == "") {
		alert("�ͻ���Ų��ܲ��գ�");
		document.forms[0].groupid.focus();
		return false;
	}
	if(document.forms[0].x.value == "") {
		alert("���Ȳ���Ϊ�գ�");
		document.forms[0].x.focus();
		return false;
	}
	if(isNaN(document.forms[0].x.value)) {
		alert("���ȱ���Ϊ���֣�");
		document.forms[0].x.focus();
		return false;
	}
	if(document.forms[0].y.value == "") {
		alert("γ�Ȳ���Ϊ�գ�");
		document.forms[0].y.focus();
		return false;
	}
	if(isNaN(document.forms[0].y.value)) {
		alert("γ�ȱ���Ϊ���֣�");
		document.forms[0].y.focus();
		return false;
	}
    document.forms[0].submit();
}

function toGetForm(id) {
	document.forms[0].action = "${ctx}/GroupCustomerAction.do?method=showOneCustomer&id="+id;
	document.forms[0].submit();
}

function toUpForm(id) {
	document.forms[0].action = "${ctx}/GroupCustomerAction.do?method=showUpCustomer&id="+id;
	document.forms[0].submit();
}

function toDelForm(id) {
	if(confirm('ȷ��ɾ���ͻ�����')) {
		document.forms[0].action = "${ctx}/GroupCustomerAction.do?method=delCustomer&id="+id;
		document.forms[0].submit();
	}
}

function toGoBack() {
	document.forms[0].action = "${ctx}/GroupCustomerAction.do?method=doBack";
	document.forms[0].submit();
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
			var allChecks = document.getElementsByName('ifCheck');
			var length = allChecks.length;
			for(var i = 0; i < length; i++) {
				allChecks[i].checked = true;
			}
		}
		
		function cancelCheck() {
			var allChecks = document.getElementsByName('ifCheck');
			var length = allChecks.length;
			for(var i = 0; i < length; i++) {
				allChecks[i].checked = false;
			}
		}
		
		function delSel() {
			var idStr = null;
			var delChecks = document.getElementsByName('ifCheck');
			var delLength = delChecks.length;
			var length = 0;
			for(var i = 0; i < delLength; i++) {
				if(delChecks[i].checked == true) {
					length++;
					if(idStr == null) {
						idStr = "'" + delChecks[i].value + "'";
					} else {
						idStr += ",'" + delChecks[i].value + "'";
					}
				}
			}
			if(length == 0) {
				alert("��ѡ������ɾ���Ŀͻ�����!");
				return;
			}
			if(!confirm("��ȷ��Ҫִ�д˴�ɾ��������?")) {
    			return;
    		} else {
				var url= "${ctx}/GroupCustomerAction.do?method=delCustomers";
    			document.forms[0].action = url;
				document.forms[0].submit();
    		}
		}
</script>
<title>GroupCustomer</title>
</head>
<style type="text/css">
.subject{text-align:center;}
</style>
<body>	
  <!--��д���ſͻ�����-->
<logic:equal value="1" scope="session" name="type">
  <link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
  <div id="groupDivId">
    <br/>
    <template:titile value="��д���ſͻ�����"/>
    <html:form action="/GroupCustomerAction?method=addGroupCustomer" method="post">
      <template:formTable contentwidth="250" namewidth="250">
    	<template:formTr name="�ͻ����" isOdd="false">
      		<html:text property="groupid" styleClass="inputtext" style="width:200" maxlength="10"/>
    	</template:formTr>
    	<template:formTr name="������" isOdd="true">
      		<html:select property="city" styleClass="inputtext" style="width:200" >
      			<html:option value="����">����</html:option>
      			<html:option value="�Ϻ�">�Ϻ�</html:option>
      			<html:option value="˳��">˳��</html:option>
      			<html:option value="��ˮ">��ˮ</html:option>
      			<html:option value="����">����</html:option>
      		</html:select>
    	</template:formTr>  	
    	<template:formTr name="�ͻ�����" isOdd="false">
      		<html:select property="grouptype" styleClass="inputtext" style="width:200" >
      			<html:option value="A">A</html:option>
      			<html:option value="B">B</html:option>
      			<html:option value="C">C</html:option>
      		</html:select>
    	</template:formTr>
    	<template:formTr name="�ͻ�����" isOdd="true">
      		<html:text property="groupname" styleClass="inputtext" style="width:200" maxlength="45"/>
    	</template:formTr>
    	<template:formTr name="�ͻ���ַ" isOdd="false">
      		<html:text property="groupaddr" styleClass="inputtext" style="width:200" maxlength="45"/>
    	</template:formTr>
    	<template:formTr name="����" isOdd="true">
      		<html:text property="x" styleClass="inputtext" style="width:200" maxlength="20"/>
    	</template:formTr>
    	<template:formTr name="γ��" isOdd="false">
      		<html:text property="y" styleClass="inputtext" style="width:200" maxlength="20"/>
    	</template:formTr>
    	<template:formTr name="����" isOdd="true">
    	    <logic:notEmpty name="regionlist"> 
    	    	<logic:present name="regionlist">
    	    	<select name="regionid" class="inputtext" style="width:200">
    	    		<logic:iterate id="element" name="regionlist">
    	    			<option value="<bean:write name="element" property="regionid"/>">
    	    				<bean:write name="element" property="regionname"/>
    	    			</option>
    	    		</logic:iterate>   	   
    	    	</select>    	    	 	
    	    	</logic:present>
    	    </logic:notEmpty>
    	</template:formTr>
    	<template:formTr name="ҵ������" isOdd="false">    	
    		<html:select property="operationtype" styleClass="inputtext" style="width:200" >
      			<html:option value="Wlan">Wlan</html:option>
      			<html:option value="IP">IP</html:option>
      			<html:option value="����">����</html:option>
      			<html:option value="����">����</html:option>
      		</html:select>
    	</template:formTr>
    	<template:formTr name="�ͻ�����" isOdd="true">
      		<html:text property="customermanager" styleClass="inputtext" style="width:200" maxlength="45"/>
    	</template:formTr>
    	<template:formTr name="�ͻ�����绰" isOdd="false">
      		<html:text property="tel" styleClass="inputtext" style="width:200" maxlength="45"/>
    	</template:formTr>
    	<template:formTr name="���ſͻ��绰" isOdd="true">
      		<html:text property="grouptel" styleClass="inputtext" style="width:200" maxlength="45"/>
    	</template:formTr>
     
     	<template:formSubmit>
     		<td>
        	<html:button property="action" onclick="isValidForm()" styleClass="button">����</html:button>
        	<html:reset styleClass="button">����</html:reset>
       	 	<html:button property="action" styleClass="button" styleId="upId" onclick="showupload()">����ͻ�����</html:button>
        	<html:button property="action" styleClass="button2" onclick="downloadFile()">�ͻ�����ģ������</html:button>     
     		</td>
     	</template:formSubmit>
  	</template:formTable> 
  	</html:form> 
  </div>
  <div id="upDivId" style="display:none">
    <html:form styleId="upform" action="/GroupCustomerAction?method=upLoadShow" method="post" enctype="multipart/form-data">
      <table align="center" border="0" width="600" height="100%">
        <tr>
          <td valign="top" height="100%">
            <table align="center" border="0">
              <tr>
                <td align="left" height="50">
                  <br/>
                  <br/>
                  <br/>
                  <br/>
                  <br/>
                  <br/>
                  <br/>
                  <b>��ѡ��Ҫ�����Excel�ļ�:</b>
                  <br/>
                </td>
              </tr>
              <tr>
                <td>
                  <html:file property="file" style="width:300px" styleClass="inputtext"/>
                </td>
              </tr>
              <tr>
                <td align="center" valign="middle" height="60">
                  <html:button styleClass="button" value="��������" property="sub" onclick="javascript:upform.submit()">�ύ</html:button>
                  <html:button value="ȡ������" styleClass="button" property="action" onclick="noupload()"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </html:form>
  </div>
</logic:equal>
<!--������ѯ��ʾ-->
<logic:equal value="2" name="type"scope="session" >
	<br />
	<template:titile value="���������ҿͻ�����"/>
	<html:form action="/GroupCustomerAction?method=doQuery"  >
    	<template:formTable namewidth="100" contentwidth="250">
      		<template:formTr name="�ͻ�����" isOdd="false">
      			<html:text property="groupname" styleClass="inputtext" style="width:200" maxlength="45"/>
    		</template:formTr>
           <template:formTr name="�ͻ�����" isOdd="true">
      		<html:select property="grouptype" styleClass="inputtext" style="width:200" >
      			<html:option value="">����</html:option>
      			<html:option value="A">A</html:option>
      			<html:option value="B">B</html:option>
      			<html:option value="C">C</html:option>
      		</html:select>
    	</template:formTr>
    	<template:formTr name="������" isOdd="false">
      		<html:select property="city" styleClass="inputtext" style="width:200" >
      			<html:option value="">����</html:option>
      			<html:option value="����">����</html:option>
      			<html:option value="�Ϻ�">�Ϻ�</html:option>
      			<html:option value="˳��">˳��</html:option>
      			<html:option value="��ˮ">��ˮ</html:option>
      			<html:option value="����">����</html:option>
      		</html:select>
    	</template:formTr>
    	<template:formTr name="ҵ������" isOdd="true">    	
    		<html:select property="operationtype" styleClass="inputtext" style="width:200" >
    			<html:option value="">����</html:option>
      			<html:option value="Wlan">Wlan</html:option>
      			<html:option value="IP">IP</html:option>
      			<html:option value="����">����</html:option>
      			<html:option value="����">����</html:option>
      		</html:select>
    	</template:formTr> 
    	<logic:present name="regionlist">
    	<logic:notEmpty name="regionlist"> 
        <template:formTr name="����" isOdd="false">    	    	
    	    	<select name="regionid" class="inputtext" style="width:200">
    	    		<option value="">����</option>
    	    		<logic:iterate id="element" name="regionlist">
    	    			<option value="<bean:write name="element" property="regionid"/>">
    	    				<bean:write name="element" property="regionname"/>
    	    			</option>
    	    		</logic:iterate>   	   
    	    	</select> 
    	</template:formTr>
    	</logic:notEmpty>
    	</logic:present>
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

<!--��ʾ�ͻ������б�-->
    <logic:equal  name="type" scope="session" value="3">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<html:form action="/GroupCustomerAction.do?method=showOneCustomer">
		<template:titile value="���ſͻ���Ϣһ����"/>
        <display:table name="sessionScope.customer" requestURI="${ctx}/GroupCustomerAction.do?method=doQuery" id="currentRowObject" pagesize="18" style="width:100%">
        	<%
      			BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      			String id  = null;
      			String groupname = null;
      			String groupnameTitle = null;
      			if(object != null) {
      				id = (String) object.get("id");
      				groupnameTitle = (String)object.get("groupname");
			    	if(groupnameTitle != null && groupnameTitle.length() > 16) {
			    		groupname = groupnameTitle.substring(0,16) + "...";
			    	} else {
			    		groupname = groupnameTitle;
			    	}
      			}
      			
    		%>
    		 <display:column media="html" title="ѡ��" sortable="true" style="width:30px">
            	&nbsp;<input type="checkbox" name="ifCheck" value="<%=id %>"/>
            </display:column>
        	<display:column  media="html" title="�ͻ�����" sortable="true" >
        		<a href="javascript:toGetForm('<%=id%>')" title="<%=groupnameTitle %>"><%=groupname%></a> 
        	</display:column> 
        	<display:column property="city" title="������"  style="width:60px" sortable="true"/>
            <display:column property="operationtype" title="ҵ������"  style="width:60px" maxLength="8" sortable="true"/>
            <display:column property="grouptype" title="�ͻ�����" style="width:60px" sortable="true"/>
            <display:column property="x" title="����"  style="width:70px" maxLength="10" sortable="true"/>           
            <display:column property="y" title="γ��" style="width:70px"  maxLength="10" sortable="true"/>
            <display:column media="html" title="����" sortable="true" style="width:80px">
            	
            	<a href="javascript:toUpForm('<%=id%>')">�޸�</a> |
            	<a href="javascript:toDelForm('<%=id%>')">ɾ��</a>
            </display:column>
		</display:table>
    <input type="checkbox" onclick="checkOrCancel()" id="sel">ȫѡ/ȫ��ѡ&nbsp; <a href="javascript:delSel()">ɾ��ѡ��</a>&nbsp;<html:link action="/GroupCustomerAction.do?method=exportGroupCustomerResult">����ΪExcel�ļ�</html:link>
    </html:form>
</logic:equal>

<!-- �ͻ�������ϸ��Ϣ -->
<logic:equal value="4" scope="session" name="type">
  <link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
    <br/>
    <template:titile value="���ſͻ�������ϸ��Ϣ"/>
    <html:form  action="/GroupCustomerAction?method=doBack" >
      <template:formTable contentwidth="250" namewidth="250">
    	<template:formTr name="�ͻ����" isOdd="false">      		
      		<bean:write name="customeBean" property="groupid"/>
    	</template:formTr>
    	<template:formTr name="������" isOdd="true">
      		<bean:write name="customeBean" property="city"/>
    	</template:formTr>  	
    	<template:formTr name="�ͻ�����" isOdd="false">
    		<bean:write name="customeBean" property="grouptype"/>      		
    	</template:formTr>
    	<template:formTr name="�ͻ�����" isOdd="true">
    		<bean:write name="customeBean" property="groupname"/>      		
    	</template:formTr>
    	<template:formTr name="�ͻ���ַ" isOdd="false">
    		<bean:write name="customeBean" property="groupaddr"/> 
    	</template:formTr>
    	<template:formTr name="����" isOdd="true">
      		<bean:write name="customeBean" property="x"/> 
    	</template:formTr>
    	<template:formTr name="γ��" isOdd="false">
      		<bean:write name="customeBean" property="y"/> 
    	</template:formTr>
    	<template:formTr name="����" isOdd="true">
    	    <bean:write name="customeBean" property="regionname"/> 
    	</template:formTr>
    	<template:formTr name="ҵ������" isOdd="false">  
    		 <bean:write name="customeBean" property="operationtype"/>  
    	</template:formTr>
    	<template:formTr name="�ͻ�����" isOdd="true">
    		<bean:write name="customeBean" property="customermanager"/>  
    	</template:formTr>
    	<template:formTr name="�ͻ�����绰" isOdd="false">	
    		<bean:write name="customeBean" property="tel"/> 
    	</template:formTr>
    	<template:formTr name="���ſͻ��绰" isOdd="true">
      		<bean:write name="customeBean" property="grouptel"/>
    	</template:formTr>
     
     	<template:formSubmit>
     		<td>
        	<input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
     		</td>
     	</template:formSubmit>
  	</template:formTable> 
  	</html:form> 
</logic:equal>

  <!--�޸ļ��ſͻ�����-->
<logic:equal value="5" scope="session" name="type">
  <link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
    <br/>
    <template:titile value="�޸ļ��ſͻ�����"/>
    <html:form onsubmit="return isValidForm()" action="/GroupCustomerAction?method=upCustomer" >
      <template:formTable contentwidth="250" namewidth="250">
      <html:hidden property="id"/>
    	<template:formTr name="�ͻ����" isOdd="false">
      		<html:text property="groupid" styleClass="inputtext" style="width:200" maxlength="10"/>
    	</template:formTr>
    	<template:formTr name="������" isOdd="false">
      		<html:select property="city" styleClass="inputtext" style="width:200" >
      			<html:option value="����">����</html:option>
      			<html:option value="�Ϻ�">�Ϻ�</html:option>
      			<html:option value="˳��">˳��</html:option>
      			<html:option value="��ˮ">��ˮ</html:option>
      			<html:option value="����">����</html:option>
      		</html:select>
    	</template:formTr>  	
    	<template:formTr name="�ͻ�����" isOdd="false">
      		<html:select property="grouptype" styleClass="inputtext" style="width:200" >
      			<html:option value="A">A</html:option>
      			<html:option value="B">B</html:option>
      			<html:option value="C">C</html:option>
      		</html:select>
    	</template:formTr>
    	<template:formTr name="�ͻ�����" isOdd="false">
      		<html:text property="groupname" styleClass="inputtext" style="width:200" maxlength="45"/>
    	</template:formTr>
    	<template:formTr name="�ͻ���ַ" isOdd="false">
      		<html:text property="groupaddr" styleClass="inputtext" style="width:200" maxlength="45"/>
    	</template:formTr>
    	<template:formTr name="����" isOdd="false">
      		<html:text property="x" styleClass="inputtext" style="width:200" maxlength="20"/>
    	</template:formTr>
    	<template:formTr name="γ��" isOdd="false">
      		<html:text property="y" styleClass="inputtext" style="width:200" maxlength="20"/>
    	</template:formTr>
    	<template:formTr name="����" isOdd="false">
    	    <logic:notEmpty name="regionlist"> 
    	    	<logic:present name="regionlist">
    	    	<select name="regionid" class="inputtext" style="width:200">
    	    		<logic:iterate id="element" name="regionlist">
    	    			<option value="<bean:write name="element" property="regionid"/>">
    	    				<bean:write name="element" property="regionname"/>
    	    			</option>
    	    		</logic:iterate>   	   
    	    	</select>    	    	 	
    	    	</logic:present>
    	    </logic:notEmpty>
    	</template:formTr>
    	<template:formTr name="ҵ������" isOdd="false">    	
    		<html:select property="operationtype" styleClass="inputtext" style="width:200" >
      			<html:option value="Wlan">Wlan</html:option>
      			<html:option value="IP">IP</html:option>
      			<html:option value="����">����</html:option>
      			<html:option value="����">����</html:option>
      		</html:select>
    	</template:formTr>
    	<template:formTr name="�ͻ�����" isOdd="false">
      		<html:text property="customermanager" styleClass="inputtext" style="width:200" maxlength="45"/>
    	</template:formTr>
    	<template:formTr name="�ͻ�����绰" isOdd="false">
      		<html:text property="tel" styleClass="inputtext" style="width:200" maxlength="45"/>
    	</template:formTr>
    	<template:formTr name="���ſͻ��绰" isOdd="false">
      		<html:text property="grouptel" styleClass="inputtext" style="width:200" maxlength="45"/>
    	</template:formTr>
     
     	<template:formSubmit>
     		<td>
        	<html:submit styleClass="button">�ύ</html:submit>
        	<html:reset styleClass="button">����</html:reset>
       	 	<input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
     		</td>
     	</template:formSubmit>
  	</template:formTable> 
  	</html:form>   
</logic:equal>
</body>
</html>
