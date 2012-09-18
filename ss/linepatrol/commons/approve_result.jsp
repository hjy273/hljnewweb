<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/common/header.jsp"%>
<!-- 
 以下2个输入参数需要在具体审核表单中进行赋值
<input type="hidden" name="objectId" value="" />
<input type="hidden" name="objectType" value="" />
 -->
 
<tr>
	<td height="25" style="text-align: right;">
		审核结果：
	</td>
	<td colspan="3" style="text-align: left;">
		<input type="radio" name="approveResult" value="1" checked />
		通过
		<input type="radio" name="approveResult" value="0" />
		不通过
	</td>
</tr>
<tr>
	<td height="25" style="text-align: right;">
		审核意见：
	</td>
	<td colspan="3" style="text-align: left;">
		<textarea name="approveRemark" rows="6" style="width: 400px;"></textarea>
	</td>
</tr>

