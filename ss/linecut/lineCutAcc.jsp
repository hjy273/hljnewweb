<%@page import="com.cabletech.linecut.beans.LineCutBean"%>
<%@include file="/common/header.jsp"%>

<html>
<head>
<script type="" language="javascript">
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

        //修改附件的显示格式
        cell1.width="155";
        cell2.width="300";
        cell1.align="right";
        cell1.innerText="附件：";
        cell2.appendChild(t1);//文字
        cell2.appendChild(b1);
	}

 //显示待验收的施工信息
 function toGetOneWorkForm(id){
 	var url = "${ctx}/LineCutAcceptAction.do?method=showOneWorkForAcc&reid=" + id;
   window.location.href=url;
 }
 function showallAcce(){
 	var url = "${ctx}/LineCutAcceptAction.do?method=showAllWorkForAcc";
    window.location.href=url;
 }
 function toGetOneAccForm(id){
 	var url = "${ctx}/LineCutAcceptAction.do?method=showOneAcc&reid=" + id;
    window.location.href=url;
 }

 function addGoBack(){
 	var url = "${ctx}/LineCutAcceptAction.do?method=showAllAcc";
    window.location.href=url;
 }

function goQueryPage() {
	var url = "${ctx}/linecut/lineCutRe.jsp"
}

       //检验是否数字
	 function valiD(id){
      	var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        var obj = document.getElementById(id);
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("你填写的数字不合法,请重新输入");
            obj.focus();
            obj.value = "0.00";
            return false;
        }
    }

 //选择日期
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
	}
	
	function oneInfoGoBack() {
		var url="LineCutAcceptAction.do?method=queryAccAfterMod";
		window.location.href=url;
	}

	//动态上传附件
	function addRowMod() {
			var maxSeq = parseInt(document.getElementById('maxSeq').value);
			maxSeq ++;//自加
			var tableBody = document.getElementsByTagName("tbody")[1];
			var newTr = document.createElement("tr");//创建一个tr
			newTr.id = maxSeq;
			newTr.className = 'trStyle';
			
			var checkTd = document.createElement("td");//创建一个TD，+到TR中
			checkTd.innerHTML = "<input type='checkbox' name='ifCheck' value='" + maxSeq + "'>";
			checkTd.className = 'selTd';
			newTr.appendChild(checkTd);
			
			var fileTd = document.createElement("td");//上传文件的td，+到TR中
			fileTd.className = 'rigthTdStyle';
			var fileUpLoad = document.createElement("input");
			fileUpLoad.type = 'file';
			fileUpLoad.className = 'fileStyle';
			fileUpLoad.name = 'uploadFile[' + maxSeq + '].file';
			fileUpLoad.id = 'uploadFile[' + maxSeq + '].file';
			fileTd.appendChild(fileUpLoad);
			newTr.appendChild(fileTd);
			
			document.getElementById('maxSeq').value = maxSeq;//设置隐藏域的值
			tableBody.appendChild(newTr);
		}
		
		function checkOrCancel() {
			var all = document.getElementById('sel');
			if(all.checked) {
				checkAll();
			} else {
				cancelCheck();
			}
		}

		function checkAll() {
			var subChecks = document.getElementsByName("ifCheck");
			var length = subChecks.length;
			for(var i = 0; i < length; i++) {
				subChecks[i].checked = true;
			}		
		}
		
		function cancelCheck() {
			var allChecks = document.getElementsByName('ifCheck');
			var length = allChecks.length;
			for(var i = 0; i < length; i++) {
				allChecks[i].checked = false;
			}
		}
		
		
		function delRow() {
			var delChecks = document.getElementsByName('ifCheck');
			var tableBody = document.getElementsByTagName('tbody')[1];
			var delRows = new Array(delChecks.length);
			var delid = 0;
			if(delChecks.length == 0) {
				alert("您还没有增加附件!");
				return;
			}
			for(i = 0; i < delChecks.length; i++) {
				if(delChecks[i].checked) {
					delRows[delid++] = document.getElementById(delChecks[i].value)
				}
			}
			if(delid == 0 ) {
				alert("请选择要删除的附件!");
				return;
			}
			if(delid != 0) {
				if(confirm("确实要删除吗？")) {
					for(i = 0; i <= delid; i++) {
						if(delRows[i] != null && delRows[i].nodeType == 1) {
							tableBody.removeChild(delRows[i]);
						}
					}
				document.getElementById('sel').checked = false;
				}
			}
		}

</script>
<title>lineCutAcc</title>
</head>
<body>
  <!--显示验收单-->
