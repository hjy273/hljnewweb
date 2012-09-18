<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linechange.bean.*;"%>
<html>
<script language="javascript">

    fileNum=0;
	 //脚本生成的删除按  钮的删除动作
	function deleteRow(){
      	//获得按钮所在行的id
     	var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
         //由id转换为行索找行的索引,并删除
      	for(i =0; i<uploadID.rows.length;i++){
        	if(uploadID.rows[i].id == rowid){
            	uploadID.deleteRow(uploadID.rows[i].rowIndex);
                fileNum--;
            }
        }
    }
    //添加一个新行
    function addRow(){
    	var  onerow=uploadID.insertRow(-1);
        onerow.id = uploadID.rows.length-2;

        var   cell1=onerow.insertCell();
		var   cell2=onerow.insertCell();


		//创建一个输入框
        var t1 = document.createElement("input");
        //t1.name = "filename"+onerow.id;//alert(onerow.id);
        t1.name = "uploadFile["+ fileNum + "].file";
		t1.type= "file";
		t1.id = "text" + onerow.id;
		t1.width="300";
        fileNum++;


        //创建删除按钮
        var b1 =document.createElement("button");
        b1.value = "删除";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");


        cell1.appendChild(t1);//文字
        cell2.appendChild(b1);
	}
	 function valiD(id){
      	var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        var obj = document.getElementById(id);
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("你填写的不是数字,请重新输入");
            obj.focus();
            obj.value = "";
            return false;
        }
    }
	//添加提交
    function addGoBack()
    {
      var url = "${ctx}/buildsetoutaction.do?method=listBuildSetout";
      self.location.replace(url);
    }
</script>
<!--[if IE]>
<style>
div {word-break:break-all;;}
</style>
<![endif]-->
<style type="">
div {width:300px;border:solid 1px #000;}
</style>
<body>
<logic:equal value="deliverto" name="type">
<template:titile value="查看施工准备信息" />
<html:form action="/buildsetoutaction?method=updateDeliverTo" styleId="addchangeinfoForm" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="id"/>
<template:formTr name="工程名称">
<bean:write name="changeinfo" property="changename"/>
</template:formTr>
<template:formTr name="工程性质">
<bean:write name="changeinfo" property="changepro"/>
</template:formTr>
<template:formTr name="工程地点">
<bean:write name="changeinfo" property="changeaddr"/>
</template:formTr>
<template:formTr name="网络性质">
<%=(String)request.getAttribute("line_class_name") %>
</template:formTr>
<template:formTr name="影响系统">
<bean:write name="changeinfo" property="involvedSystem"/>
</template:formTr>
<template:formTr name="工程预算">
<bean:write name="changeinfo" property="budget"/>万元
</template:formTr>
<template:formTr name="勘查日期">
<bean:write name="survey" property="surveydate"/>
</template:formTr>
<template:formTr name="工程中心名称">
<bean:write property="ename" name="changeinfo"/>
</template:formTr>
<template:formTr name="工程中心地址">
<bean:write property="eaddr" name="changeinfo"/>
</template:formTr>
<template:formTr name="工程中心联系人">
<bean:write property="eperson" name="changeinfo"/>
</template:formTr>
<template:formTr name="工程中心电话">
<bean:write property="ephone" name="changeinfo"/>
</template:formTr>
<template:formTr name="备注">
<bean:write property="setoutremark" name="changeinfo"/>
</template:formTr>
<template:formTr name="附件">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changeinfo" property="setoutdatum">
      <apptag:listAttachmentLink fileIdList=""  />
      </logic:empty>
      <logic:notEmpty name="changeinfo" property="setoutdatum">
        <bean:define id="temp" name="changeinfo" property="setoutdatum" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"/>
      </logic:notEmpty>
    </td>
	<td></td>
	</tr>
</table>
</template:formTr>
<template:formSubmit>
	<td>
		<input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
</logic:equal>
<logic:equal value="engage" name="type">
<template:titile value="查看施工准备信息" />
<html:form action="/buildsetoutaction?method=updateEngage" styleId="addchangeinfoForm" enctype="multipart/form-data">
<html:hidden property="id"/>
<template:formTable namewidth="150" contentwidth="350">
<template:formTr name="工程名称">
<bean:write name="changeinfo" property="changename"/>
</template:formTr>
<template:formTr name="工程性质">
<bean:write name="changeinfo" property="changepro"/>
</template:formTr>
<template:formTr name="工程地点">
<bean:write name="changeinfo" property="changeaddr"/>
</template:formTr>
<template:formTr name="网络性质">
<%=(String)request.getAttribute("line_class_name") %>
</template:formTr>
<template:formTr name="影响系统">
<bean:write name="changeinfo" property="involvedSystem"/>
</template:formTr>
<template:formTr name="工程预算">
<bean:write name="changeinfo" property="budget"/>万元
</template:formTr>
<template:formTr name="勘查日期">
<bean:write name="survey" property="surveydate"/>
</template:formTr>
<template:formTr name="监理单位名称">
<bean:write property="sname" name="changeinfo"/>
</template:formTr>
<template:formTr name="监理单位地址">

<bean:write property="saddr" name="changeinfo"/>
</template:formTr>
<template:formTr name="监理单位电话">
<bean:write property="sphone" name="changeinfo"/>
</template:formTr>
<template:formTr name="监理单位联系人">
<bean:write property="sperson" name="changeinfo"/>
</template:formTr>
<template:formTr name="监理单位资质">
<bean:write property="sgrade" name="changeinfo"/>
</template:formTr>
<template:formTr name="监理费用">
<bean:write property="sexpense" name="changeinfo"/>万元
</template:formTr>
<tr><td>&nbsp;</td>
</tr>
<template:formTr name="设计单位名称">
<bean:write property="dname" name="changeinfo"/>
</template:formTr>
<template:formTr name="设计单位地址">
<bean:write property="daddr" name="changeinfo"/>
</template:formTr>
<template:formTr name="设计单位电话">
<bean:write property="dphone" name="changeinfo"/>
</template:formTr>
<template:formTr name="设计单位联系人">
<bean:write property="dperson" name="changeinfo"/>
</template:formTr>
<template:formTr name="设计单位资质">
<bean:write property="dgrade" name="changeinfo"/>
</template:formTr>
<template:formTr name="设计费用">
<bean:write property="dexpense" name="changeinfo"/>万元
</template:formTr>
<template:formTr name="备注">
<bean:write property="setoutremark" name="changeinfo"/>
</template:formTr>
<template:formTr name="附件">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changeinfo" property="setoutdatum">
       <apptag:listAttachmentLink fileIdList=""  />
      </logic:empty>
      <logic:notEmpty name="changeinfo" property="setoutdatum">
        <bean:define id="temp" name="changeinfo" property="setoutdatum" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>" />
      </logic:notEmpty>
    </td>
	<td></td>
	</tr>
</table>
</template:formTr>
<template:formSubmit>
	<td>
		<input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
</logic:equal>
</body>
</html>

