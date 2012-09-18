<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>增加问题记录</title>
		
	</head>

	<body>
		<html:form action="/enteringCableDataAction.do?method=addCableProblem"
			styleId="saveProblem"  method="post" >
		    <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			    <tr  class=trwhite>
			      <td class="tdulleft">问题描述：</td>
			      <td class="tdulright">
			       <html:textarea property="problemDescription" styleClass="max-length-512" rows="4" style="width:275px"></html:textarea>&nbsp;&nbsp;<font color="red">*</font>  
			      </td>
			    </tr>
			     <tr  class=trcolor>
			      <td class="tdulleft">处理跟踪说明：</td>
			      <td class="tdulright">
			       <html:textarea property="processComment" styleClass="max-length-512" rows="4" style="width:275px"></html:textarea>
			      </td>
			    </tr>
			     <tr  class=trwhite>
			      <td class="tdulleft">是否已解决：</td>
			      <td class="tdulright">
				      	<input name="problemState" type="radio" value="1" checked="checked" />
						      已解决
						<input type="radio" name="problemState" value="0" />
						  未解决
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
