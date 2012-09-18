<%@ include file="/common/header.jsp"%>
<template:titile value="查询信息"/>

<html:form  method="Post" action="/NoticeAction.do?method=queryNotice" >
    	<template:formTable >
		<template:formTr name="关键字" isOdd="false">
            <html:text property="contentString"  styleClass="inputtext" style="width:300px" maxlength="20"/>
        </template:formTr>
        <template:formTr name="类型" isOdd="true">
        	<html:select property="type" styleClass="inputtext"  style="width:150px" >
        		<html:option value="">不限</html:option>
        		
        		<html:option value="公告">公告</html:option>
        	 	<html:option value="会议">会议</html:option>
        	 	<html:option value="新闻">新闻</html:option>
        	</html:select>        
        </template:formTr>
		
		<template:formTr name="重要程度" isOdd="false">
        	<html:select property="grade" styleClass="inputtext"  style="width:150px" >
        		<html:option value="">不限</html:option>
        		<html:option value="重要">重要</html:option>
        		<html:option value="一般">一般</html:option>
        	</html:select>        
        </template:formTr>
        
		<template:formTr name="是否发布" isOdd="true">
			<html:select property="isissue" styleClass="inputtext"  style="width:150px" >
				<html:option value="">不限</html:option>
				<html:option value="y">已发布</html:option>
				<html:option value="n">未发布</html:option>
			</html:select>
		</template:formTr>
		
		<template:formTr name="发布日期" isOdd="false">
			<html:text property="begindate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})"/>--<html:text property="enddate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})"/>
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
