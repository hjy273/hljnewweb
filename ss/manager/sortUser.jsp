<%@include file="/common/header.jsp"%>

<%@ taglib uri="http://displaytag.sourceforge.net/" prefix="display"%>
<%@ page import="com.cabletech.partmanage.beans.*;" %>

<%@ page import="com.cabletech.commons.hb.*" %>
<%@ page import="com.cabletech.baseinfo.domainobjects.UserInfo" %>
<%@ page import="com.cabletech.manager.dao.SortDao" %>

<%
	if(request.getAttribute("type") == null  || request.getAttribute("type").equals(""))
		request.setAttribute("type","1");
	String sql="SELECT   a.userid userid,a.POSITION, a.username username,a.positionno,"
    			+ "      DECODE (a.PASSWORD, NULL, 'ƽ̨�û�', '�����û�') usertype,"
				+ "         NVL (DECODE (a.deptype,"
				+ "                      '1', c.deptname,"
				+ "                      '2', d.contractorname,"
				+ "                       c.deptname"
				+ "                      ),"
				+ "               ''"
				+ "              ) deptid,"
				+ "          NVL (b.regionname, '') regionid, a.workid, a.PASSWORD,"
				+ "         TO_CHAR (a.accountterm, 'yyyy/mm/dd'),"
				+ "         TO_CHAR (a.passwordterm, 'yyyy/mm/dd'), a.accountstate, a.phone,"
				+ "         a.email, a.POSITION,"
				+ "         DECODE (a.deptype,"
				+ "                 '1', '�ڲ�����',"
				+ "                 '2', '��ά��λ',"
				+ "                 '�ڲ�����'"
				+ "                ) deptype"
				+ " FROM userinfo a, region b, deptinfo c, contractorinfo d"
				+ " WHERE a.regionid = b.regionid(+)"
				+ "      AND a.deptid = d.contractorid(+)"
				+ "      AND a.deptid = c.deptid(+)"
				+ "      AND a.state IS NULL  "
				+ "ORDER BY TO_NUMBER(a.positionno),a.REGIONID,a.DEPTYPE,a.POSITION,a.USERNAME";
    System.out.println("SQL:" + sql);
    List luser ;
    try {
      QueryUtil qu = new QueryUtil();
      luser = qu.queryBeans(sql);
      request.setAttribute("luser",luser);
    }
    catch (Exception ex) {
      System.out.println("�����û���Ϣ����:" + ex.getMessage());
    }
%>


<html>

<head>
	<title>
	</title>
    <script type=""  language="javascript">
		function dosubmit(){
          if(SortBean.password.value == "" || SortBean.password.value == null){
          	alert("�������������!!");
            return;
          }
          SortBean.submit();

	    }
	</script>
</head>
<body >
<logic:equal value="1" name="type">

 <html:form action="/SortAction?method=saveSort" >
  		<br />
        <br />
  		<template:titile value="ָ���û�����˳��"/>
	   <logic:notEmpty name="luser">
           <table width="800" border="0" align="center" cellpadding="3" cellspacing="0"  class="tabout">
           		<tr class=trcolor>
                    <th class="tdc" width="80">�û�����</th>
                    <th class="tdc" width="80">�������</th>
                    <th class="tdc" width="140">��������</th>
                	<th class="tdc" width="100">�û����</th>
                    <th class="tdc" width="60">�û�����</th>
                    <th class="tdc" width="100">ְ ��</th>
                    <th class="tdc" width="80">��ʾ˳��</th>

                </tr>
           		<logic:iterate id="lId" name="luser">
                	<tr class=trcolor>
                        <td class="tdl"><bean:write name="lId" property="regionid"/></td>
                        <td class="tdl"><bean:write name="lId" property="deptype"/></td>
                        <td class="tdl"><bean:write name="lId" property="deptid"/></td>
                      	<td class="tdl" ><bean:write name="lId" property="userid"/></td>
                        <td class="tdl" ><bean:write name="lId" property="username"/></td>
                    	<td class="tdl"><bean:write name="lId" property="position"/></td>
                        <td class="tdl"><input  type="text"  name="positionno" class="inputtext" style="width:80" maxlength="5" value="<bean:write name="lId" property="positionno"/>"/>
							<input  type="hidden" name="userid" class="inputtext" value="<bean:write name="lId" property="userid"/>"/>
							</td>



                	</tr>
           		</logic:iterate>
           		<tr class=trcolor>
                	<td colspan="7"  class="tdc"> �������������:<input  type="text" name="password" value=""  class="inputtext"/></td>
           		</tr>
           </table>
           <template:formSubmit>
	           	<td>
	            	<html:button styleClass="button" onclick="dosubmit()" property="action"> �ύ</html:button>
	            </td>
                <td>
		            <html:reset styleClass="button">ȡ��</html:reset>
	            </td>
				<td>
				    <html:button property="action" styleClass="button"onclick="script:window.close();">����</html:button>
				</td>
            </template:formSubmit>

	   </logic:notEmpty>
  </html:form>
 </logic:equal>

 <logic:equal value="passerror" name="type">
     <br />
     <br />
     <br />
     <br />
     <br />
     <h1>
     	<p align="center" ><font color="blue">�� �� �� ��</font></p>
	 </h1>
      <br />
     <p align="center"><html:button property="action" styleClass="button"onclick="script:window.close();">����</html:button></p>

 </logic:equal>

  <logic:equal value="success1" name="type">
  		     <br />
     <br />
     <br />
     <br />
     <br />
     <h1>
     	<p align="center" ><font color="blue">�� �� �� ��</font></p>
	 </h1>
      <br />
     <p align="center"><html:button property="action" styleClass="button"onclick="script:window.close();">����</html:button></p>

  </logic:equal>

 <logic:equal value="exerror" name="type">
 		     <br />
     <br />
     <br />
     <br />
     <br />
     <h1>
     	<p align="center" ><font color="blue">�� �� ʧ ��</font></p>
	 </h1>
      <br />
     <p align="center"><html:button property="action" styleClass="button"onclick="script:window.close();">����</html:button></p>

 </logic:equal>


</body>
</html>
