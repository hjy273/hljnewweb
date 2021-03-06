<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>增加问题记录</title>
		
	</head>

	<body>
		<html:form action="/enteringCableDataAction.do?method=addCableProblem"
			styleId="saveProblem"  method="post" >
			<input type="hidden" name="index" value="${index}"/>
			<input type="hidden" name="id" value="${problem.id}"/>
		    <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			    <tr  class=trwhite>
			      <td class="tdulleft">问题描述：</td>
			      <td class="tdulright">
			       <html:textarea property="problemDescription" value="${problem.problemDescription}" styleClass="max-length-512" rows="4" style="width:275px"></html:textarea>&nbsp;&nbsp;<font color="red">*</font>  
			      </td>
			    </tr>
			     <tr  class=trcolor>
			      <td class="tdulleft">处理跟踪说明：</td>
			      <td class="tdulright">
			       <html:textarea property="processComment" value="${problem.processComment}"  styleClass="max-length-512" rows="4" style="width:275px"></html:textarea>
			      </td>
			    </tr>
			         <tr  class=trwhite>
			      <td class="tdulleft">是否已解决：</td>
			      <td class="tdulright">
			      	<c:if test="${problem.problemState==1}">
			      	    <input name="problemState" type="radio" value="1" checked="checked" />
						      已解决
						<input type="radio" name="problemState" value="0" />
						  未解决
			      	</c:if>
				     <c:if test="${problem.problemState==0}">
			      	    <input name="problemState" type="radio" value="1"  />
						      已解决
						<input type="radio" name="problemState" value="0" checked="checked"/>
						  未解决
			      	</c:if> 	
			      </td>
			    </tr>
			    <tr class=trcolor>
			      <td align="center" colspan="2">
			        <input type="button"  onclick="saveproblem();" class="button" value="保存"/>
			        <input type="button"  onclick="javascript:window.close();" class="button" value="关闭"/>
			      </td>
			    </tr>
			  </table>
		</html:form>
	</body>
</html>
