<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<body text="#000000">
	<p>
		&nbsp;
	</p>
	<p>
		&nbsp;
	</p>
	<p>
		&nbsp;
	</p>
	<table width="95%" border="0" cellspacing="0" cellpadding="6"
		align="center">
		<tr>
			<td>
				<br />
				<br />
				<br />
				<table border="0" cellspacing="10" cellpadding="0" align="center"
					bgcolor="8DAAD8" bordercolor="#000000">
					<tr>
						<td width="60" height="60" bgcolor="#FFFFFF" align="center">
							<img src="${ctx}/images/error.gif" width="49" height="49">
						</td>
						<td width="250" bgcolor="#FFFFFF" align="center">
							<logic:equal value="nouser" name="loginerror">
            		��½ʧ��,û�и��û�!!!
                </logic:equal>
							<logic:equal value="accerror" name="loginerror">
            		��½ʧ��,���û���ͣ��!!!
                </logic:equal>
							<logic:equal value="passerror" name="loginerror">
            		��½ʧ��,�������!!!
                </logic:equal>
							<logic:equal value="accounttermerror" name="loginerror">
            		��½ʧ��,�ʺŹ���!!!
                </logic:equal>
							<logic:equal value="perror" name="loginerror">
            		��½ʧ��,�������!!!
                </logic:equal>
							<logic:equal value="syserror" name="loginerror">
            		��½ʧ�ܡ������µ�½�������Ա��ϵ��
                </logic:equal>
							<logic:equal value="powererror" name="loginerror">
            		��½ʧ�ܡ�û��Ȩ��,�������Ա��ϵ��
                </logic:equal>
							<logic:equal value="nodept" name="loginerror">
            		��¼ʧ�ܡ�û���ҵ��û����ڵĲ�����Ϣ��
                </logic:equal>
						</td>

					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table border="0" cellspacing="10" cellpadding="0" align="center">
		<tr>
			<td align="center">
				<img border="0" style="cursor: hand" src="${ctx}/images/goback.gif"
					width="80" height="22" onClick="backForm();" alt="">
				<!--javascript:self.location.replace('${ctx}/mainWeb.htm')-->
			</td>
		</tr>
	</table>
	<p>
		&nbsp;
	</p>
	<script type="text/javascript">
	function backForm() {
		var url = "${ctx}/frames/login.do?method=mainweb";
		self.location.replace(url);
		parent.location.replace(url);
	}
	document.onkeydown=function(event){
		e = event ? event :(window.event ? window.event : null);
		if(e.keyCode==13){  
			backForm();
		}
  };
</script>