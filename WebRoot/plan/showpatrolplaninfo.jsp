<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<head>
		<title>Ѳ��ƻ���Ϣ��</title>
		<script type="text/javascript" language="javascript">
 		function toUpForm(id,type){
 			var url = "${ctx}/TowerPatrolinfo.do?method=showUp&id=" + id+"&type=" + type;
    		 self.location.replace(url);
 		}

  		function toDelForm(id,type){
			var con = confirm("ȷ��Ҫɾ��������¼?"); 
			if(con == true){ 
				var url = "${ctx}/TowerPatrolinfo.do?method=deleteInfo&id=" + id+"&type=" + type;
   			 self.location.replace(url);
		    }
        }       
        function toViewForm(id){
        var url="${ctx}/TowerPatrolinfo.do?method=getPlanDetail&xtbh="+xtbh;
        self.location.replace(url);
        }
    function addGoBack()
        {
            try
			{
                location.href = "${ctx}/TowerPatrolinfo.do?method=showPlanInfo";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
</script>
	</head>
	<body>
		<template:titile value="Ѳ��ƻ���Ϣ��" />
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="18">
			<display:column property="regionname" title="Ѳ������" maxLength="18" />
			<display:column title="��ά��λ" property="contractorname" />
			<display:column property="patrolgroupname" title="Ѳ����" maxLength="20" />
			<display:column title="��ҵ�ƻ�����" sortable="true">
				<logic:equal value="1" property="plantype" name="currentRowObject">
						����
					</logic:equal>
				<logic:equal value="2" property="plantype" name="currentRowObject">
						����
					</logic:equal>
				<logic:equal value="3" property="plantype" name="currentRowObject">
						�¶�
					</logic:equal>
				<logic:equal value="4" property="plantype" name="currentRowObject">
						�Զ���
					</logic:equal>
			</display:column>
			<display:column property="starttime" title="�ƻ���ʼʱ��"
				format="{0,date,yyyy-MM-dd}" />
			<display:column property="endtime" title="�ƻ�����ʱ��"
				format="{0,date,yyyy-MM-dd}" />
			<display:column media="html" title="�ƻ�����" sortable="true">
				<a
					href="<%=request.getContextPath()%>/TowerPatrolinfo.do?method=showOnePlanInfo&planid=${currentRowObject.id}">
					${currentRowObject.planname} </a>
			</display:column>
			<display:column property="patrolcount" title="Ѳ������" maxLength="25" />
			<display:column property="planstatename" title="���״̬" sortable="true" />
			<display:column media="html" title="����">
				<logic:notEqual value="03" name="currentRowObject"
					property="planstate">
					<apptag:privilege operation="edit">
						<a
							href="javascript:toUpForm('${currentRowObject.id}','${currentRowObject.businesstype}')">�޸�</a>
					</apptag:privilege>
					<apptag:privilege operation="del">
						<a
							href="javascript:toDelForm('${currentRowObject.id}','${currentRowObject.businesstype}')">ɾ��</a>
					</apptag:privilege>
				</logic:notEqual>
			</display:column>
		</display:table>
	</body>
</html>