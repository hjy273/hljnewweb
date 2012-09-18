<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linechange.bean.*;"%>
<html>
	<script type="text/javascript" src="js/prototype.js"></script>
<script language="javascript">
	showHistoryDetail=function(value){
		var actionUrl="${ctx}/checkaction.do?method=showCheckHistory&change_id="+value+"&rnd="+Math.random();
		var myAjax=new Ajax.Updater(
			"applyAuditingTd",actionUrl,{
			method:"post",
			evalScript:true
			}
		);
	}
	
	function valCharLength(Value){
      var j=0;
      var s = Value;
      for   (var   i=0;   i<s.length;   i++)
      {
        if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
        else   j++
      }
      return j;
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
         if(form.checkdate.value == ""){
        alert("验收日期不能等于空！");
        return false;
      }
    }
    //验证
    function validate(form){
      var r= false;
      if(form.checkresult.options.selectedIndex==0){
	      if(form.square.value <= 0.0){
    	    alert("请填写结算金额！");
        	return false;
	      }
      }
      if(valCharLength(form.checkremark.value)>512){
            alert("备注信息超过250个汉字或者512个英文字符!");
         	return false;
      }
      return true;
      //if(form.ischangedatum.selected)
    }
	//添加提交
    function addGoBack()
    {
      var url = "${ctx}/changeapplyaction.do?method=getApplyInfoList&op=survey";
      self.location.replace(url);
    }
</script>
<body>
<template:titile value="填写验收信息" />
<html:form action="/checkaction?method=updateCheck" styleId="addCheckForm" onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="id"/>
<html:hidden property="changeid"/>
<html:hidden property="checkdatum"/>
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


<template:formTr name="施工开始日期">
<bean:write name="build" property="starttime"/>
</template:formTr>
<template:formTr name="施工结束日期">
<bean:write name="build" property="endtime"/>
</template:formTr>
<template:formTr name="施工单位名称">
<bean:write name="build" property="buildunit"/>
</template:formTr>
<template:formTr name="施工单位地址">
<bean:write name="build" property="buildaddr"/>
</template:formTr>
<template:formTr name="施工单位联系人">
<bean:write name="build" property="buildperson"/>
</template:formTr>
<template:formTr name="施工单位电话">
<bean:write name="build" property="buildphone"/>
</template:formTr>
<template:formTr name="施工备注">
<div><bean:write name="build" property="buildremark"/></div>
</template:formTr>
<template:formTr name="施工自评">
<div><bean:write name="build" property="buildvalue"/></div>
</template:formTr>
<template:formTr name="附件">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="build" property="builddatum">
      <apptag:listAttachmentLink fileIdList=""/>
      </logic:empty>
      <logic:notEmpty name="build" property="builddatum">
        <bean:define id="temp" name="build" property="builddatum" type="java.lang.String"/>
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


<template:formTr name="验收日期">
<html:text property="checkdate" styleClass="inputtext" style="width:250;" readonly="true" maxlength="26" />
<apptag:date property="checkdate" />
</template:formTr>
<template:formTr name="验收负责人">
<html:text property="checkperson" styleClass="inputtext" style="width:250;" maxlength="10" />
</template:formTr>
<template:formTr name="验收结果">
<html:select property="checkresult" styleClass="inputtext" style="width:160">
         <html:option value="通过审定">通过审定</html:option>
          <html:option value="未通过">未通过</html:option>
</html:select></template:formTr>
<template:formTr name="验收备注">
<html:textarea property="checkremark" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="备注信息最长250个汉字"/>
</template:formTr>


<template:formTr name="工程结算">
<html:text property="square" styleClass="inputtext" style="width:250;" maxlength="8" />万元
</template:formTr>


<template:formTr name="附件">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changecheck" property="checkdatum">
      </logic:empty>
      <logic:notEmpty name="changecheck" property="checkdatum">
        <bean:define id="temp" name="changecheck" property="checkdatum" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"  showdele="true"/>
      </logic:notEmpty>
    </td>
	<td></td>
	</tr>
</table>
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
		<html:button property="action" styleClass="button" onclick="addGoBack()">返回</html:button>
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
			<script type="text/javascript">
			showHistoryDetail("<bean:write name="changeinfo" property="id" />");
			</script>
</body>
</html>


