<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="">
	function view(tid){
		window.location.href = '${ctx}/hiddTroubleAction_view.jspx?flag=view&tid='+tid;
	}

    function to_apply(id){
		window.location.href = '${ctx}/remedy_apply.do?method=view&&power=52004&&to_page=view_square_remedy_apply&apply_id='+id;
	}
	goBack=function(){
		history.back();
	}
</script>
<div class='title2' style='font-size: 14px; font-weight: 600; bottom: 1'
	align='center'>
	<bean:write name='contractorName' />
	<FONT color="red"><bean:write name='month' />
	</FONT> 各修缮项修缮迁改费用统计信息
</div>
<hr width='100%' size='1'>
<table id="row">
	<thead>
		<tr>
			<th>
				修缮单号
			</th>
			<logic:present scope="request" name="itemList">
				<logic:iterate id="item" name="itemList">
					<th>
						<bean:write name="item" />(元)
					</th>
				</logic:iterate>
			</logic:present>
		<th>
			合计(元)
		</th>
		</tr>
	</thead>
	<tbody>
		<%
		    List labelList = (List) request.getAttribute("labelList");
		    for (int i = 0; i < labelList.size(); i++) {
		%>
		<tr class="odd">
			<%
			    List detailLabelList = (List) labelList.get(i);
			    Object id = detailLabelList.get(detailLabelList.size()-1);
			        for (int j = 0; j < detailLabelList.size(); j++) {
			        if(j==detailLabelList.size()-1){
			        	break;
			        }
			%>
			<td>
				<% Object str = detailLabelList.get(j);
					if(j==0 && i!=labelList.size()-1){	%>
						<a href="javascript:to_apply('<%=id%>')"> <%=str%></a>
					<%}else{%>
					 <%=str%>
					<%}
				 %>
			</td>
			<%	
			    }
			%>
		
		</tr>
			<%
			    }
			%>
		
	</tbody>
</table>
<a href="${ctx}/statRemedyAction.do?method=exportStatByContractor">导出为Excel文件</a>
<p align="center">
	<input name="btnBack" value="返回" onclick="goBack();" type="button"
		class="button" />
</p>
