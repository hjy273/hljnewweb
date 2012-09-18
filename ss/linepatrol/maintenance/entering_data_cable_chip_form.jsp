<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>测试数据</title>
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

	<body>
		<html:form action="/enteringCableDataAction.do?method=addChipData"
			styleId="addChip"  method="post" >
		    <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			    <tr  class=trcolor>
			      <td class="tdulleft">纤序：</td>
			      <td class="tdulright">
			      	  <html:text property="chipSeq" ></html:text>&nbsp;&nbsp;<font color="red">*</font>
				  </td>
			    </tr>
			     <tr  class=trwhite>
			      <td class="tdulleft">是否在用：</td>
			      <td class="tdulright">
			        <input name="isUsed" type="radio" value="1"  onclick="judgeType(this.value)"/>
			               在用
			      <input type="radio" name="isUsed" value="0" checked="checked" onclick="judgeType(this.value)"/>
			    	不在用
			      </td>
			    </tr>
			     <tbody id="con">
			    <tr  class=trcolor>
			      <td class="tdulleft">是否合格：</td>
			      <td class="tdulright">
			        <input name="isEligible" type="radio" value="1" checked="checked"  />
			                     合格
			      <input type="radio" name="isEligible" value="0"  />
			    	不合格
			      </td>
			    </tr>
			     <tr class=trwhite>
			      <td class="tdulleft">是否保存：</td>
			      <td class="tdulright">
			        <input name="isSave" type="radio" value="1"  onclick="judgeIsSave(this.value)" />
			                 保存
			      <input type="radio" name="isSave" value="0" checked="checked" onclick="judgeIsSave(this.value)"/>
			    	未保存
			      </td>
			    </tr>
			    <tr class=trcolor>
			      <td class="tdulleft">衰减常数：</td>
			      <td class="tdulright">
			        <html:text property="attenuationConstant" ></html:text>dB/km&nbsp;&nbsp;<font color="red">*</font>
			     </td>
			    </tr>
			    </tbody>
			    <tr  class=trwhite>
			      <td class="tdulleft">说明：</td>
			      <td class="tdulright">
			       <html:textarea property="testRemark" styleClass="max-length-512" rows="4" style="width:275px"></html:textarea>	  
			      </td>
			    </tr>
			     <tbody id="att" style="display:none">
			    <tr class=trcolor>
				   		<td class="tdulleft">测试曲线:</td>
				   		<td class="tdulright"  colspan="3">
				   		  <apptag:upload state="add" cssClass="uploadButton" />
				   		</td>
				 </tr>
				 </tbody>
			    <tr class=trwhite>
			      <td align="center" colspan="2">
			        <input type="button"  onclick="submitForm();" class="button" value="保存"/>
			        <input type="button"  onclick="javascript:parent.close();" class="button" value="关闭"/>
			      </td>
			    </tr>
			  </table>
		</html:form>
	</body>
</html>
