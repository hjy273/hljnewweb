<%@ page language="java" contentType="text/html; charset=GBK"%><%@ include file="/common/header.jsp"%>
<template:titile value="��ѯ������Ϣ"/>

<html:form  method="Post" action="/NoticeAction.do?method=queryNotice" >
    <template:formTable>
		<template:formTr name="�ؼ���" >
            <html:text property="content"  styleClass="inputtext" style="width:300" maxlength="20"/>
        </template:formTr>
        <template:formTr name="����" >
        	<html:select property="type" styleClass="inputtext">
        		<html:option value="">����</html:option>
        		<html:option value="ϵͳ����">ϵͳ����</html:option>
        		<html:option value="����">����</html:option>
        	 	<html:option value="֪ͨ">֪ͨ</html:option>
        	 	<html:option value="����">����</html:option>
        	</html:select>        
        </template:formTr>
		
		<template:formTr name="��Ҫ�̶�" >
        	<html:select property="grade" styleClass="inputtext">
        		<html:option value="">����</html:option>
        		<html:option value="��Ҫ">��Ҫ</html:option>
        		<html:option value="һ��">һ��</html:option>
        	</html:select>        
        </template:formTr>
        
		<template:formTr name="�Ƿ񷢲�">
			<html:select property="isissue" styleClass="inputtext">
				<html:option value="">����</html:option>
				<html:option value="y">�ѷ���</html:option>
				<html:option value="n">δ����</html:option>
			</html:select>
		</template:formTr>
		
		<template:formTr name="��������">
			<html:text property="begindate" styleClass="inputtext"/><apptag:date property="begindate"></apptag:date>--<html:text property="enddate" styleClass="inputtext"/><apptag:date property="enddate"></apptag:date>
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
