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
<template:titile value="�鿴��ϸ��Ϣ" />
<table border="0" cellspacing="1" cellpadding="0" width="80%" align="center">
			<tr bgcolor="#FFFFFF" class=trcolor>
				<td>
				<b>һ������������Ϣ��</b>
					<div id="item0" style="display:;">
						<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="5%" class=trcolor>								</td>
								<td class=trcolor width="15%">
									�������ƣ�								</td>
								<td width="80%">
									<bean:write name="changeinfo" property="changename" />								</td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									�������ʣ�								</td>
								<td>
									<bean:write name="changeinfo" property="changepro" />								</td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									���̵ص㣺								</td>
								<td>
									<bean:write name="changeinfo" property="changeaddr" />								</td>
							</tr>
							<!--tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									�������ʣ�								</td>
							  <td>
								</td>
							</tr-->
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									 Ӱ��ϵͳ��								</td>
								<td>
									<bean:write name="changeinfo" property="involved_system" />								</td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									 Ǩ�ĳ��ȣ�</td>
								<td><bean:write name="changeinfo" property="changelength" />��</td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									��ʼʱ�䣺</td>
							  <td><bean:write name="changeinfo" property="starttime" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>�ƻ����ڣ�</td>
							  <td><bean:write name="changeinfo" property="plantime" />��</td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>��ע��Ϣ��</td>
							  <td><div><bean:write name="changeinfo" property="remark" /></div></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">������</td>
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
					<b>������������Ϣ��</b>
					<div id="item1" style="display:;">
						<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="5%" class=trcolor></td>
								<td class=trcolor width="15%">
									�������ڣ�</td>
								<td width="80%">
									<bean:write name="changeinfo" property="surveydate" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									���鸺���ˣ�</td>
								<td>
									<bean:write name="changeinfo" property="principal" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">
									����Ԥ�㣺</td>
								<td>
									<bean:write name="changeinfo" property="budget" />��Ԫ</td>
							</tr>
							<logic:notEmpty name="survey_list">
							<logic:iterate id="change_info" name="survey_list">
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">�����鱸ע��</td>
								<td><div>
									<bean:write name="change_info" property="surveyremark" /></div></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> �������� </td>
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
							  <td class=trcolor>�����󶨽���� </td>
							  <td><bean:write name="change_info" property="approveresult" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>�����󶨵�λ�� </td>
							  <td><bean:write name="change_info" property="approvedept" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>�������ˣ� </td>
							  <td><bean:write name="change_info" property="approver" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> ���������ڣ� </td>
							  <td><bean:write name="change_info" property="approvedate" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>�����󶨱�ע�� </td>
							  <td><div><bean:write name="change_info" property="approveremark" /></div></td>
						  </tr>
						  	</logic:iterate>
						  </logic:notEmpty>
						  
						  <logic:notEqual value="B1" name="changeinfo" property="step">
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">�ƶ����鱸ע��</td>
								<td><div>
									<bean:write name="changeinfo" property="surveyremark" /></div></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> �ƶ����鸽���� </td>
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
							  <td class=trcolor>�ƶ��󶨽���� </td>
							  <td><bean:write name="changeinfo" property="approveresult" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>�ƶ��󶨵�λ�� </td>
							  <td><bean:write name="changeinfo" property="approvedept" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>�ƶ����ˣ� </td>
							  <td><bean:write name="changeinfo" property="approver" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>�ƶ������ڣ� </td>
							  <td><bean:write name="changeinfo" property="approvedate" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>�ƶ��󶨱�ע�� </td>
							  <td><div><bean:write name="changeinfo" property="approveremark" /></div></td>
						  </tr>
						</logic:notEqual>  
						</table>
					</div>
			  </td>
			</tr>
			<tr bgcolor="#FFFFFF" class=trcolor>
				<td>
					<b>����ʩ��׼����Ϣ��</b>
					<div id="item2" style="display:;">
						<table border="0" cellspacing="1" cellpadding="0" width="100%">

							<%if(tempFloat>=10.0f && tempFloat<=30.0f){%>
                          <tr bgcolor="#FFFFFF" class=trwhite>
								<td width="5%" class=trcolor></td>
								<td class=trcolor width="15%">����λ���ƣ�</td>
								<td width="80%">
									<bean:write name="changeinfo" property="sname" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>����λ��ַ�� </td>
							  <td><bean:write name="changeinfo" property="saddr" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>����λ�绰�� </td>
							  <td><bean:write name="changeinfo" property="sphone" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>����λ��ϵ�ˣ�</td>
							  <td><bean:write name="changeinfo" property="sperson" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>����λ���ʣ� </td>
							  <td><bean:write name="changeinfo" property="sgrade" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>������ã� </td>
							  <td><bean:write name="changeinfo" property="sexpense" />��Ԫ</td>
						  </tr>
							<tr bgcolor="#FFFFFF"  class=trwhite>
                              <td class=trcolor width="5%"></td>
							  <td class=trcolor width="15%">��Ƶ�λ���ƣ� </td>
							  <td width="80%"><bean:write name="changeinfo" property="dname" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>��Ƶ�λ��ַ�� </td>
							  <td><bean:write name="changeinfo" property="daddr" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>��Ƶ�λ�绰�� </td>
							  <td><bean:write name="changeinfo" property="dphone" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>��Ƶ�λ��ϵ�ˣ� </td>
							  <td><bean:write name="changeinfo" property="dperson" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>��Ƶ�λ���ʣ� </td>
							  <td><bean:write name="changeinfo" property="dgrade" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>��Ʒ��ã� </td>
							  <td><bean:write name="changeinfo" property="dexpense" />��Ԫ</td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> ��ע�� </td>
							  <td><div><bean:write name="changeinfo" property="setoutremark" /></div></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor>������ </td>
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
							  <td class=trcolor width="15%"> �����������ƣ� </td>
							  <td width="80%"><bean:write name="changeinfo" property="ename" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> �������ĵ�ַ�� </td>
							  <td><bean:write name="changeinfo" property="eaddr" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> ����������ϵ�ˣ� </td>
							  <td><bean:write name="changeinfo" property="eperson" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> �������ĵ绰�� </td>
							  <td><bean:write name="changeinfo" property="ephone" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> ��ע�� </td>
							  <td><div><bean:write name="changeinfo" property="setoutremark" /></div></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
                              <td class=trcolor></td>
							  <td class=trcolor> ������ </td>
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
							  <td width="80%">�������Ϣ</td>
						  </tr>
                          <% }%>
						</table>
					</div>
			  </td>
			</tr>
			<tr bgcolor="#FFFFFF" class=trcolor>
				<td>
					<b>�ġ�ʩ��ί����Ϣ��</b>
					<div id="item3" style="display:;">
						<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="5%" class=trcolor></td>
								<td class=trcolor width="15%">ί��ʩ����λ���ƣ�</td>
								<td width="80%"><bean:write name="changeinfo" property="entrustunit" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">ί��ʩ����λ��ַ��</td>
								<td>
									<bean:write name="changeinfo" property="entrustaddr" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">ί��ʩ����λ�绰��</td>
								<td><bean:write name="changeinfo" property="entrustphone" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>ί��ʩ����λ��ϵ�ˣ�</td>
							  <td><bean:write name="changeinfo" property="entrustperson" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>ί��ʩ����λ���ʣ�</td>
							  <td><bean:write name="changeinfo" property="entrustgrade" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>������ۣ�</td>
							  <td><bean:write name="changeinfo" property="cost" />��Ԫ</td>
						  </tr>
						  <tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>ʩ����ʼ���ڣ�</td>
							  <td><bean:write name="changeinfo" property="constartdate" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>ί�б�ע��</td>
							  <td><div><bean:write name="changeinfo" property="entrustremark" /></div></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">������</td>
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
					<b>�塢ʩ����Ϣ��</b>
					<div id="item4" style="display:;">
						<table border="0" cellspacing="1" cellpadding="0" width="100%">
								<tr bgcolor="#FFFFFF" class=trwhite>
									<td width="5%" class=trcolor></td>
									<td class=trcolor width="15%">ʩ����ʼ���ڣ�</td>
									<td width="80%"><bean:write name="changeinfo" property="bstarttime" /></td>
								</tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
								  <td class=trcolor></td>
								  <td class=trcolor>ʩ���������ڣ�</td>
								  <td><bean:write name="changeinfo" property="bendtime" /></td>
							  </tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
								  <td class=trcolor></td>
								  <td class=trcolor>ʩ����λ���ƣ�</td>
								  <td><bean:write name="changeinfo" property="buildunit" /></td>
							  </tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
								  <td class=trcolor></td>
								  <td class=trcolor>ʩ����λ��ַ��</td>
								  <td><bean:write name="changeinfo" property="buildaddr" /></td>
							  </tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
								  <td class=trcolor></td>
								  <td class=trcolor>ʩ����λ��ϵ�ˣ�</td>
								  <td><bean:write name="changeinfo" property="buildperson" /></td>
							  </tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
								  <td class=trcolor></td>
								  <td class=trcolor>ʩ����λ�绰��</td>
								  <td><bean:write name="changeinfo" property="buildphone" /></td>
							  </tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
								  <td class=trcolor></td>
								  <td class=trcolor>ʩ����ע��</td>
								  <td><div><bean:write name="changeinfo" property="buildremark" /></div></td>
							  </tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
									<td width="40" class=trcolor></td>
									<td class=trcolor width="90">ʩ��������</td>
									<td><div><bean:write name="changeinfo" property="buildvalue" /></div></td>
								</tr>
								<tr bgcolor="#FFFFFF" class=trwhite>
									<td width="40" class=trcolor></td>
									<td class=trcolor width="90">������</td>
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
					<b>�������չ鵵��Ϣ��</b>
					<div id="item5" style="display:;">
						<table border="0" cellspacing="1" cellpadding="0" width="100%">
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="5%" class=trcolor></td>
								<td class=trcolor width="15%">�������ڣ�</td>
								<td width="80%">
									<bean:write name="changeinfo" property="checkdate" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>���ո����ˣ�</td>
							  <td>&nbsp;<bean:write name="changeinfo" property="checkperson" /></td>
						  </tr>
						  <tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>���ս����</td>
							  <td>&nbsp;<bean:write name="changeinfo" property="checkresult" /></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>���ձ�ע��</td>
							  <td><div><bean:write name="changeinfo" property="checkremark" /></div></td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
							  <td class=trcolor></td>
							  <td class=trcolor>���ո�����</td>
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
							  <td class=trcolor>���̽��㣺</td>
							  <td>&nbsp;<bean:write name="changeinfo" property="square" />��Ԫ</td>
						  </tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">�Ƿ���Ҫ�޸����ϣ�</td>
								<td>
									<bean:write name="changeinfo" property="ischangedatum" /></td>
							</tr>
							<tr bgcolor="#FFFFFF" class=trwhite>
								<td width="40" class=trcolor></td>
								<td class=trcolor width="90">�鵵������</td>
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
								<td class=trcolor width="90">�鵵��ע��</td>
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
			        <input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
			        </logic:empty>
			        <logic:equal value="showSquare" name="source">
			        <input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
			        </logic:equal>
		      </td>
			</tr>
</table>
</body>

