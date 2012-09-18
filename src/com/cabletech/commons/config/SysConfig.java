package com.cabletech.commons.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.cabletech.commons.util.StrReformSplit;

public class SysConfig {
	private static SysConfig config;
	private Logger logger = Logger.getLogger(SysConfig.class);
	private String noteGrade;
	private String kmGrade;
	private String onlineDayGrade;

	/**
	 * ��ʼ��ϵͳ���� ��Ϣ
	 */
	public static SysConfig newInstance() {
		try {
			if (config == null) {
				config = new SysConfig();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return config;
	}

	/**
	 * ����������
	 */
	public SysConfig() {
		String filePath = ConfigPathUtil
				.getClassPathConfigFile("sysconfig.properties");
		Properties prop = new Properties();
		try {
			logger.info(filePath);
			FileInputStream fis = new FileInputStream(filePath);
			prop.load(fis);
			// �������ò���
			this.setNoteGrade(prop.getProperty("NOTE_GRADE"));
			this.setKmGrade(prop.getProperty("KM_GRADE"));
			this.setOnlineDayGrade(prop.getProperty("ONLINEDAY_GRADE"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getNoteGrade() {
		return noteGrade;
	}

	public void setNoteGrade(String noteGrade) {
		this.noteGrade = noteGrade;
	}

	public String getKmGrade() {
		return kmGrade;
	}

	public void setKmGrade(String kmGrade) {
		this.kmGrade = kmGrade;
	}

	public String getOnlineDayGrade() {
		return onlineDayGrade;
	}
	
	public void setOnlineDayGrade(String onlineDayGrade) {
		this.onlineDayGrade = onlineDayGrade;
	}
	/**
	 * ����,�ָ�Ĺ����������з֣�
	 * @return list ���طָ�������
	 */
	public List getSplitKmGrade(){
		return StrReformSplit.getStrSplit(this.getKmGrade(), ",");
	}
	/**
	 * ����,�ָ�Ķ����������з֣�
	 * @return list ���طָ�������
	 */
	public List getSplitNoteGrade(){
		return StrReformSplit.getStrSplit(this.getNoteGrade(), ",");
	}
	/**
	 * ����,�ָ���������������з֣�
	 * @return list ���طָ�������
	 */
	public List getSplitOnlineDayGrade(){
		return StrReformSplit.getStrSplit(this.getOnlineDayGrade(), ",");
	}

}
