<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.beans.*" %>
<script type="text/javascript">
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
  //检验是否数字
    function valiD(id){
        var obj = document.getElementById(id);
        var mysplit = /^\d{1,3}$/;
        if(!mysplit.test(obj.value)){
            alert("必须是数字,请重新输入!");
            obj.value="";
            obj.focus();
        }
    }

    function sendTrouble(){
        var url = "${ctx}/TroubleCodeAction.do?method=sendTroubleCode";
        self.location.replace(url);
    }
    function submitType(form){
        if(form.code.value.length <= 0 || form.typename.value.length <=0||form.typename.value.trim()<=0){
            alert("隐患类型代码、隐患类型名称不能为空或者空格！");
            if(form.code.value.trim().length==0) form.code.value="";
            if(form.typename.value.trim().length==0) form.typename.value="";
            return false;
        }
        form.typename.value=form.typename.value.trim();
        return true;
    }
    function submitcode(form){
        if(form.troublecode.value.length <= 0 || form.troublename.value.length <=0 || form.troublename.value.trim().length<=0||form.troubletype.value.length <=0 ){
            alert("隐患代码、隐患名称以及隐患类型不能为空或者空格！");
            if(form.troublecode.value.trim().length==0)form.troublecode.value="";
            if(form.troublename.value.trim().length==0)form.troublename.value="";
            return false;
        }
        form.troublename.value=form.troublename.value.trim();
        return true;
    }

    function showTypeMgr(id,code,name){
        if(id=="new"){
            addtype.style.display ="";
            edittype.style.display ="none";
            addcode.style.display ="none";
            editcode.style.display = "none";
        }else{
            addtype.style.display ="none";
            edittype.style.display ="";
            addcode.style.display ="none";
            editcode.style.display = "none";
            edittype.id.value=id;
            edittype.code.value=code;
            edittype.typename.value=name;
        }
    }
    function codeMgr(id,code,name,type,remark){
        if(id=="new"){
            addcode.style.display ="";
            addtype.style.display ="none";
            edittype.style.display ="none";
            editcode.style.display = "none";
        }else{
            addcode.style.display ="none";
            editcode.style.display = "";
            addtype.style.display ="none";
            edittype.style.display ="none";

            editcode.id.value=id;
            editcode.troublecode.value=code;
            editcode.troublename.value=name;
            editcode.troubletype.value=type;
            editcode.remark.value=remark;
        }
    }
    function validateType(form){
        alert(form.id.value);
    }
    function onDelCode(the){
        var url = "${ctx}/TroubleCodeAction.do?method=delTroubleCode&id="+editcode.id.value;
        self.location.replace(url);
    }
    function onDelType(the){
        var url = "${ctx}/TroubleCodeAction.do?method=delTroubleType&id="+edittype.id.value;
        self.location.replace(url);
    }
</script>
<%
    List ttl = (List)request.getAttribute("TroubleType");
 %>
<body>
<br>
<template:titile value="巡检隐患码管理" />
<table height="90%" cellspacing="0" border="0" cellpadding="0">
<tr>
<td width="200" valign="top">
    &nbsp;&nbsp;&nbsp;&nbsp;<html:button property="button" styleClass="button"  onclick="sendTrouble()">下传隐患码	</html:button><br>
    &nbsp;&nbsp;&nbsp;&nbsp;<html:button property="cancel"styleClass="button" onclick="ojavascript:showTypeMgr('new','','');">新建类型</html:button><br>
    &nbsp;&nbsp;&nbsp;&nbsp;<html:button property="cancel"styleClass="button" onclick="javascript:codeMgr('new','','');">新建隐患码</html:button><br><hr><br>

    <%
        for(int i = 0; i<ttl.size();i++){
            TroubleTypeBean type = (TroubleTypeBean)ttl.get(i);
    %>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:showTypeMgr('<%=type.getId()%>','<%=type.getCode() %>','<%=type.getTypename() %>');"><b style="font-size:14px; font-weight:600;"><%=type.getTypename() %></b></a><br>
    <%
            for(int j=0;j<type.getTroublecode().size();j++){
                TroubleCodeBean code = (TroubleCodeBean)type.getTroublecode().get(j);
    %>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:codeMgr('<%=code.getId() %>','<%=code.getTroublecode() %>','<%=code.getTroublename() %>','<%=code.getTroubletype() %>','<%=code.getRemark()==null?"":code.getRemark() %>')"><b style="font-size:14px; font-weight:600;"><%=code.getTroublename() %></b></a><br>
    <%

            }
        }
     %>

