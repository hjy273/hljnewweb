<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
			<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
			<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
			<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
			<script type="text/javascript">
				 function close(){
				  	win.close();
				 }
			</script>
	</head>
	<body><br/>
				     <div>
					 	${trunkNames}
					</div>
				   	<div align="center">	
				      	<input type="button" class="button" onclick="javascript:parent.close();" value="¹Ø±Õ"/>
				    </div>
	</body>
</html>
