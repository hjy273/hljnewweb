// 得到日期
function getPopDate(strOldDate)
{
	showx = event.screenX - event.offsetX - 4 - 210 ;
	showy = event.screenY - event.offsetY + 18;
	var retval = window.showModalDialog("/bspweb/common/CalendarDlg.htm",strOldDate, "dialogWidth:200px; dialogHeight:220px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; location=no;status:no;directories:yes;scrollbars:no;Resizable=no;help:no");
 	return (retval == null)? strOldDate: retval ;
}
