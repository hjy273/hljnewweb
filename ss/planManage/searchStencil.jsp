<%@ include file="/common/header.jsp"%>

<script language="javascript" type="text/javascript">
function toGetBack(){
        var url = "${ctx}/StencilAction.do?method=queryStencil";
        self.location.replace(url);

}
</script>



<br>
<br>
<template:titile value="�����ƻ�ģ��" />

<html:form action="/StencilAction?method=queryStencil">

	<template:formTable namewidth="150" contentwidth="300">
		
		<logic:equal value="group" name="PMType">
			<template:formTr name="ִ �� ��" isOdd="false">
				<select name="patrolid" Class="inputtext" style="width:225"
					id='eID'>
					<option value="">
						����
					</option>
					<logic:present name="patrolgroup">
						<logic:iterate id="pgId" name="patrolgroup">
							<option value="<bean:write name="pgId" property="patrolid"/>">
								<bean:write name="pgId" property="patrolname" />
							</option>
						</logic:iterate>
					</logic:present>
				</select>
				<!-- hidden id -->
				<html:hidden property="id" />
			</template:formTr>
		</logic:equal>
		<logic:notEqual value="group" name="PMType">
			<template:formTr name="ִ �� ��" isOdd="false">
				<select name="patrolid" Class="inputtext" style="width:225"
					id='eID'>
					<option value="">
						����
					</option>
					<logic:present name="patrolgroup">
						<logic:iterate id="pgId" name="patrolgroup">
							<option value="<bean:write name="pgId" property="patrolid"/>">
								<bean:write name="pgId" property="patrolname" />
							</option>
						</logic:iterate>
					</logic:present>
				</select>
				<!-- hidden id -->
				<html:hidden property="id" />
			</template:formTr>
		</logic:notEqual>
		<template:formTr name="ģ������">
			<html:text property="stencilname" style="width:225" styleClass="inputtext"></html:text>
		</template:formTr>
		<template:formSubmit>
			<td>
				<html:submit property="action" styleClass="button">����</html:submit>
			</td>
			<td>
				<input name="Button2" type="reset" class="button" value="ȡ��">
			</td>
			<td>
				<input type="button" class="button" onclick="toGetBack()" value="����">

			</td>
		</template:formSubmit>

	</template:formTable>
</html:form>
