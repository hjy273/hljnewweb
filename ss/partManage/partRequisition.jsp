<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.partmanage.beans.Part_requisitionBean" %>
<%@page import="com.cabletech.baseinfo.dao.UserInfoDAOImpl" %>

<html>
<head>
<script type="" language="javascript">
    var rowArr = new Array();//一行材料的信息
    var infoArr = new Array();//所有材料的信息

    //初始化数组
    function initArray(id,name,unit,type){
        rowArr[0] = id;
        rowArr[1] = name;
        rowArr[2] = unit;
        rowArr[3] = type;
        infoArr[infoArr.length] = rowArr;
        rowArr = new Array();
        return true;
    }
    //脚本生成的删除按  钮的删除动作
    function deleteRow(){
          //获得按钮所在行的id
         var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
        //由id转换为行索找行的索引,并删除
          for(i =0; i<queryID.rows.length;i++){
            if(queryID.rows[i].id == rowid){
                queryID.deleteRow(queryID.rows[i].rowIndex);
            }
        }
    }
    //脚本生成的select动作onselectchange
    function onselectchange(){
        rowid = this.id.substring(6,this.id.length);
		var selectValue = this.value;
        var tmpValue;
        var tr;//行对象
        for(i =0; i<queryID.rows.length;i++){
            if(queryID.rows[i].id == rowid){
                tr = queryID.rows[i];
            } else {
				if(document.getElementById("select"+queryID.rows[i].id) != null ){
            		tmpValue = document.getElementById("select"+queryID.rows[i].id).value;
					if(tmpValue == selectValue) {
						alert("该材料已经选择过了，请重新选择申请材料！");
						document.getElementById("select"+rowid).value = "";
						return;
					}
				}
			}
        }
        //找出选择材料所对应的所有信息
        var unit;
        var type;
        for(i = 0; i < infoArr.length; i++){
            if(this.value == infoArr[i][0]){
                unit = infoArr[i][2];
                type = infoArr[i][3]
            }
        }
        //写入表格
        var tu = document.createElement("input");
        tu.value= unit;
        tu.maxLength = "6";
        tu.size = "6";
        tu.style.background="#C6D6E2"
        tu.style.font.size="12px";
        tu.readonly="readonly";
        tr.cells[1].innerText ="";
        tr.cells[1].appendChild(tu);

        var tt = document.createElement("input");
        tt.value= type;
        tt.maxLength = "6";
        tt.size = "27";
        tt.style.background="#C6D6E2"
        tt.style.font.size="12px";
        tt.readonly="readonly";
        tr.cells[2].innerText ="";
        tr.cells[2].appendChild(tt);

        //tr.cells[1].innerText =unit;
        //tr.cells[2].innerText =type;
    }

    //添加一个新行
    function addRow(){
        var  onerow=queryID.insertRow(-1);
        onerow.id = queryID.rows.length;

        var   cell1=onerow.insertCell();
        var   cell2=onerow.insertCell();
        var   cell3=onerow.insertCell();
        var   cell4=onerow.insertCell();
        var   cell5=onerow.insertCell();

        //创建一个输入框
        var t1 = document.createElement("input");
        t1.name = "renumber"
        t1.id = "text" + onerow.id;
        t1.value= "0";
        t1.maxLength = "6";
        t1.size = "6";
        t1.onblur=valiD;
        t1.style.background="#C6D6E2"
        t1.style.font.size="12px";


        //创建一个select
        var s1 = document.createElement("select");
        s1.options.length = infoArr.length + 1;
        s1.options[0].value = "";
        s1.options[0].text = "请选择材料";
        for(i = 1; i<s1.options.length ;i++){
            s1.options[i].value = infoArr[i-1][0];
            s1.options[i].text = infoArr[i-1][1] + " -- " + infoArr[i-1][3];
        }
        s1.name = "id";
        s1.id = "select" + onerow.id;
        s1.onchange = onselectchange;
        s1.style.background="#C6D6E2"
        s1.style.font.size="12px";
        s1.style.width="250";

        //创建删除按钮
        var b1 =document.createElement("button");
        b1.value = "删除";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;

        cell1.appendChild(s1);//文字
        cell2.innerText="没选材料";//文字
        cell3.innerText="没选材料";//文字
        cell4.appendChild(t1);//文字
        cell5.appendChild(b1);
    }

  //检验是否数字
    function valiD(){
         var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        if(mysplit.test(this.value)){
          return true;
        }
        else{
            alert("你填写的不是数字,请重新输入");
            this.value=0
            this.focus();

        }
    }
   //添加返回
    function addGoBack(type){
        try{
          if(type=="1")
             location.href="${ctx}/PartRequisitionAction.do?method=showAllRequisition&&querytype=1";
          else
             location.href = "${ctx}/PartRequisitionAction.do?method=showAllRequisition";
             return true;
            }
            catch(e){
                alert(e);
            }
    }
    //删除确认
    function toDeletForm(idValue){
        if(confirm("你确定要执行此次删除操作吗?")){
            //var url = "${ctx}/PartBaseInfoAction.do?method=deletePartBaseInfo&id=" + idValue;
            self.location.replace(url);
         }
        else
            return false;
    }
    //显示页面转向修改页面
    function toUpForm(idValue,state){
       if(state!="待审批"){
         alert('申请单已经被审批，不能修改！');
       }else{
         var url = "${ctx}/PartRequisitionAction.do?method=upshow&reid=" + idValue;
         self.location=url;
       }
    }

    //修改页面通过迭代生成 的删除按钮删除动作
    function upDelOnClick(id){
     var trid = id.substring(1,id.length);
     var trobj = document.getElementById(trid);
     queryID.deleteRow(trobj.rowIndex);
    }
    //修改页面显示已有的材料select动作
    function upselectOncheng(id){
        var trid = id.substring(4,id.length);
         var trobj = document.getElementById(trid);//行对象
        var seleobj = document.getElementById(id);
        //alert(seleobj.selectedIndex);//seleobj.selectedIndex是所选择的选项索引值
        for(i = 0; i< infoArr.length;i++){
            if(seleobj.options[seleobj.selectedIndex].value == infoArr[i][0]){
                var tu = document.createElement("input");
                tu.value= infoArr[i][2];
                tu.maxLength = "6";
                tu.size = "6";
                tu.style.background="#C6D6E2"
                tu.style.font.size="12px";
                tu.readonly="readonly";
                trobj.cells[1].innerText ="";
                trobj.cells[1].appendChild(tu);

                var tt = document.createElement("input");
                tt.value= infoArr[i][3];
                tt.maxLength = "6";
                tt.size = "27";
                tt.style.background="#C6D6E2"
                tt.style.font.size="12px";
                tt.readonly="readonly";
                trobj.cells[2].innerText ="";
                trobj.cells[2].appendChild(tt);
            }
        }
        return true;
    }


   //显示页面详细信息按钮动作
   function toGetForm(idValue,cIdValue,type){
         var url = "${ctx}/PartRequisitionAction.do?method=showOneInfo&querytype="+type+"&contractorid="+cIdValue+"&reid=" + idValue;
        self.location.replace(url);
    }

    //修改页面提交动作
    function toUpSub(){
        if(queryID.rows.length<2){
              alert("你把材料删没了,不能提交!")
            return false;
          }
      if(confirm("你确定要执行此次修改操作吗?")){
            UpForm.submit();
         }
        else
            return false;
    }
    //显示页面删除按钮动作
     function toDelForm(idValue,state){
       if(state!="待审批"){
         alert('申请单已经被审批，不能删除！');
       }else{
         if(confirm("你确定要删除这条申请单吗?")){
           var url = "${ctx}/PartRequisitionAction.do?method=delOneReqInfo&reid=" + idValue;
           self.location.replace(url);
         }
         else
           return ;
       }
    }
 //选择日期
    function GetSelectDateTHIS(strID) {
        document.all.item(strID).value = getPopDate(document.all.item(strID).value);
		//开始时间
    	var yb = queryForm2.begintime.value.substring(0,4);
		var mb = parseInt(queryForm2.begintime.value.substring(5,7) ,10);
		var db = parseInt(queryForm2.begintime.value.substring(8,10),10);
    	//结束时间
    	var ye = queryForm2.endtime.value.substring(0,4);
		var me = parseInt(queryForm2.endtime.value.substring(5,7) ,10);
		var de = parseInt(queryForm2.endtime.value.substring(8,10),10);
    	if(yb == "" && ye != "" ) {
	  		alert("请输入查询的开始时间!");      		
      		queryForm2.begintime.focus();
      		return false;
		}
		// 在开始和结束时间都输入的情况下做不能跨年度的判断
		if(yb != "" && ye != "") {
			//if(yb!=ye){
      		//	alert("查询时间段不能跨年度!");
      		//	document.all.item(strID).value="";
      		//	queryForm2.endtime.focus();
      		//	return false;
    		//}
			if(ye < yb) {
				alert("查询结束日期不能小于开始日期!");
      			document.all.item(strID).value="";
      			queryForm2.endtime.focus();
      			return false;
			}
			if((ye == yb ) && (me < mb  || de < db)) {
      			alert("查询结束日期不能小于开始日期!");
     		 	document.all.item(strID).value="";
      			queryForm2.endtime.focus();
      			return false;
    		}
		}
        return true;
    }
    //添加提交
    function toAddSub(){
      if(queryID.rows.length < 2){
          alert("你还没有选择材料,不能提交!")
        return false;
      }
	var addsub = document.getElementById("addsub");
	addsub.disabled=true;
	addForm.submit();
    }

    //显示待审批申请单页面审批该申请单按钮动作
     function toAuditForm(idValue,audresult){
         //if(confirm("你确定要审批这条申请单吗?")){
        if(audresult == "待审核"){
              if(confirm("你确定要审核这条申请单吗?")){
              var url = "${ctx}/PartRequisitionAction.do?method=doShowOneForReAudit&reid=" + idValue;
              self.location.replace(url);
            }
        }else{
              if(confirm("你确定要审批这条申请单吗?")){
                var url = "${ctx}/PartRequisitionAction.do?method=doAuditShowOne&reid=" + idValue;
                self.location.replace(url);
              }
        }
        return

    }

    //检验是否数字
    function valiDigit(id){
        var obj = document.getElementById(id);
        if(obj.value=="")
            obj.value="0";
        var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        if(mysplit.test(obj.value)){
          return true;
        }else{
            alert("你填写的不是数字,请重新输入");
               obj.focus();
            obj.value=0
            return false;
        }
    }

    //显示审批单页面,详细信息按钮动作
   function toShowOneAudit(idValue){
         var url = "${ctx}/PartRequisitionAction.do?method=doShowOneAudit&reid=" + idValue;
        self.location.replace(url);
    }

    //显示审批单页面,详细信息按钮动作
   function  toGoBackDistail(){
         var url = "${ctx}/PartRequisitionAction.do?method=showAllAudit";
        self.location.replace(url);
    }
    //单击导入申请单时弹出页面
