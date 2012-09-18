<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript">
		goBackHome=function(){
			var url="${ctx}/wap/login.do?method=index&&env=wap";
			location=url;
		}
		goBackWaitWork=function(){
			var url="${sessionScope.S_BACK_URL}";
			location=url;
		}
		validate=function(){
			var form1=document.forms[0];
			var approver=form1.approver;
			var reader=form1.reader;
			var flag=false;
			if(typeof(approver.length)!="undefined"){
				for(i=0;i<approver.length;i++){
					if(approver[i].checked||reader[i].checked){
						flag=true;
						break;
					}
				}
			}else{
				if(approver.checked||reader.checked){
					flag=true;
				}
			}
			if(!flag){
				alert("û��ѡ��������Ϣ��");
				return false;
			}
			return true;
		}
		changeCheckBox=function(index,element){
			var form1=document.forms[0];
			var approver=form1.approver;
			var reader=form1.reader;
			var checkState=element.checked;
			if(typeof(approver.length)!="undefined"){
				approver[index].checked=false;
				if(typeof(reader)!="undefined"){
					reader[index].checked=false;
				}
				if(element.name=="approver"){
					approver[index].checked=true;
				}
				if(typeof(reader)!="undefined"){
					if(element.name=="reader"){
						reader[index].checked=checkState;
					}
				}
			}else{
				approver.checked=false;
				if(typeof(reader)!="undefined"){
					reader.checked=false;
				}
				if(element.name=="approver"){
					approver.checked=true;
				}
				if(element.name=="reader"){
					if(typeof(reader)!="undefined"){
						reader.checked=checkState;
					}
				}
			}
		}
		</script>
	</head>
	<body>
		<div>
			<a class="dept1">${LOGIN_USER.userName} ���ã�</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">���ܵ���</a><a class="dept"
				href="javascript:exitSys();">�˳�</a><br /><br />
		</div>
		<logic:equal value="transfer" name="approver_type">
		<template:titile value="��ѡ��ת����" />
		</logic:equal>
		<logic:notEqual value="transfer" name="approver_type">
		<template:titile value="��ѡ������˺ͳ�����" />
		</logic:notEqual>
		<html:form method="post"
			action="/wap/load_approvers.do?method=addWapApprovers" onsubmit="return validate();">
			<input name="action_url" value="${action_url }" type="hidden" />
			<input name="approver_input_name" value="${approver_input_name }" type="hidden" />
			<input name="reader_input_name" value="${reader_input_name }" type="hidden" />
			<input name="display_type" value="${display_type }" type="hidden" />
			<input name="depart_id" value="${depart_id }" type="hidden" />
			<input name="except_self" value="${except_self }" type="hidden" />
			<p>
				<logic:present name="approver_list">
					<logic:iterate id="one_approver" name="approver_list" indexId="index">
						<bean:write name="one_approver" property="username" />
						<logic:notEmpty name="one_approver" property="position">
								--ְλ��
							<logic:notEmpty name="one_approver" property="position">
								<bean:write name="one_approver" property="position" />
							</logic:notEmpty>
						</logic:notEmpty>
						<br/>
						<logic:equal value="1X" name="one_approver" property="is_checked">
							<input type="radio" name="approver" checked onclick="changeCheckBox('<%=index.intValue()%>',this);"
								value="<bean:write name="one_approver" property="userid" />-<bean:write name="one_approver" property="username" />-<bean:write name="one_approver" property="phone" />" />
							���
						</logic:equal>
						<logic:equal value="0X" name="one_approver" property="is_checked">
							<input type="radio" name="approver" onclick="changeCheckBox('<%=index.intValue()%>',this);"
								value="<bean:write name="one_approver" property="userid" />-<bean:write name="one_approver" property="username" />-<bean:write name="one_approver" property="phone" />" />
							���
						</logic:equal>
						<logic:notEqual value="transfer" name="approver_type">
							<logic:equal value="1X" name="one_approver"
								property="is_reader_checked">
								<input type="checkbox" name="reader" checked
									onclick="changeCheckBox('<%=index.intValue()%>',this);"
									value="<bean:write name="one_approver" property="userid" />-<bean:write name="one_approver" property="username" />-<bean:write name="one_approver" property="phone" />" />
							����
						</logic:equal>
							<logic:equal value="0X" name="one_approver"
								property="is_reader_checked">
								<input type="checkbox" name="reader"
									onclick="changeCheckBox('<%=index.intValue()%>',this);"
									value="<bean:write name="one_approver" property="userid" />-<bean:write name="one_approver" property="username" />-<bean:write name="one_approver" property="phone" />" />
							����
						</logic:equal>
						</logic:notEqual>
						<br />
						<br />
					</logic:iterate>
				</logic:present>
			</p>
			<p>
				<input name="btnFinish" type="submit" id="btnFinish" value="��һ��" />
				<input name="btnReset" type="reset" id="btnReset" value="����" />
				<input name="btnToHome" type="button" id="btnToHome" value="����ҳ"
					onclick="goBackHome();" />
				<logic:notEmpty name="S_BACK_URL">
					<input name="btnToWaitWork" type="button" id="btnToHome" value="���ش���"
						onclick="goBackWaitWork();" />
				</logic:notEmpty>
			</p>
		</html:form>
	</body>
</html>
