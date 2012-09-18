<%@include file="/common/header.jsp"%>
<html>
<head>
<script type="" language="javascript">
	function cancel() {
  		this.close();
	}

	function displayLineCut(id) {
		alert("linecut");
  		//var url = "";
		//window.open();
	}

	function displayChange(id) {
		alert("change");
  		//var url = "";
		//window.open();
	}
</script>
<title>
	材料用途详细页面
</title>
</head>
<body >
      <table height="30"><tr><td></td></tr></table>
      <table id="tableId" width="400" border="1" align="center" cellpadding="3" cellspacing="0" class="tabout">      
      <tr>
      	<th width="60" class="thlist" align="center" >用途类别</th>
      	<th width="280" class="thlist" align="center">用途名称</th>
      	<th width="40" class="thlist" align="center">数量</th>
      </tr>
      <logic:notEmpty name="linecutList">
	      <logic:present name="linecutList">	 
	      		<logic:iterate id="element" name="linecutList">
  					<tr>
  						<td align="center">割接</td>
  						<td>
  							<a href="LineCutReAction.do?method=showOneAuInfo&reid=<bean:write name='element' property='reid'/>&flg=1">
  								<bean:write name='element' property='objname'/>
  							</a>        
  						&nbsp;</td>
  						<td><bean:write name="element" property="objectnum"/>&nbsp;</td>
  					</tr>
      			</logic:iterate>	      		
	      </logic:present>
      </logic:notEmpty>
      <logic:notEmpty name="chaneList">
	      <logic:present name="chaneList">	 
	      		<logic:iterate id="element" name="chaneList">
  					<tr>
  						<td align="center">修缮</td>
  						<td>
  							<a href="changeapplyaction.do?method=getApplyInfo&type=look&changeid=<bean:write name='element' property='reid'/>&flg=1">
  								<bean:write name='element' property='objname'/>
  							</a>&nbsp;
  						</td>
  						<td ><bean:write name="element" property="objectnum"/>&nbsp;</td>
  					</tr>
      			</logic:iterate>	      		
	      </logic:present>
      </logic:notEmpty>
       </table>  
       <p align="center">
         <input type="button" id="cancel" class="button" value="返回" onclick="cancel()">
       </p>
</body>
</html>
                  