function showupload()
{
    var objpart = document.getElementById("partDivId");
    var objup = document.getElementById("upDivId");

    objpart.style.display="none";
    objup.style.display="block";
}
function noupload()
{
    var objpart = document.getElementById("partDivId");
    var objup = document.getElementById("upDivId");

    objpart.style.display="block";
    objup.style.display="none";
}

function onmouseover(){
    alert("jdfkalj");
}
function exportReq(){
  try{
    location.href = "${ctx}/PartRequisitionAction.do?method=exportOneRequisition";
    return true;
  }
  catch(e){
    alert(e);
  }
}

function downloadFile() {
  location.href = "${ctx}/PartRequisitionAction.do?method=downloadTemplet";
}

function toAuditShow() {
  location.href = "${ctx}/PartRequisitionAction.do?method=doAuditShow";
}

function textCounter(field,maxlimit) {   
  if(field.value.length > maxlimit)     
  	field.value = field.value.substring(0,maxlimit);   
   
} 

function backToAuditShow() {
	location.href = "${ctx}/PartRequisitionAction.do?method=doAuditShow"
}
</script>
<title>partRequisition</title>
</head>
<style type="text/css">
.subject{text-align:center;}
</style>
<body>
  <!--显示申请单详细信息-->
<logic:equal value="10" scope="session" name="type">
  <br/>
  <template:titile value="维护材料申请单详细信息"/>
  <html:form action="/PartRequisitionAction?method=upRequisition" styleId="addForm">
    <input type="hidden" name="reid" value="<bean:write name="reqinfo" property="reid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <table align="center" width="90%" border="0" class="tabout">
      <tr class=trcolor>
        <td width="60" height="25">
          <b>单位名称:</b>
        </td>
        <td align="left" width="120">
          <bean:write name="reqinfo" property="contractorname"/>
        </td>
        <td align="right" width="50">
          <b>申请人:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="reqinfo" property="username"/>
        </td>
        <td width="70" align="right">
          <b>申请时间:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="reqinfo" property="time"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td height="25">
          <b>申请事由:</b>
        </td>
        <td colspan="3">
          <bean:write name="reqinfo" property="reason"/>
        </td>
        <td width="80" align="right">
          <b>目标处理人:</b>
        </td>
        <td align="left" >
          <bean:write name="reqinfo" property="targetmanname"/>
        </td>
        
      </tr>
      <tr class=trcolor>
        <td height="25">
          <b>备注信息:</b>
        </td>
        <td colspan="5">
          <bean:write name="reqinfo" property="remark"/>
        </td>
      </tr>
      <logic:notEqual value="待审批" name="reqinfo" property="auditresult">
        <tr class=trcolor>
          <td height="25">
            <b>审 批 人:</b>
          </td>
          <td colspan="5">
            <bean:write name="reqinfo" property="auditusername"/>
          </td>
        </tr>
        <tr class=trcolor>
          <td height="25">
            <b>审批日期:</b>
          </td>
          <td colspan="5">
            <bean:write name="reqinfo" property="audittime"/>
          </td>
        </tr>
        <tr class=trcolor>
          <td height="25">
            <b>审批结果:</b>
          </td>
          <td colspan="5">
            <bean:write name="reqinfo" property="auditresult"/>
          </td>
        </tr>
        <tr class=trcolor>
          <td height="25">
            <b>审批备注:</b>
          </td>
          <td colspan="5">
            <bean:write name="reqinfo" property="auditremark"/>
          </td>
        </tr>
      </logic:notEqual>
      <tr class=trcolor>
        <td colspan="6" height="20" >
          <hr/>
        </td>
      </tr>
      
       <tr class=trcolor>
        <td colspan="6" >
          	<table border="0"  align="center" width="100%">
		      <tr>
		        <td colspan="8" valign="bottom">
		          <b>            该申请单所申请的材料:(
		            <bean:write name="reqinfo" property="auditresult"/>
		            )
		          </b>
		        </td>
		      </tr>
		      <tr>
		          <td>
		            <table id="queryID"  width="100%" border="1" align="center" cellpadding="3" cellspacing="0">
		      <tr>
		        <th  width="35%" class="thlist" align="center">材料名称</th>
		        <th  width="10%" class="thlist" align="center">计量单位</th>
		        <th  width="25%" class="thlist" align="center">规格型号</th>
		        <th  width="10%" class="thlist" align="center">申请数量</th>
		        <logic:notEqual value="待审批" name="reqinfo" property="auditresult">
		          <th width="10% " class="thlist" align="center">审批数量</th>
		          <th width="10%" class="thlist" align="center">入库数量</th>
		        </logic:notEqual>
		      </tr>
		      <logic:present name="reqpartinfo">
		        <logic:iterate id="reqid" name="reqpartinfo">
		          <tr id="<bean:write name="reqid" property="id"/>">
		            <td>
		                <bean:write name="reqid" property="name"/> -- <bean:write name="reqid" property="type"/>
		            </td>
		            <td>
		                <bean:write name="reqid" property="unit"/>
		            </td>
		            <td>
		                <bean:write name="reqid" property="type"/>
		            </td>
		            <td>
		               <bean:write name="reqid" property="renumber"/>
		            </td>
		            <logic:notEqual value="待审批" name="reqinfo" property="auditresult">
		              <logic:equal value="不予审批" name="reqinfo" property="auditresult">
		                <td>0</td>
		                <td>0</td>
		              </logic:equal>
		              <logic:notEqual value="不予审批" name="reqinfo" property="auditresult">
		                <td>
		                  <bean:write name="reqid" property="audnumber"/>
		                </td>
		                <td>
		                    <bean:write name="reqid" property="stocknumber"/>
		                </td>
		              </logic:notEqual>
		            </logic:notEqual>
		          </tr>
		        </logic:iterate>
		      </logic:present>
		    </table>
		        </td>
		      </tr>
		    </table>
		        </td>
		      </tr>
    </table>
    

    <p align="center">
      <input type="button" class="button" value="返回" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
      <html:button property="action" styleClass="button" onclick="exportReq()">Excel报表</html:button>
    </p>
  </html:form>
</logic:equal>
  <!--修改申请单-->
<logic:equal value="4" scope="session" name="type">
  <apptag:checkpower thirdmould="80104" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="修改维护材料申请单"/>
  <logic:present name="baseinfo">
    <logic:iterate id="baseinfoId" name="baseinfo">
