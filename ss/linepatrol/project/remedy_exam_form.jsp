<%@include file="/common/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>查看修缮申请</title>
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
			type="text/css">

		<script type='text/javascript'
			src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<link rel='stylesheet' type='text/css'
			href='${ctx}/js/extjs/resources/css/ext-all.css' />

		<script type="text/javascript">
			var win;
			function showWin(url){
				win = new Ext.Window({
					layout : 'fit',
					width:600,
					height:400,
					resizable:true,
					closeAction : 'close',
					modal:true,
					autoScroll:true,
					autoLoad:{url:url, scripts:true},
					plain: true
				});
				win.show(Ext.getBody());
			}
			function closeProcessWin(){
				win.close();
			}
			addGoBackMod=function() {
				var url = "<%=session.getAttribute("S_BACK_URL")%>";
		self.location.replace(url);
		//history.back();
	}
	function his(id) {
		var url = "${ctx}/process_history.do?method=showProcessHistoryList&object_type=project&is_close=0&object_id="
				+ id;
		showWin(url);
	}
	function checkAddInfo(){
		if(!checkAppraiseDailyValid()){
			return false;
		}
		document.getElementById("form").submit();
	}
	
	function showOrHiddeInfo(){
			var infoDiv = document.getElementById("applyinfo");
			if(infoDiv.style.display=="block"){
				infoDiv.style.display="none";
			}else{
				infoDiv.style.display="block";
			}
		}
</script>
	</head>
	<body>
		<br>
		<%
			Map applyMap = (Map) request.getAttribute("apply_map");
			pageContext.setAttribute("one_apply", applyMap.get("one_apply"));
			//pageContext.setAttribute("approve_list", applyMap.get("approve_list"));
			pageContext.setAttribute("apply_item_list", applyMap
					.get("apply_item_list"));
		%>
		<template:titile value="查看工程申请" />
		<html:form action="/remedyExamAction.do?method=examRemedy" styleId="form">
		<div id="applyinfo" style="display: none;">
		<table width="98%" align="center" border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
			<tr class=trcolor>
				<td class="tdr" width="15%">
					编号：
				</td>
				<td class="tdl" width="35%">
					<bean:write property="remedyCode" name="one_apply" />
				</td>
				<td class="tdr" width="15%">
					维护单位：
				</td>
				<td class="tdl" width="35%">
					<apptag:assorciateAttr table="contractorinfo"
						label="contractorname" key="contractorid"
						keyValue="${one_apply.contractorId}" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					项目名称：
				</td>
				<td class="tdl">
					<bean:write property="projectName" name="one_apply" />
				</td>
				<td class="tdr">
					发生地点：
				</td>
				<td class="tdl">
					<bean:write property="remedyAddress" name="one_apply" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					申请时间：
				</td>
				<td class="tdl" colspan=3>
					<bean:write property="remedyDate" name="one_apply" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					原因说明：
				</td>
				<td class="tdl">
					<bean:write property="remedyReason" name="one_apply" />
				</td>
				<td class="tdr">
					处理方案：
				</td>
				<td class="tdl">
					<bean:write property="remedySolve" name="one_apply" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					工作量汇总：
				</td>
				<td colspan="3" class="tdl" style="padding:10px;">
					<table id="itemTable" width="100%" border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
						<tr class=trcolor>
							<td style="text-align: center;">
								项目名称
							</td>
						</tr>
						<!-- 
						<tr class=trcolor>
							<td style="text-align: center; width: 150;">
								项目
							</td>
							<td style="text-align: center; width: 150;">
								类别
							</td>
						</tr>
						 -->
						<logic:notEmpty name="apply_item_list">
							<logic:iterate id="oneApplyItem" name="apply_item_list">
								<tr class=trcolor>
									<td style="text-align: center; width: 150;">
										<bean:write name="oneApplyItem" property="itemname" />
									</td>
									<!-- 
									<td style="text-align: center; width: 150;">
										<bean:write name="oneApplyItem" property="typename" />
									</td>
									 -->
								</tr>
							</logic:iterate>
						</logic:notEmpty>
					</table>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					工程费用合计（元）：
				</td>
				<td colspan="3" class="tdl">
					<bean:write property="totalFee" name="one_apply" />
					元
				</td>
			</tr>
			<tr class=trcolor>
				<td colspan=4 style="padding:10px;" class="tdc">
					<apptag:materialselect label="使用材料" materialUseType="Use"
						objectId="${one_apply.id}" useType="project_remedy"
						displayType="view" />
				</td>
			</tr>
			<tr class=trcolor>
				<td colspan=4 style="padding:10px;" class="tdc">
					<apptag:materialselect label="回收材料" displayType="view"
						materialUseType="Recycle" objectId="${one_apply.id}"
						useType="project_remedy" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					材料费用合计（元）：
				</td>
				<td colspan="3" class="tdl">
					<bean:write property="mtotalFee" name="one_apply" />
					元
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					附件：
				</td>
				<td colspan="3" class="tdl">
					<apptag:upload state="look" cssClass="" entityId="${one_apply.id}"
						entityType="LP_REMEDY" />
				</td>
			</tr>
			<logic:notEmpty property="cancelUserId" name="one_apply">
				<tr class=trcolor>
					<td class="tdr">
						取消人：
					</td>
					<td class="tdl">
						<bean:write property="cancelUserName" name="one_apply" />
					</td>
					<td class="tdr">
						取消时间：
					</td>
					<td class="tdl">
						<bean:write property="cancelTime" name="one_apply" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						取消原因：
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="cancelReason" name="one_apply" />
					</td>
				</tr>
			</logic:notEmpty>
			<tr class=trcolor>
					<apptag:approve labelClass="tdr" displayType="view" colSpan="4"
						objectId="${one_apply.id}" objectType="LP_REMEDY_APPROVE" />
			</tr>
		</table>
		</div>
		<div align="right" style="height: 30px; line-height: 30px; width:98%; margin:0px auto;"><a onclick="showOrHiddeInfo();" style="cursor: pointer;">显示/隐藏</a></div>
		
		</html:form>
		<apptag:appraiseDailyDaily businessId="${one_apply.id}" contractorId="${one_apply.contractorId}" businessModule="project" tableStyle="width:98%;border-top:0px;"></apptag:appraiseDailyDaily>
		<apptag:appraiseDailySpecial businessId="${one_apply.id}" contractorId="${one_apply.contractorId}" businessModule="project" tableStyle="width:98%;border-top:0px;"></apptag:appraiseDailySpecial>
				<table align="center" border="0">
			<tr>
				<td>
					<html:button property="action" styleClass="button"
							onclick="checkAddInfo()">提交</html:button>
					<input type="button" value="返回" class="button"
						onclick="addGoBackMod();">
				</td>
			</tr>
		</table>
	</body>
</html>