<logic:equal value="ac1" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <BR>
  <%
	DynaBean object1 = null;
	String id1 = null;
	String numerical = null;
	String name = null;
	String auditresult = null;
  %>
  <template:titile value="割接施工验收一览表"/>
  <display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutAcceptAction.do?method=doQuery" id="currentRowObject" pagesize="18">
  	<display:column media="html" title="流水号" sortable="true">
		<%
			object1 = (DynaBean)pageContext.getAttribute("currentRowObject");
			if(object1 != null) {
				id1 = (String) object1.get("reid");
				numerical = (String)object1.get("numerical");
				numerical = numerical==null ? "": numerical;
				name =  (String)object1.get("name");
				if(name != null) {
					name = name.length() < 10 ? name: name.substring(0,10)+"...";
				} else {
					name = "";
				}
				auditresult = (String)object1.get("auditresult");
			}
			
		%>
		<a href="javascript:toGetOneAccForm('<%=id1%>')"><%=numerical %></a>
	</display:column>
    <display:column media="html" title="割接名称" sortable="true">
		<a href="javascript:toGetOneAccForm('<%=id1%>')"><%=name %></a>
	</display:column>
    <display:column property="address" title="割接地点" maxLength="10"  style="align:center" sortable="true"/>
    <display:column property="essetime" title="割接时间" maxLength="20"  style="align:center" sortable="true"/>
    <display:column property="audittime" title="验收时间" maxLength="20"  style="align:center" sortable="true"/>
    <display:column media="html" title="验收结果" sortable="true">
    	<%if("通过验收".equals(auditresult)) {%>
    		<font color="#FFA500" ><%=auditresult %></font>
    	<%} else if("未通过验收".equals(auditresult)) { %>
    		<font color="#FFA500"><%=auditresult %></font>
    	<%} else { %>
    		<%=auditresult %>
    	<%} %>
    </display:column>
    <display:column property="username" title="提交人" maxLength="10"  style="align:center" sortable="true"/>
  </display:table>
  <html:link action="/LineCutAcceptAction.do?method=exportLineCutAcc">导出为Excel文件</html:link>
</logic:equal>


  <!--显示所有待验收的割接施工-->
<logic:equal value="ac2" scope="session" name="type">
  <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
  <BR>
  <%
	DynaBean object1 = null;
	String id1 = null;
	String numerical = null;
	String name = null;
  %>
  <template:titile value="割接施工验收一览表"/>
  <display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutAcceptAction.do?method=showAllWorkForAcc" id="currentRowObject" pagesize="18">
    	<display:column media="html" title="流水号">
		<%
			object1 = (DynaBean)pageContext.getAttribute("currentRowObject");
			if(object1 != null) {
				id1 = (String) object1.get("reid");
				numerical = (String)object1.get("numerical");
				numerical = numerical==null ? "": numerical;
				name =  (String)object1.get("name");
				if(name != null) {
					name = name.length() < 10 ? name: name.substring(0,10)+"...";
				} else {
					name = "";
				}
			}
			
		%>
		<a href="javascript:toGetOneWorkForm('<%=id1%>')"><%=numerical %></a>
	</display:column>
	<display:column media="html" title="割接名称">
		<a href="javascript:toGetOneWorkForm('<%=id1%>')"><%=name %></a>
	</display:column>
    <display:column property="address" title="割接地点" maxLength="10"  style="align:center" sortable="true"/>
    <display:column property="sublinename" title="中继段" maxLength="10"  style="align:center" sortable="true"/>
    <display:column property="endvalue" title="割接后衰减" maxLength="10"  style="align:center"" sortable="true"/>
    <display:column property="essetime" title="割接日期" maxLength="18"  style="align:center" sortable="true"/>
    <display:column property="username" title="提交人" maxLength="10"  style="align:center" sortable="true"/>
    <%--<display:column media="html" title="操作">
    <%
      BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String id = (String) object.get("reid");
    %>
      <a href="javascript:toGetOneWorkForm('<%=id%>')">验收</a>
    </display:column>
    --%>
  </display:table>
</logic:equal>
  <!--验收一个割接施工-->
