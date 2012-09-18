<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.linecut.beans.LineCutBean"%>
<html>
	<head>
		<script type="" language="javascript">
			function queryGoBack() {
				var url = "${ctx}/LineCutReAction.do?method=queryCutBeanStat&state=detail";
				window.location.href=url;
			}
		</script>
		<title>lineCutDetail</title>
	</head>
	<body>
		<br />
		<template:titile value="线路割接详细信息" />
		<template:formTable namewidth="100" contentwidth="500" th2="内容"
			th1="项目">

			<template:formTr name="申请单位">
				<bean:write name="detailInfo" property="contractorname" />
			</template:formTr>
			<template:formTr name="申请人">
				<bean:write name="detailInfo" property="reuser" />
			</template:formTr>
			<template:formTr name="申请时间">
				<bean:write name="detailInfo" property="retime" />
			</template:formTr>
			<template:formTr name="割接名称">
				<bean:write name="detailInfo" property="name" />
			</template:formTr>
			<template:formTr name="中继段名">
				<bean:write name="detailInfo" property="sublinename" />
			</template:formTr>

			<template:formTr name="割接原因">
				<bean:write name="detailInfo" property="reason" />
			</template:formTr>
			<template:formTr name="割接地点">
				<bean:write name="detailInfo" property="address" />
			</template:formTr>
			<!--  
			    <template:formTr name="光缆线段">
			    	<%
			    		String sublinenamecon = ((LineCutBean) request.getAttribute("detailInfo")).getSublinename();
						String[] atrArr = sublinenamecon.split("<br>");
						for(int i = 0; i < atrArr.length; i++ ) { %>
							<%=atrArr[i]%>;<br>
					<%}%>
			    </template:formTr>
			    -->

			<template:formTr name="割接类型">
				<bean:write name="detailInfo" property="cutType" />
			</template:formTr>

			<template:formTr name="计划割接日期">
				<bean:write name="detailInfo" property="protime" />
			</template:formTr>

			<template:formTr name="实际割接日期">
				<bean:write name="detailInfo" property="essetime" />
			</template:formTr>

			<template:formTr name="预计用时(小时)">
				<bean:write name="detailInfo" property="prousetime" />
			</template:formTr>

			<template:formTr name="实际用时(小时)">
				<bean:write name="detailInfo" property="usetime" />
			</template:formTr>

			<template:formTr name="割接前衰耗">
				<bean:write name="detailInfo" property="provalue" />分贝(db)
			    </template:formTr>

			<template:formTr name="割接后衰耗">
				<bean:write name="detailInfo" property="endvalue" />分贝(db)
				</template:formTr>

			<template:formTr name="受影响系统">
				<bean:write name="detailInfo" property="efsystem" />
			</template:formTr>

			<template:formTr name="修改资料">
				<bean:write name="detailInfo" property="updoc" />
			</template:formTr>

			<template:formTr name="申请备注">
				<bean:write name="detailInfo" property="reremark" />
			</template:formTr>

			<template:formTr name="申请附件">
				<logic:empty name="detailInfo" property="reacce">
					<apptag:listAttachmentLink fileIdList="" />
				</logic:empty>
				<logic:notEmpty name="detailInfo" property="reacce">
					<bean:define id="temr" name="detailInfo" property="reacce"
						type="java.lang.String" />
					<apptag:listAttachmentLink fileIdList="<%=temr%>" />
				</logic:notEmpty>
			</template:formTr>

			<tr heigth="40" class=trcolor>
				<td class="tdr" colspan="6">
					<hr />
				</td>
			</tr>

