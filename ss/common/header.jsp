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

<script type="text/javascript" src="${ctx}/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
      var jQuery = $;
</script>
<script language="JavaScript" src="${ctx}/js/prototype.js"></script>
<script language="JavaScript" src="${ctx}/js/processBar.js"></script>
<script language="JavaScript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<link rel="shortcut icon" type="image/x-icon" href="${ctx}/images/logo.ico" />
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.beanutils.*" %>
<%@ page import="com.cabletech.baseinfo.services.LableValues" %>
<%@ page import="com.cabletech.baseinfo.domainobjects.UserInfo" %>
<%@ page import="com.cabletech.commons.util.DateUtil" %>