<logic:equal value="ac20" name="type">
	<%
		LineCutBean bean = (LineCutBean)request.getAttribute("reqinfo");
	 %>
	 <br>
  <template:titile value="验收割接施工" />
  <template:formTable namewidth="100" contentwidth="400" th2="内容" th1="项目">
    <template:formTr name="割接名称">
    	<%=bean.getName() %>
      <html:hidden property="name" name="reqinfo" styleClass="inputtext" style="width:220" />
    </template:formTr>
    <template:formTr name="光缆线段">
    	<%=bean.getSublinename() %>
      <html:hidden property="sublinename" name="reqinfo" styleClass="inputtext" style="width:220"/>
    </template:formTr>
    <template:formTr name="割接原因">
    	<%=bean.getReason() %>
      <html:hidden property="reason" name="reqinfo" styleClass="inputtext" style="width:220"/>
    </template:formTr>
    <template:formTr name="割接地点">
    	<%=bean.getAddress() %>
      <html:hidden property="address" name="reqinfo" styleClass="inputtext" style="width:220"/>
    </template:formTr>
    <template:formTr name="割接类型">
    	<%=bean.getCutType() %>
    </template:formTr>
    <template:formTr name="割接前衰耗">
    	<%=bean.getProvalue() %>
      <html:hidden property="provalue" name="reqinfo" styleClass="inputtext" style="width:180"/>
      分贝(db)
    </template:formTr>
    <template:formTr name="受影响系统">
    	<%=bean.getEfsystem() %>
      <html:hidden property="efsystem" name="reqinfo" styleClass="inputtext" style="width:220"/>
    </template:formTr>
    <template:formTr name="割接后衰减">
    	<%=bean.getEndvalue() %>
      <html:hidden property="endvalue" name="reqinfo" styleClass="inputtext" style="width:180" />
      分贝(db)
    </template:formTr>
    <template:formTr name="所耗人力">
    	<%=bean.getManpower() %>
      <html:hidden property="manpower" name="reqinfo" styleClass="inputtext" style="width:200" />
      工日
    </template:formTr>
    <template:formTr name="割接用时">
    	<%=bean.getUsetime() %>
      <html:hidden property="usetime" name="reqinfo" styleClass="inputtext" style="width:200"/>
      小时
    </template:formTr>
    <template:formTr name="割接时间">
    	<bean:write name="reqinfo" property="essetime"/>
      <input id="timeid" type="hidden"  value="<bean:write name="reqinfo" property="essetime"/>" name="essetime" class="inputtext" style="width:220" title=""/>
    </template:formTr>
    <template:formTr name="施工备注">
    	<%=bean.getWorkremark() %>
      <html:hidden property="workremark" name="reqinfo" styleClass="forTextArea3" value="<=%bean.getWorkremark() %>" />      
    </template:formTr>
    <template:formTr name="修改资料">
    	<bean:write name="reqinfo" property="updoc"/>
    	<html:hidden property="updoc" name="reqinfo"  value="<%bean.getUpdoc() %>" />  
    </template:formTr>
    <template:formTr name="施工附件">
    <%
      String reacce1 = "";
      LineCutBean bean1 = (LineCutBean) request.getAttribute("reqinfo");
      reacce1 = bean1.getWorkacce();
      if (reacce1 == null) {
        reacce1 = "";
      }
    %>
      <apptag:listAttachmentLink fileIdList="<%=reacce1%>"/>
    </template:formTr>
  </template:formTable>
  <hr />
  <html:form action="/LineCutAcceptAction?method=acceptOneWork" styleId="addApplyForm" enctype="multipart/form-data">
      <html:hidden property="reid" name="reqinfo"/>
     <html:hidden property="name" name="reqinfo" />
      <template:formTable namewidth="100" contentwidth="400" th2="填写" th1="项目">
        <template:formTr name="验收人">
        	<bean:write name="LOGIN_USER" property="userName"/>
          <input type="hidden" class="inputtext"  value="<bean:write name="LOGIN_USER" property="userName"/>" style="width:200"/>
        </template:formTr>
        <template:formTr name="验收日期">
          <html:text property="audittime"  readonly="true" style="width:200; border: 0px;background-color: #F5F5F5;">
            <apptag:date property="audittime">            </apptag:date>
          </html:text>
        </template:formTr>
        <template:formTr name="验收结果">
          <html:select property="auditresult" styleClass="inputtext" style="width:270;">
            <html:option value="通过验收">通过验收</html:option>
            <html:option value="未通过验收">未通过验收</html:option>
          </html:select>
        </template:formTr>
        <template:formTr name="验收备注" style="width:270">
          <html:textarea property="auditremark" cols="36" rows="4" style="width:270"title="请不要超过250个汉字或者512个英文字符，否则将截断。" styleClass="textarea"/>
        </template:formTr>
        <table id="uploadID" width=500 border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
        	<tbody></tbody>
        </table>
        <template:formSubmit>
           <td>
			<input type="checkbox" onclick="checkOrCancel()" id="sel">全选&nbsp;
		  </td>
          <td>
          	<input type="hidden" value="0" id="maxSeq">
            <html:button property="action" styleClass="button" onclick="addRowMod()">添加附件</html:button>
          </td>
          <td>
			<input type="button" value="删除选中" onclick="delRow()" class="button">
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




  <!--显示一个验收的详细信息-->
