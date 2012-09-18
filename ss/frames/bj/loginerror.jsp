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
            		登陆失败,没有该用户!!!
                </logic:equal>
							<logic:equal value="accerror" name="loginerror">
            		登陆失败,该用户已停用!!!
                </logic:equal>
							<logic:equal value="passerror" name="loginerror">
            		登陆失败,请检查口令!!!
                </logic:equal>
							<logic:equal value="accounttermerror" name="loginerror">
            		登陆失败,帐号过期!!!
                </logic:equal>
							<logic:equal value="perror" name="loginerror">
            		登陆失败,密码过期!!!
                </logic:equal>
							<logic:equal value="syserror" name="loginerror">
            		登陆失败。请重新登陆或与管理员联系！
                </logic:equal>
							<logic:equal value="powererror" name="loginerror">
            		登陆失败。没有权限,请与管理员联系！
                </logic:equal>
							<logic:equal value="nodept" name="loginerror">
            		登录失败。没有找到用户所在的部门信息！
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