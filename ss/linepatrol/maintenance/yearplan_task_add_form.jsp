<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>测试数据</title>
			<script type='text/javascript'>
				function submitForm(){
				var reg=/^\d+$/;
					var preTestNum =  document.getElementById('preTestNum').value;
					if(preTestNum==""){
						alert("变更前测试次数不能为空!");
						return ;
					}
					if(!reg.test(preTestNum) || preTestNum<0){
						alert("变更前测试次数必须为大于等于0的数字!");
						return false;
				    }
					var applyNum =  document.getElementById('applyNum').value;
					if(applyNum==""){
						alert("申请测试次数不能为空!");
						return ;
					}
					if(!reg.test(applyNum) || applyNum<=0){
						alert("申请测试次数必须为大于0的数字!");
						return false;
				    }
					parent.saveTask($('addYearTask'));
				}
		   </script>
	</head>

	<body>
		<html:form action="/testYearPlanAction.do?method=addPlanTask"
			styleId="addYearTask"  method="post" >
		    <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			    <tr  class=trcolor>
			      <td class="tdulleft">光缆级别：</td>
			      <td class="tdulright">
			      	<apptag:quickLoadList cssClass="select" style="width:155px"  id="cableLevel" name="cableLevel" listName="cabletype" type="select"></apptag:quickLoadList>
				  </td>
			    </tr>
			     <tr  class=trwhite>
			      <td class="tdulleft">变更前测试次数：</td>
			      <td class="tdulright">
			      	<html:text property="preTestNum" styleId="preTestNum" value="${testTimes}"></html:text>
			      </td>
			    </tr>
			    <tr  class=trcolor>
			      <td class="tdulleft">申请测试次数：</td>
			      <td class="tdulright">
			        <html:text property="applyNum" styleId="applyNum"></html:text>
			      </td>
			    </tr>
			      <tr  class=trwhite>
			      <td class="tdulleft">变更中继段：</td>
			      <td class="tdulright">
			      	<apptag:trunk id="trunkIds" />
			      </td>
			    </tr>
			    <tr class=trwhite>
			      <td align="center" colspan="2">
			        <input type="button"  onclick="submitForm();" class="button" value="提交"/>
			        <input type="button"  onclick="javascript:parent.close();" class="button" value="关闭"/>
			      </td>
			    </tr>
			  </table>
		</html:form>
	</body>
</html>
