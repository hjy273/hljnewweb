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
			  title:"��������" 
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
			  title:"�޸Ĳ�������" 
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
			  title:"���������¼" 
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
			  title:"�޸������¼" 
			 });
			  win.show(Ext.getBody());
			}
			
			//���ݷ���
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
			  title:"���ݷ���" 
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
					alert("����ʱ�䲻��Ϊ��!");
					$("factTestTime").focus();
					return;
				}
				var testAddress =document.getElementById("testAddress").value;
				if(testAddress==""){
					alert("���Եص㲻��Ϊ��!");
					$("testAddress").focus();
					return;
				}
				var testMan =document.getElementById("testMan").value;
				if(testMan==null || testMan==""){
					alert("������Ա����Ϊ��!");
					return;
				}
			//	var testMan =document.getElementById("testMan").value;
				if(valCharLength(testMan)>100){
					alert("�����˲��ܳ���100���ַ�����50������!");
					return;
				}
				
				var testApparatus =document.getElementById("testApparatus").value;
				if(testApparatus==""){
					alert("�����Ǳ���Ϊ��!");
					$("testApparatus").focus();
					return;
				}
				//var testMethod =document.getElementById("testMethod").value;
				//if(testMethod==""){
					//alert("���Է�������Ϊ��!");
				//	$("testMethod").focus();
					//return;
			//	}
				var testWavelength =document.getElementById("testWavelength").value;
				if(testWavelength==""){
					alert("���Բ�������Ϊ��!");
					$("testWavelength").focus();
					return;
				}
				var testRefractiveIndex =document.getElementById("testRefractiveIndex").value;
				if(testRefractiveIndex==""){
					alert("���������ʲ���Ϊ��!");
					$("testRefractiveIndex").focus();
					return;
				}
				var regx = /^[1-9]\d{0,6}[\.]{0,4}\d{0,6}$|^0(\.\d*)?$/;
				var testAvgTime =document.getElementById("testAvgTime").value;
				if(testAvgTime==""){
					alert("����ƽ��ʱ�䲻��Ϊ��!");
					$("testAvgTime").focus();
					return;
				}
				if(!regx.test(testAvgTime)){
					alert("����ƽ��ʱ�����Ϊ����!");
					return false;
				}
				var chipdata = document.getElementById("chipdata").innerText;
				if(chipdata==""){
					alert("û��һ����������!");
					return ;
				}
				var nonConformity = document.getElementById('nonConformity').value;
				var problem =  document.getElementById("problem").innerText;
				if(nonConformity>0 && problem==""){
					alert("��"+nonConformity+"����о���ϸ�,������д�����¼!");
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
		      <td class="tdulleft">�м̶Σ�</td>
		      <td class="tdulright" colspan="3"><c:out value="${line.cablelineName}"></c:out>
		      <input type="hidden" value="${line.cablelineName}" name="cableLineName" id="cableLineName"/>
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">���Զˣ�</td>
		      <td class="tdulright" colspan="3">
		      	<input name="factTestPort" type="radio" value="A" checked="checked" />
				      A��${repeat.pointa}
				<input type="radio" name="factTestPort" value="B" />
				      
				      B��${repeat.pointz}
		      </td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft">����ʱ�䣺</td>
		      <td class="tdulright" colspan="3">
		        <input name="factTestTime" id="factTestTime" class="Wdate" style="width:205"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate: '%y-%M-#{%d-6}',maxDate: '%y-%M-%d-%H-%m-%s'})" readonly/>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		    <tr>
		      <td class="tdulleft">���Եص㣺</td>
		      <td class="tdulright" colspan="3">
		      	<html:text property="testAddress" styleId="testAddress" style="width:205px"></html:text>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft">������Ա��</td>
		  	  <td class="tdulright" colspan="3">
			     <font color="red">*</font>&nbsp;<apptag:testman spanId="tester" hiddenId="testMan" state="e" testman="${line.testMan}" tablename="cable1"></apptag:testman>
			   </td>
		    </tr>
		    <tr class=trcolor>
		      <td height="25" colspan="4" bgcolor="#CFE0F0"><span class="STYLE1">���Բ�������</span></td>
		    </tr>
		    <tr class=trcolor>
		      <td class="tdulleft">�����Ǳ�</td>
		      <td class="tdulright" colspan="3">
		      	<html:text property="testApparatus" styleId="testApparatus" ></html:text>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		  <!--  <tr class=trcolor>
		      <td class="tdulleft">���Է�����</td>
		      <td class="tdulright" colspan="3">
		      	<input name="testMethod" type="radio" value="2" checked="checked" />
				      ���㷨
				<input type="radio" name="testMethod" value="0" />
				   LSA
				 <input type="radio" name="testMethod" value="4" />
				     �ĵ㷨 <br/>
	                                    �м̶��޽�ͷѡ��LSA�����н�ͷѡ�����㷨����ͷ��Ĳ���ѡ���ĵ㷨</td>
		    </tr> -->
		    <tr class=trwhite>
		      <td class="tdulleft">���Բ���(nm)��</td>
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
		    	<td class="tdulleft">���������ʣ�</td>
		      <td colspan="3" class="tdulright">
		      		<html:text property="testRefractiveIndex" styleId="testRefractiveIndex" ></html:text>&nbsp;&nbsp;<font color="red">*</font>&nbsp;����ʵ����д
		      </td>
		    </tr>
		    <tr class=trwhite>
		      <td class="tdulleft"><div align="right">����ƽ��ʱ��(s)��</div></td>
		      <td class="tdulright" colspan="3">
		   	 	 <html:text property="testAvgTime" styleId="testAvgTime" value=""></html:text>&nbsp;&nbsp;<font color="red">*</font>
		      </td>
		    </tr>
		    <tr class=trcolor>
		      <td height="25" colspan="4" bgcolor="#CFE0F0">�������� (<c:out value="${coreNumber}"></c:out>����о)
		      	<!-- <input type="button" value="��Ӳ�������" onclick="addTestData();"/>-->
		      </td>
		    </tr>
		    <tr class=trwhite>
			    <td height="25" colspan="4">
				    <div id="chipdata">
				        <table width="100%" border="0" cellspacing="0" cellpadding="0">
				    		<tr>
				    			<td width="7%" >����</td>
				    			<td width="17%">˥������dB/km</td>
				    			<td width="12%">�Ƿ�ϸ�</td>
				    			<td width="12%">�Ƿ񱣴�</td>
				    			<td width="12%">�Ƿ�����</td>
				    			<td width="28%">&nbsp;&nbsp;˵��</td>
				    			<td width="12%">����</td>
				    		</tr>
				    		<c:forEach begin="1" end="${coreNumber}" var="i">
				    		<tr>
				    		<td>${i}</td>
				    		<td>&nbsp;</td>
				    		<td>�ϸ�</td>
				    		<td>δ����</td>
				    		<td>������</td>
				    		<td>&nbsp;</td>
				    		<td><a href="javascript:updateChipData('${i}')">¼��</a>
				            <!--  |<a href="javascript:deleteChip('${i}')">ɾ��</a>-->
				            </td>
				    		</tr>
				    	</c:forEach>
				    	<input id="nonConformity" name="nonConformity" type="hidden" value="0">
				    	</table>
				    </div>
		      	</td>
			</tr>		
			
		    <tr class=trcolor>
		      <td height="25" colspan="4" bgcolor="#CFE0F0">�ֳ����������¼������
		      	<input type="button" value="���������¼" onclick="addProblem();"/>
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
		        <input type="button" class="button" styleId="savebtn" value="�ݴ�" onclick="tempsave();"/>
		        <input type="button" class="button" value="����" onclick="checkAddInfo();"/>
		        <input type="reset" class="button"  value="����" />
		        <input type="button" class="button" onclick="javascript:parent.close();" value="�ر�" />
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
					alert("������Ϊ��!");
					return false;
				}
				if(!reg.test(chipseq) || chipseq<=0){
					alert("����ҪΪ����!����Ҫ��1��ʼ!");
					return false;
				}
				if(chipseq>coreNumber){
					alert("�����ܴ�����о����!");
					return false;
				}
			var confirmResult = chipform.isUsed;
			if(confirmResult[1].checked && confirmResult[1].value=="0"){
				if(attenuation==""){
					alert("˥����������Ϊ��!");
					return false;
				}
				if(!regx.test(attenuation)){
					alert("˥����������Ϊ����!");
					return false;
				}
			}
				if(valCharLength(testRemark)>512){
					alert("˵�����ܳ���512���ַ�����256������!");
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
						   	 alert("�������ظ�!");
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
					alert("������������Ϊ��!");
					return false;
				}
				if(valCharLength(problemDescription)>512){
					alert("�����������ܳ���512���ַ�����256������!");
					return false;
				}
				if(valCharLength(processComment)>512){
					alert("�������˵�����ܳ���512���ַ�����256������!");
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
	      	if(confirm("ȷ��Ҫɾ����?")){
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
	      	if(confirm("ȷ��Ҫɾ����?")){
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
