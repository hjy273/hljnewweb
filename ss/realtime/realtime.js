function toSMS(id,sim,pw,tm){

	var page = "/WebApp/realtime/sendMsgMenu.jsp?id="+id+"&sim="+sim+"&pw="+pw+"&tm="+tm;
	//alert(page);
	var popWin = window.open(page,'smsPop','width=360,height=500');
	popWin.focus();
}
function toReSMS(id,sim,pw){

	var page = "/WebApp/smsAction.do?method=showReSMS&id="+id+"&sim="+sim+"&pw="+pw;
	//alert(page);
	var popWin = window.open(page,'smsPop','width=360,height=500');
	popWin.focus();
}
function toCommonSMS(path){

	var sim = terminalBean.simNumber.value;

	var page = path+"?SimIDList="+sim;
	//alert(page);
	var popWin = window.open(page,'smsPop','width=360,height=300');
	popWin.focus();
}

function sendSms(){

	var content = terminalid.value + password.value + "1" + interval.value + returnnum.value + "0";
	//				�ƶ���ID			����				��������	���ʱ��				����				ģʽ
	//alert(content);

	SmsSendLogBean.content.value = content;
	SmsSendLogBean.simid.value = simid.value;

	if(confirm("ȷ�Ϸ��� ��")){
		SmsSendLogBean.submit();
	}
}
