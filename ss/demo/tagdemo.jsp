<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<html>
<script language="javascript" src="${pageContext.request.contextPath}/js/validation/prototype.js" type=""></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/validation/effects.js" type=""></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/validation/validation_cn.js" type=""></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<head>
<style type="text/css">
	.input{
		width:80px;	
	}
	
	.input_file{
		width:200px;
		height:22px;
		border:1px; 
		border-color:#AACFE4; 
		border-style:solid; 
		background-color: #FFFFFF; 
		font-size: 12px;
		line-height:24px;
	}
</style>
</head>
<body>
<div>

<pre>
<code>
quickLoadList标签使用说明：
&lt;apptag:quickLoadList  cssClass="input" name="cabletype" id="cabletype" listName="cabletype" type="select" &gt;
apptag:quickLoadList标签用于加载常用的下拉列表，复选框，单选钮等。
cssClass：是指定的样式表；
name：指表单名称
id：input 或 select 的 id；
listName：指将要从常用表单项字典表中取哪一类值；对应DICTIONARY_FORMITEM中的ASSORTMENT_ID字段；
type：表单组件类型包括：select,radio,checkbox,默认为select
</code>
</pre>
例：<br>
光缆级别：<apptag:quickLoadList cssClass="input" name="cabletype" listName="cabletype" type="select"/>
	<br>
敷设方式：<apptag:quickLoadList cssClass="checkbox" name="layingmethod" listName="layingmethod" type="checkbox"></apptag:quickLoadList>
	<br>
故障类型：<apptag:quickLoadList cssClass="radio" name="lp_trouble_type" listName="lp_trouble_type" type="radio"></apptag:quickLoadList>
</div>

<div>
<pre>
<code>
uploadFile标签使用说明：
&lt;apptag:upload  state="add" entityId="" entityType="" &gt;
state:标签的状态包括添加（add），编辑（edit），查看（look）；默认为look
entityId 是edit，look状态时，数据库中记录id，能够通过记录查询到与该记录相关的附件；
entityType 数据库表名
使用该标签需要导入js文件
</code>
</pre>

</div>	

<form action="${ctx}/uploaddemo.do?method=uploadfile" method="post" enctype="multipart/form-data">
<apptag:upload state="edit" entityId="0000001" entityType="ls_trouble" cssClass="input_file"/>
<br>
<input type="button" onclick="addRow()" value="添加"> <input type="submit" value="提交"/>
</form>

<input type="button" name="action" value="添加附件" onclick="addRow()" class="button">
<div>
<pre>
<code>
Attachment标签使用说明：
&lt;apptag:Attachment  state="add" fileIdList="" &gt;
state:标签的状态包括添加（add），编辑（edit），查看（look）；默认为look
fileIdList：edit，look状态时，附件的id；
使用该标签需要导入js文件
</code>
</pre>

</div>	
<c:if test="${'123' eq '123'}">
	sss
</c:if>
<!--  apptag:Attachment state="add" fileIdList=""/-->
<br>
<!--input type="button" name="action" value="添加附件" onclick="addRow()" class="button"-->
</body>
</html>