<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>导入临时点</title>
		<script type="text/javascript">
		  function $(id){
		    return document.getElementById(id);
		  }
		  function submitFrm(){
		     var frm = $('upform');
		         frm.submit()
		  }
		  
		  function downloadFile() {
  			location.href = "${ctx}/TemppointAction.do?method=downloadTemplet";
		  }
		  
		</script>
	</head>
	<body>

		<html:form styleId="upform"
			action="/TemppointAction?method=upLoadTemppoint" method="post"
			enctype="multipart/form-data">
			<table align="center" border="0" width="600" height="100%">
				<tr>
					<td valign="top" height="100%">
						<table align="center" border="0">
							<tr>
								<td align="left" height="50">
									<br />
									<br />
									<br />
									<br />
									<br />
									<br />
									<br />
									<b>请选择要导入的Excel文件:</b>
									<br />
								</td>
							</tr>
							<tr>
								<td>
									<html:file property="file" style="width:300px"
										styleClass="inputtext" />
								</td>
							</tr>
							<tr>
								<td align="center" valign="middle" height="60">
									<html:button styleClass="button" value="导入数据" property="sub"
										onclick="javascript:submitFrm()">提交</html:button>
									<html:button property="action" styleClass="button2" onclick="downloadFile()">临时点模板下载</html:button>   
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