<script type="" language="javascript">
                    initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>","<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>");
                </script>
    </logic:iterate>
  </logic:present>
  <html:form action="/PartRequisitionAction?method=upRequisition" styleId="UpForm">
    <input type="hidden" name="reid" value="<bean:write name="reqinfo" property="reid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <table class="tabout" align="center" width="90%" border="0">
      <tr class=trcolor>
        <td width="60" height="25">
          <b>单位名称:</b>
        </td>
        <td align="left" width="120">
          <bean:write name="reqinfo" property="contractorname"/>
        </td>
        <td align="right" width="50">
          <b>申请人:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="reqinfo" property="username"/>
        </td>
        <td width="70" align="right">
          <b>申请时间:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="reqinfo" property="time"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td height="25" width="80">
          <b>单位名称:</b>
        </td>
        <td align="left" width="120">
          <bean:write name="deptname"/>
        </td>
        <td align="right" width="50">
          <b>修改人:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="username"/>
        </td>
        <td align="right" width="70">
          <b>修改时间:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="date"/>
        </td>
      </tr>
      <tr class=trcolor>
      	<td align="left" width="80"><b>目标处理人:</b></td>
      	<td colspan="5">  
      
      	 <bean:define id="tem1" name="reqinfo" property="targetman" type="java.lang.String"/>
              <select name="targetman" style="width:100" class="inputtext">
              
                <logic:iterate id="targetmanId" name="ltargetman">
                <logic:equal value="<%=tem1%>" name="targetmanId" property="userid">
                  <option value="<bean:write name="targetmanId" property="userid" />" selected="selected">
                    <bean:write name="targetmanId" property="username"/>
                  </option>
                </logic:equal>  
                <logic:notEqual value="<%=tem1%>" name="targetmanId" property="userid">
                	<option value="<bean:write name="targetmanId" property="userid"/>">
                    <bean:write name="targetmanId" property="username"/>
                  	</option>
                </logic:notEqual>
                </logic:iterate>
              </select>
      	</td>
      </tr>
     
      <tr class=trcolor>
        <td height="25">
          <b>申请事由:</b>
        </td>
        <td colspan="5">
          <input type="text" name="reason" Class="inputtext" style="width:88%;" maxlength="24" value="<bean:write name="reqinfo" property="reason"/>"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td height="25">
          <b>备注信息:</b>
        </td>
        <td colspan="5">
          <input type="text" name="remark" Class="inputtextarea" style="width:88%;" onkeydown="textCounter(this,254)" onkeyup="textCounter(this,254)"  value="<bean:write name="reqinfo" property="remark"/>"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6" height="20" valign="top">
          <hr/>
          <br>
          请选择材料:
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6" valign="top">
         <table width="100%" id="queryID" width="600" border="1" align="center" cellpadding="3" cellspacing="0">
	      <tr>
	        <th width="40%" class="thlist" align="center">材料名称</th>
	        <th width="10%" class="thlist" align="center">计量单位</th>
	        <th width="32%" class="thlist" align="center">规格型号</th>
	        <th width="10%" class="thlist" align="center">申请数量</th>
	        <th width="8%" class="thlist" align="center">操作</th>
	      </tr>
	      <logic:present name="reqpartinfo">
	        <logic:iterate id="reqid" name="reqpartinfo">
	          <tr id="<bean:write name="reqid" property="id"/>">
	            <td >
	              <bean:define id="tem1" name="reqid" property="id" type="java.lang.String"/>
	              <select class="selecttext"  name="id" style="width=250" id="sele<bean:write name="reqid" property="id"/>" onchange="upselectOncheng(id)">
	                  <logic:iterate id="baseinfoId" name="baseinfo">
	                  <logic:equal name="baseinfoId" property="id" value="<%=tem1%>">
	                    <option selected="selected"  value="<bean:write name="baseinfoId" property="id"/>">
	                      <bean:write name="baseinfoId" property="name"/> -- <bean:write name="baseinfoId" property="type"/>
	                    </option>
	                  </logic:equal>
	                  <logic:notEqual value="<%=tem1%>" name="baseinfoId" property="id">
	                    <option value="<bean:write name="baseinfoId" property="id"/>">
	                      <bean:write name="baseinfoId" property="name"/> -- <bean:write name="baseinfoId" property="type"/>
	                    </option>
	                  </logic:notEqual>
	                </logic:iterate>
	              </select>
	            </td>
	
	            <td>
	             <input   readonly="readonly" class="inputtext" size="8" value="<bean:write name="reqid" property="unit"/>"/>
	            </td>
	            <td>
	            <input   readonly="readonly" class="inputtext" size="32" value=" <bean:write name="reqid" property="type"/>"/>
	            </td>
	            <td>
	              <input class="inputtext" name="renumber" id="reu<bean:write name="reqid" property="id"/>" maxlength="6" size="8" onblur="valiDigit(id)" value="<bean:write name="reqid" property="renumber"/>"/>
	            </td>
	            <td>
	              <input  type="button" value="删除" id="b<bean:write name="reqid" property="id"/>" onclick="upDelOnClick(id)"/>
	            </td>
	          </tr>
	        </logic:iterate>
	      </logic:present>
	    </table>
        </td>
      </tr>
    </table>
    
    <p align="center">
      <html:button property="action" styleClass="button" onclick="addRow()">添加新材料</html:button>
      <html:button property="action" styleClass="button" onclick="toUpSub()" title="一旦提交,该申请单的申请人就更改为修改人!">提交修改</html:button>
      <input type="button" value="返回" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
    </p>
  </html:form>
</logic:equal>
  <!--填写申请单-->
<logic:equal value="2" scope="session" name="type">
  <link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
  <apptag:checkpower thirdmould="80101" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <logic:present name="baseinfo">
    <logic:iterate id="baseinfoId" name="baseinfo">
<script type="" language="javascript">
                    initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>","<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>");
                </script>
    </logic:iterate>
  </logic:present>
  <div id="partDivId">
    <br/>
    <template:titile value="填写维护材料申请单"/>
    <html:form action="/PartRequisitionAction?method=addRequisition" styleId="addForm">
      <input type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
      <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
      <table align="center" width="90%" border="0" class="tabout">
        <tr class=trcolor>
          <td  width="8%">
            <b>申 请 人:</b>
          </td>
          <td align="left" width="10%">
            <bean:write name="username"/>
          </td>
          <td width="10%" align="right">
            <b>申请时间:</b>
          </td>
          <td align="left" width="10%">
            <bean:write name="date"/>
          </td>
          <logic:equal value="send" name="isSendSm">
            <td width="12%" align="right">
              <b>目标处理人:</b>
            </td>
            <td align="left">
              <select name="targetman" style="width:160" class="inputtext">
                <logic:iterate id="targetmanId" name="ltargetman">
                  <option value="<bean:write name="targetmanId" property="userid"/>">
                    <bean:write name="targetmanId" property="username"/>
                  </option>
                </logic:iterate>
              </select>
            </td>
          </logic:equal>
          <logic:notEqual value="send" name="isSendSm">
            <td>            </td>
          </logic:notEqual>
        </tr>
        <tr class=trcolor>
          <td height="25" width="8%">
            <b>申请事由:</b>
          </td>
          <td colspan="5" >
            <html:text property="reason" styleClass="inputtext" style="width:85%;" maxlength="24" value="请填写申请材料的原因或者材料的用途.">            </html:text>
          </td>
        </tr>
        <tr class=trcolor>
          <td height="25">
            <b>备注信息:</b>
          </td>
          <td colspan="5">
            <html:textarea property="remark" styleClass="inputtextarea" style="width:85%;" onkeydown="textCounter(this,254)" onkeyup="textCounter(this,254)" value="请填写申请材料其他说明信息.">            </html:textarea>
          </td>
        </tr>
        <tr class=trcolor>
          <td colspan="6">
            <hr/>
            <br/>
            请选择材料:
          </td>
        </tr>
        <tr class=trcolor>
        	<td colspan="6">
        		 <table width="100%" id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0">
			        <tr>
			          <th width="40%" class="thlist" align="center">材料名称</th>
			          <th width="12%" class="thlist" align="center">计量单位</th>
			          <th width="30%" class="thlist" align="center">规格型号</th>
			          <th width="10%" class="thlist" align="center">申请数量</th>
			          <th width="8%" class="thlist" align="center">操作</th>
			        </tr>
      </table>
        	</td>
        </tr>
      </table>
     
      <p align="center">
        <html:button property="action" styleClass="button" onclick="addRow()">添加新材料</html:button>
        <html:button property="action" styleClass="button" styleId="addsub" onclick="toAddSub()">提交申请单</html:button>
        <html:button property="action" styleClass="button" styleId="upId" onclick="showupload()">导入申请单</html:button>
        <html:button property="action" styleClass="button2" onclick="downloadFile()">申请单模板下载</html:button>
        <!--<html:button property="action" styleClass="button" onclick="addGoBack()">返回</html:button>  -->
      </p>
    </html:form>
  </div>
  <div id="upDivId" style="display:none">
    <html:form styleId="upform" action="/PartRequisitionAction?method=upLoadShow" method="post" enctype="multipart/form-data">
      <table align="center" border="0" width="600" height="100%">
        <tr >
          <td valign="top" height="100%">
            <table align="center" border="0">
              <tr>
                <td align="left" height="50">
                  <br/>
                  <br/>
                  <br/>
                  <br/>
                  <br/>
                  <br/>
                  <br/>
                  <b>请选择要导入的Excel文件:</b>
                  <br/>
                </td>
              </tr>
              <tr>
                <td>
                  <html:file property="file" style="width:300px" styleClass="inputtext"/>
                </td>
              </tr>
              <tr>
                <td align="center" valign="middle" height="60">
                  <html:button styleClass="button" value="导入申请单" property="sub" onclick="javascript:upform.submit()">提交</html:button>
                  <html:button value="取消导入" styleClass="button" property="action" onclick="noupload()"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </html:form>
  </div>
