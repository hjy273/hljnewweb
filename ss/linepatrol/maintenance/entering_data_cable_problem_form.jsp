<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>���������¼</title>
		
	</head>

	<body>
		<html:form action="/enteringCableDataAction.do?method=addCableProblem"
			styleId="saveProblem"  method="post" >
		    <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			    <tr  class=trwhite>
			      <td class="tdulleft">����������</td>
			      <td class="tdulright">
			       <html:textarea property="problemDescription" styleClass="max-length-512" rows="4" style="width:275px"></html:textarea>&nbsp;&nbsp;<font color="red">*</font>  
			      </td>
			    </tr>
			     <tr  class=trcolor>
			      <td class="tdulleft">�������˵����</td>
			      <td class="tdulright">
			       <html:textarea property="processComment" styleClass="max-length-512" rows="4" style="width:275px"></html:textarea>
			      </td>
			    </tr>
			     <tr  class=trwhite>
			      <td class="tdulleft">�Ƿ��ѽ����</td>
			      <td class="tdulright">
				      	<input name="problemState" type="radio" value="1" checked="checked" />
						      �ѽ��
						<input type="radio" name="problemState" value="0" />
						  δ���
			      </td>
			    </tr>
			    <tr class=trcolor>
			      <td align="center" colspan="2">
			        <input type="button"  onclick="saveproblem();" class="button" value="����"/>
			        <input type="button"  onclick="javascript:window.close();" class="button" value="�ر�"/>
			      </td>
			    </tr>
			  </table>
		</html:form>
	</body>
</html>
