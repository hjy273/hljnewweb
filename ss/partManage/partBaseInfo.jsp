<%@include file="/common/header.jsp"%>


<%@ page import="com.cabletech.partmanage.beans.*;" %>


<html>
<head>

<script type="text/javascript">
	//返回到上级查询页面
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

    <!--显示页面-->
    <logic:equal  name="type" scope="session" value="1">
        <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
          <apptag:checkpower thirdmould="80701" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
        <br>
        <template:titile value="维护材料信息一览表"/>
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
        	 <display:column media="html" title="材料名称" >
        	 	 <a href="javascript:toGetForm('<%=id%>')"><%=name%></a>
        	 </display:column>
            <display:column property="name" title="材料名称" sortable="true" />
            <display:column property="unit" title="计量单位" sortable="true"/>
            <display:column property="type" title="材料型号" maxLength="8" sortable="true"/>
            <display:column property="factory" title="生产厂家" sortable="true"/>
            <display:column property="remark" title="备注信息"  maxLength="20"sortable="true"/>
            <display:column media="html" title="操作" headerClass="subject" class="subject" style="width:80px">
                 <apptag:checkpower thirdmould="80704" ishead="0">      
                    <a href="javascript:toUpForm('<%=id%>')">修改</a> | 
            	</apptag:checkpower>
            	
            	<apptag:checkpower thirdmould="80705" ishead="0">
                	<a href="javascript:toDeletForm('<%=id%>')">删除</a>                 
            	</apptag:checkpower>
            </display:column>
        </display:table><br>
         <div align="center"><html:button property="action" styleClass="button" onclick="goquery()" >返回</html:button></div>
    <!--htmllink action="/PartBaseInfoAction.do?method=exportBaseInfo">导出为Excel文件</htmllink-->
    </logic:equal>

    <!--显示详细信息页面-->
    <logic:equal value="10" name="type" scope="session">
        <logic:present name="partInfo">
            <apptag:checkpower thirdmould="80701" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
            </apptag:checkpower>
           <br />
            <template:titile value="维护材料详细信息"/>
            <template:formTable namewidth="150" contentwidth="300" th2="内容">
                    <template:formTr name="材料名称">
                        <bean:write name="partInfo" property="name"/>
                    </template:formTr>
                    <template:formTr name="材料型号">
                        <bean:write name="partInfo" property="type"/>
                    </template:formTr>
                    <template:formTr name="计量单位">
                        <bean:write name="partInfo" property="unit"/>
                    </template:formTr>
                     <template:formTr name="生产厂家">
                        <bean:write name="partInfo" property="factory"/>
                    </template:formTr>
                     <template:formTr name="备注信息">
                        <bean:write name="partInfo" property="remark"/>
                    </template:formTr>
                     <template:formSubmit>
                          <td>
                          		<input type="button" class="button" value="返回" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
                          </td>
                    </template:formSubmit>
            </template:formTable>
        </logic:present>
    </logic:equal>

    <!--添加页面-->
    <logic:equal value="2" name="type" scope="session">
        <apptag:checkpower thirdmould="80702" ishead="1">
                <jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
        <br />
        <template:titile value="添加维护材料信息"/>
        <html:form action="/PartBaseInfoAction?method=addPartBaseInfo" styleId="addForm">
                <template:formTable namewidth="150" contentwidth="300">
                    <template:formTr name="材料名称">
                        <html:text property="name" styleClass="inputtext" value="" style="width:180;" maxlength="25"/>

                    </template:formTr>
                     <template:formTr name="材料型号">
                        <html:text property="type" styleClass="inputtext"  value="" style="width:180;" maxlength="50"/>
                    </template:formTr>
                    <template:formTr name="计量单位">
                        <html:text property="unit"  value="个" styleClass="inputtext"    style="width:180;" maxlength="3"/>
                    </template:formTr>
                    <template:formTr name="生产厂家">
                        <html:text property="factory"   styleClass="inputtext" value="" style="width:180;" maxlength="100"/>
                    </template:formTr>
                     <template:formTr name="备注信息">
                        <textarea  name="remark" cols="8" rows="5"   title="最多为256个汉字,512个英文字符,超过后将自动截断." class="textarea"></textarea>
                    </template:formTr>
                    <template:formSubmit>
                          <td>
                          <html:button property="action" styleClass="button"  onclick="addSubOnclick()">增加</html:button>
                          </td>
                          <td>
                                <html:reset property="action" styleClass="button" >重置	</html:reset>
                          </td>
                       
                    </template:formSubmit>


                </template:formTable>
            </html:form>
    </logic:equal>

    <!--修改页面-->
    <logic:equal value="4" name="type" scope="session">
        <link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="screen, print"/>
          <logic:present name="partInfo">
              <apptag:checkpower thirdmould="80704" ishead="1">
                    <jsp:forward page="/globinfo/powererror.jsp"/>
            </apptag:checkpower>
            <br />
            <template:titile value="修改维护材料信息"/>
            <html:form action="/PartBaseInfoAction?method=updatePartBaseInfo" styleId="addForm">
                    <template:formTable namewidth="150" contentwidth="350"  >
                        <input type="hidden" name="id" value="<bean:write name="partInfo" property="id"/>"/>
                          <template:formTr name="材料名称">
                            <input type="text" name="name" class="inputtext" style="width:180;" maxlength="25" value="<bean:write name="partInfo" property="name"/>"/>
                        </template:formTr>
                         <template:formTr name="材料型号">
                             <input type="text" name="type" class="inputtext" style="width:180;" maxlength="50" value="<bean:write name="partInfo" property="type"/>"/>
                        </template:formTr>
                        <template:formTr name="计量单位">
                            <input type="text" name="unit" class="inputtext" style="width:180;" maxlength="6" value="<bean:write name="partInfo" property="unit"/>"/>
                        </template:formTr>
                         <template:formTr name="生产厂家">
                            <input type="text" name="factory" class="inputtext" style="width:180;" maxlength="100" value="<bean:write name="partInfo" property="factory"/>"/>
                        </template:formTr>
                        <template:formTr name="备注信息">
                            <textarea  name="remark" cols="8" rows="5"   title="最多为256个汉字,512个英文字符,超过后将自动截断." class="textarea"><bean:write name="partInfo" property="remark"/></textarea>
                        </template:formTr>
                        <template:formSubmit>
                              <td>
                              <html:button property="action" styleClass="button"  onclick="addSubOnclick()">修改</html:button>
                              </td>
                              <td>
                                    <html:reset property="action" styleClass="button" >重置	</html:reset>
                              </td>
                            <td>
                                    <input type="button" value="返回" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" />
                              </td>
                        </template:formSubmit>
                    </template:formTable>
                </html:form>
            </logic:present>
    </logic:equal>

    <!--查询页面-->
    <logic:equal value="3" name="type"  scope="session">
          <br />
        <template:titile value="精确查找维护材料信息"/>
        <html:form action="/PartBaseInfoAction?method=doQuery&querytype=j"   styleId="queryForm2" >
            <template:formTable namewidth="200"  contentwidth="300">
                <template:formTr name="材料名称"  >
                    <select name="name"   class="inputtext" style="width:180" >
                        <option value="">所有材料名称</option>
                          <logic:present name="nameList">
                            <logic:iterate id="nameListId" name="nameList">
                                <option value="<bean:write name="nameListId" property="name"/>"><bean:write name="nameListId" property="name"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                </template:formTr>
                <template:formTr name="材料类型" >
                    <select name="type" style="width:180" class="inputtext"  >
                        <option value="">所有材料类型</option>
                          <logic:present name="typeList">
                            <logic:iterate id="typeListId" name="typeList">
                                <option value="<bean:write name="typeListId" property="type"/>"><bean:write name="typeListId" property="type"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                </template:formTr>
                <template:formTr name="生产厂家" >
                    <select name="factory"  style="width:180" class="inputtext"  >
                        <option value="">所有材料厂家</option>
                          <logic:present name="factoryList">
                            <logic:iterate id="factoryId" name="factoryList">
                                <option value="<bean:write name="factoryId" property="factory"/>"><bean:write name="factoryId" property="factory"/></option>
                            </logic:iterate>
                        </logic:present>
                    </select>
                </template:formTr>
                <template:formSubmit>
                              <td>
                              <html:submit property="action" styleClass="button" >查找</html:submit>
                              </td>
                              <td>
                                    <html:reset property="action" styleClass="button" >重置</html:reset>
                              </td>
                        </template:formSubmit>
            </template:formTable>
        </html:form>
        <br />
        <template:titile value="模糊查找维护材料信息"/>
        <html:form action="/PartBaseInfoAction?method=doQuery&querytype=m"   styleId="queryForm2" >
            <template:formTable contentwidth="300" namewidth="200">
                <template:formTr name="材料名称开始字符" >
                    <input type="text" name="name" class="inputtext" style="width:180;" maxlength="26" />
                </template:formTr>
                <template:formTr name="材料型号开始字符">
                    <input type="text" name="type" class="inputtext" style="width:180;" maxlength="10" />
                </template:formTr>
                <template:formTr name="生产厂家开始字符">
                    <input type="text" name="factory" class="inputtext" style="width:180;" maxlength="10" />
                </template:formTr>
                <template:formSubmit>
                              <td>
                              <html:submit property="action" styleClass="button" >查找</html:submit>
                              </td>
                              <td>
                                    <html:reset property="action" styleClass="button" >重置</html:reset>
                              </td>
                        </template:formSubmit>
            </template:formTable>
        </html:form>
    </logic:equal>
    <logic:equal  name="type" scope="session" value="showpart1">
        <link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>          
        <br>
        <template:titile value="维护材料信息一览表"/>
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
            <display:column media="html" title="材料名称" >
            	 <a href="javascript:toGetForm('<%=id%>')"><%=name %></a>
            </display:column>
            <display:column property="name" title="材料名称" sortable="true" />
            <display:column property="unit" title="计量单位" sortable="true"/>
            <display:column property="type" title="材料型号" maxLength="8" sortable="true"/>
            <display:column property="factory" title="生产厂家" sortable="true"/>
            <display:column property="remark" title="备注信息"  maxLength="20"sortable="true"/>
            <display:column media="html" title="操作" style="width:80px">
               <apptag:checkpower thirdmould="80704" ishead="0">  
                     <a href="javascript:toUpForm('<%=id%>')">修改</a> |
            	</apptag:checkpower>            	
            	<apptag:checkpower thirdmould="80705" ishead="0">
                     <a href="javascript:toDeletForm('<%=id%>')">删除</a>                 
            	</apptag:checkpower>
            </display:column>
        </display:table>
    <!--htmllink action="/PartBaseInfoAction.do?method=exportBaseInfo">导出为Excel文件</htmllink-->
    </logic:equal>


</body>
</html>
<script type="" language="javascript">

        function selecOnchang(){
              var arrAllOption = new Array();
              var arrValueText = new Array();
              //获得seleForm1.select.options 中的所有选项值,并将该值赋给arrAllArray数组
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
                alert("材料名称不能为空!");
                return false;
            }
            if((addForm.type.value == "") || (addForm.type.value ==null) ){
                alert("材料型号不能为空!");
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
        //为以后升级方便,单个信息转向Action
        function toGetForm(idValue){
            var url = "${ctx}/PartBaseInfoAction.do?method=showOnePartBaseInfo&id=" + idValue;
            self.location.replace(url);
        }

        function toUpForm(idValue){
                var url = "${ctx}/PartBaseInfoAction.do?method=upPartBaseInfo&id=" + idValue;
            self.location.replace(url);
        }
        function toDeletForm(idValue){
                if(confirm("你确定要执行此次删除操作吗?")){
                var url = "${ctx}/PartBaseInfoAction.do?method=deletePartBaseInfo&id=" + idValue;
                self.location.replace(url);
            }
            else
                return ;
        }

    </script>

