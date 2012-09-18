<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>�鿴��ά��λ�б��װ�</title>
		<script type="text/javascript">
			function showMore(){
				var contactTable = document.getElementById("contactTable");
				var contactListTable = document.getElementById("contactListTable");
				if(contactTable.style.display=='none'){
					contactTable.style.display="block";
					contactListTable.style.display="none";
				}else{
					contactTable.style.display="none";
					contactListTable.style.display="block";
				}
			}
		</script>
	</head>
	<body>
		<template:titile value="�鿴��ά��λ�б��װ�" />
		<c:if test="${not empty thisYearContract }">
			<table border="1" align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%" id="contactTable">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ά��λ���ƣ�</td>
					<td class="tdulright">
						${contractorName }
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ݣ�</td>
					<td class="tdulright">
						${thisYearContract.year }
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��װ���</td>
					<td class="tdulright">
						${thisYearContract.contractNo }
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" colspan="2"><a style="cursor: pointer;" onclick="showMore()">��ʾ����</a></td>
				</tr>
			</table>
		</c:if>
		<c:if test="${not empty contractList }">
			<table border="1" align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%" id="contactListTable" style="display: none;">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ά��λ���ƣ�</td>
					<td class="tdulright">
						${contractorName }
					</td>
				</tr>
				<c:forEach items="${contractList }" var="contract">
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">��ݣ�</td>
						<td class="tdulright">
							${contract.year }
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">��װ���</td>
						<td class="tdulright">
							${contract.contractNo }
						</td>
					</tr>
				</c:forEach>
				<tr class="trcolor">
					<td class="tdulleft" colspan="2"><a style="cursor: pointer;" onclick="showMore()">����</a></td>
				</tr>
			</table>
		</c:if>
		<c:if test="${empty thisYearContract }">
			<c:if test="${not empty contractList }">
				<table border="1" align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%" id="contactListTable">
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">��ά��λ���ƣ�</td>
						<td class="tdulright">
							${contractorName }
						</td>
					</tr>
					<c:forEach items="${contractList }" var="contract">
						<tr class="trcolor">
							<td class="tdulleft" style="width:20%;">��ݣ�</td>
							<td class="tdulright">
								${contract.year }
							</td>
						</tr>
						<tr class="trcolor">
							<td class="tdulleft" style="width:20%;">��װ���</td>
							<td class="tdulright">
								${contract.contractNo }
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</c:if>
		<c:if test="${empty contractList }">
			${contractorName }��ά��˾���б��װ�
		</c:if>
		<br/>
		<div align="center">
			<input class="button" type="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" />
		</div>
	</body>
</html>
