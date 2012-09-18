<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linechange.bean.*;"%>
<html>
<script language="javascript">
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
        //t1.style= "width:30px;height:60px"
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
    //表单验证
    function valphone(Value){
      var j=0;
      var s = Value;
      for   (var   i=0;   i<s.length;   i++)
      {
              if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
              else   j++
      }
      //alert("字符数："+j);
      if(j>20){
        alert("电话号码不能大于20个字符！");
        return false;
      }else{
        return true;
      }
    }
    function validate1(form){
      var s = form.ephone.value;
      return valphone(s);

    }
    //验证
    function validate(form){
      var str = /[^\u4E00-\u9FA5]/g;
      var Double = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
      if(!Check(Double,form.sexpense.value)){
        alert("监理费用只能为数字，不能为其他字符！");
        form.sexpense.value="0";
        return false;
      }
      if(!Check(Double,form.dexpense.value)){
        alert("设计费用只能为数字，不能为其他字符！");
        form.dexpense.value="0";
        return false;
      }
      if(valCharLength(form.setoutremark.value)>512){
            alert("备注信息超过250个汉字或者512个英文字符!");
         	return false;
      }
      var s1 = form.sphone.value;
      var s2 = form.dphone.value;
      if(!valphone(s1)){
        form.sphone.focus();
        return false;
      }
       if(!valphone(s2)){
         form.dphone.focus();
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
      var url = "${ctx}/buildsetoutaction.do?method=listBuildSetout";
      self.location.replace(url);
    }
</script>
<body>
<logic:equal value="deliverto" name="flow">
  <template:titile value="填写施工准备信息"/>
  <html:form action="/buildsetoutaction?method=updateDeliverTo" onsubmit="return validate1(this);" styleId="addchangeinfoForm" enctype="multipart/form-data">
    <template:formTable namewidth="150" contentwidth="350">
      <html:hidden property="id"/>
      <html:hidden property="type"/>
      <html:hidden property="setoutdatum"/>
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
        <bean:write name="changeinfo" property="budget"/>
        万元
      </template:formTr>
      <template:formTr name="勘查日期">
        <bean:write name="survey" property="surveydate"/>
      </template:formTr>
      <template:formTr name="监理单位名称">
        <input name="sname" value="<bean:write name="changeinfo" property="sname" />" 
         class="inputtext" style="width:250;" maxlength="25" type="text" />
      </template:formTr>
      <template:formTr name="监理单位地址">
        <input name="saddr" value="<bean:write name="changeinfo" property="saddr" />" 
         class="inputtext" style="width:250;" maxlength="50" type="text" />
      </template:formTr>
      <template:formTr name="监理单位电话">
        <input name="sphone" value="<bean:write name="changeinfo" property="sphone" />" 
         class="inputtext" style="width:250;" maxlength="20" type="text" />
      </template:formTr>
      <template:formTr name="监理单位联系人">
        <input name="sperson" value="<bean:write name="changeinfo" property="sperson" />" 
         class="inputtext" style="width:250;" maxlength="10" type="text" />
      </template:formTr>
      <template:formTr name="监理单位资质">
        <select name="sgrade" class="inputtext">
          <option value="甲级">甲级</option>
          <option value="乙级">乙级</option>
          <option value="丙级">丙级</option>
        </select>
      </template:formTr>
      <script type="text/javascript">
        addchangeinfoForm.sgrade.value="<bean:write name="changeinfo" property="sgrade" />";
      </script>
      <template:formTr name="监理费用">
        <input name="sexpense" value="<bean:write name="changeinfo" property="sexpense" />" 
         class="inputtext" style="width:250;" maxlength="8" type="text" />万元
      </template:formTr>
      <template:formTr name="工程中心名称">
        <html:text property="ename" styleClass="inputtext" style="width:250;" maxlength="25"/>
      </template:formTr>
      <template:formTr name="工程中心地址">
        <html:text property="eaddr" styleClass="inputtext" style="width:250;" maxlength="50"/>
      </template:formTr>
      <template:formTr name="工程中心联系人">
        <html:text property="eperson" styleClass="inputtext" style="width:250;" maxlength="10"/>
      </template:formTr>
      <template:formTr name="工程中心电话">
        <html:text property="ephone" styleClass="inputtext" style="width:250;" maxlength="20"/>
      </template:formTr>
      <template:formTr name="备注">
        <html:textarea property="setoutremark" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="备注信息最长250个汉字"/>
      </template:formTr>
      <template:formTr name="附件">
        <table id="uploadID" border="0" align="left" cellpadding="0" cellspacing="0">
          <tr class=trcolor>
            <td>
              <logic:empty name="changeinfo" property="setoutdatum">              </logic:empty>
              <logic:notEmpty name="changeinfo" property="setoutdatum">
                <bean:define id="temp" name="changeinfo" property="setoutdatum" type="java.lang.String"/>
                <apptag:listAttachmentLink fileIdList="<%=temp%>" showdele="true"/>
              </logic:notEmpty>
            </td>
            <td>            </td>
          </tr>
        </table>
      </template:formTr>
      <template:formSubmit>
        <td>
          <html:button property="action" styleClass="button" onclick="addRow()">添加附件</html:button>
        </td>
        <td>
          <html:submit styleClass="button">提交</html:submit>
        </td>
        <td>
          <html:reset styleClass="button">取消</html:reset>
        </td>
        <td>
         <input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
        </td>
      </template:formSubmit>
    </template:formTable>
  </html:form>
</logic:equal>
<logic:equal value="engage" name="flow">
  <template:titile value="填写施工准备信息"/>
  <html:form action="/buildsetoutaction?method=updateEngage" styleId="addchangeinfoForm" onsubmit="return validate(this);" enctype="multipart/form-data">
    <template:formTable namewidth="150" contentwidth="350">
      <html:hidden property="id"/>
      <html:hidden property="type"/>
      <html:hidden property="setoutdatum"/>
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
        <bean:write name="changeinfo" property="budget"/>
        万元
      </template:formTr>
      <template:formTr name="勘查日期">
        <bean:write name="survey" property="surveydate"/>
      </template:formTr>
      <template:formTr name="监理单位名称">
        <input name="sname" value="<bean:write name="changeinfo" property="sname" />" 
         class="inputtext" style="width:250;" maxlength="25" type="text" />
      </template:formTr>
      <template:formTr name="监理单位地址">
        <input name="saddr" value="<bean:write name="changeinfo" property="saddr" />" 
         class="inputtext" style="width:250;" maxlength="50" type="text" />
      </template:formTr>
      <template:formTr name="监理单位电话">
        <input name="sphone" value="<bean:write name="changeinfo" property="sphone" />" 
         class="inputtext" style="width:250;" maxlength="20" type="text" />
      </template:formTr>
      <template:formTr name="监理单位联系人">
        <input name="sperson" value="<bean:write name="changeinfo" property="sperson" />" 
         class="inputtext" style="width:250;" maxlength="10" type="text" />
      </template:formTr>
      <template:formTr name="监理单位资质">
        <select name="sgrade" class="inputtext">
          <option value="甲级">甲级</option>
          <option value="乙级">乙级</option>
          <option value="丙级">丙级</option>
        </select>
      </template:formTr>
      <script type="text/javascript">
        addchangeinfoForm.sgrade.value="<bean:write name="changeinfo" property="sgrade" />";
      </script>
      <template:formTr name="监理费用">
        <input name="sexpense" value="<bean:write name="changeinfo" property="sexpense" />" 
         class="inputtext" style="width:250;" maxlength="8" type="text" />万元
      </template:formTr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <template:formTr name="设计单位名称">
        <html:text property="dname" styleClass="inputtext" style="width:250;" maxlength="25"/>
      </template:formTr>
      <template:formTr name="设计单位地址">
        <html:text property="daddr" styleClass="inputtext" style="width:250;" maxlength="50"/>
      </template:formTr>
      <template:formTr name="设计单位电话">
        <html:text property="dphone" styleClass="inputtext" style="width:250;" maxlength="20"/>
      </template:formTr>
      <template:formTr name="设计单位联系人">
        <html:text property="dperson" styleClass="inputtext" style="width:250;" maxlength="10"/>
      </template:formTr>
      <template:formTr name="设计单位资质">
        <html:select property="dgrade" styleClass="inputtext">
          <html:option value="甲级">甲级</html:option>
          <html:option value="乙级">乙级</html:option>
        </html:select>
      </template:formTr>
      <template:formTr name="设计费用">
        <html:text property="dexpense" styleClass="inputtext" style="width:250;" maxlength="8"/>万元
      </template:formTr>
      <template:formTr name="备注">
        <html:textarea property="setoutremark" cols="36" onkeyup="//this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="备注信息最长250个汉字"/>
      </template:formTr>
      <template:formTr name="附件">
        <table id="uploadID" border="0" align="left" cellpadding="0" cellspacing="0">
          <tr class=trcolor>
            <td>
              <logic:empty name="changeinfo" property="setoutdatum">              </logic:empty>
              <logic:notEmpty name="changeinfo" property="setoutdatum">
                <bean:define id="temp" name="changeinfo" property="setoutdatum" type="java.lang.String"/>
                <apptag:listAttachmentLink fileIdList="<%=temp%>" showdele="true"/>
              </logic:notEmpty>
            </td>
            <td>            </td>
          </tr>
        </table>
      </template:formTr>
      <template:formSubmit>
        <td>
          <html:button property="action" styleClass="button" onclick="addRow()">添加附件</html:button>
        </td>
        <td>
          <html:submit styleClass="button">提交</html:submit>
        </td>
        <td>
          <html:reset styleClass="button">取消</html:reset>
        </td>
        <td>
<input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
        </td>
      </template:formSubmit>
    </template:formTable>
  </html:form>
</logic:equal>
</body>
</html>
