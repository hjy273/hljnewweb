<%@ page language="java" pageEncoding="GBK"%>
<script src="${ctx}/js/star_rating/jquery.js" type="text/javascript"></script>
<script src="${ctx}/js/star_rating/jquery.rating.js" type="text/javascript" language="javascript"></script>
<link href="${ctx}/js/star_rating/jquery.rating.css" type="text/css" rel="stylesheet"/>
<!-- 删除 -->
<tr>
	<td height="25" style="text-align: right;">
		考核评分：
	</td>
	<td colspan="3" style="text-align: left;">
		<input type="radio" class="star" name="entityScore" value="A"/>
  		<input type="radio" class="star" name="entityScore" value="B"/>
  		<input type="radio" class="star" name="entityScore" value="C"/>
  		<input type="radio" class="star" name="entityScore" value="D"/>
  		<input type="radio" class="star" name="entityScore" value="E"/>
	</td>
</tr>
<tr>
	<td height="25" style="text-align: right;">
		评分意见：
	</td>
	<td colspan="3" style="text-align: left;">
		<textarea name="entityRemark" rows="6" style="width: 500px;"></textarea>
	</td>
</tr>