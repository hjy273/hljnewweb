<%@page import="com.cabletech.watchinfo.beans.*"%>
<%@ include file="/common/header.jsp"%>

<script language="javascript" type="">
<!--


//-->
</script>

<template:titile value="��д�˲���Ϣ�о��������Ϣ����"/>

<html:form  method="Post" action="/subWatchAction.do?method=updateSubWatch" >
    <template:formTable>

        <template:formTr name="��Ӱ���������"   isOdd="false" >
            <html:text property="segmentname" styleClass="inputtext" style="width:160" readonly="true"/>
			<html:hidden property="kid"/>
			<html:hidden property="watchid"/>
			<html:hidden property="segment_kid"/>
			<html:hidden property="fiber_kid"/>
			
        </template:formTr>

		<template:formTr name="о��" >
            <html:text property="corenum" styleClass="inputtext" style="width:160" readonly="true"/>
        </template:formTr>

		<template:formTr name="ҵ������"   isOdd="false" >
            <html:text property="netlayer" styleClass="inputtext" style="width:160" />
        </template:formTr>

		<template:formTr name="���跽ʽ" >
            <html:text property="laytype" styleClass="inputtext" style="width:160" readonly="true"/>
        </template:formTr>

		<template:formTr name="����ҵ��Ӱ����"   isOdd="false" >
            <html:text property="fibername" styleClass="inputtext" style="width:160" maxlength="200"/>
        </template:formTr>

		<template:formTr name="��ط�ʽ" >
            <html:text property="monitortype" styleClass="inputtext" style="width:160" maxlength="45"/>
        </template:formTr>

		<template:formTr name="�����Ƿ���Ҫ���"   isOdd="false" >
		  <html:select property="ifneedcut" styleClass="inputtext" style="width:160">
			<html:option value="��">��</html:option>
			<html:option value="��">��</html:option>
		  </html:select>
        </template:formTr>

        <template:formSubmit>
            <td >
                <html:submit styleClass="button" >����</html:submit>
            </td>
            <td >
                <html:button property="action"styleClass="button"  onclick="self.close()">ȡ��</html:button>
            </td>
        </template:formSubmit>

   </template:formTable>
</html:form>
<iframe id="hiddenFrame" style="display:none">
</iframe>
<script language="javascript">
<!--
preSetEndDate();
//-->
</script>