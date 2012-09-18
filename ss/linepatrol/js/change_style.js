function changeStyle(){
	var objsClass = document.getElementsByTagName('td');
	for(var i=0 ; i < objsClass.length ; i++){
		if(objsClass[i].className == 'tdulleft')
			objsClass[i].className = 'tdulleft_normal';
	}
}
