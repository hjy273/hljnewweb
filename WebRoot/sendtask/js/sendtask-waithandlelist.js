
/**
 * sendtask-waithandlelist.js
 * @create yangjun 2011-09-15
 * @use for sendtask-waithandlelist.jsp
 */
toDoTaskForm = function (url) {
	var url = contextPath + url;
	location.href = url;
};
toTransferForm = function (dispatchId, taskId) {
	var url = contextPath + "/sendTaskTransferAction_input.jspx?id=" + dispatchId + "&&taskId=" + taskId;
	location.href = url;
};

