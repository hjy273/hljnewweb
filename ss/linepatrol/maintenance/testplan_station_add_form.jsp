<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>��Ӳ��Ի�վ</title>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type="text/javascript">
		function checkAddInfo() {
		    var testStationId =document.getElementById('testStationId').value;
			if(testStationId==null || testStationId==""){
				alert("��ѡ���վ!");
				return false;
			}
			
			var testTime =addStation.testPlanDate.value;
			if(testTime==""){
				alert("�ƻ�����ʱ�䲻��Ϊ��!");
				return false;
			}
			if(testTime!=""){
				 var testBeginDate = addStation.testBeginDate.value;
				 var testEndDate = addStation.testEndDate.value;
				 if(testTime<testBeginDate){
				 	alert("�ƻ�����ʱ�䲻�����ڼƻ��Ŀ�ʼʱ�䣡");
				 	return false;
				 }
				 if(testTime>testEndDate){
				 	alert("�ƻ�����ʱ�䲻�����ڼƻ��Ľ���ʱ�䣡");
				 	return false;
				 }
			}
			var testMan = document.getElementById('testMan').value;
			if(testMan=="" || testMan==null){
				alert("������Ա����Ϊ��!");
				return false;
			}
			if(valCharLength(document.getElementById('testRemark').value) > 512) {
	  			 alert("��ע���ܳ���256�����ֻ���512��Ӣ���ַ���")
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
			      <td align="right" width="30%">��վ��</td>
			      <td class="tdulright">
				      <select name="testStationId" id="testStationId" style="width:205px">
				      	<c:forEach items="${stations}" var="station">
				      	  <option value="<bean:write name="station" property="pointid"/>"><bean:write name="station" property="pointname"/></option>
				      	</c:forEach>
				      </select>
				  </td>
			    </tr>
			    <tr class=trwhite height="35px">
			      <td class="tdulleft">�ƻ�����ʱ�䣺</td>
			      <td class="tdulright">
			       <input name="testPlanDate" class="Wdate" style="width:205"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly/>&nbsp;&nbsp;<font color="red">*</font>
			     </td>
			    </tr>
			     <tr class=trcolor height="45px">
				      <td class="tdulleft">������Ա��</td>
				  	  <td class="tdulright" colspan="3">
					     <font color="red">*</font>&nbsp;<apptag:testman spanId="tester" hiddenId="testMan" tablename="cable"></apptag:testman>
					  </td>
				</tr>
			    <tr class=trwhite>
			      <td class="tdulleft">��ע��</td>
			      <td class="tdulright">
			       <html:textarea property="testRemark"  rows="4" style="width:275px"></html:textarea>	  
			      </td>
			    </tr>
			    <tr class=trcolor height="45px">
			      <td align="center" colspan="2">
			        <input type="submit"  class="button" value="����"/>
			         <input type="button" onclick="parent.close();" class="button" value="�ر�"/>
			      </td>
			    </tr>
			  </table>
		</html:form>
	</body>
</html>
