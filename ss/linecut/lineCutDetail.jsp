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
		<template:titile value="��·�����ϸ��Ϣ" />
		<template:formTable namewidth="100" contentwidth="500" th2="����"
			th1="��Ŀ">

			<template:formTr name="���뵥λ">
				<bean:write name="detailInfo" property="contractorname" />
			</template:formTr>
			<template:formTr name="������">
				<bean:write name="detailInfo" property="reuser" />
			</template:formTr>
			<template:formTr name="����ʱ��">
				<bean:write name="detailInfo" property="retime" />
			</template:formTr>
			<template:formTr name="�������">
				<bean:write name="detailInfo" property="name" />
			</template:formTr>
			<template:formTr name="�м̶���">
				<bean:write name="detailInfo" property="sublinename" />
			</template:formTr>

			<template:formTr name="���ԭ��">
				<bean:write name="detailInfo" property="reason" />
			</template:formTr>
			<template:formTr name="��ӵص�">
				<bean:write name="detailInfo" property="address" />
			</template:formTr>
			<!--  
			    <template:formTr name="�����߶�">
			    	<%
			    		String sublinenamecon = ((LineCutBean) request.getAttribute("detailInfo")).getSublinename();
						String[] atrArr = sublinenamecon.split("<br>");
						for(int i = 0; i < atrArr.length; i++ ) { %>
							<%=atrArr[i]%>;<br>
					<%}%>
			    </template:formTr>
			    -->

			<template:formTr name="�������">
				<bean:write name="detailInfo" property="cutType" />
			</template:formTr>

			<template:formTr name="�ƻ��������">
				<bean:write name="detailInfo" property="protime" />
			</template:formTr>

			<template:formTr name="ʵ�ʸ������">
				<bean:write name="detailInfo" property="essetime" />
			</template:formTr>

			<template:formTr name="Ԥ����ʱ(Сʱ)">
				<bean:write name="detailInfo" property="prousetime" />
			</template:formTr>

			<template:formTr name="ʵ����ʱ(Сʱ)">
				<bean:write name="detailInfo" property="usetime" />
			</template:formTr>

			<template:formTr name="���ǰ˥��">
				<bean:write name="detailInfo" property="provalue" />�ֱ�(db)
			    </template:formTr>

			<template:formTr name="��Ӻ�˥��">
				<bean:write name="detailInfo" property="endvalue" />�ֱ�(db)
				</template:formTr>

			<template:formTr name="��Ӱ��ϵͳ">
				<bean:write name="detailInfo" property="efsystem" />
			</template:formTr>

			<template:formTr name="�޸�����">
				<bean:write name="detailInfo" property="updoc" />
			</template:formTr>

			<template:formTr name="���뱸ע">
				<bean:write name="detailInfo" property="reremark" />
			</template:formTr>

			<template:formTr name="���븽��">
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
				<template:formTr name="�������">
					<bean:write name="detailInfo" property="auditresult" />
				</template:formTr>

				<template:formTr name="����ʱ��">
					<bean:write name="detailInfo" property="audittime" />
				</template:formTr>

				<template:formTr name="������">
					<bean:write name="detailInfo" property="audituser" />
				</template:formTr>

				<template:formTr name="������ע">
					<bean:write name="detailInfo" property="auditremark" />
				</template:formTr>

				<template:formTr name="��������">
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
			        <template:formTr name="ʩ��ʱ��">
					    <bean:write name="detailInfo" property="essetime"/>
					</template:formTr>
	            </logic:notEmpty>
	
	           
				
			    <template:formTr name="��������">
					 <bean:write name="detailInfo" property="manpower"/>����
				</template:formTr>
	
	            <template:formTr name="�����ʱ">
					<bean:write name="detailInfo" property="usetime"/>Сʱ
				</template:formTr>
	            
	            <template:formTr name="ʩ����λ">
			         <bean:write name="detailInfo" property="contractorname"/>
			    </template:formTr>
			    
			     <template:formTr name="ʩ������">
	                 <logic:empty name="detailInfo" property="workacce">
	                     	<apptag:listAttachmentLink fileIdList=""/>
	                 </logic:empty>
	                 <logic:notEmpty name="detailInfo" property="workacce">
	                    	<bean:define id="temw" name="detailInfo" property="workacce" type="java.lang.String"/>
						   	<apptag:listAttachmentLink fileIdList="%=temw%"/>
	                 </logic:notEmpty>
				</template:formTr>
				
				  <template:formTr name="���ո���">
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
				<template:formTr name="ʩ����ע">
					<bean:write name="detailInfo" property="workremark" />
				</template:formTr>
			</logic:notEmpty>

			
			<%
				if(((String)state).indexOf("����")>0 || ((String)state).indexOf("�鵵")>0){
			%>
				<tr heigth="40" class=trcolor>
					<td class="tdr" colspan="6">
						<hr />
					</td>
				</tr>

				<template:formTr name="������">
					<bean:write name="detailInfo" property="auusername" />
				</template:formTr>

				<template:formTr name="����ʱ��">
					<bean:write name="detailInfo" property="paudittime" />
				</template:formTr>

				<template:formTr name="���ձ�ע">
					<bean:write name="detailInfo" property="acceptremark" />
				</template:formTr>

				<template:formTr name="���ս��">
					<bean:write name="detailInfo" property="acceptresult" />
				</template:formTr>
			<% }%>
			<!--  
                  <tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
                 
	             
			    <template:formTr name="�鵵�ļ�">
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
			<input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
		</p>

		<iframe id="hiddenIframe" style="display:none"></iframe>
	</body>

</html>
