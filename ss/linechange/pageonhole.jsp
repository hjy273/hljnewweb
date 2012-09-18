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
    //验证
    function validate(form){
      var r= false;
      for(i=0;i<form.ischangedatum.length;i++){
        if(form.ischangedatum[i].checked){
          r = true;
        }
      }
      if(r==false){
        alert("请选择是否需要修改资料！");
        return false;
      }
      if(form.square.value <= 0.0){
        alert("请填写结算金额！");
        return false;
      }
      //if(form.ischangedatum.selected)
    }
    //添加提交
    function addGoBack()
    {
      var url = "${ctx}/pageonholeaction.do?method=getChangeInfo";
      self.location.replace(url);
    }
</script>
<body>
<template:titile value="填写归档信息" />
<html:form action="/pageonholeaction.do?method=updatePageonhole" onsubmit="return validate(this);" styleId="addChangeForm" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="id"/>
<html:hidden property="pageonholedatum"/>
<html:hidden property="pageonholedatum"/>
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
<template:formTr name="工程预算">
<bean:write name="changeinfo" property="budget"/>万元
</template:formTr>
<template:formTr name="迁改长度">
<bean:write name="changeinfo" property="changelength"/>米
</template:formTr>
<template:formTr name="工程造价">
<bean:write name="changeinfo" property="cost"/>万元
</template:formTr>
<template:formTr name="计划工期">
<bean:write name="changeinfo" property="plantime"/>天
</template:formTr>
<template:formTr name="工程结算">
<html:text property="square" styleClass="inputtext" style="width:250;" maxlength="8" />
</template:formTr>
<template:formTr name="是否需要修改资料">
<html:radio property="ischangedatum"  value="是">是
</html:radio>
<html:radio property="ischangedatum"  value="否">否
</html:radio>
</template:formTr>
<template:formTr name="归档附件">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changeinfo" property="pageonholedatum">
      </logic:empty>
      <logic:notEmpty name="changeinfo" property="pageonholedatum">
        <bean:define id="temp" name="changeinfo" property="pageonholedatum" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"  showdele="true"/>
      </logic:notEmpty>
    </td>
	<td></td>
	</tr>
</table>
</template:formTr>
<template:formTr name="归档备注">
<html:textarea property="pageonholeremark" cols="36" onkeyup="this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="备注信息最长250个汉字"/>
</template:formTr>
<template:formSubmit>
	<td>
		<html:button property="action" styleClass="button" onclick="addRow()">添加附件</html:button>
	</td>
	<td>
		<html:submit styleClass="button" >提交</html:submit>
	</td>
	<td>
		<html:reset styleClass="button">取消	</html:reset>
	</td>
	<td>

    <!--
		<html:button property="action" styleClass="button" onclick="addGoBack()">返回</html:button>
	-->
    </td>
</template:formSubmit>
</template:formTable>
</html:form>
</body>
</html>


