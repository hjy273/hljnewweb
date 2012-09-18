package com.cabletech.commons.web;

import java.util.Random;

public class EncryptDecrypt {
	private static Random rd = new Random();

	/**
	 * ��������ַ���
	 * 
	 * @param size
	 *            �����ַ�������
	 * @return ����ַ���
	 */
	private String getRandomString(int count) {
		return getRandomString(count, null);
	}

	private String getRandomString(int count, String exclude) {
		if (exclude == null) {
			exclude = "";
		}
		if (count <= 0) {
			throw new IllegalArgumentException("Character length must be > 0");
		}
		int start = ' ';
		int end = 'z' + 1;
		int num = end - start;
		StringBuffer buf = new StringBuffer();
		char c;
		while (count-- != 0) {
			c = (char) rd.nextInt(num);
			if (Character.isLetterOrDigit(c) && exclude.indexOf((int) c) < 0) {
				buf.append(c);
			} else {
				count++;
			}
		}
		return buf.toString();
	}

	private String getStringfromLong() {
		String token = Long.toString(Math.abs(rd.nextLong()), 36);
		return token;
	}
	/**
	 * ����
	 * @param character �������ַ���
	 * @return �����ַ���
	 */
	public String Encode(String character) {
		int size = character.length();
		StringBuffer encodeStr = new StringBuffer();
		for (int i = 0; i < size; i++) {
			String temp = character.substring(i, i + 1);
			String random = getRandomString(2);
			encodeStr.append(temp);
			encodeStr.append(random.toLowerCase());
		}
		return encodeStr.toString();
	}
	/**
	 * ����
	 * @param character �����ַ���
	 * @return ԭ�ַ���
	 */
	public String Decode(String character) {
		int size = character.length();
		StringBuffer decodeStr = new StringBuffer();
		for (int i = 0; i < size;) {
			String temp = character.substring(i, i + 1);
			decodeStr.append(temp);
			i += 3;
		}
		return decodeStr.toString();
	}

	public static void main(String[] args) {
		EncryptDecrypt ed = new EncryptDecrypt();
		String org = "gzcx";
		String encode = ed.Encode(org);
		System.out.println(org + "���� ��" + encode);
		String decode = ed.Decode(encode);
		System.out.println("���� ��" + decode);

	}
}
