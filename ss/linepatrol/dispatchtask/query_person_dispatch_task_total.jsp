<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>查询统计</title>
		<script type="" language="javascript">
	var rowArr = new Array();//一行的信息
	var infoArr = new Array();//所有的信息
	var rowDept = new Array();//一行的信息
	var infoDept = new Array();//所有的信息
    //初始化数组
    function initArray(deptid,userid,username){
    	rowArr[0] = deptid;
        rowArr[1] = userid;
        rowArr[2] = username;
        infoArr[infoArr.length] = rowArr;
        rowArr = new Array();
        return true;
    }
	function onSelectChange() {
		var slt1Obj=document.getElementById("deptid");
        var slt2Obj=document.getElementById("userid");
		if(document.all.senddeptname != null) {
			//alert(slt1Obj.options[slt1Obj.selectedIndex].text);
			document.all.senddeptname.value = slt1Obj.options[slt1Obj.selectedIndex].text;
		}
		i=0;
		for(j= 0; j< this.infoArr.length; j++ )
		{
			if(slt1Obj.value == infoArr[j][0])
			{
				i++;
			}
		}
        slt2Obj.options.length = i + 1;
		slt2Obj.options[0].text= "不限";
		slt2Obj.options[0].value= "";
		k = 1;
		for(j =0;j<this.infoArr.length;j++)
		{
			if(slt1Obj.value == infoArr[j][0])
				{
                  //UPuserid
					slt2Obj.options[k].text=this.infoArr[j][2];
					slt2Obj.options[k].value=this.infoArr[j][1];
                    var aObj = document.getElementById("UPuserid");
                    if(aObj != null){
                    	if(aObj.value == this.infoArr[j][1])
                        	slt2Obj.options[k].selected="true";
                    }
					k++;

				}
      }
		return true;
	}
	function onUserSelectChange(){
        var slt2Obj=document.getElementById("userid");
		//if(document.all.sendusername != null) {			
		//	document.all.sendusername.value = slt2Obj.options[slt2Obj.selectedIndex].text;
		//}
	} 
    function bodyOnload(){
      var Obj = document.getElementById("deptid");
      if(Obj != null){
      	 onSelectChange("deptid" ) ;
      }
    }
