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
				 	conSel.options.add(new Option("==��ѡ��ͳ������===","0"));
				 	conSel.options.add(new Option("��·����","1"));
				 	conSel.options.add(new Option("�������","2"));
			 	}
			 	if(value == 2) {
			 		var conSel = document.getElementById('statCon');
				 	conSel.options.length = 0;
				 	//conSel.options.add(new Option("==��ѡ��ͳ������===","0"));
				 	conSel.options.add(new Option("��·����","1"));
				 	//conSel.options.add(new Option("�������","2"));
			 	}
			 }
			 
			 resetHtml=function() {
			 	var conSel = document.getElementById('statCon');
			 	conSel.options.length = 0;
			 }
			 
			 checkSubInfo=function(obj) {
			 	if(obj.statType.value == 0) {
			 		alert("��ѡ��ͳ�Ʒ�ʽ!");
			 		return false;
			 	}
			 	if(obj.statCon.value == 0) {
			 		alert("��ѡ��ͳ������!");
			 		return false;
			 	}
			 }
		</script>
	</head>
	
	<body>
		<br />
        <template:titile value="�������Ҹ��ͳ��"/>
        <html:form action="/LineCutReAction?method=doStatQuery" onsubmit="return checkSubInfo(this)">
        	<input type="hidden" value="1" name="rest_query">
        	<template:formTable namewidth="200"  contentwidth="270">
        		<template:formTr name="ͳ�Ʒ�ʽ">
        			<select name="statType" id="statType" class="inputtext" style="width:220px" onchange="statTypeChange(this)">
        				<option value="0">===��ѡ��ͳ�Ʒ�ʽ===</option>
        				<option value="1">����ͳ��</option>
        				<option value="2">ʱ��ͳ��</option>
        			</select>
        		</template:formTr>
        		
        		<template:formTr name="ͳ������" tagID="statConId">
        			<select name="statCon" id="statCon" class="inputtext" style="width:220px">
        			</select>
        		</template:formTr>
        		
        		<%--
        		<template:formTr name="��·����" tagID="linelevelId" style="display:none;">
         			<select name="lineLevel" class="inputtext" style="width:220px" id="lineLevel">
         				<logic:notEmpty name="line_level_list">
         					<option value="0">===ѡ��ȫ����·����===</option>
         					<logic:iterate id="level_list" name="line_level_list">
		  						<option value="<bean:write name="level_list" property="code"/>"><bean:write name="level_list" property="name"/></option>
		  					</logic:iterate>
         				</logic:notEmpty>
         			</select>
         		</template:formTr>
         		
         		<template:formTr name="�������" tagID="cutTypeId" style="display:none;">
         			<select name="cutType" class="inputtext" style="width:220px" id="cutType">
         				<option value="0">===ѡ��ȫ���������===</option>
         				<option value="�½����">�½����</option>
         				<option value="�Ż����">�Ż����</option>
         				<option value="Ǩ���Ը��">Ǩ���Ը��</option>
         				<option value="�޸��Ը��">�޸��Ը��</option>
         			</select>
         		</template:formTr>
         		--%>
         		
         		<template:formTr name="��ʼʱ��">
					<input id="begin" type="text" readonly="readonly" name="begintime"
						class="inputtext" style="width: 220" />
					<INPUT TYPE='BUTTON' VALUE='����' readonly="readonly" ID='btn'
						onclick="GetSelectDateTHIS('begin')"
						STYLE="font: 'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formTr name="����ʱ��">
					<input id="end" type="text" name="endtime" readonly="readonly"
						class="inputtext" style="width: 220" />
					<INPUT TYPE='BUTTON' VALUE='����' ID='btn'
						onclick="GetSelectDateTHIS('end')"
						STYLE="font: 'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">����</html:submit>
					</td>
					<td>
						<html:reset property="action" styleClass="button" onclick="resetHtml()">����</html:reset>
					</td>
				</template:formSubmit>
				
         	</template:formTable>
        </html:form>
	</body>
</html>