<logic:equal value="ac10" name="type">
	<br>
  <template:titile value="验收详细信息"/>
  <template:formTable namewidth="100" contentwidth="300"  th2="内容" th1="项目">
    <template:formTr name="割接名称">
        <bean:write name="reqinfo" property="name"/>
    </template:formTr>
    <template:formTr name="光缆线段">
       			 <%
		    		String sublinenamecon = ((LineCutBean) request.getAttribute("reqinfo")).getSublinename();
					String[] atrArr = sublinenamecon.split("<br>");
					for(int i = 0; i < atrArr.length; i++ ) { %>
						<%=atrArr[i] %>;<br>
				<%}%>
    </template:formTr>
    <template:formTr name="割接原因">
        <bean:write name="reqinfo" property="reason"/>
    </template:formTr>
    <template:formTr name="割接地点">
        <bean:write name="reqinfo" property="address"/>
    </template:formTr>
    <template:formTr name="割接类型">
        <bean:write name="reqinfo" property="cutType"/>
    </template:formTr>
    <template:formTr name="割接前衰耗">
        <bean:write name="reqinfo" property="provalue"/>分贝(db)
    </template:formTr>
    <template:formTr name="受影响系统">
        <bean:write name="reqinfo" property="efsystem"/>
    </template:formTr>
    <template:formTr name="修改资料">
    	<bean:write name="reqinfo" property="updoc"/>
    </template:formTr>
    
    <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
    
    <template:formTr name="割接后衰减">
        <bean:write name="reqinfo" property="endvalue"/>分贝(db)
    </template:formTr>
    <template:formTr name="所耗人力">
        <bean:write name="reqinfo" property="manpower"/>工日
    </template:formTr>
    <template:formTr name="割接用时">
        <bean:write name="reqinfo" property="usetime"/>小时
    </template:formTr>
    <template:formTr name="割接时间">
      <bean:write name="reqinfo" property="essetime"/>
    </template:formTr>
    
     <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
    
    <template:formTr name="验收时间">
       <bean:write name="reqinfo" property="audittime"/>
    </template:formTr>
    <template:formTr name="验收结果">
        <bean:write name="reqinfo" property="auditresult"/>
    </template:formTr>
    <template:formTr name="验收备注">
        <bean:write name="reqinfo" property="auditremark"/>
    </template:formTr>
    
    <template:formTr name="验收附件">
    <%
      String reacce1 = "";
      LineCutBean bean1 = (LineCutBean) request.getAttribute("reqinfo");
      reacce1 = bean1.getAuditacce();
      if (reacce1 == null) {
        reacce1 = "";
      }
    %>
      <apptag:listAttachmentLink fileIdList="<%=reacce1%>"/>
    </template:formTr>
  </template:formTable>
  <p align="center"><input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"></p>
 </logic:equal>

	<logic:equal value="ac3" name="type"scope="session" >
	      	<br />
	        <template:titile value="按条件查找割接验收"/>
	        <html:form action="/LineCutAcceptAction?method=doQuery"   styleId="queryForm2" >
            	<template:formTable namewidth="100" contentwidth="250">
	        		<template:formTr name="割接名称">
				      <html:text property="name"  styleClass="inputtext" style="width:180" />
				    </template:formTr>
				    <template:formTr name="割接地点">
				      <html:text property="address"  styleClass="inputtext" style="width:180" />
				    </template:formTr>
				    
				    
				    <template:formTr name="割接提交单位">
				    	<select class="inputtext" name="contractorid" style="width:180"> 
				    		<option value="">===选择所有提交单位===</option>
				    		<logic:iterate id="cl" name="reConList">
				    			<option value="<bean:write name="cl" property="contractorID"/>"><bean:write name="cl" property="contractorName"/></option>
				    		</logic:iterate>
				    	</select>
				    </template:formTr>
				    
				    <template:formTr name="验收结果">
                      <select name="auditresult" Class="inputtext" style="width:180">
                          <option value="">不限</option>
                          <option value="通过验收">通过验收</option>
                          <option value="未通过验收">未通过验收</option>
                      </select>
				    </template:formTr>
	                <template:formTr name="验收开始时间">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='日期'  readonly="readonly" ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formTr name="验收结束时间">
	                	<input  id="end" type="text"  name="endtime" readonly="readonly" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='日期' ID='btn'  onclick="GetSelectDateTHIS('end')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formSubmit>
						      	<td>
		                          <html:submit property="action" styleClass="button" >查找</html:submit>
						      	</td>
						      	<td>
						       	 	<html:reset property="action" styleClass="button" >取消</html:reset>
						      	</td>
						    </template:formSubmit>
	        	</template:formTable>

	    	</html:form>
	    </logic:equal>
	</body>
</html>
