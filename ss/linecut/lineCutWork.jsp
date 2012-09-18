<%@ page import="com.cabletech.linecut.beans.LineCutBean"%>
<%@include file="/common/header.jsp"%>


<html>
<head>
<script type="" language="javascript">
    var fileNum=0;
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
        cell1.width="125";
        cell2.width="300";
        cell1.align="right";
        cell1.innerText="施工附件：";
        cell2.appendChild(t1);//文字
        cell2.appendChild(b1);
	}

    function addRow1(){
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

    //待添加施工信息页面添加按钮动作
   function toGetFormForWork(idValue){
     	var url = "${ctx}/LineCutWorkAction.do?method=showOneInfoForWork&reid=" + idValue;
        window.location.href=url;
    }
    //查看施工详细按钮动作
 	function toGetOneWorkForm(idValue){
     	var url = "${ctx}/LineCutWorkAction.do?method=showOneWorkInfo&reid=" + idValue;
        window.location.href=url;
    }

    //查看审批详细返回动作
 	function  WorkShowGoBack(){
     	var url = "${ctx}/LineCutWorkAction.do?method=showAllWork";
        window.location.href=url;
    }
    function toGetWorkUp(idvalue){
    	var url = "${ctx}/LineCutWorkAction.do?method=showWorkUp&reid=" + idValue;
        window.location.href=url;
    }

	function queryGoBack() {
		var url = "${ctx}/LineCutWorkAction.do?method=doQueryForArAfter";
		window.location.href=url;
	}

	//待归档页面按钮动作
   function toGetFormForAr(idValue){
     	var url = "${ctx}/LineCutWorkAction.do?method=showOneInfoForAr&reid=" + idValue;
        window.location.href=url;
    }
     //查看割接详细按钮动作
 	function toGetOneArForm(idValue){
     	var url = "${ctx}/LineCutWorkAction.do?method=showOneWorkArInfo&reid=" + idValue;
        window.location.href=url;
    }
    //查看详细返回动作
 	function  arShowGoBack(){
     	var url = "${ctx}/LineCutWorkAction.do?method=showAllAr";
        window.location.href=url;
    }
    function  addGoBack(){
     	var url = "${ctx}/LineCutWorkAction.do?method=workShow";
        window.location.href=url;
    }
    function toGetWorkEdit(id,archive){
    	if(archive !="正在施工"){
          alert("对不起，已经施工完毕，不能修改！！");
          return;
        }
    	var url = "${ctx}/LineCutWorkAction.do?method=showWorkUp&reid=" + id;
        window.location.href=url;
    }

    function dosubmit(){
        if(LineCutBean.isarchive[0].checked==true&&compareDate(LineCutBean.essetime.value)){
          alert("施工日期比归档日期晚，不能完成归档！");
          return false;
        }else{
          if(LineCutBean.flag[1].checked ==true){
            if(window.confirm("确认不需用修改资料吗?"))
            LineCutBean.submit();
            else
            return false;
          }
          LineCutBean.submit();
        }
    }

    function compareDate(time){
      var date=new Date();
      var worktime=parseDate(time+":00");
      return (date<worktime);
    }

function parseDate(str){
  if(typeof str == 'string'){
    var results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) *$/);
    if(results && results.length>3)
      return new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]));
    results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2}) *$/);
    if(results && results.length>6)
      return new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]),parseInt(results[4]),parseInt(results[5]),parseInt(results[6]));
    results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2})\.(\d{1,9}) *$/);
    if(results && results.length>7)
      return new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]),parseInt(results[4]),parseInt(results[5]),parseInt(results[6]),parseInt(results[7]));
  }
  return null;
}

    function getTimeWin(objName){
		iteName = objName;
		showx = event.screenX - event.offsetX - 4 - 210 ;
		showy = event.screenY - event.offsetY + 18;
		var timeWin = window.showModalDialog("${ctx}/common/time.html",self, "dialogWidth:255px; dialogHeight:285px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;help:no");
	}
	function setSelecteTime(time) {
	    	document.all.item(iteName).value = time;
    }

    function addworksub(){
        if(LineCutBean.date.value == null || LineCutBean.date.value ==""){
	        alert("请填写施工的日期！");
	        return false;
        }
        if(LineCutBean.time.value == null || LineCutBean.time.value ==""){
	        alert("请填写施工的时间！");
	        return false;
        }
	
    	LineCutBean.essetime.value = LineCutBean.date.value + " " + LineCutBean.time.value;
        LineCutBean.submit();
    }


	//动态上传附件
	function addRowMod() {
			var maxSeq = parseInt(document.getElementById('maxSeq').value);
			maxSeq ++;//自加
			var tableBody = document.getElementsByTagName("tbody")[1];
			var newTr = document.createElement("tr");//创建一个tr
			newTr.id = maxSeq;
			newTr.className = 'trColor';
			
			var checkTd = document.createElement("td");//创建一个TD，+到TR中
			checkTd.innerHTML = "<input type='checkbox' name='ifCheck' value='" + maxSeq + "'>";
			checkTd.className = 'selTd';
			checkTd.style.width = "100px";
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

		function oneReGoBack() {
			var url= "LineCutWorkAction.do?method=doQueryForWorkAfterMod";
			window.location.href=url;
		}
</script>
<title>
partRequisition
</title>
</head>
<body>

  <!--显示待添加施工信息的割接-->
    <logic:equal value="reforwork1"scope="session" name="type">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"  />
        <br />
        <%
			DynaBean object = null;
			String id = null;
			String numerical = null;
			String name = null;
		%>
        <template:titile value="待添加施工信息的割接一览表" />
    		<display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutWorkAction.do?method=workShow" id="currentRowObject"   pagesize="18">
    			<display:column media="html" title="流水号" sortable="true">
    				<%
						object = (DynaBean)pageContext.getAttribute("currentRowObject");
						if(object != null) {
							id = (String) object.get("reid");
							numerical = (String)object.get("numerical");
							numerical = numerical == null? "": numerical;
							name =  (String)object.get("name");
							if(name != null) {
								name = name.length() < 10 ? name: name.substring(0,10)+"...";
							} else {
								name = "";
							}
						}
					 %>
					 <a href="javascript:toGetFormForWork('<%=id%>')"><%=numerical %></a>
    			</display:column>
            	<display:column media="html"  title="割接名称" sortable="true">
					  <a href="javascript:toGetFormForWork('<%=id%>')"><%=name %></a>
            	</display:column>
                <display:column property="sublinename" title="中继段" maxLength="10"style="align:center"sortable="true"/>
                <%--<display:column property="prousetime" title="计划用时"maxLength="10"style="align:center"sortable="true"/>
                --%>
                <display:column property="protime" title="计划割接时间" maxLength="18"style="align:center"sortable="true"/>
                <display:column property="address" title="割接地点" maxLength="10"sortable="true"/>
                <display:column property="contractorname" title="提交单位" maxLength="10"sortable="true"/>
    		</display:table>
    </logic:equal>


	<!--添加信息页面-->
	<logic:equal value="work2" name="type">
    <br />
		<template:titile value="线路割接基本信息"/>
		  <template:formTable namewidth="100" contentwidth="400"  th2="内容" th1="项目">
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
            <template:formTr name="计划割接日期">
		      <bean:write name="reqinfo" property="protime"/>
		    </template:formTr>
            <template:formTr name="预计割接用时">
		      <bean:write name="reqinfo" property="prousetime"/>小时
		    </template:formTr>
            <template:formTr name="割接前衰耗">
		      <bean:write name="reqinfo" property="provalue"/>分贝(db)
		    </template:formTr>
            <template:formTr name="受影响系统">
		      <bean:write name="reqinfo" property="efsystem"/>
		    </template:formTr>
            <template:formTr name="审批情况">
		      <bean:write name="reqinfo" property="auditresult"/>
		    </template:formTr>
		  </template:formTable>
          <hr />
          
          <html:form action="/LineCutWorkAction?method=addWork" styleId="addApplyForm" enctype="multipart/form-data" onsubmit="checkAffix()">
          		<html:hidden property="reid" name="reqinfo"/>
          		<html:hidden property="essetime" />

            	<template:formTable namewidth="100" contentwidth="400"  th2="填写" th1="项目">
            		<%
            			String dateTime = ((LineCutBean)request.getAttribute("reqinfo")).getProtime();
            			String[] date = dateTime.split(" ");
            		 %>
				    <template:formTr name="实际割接日期">
				      	 <input  type="text"  id="protimeid" name="date" value="<%=date[0] %>" readonly="readonly" class="inputtext" style="width:125" />
                    	<INPUT TYPE='BUTTON' VALUE='日期' ID='btn'  onclick="GetSelectDateTHIS('protimeid')" STYLE="font:'normal small-caps 6pt serif';" >
                        <html:text property="time"   value="<%=date[1] %>" styleClass="inputtext" style="width:88"  readonly="true"/>
                        <input type='button' value="时间" id='btn'  onclick="getTimeWin('time')" style="font:'normal small-caps 6pt serif';">

				    </template:formTr>
				    <%
				    	String time = ((LineCutBean)request.getAttribute("reqinfo")).getProusetime();
				     %>
					 <template:formTr name="割接用时" style="width:270px;" >
				     	<html:text property="usetime"  value="<%=time %>" styleId="usetimeid" styleClass="inputtext"  onchange="valiD(id)" style="width:250;" />小时
				     </template:formTr>
				     <%
				     	String manpower = ((LineCutBean)request.getAttribute("reqinfo")).getManpower();
				      %>
                      <template:formTr name="所用人力"  style="width:270" >
				     	<html:text property="manpower"   value="<%=manpower %>" styleId="manpowerid" styleClass="inputtext"  onchange="valiD(id)" style="width:250;" />工日
				     </template:formTr>
				     <%
				     	String endvalue = ((LineCutBean)request.getAttribute("reqinfo")).getProvalue();
				      %>
                      <template:formTr name="实际衰耗" style="width:270" >
				     	<html:text property="endvalue"  value="<%=endvalue %>" styleId="endvalueid" styleClass="inputtext"  onchange="valiD(id)" style="width:250;" />分贝(db)
				     </template:formTr>
                     <template:formTr name="完成填写"  style="width:270" >
                     	<input  type="radio" name="flag" value="1"  checked="checked" />已经完成
                        <input  type="radio" name="flag" value="0"   />尚未完成
                     </template:formTr>
				     <template:formTr name="施工备注" style="width:270" >
                       <html:textarea property="workremark" cols="36" rows="4" value="" title="请不要超过250个汉字或者512个英文字符，否则将截断。" style="width:270" styleClass="textarea"/>
				     </template:formTr>
				    <table  id="uploadID"  width=500 border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				    	<tbody></tbody>
				    </table>
				    <template:formSubmit>
				        <td>
				    		<input type="checkbox" onclick="checkOrCancel()" id="sel">全选&nbsp;
				    	</td>
				        <td>
				        	<input type="hidden" value="0" id="maxSeq">
				        	<html:button  property="action" styleClass="button"  onclick="addRowMod()">添加附件</html:button>
				        </td>
				        <td>
							<input type="button" value="删除选中" onclick="delRow()" class="button">
						</td>
				        <td>
				          <html:button styleClass="button"  onclick="addworksub()" property="">提交</html:button>
				        </td>
				        <td>
				          <html:reset styleClass="button" >取消	</html:reset>
				        </td>
				        <td>
				            <input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
				        </td>
				  </template:formSubmit>
			  </template:formTable>
	     </html:form>
	</logic:equal>


      <!--显示施工信息-->
    <logic:equal value="work1"scope="session" name="type">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"  />
          <br />
          <%
				DynaBean object1=null;
				String id1= null;
				String numerical = null;
				String name = null;
				String isarchive = null;
		  %>
        <template:titile value="已经施工割接一览表" />
    		<display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutWorkAction.do?method=doQueryForWork" id="currentRowObject"   pagesize="18">
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
							isarchive = (String)object1.get("isarchive");
						}
					 %>
					 <a href="javascript:toGetOneWorkForm('<%=id1%>')"><%=numerical %></a> 
            	</display:column>
            	<display:column media="html" title="割接名称"  sortable="true">
					  <a href="javascript:toGetOneWorkForm('<%=id1%>')"><%=name %></a>
				</display:column>
            	
                <display:column property="sublinename" title="中继段" maxLength="10"style="align:center"sortable="true"/>
                <display:column property="address" title="割接地点" maxLength="10"sortable="true"/>
                <display:column property="endvalue" title="割接后衰耗" maxLength="10"sortable="true"/>
                <display:column property="usetime" title="割接用时" maxLength="10"sortable="true"/>
                <display:column property="essetime" title="割接日期" maxLength="18"sortable="true"/>

                <display:column media="html" title="当前状态" sortable="true">
                	 <%if("已经归档".equals(isarchive)) {%>
						<font color=""><%=isarchive %></font>
					  <%} 
					  else if("施工完毕".equals(isarchive)) {%>
					  	<font color="#DA70D6"><%=isarchive %></font>
					  <%} 
					  else if("正在施工".equals(isarchive)) {%>
					  	<font color="#FFA500"><%=isarchive %></font>
					  <%} else {%>
					  	<%=isarchive %>
					  <%} %>
                </display:column>

				<%
					UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
                    String deptType = userinfo.getDeptype();
                    if("2".equals(deptType)) { %>
                <display:column media="html" title="操作" >
	            	 <apptag:checkpower thirdmould="30304" ishead="0">
	            	 <%
	            	 	if("正在施工".equals(isarchive)) { %>
	            	 		 <a href="javascript:toGetWorkEdit('<%=id1%>','<%=isarchive%>')">修改</a>
	            	 <%	}%>
	            	 </apptag:checkpower>
			  	</display:column>
			  	
			  	<%} %>
            </display:table>
            <html:link action="/LineCutWorkAction.do?method=exportLineCutWork">导出为Excel文件</html:link>
    </logic:equal>



    	<!--显示割接施工的详细信息-->
	<logic:equal value="work10" name="type">
    <br />
		<template:titile value="线路割接施工详细信息"/>
		  <template:formTable namewidth="100" contentwidth="450"  th2="内容" th1="项目">

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
             <template:formTr name="施工备注" >
               <bean:write name="reqinfo" property="workremark" />
		    </template:formTr>
		    <template:formTr name="修改资料">
		    	<bean:write name="reqinfo" property="updoc" />
		    </template:formTr>
            <template:formTr name="施工附件">
				<logic:empty name="reqinfo" property="workacce">
                	<apptag:listAttachmentLink fileIdList=""/>
				</logic:empty>
                <logic:notEmpty name="reqinfo" property="workacce">
                  	<bean:define id="tem1" name="reqinfo" property="workacce" type="java.lang.String"/>
			   		<apptag:listAttachmentLink fileIdList="<%=tem1%>"/>
                </logic:notEmpty>


		    </template:formTr>
		  </template:formTable>

		   <p align="center"><input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"></p>
	</logic:equal>



    	<!--割接施工修改显示-->
	<logic:equal value="work4" name="type">
    <br />
		<template:titile value="线路割接施工信息修改"/>
            <html:form action="/LineCutWorkAction?method=WorkUp" styleId="addApplyForm" enctype="multipart/form-data" onsubmit="checkAffix()">
            	<html:hidden property="workacce" name="reqinfo"/>
                <html:hidden property="reid" name="reqinfo"/>
                <html:hidden property="workid"  name="reqinfo"/>
                <html:hidden property="essetime"  name="reqinfo"/>

			  <template:formTable namewidth="100" contentwidth="450">
			    <template:formTr name="割接名称">
			    	<bean:write property="name"  name="reqinfo"/>
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
	            	<bean:write property="reason"  name="reqinfo"/>
			    </template:formTr>
	            <template:formTr name="割接地点">
	            	<bean:write property="address"  name="reqinfo"/>
			    </template:formTr>
	            <template:formTr name="割接前衰耗">
	            	<bean:write property="provalue"  name="reqinfo"/>
			    </template:formTr>
	            <template:formTr name="受影响系统">
	            	<bean:write property="efsystem"  name="reqinfo"/>
			    </template:formTr>
	             <template:formTr name="割接后衰减">
                 	<html:text property="endvalue"  name="reqinfo" styleClass="inputtext"  style="width:180" />分贝(db)
			    </template:formTr>
	            <template:formTr name="所耗人力">
                	<html:text property="manpower"  name="reqinfo" styleClass="inputtext"  style="width:200" />工日
			    </template:formTr>
	            <template:formTr name="割接用时">
                	<html:text property="usetime"  name="reqinfo" styleClass="inputtext"  style="width:200" />小时
			    </template:formTr>
	            <template:formTr name="割接时间">
                	<% String temp1 = ((LineCutBean)request.getAttribute("reqinfo")).getEssetime();
                   		if(temp1 == null  || temp1.equals(""))
                            temp1 = "                       ";
                    %>
				      	 <input  type="text"  id="protimeid" name="date" value="<%=temp1.substring(0,10)%>" readonly="readonly" class="inputtext" style="width:100" />
                    	<INPUT TYPE='BUTTON' VALUE='日期' ID='btn'  onclick="GetSelectDateTHIS('protimeid')" STYLE="font:'normal small-caps 6pt serif';" >
                        <html:text property="time"   value="<%= temp1.substring(11,16)%>" styleClass="inputtext" style="width:60"  readonly="true"/>
                        <input type='button' value="时间" id='btn'  onclick="getTimeWin('time')" style="font:'normal small-caps 6pt serif';">

			    </template:formTr>
	             <template:formTr name="施工备注">
                 	<html:textarea property="workremark"  name="reqinfo" cols="34"  rows="3"  title="请不要超过250个汉字或者512个英文字符，否则将截断。"   styleClass="forTextArea3">
                    </html:textarea>
			    </template:formTr>
                <template:formTr name="完成填写"  style="width:270" >
                     	<input  type="radio" name="flag" value="1"  checked="checked" />已经完成
                        <input  type="radio" name="flag" value="0"   />尚未完成
                     </template:formTr>
	            <template:formTr name="施工附件">
					<%
				    	String reacce1="";
				        LineCutBean bean1 = (LineCutBean )request.getAttribute("reqinfo");
				        reacce1 = bean1.getWorkacce();
				        if(reacce1==null){
				        	reacce1="";
				        }
				 	%>
				   <apptag:listAttachmentLink fileIdList="<%=reacce1%>" showdele="true"/>

                </template:formTr>
                    <table  id="uploadID"  width=550 border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
                    	<tbody></tbody>
				    </table>
				    <template:formSubmit>
				        <td>
				    		<input type="checkbox" onclick="checkOrCancel()" id="sel">全选&nbsp;
				    	</td>
				        <td>
				        	<input type="hidden" value="0" id="maxSeq">
				        	<html:button  property="action" styleClass="button"  onclick="addRowMod()">添加附件</html:button>
				        </td>
				        <td>
							<input type="button" value="删除选中" onclick="delRow()" class="button">
						</td>
				        <td>
				          <html:button styleClass="button"  onclick="addworksub()" property="">提交</html:button>
				        </td>
				        <td>
				          <html:reset styleClass="button" >取消	</html:reset>
				        </td>
				        <td>
				            <input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
				        </td>
				  </template:formSubmit>
			  </template:formTable>

			</html:form>
	</logic:equal>



        <!--条件查询页面-->
	    <logic:equal value="work3" name="type"scope="session" >
	      	<script type="text/javascript" src="./js/json2.js"></script>
			<script type="text/javascript" src="./js/prototype.js"></script>
			<script type="text/javascript">
				//级联
				function onSelChangeLevel() {
					var ops = document.getElementById('line');
					ops.options.length = 0;
					var clewOps = document.getElementById('clew');
					clewOps.options.length = 0;
					var newClewOp = document.createElement("option");
					newClewOp.value = "";
					newClewOp.text = "===选择所有段名===";
					clewOps.add(newClewOp);
					var params = document.getElementById('level').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
						return;
					}
					var url = 'LineCutReAction.do?method=getLineByLevelId&levelId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callback, asynchronous:true});
				}
				
				function callback(originalRequest) {
					var ops = document.getElementById('line');
					var rst = originalRequest.responseText;
					var linearr = eval('('+rst+')');
					if(linearr.length == 0) {
						alert("该级别尚未制定线路!");
						return;
					}
					ops.options.add(new Option('===请选择线路===','')); 
					for(var i = 0; i < linearr.length; i++) {
						ops.options.add(new Option(linearr[i].linename,linearr[i].lineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("网络连接出现问题，请关闭后重试!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
				
				function onSelChangeLine() {
					var ops = document.getElementById('clew');
					var length = ops.length;
					ops.options.length = 0;
					var params = document.getElementById('line').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
						return;
					}
					ops.options.add(new Option('===选择所有段名===','')); 
					var url = 'LineCutReAction.do?method=getClewByLineId&lineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServer, asynchronous:true});
				}
				
				function callbackToServer(originalRequest) {
					var ops = document.getElementById('clew');
					var rst = originalRequest.responseText;
					var clewarr = eval('('+rst+')');
					if(clewarr.length == 0) {
						alert("该路线尚未制定线段!");
						return;
					}
					for(var i = 0; i < clewarr.length; i++) {
						ops.options.add(new Option(clewarr[i].sublinename,clewarr[i].sublineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("网络连接出现问题，请关闭后重试!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
				
				function onSelectChangeClew() {
					var ops = document.getElementById('cutLineName');
					var length = ops.length;
					ops.options.length = 0;//清空下拉列表
					ops.options.add(new Option("===选择所有名称===",""));
					var params = document.getElementById('clew').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getNameBySublineid&sublineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServerByName, asynchronous:true});
				}
				
				function callbackToServerByName(originalRequest) {
					var ops = document.getElementById('cutLineName');
					var rst = originalRequest.responseText;
					var nameArr = eval('('+rst+')');
					if(nameArr.length == 0) {
						alert("该路线下没有割接施工信息，\n或者还未开始施工!");
						return;
					}
					for(var i = 0; i < nameArr.length; i++) {
						ops.options.add(new Option(nameArr[i].name,nameArr[i].name));
					}
				}
				
				function resetInfo() {
					var ops = document.getElementById('line');
					ops.options.length = 0;
					ops.options.add(new Option("",""));
					
					var ops1 = document.getElementById('clew');
					ops1.options.length = 0;
					ops1.options.add(new Option("===选择所有段名===",""));
					
					var ops2 = document.getElementById('cutLineName');
					ops2.options.length = 0;
					ops2.options.add(new Option("===选择所有段名===",""));
					
				}
				
			</script>
	        <template:titile value="按条件查找割接施工信息"/>
	        <html:form action="/LineCutWorkAction?method=doQueryForWork"   styleId="queryForm2" >
	        	<template:formTable namewidth="200"  contentwidth="200">
	        		<template:formTr name="线路级别">
	        			<select class="inputtext" id="level" onchange="onSelChangeLevel()" name="level" style="width:180px" >
		  					<option value="mention">===请选择线路级别===</option>
		  					<logic:iterate id="levelList" name="lineLevelList">
		  						<option value="<bean:write name="levelList" property="code"/>"><bean:write name="levelList" property="name"/></option>
		  					</logic:iterate>
		  				</select>
		  				<img id="Loadingimg" src="./images/ajaxtags/indicator.gif" style="display:none">
	        		</template:formTr>
	        		
	        		<template:formTr name="线路名称">
	        			<select class="inputtext" id="line" onchange="onSelChangeLine()" name="line" style="width:180px" >
		  					<option></option>
		  				</select>
	        		</template:formTr>
	        		
	            	<template:formTr name="巡检线段"  >
	            		<select class="inputtext" id="clew" style="width:180px" name="sublineid" onchange="onSelectChangeClew()">
		  					<option  value="">===选择所有段名===</option>
		  				</select>
	                </template:formTr>
	                
                    <template:formTr name="割接名称"  >
	            		<select name="name"   class="inputtext" style="width:180px" id="cutLineName">
				        	<option  value="">===选择所有割接===</option>
			    		</select>
	                </template:formTr>
	                
                   
	                <template:formTr name="割接开始时间">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='日期'  readonly="readonly" ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formTr name="割接结束时间">
	                	<input  id="end" type="text"  name="endtime" class="inputtext" style="width:150" />
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

        <!--//=================归档与割接查询===================//-->
  <!--显示待归档的割接信息-->
    <logic:equal value="reforAr1"scope="session" name="type">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"  />
        <br />
        <template:titile value="待归档割接一览表" />
    		<display:table name="sessionScope.reqinfo" id="currentRowObject"   pagesize="18">
            	<display:column property="contractorname" title="割接单位" maxLength="10" style="align:center"  sortable="true"/>
            	<display:column property="name" title="割接名称" maxLength="10" style="align:center"  sortable="true"/>
                <display:column property="name" title="割接地点" maxLength="10"style="align:center"  sortable="true"/>
                <display:column property="sublinename" title="光缆线段" maxLength="10"style="align:center"sortable="true"/>
                <display:column property="essetime" title="割接时间" maxLength="18"style="align:center"sortable="true"/>
                <display:column property="reason" title="割接原因" maxLength="10"style="align:center"sortable="true"/>
                <display:column property="isarchive" title="当前状态" maxLength="10"style="align:center"sortable="true"/>
                <display:column media="html" title="操作" >
	            	 <%
					    BasicDynaBean  objecta1 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
					    String ida1 = (String) objecta1.get("reid");
					  %>
	            	 <a href="javascript:toGetFormForAr('<%=ida1%>')">归档割接信息</a>
			  	</display:column>
    		</display:table>
    </logic:equal>

    	<!--归档页面-->
	<logic:equal value="ar2" name="type">
		 <html:form action="/LineCutWorkAction?method=addAr" styleId="addApplyForm" enctype="multipart/form-data">
         		<html:hidden property="reid" name="reqinfo"/>
                <html:hidden property="essetime" name="reqinfo"/>
	            <br>
                <template:titile value="线路割接归档"/>
			  <template:formTable namewidth="100" contentwidth="400">
				     <template:formTr name="割接单位">
				      	<bean:write name="reqinfo" property="contractorname"/>
				    </template:formTr>
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
		           <template:formTr name="受影响系统">
				      	<bean:write name="reqinfo" property="efsystem"/>
				    </template:formTr>
		            <template:formTr name="割接日期">
				      	<bean:write name="reqinfo" property="essetime"/>
				    </template:formTr>
		             <template:formTr name="割接用时">
				      	<bean:write name="reqinfo" property="usetime"/>小时
				    </template:formTr>
		             <template:formTr name="割接后衰耗">
				      	<bean:write name="reqinfo" property="endvalue"/>分贝(db)
				    </template:formTr>
		            <template:formTr name="归档情况">
                    				<input  type="radio" name="isarchive" value="已经归档" /> 完成归档 &nbsp;&nbsp;&nbsp
                                    <input  type="radio" name="isarchive" value="已经验收" checked="checked"/> 尚未完成归档
		            </template:formTr>
                     <template:formTr name="资料修改">
		               				<input  type="radio" name="flag" value="需用修改" checked="checked"/> 需用修改 &nbsp;&nbsp;&nbsp
                                    <input  type="radio" name="flag" value="不需修改"/> 不需修改
		            </template:formTr>
		            <template:formTr name="归档文件">
		                    	<table  id="uploadID"  width=400 border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
										<tr  class=trcolor ><td ></td></tr>
					            </table>
		            </template:formTr>
		            <template:formSubmit>
						        <td><html:button  property="action" styleClass="button"  onclick="addRow1()">添加附件</html:button>
						        </td>
						        <td>
						          <html:button styleClass="button" property="action" onclick="dosubmit()">提交</html:button>
						        </td>
						        <td>
						          <html:reset styleClass="button" >取消	</html:reset>
						        </td>
						        <td>
						            <html:button property="action" styleClass="button" onclick="javascript:window.history.go(-1)" >返回</html:button>
						        </td>
		            </template:formSubmit>
			  </template:formTable>
	     </html:form>
	</logic:equal>

        <!--显示割接信息-->
    <logic:equal value="ar1"scope="session" name="type">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"  />
			<%
				DynaBean object1=null;
				String id1= null;
				String numerical = null;
				String name = null;
			 %>
        <br />
        <template:titile value="割接一览表" />
    		<display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutWorkAction.do?method=doQueryForAr" id="currentRowObject"   pagesize="18">
    			<display:column media="html" title="流水号" sortable="true">
					<%
						object1 = (DynaBean)pageContext.getAttribute("currentRowObject");
						if(object1 != null) {
							id1 = (String) object1.get("reid");
							numerical = (String)object1.get("numerical");
							numerical = numerical == null ? "":numerical;
							name = (String)object1.get("name");
							if(name!= null) {
								name = name.length() < 10 ? name: name.substring(0,10)+"...";
							} else {
								name = "";
							}
						}
					 %>
					 <a href="javascript:toGetOneArForm('<%=id1%>')"><%=numerical %></a>
				</display:column>
				<display:column media="html" title="割接名称" sortable="true">
					  <a href="javascript:toGetOneArForm('<%=id1%>')"><%=name %></a>
				</display:column>
    			
            	<display:column property="contractorname" title="割接单位" maxLength="20" style="align:center"  sortable="true"/>
                <display:column property="sublinename" title="中继段" maxLength="15"style="align:center"sortable="true"/>
                <display:column property="address" title="割接地点" maxLength="10"sortable="true"/>
                <display:column property="essetime" title="割接时间" maxLength="18"sortable="true"/>

                <display:column property="updoc" title="修改资料" maxLength="10"style="align:center"sortable="true"/>
                <display:column property="isarchive" title="工作阶段" maxLength="10"sortable="true"/>
    		</display:table>
            <html:link action="/LineCutWorkAction.do?method=exportLineCut">导出为Excel文件</html:link>
    </logic:equal>

    <!--显示割接详细信息-->
	<logic:equal value="ar10" name="type">
    <br />
    	
    		<template:titile value="线路割接详细信息"/>
			  <template:formTable namewidth="100" contentwidth="500" th2="内容" th1="项目">
	
			    <template:formTr name="割接名称">
			      <bean:write name="reqinfo" property="name"/>
			    </template:formTr>
	            <template:formTr name="割接地点">
	              <bean:write name="reqinfo" property="address"/>
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
			    
			    <template:formTr name="割接类型">
			      <bean:write name="reqinfo" property="cutType"/>
			    </template:formTr>
	
	            <template:formTr name="受影响系统">
			      <bean:write name="reqinfo" property="efsystem"/>
			    </template:formTr>
	            
	            <template:formTr name="修改资料">
					 <bean:write name="reqinfo" property="updoc"/>
				</template:formTr>
	            
			    <template:formTr name="申请备注">
					<bean:write name="reqinfo" property="reremark"/>
				</template:formTr>
	            
	            <template:formTr name="申请附件">
	                    <logic:empty name="reqinfo" property="reacce">
	                     	<apptag:listAttachmentLink fileIdList=""/>
	                    </logic:empty>
	                    <logic:notEmpty name="reqinfo" property="reacce">
	                    	<bean:define id="temr" name="reqinfo" property="reacce" type="java.lang.String"/>
						   	<apptag:listAttachmentLink fileIdList="<%=temr%>"/>
	                    </logic:notEmpty>
				</template:formTr>
	            
				 <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
                 
			     <template:formTr name="审批结果">
					  <bean:write name="reqinfo" property="auditresult"/>
				</template:formTr>
                 
			    <template:formTr name="审批备注">
					   <bean:write name="reqinfo" property="auditremark"/>
				</template:formTr>
	            
	            <template:formTr name="审批附件">
	                <logic:empty name="reqinfo" property="auditacce">
	                    <apptag:listAttachmentLink fileIdList=""/>
	                </logic:empty>
	                <logic:notEmpty name="reqinfo" property="auditacce">
	                    <bean:define id="tema" name="reqinfo" property="auditacce" type="java.lang.String"/>
						<apptag:listAttachmentLink fileIdList="<%=tema%>"/>
	                </logic:notEmpty>
				</template:formTr>
	            
	             <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
				
	            <logic:notEmpty name="reqinfo" property="essetime">
			        <template:formTr name="施工时间">
					    <bean:write name="reqinfo" property="essetime"/>
					</template:formTr>
	            </logic:notEmpty>
	
	            <template:formTr name="割接前衰耗">
			      <bean:write name="reqinfo" property="provalue"/>分贝(db)
			    </template:formTr>
	
	            <template:formTr name="割接后衰耗">
					<bean:write name="reqinfo" property="endvalue"/>分贝(db)
				</template:formTr>
				
			    <template:formTr name="所耗人力">
					 <bean:write name="reqinfo" property="manpower"/>工日
				</template:formTr>
	
	            <template:formTr name="割接用时">
					<bean:write name="reqinfo" property="usetime"/>小时
				</template:formTr>
	            
	            <template:formTr name="施工单位">
			         <bean:write name="reqinfo" property="contractorname"/>
			    </template:formTr>
			    
			     <template:formTr name="施工备注">
				 	<bean:write name="reqinfo" property="workremark"/>
				 </template:formTr>
			    
			     <template:formTr name="施工附件">
	                 <logic:empty name="reqinfo" property="workacce">
	                     	<apptag:listAttachmentLink fileIdList=""/>
	                 </logic:empty>
	                 <logic:notEmpty name="reqinfo" property="workacce">
	                    	<bean:define id="temw" name="reqinfo" property="workacce" type="java.lang.String"/>
						   	<apptag:listAttachmentLink fileIdList="<%=temw%>"/>
	                 </logic:notEmpty>
				</template:formTr>
			    
			    <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
                
			     <template:formTr name="验收备注">
	                    <bean:write name="reqinfo" property="acceptremark"/>
				 </template:formTr>
	            
			     <template:formTr name="验收附件">
	                 <logic:empty name="reqinfo" property="acceptacce">
	                     <apptag:listAttachmentLink fileIdList=""/>
	                 </logic:empty>
	                 <logic:notEmpty name="reqinfo" property="acceptacce">
	                    <bean:define id="tema" name="reqinfo" property="acceptacce" type="java.lang.String"/>
						<apptag:listAttachmentLink fileIdList="<%=tema%>"/>
	                 </logic:notEmpty>
				</template:formTr>
                 
                  <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
                 
	             <template:formTr name="当前状态">
			      	<bean:write name="reqinfo" property="isarchive"/>
			    </template:formTr>
	
			    <template:formTr name="归档文件">
	            	<logic:empty name="reqinfo" property="archives">
	                	<apptag:listAttachmentLink fileIdList=""/>
	                 </logic:empty>
	                 <logic:notEmpty name="reqinfo" property="archives">
	                    <bean:define id="temar" name="reqinfo" property="archives" type="java.lang.String"/>
						<apptag:listAttachmentLink fileIdList="<%=temar%>"/>
	                 </logic:notEmpty>
				</template:formTr>
			  </template:formTable>
	
			   <p align="center"><input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"></p>
		
	</logic:equal>


       <!--条件查询页面-->
	    <logic:equal value="au3" name="type"scope="session" >
	    	<script type="text/javascript" src="./js/json2.js"></script>
			<script type="text/javascript" src="./js/prototype.js"></script>
	    	<script type="text/javascript" language="javascript">
				//级联..............
				function onSelChangeLevel() {
					var ops = document.getElementById('line');
					ops.options.length = 0;
					var clewOps = document.getElementById('clew');
					clewOps.options.length = 0;
					var newClewOp = document.createElement("option");
					newClewOp.value = "";
					newClewOp.text = "===选择所有段名===";
					clewOps.add(newClewOp);
					var params = document.getElementById('level').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getLineByLevelId&levelId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callback, asynchronous:true});
				}
				
				function callback(originalRequest) {
					var ops = document.getElementById('line');
					var rst = originalRequest.responseText;
					var linearr = eval('('+rst+')');
					if(linearr.length == 0) {
						alert("该级别尚未制定线路!");
						return;
					}
					ops.options.add(new Option('===请选择线路===','mention'));
					for(var i = 0; i < linearr.length; i++) {
						ops.options.add(new Option(linearr[i].linename,linearr[i].lineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("网络连接出现问题，请关闭后重试!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
				
				function onSelChangeLine() {
					var ops = document.getElementById('clew');
					var length = ops.length;
					ops.options.length = 0;//清空下拉列表
					ops.options.add(new Option("===选择所有段名===",""));
					var params = document.getElementById('line').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getClewByLineId&lineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServer, asynchronous:true});
				}
				
				function callbackToServer(originalRequest) {
					var ops = document.getElementById('clew');
					var rst = originalRequest.responseText;
					var clewarr = eval('('+rst+')');
					if(clewarr.length == 0) {
						alert("该路线尚未制定线段!");
						return;
					}
					for(var i = 0; i < clewarr.length; i++) {
						ops.options.add(new Option(clewarr[i].sublinename,clewarr[i].sublineid));
					}
					var myGlobalHandlers = {onCreate:function () {
					Element.show("Loadingimg");
					}, onFailure:function () {
						alert("网络连接出现问题，请关闭后重试!");
					}, onComplete:function () {
						if (Ajax.activeRequestCount == 0) {
							Element.hide("Loadingimg");
						}
					}};
					Ajax.Responders.register(myGlobalHandlers);
				}
				
				function onSelectChangeClew() {
					var ops = document.getElementById('cutLineName');
					var length = ops.length;
					ops.options.length = 0;//清空下拉列表
					ops.options.add(new Option("===选择所有名称===",""));
					var params = document.getElementById('clew').value;
					if(params == "mention") {
						ops.options.add(new Option("",""));
					}
					var url = 'LineCutReAction.do?method=getNameBySublineidAndDeptid&sublineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServerByName, asynchronous:true});
				}
				
				function callbackToServerByName(originalRequest) {
					var ops = document.getElementById('cutLineName');
					var rst = originalRequest.responseText;
					var nameArr = eval('('+rst+')');
					if(nameArr.length == 0) {
						alert("该路线下没有割接或者还没有归档!");
						return;
					}
					for(var i = 0; i < nameArr.length; i++) {
						ops.options.add(new Option(nameArr[i].name,nameArr[i].name));
					}
				}
				
	    	</script>
	    	
	      	<br />
	        <template:titile value="按条件查找割接信息"/>
	        <html:form action="/LineCutWorkAction?method=doQueryForAr"   styleId="queryForm2" >
	        	<template:formTable namewidth="200"  contentwidth="200">

                  	
                  	<template:formTr name="线路级别"  >
	            		<select name="level"   class="inputtext" style="width:180px" onchange="onSelChangeLevel()" id="level">
				        	<option  value="mention">===请选择线路级别===</option>
			                	<logic:iterate id="linelevel" name="linelevelList">
			                		<bean:write name="linelevel" property="name"/>
			                    	<option value="<bean:write name="linelevel" property="code"/>"><bean:write name="linelevel" property="name"/></option>
			                	</logic:iterate>
			    		</select>
			    		<img id="Loadingimg" src="./images/ajaxtags/indicator.gif" style="display:none">
	                </template:formTr>
	                
	                <template:formTr name="线路名称"  >
	            		<select class="inputtext" id="line" onchange="onSelChangeLine()" name="line" style="width: 180px;">
		  					<option></option>
			    		</select>
	                </template:formTr>
	                
	               
	              	<template:formTr name="中继段名"  >
	            		<select name="sublineid"   class="inputtext" style="width:180px" id="clew" onchange="onSelectChangeClew()">
				        	<option value="">===选择所有段名===</option>
			    		</select>
	                </template:formTr>
	                
	                 <template:formTr name="割接名称"  >
	            		<select name="name"   class="inputtext" style="width:180px" id="cutLineName">
				        	<option  value="">===选择所有割接===</option>
			    		</select>
	                </template:formTr>
	            
	                
					<input type="hidden" name="isarchive" value="已经归档" >
	                <template:formTr name="割接开始时间">
	                	<input   id="begin" type="text" readonly="readonly"  name="begintime" class="inputtext" style="width:150" />
	                    <INPUT TYPE='BUTTON' VALUE='日期'  readonly="readonly" ID='btn'  onclick="GetSelectDateTHIS('begin')" STYLE="font:'normal small-caps 6pt serif';" >
	                </template:formTr>
	                <template:formTr name="割接结束时间">
	                	<input  id="end" type="text"  name="endtime" class="inputtext" style="width:150" />
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




        <!--显示处于施工阶段的割接信息-->
    <logic:equal value="working1"scope="session" name="type">
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"  />
            <table width="100%" border="0" style="align:center" cellpadding="0" cellspacing="0"><tr><td valign="middle" align="center" class="title2">当前割接信息</td></tr></table>
    		<display:table name="sessionScope.reqinfo" id="currentRowObject"   pagesize="2">
            	<display:column property="contractorname" title="割接单位" maxLength="20" style="align:center"  sortable="true"/>
            	<display:column property="name" title="割接名称" maxLength="20" style="align:center"sortable="true"/>
                <display:column property="sublinename" title="光缆线段" maxLength="15"style="align:center"sortable="true"/>
                <display:column property="address" title="割接地点" maxLength="10"sortable="true"/>
                <display:column property="reason" title="割接原因" maxLength="13"sortable="true"/>
                <display:column property="isarchive" title="工作阶段" maxLength="10"sortable="true"/>

    		</display:table>
    </logic:equal>





	<iframe id="hiddenIframe" style="display:none"></iframe>
</body>

</html>
