<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>��������</title>
			<script type='text/javascript'>
				function submitForm(){
				var reg=/^\d+$/;
					var preTestNum =  document.getElementById('preTestNum').value;
					if(preTestNum==""){
						alert("���ǰ���Դ�������Ϊ��!");
						return ;
					}
					if(!reg.test(preTestNum) || preTestNum<0){
						alert("���ǰ���Դ�������Ϊ���ڵ���0������!");
						return false;
				    }
					var applyNum =  document.getElementById('applyNum').value;
					if(applyNum==""){
						alert("������Դ�������Ϊ��!");
						return ;
					}
					if(!reg.test(applyNum) || applyNum<=0){
						alert("������Դ�������Ϊ����0������!");
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
			      <td class="tdulleft">���¼���</td>
			      <td class="tdulright">
			      	<apptag:quickLoadList cssClass="select" style="width:155px"  id="cableLevel" name="cableLevel" listName="cabletype" type="select"></apptag:quickLoadList>
				  </td>
			    </tr>
			     <tr  class=trwhite>
			      <td class="tdulleft">���ǰ���Դ�����</td>
			      <td class="tdulright">
			      	<html:text property="preTestNum" styleId="preTestNum" value="${testTimes}"></html:text>
			      </td>
			    </tr>
			    <tr  class=trcolor>
			      <td class="tdulleft">������Դ�����</td>
			      <td class="tdulright">
			        <html:text property="applyNum" styleId="applyNum"></html:text>
			      </td>
			    </tr>
			      <tr  class=trwhite>
			      <td class="tdulleft">����м̶Σ�</td>
			      <td class="tdulright">
			      	<apptag:trunk id="trunkIds" />
			      </td>
			    </tr>
			    <tr class=trwhite>
			      <td align="center" colspan="2">
			        <input type="button"  onclick="submitForm();" class="button" value="�ύ"/>
			        <input type="button"  onclick="javascript:parent.close();" class="button" value="�ر�"/>
			      </td>
			    </tr>
			  </table>
		</html:form>
	</body>
</html>
