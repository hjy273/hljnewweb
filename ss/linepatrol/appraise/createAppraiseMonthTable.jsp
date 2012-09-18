<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<link rel='stylesheet' type='text/css'
			href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript'
			src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<html>
	<head>
		<title>���˱�</title>
		<script type="text/javascript">
	viewExamBase=function(ruleId){
		
		var actionUrl="${ctx}/appraiseMonthAction.do?method=viewExamBase&ruleId="+ruleId;
		viewExamBaseWin = new Ext.Window({
			layout : 'fit',
			width:650,height:300, 
			resizable:true,
			closeAction : 'close', 
			modal:false,
			autoScroll:true,
			autoLoad:{url: actionUrl,scripts:true}, 
			plain: false,
			title:"��������" 
		});
		viewExamBaseWin.show(Ext.getBody());
	};
	closeviewExamBaseWin=function(){
		viewExamBaseWin.close();
	};
	function view(module,id){
		if(module=='trouble'){
			window.open("${ctx}/troubleQueryStatAction.do?method=viewTrouble&troubleid="+id, "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
		}
		if(module=='acceptance'){
			window.open("${ctx}/acceptanceQueryAction.do?method=view&id="+id,"toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
		}
		if(module=='hiddanger'){
			window.open("${ctx}/hiddangerQueryAction.do?method=view&id=" + id,"toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
		}
		if(module=='maintenance'){
			window.open("${ctx}/testPlanAction.do?method=viewPaln&planId="+id,"toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
		}
		if(module=='cut'){
			window.open("${ctx}/cutQueryStatAction.do?method=viewQueryCut&cutId="+id,"toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
		}
		if(module=='dispatchtask'){
			window.open("${ctx}/dispatchtask/dispatch_task.do?method=viewDispatchTask&dispatch_id="+id+"&rnd="+Math.random(),"toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
		}
		if(module=='project'){
			window.open("${ctx}/project/remedy_apply.do?method=view&&apply_id="+ id,"toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
		}
		if(module=='overhaul'){
			window.open("${ctx}/overHaulAction.do?method=view&id="+id,"toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
		}
	}
	//��֤�÷ֲ��ܳ������ֵġ�
	onchangeValue = function(Weight,result_id){
		var result = eval(result_id.value);
		if(result>Weight){
			result_id.value=Weight;
		}
	};
	
</script>
	</head>

	<body>
		<template:titile value="${titleMap['title']}" />
		<h3>�ο�ָ�꣺</h3>
		<table width="100%">
			<tr align="center">
				<td>
					���˵�λ��${titleMap['contractorName']}
				</td>
				<td>
					ͳ���·ݣ�${titleMap['statDate']}
				</td>
			</tr>
		</table>
		
	
		<table border="1" align="center" style="width:90%;" class="tabout">
			<tr align="center" class="trcolor">
				<td class="tdulright">
					Ѳ����
				</td>
				<td class="tdulright">
					Ѳ����
				</td>
				<td class="tdulright">
					©����
				</td>
				<td class="tdulright">
					�ƻ���
				</td>
			</tr>
			<c:forEach var="monthlyStat" items="${monthlyStats}">
			<tr align="center" class="trcolor">
				<td class="tdulright">
					${monthlyStat['executorid']}
				</td>
				<td class="tdulright">
					${monthlyStat['patrolp']}%
				</td>
				<td class="tdulright">
					${monthlyStat['factp']}%
				</td>
				<td class="tdulright">
					${monthlyStat['planNumber']}
				</td>
			</tr>
			</c:forEach>
			<c:forEach var="monthStatAll" items="${monthStatAll}">
				<tr align="center" class="trcolor">
				<td class="tdulright">
					�ϼ�
				</td>
				<td class="tdulright">
					${monthStatAll['patrolP']}%
				</td>
				<td class="tdulright">
					${monthStatAll['lossP']}%
				</td>
				<td class="tdulright">
					${monthStatAll['planNum']}
				</td>
			</tr>
			</c:forEach>
		</table>
		 	
		<br />
		<table width="100%">
			<tr align="center">
				<td>
					���˵�λ��${titleMap['contractorName']}
				</td>
				<td>
					�������ڣ�${titleMap['appraiseMonth']}
				</td>
				<td>
					�����ˣ�${LOGIN_USER.userName}
				</td>
			</tr>
		</table>
		<html:form action="/appraiseMonthAction.do?method=getRules" method="post">
		${html}
		<br/>
		<div align="center">
		<html:submit styleClass="button">���</html:submit>
		</div>
		</html:form>
	</body>
</html>