</logic:equal>
  <!--导入文件后显示-->
<logic:equal value="upshow" scope="session" name="type">
  <apptag:checkpower thirdmould="80104" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="确认维护材料申请单"/>
  <logic:present name="baseinfo">
    <logic:iterate id="baseinfoId" name="baseinfo">
<script type="" language="javascript">
                    initArray("<bean:write name="baseinfoId" property="id"/>","<bean:write name="baseinfoId" property="name"/>","<bean:write name="baseinfoId" property="unit"/>","<bean:write name="baseinfoId" property="type"/>");
                </script>
    </logic:iterate>
  </logic:present>
  <html:form action="/PartRequisitionAction?method=addRequisition" styleId="addForm">
    <input type="hidden" name="contractorid" value="<bean:write name="deptid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <table align="center" width="90%" border="0" class="tabout">
      <tr class=trcolor>
        <td width="8%" align="right">
          <b>申 请 人:</b>
        </td>
        <td width="10%">
          <bean:write name="username"/>
        </td>
        <td width="10%" align="right">
          <b>申请时间:</b>
        </td>
        <td align="left" width="10%">
          <bean:write name="date"/>
        </td>
        <logic:equal value="send" name="isSendSm">
          <td align="right" width="12%">
            <b>目标处理人:</b>
          </td>
          <td align="left">
            <select name="targetman" style="width:100" class="inputtext">
              <logic:iterate id="targetmanId" name="ltargetman">
                <option value="<bean:write name="targetmanId" property="userid"/>">
                  <bean:write name="targetmanId" property="username"/>
                </option>
              </logic:iterate>
            </select>
          </td>
        </logic:equal>
        <logic:notEqual value="send" name="isSendSm">
          <td >          </td>
        </logic:notEqual>
      </tr>
      <tr class=trcolor>
        <td height="25" align="right">
          <b>申请事由:</b>
        </td>
        <td colspan="5"  >
          <html:text property="reason" styleClass="inputtext" style="width:85%;" maxlength="24" value="请填写申请材料的原因或者材料的用途.">          </html:text>
        </td>
      </tr>
      <tr class=trcolor>
        <td height="25"  align="right">
          <b>备注信息:</b>
        </td>
        <td colspan="5">
          <html:textarea property="remark" styleClass="inputtextarea" style="width:85%;" onkeydown="textCounter(this,254)" onkeyup="textCounter(this,254)" value="请填写申请材料其他说明信息.">          </html:textarea>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6">
          <hr/>
          <br/>
          请选择材料:
        </td>
      </tr>
 
    <tr class=trcolor>
    <td colspan="6">
    <table id="queryID"  width="100%" border="1" align="center" cellpadding="3" cellspacing="0">
      <tr>
        <th width="40%" class="thlist" align="center">材料名称</th>
        <th width="12%" class="thlist" align="center">计量单位</th>
        <th width="30%" class="thlist" align="center">规格型号</th>
        <th width="10%" class="thlist" align="center">申请数量</th>
        <th width="8%" class="thlist" align="center">操作</th>
      </tr>
      <logic:present name="reqpartinfo">
        <logic:iterate id="UpLoadId" name="reqpartinfo">
          <tr id="<bean:write name="UpLoadId" property="id"/>">
            <td>
              <bean:define id="tem1" name="UpLoadId" property="id" type="java.lang.String"/>
              <select name="id"   style="width:250" class="selecttext" id="sele<bean:write name="UpLoadId" property="id"/>" onchange="upselectOncheng(id)">
                <logic:iterate id="baseinfoId" name="baseinfo">
                  <logic:equal name="baseinfoId" property="id" value="<%=tem1%>">
                    <option selected="selected" value="<bean:write name="baseinfoId" property="id"/>">
                      <bean:write name="baseinfoId" property="name"/> -- <bean:write name="baseinfoId" property="type"/>
                    </option>
                  </logic:equal>
                  <logic:notEqual value="<%=tem1%>" name="baseinfoId" property="id">
                    <option value="<bean:write name="baseinfoId" property="id"/>">
                      <bean:write name="baseinfoId" property="name"/>-- <bean:write name="baseinfoId" property="type"/>
                    </option>
                  </logic:notEqual>
                </logic:iterate>
              </select>
            </td>

            <td>
                <input  size="8" class="inputtext"  value=" <bean:write name="UpLoadId" property="unit"/>"/>

            </td>
            <td>
             <input  size="32" class="inputtext"  value=" <bean:write name="UpLoadId" property="type"/>"/>
            </td>
            <td>
              <input id="re<bean:write name="UpLoadId" property="id"/>" class="inputtext" name="renumber" maxlength="6" onblur="valiDigit(id)" size="8" value="<bean:write name="UpLoadId" property="renumber"/>"/>
            </td>
            <td>
              <input type="button" value="删除" id="b<bean:write name="UpLoadId" property="id"/>" onclick="upDelOnClick(id)"/>
            </td>
          </tr>
        </logic:iterate>
      </logic:present>
    </table>
 </td>
</tr>   
   </table>
    <p align="center">
      <html:button property="action" styleClass="button" onclick="addRow()">添加新材料</html:button>
      <html:button property="action" styleClass="button" styleId="addsub" onclick="toAddSub()">提交申请单</html:button>
    </p>
  </html:form>
</logic:equal>
  <!--显示申请单-->
<logic:equal value="1" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <br/>
  <template:titile value="维护材料申请单一览表"/>
  <display:table name="sessionScope.reqinfo" requestURI="${ctx}/PartRequisitionAction.do?method=queryShow" id="currentRowObject" pagesize="18">
  	<%
      	BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      	String id = "";
      	String reason = "";
      	String reasonTitle = "";
      	String auditresult = "";
      	if(object != null) {
      		id = (String) object.get("reid");
      		reasonTitle = (String)object.get("reason");
      		if(reasonTitle != null && reasonTitle.length() > 15) {
      			reason = reasonTitle.substring(0,15) + "...";
      		} else {
      			reason = reasonTitle;
      		}
      		
      		auditresult=(String) object.get("auditresult");
      	}      	
    %>
    <display:column media="html" title="申请单流水号" style="width:100px">
        <%if(id != null) { %>
    		<a href="javascript:toGetForm('<%=id%>')"><%=id%></a>
    	<%} %>
    </display:column>    
    <display:column media="html" title="申请原因">
        <%if(reason != null) { %>
    		<a href="javascript:toGetForm('<%=id%>')" title="<%=reasonTitle %>"><%=reason%></a>
    	<%} %>
    </display:column>
    <display:column property="contractorname" title="申请单位" maxLength="20" style="align:center"/>
    <display:column property="username" title="申 请 人" maxLength="20" style="align:center"/>
    <display:column property="time" title="申请时间" maxLength="20" style="align:center"/>
    <display:column media="html" title="审批状态">    
  		<%if("待审批".equals(auditresult)) { %>    	
    		<font color="#0000CD" ><%=auditresult %></font>
    	<%} else if("通过审批".equals(auditresult)) { %>
    		<font color="#008000" ><%=auditresult %></font>
    	<%} else if("不予审批".equals(auditresult)) {%>
    		<font color="red" ><%=auditresult %></font>
    	<%} else if("待审核".equals(auditresult)) {%>
    		<font color="#FFA500" ><%=auditresult %></font>
    	<%} else {%>
    		<%=auditresult %>
    	<%} %>
    </display:column>
    <display:column media="html" title="操作" headerClass="subject" class="subject" style="width:100px">  
      <apptag:checkpower thirdmould="80104" ishead="0">
      	<%
        
        if("待审批".equals(auditresult)) {
      	%>
        <a href="javascript:toUpForm('<%=id%>','<%=auditresult%>')">修改</a> | 
       
        <%} else {%>
        	--
        <%} %>
    </apptag:checkpower>
    <apptag:checkpower thirdmould="80105" ishead="0">     
      	<%
        	if("待审批".equals(auditresult)) {
      	%>
        <a href="javascript:toDelForm('<%=id%>','<%=auditresult%>')">删除</a>
       
        <%} %>
    </apptag:checkpower>
  </display:column>      
  </display:table>
   <logic:notEmpty name="reqinfo">
          <html:link action="/PartRequisitionAction.do?method=exportRequisitonResult">导出为Excel文件</html:link><br>
   </logic:notEmpty>
   <logic:equal value="query" name="queryApply">
  		 <div align="center"><html:button property="action" styleClass="button" onclick="goApplyquery()" >返回</html:button></div>
   </logic:equal>
 
 
</logic:equal>


  <!--查询页面-->
