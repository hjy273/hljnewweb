<%@include file="/common/header.jsp"%>

<html>
<head>
<script type="" language="javascript">
function downloadFile() {
  location.href = "${ctx}/GroupCustomerAction.do?method=downloadTemplet";
}


function isValidForm() {   
    if(document.forms[0].bestrowrange.value == "") {
		alert("预覆盖范围不能为空！");
		document.forms[0].bestrowrange.focus();
		return false;
	} else {
		if(isNaN(document.forms[0].bestrowrange.value)) {
			alert("预覆盖范围必须为数字！");
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
<!--条件查询显示-->
<logic:equal value="1" name="type"scope="session" >
	<br />
	<template:titile value="按条件查找分析客户"/>
	<html:form action="/GroupCustomerParserAction?method=doCustomerParser"  >
    	<template:formTable namewidth="100" contentwidth="250">
           <template:formTr name="客户类型" isOdd="false">
      		<html:select property="grouptype" styleClass="inputtext" style="width:200" >
      			<html:option value="">不限</html:option>
      			<html:option value="A">A</html:option>
      			<html:option value="B">B</html:option>
      			<html:option value="C">C</html:option>
      		</html:select>
    	</template:formTr>
    	<template:formTr name="所属区" isOdd="true">
      		<html:select property="city" styleClass="inputtext" style="width:200" >
      			<html:option value="">不限</html:option>
      			<html:option value="禅城">禅城</html:option>
      			<html:option value="南海">南海</html:option>
      			<html:option value="顺德">顺德</html:option>
      			<html:option value="三水">三水</html:option>
      			<html:option value="高明">高明</html:option>
      		</html:select>
    	</template:formTr>  
      	<template:formTr name="预覆盖范围(米)" isOdd="false">
      			<html:text property="bestrowrange" styleClass="inputtext" style="width:200" maxlength="45"/>
    	</template:formTr>
    	<logic:present name="regionlist">
    	<logic:notEmpty name="regionlist"> 
        <template:formTr name="区域" isOdd="false">    	    	
    	    	<select name="regionid" class="inputtext" style="width:200">
    	    		<option value="">不限</option>
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
	            <html:button onclick="isValidForm()" property="action" styleClass="button" >查找</html:button>
	      	</td>
	      	<td>
	       		<html:reset property="action" styleClass="button" >重置</html:reset>
	      	</td>
		</template:formSubmit>
      	</template:formTable>
	</html:form>
</logic:equal>

<!--显示客户资料列表-->
    <logic:equal  name="type" scope="session" value="2">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<html:form action="/GroupCustomerParserAction?method=doCustomerParser">
		
		<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>
			客户分析一览表
			<font face="宋体" size="2" color="red" >
			(客户总数：<bean:write name="customernum"/>个; 
			 预覆盖率：<bean:write name="parsernum"/>/<bean:write name="customernum"/> = <bean:write name="parserpercent"/>)</font>
		</div>
		<hr width='100%' size='1'>
		<font face="宋体" size="2" color="red" >
			<logic:notEmpty name="querybean" property="city">
				<bean:write name="querybean" property="city"/>区
			</logic:notEmpty>
			<logic:empty name="querybean" property="city">
				所有区域
			</logic:empty>
			<logic:notEmpty name="querybean" property="grouptype">
				<bean:write name="querybean" property="grouptype"/>类
			</logic:notEmpty>			
			<logic:empty name="querybean" property="grouptype">
				所有类型
			</logic:empty>
			
			
			的集团客户的预覆盖范围为
			<logic:notEmpty name="querybean" property="bestrowrange">
			<bean:write name="querybean" property="bestrowrange"/>
			</logic:notEmpty>
			米的预覆盖率为<bean:write name="parserpercent"/>。
		</font>
		
        <display:table name="sessionScope.parserlist" requestURI="${ctx}/GroupCustomerParserAction.do?method=doCustomerParser" id="currentRowObject" pagesize="18" style="width:100%">
        	<display:column property="city" title="所属区" style="width:80px" sortable="true"/>
        	<display:column  property="groupname" title="客户名称" sortable="true" maxLength="25"/>         	
            <display:column property="grouptype" title="客户类型" style="width:80px" sortable="true"/>
            <display:column property="len" title="预覆盖距离(米)"  style="width:100px" maxLength="8" sortable="true"/>             
		</display:table>
		
   <html:link action="/GroupCustomerParserAction.do?method=exportGroupCustomerParserResult">导出为Excel文件</html:link>
    </html:form>
</logic:equal>
</body>
</html>
