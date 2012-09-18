<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linechange.bean.*;"%>
<html>
	<script type="text/javascript" src="js/prototype.js"></script>
<script language="javascript">
	showHistoryDetail=function(value){
		var actionUrl="${ctx}/checkaction.do?method=showCheckHistory&show_type=all&change_id="+value+"&rnd="+Math.random();
		var myAjax=new Ajax.Updater(
			"applyAuditingTd",actionUrl,{
			method:"post",
			evalScript:true
			}
		);
	}

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
		t1.width="200";
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
      var url = "${ctx}/checkaction.do?method=getCheckInfo";
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
<%
ChangeInfoBean bean = (ChangeInfoBean)request.getAttribute("changeinfo");
System.out.print(bean.getChangename());
%>
<template:titile value="查看验收信息" />
<html:form action="/checkaction?method=addCheck" styleId="addCheckForm" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<input type="hidden" name="changeid">

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
<template:formTr name="工程造价">
<bean:write name="changeinfo" property="cost"/>万元
</template:formTr>
<template:formTr name="计划工期">
<bean:write name="changeinfo" property="plantime"/>天
</template:formTr>
<template:formTr name="验收日期">
<bean:write name="changecheck" property="checkdate"/>
</template:formTr>
<template:formTr name="验收负责人">
<bean:write name="changecheck" property="checkperson"/>
</template:formTr>
<template:formTr name="验收结果">
<bean:write name="changecheck" property="checkresult"/>
</template:formTr>
<template:formTr name="工程结算">
<bean:write name="changeinfo" property="square"/>万元
</template:formTr>
<template:formTr name="验收备注">
<div><bean:write name="changecheck" property="checkremark"/></div>
</template:formTr>
<template:formTr name="验收附件">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changecheck" property="checkdatum">
      <apptag:listAttachmentLink fileIdList=""/>
      </logic:empty>
      <logic:notEmpty name="changecheck" property="checkdatum">
        <bean:define id="temp" name="changecheck" property="checkdatum" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"/>
      </logic:notEmpty>
    </td>
	<td></td>
	</tr>
</table>
</template:formTr>
    <tr>
		<td colspan="3" id="applyAuditingTd"></td>
	</tr>
<template:formSubmit>
	<td>
		<input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
			<script type="text/javascript">
			showHistoryDetail("<bean:write name="changeinfo" property="id" />");
			</script>
</body>
</html>


