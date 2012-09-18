<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type="text/javascript" src="${ctx}/js/prototype.js"></script>
<script language="javascript">

function toGetBack(){
        	try{
            	location.href = "${ctx}/materialInfoAction.do?method=queryPartBase";
                return true;
            }
            catch(e){
            	alert(e);
            }
        }
       /*根据材料ID，获取相应的类型信息*/
function changeModel(){
   var mid = $("typeid").value;
   var ops = document.getElementById("modelid");
	ops.options.length = 0;
	if(mid == "mention") {
	ops.options.add(new Option("===请选择材料规格===",""));
	}
    var urls = '${ctx}/materialInfoAction.do?method=queryTypeList&id='+mid+'&rnd='+Math.random();
    var pars = 'id='+mid+'&rnd='+Math.random();
    $("modelDiv").innerHTML = "<img src='${ctx}/images/indicator.gif'>";
     var myAjax = new Ajax.Updater('modelDiv',urls,
     {
      method: 'post',
      parapmeters: pars,
      onSuccess:showRs,
      onComplete: showRs
     });
}

function showRs(rs){
}		
</script>
<template:titile value="查询材料信息"/>
<html:form method="Post" action="/materialInfoAction.do?method=queryPartBase">
  <template:formTable contentwidth="240" namewidth="240">
    <template:formTr name="材料名称">
      <html:text property="name" styleClass="inputtext" style="width:205px" maxlength="200"/>
    </template:formTr>
   <template:formTr name="材料类型">
					<select name="typeid" class="inputtext" style="width:205px"
						id="typeid" onchange="changeModel()">
						<option value="mention">===请选择材料类型===</option>
						<logic:present name="typelist">
							<logic:iterate id="r" name="typelist">
								<option value="<bean:write name="r" property="id" />">
									<bean:write name="r" property="typename" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
				</template:formTr>
				<template:formTr name="材料规格">
					<div id="modelDiv" name="modelDiv">
						<select name="modelid" class="inputtext" style="width:205px"
							id="modelid">
							<option value="mention">===请选择材料规格===</option>
							<logic:present name="modellist">
								<logic:iterate id="r" name="modellist">
									<option value="<bean:write name="r" property="id" />">
										<bean:write name="r" property="modelname" />
									</option>
								</logic:iterate>
							</logic:present>
						</select>
					</div>
				</template:formTr>
	<template:formTr name="生产厂家">
      <html:text property="factory" styleClass="inputtext" style="width:205px" maxlength="200"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
      <td>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
    