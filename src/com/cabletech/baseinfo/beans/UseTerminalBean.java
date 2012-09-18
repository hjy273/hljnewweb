package com.cabletech.baseinfo.beans;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cabletech.commons.base.BaseBean;
/**
 * �豸ʹ�ò�ѯ����
 */
public class UseTerminalBean extends BaseBean {
	public static final String NOTE_NUM = "note";
	public static final String PATROL_KM = "km";
	public static final String ONLINE_DAY = "day";
	public static final Map TYPE_MAP = new HashMap();
	private String regionid = "";
	private String type = ""; // ������������ 1�������ŷ������� 2����Ѳ����� 3������������
	private String year = "";
	private String month = "";
	static {
		TYPE_MAP.put(NOTE_NUM, "�����ŷ�������ͳ��");
		TYPE_MAP.put(PATROL_KM, "��Ѳ�����ͳ��");
		TYPE_MAP.put(ONLINE_DAY, "����������ͳ��");
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYearMonth() {
		return this.year + "-" + this.month;
	}
	public String toSting() {
		return ToStringBuilder.reflectionToString(this);
	}
}
