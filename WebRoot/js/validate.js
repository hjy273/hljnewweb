
var numberFloat = /^(\-)?\d+(\.\d+)?$/;
var numberInt = /^\d+$/;
String.prototype.getGBLength = function () {
	var len = 0;
	if (typeof (this) == "undefined" || this == null || this == "") {
		return 0;
	}
	for (var i = 0; i < this.length; i++) {
		if (this.charCodeAt(i) > 127 || this.charCodeAt(i) == 94) {
			len += 2;
		} else {
			len++;
		}
	}
	return len;
};
convertStrToDate = function (dateTimeStr, dateTimeSeperator, dateSeperator, timeSeperator) {
	var dateStr = "";
	var timeStr = "";
	var d = new Date();
	if (dateTimeSeperator == null || typeof (dateTimeSeperator) == "undefined") {
		dateTimeSeperator = " ";
	}
	if (dateSeperator == null || typeof (dateSeperator) == "undefined") {
		dateSeperator = "-";
	}
	if (timeSeperator == null || typeof (timeSeperator) == "undefined") {
		timeSeperator = ":";
	}
	if (dateTimeStr.indexOf(dateTimeSeperator) != -1) {
		dateStr = dateTimeStr.split(dateTimeSeperator)[0];
	} else {
		dateStr = dateTimeStr;
	}
	if (dateTimeStr.indexOf(dateTimeSeperator) != -1) {
		timeStr = dateTimeStr.split(dateTimeSeperator)[1];
	}
	try {
		if (dateStr.indexOf(dateSeperator) != -1) {
			var dateValues = dateStr.split(dateSeperator);
			d = new Date(parseInt(dateValues[0], 10), parseInt(dateValues[1], 10) - 1, parseInt(dateValues[2], 10));
		}
		if (timeStr != "" && timeStr.indexOf(timeSeperator) != -1) {
			var timeValues = timeStr.split(timeSeperator);
			d.setHours(parseInt(timeValues[0], 10));
			d.setMinutes(parseInt(timeValues[1], 10));
			d.setSeconds(parseInt(timeValues[2], 10));
		}
	}
	catch (e) {
		return -1;
	}
	return d;
};
validateTime = function (value, type, message, dateTimeSeperator, dateSeperator, timeSeperator) {
	if (dateTimeSeperator == null || typeof (dateTimeSeperator) == "undefined") {
		dateTimeSeperator = "\\s";
	}
	if (dateSeperator == null || typeof (dateSeperator) == "undefined") {
		dateSeperator = "-";
	}
	if (timeSeperator == null || typeof (timeSeperator) == "undefined") {
		timeSeperator = ":";
	}
	var timeRegx = "^\\d{1,4}" + dateSeperator + "\\d{1,2}" + dateSeperator + "\\d{1,2}$";
	if (type == "1") {
		timeRegx = "^\\d{1,4}" + dateSeperator + "\\d{1,2}" + dateSeperator + "\\d{1,2}" + dateTimeSeperator + "\\d{1,2}" + timeSeperator + "\\d{1,2}" + timeSeperator + "\\d{1,2}$";
	}
	if (value.match(timeRegx) && convertStrToDate(value) != -1) {
		return true;
	} else {
		alert(message + "\u4e0d\u662f\u6807\u51c6\u65e5\u671f\u683c\u5f0f\uff01");
		return false;
	}
};
compareTwoDate = function (date1, date2) {
	if (date1 < date2) {
		return -1;
	}
	if (date1 > date2) {
		return 1;
	}
	if (date1 == date2) {
		return 0;
	}
};
judgeEmptyValue = function (item, message) {
	var value = "";
	if (typeof (item) != "undefined") {
		if (typeof (item.length) != "undefined") {
			for (i = 0; i < item.length; i++) {
				value = item[i].value;
				if (value == "") {
					alert(message + (i + 1) + "\u503c\u4e0d\u80fd\u4e3a\u7a7a\uff01");
					return false;
				}
			}
		} else {
			value = item.value;
			if (value == "") {
				alert(message + "\u503c\u4e0d\u80fd\u4e3a\u7a7a\uff01");
				return false;
			}
		}
	} else {
		alert("\u6ca1\u6709\u627e\u5230\u5143\u7d20\uff01");
		return false;
	}
	return true;
};
judgeOnlyEmptyValue = function (item, message) {
	var value = "";
	if (typeof (item) != "undefined") {
		value = item.value;
		if (value == "") {
			alert(message + "\u503c\u4e0d\u80fd\u4e3a\u7a7a\uff01");
			return false;
		}
	} else {
		alert("\u6ca1\u6709\u627e\u5230\u5143\u7d20\uff01");
		return false;
	}
	return true;
};
judgeValueLength = function (item, maxLength, message) {
	var value = "";
	if (typeof (item) != "undefined") {
		value = item.value;
		if (value.getGBLength() > maxLength) {
			alert(message + "\u4e0d\u80fd\u8d85\u8fc7\u89c4\u5b9a\u957f\u5ea6" + maxLength + "\u4e2a\u5b57\u7b26\uff01");
			return false;
		}
	} else {
		alert("\u6ca1\u6709\u627e\u5230\u5143\u7d20\uff01");
		return false;
	}
	return true;
};
judgeValueOutOfRange = function (item, minValue, maxValue, message) {
	var value = "";
	if (typeof (item) != "undefined") {
		var value = parseFloat(item.value);
		if (value < minValue) {
			alert(message + "\u4e0d\u80fd\u5c0f\u4e8e" + minValue + "!");
			return false;
		} else {
			if (value >= maxValue) {
				alert(message + "\u4e0d\u80fd\u5927\u4e8e" + maxValue + "!");
				return false;
			}
		}
	} else {
		alert("\u6ca1\u6709\u627e\u5230\u5143\u7d20\uff01");
		return false;
	}
	return true;
};
judgeNoneSelectedValue = function (item, message) {
	if (typeof (item) != "undefined") {
		if (typeof (item.length) != "undefined") {
			for (i = 0; i < item.length; i++) {
				if (item[i].checked == true) {
					return true;
				}
			}
			alert(message + "\u81f3\u5c11\u6709\u4e00\u4e2a\u88ab\u9009\u4e2d\uff01");
			return false;
		} else {
			if (item.checked == false) {
				alert(message + "\u81f3\u5c11\u6709\u4e00\u4e2a\u88ab\u9009\u4e2d\uff01");
				return false;
			}
		}
	} else {
		alert("\u6ca1\u6709\u627e\u5230\u5143\u7d20\uff01");
		return false;
	}
	return true;
};
beforeDate = function (start, end, message) {
	if (compareTwoDate(start, end) > 0) {
		alert(message);
		return false;
	}
	return true;
};
judgeValueIsInt = function (item, message) {
	var value = "";
	if (typeof (item) != "undefined") {
		value = item.value;
		if (!numberInt.test(value)) {
			alert(message);
			return false;
		}
	} else {
		alert("\u6ca1\u6709\u627e\u5230\u5143\u7d20\uff01");
		return false;
	}
	return true;
};
judgeValueIsFloat = function (item, message) {
	var value = "";
	if (typeof (item) != "undefined") {
		value = item.value;
		if (!numberFloat.test(value)) {
			alert(message);
			return false;
		}
	} else {
		alert("\u6ca1\u6709\u627e\u5230\u5143\u7d20\uff01");
		return false;
	}
	return true;
};

