<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.linecut.beans.LineCutBean"%>

<html>
	<head>
		<title></title>
		<script type="text/javascript">
			 function GetSelectDateTHIS(strID) {
		    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
		    	return ;
			 }
			 
			 statTypeChange=function(obj) {
			 	var value = parseInt(obj.value);
			 	if(value == 0) {
			 		var conSel = document.getElementById('statCon');
			 		conSel.options.length = 0;
			 	}
			 	if(value == 1) {
			 		var conSel = document.getElementById('statCon');
				 	conSel.options.length = 0;
				 	conSel.options.add(new Option("==请选择统计条件===","0"));
				 	conSel.options.add(new Option("线路级别","1"));
				 	conSel.options.add(new Option("割接类型","2"));
			 	}
			 	if(value == 2) {
			 		var conSel = document.getElementById('statCon');
				 	conSel.options.length = 0;
				 	//conSel.options.add(new Option("==请选择统计条件===","0"));
				 	conSel.options.add(new Option("线路级别","1"));
				 	//conSel.options.add(new Option("割接类型","2"));
			 	}
			 }
			 
			 resetHtml=function() {
			 	var conSel = document.getElementById('statCon');
			 	conSel.options.length = 0;
			 }
			 
			 checkSubInfo=function(obj) {
			 	if(obj.statType.value == 0) {
			 		alert("请选择统计方式!");
			 		return false;
			 	}
			 	if(obj.statCon.value == 0) {
			 		alert("请选择统计条件!");
			 		return false;
			 	}
			 }
		</script>
	</head>
	
	<body>
		<br />
        <template:titile value="条件查找割接统计"/>
        <html:form action="/LineCutReAction?method=doStatQuery" onsubmit="return checkSubInfo(this)">
        	<input type="hidden" value="1" name="rest_query">
        	<template:formTable namewidth="200"  contentwidth="270">
        		<template:formTr name="统计方式">
        			<select name="statType" id="statType" class="inputtext" style="width:220px" onchange="statTypeChange(this)">
        				<option value="0">===请选择统计方式===</option>
        				<option value="1">数量统计</option>
        				<option value="2">时间统计</option>
        			</select>
        		</template:formTr>
        		
        		<template:formTr name="统计条件" tagID="statConId">
        			<select name="statCon" id="statCon" class="inputtext" style="width:220px">
        			</select>
        		</template:formTr>
        		
        		<%--
        		<template:formTr name="线路级别" tagID="linelevelId" style="display:none;">
         			<select name="lineLevel" class="inputtext" style="width:220px" id="lineLevel">
         				<logic:notEmpty name="line_level_list">
         					<option value="0">===选择全部线路级别===</option>
         					<logic:iterate id="level_list" name="line_level_list">
		  						<option value="<bean:write name="level_list" property="code"/>"><bean:write name="level_list" property="name"/></option>
		  					</logic:iterate>
         				</logic:notEmpty>
         			</select>
         		</template:formTr>
         		
         		<template:formTr name="割接类型" tagID="cutTypeId" style="display:none;">
         			<select name="cutType" class="inputtext" style="width:220px" id="cutType">
         				<option value="0">===选择全部割接类型===</option>
         				<option value="新建割接">新建割接</option>
         				<option value="优化割接">优化割接</option>
         				<option value="迁改性割接">迁改性割接</option>
         				<option value="修复性割接">修复性割接</option>
         			</select>
         		</template:formTr>
         		--%>
         		
         		<template:formTr name="开始时间">
					<input id="begin" type="text" readonly="readonly" name="begintime"
						class="inputtext" style="width: 220" />
					<INPUT TYPE='BUTTON' VALUE='日期' readonly="readonly" ID='btn'
						onclick="GetSelectDateTHIS('begin')"
						STYLE="font: 'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formTr name="结束时间">
					<input id="end" type="text" name="endtime" readonly="readonly"
						class="inputtext" style="width: 220" />
					<INPUT TYPE='BUTTON' VALUE='日期' ID='btn'
						onclick="GetSelectDateTHIS('end')"
						STYLE="font: 'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">查找</html:submit>
					</td>
					<td>
						<html:reset property="action" styleClass="button" onclick="resetHtml()">重置</html:reset>
					</td>
				</template:formSubmit>
				
         	</template:formTable>
        </html:form>
	</body>
</html>