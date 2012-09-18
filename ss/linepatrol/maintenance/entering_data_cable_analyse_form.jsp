<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type='text/javascript'>
			function checkAddInfo() {
			    var regx = /^[1-9]\d{0,6}[\.]{0,4}\d{0,6}$|^0(\.\d*)?$/;
			    var reg=/^\d+$/;
				var baseStation =document.getElementById("baseStation").value;
				if(baseStation=="" || baseStation.trim()==""){
					alert("测试基站不能为空!");
					$("baseStation").focus();
					return;
				}
				var testDate =document.getElementById("testDate").value;
				if(testDate==""){
					alert("记录日期不能为空!");
					$("testDate").focus();
					return;
				}
				
				var fileRemark =document.getElementById("fileRemark").value;
				if(valCharLength(fileRemark)>1024){
					alert("分析文件记录说明不能超过1024个字符或者512个汉字!");
					return;
				}
				
				var refractiveIndex =document.getElementById("refractiveIndex").value;
				if(refractiveIndex==null || refractiveIndex==""){
					alert("测试折射率不能为空!");
					$("refractiveIndex").focus();
					return;
				}
				if(!regx.test(refractiveIndex)){
					alert("测试折射率必须为数字!");
					$("refractiveIndex").focus();
					return false;
				}
				
				var pulseWidth =document.getElementById("pulseWidth").value;
				if(pulseWidth==""){
					alert("测试脉宽不能为空!");
					$("pulseWidth").focus();
					return;
				}
				if(!regx.test(pulseWidth)){
					alert("测试脉宽必须为数字!");
					$("pulseWidth").focus();
					return false;
				}
				
				var coreLength =document.getElementById("coreLength").value;
				if(coreLength==""){
					alert("芯长不能为空!");
					$("coreLength").focus();
					return;
				}
				
				if(!regx.test(coreLength)){
					alert("芯长必须为数字!");
					$("coreLength").focus();
					return false;
				}
				
				var decayConstant =document.getElementById("decayConstant").value;
				if(decayConstant==""){
					alert("衰减常数不能为空!");
					$("decayConstant").focus();
					return;
				}

                var endWaste =document.getElementById("endWaste").value;
				if(endWaste==""){
					alert("成端损耗不能为空!");
					$("endWaste").focus();
					return;
				}
				
				
				 var wasteID = document.getElementById("waste"); 
	  			 var rownum = wasteID.rows.length;
	  			 var rowid;
				 for(i =1; i<rownum;i++){//判断行是不是空数据
					rowid=wasteID.rows[i].id;
					if(!reg.test(rowid)){
						continue;
					}
			        var r = rowid-1;
			        var orderNumber = document.getElementById('orderNumber'+rowid).value;
			        var connectorStation = document.getElementById('connectorStation'+rowid).value;
			        var waste = document.getElementById('waste'+rowid).value;
			        var problemAnalyse = document.getElementById('problemAnalyse'+rowid).value;
			        var remark = document.getElementById('remark'+rowid).value;
			      	if(orderNumber==""){
			      		alert("第"+r+"行接头损耗分析的序号不能为空!");
			      		return;
			      	}
			      	if(!reg.test(orderNumber) || orderNumber<=0){
						alert("第"+r+"行接头损耗分析的序号要为数字!并且要从1开始!");
						return false;
				    }
			      	if(connectorStation==""){
			      		alert("第"+r+"行接头损耗分析的接头位置不能为空!");
			      		return;
			      	}
			      	if(waste==""){
			      		alert("第"+r+"行接头损耗分析的损耗值不能为空!");
			      		return;
			      	}
			      	if(!regx.test(waste)){
						alert("第"+r+"行接头损耗分析的损耗值必须为数字!");
						return false;
				    }
				
			     }
			
				
				 var execID = document.getElementById("exec"); 
	  			 var rownumExe = execID.rows.length;
	  			 var rowID;
				 for(m =1; m<rownumExe;m++){//判断行是不是空数据
					rowID=execID.rows[m].id;
					if(!reg.test(rowID)){
						continue;
					}
			        var row = rowID-1;
			        var orderNumberExe = document.getElementById('orderNumberExe'+rowID).value;
			        var eventStation = document.getElementById('eventStation'+rowID).value;
			        var wasteExe = document.getElementById('wasteExe'+rowID).value;
			        var problemAnalyseExe = document.getElementById('problemAnalyseExe'+rowID).value;
			        var remarkExe = document.getElementById('remarkExe'+rowID).value;
			      	if(orderNumberExe==""){
			      		alert("第"+row+"行异常事件分析的序号不能为空!");
			      		return;
			      	}
			      	if(!reg.test(orderNumberExe) || orderNumberExe<=0){
						alert("第"+row+"行异常事件分析的序号要为数字!并且要从1开始!");
						return false;
				    }
			      	if(eventStation==""){
			      		alert("第"+row+"行异常事件分析的事件位置不能为空!");
			      		return;
			      	}
			      	if(wasteExe==""){
			      		alert("第"+row+"行异常事件分析的损耗值不能为空!");
			      		return;
			      	}
			      	if(!regx.test(wasteExe)){
						alert("第"+row+"行异常事件分析的损耗值必须为数字!");
						return false;
				    }
				
			     }
			
				//$("analyseform").submit();
				savedata($("analyseform"));
		}
		
		function savedata(obj){
					obj.request({ 
					   onComplete: function(originalRequest){ 
						   var rst = originalRequest.responseText;						  
						   	parent.$('chipdata').update(rst);
						   	parent.close();
						}
				     }); 
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
	    
	    
	    
		 //添加一个新行
    function addWasteRow(){		
		var queryID = document.getElementById("waste");
        var  onerow=queryID.insertRow(-1);
        onerow.id = queryID.rows.length;
        var   cell1=onerow.insertCell();
        var   cell2=onerow.insertCell();
        var   cell3=onerow.insertCell();
        var   cell4=onerow.insertCell();
        var   cell5=onerow.insertCell();
 		var   cell6=onerow.insertCell();

		var orderNumber = document.createElement("input");
		orderNumber.name = "orderNumber"
        orderNumber.id = "orderNumber" + onerow.id;
        orderNumber.type="text";
        orderNumber.style.width="70px";

		var connectorStation = document.createElement("input");
		connectorStation.name = "connectorStation"
        connectorStation.id = "connectorStation" + onerow.id;
        connectorStation.type="text";
        connectorStation.style.width="125px";

		var waste = document.createElement("input");
		waste.name = "waste"
        waste.id = "waste" + onerow.id;
        waste.type="text";
        waste.style.width="70px";

        var problemAnalyse = document.createElement("textarea");
        problemAnalyse.name = "problemAnalyse"
        problemAnalyse.id = "problemAnalyse" + onerow.id;
       

 		var remark = document.createElement("textarea");
        remark.name = "remark"
        remark.id = "remark" + onerow.id;
        
         //创建删除按钮
        var delWaste =document.createElement("button");
        delWaste.value = "删除";
        delWaste.id = "delWaste" + onerow.id;
        delWaste.onclick=deleteRowWaste;
        
       // cell1.innerText=onerow.id-1;
        cell1.appendChild(orderNumber);
        cell2.appendChild(connectorStation);
        cell3.appendChild(waste);
        cell4.appendChild(problemAnalyse);
 		cell5.appendChild(remark);
        cell6.appendChild(delWaste);

    }
	 
    //脚本生成的删除按  钮的删除动作
    function deleteRowWaste(){
    var queryID = document.getElementById("waste"); 
          //获得按钮所在行的id
         var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(8,rowid.length);
        //由id转换为行索找行的索引,并删除
          for(i =0; i<queryID.rows.length;i++){//alert("rowid:"+rowid+" queryID.rows[i].id :"+queryID.rows[i].id );
            if(queryID.rows[i].id == rowid){
                queryID.deleteRow(queryID.rows[i].rowIndex);
            }
        }
    }
	
	
	 
		 //添加一个新行
    function addExeceptionRow(){		
		var queryID = document.getElementById("exec");
        var  onerow=queryID.insertRow(-1);
        onerow.id = queryID.rows.length;
        var   cell1=onerow.insertCell();
        var   cell2=onerow.insertCell();
        var   cell3=onerow.insertCell();
        var   cell4=onerow.insertCell();
        var   cell5=onerow.insertCell();
 		var   cell6=onerow.insertCell();

		var orderNumber = document.createElement("input");
		orderNumber.name = "orderNumberExe"
        orderNumber.id = "orderNumberExe" + onerow.id;
        orderNumber.type="text";
        orderNumber.style.width="70px";

		var eventStation = document.createElement("input");
		eventStation.name = "eventStation"
        eventStation.id = "eventStation" + onerow.id;
        eventStation.type="text";
        eventStation.style.width="125px";

		var waste = document.createElement("input");
		waste.name = "wasteExe"
        waste.id = "wasteExe" + onerow.id;
        waste.type="text";
        waste.style.width="70px";

        var problemAnalyse = document.createElement("textarea");
        problemAnalyse.name = "problemAnalyseExe"
        problemAnalyse.id = "problemAnalyseExe" + onerow.id;
       

 		var remark = document.createElement("textarea");
        remark.name = "remarkExe"
        remark.id = "remarkExe" + onerow.id;
        
         //创建删除按钮
        var delExe =document.createElement("button");
        delExe.value = "删除";
        delExe.id = "delExe" + onerow.id;
        delExe.onclick=deleteRowExe;
        
        cell1.appendChild(orderNumber);
        cell2.appendChild(eventStation);
        cell3.appendChild(waste);
        cell4.appendChild(problemAnalyse);
 		cell5.appendChild(remark);
        cell6.appendChild(delExe);

    }
	 
    //脚本生成的删除按  钮的删除动作
    function deleteRowExe(){
    var queryID = document.getElementById("exec"); 
          //获得按钮所在行的id
         var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(6,rowid.length);
        //由id转换为行索找行的索引,并删除
          for(i =0; i<queryID.rows.length;i++){//alert("rowid:"+rowid+" queryID.rows[i].id :"+queryID.rows[i].id );
            if(queryID.rows[i].id == rowid){
                queryID.deleteRow(queryID.rows[i].rowIndex);
            }
        }
    }
		
	    </script>
	</head>
	<body>
		<html:form action="/enteringCableDataAction.do?method=addAnalyseData" styleId="analyseform">
		<table width="100%" border="1" align="center"  bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" bordercolor="#DBDBD8" style="border-collapse: collapse"> 
			<input type="hidden" name="chiseq" value="${chipdata.chipSeq}"/>
		    <tr>
		      <td align="right">中继段：</td>
		      <td  colspan="3"><c:out value="${trunkName}"></c:out></td>
		      <td align="right">纤序：</td>
		      <td ><c:out value="${chipdata.chipSeq}"></c:out></td>
		    </tr>
		    <tr>
		      <td align="right">测试端：</td>
		      <td  colspan="2">
		      	<input name="abEnd" type="radio" value="A" checked="checked" />A端&nbsp;
				<input type="radio" name="abEnd" value="B" />B端
		      </td>
		       <td align="right">测试基站：</td>
		       <td colspan="2" >
			       	<html:text property="baseStation" styleId="baseStation"></html:text>
			       	<font color="red">*</font>
		       </td>
		    </tr>
		      <tr>
			      <td align="right">记录日期：</td>
			      <td colspan="5">
			          <input name="testDate" id="testDate" class="Wdate" 
			          onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate: '%y-%M-%d'})" readonly/>
			          &nbsp;&nbsp;<font color="red">*</font>
			      </td>
		      </tr>
		    <tr >
		      <td height="25px" bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">分析文件记录</font></td>
		    </tr>
		    <tr >
		       <td colspan="3" align="center">保存文件名称</td>
		       <td  colspan="3" align="left">&nbsp;&nbsp;&nbsp;&nbsp;说明</td>
		    </tr>
		     <tr>
		       <td colspan="3">
		          <apptag:upload state="look" value="${chipdata.attachments}" cssClass="" entityId="${chipdata.id}" entityType="LP_TEST_CHIP_DATA"/>
		       </td>
		       <td colspan="3">
		       	  <html:textarea property="fileRemark" styleId="fileRemark" rows="3" style="width:275px"></html:textarea>
		       </td>
		    </tr>
		     <tr>
		      <td height="25"  bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">纤芯长度分析</font></td>
		    </tr>
		    <tr >
		       <td align="center">测试折射率</td>
		       <td align="center">测试脉宽</td>
		       <td align="center">芯长km</td>
		       <td align="center">是否有问题</td>
		       <td align="center">问题分析</td>
		       <td align="center">备注</td>
		    </tr>  
		    
		    <tr>
		       <td >
			       <html:text property="refractiveIndex" styleId="refractiveIndex" value="" style="width:70px"></html:text>
			       <font color="red">*</font>
		       </td>
		       <td >
			       <html:text property="pulseWidth" styleId="pulseWidth" value="" style="width:70px"></html:text>
			       <font color="red">*</font>
		       </td>
		       <td >
			       <html:text property="coreLength" styleId="coreLength" value="" style="width:70px"></html:text>
			       <font color="red">*</font>
		       </td>
		       <td >
		         <input name="isProblem" type="radio" value="1" checked="checked" />有&nbsp;
				 <input type="radio" name="isProblem" value="0" />没有
		       </td>
		       <td > <html:textarea property="problemAnalyseLen"></html:textarea></td>
		       <td > <html:textarea property="lengremark"></html:textarea></td>
		    </tr>  
		    
		     <tr>
		      <td height="25"  bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">衰减常数分析</font></td>
		    </tr>
		    
		    <tr >
		       <td align="center">衰减常数dB/km</td>
		       <td align="center">是否合格</td>
		       <td align="center" colspan="2">问题分析</td>
		       <td align="center" colspan="2">备注</td>
		    </tr>
		     <tr>
		       <td >
			       <html:text property="decayConstant" styleId="decayConstant" value="" style="width:70px"></html:text>
			       <font color="red">*</font>
		       </td>
		       <td >
		         <input name="isStandardDec" type="radio" value="1" checked="checked" />合格&nbsp;
				 <input type="radio" name="isStandardDec" value="0" />不合格
		       </td>
		       <td colspan="2"> <html:textarea property="problemAnalyseDec"></html:textarea></td>
		       <td  colspan="2"> <html:textarea property="decayRemark"></html:textarea></td>
		    </tr>    
		    
		    <tr>
		      <td height="25" bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">成端损耗分析</font></td>
		    </tr>
		    
		    <tr >
		       <td align="center">成端损耗dB</td>
		       <td align="center">是否合格</td>
		       <td align="center" colspan="2">问题分析</td>
		       <td align="center" colspan="2">备注</td>
		    </tr>  
		    <tr >
		       <td>
			       <html:text property="endWaste" value="" style="width:70px"></html:text>
			       <font color="red">*</font>
		       </td>
		       <td>
		       	  <input name="isStandardEnd" type="radio" value="1" checked="checked" />合格&nbsp;
				  <input type="radio" name="isStandardEnd" value="0" />不合格
		       </td>
		       <td colspan="2"> <html:textarea property="problemAnalyseEnd"></html:textarea></td>
		       <td colspan="2"> <html:textarea property="endRemark"></html:textarea></td>
		    </tr>    
		    
		    <tr >
		      <td height="25" bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">接头损耗分析</font>（记录分析存在问题或损值超过0.5dB的接头）</td>
		    </tr>
		    <tr>
		    	<td colspan="6"><input class="button" type="button" onclick="addWasteRow()" value="添加损耗分析"/></td>
		    </tr>
		    <tr>
		    	<td colspan="6">
		    	    <table border="1" width="100%" id="waste" align="center"  bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" bordercolor="#DBDBD8" style="border-collapse: collapse">
			    	    <tr>
					       <td align="center">序号</td>
					       <td align="center">接头位置</td>
					       <td align="center">损耗值dB</td>
					       <td align="center">问题分析</td>
					       <td align="center" colspan="2">备注</td>
					    </tr>  
		    	    </table>
		    	</td>
		    </tr>
		     <tr>
		      <td height="25"  bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">异常事件分析</font>（包括反射或非反射事件等）</td>
		    </tr>
		     <tr>
		    	<td colspan="6"><input class="button" type="button" onclick="addExeceptionRow()" value="添加异常分析"/></td>
		    </tr>
		    <tr>
		    	<td colspan="6">
		    	    <table border="1" width="100%" id="exec" align="center"  bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" bordercolor="#DBDBD8" style="border-collapse: collapse">
			    	    <tr >
					       <td align="center">序号</td>
					       <td align="center">事件位置</td>
					       <td align="center">损耗值dB</td>
					       <td align="center">问题分析</td>
					       <td align="center" colspan="2">备注</td>
					    </tr> 
		    	    </table>
		    	</td>
		    </tr>
		  
		    <tr>
		      <td height="25"  bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">其他分析</font></td>
		    </tr>
		    <tr >
		       <td align="center" colspan="2">分析简述</td>
		       <td align="center" colspan="2">分析结果</td>
		       <td align="center" colspan="2">说明</td>
		    </tr>  
		     <tr>
		       <td colspan="2"><html:textarea property="analyseOther" style="width:225px" rows="3"></html:textarea></td>
		       <td colspan="2"><html:textarea property="analyseResultOther" style="width:225px" rows="3"></html:textarea></td>
		       <td colspan="2"><html:textarea property="remarkOther" style="width:225px" rows="3"></html:textarea></td>
		    </tr>  
		    <tr class=trcolor>
		      <td colspan="6" align="center">
		        <input type="button" class="button" value="保存" onclick="checkAddInfo();"/>
		        <input type="reset" class="button"  value="重置" />
		        <input type="button" class="button" onclick="javascript:parent.close();" value="关闭" />
		      </td>
		    </tr>
		  </table>
		</html:form>
		<script type="text/javascript">
	jQuery(document).ready(function() {
	jQuery("#analyseform").bind("submit",function(){processBar(analyseform);});
})
	</script>
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
