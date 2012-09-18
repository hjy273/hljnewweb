<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type='text/javascript'>
	    </script>
	</head>
	<body>
		<html:form action="/enteringCableDataAction.do?method=addAnalyseData" styleId="analyseform">
		<table width="98%" border="1" align="center"  bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" bordercolor="#DBDBD8" style="border-collapse: collapse"> 
			<input type="hidden" name="chiseq" value="${chipdata.chipSeq}"/>
		    <tr>
		      <td align="right">�м̶Σ�</td>
		      <td  colspan="3"><c:out value="${trunkName}"></c:out></td>
		      <td align="right">����</td>
		      <td ><c:out value="${chipdata.chipSeq}"></c:out></td>
		    </tr>
		    <tr>
		      <td align="right">���Զˣ�</td>
		      <td  colspan="2">
		      	<c:if test="${chipdata.coreData.abEnd=='A'}">
			        A��			
		      	</c:if>
		      	<c:if test="${chipdata.coreData.abEnd=='B'}">
					B��
				</c:if>
		      </td>
		       <td align="right">���Ի�վ��</td>
		       <td colspan="2" >
		     		${chipdata.coreData.baseStation}
		       </td>
		    </tr>
		      <tr>
			      <td align="right">��¼���ڣ�</td>
			      <td colspan="5">
			        <bean:write name="chipdata" property="coreData.testDate" format="yyyy/MM/dd"/>
			      </td>
		      </tr>
		    <tr >
		      <td height="25px" bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">�����ļ���¼</font></td>
		    </tr>
		    <tr >
		       <td colspan="3" align="center">�����ļ�����</td>
		       <td  colspan="3" align="left">&nbsp;&nbsp;&nbsp;&nbsp;˵��</td>
		    </tr>
		     <tr>
		       <td colspan="3">
		         <apptag:upload state="look" value="${chipdata.attachments}" cssClass="" entityId="${chipdata.id}" entityType="LP_TEST_CHIP_DATA"/>
		       </td>
		       <td colspan="3">
		       	  ${chipdata.coreData.fileRemark}
		       </td>
		    </tr>
		     <tr>
		      <td height="25"  bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">��о���ȷ���</font></td>
		    </tr>
		    <tr >
		       <td align="center">����������</td>
		       <td align="center">��������</td>
		       <td align="center">о��km</td>
		       <td align="center">�Ƿ�������</td>
		       <td align="center">�������</td>
		       <td align="center">��ע</td>
		    </tr>  
		    
		    <tr>
		       <td >
		       		&nbsp;&nbsp;${chipdata.corelength.refractiveIndex}
		       </td>
		       <td >
		       		${chipdata.corelength.pulseWidth}
		       </td>
		       <td >
		       		${chipdata.corelength.coreLength}
		       </td>
		       <td >
			     <c:if test="${chipdata.corelength.isProblem=='1'}">
			                      ��
				 </c:if>
				  <c:if test="${chipdata.corelength.isProblem=='0'}">
			                    û��
				 </c:if>
		       </td>
		       <td >${chipdata.corelength.problemAnalyseLen}</td>
		       <td >${chipdata.corelength.lengremark}</td>
		    </tr>  
		    
		     <tr>
		      <td height="25"  bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">˥����������</font></td>
		    </tr>
		    
		    <tr >
		       <td align="center">˥������dB/km</td>
		       <td align="center">�Ƿ�ϸ�</td>
		       <td align="center" colspan="2">�������</td>
		       <td align="center" colspan="2">��ע</td>
		    </tr>
		     <tr>
		       <td >
		           &nbsp;&nbsp;${chipdata.decay.decayConstant}
		       </td>
		       <td >
		        <c:if test="${chipdata.decay.isStandardDec=='1'}">
			       	�ϸ�
		        </c:if>
		         <c:if test="${chipdata.decay.isStandardDec=='0'}">
				       ���ϸ�
		        </c:if>
		       </td>
		       <td colspan="2">${chipdata.decay.problemAnalyseDec}</td>
		       <td  colspan="2">${chipdata.decay.decayRemark}</td>
		    </tr>    
		    
		    <tr>
		      <td height="25" bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">�ɶ���ķ���</font></td>
		    </tr>
		    
		    <tr >
		       <td align="center">�ɶ����dB</td>
		       <td align="center">�Ƿ�ϸ�</td>
		       <td align="center" colspan="2">�������</td>
		       <td align="center" colspan="2">��ע</td>
		    </tr>  
		    <tr >
		       <td >
		       		&nbsp;&nbsp;${chipdata.endwaste.endWaste}
		       </td>
		       <td>
		        <c:if test="${chipdata.endwaste.isStandardEnd=='1'}">
		        	�ϸ�				   
		        </c:if>
		         <c:if test="${chipdata.endwaste.isStandardEnd=='0'}">
				     ���ϸ�
		        </c:if>
		       </td>
		       <td colspan="2">${chipdata.endwaste.problemAnalyseEnd}</td>
		       <td colspan="2">${chipdata.endwaste.endRemark}</td>
		    </tr>    
		    
		    <tr>
		        <td height="25" bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">��ͷ��ķ���</font>����¼���������������ֵ����0.5dB�Ľ�ͷ��</td>
		    </tr>
		    <tr>
		    	<td colspan="6">
		    	    <table border="1" width="100%" id="waste" align="center"  bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" bordercolor="#DBDBD8" style="border-collapse: collapse">
			    	    <tr>
					       <td align="center">���</td>
					       <td align="center">��ͷλ��</td>
					       <td align="center">���ֵdB</td>
					       <td align="center">�������</td>
					       <td align="center" colspan="2">��ע</td>
					    </tr>  
					    <c:forEach items="${chipdata.conwaste}" var="conwa" varStatus="loop">
				    		<tr>
				    			<td>
				    				&nbsp;&nbsp;${conwa.orderNumber}
				    			</td>
				    			<td>
				    			  ${conwa.connectorStation}
				    			</td>
				    			<td>
				    				${conwa.waste}
				    			</td>
				    			<td>
				    				${conwa.problemAnalyse}
				    			</td>
				    			<td>
				    			   ${conwa.remark}
				    			</td>
				    	    </tr>
				    	</c:forEach>
		    	    </table>
		    	</td>
		    </tr>
		     <tr>
		      <td height="25"  bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">�쳣�¼�����</font>�����������Ƿ����¼��ȣ�</td>
		    </tr>
		    <tr>
		    	<td colspan="6">
		    	    <table border="1" width="100%" id="exec" align="center"  bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" bordercolor="#DBDBD8" style="border-collapse: collapse">
			    	    <tr >
					       <td align="center">���</td>
					       <td align="center">�¼�λ��</td>
					       <td align="center">���ֵdB</td>
					       <td align="center">�������</td>
					       <td align="center" colspan="2">��ע</td>
					    </tr> 
					     <c:forEach items="${chipdata.exceptionEvent}" var="event" varStatus="loop">
				    		<tr>
				    			<input type="hidden" name="proid" id="proid" value=""/>
				    			<td>
				    				&nbsp;&nbsp;${event.orderNumberExe}
				    			</td>
				    			<td>
				    			   ${event.eventStation}
				    			</td>
				    			<td>
				    				${event.wasteExe}
				    			</td>
				    			<td>
				    				${event.problemAnalyseExe}
				    			</td>
				    			<td>
				    			    ${event.remarkExe}
				    			</td>
				    	    </tr>
				    	</c:forEach>
		    	    </table>
		    	</td>
		    </tr>
		  
		    <tr>
		      <td height="25"  bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">��������</font></td>
		    </tr>
		    <tr >
		       <td align="center" colspan="2">��������</td>
		       <td align="center" colspan="2">�������</td>
		       <td align="center" colspan="2">˵��</td>
		    </tr>  
		     <tr>
		       <td colspan="2">
		       		&nbsp;&nbsp;${chipdata.otherAnalyse.analyseOther}
		       </td>
		       <td colspan="2">
		       		${chipdata.otherAnalyse.analyseResultOther}
		       </td>
		       <td colspan="2">
		       		${chipdata.otherAnalyse.remarkOther}
		       </td>
		    </tr>  
		    <tr class=trcolor>
		      <td colspan="6" align="center">
		        <input type="button" class="button" onclick="javascript:parent.close();" value="����" />
		      </td>
		    </tr>
		  </table>
		</html:form>
	</body>
</html>
