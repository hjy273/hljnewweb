<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="" language="javascript">

    //�鿴��ϸ��ť����
 	function toGetOneAuForm(idValue){
     	var url = "${ctx}/LineCutReAction.do?method=getOneQueryDetail&reid=" + idValue;
        window.location.href=url;
    }
     //�鿴������ϸ���ض���
 	function  AuShowGoBack(){
     	var url = "${ctx}/LineCutReAction.do?method=showAllAu";
        window.location.href=url;
    }
</script>


		<title>lineCutQueryList</title>
	</head>
	<body>
	
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<BR>
			 <%
				DynaBean object2=null;
				String id2= null;
				String numerical = null;
				String name = null;
				String isarchive = null;
				String auditresult = null;
			%>
			<template:titile value="��Ӳ�ѯ�б�" />
			<display:table name="sessionScope.lineCutQueryList" requestURI="${ctx}/LineCutReAction.do?method=queryCutBeanStat" id="currentRowObject"
				pagesize="18">
				<display:column media="html" title="��ˮ��" sortable="true" >
    				<%
						object2 = (DynaBean)pageContext.getAttribute("currentRowObject");
						if(object2 != null) {
							id2 = (String) object2.get("reid");
							numerical = (String)object2.get("numerical");
							numerical = numerical == null? "": numerical;
							name = (String) object2.get("name");
							auditresult = (String)object2.get("auditresult");
							if(name != null) {
								name = name.length() < 10 ? name: name.substring(0,10)+"...";
							} else {
								name = "";
							}
							isarchive = (String) object2.get("isarchive");
						}
					 %>
					 <a href="javascript:toGetOneAuForm('<%=id2%>')"><%=numerical %></a>
    			</display:column>
				
				<display:column media="html"  title="�������" sortable="true" >
					  <a href="javascript:toGetOneAuForm('<%=id2%>')"><%=name %></a>
            	</display:column>
            	
				<display:column property="sublinename" title="�м̶�" maxLength="15"
					style="align:center" sortable="true" />
					
					<display:column property="address" title="��ӵص�" maxLength="10"
					sortable="true" />
					
				<display:column property="prousetime" title="��Ӽƻ���ʱ(Сʱ)" maxLength="15"
					style="align:center" sortable="true" />
				
					<display:column property="usetime" title="���ʵ����ʱ(Сʱ)" maxLength="15"
					style="align:center" sortable="true" />
					
				<display:column property="protime" title="��Ӽƻ�ʱ��" maxLength="18"
					style="align:center" sortable="true" />
					
				<display:column property="essetime" title="���ʵ��ʱ��" maxLength="18"
					style="align:center" sortable="true" />
				<%if(("ͨ������".equals(auditresult) || "δͨ������".equals(auditresult)) && "�Ѿ�����".equals(isarchive)) 
				{%>
					<display:column property="auditresult" title="��ǰ״̬" style="align:center" sortable="true" />
				<% }else{%>
					<display:column property="isarchive" title="��ǰ״̬" style="align:center" sortable="true" />
				<% }%>
					
			</display:table>
			<html:link action="/LineCutReAction.do?method=exportQueryStat">����ΪExcel�ļ�</html:link>


		<iframe id="hiddenIframe" style="display: none"></iframe>
	</body>

</html>
