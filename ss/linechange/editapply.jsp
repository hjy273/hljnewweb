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
    //验证
    function validate(form){
      var str = /[^\u4E00-\u9FA5]/g;
      var Double = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
      if(!Check(Double,form.changelength.value)){
        alert("长度只能为数字，不能为其他字符！");
        form.changelength.value="0";
        return false;
      }
      if(!Check(Double,form.plantime.value)){
        alert("计划工期只能是数字！");
        form.plantime.value="0";
        return false;
      }
      if(form.startdate.value == ""){
        alert("开始日期不能等于空！");
        return false;
      }
      if(valCharLength(form.remark.value)>512){
            alert("备注信息超过250个汉字或者512个英文字符!");
         	return false;
      }
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
    //go back
    function addGoBack()
    {
      var url = "${ctx}/changeapplyaction.do?method=getApplyInfoList";
      self.location.replace(url);
    }
</script>
<body>
<template:titile value="编辑申请信息" />
<html:form action="/changeapplyaction?method=updateApply" styleId="addApplyForm"  onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="applydatumid" />
<html:hidden property="id"/>
<template:formTr name="工程名称">
<html:text property="changename" styleClass="inputtext" style="width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="工程性质">
<html:text property="changepro" styleClass="inputtext" style="width:250;" maxlength="15" />
</template:formTr>
<template:formTr name="工程地点">
<html:text property="changeaddr" styleClass="inputtext" style="width:250;" maxlength="50" />
</template:formTr>
<template:formTr name="网络性质">
<apptag:setSelectOptions columnName1="name" columnName2="code" tableName="lineclassdic" valueName="linetypeColl"/>
<html:select property="lineclass" styleClass="inputtext" style="width:160">
  <html:options collection="linetypeColl" property="value" labelProperty="label"/>
</html:select>
</template:formTr>
<template:formTr name="影响系统">
<html:text property="involvedSystem" styleClass="inputtext" style="width:250;" maxlength="100" />
</template:formTr>
<template:formTr name="迁改长度">
<html:text property="changelength" styleClass="inputtext" style="width:250;" maxlength="8" />
</template:formTr>
<template:formTr name="开始时间">
<html:text property="startdate" styleClass="inputtext" style="width:250;" maxlength="15" />
<apptag:date property="startdate" />
</template:formTr>
<template:formTr name="计划工期">
<html:text property="plantime" styleClass="inputtext" style="width:250;" maxlength="8" />
</template:formTr>

<template:formTr name="工程预算">
<html:text property="budget" styleClass="inputtext" style="width:250;" maxlength="8"/>
万元
</template:formTr>
					<template:formTr name="监理公司">
						<select name="superviseUnitId" class="inputtext" style="width: 180px">
							<logic:present name="supervise_units">
								<logic:iterate id="supervise_unit" name="supervise_units">
									<option
										value="<bean:write name="supervise_unit" property="contractorid"/>">
										<bean:write name="supervise_unit" property="contractorname" />
									</option>
								</logic:iterate>
							</logic:present>
						</select>
					</template:formTr>

<template:formTr name="委托施工单位名称">
<html:text property="entrustunit" styleClass="inputtext" style="width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="委托施工单位地址">
<html:text property="entrustaddr" styleClass="inputtext" style="width:250;" maxlength="50" />
</template:formTr>
<template:formTr name="委托施工单位电话">
<html:text property="entrustphone" styleClass="inputtext" style="width:250;" maxlength="20" />
</template:formTr>
<template:formTr name="委托施工单位联系人">
<html:text property="entrustperson" styleClass="inputtext" style="width:250;" maxlength="10" />
</template:formTr>
<template:formTr name="委托施工单位资质">
<html:select property="entrustgrade" styleClass="inputtext">
<html:option value="甲级">甲级
</html:option>
<html:option value="乙级">乙级
</html:option>
<html:option value="丙级">丙级
</html:option>
</html:select>
</template:formTr>

<template:formTr name="施工单位名称">
<html:text property="buildunit" styleClass="inputtext" style="width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="施工开始日期">
<html:text property="starttime" styleClass="inputtext" style="width:250;" readonly="true" maxlength="26" />
<apptag:date property="starttime" />
</template:formTr>

<template:formTr name="备注信息">
<html:textarea property="remark" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="备注信息最长250个汉字"/>
</template:formTr>
<template:formTr name="申请附件">
<table id="uploadID"  border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr class=trcolor>
    <td>
      <logic:empty name="changeinfo" property="applydatumid">
      </logic:empty>
      <logic:notEmpty name="changeinfo" property="applydatumid">
        <bean:define id="temp" name="changeinfo" property="applydatumid" type="java.lang.String"/>
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
	    <input type="button" value="返回" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
		
	</td>
</template:formSubmit>
</template:formTable>
</html:form>

<script type="text/javascript">
addApplyForm.superviseUnitId.value="<bean:write name="changeinfo" property="superviseUnitId"/>";
</script>
			<script type="text/javascript">
			showHistoryDetail("<bean:write name="changeinfo" property="id" />");
			</script>
</body>
</html>
