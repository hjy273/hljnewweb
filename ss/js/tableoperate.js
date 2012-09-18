var cell;
var text;
var table;
var sampleTable;
initTb = function(inputTbId, sampleTbId) {
	var cellNum = document.getElementById(sampleTbId).rows[0].cells.length;
	table = document.getElementById(inputTbId);
	cell = new Array(cellNum);
	text = new Array(cellNum);
	sampleTable = document.getElementById(sampleTbId);
};
addTbRow = function(inputTb, sampleTb, rowClass,rowStyle) {
	initTb(inputTb, sampleTb);
	var rowId = "0";
	if (table.rows.length >= 2) {
		rowId = table.rows[table.rows.length - 1].id;
	}
	var onerow = table.insertRow(-1);
	var cells = sampleTable.rows[0].cells;
	onerow.id = table.rows.length - 2;
	if (typeof (rowClass) != "undefined") {
		onerow.className = rowClass;
	}
	if (typeof (rowStyle) != "undefined") {
		onerow.style = rowStyle;
	}
	if (parseInt(rowId) >= parseInt(onerow.id)) {
		onerow.id = "" + (parseInt(rowId) + 1);
	}
	for (i = 0; i < text.length; i++) {
		text[i] = cells[i].innerHTML;
	}
	for (i = 0; i < cell.length; i++) {
		cell[i] = onerow.insertCell();
		cell[i].style.textAlign = cells[i].style.textAlign;
		cell[i].innerHTML = text[i].replace(new RegExp("sample_", "gm"), "")
				.replace(new RegExp("row0", "gm"), onerow.id);
	}
	if (inputTb == "itemTableSpecial") {
		var id = parseInt(onerow.id) + 1;
		//table.rows[id].cells[0].innerHTML = onerow.id;
	}
	return onerow.id;
};
deleteTbRow = function(inputTb, sampleTb, rowid) {
	initTb(inputTb, sampleTb);
	for (i = table.rows.length - 1; i > 0; i--) {
		if (table.rows[i].id == rowid) {
			table.deleteRow(table.rows[i].rowIndex);
		}
	}
};
deleteAllTbRow = function(inputTb, sampleTb) {
	initTb(inputTb, sampleTb);
	for (i = table.rows.length - 1; i >= 1; i--) {
		table.deleteRow(i);
	}
};
