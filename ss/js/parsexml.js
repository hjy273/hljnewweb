function parsexml(select_root,req){
	 var xmlDoc=req.responseXML;
            var xSel=xmlDoc.getElementsByTagName('select');
            //alert(xSel.length);
            select_root.options.length=0;
            for(var i=0;i<xSel.length;i++)
            {
                var xValue=xSel[i].childNodes[1].firstChild.nodeValue;
                var xText=xSel[i].childNodes[0].firstChild.nodeValue;
                var option=new Option(xText,xValue);
                try{
                    select_root.add(option);
                }catch(e){
                }
            }
}
