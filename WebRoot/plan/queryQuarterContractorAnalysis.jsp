<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title></title>
		<script type="" language="javascript">
			function check() {
				var regionid = document.all.item("contractorid").value
				if(regionid == '' || regionid == null) {
					alert("请选择查询统计的代维公司");
					return false;
				}
				return true;
			}
    	</script>
	</head>
	<body>
		<template:titile value="按代维公司季度统计查询" />
		<html:form
			action="/Patrolanalysis.do?method=doContractorQuery&type=${businesstype}"
			styleId="queryForm2" onsubmit="return check();">
			<html:hidden property="timeFlgStr" value="quarter"/>
			<template:formTable namewidth="200" contentwidth="200">
				<template:formTr name="代维公司">
					<select name="contractorid" class="inputtext" style="width: 220px"
						id="contractorid">
						<option value=""></option>
						<logic:present name="contractor_list">
							<logic:iterate id="oneContractor" name="contractor_list">
								<option
									value="<bean:write name="oneContractor" property="contractorid"/>">
									<bean:write name="oneContractor" property="contractorname" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
				</template:formTr>				
				<template:formTr name="统计年份">
					<html:text property="startdate" readonly="true"
						styleClass="inputtext Wdate" style="width: 220px" 
						onfocus="WdatePicker({dateFmt:'yyyy',vel:'starttime',realDateFmt:'yyyy'})" />
						<input id="starttime" name="starttime" type="hidden" />
				</template:formTr>
				<template:formTr name="统计季度">
				<select id="quarter" name="quarter" style="width:220">
					<option value="1">第一季度</option>
					<option value="2">第二季度</option>
					<option value="3">第三季度</option>
					<option value="4">第四季度</option>
				</select>
		</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">查找</html:submit>
						<html:reset property="action" styleClass="button">取消</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
