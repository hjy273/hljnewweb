<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>查看已经录入的光缆信息</title>
		<script type='text/javascript'>
		
	    </script>
	</head>

	<body>
		<template:titile value="查询光缆" />
		<html:form action="/testPlanQueryStatAction.do?method=getCablesList" >
	       <table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			    <logic:equal value="1" name="LOGIN_USER" property="deptype">
			      <tr class=trcolor>
			      <td class="tdulleft">测试单位：</td>
			      <td class="tdulright">
			        <select name="contractorId" style="width:155px">
			             <option value="">不限</option>
				         <c:forEach items="${cons}" var="con">
				          <option value="${con.contractorID}"><c:out value="${con.contractorName}"></c:out></option>
				         </c:forEach>
			        </select>
			      </td>
			    </tr>
			    </logic:equal>
			    <tr  class=trwhite>
			      <td class="tdulleft">光缆级别：</td>
			      <td class="tdulright">
			      	<apptag:quickLoadList cssClass="select" style="width:155px"  id="cableLevel" name="cableLevel" listName="cabletype" type="select" isQuery="true"></apptag:quickLoadList>
				  </td>
			    </tr>
			    <tr class=trcolor>
			      <td class="tdulleft">光缆测试日期：</td>
			      <td class="tdulright">
			       <input name="planEndTimeStart" id="planEndTimeStart" class="Wdate" style="width:205"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly/>
			      	  至
			         <input name="planEndTimeEnd" id="planEndTimeEnd" class="Wdate" style="width:205"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate: '#F{$dp.$D(\'planEndTimeStart\')}'})" readonly/>
			    </td>
			    </tr>
			    <tr  class=trwhite>
			      <td class="tdulleft">光缆名称：</td>
			      <td class="tdulright">
			      	<input type="text" id="cableName" name="cableName"/>
				  </td>
			    </tr>
			    <td colspan="2" style="text-align:center;">
					<input name="action" class="button" value="查询"	 type="submit" />
				</td>
			  </table>
		</html:form>
	</body>
</html>
