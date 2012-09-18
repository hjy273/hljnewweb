<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ page import="java.util.*" %>
<?xml version="1.0" encoding="utf-8"?>
<%@ page language="java" pageEncoding="utf-8"%>
<root>
	<%
		Map townMap = (Map)request.getAttribute("townMap");
		Map labelMap = (Map)request.getAttribute("labelMap");
		Iterator iterator = labelMap.keySet().iterator();
		while(iterator.hasNext()){
			String townId = (String)iterator.next();
			%>
			<row><region>
			<%=townMap.get(townId)%>
			</region><number>
			<%=labelMap.get(townId)%>
			</number>
			</row>
			<%
		}
	 %>
 </root>