<logic:equal value="3" scope="session" name="type">
  <apptag:checkpower thirdmould="80103" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="条件查找维护材料申请单"/>
  <html:form action="/PartRequisitionAction?method=queryExec" styleId="queryForm2">
    <template:formTable namewidth="200" contentwidth="200">
      <template:formTr name="审批状态">
        <select name="auditresult" class="inputtext" style="width:180px">
          <option value="">选择所有状态</option>
          <option value="待审批">待 审 批</option>
          <option value="通过审批">通过审批</option>
          <option value="待审核">待 审 核</option>
          <option value="不予审批">不予审批</option>
        </select>
      </template:formTr>
      <template:formTr name="申 请 人">
        <input type="text" name="userid" class="inputtext" style="width:180px"/>
      </template:formTr>
      <template:formTr name="申请原因">
        <input type="text" name="reason" style="width:180px" class="inputtext">
      </template:formTr>
      <template:formTr name="开始时间">
        <input id="begin" type="text" name="begintime" readonly="readonly" class="inputtext" style="width:150"/>
        <INPUT TYPE='BUTTON' VALUE='日期' ID='btn' onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';">
      </template:formTr>
      <template:formTr name="结束时间">
        <input id="end" type="text" name="endtime" readonly="readonly" class="inputtext" style="width:150"/>
        <INPUT TYPE='BUTTON' VALUE='日期' ID='btn' onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';">
      </template:formTr>
      <template:formSubmit>
        <td>
          <html:submit property="action" styleClass="button">查找</html:submit>
        </td>
        <td>
          <html:reset property="action" styleClass="button">重置</html:reset>
        </td>
      </template:formSubmit>
    </template:formTable>
  </html:form>
</logic:equal>
<iframe id="hiddenIframe" style="display:none"></iframe>

<!--//////////////////////////////////审批部分///////////////////////////////////////////-->
  <!--显示待审批的申请单-->
<logic:equal value="audit2" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <br/>
  <template:titile value="待审批申请单一览表"/>
  <display:table name="sessionScope.reqinfo" requestURI="${ctx}/PartRequisitionAction.do?method=doAuditShow" id="currentRowObject" pagesize="18">
  	<%
      BasicDynaBean objectA1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String idA1 = "";
      String audresult = "";
      String reason = "";
      String auditresult = "";
      String reasonTitle = "";
      if(objectA1 != null) {
      	idA1 = (String) objectA1.get("reid");
      	audresult = (String) objectA1.get("auditresult");
      	
      	reasonTitle = (String)objectA1.get("reason");
    	if(reasonTitle != null && reasonTitle.length() > 15) {
    		reason = reasonTitle.substring(0,15) + "...";
    	} else {
    		reason = reasonTitle;
    	}
      }      
    %>
  	<display:column media="html" title="申请单流水号" style="width:100px">    
  		<%if(idA1 != null) { %>
    		<a href="javascript:toAuditForm('<%=idA1%>','<%=audresult%>')"><%=idA1 %></a>
    	<%} %>
    </display:column>
    <display:column media="html" title="申请原因">    
  		<%if(reason != null) { %>
    		<a href="javascript:toAuditForm('<%=idA1%>','<%=audresult%>')" title="<%=reasonTitle %>"><%=reason %></a>
    	<%} %>
    </display:column>  	
    <display:column property="contractorname" title="申请单位" maxLength="20" style="align:center"/>
    <display:column property="username" title="申 请 人" maxLength="20" style="align:center"/>
    <display:column property="time" title="申请时间" maxLength="20" style="align:center"/>    
    <display:column media="html" title="审批状态">    
  		<%if("待审批".equals(audresult)) { %>    	
    		<font color="#0000CD" ><%=audresult %></font>
    	<%} else if("通过审批".equals(audresult)) { %>
    		<font color="#008000" ><%=audresult %></font>
    	<%} else if("不予审批".equals(audresult)) {%>
    		<font color="red" ><%=audresult %></font>
    	<%} else if("待审核".equals(audresult)) {%>
    		<font color="#FFA500" ><%=audresult %></font>
    	<%} else {%>
    		<%=auditresult %>
    	<%} %>
    </display:column>

  </display:table>
</logic:equal>

  <!--审批单个申请单-->
<logic:equal value="audit20" scope="session" name="type">

  <br/>
  <template:titile value="审批材料申请单"/>
  <html:form action="/PartRequisitionAction?method=auditRequisition" styleId="addForm">
    <input type="hidden" name="reid" value="<bean:write name="reqinfo" property="reid"/>"/>
    <input type="hidden" name="useid" value="<bean:write name="reqinfo" property="useid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <table class="tabout" align="center" width="90%" border="0">
      <tr class=trcolor>
        <td width="8%">
          <b>单位名称:</b>
        </td>
        <td align="left" width="25%">
          <bean:write name="reqinfo" property="contractorname"/>
        </td>
        <td align="right" width="8%">
          <b>申请人:</b>
        </td>
        <td align="left" width="15%">
          <bean:write name="reqinfo" property="username"/>
        </td>
        <td width="10%" align="right">
          <b>申请时间:</b>
        </td>
        <td align="left" >
          <bean:write name="reqinfo" property="time"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td>
          <b>申请事由:</b>
        </td>
        <td colspan="5">
          <bean:write name="reqinfo" property="reason"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td>
          <b>申请备注:</b>
        </td>
        <td colspan="5">
          <bean:write name="reqinfo" property="remark"/>
        </td>
        
      </tr>
      <tr class=trcolor>
        <td colspan="6" valign="bottom">
         <br/>
          <br/>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6" valign="bottom">
          <b>            该申请单所申请的材料:(
            <bean:write name="reqinfo" property="auditresult"/>
            )
          </b>
          <br/>
        </td>
      </tr>
       
      <tr class=trcolor>
        <td colspan="6" valign="bottom">
         	<table width="100%" id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0">
		      <tr>
		        <th width="40%" class="thlist" align="center">材料名称</th>
		        <th width="10%" class="thlist" align="center">计量单位</th>
		        <th width="30%" class="thlist" align="center">规格型号</th>
		        <th width="10%" class="thlist" align="center">申请数量</th>
		        <th width="10%" class="thlist" align="center">审批数量</th>
		      </tr>
		      <logic:present name="reqpartinfo">
		        <logic:iterate id="reqid" name="reqpartinfo">
		          <tr id="<bean:write name="reqid" property="id"/>">
		            <td>
		              <input type="hidden" name="id" value="<bean:write name="reqid" property="id"/>"/>
		              <bean:write name="reqid" property="name"/> -- <bean:write name="reqid" property="type"/>
		            </td>
		            <td>
		             <bean:write name="reqid" property="unit"/>
		            </td>
		            <td>
		              <bean:write name="reqid" property="type"/>
		            </td>
		            <td>
		              <bean:write name="reqid" property="renumber"/> 
		            </td>
		            <td>
		              <input id="aduditnub<bean:write name="reqid" property="id"/>" onblur="valiDigit(id)" size="8" maxlength="6" align="right" class="inputtext"  name="audnumber" value="<bean:write name="reqid" property="renumber"/>"/>
		            </td>
		          </tr>
		        </logic:iterate>
		      </logic:present>
		    </table>
		    <br/>
    		<br/>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6" valign="bottom">
         	<table class="tabout" width="100%" align="center" width="100%" border="0">
		      <tr class=trcolor>
		        <td height="30" width="60">
		          <b>审批结果:</b>
		        </td>
		        <td colspan="5">
		          <select name="auditresult" class="selecttext">
		            <option value="通过审批">通过审批</option>
		            <option value="不予审批">不予审批</option>
		          </select>
		        </td>
		      </tr>
		      <tr class=trcolor>
		        <td height="30" width="60">
		          <b>审批备注:</b>
		        </td>
		        <td colspan="5">
		          <textarea rows="5" name="auditremark" maxlength="512" style="width:70%" class="inputtextarea" value="请填写审批备注" ></textarea>
		        </td>
		      </tr>
		      <tr class=trcolor>
		        <td width="60" height="30">
		          <b>审批单位:</b>
		        </td>
		        <td align="left" width="120">
		          <bean:write name="deptname"/>
		        </td>
		        <td align="right" width="50">
		          <b>审批人:</b>
		        </td>
		        <td align="left" width="100">
		          <bean:write name="username"/>
		        </td>
		        <td width="70" align="right">
		          <b>审批时间:</b>
		        </td>
		        <td align="left" width="100">
		          <bean:write name="date"/>
		        </td>
		      </tr>
		      
		    </table>
        </td>
      </tr>
    </table>
   <p align="center">
   		<html:submit property="action" styleClass="button">提交审批</html:submit>
		<input type="button" value="返&nbsp;&nbsp;回" Class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
   
   </p>
    
    
    
  </html:form>
</logic:equal>
  <!--审核单个申请单-->
