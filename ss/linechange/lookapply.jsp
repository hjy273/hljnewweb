<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linechange.bean.*;"%>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script language="javascript" type="">
	showHistoryDetail=function(value){
		var actionUrl="${ctx}/changesurveyaction.do?method=showSurveyHistory&change_id="+value+"&rnd="+Math.random();
		var myAjax=new Ajax.Updater(
			"applyAuditingTd",actionUrl,{
			method:"post",
			evalScript:true
			}
		);
	}
 function addGoBack()
    {
      var url = "${ctx}/changeapplyaction.do?method=getApplyInfoList";
      self.location.replace(url);
    }
</script>
<!--
  [if IE]>
  <style>
  div {word-break:break-all;;}
  </style>
  <![endif]
-->
<style type="">div {width:300px;border:solid 1px #000;}</style>
<body>
<template:titile value="�鿴������ϸ��Ϣ"/>
<html:form action="/changeapplyaction?method=addApply" styleId="addApplyForm" enctype="multipart/form-data">
  <template:formTable namewidth="150" contentwidth="350">
    <template:formTr name="��������">
      <bean:write name="changeinfo" property="changename"/>
    </template:formTr>
    <template:formTr name="��������">
      <bean:write name="changeinfo" property="changepro"/>
    </template:formTr>
    <template:formTr name="���̵ص�">
      <bean:write name="changeinfo" property="changeaddr"/>
    </template:formTr>
    <template:formTr name="��������">
      <%=(String)request.getAttribute("line_class_name") %>
    </template:formTr>
    <template:formTr name="Ӱ��ϵͳ">
      <bean:write name="changeinfo" property="involvedSystem"/>
    </template:formTr>
    <template:formTr name="Ǩ�ĳ���">
      <bean:write name="changeinfo" property="changelength"/>
      ��
    </template:formTr>
    <template:formTr name="��ʼʱ��">
      <bean:write name="changeinfo" property="startdate"/>
    </template:formTr>
    <template:formTr name="�ƻ�����">
      <bean:write name="changeinfo" property="plantime"/>
      ��
    </template:formTr>
    
    
<template:formTr name="����˾">
<%=(String)request.getAttribute("supervise_unit_name") %>
</template:formTr>    
    <template:formTr name="����Ԥ��">
      <bean:write name="changeinfo" property="budget"/>��Ԫ
    </template:formTr>


<template:formTr name="ί��ʩ����λ����">
<bean:write name="changeinfo" property="entrustunit"/>
</template:formTr>
<template:formTr name="ί��ʩ����λ��ַ">
<bean:write name="changeinfo" property="entrustaddr"/>
</template:formTr>
<template:formTr name="ί��ʩ����λ�绰">
<bean:write name="changeinfo" property="entrustphone"/>
</template:formTr>
<template:formTr name="ί��ʩ����λ��ϵ��">
<bean:write name="changeinfo" property="entrustperson"/>
</template:formTr>
<template:formTr name="ί��ʩ����λ����">
<bean:write name="changeinfo" property="entrustgrade"/>
</template:formTr>

<template:formTr name="ʩ����λ����">
<bean:write name="changeinfo" property="buildunit"/>
</template:formTr>
<template:formTr name="ʩ����ʼ����">
<bean:write name="changeinfo" property="starttime"/>
</template:formTr>


    <template:formTr name="��ע��Ϣ">
      <div>
        <bean:write name="changeinfo" property="remark"/>
      </div>
    </template:formTr>
    <template:formTr name="����">
      <logic:empty name="changeinfo" property="applydatumid">
        <apptag:listAttachmentLink fileIdList=""/>
      </logic:empty>
      <logic:notEmpty name="changeinfo" property="applydatumid">
        <bean:define id="temp" name="changeinfo" property="applydatumid" type="java.lang.String"/>
        <apptag:listAttachmentLink fileIdList="<%=temp%>"/>
      </logic:notEmpty>
    </template:formTr>
    <tr>
		<td colspan="3" id="applyAuditingTd"></td>
	</tr>
    <template:formSubmit>
      <td>
      	<logic:equal value="1" name="flg">
      		  <input type="button" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
      	</logic:equal>
      	<logic:equal value="2" name="flg">
      		<html:button property="action" styleClass="button" onclick="window.close();">�ر�</html:button>
      	</logic:equal>
        
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
			<script type="text/javascript">
			showHistoryDetail("<bean:write name="changeinfo" property="id" />");
			</script>
</body>
