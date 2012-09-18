<%@include file="/common/header.jsp"%>
<script type="text/javascript">
	parent.$('trunknames').value = '${trunknames}';
	parent.$('${id}').value = '${trunkIds}';
	parent.close();
</script>