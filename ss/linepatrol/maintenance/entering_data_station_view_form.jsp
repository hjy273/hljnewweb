<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
	</head>
	<body>
		<template:titile value="基站录入数据详细信息" />
		<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		    <tr class=trcolor >
		      <td class="tdulleft"><div align="right">基站名称：</div></td>
		      <td class="tdulright" colspan="3"><c:out value="${station.stationName}"></c:out></td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">测试时间：</td>
		       <td class="tdulright">
		       <bean:write name="data" property="factTestTime" format="yyyy/MM/dd"/>
		      </td>
		      <td class="tdulleft">测试天气：</td>
		      <td class="tdulright">
		      ${data.testWeather}
		      </td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft"> 测试人员：</td>
		      <td class="tdulright" colspan="3">
			 	 <apptag:testman spanId="tester" hiddenId="tester" state="view" value="${data.id}" tablename="station"></apptag:testman>
			  </td>
		    </tr>
		     <tr class=trwhite>
		      <td class="tdulleft">测试地点：</td>
		      <td class="tdulright" colspan="3" >
			      ${data.testAddress}
			   </td>
		    </tr>
		    <tr>
		      <td height="25" colspan="4" bgcolor="#0099FF"><span class="STYLE1">测试参数设置</span></td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft">测试仪表：</td>
		      <td class="tdulright" colspan="3">
		       ${data.testApparatus}
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">测试方法：</td>
		       <td class="tdulright" colspan="3">
		       	    <c:if test="${data.testMethod=='1'}">
				                      三极法
		       	    </c:if>
		       	    <c:if test="${data.testMethod=='0'}">
			    	钳表法  
			    	</c:if>
			  </td>
		    </tr>
		     <tr class=trcolor>
		      <td class="tdulleft">测试阻值：</td>
		      <td class="tdulright" colspan="3">
		      	${data.resistanceValue}&Omega;
		      </td>
		    </tr>
		     <tr class=trwhite>
		      <td class="tdulleft">是否合格：</td>
		      <td class="tdulright" colspan="3">
		      <c:if test="${data.isEligible=='1'}">
			                     合格
		      </c:if>
		       <c:if test="${data.isEligible=='0'}">
			    	不合格  
		      </c:if>
			       (&le;5&Omega;合格)
			  </td>
		    </tr>
		    <tr >
		      <td height="25" colspan="4" bgcolor="#0099FF">问题记录</td>
		    </tr>
		    <tr class=trcolor>
		      
		      <td class="tdulleft">问题简述：</td>
		      <td class="tdulright" colspan="3">
		      		${data.problemComment}
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">处理方法：</td>
		      <td class="tdulright" colspan="3">
					${data.disposeMethod}
			  </td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft">备 注：</td>
		      <td class="tdulright" colspan="3">
		      	${data.remark}
		      </td>
		    </tr>
			  <tr class=trcolor>
			      <td colspan="4" align="center">
			        <input type="button" class="button" onclick="javascript:history.back();" value="返回" />
			      </td>
			  </tr>
		 </table>
	</body>
</html>
