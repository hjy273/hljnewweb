<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<jsp:useBean id="selectForTag" class="com.cabletech.commons.tags.SelectForTag"
    scope="request" />

<%
    List list = (List) request.getAttribute("newTerminal");
    request.setAttribute("newTerminalColl", list);
%>
<html>
<head>
<title>�鿴�豸��ѹ</title>
<script type="text/javascript">
    function makeSize(enlargeFlag){
    if(enlargeFlag == 0){

        mainSpan.style.display = "none";
        iframeDiv.style.height = "400";
    }else{
        mainSpan.style.display = "";
        iframeDiv.style.height = "280";
    }
}
    function validate(form){
         var selValue;
         var selNum = 0;
        for(var i=0;i<form.simNumber.options.length;i++)
        {
            //��ѡ��������ʾѡ������Ϊvalue=""
            if(form.simNumber.options[i].selected && form.simNumber.options[i].value!="")
            {
                selValue = ";"+form.simNumber.options[i].value
                selNum++;
            }
        }
        if(selNum == 0){
            alert("��ѡ��Ѳ���豸!");
            return false;
        }else{
            alert("������ԭ�򣬲�ѯ�������2�������ҷ��أ�\nԤ��ѯ�����ѡ������Ĳ鿴�豸��ѹ��");
            return true;
        }
    }
    function valQueryValue(form){
        if(form.starttime.value == "" && form.endtime.value ==""){
            alert("����д��ʼ����ʱ�䣡");
            return false;
        }
    }
var iteName = "";

function getTimeWin(objName){

    iteName = objName;

    showx = event.screenX - event.offsetX - 4 - 210 ;
    showy = event.screenY - event.offsetY + 18;

    var timeWin = window.showModalDialog("${ctx}/common/time.html",self, "dialogWidth:255px; dialogHeight:285px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;help:no");

}

function setSelecteTime(time) {
    document.all.item(iteName).value = time;
}


</script>
<body>
<span id="mainSpan" style="display:"> 
<template:titile value="�鿴�豸��ѹ" />
<table width="90%" height="30%" cellpadding="0" cellspacing="0"
    border="0" align="center">
    <tr>
        <td><html:form method="Post" onsubmit="return validate(this)"
            action="/terminalAction.do?method=queryVoltage" target="hiddenInfoFrame">
            <div class='title2' align="center" style='font-size:12px; font-weight:600;bottom:1'>��ѯ�豸��ѹ</div>
            <template:formTable namewidth="150" contentwidth="150">

                <template:formTr name="�豸������" >
                    <html:select property="simNumber" size="10"
                        styleClass="multySelect" style="width:180" multiple="true">
                        <html:options collection="newTerminalColl" property="value"
                            labelProperty="label" />
                    </html:select>
                </template:formTr>
            </template:formTable>

            <template:formSubmit>
                <td width="85"><html:submit property="action"
                    styleClass="button">���Ͳ�ѯָ��</html:submit></td>
            </template:formSubmit>
        </html:form></td>
        <td align="center" valign="top"><html:form method="Post"
            action="/terminalAction.do?method=queryLowVoltage" onsubmit="return valQueryValue(this)" target="queryVoltage">
            <div class='title2' align="center" style='font-size:12px; font-weight:600;bottom:1'>�鿴�豸��ѹ</div>
            <template:formTable namewidth="150" contentwidth="150">

                <template:formTr name="��ʼʱ��" >
                    <html:text property="starttime" styleClass="inputtext"
                        style="width:100" maxlength="45" readonly="true"/>
                    <input type='button' value="��" id='btn'
                        onclick="getTimeWin('starttime')"
                        style="font:'normal small-caps 6pt serif';">
                </template:formTr>
                <template:formTr name="����ʱ��" >
                    <html:text property="endtime" styleClass="inputtext"
                        style="width:100" maxlength="45" readonly="true"/>
                    <input type='button' value="��" id='btn'
                        onclick="getTimeWin('endtime')"
                        style="font:'normal small-caps 6pt serif';">

                </template:formTr>

            </template:formTable>
            <template:formSubmit>
                <td width="85"><html:submit property="action"
                    styleClass="button">�鿴</html:submit></td>
            </template:formSubmit>
        </html:form></td>
    </tr>
</table>
</span>

<iframe name="hiddenInfoFrame" style="display:none"></iframe>

<div id="iframeDiv" style="height:280">
 <iframe name=queryVoltage border=0 marginWidth=0 marginHeight=0
    src="${ctx}/baseinfo/lowVoltage.jsp" frameBorder=0 width="100%"
    scrolling="yes" height="100%"> </iframe></div>

</body>
</html>
