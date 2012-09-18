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
				if(baseStation==""){
					alert("���Ի�վ����Ϊ��!");
					$("baseStation").focus();
					return;
				}
				var testDate =document.getElementById("testDate").value;
				if(testDate==""){
					alert("��¼���ڲ���Ϊ��!");
					$("testDate").focus();
					return;
				}
				
				var fileRemark =document.getElementById("fileRemark").value;
				if(valCharLength(fileRemark)>1024){
					alert("�����ļ���¼˵�����ܳ���1024���ַ�����512������!");
					return;
				}
				
				var refractiveIndex =document.getElementById("refractiveIndex").value;
				if(refractiveIndex==null || refractiveIndex==""){
					alert("���������ʲ���Ϊ��!");
					$("refractiveIndex").focus();
					return;
				}
				if(!regx.test(refractiveIndex)){
					alert("���������ʱ���Ϊ����!");
					$("refractiveIndex").focus();
					return false;
				}
				
				var pulseWidth =document.getElementById("pulseWidth").value;
				if(pulseWidth==""){
					alert("����������Ϊ��!");
					$("pulseWidth").focus();
					return;
				}
				if(!regx.test(pulseWidth)){
					alert("�����������Ϊ����!");
					$("pulseWidth").focus();
					return false;
				}
				
				var coreLength =document.getElementById("coreLength").value;
				if(coreLength==""){
					alert("о������Ϊ��!");
					$("coreLength").focus();
					return;
				}
				
				if(!regx.test(coreLength)){
					alert("о������Ϊ����!");
					$("coreLength").focus();
					return false;
				}
				
				var decayConstant =document.getElementById("decayConstant").value;
				if(decayConstant==""){
					alert("˥����������Ϊ��!");
					$("decayConstant").focus();
					return;
				}

                var endWaste =document.getElementById("endWaste").value;
				if(endWaste==""){
					alert("�ɶ���Ĳ���Ϊ��!");
					$("endWaste").focus();
					return;
				}
				
				
				 var wasteID = document.getElementById("waste"); 
	  			 var rownum = wasteID.rows.length;
	  			 var rowid;
				 for(i =1; i<rownum;i++){//�ж����ǲ��ǿ�����
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
			      		alert("��"+r+"�н�ͷ��ķ�������Ų���Ϊ��!");
			      		return;
			      	}
			      	if(!reg.test(orderNumber) || orderNumber<=0){
						alert("��"+r+"�н�ͷ��ķ��������ҪΪ����!����Ҫ��1��ʼ!");
						return false;
				    }
			      	if(connectorStation==""){
			      		alert("��"+r+"�н�ͷ��ķ����Ľ�ͷλ�ò���Ϊ��!");
			      		return;
			      	}
			      	if(waste==""){
			      		alert("��"+r+"�н�ͷ��ķ��������ֵ����Ϊ��!");
			      		return;
			      	}
			      	if(!regx.test(waste)){
						alert("��"+r+"�н�ͷ��ķ��������ֵ����Ϊ����!");
						return false;
				    }
			     }
			
				
				 var execID = document.getElementById("exec"); 
	  			 var rownumExe = execID.rows.length;
	  			 var rowID;
				 for(m =1; m<rownumExe;m++){//�ж����ǲ��ǿ�����
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
			      		alert("��"+row+"���쳣�¼���������Ų���Ϊ��!");
			      		return;
			      	}
			      	if(!reg.test(orderNumberExe) || orderNumberExe<=0){
						alert("��"+row+"���쳣�¼����������ҪΪ����!����Ҫ��1��ʼ!");
						return false;
				    }
			      	if(eventStation==""){
			      		alert("��"+row+"���쳣�¼��������¼�λ�ò���Ϊ��!");
			      		return;
			      	}
			      	if(wasteExe==""){
			      		alert("��"+row+"���쳣�¼����������ֵ����Ϊ��!");
			      		return;
			      	}
			      	if(!regx.test(wasteExe)){
						alert("��"+row+"���쳣�¼����������ֵ����Ϊ����!");
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
	    
	    
	    
		 //���һ������
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
        
         //����ɾ����ť
        var delWaste =document.createElement("button");
        delWaste.value = "ɾ��";
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
	 
    //�ű����ɵ�ɾ����  ť��ɾ������
    function deleteRowWaste(){
    var queryID = document.getElementById("waste"); 
          //��ð�ť�����е�id
         var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(8,rowid.length);
        //��idת��Ϊ�������е�����,��ɾ��
          for(i =0; i<queryID.rows.length;i++){//alert("rowid:"+rowid+" queryID.rows[i].id :"+queryID.rows[i].id );
            if(queryID.rows[i].id == rowid){
                queryID.deleteRow(queryID.rows[i].rowIndex);
            }
        }
    }
    
    
    function deleteRowWasteEdit(obj){
		    var queryID = document.getElementById("waste"); 
		        var rowid = "000";
		        rowid = obj.id;
		      // rowid = rowid.substring(8,rowid.length);
		        for(i =0; i<queryID.rows.length;i++){
		            if(queryID.rows[i].id == rowid){
		                queryID.deleteRow(queryID.rows[i].rowIndex);
		            }
		        }
	  }
	
	
	 
		 //���һ������
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
        
         //����ɾ����ť
        var delExe =document.createElement("button");
        delExe.value = "ɾ��";
        delExe.id = "delExe" + onerow.id;
        delExe.onclick=deleteRowExe;
        
        cell1.appendChild(orderNumber);
        cell2.appendChild(eventStation);
        cell3.appendChild(waste);
        cell4.appendChild(problemAnalyse);
 		cell5.appendChild(remark);
        cell6.appendChild(delExe);

    }
	 
    //�ű����ɵ�ɾ����  ť��ɾ������
    function deleteRowExe(){
    var queryID = document.getElementById("exec"); 
          //��ð�ť�����е�id
         var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(6,rowid.length);
        //��idת��Ϊ�������е�����,��ɾ��
          for(i =0; i<queryID.rows.length;i++){//alert("rowid:"+rowid+" queryID.rows[i].id :"+queryID.rows[i].id );
            if(queryID.rows[i].id == rowid){
                queryID.deleteRow(queryID.rows[i].rowIndex);
            }
        }
    }
    
       function deleteRowExeEdit(obj){
		    var queryID = document.getElementById("exec"); 
		        var rowid = "000";
		        rowid = obj.id;
		     //   rowid = rowid.substring(6,rowid.length);alert(rowid);
		        for(i =0; i<queryID.rows.length;i++){
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
			<input type="hidden" name="chiseq" id="chiseq" value="${chipdata.chipSeq}"/>
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
			      	<input name="abEnd" type="radio" value="A" checked="checked" />A��&nbsp;
					<input type="radio" name="abEnd" value="B" />B��
		      	</c:if>
		      	<c:if test="${chipdata.coreData.abEnd=='B'}">
			      	<input name="abEnd" type="radio" value="A" />A��&nbsp;
					<input type="radio" name="abEnd" value="B" checked="checked" />B��
				</c:if>
		      </td>
		       <td align="right">���Ի�վ��</td>
		       <td colspan="2" >
		       	<html:text property="baseStation" styleId="baseStation" value="${chipdata.coreData.baseStation}"></html:text>
		       	<font color="red">*</font>
		       </td>
		    </tr>
		      <tr>
			      <td align="right">��¼���ڣ�</td>
			      <td colspan="5">
			          <input name="testDate" id="testDate" class="Wdate" 
			          value="<bean:write name="chipdata" property="coreData.testDate" format="yyyy/MM/dd"/>"
			           onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate: '%y-%M-%d'})" readonly/>
			           <font color="red">*</font>
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
		       	  <html:textarea property="fileRemark" styleId="fileRemark" rows="3" style="width:275px"
		       	  value="${chipdata.coreData.fileRemark}"></html:textarea>
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
		       		<html:text property="refractiveIndex" styleId="refractiveIndex" value="${chipdata.corelength.refractiveIndex}" style="width:70px"></html:text>
		            <font color="red">*</font>
		       </td>
		       <td >
		       		<html:text property="pulseWidth" styleId="pulseWidth" value="${chipdata.corelength.pulseWidth}" style="width:70px"></html:text>
		       		<font color="red">*</font>
		       </td>
		       <td >
		       		<html:text property="coreLength" styleId="coreLength" value="${chipdata.corelength.coreLength}" style="width:70px"></html:text>
		            <font color="red">*</font>
		       </td>
		       <td >
			     <c:if test="${chipdata.corelength.isProblem=='1'}">
			         <input name="isProblem" type="radio" value="1" checked="checked"/>��&nbsp;
					 <input type="radio" name="isProblem" value="0" />û��
				 </c:if>
				  <c:if test="${chipdata.corelength.isProblem=='0'}">
			         <input name="isProblem" type="radio" value="1"/>��&nbsp;
					 <input type="radio" name="isProblem" value="0" checked="checked"/>û��
				 </c:if>
		       </td>
		       <td > <html:textarea property="problemAnalyseLen" styleId="problemAnalyseLen" value="${chipdata.corelength.problemAnalyseLen}"></html:textarea></td>
		       <td > <html:textarea property="lengremark" styleId="lengremark" value="${chipdata.corelength.lengremark}"></html:textarea></td>
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
		           <html:text property="decayConstant" styleId="decayConstant" value="${chipdata.decay.decayConstant}" style="width:70px"></html:text>
		           <font color="red">*</font>
		       </td>
		       <td >
		        <c:if test="${chipdata.decay.isStandardDec=='1'}">
			       	 <input name="isStandardDec" type="radio" value="1" checked="checked" />�ϸ�&nbsp;
					 <input type="radio" name="isStandardDec" value="0" />���ϸ�
		        </c:if>
		         <c:if test="${chipdata.decay.isStandardDec=='0'}">
			       	 <input name="isStandardDec" type="radio" value="1" />�ϸ�&nbsp;
					 <input type="radio" name="isStandardDec" value="0" checked="checked" />���ϸ�
		        </c:if>
		       </td>
		       <td colspan="2"> <html:textarea property="problemAnalyseDec" value="${chipdata.decay.problemAnalyseDec}" ></html:textarea></td>
		       <td  colspan="2"> <html:textarea property="decayRemark" value="${chipdata.decay.decayRemark}" ></html:textarea></td>
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
		       		<html:text property="endWaste" value="${chipdata.endwaste.endWaste}" style="width:70px"></html:text>
		       		<font color="red">*</font>
		       </td>
		       <td>
		        <c:if test="${chipdata.endwaste.isStandardEnd=='1'}">
		        	 <input name="isStandardEnd" type="radio" value="1" checked="checked" />�ϸ�&nbsp;
				     <input type="radio" name="isStandardEnd" value="0" />���ϸ�
		        </c:if>
		         <c:if test="${chipdata.endwaste.isStandardEnd=='0'}">
		        	 <input name="isStandardEnd" type="radio" value="1"  />�ϸ�&nbsp;
				     <input type="radio" name="isStandardEnd" value="0" checked="checked"/>���ϸ�
		        </c:if>
		       </td>
		       <td colspan="2"> <html:textarea property="problemAnalyseEnd" value="${chipdata.endwaste.problemAnalyseEnd}"></html:textarea></td>
		       <td colspan="2"> <html:textarea property="endRemark" value="${chipdata.endwaste.endRemark}"></html:textarea></td>
		    </tr>    
		    
		    <tr>
		        <td height="25" bgcolor="#E6EFF7" colspan="6" align="center"><font size="2px" style="font-weight: bold">��ͷ��ķ���</font>����¼���������������ֵ����0.5dB�Ľ�ͷ��</td>
		    </tr>
		    <tr>
		    	<td colspan="6"><input class="button" type="button" onclick="addWasteRow()" value="�����ķ���"/></td>
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
				    		<tr id="delWaste${loop.index}">
				    			<input type="hidden" name="proid" id="proid" value=""/>
				    			<td>
				    				<input name=orderNumber  id="orderNumber" style="width:70px"  
				    				value="${conwa.orderNumber}"/>
				    			</td>
				    			<td>
				    			   <input name="connectorStation" id="connectorStation" style="width:125px" 
                                     value="${conwa.connectorStation}"/>
				    			</td>
				    			<td><input name="waste" id="waste" style="width:70px" 
				    				 value="${conwa.waste}"/>
				    			</td>
				    			<td>
				    				 <html:textarea property="problemAnalyse"  value="${conwa.problemAnalyse}"></html:textarea>
				    			</td>
				    			<td>
				    			      <html:textarea property="remark"  value="${conwa.remark}"></html:textarea>
				    			</td>
				    			<td>
										<input id="delWaste${loop.index}" type="button" value="ɾ��" onclick="deleteRowWasteEdit(this);"/>
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
		    	<td colspan="6"><input class="button" type="button" onclick="addExeceptionRow()" value="����쳣����"/></td>
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
				    		<tr id="delExe${loop.index}">
				    			<input type="hidden" name="proid" id="proid" value=""/>
				    			<td>
				    				<input name=orderNumberExe  id="orderNumberExe" style="width:70px"  
				    				value="${event.orderNumberExe}"/>
				    			</td>
				    			<td>
				    			   <input name="eventStation" id="eventStation" style="width:125px" 
                                     value="${event.eventStation}"/>
				    			</td>
				    			<td><input name="wasteExe" id="wasteExe" style="width:70px" 
				    				 value="${event.wasteExe}"/>
				    			</td>
				    			<td>
				    				<html:textarea property="problemAnalyseExe"  value="${event.problemAnalyseExe}"></html:textarea>
				    			</td>
				    			<td>
				    			     <html:textarea property="remarkExe"  value="${event.remarkExe}"></html:textarea>
				    			</td>
				    			<td>
										<input id="delExe${loop.index}" type="button" value="ɾ��" onclick="deleteRowExeEdit(this);"/>
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
		       		<html:textarea property="analyseOther" style="width:225px" rows="3"
		       		value="${chipdata.otherAnalyse.analyseOther}"></html:textarea>
		       </td>
		       <td colspan="2">
		       		<html:textarea property="analyseResultOther" style="width:225px" rows="3"
		       		value="${chipdata.otherAnalyse.analyseResultOther}"></html:textarea>
		       </td>
		       <td colspan="2">
		       		<html:textarea property="remarkOther" style="width:225px" rows="3"
		       		value="${chipdata.otherAnalyse.remarkOther}"></html:textarea>
		       </td>
		    </tr>  
		    <tr class=trcolor>
		      <td colspan="6" align="center">
		        <input type="button" class="button" value="����" onclick="checkAddInfo();"/>
		        <input type="reset" class="button"  value="����" />
		        <input type="button" class="button" onclick="javascript:parent.close();" value="�ر�" />
		      </td>
		    </tr>
		  </table>
		</html:form>
	</body>
	<script type="text/javascript">
	jQuery(document).ready(function() {
	jQuery("#analyseform").bind("submit",function(){processBar(analyseform);});
})
	</script>
</html>
