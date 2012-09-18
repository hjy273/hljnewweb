<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://www.cabletech.com.cn/baseinfo" prefix="baseinfo"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ taglib uri="cabletechtag" prefix="apptag"%>
<%@ taglib uri="templatetag" prefix="template"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css" />
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/js/WdatePicker/lang/zh-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jQuery/jquery-1.7.1.min.js"></script>
<!--jvalidation  -->
<script type="text/javascript" src="${ctx}/js/jQuery/jvalidation/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/js/jQuery/jvalidation/messages_cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jQuery/jvalidation/additional-methods.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jQuery/jvalidation/jquery.validate.ex.js"></script>
<script src="${ctx}/js/extjs/adapter/ext/ext-base.js" type="text/javascript"></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-lang-zh_CN.js'></script>
<script type="text/javascript">
	//$.noConflict();
	var jQuery = $;
	Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
</script>

<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.beanutils.*" %>
<%@ page import="com.cabletech.sysmanage.domainobjects.*"%>


