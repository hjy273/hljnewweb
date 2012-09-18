<%@ page language="java" contentType="text/html; charset=GBK"%><%@ include file="/common/header.jsp"%>

<template:titile value="查询系统字典信息"/>

<html:form  onsubmit="return isValidForm()"  method="Post"
    action="/dictionaryAction.do?method=queryDictionary" >
    <template:formTable>

		<template:formTr name="数值" >
            <html:text property="value"  styleClass="inputtext" style="width:160" maxlength="8"/>
        </template:formTr>

        <template:formTr name="标签">
            <html:text property="lable"  styleClass="inputtext" style="width:160" maxlength="24"/>
        </template:formTr>

		<template:formTr name="类型" >
            <html:text property="type" styleClass="inputtext" style="width:160" maxlength="24"/>
        </template:formTr>

        <template:formTr name="最大长度" >
            <html:text property="maxsize"  styleClass="inputtext" style="width:160" maxlength="1"/>
        </template:formTr>

        <template:formTr name="状态" >
			<html:select property="state" styleClass="inputtext" style="width:160">
				<html:option value="1">有效</html:option>
				<html:option value="2">无效</html:option>
			</html:select>
        </template:formTr>

        <template:formTr name="属主表" >
            <html:text property="tables" styleClass="inputtext" style="width:160" maxlength="24"/>
        </template:formTr>

        <template:formTr name="备注" >
            <html:text property="remark"  styleClass="inputtext" style="width:160" maxlength="6"/>
        </template:formTr>

        <template:formSubmit>
            <td >
                <html:submit styleClass="button" >查询</html:submit>
            </td>
            <td >
                <html:reset styleClass="button"  >取消</html:reset>
            </td>
        </template:formSubmit>

   </template:formTable>
</html:form>
