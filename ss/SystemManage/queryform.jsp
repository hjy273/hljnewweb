<%@ include file="/common/header.jsp"%>
<template:titile value="��ѯ��Ϣ"/>

<html:form  method="Post" action="/NoticeAction.do?method=queryNotice" >
    	<template:formTable >
		<template:formTr name="�ؼ���" isOdd="false">
            <html:text property="contentString"  styleClass="inputtext" style="width:300px" maxlength="20"/>
        </template:formTr>
        <template:formTr name="����" isOdd="true">
        	<html:select property="type" styleClass="inputtext"  style="width:150px" >
        		<html:option value="">����</html:option>
        		
        		<html:option value="����">����</html:option>
        	 	<html:option value="����">����</html:option>
        	 	<html:option value="����">����</html:option>
        	</html:select>        
        </template:formTr>
		
		<template:formTr name="��Ҫ�̶�" isOdd="false">
        	<html:select property="grade" styleClass="inputtext"  style="width:150px" >
        		<html:option value="">����</html:option>
        		<html:option value="��Ҫ">��Ҫ</html:option>
        		<html:option value="һ��">һ��</html:option>
        	</html:select>        
        </template:formTr>
        
		<template:formTr name="�Ƿ񷢲�" isOdd="true">
			<html:select property="isissue" styleClass="inputtext"  style="width:150px" >
				<html:option value="">����</html:option>
				<html:option value="y">�ѷ���</html:option>
				<html:option value="n">δ����</html:option>
			</html:select>
		</template:formTr>
		
		<template:formTr name="��������" isOdd="false">
			<html:text property="begindate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})"/>--<html:text property="enddate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})"/>
		</template:formTr>

		<template:formSubmit>
            <td>
            	<html:submit styleClass="button" >��ѯ</html:submit>
            </td>
            <td>
                <html:reset styleClass="button"  >ȡ��</html:reset>
            </td>
        </template:formSubmit>


   </template:formTable>
</html:form>
