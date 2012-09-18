<%
		java.util.Enumeration e = request.getParameterNames();
		String pressUrl = "";
		while (e.hasMoreElements()) {
			String prams = (String) e.nextElement().toString();
			if(prams.startsWith("d-")){ 
				pressUrl = prams+"="+request.getParameter(prams);
			} 
		}
		
		String queryAction = (String)request.getSession().getAttribute("previousURL"); 
				
		if(queryAction.indexOf("&d-")!=-1){ 
			String[] arr = queryAction.split("&"); 
			queryAction = "";
			for(int i=0;i<arr.length;i++){
				if(!arr[i].startsWith("d-")){ 
					queryAction += arr[i] + "&";
				}
			}
		}  
		
		queryAction = queryAction+pressUrl;
		
		request.getSession().setAttribute("previousURL", queryAction);
		
		//System.out.println("##########"+queryAction);
%>