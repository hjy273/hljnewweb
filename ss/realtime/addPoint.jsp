<%@page contentType="text/html; charset=GBK"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="cabletechtag" prefix="apptag"%>
<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="tpBean" class="com.cabletech.baseinfo.beans.PointBean" scope="request"/>
<%@page import="com.cabletech.lineinfo.common.TableList"%>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<body text="#000000">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">增加巡检点信息</td>
  </tr>
  <tr>
    <td height="2" background="${ctx}/images/bg_line.gif">
      <img src="${ctx}/images/1px.gif" width="1" height="1">
    </td>
  </tr>
</table>
<html:form method="Post" action="/tpAction.do?method=addPoint">
  <table width="98%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
    <tr>
      <th class="thlist">项目</th>
      <th class="thlist">填写</th>
    </tr>
    <tr class="trwhite">
      <td class="tdulleft">巡检点编号</td>
      <td class="tdulright">
        <html:text property="pointID" value="<%=tpBean.getPointID()%>" size="20" maxlength="45"/>
      </td>
    </tr>
    <tr class="trcolor">
      <td class="tdulleft">巡检点名称</td>
      <td class="tdulright">
        <html:text property="pointName" value="<%=tpBean.getPointName()%>" size="20" maxlength="45"/>
      </td>
    </tr>
    <tr class="trcolor">
      <td class="tdulleft">巡检点地址</td>
      <td class="tdulright">
        <html:text property="addressInfo" value="<%=tpBean.getAddressInfo()%>" size="20" maxlength="45"/>
      </TD>
    </TR>
    <tr class="trcolor">
      <td class="tdulleft">GPS坐标</td>
      <td class="tdulright">
        <html:text property="gpsCoordinate" value="<%=tpBean.getGpsCoordinate()%>" size="20" maxlength="45"/>
      </TD>
    </TR>
    <tr class="trcolor">
      <td class="tdulleft">所属段</td>
      <td class="tdulright">
        <html:select property="subLineID" size="1" style="length=2">
          <html:options collection="sublineList" property="value" labelProperty="label"/>
        </html:select>
      </TD>
    </TR>
    <tr class="trwhite">
      <td class="tdulleft">线路类型</td>
      <td class="tdulright">
        <html:text property="lineType" value="<%=tpBean.getLineType()%>"/>
      </TD>
    </TR>
    <tr class="trcolor">
      <td class="tdulleft">isFocus</td>
      <td class="tdulright">
        <html:text property="isFocus" value="<%=tpBean.getIsFocus()%>" size="20" maxlength="45"/>
      </TD>
    </TR>
    <tr class="trcolor">
      <td class="tdulleft">所属区域</td>
      <td class="tdulright">
        <html:text disabled="True" property="regionID" value="<%=tpBean.getRegionID()%>"/>
      </TD>
    </TR>
  </TABLE>
  <!--按钮表格开始-->
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="2" background="${ctx}/images/bg_line.gif">
        <img src="${ctx}/images/1px.gif" width="1" height="1">
      </td>
    </tr>
    <tr>
      <td align="right">
        <table border="0" align="center" cellpadding="0" cellspacing="0">
          <tr align="center">
            <td width="85">
              <html:submit property="action" tabindex="16">增 加</html:submit>
            </td>
            <td width="85">
              <html:submit property="action" tabindex="17">取 消</html:submit>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <!--按钮表格结束-->
</html:form>

