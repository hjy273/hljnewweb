<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<script language="javascript" type="">
var clicktimes = 0;
function showUnfinishedPlan(){
   clicktimes--;
   document.getElementById("stattype").src="${ctx}/planstat/showSublineUnfinished.jsp" 
}
function showNeverStart(){
   clicktimes--;
   document.getElementById("stattype").src="${ctx}/planstat/showSublineNeverStart.jsp"  
}
function showFinished(){
   clicktimes--;
   document.getElementById("stattype").src="${ctx}/planstat/showSublineFinished.jsp" 
}
function addGoBack()
{  
   history.go(clicktimes);
}
</script>
<body onload="showNeverStart()" style="height: 400px">
	<center>
		<template:titile value="�漰Ѳ���߶���ϸ��Ϣ" />
		<b><template:formTr name="����ѡ��">
		</b>
		<input type="radio" name="comptype" value="1" checked="checked"
			onclick="showNeverStart()" />
		��δ��ʼ��Ѳ���߶�
		<input type="radio" name="comptype" value="2"
			onclick="showUnfinishedPlan()" />
		��δ�ϸ��Ѳ���߶�
		<input type="radio" name="comptype" value="3" onclick="showFinished()" />
		�Ѿ��ϸ��Ѳ���߶�
		</template:formTr>
	</center>
	<div style="height: 85%">
		<iframe id="stattype" marginWidth="0" marginHeight="0" src=""
			frameBorder=0 width="100%" scrolling=auto style="height: 85%">
		</iframe>
	</div>
	<table style="border: 0px; width: 100%;">
		<tr>
			<td style="border: 0px;">
				<center>
					<html:button property="action" styleClass="button"
						onclick="addGoBack()">����</html:button>
				</center>
			</td>
		</tr>
	</table>
</body>
