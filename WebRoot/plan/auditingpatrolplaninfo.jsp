<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>

	<script type="text/javascript">
	checkValid=function(addForm){
		var flag=judgeEmptyValue(addForm.result,"�������");
		//flag=flag&&judgeValueLength(addForm.attitude,500,"������ע");
		return flag;
	}
	addGoBackMod=function(){
		var url = "<%=(String) request.getSession().getAttribute("previousURL")%>";
		self.location.replace(url);
	}
	</script>
	<script type="text/javascript">
	fileNum=0;
	 //�ű����ɵ�ɾ����  ť��ɾ������
	function deleteRow(){
      	//��ð�ť�����е�id
     	var rowid = "000";
        rowid = this.id;
        rowid = rowid.substring(1,rowid.length);
         //��idת��Ϊ�������е�����,��ɾ��
      	for(i =0; i<uploadID.rows.length;i++){
        	if(uploadID.rows[i].id == rowid){
            	uploadID.deleteRow(uploadID.rows[i].rowIndex);
                fileNum--;
            }
        }
    }
    //���һ������
    function addRow(){
    	var  onerow=uploadID.insertRow(-1);
        onerow.id = uploadID.rows.length-2;

        var   cell1=onerow.insertCell();
		var   cell2=onerow.insertCell();


		//����һ�������
        var t1 = document.createElement("input");
        //t1.name = "filename"+onerow.id;//alert(onerow.id);
        t1.name = "uploadFile["+ fileNum + "].file";
		t1.type= "file";
		t1.id = "text" + onerow.id;
		t1.width="300";
        fileNum++;


        //����ɾ����ť
        var b1 =document.createElement("button");
        b1.value = "ɾ��";
        b1.id = "b" + onerow.id;
        b1.onclick=deleteRow;
        //alert(b1.id + "rrr");


        cell1.appendChild(t1);//����
        cell2.appendChild(b1);
	}
	</script>
	<body>
		<div id="main">
			<form action="${ctx}/TowerPatrolinfo.do?method=auditing"
				id="auditingForm" method="post"
				onsubmit="return checkValid(this);">
					<div class="title">
						���Ѳ����ҵ�ƻ�����
					</div>
				<div>
					<table width="98%" border="0" cellpadding="0" cellspacing="0"
						class="tabout">
						<tr>
							<td class="tdulleft">
								��ݣ�
							</td>
							<td class="tdulright">
								<bean:write name="plan_bean" property="year" />
								��
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								�ƻ����ƣ�
							</td>
							<td class="tdulright">
								<bean:write name="plan_bean" property="planname" />
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								�ƻ����ͣ�
							</td>
							<td class="tdulright">
								<logic:equal name="plan_bean" property="plantype" value="1">
									����
								</logic:equal>
								<logic:equal name="plan_bean" property="plantype" value="2">
									����
								</logic:equal>
								<logic:equal name="plan_bean" property="plantype" value="3">
									�¶�
								</logic:equal>
								<logic:equal name="plan_bean" property="plantype" value="4">
									�Զ���
								</logic:equal>
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								ʱ�䣺
							</td>
							<td class="tdulright">
								<bean:write name="plan_bean" property="starttime" />
								��
								<bean:write name="plan_bean" property="endtime" />
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								��ά��˾��
							</td>
							<td class="tdulright">
								<bean:write name="plan_bean" property="contractorname" />
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								Ѳ���飺
							</td>
							<td class="tdulright">
								<bean:write name="plan_bean" property="patrolgroupname" />
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								Ѳ�����أ�
							</td>
							<td class="tdulright">
								<bean:write name="plan_bean" property="regionname" />
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								Ѳ����Դ��
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
								������Ϣ��
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
											�����
										</td>
										<td style="text-align: center;">
											��˽��
										</td>
										<td style="text-align: center;">
											�������
										</td>
										<td class="tdulright">
											��˱�ע
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
												���ͨ��
											</logic:equal>
												<logic:equal name="auditingItem" property="result"
													value="1">
												��˲�ͨ��
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
								�����ˣ�
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
								���������
							</td>
							<td class="tdulright">
								<input name="result" value="0" type="radio" checked />
								ͬ��
								<input name="result" value="1" type="radio" />
								��ͬ��
							</td>
						</tr>
						<tr>
							<td class="tdulleft">
								��ע��
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
								<input name="btnUpload" class="buttondl" value="��Ӹ���"
									type="button" onclick="addRow();" />
								 -->
								<html:submit styleClass="buttondl">���</html:submit>
								<html:reset styleClass="buttondl">����</html:reset>
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