<logic:equal value="audit200" scope="session" name="type">
  
  <br/>
  <template:titile value="审核材料申请单"/>
  <html:form action="/PartRequisitionAction?method=doReAuditRequisition" styleId="addForm">
    <input type="hidden" name="reid" value="<bean:write name="reqinfo" property="reid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <table align="center" width="90%" border="0">
      <tr>
        <td width="10%%">
          <b>单位名称:</b>
        </td>
        <td align="left" width="15%">
          <bean:write name="reqinfo" property="contractorname"/>
        </td>
        <td align="right" width="10%">
          <b>申请人:</b>
        </td>
        <td align="left" width="15%">
          <bean:write name="reqinfo" property="username"/>
        </td>
        <td width="10%" align="right">
          <b>申请时间:</b>
        </td>
        <td align="left" width="100">
          <bean:write name="reqinfo" property="time"/>
        </td>
      </tr>
      <tr>
        <td>
          <b>申请事由:</b>
        </td>
        <td colspan="5">
          <bean:write name="reqinfo" property="reason"/>
        </td>
      </tr>
      <tr>
        <td>
          <b>申请备注:</b>
        </td>
        <td colspan="5">
          <bean:write name="reqinfo" property="remark"/>
        </td>
      </tr>
      <tr height="10">
        <td colspan="6">
         
        </td>
      </tr>
      <tr>
        <td colspan="6" valign="bottom">
          <b>该申请单下列材料没有全部入库,请予以审核后确认:</b>
        </td>
      </tr>
    </table>
    <table id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0" width="90%">
      <tr>
        <th width="250" class="thlist" align="center">材料名称</th>
        <th width="60" class="thlist" align="center">计量单位</th>
        <th width="200" class="thlist" align="center">规格型号</th>
        <th width="60" class="thlist" align="center">申请数量</th>
        <th width="60" class="thlist" align="center">审批数量</th>
        <th width="60" class="thlist" align="center">入库数量</th>
      </tr>
      <logic:present name="reqpartinfo">
        <logic:iterate id="reqid" name="reqpartinfo">
          <tr id="<bean:write name="reqid" property="id"/>">
            <td>
              <input type="hidden" name="id" value="<bean:write name="reqid" property="id"/>"/>
              <bean:write name="reqid" property="name"/> -- <bean:write name="reqid" property="type"/>
            </td>
            <td>
              <bean:write name="reqid" property="unit"/>
            </td>
            <td>
              <bean:write name="reqid" property="type"/>
            </td>
            <td>
              <bean:write name="reqid" property="renumber"/>
            </td>
            <td>
              <font color="blue">
              <bean:write name="reqid" property="audnumber"/>
              </font>
            </td>
            <td>
              <font color="red">
              <bean:write name="reqid" property="stocknumber"/>
              </font>
            </td>
          </tr>
        </logic:iterate>
      </logic:present>
    </table>
    <br/>
    <hr/>
    <br/>
    <table align="center" width="90%" border="0">
      <tr>
        <td width="10%" height="30">
          <b>审核单位:</b>
        </td>
        <td align="left" width="15%">
          <bean:write name="deptname"/>
        </td>
        <td align="right" width="10%">
          <b>审核人:</b>
        </td>
        <td align="left" width="15%">
          <bean:write name="username"/>
        </td>
        <td width="10%" align="right">
          <b>审核时间:</b>
        </td>
        <td align="left" >
          <bean:write name="date"/>
        </td>
      </tr>
      <tr>
        <td colspan="6" height="40" valign="top">
          <template:formSubmit>
            <td>              &nbsp;
              <html:submit property="action" styleClass="button" title="提交审核,意味着将审批数量改为入库数量！">提交审核</html:submit>
              <input type="button" value="返&nbsp;&nbsp;回" Class="button" onclick="javascript:backToAuditShow()"/>
            </td>
          </template:formSubmit>
        </td>
      </tr>
    </table>
  </html:form>
</logic:equal>

  <!--显示所有审批单-->
<logic:equal value="audit1" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <apptag:checkpower thirdmould="80201" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="维护材料审批单一览表"/>
  <display:table name="sessionScope.reqinfo" requestURI="${ctx}/PartRequisitionAction.do?method=showAllAudit" id="currentRowObject" pagesize="18">
  	 <%
      BasicDynaBean objectb1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String idb1 = "";
      String reason = "";
      String auditresult = "";
      String reasonTitle = "";
      if(objectb1 != null) {
      	 idb1 = (String) objectb1.get("reid");
      	
      	 reasonTitle = (String)objectb1.get("reason");
    	 if(reasonTitle != null && reasonTitle.length() > 15) {
    		reason = reasonTitle.substring(0,15) + "...";
    	 } else {
    		reason = reasonTitle;
    	 }
      	 auditresult = (String)objectb1.get("auditresult");
      }
    %>
  	<display:column media="html" title="申请单流水号" style="width:100px">   
  	  <%if(idb1 != null ) { %>
      	<a href="javascript:toShowOneAudit('<%=idb1%>')"><%=idb1 %></a>
      <%} %>
    </display:column>
    <display:column media="html" title="申请原因">   
  	  <%if(reason != null ) { %>
      	<a href="javascript:toShowOneAudit('<%=idb1%>')" title="<%=reasonTitle %>"><%=reason %></a>
      <%} %>
    </display:column>
    <display:column property="contractorname" title="申请单位" maxLength="10"/>    
    <display:column property="username" title="审 批 人" maxLength="8" style="align:center"/>
    <display:column property="time" title="审批时间" maxLength="20" style="align:center"/>
   
    <display:column media="html" title="审批结果">    
  		<%if("待审批".equals(auditresult)) { %>    	
    		<font color="#0000CD" ><%=auditresult %></font>
    	<%} else if("通过审批".equals(auditresult)) { %>
    		<font color="#008000" ><%=auditresult %></font>
    	<%} else if("不予审批".equals(auditresult)) {%>
    		<font color="red" ><%=auditresult %></font>
    	<%} else if("待审核".equals(auditresult)) {%>
    		<font color="#FFA500" ><%=auditresult %></font>
    	<%} else {%>
    		<%=auditresult %>
    	<%} %>
    </display:column>
    
  </display:table>
  <logic:notEmpty name="reqinfo">
  	<html:link action="/PartRequisitionAction.do?method=exportRequisiton">导出为Excel文件</html:link>
  </logic:notEmpty>
  <logic:equal value="query" name="audit1">
  	 <div align="center"><html:button property="action" styleClass="button" onclick="goquery()" >返回</html:button></div>
  </logic:equal>
  
</logic:equal>

  <!--查看一个审批单的详细信息-->
<logic:equal value="audit10" scope="session" name="type">
  <apptag:checkpower thirdmould="80201" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="材料申请单审批信息"/>
  <html:form action="/PartRequisitionAction?method=auditRequisition" styleId="addForm">
    <input type="hidden" name="reid" value="<bean:write name="reqinfo" property="reid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <table class="tabout" align="center" width="90%" border="0">
      <tr class=trcolor>
        <td>
          <b>申请事由:</b>
        </td>
        <td colspan="5">
          <bean:write name="reqinfo" property="reason"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td>
          <b>申请备注:</b>
        </td>
        <td colspan="5">
          <bean:write name="reqinfo" property="remark"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td width="8%">
          <b>单位名称:</b>
        </td>
        <td align="left" width="25%">
          <bean:write name="reqinfo" property="contractorname"/>
        </td>
        <td align="right" width="8%">
          <b>申请人:</b>
        </td>
        <td align="left" width="15%">
          <bean:write name="reqinfo" property="username"/>
        </td>
        <td width="10%" align="right">
          <b>申请时间:</b>
        </td>
        <td align="left" >
          <bean:write name="reqinfo" property="time"/>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6" valign="bottom">
          <br/><br/>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6" valign="bottom">
          <b>该申请单所申请的材料:</b>
        </td>
      </tr>
      <tr class=trcolor>
        <td colspan="6" valign="bottom">
         	<table width="100%" id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0">
		      <tr>
		        <th width="40%" class="thlist" align="center">材料名称</th>
		        <th width="10%" class="thlist" align="center">计量单位</th>
		        <th width="30%" class="thlist" align="center">规格型号</th>
		        <th width="10%" class="thlist" align="center">申请数量</th>
		        <th width="10%" class="thlist" align="center">审批数量</th>
		      </tr>
		      <logic:present name="reqpartinfo">
		        <logic:iterate id="reqid" name="reqpartinfo">
		          <tr id="<bean:write name="reqid" property="id"/>">
		            <td>
		              <input type="hidden" name="id" value="<bean:write name="reqid" property="id"/>"/>
		              <bean:write name="reqid" property="name"/> -- <bean:write name="reqid" property="type"/>
		            </td>
		            <td>
		              <bean:write name="reqid" property="unit"/>
		            </td>
		            <td>
		              <bean:write name="reqid" property="type"/>
		            </td>
		            <td>
		              <bean:write name="reqid" property="renumber"/>
		            </td>
		            <td>
		              <bean:write name="reqid" property="audnumber"/>
		            </td>
		          </tr>
		        </logic:iterate>
		      </logic:present>
		    </table>
        </td>
      </tr>
      
      <tr class=trcolor>
        <td colspan="6" valign="bottom">
           
		    <br/>
		    <hr/>
		    <br/>
        </td>
      </tr>
       <tr class=trcolor>
        <td colspan="6" valign="bottom">
           <logic:present name="auditinfo">
      <table class="tabout" align="center" width="100%" border="0">
        <tr class=trcolor>
          <td height="30">
            <b>审批结果:</b>
          </td>
          <td colspan="5">
            <bean:write name="auditinfo" property="auditresult"/>
          </td>
        </tr>
        <tr class=trcolor>
          <td height="30">
            <b>审批备注:</b>
          </td>
          <td colspan="5">
            <bean:write name="auditinfo" property="auditremark"/>
          </td>
        </tr>
        <tr class=trcolor>
          <td width="60" height="30">
            <b>审批单位:</b>
          </td>
          <td align="left" width="120">
            <bean:write name="auditinfo" property="deptname"/>
          </td>
          <td align="right" width="50">
            <b>审批人:</b>
          </td>
          <td align="left" width="100">
            <bean:write name="auditinfo" property="username"/>
          </td>
          <td width="70" align="right">
            <b>审批时间:</b>
          </td>
          <td align="left" width="100">
            <bean:write name="auditinfo" property="audittime"/>
          </td>
        </tr>
       
      </table>
    </logic:present>
        </td>
      </tr>
    </table>  
   <p align="center">
   <input type="button" class="button" value="返回" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">	
   </p>
  </html:form>