</td>
<td width="600" height="100%" valign="top" style="border-left-color:#999999; border-left:thin; border-left-style:ridge;height:auto">
<br>
<br>
<html:form styleId="editcode" style="display:none"
    action="/TroubleAction.do?method=updateTroubleCode" onsubmit='return submitcode(this)'>
    <html:hidden property="id"/>
    <template:formTable namewidth="150" contentwidth="200">
        <template:formTr name="隐患码">
            <html:text property="troublecode" styleClass="inputtext"  style="width:170" styleId="t1" onchange="valiD('t1')" maxlength="3"></html:text>
        </template:formTr>
        <template:formTr name="隐患码名称">
            <html:text property="troublename" styleClass="inputtext" style="width:170" styleId="t2" maxlength="10" onchange="valiD('t2')"></html:text>
        </template:formTr>
        <template:formTr name="隐患码类型">
            <apptag:setSelectOptions tableName="troubletype" valueName="typecoll" columnName1="typename" columnName2="id" region="true"/>
            <html:select styleId="selId" property="troubletype" styleClass="multySelect" style="width:170">
                <html:options collection="typecoll" property="value" labelProperty="label"/>
            </html:select>
        </template:formTr>
        <template:formTr name="备注">
            <html:text property="remark" styleClass="inputtext" style="width:170" maxlength="20"></html:text>
        </template:formTr>
    </template:formTable>
    <template:formSubmit>
            <td><html:submit property="action" styleClass="button">提交	</html:submit></td>
                <td><html:button property="cancel"styleClass="button"
                onclick="onDelCode(this)">删除	</html:button></td>
        </template:formSubmit>
</html:form>
<html:form styleId="addtype"
    action="/TroubleCodeAction.do?method=addTroubleType" onsubmit='return submitType(this)'>
    <template:formTable namewidth="120" contentwidth="200">
        <template:formTr name="隐患类型代码">
            <html:text property="code" styleClass="inputtext" maxlength="3" styleId="t3" onchange="valiD('t3')" style="width:170"></html:text>
        </template:formTr>
        <template:formTr name="隐患类型名称">
            <html:text property="typename" styleClass="inputtext" maxlength="10" style="width:170"></html:text>
        </template:formTr>
    </template:formTable>
    <template:formSubmit>
            <td><html:submit property="action" styleClass="button">提交	</html:submit></td>
                <td><html:cancel property="cancel"styleClass="button"
                >取消	</html:cancel></td>
        </template:formSubmit>
</html:form>
<html:form styleId="edittype" style="display:none"
    action="/TroubleCodeAction.do?method=updateTroubleType" onsubmit='return submitType(this)'>
    <html:hidden property="id"/>
    <template:formTable namewidth="100" contentwidth="200">
        <template:formTr name="隐患类型代码">
            <html:text styleId="code" property="code" styleClass="inputtext" onchange="valiD('code')" maxlength="3" style="width:170"></html:text>
        </template:formTr>
        <template:formTr name="隐患类型名称">
            <html:text styleId="typename" property="typename" styleClass="inputtext" maxlength="10" style="width:170"></html:text>
        </template:formTr>
    </template:formTable>
    <template:formSubmit>
            <td><html:submit property="action" styleClass="button">提交	</html:submit></td>
                <td><html:button property="cancel"styleClass="button"
                onclick="onDelType(this)">删除	</html:button></td>
        </template:formSubmit>
</html:form>
<html:form styleId="addcode" style="display:none"
    action="/TroubleAction.do?method=addTroubleCode" onsubmit='return submitcode(this)'>
    <template:formTable namewidth="100" contentwidth="200">
        <template:formTr name="隐患码">
            <html:text property="troublecode"  styleClass="inputtext" styleId="t4" onchange="valiD('t4')" maxlength="3" style="width:170"></html:text>
        </template:formTr>
        <template:formTr name="隐患码名称">
            <html:text property="troublename" styleClass="inputtext"  maxlength="10" style="width:170"></html:text>
        </template:formTr>
        <template:formTr name="隐患码类型">
            <apptag:setSelectOptions tableName="troubletype" valueName="typecoll" columnName1="typename" columnName2="id" region="true"/>
            <html:select styleId="selId" property="troubletype" styleClass="multySelect" style="width:170" >
                <html:options collection="typecoll" property="value" labelProperty="label"/>
            </html:select>
        </template:formTr>
        <template:formTr name="备注">
            <html:text property="remark" styleClass="inputtext" style="width:170"></html:text>
        </template:formTr>
    </template:formTable>
    <template:formSubmit>
            <td><html:submit property="action" styleClass="button">提交	</html:submit></td>
                <td><html:cancel property="cancel"styleClass="button">取消	</html:cancel></td>
        </template:formSubmit>
</html:form>


</td>
</tr>
</table>
</body>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
