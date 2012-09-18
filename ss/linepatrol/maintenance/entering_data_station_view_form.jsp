<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
	</head>
	<body>
		<template:titile value="��վ¼��������ϸ��Ϣ" />
		<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		    <tr class=trcolor >
		      <td class="tdulleft"><div align="right">��վ���ƣ�</div></td>
		      <td class="tdulright" colspan="3"><c:out value="${station.stationName}"></c:out></td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">����ʱ�䣺</td>
		       <td class="tdulright">
		       <bean:write name="data" property="factTestTime" format="yyyy/MM/dd"/>
		      </td>
		      <td class="tdulleft">����������</td>
		      <td class="tdulright">
		      ${data.testWeather}
		      </td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft"> ������Ա��</td>
		      <td class="tdulright" colspan="3">
			 	 <apptag:testman spanId="tester" hiddenId="tester" state="view" value="${data.id}" tablename="station"></apptag:testman>
			  </td>
		    </tr>
		     <tr class=trwhite>
		      <td class="tdulleft">���Եص㣺</td>
		      <td class="tdulright" colspan="3" >
			      ${data.testAddress}
			   </td>
		    </tr>
		    <tr>
		      <td height="25" colspan="4" bgcolor="#0099FF"><span class="STYLE1">���Բ�������</span></td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft">�����Ǳ�</td>
		      <td class="tdulright" colspan="3">
		       ${data.testApparatus}
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">���Է�����</td>
		       <td class="tdulright" colspan="3">
		       	    <c:if test="${data.testMethod=='1'}">
				                      ������
		       	    </c:if>
		       	    <c:if test="${data.testMethod=='0'}">
			    	ǯ��  
			    	</c:if>
			  </td>
		    </tr>
		     <tr class=trcolor>
		      <td class="tdulleft">������ֵ��</td>
		      <td class="tdulright" colspan="3">
		      	${data.resistanceValue}&Omega;
		      </td>
		    </tr>
		     <tr class=trwhite>
		      <td class="tdulleft">�Ƿ�ϸ�</td>
		      <td class="tdulright" colspan="3">
		      <c:if test="${data.isEligible=='1'}">
			                     �ϸ�
		      </c:if>
		       <c:if test="${data.isEligible=='0'}">
			    	���ϸ�  
		      </c:if>
			       (&le;5&Omega;�ϸ�)
			  </td>
		    </tr>
		    <tr >
		      <td height="25" colspan="4" bgcolor="#0099FF">�����¼</td>
		    </tr>
		    <tr class=trcolor>
		      
		      <td class="tdulleft">���������</td>
		      <td class="tdulright" colspan="3">
		      		${data.problemComment}
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">��������</td>
		      <td class="tdulright" colspan="3">
					${data.disposeMethod}
			  </td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft">�� ע��</td>
		      <td class="tdulright" colspan="3">
		      	${data.remark}
		      </td>
		    </tr>
			  <tr class=trcolor>
			      <td colspan="4" align="center">
			        <input type="button" class="button" onclick="javascript:history.back();" value="����" />
			      </td>
			  </tr>
		 </table>
	</body>
</html>
