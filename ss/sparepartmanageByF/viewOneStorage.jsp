<%@include file="/common/header.jsp"%>
<html>
		<style type="text/css">
			.table{					
				text-align: center;
				align:center;
				width:70%;
			}			
		</style>
		
		
		
	<script type="text/javascript">
	checkValid=function(addForm){
		return true;
	}
	addGoBack=function(){
		window.location.href = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageForDraw";
		//self.location.replace(url);
		//history.back();
	}
	addGoBackForrestore=function() {
		window.location.href = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageForAgain";
		//self.location.replace(url);
	}
	addGoBackForQuery=function() {
		window.location.href="${ctx}/SparepartStorageAction.do?method=listSparepartStorage";
		//self.location.replace(url);
	}
	</script>
	<body>
		<br/>
		<template:titile value="查看备件库存信息" />
			<template:formTable namewidth="250" contentwidth="500" th1="项目" th1="内容">
				<template:formTr name="备件名称">
					<bean:write name="baseInfo" property="sparePartName" />
				</template:formTr>
				<template:formTr name="备件型号">
					<bean:write name="baseInfo" property="sparePartModel" />
				</template:formTr>
				<template:formTr name="生产厂商">
					<bean:write name="baseInfo" property="productFactory" />
				</template:formTr>
				<template:formTr name="库存数量">
					<%Object num = request.getSession().getAttribute("number"); %><%=num %>
				</template:formTr>
			<template:formTr  colspan="colspan">
				<display:table name="sessionScope.sparePartStroages" id="currentRowObject" pagesize="16" class="table">	
					<display:setProperty name="paging.banner.placement" value="bottom" /> 
					<display:column property="serial_number" title="备件序列号" headerClass="subject" />
					<display:column property="name"  title="保存位置" headerClass="subject" maxLength="15" />
					<display:column property="username" title="操作人" headerClass="subject" maxLength="8" />
					<display:column property="storage_remark"  title="备注" headerClass="subject" maxLength="30" />
				</display:table>
			</template:formTr>			
				<template:formSubmit>
					<td>
						<input type="button" class="button" value="返回" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
					</td>
				</template:formSubmit>
			</template:formTable>
	</body>
</html>
