<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<br />
	<br />
	<br />
	<div align="center">
	<img src="${ctx}/images/error.gif" width="49" height="49">
	</div>
	<br/>
	<div align="center">
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
    </div>
    <div align="center">
	<input type="button" value="返回" onClick="backForm();" alt="">
	</div>
	<p>
		&nbsp;
	</p>
	<script type="text/javascript">
	function backForm() {
		var url = "${ctx}/wap/login.jsp";
		location = url;
	}
</script>