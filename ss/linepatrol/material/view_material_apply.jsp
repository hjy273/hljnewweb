<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<link rel='stylesheet' type='text/css'
			href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript'
			src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script type="text/javascript">
		var win;
		var processWin;
		toViewMaterialPutStorage=function(putStorageId){
			var actionUrl="${ctx}//material_put_storage.do?method=viewMaterialPutStorage";
			var queryString="put_storage_id="+putStorageId+"&&apply_id=${bean.id}";
			//location=actionUrl+"&"+queryString+"&rnd="+Math.random();
			win = new Ext.Window({
				layout : 'fit',
				width:750,height:400, 
				resizable:true,
				closeAction : 'close', 
				modal:true,
				autoScroll:true,
				autoLoad:{url: actionUrl+"&&"+queryString+"&&rnd="+Math.random(),scripts:true}, 
				plain: true,
				title:"�鿴������ⵥ��ϸ��Ϣ" 
			});
			win.show(Ext.getBody());
		}
		closeWin=function(){
			win.close();
		}
		toViewMaterialProcessHistory=function(){
			var actionUrl="${ctx}/process_history.do?method=showProcessHistoryList";
			var queryString="object_type=material&&object_id=${bean.id}";
			//location=actionUrl+"&"+queryString+"&rnd="+Math.random();
			processWin = new Ext.Window({
				layout : 'fit',
				width:750,height:400, 
				resizable:true,
				closeAction : 'close', 
				modal:true,
				autoScroll:true,
				autoLoad:{url: actionUrl+"&&"+queryString+"&&rnd="+Math.random(),scripts:true}, 
				plain: true,
				title:"�鿴�������̴�����Ϣ" 
			});
			processWin.show(Ext.getBody());
		}
		closeProcessWin=function(){
			processWin.close();
		}
		listApproveInfo=function(){
			var url="${ctx}/material_apply.do?method=queryMaterialApplyApproveInfoList";
			var queryString="apply_id=${bean.id}";
			var actionUrl=url+"&&"+queryString+"&&rnd="+Math.random();
			var myAjax=new Ajax.Updater(
				"approveInfoTd",actionUrl,{
					method:"post",
					evalScripts:true
				}
			);
		}
		</script>
	</head>
	<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
		type="text/css">
	<body>
		<template:titile value="�鿴�������뵥" />
		<center>
			<table width="90%" border="0" cellpadding="0" cellspacing="0"
				class="tabout">
				<tr class="trcolor">
					<td colspan="4" class="tdc">
						<b><bean:write name="bean" property="title" /> </b>
					</td>
				</tr>
				<tr class="trcolor">
					<td width="15%" class="tdr">
						<b>�� �� ��:</b>
					</td>
					<td width="35%" class="tdl">
						<bean:write name="bean" property="creatorName" />
					</td>
					<td width="15%" class="tdr">
						<b>����ʱ��:</b>
					</td>
					<td width="35%" class="tdl">
						<bean:write name="bean" property="createDate" />
					</td>
				</tr>
				<tr class="trcolor">
					<td height="25" class="tdr">
						<b>������Դ:</b>
					</td>
					<td colspan="3" class="tdl">
						<bean:define id="state" name="bean" property="type"
							type="java.lang.String" />
						<logic:equal value="1" name="state">
               ��������
             </logic:equal>
						<logic:equal value="0" name="state">
               ���ɲ���
             </logic:equal>
						<logic:equal value="2" name="state">
              �Թ�����
             </logic:equal>
					</td>
				</tr>
				<tr class="trcolor">
					<td height="25" class="tdr">
						<b>��;��Ϣ:</b>
					</td>
					<td colspan="3" class="tdl">
						<bean:write name="bean" property="remark" />
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdr">
						<b>�������:</b>
					</td>
					<td colspan="3" class="tdl">
					</td>
				</tr>
				<tr class=trcolor>
					<td colspan="4" class="tdl" style="padding: 10px;">
						<table width="100%" id="queryID" border="1" cellpadding="0"
							cellspacing="0" style="border-collapse: collapse;">
							<tr>
								<th width="15%" style="text-align:center">
									���Ϲ��
								</th>
								<th width="15%" style="text-align:center">
									��������
								</th>
								<th width="20%" style="text-align:center">
									��������
								</th>
								<th width="10%" style="text-align:center">
									��λ
								</th>
								<th width="20%" style="text-align:center">
									��������
								</th>
								<th width="20%" style="text-align:center">
									��ŵ�ַ
								</th>
							</tr>
							<logic:notEmpty name="bean" property="count">
								<bean:define id="count" name="bean" property="count"
									type="java.lang.String[]" />
								<bean:define id="materialid" name="bean" property="materialName"
									type="java.lang.String[]" />
								<bean:define id="addressid" name="bean" property="addressName"
									type="java.lang.String[]" />
								<bean:define id="modelname" name="bean"
									property="materialModelName" type="java.lang.String[]" />
								<bean:define id="modelunit" name="bean" property="materialUnit"
									type="java.lang.String[]" />
								<bean:define id="typename" name="bean"
									property="materialTypeName" type="java.lang.String[]" />
								<%
									for (int i = 0; i < count.length; i++) {
								%>
								<tr>
									<td style="text-align:center"><%=modelname[i]%></td>
									<td style="text-align:center"><%=typename[i]%></td>
									<td style="text-align:center"><%=materialid[i]%></td>
									<td style="text-align:center"><%=modelunit[i]%></td>
									<td style="text-align:center"><%=count[i]%></td>
									<td style="text-align:center"><%=addressid[i]%></td>
								</tr>
								<%
									}
								%>
							</logic:notEmpty>
						</table>
					</td>
				</tr>
			<logic:notEmpty property="cancelUserId" name="bean">
				<tr class=trcolor>
					<td class="tdr">
						ȡ���ˣ�
					</td>
					<td class="tdl">
						<bean:write property="cancelUserName" name="bean" />
					</td>
					<td class="tdr">
						ȡ��ʱ�䣺
					</td>
					<td class="tdl">
						<bean:write property="cancelTime" name="bean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ԭ��
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="cancelReason" name="bean" />
					</td>
				</tr>
			</logic:notEmpty>
				<tr class=trcolor>
					<td colspan="4" id="approveInfoTd" class="tdl" style="padding: 10px">
					</td>
				</tr>
			</table>
		</center>
		<p align="center">
			<input type="button" class="button" id="viewPutStorageBtn"
				value="�鿴��ⵥ">
			<c:if test="${storage_id=='-1' }">
				<script type="text/javascript">
				viewPutStorageBtn.disabled=true;
				</script>
			</c:if>
			<c:if test="${storage_id!='-1' }">
				<script type="text/javascript">
				viewPutStorageBtn.onclick=function(){
					toViewMaterialPutStorage('${storage_id}');
				}
				</script>
			</c:if>
			<input type="button" class="button"
				onclick="toViewMaterialProcessHistory();"
				value="�鿴������ʷ">
			<!-- <input type="button" class="button"
				onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"
				value="����">
				-->
				<input type="button" class="button" onclick="history.back()" value="����"/>
		</p>
		<script type="text/javascript">
		listApproveInfo();
		</script>
	</body>
</html>
