<%@ page language="java" contentType="text/html; charset=GBK"%><%@ include file="/common/header.jsp"%>

<template:titile value="��ѯϵͳ�ֵ���Ϣ"/>

<html:form  onsubmit="return isValidForm()"  method="Post"
    action="/dictionaryAction.do?method=queryDictionary" >
    <template:formTable>

		<template:formTr name="��ֵ" >
            <html:text property="value"  styleClass="inputtext" style="width:160" maxlength="8"/>
        </template:formTr>

        <template:formTr name="��ǩ">
            <html:text property="lable"  styleClass="inputtext" style="width:160" maxlength="24"/>
        </template:formTr>

		<template:formTr name="����" >
            <html:text property="type" styleClass="inputtext" style="width:160" maxlength="24"/>
        </template:formTr>

        <template:formTr name="��󳤶�" >
            <html:text property="maxsize"  styleClass="inputtext" style="width:160" maxlength="1"/>
        </template:formTr>

        <template:formTr name="״̬" >
			<html:select property="state" styleClass="inputtext" style="width:160">
				<html:option value="1">��Ч</html:option>
				<html:option value="2">��Ч</html:option>
			</html:select>
        </template:formTr>

        <template:formTr name="������" >
            <html:text property="tables" styleClass="inputtext" style="width:160" maxlength="24"/>
        </template:formTr>

        <template:formTr name="��ע" >
            <html:text property="remark"  styleClass="inputtext" style="width:160" maxlength="6"/>
        </template:formTr>

        <template:formSubmit>
            <td >
                <html:submit styleClass="button" >��ѯ</html:submit>
            </td>
            <td >
                <html:reset styleClass="button"  >ȡ��</html:reset>
            </td>
        </template:formSubmit>

   </template:formTable>
</html:form>
