package com.cabletech.commons.util;

import java.text.NumberFormat;

/**
 * 空间数据库操作工具
 * 
 * @author runthu
 * 
 */
public class GeolocTik {

	private String FomatMyString(String str, int nt) {
		int lnt = 0;

		lnt = str.length();

		if (lnt >= nt) {
			return str.substring(0, nt);
		} else {
			int i;
			for (i = 0; i < (nt - lnt); i++) {
				str += "0";
			}
			return str;
		}
	}

	private int getInt(double input) {
		String str = String.valueOf(input);

		int dot = 0;
		int afterdot = 0;
		dot = str.indexOf(".");
		afterdot = Integer.parseInt(str.substring(dot + 1, dot + 2));
		if (afterdot > 4) {
			return Integer.parseInt(str.substring(0, dot));
		} else {
			return Integer.parseInt(str.substring(0, dot)) + 1;
		}
	}

	/**
	 * 坐标转换
	 * 
	 * @param strGPSCoordinate
	 * @return String 23 097313 113 188366
	 */
	public String getGps(String strGPSCoordinate) {
		String strLatDu = strGPSCoordinate.substring(0, 2);
		String strLatFen = strGPSCoordinate.substring(2, 8);
		String strLongDu = strGPSCoordinate.substring(8, 11);
		String strLongFen = strGPSCoordinate.substring(11, 17);

		double dbLatDu = java.lang.Double.parseDouble(strLatDu);
		double dbLatFen = java.lang.Double.parseDouble(strLatFen);
		double dbLongDu = java.lang.Double.parseDouble(strLongDu);
		double dbLongFen = java.lang.Double.parseDouble(strLongFen);

		dbLatFen = dbLatFen / 600000;
		dbLongFen = dbLongFen / 600000;

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(8);
		nf.setMaximumIntegerDigits(3);

		dbLatDu = dbLatDu + dbLatFen;
		dbLongDu = dbLongDu + dbLongFen;
		nf.format(dbLatDu);
		nf.format(dbLongDu);
		String dtLd = String.valueOf(dbLongDu);
		if (dtLd.length() > 12)
			dtLd = dtLd.substring(0, 11);
		String dtLf = String.valueOf(dbLatDu);
		if (dtLf.length() > 12)
			dtLf = dtLf.substring(0, 11);

		return dtLd + "," + dtLf;
	}

	/**
	 * X,Y转换成GPS字符串
	 * 
	 * @deprecated
	 * @param x
	 * @param y
	 * @return
	 */
	public String setGpsStringsssss(String x, String y) {

		String gpscoordinate = "";
		String intx = x.substring(0, x.indexOf("."));
		String floatx = "0" + x.substring(x.indexOf("."));

		String inty = y.substring(0, y.indexOf("."));
		String floaty = "0" + y.substring(y.indexOf("."));

		String longx = String.valueOf(Math
				.round(Double.parseDouble(floatx) * 60 * 10000));
		String longy = String.valueOf(Math
				.round(Double.parseDouble(floaty) * 60 * 10000));
		if (longx.length() < 6) {
			longx = "0" + longx;
		}
		if (longy.length() < 6) {
			longy = "0" + longy;
		}
		gpscoordinate = inty + "" + longy + "" + intx + "" + longx;

		return gpscoordinate;
	}

	/**
	 * 取得坐标字串
	 * 
	 * @param aString
	 *            String
	 * @return String
	 */
	public String setGpsString(String x, String y) {
		String ladu = null;
		String lafen = null;
		String longdu = null;
		String longfen = null;
		String orgla = null;
		String orglong = null;
		String rstr = null;

		orgla = String.valueOf(x);
		orglong = String.valueOf(y);
		ladu = orgla.split("\\.")[0];
		lafen = "0." + orgla.split("\\.")[1];
		longdu = orglong.split("\\.")[0];
		longfen = "0." + orglong.split("\\.")[1];
		lafen = String.valueOf((Double.parseDouble(lafen)) * 60);

		longfen = String.valueOf((Double.parseDouble(longfen)) * 60);

		if (lafen.split("\\.")[0].length() == 1) {
			lafen = "0" + lafen.split("\\.")[0] + lafen.split("\\.")[1];
		} else {
			lafen = lafen.split("\\.")[0] + lafen.split("\\.")[1];
		}

		if (longfen.split("\\.")[0].length() == 1) {
			longfen = "0" + longfen.split("\\.")[0] + longfen.split("\\.")[1];
		} else {
			longfen = longfen.split("\\.")[0] + longfen.split("\\.")[1];
		}

		lafen = FomatMyString(lafen, 6);

		if (ladu.length() < 3) {
			ladu = "0" + ladu;
		}
		longfen = FomatMyString(longfen, 6);

		rstr = longdu + longfen + ladu + lafen;

		return rstr;
	}

	/**
	 * 获取GPS字符串的XY坐标,数组表示
	 * 
	 * @param gps
	 * @return
	 */
	public String[] getGpsXY(String gps) {
		GeolocTik geoloc = new GeolocTik();
		String[] xy = { "0", "0" };
		if (gps == null || gps.trim().equals("") || gps.length() != 17) {
			xy[0] = "非法坐标X";
			xy[1] = "非法坐标Y";
		} else {
			String geoGps = geoloc.getGps(gps);
			xy = geoGps.split(",");
		}
		return xy;
	}

	/**
	 * 获取X坐标
	 * 
	 * @param gps
	 * @return
	 */
	public String getGpsX(String gps) {
		String[] xy = this.getGpsXY(gps);
		return xy[0];
	}

	/**
	 * 获取Y坐标
	 * 
	 * @param gps
	 * @return
	 */
	public String getGpsY(String gps) {
		String[] xy = this.getGpsXY(gps);
		return xy[1];
	}

	public static void main(String args[]) {
		// System.out.println(new GeolocTik().getGps("23009918113078666"));
		System.out.println(new GeolocTik()
				.setGpsString("113.22", "23.11"));
		// 33 43 8582 113 19 4912
	}
}
