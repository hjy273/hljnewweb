<%@include file="/common/header.jsp"%>
<script type="">
function toGetBack(){
 	var url="${ctx}/pointAction.do?method=queryPoint&pointName=&addressInfo=";
	self.location.replace(url);
}
function check(){
  if(document.pointBean.inumber.value!=""){
    var data = /^\d{1,10}$/;//
    if(!data.test(document.pointBean.inumber.value)){
      document.pointBean.inumber.focus();
      alert("线顺只能为数字！");
      document.pointBean.inumber.value="";
      return false;
    }
  }
}
</script>
<br>
<template:titile value="查询巡检点信息" />
<html:form method="Post" action="/pointAction.do?method=queryPoint">
	<template:formTable>
		<template:formTr name="巡检点名称">
			<input name="isQuery" type="hidden" value="1" />
			<html:text property="pointName" styleClass="inputtext"
				style="width:200px" maxlength="20" />
		</template:formTr>
		<template:formTr name="巡检点位置" isOdd="false">
			<html:text property="addressInfo" styleClass="inputtext"
				style="width:200px" maxlength="25" />
		</template:formTr>
		<!-- 
    <template:formTr name="GPS坐标">
      <html:text property="gpsCoordinate" styleClass="inputtext" style="width:200px" maxlength="17"/>
    </template:formTr>
     -->
		<template:formTr name="所属巡检段" isOdd="false">
			<apptag:setSelectOptions valueName="sublineCollection"
				tableName="sublineinfo" columnName1="sublinename"
				columnName2="sublineid" region="true" />
			<html:select property="subLineID" styleClass="inputtext"
				style="width:200px">
				<html:options collection="sublineCollection" property="value"
					labelProperty="label" />
			</html:select>
		</template:formTr>
		<template:formTr name="是否关键点">
			<html:select property="isFocus" styleClass="inputtext"
				style="width:200px">
				<html:option value="">不限</html:option>
				<html:option value="0">否</html:option>
				<html:option value="1">是</html:option>
			</html:select>
		</template:formTr>
		<!-- 
    <template:formTr name="所在线段顺序" isOdd="false">
      <html:text property="inumber" styleClass="inputtext" style="width:200px" maxlength="10" onblur="check()"/>
    </template:formTr>
     -->
		<template:formTr name="是否巡检点">
			<html:select property="state" styleClass="inputtext"
				style="width:200px">
				<html:option value="">不限</html:option>
				<html:option value="0">否</html:option>
				<html:option value="1">是</html:option>
			</html:select>
		</template:formTr>

		<template:formSubmit>
			<td>
				<html:submit styleClass="button">查询</html:submit>
			</td>
			<td>
				<html:reset styleClass="button">取消</html:reset>
			</td>
			<td>
				<input type="button" class="button" onclick="toGetBack()" value="返回">
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
