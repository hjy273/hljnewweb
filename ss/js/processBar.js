function processBar(form) {
//		var bgObj = document.createElement("div");
//		bgObj.setAttribute("id", "bgDiv");
//		bgObj.style.position = "absolute";
//		bgObj.style.top = "0";
//		bgObj.style.background = "#BBBBBB";
//		bgObj.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
//		bgObj.style.opacity = "0.6";
//		bgObj.style.left = "0";
//		bgObj.style.width = window.screen.availHeight;
//		bgObj.style.height = document.body.scrollHeight;
//		bgObj.style.zIndex = "3";
//		document.body.appendChild(bgObj);
//		var bgObj2 = document.createElement("div");
//		bgObj2.setAttribute("id","message");
//		bgObj2.style.position = "absolute";
//		bgObj2.style.top="80%";
//		bgObj2.style.left="40%";
//		bgObj2.style.zIndex = "4";
//		document.body.appendChild(bgObj2);
//		document.getElementById("message").innerHTML = "<img src='./images/processBar.gif' border='0'/><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;«Î…‘∫Û... </h3>";
//		alert("111");
	var elements = form.elements; 
	for (var i = 0; i < elements.length; i++) { 
	if (elements[i].type == 'submit') { 
	elements[i].disabled = true; 
	} 
	}
//	alert("1111");
	}
