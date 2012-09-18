<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.linecut.beans.LineCutBean"%>


<html>
	<head>
		<script type="text/javascript" src="./js/prototype.js"></script>
		<script type="text/javascript" src="./js/json2.js"></script>
		<style type="text/css">
.trStyle {
	background: #ffffff;
}

.fileStyle {
	width: 240px;
	height: 20px;
	line-height: 20px;
}

.selTd {
	text-align: center;
}
</style>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<script type="" language="javascript">

	

	 
  //添加返回
    function addGoBack(){
    	try{
            	location.href = "${ctx}/LineCutReAction.do?method=showAllRe";
                return true;
            }
            catch(e){
            	alert(e);
            }
    }
      //添加审批返回
    function addGoBackForAu(){
    	try{
            	location.href = "${ctx}/LineCutReAction.do?method=auditShow";
                return true;
            }
            catch(e){
            	alert(e);
            }
    }
      //显示页面详细信息按钮动作
   function toGetForm(idValue){
     	var url = "${ctx}/LineCutReAction.do?method=showOneInfo&reid=" + idValue;
        window.location.href=url;
    }
	//修改按钮动作
   function toUpForm(idValue,isarchive){
        if(isarchive!="待审批"&&isarchive!="未通过审批"){
          alert("该申请已被审批，不能修改！");
        }else{
          var url = "${ctx}/LineCutReAction.do?method=upshow&reid=" + idValue;
          window.location.href=url;
        }
    }
    	//删除修改按钮动作
   function toDelForm(id,reacce,state){
        if(state!="待审批"&&state!="未通过审批"){
          alert("该申请已被审批，不能删除！");
        }
        else{
          var url = "${ctx}/LineCutReAction.do?method=doDel&reid=" + id +"&reacce=" + reacce;
          if(confirm("确定删除该纪录？")){
            window.location.href=url;
          }
        }
    }
    //查看审批详细按钮动作
 	function toGetOneAuForm(idValue){
     	var url = "${ctx}/LineCutReAction.do?method=showOneAuInfo&reid=" + idValue;
        window.location.href=url;
    }
     //查看审批详细返回动作
 	function  AuShowGoBack(){
     	var url = "${ctx}/LineCutReAction.do?method=showAllAu";
        window.location.href=url;
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
    //=======shenpi=======

	//待审批页面审批按钮动作
   function toGetFormForAu(idValue){
     	var url = "${ctx}/LineCutReAction.do?method=showOneInfoForAu&reid=" + idValue;
        window.location.href=url;
    }

 //选择日期
    function GetSelectDateTHIS(strID) {
    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
    	return ;
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

	

	//提交前验证
	function addresub(){
		var reason = document.getElementById('cutreason').value;
		if(reason.length == 0 ) {
			alert("请输入割接原因!");
			document.getElementById('cutreason').focus();
			return false;
		}
		if(valCharLength(reason)>512) {
			alert("输入的割接原因过长!");
			document.getElementById('cutreason').focus();
			return false;
		}
		var address = document.getElementById('linId').value;
		if(address.length == 0 ) {
			alert("请输入割接地点!");
			document.getElementById('linId').focus();
			return false;
		}
		if(valCharLength(address)>100) {
			alert("输入的割接地点过长!");
			document.getElementById('cutreason').focus();
			return false;
		}
		var efsystem = document.getElementById('efsystem').value;
			if(efsystem.length == 0) {
			alert("请输入影响系统!");
			document.getElementById('efsystem').focus();
			return false;
		}
		if(valCharLength(efsystem)>200) {
			alert("输入的受影响系统过长!");
			document.getElementById('cutreason').focus();
			return false;
		}
      	if(LineCutBean.date.value == null || LineCutBean.date.value ==""){
	        alert("请填写施工的日期！");
	        return false;
        }
        if(LineCutBean.time.value == null || LineCutBean.time.value ==""){
	        alert("请填写施工的时间！");
	        return false;
        }
        if(valCharLength(LineCutBean.reremark.value)>512){
            if (!confirm("备注信息超过250个汉字或者512个英文字符，将被截去。请确认？")){
            	return false;
            }
        }
    	LineCutBean.protime.value = LineCutBean.date.value + " " + LineCutBean.time.value;
        LineCutBean.submit();
    }

	//打开模态窗口
	function showModDialog(sURL,height,width){
		var top = (screen.height-height)/2;   // 窗口与screen上端的距离
		var left = (screen.width-width)/2;    // 窗口与screen左端的距离
		var sFeatures = "dialogLeft:" + left + "px; dialogTop:" + top + "px; dialogWidth:" + width + "px; dialogHeight:" + height + "px; help:no; resizable:no; scroll:no; status:no"
		var ret = window.showModalDialog(sURL,self,sFeatures);
		return ret;
	}

	function resetInfo() {
		document.getElementById('lineinfo').innerHTML = '';
		document.getElementById('sublineid').value = '';
	}
	
	//动态上传附件
	function addRowMod() {
			var maxSeq = parseInt(document.getElementById('maxSeq').value);
			maxSeq ++;//自加
			var tableBody = document.getElementsByTagName("tbody")[0];
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
			var tableBody = document.getElementsByTagName('tbody')[0];
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

				function subRe() {
					var cutname = document.getElementById('cutname').value;
					if(cutname.length == 0 ) {
						alert("请输入割接名称!")
						document.getElementById('cutname').focus();
						return false;
					}
					var sublineidValue = document.getElementById('sublineid').value;
					if(sublineidValue.length == 0) {
						alert("请制定线路!");
						document.getElementById('lineMark').focus();
						return false;
					}
					if(sublineidValue.length > 3996) {
						alert("制定的线段涉及太多，最多可涉及444条线段!");
						document.getElementById('lineMark').focus();
						return false;
					}
					var reason = document.getElementById('cutreason').value;
					if(reason.length == 0 ) {
						alert("请输入割接原因!");
						document.getElementById('cutreason').focus();
						return false;
					}
					if(valCharLength(reason)>512) {
						alert("输入的割接原因过长!");
						document.getElementById('cutreason').focus();
						return false;
					}
					var address = document.getElementById('linId').value;
					if(address.length == 0 ) {
						alert("请输入割接地点!");
						document.getElementById('linId').focus();
						return false;
					}
					if(valCharLength(address)>100) {
						alert("输入的割接地点过长!");
						document.getElementById('cutreason').focus();
						return false;
					}
					var efsystem = document.getElementById('efsystem').value;
					if(efsystem.length == 0) {
						alert("请输入影响系统!");
						document.getElementById('efsystem').focus();
						return false;
					}
					if(valCharLength(efsystem)>200) {
						alert("输入的受影响系统过长!");
						document.getElementById('cutreason').focus();
						return false;
					}
					if(LineCutBean.date.value == null || LineCutBean.date.value ==""){
	       				alert("请填写施工的日期！");
	        			return false;
        			}
        			if(LineCutBean.time.value == null || LineCutBean.time.value ==""){
	        			alert("请填写施工的时间！");
	        			return false;
        			}
        			if(valCharLength(LineCutBean.reremark.value)>512){
            			if (!confirm("备注信息超过250个汉字或者512个英文字符，将被截去。请确认？")){
            				return false;
            			}
        			}
    				LineCutBean.protime.value = LineCutBean.date.value + " " + LineCutBean.time.value;
        			LineCutBean.submit();
				}
		
		function oneReGoBack() {
			var url = "LineCutReAction.do?method=doQueryAfterMod";
			window.location.href=url;
		}

		function auOneGoBack() {
			var url = "LineCutReAction.do?method=doQueryForAuAfterMod";
			window.location.href=url;
		}

	function cancel() {
  		this.close();
	}
</script>


		<title>partRequisition</title>
	</head>
	<body>
		<logic:equal value="r2" name="type">

			<br>
			<template:titile value="填写线路割接申请" />
			<html:form action="/LineCutReAction?method=addRe"
				styleId="addApplyForm" enctype="multipart/form-data">

				<html:hidden property="protime" value="" />

				<template:formTable namewidth="100" contentwidth="500" th2="内容"
					th1="项目">
					<template:formTr name="割接名称">
						<html:text property="name" styleId="cutname" value=""
							styleClass="inputtext" style="width:270;" maxlength="50" />
					</template:formTr>

					<template:formTr name="线路制定">
						<html:button property="lineMark" styleId="lineMark"
							styleClass="button"
							onclick="showModDialog('LineCutReAction.do?method=getLineLevle',300,460)"
							value="制定线路" />
					</template:formTr>

					<template:formTr name="线路信息">
						<font id="lineinfo"></font>
						<html:hidden property="sublineid" styleId="sublineid" />
					</template:formTr>

					<template:formTr name="割接原因">
						<html:textarea property="reason" styleId="cutreason" cols="36"
							rows="4" style="width:270" title="请不要超过250个汉字或者512个英文字符!"
							styleClass="textarea" />
						
					</template:formTr>

					<template:formTr name="割接地点">
						<%--<html:text property="address" value="" styleId="linId"
							styleClass="inputtext" style="width:200;" maxlength="50" />
						--%>
						<html:textarea property="address" styleId="linId" cols="36"
							rows="4" style="width:270" title="请不要超过50个汉字或者100个英文字符!"
							styleClass="textarea" />
					</template:formTr>

					<template:formTr name="影响系统" style="width:200">
						<html:textarea property="efsystem" styleId="efsystem" cols="36"
							rows="4" style="width:270" title="请不要超过100个汉字或者200个英文字符!"
							styleClass="textarea" />
					</template:formTr>

					<template:formTr name="原有衰耗" style="width:200">
						<html:text property="provalue" styleId="provalueid" value="0.00"
							onchange="valiD(id)" styleClass="inputtext" style="width:270;"
							maxlength="26" />分贝(db)
				    </template:formTr>

					<template:formTr name="预计用时">
						<html:text property="prousetime" styleId="prousetimeid"
							value="0.00" onchange="valiD(id)" styleClass="inputtext"
							style="width:270;" />小时
				    </template:formTr>

					<template:formTr name="预计日期">
						<input type="text" id="protimeid" name="date" value=""
							readonly="readonly" class="inputtext" style="width: 130" />
						<INPUT TYPE='BUTTON' VALUE='日期' ID='btn'
							onclick="GetSelectDateTHIS('protimeid')"
							STYLE="font: 'normal small-caps 6pt serif';">
						<html:text property="time" styleClass="inputtext" style="width:80"
							readonly="true" styleId="time" />
						<input type='button' value="时间" id='btn'
							onclick="getTimeWin('time')"
							style="font: 'normal small-caps 6pt serif';">
					</template:formTr>
					
					<template:formTr name="割接类型">
							<select name="cutType" class="inputtext" style="width: 270">
								<option value="新建割接">
									新建割接
								</option>
								<option value="优化割接">
									优化割接
								</option>
								<option value="修复性割接">
									修复性割接
								</option>
								<option value="迁改性割接">
									迁改性割接
								</option>
							</select>
						</template:formTr>
					
					<logic:equal value="send" name="isSendSm">
						<template:formTr name="短信接收人">
							<apptag:setSelectOptions columnName1="username"
								columnName2="phone" tableName="userinfo" valueName="objectman"
								region="true" condition="deptype='1' " />
							<html:select property="phone" styleClass="inputtext"
								style="width:270">
								<html:options collection="objectman" property="value"
									labelProperty="label" />
							</html:select>
						</template:formTr>
					</logic:equal>

					<template:formTr name="资料是否修改">
						<select name="UPDOC" class="inputtext" style="width: 270" id="mod">
							<option value="不需修改">
								不需修改
							</option>
							<option value="需要修改">
								需要修改
							</option>
						</select>
					</template:formTr>

					<template:formTr name="申请备注" style="width:270">
						<html:textarea property="reremark" styleId="remark" cols="36"
							rows="4" style="width:270" title="请不要超过250个汉字或者512个英文字符，将被截去。"
							styleClass="textarea" />
					</template:formTr>

					<table id="uploadID" width=500 border="0" align="center"
						cellpadding="3" cellspacing="0" class="tabout">
						<tbody></tbody>
					</table>

					<template:formSubmit>
						<td>
							<input type="checkbox" onclick="checkOrCancel()" id="sel">
							全选&nbsp;
						</td>
						<td>
							<input type="hidden" value="0" id="maxSeq">
							<html:button property="action" styleClass="button"
								onclick="addRowMod()">添加附件</html:button>
						</td>
						<td>
							<input type="button" value="删除选中" onclick="delRow()"
								class="button">
						</td>
						<td>
							<html:button styleClass="button" onclick="subRe()" property="">提 交</html:button>
						</td>
						<td>
							<input type="reset" value="重 置" class="button"
								onclick="resetInfo()">
						</td>
					</template:formSubmit>

				</template:formTable>
			</html:form>
		</logic:equal>

		<!--显示申请单-->
		<logic:equal value="r1" scope="session" name="type">
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<br />
			<%
				DynaBean object1=null;
				String id1= null;
				String numerical = null;
				String name = null;
				String isarchive = null;
				String reacced = null;
				String state = null;
			 %>
			<template:titile value="割接申请一览表" />
			<display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutReAction.do?method=doQuery" id="currentRowObject"
				pagesize="18">
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
							isarchive = (String)object1.get("isarchive");
							reacced=(String) object1.get("reacce");
                        	state=(String)object1.get("isarchive");
						}
					 %>
					 <a href="javascript:toGetForm('<%=id1%>')"><%=numerical %></a>
				</display:column>
				<display:column media="html" title="割接名称" sortable="true">
					  <a href="javascript:toGetForm('<%=id1%>')"><%=name %></a>
				</display:column>
				<display:column property="sublinename" title="中继段" maxLength="10"
					style="align:center" sortable="true" />
				<display:column property="address" title="割接地点" maxLength="5"
					sortable="true" />
				<display:column property="protime" title="计划割接时间" maxLength="20"
					style="align:center" sortable="true" />
				<display:column property="prousetime" title="计划用时" maxLength="10"
					style="align:center" sortable="true" />
				<display:column media="html" title="当前状态" sortable="true">
					<%if("未通过审批".equals(isarchive)) {%>
						<font color="red"><%=isarchive %></font>
					<%} else if("待审批".equals(isarchive)) {%>
						<font color="#FFA500"><%=isarchive %></font>
					<%} else if("通过审批".equals(isarchive)) {%>
						<%=isarchive %>
					<%} else if("正在施工".equals(isarchive)) {%>
						<%=isarchive %>
					<%} else if("施工完毕".equals(isarchive)) {%>
						<%=isarchive %>
					<%} else if("已经归档".equals(isarchive)) {%>
						<%=isarchive %>
					<%} %>
				</display:column>
				<display:column media="html" title="操作">
					<apptag:checkpower thirdmould="30104" ishead="0">
					<%
						if("待审批".equals(isarchive) || "未通过审批".equals(isarchive)) { %>
					    	<a href="javascript:toUpForm('<%=id1%>','<%=isarchive%>')">修改</a> 
					  <% } %>
					  </apptag:checkpower>
					  <apptag:checkpower thirdmould="30105" ishead="0">
					  <%
					  	if("待审批".equals(isarchive) || "未通过审批".equals(isarchive)) { %>
					  		| <a href="javascript:toDelForm('<%=id1%>','<%=reacced%>','<%=state%>')">删除</a>
					  <% } %>
					  </apptag:checkpower>
				</display:column>
			</display:table>
			<html:link action="/LineCutWorkAction.do?method=exportLineCutRe">导出为Excel文件</html:link>
		</logic:equal>

		<!--显示申请单的详细信息-->
		<logic:equal value="r10" name="type">
			<BR>
			<template:titile value="线路割接申请详细信息" />
			<template:formTable namewidth="200" contentwidth="400"  th2="内容" th1="项目">
				<template:formTr name="申请单位">
					<bean:write name="reqinfo" property="contractorname" />
				</template:formTr>
				<template:formTr name="申 请 人">
					<bean:write name="reqinfo" property="username" />
				</template:formTr>
				<template:formTr name="申请日期">
					<bean:write name="reqinfo" property="retime" />
				</template:formTr>
				<template:formTr name="割接名称">
					<bean:write name="reqinfo" property="name" />
				</template:formTr>
				<template:formTr name="中继段名">
					<%
		    		String sublinenamecon = ((LineCutBean) request.getAttribute("reqinfo")).getSublinename();
					String[] atrArr = sublinenamecon.split("<br>");
					for(int i = 0; i < atrArr.length; i++ ) { %>
					<%=atrArr[i] %>;<br>
					<%}%>
				</template:formTr>
				<template:formTr name="割接原因">
					<bean:write name="reqinfo" property="reason" />
				</template:formTr>
				<template:formTr name="割接地点">
					<bean:write name="reqinfo" property="address" />
				</template:formTr>
				<template:formTr name="割接类型">
					<bean:write name="reqinfo" property="cutType" />
				</template:formTr>
				<template:formTr name="计划日期">
					<bean:write name="reqinfo" property="protime" />
				</template:formTr>
				<template:formTr name="预计用时">
					<bean:write name="reqinfo" property="prousetime" />小时
		    </template:formTr>
				<template:formTr name="割接前衰耗">
					<bean:write name="reqinfo" property="provalue" />分贝(db)
		    </template:formTr>
				<template:formTr name="受影响系统">
					<bean:write name="reqinfo" property="efsystem" />
				</template:formTr>
				<template:formTr name="修改资料">
					<bean:write name="reqinfo" property="updoc" />
				</template:formTr>
				<template:formTr name="申请备注">
					<bean:write name="reqinfo" property="reremark" />
				</template:formTr>
				<template:formTr name="申请附件">
					<%
		    	String reacce="";
		        LineCutBean bean = (LineCutBean )request.getAttribute("reqinfo");
		        reacce = bean.getReacce();
		        if(reacce==null){
		        	reacce="";
		        }
		 	%>
					<apptag:listAttachmentLink fileIdList="<%=reacce%>" />
				</template:formTr>

				<logic:equal value="" name="reqinfo" property="auditresult">
					<template:formTr name="审批结果">
	            	待审批
	            </template:formTr>
				</logic:equal>

				<logic:notEqual value="" name="reqinfo" property="auditresult">
					<logic:notEqual value="未通过审批" name="reqinfo" property="auditresult">
						<template:formTr name="审批结果">
							<bean:write name="reqinfo" property="auditresult" />
						</template:formTr>
						<template:formTr name="审批时间">
							<bean:write name="reqinfo" property="audittime" />
						</template:formTr>
						<template:formTr name="审 批 人">
							<bean:write name="reqinfo" property="audituserid" />
						</template:formTr>
						<template:formTr name="审批备注">
							<bean:write name="reqinfo" property="auditremark" />
						</template:formTr>
						<template:formTr name="审批附件">
							<%
					    	String auacce="";
	                        LineCutBean beanau = (LineCutBean )request.getAttribute("reqinfo");
					        auacce = beanau.getAuditacce();
					        if(auacce==null){
					        	auacce="";
					        }
					 	%>
							<apptag:listAttachmentLink fileIdList="<%=auacce%>" />
						</template:formTr>
					</logic:notEqual>

					<tr class=trcolor>
						<td>
							&nbsp
						<td>
					</tr>
					<logic:equal value="1" name="apphavemore">
						<logic:iterate id="approve" name="reapprove">
							<template:formTr name="审批结果">
								<bean:write name="approve" property="auditresult" />
							</template:formTr>
							<template:formTr name="审批时间">
								<bean:write name="approve" property="audittime" />
							</template:formTr>
							<template:formTr name="审 批 人">
								<bean:write name="approve" property="username" />
							</template:formTr>
							<template:formTr name="审批备注">
								<bean:write name="approve" property="auditremark" />
							</template:formTr>
							<template:formTr name="审批附件">
								<logic:equal value="" name="approve" property="auditacce">
                                	无附件
                            	</logic:equal>
								<logic:notEqual value="" name="approve" property="auditacce">
									<bean:define id="appacce" name="approve" property="auditacce"
										type="java.lang.String" />
									<apptag:listAttachmentLink fileIdList="<%=appacce%>" />
								</logic:notEqual>
								<tr class=trcolor>
									<td>
										&nbsp
									<td>
								</tr>
							</template:formTr>
						</logic:iterate>
					</logic:equal>

				</logic:notEqual>

			</template:formTable>
			<p align="center">
				<input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
			</p>
		</logic:equal>

		<!--修改申请-->
		<logic:equal value="r4" name="type">
			<br>
			<template:titile value="修改线路割接申请" />
			<html:form action="/LineCutReAction?method=doUp"
				styleId="addApplyForm" enctype="multipart/form-data">
				<html:hidden name="reqinfo" property="reid" />
				<html:hidden name="reqinfo" property="reacce" />
				<html:hidden property="protime" value="" />
				<%
                	LineCutBean linecutbean = (LineCutBean)request.getAttribute("reqinfo");
                 %>
				<template:formTable namewidth="100" contentwidth="400" th1="内容"
					th2="填写">
					<template:formTr name="割接名称">
						<%=linecutbean.getName()%>
						<html:hidden name="reqinfo" property="name"
							value="<%=linecutbean.getName() %>" />
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
						<html:textarea property="reason" name="reqinfo" styleId="cutreason" cols="36"
							rows="4" style="width:270" title="请不要超过250个汉字或者512个英文字符!"
							styleClass="textarea" />
						<%--<html:text name="reqinfo" property="reason"
							title="<%=linecutbean.getReason() %>" styleClass="inputtext"
							style="width:200;" maxlength="256" />
					--%>
					</template:formTr>

					<template:formTr name="割接地点">
						<html:textarea property="address" name="reqinfo" styleId="linId" cols="36"
							rows="4" style="width:270" title="请不要超过50个汉字或者100个英文字符!"
							styleClass="textarea" />
						<%--<html:text name="reqinfo" property="address"
							title="<%=linecutbean.getAddress() %>" styleId="linId"
							styleClass="inputtext" style="width:200;" maxlength="50" />
					--%>
					</template:formTr>
					<template:formTr name="影响系统" style="width:200">
						<html:textarea property="efsystem" name="reqinfo" styleId="efsystem" cols="36"
							rows="4" style="width:270" title="请不要超过100个汉字或者200个英文字符!"
							styleClass="textarea" />
						<%--<html:text name="reqinfo" property="efsystem"
							title="<%=linecutbean.getEfsystem() %>" styleClass="inputtext"
							style="width:200;" maxlength="100" />
					--%>
					</template:formTr>
					
					<template:formTr name="割接类型">
							<select name="cutType" class="inputtext" style="width: 270" id="cutType">
								<logic:equal value="新建割接" property="cutType" name="reqinfo">
									<option value="新建割接" selected="selected">
										新建割接
									</option>
									<option value="优化割接">
										优化割接
									</option>
									<option value="修复性割接">
										修复性割接
									</option>
									<option value="迁改性割接">
										迁改性割接
									</option>
								</logic:equal>
								<logic:equal value="优化割接" property="cutType" name="reqinfo">
									<option value="新建割接">
										新建割接
									</option>
									<option value="优化割接"  selected="selected">
										优化割接
									</option>
									<option value="修复性割接">
										修复性割接
									</option>
									<option value="迁改性割接">
										迁改性割接
									</option>
								</logic:equal>
								<logic:equal value="修复性割接" property="cutType" name="reqinfo">
									<option value="新建割接">
										新建割接
									</option>
									<option value="优化割接">
										优化割接
									</option>
									<option value="修复性割接"   selected="selected">
										修复性割接
									</option>
									<option value="迁改性割接">
										迁改性割接
									</option>
								</logic:equal>
								<logic:equal value="迁改性割接" property="cutType" name="reqinfo">
									<option value="新建割接">
										新建割接
									</option>
									<option value="优化割接">
										优化割接
									</option>
									<option value="修复性割接">
										修复性割接
									</option>
									<option value="迁改性割接"   selected="selected">
										迁改性割接
									</option>
								</logic:equal>
							</select>
						</template:formTr>
					
					<template:formTr name="原有衰耗" style="width:270">
						<html:text name="reqinfo" property="provalue" styleId="provalueid"
							onchange="valiD(id)" styleClass="inputtext" style="width:270;"
							maxlength="26" title="<%=linecutbean.getProvalue() %>" />分贝(db)
				    </template:formTr>
					<template:formTr name="预计用时">
						<html:text property="prousetime" name="reqinfo"
							title="<%=linecutbean.getProusetime() %>" styleId="prousetimeid"
							onchange="valiD(id)" styleClass="inputtext" style="width:270;" />(小时)
				    </template:formTr>

					<template:formTr name="预计日期">
						<% String temp1 = ((LineCutBean)request.getAttribute("reqinfo")).getProtime();  %>
						<input type="text" id="protimeid" name="date"
							value="<%=temp1.substring(0,10)%>" readonly="readonly"
							class="inputtext" style="width: 130"
							title="<%=temp1.substring(0,10)%>" />
						<INPUT TYPE='BUTTON' VALUE='日期' ID='btn'
							onclick="GetSelectDateTHIS('protimeid')"
							STYLE="font: 'normal small-caps 6pt serif';">
						<html:text property="time" value="<%= temp1.substring(11,16)%>"
							styleClass="inputtext" style="width:80" readonly="true"
							title="<%= temp1.substring(11,16)%>" />
						<input type='button' value="时间" id='btn'
							onclick="getTimeWin('time')"
							style="font: 'normal small-caps 6pt serif';">

					</template:formTr>
					<template:formTr name="申请备注" style="width:270">
						<html:textarea name="reqinfo" property="reremark"
							title="请不要超过250个汉字或者512个英文字符，否则将截断。" cols="36" rows="4"
							style="width:270" styleClass="textarea" />
					</template:formTr>
					<template:formTr name="附件">
						<%
				    	String reacce1="";
				        LineCutBean bean1 = (LineCutBean )request.getAttribute("reqinfo");
				        reacce1 = bean1.getReacce();
				        if(reacce1==null){
				        	reacce1="";
				        }
				 	%>
						<apptag:listAttachmentLink fileIdList="<%=reacce1%>"
							showdele="true" />
					</template:formTr>
					<table id="uploadID" width=400 border="0" align="center"
						cellpadding="3" cellspacing="0" class="tabout">
						<tbody></tbody>
					</table>
					<template:formSubmit>
						<td>
							<input type="checkbox" onclick="checkOrCancel()" id="sel">
							全选&nbsp;
						</td>
						<td>
							<input type="hidden" value="0" id="maxSeq">
							<html:button property="action" styleClass="button"
								onclick="addRowMod()">添加附件</html:button>
						</td>
						<td>
							<input type="button" value="删除选中" onclick="delRow()"
								class="button">
						</td>
						<td>
							<html:button styleClass="button" onclick="addresub()" property="">提交</html:button>
						</td>
						<td>
							<input type="reset" value="重 置" class="button">
						</td>
						<td>
							<input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
						</td>
					</template:formSubmit>
				</template:formTable>
			</html:form>
		</logic:equal>

		<!--条件查询页面-->
		<logic:equal value="r3" name="type" scope="session">
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
					var url = 'LineCutReAction.do?method=getCutNameBySublineid&sublineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServerByName, asynchronous:true});
				}
				
				function callbackToServerByName(originalRequest) {
					var ops = document.getElementById('cutLineName');
					var rst = originalRequest.responseText;
					var nameArr = eval('('+rst+')');
					if(nameArr.length == 0) {
						alert("该路线下没有割接申请信息!");
						return;
					}
					for(var i = 0; i < nameArr.length; i++) {
						ops.options.add(new Option(nameArr[i].name,nameArr[i].name));
					}
				}
				
	    	</script>
			<br />
			<template:titile value="按条件查找割接申请" />
			<html:form action="/LineCutReAction?method=doQuery"
				styleId="queryForm2">
				<template:formTable namewidth="200" contentwidth="200">
					<template:formTr name="申请操作人">
						<select name="reuserid" class="inputtext" style="width: 180px">
							<option value="">
								===选择所有人员===
							</option>
							<logic:present name="lusers">
								<logic:iterate id="lusersId" name="lusers">
									<option
										value="<bean:write name="lusersId" property="reuserid"/>">
										<bean:write name="lusersId" property="username" />
									</option>
								</logic:iterate>
							</logic:present>
						</select>
					</template:formTr>

					<template:formTr name="线路级别">
						<select name="level" class="inputtext" style="width: 180px"
							onchange="onSelChangeLevel()" id="level">
							<option value="mention">
								===请选择线路级别===
							</option>
							<logic:iterate id="linelevel" name="linelevelList">
								<bean:write name="linelevel" property="name" />
								<option value="<bean:write name="linelevel" property="code"/>">
									<bean:write name="linelevel" property="name" />
								</option>
							</logic:iterate>
						</select>
						<img id="Loadingimg" src="./images/ajaxtags/indicator.gif"
							style="display: none">
					</template:formTr>

					<template:formTr name="线路名称">
						<select class="inputtext" id="line" onchange="onSelChangeLine()"
							name="line" style="width: 180px;">
							<option></option>
						</select>
					</template:formTr>

					<template:formTr name="中继段名">
						<select name="sublineid" class="inputtext" style="width: 180px"
							id="clew" onchange="onSelectChangeClew()">
							<option value="">
								===选择所有段名===
							</option>
						</select>
					</template:formTr>


					<template:formTr name="割接名称">
						<select name="name" class="inputtext" style="width: 180px"
							id="cutLineName">
							<option value="">
								===选择所有割接===
							</option>
						</select>
					</template:formTr>

					<template:formTr name="申请开始时间">
						<input id="begin" type="text" readonly="readonly" name="begintime"
							class="inputtext" style="width: 150" />
						<INPUT TYPE='BUTTON' VALUE='日期' readonly="readonly" ID='btn'
							onclick="GetSelectDateTHIS('begin')"
							STYLE="font: 'normal small-caps 6pt serif';">
					</template:formTr>
					<template:formTr name="申请结束时间">
						<input id="end" type="text" name="endtime" readonly="readonly"
							class="inputtext" style="width: 150" />
						<INPUT TYPE='BUTTON' VALUE='日期' ID='btn'
							onclick="GetSelectDateTHIS('end')"
							STYLE="font: 'normal small-caps 6pt serif';">
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
		<!--=========================审批管理=================================-->
		<!--显示待审批申请单-->
		<logic:equal value="reforAu1" scope="session" name="type">
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<br />
			<%
				DynaBean object=null;
				String id= null;
				String numerical = null;
				String name = null;
			%>
			<template:titile value="待审批割接申请一览表" />
			<display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutReAction.do?method=auditShow" id="currentRowObject"
				pagesize="18">
				<display:column media="html" title="流水号" sortable="true">
    				<%
						object = (DynaBean)pageContext.getAttribute("currentRowObject");
						if(object != null) {
							id = (String) object.get("reid");
							numerical = (String)object.get("numerical");
							numerical = numerical == null?"":numerical;
							name =  (String)object.get("name");
							if(name != null) {
								name = name.length() < 10 ? name: name.substring(0,10)+"...";
							} else {
								name = "";
							}
						}
					 %>
					 <a href="javascript:toGetFormForAu('<%=id%>')"><%=numerical %></a>
    			</display:column>
				<display:column media="html"  title="割接名称" sortable="true">
					  <a href="javascript:toGetFormForAu('<%=id%>')"><%=name %></a>
            	</display:column>
				<display:column property="sublinename" title="光缆中继段" maxLength="10"
					style="align:center" sortable="true" />
				<display:column property="prousetime" title="计划用时" maxLength="10"
					style="align:center" sortable="true" />
				<display:column property="protime" title="计划割接时间" maxLength="18"
					style="align:center" sortable="true" />
				<display:column property="address" title="割接地点" maxLength="10"
					sortable="true" />
			</display:table>
		</logic:equal>


		<!--审批页面-->
		<logic:equal value="au2" name="type">
			<script type="text/javascript">
		//动态上传附件
	function addRowModAu() {
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
		
		function checkOrCancelAu() {
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
		
		
		function delRowAu() {
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
			<BR>
			<template:titile value="线路割接申请信息" />
			<template:formTable namewidth="100" contentwidth="400"  th2="内容" th1="项目">
				<template:formTr name="申请单位">
					<bean:write name="reqinfo" property="contractorname" />
				</template:formTr>
				<template:formTr name="申请操作人">
					<bean:write name="reqinfo" property="username" />
				</template:formTr>
				<template:formTr name="申请日期">
					<bean:write name="reqinfo" property="retime" />
				</template:formTr>
				<template:formTr name="割接名称">
					<bean:write name="reqinfo" property="name" />
				</template:formTr>
				<template:formTr name="中继段名">
					<%
		    		String sublinenamecon = ((LineCutBean) request.getAttribute("reqinfo")).getSublinename();
					String[] atrArr = sublinenamecon.split("<br>");
					for(int i = 0; i < atrArr.length; i++ ) { %>
					<%=atrArr[i] %>;<br>
					<%}%>
				</template:formTr>
				<template:formTr name="割接原因">
					<bean:write name="reqinfo" property="reason" />
				</template:formTr>
				<template:formTr name="割接地点">
					<bean:write name="reqinfo" property="address" />
				</template:formTr>
				<template:formTr name="割接类型">
					<bean:write name="reqinfo" property="cutType" />
				</template:formTr>
				<template:formTr name="计划割接日期">
					<bean:write name="reqinfo" property="protime" />
				</template:formTr>
				<template:formTr name="预计割接用时">
					<bean:write name="reqinfo" property="prousetime" />小时
		    </template:formTr>
				<template:formTr name="割接前衰耗">
					<bean:write name="reqinfo" property="provalue" />分贝(db)
		    </template:formTr>
				<template:formTr name="受影响系统">
					<bean:write name="reqinfo" property="efsystem" />
				</template:formTr>
				<template:formTr name="申请备注">
					<bean:write name="reqinfo" property="reremark" />
				</template:formTr>
				<template:formTr name="修改资料">
					<bean:write name="reqinfo" property="updoc" />
				</template:formTr>
				<template:formTr name="附件">
					<%
		    	String reacce="";
		        LineCutBean bean = (LineCutBean )request.getAttribute("reqinfo");
		        reacce = bean.getReacce();
		        if(reacce==null){
		        	reacce="";
		        }
		 	%>
					<apptag:listAttachmentLink fileIdList="<%=reacce%>" />
				</template:formTr>
			</template:formTable>
			<hr />
			<html:form action="/LineCutReAction?method=addAu"
				styleId="addApplyForm" enctype="multipart/form-data">
				<html:hidden property="reid" name="reqinfo" />
				
				<html:hidden property="name" name="reqinfo" />
				<template:formTable namewidth="100" contentwidth="400" th2="填写"
					th1="项目">
					<template:formTr name="审批人">
						<bean:write name="LOGIN_USER" property="userName" />
						<input type="hidden" class="inputtext" readonly="readonly"
							value="<bean:write name="LOGIN_USER" property="userName"/>"
							style="width: 200" />
					</template:formTr>
					<template:formTr name="审批日期">
						<html:text property="audittime" readonly="true"
							style="width:200; border: 0px;background-color: #F5F5F5;">
							<apptag:date property="audittime">
							</apptag:date>
						</html:text>
					</template:formTr>
					<template:formTr name="审批结果">
						<html:select property="auditresult" styleClass="inputtext"
							style="width:270;">
							<html:option value="通过审批">通过审批	</html:option>
							<html:option value="未通过审批">未通过审批	</html:option>
						</html:select>
					</template:formTr>

					<template:formTr name="审批备注" style="width:270">
						<html:textarea property="auditremark" cols="36" rows="4"
							title="请不要超过256个汉字或者512个英文字符，否则将截断。" style="width:270"
							styleClass="textarea" />
					</template:formTr>

					<table id="uploadID" width=500 border="0" align="center"
						cellpadding="3" cellspacing="0" class="tabout">
						<tbody></tbody>
					</table>


					<template:formSubmit>
						<td>
							<input type="checkbox" onclick="checkOrCancelAu()" id="sel">
							全选&nbsp;
						</td>
						<td>
							<html:button property="action" styleClass="button"
								onclick="addRowModAu()">添加附件</html:button>
						</td>
						<td>
							<input type="button" value="删除选中" onclick="delRowAu()"
								class="button">
						</td>
						<td>
							<input type="hidden" value="0" id="maxSeq">
							<html:submit styleClass="button">提交</html:submit>
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
		</logic:equal>

		<!--显示审批单-->
		<logic:equal value="au1" scope="session" name="type">
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<BR>
			 <%
				DynaBean object2=null;
				String id2= null;
				String numerical = null;
				String name = null;
				String isarchive = null;
			%>
			<template:titile value="割接申请审批一览表" />
			<display:table name="sessionScope.reqinfo" requestURI="${ctx}/LineCutReAction.do?method=doQueryForAu" id="currentRowObject"
				pagesize="18">
				<display:column media="html" title="流水号" sortable="true" >
    				<%
						object2 = (DynaBean)pageContext.getAttribute("currentRowObject");
						if(object2 != null) {
							id2 = (String) object2.get("reid");
							numerical = (String)object2.get("numerical");
							numerical = numerical == null? "": numerical;
							name = (String) object2.get("name");
							if(name != null) {
								name = name.length() < 10 ? name: name.substring(0,10)+"...";
							} else {
								name = "";
							}
							isarchive = (String) object2.get("isarchive");
						}
					 %>
					 <a href="javascript:toGetOneAuForm('<%=id2%>')"><%=numerical %></a>
    			</display:column>
				
				<display:column media="html"  title="割接名称" sortable="true" >
					  <a href="javascript:toGetOneAuForm('<%=id2%>')"><%=name %></a>
            	</display:column>
            	
				<display:column property="sublinename" title="中继段" maxLength="15"
					style="align:center" sortable="true" />
				<display:column property="protime" title="计划割接时间" maxLength="18"
					style="align:center"  sortable="true" />
				<display:column property="address" title="割接地点" maxLength="10"
					sortable="true" />
				<display:column property="audittime" title="审批日期" maxLength="13"
					sortable="true" />
				<display:column media="html" title="当前状态" sortable="true" >
					  <%if("已经归档".equals(isarchive)) {%>
						<font color=""><%=isarchive %></font>
					  <%} 
					  else if("施工完毕".equals(isarchive)) {%>
					  	<font color="#DA70D6"><%=isarchive %></font>
					  <%} 
					  else if("已经审批".equals(isarchive)) {%>
						<font color="#BDB76B"><%=isarchive %></font>					  	
					  <%} 
					  else if("正在施工".equals(isarchive)) {%>
					  	<font color="#FFA500"><%=isarchive %></font>
					  <%} else {%>
					  	<%=isarchive %>
					  <%} %>
				</display:column>
			</display:table>
			<html:link action="/LineCutWorkAction.do?method=exportReLineCut">导出为Excel文件</html:link>
		</logic:equal>

		<!--显示审批单的详细信息-->
		<logic:equal value="au10" name="type">
			<br />
			<template:titile value="线路割接申请详细信息" />
			<template:formTable namewidth="200"  contentwidth="400"  th2="内容" th1="项目">
				<template:formTr name="申请单位">
					<bean:write name="reqinfo" property="contractorname" />
				</template:formTr>
				
				<template:formTr name="申请日期">
					<bean:write name="reqinfo" property="retime" />
				</template:formTr>
				<template:formTr name="割接名称">
					<bean:write name="reqinfo" property="name" />
				</template:formTr>
				<template:formTr name="中继段名">
					<%
		    		String sublinenamecon = ((LineCutBean) request.getAttribute("reqinfo")).getSublinename();
					String[] atrArr = sublinenamecon.split("<br>");
					for(int i = 0; i < atrArr.length; i++ ) { %>
					<%=atrArr[i] %>;<br>
					<%}%>
				</template:formTr>
				<template:formTr name="割接原因">
					<bean:write name="reqinfo" property="reason" />
				</template:formTr>
				<template:formTr name="割接地点">
					<bean:write name="reqinfo" property="address" />
				</template:formTr>
				<template:formTr name="割接类型">
					<bean:write name="reqinfo" property="cutType" />
				</template:formTr>
				<template:formTr name="计划割接日期">
					<bean:write name="reqinfo" property="protime" />
				</template:formTr>
				<template:formTr name="割接前衰耗">
					<bean:write name="reqinfo" property="provalue" />分贝(db)
		    </template:formTr>
				<template:formTr name="受影响系统">
					<bean:write name="reqinfo" property="efsystem" />
				</template:formTr>
				<template:formTr name="申请备注">
					<bean:write name="reqinfo" property="reremark" />
				</template:formTr>

				<template:formTr name="修改资料">
					<bean:write name="reqinfo" property="updoc" />
				</template:formTr>

				<template:formTr name="申请附件">
					<logic:equal value="" name="reqinfo" property="reacce">
						<apptag:listAttachmentLink fileIdList="" />
					</logic:equal>
					<logic:notEqual value="" name="reqinfo" property="reacce">
						<bean:define id="tem" name="reqinfo" property="reacce"
							type="java.lang.String" />
						<apptag:listAttachmentLink fileIdList="<%=tem%>" />
					</logic:notEqual>
				</template:formTr>

				<template:formTr name="审批单位">
					<bean:write name="reqinfo" property="deptname" />
				</template:formTr>
				<template:formTr name="审批人">
					<bean:write name="reqinfo" property="username" />
				</template:formTr>
				<template:formTr name="审批时间">
					<bean:write name="reqinfo" property="audittime" />
				</template:formTr>
				<template:formTr name="审批结果">
					<bean:write name="reqinfo" property="auditresult" />
				</template:formTr>
				<template:formTr name="审批备注">
					<bean:write name="reqinfo" property="auditremark" />
				</template:formTr>
				<template:formTr name="修改资料">
					<bean:write name="reqinfo" property="updoc" />
				</template:formTr>

				<template:formTr name="审批附件">

					<logic:equal value="" name="reqinfo" property="auditacce">
						<apptag:listAttachmentLink fileIdList="" />
					</logic:equal>
					<logic:notEqual value="" name="reqinfo" property="auditacce">
						<bean:define id="tem1" name="reqinfo" property="auditacce"
							type="java.lang.String" />
						<apptag:listAttachmentLink fileIdList="<%=tem1%>" />
					</logic:notEqual>

				</template:formTr>
			</template:formTable>

			<p align="center">
			<logic:equal value="1" name="flg">
				<input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
			</logic:equal>
			<logic:equal value="2" name="flg">
				<html:button property="action" styleClass="button"
					onclick="cancel()">关闭</html:button>
			</logic:equal>	
			</p>
		</logic:equal>

		<!--条件查询页面-->
		<logic:equal value="au3" name="type" scope="session">
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
					var url = 'LineCutReAction.do?method=getNameByClewId&sublineId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callbackToServerByName, asynchronous:true});
				}
				
				function callbackToServerByName(originalRequest) {
					var ops = document.getElementById('cutLineName');
					var rst = originalRequest.responseText;
					var nameArr = eval('('+rst+')');
					if(nameArr.length == 0) {
						alert("该路线下没有割接!");
						return;
					}
					for(var i = 0; i < nameArr.length; i++) {
						ops.options.add(new Option(nameArr[i].name,nameArr[i].name));
					}
				}
	    	</script>

			<br />
			<template:titile value="按条件查找割接审批信息" />
			<html:form action="/LineCutReAction?method=doQueryForAu"
				styleId="queryForm2">
				<template:formTable namewidth="200" contentwidth="200">
					<template:formTr name="审批操作人">
						<select name="audituserid" class="inputtext" style="width: 180px">
							<option value="">
								选择所有人员
							</option>
							<logic:present name="lusers">
								<logic:iterate id="lusersId" name="lusers">
									<option
										value="<bean:write name="lusersId" property="audituserid"/>">
										<bean:write name="lusersId" property="username" />
									</option>
								</logic:iterate>
							</logic:present>
						</select>
					</template:formTr>

					<template:formTr name="线路级别">
						<select name="level" class="inputtext" style="width: 180px"
							onchange="onSelChangeLevel()" id="level">
							<option value="mention">
								===请选择线路级别===
							</option>
							<logic:iterate id="linelevel" name="linelevelList">
								<bean:write name="linelevel" property="name" />
								<option value="<bean:write name="linelevel" property="code"/>">
									<bean:write name="linelevel" property="name" />
								</option>
							</logic:iterate>
						</select>
						<img id="Loadingimg" src="./images/ajaxtags/indicator.gif"
							style="display: none">
					</template:formTr>

					<template:formTr name="线路名称">
						<select class="inputtext" id="line" onchange="onSelChangeLine()"
							name="line" style="width: 180px;">
							<option></option>
						</select>
					</template:formTr>

					<template:formTr name="中继段名">
						<select name="sublineid" class="inputtext" style="width: 180px"
							id="clew" onchange="onSelectChangeClew()">
							<option value="">
								===选择所有段名===
							</option>
						</select>
					</template:formTr>

					<template:formTr name="割接名称">
						<select name="name" class="inputtext" style="width: 180px"
							id="cutLineName">
							<option value="">
								===选择所有割接===
							</option>
						</select>
					</template:formTr>


					<template:formTr name="审批开始时间">
						<input id="begin" type="text" readonly="readonly" name="begintime"
							class="inputtext" style="width: 150" />
						<INPUT TYPE='BUTTON' VALUE='日期' readonly="readonly" ID='btn'
							onclick="GetSelectDateTHIS('begin')"
							STYLE="font: 'normal small-caps 6pt serif';">
					</template:formTr>
					<template:formTr name="审批结束时间">
						<input id="end" type="text" name="endtime" class="inputtext"
							style="width: 150" />
						<INPUT TYPE='BUTTON' VALUE='日期' ID='btn'
							onclick="GetSelectDateTHIS('end')"
							STYLE="font: 'normal small-caps 6pt serif';">
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


		<iframe id="hiddenIframe" style="display: none"></iframe>
	</body>

</html>
