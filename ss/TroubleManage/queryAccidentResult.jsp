<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function doUpAccident(idValue, statusFlag){
    var url = "${ctx}/accidentAction.do?method=loadAccident&id=" + idValue +"&statusFlag=" + statusFlag;
    self.location.replace(url);
}
</script><template:titile value="�����ϰ��б�"/>
<display:table name="sessionScope.queryresult" pagesize="20" id="currentRowObject">
  <display:column property="sendtime" title="�ϱ�ʱ��" sortable="true"/>
  <display:column property="reason" title="�ϰ�ԭ��" sortable="true"/>
  <display:column property="emlevel" title="���س̶�" sortable="true"/>
  <display:column property="subline" title="Ѳ���" sortable="true"/>
  <display:column property="point" title="Ѳ���" sortable="true"/>
  <display:column property="contractor" title="��ά��λ" sortable="true"/>
  <display:column property="status" title="״̬" sortable="true"/>
  <display:column media="html" title="����">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
    String statusFlag = (String) object.get("statusflag");
    String lableName = "�·�����֪ͨ��";
    if (statusFlag.equals("0")) {
      lableName = "�·�����֪ͨ��";
    }
    if (statusFlag.equals("1")) {
      lableName = "��Ӧ֪ͨ";
    }
    if (statusFlag.equals("2")) {
      lableName = "��д��ɱ��浥";
    }
    if (statusFlag.equals("3")) {
      lableName = "�鿴��ɱ��浥";
    }
  %>
    <a href="javascript:doUpAccident('<%=id%>','<%=statusFlag%>')"><%=lableName%>    </a>
  </display:column>
</display:table>
 <html:link action="/accidentAction.do?method=exportAccidentResult">����ΪExcel�ļ�</html:link>
