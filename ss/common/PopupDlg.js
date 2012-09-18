// 得到日期
function getPopDate(strOldDate)
{
	showx = event.screenX - event.offsetX - 4 - 210 ;
	showy = event.screenY - event.offsetY + 18;
	var retval = window.showModalDialog("/WebApp/common/CalendarDlg.htm",strOldDate, "dialogWidth:222px; dialogHeight:271px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no;directories:yes;scrollbars:no;Resizable=no;help:no");
 	return (retval == null)? strOldDate: retval ;
}
