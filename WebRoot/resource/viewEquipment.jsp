<%@ page language="java" contentType="text/html; charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/js/view/simplegallery.js"></script>
<script type="text/javascript" src="${ctx}/js/view/jquery-1.2.6.pack.js"></script>

<style type="text/css">

#simplegallery1{ 
position: absolute; 
visibility: hidden; 
border: 3px solid darkred;
left:50%;
top:-51%;
}


#simplegallery1.gallerydesctext{ 
text-align: left;
padding: 2px 5px;
}

</style>
<c:if test="${PICTURES!=null}">
<script type="text/javascript">
	var mygallery=new simpleGallery({
	wrapperid: "simplegallery1",
	dimensions: [413, 291], 
	imagearray: ${PICTURES},
	autoplay: [true, 2500, 2],
	persist: false, 
	fadeduration: 500,
	oninit:function(){
	},
	onslide:function(curslide, i){
	}
	})
</script>
</c:if>
<body>
	<template:titile value="查看设备"/>
	<br />
	<table border="0" bgcolor="#FFFFFF" width="100%" align="center" cellpadding="0" cellspacing="0"><tr><td>
	<s:form action="equipmentAction_save" name="view" method="post">
		<table width="850" border="1" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					所属基站：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${BASESTATIONS[equipment.parentId]}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					设备类型：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${EQUIPMENTSTYPE[equipment.equTypeId]}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					设备名称：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${equipment.equName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					设备厂商：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${equipment.equProducter}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					设备型号：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${equipment.equModel}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					配置：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${equipment.equDeploy}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					数量：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${equipment.equNum}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					容量：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${equipment.equCapacity}
				</td>
			</tr>
			<tr class="trwhite">	
				<td class="tdulleft" style="width:15%">
					启用日期：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					<bean:write name="equipment" property="equEnableDate" format="yyyy-MM-dd" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					条形码：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
				${equipment.barCode}
				</td>
			</tr>
			<tr class="trwhite">	
				<td class="tdulleft" style="width:15%">
					备注：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${equipment.remark}
				</td>
			</tr>
			
		</table>
		<table width="40%" border="0"  style="margin-top: 6px" align="center" cellpadding="0" cellspacing="0"><tr align="center">
		      	<td>
		      	  	<c:if test="${showClose=='0'}">
		      	  	<input type="button" class="button" onclick="history.back()" value="返回" >
		      	  	</c:if>
		      	  	<c:if test="${showClose=='1'}">
		      	  	<input type="button" class="button" onclick="closeWin();" value="关闭" >
		      	  	</c:if>
		      	</td>
		    </tr></table>
	</s:form>
	</td></tr></table>
	<div id="simplegallery1" style="position:absolute;"></div>
</body>