<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linechange.bean.*;"%>
<html>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script language="javascript">
	showHistoryDetail=function(value){
		var actionUrl="${ctx}/changesurveyaction.do?method=showSurveyHistory&change_id="+value+"&rnd="+Math.random();
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
    function validate(form){
      var str = /[^\u4E00-\u9FA5]/g;
      var Double = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
      if(!Check(Double,form.budget.value)){
        alert("工程预算只能为数字");
        form.budget.value="0";
        return false;
      }
	  if(form.surveydate.value == ""){
        alert("勘查日期不能等于空！");
        return false;
      }
      if(valCharLength(form.surveyremark.value)>512){
            alert("勘查备注信息超过250个汉字或者512个英文字符!");
         	return false;
      }
      if(valCharLength(form.approveremark.value)>512){
            alert("审核备注信息超过250个汉字或者512个英文字符!");
         	return false;
      }
      return true;
    }
    //验证空字符串
    function CheckEmpty( str )
    {
      return ( str == "" );
    }

    function Check( reg, str )
    {
      if( reg.test( str ) )
      {
        return true;
      }
      return false;
    }
	//添加提交
    function addGoBack()
    {
      var url = "${ctx}/changeapplyaction.do?method=getApplyInfoList&op=survey";
      self.location.replace(url);
    }
</script>
<body>
<template:titile value="填写勘查审定信息" />
<html:form action="/changesurveyaction?method=updateSurvey" styleId="addSurveyForm" onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="surveydatum" />
<html:hidden property="changeid"/>
<html:hidden property="id"/>
<template:formTr name="工程名称">
      <bean:write name="changeinfo" property="changename"/>
    </template:formTr>
    <template:formTr name="工程地点">
      <bean:write name="changeinfo" property="changeaddr"/>
    </template:formTr>
    <template:formTr name="工程性质">
      <bean:write name="changeinfo" property="changepro"/>
    </template:formTr>
    <template:formTr name="迁改长度">
      <bean:write name="changeinfo" property="changelength"/> 米
    </template:formTr>
<template:formTr name="计划工期">
<bean:write name="changeinfo" property="plantime"/>天
</template:formTr>
    <template:formTr name="申请附件">
      <logic:empty name="changeinfo" property="applydatumid">
        <apptag:listAttachmentLink fileIdList=""/>
      </logic:empty>
      <logic:notEmpty name="changeinfo" property="applydatumid">
        <bean:define id="temp" name="changeinfo" property="applydatumid" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"/>
      </logic:notEmpty>
    </template:formTr>
<template:formTr name="勘查日期">
<html:text property="surveydate" styleClass="inputtext" style="width:250;" maxlength="26" />
<apptag:date property="surveydate" />
</template:formTr>


<template:formTr name="勘查负责人">
<html:text property="principal" styleClass="inputtext" style="width:250;" maxlength="10" />
</template:formTr>
<template:formTr name="工程预算">
<html:text property="budget" styleClass="inputtext" style="width:250;" maxlength="8" /> 万元
</template:formTr>
    <logic:equal value="1" name="dept_type">
    <template:formTr name="监理单位">
      <bean:write name="changeinfo" property="sname"/>
      <html:hidden name="changeinfo" property="sname" />
      <input name="surveytype" value="B2" type="hidden" />
    </template:formTr>
    <template:formTr name="监理单位地址">
      <bean:write name="changeinfo" property="saddr"/>
      <html:hidden name="changeinfo" property="saddr" />
    </template:formTr>
    <template:formTr name="监理单位电话">
      <bean:write name="changeinfo" property="sphone"/>
      <html:hidden name="changeinfo" property="sphone" />
    </template:formTr>
    <template:formTr name="监理单位联系人">
      <bean:write name="changeinfo" property="sperson"/>
      <html:hidden name="changeinfo" property="sperson" />
    </template:formTr>
    <template:formTr name="监理单位资质">
      <bean:write name="changeinfo" property="sgrade"/>
      <html:hidden name="changeinfo" property="sgrade" />
    </template:formTr>
    <template:formTr name="监理费用">
      <bean:write name="changeinfo" property="sexpense"/>万元
      <html:hidden name="changeinfo" property="sexpense" />
    </template:formTr>
    </logic:equal>
    <logic:equal value="2" name="dept_type">
    <template:formTr name="监理单位">
      <input name="surveytype" value="B1" type="hidden" />
      <html:text property="sname" name="changeinfo" styleClass="inputtext" style="width:250;" maxlength="10"/>
    </template:formTr>
    <template:formTr name="监理单位地址">
        <html:text property="saddr" name="changeinfo" styleClass="inputtext" style="width:250;" maxlength="50"/>
    </template:formTr>
    <template:formTr name="监理单位电话">
        <html:text property="sphone" name="changeinfo" styleClass="inputtext" style="width:250;" maxlength="20"/>
    </template:formTr>
    <template:formTr name="监理单位联系人">
      <html:text property="sperson" name="changeinfo" styleClass="inputtext" style="width:250;" maxlength="10"/>
    </template:formTr>
    <template:formTr name="监理单位资质">
        <html:select property="sgrade" name="changeinfo" styleClass="inputtext">
          <html:option value="甲级">甲级</html:option>
          <html:option value="乙级">乙级</html:option>
          <html:option value="丙级">丙级</html:option>
        </html:select>
    </template:formTr>
    <template:formTr name="监理费用">
        <html:text property="sexpense" name="changeinfo" styleClass="inputtext" style="width:250;" maxlength="8"/>万元
    </template:formTr>
    </logic:equal>


<template:formTr name="勘查备注">
<html:textarea property="surveyremark" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="备注信息最长250个汉字"/>
</template:formTr>
<template:formTr name="附件">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changesurvey" property="surveydatum">
      </logic:empty>
      <logic:notEmpty name="changesurvey" property="surveydatum">
        <bean:define id="temp" name="changesurvey" property="surveydatum" type="java.lang.String"/>
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
<tr>
      <td colspan="3" align="center">&nbsp;</td>
</tr>
<template:formTr name="审定结果">
<html:select property="approveresult" styleClass="inputtext" style="width:160">
         <html:option value="通过审定">通过审定</html:option>
          <html:option value="未通过">未通过</html:option>
</html:select>
</template:formTr>

<template:formTr name="审 定 人">
<html:text property="approver" styleClass="inputtext" style="width:250;" maxlength="26" />
</template:formTr>
<template:formTr name="备注">
<html:textarea property="approveremark" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="备注信息最长250个汉字"/>
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


