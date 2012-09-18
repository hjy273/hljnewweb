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
				alert("测试时间不能为空!");
				$("factTestTime").focus();
				return;
			}
			
			var testWeather =document.getElementById("testWeather").value;
			if(testWeather==""){
				alert("测试 天气不能为空!");
				$("testWeather").focus();
				return;
			}
			var testMan =document.getElementById("tester").value;
				if(testMan==null || testMan==""){
					alert("测试人员不能为空!");
					return;
		  }
			var testMan =document.getElementById("tester").value;
				if(valCharLength(testMan)>100){
					alert("测试人不能超过100个字符或者50个汉字!");
					return;
			}
			var testAddress =document.getElementById("testAddress").value;
			if(testAddress==""){
				alert("测试 地点不能为空!");
				$("testAddress").focus();
				return;
			}
			var testApparatus =document.getElementById("testApparatus").value;
			if(testApparatus==""){
				alert("测试仪表不能为空!");
				$("testApparatus").focus();
				return;
			}
			var testMethod =document.getElementById("testMethod").value;
			if(testMethod==""){
				alert("测试方法不能为空!");
				$("testMethod").focus();
				return;
			}
			var resistanceValue =document.getElementById("resistanceValue").value;
			if(resistanceValue==""){
				alert("测试阻值不能为空!");
				$("resistanceValue").focus();
				return;
			}
			var regx = /^[1-9]\d{0,2}[\.]{0,4}\d{0,6}$|^-?0(\.\d*)?$/;
			if(!regx.test(resistanceValue)){
				alert("测试阻值必须为数字！");
				return false;
			}
			var problemComment =document.getElementById("problemComment").value;
			if(problemComment==""){
				alert("问题简述不能为空!");
				$("problemComment").focus();
				return;
			}
			var disposeMethod =document.getElementById("disposeMethod").value;
			if(disposeMethod==""){
				alert("处理方法不能为空!");
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
		      <td class="tdulleft"><div align="right">基站名称：</div></td>
		      <td class="tdulright" colspan="3"><c:out value="${station.stationName}"></c:out></td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">测试时间：</td>
		       <td class="tdulright">
		        <input name="factTestTime" id="factTestTime" class="Wdate" style="width:205" value="<bean:write name="data" property="factTestTime" format="yyyy/MM/dd"/>"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate: '%y-%M-#{%d-6}',maxDate:'%y-%M-%d-%H-%m-%s'})" readonly/>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		      <td class="tdulleft">测试天气：</td>
		      <td class="tdulright">
		       <html:text property="testWeather" styleId="testWeather" styleClass="required" value="${data.testWeather}" ></html:text>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft"> 测试人员：</td>
		      <td class="tdulright" colspan="3">
			        <apptag:testman spanId="testMan" hiddenId="tester" state="edit" value="${data.id}" tablename="station"></apptag:testman>
			   </td>
		    </tr>
		     <tr class=trwhite>
		      <td class="tdulleft">测试地点：</td>
		      <td class="tdulright" colspan="3" >
			       <html:text property="testAddress" styleId="testAddress" styleClass="required" value="${data.testAddress}" style="width:205" ></html:text>&nbsp;&nbsp;<font color="red">*</font>
			   </td>
		    </tr>
		    <tr>
		      <td height="25" colspan="4" bgcolor="#0099FF"><span class="STYLE1">测试参数设置</span></td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft">测试仪表：</td>
		      <td class="tdulright" colspan="3">
		       <html:text property="testApparatus" styleId="testApparatus" value="${data.testApparatus}"></html:text>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">测试方法：</td>
		       <td class="tdulright" colspan="3">
		       	    <c:if test="${data.testMethod=='1'}">
			       	  <input name="testMethod" id="testMethod" type="radio" value="1" checked="checked" />
				                      三极法
				      <input type="radio" name="testMethod" value="0" />
				    	钳表法  
		       	    </c:if>
		       	    <c:if test="${data.testMethod=='0'}">
		       	      <input name="testMethod" type="radio" value="1"  />
				                      三极法
				      <input type="radio" name="testMethod" value="0" checked="checked"/>
			    	钳表法  
			    	</c:if>
			  </td>
		    </tr>
		     <tr class=trcolor>
		      <td class="tdulleft">测试阻值：</td>
		      <td class="tdulright" colspan="3">
		      	<html:text property="resistanceValue" styleId="resistanceValue" value="${data.resistanceValue}"></html:text>&Omega;&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		     <tr class=trwhite>
		      <td class="tdulleft">是否合格：</td>
		      <td class="tdulright" colspan="3">
		      <c:if test="${data.isEligible=='1'}">
		          <input name="isEligible" type="radio" value="1" checked="checked" />
			                     合格
			      <input type="radio" name="isEligible" value="0" />
			    	不合格  
		      </c:if>
		       <c:if test="${data.isEligible=='0'}">
		      	  <input name="isEligible" type="radio" value="1"  />
			                     合格
			      <input type="radio" name="isEligible" value="0" checked="checked"/>
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
		      		<html:textarea property="problemComment" styleId="problemComment" value="${data.problemComment}" styleClass="max-length-512" rows="3" style="width:325px"></html:textarea>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">处理方法：</td>
		      <td class="tdulright" colspan="3">
					<html:textarea property="disposeMethod" styleId="disposeMethod" value="${data.disposeMethod}" styleClass="max-length-512" rows="3" style="width:325px"></html:textarea>&nbsp;&nbsp;<font color="red">*</font>
			  </td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft">备 注：</td>
		      <td class="tdulright" colspan="3">
		      	<html:textarea property="remark" styleId="remark" value="${data.remark}" styleClass="max-length-512" rows="3" style="width:325px"></html:textarea>
		      </td>
		    </tr>
			  <tr class=trcolor>
			      <td colspan="4" align="center">
				    <input type="hidden" id="tempstate" name="tempstate"/>
			        <input type="button" class="button" styleId="savebtn" value="暂存" onclick="tempsave();"/>
			        <input type="button" class="button" onclick="checkAddInfo();" value="修改" />
			        <input type="reset" class="button"  value="重置" />
			        <input type="button" class="button" onclick="javascript:parent.close();" value="关闭" />
			      </td>
			  </tr>
		 </table>
	</html:form>
	</body>
</html>
