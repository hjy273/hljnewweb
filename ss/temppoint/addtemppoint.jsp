<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>������ʱ��</title>
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
									<b>��ѡ��Ҫ�����Excel�ļ�:</b>
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
									<html:button styleClass="button" value="��������" property="sub"
										onclick="javascript:submitFrm()">�ύ</html:button>
									<html:button property="action" styleClass="button2" onclick="downloadFile()">��ʱ��ģ������</html:button>   
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
