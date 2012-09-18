<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>

	<script type="text/javascript">
	checkValid=function(addForm){
		var flag=judgeEmptyValue(addForm.result,"审批结果");
		//flag=flag&&judgeValueLength(addForm.attitude,500,"审批备注");
		return flag;
	}
	addGoBackMod=function(){
		var url = "<%=(String) request.getSession().getAttribute("previousURL")%>";
		self.location.replace(url);
	}
	</script>
	<script type="text/javascript">
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
	</script>
	<body>
		<div id="main">
			<form action="${ctx}/TowerPatrolinfo.do?method=auditing"
				id="auditingForm" method="post"
				onsubmit="return checkValid(this);">
					<div class="title">
						审核巡检作业计划申请
					</div>
				<div>
					<table width="98%" border="0" cellpadding="0" cellspacing="0"
						class="tabout">
						<tr>
							<td class="tdulleft">
								年份：
							</td>
							<td class="tdulright">
								<bean:write name="plan_bean" property="year" />
								年
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								计划名称：
							</td>
							<td class="tdulright">
								<bean:write name="plan_bean" property="planname" />
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								计划类型：
							</td>
							<td class="tdulright">
								<logic:equal name="plan_bean" property="plantype" value="1">
									半年
								</logic:equal>
								<logic:equal name="plan_bean" property="plantype" value="2">
									季度
								</logic:equal>
								<logic:equal name="plan_bean" property="plantype" value="3">
									月度
								</logic:equal>
								<logic:equal name="plan_bean" property="plantype" value="4">
									自定义
								</logic:equal>
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								时间：
							</td>
							<td class="tdulright">
								<bean:write name="plan_bean" property="starttime" />
								到
								<bean:write name="plan_bean" property="endtime" />
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								代维公司：
							</td>
							<td class="tdulright">
								<bean:write name="plan_bean" property="contractorname" />
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								巡检组：
							</td>
							<td class="tdulright">
								<bean:write name="plan_bean" property="patrolgroupname" />
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								巡检区县：
							</td>
							<td class="tdulright">
								<bean:write name="plan_bean" property="regionname" />
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								巡检资源：
							</td>
							<td class="tdulright" style="text-align: left;">
								<logic:iterate id="resourceids" name="resouce_list">
									<bean:write name="resourceids" property="rs_name" />
									<br />
								</logic:iterate>
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								审批信息：
							</td>
							<td class="tdulright">
							</td>
						</tr>
						<tr>
							<td class="tdulright" colspan="2" width="100%">
								<table border="0" cellpadding="0" cellspacing="0"
									style="width: 100%; margin-left: 0px; margin-top: 0px;"
									class="tabout">
									<tr>
										<td style="text-align: center;">
											审核人
										</td>
										<td style="text-align: center;">
											审核结果
										</td>
										<td style="text-align: center;">
											审核日期
										</td>
										<td class="tdulright">
											审核备注
										</td>
									</tr>
									<logic:iterate id="auditingItem" name="auditing_history">
										<tr>
											<td style="text-align: center;">
												<bean:write name="auditingItem" property="username" />
											</td>
											<td style="text-align: center;">
												<logic:equal name="auditingItem" property="result"
													value="0">
												审核通过
											</logic:equal>
												<logic:equal name="auditingItem" property="result"
													value="1">
												审核不通过
											</logic:equal>
											</td>
											<td style="text-align: center;">
												<bean:write name="auditingItem" property="approve_time" />
											</td>
											<td class="tdulright">
												<bean:write name="auditingItem" property="attitude" />
											</td>
										</tr>
									</logic:iterate>
								</table>
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								审批人：
							</td>
							<td class="tdulright">
								<input name="query_method" type="hidden"
									value="<%=request.getAttribute("query_method")%>" />
								<input name="planid" type="hidden"
									value="<bean:write name="plan_bean" property="id" />" />
								<input name="approver" type="hidden"
									value="<%=((UserInfo) session.getAttribute("LOGIN_USER")).getUserID()%>" />
								<%=((UserInfo) session.getAttribute("LOGIN_USER")).getUserName()%>
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								审批结果：
							</td>
							<td class="tdulright">
								<input name="result" value="0" type="radio" checked />
								同意
								<input name="result" value="1" type="radio" />
								不同意
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								备注：
							</td>
							<td class="tdulright">
								<textarea name="attitude" class="inputtextarea"
									style="width: 250; border: 1px solid #6BB2D1; background-color: #ffffff; width: 200px; font-size: 12px; padding: 4px 2px; color: #0D5984;"
									rows="5"></textarea>
							</td>
						</tr>
						<tr>
							<td colspan="2" width="100%">
								<table id="uploadID"
									style="width: 100%; margin-left: 0px; margin-top: 0px;"
									border="0" align="left" cellpadding="0" cellspacing="0">
									<tr>
										<td></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="ge">
						<tr>
							<td width="15%">
								&nbsp;
							</td>
							<td width="55%" align="center">
								<!-- 
								<input name="btnUpload" class="buttondl" value="添加附件"
									type="button" onclick="addRow();" />
								 -->
								<html:submit styleClass="buttondl">审核</html:submit>
								<html:reset styleClass="buttondl">重置</html:reset>
							</td>
							<td width="30%">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</body>
</html>