</logic:equal>
  <!--审批,条件查询页面-->
<logic:equal value="audit3" scope="session" name="type">
  <apptag:checkpower thirdmould="80203" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="按条件查找审批单"/>
  <html:form action="/PartRequisitionAction?method=queryExecForAudit" styleId="queryForm2">
    <template:formTable namewidth="200" contentwidth="200">

      <template:formTr name="审批状态">
        <select name="auditresult" class="inputtext" style="width:180px">
          <option value="">选择所有状态</option>
          <option value="通过审批">通过审批</option>
          <option value="不予审批">不予审批</option>
        </select>
      </template:formTr>
      <template:formTr name="申请单位">
        <select name="contractorid" style="width:180px" class="inputtext">
          <option value="">选择所有申请单位</option>
          <logic:present name="deptname">
            <logic:iterate id="deptnameId" name="deptname">
              <option value="<bean:write name="deptnameId"  property="contractorid"/>">
                <bean:write name="deptnameId" property="contractorname"/>
              </option>
            </logic:iterate>
          </logic:present>
        </select>
      </template:formTr>
       <template:formTr name="审 批 人">
        <input type="text" name="audituserid" class="inputtext" style="width:180px"/>
      </template:formTr>
      <template:formTr name="申请原因">
        <input type="text"  name="reason" style="width:180px" class="inputtext"/>
      </template:formTr>
      <template:formTr name="开始时间">
        <input id="begin" type="text" name="begintime" class="inputtext" style="width:150"/>
        <INPUT TYPE='BUTTON' VALUE='日期' ID='btn' onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';">
      </template:formTr>
      <template:formTr name="结束时间">
        <input id="end" type="text" class="inputtext" name="endtime" style="width:150"/>
        <INPUT TYPE='BUTTON' VALUE='日期' ID='btn' onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';">
      </template:formTr>
      <template:formSubmit>
        <td>
          <html:submit property="action" styleClass="button">查找</html:submit>
        </td>
        <td>
          <html:reset property="action" styleClass="button">取消</html:reset>
        </td>
      </template:formSubmit>
    </template:formTable>
  </html:form>
</logic:equal>
<logic:equal value="showall10" scope="session" name="type">
  <apptag:checkpower thirdmould="80109" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="维护材料申请单详细信息"/>
  <html:form action="/PartRequisitionAction?method=upRequisition" styleId="addForm">
    <input type="hidden" name="reid" value="<bean:write name="reqinfo" property="reid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <table align="center" width="90%" border="0" class="tabout">
      <tr class=trcolor>
        <td width="60" height="25">
          <b>单位名称:</b>
        <br></td>
        <td align="left" width="120">
          
        <br></td>
        <td align="right" width="50">
          <b>申请人:</b>
        <br></td>
        <td align="left" width="100">
          
        <br></td>
        <td width="70" align="right">
          <b>申请时间:</b>
        <br></td>
        <td align="left" width="100">
          
        <br></td>
      </tr>
      <tr class=trcolor>
        <td height="25">
          <b>申请事由:</b>
        <br></td>
        <td colspan="5">
          
        <br></td>
      </tr>
      <tr class=trcolor>
        <td height="25">
          <b>备注信息:</b>
        <br></td>
        <td colspan="5">
          
        <br></td>
      </tr>
      <logic:notEqual value="待审批" name="reqinfo" property="auditresult">
        <tr class=trcolor>
          <td height="25">
            <b>审 批 人:</b>
          <br></td>
          <td colspan="5">
            
          <br></td>
        </tr>
        <tr class=trcolor>
          <td height="25">
            <b>审批日期:</b>
          <br></td>
          <td colspan="5">
            
          <br></td>
        </tr>
        <tr class=trcolor>
          <td height="25">
            <b>审批结果:</b>
          <br></td>
          <td colspan="5">
            
          <br></td>
        </tr>
        <tr class=trcolor>
          <td height="25">
            <b>审批备注:</b>
          <br></td>
          <td colspan="5">
            
          <br></td>
        </tr>
      </logic:notEqual>
      <tr class=trcolor>
        <td colspan="6" height="40" valign="top">
          <hr/>
        <br></td>
      </tr>
      <tr class=trcolor>
        <td colspan="6"  valign="top">
         	    <table border="0"  align="center" width="100%">
		      <tr>
		        <td colspan="8" valign="bottom">
		          <b>            该申请单所申请的材料:(
		            
		            )
		          </b>
		        <br></td>
		      </tr>
		      <tr>
		          <td>
		            <table id="queryID"  width="100%" border="1" align="center" cellpadding="3" cellspacing="0">
		      <tr>
		        <th  width="35%" class="thlist" align="center">材料名称<br></th>
		        <th  width="10%" class="thlist" align="center">计量单位<br></th>
		        <th  width="25%" class="thlist" align="center">规格型号<br></th>
		        <th  width="10%" class="thlist" align="center">申请数量<br></th>
		        <logic:notEqual value="待审批" name="reqinfo" property="auditresult">
		          <th width="10% " class="thlist" align="center">审批数量<br></th>
		          <th width="10%" class="thlist" align="center">入库数量<br></th>
		        </logic:notEqual>
		      </tr>
		      <logic:present name="reqpartinfo">
		        <logic:iterate id="reqid" name="reqpartinfo">
		          <tr id="<bean:write name="reqid" property="id"/>">
		            <td>
		                 -- 
		            <br></td>
		            <td>
		               
		            <br></td>
		            <td>
		                
		            <br></td>
		            <td>
		                
		            <br></td>
		            <logic:notEqual value="待审批" name="reqinfo" property="auditresult">
		              <logic:equal value="不予审批" name="reqinfo" property="auditresult">
		                <td>0<br></td>
		                <td>0<br></td>
		              </logic:equal>
		              <logic:notEqual value="不予审批" name="reqinfo" property="auditresult">
		                <td>
		                  
		                <br></td>
		                <td>s
		                    <bean:write name="reqid" property="stocknumber"/>
		                </td>
		              </logic:notEqual>
		            </logic:notEqual>
		          </tr>
		        </logic:iterate>
		      </logic:present>
		    </table>
		        </td>
		      </tr>
		    </table>
		        </td>
      </tr>
    </table>


    <p align="center">
      <input type="button" style="button" value="返回" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
      <html:button property="action" styleClass="button" onclick="exportReq()">Excel报表</html:button>
    </p>
  </html:form>
