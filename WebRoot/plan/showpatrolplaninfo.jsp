<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<head>
		<title>巡检计划信息表</title>
		<script type="text/javascript" language="javascript">
 		function toUpForm(id,type){
 			var url = "${ctx}/TowerPatrolinfo.do?method=showUp&id=" + id+"&type=" + type;
    		 self.location.replace(url);
 		}

  		function toDelForm(id,type){
			var con = confirm("确定要删除该条记录?"); 
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
		<template:titile value="巡检计划信息表" />
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="18">
			<display:column property="regionname" title="巡检区县" maxLength="18" />
			<display:column title="代维单位" property="contractorname" />
			<display:column property="patrolgroupname" title="巡检组" maxLength="20" />
			<display:column title="作业计划类型" sortable="true">
				<logic:equal value="1" property="plantype" name="currentRowObject">
						半年
					</logic:equal>
				<logic:equal value="2" property="plantype" name="currentRowObject">
						季度
					</logic:equal>
				<logic:equal value="3" property="plantype" name="currentRowObject">
						月度
					</logic:equal>
				<logic:equal value="4" property="plantype" name="currentRowObject">
						自定义
					</logic:equal>
			</display:column>
			<display:column property="starttime" title="计划开始时间"
				format="{0,date,yyyy-MM-dd}" />
			<display:column property="endtime" title="计划结束时间"
				format="{0,date,yyyy-MM-dd}" />
			<display:column media="html" title="计划名称" sortable="true">
				<a
					href="<%=request.getContextPath()%>/TowerPatrolinfo.do?method=showOnePlanInfo&planid=${currentRowObject.id}">
					${currentRowObject.planname} </a>
			</display:column>
			<display:column property="patrolcount" title="巡检数量" maxLength="25" />
			<display:column property="planstatename" title="审核状态" sortable="true" />
			<display:column media="html" title="操作">
				<logic:notEqual value="03" name="currentRowObject"
					property="planstate">
					<apptag:privilege operation="edit">
						<a
							href="javascript:toUpForm('${currentRowObject.id}','${currentRowObject.businesstype}')">修改</a>
					</apptag:privilege>
					<apptag:privilege operation="del">
						<a
							href="javascript:toDelForm('${currentRowObject.id}','${currentRowObject.businesstype}')">删除</a>
					</apptag:privilege>
				</logic:notEqual>
			</display:column>
		</display:table>
	</body>
</html>