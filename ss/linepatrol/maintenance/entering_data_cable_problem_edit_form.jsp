<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>���������¼</title>
		
	</head>

	<body>
		<html:form action="/enteringCableDataAction.do?method=addCableProblem"
			styleId="saveProblem"  method="post" >
			<input type="hidden" name="index" value="${index}"/>
			<input type="hidden" name="id" value="${problem.id}"/>
		    <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			    <tr  class=trwhite>
			      <td class="tdulleft">����������</td>
			      <td class="tdulright">
			       <html:textarea property="problemDescription" value="${problem.problemDescription}" styleClass="max-length-512" rows="4" style="width:275px"></html:textarea>&nbsp;&nbsp;<font color="red">*</font>  
			      </td>
			    </tr>
			     <tr  class=trcolor>
			      <td class="tdulleft">�������˵����</td>
			      <td class="tdulright">
			       <html:textarea property="processComment" value="${problem.processComment}"  styleClass="max-length-512" rows="4" style="width:275px"></html:textarea>
			      </td>
			    </tr>
			         <tr  class=trwhite>
			      <td class="tdulleft">�Ƿ��ѽ����</td>
			      <td class="tdulright">
			      	<c:if test="${problem.problemState==1}">
			      	    <input name="problemState" type="radio" value="1" checked="checked" />
						      �ѽ��
						<input type="radio" name="problemState" value="0" />
						  δ���
			      	</c:if>
				     <c:if test="${problem.problemState==0}">
			      	    <input name="problemState" type="radio" value="1"  />
						      �ѽ��
						<input type="radio" name="problemState" value="0" checked="checked"/>
						  δ���
			      	</c:if> 	
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
