<%@include file="/common/header.jsp"%>


<%@ page import="com.cabletech.partmanage.beans.*;" %>


<html>
<head>

<script type="text/javascript">
	//���ص��ϼ���ѯҳ��
	function goquery(){
		window.location.href = "${ctx}/PartBaseInfoAction.do?method=queryShow";
	}
</script>

<title>
partBaseInfo
</title>
</head>
<style type="text/css">
.subject{text-align:center;}
</style>
<body >

    <!--��ʾҳ��-->
    <logic:equal  name="type" scope="session" value="1">
        <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
          <apptag:checkpower thirdmould="80701" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
        <br>
        <template:titile value="ά��������Ϣһ����"/>
        <display:table    name="sessionScope.partInfo" requestURI="${ctx}/PartBaseInfoAction.do?method=doQuery"  id="currentRowObject"  pagesize="18">
        <%
           BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
           String name = "";
           String id = "";
           if(object != null) {
           	  name = (String)object.get("name");
           	  id = (String) object.get("id");
           }
        %>
        	 <display:column media="html" title="��������" >
        	 	 <a href="javascript:toGetForm('<%=id%>')"><%=name%></a>
        	 </display:column>
            <display:column property="name" title="��������" sortable="true" />
            <display:column property="unit" title="������λ" sortable="true"/>
            <display:column property="type" title="�����ͺ�" maxLength="8" sortable="true"/>
            <display:column property="factory" title="��������" sortable="true"/>
            <display:column property="remark" title="��ע��Ϣ"  maxLength="20"sortable="true"/>
            <display:column media="html" title="����" headerClass="subject" class="subject" style="width:80px">
                 <apptag:checkpower thirdmould="80704" ishead="0">      
                    <a href="javascript:toUpForm('<%=id%>')">�޸�</a> | 
            	</apptag:checkpower>
            	
            	<apptag:checkpower thirdmould="80705" ishead="0">
                	<a href="javascript:toDeletForm('<%=id%>')">ɾ��</a>                 
            	</apptag:checkpower>
            </display:column>
        </display:table><br>
         <div align="center"><html:button property="action" styleClass="button" onclick="goquery()" >����</html:button></div>
    <!--htmllink action="/PartBaseInfoAction.do?method=exportBaseInfo">����ΪExcel�ļ�</htmllink-->
    </logic:equal>

    <!--��ʾ��ϸ��Ϣҳ��-->
    <logic:equal value="10" name="type" scope="session">
        <logic:present name="partInfo">
            <apptag:checkpower thirdmould="80701" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
            </apptag:checkpower>
           <br />
            <template:titile value="ά��������ϸ��Ϣ"/>
            <template:formTable namewidth="150" contentwidth="300" th2="����">
                    <template:formTr name="��������">
                        <bean:write name="partInfo" property="name"/>
                    </template:formTr>
                    <template:formTr name="�����ͺ�">
                        <bean:write name="partInfo" property="type"/>
                    </template:formTr>
                    <template:formTr name="������λ">
                        <bean:write name="partInfo" property="unit"/>
                    </template:formTr>
                     <template:formTr name="��������">
                        <bean:write name="partInfo" property="factory"/>
                    </template:formTr>
                     <template:formTr name="��ע��Ϣ">
                        <bean:write name="partInfo" property="remark"/>
                    </template:formTr>
                     <template:formSubmit>
                          <td>
                          		<input type="button" class="button" value="����" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
                          </td>
                    </template:formSubmit>
            </template:formTable>
        </logic:present>
    </logic:equal>

    <!--���ҳ��-->
    <logic:equal value="2" name="type" scope="session">
        <apptag:checkpower thirdmould="80702" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
        <br />
        <template:titile value="���ά��������Ϣ"/>
        <html:form action="/PartBaseInfoAction?method=addPartBaseInfo" styleId="addForm">
                <template:formTable namewidth="150" contentwidth="300">
                    <template:formTr name="��������">
                        <html:text property="name" styleClass="inputtext" value="" style="width:180;" maxlength="25"/>

                    </template:formTr>
                     <template:formTr name="�����ͺ�">
                        <html:text property="type" styleClass="inputtext"  value="" style="width:180;" maxlength="50"/>
                    </template:formTr>
                    <template:formTr name="������λ">
                        <html:text property="unit"  value="��" styleClass="inputtext"    style="width:180;" maxlength="3"/>
                    </template:formTr>
                    <template:formTr name="��������">
                        <html:text property="factory"   styleClass="inputtext" value="" style="width:180;" maxlength="100"/>
                    </template:formTr>
                     <template:formTr name="��ע��Ϣ">
                        <textarea  name="remark" cols="8" rows="5"   title="���Ϊ256������,512��Ӣ���ַ�,�������Զ��ض�." class="textarea"></textarea>
                    </template:formTr>
                    <template:formSubmit>
                          <td>
                          <html:button property="action" styleClass="button"  onclick="addSubOnclick()">����</html:button>
                          </td>
                          <td>
                                <html:reset property="action" styleClass="button" >����	</html:reset>
                          </td>
                       
                    </template:formSubmit>


                </template:formTable>
            </html:form>
    </logic:equal>

    <!--�޸�ҳ��-->
    <logic:equal value="4" name="type" scope="session">
        <link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="screen, print"/>
          <logic:present name="partInfo">
              <apptag:checkpower thirdmould="80704" ishead="1">
                    <jsp:forward page="/globinfo/powererror.jsp"/>
            </apptag:checkpower>
            <br />
            <template:titile value="�޸�ά��������Ϣ"/>
            <html:form action="/PartBaseInfoAction?method=updatePartBaseInfo" styleId="addForm">
                    <template:formTable namewidth="150" contentwidth="350"  >
                        <input type="hidden" name="id" value="<bean:write name="partInfo" property="id"/>"/>
                          <template:formTr name="��������">
                            <input type="text" name="name" class="inputtext" style="width:180;" maxlength="25" value="<bean:write name="partInfo" property="name"/>"/>
                        </template:formTr>
                         <template:formTr name="�����ͺ�">
                             <input type="text" name="type" class="inputtext" style="width:180;" maxlength="50" value="<bean:write name="partInfo" property="type"/>"/>
                        </template:formTr>
                        <template:formTr name="������λ">
                            <input type="text" name="unit" class="inputtext" style="width:180;" maxlength="6" value="<bean:write name="partInfo" property="unit"/>"/>
                        </template:formTr>
                         <template:formTr name="��������">
                            <input type="text" name="factory" class="inputtext" style="width:180;" maxlength="100" value="<bean:write name="partInfo" property="factory"/>"/>
                        </template:formTr>
                        <template:formTr name="��ע��Ϣ">
                            <textarea  name="remark" cols="8" rows="5"   title="���Ϊ256������,512��Ӣ���ַ�,�������Զ��ض�." class="textarea"><bean:write name="partInfo" property="remark"/></textarea>
                        </template:formTr>
                        <template:formSubmit>
                              <td>
                              <html:button property="action" styleClass="button"  onclick="addSubOnclick()">�޸�</html:button>
                              </td>
                              <td>
                                    <html:reset property="action" styleClass="button" >����	</html:reset>
                              </td>
                            <td>
                                    <input type="button" value="����" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" />
                              </td>
                        </template:formSubmit>
                    </template:formTable>
                </html:form>
            </logic:present>
    </logic:equal>

    <!--��ѯҳ��-->
    <logic:equal value="3" name="type"  scope="session">
          <br />
        <template:titile value="��ȷ����ά��������Ϣ"/>
        <html:form action="/PartBaseInfoAction?method=doQuery&querytype=j"   styleId="queryForm2" >
            <template:formTable namewidth="200"  contentwidth="300">
                <template:formTr name="��������"  >
                    <select name="name"   class="inputtext" style="width:180" >
                        <option value="">���в�������</option>
                          <logic:present name="nameList">
                            <logic:iterate id="nameListId" name="nameList">
                                <option value="<bean:write name="nameListId" property="name"/>"><bean:write name="nameListId" property="name"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                </template:formTr>
                <template:formTr name="��������" >
                    <select name="type" style="width:180" class="inputtext"  >
                        <option value="">���в�������</option>
                          <logic:present name="typeList">
                            <logic:iterate id="typeListId" name="typeList">
                                <option value="<bean:write name="typeListId" property="type"/>"><bean:write name="typeListId" property="type"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                </template:formTr>
                <template:formTr name="��������" >
                    <select name="factory"  style="width:180" class="inputtext"  >
                        <option value="">���в��ϳ���</option>
                          <logic:present name="factoryList">
                            <logic:iterate id="factoryId" name="factoryList">
                                <option value="<bean:write name="factoryId" property="factory"/>"><bean:write name="factoryId" property="factory"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                </template:formTr>
                <template:formSubmit>
                              <td>
                              <html:submit property="action" styleClass="button" >����</html:submit>
                              </td>
                              <td>
                                    <html:reset property="action" styleClass="button" >����</html:reset>
                              </td>
                        </template:formSubmit>
            </template:formTable>
        </html:form>
        <br />
        <template:titile value="ģ������ά��������Ϣ"/>
        <html:form action="/PartBaseInfoAction?method=doQuery&querytype=m"   styleId="queryForm2" >
            <template:formTable contentwidth="300" namewidth="200">
                <template:formTr name="�������ƿ�ʼ�ַ�" >
                    <input type="text" name="name" class="inputtext" style="width:180;" maxlength="26" />
                </template:formTr>
                <template:formTr name="�����ͺſ�ʼ�ַ�">
                    <input type="text" name="type" class="inputtext" style="width:180;" maxlength="10" />
                </template:formTr>
                <template:formTr name="�������ҿ�ʼ�ַ�">
                    <input type="text" name="factory" class="inputtext" style="width:180;" maxlength="10" />
                </template:formTr>
                <template:formSubmit>
                              <td>
                              <html:submit property="action" styleClass="button" >����</html:submit>
                              </td>
                              <td>
                                    <html:reset property="action" styleClass="button" >����</html:reset>
                              </td>
                        </template:formSubmit>
            </template:formTable>
        </html:form>
    </logic:equal>
    <logic:equal  name="type" scope="session" value="showpart1">
        <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>          
        <br>
        <template:titile value="ά��������Ϣһ����"/>
        <display:table    name="sessionScope.partInfo" requestURI="${ctx}/PartBaseInfoAction.do?method=doQuery"  id="currentRowObject"  pagesize="18">
         	<%
                BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
                String id = "";
                String name = "";
                if(object != null) {
                	id = (String) object.get("id");
                	name = (String) object.get("name");
                }
                
            %>
            <display:column media="html" title="��������" >
            	 <a href="javascript:toGetForm('<%=id%>')"><%=name %></a>
            </display:column>
            <display:column property="name" title="��������" sortable="true" />
            <display:column property="unit" title="������λ" sortable="true"/>
            <display:column property="type" title="�����ͺ�" maxLength="8" sortable="true"/>
            <display:column property="factory" title="��������" sortable="true"/>
            <display:column property="remark" title="��ע��Ϣ"  maxLength="20"sortable="true"/>
            <display:column media="html" title="����" style="width:80px">
               <apptag:checkpower thirdmould="80704" ishead="0">  
                     <a href="javascript:toUpForm('<%=id%>')">�޸�</a> |
            	</apptag:checkpower>            	
            	<apptag:checkpower thirdmould="80705" ishead="0">
                     <a href="javascript:toDeletForm('<%=id%>')">ɾ��</a>                 
            	</apptag:checkpower>
            </display:column>
        </display:table>
    <!--htmllink action="/PartBaseInfoAction.do?method=exportBaseInfo">����ΪExcel�ļ�</htmllink-->
    </logic:equal>


