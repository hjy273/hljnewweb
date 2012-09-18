<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>ר���ͳ�Ʋ�ѯ</title>
		<script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<link type="text/css" rel="stylesheet" href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		</head>
	<script type="text/javascript">
		function getTableOption(){
			var startDate=$('startDate').value;
			var endDate=$('endDate').value;
			if(endDate==''||startDate==''){
				return false;
			}
			$("tableId").length=1;
			var url="${ctx}/appraiseSpecialAction.do?method=getTableOption&startDate="+startDate+"-01-01&endDate="+endDate+"-12-31";
			var myAjax = new Ajax.Request( url , {
        	    method: 'post',
        	    asynchronous: true,
        	    onComplete: setTableOption });
		}
		
		function setTableOption(response){
			var str=response.responseText;
			if(str==""){
				return true;
			}
			var optionlist=str.split(";");
			$("tableId").options[i]=new Option("","����");
			for(var i=0;i<optionlist.length-1;i++){
				$("tableId").options[i+1]=new Option(optionlist[i].split(":")[1],optionlist[i].split(":")[0]);
			}
		}
		function view(id){
			window.location.href="${ctx}/appraiseSpecialAction.do?method=viewAppraiseMonth&flag=view&resultId="+id;
		}
	</script>
	<body>
		<template:titile value="ר���ͳ�Ʋ�ѯ" />
			<html:form action="/appraiseSpecialAction?method=queryAppraiseStat" styleId="form">
			<template:formTable>
				<template:formTr name="��������">
					<html:text property="startDate" style="Wdate;width:135px" styleClass="inputtext required" styleId="startDate" onfocus="WdatePicker({dateFmt:'yyyy'})" onchange="getTableOption()"></html:text>
					--
					<html:text property="endDate" style="Wdate;width:135px" styleClass="inputtext required" styleId="endDate" onfocus="WdatePicker({dateFmt:'yyyy',maxDate:'%y-%M',minDate:'#F{$dp.$D(\'startDate\')}'})" onchange="getTableOption()"></html:text><font color="red">*</font>
				</template:formTr>
				<template:formTr name="���˱�">
					<html:select property="tableId" styleId="tableId" styleClass="inputtext" style="width:290px">
					<html:option value="">����</html:option>
				</html:select>
				</template:formTr>
				<template:formTr name="���˴�ά">
					<html:select property="contractorId" styleClass="inputtext required" styleId="contractorId" style="width:290px" >
						<html:option value="">����</html:option>
						<html:options collection="cons" property="contractorID" labelProperty="contractorName"></html:options>
					</html:select>
				</template:formTr>
				<template:formTr name="���˷���">
					<html:text property="score" styleClass="validate-number" style="width:135px"></html:text> &nbsp;&nbsp;<html:radio property="scope" styleId="scope" value=">=">���ڵ���</html:radio>&nbsp;&nbsp;<html:radio property="scope" styleId="scope" value="<=">С�ڵ���</html:radio>
				</template:formTr>
				</template:formTable>
				<div align="center"><input type="submit" value="��ѯ" class="button"/></div>
			</html:form>
		<logic:notEmpty name="stat">
		<table style="width:100%;" class="tabout" align="center">
		<tr class="trcolor" align="center">
						<td style="text-align: center; vertical-align: middle;" width="20%">��ά��λ</td>
						<td style="text-align: center; vertical-align: middle;" width="20%">����ʱ�䷶Χ</td>
						<td style="text-align: center; vertical-align: middle;" width="20%">���˽��</td>
						<td style="text-align: center; vertical-align: middle;" width="20%">����ʱ��</td>
						<td style="text-align: center; vertical-align: middle;" width="20%">������</td>
					</tr>
			<c:forEach var="specialAllStatMap" items="${stat}">
				<tr class="trcolorgray">
					<td colspan="5">${specialAllStatMap['tableName']}</td>
				</tr>
				<c:forEach var="specialStatMap" items="${specialAllStatMap['specialStatList']}">
					<tr class="trcolor" >
						<td style="text-align: center; vertical-align: middle;">${specialStatMap['contractorName']}</td>
					<c:forEach var="appraiseResult" items="${specialStatMap['specialList']}">
								<td style="text-align: center; vertical-align: middle;" ><a href="javascript:view('${appraiseResult.id}');">${appraiseResult.startDate }--${appraiseResult.endDate }</a></td>
								<td style="text-align: center; vertical-align: middle;" >${appraiseResult.result }</td>
								<td style="text-align: center; vertical-align: middle;" >${appraiseResult.appraiseDate }</td>
								<td style="text-align: center; vertical-align: middle;" >${appraiseResult.appraiser }</td>
					</c:forEach>
					</tr>
				<!-- 	<tr class="trcolor" align="center">
						<td style="text-align: center; vertical-align: middle;" colspan="5">ƽ���֣�${specialStatMap['avgResult']}&nbsp;&nbsp;&nbsp;&nbsp;�ܷ֣�${specialStatMap['sumResult']}</td>
					</tr>
					 -->
				</c:forEach>
			</c:forEach>
		</table>
		</logic:notEmpty>
	</body>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</html>
