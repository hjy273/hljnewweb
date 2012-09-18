<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/xtheme-gray.css'/>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type='text/javascript'>
		  var win;
			function addTestData(){
			  win = new Ext.Window({
			  layout : 'fit',
			  width:460,
			  height:310, 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			//  autoLoad:{url: '${ctx}/enteringCableDataAction.do?method=addChipDataForm',scripts:true}, 
			 html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="${ctx}/enteringCableDataAction.do?method=addChipDataForm" />',
			  plain: true,
			  title:"测试数据" 
			 });
			  win.show(Ext.getBody());
			}
			
		function updateChipData(id){
			  var u="${ctx}/enteringCableDataAction.do?method=editChipForm&chipSeq="+id;
			  win = new Ext.Window({
			  layout : 'fit',
			  width:430,
			  height:310, 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			//  autoLoad:{url: u,scripts:true}, 
			  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+u+' />',
			  plain: true,
			  title:"修改测试数据" 
			 });
			  win.show(Ext.getBody());
			}
			
			function addProblem(){
			  win = new Ext.Window({
			  layout : 'fit',
			  width:460,
			  height:230, 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			  autoLoad:{url: '${ctx}/enteringCableDataAction.do?method=addProblemForm',scripts:true}, 
			  plain: true,
			  title:"增加问题记录" 
			 });
			  win.show(Ext.getBody());
			}
			
			function updateProblem(id){
			  var u="${ctx}/enteringCableDataAction.do?method=editProblemForm&index="+id;
			  win = new Ext.Window({
			  layout : 'fit',
			  width:460,
			  height:230, 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			  autoLoad:{url: u,scripts:true}, 
			  plain: true,
			  title:"修改问题记录" 
			 });
			  win.show(Ext.getBody());
			}
			
			//数据分析
			function addAnalyse(id){
			 var cableLineName = document.getElementById('cableLineName').value;
			 var u="${ctx}/enteringCableDataAction.do?method=analyseDataForm&index="+id+"&cableLineName="+cableLineName;
			  win = new Ext.Window({
			  layout : 'fit',
			  width: document.body.clientWidth * 0.95 , 
			  height:390, 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			  
			 // autoLoad:{url: '${ctx}/enteringCableDataAction.do?method=addChipDataForm',scripts:true}, 
			  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+u+' />',
			  plain: true,
			  title:"数据分析" 
			 });
			  win.show(Ext.getBody());
			}
			  
			 function close(){
			  	win.close();
			 }
			 
			  function freshPage(){
			  	window.location.href=window.location.href;
			  	
			  }
		
			function checkAddInfo() {
				var factTestTime =document.getElementById("factTestTime").value;
				if(factTestTime==""){
					alert("测试时间不能为空!");
					$("factTestTime").focus();
					return;
				}
				var testAddress =document.getElementById("testAddress").value;
				if(testAddress==""){
					alert("测试地点不能为空!");
					$("testAddress").focus();
					return;
				}
				var testMan =document.getElementById("testMan").value;
				if(testMan==null || testMan==""){
					alert("测试人员不能为空!");
					return;
				}
			//	var testMan =document.getElementById("testMan").value;
				if(valCharLength(testMan)>100){
					alert("测试人不能超过100个字符或者50个汉字!");
					return;
				}
				
				var testApparatus =document.getElementById("testApparatus").value;
				if(testApparatus==""){
					alert("测试仪表不能为空!");
					$("testApparatus").focus();
					return;
				}
				//var testMethod =document.getElementById("testMethod").value;
				//if(testMethod==""){
					//alert("测试方法不能为空!");
				//	$("testMethod").focus();
					//return;
			//	}
				var testWavelength =document.getElementById("testWavelength").value;
				if(testWavelength==""){
					alert("测试波长不能为空!");
					$("testWavelength").focus();
					return;
				}
				var testRefractiveIndex =document.getElementById("testRefractiveIndex").value;
				if(testRefractiveIndex==""){
					alert("所设折射率不能为空!");
					$("testRefractiveIndex").focus();
					return;
				}
				var regx = /^[1-9]\d{0,6}[\.]{0,4}\d{0,6}$|^0(\.\d*)?$/;
				var testAvgTime =document.getElementById("testAvgTime").value;
				if(testAvgTime==""){
					alert("测试平均时间不能为空!");
					$("testAvgTime").focus();
					return;
				}
				if(!regx.test(testAvgTime)){
					alert("测试平均时间必须为数字!");
					return false;
				}
				var chipdata = document.getElementById("chipdata").innerText;
				if(chipdata==""){
					alert("没有一条测试数据!");
					return ;
				}
				var nonConformity = document.getElementById('nonConformity').value;
				var problem =  document.getElementById("problem").innerText;
				if(nonConformity>0 && problem==""){
					alert("有"+nonConformity+"条纤芯不合格,必须填写问题记录!");
					return;
				}
				$("dataform").submit();
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
  			$("dataform").submit();
  		   
  		}
		
	    </script>
	</head>
	<body>
		<html:form action="/enteringCableDataAction.do?method=addEnteringCableData" styleId="dataform">
		<table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<html:hidden property="testPlanId" value="${line.testPlanId}"></html:hidden>
			<html:hidden property="testCablelineId" value="${line.cablelineId}"></html:hidden>
			<html:hidden property="lineId" value="${line.id}"></html:hidden>
			<input type="hidden" id="coreNumber" name="coreNumber" value="<c:out value="${coreNumber}"/>"/>
		    <tr class=trcolor>
		      <td class="tdulleft">中继段：</td>
		      <td class="tdulright" colspan="3"><c:out value="${line.cablelineName}"></c:out>
		      <input type="hidden" value="${line.cablelineName}" name="cableLineName" id="cableLineName"/>
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">测试端：</td>
		      <td class="tdulright" colspan="3">
		      	<input name="factTestPort" type="radio" value="A" checked="checked" />
				      A端${repeat.pointa}
				<input type="radio" name="factTestPort" value="B" />
				      
				      B端${repeat.pointz}
		      </td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft">测试时间：</td>
		      <td class="tdulright" colspan="3">
		        <input name="factTestTime" id="factTestTime" class="Wdate" style="width:205"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate: '%y-%M-#{%d-6}',maxDate: '%y-%M-%d-%H-%m-%s'})" readonly/>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		    <tr>
		      <td class="tdulleft">测试地点：</td>
		      <td class="tdulright" colspan="3">
		      	<html:text property="testAddress" styleId="testAddress" style="width:205px"></html:text>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">测试人员：</td>
		  	  <td class="tdulright" colspan="3">
			     <font color="red">*</font>&nbsp;<apptag:testman spanId="tester" hiddenId="testMan" state="e" testman="${line.testMan}" tablename="cable1"></apptag:testman>
			   </td>
		    </tr>
		    <tr class=trcolor>
		      <td height="25" colspan="4" bgcolor="#CFE0F0"><span class="STYLE1">测试参数设置</span></td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft">测试仪表：</td>
		      <td class="tdulright" colspan="3">
		      	<html:text property="testApparatus" styleId="testApparatus" ></html:text>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		  <!--  <tr class=trcolor>
		      <td class="tdulleft">测试方法：</td>
		      <td class="tdulright" colspan="3">
		      	<input name="testMethod" type="radio" value="2" checked="checked" />
				      两点法
				<input type="radio" name="testMethod" value="0" />
				   LSA
				 <input type="radio" name="testMethod" value="4" />
				     四点法 <br/>
	                                    中继段无接头选用LSA法，有接头选用两点法；接头损耗测试选用四点法</td>
		    </tr> -->
		    <tr class=trwhite>
		      <td class="tdulleft">测试波长(nm)：</td>
		      <td class="tdulright" colspan="3">
		      	<!--    <html:select property="testWavelength" style="width:152px">
		   	      	<html:option value="1550nm">1550nm</html:option>
		   	      	<html:option value="1310nm">1310nm</html:option>
		   	      </html:select>
		   	     -->
		   	     <html:text property="testWavelength" styleId="testWavelength" value="1550"></html:text>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		      
		    </tr>
		    <tr class=trcolor>
		    	<td class="tdulleft">所设折射率：</td>
		      <td colspan="3" class="tdulright">
		      		<html:text property="testRefractiveIndex" styleId="testRefractiveIndex" ></html:text>&nbsp;&nbsp;<font color="red">*</font>&nbsp;根据实际填写
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft"><div align="right">测试平均时间(s)：</div></td>
		      <td class="tdulright" colspan="3">
		   	 	 <html:text property="testAvgTime" styleId="testAvgTime" value=""></html:text>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		    <tr class=trcolor>
		      <td height="25" colspan="4" bgcolor="#CFE0F0">测试数据 (<c:out value="${coreNumber}"></c:out>条纤芯)
		      	<!-- <input type="button" value="添加测试数据" onclick="addTestData();"/>-->
		      </td>
		    </tr>
		    <tr class=trwhite>
			    <td height="25" colspan="4">
				    <div id="chipdata">
				        <table width="100%" border="0" cellspacing="0" cellpadding="0">
				    		<tr>
				    			<td width="7%" >纤序</td>
				    			<td width="17%">衰减常数dB/km</td>
				    			<td width="12%">是否合格</td>
				    			<td width="12%">是否保存</td>
				    			<td width="12%">是否在用</td>
				    			<td width="28%">&nbsp;&nbsp;说明</td>
				    			<td width="12%">操作</td>
				    		</tr>
				    		<c:forEach begin="1" end="${coreNumber}" var="i">
				    		<tr>
				    		<td>${i}</td>
				    		<td>&nbsp;</td>
				    		<td>合格</td>
				    		<td>未保存</td>
				    		<td>不再用</td>
				    		<td>&nbsp;</td>
				    		<td><a href="javascript:updateChipData('${i}')">录入</a>
				            <!--  |<a href="javascript:deleteChip('${i}')">删除</a>-->
				            </td>
				    		</tr>
				    	</c:forEach>
				    	<input id="nonConformity" name="nonConformity" type="hidden" value="0">
				    	</table>
				    </div>
		      	</td>
			</tr>		
			
		    <tr class=trcolor>
		      <td height="25" colspan="4" bgcolor="#CFE0F0">现场测试问题记录及跟踪
		      	<input type="button" value="增加问题记录" onclick="addProblem();"/>
		      </td>
		    </tr>
		     <tr class=trwhite>
			    <td height="25" colspan="4">
				    <div id="problem">
				    </div>
		      	</td>
			</tr>
		    <tr class=trcolor>
		      <td colspan="4" align="center">
		        <input type="hidden" id="tempstate" name="tempstate"/>
		        <input type="button" class="button" styleId="savebtn" value="暂存" onclick="tempsave();"/>
		        <input type="button" class="button" value="保存" onclick="checkAddInfo();"/>
		        <input type="reset" class="button"  value="重置" />
		        <input type="button" class="button" onclick="javascript:parent.close();" value="关闭" />
		      </td>
		    </tr>
		  </table>
		</html:form>
		<script language="javascript" type="text/javascript">
			function valid(obj){
				var chipform = obj;
				var chipseq =parseFloat(chipform.chipSeq.value);
				var attenuation =chipform.attenuationConstant.value;
				var testRemark =chipform.testRemark.value;
				var coreNumber = parseFloat(document.getElementById("coreNumber").value);
				var regx = /^[1-9]\d{0,6}[\.]{0,4}\d{0,6}$|^0(\.\d*)?$/;
				var reg=/^\d+$/;
				if(chipseq==""){
					alert("纤序不能为空!");
					return false;
				}
				if(!reg.test(chipseq) || chipseq<=0){
					alert("纤序要为数字!并且要从1开始!");
					return false;
				}
				if(chipseq>coreNumber){
					alert("纤序不能大于纤芯数量!");
					return false;
				}
			var confirmResult = chipform.isUsed;
			if(confirmResult[1].checked && confirmResult[1].value=="0"){
				if(attenuation==""){
					alert("衰减常数不能为空!");
					return false;
				}
				if(!regx.test(attenuation)){
					alert("衰减常数必须为数字!");
					return false;
				}
			}
				if(valCharLength(testRemark)>512){
					alert("说明不能超过512个字符或者256个汉字!");
					return false;
				}
				return true;
			}
			
			function savechip(obj){
				if(valid(obj)){
					obj.request({ 
					   onComplete: function(originalRequest){ 
						   var rst = originalRequest.responseText;
						   if(rst=="1"){
						   	 alert("纤序不能重复!");
						   }else{
						   	 $('chipdata').update(rst);
						   	 window.close();
						   }
						}
				     }); 
				     //	$("addChip").formUpdate({
								///handler:'chipdata'
								//window.close();
					//	});
			     }
			}
			function validPro(){
				var problemform = $("saveProblem");
				var problemDescription =problemform.problemDescription.value;
				var processComment =problemform.processComment.value;
				if(problemDescription==""){
					alert("问题描述不能为空!");
					return false;
				}
				if(valCharLength(problemDescription)>512){
					alert("问题描述不能超过512个字符或者256个汉字!");
					return false;
				}
				if(valCharLength(processComment)>512){
					alert("处理跟踪说明不能超过512个字符或者256个汉字!");
					return false;
				}
				return true;
			}
			
			function saveproblem(){
				if(validPro()){
					$("saveProblem").request({ 
					   onComplete: function(originalRequest){ 
						   var rst = originalRequest.responseText;
						   	 $('problem').update(rst);
						   	 window.close();
						}
				     }); 
			     }
			}
			
	     function deleteProblem(idValue){    
	      	if(confirm("确定要删除吗?")){
	       		var params = "&index="+idValue;
	  			var url = "${ctx}/enteringCableDataAction.do?method=deleteProblem"
	  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:true}); 
	      	}
	     }
	    
	    function callback(originalRequest) {
	    		var rst = originalRequest.responseText;
	  		    $('problem').update(rst);
	    }
	    
	     function deleteChip(idValue){    
	      	if(confirm("确定要删除吗?")){
	       		var params = "&chipSeq="+idValue;
	  			var url = "${ctx}/enteringCableDataAction.do?method=deleteCable"
	  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:chipcallback,asynchronous:true}); 
	      	}
	     }
	    
	    function chipcallback(originalRequest) {
	    		var rst = originalRequest.responseText;
	  		    $('chipdata').update(rst);
	    }
		</script>
	</body>
</html>
