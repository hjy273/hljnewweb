<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function doUpAccident(idValue, statusFlag){
    if(statusFlag == "������"){
      var url = "${ctx}/accidentAction.do?method=loadTrouble&statusFlag=0&id=" + idValue;
      self.location.replace(url);
    }
    if(statusFlag == "������"){
     var url = "${ctx}/accidentAction.do?method=loadTrouble&id=" + idValue +"&statusFlag=2" ;
      self.location.replace(url);
    }
    if(statusFlag == "�Ѵ���"){
      var url = "${ctx}/accidentAction.do?method=loadTrouble&id=" + idValue +"&statusFlag=3";
      self.location.replace(url);
    }
    if(statusFlag == "�Ѻ���"){
      alert("�������Ѿ����ԣ�");
      return;
    }

}
</script><template:titile value="������ѯ�б�"/>
<display:table name="sessionScope.queryresult" pagesize="20" id="currentRowObject">
  <display:column property="contractor" title="��ά��λ" sortable="true"/>
  <display:column property="sendtime" title="�ϱ�ʱ��" sortable="true"/>
  <display:column property="reason" title="�ϰ�ԭ��" sortable="true"/>
  <display:column property="emlevel" title="���س̶�" sortable="true"/>
  <display:column property="subline" title="Ѳ���" sortable="true"/>
  <display:column property="point" title="Ѳ���" sortable="true"/>
  <display:column property="status" title="״̬" sortable="true"/>
  <display:column media="html" title="����">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
    String statusFlag = (String) object.get("status");
    String lableName = "���������";
    if (statusFlag.equals("������")) {
      lableName = "���������";
    }

    if (statusFlag.equals("������")) {
      lableName = "��д��ɱ��浥";
    }
    if (statusFlag.equals("�Ѻ���")) {
      lableName = "��������";
    }
    if (statusFlag.equals("�Ѵ���")) {
      lableName = "�����鿴��ϸ����";
    }
  %>
    <a href="javascript:doUpAccident('<%=id%>','<%=statusFlag%>')"><%=lableName%>    </a>
  </display:column>
</display:table>

