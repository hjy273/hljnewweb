<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<html>
	<head>
		<title>�¿��˲�ѯͳ��</title>
		<style type="text/css">
			body { margin:20px; font-size:12px;
			}
			
			.list{  width:100%; background-color:#F7FAFF; border-right:1px #CCCBC9 solid; border-bottom:1px #CCCBC9 solid; }
			.list td{ font-size:12px; border:1px #CCCBC9 solid; border-style:solid; border-collapse:collapse; height:24px; border-collapse:collapse; padding-left:20px; border-bottom:0px;  border-right:0px; width:33.3%}
			.div1{ border:1px #CCCBC9 solid; border-bottom:0px;  border-right:0px;  height:24px; line-height:24px; padding-left:20px; font-weight:bold; }
			.div3{ border:1px #CCCBC9 solid; border-bottom:0px;  border-right:0px;  height:24px; line-height:24px; padding-right:20px; text-align:center; }
		</style>
	</head>
	<body>
	<script type="text/javascript">
		function view(id){
			window.location.href="${ctx}/appraiseMonthAction.do?method=viewAppraiseMonth&flag=view&resultId="+id;
		}
	</script>
		<template:titile value="�¿��˲�ѯͳ��" />
		<html:form
			action="/appraiseMonthStatAction.do?method=appraiseMonthStat"
			styleId="submitForm1">
			<table border="1" align="center" cellspacing="0" cellpadding="1"
				class="tabout" width="80%">
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						ʱ��Σ�
					</td>
					<td class="tdulright">
						<html:text property="startDate" style="Wdate;width:135px"
							styleClass="inputtext required" styleId="appraiseTime"
							onfocus="WdatePicker({dateFmt:'yyyy-MM'})"></html:text>
						-
						<html:text property="endDate" style="Wdate;width:135px"
							styleClass="inputtext required" styleId="appraiseTime"
							onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'startDate\')}'})"></html:text><font color="red">*</font>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						���˷�����
					</td>
					<td class="tdulright">
						<html:text property="score" styleClass="inputtext" styleId="score"></html:text>
						<html:radio property="scoreType" value="&gt;"></html:radio>
						����
						<html:radio property="scoreType" value="&lt;"></html:radio>
						����
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						��ά��˾��
					</td>
					<td class="tdulright">
						<html:select property="contractorId"
							styleClass="inputtext" styleId="contractorId"
							style="width:140px">
							<html:option value="">ȫ��</html:option>
							<html:options collection="cons" property="contractorID"
								labelProperty="contractorName"></html:options>
						</html:select>
					</td>
				</tr>
			</table>
			<div align="center">
				<html:submit property="action" styleClass="button">��ѯ</html:submit>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<html:reset property="action" styleClass="button">��д</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="submitForm1"></template:displayHide>
		<logic:notEmpty name="monthStatList">
			<div class="list">
				<c:forEach items="${monthStatList}" var="monthStat">
					<div class="div1">
						<b>${monthStat.contractorName }</b>
					</div>
					<div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="40%" align="center">
									�����·�
								</td>
								<td width="30%" align="center">
									��װ�
								</td>
								<td width="30%" align="center">
									���˽��
								</td>
							</tr>
						</table>
					</div>
					<div>
						<c:forEach items="${monthStat.resultList}" var="monthStatDetail">
							<div>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<c:forEach items="${monthStatDetail.contentList}" var="content">
										<tr>
											<td width="40%">
												<a href="javascript:view('${content.resultId}');">${content.appraiseDate }&nbsp;</a>
											</td>
											<td width="30%">
												${content.contractNo }&nbsp;
											</td>
											<td width="30%">
												${content.score }&nbsp;
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<div class="div3">
								ƽ����${monthStatDetail.averageScore }
								�ܷ֣�${monthStatDetail.totalScore }
							</div>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
		</logic:notEmpty>
	</body>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
				if(result){
					processBar(form);
				}
			}
	
			var valid = new Validation('submitForm1', {immediate : true, onFormValidate : formCallback});
		</script>
</html>