<bean:define id="state" name="detailInfo" property="isarchive"/>
		<logic:notEmpty name="detailInfo" property="audituser">
				<template:formTr name="审批结果">
					<bean:write name="detailInfo" property="auditresult" />
				</template:formTr>

				<template:formTr name="审批时间">
					<bean:write name="detailInfo" property="audittime" />
				</template:formTr>

				<template:formTr name="审批人">
					<bean:write name="detailInfo" property="audituser" />
				</template:formTr>

				<template:formTr name="审批备注">
					<bean:write name="detailInfo" property="auditremark" />
				</template:formTr>

				<template:formTr name="审批附件">
					<logic:empty name="detailInfo" property="auditacce">
						<apptag:listAttachmentLink fileIdList="" />
					</logic:empty>
					<logic:notEmpty name="detailInfo" property="auditacce">
						<bean:define id="tema" name="detailInfo" property="auditacce"
							type="java.lang.String" />
						<apptag:listAttachmentLink fileIdList="<%=tema%>" />
					</logic:notEmpty>
				</template:formTr>
			</logic:notEmpty>
			<tr heigth="40" class=trcolor>
				<td class="tdr" colspan="6">
					<hr />
				</td>
			</tr>

			<!--  
	            <logic:notEmpty name="detailInfo" property="essetime">
			        <template:formTr name="施工时间">
					    <bean:write name="detailInfo" property="essetime"/>
					</template:formTr>
	            </logic:notEmpty>
	
	           
				
			    <template:formTr name="所耗人力">
					 <bean:write name="detailInfo" property="manpower"/>工日
				</template:formTr>
	
	            <template:formTr name="割接用时">
					<bean:write name="detailInfo" property="usetime"/>小时
				</template:formTr>
	            
	            <template:formTr name="施工单位">
			         <bean:write name="detailInfo" property="contractorname"/>
			    </template:formTr>
			    
			     <template:formTr name="施工附件">
	                 <logic:empty name="detailInfo" property="workacce">
	                     	<apptag:listAttachmentLink fileIdList=""/>
	                 </logic:empty>
	                 <logic:notEmpty name="detailInfo" property="workacce">
	                    	<bean:define id="temw" name="detailInfo" property="workacce" type="java.lang.String"/>
						   	<apptag:listAttachmentLink fileIdList="%=temw%"/>
	                 </logic:notEmpty>
				</template:formTr>
				
				  <template:formTr name="验收附件">
	                 <logic:empty name="detailInfo" property="acceptacce">
	                     <apptag:listAttachmentLink fileIdList=""/>
	                 </logic:empty>
	                 <logic:notEmpty name="detailInfo" property="acceptacce">
	                    <bean:define id="tema" name="detailInfo" property="acceptacce" type="java.lang.String"/>
						<apptag:listAttachmentLink fileIdList="tema"/>
	                 </logic:notEmpty>
				</template:formTr>
				
			    -->

			<logic:notEmpty name="detailInfo" property="workremark">
				<template:formTr name="施工备注">
					<bean:write name="detailInfo" property="workremark" />
				</template:formTr>
			</logic:notEmpty>

			
			<%
				if(((String)state).indexOf("验收")>0 || ((String)state).indexOf("归档")>0){
			%>
				<tr heigth="40" class=trcolor>
					<td class="tdr" colspan="6">
						<hr />
					</td>
				</tr>

				<template:formTr name="验收人">
					<bean:write name="detailInfo" property="auusername" />
				</template:formTr>

				<template:formTr name="验收时间">
					<bean:write name="detailInfo" property="paudittime" />
				</template:formTr>

				<template:formTr name="验收备注">
					<bean:write name="detailInfo" property="acceptremark" />
				</template:formTr>

				<template:formTr name="验收结果">
					<bean:write name="detailInfo" property="acceptresult" />
				</template:formTr>
			<% }%>
			<!--  
                  <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
                 
	             
			    <template:formTr name="归档文件">
	            	<logic:empty name="detailInfo" property="archives">
	                	<apptag:listAttachmentLink fileIdList=""/>
	                 </logic:empty>
	                 <logic:notEmpty name="detailInfo" property="archives">
	                    <bean:define id="temar" name="detailInfo" property="archives" type="java.lang.String"/>
						<apptag:listAttachmentLink fileIdList="%=temar%"/>
	                 </logic:notEmpty>
				</template:formTr>
					-->
		</template:formTable>

		<p align="center">
			<input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
		</p>

		<iframe id="hiddenIframe" style="display:none"></iframe>
	</body>

</html>
