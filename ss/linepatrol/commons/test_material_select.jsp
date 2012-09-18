<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/common/header.jsp"%>
<form>
	<apptag:materialselect label="使用材料" materialUseType="Use"
		objectId="001" useType="lp_trouble_info" />
	<apptag:materialselect label="回收材料" materialUseType="Recycle"
		objectId="002" useType="lp_trouble_info" />
</form>
<apptag:materialselect label="使用材料" materialUseType="Use" objectId="001"
	useType="lp_trouble_info" displayType="view" />
<apptag:materialselect label="回收材料" materialUseType="Recycle"
	objectId="002" useType="lp_trouble_info" displayType="view" />