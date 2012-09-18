<%@ page language="java" contentType="text/html; charset=GBK"%><%@ include file="/common/header.jsp"%>
<template:titile value="查询公告信息"/>

<html:form  method="Post" action="/NoticeAction.do?method=queryNotice" >
    <template:formTable>
		<template:formTr name="关键字" >
            <html:text property="content"  styleClass="inputtext" style="width:300" maxlength="20"/>
        </template:formTr>
        <template:formTr name="类型" >
        	<html:select property="type" styleClass="inputtext">
        		<html:option value="">不限</html:option>
        		<html:option value="系统升级">系统升级</html:option>
        		<html:option value="公告">公告</html:option>
        	 	<html:option value="通知">通知</html:option>
        	 	<html:option value="新闻">新闻</html:option>
        	</html:select>        
        </template:formTr>
		
		<template:formTr name="重要程度" >
        	<html:select property="grade" styleClass="inputtext">
        		<html:option value="">不限</html:option>
        		<html:option value="重要">重要</html:option>
        		<html:option value="一般">一般</html:option>
        	</html:select>        
        </template:formTr>
        
		<template:formTr name="是否发布">
			<html:select property="isissue" styleClass="inputtext">
				<html:option value="">不限</html:option>
				<html:option value="y">已发布</html:option>
				<html:option value="n">未发布</html:option>
			</html:select>
		</template:formTr>
		
		<template:formTr name="发布日期">
			<html:text property="begindate" styleClass="inputtext"/><apptag:date property="begindate"></apptag:date>--<html:text property="enddate" styleClass="inputtext"/><apptag:date property="enddate"></apptag:date>
		</template:formTr>

		<template:formSubmit>
            <td>
            	<html:submit styleClass="button" >查询</html:submit>
            </td>
            <td>
                <html:reset styleClass="button"  >取消</html:reset>
            </td>
        </template:formSubmit>


   </template:formTable>
</html:form>