</logic:equal>
<logic:equal value="showall01" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <br/>
  <template:titile value="维护材料申请单一览表"/>
  <display:table name="sessionScope.reqinfo" requestURI="${ctx}/PartRequisitionAction.do?method=queryShow" id="currentRowObject" pagesize="18">
  	<%
      	BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      	String id = "";
      	String reason = "";
      	String auditresult = "";
      	String contractorid = "";
      	String reasonTitle = "";
      	if(object != null) {
      		id = (String) object.get("reid");
      		
      		reasonTitle = (String)object.get("reason");
      		if(reasonTitle != null && reasonTitle.length() > 15) {
      			reason = reasonTitle.substring(0,15) + "...";
      		} else {
      			reason = reasonTitle;
      		}
      		auditresult=(String) object.get("auditresult");
      		contractorid=(String) object.get("contractorid");  	
      	}    
      	
      	
    %>
     <display:column media="html" title="申请单流水号" style="width:100px">
        <%if(id != null) { %>
    		<a href="javascript:toGetForm('<%=id%>','<%=contractorid%>','1')"><%=id %></a>
    	<%} %>
    </display:column>    
    <display:column media="html" title="申请原因">
        <%if(reason != null) { %>
    		<a href="javascript:toGetForm('<%=id%>','<%=contractorid%>','1')" title="<%=reasonTitle %>"><%=reason %></a>
    	<%} %>
    </display:column>
    <display:column property="contractorname" title="申请单位" maxLength="20" style="align:center"/>
    <display:column property="username" title="申 请 人" maxLength="20" style="align:center"/>
    <display:column property="time" title="申请时间" maxLength="20" style="align:center"/>    
    <display:column media="html" title="审批状态">    
  		<%if("待审批".equals(auditresult)) { %>    	
    		<font color="#0000CD" ><%=auditresult %></font>
    	<%} else if("通过审批".equals(auditresult)) { %>
    		<font color="#008000" ><%=auditresult %></font>
    	<%} else if("不予审批".equals(auditresult)) {%>
    		<font color="red" ><%=auditresult %></font>
    	<%} else if("待审核".equals(auditresult)) {%>
    		<font color="#FFA500" ><%=auditresult %></font>
    	<%} else {%>
    		<%=auditresult %>
    	<%} %>
    </display:column>   
    <display:column media="html" title="操作" headerClass="subject" class="subject">    	
      <apptag:checkpower thirdmould="80104" ishead="0">
        <a href="javascript:toUpForm('<%=id%>')">修改</a> | 
    </apptag:checkpower>
    <apptag:checkpower thirdmould="80105" ishead="0"> 
        <a href="javascript:toDelForm('<%=id%>')">删除</a>
    </apptag:checkpower>
  </display:column>      
  </display:table>
  <html:link action="/PartRequisitionAction.do?method=exportRequisitonResult">导出为Excel文件</html:link>
</logic:equal>

<logic:equal value="showaudit1" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

  <br/>
  <template:titile value="维护材料审批单一览表"/>
  <display:table name="sessionScope.reqinfo" requestURI="${ctx}/PartRequisitionAction.do?method=queryExecForAudit" id="currentRowObject" pagesize="18">
  	<%
      BasicDynaBean objectb1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String idb1 = "";
      String reason = "";
      String auditresult = "";
      String reasonTitle = "";
      if(objectb1 != null) {
      	 idb1 = (String) objectb1.get("reid");
      	 reason = (String)objectb1.get("reason");
      	 
      	 reasonTitle = (String)objectb1.get("reason");
    	 if(reasonTitle != null && reasonTitle.length() > 15) {
    		reason = reasonTitle.substring(0,15) + "...";
    	 } else {
    		reason = reasonTitle;
    	 }
      	 auditresult = (String)objectb1.get("auditresult");
      }
    %>
  	<display:column media="html" title="申请单流水号">    
      <a href="javascript:toShowOneAudit('<%=idb1%>')"><%=idb1 %></a>
     </display:column>
  	<display:column media="html" title="申请原因">   
  	  <%if(reason != null ) { %>
      	<a href="javascript:toShowOneAudit('<%=idb1%>')" title="<%=reasonTitle %>"><%=reason %></a>
      <%} %>
    </display:column>
    <display:column property="contractorname" title="申请单位" maxLength="10"/>    
    <display:column property="username" title="审 批 人" maxLength="8" style="align:center"/>
    <display:column property="time" title="审批时间" maxLength="20" style="align:center"/>
    <display:column media="html" title="审批结果">    
  		<%if("待审批".equals(auditresult)) { %>    	
    		<font color="#0000CD" ><%=auditresult %></font>
    	<%} else if("通过审批".equals(auditresult)) { %>
    		<font color="#008000" ><%=auditresult %></font>
    	<%} else if("不予审批".equals(auditresult)) {%>
    		<font color="red" ><%=auditresult %></font>
    	<%} else if("待审核".equals(auditresult)) {%>
    		<font color="#FFA500" ><%=auditresult %></font>
    	<%} else {%>
    		<%=auditresult %>
    	<%} %>
    </display:column>
  </display:table>
  <logic:notEmpty name="reqinfo">
  	<html:link action="/PartRequisitionAction.do?method=exportRequisiton">导出为Excel文件</html:link>
  </logic:notEmpty>
</logic:equal>

<logic:equal value="showaudit20" scope="session" name="type">
  <apptag:checkpower thirdmould="80209" ishead="1">
    <jsp:forward page="/globinfo/powererror.jsp"/>
  </apptag:checkpower>
  <br/>
  <template:titile value="审批材料申请单"/>
  <html:form action="/PartRequisitionAction?method=auditRequisition" styleId="addForm">
    <input type="hidden" name="reid" value="<bean:write name="reqinfo" property="reid"/>"/>
    <input type="hidden" name="useid" value="<bean:write name="reqinfo" property="useid"/>"/>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <table align="center" width="600" border="0">
      <tr>
        <td width="60">
          <b>单位名称:</b>
        <br></td>
        <td align="left" width="120">
          
        <br></td>
        <td align="right" width="50">
          <b>申请人:</b>
        <br></td>
        <td align="left" width="100">
          
        <br></td>
        <td width="70" align="right">
          <b>申请时间:</b>
        <br></td>
        <td align="left" width="100">
          
        <br></td>
      </tr>
      <tr>
        <td>
          <b>申请事由:</b>
        <br></td>
        <td colspan="5">
          
        <br></td>
      </tr>
      <tr>
        <td>
          <b>申请备注:</b>
        <br></td>
        <td colspan="5">
          
        <br></td>
      </tr>
      <tr>
        <td colspan="6" valign="bottom">
          <b>            该申请单所申请的材料:(
            
            )
          </b>
        <br></td>
      </tr>
    </table>
    <br/>
    <table id="queryID"  border="1" align="center" cellpadding="3" cellspacing="0">
      <tr>
        <th width="250" class="thlist" align="center">材料名称<br></th>
        <th width="60" class="thlist" align="center">计量单位<br></th>
        <th width="200" class="thlist" align="center">规格型号<br></th>
        <th width="60" class="thlist" align="center">申请数量<br></th>
        <th width="60" class="thlist" align="center">审批数量<br></th>
      </tr>
      <logic:present name="reqpartinfo">
        <logic:iterate id="reqid" name="reqpartinfo">
          <tr id="<bean:write name="reqid" property="id"/>">
            <td>
              <input type="hidden" name="id" value="<bean:write name="reqid" property="id"/>"/>
              <input type="text" readonly="readonly" class="inputtext" size="32"  value="<bean:write name="reqid" property="name"/> -- <bean:write name="reqid" property="type"/>"/>
            <br></td>
            <td>
             <input type="text"  readonly="readonly" class="inputtext" size="8"  value="<bean:write name="reqid" property="unit"/>"/>
            <br></td>
            <td>
              <input type="text"readonly="readonly" class="inputtext" size="32" value="<bean:write name="reqid" property="type"/>"/>
            <br></td>
            <td>
              <input type="text" readonly="readonly" class="inputtext" size="8"  value="<bean:write name="reqid" property="renumber"/> "/>
            <br></td>
            <td>
              <input id="aduditnub<bean:write name="reqid" property="id"/>" onblur="valiDigit(id)" size="8" maxlength="6" align="right" class="inputtext"  name="audnumber" value="<bean:write name="reqid" property="renumber"/>"/>
            <br></td>
          </tr>
        </logic:iterate>
      </logic:present>
    </table>
    <br/>
    <hr/>
    <br/>
    <table align="center" width="600" border="0">
      <tr>
        <td height="30">
          <b>审批结果:</b>
        <br></td>
        <td colspan="5">
          <select name="auditresult" class="selecttext">
            <option value="通过审批">通过审批</option>
            <option value="不予审批">不予审批</option>
          </select>
        <br></td>
      </tr>
      <tr>
        <td height="30">
          <b>审批备注:</b>
        <br></td>
        <td colspan="5">
          <input class="inputtext" name="auditremark" maxlength="512" style="width:520" value="请填写审批备注"/>
        <br></td>
      </tr>
      <tr>
        <td width="60" height="30">
          <b>审批单位:</b>
        <br></td>
        <td align="left" width="120">
          
        <br></td>
        <td align="right" width="50">
          <b>审批人:</b>
        <br></td>
        <td align="left" width="100">
          
        <br></td>
        <td width="70" align="right">
          <b>审批时间:</b>
        <br></td>
        <td align="left" width="100">1
          <bean:write name="date"/>
        </td>
      </tr>
      <tr>
        <td colspan="6" height="40" valign="top">
          <template:formSubmit>
            <td>
              <html:submit property="action" styleClass="button">提交审批</html:submit>
              <input type="button" value="返&nbsp;&nbsp;回" Class="button" onclick="javascript:backToAuditShow()"/>
            </td>
          </template:formSubmit>
        </td>
      </tr>
    </table>
  </html:form>
</logic:equal>
<iframe id="hiddenIframe" style="display:none"></iframe>
<script type="text/javascript">
	//返回到上级查询页面
	function goquery(){
		window.location.href = "${ctx}/PartRequisitionAction.do?method=queryShowForAudit";
	}
	
	function goApplyquery(){
		window.location.href = "${ctx}/PartRequisitionAction.do?method=queryShow";
	}
</script>
</body>
</html>
