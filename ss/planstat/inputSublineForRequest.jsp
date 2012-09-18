<%@ include file="/common/header.jsp"%>
<script language="javascript" type="">

</script>

<html>
<head>
</head>
<br>
<body>
<template:titile value="巡检线段实时查询"/>
<br>
<form method="post" action="${ctx}/SublineRealTimeAction.do?method=showSublineList">
    <template:formTable contentwidth="200" namewidth="300">
	    <template:formTr name="巡检线段名称" isOdd="false">
	    <input id="sublineName" name="sublineName" type="text" size="30" />
	    </template:formTr>
	    <template:formSubmit>
	      <td>
	        <html:submit styleClass="button">查询</html:submit>
	      </td>	    
	      <td>
	        <html:reset styleClass="button">取消</html:reset>
	      </td>
	    </template:formSubmit>
    </template:formTable>
</form>
 <table width="340" border="0" align="center">
     <tr style="color:red">
       <td width="12%" scope="col" valign="top">说明：</td>
         <td scope="col">
           只能查询到当前正在进行中的计划所涉及到的巡检线段<br>
         </td>
     </tr>
</table> 
</body>
</html>



  
