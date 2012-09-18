<%@include file="/common/header.jsp"%>

<html>
<head>
<script type="" language="javascript">
function downloadFile() {
  location.href = "${ctx}/GroupCustomerAction.do?method=downloadTemplet";
}


function isValidForm() {   
    if(document.forms[0].bestrowrange.value == "") {
		alert("Ԥ���Ƿ�Χ����Ϊ�գ�");
		document.forms[0].bestrowrange.focus();
		return false;
	} else {
		if(isNaN(document.forms[0].bestrowrange.value)) {
			alert("Ԥ���Ƿ�Χ����Ϊ���֣�");
			document.forms[0].bestrowrange.focus();
			return false;
		}
	}
    document.forms[0].submit();
}

</script>
<title>GroupCustomer</title>
</head>
<style type="text/css">
.subject{text-align:center;}
</style>
<body>	  
<!--������ѯ��ʾ-->
<logic:equal value="1" name="type"scope="session" >
	<br />
	<template:titile value="���������ҷ����ͻ�"/>
	<html:form action="/GroupCustomerParserAction?method=doCustomerParser"  >
    	<template:formTable namewidth="100" contentwidth="250">
           <template:formTr name="�ͻ�����" isOdd="false">
      		<html:select property="grouptype" styleClass="inputtext" style="width:200" >
      			<html:option value="">����</html:option>
      			<html:option value="A">A</html:option>
      			<html:option value="B">B</html:option>
      			<html:option value="C">C</html:option>
      		</html:select>
    	</template:formTr>
    	<template:formTr name="������" isOdd="true">
      		<html:select property="city" styleClass="inputtext" style="width:200" >
      			<html:option value="">����</html:option>
      			<html:option value="����">����</html:option>
      			<html:option value="�Ϻ�">�Ϻ�</html:option>
      			<html:option value="˳��">˳��</html:option>
      			<html:option value="��ˮ">��ˮ</html:option>
      			<html:option value="����">����</html:option>
      		</html:select>
    	</template:formTr>  
      	<template:formTr name="Ԥ���Ƿ�Χ(��)" isOdd="false">
      			<html:text property="bestrowrange" styleClass="inputtext" style="width:200" maxlength="45"/>
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
	            <html:button onclick="isValidForm()" property="action" styleClass="button" >����</html:button>
	      	</td>
	      	<td>
	       		<html:reset property="action" styleClass="button" >����</html:reset>
	      	</td>
		</template:formSubmit>
      	</template:formTable>
	</html:form>
</logic:equal>

<!--��ʾ�ͻ������б�-->
    <logic:equal  name="type" scope="session" value="2">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<html:form action="/GroupCustomerParserAction?method=doCustomerParser">
		
		<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>
			�ͻ�����һ����
			<font face="����" size="2" color="red" >
			(�ͻ�������<bean:write name="customernum"/>��; 
			 Ԥ�����ʣ�<bean:write name="parsernum"/>/<bean:write name="customernum"/> = <bean:write name="parserpercent"/>)</font>
		</div>
		<hr width='100%' size='1'>
		<font face="����" size="2" color="red" >
			<logic:notEmpty name="querybean" property="city">
				<bean:write name="querybean" property="city"/>��
			</logic:notEmpty>
			<logic:empty name="querybean" property="city">
				��������
			</logic:empty>
			<logic:notEmpty name="querybean" property="grouptype">
				<bean:write name="querybean" property="grouptype"/>��
			</logic:notEmpty>			
			<logic:empty name="querybean" property="grouptype">
				��������
			</logic:empty>
			
			
			�ļ��ſͻ���Ԥ���Ƿ�ΧΪ
			<logic:notEmpty name="querybean" property="bestrowrange">
			<bean:write name="querybean" property="bestrowrange"/>
			</logic:notEmpty>
			�׵�Ԥ������Ϊ<bean:write name="parserpercent"/>��
		</font>
		
        <display:table name="sessionScope.parserlist" requestURI="${ctx}/GroupCustomerParserAction.do?method=doCustomerParser" id="currentRowObject" pagesize="18" style="width:100%">
        	<display:column property="city" title="������" style="width:80px" sortable="true"/>
        	<display:column  property="groupname" title="�ͻ�����" sortable="true" maxLength="25"/>         	
            <display:column property="grouptype" title="�ͻ�����" style="width:80px" sortable="true"/>
            <display:column property="len" title="Ԥ���Ǿ���(��)"  style="width:100px" maxLength="8" sortable="true"/>             
		</display:table>
		
   <html:link action="/GroupCustomerParserAction.do?method=exportGroupCustomerParserResult">����ΪExcel�ļ�</html:link>
    </html:form>
</logic:equal>
</body>
</html>
