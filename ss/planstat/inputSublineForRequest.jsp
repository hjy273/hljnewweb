<%@ include file="/common/header.jsp"%>
<script language="javascript" type="">

</script>

<html>
<head>
</head>
<br>
<body>
<template:titile value="Ѳ���߶�ʵʱ��ѯ"/>
<br>
<form method="post" action="${ctx}/SublineRealTimeAction.do?method=showSublineList">
    <template:formTable contentwidth="200" namewidth="300">
	    <template:formTr name="Ѳ���߶�����" isOdd="false">
	    <input id="sublineName" name="sublineName" type="text" size="30" />
	    </template:formTr>
	    <template:formSubmit>
	      <td>
	        <html:submit styleClass="button">��ѯ</html:submit>
	      </td>	    
	      <td>
	        <html:reset styleClass="button">ȡ��</html:reset>
	      </td>
	    </template:formSubmit>
    </template:formTable>
</form>
 <table width="340" border="0" align="center">
     <tr style="color:red">
       <td width="12%" scope="col" valign="top">˵����</td>
         <td scope="col">
           ֻ�ܲ�ѯ����ǰ���ڽ����еļƻ����漰����Ѳ���߶�<br>
         </td>
     </tr>
</table> 
</body>
</html>



  
