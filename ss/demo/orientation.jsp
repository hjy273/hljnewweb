<%@include file="/common/header.jsp"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<%@ page import="com.cabletech.partmanage.beans.*;" %>
<html>
<head>
<title>
orientation
</title>
</head>
<body >
<script type="" language="javascript">
    function goto(){
      var url = "${ctx}/demo/info.jsp";
                self.location.replace(url);
    }
</script>
 <br />
            <template:titile value="嘟梤隅弇"/>
            <template:formTable namewidth="150" contentwidth="300" th2="囀��">
                     <template:formTr name="盄繚瘍">
                        <select name="point"  style="width:150" class="inputtext">
                            <option value="裝001">嘉傑-坋碩</option>
                            <option value="裝001">虛摩-繩阨</option>
                            <option value="裝001">裝003</option>
                            <option value="裝001">裝004</option>
                            <option value="裝001">裝005</option>
                            <option value="裝001">凝001</option>
                            <option value="裝001">凝002</option>
                            <option value="裝001">凝003</option>
                        </select>
                        <button class="button" style="width:40" value="">脤梑</button>
                    </template:formTr>
                    <template:formTr name="盄僇瘍">
                        <select name="point"  style="width:150" class="inputtext">
                            <option value="裝001">啋貕ㄜ挔虛僇</option>
                            <option value="裝001">虛摩-繩阨</option>
                            <option value="裝001">裝003</option>
                            <option value="裝001">裝004</option>
                            <option value="裝001">裝005</option>
                            <option value="裝001">凝001</option>
                            <option value="裝001">凝002</option>
                            <option value="裝001">凝025</option>
                        </select>
                        <button class="button" style="width:40" value="">脤梑</button>
                    </template:formTr>
                      <template:formTr name="聆講萸">
                        <select name="point"  style="width:150" class="inputtext">
                            <option value="裝001">潼諷萸1</option>
                            <option value="裝001">裝002</option>
                            <option value="裝001">裝003</option>
                            <option value="裝001">裝004</option>
                            <option value="裝001">裝025</option>
                            <option value="裝001">凝001</option>
                            <option value="裝001">凝002</option>
                            <option value="裝001">凝003</option>
                        </select>
                        <button class="button" style="width:40" value="">脤梑</button>
                    </template:formTr>

                    <template:formTr name="酗 僅">
                        <input  type="text" class="inputtext" style="width:140"/>譙
                    </template:formTr>

                     <template:formSubmit>
                          <td>
                                <html:button property="action" styleClass="button" onclick="addGoBack()" >褫弝趙隅弇	</html:button>
                          </td>
                        <td>
                                <html:button property="action" styleClass="button"  onclick="goto()" >陓洘隅弇	</html:button>
                          </td>
                    </template:formSubmit>
            </template:formTable>
</body>
</html>
