<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>添加测试基站</title>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type="text/javascript">
		function checkAddInfo() {
		    var testStationId =document.getElementById('testStationId').value;
			if(testStationId==null || testStationId==""){
				alert("请选择基站!");
				return false;
			}
			
			var testTime =addStation.testPlanDate.value;
			if(testTime==""){
				alert("计划测试时间不能为空!");
				return false;
			}
			if(testTime!=""){
				 var testBeginDate = addStation.testBeginDate.value;
				 var testEndDate = addStation.testEndDate.value;
				 if(testTime<testBeginDate){
				 	alert("计划测试时间不能早于计划的开始时间！");
				 	return false;
				 }
				 if(testTime>testEndDate){
				 	alert("计划测试时间不能晚于计划的结束时间！");
				 	return false;
				 }
			}
			var testMan = document.getElementById('testMan').value;
			if(testMan=="" || testMan==null){
				alert("测试人员不能为空!");
				return false;
			}
			if(valCharLength(document.getElementById('testRemark').value) > 512) {
	  			 alert("备注不能超过256个汉字或者512个英文字符！")
	             return false;
	  		}
			return true;
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
	</script>
	</head>

	<body>
		
		<html:form action="/testPlanAction.do?method=addStation"
			styleId="addStation" onsubmit="return checkAddInfo();">
		    <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		    	<html:hidden property="testBeginDate" value="${testPlan.testBeginDate}"/>
		    	<html:hidden property="testEndDate" value="${testPlan.testEndDate}"/>
			    <tr  class=trcolor height="35px">
			      <td align="right" width="30%">基站：</td>
			      <td class="tdulright">
				      <select name="testStationId" id="testStationId" style="width:205px">
				      	<c:forEach items="${stations}" var="station">
				      	  <option value="<bean:write name="station" property="pointid"/>"><bean:write name="station" property="pointname"/></option>
				      	</c:forEach>
				      </select>
				  </td>
			    </tr>
			    <tr class=trwhite height="35px">
			      <td class="tdulleft">计划测试时间：</td>
			      <td class="tdulright">
			       <input name="testPlanDate" class="Wdate" style="width:205"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly/>&nbsp;&nbsp;<font color="red">*</font>
			     </td>
			    </tr>
			     <tr class=trcolor height="45px">
				      <td class="tdulleft">测试人员：</td>
				  	  <td class="tdulright" colspan="3">
					     <font color="red">*</font>&nbsp;<apptag:testman spanId="tester" hiddenId="testMan" tablename="cable"></apptag:testman>
					  </td>
				</tr>
			    <tr class=trwhite>
			      <td class="tdulleft">备注：</td>
			      <td class="tdulright">
			       <html:textarea property="testRemark"  rows="4" style="width:275px"></html:textarea>	  
			      </td>
			    </tr>
			    <tr class=trcolor height="45px">
			      <td align="center" colspan="2">
			        <input type="submit"  class="button" value="保存"/>
			         <input type="button" onclick="parent.close();" class="button" value="关闭"/>
			      </td>
			    </tr>
			  </table>
		</html:form>
	</body>
</html>
