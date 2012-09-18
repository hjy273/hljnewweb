<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html; charset=GBK" %>
<jsp:directive.page import="com.cabletech.baseinfo.beans.UserInfoBean"/>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="cabletechtag" prefix="apptag"%>
<%@ taglib uri="templatetag" prefix="template"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tlds/ctags.tld" prefix="ct" %>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.beanutils.*" %>
<script type="text/javascript">
	exitSys = function() {
		if (confirm("您确实要退出系统吗？")) {
			location = "${ctx}/wap/login.do?method=relogin";
		}
	};
</script>
<style type="text/css">
.dept {
	font-size: 14px;
	width: 48px;
	text-align: right;
	float: right;
}

.dept1 {
	font-size: 14px;
	width: 100px;
	float: left;
	color:red;
}
.dept2 {
	font-size: 14px;
	width: 60px;
	float: left;
}
</style>
