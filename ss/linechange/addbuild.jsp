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
    }

   //表单验证
    function validate(form){
      var j=0;
      var s = form.buildphone.value;
      for   (var   i=0;   i<s.length;   i++)
      {
              if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
              else   j++
      }
      //alert("字符数："+j);
      if(j>20){
        alert("电话号码不能大于20个字符！");
        return false;
      }
      if(form.starttime.value == ""){
        alert("施工开始日期不能等于空！");
        return false;
      }
      if(form.endtime.value == ""){
        alert("施工结束日期不能等于空！");
        return false;
      }
      if(valCharLength(form.buildremark.value)>512){
            alert("施工备注信息超过250个汉字或者512个英文字符!");
         	return false;
      }
      if(valCharLength(form.buildvalue.value)>512){
            alert("施工自评信息超过250个汉字或者512个英文字符!");
         	return false;
      }
    }
	//添加提交
    function addGoBack()
    {
      var url = "${ctx}/buildaction.do?method=getChangeInfo";
      self.location.replace(url);
    }
</script>
<body>
<template:titile value="填写施工信息" />
<html:form action="/buildaction.do?method=addBuild" styleId="addBuildForm" onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="changeid"/>
<html:hidden property="id"/>
<html:hidden property="builddatum"/>
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
<template:formTr name="委托施工单位名称">
<bean:write name="changeinfo" property="entrustunit"/>
</template:formTr>
<template:formTr name="委托施工单位电话">
<bean:write name="changeinfo" property="entrustphone"/>
</template:formTr>
<template:formTr name="委托施工单位联系人">
<bean:write name="changeinfo" property="entrustperson"/>
</template:formTr>


<template:formTr name="施工开始日期">
<input name="starttime" value="<bean:write name="changeinfo" property="starttime" />"  
 class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:250;" readonly maxlength="26" />
</template:formTr>


<template:formTr name="施工结束日期">
<html:text property="endtime" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:250;" readonly="true" maxlength="26" />
</template:formTr>


<template:formTr name="施工单位名称">
<input name="buildunit" value="<bean:write name="changeinfo" property="buildunit" />"  
 class="inputtext" style="width:250;" maxlength="25"/>
</template:formTr>


<template:formTr name="施工单位地址">
<html:text property="buildaddr" styleClass="inputtext" style="width:250;" maxlength="50" />
</template:formTr>
<template:formTr name="施工单位联系人">
<html:text property="buildperson" styleClass="inputtext" style="width:250;" maxlength="10" />
</template:formTr>
<template:formTr name="施工单位电话">
<html:text property="buildphone" styleClass="inputtext" style="width:250;" maxlength="20" />
</template:formTr>
<template:formTr name="施工备注">
<html:textarea property="buildremark" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="备注信息最长250个汉字"/>
</template:formTr>
<template:formTr name="施工自评">
<html:textarea property="buildvalue" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="备注信息最长250个汉字"/>
</template:formTr>
<template:formTr name="附件">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changebuild" property="builddatum">
      </logic:empty>
      <logic:notEmpty name="changebuild" property="builddatum">
        <bean:define id="temp" name="changebuild" property="builddatum" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"  showdele="true"/>
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
		<html:button property="action" styleClass="button" onclick="addRow()">添加附件</html:button>
	</td>
	<td>
		<html:submit styleClass="button" >提交</html:submit>
	</td>
	<td>
		<html:reset styleClass="button">取消	</html:reset>
	</td>
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


