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
            <template:titile value="���϶�λ"/>
            <template:formTable namewidth="150" contentwidth="300" th2="����">
                     <template:formTr name="��·��">
                        <select name="point"  style="width:150" class="inputtext">
                            <option value="��001">�ų�-ʮ��</option>
                            <option value="��001">�꼯-÷ˮ</option>
                            <option value="��001">��003</option>
                            <option value="��001">��004</option>
                            <option value="��001">��005</option>
                            <option value="��001">��001</option>
                            <option value="��001">��002</option>
                            <option value="��001">��003</option>
                        </select>
                        <button class="button" style="width:40" value="">����</button>
                    </template:formTr>
                    <template:formTr name="�߶κ�">
                        <select name="point"  style="width:150" class="inputtext">
                            <option value="��001">Ԫ�����</option>
                            <option value="��001">�꼯-÷ˮ</option>
                            <option value="��001">��003</option>
                            <option value="��001">��004</option>
                            <option value="��001">��005</option>
                            <option value="��001">��001</option>
                            <option value="��001">��002</option>
                            <option value="��001">��025</option>
                        </select>
                        <button class="button" style="width:40" value="">����</button>
                    </template:formTr>
                      <template:formTr name="������">
                        <select name="point"  style="width:150" class="inputtext">
                            <option value="��001">��ص�1</option>
                            <option value="��001">��002</option>
                            <option value="��001">��003</option>
                            <option value="��001">��004</option>
                            <option value="��001">��025</option>
                            <option value="��001">��001</option>
                            <option value="��001">��002</option>
                            <option value="��001">��003</option>
                        </select>
                        <button class="button" style="width:40" value="">����</button>
                    </template:formTr>

                    <template:formTr name="�� ��">
                        <input  type="text" class="inputtext" style="width:140"/>��
                    </template:formTr>

                     <template:formSubmit>
                          <td>
                                <html:button property="action" styleClass="button" onclick="addGoBack()" >���ӻ���λ	</html:button>
                          </td>
                        <td>
                                <html:button property="action" styleClass="button"  onclick="goto()" >��Ϣ��λ	</html:button>
                          </td>
                    </template:formSubmit>
            </template:formTable>
</body>
</html>
