<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linechange.bean.*;"%>
<script language="javascript" type="">
 function addGoBack()
    {
      var url = "${ctx}/changeapplyaction.do?method=getApplyInfoList";
      self.location.replace(url);
    }
 function goTo(){
   var url="${ctx}/pageonholeaction.do?method=showSquare";
   self.location.replace(url);
 }
</script>
<!--[if IE]>
<style>
div {word-break:break-all;;}
</style>
<![endif]-->
<style type="">
div {width:300px;border:solid 1px #000;}
</style>
<body>
<%
DynaBean bean = (DynaBean) request.getAttribute("changeinfo");
float tempFloat = Float.parseFloat(bean.get("budget").toString());
%>
<template:titile value="查看详细信息" />
<table border="0" cellspacing="1" cellpadding="0" width="80%" align="center">
			<tr bgcolor="#FFFFFF" class=trcolor>
				<td>
				<b>一、修缮申请信息：</b>
					<div id="item0" style="display:;">
						<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="5%" class=trcolor>								</td>
								<td class=trcolor width="15%">
									工程名称：								</td>
								<td width="80%">
									<bean:write name="changeinfo" property="changename" />								</td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									工程性质：								</td>
								<td>
									<bean:write name="changeinfo" property="changepro" />								</td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									工程地点：								</td>
								<td>
									<bean:write name="changeinfo" property="changeaddr" />								</td>
							</tr>
							<!--tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									网络性质：								</td>
							  <td>
								</td>
							</tr-->
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									 影响系统：								</td>
								<td>
									<bean:write name="changeinfo" property="involved_system" />								</td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									 迁改长度：</td>
								<td><bean:write name="changeinfo" property="changelength" />米</td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									开始时间：</td>
							  <td><bean:write name="changeinfo" property="starttime" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>计划工期：</td>
							  <td><bean:write name="changeinfo" property="plantime" />天</td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>备注信息：</td>
							  <td><div><bean:write name="changeinfo" property="remark" /></div></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">附件：</td>
								<td>
                                  <logic:empty name="changeinfo" property="applydatumid">
                                    <apptag:listAttachmentLink fileIdList=""/>
                                  </logic:empty>
                                  <logic:notEmpty name="changeinfo" property="applydatumid">
                                    <bean:define id="temp1" name="changeinfo" property="applydatumid" type="java.lang.String"/>
                                    <apptag:listAttachmentLink fileIdList="<%=temp1%>"/>
                                  </logic:notEmpty></td>
							</tr>
						</table>
					</div>
			  </td>
			</tr>
			<tr bgcolor="#FFFFFF" class=trcolor>
				<td>
					<b>二、勘查审定信息：</b>
					<div id="item1" style="display:;">
						<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="5%" class=trcolor></td>
								<td class=trcolor width="15%">
									勘查日期：</td>
								<td width="80%">
									<bean:write name="changeinfo" property="surveydate" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									勘查负责人：</td>
								<td>
									<bean:write name="changeinfo" property="principal" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									工程预算：</td>
								<td>
									<bean:write name="changeinfo" property="budget" />万元</td>
							</tr>
							<logic:notEmpty name="survey_list">
							<logic:iterate id="change_info" name="survey_list">
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">监理勘查备注：</td>
								<td><div>
									<bean:write name="change_info" property="surveyremark" /></div></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> 监理附件： </td>
							  <td>
                              <logic:empty name="change_info" property="surveydatum">
                                <apptag:listAttachmentLink fileIdList=""/>
                              </logic:empty>
                              <logic:notEmpty name="change_info" property="surveydatum">
                                <bean:define id="temp2" name="change_info" property="surveydatum" type="java.lang.String"/>
                                <apptag:listAttachmentLink fileIdList="<%=temp2%>"/>
                              </logic:notEmpty>
                              </td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>监理审定结果： </td>
							  <td><bean:write name="change_info" property="approveresult" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>监理审定单位： </td>
							  <td><bean:write name="change_info" property="approvedept" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>监理审定人： </td>
							  <td><bean:write name="change_info" property="approver" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> 监理审定日期： </td>
							  <td><bean:write name="change_info" property="approvedate" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>监理审定备注： </td>
							  <td><div><bean:write name="change_info" property="approveremark" /></div></td>
						  </tr>
						  	</logic:iterate>
						  </logic:notEmpty>
						  
						  <logic:notEqual value="B1" name="changeinfo" property="step">
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">移动勘查备注：</td>
								<td><div>
									<bean:write name="changeinfo" property="surveyremark" /></div></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> 移动勘查附件： </td>
							  <td>
                              <logic:empty name="changeinfo" property="surveydatum">
                                <apptag:listAttachmentLink fileIdList=""/>
                              </logic:empty>
                              <logic:notEmpty name="changeinfo" property="surveydatum">
                                <bean:define id="temp2" name="changeinfo" property="surveydatum" type="java.lang.String"/>
                                <apptag:listAttachmentLink fileIdList="<%=temp2%>"/>
                              </logic:notEmpty>
                              </td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>移动审定结果： </td>
							  <td><bean:write name="changeinfo" property="approveresult" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>移动审定单位： </td>
							  <td><bean:write name="changeinfo" property="approvedept" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>移动审定人： </td>
							  <td><bean:write name="changeinfo" property="approver" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>移动审定日期： </td>
							  <td><bean:write name="changeinfo" property="approvedate" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>移动审定备注： </td>
							  <td><div><bean:write name="changeinfo" property="approveremark" /></div></td>
						  </tr>
						</logic:notEqual>  
						</table>
					</div>
			  </td>
			</tr>
			<tr bgcolor="#FFFFFF" class=trcolor>
				<td>
					<b>三、施工准备信息：</b>
					<div id="item2" style="display:;">
						<table border="0" cellspacing="1" cellpadding="0" width="100%">

							<%if(tempFloat>=10.0f && tempFloat<=30.0f){%>
                          <tr bgcolor="#FFFFFF" class=trwhite>
								<td width="5%" class=trcolor></td>
								<td class=trcolor width="15%">监理单位名称：</td>
								<td width="80%">
									<bean:write name="changeinfo" property="sname" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>监理单位地址： </td>
							  <td><bean:write name="changeinfo" property="saddr" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>监理单位电话： </td>
							  <td><bean:write name="changeinfo" property="sphone" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>监理单位联系人：</td>
							  <td><bean:write name="changeinfo" property="sperson" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>监理单位资质： </td>
							  <td><bean:write name="changeinfo" property="sgrade" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>监理费用： </td>
							  <td><bean:write name="changeinfo" property="sexpense" />万元</td>
						  </tr>
							<tr bgcolor="#FFFFFF"  class=trwhite>
                              <td class=trcolor width="5%"></td>
							  <td class=trcolor width="15%">设计单位名称： </td>
							  <td width="80%"><bean:write name="changeinfo" property="dname" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>设计单位地址： </td>
							  <td><bean:write name="changeinfo" property="daddr" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>设计单位电话： </td>
							  <td><bean:write name="changeinfo" property="dphone" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>设计单位联系人： </td>
							  <td><bean:write name="changeinfo" property="dperson" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>设计单位资质： </td>
							  <td><bean:write name="changeinfo" property="dgrade" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>设计费用： </td>
							  <td><bean:write name="changeinfo" property="dexpense" />万元</td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> 备注： </td>
							  <td><div><bean:write name="changeinfo" property="setoutremark" /></div></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>附件： </td>
							  <td><logic:empty name="changeinfo" property="setoutdatum">
                                <apptag:listAttachmentLink fileIdList=""/>
                              </logic:empty>
                              <logic:notEmpty name="changeinfo" property="setoutdatum">
                                <bean:define id="temp3" name="changeinfo" property="setoutdatum" type="java.lang.String"/>
                                <apptag:listAttachmentLink fileIdList="<%=temp3%>"/>
                              </logic:notEmpty>
                              </td>
						  </tr>
							<%}else if(tempFloat > 30.0f){%>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor width="5%"></td>
							  <td class=trcolor width="15%"> 工程中心名称： </td>
							  <td width="80%"><bean:write name="changeinfo" property="ename" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> 工程中心地址： </td>
							  <td><bean:write name="changeinfo" property="eaddr" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> 工程中心联系人： </td>
							  <td><bean:write name="changeinfo" property="eperson" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> 工程中心电话： </td>
							  <td><bean:write name="changeinfo" property="ephone" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> 备注： </td>
							  <td><div><bean:write name="changeinfo" property="setoutremark" /></div></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> 附件： </td>
							  <td><logic:empty name="changeinfo" property="setoutdatum">
                                <apptag:listAttachmentLink fileIdList=""/>
                              </logic:empty>
                              <logic:notEmpty name="changeinfo" property="setoutdatum">
                                <bean:define id="temp4" name="changeinfo" property="setoutdatum" type="java.lang.String"/>
                                <apptag:listAttachmentLink fileIdList="<%=temp4%>"/>
                              </logic:notEmpty></td>
						  </tr>
                          <%}else{%>
                          <tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor width="5%"></td>
							  <td class=trcolor width="15%"> &nbsp; </td>
							  <td width="80%">无相关信息</td>
						  </tr>
                          <% }%>
						</table>
					</div>
			  </td>
			</tr>
			<tr bgcolor="#FFFFFF" class=trcolor>
				<td>
					<b>四、施工委托信息：</b>
					<div id="item3" style="display:;">
						<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="5%" class=trcolor></td>
								<td class=trcolor width="15%">委托施工单位名称：</td>
								<td width="80%"><bean:write name="changeinfo" property="entrustunit" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">委托施工单位地址：</td>
								<td>
									<bean:write name="changeinfo" property="entrustaddr" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">委托施工单位电话：</td>
								<td><bean:write name="changeinfo" property="entrustphone" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>委托施工单位联系人：</td>
							  <td><bean:write name="changeinfo" property="entrustperson" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>委托施工单位资质：</td>
							  <td><bean:write name="changeinfo" property="entrustgrade" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>工程造价：</td>
							  <td><bean:write name="changeinfo" property="cost" />万元</td>
						  </tr>
						  <tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>施工开始日期：</td>
							  <td><bean:write name="changeinfo" property="constartdate" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>委托备注：</td>
							  <td><div><bean:write name="changeinfo" property="entrustremark" /></div></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">附件：</td>
								<td>
                                  <logic:empty name="changeinfo" property="entrustdatum">
                                <apptag:listAttachmentLink fileIdList=""/>
                              </logic:empty>
                              <logic:notEmpty name="changeinfo" property="entrustdatum">
                                <bean:define id="temp5" name="changeinfo" property="entrustdatum" type="java.lang.String"/>
                                <apptag:listAttachmentLink fileIdList="<%=temp5%>"/>
                              </logic:notEmpty></td>
							</tr>
						</table>
					</div>
			  </td>
			</tr>
			<tr bgcolor="#FFFFFF" class=trcolor>
				<td>
					<b>五、施工信息：</b>
					<div id="item4" style="display:;">
						<table border="0" cellspacing="1" cellpadding="0" width="100%">
								<tr bgcolor="#FFFFFF" class=trwhite>
									<td width="5%" class=trcolor></td>
									<td class=trcolor width="15%">施工开始日期：</td>
									<td width="80%"><bean:write name="changeinfo" property="bstarttime" /></td>
								</tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
								  <td class=trcolor></td>
								  <td class=trcolor>施工结束日期：</td>
								  <td><bean:write name="changeinfo" property="bendtime" /></td>
							  </tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
								  <td class=trcolor></td>
								  <td class=trcolor>施工单位名称：</td>
								  <td><bean:write name="changeinfo" property="buildunit" /></td>
							  </tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
								  <td class=trcolor></td>
								  <td class=trcolor>施工单位地址：</td>
								  <td><bean:write name="changeinfo" property="buildaddr" /></td>
							  </tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
								  <td class=trcolor></td>
								  <td class=trcolor>施工单位联系人：</td>
								  <td><bean:write name="changeinfo" property="buildperson" /></td>
							  </tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
								  <td class=trcolor></td>
								  <td class=trcolor>施工单位电话：</td>
								  <td><bean:write name="changeinfo" property="buildphone" /></td>
							  </tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
								  <td class=trcolor></td>
								  <td class=trcolor>施工备注：</td>
								  <td><div><bean:write name="changeinfo" property="buildremark" /></div></td>
							  </tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
									<td width="40" class=trcolor></td>
									<td class=trcolor width="90">施工自评：</td>
									<td><div><bean:write name="changeinfo" property="buildvalue" /></div></td>
								</tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
									<td width="40" class=trcolor></td>
									<td class=trcolor width="90">附件：</td>
                                    <td>
                                      <logic:empty name="changeinfo" property="builddatum">
                                      <apptag:listAttachmentLink fileIdList=""/>
                                      </logic:empty>
                                      <logic:notEmpty name="changeinfo" property="builddatum">
                                        <bean:define id="temp6" name="changeinfo" property="builddatum" type="java.lang.String"/>
                                        <apptag:listAttachmentLink fileIdList="<%=temp6%>"/>
                                      </logic:notEmpty>
								</td>
								</tr>
						</table>
					</div>
			  </td>
			</tr>
			<tr bgcolor="#FFFFFF" class=trcolor>
				<td>
					<b>六、验收归档信息：</b>
					<div id="item5" style="display:;">
						<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="5%" class=trcolor></td>
								<td class=trcolor width="15%">验收日期：</td>
								<td width="80%">
									<bean:write name="changeinfo" property="checkdate" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>验收负责人：</td>
							  <td>&nbsp;<bean:write name="changeinfo" property="checkperson" /></td>
						  </tr>
						  <tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>验收结果：</td>
							  <td>&nbsp;<bean:write name="changeinfo" property="checkresult" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>验收备注：</td>
							  <td><div><bean:write name="changeinfo" property="checkremark" /></div></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>验收附件：</td>
							  <td> <logic:empty name="changeinfo" property="checkdatum">
                                      <apptag:listAttachmentLink fileIdList=""/>
                                      </logic:empty>
                                      <logic:notEmpty name="changeinfo" property="checkdatum">
                                        <bean:define id="temp7" name="changeinfo" property="checkdatum" type="java.lang.String"/>
                                        <apptag:listAttachmentLink fileIdList="<%=temp7%>"/>
                                      </logic:notEmpty>
                              </td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>工程结算：</td>
							  <td>&nbsp;<bean:write name="changeinfo" property="square" />万元</td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">是否需要修改资料：</td>
								<td>
									<bean:write name="changeinfo" property="ischangedatum" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">归档附件：</td>
								<td><logic:empty name="changeinfo" property="pageonholedatum">
                                      <apptag:listAttachmentLink fileIdList=""/>
                                      </logic:empty>
                                      <logic:notEmpty name="changeinfo" property="pageonholedatum">
                                        <bean:define id="temp8" name="changeinfo" property="pageonholedatum" type="java.lang.String"/>
                                        <apptag:listAttachmentLink fileIdList="<%=temp8%>"/>
                                      </logic:notEmpty>
                                </td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">归档备注：</td>
								<td><div><bean:write name="changeinfo" property="pageonholeremark" /></div></td>
							</tr>
						</table>
					</div>
			  </td>
			</tr>

		</table>
		<br />
<table border="0" cellspacing="0" cellpadding="0" width="80%" align="center">
			<tr>
				<td colspan="4" align="center">
					<logic:empty name="source">
			        <input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
			        </logic:empty>
			        <logic:equal value="showSquare" name="source">
			        <input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
			        </logic:equal>
		      </td>
			</tr>
</table>
</body>

