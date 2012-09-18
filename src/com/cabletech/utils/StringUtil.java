package com.cabletech.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtil {

	public static synchronized int length(String strExp) {
		return strExp.getBytes().length;
	}

	public static synchronized String left(String strExp, int intLen) {
		if (intLen <= 0)
			return "";

		if (length(strExp) <= intLen)
			return strExp;

		if (length(strExp) == strExp.length())
			return strExp.substring(0, intLen);

		int intCLoop = 0;
		int intBLoop = 0;
		byte abytTemp[] = strExp.getBytes();

		while (true) {
			if (abytTemp[intBLoop] > 127 || abytTemp[intBLoop] < 0) {
				if (intBLoop + 2 > intLen) {
					break;
				} else {
					intBLoop++;
					intBLoop++;
				}
			} else {
				if (intBLoop + 1 > intLen) {
					break;
				} else {
					intBLoop++;
				}
			}
			intCLoop++;
		}

		return strExp.substring(0, intCLoop);
	}

	public static synchronized String right(String strExp, int intLen) {
		if (intLen <= 0)
			return "";

		if (length(strExp) <= intLen)
			return strExp;

		if (length(strExp) == strExp.length())
			return strExp.substring(strExp.length() - intLen);

		int intCLoop = 0;
		int intBLoop = 0;
		byte abytTemp[] = strExp.getBytes();

		while (intBLoop < length(strExp) - intLen) {
			if (abytTemp[intBLoop] > 127 || abytTemp[intBLoop] < 0) {
				intBLoop++;
				intBLoop++;
			} else {
				intBLoop++;
			}
			intCLoop++;

		}
		return strExp.substring(intCLoop);
	}

	public static synchronized String substring(String strExp, int intFrom) {
		if (intFrom <= 0)
			return strExp;

		if (length(strExp) <= intFrom)
			return "";

		int intCLoop = 0;
		int intBLoop = 0;
		byte abytTemp[] = strExp.getBytes();

		while (true) {
			if (abytTemp[intBLoop] > 127 || abytTemp[intBLoop] < 0) {
				if (intBLoop + 2 > intFrom) {
					break;
				} else {
					intBLoop++;
					intBLoop++;
				}
			} else {
				if (intBLoop + 1 > intFrom) {
					break;
				} else {
					intBLoop++;
				}
			}
			intCLoop++;
		}

		return strExp.substring(intCLoop);
	}

	public static synchronized String substring(String strExp, int intFrom, int intLen) {
		if (intLen <= 0)
			return "";

		strExp = substring(strExp, intFrom);
		if (strExp.length() > 0) {
			strExp = left(strExp, intLen);
		}
		return strExp;
	}

	public static synchronized int indexOf(String strExp, String strSub) {

		int intTemp = strExp.indexOf(strSub);
		if (intTemp <= 0 || length(strExp) == strExp.length())
			return intTemp;

		int intCLoop = 0;
		int intBLoop = 0;
		byte abytTemp[] = strExp.getBytes();

		while (intCLoop < intTemp) {
			if (abytTemp[intBLoop] > 127 || abytTemp[intBLoop] < 0) {
				intBLoop++;
				intBLoop++;
			} else {
				intBLoop++;
			}
			intCLoop++;
		}

		return intBLoop;
	}

	public static synchronized int indexOf(String strExp, String strSub, int intFrom) {
		intFrom = intFrom < 0 ? 0 : intFrom;

		if (length(strExp) < intFrom)
			return -1;

		if (length(strExp) == strExp.length())
			return strExp.indexOf(strSub, intFrom);

		int intCLoop = 0;
		int intBLoop = 0;
		byte abytTemp[] = strExp.getBytes();

		while (true) {
			if (abytTemp[intBLoop] > 127 || abytTemp[intBLoop] < 0) {
				if (intBLoop + 2 > intFrom) {
					break;
				} else {
					intBLoop++;
					intBLoop++;
				}
			} else {
				if (intBLoop + 1 > intFrom) {
					break;
				} else {
					intBLoop++;
				}
			}
			intCLoop++;
		}

		intFrom = intCLoop;

		int intTemp = strExp.indexOf(strSub, intFrom);
		if (intTemp <= 0)
			return intTemp;

		intCLoop = 0;
		intBLoop = 0;

		while (intCLoop < intTemp) {
			if (abytTemp[intBLoop] > 127 || abytTemp[intBLoop] < 0) {
				intBLoop++;
				intBLoop++;
			} else {
				intBLoop++;
			}
			intCLoop++;
		}
		return intBLoop;
	}

	public static synchronized int lastIndexOf(String strExp, String strSub) {
		int intTemp = strExp.lastIndexOf(strSub);
		if (intTemp <= 0 || length(strExp) == strExp.length())
			return intTemp;

		int intCLoop = 0;
		int intBLoop = 0;
		byte abytTemp[] = strExp.getBytes();

		while (intCLoop < intTemp) {
			if (abytTemp[intBLoop] > 127 || abytTemp[intBLoop] < 0) {
				intBLoop++;
				intBLoop++;
			} else {
				intBLoop++;
			}
			intCLoop++;
		}

		return intBLoop;
	}

	public static synchronized String replace(String strExp, String strFind, String strRep) {
		int intFrom, intTo;
		String strHead, strTail;

		if (strFind == null || strFind.length() == 0)
			return strExp;

		intFrom = strExp.indexOf(strFind, 0);
		while (intFrom >= 0) {
			intTo = intFrom + strFind.length();
			strHead = strExp.substring(0, intFrom);
			strTail = strExp.substring(intTo);
			strExp = strHead + strRep + strTail;
			intTo = intFrom + strRep.length();
			intFrom = strExp.indexOf(strFind, intTo);
		}

		return strExp;
	}

	public static synchronized String ltrim(String strExp, String strTrim) {
		int i;

		if (strExp == null || strExp.length() == 0) {
			strExp = "";
		}

		if (strTrim == null || strTrim.length() == 0)
			return strExp;

		char chrTrim = strTrim.charAt(0);

		for (i = 0; i < strExp.length(); i++) {
			if (strExp.charAt(i) != chrTrim) {
				break;
			}
		}

		return strExp.substring(i);
	}

	public static synchronized String rtrim(String strExp, String strTrim) {
		int i;

		if (strExp == null || strExp.length() == 0) {
			strExp = "";
		}

		int intLen = strExp.length();

		if (strTrim == null || strTrim.length() == 0)
			return strExp;

		char chrTrim = strTrim.charAt(0);

		for (i = 0; i < strExp.length(); i++) {
			if (strExp.charAt(intLen - i - 1) != chrTrim) {
				break;
			}
		}

		return strExp.substring(0, intLen - i);
	}

	public static synchronized String trim(String strExp) {
		return trim(strExp, " ");
	}

	public static synchronized String trim(String strExp, String strTrim) {
		return ltrim(rtrim(strExp, strTrim), strTrim);
	}

	public static synchronized String lpad(String strExp, int intLen, String strPad) {
		if (strExp == null) {
			strExp = "";
		}

		while (strExp.length() < intLen) {
			strExp = strPad + strExp;
		}
		return right(strExp, intLen);
	}

	public static synchronized String rpad(String strExp, int intLen, String strPad) {
		if (strExp == null) {
			strExp = "";
		}

		while (strExp.length() < intLen) {
			strExp = strExp + strPad;
		}
		return left(strExp, intLen);
	}

	public static synchronized String HtmlString(String strExp) {
		if (strExp == null || strExp.length() == 0)
			return "";

		strExp = replace(strExp, "&", "&amp;");
		strExp = replace(strExp, "<", "&lt;");
		strExp = replace(strExp, ">", "&gt;");
		strExp = replace(strExp, "\"", "&quot;");
		strExp = replace(strExp, " ", "&nbsp;");
		return strExp;
	}

	// Transfer text string into html format

	public static synchronized String HtmlStringFromText(String strExp) {
		return HtmlStringFromText(strExp, "");
	}

	// Transfer text string into html format

	public static synchronized String HtmlStringFromText(String strExp, String strPad) {
		if (strExp == null || strExp.length() == 0)
			return strPad;

		strExp = replace(strExp, "&", "&amp;");
		strExp = replace(strExp, "<", "&lt;");
		strExp = replace(strExp, ">", "&gt;");
		strExp = replace(strExp, "\"", "&quot;");
		strExp = replace(strExp, "\r\n", "<br>\r\n");
		return strExp;
	}

	// Get data from database, and put it into JSP file as a display string

	public static synchronized String HtmlString(String strExp, String strPad) {

		if (strExp == null || strExp.trim().length() == 0)
			return strPad;
		strExp = strExp.trim();

		strExp = HtmlString(strExp);
		return strExp;
	}

	public static synchronized String ShowTextArea(String strExp) {
		if (strExp == null || strExp.length() == 0)
			return "";

		strExp = replace(strExp, "&", "&amp;");
		strExp = replace(strExp, "<", "&lt;");
		strExp = replace(strExp, ">", "&gt;");
		strExp = replace(strExp, "\"", "&quot;");
		return strExp;
	}

	public static synchronized String SpecString(String strExp) {
		if (strExp == null || strExp.length() == 0)
			return "";

		strExp = replace(strExp, "\\", "\\\\");
		strExp = replace(strExp, "\"", "\\\"");
		return strExp;
	}

	public static synchronized String[] getAccountInfoString(String strExp) {
		if (strExp == null || strExp.length() == 0)
			return null;
		StringTokenizer st = new StringTokenizer(strExp, ";");
		String[] strTemp = new String[2];
		strTemp[1] = "";
		int i = 0;
		while (st.hasMoreTokens()) {
			strTemp[i++] = st.nextToken();
		}
		return strTemp;
	}

	public static synchronized List convertStringArrayToList(String[] string) {
		List list = new ArrayList();
		for (int i = 0; string != null && i < string.length; i++) {
			list.add(string[i]);
		}
		return list;
	}
}
