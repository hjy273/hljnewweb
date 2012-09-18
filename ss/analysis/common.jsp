
<%@ page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@ page import="com.cabletech.analysis.util.WorkInfoHistoryCommon"%>

<%
	WorkInfoHistoryCommon getRange = new WorkInfoHistoryCommon();
	String regionName = (String)session.getAttribute("LOGIN_USER_REGION_NAME");
	String deptName = (String)session.getAttribute("LOGIN_USER_DEPT_NAME");
	UserInfo user = (UserInfo)session.getAttribute("LOGIN_USER");
	session.setAttribute("rangeinfo",getRange.getRangeList(user));
 %>

<select name="range" id="rangeId" onchange="go(this)">
		<logic:equal value="11" name="LOGIN_USER" property="type">
		<option value=""><%=regionName%></option>
		</logic:equal>
		<logic:equal value="12" name="LOGIN_USER" property="type">
		<option value=""><%=regionName%></option>
		</logic:equal>
		<logic:equal value="22" name="LOGIN_USER" property="type">
		<option value=""><%=deptName%></option>
		</logic:equal>
		<logic:present name="rangeinfo">
			<logic:iterate id="rangeinfoId" name="rangeinfo">
		<option value="<bean:write name="rangeinfoId" property="rangeid"/>">
			<bean:write name="rangeinfoId" property="rangename"/>
		</option>
		</logic:iterate>
		</logic:present>
</select>
