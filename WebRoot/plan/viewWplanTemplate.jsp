<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<script language="javascript" type="text/javascript">
jQuery.fn.rowspan = function(colIdx) {
return this.each(function(){
var that;
  jQuery('tr', this).each(function(row) {
  jQuery('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {
  //add that!=null by hxzon
        if (that!=null && jQuery(this).html() == jQuery(that).html()) {
          rowspan = jQuery(that).attr("rowSpan");
          if (rowspan == undefined) {
   
            jQuery(that).attr("rowSpan",1);
            rowspan = jQuery(that).attr("rowSpan");
          }
          rowspan = Number(rowspan)+1;
          jQuery(that).attr("rowSpan",rowspan); // do your action for the colspan cell here
          jQuery(this).hide(); // .remove(); // do your action for the old cell here
        } else {
          that = this;
        }
        //that = (that == null) ? this : that; // set the that if not already set
  });
 });

});
}
jQuery.fn.colspan = function(rowIdx) {
return this.each(function(){
var that;
  jQuery('tr', this).filter(":eq("+rowIdx+")").each(function(row) {
  jQuery(this).find('td').filter(':visible').each(function(col) {
  
        if (that!=null && jQuery(this).html() == jQuery(that).html()) {
          colspan = jQuery(that).attr("colSpan");
          if (colspan == undefined) {
            jQuery(that).attr("colSpan",1);
            colspan = jQuery(that).attr("colSpan");
          }
          colspan = Number(colspan)+1;
          jQuery(that).attr("colSpan",colspan);
          jQuery(this).hide(); // .remove();
        } else {
          that = this;
        }
        //that = (that == null) ? this : that; // set the that if not already set
  });
 });
});
}

jQuery(function(){
 jQuery("#_table2").rowspan(0);
});
</script>
</head>
<body>
	<template:titile value="查看计划模板信息" />
	<br />
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		   <tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					模板名称：
				</td>
				<td class="tdulright" style="width:85%">
				${vo.templateName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					专业：
				</td>
				<td class="tdulright" style="width:85%">
				${vo.businessTypeName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					备注：
				</td>
				<td class="tdulright" style="width:85%">
				${vo.remark}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					巡检项：
				</td>
				<td class="tdulright" style="width:85%">
				  <table id="_table2">
				    		<c:forEach var="item" items="${items}" varStatus="stauts">
               					<tr >
               					    <td style='<c:if test="${stauts.index>0}">border-top:1px solid #7DBADB;</c:if> text-align:left;width:20%'>
										${item.ITEM_NAME}
									</td>
               					    <td style="<c:if test="${stauts.index>0}">border-top:1px solid #7DBADB;</c:if> text-align:left;width:5%">
										${stauts.index+1} : 
									</td>
									<td style="<c:if test="${stauts.index>0}">border-top:1px solid #7DBADB;</c:if> text-align:left;width:60%">
										${item.SUBITEM_NAME}
									</td>
									<td style="<c:if test="${stauts.index>0}">border-top:1px solid #7DBADB;</c:if> text-align:left;width:15%">
										<c:if test="${empty item.QUALITY_STANDARD}">&nbsp;</c:if>
										<c:if test="${!empty item.QUALITY_STANDARD}">${item.QUALITY_STANDARD}</c:if>
									</td>
								</tr>
            				</c:forEach>
				  </table>
				</td>
			</tr>
			<tr class="trwhite">
				<td align="center" colspan="2">
					<input type="button" class="button" onclick="history.back()" value="返回" >
				</td>
			</tr>
		</table>
</body>