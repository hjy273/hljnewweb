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
		<template:titile value="�鿴���������Ϣ" />
			<template:formTable namewidth="250" contentwidth="500" th1="��Ŀ" th1="����">
				<template:formTr name="��������">
					<bean:write name="baseInfo" property="sparePartName" />
				</template:formTr>
				<template:formTr name="�����ͺ�">
					<bean:write name="baseInfo" property="sparePartModel" />
				</template:formTr>
				<template:formTr name="��������">
					<bean:write name="baseInfo" property="productFactory" />
				</template:formTr>
				<template:formTr name="�������">
					<%Object num = request.getSession().getAttribute("number"); %><%=num %>
				</template:formTr>
			<template:formTr  colspan="colspan">
				<display:table name="sessionScope.sparePartStroages" id="currentRowObject" pagesize="16" class="table">	
					<display:setProperty name="paging.banner.placement" value="bottom" /> 
					<display:column property="serial_number" title="�������к�" headerClass="subject" />
					<display:column property="name"  title="����λ��" headerClass="subject" maxLength="15" />
					<display:column property="username" title="������" headerClass="subject" maxLength="8" />
					<display:column property="storage_remark"  title="��ע" headerClass="subject" maxLength="30" />
				</display:table>
			</template:formTr>			
				<template:formSubmit>
					<td>
						<input type="button" class="button" value="����" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
					</td>
				</template:formSubmit>
			</template:formTable>
	</body>
</html>
