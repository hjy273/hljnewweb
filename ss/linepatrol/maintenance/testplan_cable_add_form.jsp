<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<title>��Ӳ����м̶�</title>
		
		<script type="text/javascript">
		function checkAddInfo() {
		    //var cablelineId =addCable.cablelineId.value;
			//if(cablelineId==""){
			//	alert("�м̶β���Ϊ��!");
			//	return false;
			//}
			if(typeof($('addCable').cablelineIds)=="undefined"){
				alert("û��ѡ���м̶�!");
				return false;
			}
			if(typeof(addCable.cablelineIds.length)=="undefined"){
				if(addCable.cablelineIds.checked==false){
				alert("û��ѡ���м̶�!");
				return false;
				}
			}else{
				var flag=false;
				for(i=0;i<addCable.cablelineIds.length;i++){
					if(addCable.cablelineIds[i].checked==true){
						flag=true;
						break;
					}
				}
				if(!flag){
				alert("û��ѡ���м̶�!");
				return false;
				}
			}
			var testTime =addCable.testPlanDate.value;
			if(testTime==""){
				alert("�ƻ�����ʱ�䲻��Ϊ��!");
				return false;
			}
			if(testTime!=""){
				 var testBeginDate = addCable.testBeginDate.value;
				 var testEndDate = addCable.testEndDate.value;
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
	   
	     function queryCable(){
	     		var level = document.getElementById("cableLevel").value;
	     		var cableName = document.getElementById("cableName").value;
	     		var testBeginDate = document.getElementById("cableName").value;
	       		var params = "&level="+level+"&cableName="+cableName;
	       		document.getElementById("num").innerHTML="";
	       		//var ops = document.getElementById('cablelineId');
	  			//ops.options.length = 0;//��������б�
	  			//ops.options.add(new Option("��ѡ��", ""));//������ʾ��Ϣ
	  			var url = "${ctx}/testPlanAction.do?method=getCablesByLevelAndName";
	  			document.getElementById("btn001").disabled=false;
	  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:true}); 
	     }
	    
	    function callback(originalRequest) {
	    		var rst = originalRequest.responseText;
	  			var queryRes = eval('('+rst+')');
	    		//var ops = document.getElementById('cablelineId');
	    		//for(var i = 0 ; i < queryRes.length; i++) {
	  				//ops.options.add(new Option(queryRes[i].segmentname, queryRes[i].kid));
	  			//}
	  			var htmlStr="";
	  			for(var i = 0 ; i < queryRes.length; i++) {
	  				htmlStr+="<input name='cablelineIds' value='"+queryRes[i].kid+"' type='checkbox'>"+queryRes[i].segmentname;
	  				if(i!=queryRes.length-1){
	  					htmlStr+="<br>";
	  				}
	  			}
	  			document.getElementById("num").innerHTML=htmlStr;
	  			var myGlobalHandlers = {onCreate:function () {
				Element.show("Loadingimg");
				}, onFailure:function () {
					alert("�������ӳ������⣬��رպ�����!");
				}, onComplete:function () {
					if (Ajax.activeRequestCount == 0) {
						Element.hide("Loadingimg");
					}
				}};
				Ajax.Responders.register(myGlobalHandlers);
	    }
	    //�ж��������½����Ѿ����Թ����� ���ƻ����Լ���
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
	    
	    //��ѯ�������µ�a����b����Ϣ
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
	    function selectAll(){
	    	var btn=document.getElementById("btn001");
	    	var cablelineIds=document.forms[0].elements["cablelineIds"];
	    	if(typeof(cablelineIds)!="undefined"){
	    		if(btn.checked){
	    			if(typeof(cablelineIds.length)!="undefined"){
	    				for(i=0;i<cablelineIds.length;i++){
	    					cablelineIds[i].checked=true;
	    				}
	    			}else{
	    				cablelineIds.checked=true;
	    			}
	    		}else{
	    			if(typeof(cablelineIds.length)!="undefined"){
	    				for(i=0;i<cablelineIds.length;i++){
	    					cablelineIds[i].checked=false;
	    				}
	    			}else{
	    				cablelineIds.checked=false;
	    			}
	    		}
	    	}
	    }
	</script>
	</head>
	<body>
		<html:form action="/testPlanAction.do?method=addCable"
			styleId="addCable" onsubmit="return checkAddInfo();">
		    <table width="100%" border="0" align="center" cellpadding="3"  cellspacing="0" class="tabout">
		    	<html:hidden property="testBeginDate" styleId="testBeginDate" value="${testPlan.testBeginDate}"/>
		    	<html:hidden property="testEndDate" value="${testPlan.testEndDate}"/>
		        <tr  class=trcolor height="35px">
			      <td align="right" width="30%">���¼���</td>
			      <td class="tdulright">
				        <apptag:quickLoadList cssClass="input" isQuery="true" style="width:205px" id="cableLevel" name="cableLevel" listName="cabletype" type="select"/>
				  </td>
			    </tr>
			    <tr  class=trwhite height="35px">
			      <td align="right" width="30%">ģ����ѯ�м̶Σ�</td>
			      <td class="tdulright">
				     	<input type="text" style="width:205px" class="inputtext" id="cableName" name="cableName"/>
				  </td>
			    </tr>
			    <tr><td colspan="2" align="center"><input type="button" class="button" value="��ѯ" onclick="queryCable();"/></td></tr>
			    <tr  class=trcolor height="35px">
			      <td align="right" width="30%">�м̶Σ�</td>
			      <td class="tdulright">
			      	  <!-- 
				      <select name="cablelineId" id="cablelineId" style="width:205px" onchange="viewChangeNum();viewCableInfo();">
				      	<option value="">��ѡ��</option>
				      </select>
				       -->
						<input name="btn001" id="btn001" type="checkbox" onclick="selectAll();"
							disabled="disabled" />
						ȫѡ/ȫ��ѡ
				       <div id="num" ></div>
				  </td>
			    </tr>
			     <input name="cablelineTestPort" type="hidden" value="A" />
			    <!-- 
			    <tr class=trwhite height="35px">
			      <td class="tdulleft">�ƻ����Զˣ�</td>
			      <td class="tdulright">
			      <div id="pointdiv">
				       <input name="cablelineTestPort" type="radio" value="A" checked="checked" />
				      A��
				      <input type="radio" name="cablelineTestPort" value="B" />
				      B��</td>
			      </div>
			    </tr>
			     -->
			    <tr  class=trcolor height="35px">
			      <td class="tdulleft">�ƻ�����ʱ�䣺</td>
			      <td class="tdulright">
			       <input name="testPlanDate" class="Wdate" style="width:205" id="testPlanDate"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly/>&nbsp;&nbsp;<font color="red">*</font>
			     </td>
			    </tr>
			    <tr class=trwhite height="45px">
				      <td class="tdulleft">������Ա��</td>
				  	  <td class="tdulright" colspan="3">
					     <font color="red">*</font>&nbsp;<apptag:testman spanId="tester" hiddenId="testMan" tablename="cable"></apptag:testman>
					  </td>
				</tr>
			    <tr  class=trcolor>
			      <td class="tdulleft">��ע��</td>
			      <td class="tdulright">
			       <html:textarea property="testRemark"  rows="4" style="width:275px"></html:textarea>	  
			      </td>
			    </tr>
			    <tr class=trwhite height="35px">
			      <td align="center" colspan="2">
			        <input type="submit"  class="button" value="����"/>
			         <input type="button" onclick="parent.close();" class="button" value="�ر�"/>
			      </td>
			    </tr>
			  </table>
		</html:form>
	</body>
</html>
