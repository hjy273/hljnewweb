<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type="text/javascript">
		function checkAddInfo() {
			var factTestTime =document.getElementById("factTestTime").value;
			if(factTestTime==""){
				alert("����ʱ�䲻��Ϊ��!");
				$("factTestTime").focus();
				return;
			}
			
			var testWeather =document.getElementById("testWeather").value;
			if(testWeather==""){
				alert("���� ��������Ϊ��!");
				$("testWeather").focus();
				return;
			}
			var testMan =document.getElementById("tester").value;
				if(testMan==null || testMan==""){
					alert("������Ա����Ϊ��!");
					return;
		  }
			var testMan =document.getElementById("tester").value;
				if(valCharLength(testMan)>100){
					alert("�����˲��ܳ���100���ַ�����50������!");
					return;
			}
			var testAddress =document.getElementById("testAddress").value;
			if(testAddress==""){
				alert("���� �ص㲻��Ϊ��!");
				$("testAddress").focus();
				return;
			}
			var testApparatus =document.getElementById("testApparatus").value;
			if(testApparatus==""){
				alert("�����Ǳ���Ϊ��!");
				$("testApparatus").focus();
				return;
			}
			var testMethod =document.getElementById("testMethod").value;
			if(testMethod==""){
				alert("���Է�������Ϊ��!");
				$("testMethod").focus();
				return;
			}
			var resistanceValue =document.getElementById("resistanceValue").value;
			if(resistanceValue==""){
				alert("������ֵ����Ϊ��!");
				$("resistanceValue").focus();
				return;
			}
			var regx = /^[1-9]\d{0,2}[\.]{0,4}\d{0,6}$|^-?0(\.\d*)?$/;
			if(!regx.test(resistanceValue)){
				alert("������ֵ����Ϊ���֣�");
				return false;
			}
			var problemComment =document.getElementById("problemComment").value;
			if(problemComment==""){
				alert("�����������Ϊ��!");
				$("problemComment").focus();
				return;
			}
			var disposeMethod =document.getElementById("disposeMethod").value;
			if(disposeMethod==""){
				alert("����������Ϊ��!");
				$("disposeMethod").focus();
				return;
			}
			$("stationDataform").submit();
		}
		
		function valCharLength(Value){
		     var j=0;
		     var s = Value;
		     for(var i=0; i<s.length; i++) {
				if (s.substr(i,1).charCodeAt(0)>255) {
					j  = j + 2;
				} else { 
					j++;
				}
		     }
		     return j;
	   }
		function tempsave(){
  			var btnstate = $('tempstate');
  			btnstate.value="tempsave";
  			$("stationDataform").submit();
  		   
  		}
	</script>
	</head>
	<body>
	<html:form action="/enteringStationDataAction.do?method=addEnteringStationData&act=edit" styleId="stationDataform" >
		<table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<html:hidden property="testPlanId" value="${station.testPlanId}"></html:hidden>
			<html:hidden property="testStationId" value="${station.testStationId}"></html:hidden>
			<html:hidden property="stationId" value="${station.id}"></html:hidden>
			<html:hidden property="id" value="${data.id}"></html:hidden>
			<html:hidden property="testDataId" value="${data.testDataId}"></html:hidden>
		    <tr class=trcolor >
		      <td class="tdulleft"><div align="right">��վ���ƣ�</div></td>
		      <td class="tdulright" colspan="3"><c:out value="${station.stationName}"></c:out></td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">����ʱ�䣺</td>
		       <td class="tdulright">
		        <input name="factTestTime" id="factTestTime" class="Wdate" style="width:205" value="<bean:write name="data" property="factTestTime" format="yyyy/MM/dd"/>"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate: '%y-%M-#{%d-6}',maxDate:'%y-%M-%d-%H-%m-%s'})" readonly/>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		      <td class="tdulleft">����������</td>
		      <td class="tdulright">
		       <html:text property="testWeather" styleId="testWeather" styleClass="required" value="${data.testWeather}" ></html:text>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft"> ������Ա��</td>
		      <td class="tdulright" colspan="3">
			        <apptag:testman spanId="testMan" hiddenId="tester" state="edit" value="${data.id}" tablename="station"></apptag:testman>
			   </td>
		    </tr>
		     <tr class=trwhite>
		      <td class="tdulleft">���Եص㣺</td>
		      <td class="tdulright" colspan="3" >
			       <html:text property="testAddress" styleId="testAddress" styleClass="required" value="${data.testAddress}" style="width:205" ></html:text>&nbsp;&nbsp;<font color="red">*</font>
			   </td>
		    </tr>
		    <tr>
		      <td height="25" colspan="4" bgcolor="#0099FF"><span class="STYLE1">���Բ�������</span></td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft">�����Ǳ�</td>
		      <td class="tdulright" colspan="3">
		       <html:text property="testApparatus" styleId="testApparatus" value="${data.testApparatus}"></html:text>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">���Է�����</td>
		       <td class="tdulright" colspan="3">
		       	    <c:if test="${data.testMethod=='1'}">
			       	  <input name="testMethod" id="testMethod" type="radio" value="1" checked="checked" />
				                      ������
				      <input type="radio" name="testMethod" value="0" />
				    	ǯ��  
		       	    </c:if>
		       	    <c:if test="${data.testMethod=='0'}">
		       	      <input name="testMethod" type="radio" value="1"  />
				                      ������
				      <input type="radio" name="testMethod" value="0" checked="checked"/>
			    	ǯ��  
			    	</c:if>
			  </td>
		    </tr>
		     <tr class=trcolor>
		      <td class="tdulleft">������ֵ��</td>
		      <td class="tdulright" colspan="3">
		      	<html:text property="resistanceValue" styleId="resistanceValue" value="${data.resistanceValue}"></html:text>&Omega;&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		     <tr class=trwhite>
		      <td class="tdulleft">�Ƿ�ϸ�</td>
		      <td class="tdulright" colspan="3">
		      <c:if test="${data.isEligible=='1'}">
		          <input name="isEligible" type="radio" value="1" checked="checked" />
			                     �ϸ�
			      <input type="radio" name="isEligible" value="0" />
			    	���ϸ�  
		      </c:if>
		       <c:if test="${data.isEligible=='0'}">
		      	  <input name="isEligible" type="radio" value="1"  />
			                     �ϸ�
			      <input type="radio" name="isEligible" value="0" checked="checked"/>
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
		      		<html:textarea property="problemComment" styleId="problemComment" value="${data.problemComment}" styleClass="max-length-512" rows="3" style="width:325px"></html:textarea>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">��������</td>
		      <td class="tdulright" colspan="3">
					<html:textarea property="disposeMethod" styleId="disposeMethod" value="${data.disposeMethod}" styleClass="max-length-512" rows="3" style="width:325px"></html:textarea>&nbsp;&nbsp;<font color="red">*</font>
			  </td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft">�� ע��</td>
		      <td class="tdulright" colspan="3">
		      	<html:textarea property="remark" styleId="remark" value="${data.remark}" styleClass="max-length-512" rows="3" style="width:325px"></html:textarea>
		      </td>
		    </tr>
			  <tr class=trcolor>
			      <td colspan="4" align="center">
				    <input type="hidden" id="tempstate" name="tempstate"/>
			        <input type="button" class="button" styleId="savebtn" value="�ݴ�" onclick="tempsave();"/>
			        <input type="button" class="button" onclick="checkAddInfo();" value="�޸�" />
			        <input type="reset" class="button"  value="����" />
			        <input type="button" class="button" onclick="javascript:parent.close();" value="�ر�" />
			      </td>
			  </tr>
		 </table>
	</html:form>
	</body>
</html>
