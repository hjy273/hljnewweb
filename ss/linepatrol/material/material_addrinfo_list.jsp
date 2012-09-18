<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<script type="text/javascript" language="javascript">
	
		function  goBack(){
			var url="${ctx}/materialModelAction.do?method=queryMaterialModelForm";
			self.location.replace(url);
		}
		
		function getMarterialInfo(addrid,state){
		    var num=0;
			if(state==1){
				num = document.getElementById(addrid+"1").innerHTML;
			}
			if(state==2){
			    num = document.getElementById(addrid+"2").innerHTML;
			}
			var mtid = document.getElementById("mtid").value;
		    var URL = "${ctx}/materialUsedInfoAction.do?method=getMarterialInfo&state="+state+"&mtid="+mtid+"&addrid="+addrid+"&num="+num;
	       	myleft=(screen.availWidth-500)/2;
			mytop=260;
			mywidth=500;
			myheight=320;
	        window.open(URL,"open","height="+myheight+",width="+mywidth+",status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=no");
	}
		</script>

	</head>
	<body>
		<html:form
			action="/materialUsedInfoAction.do?method=updateMaterialInfo">
			<logic:iterate id="mt" name="marterinfos" offset="0" length="1">
				<table align="center">
					<tr height="40">
						<td>
							<font size="3" style="font-weight: bold"><bean:write
									name="mt" property="name" />材料信息列表</font>
						</td>
					</tr>
				</table>
				<input type="hidden"
					value="<bean:write name="mt" property="baseid"/>" id="mtid"
					name="mtid">
				<input type="hidden" id="seq_id" name="seq_id"
					value="<%=(String) request.getAttribute("seq_id")%>" />
			</logic:iterate>
			<logic:notEmpty name="marterinfos" scope="request">
				<table border="1" width="95%" align="center" bgcolor="#FFFFFF"
					cellpadding="0" cellspacing="0" bordercolor="#000000"
					style="border-collapse: collapse">
					<tr height="30px;" align="center"
						style="text-align: center; line-height: 30px;">
						<th>
							材料地址
						</th>
						<th>
							新增库存
						</th>
						<th>
							利旧库存
						</th>
					</tr>
					<logic:iterate id="mtinfo" name="marterinfos">
						<bean:define id="addrid" name="mtinfo" property="addrid"></bean:define>
						<input type="hidden" id="<%=addrid%>" name="addrid"
							value="<bean:write name="mtinfo" property="addrid"/>">
						<input type="hidden" id="<%=addrid + "11"%>" name="newstock"
							value="<bean:write name="mtinfo" property="newstock"/>">
						<input type="hidden" id="<%=addrid + "22"%>" name="oldstock"
							value="<bean:write name="mtinfo" property="oldstock"/>">
						<tr>
							<td>
								<bean:write name="mtinfo" property="address" />
							</td>
							<td>
								<a href="javascript:getMarterialInfo('<%=addrid%>','1');"><span
									id="<%=addrid + "1"%>"><bean:write name="mtinfo"
											property="newstock" /> </span> </a>
							</td>
							<td>
								<a href="javascript:getMarterialInfo('<%=addrid%>','2');"><span
									id="<%=addrid + "2"%>"><bean:write name="mtinfo"
											property="oldstock" /> </span> </a>
							</td>
						</tr>
					</logic:iterate>
				</table>
				<table align="center">
					<tr height="55px">
						<td>
							<input type="submit" class="button" value="提交">
						</td>
						<td>
							<html:button property="action" styleClass="button"
							onclick="javascript:window.close();">关闭</html:button>
						</td>
					</tr>
				</table>
			</logic:notEmpty>
		</html:form>
	</body>
</html>
