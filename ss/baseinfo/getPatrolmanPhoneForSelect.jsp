<%@include file="/common/header.jsp"%>
<!-- ZSH 2004-10-13 -->
<%@page import="org.apache.struts.util.*,com.cabletech.baseinfo.services.RoleManage,com.cabletech.baseinfo.domainobjects.*"%>
<%
  String selectname = request.getParameter("selectname");
  String contractorid = request.getParameter("contractorid");
  String userid=request.getParameter("userid");
  System.out.println(selectname+" ++ "+contractorid);
  ArrayList list=RoleManage.getDeptPatrolmanPhone(contractorid);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta http-equiv="Pragma" content="no-cache">
<title>移动传输线路巡检管理系统</title>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
<!--
var userid=parent.document.forms[0].elements["userid"].value;
function init(){

    alert(userid);
    for(i=0;i<document.all.phone.options.length;i++){
      if(userid==document.all.phone.options[i].value){
        parent.selectSpan1.simcode.value=document.all.phone.options[i].text;
      }
    }
}

//-->
</script>
<body onload="init()">
<html:form action="/AlertreceiverListAction.do">
  <span id="selectSpan1">
    <select name="phone" style="display:none">
<%
  //System.out.println(list.size());
  for(int i=0;i<list.size();i++){
    LabelValueBean bean=(LabelValueBean)list.get(i);
    //System.out.println(list.get(i));
    String id=bean.getValue();
    String phone=bean.getLabel();
%>
       <option value="<%=id%>"><%=phone%></option>
<%
  }
%>
    </select>
  </span>
</html:form>
</body>
</html>
