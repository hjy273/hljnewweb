<%@include file="/common/header.jsp"%>

<%@ page import="com.cabletech.partmanage.beans.*;" %>


<html>
<head>
<script type="" language="javascript">
        function toDeletForm(idValue){
       	 	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
            	var url = "${ctx}/ToolBaseAction.do?method=deleteToolBaseInfo&id=" + idValue;
	        	self.location.replace(url);
            }
            else
            	return ;
		}
		 function toUpForm(idValue){
		       	 	var url = "${ctx}/ToolBaseAction.do?method=upBaseInfoShow&id=" + idValue;
		        	self.location.replace(url);
				}

        //Ϊ�Ժ���������,������Ϣת��Action
        function toGetForm(idValue){
        	var url = "${ctx}/ToolBaseAction.do?method=showOneToolBaseInfo&id=" + idValue;
        	self.location.replace(url);
		}
 function valCharLength(Value){
      var j=0;
      var s = Value;
      for   (var   i=0;   i<s.length;   i++)
      {
        if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
        else   j++
      }
      return j;
    }

		function addSubOnclick(){
        	if((addtoosbaseFormId.name.value == "") || (addtoosbaseFormId.name.value ==null) ){
            	alert("�������Ʋ���Ϊ��!");
                return false;
            }
            if(valCharLength(addtoosbaseFormId.tooluse.value)>512){
              alert("������;��Ϣ���ܳ���250�����ֻ���512��Ӣ���ַ���")
              return false;
            }
            if(valCharLength(addtoosbaseFormId.remark.value)>512){
              alert("��ע��Ϣ���ܳ���250�����ֻ���512��Ӣ���ַ���")
              return false;
            }
            addtoosbaseFormId.submit();
        }

    	function addGoBack()
        {
        	try{
            	location.href = "${ctx}/ToolBaseAction.do?method=showToolsBaseInfo";
                return true;
            }
            catch(e){
            	alert(e);
            }
        }

     function isassetSelect(){
     	var  form = document.getElementById("addtoosbaseFormId");
        var  assetsn = document.getElementById("assetsnid");
        if(form.isasset[0].checked){
          assetsn.style.display="block";
        }
        if(form.isasset[1].checked){
        	assetsn.style.display="none";
        }
     }


	</script>

