<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>自定义故障年指标</title>
		<script type="text/javascript">
		</script>
<!-- 验证框架 -->
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	</head>
	<body>
	<template:titile value="添加故障基准指标" />
	<html:form action="/customTroubleQuotaActon.do?method=saveCustomTroubleQuota" styleId="addYearQuota">
		<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class=trcolor>
					<td class="tdulleft" colspan="2" width="20%" >
						统计指标：
					</td>
					<td class="tdulright">
						<select name="guideType" id="guideType" style="width: 175px">
							<c:if test="${customQuotaYear.guideType=='1'}">
							<option value="1" selected>一干故障指标</option>
							<option value="0">城域网故障指标</option>
							</c:if>
							<c:if test="${customQuotaYear.guideType=='0'}">
							<option value="1">一干故障指标</option>
							<option value="0" selected>城域网故障指标</option>
							</c:if>
							<c:if test="${customQuotaYear.guideType!='0' && customQuotaYear.guideType!='1'}">
							<option value="1">一干故障指标</option>
							<option value="0" selected>城域网故障指标</option>
						</c:if>
						</select>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft"  colspan="2"  width="20%">
						年份：
					</td>
					<td class="tdulright">
					<input type="hidden" name="id" value="${customQuotaYear.id}"/>
        			<input type="text" name="year" class="required" value="${customQuotaYear.year }" style="width:175px" onfocus="WdatePicker({dateFmt:'yyyy'})"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft"  colspan="2" width="20%">
						维护公里数：
					</td>
					<td class="tdulright">
        			<input type="text" name="maintenanceLength" value="${customQuotaYear.maintenanceLength }" class="required validate-integer-float-double" style="width:175px"/>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" rowspan="3" width="20%">
						千公里阻断次数
					</td>
					<td class="tdulleft" width="20%">
						基准值：
					</td>
					<td class="tdulright">
        			<input type="text" name="interdictionNormTimes" value="${customQuotaYear.interdictionNormTimes }" class="required validate-integer-float-double" style="width:175px"/>
					</td>
				</tr>
				<tr class="trwhite">
					
					<td class="tdulleft"   width="20%">
						挑战值：
					</td>
					<td class="tdulright">
        			<input type="text" name="interdictionDareTimes" value="${customQuotaYear.interdictionDareTimes }" class="required validate-integer-float-double" style="width:175px"/>
					</td>
				</tr>
				<tr class="trwhite">
					
					<td class="tdulleft"  width="20%">
						完成值：
					</td>
					<td class="tdulright">
        			<input type="text" name="troubleTimes" value="${customQuotaYear.troubleTimes }" class="required validate-integer-float-double" style="width:175px"/>
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" rowspan="3" width="20%">
						千公里阻断时长
					</td>
					<td class="tdulleft" width="20%">
						基准值：
					</td>
					<td class="tdulright">
        			<input type="text" name="interdictionNormTime" value="${customQuotaYear.interdictionNormTime }" class="required validate-integer-float-double" style="width:175px"/>
					</td>
				</tr>
				<tr class="trcolor">
					
					<td class="tdulleft" width="20%">
						挑战值：
					</td>
					<td class="tdulright">
        			<input type="text" name="interdictionDareTime" value="${customQuotaYear.interdictionDareTime }" class="required validate-integer-float-double" style="width:175px"/>
					</td>
				</tr>
				<tr class="trcolor">
					
					<td class="tdulleft" width="20%">
						完成值：
					</td>
					<td class="tdulright">
        			<input type="text" name="interdictionTime" value="${customQuotaYear.interdictionTime }" class="required validate-integer-float-double" style="width:175px"/>
					</td>
				</tr>
				
				<tr class="trwhite">
					<td class="tdulleft" rowspan="3" width="20%">
						光缆抢修平均时长
					</td>
					<td class="tdulleft" width="20%">
						基准值：
					</td>
					<td class="tdulright">
        			<input type="text" name="rtrTimeNormValue" value="${customQuotaYear.rtrTimeNormValue }" class="required validate-integer-float-double" style="width:175px"/>
					</td>
				</tr>
				<tr class="trwhite">
					
					<td class="tdulleft" width="20%">
						挑战值：
					</td>
					<td class="tdulright">
        			<input type="text" name="rtrTimeDareValue" value="${customQuotaYear.rtrTimeDareValue }" class="required validate-integer-float-double" style="width:175px"/>
					</td>
				</tr>
				<tr class="trwhite">
					
					<td class="tdulleft" width="20%">
						完成值：
					</td>
					<td class="tdulright">
        			<input type="text" name="rtrTimeFinishValue" value="${customQuotaYear.rtrTimeFinishValue }" class="required validate-integer-float-double" style="width:175px"/>
					</td>
				</tr>
			</table>
			<div align="center">
				<html:submit property="action" styleClass="button" value="保存"></html:submit> &nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
			</div>
	</html:form>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('addYearQuota', {immediate : true, onFormValidate : formCallback});
		</script>
	</body>
</html>