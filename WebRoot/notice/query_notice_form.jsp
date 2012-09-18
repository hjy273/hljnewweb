<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/header.jsp"%>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<script type="text/javascript">
Ext.onReady(function(){
	//��Ϣ����	type			notice_type			INFORMATION
	var  notice_type = new Appcombox({
		hiddenName : 'type',
		hiddenId : 'notice_type',
		width : 150,
		emptyText : '��ѡ��',
		dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=INFORMATION',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
		allowBlank:true,
		renderTo: 'notice_type_div'
	});
})
</script>
<template:titile value="��ѯ��Ϣ"/>

<html:form  method="Post" action="/NoticeAction.do?method=queryNotice" >
    	<template:formTable >
		<template:formTr name="�ؼ���" isOdd="false">
            <html:text property="contentString"  styleClass="inputtext" style="width:300px" maxlength="20"/>
        </template:formTr>
        <template:formTr name="����" isOdd="true">
        	<div id="notice_type_div"></div>
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
