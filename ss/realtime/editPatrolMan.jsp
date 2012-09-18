<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="cabletechtag" prefix="apptag" %>
<%@ page import="com.cabletech.baseinfo.beans.*" %>
<jsp:useBean id="patrolmanBean" class="com.cabletech.baseinfo.beans.PatrolManBean" scope="request"/>

<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<body text="#000000">

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">修改巡检员信息</td>
  </tr>
  <tr>
    <td height="2" background="${ctx}/images/bg_line.gif"><img src="${ctx}/images/1px.gif" width="1" height="1"></td>
  </tr>
</table>

<html:form method="Post" action="/updatePatrolManAction.do" focus="patrolName">
 <html:hidden    property="patrolID" value="<%=patrolmanBean.getPatrolID()%>"/>
   <table width="98%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
    <tr>
      <th class="thlist">项目</th>
      <th class="thlist">填写</th>
    </tr>
    <tr class="trcolor">
      <td class="tdulleft">巡检员姓名：</td>
      <td class="tdulright">
         <html:text         property="patrolName"   value="<%=patrolmanBean.getPatrolName()%>"    size="20"       maxlength="45"/>
      </td>
    </tr>
  <tr class="trwhite">
      <td class="tdulleft">巡检员ID：</td>
      <td class="tdulright">
        <html:text         property="patrolID"   value="<%=patrolmanBean.getPatrolID()%>"   size="20"      maxlength="45"/>
      </td>
    </tr>

  <tr class="trcolor">
       <td class="tdulleft">巡检员性别：</td>
       <td class="tdulright">
        <html:text     property="sex"  value="<%=patrolmanBean.getSex()%>"    size="20"      maxlength="45"/>
      </TD>
     </TR>
  <tr class="trwhite">
       <td class="tdulleft">出生日期：</td>
       <td class="tdulright">

        <html:text          property="birthday"   value="<%=patrolmanBean.getBirthday()%>"  size="20"      maxlength="45"/>
      </TD>
     </TR>
  <tr class="trwhite">
       <td class="tdulleft">文化程度：</td>
       <td class="tdulright">

        <html:text          property="culture"    value="<%=patrolmanBean.getCulture()%>"  size="20"      maxlength="45"/>
      </TD>
     </TR>
  <tr class="trwhite">
       <td class="tdulleft">婚否：</td>
       <td class="tdulright">

        <html:text          property="isMarriage"   value="<%=patrolmanBean.getIsMarriage()%>"  size="20"      maxlength="45"/>
      </TD>
     </TR>
  <tr class="trwhite">
       <td class="tdulleft">年龄：</td>
       <td class="tdulright">

        <html:text   property="age"   value="<%=java.lang.String.valueOf((patrolmanBean.getAge()))%>"  size="20"      maxlength="45"/>
      </TD>
     </TR>
  <tr class="trwhite">
       <td class="tdulleft">联系电话：</td>
       <td class="tdulright">

        <html:text          property="phone"   value="<%=patrolmanBean.getPhone()%>"   size="20"      maxlength="45"/>
      </TD>
     </TR>
  <tr class="trwhite">
       <td class="tdulleft">联系手机：</td>
       <td class="tdulright">

        <html:text          property="patrol"   value="<%=patrolmanBean.getPhone()%>"  size="20"      maxlength="45"/>
      </TD>
     </TR>
  <tr class="trwhite">
       <td class="tdulleft">邮政编码：</td>
       <td class="tdulright">

        <html:text   property="postalcode"  value="<%=patrolmanBean.getPostalcode()%>"   size="20"      maxlength="45"/>
      </TD>
     </TR>
  <tr class="trwhite">
       <td class="tdulleft">身份证编码：</td>
       <td class="tdulright">

        <html:text          property="identityCard"  value="<%=patrolmanBean.getIdentityCard()%>"    size="20"      maxlength="45"/>
      </TD>
     </TR>
  <tr class="trwhite">
       <td class="tdulleft">家庭住址：</td>
       <td class="tdulright">

        <html:text          property="familyAddress"   value="<%=patrolmanBean.getFamilyAddress()%>"   size="20"      maxlength="45"/>
      </TD>
     </TR>
  <tr class="trwhite">
       <td class="tdulleft">主要工作经历：</td>
       <td class="tdulright">

        <html:text          property="workRecord"   value="<%=patrolmanBean.getWorkRecord()%>"   size="20"      maxlength="45"/>
      </TD>
     </TR>  <tr class="trwhite">
       <td class="tdulleft">所属单位ID：</td>
       <td class="tdulright">

        <html:text          property="parentID"   value="<%=patrolmanBean.getParentID()%>"    size="20"      maxlength="45"/>
      </TD>
     </TR>  <tr class="trwhite">
       <td class="tdulleft">工作情况：</td>
       <td class="tdulright">

        <html:text          property="jobInfo"    value="<%=patrolmanBean.getJobInfo()%>" size="20"      maxlength="45"/>
      </TD>
     </TR>  <tr class="trwhite">
       <td class="tdulleft">工作状态：</td>
       <td class="tdulright">

        <html:text          property="jobState"    value="<%=patrolmanBean.getJobState()%>"  size="20"      maxlength="45"/>
      </TD>
  <tr class="trcolor">
       <td class="tdulleft">备注：</td>
       <td class="tdulright">
        <html:text          property="remark"     value="<%=patrolmanBean.getRemark()%>"  size="20"      maxlength="45"/>
      </TD>
     </TR>
    </TABLE>
<!--按钮表格开始-->
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="2" background="${ctx}/images/bg_line.gif"><img src="${ctx}/images/1px.gif" width="1" height="1"></td>
  </tr>
  <tr>
    <td align="right"><table  border="0" align="center" cellpadding="0" cellspacing="0">
      <tr align="center">
        <td width="85">
             <html:submit property="action" tabindex="16">提交</html:submit>
        </td>
        <td width="85">
            <html:submit property="action" tabindex="17">取消</html:submit>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
<!--按钮表格结束-->
</html:form>