</script>
	</head>
	<body>
		<!--个人工单统计条件查询显示-->
		<logic:present name="userinfo">
			<logic:iterate id="uId" name="userinfo">
				<script type="" language="javascript">
            		initArray('<bean:write name="uId" property="deptid"/>','<bean:write name="uId" property="userid"/>','<bean:write name="uId" property="username"/>')
            		</script>
			</logic:iterate>
		</logic:present>
		<br />
		<template:titile value="按条件统计查询个人工单" />
		<html:form action="/dispatchtask/query_dispatch_task.do?method=queryPersonDispatchTaskTotalNum"
			styleId="queryForm">
			<template:formTable namewidth="200" contentwidth="400">
				<template:formTr name="工作单类别">
					<html:select property="sendtype" styleClass="inputtext" style="width:200px">
							<html:option value="">不限</html:option>
							<html:options property="code" labelProperty="lable"  collection="dispatch_task_type_list"/>
					</html:select>
				</template:formTr>
				<template:formTr name="受理部门">
					<html:select property="acceptdeptid" styleId="deptid" styleClass="inputtext" style="width:200px" onchange="onSelectChange();">
							<html:option value="">不限</html:option>
							<html:options property="deptid" labelProperty="deptname"  collection="deptinfo"/>
					</html:select>
				</template:formTr>
				<template:formTr name="受理人">
					<select name="acceptuserid" id="userid" style="width: 200px"
						class="inputtext" onchange="onUserSelectChange()">
						<option value="">
							不限
						</option>
					</select>
				</template:formTr>
				<template:formTr name="统计开始时间">
					<input id="begin" type="text" readonly="readonly" name="begintime"
						class="Wdate" value="${querycon.begintime}"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate:'#F{$dp.$D(\'end\')||\'%y-%M-%d\'}'})"
						style="width: 200px" />
				</template:formTr>
				<template:formTr name="统计结束时间">
					<input id="end" type="text" name="endtime" readonly="readonly"
						class="Wdate" value="${querycon.endtime}"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'begin\')}',maxDate:'%y-%M-%d'})"
						style="width: 200px" />
				</template:formTr>
			</template:formTable>
			<div align="center">
				<input type="hidden" name="isQuery" value="true" />
				<html:submit property="action" styleClass="button">查询</html:submit>
				<html:reset property="action" styleClass="button">重置</html:reset>
			</div>
		</html:form>
		<script type="" language="javascript">
			onSelectChange();
		</script>
		<template:displayHide styleId="queryForm"></template:displayHide>

		<!-- 查询结果 -->
		<script type="" language="javascript">
	function toPersonInfo(userid, username, flg) {
		//var url="${ctx}/dispatchtask/query_dispatch_task.do?method=queryPersonDispatchTaskTotal&flg="+flg+"&userid="+userid+"&username="+username;
		//window.location.href=url;
	}
		</script>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
		<font face="宋体" size="2" color="red"> <logic:notEmpty
				name="querycon">
				<bean:write name="querycon" property="acceptdeptname" />
				<logic:notEmpty name="querycon" property="acceptusername">
					<bean:write name="querycon" property="acceptusername" />
				</logic:notEmpty>
				<logic:notEmpty name="querycon" property="begintime">
					<bean:write name="querycon" property="begintime" />
				</logic:notEmpty>
				<logic:empty name="querycon" property="begintime">
				开始
			</logic:empty>
				<logic:notEmpty name="querycon" property="endtime">
				-- <bean:write name="querycon" property="endtime" />
				</logic:notEmpty>
				<logic:empty name="querycon" property="endtime">
				-- 至今
			</logic:empty>
				<logic:notEmpty name="querycon" property="sendtype">类型为
					<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
						type="look" keyValue="${querycon.sendtype}"></apptag:quickLoadList>
				</logic:notEmpty>
				<logic:empty name="querycon" property="sendtype">类型为全部</logic:empty>		
			工单统计	
		</logic:notEmpty> </font>
		<logic:notEmpty name="PERSON_TOTAL_NUM_LIST">
			<display:table name="sessionScope.PERSON_TOTAL_NUM_LIST"
				requestURI="${ctx}/dispatchtask/query_dispatch_task.do?method=queryPersonDispatchTaskTotal"
				id="currentRowObject" pagesize="20" style="width:100%">
				<bean:define id="userId" name="currentRowObject" property="userid" />
				<bean:define id="dispatchNum" name="currentRowObject"
					property="sum_num" />
				<bean:define id="waitSignInNum" name="currentRowObject"
					property="wait_sign_in_num" />
				<bean:define id="waitReplyNum" name="currentRowObject"
					property="wait_reply_num" />
				<bean:define id="waitCheckNum" name="currentRowObject"
					property="wait_check_num" />
				<bean:define id="refuseNum" name="currentRowObject"
					property="refuse_num" />
				<bean:define id="transferNum" name="currentRowObject"
					property="transfer_num" />
				<bean:define id="completeNum" name="currentRowObject"
					property="complete_num" />
				<bean:define id="replyOutTimeNum" name="currentRowObject"
					property="reply_out_time_num" />
				<bean:define id="checkOutTimeNum" name="currentRowObject"
					property="check_out_time_num" />
				<display:column property="username" title="姓名" style="width:60px"
					maxLength="4" sortable="true" />
				<display:column media="html" title="处理派单总数" style="width:80px"
					sortable="true">
					${dispatchNum}
				</display:column>
				<display:column media="html" title="待签收" style="width:80px"
					sortable="true">
					${waitSignInNum}
				</display:column>
				<display:column media="html" title="待反馈" style="width:80px"
					sortable="true">
					${waitReplyNum}
				</display:column>
				<display:column media="html" title="待验证" style="width:80px"
					sortable="true">
					${waitCheckNum}
				</display:column>
				<display:column media="html" title="拒签" style="width:80px"
					sortable="true">
					${refuseNum}
				</display:column>
				<display:column media="html" title="转派" style="width:80px"
					sortable="true">
					${transferNum}
				</display:column>
				<display:column media="html" title="完成" style="width:80px"
					sortable="true">
					${completeNum}
				</display:column>
				<display:column media="html" title="反馈超时" style="width:80px"
					sortable="true">
					${replyOutTimeNum}
				</display:column>
			</display:table>
			<html:link
				action="/dispatchtask/query_dispatch_task.do?method=exportPersonNumTotalResult">导出为Excel文件</html:link>
		</logic:notEmpty>
	</body>
</html>
