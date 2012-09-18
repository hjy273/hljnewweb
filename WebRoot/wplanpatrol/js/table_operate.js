/**
 * table_operate.js
 * 
 * @author yangjun
 * @version 1.0
 * @created 2011-11-14 14:16:32
 */
var cell;
var text;
var table;
var sampleTable;
/**
 * initialize input table and sample table
 */
initTb = function(inputTbId, sampleTbId) {
	var cellNum = document.getElementById(sampleTbId).rows[0].cells.length;
	table = document.getElementById(inputTbId);
	cell = new Array(cellNum);
	text = new Array(cellNum);
	sampleTable = document.getElementById(sampleTbId);
};
/**
 * add one row to input table by sample table's template row
 */
addTbRow = function(inputTb, sampleTb) {
	initTb(inputTb, sampleTb);
	var rowId = "0";
	if (table.rows.length >= 3) {
		rowId = table.rows[table.rows.length - 1].id;
	}
	var onerow = table.insertRow(-1);
	var cells = sampleTable.rows[0].cells;
	onerow.id = table.rows.length;
	if (parseInt(rowId) >= parseInt(onerow.id)) {
		onerow.id = "" + (parseInt(rowId) + 1);
	}
	for (i = 0; i < text.length; i++) {
		text[i] = cells[i].innerHTML;
	}
	for (i = 0; i < cell.length; i++) {
		cell[i] = onerow.insertCell();
		cell[i].style.textAlign = cells[i].style.textAlign;
		cell[i].style = "padding:2px;" + cell[i].style;
		cell[i].className = cells[i].className;
		cell[i].innerHTML = text[i].replace(new RegExp("sample_", "gm"), "")
				.replace(new RegExp("row0", "gm"), onerow.id);
	}
	return onerow.id;
};
/**
 * according rowid,delete one row from input table
 */
deleteTbRow = function(inputTb, sampleTb, rowid) {
	initTb(inputTb, sampleTb);
	for (i = table.rows.length - 1; i >= 0; i--) {
		if (table.rows[i].id == rowid) {
			table.deleteRow(table.rows[i].rowIndex);
		}
	}
};
/**
 * delete all rows from input table
 */
deleteAllTbRow = function(inputTb, sampleTb) {
	initTb(inputTb, sampleTb);
	for (i = table.rows.length - 1; i >= 1; i--) {
		table.deleteRow(i);
	}
};
