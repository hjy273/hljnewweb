<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>��������</title>
		<script type='text/javascript'>
				function submitForm(){
					parent.savechip($('addChip'));
				}
				
				function judgeType(obj){
					if(obj=="1"){
						$('con').style.display="none";
						$('att').style.display="none";
					}
					if(obj=="0"){
						$('con').style.display="";
						$('att').style.display="";
					}
				}
				
				function judge(){
					var confirmResult = addChip.isUsed;
					var isSaveValue = addChip.isSave;
			    	if(confirmResult[0].checked && confirmResult[0].value=="1"){
							$('con').style.display="none";
							$('att').style.display="none";
					}else{
					        $('con').style.display="";
							$('att').style.display="";  
					}
					if(isSaveValue[1].checked && isSaveValue[1].value=="0"){
							$('att').style.display="none";
					}else{
							$('att').style.display="";  
					}
					
				}
				
				function judgeIsSave(obj){
					if(obj=="1"){
						$('att').style.display="";
					}
					if(obj=="0"){
						$('att').style.display="none";
					}
				}
		   </script>
	</head>
	<body onload="judge()">
		<html:form action="/enteringCableDataAction.do?method=addChipData"
			styleId="addChip"  method="post">
			<input type="hidden" name="id" value="${chipdata.id}"/>
			<input type="hidden" name="oldchipseq" value="${chipdata.chipSeq}"/>
		    <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			    <tr  class=trcolor>
			      <td class="tdulleft">����</td>
			      <td class="tdulright">
			      	  ${chipdata.chipSeq}  
			      	  <input type="hidden" value="${chipdata.chipSeq}" name="chipSeq" id="chipSeq"/>
				  </td>
			    </tr>
			     <tr class=trwhite>
			      <td class="tdulleft">�Ƿ����ã�</td>
			      <td class="tdulright">
			      <c:if test="${chipdata.isUsed=='1'}">
				      <input name="isUsed" type="radio" value="1" checked="checked" onclick="judgeType(this.value)"/>
				                   ����
				      <input type="radio" name="isUsed" value="0" onclick="judgeType(this.value)"/>
				    	������
			      </c:if>
			        <c:if test="${chipdata.isUsed=='0'}">
				        <input name="isUsed" type="radio" value="1" onclick="judgeType(this.value)"/>
				                     ����
				      <input type="radio" name="isUsed" value="0" checked="checked" onclick="judgeType(this.value)"/>
				    	������
			    	</c:if>
			      </td>
			    </tr>
			    <tbody id="con">
			    <tr  class=trcolor>
			      <td class="tdulleft">�Ƿ�ϸ�</td>
			      <td class="tdulright">
			         <c:if test="${chipdata.isEligible=='1' || empty chipdata.isEligible}">
			         	 <input name="isEligible" type="radio" value="1" checked="checked" />
				                     �ϸ�
				        <input type="radio" name="isEligible" value="0" />
				    	���ϸ�
			         </c:if>
			         <c:if test="${chipdata.isEligible=='0'}">
			         	 <input name="isEligible" type="radio" value="1" />
				                     �ϸ�
				        <input type="radio" name="isEligible" value="0" checked="checked" />
				    	���ϸ�
			         </c:if>
			      </td>
			    </tr>
			      <tr class=trwhite>
			      <td class="tdulleft">�Ƿ񱣴棺</td>
			      <td class="tdulright">
			      <c:if test="${chipdata.isSave=='1'}">
				      <input name="isSave" type="radio" value="1" checked="checked" onclick="judgeIsSave(this.value)"/>
				        ����
				      <input type="radio" name="isSave" value="0" onclick="judgeIsSave(this.value)"/>
				    	   δ����
			      </c:if>
			        <c:if test="${chipdata.isSave=='0' || empty chipdata.isSave}">
				        <input name="isSave" type="radio" value="1" onclick="judgeIsSave(this.value)"/>
				                         ����
				      <input type="radio" name="isSave" value="0" checked="checked" onclick="judgeIsSave(this.value)"/>
				    	    δ����
			    	</c:if>
			      </td>
			    </tr>
			    <tr class=trcolor>
			      <td class="tdulleft">˥��������</td>
			      <td class="tdulright">
			        <html:text property="attenuationConstant" value="${chipdata.attenuationConstant}"></html:text>dB/km&nbsp;&nbsp;<font color="red">*</font>
			     </td>
			    </tr>
			    </tbody>
			    <tr  class=trwhite>
			      <td class="tdulleft">˵����</td>
			      <td class="tdulright">
			       <html:textarea property="testRemark" value="${chipdata.testRemark}" styleClass="max-length-512" rows="4" style="width:275px"></html:textarea>	  
			      </td>
			    </tr>
			    <tbody id="att" style="display:none">
			    <tr  class=trcolor>
			      <td class="tdulleft">�������ߣ�</td>
			      <td>
			         <apptag:upload state="edit" value="${chipdata.attachments}" cssClass="" entityId="${chipdata.id}" entityType="LP_TEST_CHIP_DATA"/>
			       </td>
			    </tr>
			    </tbody>
			    <tr class=trwhite>
			      <td align="center" colspan="2">
			        <input type="button"  onclick="submitForm();" class="button" value="����"/>
			        <input type="button"  onclick="javascript:parent.close();" class="button" value="�ر�"/>
			      </td>
			    </tr>
			  </table>
		</html:form>
		<script language="javascript" type="text/javascript">
     function toDeleteCable(idValue){    
  		var params = "&cableid="+idValue;
  		var url = "${ctx}/testPlanAction.do?method=deleteCable"
  		var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:true});
    }
    
    function callback(originalRequest) {
    		var rst = originalRequest.responseText;
  			var myGlobalHandlers = {onCreate:function () {
			}, onFailure:function () {
				alert("�������ӳ������⣬��رպ�����!");
			}, onComplete:function () {
			}};
			Ajax.Responders.register(myGlobalHandlers);
			freshPage();
    }
	
	</script>
	</body>
</html>