<title>
partBaseInfo
</title>
</head>
<body >

	<!--���ҳ��-->
	<logic:equal value="2" name="type"scope="session" >
    	<apptag:checkpower thirdmould="90802" ishead="1">
				<jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
		<br />
        <template:titile value="��ӱ���������Ϣ"/>
        <html:form action="/ToolBaseAction?method=addToolBaseInfo" styleId="addtoosbaseFormId">
				<template:formTable namewidth="150" contentwidth="300">
			    	<template:formTr name="��������">
			        	<html:text property="name" styleClass="inputtext" style="width:180;" maxlength="10"/>
			        </template:formTr>
			    	<template:formTr name="������λ">
			        	<html:text property="unit"  value="��" styleClass="inputtext" style="width:180;" maxlength="6"/>
			        </template:formTr>
                    <template:formTr name="�����ͺ�">
			        	<html:text property="style" styleClass="inputtext" style="width:180;" maxlength="25"/>
			        </template:formTr>
			         <template:formTr name="��������">
			        	<html:text property="type" styleClass="inputtext" style="width:180;" maxlength="25"/>
			        </template:formTr>
                     <template:formTr name="������Դ">
			        	<html:text property="source" styleClass="inputtext" style="width:180;" maxlength="25"/>
			        </template:formTr>
                    <template:formTr name="��������">
			        	<html:text property="factory"   styleClass="inputtext" style="width:180;" maxlength="25"/>
			        </template:formTr>
                    <template:formTr name="��Ʒ���к�">
			        	<html:text property="factorysn"   styleClass="inputtext" style="width:180;" maxlength="20"/>
			        </template:formTr>
                    <template:formTr name="�Ƿ�̶��ʲ�">
			        	<input type="radio" name="isasset"  value="��"  checked="checked" onclick="isassetSelect()"/>��&nbsp;&nbsp;&nbsp;
                        <input type="radio" name="isasset"  value="��"  onclick="isassetSelect()"/>��&nbsp;
			        </template:formTr>
                    <template:formTr name="�ʲ����" tagID="assetsnid">
                    	<html:text property="assetsn"   styleClass="inputtext" style="width:180;" maxlength="20"/>
                    </template:formTr>
                    <template:formTr name="������;">
			        	<textarea  name="tooluse" cols="12" rows="2"   title="���Ϊ256������,512��Ӣ���ַ�,�������Զ��ض�." class="textarea"></textarea>
			        </template:formTr>
			         <template:formTr name="��ע��Ϣ">
			        	<textarea  name="remark" cols="12" rows="3"   title="���Ϊ256������,512��Ӣ���ַ�,�������Զ��ض�." class="textarea"></textarea>
			        </template:formTr>
				    <template:formSubmit>
				      	<td>
                          <html:button property="action" styleClass="button"  onclick="addSubOnclick()">����</html:button>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >ȡ��	</html:reset>
				      	</td>
                        <td>
				       	 	<html:button property="action" styleClass="button" onclick="addGoBack()" >����	</html:button>
				      	</td>
				    </template:formSubmit>


				</template:formTable>
			</html:form>
	</logic:equal>

    	<!--��ʾҳ��-->
    <logic:equal value="1" name="type"scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<br />
        <template:titile value="������Ϣһ����"/>

        <display:table    name="sessionScope.toolInfo"   id="currentRowObject"  pagesize="18">

          	<display:column property="name" title="��������"  maxLength="10"/>
        	<display:column property="unit" title="������λ" maxLength="10" />
            <display:column property="type" title="��������" maxLength="10" />
            <display:column property="source" title="������Դ"  maxLength="10"/>
             <display:column property="factory" title="��������"  maxLength="10"/>

            <display:column media="html" title="����" >
            	 <%
				    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				    String id = (String) object.get("id");
				  %>
            	 <a href="javascript:toGetForm('<%=id%>')">��ϸ</a>
		  	</display:column>

            <apptag:checkpower thirdmould="90804" ishead="0">
            	<display:column media="html" title="����" >
                	<%
				    	BasicDynaBean  object1 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				    	String id1 = (String) object1.get("id");
				  	%>
            	 	<a href="javascript:toUpForm('<%=id1%>')">�޸�</a>
                 </display:column>
            </apptag:checkpower>

            <apptag:checkpower thirdmould="90805" ishead="0">
            	<display:column media="html" title="����" >
                	<%
				    	BasicDynaBean  object2 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				    	String id2 = (String) object2.get("id");
				  	%>
            	 	<a href="javascript:toDeletForm('<%=id2%>')">ɾ��</a>
                 </display:column>
            </apptag:checkpower>
		</display:table>
        <!--htmllink action="/ToolBaseAction?method=exportBaseResult">����ΪExcel�ļ�</htmllink-->
	</logic:equal>


    <!--��ʾ��ϸ��Ϣҳ��-->
    <logic:equal value="10" name="type"scope="session" >
    	<logic:present name="toolInfo">
           <br />
            <template:titile value="������ϸ��Ϣ"/><br />
            <template:formTable namewidth="150" contentwidth="300" th2="����">
			    	<template:formTr name="��������">
			        	<bean:write name="toolInfo" property="name"/>
			        </template:formTr>
                    <template:formTr name="�����ͺ�">
			        	<bean:write name="toolInfo" property="type"/>
			        </template:formTr>
			        <template:formTr name="������λ">
			        	<bean:write name="toolInfo" property="unit"/>
			        </template:formTr>
                    <template:formTr name="������Դ">
			        	<bean:write name="toolInfo" property="source"/>
			        </template:formTr>
                     <template:formTr name="��������">
			        	<bean:write name="toolInfo" property="factory"/>
			        </template:formTr>
                     <template:formTr name="�������">
			        	<bean:write name="toolInfo" property="factorysn"/>
			        </template:formTr>
                     <template:formTr name="�ʲ����">
			        	<bean:write name="toolInfo" property="assetsn"/>
			        </template:formTr>
                    <template:formTr name="�豸��;">
			        	<bean:write name="toolInfo" property="tooluse"/>
			        </template:formTr>
			         <template:formTr name="��ע��Ϣ">
			        	<bean:write name="toolInfo" property="remark"/>
			        </template:formTr>
                     <template:formSubmit>
				      	<td>
				       	 	<html:button property="action" styleClass="button" onclick="addGoBack()" >����	</html:button>
				      	</td>
				    </template:formSubmit>
            </template:formTable>
    	</logic:present>
    </logic:equal>


   <!--�޸�ҳ��-->
	<logic:equal value="4" name="type"scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="screen, print"/>
      	<logic:present name="toolInfo">
			<br />
            <template:titile value="�޸ı�����Ϣ"/>
	        <html:form action="/ToolBaseAction?method=upToolBaseInfo" styleId="addtoosbaseFormId">
					<template:formTable namewidth="150" contentwidth="350"  >
				    	<input type="hidden" name="id" value="<bean:write name="toolInfo" property="id"/>"/>
                      	<template:formTr name="��������">
			        	<input  type="text" name="name" value="<bean:write name="toolInfo" property="name"/>" Class="inputtext" style="width:180;" maxlength="10"/>
				        </template:formTr>
				    	<template:formTr name="������λ">
				        	<input type="text" name="unit" value="<bean:write name="toolInfo" property="unit"/>"  Class="inputtext" style="width:180;" maxlength="6"/>
				        </template:formTr>
	                    <template:formTr name="�����ͺ�">
				        	<input type="text" name="style" value="<bean:write name="toolInfo" property="style"/>" Class="inputtext" style="width:180;" maxlength="25"/>
				        </template:formTr>
				         <template:formTr name="��������">
				        	<input  type="text" name="type" value="<bean:write name="toolInfo" property="type"/>" Class="inputtext" style="width:180;" maxlength="25"/>
				        </template:formTr>
	                     <template:formTr name="������Դ">
				        	<input type="text" name="source" value="<bean:write name="toolInfo" property="source"/>" Class="inputtext" style="width:180;" maxlength="25"/>
				        </template:formTr>
	                    <template:formTr name="��������">
				        	<input type="text" name="factory" value="<bean:write name="toolInfo" property="factory"/>"   Class="inputtext" style="width:180;" maxlength="25"/>
				        </template:formTr>
	                    <template:formTr name="��Ʒ���к�">
				        	<input type="text" name="factorysn"  value="<bean:write name="toolInfo" property="factorysn"/>"   Class="inputtext" style="width:180;" maxlength="20"/>
				        </template:formTr>
	                    <template:formTr name="�Ƿ�̶��ʲ�">
				        	<logic:equal value="��" name="toolInfo" property="isasset">
                            	<input type="radio" name="isasset"  value="��"  checked="checked" onclick="isassetSelect()"/>��&nbsp;&nbsp;&nbsp;
				        		<input type="radio" name="isasset"  value="��"  onclick="isassetSelect()"/>��&nbsp;
                            </logic:equal>
                            <logic:notEqual value="��" name="toolInfo" property="isasset">
	                          	<input type="radio" name="isasset"  value="��"  onclick="isassetSelect()"/>��&nbsp;&nbsp;&nbsp;
	                            <input type="radio" name="isasset"  value="��"  checked="checked" onclick="isassetSelect()"/>��&nbsp;
                            </logic:notEqual>
				        </template:formTr>

	                        <template:formTr name="�ʲ����" tagID="assetsnid">
		                    	<input type="text" name="assetsn" value="<bean:write name="toolInfo" property="assetsn"/>"  Class="inputtext" style="width:180;" maxlength="20"/>
		                    </template:formTr>

	                    <template:formTr name="������;">
				        	<textarea  name="tooluse" cols="12" rows="2"   title="���Ϊ256������,512��Ӣ���ַ�,�������Զ��ض�." class="textarea"><bean:write name="toolInfo" property="tooluse"/></textarea>
				        </template:formTr>
				         <template:formTr name="��ע��Ϣ">
				        	<textarea  name="remark" cols="12" rows="3"   title="���Ϊ256������,512��Ӣ���ַ�,�������Զ��ض�." class="textarea"><bean:write name="toolInfo" property="remark"/></textarea>
				        </template:formTr>
					    <template:formSubmit>
					      	<td>
	                          <html:button property="action" styleClass="button"  onclick="addSubOnclick()">�޸�</html:button>
					      	</td>
					      	<td>
					       	 	<html:reset property="action" styleClass="button" >ȡ��	</html:reset>
					      	</td>
	                        <td>
					       	 	<html:button property="action" styleClass="button" onclick="addGoBack()" >����	</html:button>
					      	</td>
					    </template:formSubmit>
					</template:formTable>
				</html:form>
            </logic:present>
	</logic:equal>
    <!--��ѯҳ��-->
    <logic:equal value="3" name="type" scope="session" >

      	<br />
        <template:titile value="�������ұ�����Ϣ"/>
        <html:form action="/ToolBaseAction?method=doQuery"   styleId="queryForm2" >
        	<template:formTable namewidth="200"  contentwidth="200">
            	<template:formTr name="��������:"  >
            		<select name="name"   class="inputtext" style="width:180px" >
			        	<option value="">���б�������</option>
                      	<logic:present name="nameList">
		                	<logic:iterate id="nameListId" name="nameList">
		                    	<option value="<bean:write name="nameListId" property="name"/>"><bean:write name="nameListId" property="name"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <template:formTr name="��������:" >
            		<select name="style" style="width:180px" class="inputtext"  >
			        	<option value="">���б�������</option>
                      	<logic:present name="styleList">
		                	<logic:iterate id="styleListId" name="styleList">
		                    	<option value="<bean:write name="styleListId" property="style"/>"><bean:write name="styleListId" property="style"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <template:formTr name="�����ͺ�:" >
            		<select name="type" style="width:180px" class="inputtext"  >
			        	<option value="">���б����ͺ�</option>
                      	<logic:present name="typeList">
		                	<logic:iterate id="typeListId" name="typeList">
		                    	<option value="<bean:write name="typeListId" property="type"/>"><bean:write name="typeListId" property="type"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                 <template:formTr name="������Դ:" >
            		<select name="source" style="width:180px" class="inputtext"  >
			        	<option value="">���б�����Դ</option>
                      	<logic:present name="sourceList">
		                	<logic:iterate id="sourceListId" name="sourceList">
		                    	<option value="<bean:write name="sourceListId" property="source"/>"><bean:write name="sourceListId" property="source"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <template:formTr name="��������:" >
            		<select name="factory"  style="width:180px" class="inputtext"  >
			        	<option value="">���г���</option>
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
					       	 	<html:reset property="action" styleClass="button" >ȡ��</html:reset>
					      	</td>
	                        <td>
					       	 	<html:button property="action" styleClass="button" onclick="addGoBack()" >����</html:button>
					      	</td>
					    </template:formSubmit>
        	</template:formTable>
    	</html:form>
    </logic:equal>
</body>
</html>


