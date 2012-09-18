<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<html>
<head>
<title>查看公告通知</title>
<style>
.hiInput {
    width: 100%;
    background-color:transparent;
    border-bottom-width: 1px;
    border-top-style: none;
    border-right-style: none;
    border-bottom-style: solid;
    border-left-style: none;
    border-bottom-color: #000000;
}
</style>
</head>

<body class=""  topmargin="5">
<html:form  method="Post" action="/NoticeAction.do?method=updateNotice" >
<br>
<div align="center" class="title2" style="font-size:14px"><bean:write name="notice" property="title"/></div>
<br>
  <table border="0" width="90%" cellpadding="1" class="tabout" cellspacing="1" align="center" bgcolor="#999999">
   
    <tr>
    <td width="33" class=trcolor>类型</td>
    <td width="72" bgcolor="#FFFFFF"><bean:write name="notice" property="type"/></td>
    <td width="66" class=trcolor>重要程度</td>
    <td width="31"  bgcolor="#FFFFFF"><bean:write name="notice" property="grade"/></td>
    <td width="44" class=trcolor>发布人</td>
    <td width="128" bgcolor="#FFFFFF"><bean:write name="notice" property="issueperson"/></td>
    <td width="79" class=trcolor>发布时间</td>
    <td width="165" bgcolor="#FFFFFF" align="right">
    <bean:write name="notice" property="issuedate" format="yyyy/MM/dd HH:mm:ss" /></td>
    </tr>
     <tr class=trcolor>
     <td>内容</td>
     <td  height="163" colspan="7" bgcolor="#FFFFFF" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;
          <bean:write name="notice" property="content"/></td>
     </tr>
     
		<logic:empty name="notice" property="fileinfo">
      	</logic:empty>
      	<logic:notEmpty name="notice" property="fileinfo">
      	<tr class=trwhite>
      	<td class=trcolor>附件</td>
      	<td colspan="7">
        <bean:define id="temp" name="notice" property="fileinfo" type="java.lang.String"/>
        	<apptag:listAttachmentLink fileIdList="<%=temp%>"/>
        </td>
    </tr>	
      	</logic:notEmpty>
	 
  </table>
  <p align="center">
    <input name="button" type="button" class="BigButton" onClick="javascript:window.close();" value="关 闭">
  </p>

</html:form>
</body>
</html>