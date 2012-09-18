<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>修改中继段</title>
			<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
			<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
			<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type="text/javascript">
		function checkAddInfo() {
			var testTime =addCable.testPlanDate.value;
			if(testTime==""){
				alert("计划测试时间不能为空!");
				return false;
			}
			if(testTime!=""){
				 var testBeginDate = addCable.testBeginDate.value;
				 var testEndDate = addCable.testEndDate.value;
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
	   
	     function queryCable(){
	     		var level = document.getElementById("cableLevel").value;
	     		var cableName = document.getElementById("cableName").value;
	       		var params = "&level="+level+"&cableName="+cableName;
	       		var ops = document.getElementById('cablelineId');
	  			ops.options.length = 0;//清空下拉列表
	  			ops.options.add(new Option("请选择", ""));//增加提示信息
	  			var url = "${ctx}/testPlanAction.do?method=getCablesByLevelAndName"
	  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:true}); 
	     }
	    
	    function callback(originalRequest) {
	    		var rst = originalRequest.responseText;
	    		var queryRes = eval('('+rst+')');
	    		var ops = document.getElementById('cablelineId');
	    		for(var i = 0 ; i < queryRes.length; i++) {
	  				ops.options.add(new Option(queryRes[i].segmentname, queryRes[i].kid));
	  			}
	  			var myGlobalHandlers = {onCreate:function () {
				Element.show("Loadingimg");
				}, onFailure:function () {
					alert("网络连接出现问题，请关闭后重试!");
				}, onComplete:function () {
					if (Ajax.activeRequestCount == 0) {
						Element.hide("Loadingimg");
					}
				}};
				Ajax.Responders.register(myGlobalHandlers);
	    }
	    //判断这条光缆今年已经测试过几次 。计划测试几次
	      function viewChangeNum(){
	     		var cablelineId = document.getElementById("cablelineId").value;
	     		var testBeginDate = document.getElementById("testBeginDate").value;
	       		var params = "&cablelineId="+cablelineId+"&testBeginDate="+testBeginDate;
	  			var url = "${ctx}/testPlanAction.do?method=getTestNum"
	  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:view,asynchronous:true}); 
	     }
	    
	    function view(originalRequest) {
	    		var rst = originalRequest.responseText;
	    		rst="<font color='red'>"+rst+"</font>"
	    	    $('num').update(rst);
	    		
	    }
		
		   //查询这条光缆的a段与b段信息
		 function viewCableInfo(){
	     		var cablelineId = document.getElementById("cablelineId").value;
	       		var params = "&cablelineId="+cablelineId;
	  			var url = "${ctx}/testPlanAction.do?method=getCablePointInfo"
	  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:viewPoint,asynchronous:true}); 
	     }
	    
	    function viewPoint(originalRequest) {
	    		var rst = originalRequest.responseText;
	    	    $('pointdiv').update(rst);
	    		
	    }
	</script>
	</head>

	<body>
		
		<html:form  action="/testPlanAction.do?method=updateCable"
			styleId="addCable" onsubmit="return checkAddInfo();" >
			<input type="hidden" value="${TestPlanBean.cablelineId}" name="oldCablelineId"/>
		    <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout" >
		    	<html:hidden property="testBeginDate" value="${testPlan.testBeginDate}"/>
		    	<html:hidden property="testEndDate" value="${testPlan.testEndDate}"/>
		    	 <tr  class=trcolor height="35px">
			      <td align="right" width="30%">光缆级别：</td>
			      <td class="tdulright">
				        <apptag:quickLoadList cssClass="input" isQuery="true" style="width:205px" id="cableLevel" name="cableLevel" listName="cabletype" type="select"/>
				  </td>
			    </tr>
			    <tr  class=trwhite height="35px">
			      <td align="right" width="30%">模糊查询中继段：</td>
			      <td class="tdulright">
				     	<input type="text" style="width:205px" class="inputtext" id="cableName" name="cableName"/>
				  </td>
			    </tr>
			    <tr><td colspan="2" align="center"><input type="button" class="button" value="查询" onclick="queryCable();"/></td></tr>
			    <tr  class=trcolor height="35px">
			      <td align="right" width="30%">中继段：</td>
			      <td class="tdulright">
				     <html:select property="cablelineId" styleId="cablelineId" name="TestPlanBean" onchange="viewChangeNum();viewCableInfo();"  style="width:205px">
				      <option value="${TestPlanBean.cablelineId}">${TestPlanBean.cablelineName}</option>
			      	 </html:select><div id="num" ><font color="red">${str}</font></div>
				  </td>
			    </tr>
			    <tr class=trwhite height="35px">
			      <td class="tdulleft">计划测试端：</td>
			      <td class="tdulright">
				      <div id="pointdiv">
				      	<c:if test="${TestPlanBean.cablelineTestPort=='A'}">
					      	 <input name="cablelineTestPort" type="radio" value="A" checked="checked" />
					      A端:<c:if test="${repeat.pointa!=null&&repeat.pointa!=''}">${repeat.pointa}</c:if>
					      <c:if test="${repeat.pointa==null||repeat.pointa==''}">数据没有录入</c:if>
					      <input type="radio" name="cablelineTestPort" value="B" />
					      B端:<c:if test="${repeat.pointz!=null&&repeat.pointz!=''}">${repeat.pointz}</c:if>
					      <c:if test="${repeat.pointz==null||repeat.pointz==''}">数据没有录入</c:if>
				      	</c:if>
				      	<c:if test="${TestPlanBean.cablelineTestPort=='B'}">
					      	 <input name="cablelineTestPort" type="radio" value="A"  />
					      A端:<c:if test="${repeat.pointa!=null&&repeat.pointa!=''}">${repeat.pointa}</c:if>
					      <c:if test="${repeat.pointa==null||repeat.pointa==''}">数据没有录入</c:if>
					      <input type="radio" name="cablelineTestPort" value="B" checked="checked"/>
					      B端:<c:if test="${repeat.pointz!=null&&repeat.pointz!=''}">${repeat.pointz}</c:if>
					      <c:if test="${repeat.pointz==null||repeat.pointz==''}">数据没有录入</c:if>
				      	</c:if>
				      </div>
			       </td>
			    </tr>
			    <tr  class=trcolor height="35px">
			      <td class="tdulleft">计划测试时间：</td>
			      <td class="tdulright">
			       <input name="testPlanDate" class="Wdate" style="width:205" value="<bean:write name="TestPlanBean" property="testPlanDate" format="yyyy/MM/dd"/>" 
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly/>&nbsp;&nbsp;<font color="red">*</font>
			     </td>
			    </tr>
			    <tr class=trwhite height="35px">
			      <td class="tdulleft">测试人员：</td>
			  	  <td class="tdulright" colspan="3">
				        <apptag:testman spanId="tester" hiddenId="testMan" state="edit" testman="${TestPlanBean.testMan}" tablename="no"></apptag:testman>
				   </td>
			    </tr>
			    <tr  class=trcolor>
			      <td class="tdulleft">备注：</td>
			      <td class="tdulright">
			       <html:textarea property="testRemark" value="${TestPlanBean.testRemark}" styleClass="max-length-512" rows="4" style="width:275px"></html:textarea>	  
			      </td>
			    </tr>
			    <tr class=trwhite height="45px">
			      <td align="center" colspan="2">
			        <input type="submit"  class="button" value="保存"/>
			         <input type="button" onclick="parent.close();" class="button" value="关闭"/>
			      </td>
			    </tr>
			  </table>
		</html:form>
	</body>
</html>