</body>
</html>
<script type="" language="javascript">

        function selecOnchang(){
              var arrAllOption = new Array();
              var arrValueText = new Array();
              //���seleForm1.select.options �е�����ѡ��ֵ,������ֵ����arrAllArray����
              for(i=0;i<seleForm1.select.options.length;i++){
                  arrValueText[0]=seleForm1.select.options[i].value;
                arrValueText[1]=seleForm1.select.options[i].text;
                  arrAllOption[arrAllOption.length] = arrValueText;
                arrValueText = new Array();
            }
             //
             seleForm1.newselect.options.length = arrAllOption.length;
            for(i=0;i<arrAllOption.length;i++){

                  seleForm1.newselect.options[i].value=arrAllOption[i][0];
                seleForm1.newselect.options[i].text=arrAllOption[i][1];

            }

        }

        function addSubOnclick(){
            if((addForm.name.value == "") || (addForm.name.value ==null) ){
                alert("�������Ʋ���Ϊ��!");
                return false;
            }
            if((addForm.type.value == "") || (addForm.type.value ==null) ){
                alert("�����ͺŲ���Ϊ��!");
                return false;
            }
            addForm.submit();
        }

        function addGoBack()
        {
            try{
                location.href = "${ctx}/PartBaseInfoAction.do?method=showPartBaseInfo";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
        //Ϊ�Ժ���������,������Ϣת��Action
        function toGetForm(idValue){
            var url = "${ctx}/PartBaseInfoAction.do?method=showOnePartBaseInfo&id=" + idValue;
            self.location.replace(url);
        }

        function toUpForm(idValue){
                var url = "${ctx}/PartBaseInfoAction.do?method=upPartBaseInfo&id=" + idValue;
            self.location.replace(url);
        }
        function toDeletForm(idValue){
                if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
                var url = "${ctx}/PartBaseInfoAction.do?method=deletePartBaseInfo&id=" + idValue;
                self.location.replace(url);
            }
            else
                return ;
        }

    </script>

