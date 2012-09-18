<%@include file="/common/header.jsp"%>

<%@ page import="com.cabletech.partmanage.beans.*;" %>


<html>
<head>
<script type="" language="javascript">
        function toDeletForm(idValue){
       	 	if(confirm("你确定要执行此次删除操作吗?")){
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

        //为以后升级方便,单个信息转向Action
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
            	alert("备件名称不能为空!");
                return false;
            }
            if(valCharLength(addtoosbaseFormId.tooluse.value)>512){
              alert("备件用途信息不能超过250个汉字或者512个英文字符！")
              return false;
            }
            if(valCharLength(addtoosbaseFormId.remark.value)>512){
              alert("备注信息不能超过250个汉字或者512个英文字符！")
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

	<!--添加页面-->
	<logic:equal value="2" name="type"scope="session" >
    	<apptag:checkpower thirdmould="90802" ishead="1">
				<jsp:forward page="/globinfo/powererror.jsp"/>
        </apptag:checkpower>
		<br />
        <template:titile value="添加备件基本信息"/>
        <html:form action="/ToolBaseAction?method=addToolBaseInfo" styleId="addtoosbaseFormId">
				<template:formTable namewidth="150" contentwidth="300">
			    	<template:formTr name="备件名称">
			        	<html:text property="name" styleClass="inputtext" style="width:180;" maxlength="10"/>
			        </template:formTr>
			    	<template:formTr name="计量单位">
			        	<html:text property="unit"  value="个" styleClass="inputtext" style="width:180;" maxlength="6"/>
			        </template:formTr>
                    <template:formTr name="材料型号">
			        	<html:text property="style" styleClass="inputtext" style="width:180;" maxlength="25"/>
			        </template:formTr>
			         <template:formTr name="备件类型">
			        	<html:text property="type" styleClass="inputtext" style="width:180;" maxlength="25"/>
			        </template:formTr>
                     <template:formTr name="备件来源">
			        	<html:text property="source" styleClass="inputtext" style="width:180;" maxlength="25"/>
			        </template:formTr>
                    <template:formTr name="生产厂家">
			        	<html:text property="factory"   styleClass="inputtext" style="width:180;" maxlength="25"/>
			        </template:formTr>
                    <template:formTr name="产品序列号">
			        	<html:text property="factorysn"   styleClass="inputtext" style="width:180;" maxlength="20"/>
			        </template:formTr>
                    <template:formTr name="是否固定资产">
			        	<input type="radio" name="isasset"  value="是"  checked="checked" onclick="isassetSelect()"/>是&nbsp;&nbsp;&nbsp;
                        <input type="radio" name="isasset"  value="否"  onclick="isassetSelect()"/>否&nbsp;
			        </template:formTr>
                    <template:formTr name="资产编号" tagID="assetsnid">
                    	<html:text property="assetsn"   styleClass="inputtext" style="width:180;" maxlength="20"/>
                    </template:formTr>
                    <template:formTr name="备件用途">
			        	<textarea  name="tooluse" cols="12" rows="2"   title="最多为256个汉字,512个英文字符,超过后将自动截断." class="textarea"></textarea>
			        </template:formTr>
			         <template:formTr name="备注信息">
			        	<textarea  name="remark" cols="12" rows="3"   title="最多为256个汉字,512个英文字符,超过后将自动截断." class="textarea"></textarea>
			        </template:formTr>
				    <template:formSubmit>
				      	<td>
                          <html:button property="action" styleClass="button"  onclick="addSubOnclick()">增加</html:button>
				      	</td>
				      	<td>
				       	 	<html:reset property="action" styleClass="button" >取消	</html:reset>
				      	</td>
                        <td>
				       	 	<html:button property="action" styleClass="button" onclick="addGoBack()" >返回	</html:button>
				      	</td>
				    </template:formSubmit>


				</template:formTable>
			</html:form>
	</logic:equal>

    	<!--显示页面-->
    <logic:equal value="1" name="type"scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
      	<br />
        <template:titile value="备件信息一览表"/>

        <display:table    name="sessionScope.toolInfo"   id="currentRowObject"  pagesize="18">

          	<display:column property="name" title="备件名称"  maxLength="10"/>
        	<display:column property="unit" title="计量单位" maxLength="10" />
            <display:column property="type" title="备件类型" maxLength="10" />
            <display:column property="source" title="备件来源"  maxLength="10"/>
             <display:column property="factory" title="生产厂家"  maxLength="10"/>

            <display:column media="html" title="操作" >
            	 <%
				    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				    String id = (String) object.get("id");
				  %>
            	 <a href="javascript:toGetForm('<%=id%>')">详细</a>
		  	</display:column>

            <apptag:checkpower thirdmould="90804" ishead="0">
            	<display:column media="html" title="操作" >
                	<%
				    	BasicDynaBean  object1 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				    	String id1 = (String) object1.get("id");
				  	%>
            	 	<a href="javascript:toUpForm('<%=id1%>')">修改</a>
                 </display:column>
            </apptag:checkpower>

            <apptag:checkpower thirdmould="90805" ishead="0">
            	<display:column media="html" title="操作" >
                	<%
				    	BasicDynaBean  object2 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				    	String id2 = (String) object2.get("id");
				  	%>
            	 	<a href="javascript:toDeletForm('<%=id2%>')">删除</a>
                 </display:column>
            </apptag:checkpower>
		</display:table>
        <!--htmllink action="/ToolBaseAction?method=exportBaseResult">导出为Excel文件</htmllink-->
	</logic:equal>


    <!--显示详细信息页面-->
    <logic:equal value="10" name="type"scope="session" >
    	<logic:present name="toolInfo">
           <br />
            <template:titile value="备件详细信息"/><br />
            <template:formTable namewidth="150" contentwidth="300" th2="内容">
			    	<template:formTr name="备件名称">
			        	<bean:write name="toolInfo" property="name"/>
			        </template:formTr>
                    <template:formTr name="备件型号">
			        	<bean:write name="toolInfo" property="type"/>
			        </template:formTr>
			        <template:formTr name="计量单位">
			        	<bean:write name="toolInfo" property="unit"/>
			        </template:formTr>
                    <template:formTr name="备件来源">
			        	<bean:write name="toolInfo" property="source"/>
			        </template:formTr>
                     <template:formTr name="生产厂家">
			        	<bean:write name="toolInfo" property="factory"/>
			        </template:formTr>
                     <template:formTr name="生产序号">
			        	<bean:write name="toolInfo" property="factorysn"/>
			        </template:formTr>
                     <template:formTr name="资产编号">
			        	<bean:write name="toolInfo" property="assetsn"/>
			        </template:formTr>
                    <template:formTr name="设备用途">
			        	<bean:write name="toolInfo" property="tooluse"/>
			        </template:formTr>
			         <template:formTr name="备注信息">
			        	<bean:write name="toolInfo" property="remark"/>
			        </template:formTr>
                     <template:formSubmit>
				      	<td>
				       	 	<html:button property="action" styleClass="button" onclick="addGoBack()" >返回	</html:button>
				      	</td>
				    </template:formSubmit>
            </template:formTable>
    	</logic:present>
    </logic:equal>


   <!--修改页面-->
	<logic:equal value="4" name="type"scope="session" >
    	<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="screen, print"/>
      	<logic:present name="toolInfo">
			<br />
            <template:titile value="修改备件信息"/>
	        <html:form action="/ToolBaseAction?method=upToolBaseInfo" styleId="addtoosbaseFormId">
					<template:formTable namewidth="150" contentwidth="350"  >
				    	<input type="hidden" name="id" value="<bean:write name="toolInfo" property="id"/>"/>
                      	<template:formTr name="备件名称">
			        	<input  type="text" name="name" value="<bean:write name="toolInfo" property="name"/>" Class="inputtext" style="width:180;" maxlength="10"/>
				        </template:formTr>
				    	<template:formTr name="计量单位">
				        	<input type="text" name="unit" value="<bean:write name="toolInfo" property="unit"/>"  Class="inputtext" style="width:180;" maxlength="6"/>
				        </template:formTr>
	                    <template:formTr name="材料型号">
				        	<input type="text" name="style" value="<bean:write name="toolInfo" property="style"/>" Class="inputtext" style="width:180;" maxlength="25"/>
				        </template:formTr>
				         <template:formTr name="备件类型">
				        	<input  type="text" name="type" value="<bean:write name="toolInfo" property="type"/>" Class="inputtext" style="width:180;" maxlength="25"/>
				        </template:formTr>
	                     <template:formTr name="备件来源">
				        	<input type="text" name="source" value="<bean:write name="toolInfo" property="source"/>" Class="inputtext" style="width:180;" maxlength="25"/>
				        </template:formTr>
	                    <template:formTr name="生产厂家">
				        	<input type="text" name="factory" value="<bean:write name="toolInfo" property="factory"/>"   Class="inputtext" style="width:180;" maxlength="25"/>
				        </template:formTr>
	                    <template:formTr name="产品序列号">
				        	<input type="text" name="factorysn"  value="<bean:write name="toolInfo" property="factorysn"/>"   Class="inputtext" style="width:180;" maxlength="20"/>
				        </template:formTr>
	                    <template:formTr name="是否固定资产">
				        	<logic:equal value="是" name="toolInfo" property="isasset">
                            	<input type="radio" name="isasset"  value="是"  checked="checked" onclick="isassetSelect()"/>是&nbsp;&nbsp;&nbsp;
				        		<input type="radio" name="isasset"  value="否"  onclick="isassetSelect()"/>否&nbsp;
                            </logic:equal>
                            <logic:notEqual value="是" name="toolInfo" property="isasset">
	                          	<input type="radio" name="isasset"  value="是"  onclick="isassetSelect()"/>是&nbsp;&nbsp;&nbsp;
	                            <input type="radio" name="isasset"  value="否"  checked="checked" onclick="isassetSelect()"/>否&nbsp;
                            </logic:notEqual>
				        </template:formTr>

	                        <template:formTr name="资产编号" tagID="assetsnid">
		                    	<input type="text" name="assetsn" value="<bean:write name="toolInfo" property="assetsn"/>"  Class="inputtext" style="width:180;" maxlength="20"/>
		                    </template:formTr>

	                    <template:formTr name="备件用途">
				        	<textarea  name="tooluse" cols="12" rows="2"   title="最多为256个汉字,512个英文字符,超过后将自动截断." class="textarea"><bean:write name="toolInfo" property="tooluse"/></textarea>
				        </template:formTr>
				         <template:formTr name="备注信息">
				        	<textarea  name="remark" cols="12" rows="3"   title="最多为256个汉字,512个英文字符,超过后将自动截断." class="textarea"><bean:write name="toolInfo" property="remark"/></textarea>
				        </template:formTr>
					    <template:formSubmit>
					      	<td>
	                          <html:button property="action" styleClass="button"  onclick="addSubOnclick()">修改</html:button>
					      	</td>
					      	<td>
					       	 	<html:reset property="action" styleClass="button" >取消	</html:reset>
					      	</td>
	                        <td>
					       	 	<html:button property="action" styleClass="button" onclick="addGoBack()" >返回	</html:button>
					      	</td>
					    </template:formSubmit>
					</template:formTable>
				</html:form>
            </logic:present>
	</logic:equal>
    <!--查询页面-->
    <logic:equal value="3" name="type" scope="session" >

      	<br />
        <template:titile value="条件查找备件信息"/>
        <html:form action="/ToolBaseAction?method=doQuery"   styleId="queryForm2" >
        	<template:formTable namewidth="200"  contentwidth="200">
            	<template:formTr name="备件名称:"  >
            		<select name="name"   class="inputtext" style="width:180px" >
			        	<option value="">所有备件名称</option>
                      	<logic:present name="nameList">
		                	<logic:iterate id="nameListId" name="nameList">
		                    	<option value="<bean:write name="nameListId" property="name"/>"><bean:write name="nameListId" property="name"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <template:formTr name="备件类型:" >
            		<select name="style" style="width:180px" class="inputtext"  >
			        	<option value="">所有备件类型</option>
                      	<logic:present name="styleList">
		                	<logic:iterate id="styleListId" name="styleList">
		                    	<option value="<bean:write name="styleListId" property="style"/>"><bean:write name="styleListId" property="style"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <template:formTr name="备件型号:" >
            		<select name="type" style="width:180px" class="inputtext"  >
			        	<option value="">所有备件型号</option>
                      	<logic:present name="typeList">
		                	<logic:iterate id="typeListId" name="typeList">
		                    	<option value="<bean:write name="typeListId" property="type"/>"><bean:write name="typeListId" property="type"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                 <template:formTr name="备件来源:" >
            		<select name="source" style="width:180px" class="inputtext"  >
			        	<option value="">所有备件来源</option>
                      	<logic:present name="sourceList">
		                	<logic:iterate id="sourceListId" name="sourceList">
		                    	<option value="<bean:write name="sourceListId" property="source"/>"><bean:write name="sourceListId" property="source"/></option>
		                	</logic:iterate>
			        	</logic:present>
		    		</select>
                </template:formTr>
                <template:formTr name="生产厂家:" >
            		<select name="factory"  style="width:180px" class="inputtext"  >
			        	<option value="">所有厂家</option>
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
					       	 	<html:reset property="action" styleClass="button" >取消</html:reset>
					      	</td>
	                        <td>
					       	 	<html:button property="action" styleClass="button" onclick="addGoBack()" >返回</html:button>
					      	</td>
					    </template:formSubmit>
        	</template:formTable>
    	</html:form>
    </logic:equal>
</body>
</html>


