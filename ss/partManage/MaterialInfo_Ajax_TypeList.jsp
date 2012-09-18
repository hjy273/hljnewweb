<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<select name="modelid" class="inputtext" style="width:160px"
	id="modelid">
	<logic:present name="modellist">
		<logic:iterate id="r" name="modellist">
			<option value="<bean:write name="r" property="id" />">
				<bean:write name="r" property="modelname" />
			</option>
		</logic:iterate>
	</logic:present>
</select>
